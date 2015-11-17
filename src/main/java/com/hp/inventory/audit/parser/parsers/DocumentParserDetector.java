package com.hp.inventory.audit.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.hp.inventory.audit.parser.RulesConfig;
import com.hp.inventory.audit.parser.handlers.ResultHandler;
import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Product;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Functions to detect the correct document parser for each page.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class DocumentParserDetector {

	/**
	 * Matches the URL.
	 */
	private static final Pattern urlPattern = Pattern.compile("/pdp/(.*)/");

	/**
	 * Lowercase valid product type names
	 */
	private static Map<String, Class<? extends DocumentParser>> typeMap;

	/**
	 * URL patterns to help detecting some corner cases.
	 */
	private static Map<String, Class<? extends DocumentParser>> urlMatches;

	/**
	 * URL patterns for exclusion
	 */
	private static Set<String> urlMatchesExcl;

	/**
	 * URL patterns order
	 */
	private static List<String> urlMatchesOrder;

	/**
	 * Product name patterns to help detecting some corner cases.
	 */
	private static Map<String, Class<? extends DocumentParser>> prodNameMatches;

	/**
	 * Product name patterns for exclusion
	 */
	private static Set<String> prodNameMatchesExcl;

	/**
	 * Product name patterns order
	 */
	private static List<String> prodNameMatchesOrder;

	/**
	 * Content patterns for exclusion
	 */
	private static Set<String> contentMatchesExcl;

	/**
	 * Content patterns to help detecting some corner cases.
	 */
	private static Map<String, Class<? extends DocumentParser>> contentMatches;

	/**
	 * Content patterns order
	 */
	private static List<String> contentMatchesOrder;

	private static Logger log = LoggerFactory.getLogger(DocumentParserDetector.class);

	/**
	 * Uses a number of heuristics to detect the correct DocumentParser for the
	 * page.
	 * 
	 * @param url
	 *            The original URL of the page.
	 * @param content
	 *            The page content.
	 * @return A DocumentParser instance or null if none could be found.
	 */
	public static DocumentParser detect(Product definition, String content, ResultHandler handler) {

		String url = definition.getProductUrl();

		Matcher matcher;
		String urlType, svType, subType, title;

		try {
			String loweredContent = content.toLowerCase();
			
			// disable parsing based on content
			for (String contentPattern : contentMatchesExcl) {
				if (loweredContent.contains(contentPattern.toLowerCase())) {
					handler.addHit("contentExclusion");
					return new IgnoringParser();
				}
			}

			// Try the URL
			if ((matcher = urlPattern.matcher(url.toLowerCase())).find()) {
				urlType = matcher.group(1).toLowerCase();

				log.debug(urlType);
				if (typeMap.containsKey(urlType)) {
					handler.addHit("typeMap");
					return typeMap.get(urlType).newInstance();
				}
			}

			// disable parsing of this url
			for (String urlcase : urlMatchesExcl) {
				if (url.contains(urlcase)) {
					handler.addHit("urlExclusion");
					return new IgnoringParser();
				}
			}
			
			// Try URL corner cases
			for (String urlcase : urlMatchesOrder) {
				if (url.contains(urlcase)) {
					handler.addHit("urlMatch");
					return urlMatches.get(urlcase).newInstance();
				}
			}

			// Try content-based matchings
			for (String contentPattern : contentMatchesOrder) {
				if (loweredContent.contains(contentPattern.toLowerCase())) {
					handler.addHit("contentMatch");
					return contentMatches.get(contentPattern).newInstance();
				}
			}

			String baseUrl = getBaseURL(definition.getProductUrl());

			Document doc = Jsoup.parse(content, baseUrl);

			DocumentParser ret = detectFromGeneral(doc, definition, content, handler);
			doc = null;
			return ret;

		} catch (Exception ex) {
			throw new RuntimeException("Unexpected Exception", ex);
		}
	}

	private static String getBaseURL(String urlString) throws MalformedURLException {
		URL url = new URL(urlString);
		String path = url.getFile().substring(0, url.getFile().lastIndexOf('/'));
		String base = url.getProtocol() + "://" + url.getHost() + path;
		return base;
	}

	private static DocumentParser detectFromGeneral(Document doc, Product definition,
			String content, ResultHandler handler)
			throws Exception {

		GeneralParser parser = new GeneralParser();

		IProduct p = parser.parse(doc, definition, null);

		if (p != null && p.getProductName() != null) {
			String prodName = p.getProductName().toLowerCase();

			// disable parsing of this url
			for (String productNamePattern : prodNameMatchesExcl) {
				if (prodName.contains(productNamePattern.toLowerCase())) {
					handler.addHit("productNameExclusion");
					return new IgnoringParser();
				}
			}

			// Try URL corner cases
			for (String productNamePattern : prodNameMatchesOrder) {
				if (prodName.contains(productNamePattern.toLowerCase())) {
					handler.addHit("productNameMatch");
					return prodNameMatches.get(productNamePattern).newInstance();
				}
			}
		}
		return null;
	}

	private static <T extends DocumentParser> void addUrlMatch(String pattern, Class<T> clazz) {
		urlMatches.put(pattern, clazz);
		urlMatchesOrder.add(pattern);
	}

	private static <T extends DocumentParser> void addProdNameMatch(String pattern, Class<T> clazz) {
		prodNameMatches.put(pattern, clazz);
		prodNameMatchesOrder.add(pattern);
	}

	private static <T extends DocumentParser> void addContentMatch(String pattern, Class<T> clazz) {
		contentMatches.put(pattern, clazz);
		contentMatchesOrder.add(pattern);
	}

	@SuppressWarnings("unchecked")
	public static void init(File rulesConfig) throws IOException, ClassNotFoundException {
		StringBuilder sb = new StringBuilder();

		for (String line : Files.readAllLines(rulesConfig.toPath())) {
			sb.append(line);
			sb.append("\n");
		}

		RulesConfig rulesCfg = (new Gson()).fromJson(sb.toString(), RulesConfig.class);

		typeMap = new HashMap<>();

		for (String key : rulesCfg.typeMap.keySet()) {
			typeMap.put(key, (Class<? extends DocumentParser>) Class.forName(rulesCfg.typeMap.get(key)));
		}
		
		urlMatches = new HashMap<>();
		urlMatchesOrder = new ArrayList<String>();
		urlMatchesExcl = new HashSet<>();

		for (String excludePattern : rulesCfg.urlExclude) {
			urlMatchesExcl.add(excludePattern);
		}

		for (Map<String, String> urlMatch : rulesCfg.urlMatch) {
			for (String key : urlMatch.keySet()) {
				addUrlMatch(key, (Class<? extends DocumentParser>) Class.forName(urlMatch.get(key)));
			}
		}
		
		prodNameMatches = new HashMap<>();
		prodNameMatchesOrder = new ArrayList<String>();
		prodNameMatchesExcl = new HashSet<>();

		for (String excludePattern : rulesCfg.productNameExclude) {
			prodNameMatchesExcl.add(excludePattern);
		}

		for (Map<String, String> productNameMatch : rulesCfg.productNameMatch) {
			for (String key : productNameMatch.keySet()) {
				addProdNameMatch(key, (Class<? extends DocumentParser>) Class.forName(productNameMatch.get(key)));
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
				addContentMatch(key, (Class<? extends DocumentParser>) Class.forName(contentMatch.get(key)));
			}
		}
	}
}
