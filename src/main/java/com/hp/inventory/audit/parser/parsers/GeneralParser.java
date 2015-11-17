package com.hp.inventory.audit.parser.parsers;

import java.math.BigDecimal;
import java.util.Date;

import com.hp.inventory.audit.parser.model.Desktop;
import com.hp.inventory.audit.parser.model.GeneralProduct;
import com.hp.inventory.audit.parser.model.IProduct;

/**
 * Document parser. Tries every possible parser, in attempt to find the most suitable
 *
 * @author TCDEVELOPER
 * @version 1.0.3
 */
public class GeneralParser extends DocumentParser {

	IProduct bestParsed = null;
	int bestSpecParsed = 0;
	
    /**
     * @throws Exception 
     * @inheritDoc
     */
    @Override
    protected IProduct extract() throws Exception {
    	
    	GeneralProduct p = new GeneralProduct();
    	
    	setParsingErrorsReceiver(p);
        
    	extractCommonProps(p);
    	
    	return p;
    }
//
//	private void tryParse(DocumentParser parser) {
//		
//		try {
//			IProduct parsed = parser.parse(doc, definition, resultHandler);
//	    	
//	    	if(parsed != null) {
//	    		int currentSpecParsed = parser.getSpecParsed();
//	    		
//	    		log.info("parsed successfully as {}: parsed specs {}", parsed.getClass().toString(), currentSpecParsed );
//    			
//	    		if (bestParsed==null || currentSpecParsed > bestSpecParsed ) {
//	    			bestParsed = parsed;
//	    			bestSpecParsed = currentSpecParsed;
//	    		}
//	    	}
//    	} catch (Exception ignored) {
//    		
//    	}
//	}
//

}
