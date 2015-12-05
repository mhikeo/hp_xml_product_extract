/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Tablet;

import java.math.BigDecimal;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.3
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
        Tablet p = new Tablet();
        
        setParsingErrorsReceiver(p);
        
        extractCommonProps(p);
        
        p.setBattery(prop("Battery", listDelimiter));
        p.setBatteryLife(prop("Battery life", true, listDelimiter));
        p.setMaxBatteryLifeInHours(parseBatteryLife(p.getBatteryLife()));
        p.setColor(prop("Color", listDelimiter));

        p.setDimensions(prop("Dimensions", listDelimiter));
        BigDecimal[] wdh = parseDimensions(p.getDimensions());
        if(wdh != null) {
            p.setDimensionWidthInInches(wdh[0]);
            p.setDimensionDepthInInches(wdh[1]);
            p.setDimensionHeightInInches(wdh[2]);
        }

        p.setDisplay(prop("Display", listDelimiter));
        p.setExpansionSlots(prop("Expansion slots", listDelimiter));
        p.setHpDataSheet(propLink("HP Data Sheet"));

        p.setMemory(prop("Memory", listDelimiter));
        p.setModelNumber(prop("Model number", listDelimiter));
        p.setOperatingSystem(prop("Operating system", listDelimiter));
        p.setPorts(prop("Ports", listDelimiter));
        p.setProcessor(prop("Processor", listDelimiter));
        p.setSoftwareIncluded(prop("Software included", listDelimiter));

        p.setWeight(prop("Weight", listDelimiter));
        p.setWeightInPounds(parseWeightInPounds(p.getWeight()));
        p.setWarranty(prop("Warranty", listDelimiter));
        p.setWireless(prop("Wireless", listDelimiter));
        p.setInternalStorage(prop("Internal storage", listDelimiter));
        p.setIntegratedCamera(prop("Integrated camera", listDelimiter));
        p.setSensors(prop("Sensors", listDelimiter));
        p.setAudio(prop("Audio", listDelimiter));
        p.setWhatsInTheBox(prop("What's in the box", listDelimiter));
        
        p.setChipset(prop("Chipset", true, listDelimiter));
        p.setEnergyEfficiency(prop("Energy efficiency", true, listDelimiter));
        p.setGraphics(prop("Graphics", true, listDelimiter));
        p.setInternalDrive(prop("Internal drive", true, listDelimiter));
        p.setPowerSupply(prop("Power supply", true, listDelimiter));
        p.setProcessorFamily(prop("Processor family", true, listDelimiter));
        p.setProcessorTechnology(prop("Processor technology", true, listDelimiter));
        p.setSecurityManagement(prop("Security management", true, listDelimiter));

        checkParsedProps();
        
        return p;
    }
}
