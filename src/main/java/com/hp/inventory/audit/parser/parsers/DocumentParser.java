/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.google.gson.Gson;
import com.hp.inventory.audit.parser.RulesConfig;
import com.hp.inventory.audit.parser.handlers.ResultHandler;
import com.hp.inventory.audit.parser.model.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract document parser with common utilities.
 *
 * @author TCDEVELOPER
 * @version 1.0.2
 */
public abstract class DocumentParser {

	@SuppressWarnings("unchecked")
	public static void init(File rulesConfig) throws IOException, ClassNotFoundException {
		StringBuilder sb = new StringBuilder();

		for (String line : Files.readAllLines(rulesConfig.toPath())) {
			sb.append(line);
			sb.append("\n");
		}

		RulesConfig rulesCfg = (new Gson()).fromJson(sb.toString(), RulesConfig.class);
		
		QueriesSpec.productNumberQuery = rulesCfg.queriesSpec.get("productNumberQuery");
		QueriesSpec.productNameQuery = rulesCfg.queriesSpec.get("productNameQuery");
		QueriesSpec.currentPriceQueryVisible = rulesCfg.queriesSpec.get("currentPriceQueryVisible");
		QueriesSpec.currentPriceQuery = rulesCfg.queriesSpec.get("currentPriceQuery");
		QueriesSpec.currencyQuery = rulesCfg.queriesSpec.get("currencyQuery");
		QueriesSpec.strikedPriceQuery = rulesCfg.queriesSpec.get("strikedPriceQuery");
		QueriesSpec.reviewIdPattern = Pattern.compile(rulesCfg.queriesSpec.get("reviewIdPattern"));
		QueriesSpec.ratingQuery = rulesCfg.queriesSpec.get("ratingQuery");
		QueriesSpec.reviewsQuery = rulesCfg.queriesSpec.get("reviewsQuery");
		QueriesSpec.comingSoonQuery = rulesCfg.queriesSpec.get("comingSoonQuery");
		
		QueriesSpec.specQueryPrefix = rulesCfg.queriesSpec.get("specQueryPrefix");
		QueriesSpec.specQuerySuffix = rulesCfg.queriesSpec.get("specQuerySuffix");
		QueriesSpec.specQueryPostQuery = rulesCfg.queriesSpec.get("specQueryPostQuery");
		
		QueriesSpec.linkQueryPrefix = rulesCfg.queriesSpec.get("linkQueryPrefix");
		QueriesSpec.linkQuerySuffix = rulesCfg.queriesSpec.get("linkQuerySuffix");
		QueriesSpec.linkQueryPostQuery = rulesCfg.queriesSpec.get("linkQueryPostQuery");
		
		QueriesSpec.accessoriesNameQuery = rulesCfg.queriesSpec.get("accessoriesNameQuery");
		QueriesSpec.accessoriesUrlQuery = rulesCfg.queriesSpec.get("accessoriesUrlQuery");
		
		QueriesSpec.imagesSelectQuery = rulesCfg.queriesSpec.get("imagesSelectQuery");
		QueriesSpec.imagesSelectPostQuery = rulesCfg.queriesSpec.get("imagesSelectPostQuery");
		
		
		QueriesSpec.reviewsExtractQuery = rulesCfg.queriesSpec.get("reviewsExtractQuery");
		QueriesSpec.reviewsExtractDateFormat = rulesCfg.queriesSpec.get("reviewsExtractDateFormat");
		QueriesSpec.reviewsExtractPostDateQuery = rulesCfg.queriesSpec.get("reviewsExtractPostDateQuery");
		QueriesSpec.reviewsExtractHpResponseDateQuery = rulesCfg.queriesSpec.get("reviewsExtractHpResponseDateQuery");
		QueriesSpec.reviewsExtractRatingQuery = rulesCfg.queriesSpec.get("reviewsExtractRatingQuery");
		QueriesSpec.reviewsExtractScaleQuery = rulesCfg.queriesSpec.get("reviewsExtractScaleQuery");
		QueriesSpec.reviewsExtractTitleQuery = rulesCfg.queriesSpec.get("reviewsExtractTitleQuery");
		QueriesSpec.reviewsExtractComentsQuery = rulesCfg.queriesSpec.get("reviewsExtractComentsQuery");
		QueriesSpec.reviewsExtractUsernameQuery = rulesCfg.queriesSpec.get("reviewsExtractUsernameQuery");
		QueriesSpec.reviewsExtractLocationQuery = rulesCfg.queriesSpec.get("reviewsExtractLocationQuery");
		QueriesSpec.reviewsExtractHpResponseQuery = rulesCfg.queriesSpec.get("reviewsExtractHpResponseQuery");
		QueriesSpec.reviewsExtractHpResponseUserQuery = rulesCfg.queriesSpec.get("reviewsExtractHpResponseUserQuery");
		QueriesSpec.reviewsExtractYesQuery = rulesCfg.queriesSpec.get("reviewsExtractYesQuery");
		QueriesSpec.reviewsExtractYesPostQuery = rulesCfg.queriesSpec.get("reviewsExtractYesPostQuery");
		QueriesSpec.reviewsExtractNoQuery = rulesCfg.queriesSpec.get("reviewsExtractNoQuery");
		QueriesSpec.reviewsExtractNoPostQuery = rulesCfg.queriesSpec.get("reviewsExtractNoPostQuery");
		
	}
	
