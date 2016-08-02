/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.ProductSpecification;
import com.hp.inventory.audit.parser.utils.LangTranslator;

/**
 * Document parser for "PDP" type Desktop pages
 *
 * changes:
 *  - 1.0.4: refactor the columns to specifications.
 *  - 1.0.5: use a general method to extract specs.
 *
 * @author TCDEVELOPER
 * @version 1.0.5
 */
public class DesktopParser extends DocumentParser {

    /**
     * @throws Exception 
     * @inheritDoc
     */
    @Override
    protected AbstractProduct extract() throws Exception {

        Product p = this.definition;

        p.setCategory("Desktop");

        setParsingErrorsReceiver(p);
        
        extractCommonProps(p);

        p.setHpDataSheet(propLink(getLocaleString("HP Data Sheet")));

        checkParsedProps();
        return p;
    }


}
