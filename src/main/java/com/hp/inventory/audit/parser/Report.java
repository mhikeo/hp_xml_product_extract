/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import com.google.gson.annotations.Expose;
import com.hp.inventory.audit.parser.model.Product;

import java.util.*;

/**
 * Report object holding extraction results.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class Report {

    @Expose
    private final List<Error> errors = new LinkedList<>();

    @Expose
    private final Map<String, Integer> productCount = new TreeMap<>();

    @Expose
    private final Map<String, Integer> productDupes = new TreeMap<>();

    @Expose
    private final Map<String, Integer> rulesHit = new TreeMap<>();

    private final Map<String, Set<String>> productIDs = new TreeMap<>();

    @Expose
    private final Map<String, List<Integer>> nonParsedSpecs = new TreeMap<>();

    @Expose
    private final List<Integer> parserNotFoundProducts = new LinkedList<>();

    @Expose
    private final Map<Integer, String> nonDetectedProductTypes = new TreeMap<>();

    @Expose
    private final Map<Integer, String> ignoredProducts = new TreeMap<>();

    /**
     * Add an error thrown when extracting a product
     * @param definition
     */
    public synchronized void addError(Product definition, String message) {
        Error error = new Error(definition, message);
        errors.add(error);
    }

    /**
     * Add a product count, evaluated when finished the job.
     * @param typeName A string to identify the product type.
     */
    public synchronized void addProductCount(String typeName, String productID) {
        Set<String> ids = productIDs.getOrDefault(typeName, new HashSet<String>());
        if(ids.contains(productID)) {
        	Integer cc = productDupes.getOrDefault(typeName, 0);
        	cc += 1;
        	productDupes.put(typeName, cc);
        }
        ids.add(productID);
        
        Integer cnt = ids.size();
        
        productIDs.put(typeName, ids);
        productCount.put(typeName, cnt);
    }

    /**
     * Notify that a spec attribute was detected but not parsed. To not clobber up the report output,
     * only the first 3 products are displayed.
     * @param parserName The name of the parser that detected the product.
     * @param attribute The name of the attribute ignored.
     * @param productId The id of the product where the issue was found.
     */
    public synchronized void addNonParsedSpecAttribute(String parserName, String attribute, Integer productId) {
        String key = parserName + "::" + attribute;
        List<Integer> ids = nonParsedSpecs.getOrDefault(key, new ArrayList<>());
        if (ids.size() < 3) {
            ids.add(productId);
        }
        nonParsedSpecs.put(key, ids);
    }

    /**
     * Notify that no parser could be found for the given product
     * @param definition The product definition.
     */
    public void addParserNotFound(Product definition) {
        parserNotFoundProducts.add(definition.getId());
    }

    protected List<Error> getErrors() {
        return errors;
    }

    protected Map<String, List<Integer>> getNonParsedSpecs() {
        return nonParsedSpecs;
    }

    protected Map<String, Set<String>> getProductIDs() {
        return productIDs;
    }

    public List<Integer> getParserNotFoundProducts() {
        return parserNotFoundProducts;
    }

    protected static class Error {

    	@Expose
        private final Product productDefinition;
        
    	@Expose
    	private final String message;

        public Error(Product definition, String message) {
            this.productDefinition = definition;
            this.message = message;
        }

        public Product getProductDefinition() {
            return productDefinition;
        }

        public String getMessage() {
            return message;
        }
    }

	public void addDetectFailed(Product definition) {
		nonDetectedProductTypes.put(definition.getId(), definition.getProductUrl());
	}

	public void addIgnored(Product definition) {
		ignoredProducts.put(definition.getId(), definition.getProductUrl());
	}

	public void addHit(String ruleHit) {
		Integer cc = rulesHit.getOrDefault(ruleHit, 0);
    	cc += 1;
    	rulesHit.put(ruleHit, cc);
	}
}
