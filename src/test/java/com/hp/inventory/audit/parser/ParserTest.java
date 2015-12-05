/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import static org.junit.Assert.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.hp.inventory.audit.parser.parsers.DocumentParser;
import com.hp.inventory.audit.parser.parsers.DocumentParserDetector;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;

import com.hp.inventory.audit.parser.model.Product;

public abstract class ParserTest {
	private static List<Product> products;
	
	@BeforeClass
	public static void initClass() throws Exception {
		ByteArrayInputStream rulesConfig = new ByteArrayInputStream(IOUtils.toByteArray(ParserTest.class.getResourceAsStream("rules.json")));
		DocumentParserDetector.init(new InputStreamReader(rulesConfig));
		rulesConfig.reset();
		DocumentParser.init(new InputStreamReader(rulesConfig));
		rulesConfig.close();

		products = ProductsUtil.loadProducts(DesktopParserTest.class.getResourceAsStream("source.csv"));
		products.sort(new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
				return o1.getSourceFile().compareTo(o2.getSourceFile());
			}
		});
	}


	private String getBaseURL(String urlString) throws MalformedURLException {
		URL url = new URL(urlString);
		String path = url.getFile().substring(0, url.getFile().lastIndexOf('/'));
		String base = url.getProtocol() + "://" + url.getHost() + path;
		return base;
	}

	private static class ProductComparator implements Comparator<Product> {
		@Override
		public int compare(Product o1, Product o2) {
			return o1.getSourceFile().compareTo(o2.getSourceFile());
		}
	}

	protected Document parseHtml(Product product) throws IOException, MalformedURLException {
		String content = IOUtils.toString(this.getClass().getResourceAsStream(product.getSourceFile()));
		String baseUrl = getBaseURL(product.getProductUrl());
		Document doc = Jsoup.parse(content, baseUrl);
		return doc;
	}

	protected static Product findProduct(String productId) {
		Product p = new Product();
		p.setSourceFile(productId);
		int location = Collections.binarySearch(products, p, new ProductComparator());
		return products.get(location);
	}

	protected void checkIsToday(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		Calendar now = Calendar.getInstance();
		
		assertEquals(now.get(Calendar.YEAR), c.get(Calendar.YEAR));
		assertEquals(now.get(Calendar.MONTH), c.get(Calendar.MONTH));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_MONTH));
	}
	
	protected void checkIsYesterday(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		
		assertEquals(yesterday.get(Calendar.YEAR), c.get(Calendar.YEAR));
		assertEquals(yesterday.get(Calendar.MONTH), c.get(Calendar.MONTH));
		assertEquals(yesterday.get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_MONTH));
	}
}
