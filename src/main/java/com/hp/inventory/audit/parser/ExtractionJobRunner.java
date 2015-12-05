/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.inventory.audit.parser.handlers.ResultHandler;
import com.hp.inventory.audit.parser.model.Product;

/**
 * Main class for running HP Product page parsing jobs.
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class ExtractionJobRunner {


    /**
     * The location of the source CSV file within data directory
     */
    private static final String SOURCE_CSV = "source.csv";

    Logger log = LoggerFactory.getLogger(ExtractionJobRunner.class);

    private Config config;

    /**
     * Iterate the source file, extract product information and adds to the database.
     */
    public void start() {
        log.info("Starting parsing job.");

        log.info("Maximum of jobs: {}", config.maxJobs);
        ExecutorService executorService = Executors.newFixedThreadPool(config.maxJobs);

        List<Future> futures = new LinkedList<>();

        config.resultHandler.beforeStart();

        Iterable<Product> products;
        if (config.singleProductId == -1) {
            products = parseProducts();
        } else {
            ArrayList<Product> arr = new ArrayList<>();
            Product p = getProduct();
            if (p != null) arr.add(p);
            products = arr;
        }

        for (Product prod : products) {
            log.debug("Adding product Id:{} to parsing queue", prod.getId());
            ProductExtractorJob extractor  = new ProductExtractorJob(config, prod);
            futures.add(executorService.submit(extractor));
        }

        // Wait for all the futures to return
        for(Future f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();

        config.resultHandler.reportResults();
    }

    private Product getProduct() {
        for (Product prod : parseProducts()) {
            if (prod.getId() == config.singleProductId) {
                return prod;
            }
        }
        return null;
    }


    private Iterable<Product> parseProducts() {
        return new ProductIterable(new File(config.dataDirectory, SOURCE_CSV), config);
    }

    /**
     * Set the application configuration
     */
    public void setConfig(Config config) {
        this.config = config;
    }
}
