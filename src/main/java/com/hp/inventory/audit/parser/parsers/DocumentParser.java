/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.google.gson.Gson;
import com.hp.inventory.audit.parser.Config;
import com.hp.inventory.audit.parser.RulesConfig;
import com.hp.inventory.audit.parser.handlers.ResultHandler;
import com.hp.inventory.audit.parser.model.*;

import com.hp.inventory.audit.parser.parsers.extractors.FullTextExtractor;
import com.hp.inventory.audit.parser.parsers.extractors.ReviewsExtractor;
import com.hp.inventory.audit.parser.parsers.rules.QueriesSpec;
import com.hp.inventory.audit.parser.parsers.rules.TypeSpec;
import com.hp.inventory.audit.parser.utils.LangTranslator;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract document parser with common utilities.
 *
 * changes:
 *  - 1.0.3: refactor the columns to specifications.
 *  - 1.0.4: support productId; support EUR/GBP currency; use a general way to extract specs.
 * @author TCDEVELOPER
 * @version 1.0.4
 */
public abstract class DocumentParser {

    public static void init(Reader rulesConfig) throws IOException, ClassNotFoundException {
        RulesConfig rulesCfg;
        try (BufferedReader reader = new BufferedReader(rulesConfig)) {
            rulesCfg = (new Gson()).fromJson(reader, RulesConfig.class);
        }

        QueriesSpec.init(rulesCfg);
        TypeSpec.init(rulesCfg);
    }

    public static void init(File rulesConfig) throws IOException, ClassNotFoundException {
        init(new FileReader(rulesConfig));
    }

    private AbstractProduct parsingErrorsReceiver = null;


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

    private Config config;

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
    protected void extractCommonProps(Product product) throws Exception {

        product.setProductNumber(text(QueriesSpec.productNumberQuery));
        product.setSiteId(config.siteId);
        product.setProductId(config.constructProductId(product.getProductNumber()));

        if (product.getProductType() == null) {
            product.setProductType(product.getCategory());
        }

        product.setItemNumber(product.getProductNumber().split("#|_")[0]);

        listDelimiter = definition.getListDelimiter();
        propertiesThreshold = definition.getPropertiesThreshold();

        product.setParseDate(new Date());

        product.setProductName(text(QueriesSpec.productNameQuery));


        // Product ratings
        ProductRating rating = new ProductRating();
        rating.setProductId(definition.getProductId());
        rating.setSiteId(config.siteId);
        rating.setProductNumber(definition.getProductNumber());
        Elements ratingElements = q(QueriesSpec.ratingQuery);
        if (ratingElements.size() == 0) {
            rating.setRating(0);
        } else {
            rating.setRating(rating(q(QueriesSpec.ratingQuery).last().text()));
        }
        rating.setNumberOfReviews(reviewsCount(q(QueriesSpec.reviewsQuery).text()));
        if (rating.getRating() != null || rating.getNumberOfReviews() != null)
            definition.getRatings().put(rating.getSiteId(), rating);


        // Product prices
        ProductPrice price = new ProductPrice();
        if (config.defaultCurrency == null) {
            // only if default currency hasn't been set yet
            price.setCurrency(nullIfEmpty(q(QueriesSpec.currencyQuery).attr("content")));
        } else {
            // otherwise set it to default
            price.setCurrency(config.defaultCurrency);
        }

        price.setProductId(definition.getProductId());
        price.setProductNumber(definition.getProductNumber());
        price.setSiteId(config.siteId);
        price.setStrikedPrice(cur(q(QueriesSpec.strikedPriceQuery).text(), price.getCurrency()));

        String currentPriceVisible = q(QueriesSpec.currentPriceQueryVisible).text();
        String currentPrice = text(QueriesSpec.currentPriceQuery);

        // First try to parse price from { id="price_value" } tag
        if (currentPriceVisible != null && !currentPriceVisible.isEmpty()) {
            price.setCurrentPrice(cur(currentPriceVisible, price.getCurrency()));
        }
        // If we were unable to parce the price, then try to parse it from {
        // itemprop="price" }
        if (price.getCurrentPrice() == null) {
            price.setCurrentPrice(cur(currentPrice, price.getCurrency()));
        }

        if (price.getCurrentPrice() != null)
            definition.getPrices().put(price.getSiteId(), price);

        // set comingSoonDate
        Elements comingSoon = q(QueriesSpec.comingSoonQuery);
        if (comingSoon.size() > 0) {
            product.setComingSoonDate(new Date());
        }

        definition.setImages(images());

        if (config.resultHandler.shouldExtractAccessories())
            definition.setAccessories(accessories());

        definition.setReviews(new ReviewsExtractor(this).extract());

        // add all the specs
        Map<String, String> allSpecs = allSpecs();
        Set<ProductSpecification> specifications = new HashSet<>();
        int displayOrder = 0;
        for (String key : allSpecs.keySet()) {
            String value = allSpecs.get(key);
            ProductSpecification spec = constructSpecification(definition, key, value);
            spec.setDisplayOrder(displayOrder++);
            specifications.add(spec);
            specParsed.add(key);
        }
        definition.setSpecifications(specifications);

    }

