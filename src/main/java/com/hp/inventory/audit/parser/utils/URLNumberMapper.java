/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.utils;

import com.google.gson.Gson;
import com.hp.inventory.audit.parser.Config;
import com.hp.inventory.audit.parser.ProductIterable;
import com.hp.inventory.audit.parser.RulesConfig;
import com.hp.inventory.audit.parser.handlers.DBResultHandler;
import com.hp.inventory.audit.parser.model.Product;
import org.riversun.finbin.BinarySearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Utility class to extract product number from pages and map to URLs. Hopefully it's more efficient than
 * naive processing.
 * changes in 1.0.6: support Germany/UK sites.
 * @author TCDEVELOPER
 * @version 1.0.6
 */
public class URLNumberMapper {


    private byte[] prodNumberStart = "<span class=\"prodNum\">".getBytes();
    private byte[] prodNumberEnd = "</span>".getBytes();
    private BinarySearcher binarySearcher = new BinarySearcher();

    Logger log = LoggerFactory.getLogger(URLNumberMapper.class);

    /**
     * Collects URL-to-productNumber associations from both the DB and data files to
     * resolve RelatedAccessory.
     */
    @SuppressWarnings("unchecked")
    public void buildMap(Config config, ProductIterable products) {
        RulesConfig rulesCfg;

        try (BufferedReader reader = new BufferedReader(new FileReader(config.rulesConfig))) {
            rulesCfg = (new Gson()).fromJson(reader, RulesConfig.class);
            if (rulesCfg.queriesSpec.get("fastProductNumberQueryPrefix") != null) {
                prodNumberStart = rulesCfg.queriesSpec.get("fastProductNumberQueryPrefix").getBytes();
            }
            if (rulesCfg.queriesSpec.get("fastProductNumberQuerySuffix") != null) {
                prodNumberEnd = rulesCfg.queriesSpec.get("fastProductNumberQuerySuffix").getBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (config.resultHandler instanceof DBResultHandler) {
            log.info("Retrieving URL-productNumber map from DB");
            // Get from DB first
            String q = "SELECT productUrl, productId from Product";
            List<Object[]> res = ((DBResultHandler)config.resultHandler).getEntityManager()
                    .createNativeQuery(q).getResultList();
            for (Object[] r : res) {
                config.urlProdIdMap.put(r[0].toString(), r[1].toString());
            }
        }

        // Get from pages
        log.info("Retrieving URL-productNumber map from files.");
        ProgressLogger progressLogger = new ProgressLogger(log, products.size());
        for (Product p : products) {
            try {
                File page = new File(config.dataDirectory, p.getSourceFile());
                String prodNum = extractNumber(page);
                if (prodNum == null) {
                    log.warn("Could not extract product id from {}: {}", page, p.getProductUrl());
                }
                config.urlProdIdMap.put(p.getProductUrl(), config.constructProductId(prodNum));
                progressLogger.tick();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        config.prodIds.addAll(config.urlProdIdMap.values());
        progressLogger.finish();

        log.info("Done retrieving URL-productNumber map from files.");
    }

    /**
     * Extract of product number from a file. It uses binary search and partial file read to make the process very fast.
     *
     * @throws IOException
     */
    public String extractNumber(File page) throws IOException {
        FileInputStream f = new FileInputStream(page);

        // Those numbers were obtained via testing and should
        // extract most files in a single pass while reading a small
        // amount of data;
        byte[] data = new byte[65000];
        f.skip(75000);

        f.read(data);
        int start = binarySearcher.indexOf(data, prodNumberStart);
        if (start == -1) {
            // fall back to full file read
            f.close();
            f = new FileInputStream(page);
            data = new byte[(int)page.length()];
            f.read(data);
            start = binarySearcher.indexOf(data, prodNumberStart);
        }

        if (start == -1) {
            f.close();
            return null;
        }

        start += prodNumberStart.length;
        int end = binarySearcher.indexOf(data, prodNumberEnd, start);
        byte[] numberBytes = new byte[end - start];
        System.arraycopy(data, start, numberBytes, 0, numberBytes.length);

        String prodNum = new String(numberBytes);
        f.close();
        return prodNum;
    }
}
