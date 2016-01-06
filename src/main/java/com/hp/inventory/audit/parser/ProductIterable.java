/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import au.com.bytecode.opencsv.CSVReader;
import com.hp.inventory.audit.parser.model.Product;
import org.apache.commons.cli.ParseException;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Iterate over items in source file
 *
 * @author TCDEVELOPER
 * @version 1.0.1
 */
public class ProductIterable implements Iterable<Product> {

    private final File source;
    private CSVReader reader;
    private String[] row;
    private final DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.localDateOptionalTimeParser();
    private List<Product> backingList = new LinkedList<>();

    /**
     * The location of the source CSV file within data directory
     */
    public static final String SOURCE_CSV = "source.csv";


    /**@since 1.0.2
     */
    private String listDelimiter;

    /**@since 1.0.3
     */
    private Integer propertiesThreshold;
    private Pattern idPattern = Pattern.compile("productPage(\\d+).html");

    public ProductIterable(Config config) {
        this.source = new File(config.dataDirectory, SOURCE_CSV);
        this.listDelimiter = config.listDelimiter;
        this.propertiesThreshold = config.propertiesThreshold;
        loadProducts();
        sortProducts();
    }

    /**
     * Sort this backing list. We use a specific sorting algorithm to increase our chances
     * of properly detecting the correct document parser and selecting a more meaningful URL.
     */
    private void sortProducts() {
        final String qView = "PDPStdView"; // Unique string in "PDPStdView" type URLs
        final String qName = "---"; // Unique string in URLs constructed from product name
        Collections.sort(backingList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                Integer p1 = score(o1);
                Integer p2 = score(o2);
                return p1.compareTo(p2);
            }

            public Integer score(Product p) {
                String url = p.getProductUrl();
                int score = 0;
                score += url.contains(qView) ? 2 : 0;
                score += url.contains(qName) ? 1 : 0;
                return score;
            }

        });
    }


    /**
     * Load products from the CSV file into a backing list.
     */
    private void loadProducts() {
        if (reader == null) {
            try {
                reader = new CSVReader(new FileReader(source));
                reader.readNext(); // Ignore header
                while(true) {
                    this.row = reader.readNext();
                    if (this.row == null) return;
                    backingList.add(parseRow(this.row));
                }
            } catch (IOException e) {
                throw new RuntimeException("Unexpected exception.", e);
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Iterator<Product> iterator() {
        return backingList.iterator();
    }

    /**
     * Parse a CSV row into a Product instance.
     */
    private Product parseRow(String[] row) {
        String url = row[0];
        String file = row[1];
        String extractionTime = row[2].replace(' ', 'T');

        Date extractionTimeStamp = dateTimeFormatter.parseLocalDateTime(extractionTime).toDate();
        Integer id;
        try {
            Matcher matcher = idPattern.matcher(file);
            if (!matcher.find()) {
                throw new ParseException("Match not found.");
            } else {
                id = Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            String err = "Error while trying to parse product ID from file name: '" + file + "'. The expected format " +
                    "is 'productPage000.html' where 000 is the product ID";
            throw new RuntimeException(err);
        }

        Product p = new Product();
        p.setId(id);
        p.setSourceFile(file);
        p.setProductUrl(url);
        p.setAuditTimeStamp(extractionTimeStamp);
        p.setListDelimiter(listDelimiter);
        p.setPropertiesThreshold(propertiesThreshold);
        return p;
    }

    /**
     * Return how many products this source has. Simply counts the number of lines, excluding header.
     */
    public int size() {
        try {
            LineNumberReader lnr = new LineNumberReader(new FileReader(this.source));
            lnr.skip(Long.MAX_VALUE);
            int c = lnr.getLineNumber(); // Assume the file has a header.
            lnr.close();
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