	protected static class QueriesSpec {
		protected static String productNumberQuery; // = "span.prodNum";
		protected static String productNameQuery; // = "span[itemprop=name]";

		protected static String currentPriceQueryVisible; // = "div[itemprop=offers] #price_value";
		protected static String currentPriceQuery; // = "div[itemprop=offers] span[itemprop=price]";

		protected static String currencyQuery; // = "div[itemprop=offers] meta[itemprop=priceCurrency]";
		protected static String strikedPriceQuery; // = "div[itemprop=offers] del";

		protected static Pattern reviewIdPattern; // = Pattern.compile("(\\d+)");

		// protected final static String ratingQuery =
		// "span[itemprop=aggregateRating] span[itemprop=ratingValue]";
		protected static String ratingQuery; // = "#BVSecondaryCustomerRatings div.BVRRRatingNormalOutOf span.BVRRRatingNumber";

		// protected final static String reviewsQuery =
		// "span[itemprop=aggregateRating] meta[itemprop=reviewCount]";
		protected static String reviewsQuery; // = "#BVSecondaryCustomerRatings div.BVRRRatingSummaryLinks span.BVRRCount span.BVRRNumber";

		protected static String comingSoonQuery; // = "div[itemprop=offers] span:contains(Out of stock)";
		
		protected static String specQueryPrefix;
		protected static String specQuerySuffix;
		protected static String specQueryPostQuery;
		
		protected static String linkQueryPrefix;
		protected static String linkQuerySuffix;
		protected static String linkQueryPostQuery;
		
		protected static String accessoriesNameQuery;
		protected static String accessoriesUrlQuery;
		
		
		protected static String imagesSelectQuery;
		protected static String imagesSelectPostQuery;
		
		
		protected static String reviewsExtractQuery;
		protected static String reviewsExtractDateFormat;
		protected static String reviewsExtractPostDateQuery;
		protected static String reviewsExtractHpResponseDateQuery;
		protected static String reviewsExtractRatingQuery;
		protected static String reviewsExtractScaleQuery;
		protected static String reviewsExtractTitleQuery;
		protected static String reviewsExtractComentsQuery;
		protected static String reviewsExtractUsernameQuery;
		protected static String reviewsExtractLocationQuery;
		protected static String reviewsExtractHpResponseQuery;
		protected static String reviewsExtractHpResponseUserQuery;

		protected static String reviewsExtractYesQuery;
		protected static String reviewsExtractYesPostQuery;
		protected static String reviewsExtractNoQuery;
		protected static String reviewsExtractNoPostQuery;

	}

	private IProduct parsingErrorsReceiver = null;

	/**
	 * The number of accessories to add to "topAccessories" field
	 */
	private static final int ACCESSORIES_COUNT = 3;

	/**
	 * If the "prop" method assume fields are optional by default, thus not
	 * warning.
	 */
	private static final boolean DEFAULT_OPTIONAL = true;

	/**
	 * Delimiter for properties list
	 */
	protected String listDelimiter = "";

	/**
	 * Properties threshold number
	 */
	protected Integer propertiesThreshold = 3;

	protected Document doc;

	Logger log = LoggerFactory.getLogger(LaptopParser.class);
	protected Set<String> specParsed = new HashSet<>();

