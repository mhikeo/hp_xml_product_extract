/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import com.hp.inventory.audit.parser.handlers.DBResultHandler;
import com.hp.inventory.audit.parser.handlers.ResultHandler;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Holder for configuration properties.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
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
     * Mapping between URL and product number. Build dynamically from pages and, maybe, from DB data.
     */
    public Map<String, String> urlProdNumberMap = new HashMap<>();

    /**
     * If the job runner should parse more than one page referring to the same product. This is increases
     * parsing time and is generally not recommended.
     */
    public boolean parseDuplicates = false;
}
