package com.hp.inventory.audit.parser.handlers;

import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.DocumentParser;

import java.io.PrintWriter;
import java.util.Set;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public interface ResultHandler {

    /**
     * Called when a product extraction fails.
     * @param definition The product definition.
     * @param e the exception
     */
    void extractionFailed(Product definition, Exception e);

    /**
     * Called when a product extraction succeeded.
     * @param definition The product definition.
     * @param extracted The extracted product.
     * @throws Exception 
     */
    void extractionSucceeded(Product definition, IProduct extracted) throws Exception;

    /**
     * Notify the result handler of a set of non-parsed spec attribute.
     * @param definition The product definition.
     * @param documentParser The DocumentParser processing this product.
     * @param attributes Set of non-parsed attributes.
     */
    default void addNonParsedSpecItems(Product definition, DocumentParser documentParser, Set<String> attributes) { };

    /**
     * Method called before starting the job, for configuration.
     */
    default void beforeStart(){ }


    /**
     * Method called after job finish, to report errors to the user.
     */
    default void reportResults() { }

    /**
     * Sets the execution report output.
     */
    void setReportOutput(PrintWriter printWriter);

    void addParserNotFound(Product definition);
}
