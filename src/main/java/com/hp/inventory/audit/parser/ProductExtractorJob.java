package com.hp.inventory.audit.parser;

import com.hp.inventory.audit.parser.handlers.ResultHandler;
import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.*;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Runs a single product extract job. To be used with a ExecutorService.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class ProductExtractorJob implements Runnable {
    private final Product definition;
    private final File dataDir;
    private final ResultHandler resultHandler;

    Logger log = LoggerFactory.getLogger(ProductExtractorJob.class);


    public ProductExtractorJob(File dataDir, Product definition, ResultHandler resultHandler) {
        this.dataDir = dataDir;
        this.definition = definition;
        this.resultHandler = resultHandler;
    }

    /**
     * Runs the extraction job by loading the content, identifying the correct parser, parsing the content
     * and passing the results to the result handler.
     */
    @Override
    public void run() {
        try {
            log.info("Parsing html file: {}", definition.getSourceFile());
            String baseUrl = getBaseURL(definition.getProductUrl());
            File f = new File(this.dataDir, definition.getSourceFile());
            String content = FileUtils.readFileToString(f);
            DocumentParser parser = DocumentParserDetector.detect(definition.getProductUrl(), content);
            if (parser != null) {
                Document doc = Jsoup.parse(content, baseUrl);
                IProduct extracted = parser.parse(doc, definition, resultHandler);
                resultHandler.extractionSucceeded(definition, extracted);
                doc = null; // Hint GC deallocation
            }
            content = null; // Hint GC deallocation
        } catch (Exception e) {
            resultHandler.extractionFailed(definition, e);
        }
    }

    private String getBaseURL(String urlString) throws MalformedURLException {
        URL url = new URL(urlString);
        String path = url.getFile().substring(0, url.getFile().lastIndexOf('/'));
        String base = url.getProtocol() + "://" + url.getHost() + path;
        return base;
    }

}
