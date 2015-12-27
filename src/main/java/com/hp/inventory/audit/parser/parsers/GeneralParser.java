/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.GeneralProduct;
import com.hp.inventory.audit.parser.model.IProduct;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.dom4j.Comment;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generic document parser.
 *
 * @author TCDEVELOPER
 * @version 1.0.3
 */
public class GeneralParser extends DocumentParser {

	IProduct bestParsed = null;
	int bestSpecParsed = 0;

	Pattern productTypePattern = Pattern.compile("catGroupTraversalByName FINAL: (.*?)~.*");

    /**
     * @throws Exception 
     * @inheritDoc
     */
    @Override
    protected AbstractProduct extract() throws Exception {
    	
    	GeneralProduct p = new GeneralProduct();

    	setParsingErrorsReceiver(p);
        
    	extractCommonProps(p);

		extractType(p);

    	return p;
    }

	/**
	 * Try to extract a more meaningful product type than "Other".
	 */
	private void extractType(GeneralProduct p) {
		String head = this.doc.head().html();
		Matcher m = productTypePattern.matcher(head);
		if (m.find()) {
			String t = m.group(1);
			t = WordUtils.capitalizeFully(t);
			this.definition.setProductType(t);
		} else {
			this.definition.setProductType("Other");
		}
	}
}
