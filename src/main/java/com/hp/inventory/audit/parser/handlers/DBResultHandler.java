/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.inventory.audit.parser.Config;
import com.hp.inventory.audit.parser.Report;
import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.RelatedAccessory;
import com.hp.inventory.audit.parser.parsers.DetectionResult;
import com.hp.inventory.audit.parser.parsers.DocumentParser;
import com.hp.inventory.audit.parser.parsers.IgnoringParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Result handler that writes products to the configured database.
 *
 * changes: remove useless update entity
 * @author TCDEVELOPER
 * @version 1.0.6
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

    ObjectMapper jsonMapper = new ObjectMapper();


    private Set<String> unknownAccessoryURLs = new HashSet<>();

    /**
     * @inheritDoc
     */
    @Override
    public void unknownAccessory(RelatedAccessory ra) {
        boolean isNew = unknownAccessoryURLs.add(ra.getUrl());
        if (isNew)
            log.warn("Could not find the product number for accessory with URL {}", ra.getUrl());
    }

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
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        report = new Report();
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
    public synchronized void extractionSucceeded(Product productDefinition, AbstractProduct extractedEntity) throws Exception {
        extractedEntity.populateCommonsToProduct(productDefinition);

        getEntityManager().clear();

        startTransaction();
        try {
            if(productDefinition.getParsingError()!=null) {
                log.warn("product Id: {}, #: {}, {}",
                        productDefinition.getId(),
                        productDefinition.getProductNumber(),
                        productDefinition.getParsingError());
            }

            productDefinition = upgradeDefinitionIfExisting(productDefinition);

            getEntityManager().flush();

            commitTransaction();

            log.info("Processed product {}: SKU: {}, Class: {}, ProdType: {}",
                    productDefinition.getId(),
                    productDefinition.getProductNumber(),
                    extractedEntity.getClass().getSimpleName(),
                    productDefinition.getProductType());
            report.addProductCount(extractedEntity.getClass().getSimpleName(), productDefinition.getProductNumber());
        } catch (Exception e) {
            try { rollbackTransaction(); } catch (Exception ignored) { }
            String json = jsonMapper.writeValueAsString(extractedEntity);
            log.error("Offending product: {}", json);
            throw e;
        }

        try { rollbackTransaction(); } catch (Exception ignored) { }

    }


    private Product upgradeDefinitionIfExisting(Product productDefinition) throws Exception {
        Product existingDefinition = getEntityManager().find(Product.class, productDefinition.getProductNumber());
        if(existingDefinition != null) {
            //existing Product
            existingDefinition.upgradeEntityFrom(productDefinition);
            return getEntityManager().merge(existingDefinition);


        } else {
            //new Product
            productDefinition.initNewEntity();
            getEntityManager().persist(productDefinition);
            return productDefinition;
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
    public void detectionSucceeded(DetectionResult detectionResult, Product definition, AbstractProduct extracted) {
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