	public int getSpecParsed() {
		return specParsed.size();
	}

	protected Product definition;
	protected ResultHandler resultHandler;

	/**
	 * Extracts common properties, such as productName, price, striked price,
	 * rating, etc
	 * 
	 * @param product
	 * @throws Exception 
	 */
	protected void extractCommonProps(AbstractProduct product) throws Exception {

		product.setProductNumber(text(QueriesSpec.productNumberQuery));

		definition.setProductNumber(product.getProductNumber());

		listDelimiter = definition.getListDelimiter();
		propertiesThreshold = definition.getPropertiesThreshold();

		product.setParseDate(new Date());

		product.setProductName(text(QueriesSpec.productNameQuery));
		product.setProductUrl(definition.getProductUrl());

		product.setRating(rating(q(QueriesSpec.ratingQuery).text()));

		product.setStrikedPrice(cur(q(QueriesSpec.strikedPriceQuery).text()));

		String currentPriceVisible = q(QueriesSpec.currentPriceQueryVisible).text();
		String currentPrice = q(QueriesSpec.currentPriceQuery).text();

		// First try to parse price from { id="price_value" } tag
		if (currentPriceVisible != null && !currentPriceVisible.isEmpty()) {
			product.setCurrentPrice(cur(currentPriceVisible));
		}
		// If we were unable to parce the price, then try to parse it from {
		// itemprop="price" }
		if (product.getCurrentPrice() == null) {
			product.setCurrentPrice(cur(currentPrice));
		}

		// set comingSoonDate
		Elements comingSoon = q(QueriesSpec.comingSoonQuery);
		if (comingSoon.size() > 0) {
			product.setComingSoonDate(new Date());
		}
		if (definition.getCurrency() == null) {
			// only if default currency hasn't been set yet
			product.setCurrency(nullIfEmpty(q(QueriesSpec.currencyQuery).attr("content")));
		} else {
			// otherwise set it to default
			product.setCurrency(definition.getCurrency());
		}

		product.setNumberOfReviews(reviews(q(QueriesSpec.reviewsQuery).text()));

		definition.setImages(images());
		definition.setTopAccessories(accessories());

		definition.setReviews(reviews());
	}

	/**
	 * Method to extract an IProduct from a Jsoup document node.
	 * @throws Exception 
	 */
	public IProduct parse(Document doc, Product definition, ResultHandler resultHandler) throws Exception {
		this.doc = doc;
		this.definition = definition;
		this.resultHandler = resultHandler;
		Locale locale = getLocale();
		IProduct result = extract();
		checkForNonParsedSpecItems(definition, resultHandler);
		return result;
	}

	/**
	 * Implements the specific document extraction logic. Check the
	 * DocumentParser protected fields "definition", "doc", "resultHandler"
	 * expose respectively current product definition, document and result
	 * handler.
	 *
	 * The product number should be the first field to be extracted the
	 * implementation must call "definition.setProductNumber" as soon as
	 * possible.
	 *
	 * @return A concrete IProduct resulting form extraction.
	 */
	protected abstract IProduct extract() throws Exception;

	/**
	 * Detect document locale from meta tag, if possible. Else defaults to
	 * system locale.
	 */
	protected Locale getLocale() {
		String lang = q("meta[name=language]").attr("content");
		if (lang != null && !lang.trim().isEmpty()) {
			return Locale.forLanguageTag(lang);
		} else {
			return Locale.getDefault();
		}
	}

	/**
	 * Iterates over the page spec table to check if we skipped any attribute.
	 * If so, the ResultHandler#addSkippedAttribute method is called.
	 */
	protected void checkForNonParsedSpecItems(Product definition, ResultHandler resultHandler) {
		Set<String> nonParsed = new HashSet<>();
		q("#specs .desc").forEach(item -> {
			String prop = item.text();
			if (!specParsed.contains(prop)) {
				nonParsed.add(prop);
			}
		});

		if (resultHandler != null) {
			resultHandler.addNonParsedSpecItems(definition, this, nonParsed);
		}
	}

	private static final Pattern dimensionsPattern = Pattern
			.compile("(\\d*\\.?\\d*) x (\\d*\\.?\\d*) x (\\d*\\.?\\d*)");

