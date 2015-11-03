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

    private File dataDir;

    private int maxJobs = Runtime.getRuntime().availableProcessors();

    private ResultHandler resultHandler;

    Logger log = LoggerFactory.getLogger(ExtractionJobRunner.class);
    private int productId = -1;

    private String defaultCurrency = null;
    private String listDelimiter = null;
    
    /**
     * Iterate the source file, extract product information and adds to the database.
     */
    public void start() {
        log.info("Starting parsing job.");

        log.info("Maximum of jobs: {}", getMaxJobs());
        ExecutorService executorService = Executors.newFixedThreadPool(getMaxJobs());

        List<Future> futures = new LinkedList<>();

        resultHandler.beforeStart();

        Iterable<Product> products;
        if (productId == -1) {
            products = parseProducts();
        } else {
            ArrayList<Product> arr = new ArrayList<>();
            Product p = getProduct();
            if (p != null) arr.add(p);
            products = arr;
        }

        for (Product prod : products) {
            log.debug("Adding product Id:{} to parsing queue", prod.getId());
            ProductExtractorJob extractor  = new ProductExtractorJob(dataDir, prod, resultHandler);
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

        resultHandler.reportResults();
    }

    private Product getProduct() {
        for (Product prod : parseProducts()) {
            if (prod.getId() == getProductId()) {
                return prod;
            }
        }
        return null;
    }


    private Iterable<Product> parseProducts() {
        return new ProductIterable(new File(getDataDir(), SOURCE_CSV), defaultCurrency, listDelimiter);
    }



    /**
     * Gets the location of the "data" directory.
     */
    public File getDataDir() {
        return dataDir;
    }

    /**
     * Sets the location of the "data" directory.
     */
    public void setDataDir(File dataDir) {
        this.dataDir = dataDir;
    }


    /**
     * Gets the number of parallel jobs
     */
    public int getMaxJobs() {
        return maxJobs;
    }

    /**
     * Sets the number of parallel jobs
     * @param maxJobs
     */
    public void setMaxJobs(int maxJobs) {
        this.maxJobs = maxJobs;
    }

    /**
     * Gets the result handler for this job.
     */
    public ResultHandler getResultHandler() {
        return resultHandler;
    }

    /**
     * Sets the result handler for this job.
     */
    public void setResultHandler(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    /**
     * Sets the product Id for single page extraction. Set to -1 for extracting all pages.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets the product Id for single page extraction. A value of -1 means all pages.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the default currency
     */
	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}
	
	/**
     * Gets the default currency
     */
	public String getDefaultCurrency() {
		return this.defaultCurrency;
	}

	public String getListDelimiter() {
		return listDelimiter;
	}

	public void setListDelimiter(String listDelimiter) {
		this.listDelimiter = listDelimiter;
	}
}
