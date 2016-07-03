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
 * Document parser for "PDP" type Ink and Toner pages
 *
 * changes:
 *  - 1.0.6: refactor the columns to specifications.
 * 
 * @author TCDEVELOPER
 * @version 1.0.6
 */
public class InkAndTonerParser extends DocumentParser {

    /**
     * @inheritDoc
     */
    @Override
    protected AbstractProduct extract() throws Exception {
        Product p = this.definition;
        p.setCategory("InkAndToner");
        setParsingErrorsReceiver(p);

        extractCommonProps(p);

        Set<ProductSpecification> specifications = new HashSet<>();

        specifications.add(constructSpecification(p, "colorsOfPrintCartridges", prop("Color(s) of print cartridges")));
        specifications.add(constructSpecification(p, "pageYield",
                any(prop("Page yield (black and white)"), prop("Page yield (color)"))));
        specifications.add(constructSpecification(p, "pageYieldFootnote", prop("Page yield footnote")));
        specifications.add(constructSpecification(p, "inkDrop", prop("Ink drop")));
        specifications.add(constructSpecification(p, "compatibleInkTypes", prop("Compatible ink types")));
        specifications.add(constructSpecification(p, "operatingTemperatureRange", prop("Operating temperature range")));
        specifications.add(constructSpecification(p, "storageTemperatureRange", prop("Storage temperature range")));
        specifications.add(constructSpecification(p, "operatingHumidityRange", prop("Operating humidity range")));
        specifications.add(constructSpecification(p, "storageHumidity", prop("Storage humidity")));
        specifications.add(constructSpecification(p, "packageDimensions", prop("Package dimensions (W x D x H)")));
        specifications.add(constructSpecification(p, "packageWeight", prop("Package weight")));
        specifications.add(constructSpecification(p, "warranty", prop("Warranty")));
        specifications.add(constructSpecification(p, "whatsInTheBox", prop("What's in the box")));

        p.setSpecifications(specifications);

        checkParsedProps();

        return p;
    }
}
