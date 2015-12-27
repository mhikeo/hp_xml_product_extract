/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import au.com.bytecode.opencsv.CSVReader;
import com.hp.inventory.audit.parser.model.Product;
import org.apache.commons.cli.ParseException;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Iterate over items in source file
 *
 * @author TCDEVELOPER
 * @version 1.0.1
 */
public class ProductIterable implements Iterable<Product>, Iterator<Product> {

    private final File source;
    private CSVReader reader;
    private String[] row;
    private final DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.localDateOptionalTimeParser();

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

    public ProductIterable(Config config) {
        this.source = new File(config.dataDirectory, SOURCE_CSV);
        this.listDelimiter = config.listDelimiter;
        this.propertiesThreshold = config.propertiesThreshold;
    }

    @Override
    public Iterator<Product> iterator() {
        if (reader == null) {
            try {
                reader = new CSVReader(new FileReader(source));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Unexpected exception.", e);
            }
        }
        return this;
    }

    @Override
    public boolean hasNext() {
        try {
            this.row = reader.readNext();
            if (this.row != null && this.row[0].toUpperCase().equals("URL")) {
                // Checks header
                return hasNext();
            } else {
                return (this.row != null);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception.", e);
        }
    }

    @Override
    public Product next() {
        return parseRow(this.row);
    }
    
    private Product parseRow(String[] row) {
        String url = row[0];
        String file = row[1];
        String extractionTime = row[2].replace(' ', 'T');

        Date extractionTimeStamp = dateTimeFormatter.parseLocalDateTime(extractionTime).toDate();
        Pattern pat = getIdPattern();
        Integer id;
        try {
            Matcher matcher = pat.matcher(file);
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

    private Pattern getIdPattern() {
        return Pattern.compile("productPage(\\d+).html");
    }
}
