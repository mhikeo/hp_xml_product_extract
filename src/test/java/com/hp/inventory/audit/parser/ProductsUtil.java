package com.hp.inventory.audit.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.ParseException;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.hp.inventory.audit.parser.model.Product;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Utility class for products.
 *
 * @author scutaru
 * @version 1.0.0
 */
public class ProductsUtil {

	private final static DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.localDateOptionalTimeParser();
	private final static Pattern idPattern = Pattern.compile("productPage(\\d+).html");

	/**
	 * Loads the products from the csv file provided as parameter
	 * 
	 * @param source
	 * @return
	 */
	public static List<Product> loadProducts(InputStream source) {
		List<Product> products = new ArrayList<>();
		
		try(CSVReader reader = new CSVReader(new InputStreamReader(source))){
			List<String[]> rows = reader.readAll();
			rows.forEach(row -> {
				if(!row[0].equals("URL")){
					products.add(parseRow(row));
				}
			});
		} catch (IOException e) {
			throw new RuntimeException("Unexpected exception.", e);
		}
		return products;
	}

	private static Product parseRow(String[] row) {
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
			String err = "Error while trying to parse product ID from file name: '" + file + "'. The expected format "
					+ "is 'productPage000.html' where 000 is the product ID";
			throw new RuntimeException(err);
		}

		Product p = new Product();
		p.setId(id);
		p.setSourceFile(file);
		p.setProductUrl(url);
		p.setAuditTimeStamp(extractionTimeStamp);
		return p;
	}

}
