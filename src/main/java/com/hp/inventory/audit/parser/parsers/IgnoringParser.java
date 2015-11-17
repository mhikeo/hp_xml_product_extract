package com.hp.inventory.audit.parser.parsers;

import java.math.BigDecimal;
import java.util.Date;

import com.hp.inventory.audit.parser.model.Desktop;
import com.hp.inventory.audit.parser.model.IProduct;

/**
 * Ignoring parser.
 *
 * @author TCDEVELOPER
 * @version 1.0.3
 */
public class IgnoringParser extends DocumentParser {

    /**
     * @inheritDoc
     */
    @Override
    protected IProduct extract() {
    	 
    	return null;
    	
    }


}
