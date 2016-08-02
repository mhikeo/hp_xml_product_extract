/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import com.google.gson.annotations.Since;
import com.hp.inventory.audit.parser.handlers.DBResultHandler;
import com.hp.inventory.audit.parser.handlers.ResultHandler;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Holder for configuration properties.
 *
 * <p>
 *   Changes in 1.1:
 *  1. Add the mainSite config.
 *  2. add/change the productNumber/productId mappings.
 * </p>
 * @author TCDEVELOPER
 * @version 1.1
 */
public class Config {

    /**
     * The data directory when using the parser that feed from a local directory
     */
    public File dataDirectory;

    /**
     * Max number of simultaneous jobs running
     */
    public int maxJobs = Runtime.getRuntime().availableProcessors();

    /**
     * The ResultHandler instance to process the extracted data
     */
    public ResultHandler resultHandler;

    /**
     * Product ID when running on single-product mode
     */
    public int singleProductId = -1;

    /**
     * The default currency to use when parsing products
     */
    public String defaultCurrency;

    public String listDelimiter;

    /**
     * The number of required properties to accept the page as a product description
     */
    public int propertiesThreshold = 3;

    /**
     * Parser-specific rules
     */
    public File rulesConfig;

    /**
     * The source site ID. This value match one of the sites in the "Sites" table. Default to 1, whatever
     * that site might be.
     */
    public Integer siteId = 1;

    /**
     * Sets if this is the main site or not.
     */
    @Since(1.1)
    public boolean isMainSite = true;

    /**
     * Mapping between URL and product id. Build dynamically from pages and, maybe, from DB data.
     */
    public Map<String, String> urlProdIdMap = new HashMap<>();

    /**
     * If the job runner should parse more than one page referring to the same product. This is increases
     * parsing time and is generally not recommended.
     */
    public boolean parseDuplicates = false;

    /**
     * The set contains all the product ids.
     */
    public Set<String> prodIds = new HashSet<>();

    /**
     * Constructs the product id from the product number.
     * @param productNumber the product number.
     * @return the product id.
     */
    public String constructProductId(String productNumber) {
          return siteId + " - " + productNumber;
      }

    /**
     * Parses the product number form product id.
     * @param productId the product id.
     * @return the product number.
     */
     public String parseProductNumber(String productId) {
          return productId.split(" - ")[1];
     }
}
