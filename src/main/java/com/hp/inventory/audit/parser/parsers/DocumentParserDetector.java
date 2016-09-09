/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.Config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.hp.inventory.audit.parser.RulesConfig;
import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Functions to detect the correct document parser for each page.
 * changes:
 *  - 1.0.1: support UPC product types and remove concrete parsers.
 * @author TCDEVELOPER
 * @version 1.0.1
 */
public class DocumentParserDetector {

	/**
	 * Matches the URL.
	 */
	private static final Pattern urlPattern = Pattern.compile("/pdp/(.*)/");

	/**
	 * Lowercase valid product type names
	 */
	private static Map<String, String> typeMap;

	/**
	 * URL patterns to help detecting some corner cases.
	 */
	private static LinkedHashMap<Pattern, String> urlMatches;

	/**
	 * URL patterns for exclusion
	 */
	private static Set<String> urlMatchesExcl;

	/**
	 * Product name patterns to help detecting some corner cases.
	 */
	private static LinkedHashMap<Pattern, String> prodNameMatches;

	/**
	 * Product name patterns for exclusion
	 */
	private static Set<Pattern> prodNameMatchesExcl;

	/**
	 * Content patterns for exclusion
	 */
	private static Set<String> contentMatchesExcl;

	/**
	 * Content patterns to help detecting some corner cases.
	 */
	private static Map<String, String> contentMatches;

	/**
	 * Content patterns order
	 */
	private static List<String> contentMatchesOrder;

	private static Logger log = LoggerFactory.getLogger(DocumentParserDetector.class);


	/**
	 * Uses a number of heuristics to detect the correct DocumentParser for the
	 * page.
	 * 
	 * @param definition
	 *            The product definition
	 * @param content
	 *            The page content.
	 * @return A DocumentParser instance or null if none could be found.
	 */
	public static DetectionResult detect(Product definition, String content, Config config) {

		String url = definition.getProductUrl();

		String baseUrl = getBaseURL(definition.getProductUrl());
		Document doc = Jsoup.parse(content, baseUrl);

		Matcher matcher;
		String urlType, svType, subType, title;

		try {
			String loweredContent = content.toLowerCase();
			
			// disable parsing based on content
			for (String contentPattern : contentMatchesExcl) {
				if (loweredContent.contains(contentPattern.toLowerCase())) {
					String ptype = "contentExclusion";
					return new DetectionResult(new IgnoringParser(), doc, ptype, contentPattern);
				}
			}

			// Try the URL
			if ((matcher = urlPattern.matcher(url.toLowerCase())).find()) {
				urlType = matcher.group(1).toLowerCase();

				log.debug(urlType);
				if (typeMap.containsKey(urlType)) {
					String ptype = "typeMap";
					String productType = typeMap.get(urlType);
					DocumentParser parser = new GeneralParser();
					parser.setProductType(productType);
					return new DetectionResult(parser, doc, ptype, urlType);
				}
			}

			// disable parsing of this url
			for (String urlcase : urlMatchesExcl) {
				if (url.contains(urlcase)) {
					String ptype = "urlExclusion";
					return new DetectionResult(new IgnoringParser(), doc, ptype, urlcase);
				}
			}
			
			// Try URL corner cases
			for (Pattern urlcase : urlMatches.keySet()) {
				if (urlcase.matcher(url).find()) {
					String ptype = "urlMatch";
					String productType = urlMatches.get(urlcase);
					DocumentParser parser = new GeneralParser();
					parser.setProductType(productType);
					return new DetectionResult(parser, doc, ptype, urlcase.toString());
				}
			}

			// Try content-based matchings
			for (String contentPattern : contentMatchesOrder) {
				if (loweredContent.contains(contentPattern.toLowerCase())) {
					String ptype = "contentMatch";
					String productType = contentMatches.get(contentPattern);
					DocumentParser parser = new GeneralParser();
					parser.setProductType(productType);
					return new DetectionResult(parser, doc, ptype, contentPattern);
				}
			}


			DetectionResult ret = detectFromGeneral(doc, definition, content, config);
			return ret;

		} catch (Exception ex) {
			throw new RuntimeException("Unexpected Exception", ex);
		}
	}

