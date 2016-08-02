/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import com.hp.inventory.audit.parser.utils.LangTranslator;
import com.hp.inventory.audit.parser.utils.ProgressLogger;
import com.hp.inventory.audit.parser.utils.URLNumberMapper;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.inventory.audit.parser.model.Product;

/**
 * Main class for running HP Product page parsing jobs.
 *
 * <p>
 *   Changes in 1.0.6:
 *   add productId support.
 * </p>
 * @author TCDEVELOPER
 * @version 1.0.6
 */
public class ExtractionJobRunner {


    Logger log = LoggerFactory.getLogger(ExtractionJobRunner.class);

    private Config config;
    private Set<String> parsedProducts = new HashSet<>();

    /**
     * Iterate the source file, extract product information and adds to the database.
     */
    public void start() {
        log.info("Starting parsing job.");

        log.info("Maximum of jobs: {}", config.maxJobs);
        ExecutorService executorService = Executors.newFixedThreadPool(config.maxJobs);

        config.resultHandler.beforeStart();

        ProductIterable products = new ProductIterable(config);
        new URLNumberMapper().buildMap(config, products);
        LangTranslator.getInstance().init(config);

        ProgressLogger progressLogger = new ProgressLogger(log, 0);
        int effectiveTotal = 0;
        for (Product prod : products) {

            // Check if we should avoid duplicates
            if (config.singleProductId == -1 && !config.parseDuplicates) {
                prod.setProductId(config.urlProdIdMap.get(prod.getProductUrl()));
                if (prod.getProductId() == null)
                    continue;

                boolean isNew = parsedProducts.add(prod.getProductId());
                if (!isNew)  {
                    log.debug("Duplicate product ignored: {}", prod.getProductId());
                    continue;
                }
            }

            // Check if in single product mode
            if (config.singleProductId != -1 && prod.getId() != config.singleProductId) {
                continue;
            }
            effectiveTotal++;

            log.debug("Adding Id:{} to parsing queue", prod.getId());
            ProductExtractorJob extractor = new ProductExtractorJob(config, prod);
            CompletableFuture future = CompletableFuture.runAsync(extractor, executorService);
            //noinspection unchecked
            future
                    .thenRun(progressLogger::tick)
                    .exceptionally((e) -> {
                        log.error("Unexpected exception when running the extraction job", e);
                        return null;
                    });
        }

        progressLogger.setTotal(effectiveTotal);

        // Wait for all the futures to return
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            progressLogger.finish();
        } catch (InterruptedException ignored) {

        }

        config.resultHandler.afterFinish();

        config.resultHandler.reportResults();
    }




    /**
     * Set the application configuration
     */
    public void setConfig(Config config) {
        this.config = config;
    }
}
