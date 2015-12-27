/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.*;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.inventory.audit.parser.model.Product;

/**
 * Main class for running HP Product page parsing jobs.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class ExtractionJobRunner {


    Logger log = LoggerFactory.getLogger(ExtractionJobRunner.class);

    private Config config;

    private int doneCount;
    private int futureCount;
    private long startTime;
    private PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix("h")
            .appendMinutes()
            .appendSuffix("m")
            .minimumPrintedDigits(2)
            .printZeroAlways()
            .appendSeconds()
            .appendSuffix("s")
            .toFormatter();

    /**
     * Iterate the source file, extract product information and adds to the database.
     */
    public void start() {
        log.info("Starting parsing job.");

        log.info("Maximum of jobs: {}", config.maxJobs);
        ExecutorService executorService = Executors.newFixedThreadPool(config.maxJobs);

        config.resultHandler.beforeStart();

        startTime = System.currentTimeMillis();
        Iterable<Product> products = new ProductIterable(config);
        for (Product prod : products) {
            if (config.singleProductId != -1 && prod.getId() != config.singleProductId) {
                continue;
            }

            log.debug("Adding product Id:{} to parsing queue", prod.getId());
            ProductExtractorJob extractor = new ProductExtractorJob(config, prod);
            CompletableFuture future = CompletableFuture.runAsync(extractor, executorService);
            //noinspection unchecked
            future
                    .thenRun(ExtractionJobRunner.this::tick)
                    .exceptionally((e) -> {
                        log.error("Unexpected exception when running the extraction job", e);
                        return null;
                    });
            futureCount++;
        }

        // Wait for all the futures to return
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {

        }

        config.resultHandler.afterFinish();

        config.resultHandler.reportResults();
    }

    private void tick() {
        doneCount++;

        if (futureCount == 0) return;
        if (doneCount % 10 != 0) return;

        double progressPercent = ((double) doneCount / (double)futureCount) * 100.0;

        long ctime = System.currentTimeMillis();
        long elapsed = (ctime - startTime);

        double speed = (double) doneCount / (double)elapsed;

        long eta = (int)Math.round((futureCount- doneCount) / speed);
        String etaString = periodFormatter.print(new Period(eta));

        log.info(String.format("PROGRESS: %.2f%% [%s/%s]. %.2f per sec. ETA: %s",
                progressPercent, doneCount, futureCount, speed * 1000d, etaString));
    }

    private Iterable<Product> parseProducts() {
        return new ProductIterable(config);
    }

    /**
     * Set the application configuration
     */
    public void setConfig(Config config) {
        this.config = config;
    }
}
