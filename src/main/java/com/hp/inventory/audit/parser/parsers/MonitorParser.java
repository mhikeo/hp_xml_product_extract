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
 * 
 * @author TCDEVELOPER
 * @version 1.0.6
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

        Set<ProductSpecification> specifications = new HashSet<>();

        specifications.add(constructSpecification(p, "brightness", prop("Brightness")));
        specifications.add(constructSpecification(p, "nativeResolution", prop("Native resolution")));
        specifications.add(constructSpecification(p, "contrastRatio", prop("Contrast ratio")));
        specifications.add(constructSpecification(p, "pixelPitch", prop("Pixel pitch")));
        specifications.add(constructSpecification(p, "responseTime", prop("Response time")));
        specifications.add(constructSpecification(p, "displayTiltAndSwivelRange", prop("Display Tilt & Swivel Range")));
        specifications.add(constructSpecification(p, "energyEfficiency", prop("Energy efficiency")));
        specifications.add(constructSpecification(p, "dimensions", prop("Dimensions (W X D X H)")));
        specifications.add(constructSpecification(p, "weight", prop("Weight")));
        specifications.add(constructSpecification(p, "whatsInTheBox", prop("What's in the box")));

        p.setSpecifications(specifications);

        checkParsedProps();

        return p;
    }
}