	/**
	 * Parse a dimensions string of the format "w x d x h".
	 * 
	 * @param dimensions
	 *            The dimensions string
	 * @return an 3-sized array of BigDecimals, valued {width, depth, height}
	 */
	protected BigDecimal[] parseDimensions(String dimensions) {
		if (dimensions == null || dimensions.trim().isEmpty())
			return null;

		Matcher matcher = dimensionsPattern.matcher(dimensions);
		if (!matcher.find()) {
			addParsingError(String.format("Could not parse dimensions from string: %s\n", dimensions));
			return null;
			// throw new
			// DocumentParseException("Could not parse dimensions from string: "
			// + dimensions);
		} else {
			return new BigDecimal[] { bd(matcher.group(1)), bd(matcher.group(2)), bd(matcher.group(3)) };
		}
	}

	/**
	 * Sets the destination product to add parsing errrs to
	 * 
	 * @param p
	 */
	protected void setParsingErrorsReceiver(IProduct p) {
		this.parsingErrorsReceiver = p;
	}

	/**
	 * Adds parsing error
	 * 
	 * @param text
	 */
	private void addParsingError(String text) {
		parsingErrorsReceiver.getParsingErrors().append(text);
	}

	private static final Pattern weightPattern0 = Pattern.compile("(\\d+\\.?\\d*) lb");
	private static final Pattern weightPattern1 = Pattern.compile("(\\d+\\.?\\d*) oz");

