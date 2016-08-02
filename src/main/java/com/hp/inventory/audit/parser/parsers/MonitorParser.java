/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.ProductSpecification;

import java.util.HashSet;
import java.util.Set;

/**
 * Document parser for "PDP" type Monitor pages
 *
 * changes:
 *  - 1.0.6: refactor the columns to specifications.
 *  - 1.0.7: use a general way to extract the specs.
 * @author TCDEVELOPER
 * @version 1.0.7
 */
public class MonitorParser extends DocumentParser {

    /**
     * @inheritDoc
     */
    @Override
    protected AbstractProduct extract() throws Exception {
        Product p = this.definition;
        p.setCategory("Monitor");

        setParsingErrorsReceiver(p);

        extractCommonProps(p);

        checkParsedProps();

        return p;
    }
}
