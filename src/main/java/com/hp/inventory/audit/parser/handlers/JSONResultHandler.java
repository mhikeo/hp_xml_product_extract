package com.hp.inventory.audit.parser.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.inventory.audit.parser.Report;
import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.DocumentParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.*;

/**
 * Writes the extracted products to the output file.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class JSONResultHandler implements ResultHandler {

    /**
     * If we should pretty-print the outputted JSON
     */
    private static final boolean PRETTY_PRINT = true;

    private Logger log = LoggerFactory.getLogger(DBResultHandler.class);
    private List<IProduct> parsed = new ArrayList<>();
    private PrintWriter output;
    private Map<String, Set<String>> nonParsedAttrs = new HashMap<>();

    private Report report;
    private PrintWriter reportOutput;

    /**
     * @inheritDoc
     */
    @Override
    public void beforeStart() {
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
     * @inheritDoc
     */
    @Override
    public void extractionSucceeded(Product definition, IProduct extracted) {
        parsed.add(extracted);
        report.addProductCount(extracted.getClass().getSimpleName(), extracted.getProductNumber());
    }

    /**
     * Print to output file the resulted extracted products as JSON objects.
     * Also logs statistics about non-parsed spec attributes.
     */
    @Override
    public void reportResults() {
        Gson gson;
        GsonBuilder gsonBuilder;
        if (PRETTY_PRINT) {
        	gsonBuilder = new GsonBuilder().setPrettyPrinting().serializeNulls();
        } else {
        	gsonBuilder = new GsonBuilder();
        }

        gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        
        gson = gsonBuilder.create();
        
        String out = gson.toJson(parsed);
        output.print(out);
        output.flush();

        String rep = gson.toJson(report);
        log.info("Writing report results.");
        reportOutput.print(rep);
        reportOutput.flush();

        output.close();
        reportOutput.close();
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
     * Sets the PrintWriter to write the results.
     */
    public void setOutput(PrintWriter output) {
        this.output = output;
    }

    /**
     * Gets the PrintWriter to write the results.
     */
    public PrintWriter getOutput() {
        return output;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setReportOutput(PrintWriter reportOutput) {
        this.reportOutput = reportOutput;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addParserNotFound(Product definition) {
        report.addParserNotFound(definition);
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
