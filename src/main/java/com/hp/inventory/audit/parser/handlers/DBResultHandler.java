/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.inventory.audit.parser.Report;
import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.DocumentParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Set;

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

    /**
     * @inheritDoc
     */
    @Override
    public void beforeStart() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("hp-product-parser");
        report = new Report();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void extractionFailed(Product definition, Exception e) {
        log.error("Exception occurred while trying to extract product #{}:", definition.getId(), e);
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
        	
        	getEntityManager().clear();
        	
        	startTransaction();
            try {
            	if(productDefinition.getParsingError()!=null) {
                	log.warn("product Id: {}, #{}: {}",
                			productDefinition.getId(),
                            productDefinition.getProductNumber(),
                            productDefinition.getParsingError());
                }

                upgradeDefinitionIfExisting(productDefinition);

                upgradeProductIfExisting(extractedEntity);

            	getEntityManager().flush();

            	commitTransaction();

                log.info("Processed product Id: {}, #{}, Type: {}",
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

    @Override
    public void addParserNotFound(Product definition) {
        report.addParserNotFound(definition);
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

	@Override
	public void detectParserFailed(Product definition) {
		report.addDetectFailed(definition);
	}
	
	@Override
	public void addIgnored(Product definition) {
		report.addIgnored(definition);
	}
	
	@Override
	public void addHit(String ruleHit) {
		report.addHit(ruleHit);
	}
}