    /**
     * Method to extract an AbstractProduct from a Jsoup document node.
     *
     * @throws Exception
     */
    public AbstractProduct parse(Document doc, Product definition, Config config) throws Exception {
        this.doc = doc;
        this.definition = definition;
        this.config = config;
        this.resultHandler = config.resultHandler;

        this.definition.setFullText(new FullTextExtractor(this).extract());

        AbstractProduct result = extract();
        checkForNonParsedSpecItems(definition, resultHandler);
        return result;
    }



    /**
     * Implements the specific document extraction logic. The
     * DocumentParser protected fields "definition", "doc", "resultHandler" and "config"
     * expose respectively current product definition, document, result
     * handler and app configuration.
     * <p>
     * The product number should be the first field to be extracted and the
     * implementation must call "definition.setProductNumber" as soon as
     * possible. The implementation should also bind the returned AbstractProduct and the
     * definition by calling AbstractProduct.setProduct early.
     *
     * @return A concrete AbstractProduct resulting form extraction.
     */
    protected abstract AbstractProduct extract() throws Exception;

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
     * @param dimensions The dimensions string
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
            return new BigDecimal[]{bd(matcher.group(1)), bd(matcher.group(2)), bd(matcher.group(3))};
        }
    }

    /**
     * Sets the destination product to add parsing errrs to
     *
     * @param p
     */
    protected void setParsingErrorsReceiver(AbstractProduct p) {
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

    private static final Pattern weightPattern0 = Pattern.compile("(\\d+\\.?\\d*) lb", Pattern.CASE_INSENSITIVE);
    private static final Pattern weightPattern1 = Pattern.compile("(\\d+\\.?\\d*) oz", Pattern.CASE_INSENSITIVE);
    private static final Pattern weightPattern2 = Pattern.compile("(\\d+\\.?\\d*) kg", Pattern.CASE_INSENSITIVE);
    private static final Pattern weightPattern3 = Pattern.compile("(\\d+\\.?\\d*) g", Pattern.CASE_INSENSITIVE);

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

        if (r == null && ((matcher = weightPattern2.matcher(weight)).find())) {
            r = bd(matcher.group(1)).multiply(bd(2.20462)).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        if (r == null && ((matcher = weightPattern3.matcher(weight)).find())) {
            r = bd(matcher.group(1)).multiply(bd(2.20462 * 0.001)).setScale(2, BigDecimal.ROUND_HALF_UP);
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
    protected Integer reviewsCount(String text) {
        if (text == null || text.trim().isEmpty())
            return null;
        text = text.replaceAll("[^\\d]", "");
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
     * Convert a currency string into BigDecimal
     *
     * @since 1.0.2 Added , removal
     */
    protected BigDecimal cur(String text, String currency) {
        if (text == null)
            return null;
        // remove $ sign and , delimiter and space
        if (currency != null && currency.equals("EUR")) {
            // in EUR, funny is that, they use "," as "." and use "." as ",".
            String tmp = ",,,";
            text = text.replace(",", tmp);
            text = text.replace(".", ",");
            text = text.replace(tmp, ".");
        }
        text = text.replace("$", "").replace(",", "").replace(" ", "");
        text = text.replace("£", "");
        text = text.replace("€", "");

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
     * @param batteryLife The string with battery life info.
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
     * @param prop          The title of the property.
     * @param optional      True if this property is optional and should not issue a
     *                      warning in the logs when missing.
     * @param listDelimiter Delimiter to use for distinguish between list values
     * @return The text found or null if not found.
     * @since 1.0.2 Appends configurable delimiter to the list of properties
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
     * Return all texts of a spec table property, specific to this document
     * layout. This method also populates the #specParsed set for latter
     * comparison if any spec attribute was ignored.
     *
     * @param prop     The title of the property.
     * @param optional True if this property is optional and should not issue a
     *                 warning in the logs when missing.
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

            return propValues.toArray(new String[]{});
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
     * @param prop          The title of the property.
     * @param listDelimiter Delimiter to use for distinguish between list values
     * @return The text found or null if not found.
     * @see DocumentParser#prop(String, boolean, String)
     */
    protected String prop(String prop, String listDelimiter) {
        return prop(prop, DEFAULT_OPTIONAL, listDelimiter);
    }

    /**
     * Same as #prop(String, DEFAULT_OPTIONAL, DocumentParser#listDelimiter).
     *
     * @param prop          The title of the property.
     * @return The text found or null if not found.
     * @see DocumentParser#prop(String, boolean, String)
     */
    protected String prop(String prop) {
        return prop(prop, DEFAULT_OPTIONAL, listDelimiter);
    }

    /**
     * Same as #props(String, DEFAULT_OPTIONAL).
     *
     * @param prop The title of the property.
     * @return The text found or null if not found.
     * @see DocumentParser#props(String, boolean)
     * @since 1.0.2
     */
    protected String[] props(String prop) {
        return props(prop, DEFAULT_OPTIONAL);
    }

    protected Elements propElem(String prop) {
        String propQuoted = Pattern.quote(prop);

        Element propElement = q(QueriesSpec.specQueryPrefix + propQuoted + QueriesSpec.specQuerySuffix).first();

        Elements res = propElement.select(QueriesSpec.specQueryPostQuery);


        // only add if there were no errors
        specParsed.add(prop);
        return res;
    }

  /**
   * Extract all the specs.
   * @return the specs keys and values.
   */
  protected Map<String, String> allSpecs() {
        // keep the order
        Map<String, String> result = new LinkedHashMap<>();
        Elements res = q(QueriesSpec.allSpecQuery);
        for (Element ele : res) {
            String key = ele.select(QueriesSpec.allSpecKeyQuery).text();
            String value = ele.select(QueriesSpec.allSpecValueQuery).text();
            result.put(key, value);
        }
        return result;
    }

    /**
     * Same as #prop(String) but returns the "href" attribute of the first found
     * enclosed link.
     *
     * @param prop The title of the property.
     * @return The href of the found link
     */
    protected String propLink(String prop) {
        try {

            prop = Pattern.quote(prop);
            String url = q(QueriesSpec.linkQueryPrefix + prop + QueriesSpec.linkQuerySuffix).parents()
                    .select(QueriesSpec.linkQueryPostQuery).first().attr("href");

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
     * @param queryString The CSS query string to match.
     * @return The text found or null if not found.
     */
    protected String text(String queryString) {
        try {
            Element e = q(queryString).first();
            String text = e.ownText();
            if (text!= null && text.trim().length() > 0) {
                return text;
            }
            return e.attr("content");
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
     * Retrieves a set of related accessories from the "accessories" section.
     */
    protected Set<RelatedAccessory> accessories() {
        String nameQuery = QueriesSpec.accessoriesNameQuery; // "#accessories div.items h3";
        String urlQuery = QueriesSpec.accessoriesUrlQuery; // "#accessories div.items .priceHolder div > div > a";
        String productNumberQuery = QueriesSpec.accessoriesProductNumberQuery;
        Set<RelatedAccessory> out = new HashSet<>();

        Elements names = q(nameQuery);
        Elements urls = null;
        if (urlQuery != null) {
            urls = q(urlQuery);
        }
        Elements productNumbers = null;
        if (productNumberQuery != null) {
            productNumbers = q(productNumberQuery);
        }

        for (int i = 0; i < names.size(); i++) {
            boolean ignore = false;
            RelatedAccessory r = new RelatedAccessory();
            r.setProductId(definition.getProductId());
            r.setProductNumber(definition.getProductNumber());
            if (urls != null) {
                r.setUrl(urls.get(i).absUrl("href"));
            }
            if (productNumbers != null) {
                String productNumber = productNumbers.get(i).text();
                // check if that product number/id already crawled
                String productId = config.constructProductId(productNumber);
                if (this.config.prodIds.contains(productId)) {
                    r.setAccessoryProductId(productId);
                    r.setAccessoryProductNumber(productNumber);
                } else {
                    ignore = true;
                }
            }
            if (!ignore) {
                out.add(r);
            }
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
            prodImage.setProductId(definition.getProductId());
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
     * Extract processor and graphics fields, accounting for some variations.
     */
    protected void extractProcessorAndGraphics(Product p, Set<ProductSpecification> specifications) {
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
            specifications.add(constructSpecification(p, "processor", StringUtils.join(processors, ";")));
        } else {
            specifications.add(constructSpecification(p, "processor", prop("Processor", listDelimiter)));
        }

        if (graphics.size() > 0) {
            specifications.add(constructSpecification(p, "graphics", StringUtils.join(processors, ";")));
        } else {
            specifications.add(constructSpecification(p, "graphics",
                    any(prop("Graphics", listDelimiter), prop("Graphics card", listDelimiter))));
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

    protected ProductSpecification constructSpecification(Product product, String name, String value) {
        ProductSpecification specification = new ProductSpecification();
        specification.setProductId(product.getProductId());
        specification.setProductNumber(product.getProductNumber());
        specification.setName(name);
        specification.setValue(value);
        return specification;
    }

    public Document getDoc() {
        return doc;
    }

    public Product getDefinition() {
        return definition;
    }

  /**
   * Gets the locale string.
   * @param str the original string.
   * @return the locale string.
   */
    protected String getLocaleString(String str) {
        LangTranslator translator = LangTranslator.getInstance();
        return translator.getString(str);
    }
}