	private static String getBaseURL(String urlString) {
		try {
			URL url = new URL(urlString);
			String path = url.getFile().substring(0, url.getFile().lastIndexOf('/'));
			return url.getProtocol() + "://" + url.getHost() + path;
		} catch (MalformedURLException e){
			throw new RuntimeException(e);
		}
	}

	private static DetectionResult detectFromGeneral(Document doc, Product definition, String content, Config config) throws Exception {

		GeneralParser parser = new GeneralParser();

		parser.setSkipExtractProductType(true);
		AbstractProduct extracted = parser.parse(doc, definition, config);

		if (extracted != null && extracted.getProductName() != null) {
			String prodName = extracted.getProductName().toLowerCase();

			// disable parsing of this url
			for (Pattern productNamePattern : prodNameMatchesExcl) {
				if (productNamePattern.matcher(prodName).find()) {
					String ptype = "productNameExclusion";

					return new DetectionResult(new IgnoringParser(), doc, ptype, productNamePattern.toString());
				}
			}

			// Try URL corner cases
			for (Pattern productNamePattern : prodNameMatches.keySet()) {
				if (productNamePattern.matcher(prodName).find()) {
					String ptype = "productNameMatch";
					String productType  = prodNameMatches.get(productNamePattern);
					DocumentParser generalParser = new GeneralParser();
					parser.setProductType(productType);
					return new DetectionResult(
							generalParser, doc, ptype, productNamePattern.toString());
				}
			}
		}

		return new DetectionResult(new GeneralParser(), doc, "default", null);
	}


	private static <T extends DocumentParser> void addUrlMatch(String pattern, String productType) {
		urlMatches.put(Pattern.compile(pattern), productType);
	}

	private static <T extends DocumentParser> void addProdNameMatch(String pattern, String productType) {
		prodNameMatches.put(Pattern.compile(pattern, Pattern.CASE_INSENSITIVE), productType);
	}

	private static <T extends DocumentParser> void addContentMatch(String pattern, String productType) {
		contentMatches.put(pattern, productType);
		contentMatchesOrder.add(pattern);
	}

	@SuppressWarnings("unchecked")
	public static void init(Reader rulesConfig) throws IOException, ClassNotFoundException {

		RulesConfig rulesCfg;
		try(BufferedReader reader = new BufferedReader(rulesConfig)) {
			rulesCfg = (new Gson()).fromJson(reader, RulesConfig.class);
		}

		typeMap = new HashMap<>();

		for (String key : rulesCfg.typeMap.keySet()) {
			typeMap.put(key, rulesCfg.typeMap.get(key));
		}
		
		urlMatches = new LinkedHashMap<>();
		urlMatchesExcl = new HashSet<>();

		for (String excludePattern : rulesCfg.urlExclude) {
			urlMatchesExcl.add(excludePattern);
		}

		for (Map<String, String> urlMatch : rulesCfg.urlMatch) {
			for (String key : urlMatch.keySet()) {
				addUrlMatch(key, urlMatch.get(key));
			}
		}
		
		prodNameMatches = new LinkedHashMap<>();
		prodNameMatchesExcl = new HashSet<>();

		for (String excludePattern : rulesCfg.productNameExclude) {
			prodNameMatchesExcl.add(Pattern.compile(excludePattern, Pattern.CASE_INSENSITIVE));
		}

		for (Map<String, String> productNameMatch : rulesCfg.productNameMatch) {
			for (String key : productNameMatch.keySet()) {
				addProdNameMatch(key, productNameMatch.get(key));
			}
		}

		contentMatches = new HashMap<>();
		contentMatchesOrder = new ArrayList<String>();

		// catGroupTraversalByName FIRST: Laptops
		contentMatchesExcl = new HashSet<>();

		for (String excludePattern : rulesCfg.contentExclude) {
			contentMatchesExcl.add(excludePattern);
		}

		for (Map<String, String> contentMatch : rulesCfg.contentMatch) {
			for (String key : contentMatch.keySet()) {
				addContentMatch(key, contentMatch.get(key));
			}
		}
	}

	public static void init(File rulesConfig) throws IOException, ClassNotFoundException {
		init(new FileReader(rulesConfig));
	}
}
