/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.ProductSpecification;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Parser for Tablet products
 *
 * changes:
 *  - 1.0.4: refactor the columns to specifications.
 * 
 * @author TCDEVELOPER
 * @version 1.0.4
 */
public class TabletParser extends DocumentParser {
	
    /**
     * The number of accessories to add to "topAccessories" field
     */
    private static final int ACCESSORIES_COUNT = 3;

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

        p.setHpDataSheet(propLink("HP Data Sheet"));

        Set<ProductSpecification> specifications = new HashSet<>();

        specifications.add(constructSpecification(p, "battery", prop("Battery", listDelimiter)));
        specifications.add(constructSpecification(p, "batteryLife", prop("Battery life", true, listDelimiter)));
        specifications.add(constructSpecification(p, "maxBatteryLifeInHours", String.valueOf(parseBatteryLife(prop("Battery life", true, listDelimiter)))));
        specifications.add(constructSpecification(p, "color", prop("Color", listDelimiter)));

        specifications.add(constructSpecification(p, "dimensions", prop("Dimensions", listDelimiter)));
        BigDecimal[] wdh = parseDimensions(prop("Dimensions", listDelimiter));
        if(wdh != null) {
            specifications.add(constructSpecification(p, "dimensionWidthInInches", String.valueOf(wdh[0])));
            specifications.add(constructSpecification(p, "dimensionDepthInInches", String.valueOf(wdh[1])));
            specifications.add(constructSpecification(p, "dimensionHeightInInches", String.valueOf(wdh[2])));
        }

        specifications.add(constructSpecification(p, "display", prop("Display", listDelimiter)));
        specifications.add(constructSpecification(p, "expansionSlots", prop("Expansion slots", listDelimiter)));


        specifications.add(constructSpecification(p, "memory", prop("Memory", listDelimiter)));
        specifications.add(constructSpecification(p, "modelNumber", prop("Model number", listDelimiter)));
        specifications.add(constructSpecification(p, "operatingSystem", prop("Operating system", listDelimiter)));
        specifications.add(constructSpecification(p, "ports", prop("Ports", listDelimiter)));
        specifications.add(constructSpecification(p, "processor", prop("Processor", listDelimiter)));
        specifications.add(constructSpecification(p, "softwareIncluded", prop("Software included", listDelimiter)));

        specifications.add(constructSpecification(p, "weight", prop("Weight", listDelimiter)));
        specifications.add(constructSpecification(p, "weightInPounds",
                String.valueOf(parseWeightInPounds(prop("Weight", listDelimiter)))));
        specifications.add(constructSpecification(p, "warranty", prop("Warranty", listDelimiter)));
        specifications.add(constructSpecification(p, "wireless", prop("Wireless", listDelimiter)));
        specifications.add(constructSpecification(p, "internalStorage", prop("Internal storage", listDelimiter)));
        specifications.add(constructSpecification(p, "integratedCamera", prop("Integrated camera", listDelimiter)));
        specifications.add(constructSpecification(p, "sensors", prop("Sensors", listDelimiter)));
        specifications.add(constructSpecification(p, "audio", prop("Audio", listDelimiter)));
        specifications.add(constructSpecification(p, "whatsInTheBox", prop("What's in the box", listDelimiter)));
        
        specifications.add(constructSpecification(p, "chipset", prop("Chipset", true, listDelimiter)));
        specifications.add(constructSpecification(p, "energyEfficiency", prop("Energy efficiency", true, listDelimiter)));
        specifications.add(constructSpecification(p, "graphics", prop("Graphics", true, listDelimiter)));
        specifications.add(constructSpecification(p, "internalDrive", prop("Internal drive", true, listDelimiter)));
        specifications.add(constructSpecification(p, "powerSupply", prop("Power supply", true, listDelimiter)));
        specifications.add(constructSpecification(p, "processorFamily", prop("Processor family", true, listDelimiter)));
        specifications.add(constructSpecification(p, "processorTechnology", prop("Processor technology", true, listDelimiter)));
        specifications.add(constructSpecification(p, "securityManagement", prop("Security management", true, listDelimiter)));

        checkParsedProps();

        p.setSpecifications(specifications);
        
        return p;
    }
}
