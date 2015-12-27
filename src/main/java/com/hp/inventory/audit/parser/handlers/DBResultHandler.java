/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.handlers;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.inventory.audit.parser.Config;
import com.hp.inventory.audit.parser.ProductIterable;
import com.hp.inventory.audit.parser.Report;
import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.RelatedAccessory;
import com.hp.inventory.audit.parser.parsers.DetectionResult;
import com.hp.inventory.audit.parser.parsers.DocumentParser;

import com.hp.inventory.audit.parser.parsers.IgnoringParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.riversun.finbin.BinarySearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Result handler that writes products to the configured database.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class DBResultHandler implements ResultHandler {

    /**
     * If we should pretty-print the outputted JSON
     */
    private static final boolean PRETTY_PRINT = true;

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Logger log = LoggerFactory.getLogger(DBResultHandler.class);

    private PrintWriter reportOutput;
    private Report report;
    private Config config;

    private Map<String, String> urlProdNumberMap = new HashMap<>();


    private byte[] prodNumberStart = "<span class=\"prodNum\">".getBytes();
    private byte[] prodNumberEnd = "</span>".getBytes();
    private BinarySearcher binarySearcher = new BinarySearcher();

    /**
     * @inheritDoc
     */
    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void beforeStart() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("hp-product-parser");
        report = new Report();
        collectProductNumbers();
    }

    /**
     * Collects URL-to-productNumber associations from both the DB and data files to
     * resolve RelatedAccessory.
     */
    @SuppressWarnings("unchecked")
    private void collectProductNumbers() {
        log.info("Retrieving URL-productNumber map from DB");
        // Get from DB first
        String q = "SELECT productUrl, productNumber from Product";
        List<Object[]> res = getEntityManager().createNativeQuery(q).getResultList();
        for (Object[] r : res) {
            urlProdNumberMap.put(r[0].toString(), r[1].toString());
        }

        // Get from pages
        log.info("Retrieving URL-productNumber map from files.");
        for (Product p : new ProductIterable(config)) {
            try {
                File page = new File(config.dataDirectory, p.getSourceFile());
                String prodNum = extractNumber(page);
                if (prodNum == null) continue;
                urlProdNumberMap.put(p.getProductUrl(), prodNum);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Done retrieving URL-productNumber map from files.");
    }

    /**
     * Extract of product number from a file. It uses binary search and partial file read to make the process very fast.
     *
     * @throws IOException
     */
    private String extractNumber(File page) throws IOException {
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


    /**
     * Associates records within the RelatedAccessory table with their products
     * in Product table.
     */
    private void associateRelatedAccessories(Product product) {
        Set<RelatedAccessory> toRemove = new HashSet<>();
        for (RelatedAccessory ra : product.getAccessories()) {
            if (ra.getAccessoryProductNumber() == null) {
                String url = ra.getUrl();
                String number = urlProdNumberMap.get(url);
                if (number == null) {
                    // When we can't find the product number it means that the URL was never
                    // downloaded and that's most likely a mistake. We'll remove the related
                    // accessory since the schema requires the product number.

                    // An improvement would be to fetch the URL and extract the number but that could impact
                    // processing time - better to check with management first.

                    log.warn("Could not find the product number for accessory with URL {}", url);
                    toRemove.add(ra);
                } else {
                    ra.setProductNumber(number);
                }
            }
        }
        product.getAccessories().removeAll(toRemove);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void extractionFailed(Product definition, Exception e) {
        log.error("Exception occurred while trying to extract product #: {}:", definition.getId(), e);
                report.addError(definition, e.getMessage());
    }

    /**
     * @throws Exception 
     * @inheritDoc
     */
    @Override
    @Transactional
    public synchronized void extractionSucceeded(Product productDefinition, IProduct extractedEntity) throws Exception {

        extractedEntity.populateCommonsToProduct(productDefinition);

        associateRelatedAccessories(productDefinition);

        getEntityManager().clear();

        startTransaction();
        try {
            if(productDefinition.getParsingError()!=null) {
                log.warn("product Id: {}, #: {}, {}",
                        productDefinition.getId(),
                        productDefinition.getProductNumber(),
                        productDefinition.getParsingError());
            }

            upgradeDefinitionIfExisting(productDefinition);

            upgradeProductIfExisting(extractedEntity);

            getEntityManager().flush();

            commitTransaction();

            log.info("Processed product Id: {}, #: {}, Type: {}",
                    productDefinition.getId(),
                    productDefinition.getProductNumber(),
                    extractedEntity.getClass().getSimpleName());
            report.addProductCount(extractedEntity.getClass().getSimpleName(), productDefinition.getProductNumber());
        } catch (Exception e) {
            try { rollbackTransaction(); } catch (Exception ignored) { }
            log.error("Offending product: " + new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create()
                    .toJson(extractedEntity));
            throw e;
        }

        try { rollbackTransaction(); } catch (Exception ignored) { }

    }

    private void upgradeProductIfExisting(IProduct extractedEntity) throws Exception {
        IProduct existingExtracted = getEntityManager().find(extractedEntity.getClass(), extractedEntity.getProductNumber());
        if(existingExtracted!=null) {
            //existing entity
            existingExtracted.upgradeEntityFrom(extractedEntity);

            getEntityManager().merge(existingExtracted);
        } else {
            //new entity
            extractedEntity.initNewEntity();

            getEntityManager().persist(extractedEntity);
        }
    }

    private void upgradeDefinitionIfExisting(Product productDefinition) throws Exception {
        Product existingDefinition = getEntityManager().find(Product.class, productDefinition.getProductNumber());

        Date now = new Date();

        if(existingDefinition!=null) {
            //existing Product
            existingDefinition.upgradeEntityFrom(productDefinition);

            //

            getEntityManager().merge(existingDefinition);

        } else {
            //new Product
            productDefinition.initNewEntity();

            getEntityManager().persist(productDefinition);
        }
    }

    private void rollbackTransaction() {
        getEntityManager().getTransaction().rollback();
    }

    private void commitTransaction() {
        if(getEntityManager().getTransaction().isActive())
            getEntityManager().getTransaction().commit();
    }

    private void startTransaction() {
        if(!getEntityManager().getTransaction().isActive())
            getEntityManager().getTransaction().begin();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void reportResults() {
    	Gson gson;
        GsonBuilder gsonBuilder;
        if (PRETTY_PRINT) {
        	gsonBuilder = new GsonBuilder().setPrettyPrinting();
        } else {
        	gsonBuilder = new GsonBuilder();
        }

        gsonBuilder = gsonBuilder.disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation();
        
        gson = gsonBuilder.create();
        
        String out = gson.toJson(report);
        log.info("Writing report results.");
        reportOutput.print(out);
        reportOutput.flush();
        getEntityManager().close();
    }

    @Override
    public void setReportOutput(PrintWriter printWriter) {
        this.reportOutput = printWriter;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addNonParsedSpecItems(Product definition, DocumentParser documentParser, Set<String> attributes) {
        attributes.forEach(
                attr -> {
                    report.addNonParsedSpecAttribute(documentParser.getClass().getSimpleName(), attr, definition.getId());
                }
        );
    }

    /**
     * Gets the currently open EntityManager. Creates a new one if none is available.
     */
    public EntityManager getEntityManager() {
        if (this.entityManager == null) {
            this.entityManager = this.getEntityManagerFactory().createEntityManager();
        }

        return this.entityManager;
    }

    /**
     * Gets the EntityManagerFactory for this job.
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * Sets the EntityManagerFactory for this job.
     * @param entityManagerFactory
     */
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void detectionSucceeded(DetectionResult detectionResult, Product definition, IProduct extracted) {
        if (extracted == null) {
            report.addParserNotFound(definition);

        } else if (detectionResult.parser instanceof IgnoringParser) {
            report.addIgnored(definition);
        } else {
            report.addHit(detectionResult.ruleHit);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void detectionFailed(Product definition, Exception e) {
        log.error("Error on parser detection", e);
        report.addDetectFailed(definition);
    }

}
