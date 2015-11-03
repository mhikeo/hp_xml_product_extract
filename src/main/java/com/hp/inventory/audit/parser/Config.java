package com.hp.inventory.audit.parser;

import com.hp.inventory.audit.parser.handlers.DBResultHandler;
import com.hp.inventory.audit.parser.handlers.ResultHandler;

import javax.persistence.EntityManagerFactory;
import java.io.File;

/**
 * Holder for configuration properties.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class Config {

    public File dataDirectory;
    public int maxJobs;
    public ResultHandler resultHandler;
    public int singleProductId;
    public String defaultCurrency;
    public String listDelimiter;
}
