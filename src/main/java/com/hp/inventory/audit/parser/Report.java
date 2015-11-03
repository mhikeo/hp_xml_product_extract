package com.hp.inventory.audit.parser;

import com.hp.inventory.audit.parser.model.Product;

import java.util.*;

/**
 * Report object holding extraction results.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class Report {

    private final List<Error> errors = new LinkedList<>();

    private final Map<String, Integer> productCount = new TreeMap<>();

    private final Map<String, List<Integer>> nonParsedSpecs = new TreeMap<>();

    private final List<Integer> parserNotFoundProducts = new LinkedList<>();

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
    public synchronized void addProductCount(String typeName) {
        Integer c = productCount.getOrDefault(typeName, 0);
        c += 1;
        productCount.put(typeName, c);
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

    protected Map<String, Integer> getProductCount() {
        return productCount;
    }

    public List<Integer> getParserNotFoundProducts() {
        return parserNotFoundProducts;
    }

    protected static class Error {

        private final Product productDefinition;
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

}
