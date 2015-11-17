package com.hp.inventory.audit.parser;

import com.hp.inventory.audit.parser.handlers.DBResultHandler;
import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.parsers.DocumentParser;
import com.hp.inventory.audit.parser.parsers.DocumentParserDetector;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Command-line application entry-point.
 *
 * @author TCDEVELOPER
 * @version 1.0.3
 */
public class Main {


    public static final String REPORT_JSON = "report.json";
    public static final String EXECUTABLE_NAME = "hp_product_extract";
    private static Options options;
    private static CommandLine cmd;

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Config config = configure(args);
            start(config);
        } catch (ParseException e) {
            log.warn("Error while parsing the options: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected exception was thrown. Check log files for more information.", e);
            System.exit(1);
        }
    }

    public static void start(Config config) throws ClassNotFoundException, IOException {
    	DocumentParserDetector.init(config.rulesConfig);
    	DocumentParser.init(config.rulesConfig);
    	
        ExtractionJobRunner parser = new ExtractionJobRunner();
        parser.setResultHandler(config.resultHandler);
        parser.setDataDir(config.dataDirectory);
        parser.setMaxJobs(config.maxJobs);
        parser.setProductId(config.singleProductId);
        parser.setDefaultCurrency(config.defaultCurrency);
        parser.setListDelimiter(config.listDelimiter);
        parser.setPropertiesThreshold(config.propertiesThreshold);
        parser.start();
    }

    /**
     * Create configuration options, parse the command-line arguments and return a Config object with the
     * parsed parameters.
     * @param args The command-line args from "main".
     * @return a configured Config object.
     * @throws Exception
     */
    public static Config configure(String[] args) throws Exception {
        createOptions();
        parseOptions(args);
        Config config = new Config();
        if (cmd.hasOption("h")) {
            printHelpAndQuit();
        }

        if (!cmd.hasOption("d")) {
            printHelpAndQuit();
        } else {
            config.dataDirectory = new File(cmd.getOptionValue("d"));
            checkDirectoryReadable(config.dataDirectory);
        }

        if (cmd.hasOption("j")) {
            try {
                config.maxJobs = Integer.parseInt(cmd.getOptionValue("j"));
            } catch (NumberFormatException e) {
                throw new ParseException("Max jobs should be an integer: " + cmd.getOptionValue("j"));
            }
        } else {
            config.maxJobs = Runtime.getRuntime().availableProcessors();
            //config.maxJobs = 1;
        }

        if (cmd.hasOption("r")) {
            if (cmd.getOptionValue("r").toLowerCase().equals("db")) {
                config.resultHandler = new DBResultHandler();

            } else if (cmd.getOptionValue("r").toLowerCase().equals("json")) {
                PrintWriter output = new PrintWriter(System.out);
                if (cmd.hasOption("f")) {
                    output = new PrintWriter(new FileWriter(cmd.getOptionValue("f")));
                }

                JSONResultHandler h = new JSONResultHandler();
                h.setOutput(output);
                config.resultHandler = h;
            } else {
                throw new ParseException("Output should be either 'db' or 'json': " + cmd.getOptionValue("o"));
            }
        } else {
            config.resultHandler = new DBResultHandler();
        }

        if (cmd.hasOption("s")) {
            try {
                config.singleProductId = Integer.parseInt(cmd.getOptionValue("s"));
            } catch (NumberFormatException e) {
                throw new ParseException("Product ID for single page extraction should be an integer: " + cmd.getOptionValue("s"));
            }
        } else {
            config.singleProductId = -1;
        }

        if (cmd.hasOption("p")) {
            File reportFile = new File(cmd.getOptionValue("p"));
            config.resultHandler.setReportOutput(new PrintWriter(new FileWriter(reportFile)));
        } else {
            config.resultHandler.setReportOutput(new PrintWriter(new FileWriter(new File(REPORT_JSON))));
        }

        /**@since 1.0.1
         */
        if (cmd.hasOption("c")) {
        	config.defaultCurrency = cmd.getOptionValue("c");
        } else {
        	config.defaultCurrency = null;
        }
        
        /**@since 1.0.2
         */
        if (cmd.hasOption("l")) {
        	config.listDelimiter = cmd.getOptionValue("l");
        } else {
        	config.listDelimiter = "\n";
        }
        
        /**@since 1.0.3
         */
        if (cmd.hasOption("t")) {
        	config.propertiesThreshold = Integer.parseInt(cmd.getOptionValue("t"));
        } else {
        	config.propertiesThreshold = 3;
        }
        
        /**@since 1.0.3
         */
        if (!cmd.hasOption("cfg")) {
        	printHelpAndQuit();
        } else {
        	config.rulesConfig  = new File(cmd.getOptionValue("cfg"));
            checkConfigReadable(config.rulesConfig);
        }
        
        return config;

    }

    private static void checkDirectoryReadable(File dir) throws ParseException {
        if (!dir.isDirectory()) {
            throw new ParseException("Directory is not, in fact, a directory: " + dir.getAbsolutePath());
        }

        if (!dir.canRead()) {
            throw new ParseException("Directory is not readable: " + dir.getAbsolutePath());
        }
    }

    private static void checkConfigReadable(File file) throws ParseException {
        if (!file.isFile()) {
            throw new ParseException("File is not, in fact, a file: " + file.getAbsolutePath());
        }

        if (!file.canRead()) {
            throw new ParseException("File is not readable: " + file.getAbsolutePath());
        }
    }
    private static void printHelpAndQuit() {
        HelpFormatter fmt = new HelpFormatter();
        fmt.printHelp(EXECUTABLE_NAME, options, true);
        System.exit(1);
    }

    private static void parseOptions(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            log.error("Error: {}", e.getLocalizedMessage());
            printHelpAndQuit();
        }
    }

    private static void createOptions() {
        options = new Options();

        options.addOption("d", "data", true, "Data directory. Required.");
        options.getOption("d").setRequired(true);

        options.addOption("r", "result-handler", true, "Result handler type. 'db' for writing to the database, " +
                "'json' for outputting a JSON representation of the extracted products. Defaults to 'db'.");

        options.addOption("j", "jobs", true, "Max number of parallel jobs. Defaults to number of cores.");

        options.addOption("h", "Show this help file.");

        options.addOption("f", "file", true, "(JSON only) specify the output file. Defaults to console.");

        options.addOption("p", "report-file", true, "Specify the extraction report output file. Defaults to 'report.json'.");

        options.addOption("s", "single", true, "If present, extracts a single page with the given number instead of all pages.");
        
        /** @since 1.0.1
         */
        options.addOption("c", "currency", true, "Specify the currency to set to all parsed products. " +
                "If not specified, will try to parse currency from each individual file");

        /** @since 1.0.2
         */
        options.addOption("l", "delimiter", true, "Specify the delimiter to use for property list. " +
                "Defaults to line feed (\\n)");
        
        /** @since 1.0.3
         */
        options.addOption("t", "threshold", true, "Specify the number of required properties to accept the page as a product description.");
        
        /** @since 1.0.3
         */
        options.addOption("cfg", "config", true, "Rules config file. Required.");
}

}