	/**
	 * Parse a weight string into a decimal representation.
	 */
	protected BigDecimal parseWeightInPounds(String weight) {
		if (weight == null || weight.trim().isEmpty())
			return null;

		Matcher matcher = weightPattern0.matcher(weight);
		BigDecimal r = null;
		if (matcher.find()) {
			r = bd(matcher.group(1));
		}

		if (r == null && ((matcher = weightPattern1.matcher(weight)).find())) {
			r = bd(matcher.group(1)).multiply(bd(0.0625)).setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		if (r == null) {
			addParsingError(String.format("Could not parse weight from string: %s\n", weight));
			return null;
			// throw new
			// DocumentParseException("Could not parse weight from string: " +
			// weight);
		} else {
			return r;
		}
	}

	/**
	 * Parse a rating integer value
	 */
	protected Integer rating(String rating) {
		if (rating == null || rating.trim().isEmpty())
			return null;

		BigDecimal r;
		try {
			r = new BigDecimal(rating);
		} catch (NumberFormatException ex) {
			addParsingError(String.format("Could not parse rating from string: %s\n", rating));
			return null;
		}
		return r.setScale(0, BigDecimal.ROUND_DOWN).intValue();
	}

	private BigDecimal bd(double v) {
		return new BigDecimal(v);
	}

	/**
	 * Convert a numeric string to BigDecimal
	 */
	protected BigDecimal bd(String text) {
		return new BigDecimal(text);
	}

	/**
	 * Convert a numeric string to Integer
	 */
	protected Integer reviews(String text) {
		if (text == null || text.trim().isEmpty())
			return null;

		Integer i;
		try {
			i = Integer.parseInt(text);
		} catch (NumberFormatException ex) {
			addParsingError(String.format("Could not parse reviews count from string: %s\n", text));
			return null;
		}
		return i;
	}

	/**
	 * Convert a numeric string to Integer
	 */
	protected Integer intVal(Elements elements) {
		if (elements == null)
			return null;
		String text = elements.text();

		if (text == null || text.trim().isEmpty())
			return null;

		Integer i;
		try {
			i = Integer.parseInt(text);
		} catch (NumberFormatException ex) {
			return null;
		}
		return i;
	}

	/**
	 * Convert elements content to String
	 */
	protected String textVal(Elements elements) {
		if (elements == null)
			return null;
		String text = elements.text();

		if (text != null && text.isEmpty()) {
			return null;
		}
		return text;
	}

	/**
	 * Convert a currency string into BigDecimal
	 * 
	 * @since 1.0.1 Changed logic to remove $ sign first, and then parse as
	 *        simple decimal number due to broken price parser in 1.0.0
	 * @since 1.0.2 Added , removal
	 */
	protected BigDecimal cur(String text) {
		if (text == null)
			return null;
		// remove $ sign and , delimiter
		text = text.replace("$", "").replace(",", "");
		if (text.trim().isEmpty())
			return null;

		BigDecimal c;
		try {
			c = new BigDecimal(text).setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (NumberFormatException ex) {
			addParsingError(String.format("Could not parse currency from string: %s\n", text));
			return null;
		}

		return c;

	}

	private static final Pattern batteryLifePattern0 = Pattern.compile("[Uu]p to (\\d+) hours and (\\d+) minutes");
	private static final Pattern batteryLifePattern1 = Pattern.compile("[Uu]p to (\\d+) hours");

	/**
	 * Parse battery life in the format specific to this document layout
	 * 
	 * @param batteryLife
	 *            The string with battery life info.
	 * @return The parsed battery life in hours.
	 */
	protected BigDecimal parseBatteryLife(String batteryLife) {
		Double hours;
		Matcher matcher;
		// Try the first pattern

		if (batteryLife == null || batteryLife.isEmpty()) {
			return null;
		}

		if ((matcher = batteryLifePattern0.matcher(batteryLife)).find()) {
			hours = Double.valueOf(matcher.group(1));
			hours += Double.valueOf(matcher.group(2)) / 60.; // add minutes

		} else if ((matcher = batteryLifePattern1.matcher(batteryLife)).find()) {
			hours = Double.valueOf(matcher.group(1));

		} else {

			addParsingError(String.format("Could not parse battery life from string: %s\n", batteryLife));
			return null;
			// throw new
			// DocumentParseException("Could not parse battery life from string:
			// "
			// + batteryLife);
		}

		return new BigDecimal(hours).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * Return the text of a spec table property, specific to this document
	 * layout. This method also populates the #specParsed set for latter
	 * comparison if any spec attribute was ignored.
	 * 
	 * @since 1.0.1 Tries to get the property first, before adding it to
	 *        "parsed" set
	 * @since 1.0.2 Appends configurable delimiter to the list of properties
	 * 
	 * @param prop
	 *            The title of the property.
	 * @param optional
	 *            True if this property is optional and should not issue a
	 *            warning in the logs when missing.
	 * @param listDelimiter
	 *            Delimiter to use for distinguish between list values
	 * 
	 * @return The text found or null if not found.
	 */
	protected String prop(String prop, boolean optional, String listDelimiter) {
		try {
			Elements propElems = propElem(prop);
			StringBuilder propValues = new StringBuilder();
			for (Element oneProp : propElems) {
				if (listDelimiter != null && propValues.length() > 0) {
					propValues.append(listDelimiter);
				}
				propValues.append(oneProp.text());
			}
			String propValue = propValues.toString();
			// only add if there were no errors
			specParsed.add(prop);
			return propValue;
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			if (!optional) {
				logNotFound("prop", prop);
			}
			return null;
		}
	}

	/**
	 * 
	 * Return all texts of a spec table property, specific to this document
	 * layout. This method also populates the #specParsed set for latter
	 * comparison if any spec attribute was ignored.
	 * 
	 * @param prop
	 *            The title of the property.
	 * @param optional
	 *            True if this property is optional and should not issue a
	 *            warning in the logs when missing.
	 * @return The array of texts found or empty array if not found.
	 * @since 1.0.2
	 */
	protected String[] props(String prop, boolean optional) {
		try {
			Elements propElems = propElem(prop);
			List<String> propValues = new ArrayList<String>();
			for (Element oneProp : propElems) {
				propValues.add(oneProp.text());
			}

			// only add if there were no errors
			specParsed.add(prop);

			return propValues.toArray(new String[] {});
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			if (!optional) {
				logNotFound("prop", prop);
			}
			return null;
		}
	}

	/**
	 * Same as #prop(String, DEFAULT_OPTIONAL).
	 *
	 * @see DocumentParser#prop(String, boolean, String)
	 * @param prop
	 *            The title of the property.
	 * @param listDelimiter
	 *            Delimiter to use for distinguish between list values
	 * @return The text found or null if not found.
	 */
	protected String prop(String prop, String listDelimiter) {
		return prop(prop, DEFAULT_OPTIONAL, listDelimiter);
	}

	/**
	 * Same as #props(String, DEFAULT_OPTIONAL).
	 *
	 * @see DocumentParser#props(String, boolean)
	 * @param prop
	 *            The title of the property.
	 * @return The text found or null if not found.
	 * 
	 * @since 1.0.2
	 */
	protected String[] props(String prop) {
		return props(prop, DEFAULT_OPTIONAL);
	}

	protected Elements propElem(String prop) {
		String propQuoted = Pattern.quote(prop);

		Element propElement = q(QueriesSpec.specQueryPrefix + propQuoted + QueriesSpec.specQuerySuffix).parents().get(2);

		Elements res = propElement.select(QueriesSpec.specQueryPostQuery);

		// only add if there were no errors
		specParsed.add(prop);
		return res;
	}

	/**
	 * Same as #prop(String) but returns the "href" attribute of the first found
	 * enclosed link.
	 * 
	 * @param prop
	 *            The title of the property.
	 * @return The href of the found link
	 */
	protected String propLink(String prop) {
		try {

			prop = Pattern.quote(prop);
			String url = q(QueriesSpec.linkQueryPrefix + prop + QueriesSpec.linkQuerySuffix).parents().select(QueriesSpec.linkQueryPostQuery).attr("href");

			if (url.isEmpty()) {
				throw new NullPointerException();
			}

			if (!url.toLowerCase().startsWith("http")) {
				url = "http:" + url;
			}
			// only add if there were no errors
			specParsed.add(prop);
			return url;

		} catch (NullPointerException e) {
			if (!DEFAULT_OPTIONAL)
				logNotFound("prop", prop);
			return null;
		}
	}

	private void logNotFound(String method, String query) {
		log.warn("Query '{}' was not found when executing method {}", query, method);
	}

	/**
	 * Return the text value of the first element matching the query string.
	 * 
	 * @param queryString
	 *            The CSS query string to match.
	 * @return The text found or null if not found.
	 */
	protected String text(String queryString) {
		try {
			return q(queryString).first().ownText();
		} catch (NullPointerException e) {
			logNotFound("text", queryString);
			return null;
		}
	}

	/**
	 * Shortcut for selecting an element
	 * 
	 * @param queryString
	 * @return
	 */
	protected Elements q(String queryString) {
		return doc.select(queryString);
	}

	/**
	 * Retrieves a set of related accessories from the "accessories" section. Up
	 * to ACCESSORIES_COUNT.
	 */
	protected Set<RelatedAccessory> accessories() {
		String nameQuery = QueriesSpec.accessoriesNameQuery; // "#accessories div.items h3";
		String urlQuery = QueriesSpec.accessoriesUrlQuery; // "#accessories div.items .priceHolder div > div > a";

		Set<RelatedAccessory> out = new HashSet<>();

		Elements names = q(nameQuery);
		Elements urls = q(urlQuery);

		for (int i = 0; i < names.size(); i++) {
			if (i > ACCESSORIES_COUNT)
				break;
			RelatedAccessory r = new RelatedAccessory();
			r.setProductNumber(definition.getProductNumber());
			r.setName(names.get(i).text());
			r.setUrl(urls.get(i).attr("href"));
			out.add(r);
		}

		return out;
	}

	/**
	 * Extract product images
	 */
	protected Set<ProductImage> images() {
		Set<ProductImage> images = new HashSet<>();
		// Images
		doc.select(QueriesSpec.imagesSelectQuery).select(QueriesSpec.imagesSelectPostQuery).forEach(img -> {
			ProductImage prodImage = new ProductImage();
			prodImage.setProductNumber(definition.getProductNumber());
			String url = img.attr("src");
			if (!url.startsWith("http")) {
				url = "http:" + url;
			}
			prodImage.setUrl(url);
			images.add(prodImage);
		});
		return images;
	}

	/**
	 * Extract product reviews
	 */
	protected Set<ProductReview> reviews() {
		Set<ProductReview> reviews = new HashSet<>();
		// Reviews
		doc.select(QueriesSpec.reviewsExtractQuery).forEach(review -> {
			ProductReview prodReview = new ProductReview();
			prodReview.setSiteId(1);
			prodReview.setProductNumber(definition.getProductNumber());

			prodReview.setId(reviewId(review));
			prodReview.setReviewDate(reviewPostDate(review));
			prodReview.setRating(intVal(review.select(QueriesSpec.reviewsExtractRatingQuery)));
			prodReview.setScale(intVal(review.select(QueriesSpec.reviewsExtractScaleQuery)));
			prodReview.setTitle(textVal(review.select(QueriesSpec.reviewsExtractTitleQuery)));
			prodReview.setComments(textVal(review.select(QueriesSpec.reviewsExtractComentsQuery)));
			prodReview.setUsername(textVal(review.select(QueriesSpec.reviewsExtractUsernameQuery)));
			prodReview.setLocation(textVal(review.select(QueriesSpec.reviewsExtractLocationQuery)));
			prodReview.setHpResponse(textVal(review.select(QueriesSpec.reviewsExtractHpResponseQuery)));
			prodReview.setHpResponseDate(reviewHpResponseDate(review));
			prodReview.setHpResponseUser(textVal(review.select(QueriesSpec.reviewsExtractHpResponseUserQuery)));

			Elements yesVote = review.select(QueriesSpec.reviewsExtractYesQuery);
			if (yesVote != null && yesVote.size() > 0) {
				yesVote = yesVote.parents();
				if (yesVote != null && yesVote.get(0) != null) {
					yesVote = yesVote.get(0).select(QueriesSpec.reviewsExtractYesPostQuery);
					prodReview.setReviewHelpfulYesCount(intVal(yesVote));
				}
			}
			Elements noVote = review.select(QueriesSpec.reviewsExtractNoQuery);
			if (noVote != null && noVote.size() > 0) {
				noVote = noVote.parents();
				if (noVote != null && noVote.get(0) != null) {
					noVote = noVote.get(0).select(QueriesSpec.reviewsExtractNoPostQuery);
					prodReview.setReviewHelpfulNoCount(intVal(noVote));
				}

			}

			reviews.add(prodReview);
		});
		return reviews;
	}

	private Integer reviewId(Element review) {
		String id = review.attr("id");
		if (id == null || id.isEmpty())
			return null;

		Matcher matcher;

		if ((matcher = QueriesSpec.reviewIdPattern.matcher(id)).find()) {
			return Integer.parseInt(matcher.group(1).toLowerCase());
		}
		return null;
	}

	private Date reviewPostDate(Element review) {
		String dt = textVal(review.select(QueriesSpec.reviewsExtractPostDateQuery));

		if(dt!=null) {
			try {
				return DateUtils.parseDateStrictly(dt, new String[] { QueriesSpec.reviewsExtractDateFormat });
			} catch (ParseException ignored) {
			}
		}
		return null;
	}

	private Date reviewHpResponseDate(Element review) {
		String dt = textVal(review.select(QueriesSpec.reviewsExtractHpResponseDateQuery));

		if(dt!=null) {
			try {
				return DateUtils.parseDateStrictly(dt, new String[] { QueriesSpec.reviewsExtractDateFormat });
			} catch (ParseException ignored) {
			}
		}
		return null;
	}

	/**
	 * Extract processor and graphics fields, accounting for some variations.
	 */
	protected void extractProcessorAndGraphics(ProcessorGraphicsMixin p) {
		List<String> processors = new LinkedList<>();
		List<String> graphics = new LinkedList<>();

		String current = "";
		try {
			Elements elements = propElem("Processor and graphics").select("p");
			for (Element el : elements) {
				current = el == null ? "NULL" : el.text();
				String[] s = current.split("\\+");
				processors.add(s[0].trim());
				graphics.add(s[1].trim());
			}
		} catch (IndexOutOfBoundsException | NullPointerException ignored) {

		}

		if (processors.size() > 0) {
			p.setProcessor(StringUtils.join(processors, ';'));
		} else {
			p.setProcessor(prop("Processor", listDelimiter));
		}

		if (graphics.size() > 0) {
			p.setGraphics(StringUtils.join(graphics, ';'));
		} else {
			p.setGraphics(any(prop("Graphics", listDelimiter), prop("Graphics card", listDelimiter)));
		}
	}

	/**
	 * Iterate sequentially on objects until finding a non-null. If none can be
	 * found, return null.
	 */
	protected <T> T any(T... objects) {
		for (T o : objects) {
			if (o != null)
				return o;
		}
		return null;
	}

	protected String nullIfEmpty(String text) {
		if (text == null || text.trim().isEmpty())
			return null;
		return text;
	}

	protected void checkParsedProps() {
		int countExpected = propertiesThreshold;
		int parsed = specParsed.size();
		if (parsed < countExpected) {
			throw new DocumentParseException(
					String.format("Unable to parse document. Parsed properties count: %d, expected at least: %d",
							parsed, countExpected));
		}

	}
}
