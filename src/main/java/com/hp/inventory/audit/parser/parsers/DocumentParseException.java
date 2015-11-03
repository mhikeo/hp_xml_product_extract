package com.hp.inventory.audit.parser.parsers;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class DocumentParseException extends RuntimeException {

    public DocumentParseException() {

    }

    public DocumentParseException(Throwable cause) {
        super(cause);
    }

    public DocumentParseException(String message) {
        super(message);
    }

    public DocumentParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
