/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.ProductSpecification;
import com.hp.inventory.audit.parser.utils.LangTranslator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Parser for Tablet products
 *
 * changes:
 *  - 1.0.4: refactor the columns to specifications.
 *  - 1.0.5: use a general way to extract the specs.
 * @author TCDEVELOPER
 * @version 1.0.5
 */
public class TabletParser extends DocumentParser {
    /**
     * @throws Exception 
     * @inheritDoc
     */
    @Override
    public AbstractProduct extract() throws Exception {
        Product p = this.definition;
        p.setCategory("Tablet");
        setParsingErrorsReceiver(p);
        
        extractCommonProps(p);

        p.setHpDataSheet(propLink(getLocaleString("HP Data Sheet")));

        checkParsedProps();

        return p;
    }
}
