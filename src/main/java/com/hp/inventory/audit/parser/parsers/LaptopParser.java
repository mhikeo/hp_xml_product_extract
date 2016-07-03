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
 * Document parser for "PDP" type Laptop pages
 *
 * changes:
 *  - 1.0.4: refactor the columns to specifications.
 * 
 * @author TCDEVELOPER
 * @version 1.0.4
 */
public class LaptopParser extends DocumentParser {
	
    /**
     * @throws Exception 
     * @inheritDoc
     */
    @Override
    protected AbstractProduct extract() throws Exception {

        Product p = this.definition;
        p.setCategory("Laptop");

        setParsingErrorsReceiver(p);
        
        extractCommonProps(p);

        p.setHpDataSheet(propLink("HP Data Sheet"));

        Set<ProductSpecification> specifications = new HashSet<>();

        specifications.add(constructSpecification(p, "battery", any(prop("Battery", true, listDelimiter),
                prop("Primary battery", listDelimiter))));
        specifications.add(constructSpecification(p, "batteryLife", prop("Battery life", true, listDelimiter)));
        specifications.add(constructSpecification(p, "maxBatteryLifeInHours",
                String.valueOf(parseBatteryLife(prop("Battery life", true, listDelimiter)))));
        specifications.add(constructSpecification(p, "color", prop("Color", listDelimiter)));

        specifications.add(constructSpecification(p, "dimensions", prop("Dimensions", listDelimiter)));
        BigDecimal[] wdh = parseDimensions(prop("Dimensions", listDelimiter));
        if(wdh != null) {
            specifications.add(constructSpecification(p, "dimensionWidthInInches", String.valueOf(wdh[0])));
            specifications.add(constructSpecification(p, "dimensionDepthInInches", String.valueOf(wdh[1])));
            specifications.add(constructSpecification(p, "dimensionHeightInInches", String.valueOf(wdh[2])));
        }

        specifications.add(constructSpecification(p, "display", prop("Display", listDelimiter)));
        specifications.add(constructSpecification(p, "energyEfficiency", prop("Energy efficiency", listDelimiter)));
        specifications.add(constructSpecification(p, "expansionSlots", prop("Expansion slots", listDelimiter)));
        specifications.add(constructSpecification(p, "hardDrive", any(prop("Hard drive", listDelimiter),
                prop("Internal drive", listDelimiter), prop("Internal storage", listDelimiter))));


        specifications.add(constructSpecification(p, "keyboard", prop("Keyboard", listDelimiter)));
        specifications.add(constructSpecification(p, "memory", prop("Memory", listDelimiter)));
        specifications.add(constructSpecification(p, "modelNumber", prop("Model number", listDelimiter)));
        specifications.add(constructSpecification(p, "operatingSystem", prop("Operating system", listDelimiter)));
        specifications.add(constructSpecification(p, "pointingDevices", prop("Pointing device", listDelimiter)));
        specifications.add(constructSpecification(p, "ports", prop("Ports", listDelimiter)));
        specifications.add(constructSpecification(p, "powerSupply", prop("Power supply", listDelimiter)));
        specifications.add(constructSpecification(p, "softwareIncluded",
                any(prop("Software included", listDelimiter), prop("Software", listDelimiter))));
        
        specifications.add(constructSpecification(p, "webcam",
                any(prop("Webcam", listDelimiter), prop("Integrated camera", listDelimiter),
                        prop("Camera", listDelimiter))));
        specifications.add(constructSpecification(p, "weight", prop("Weight", true, listDelimiter)));
        specifications.add(constructSpecification(p, "weightInPounds",
                String.valueOf(parseWeightInPounds(prop("Weight", true, listDelimiter)))));
        specifications.add(constructSpecification(p, "warranty", prop("Warranty",true, listDelimiter)));
        specifications.add(constructSpecification(p, "networking",
                any(prop("Wireless", listDelimiter), prop("Networking", listDelimiter),
                        prop("Network interface", listDelimiter))));

        specifications.add(constructSpecification(p, "acAdapter", prop("AC adapter",true, listDelimiter)));
        specifications.add(constructSpecification(p, "accessories", prop("Accessories",true, listDelimiter)));
        specifications.add(constructSpecification(p, "additionalBay", prop("Additional bay",true, listDelimiter)));
        specifications.add(constructSpecification(p, "audio", prop("Audio",true, listDelimiter)));
        specifications.add(constructSpecification(p, "bluetooth", prop("Bluetooth",true, listDelimiter)));
        specifications.add(constructSpecification(p, "broadbandServiceProvider",
                prop("Broadband service provider",true, listDelimiter)));
        specifications.add(constructSpecification(p, "chipset", prop("Chipset",true, listDelimiter)));
        specifications.add(constructSpecification(p, "externalIOPorts",
                prop("External I/O Ports",true, listDelimiter)));
        specifications.add(constructSpecification(p, "fingerPrintReader",
                prop("Finger print reader",true, listDelimiter)));
        specifications.add(constructSpecification(p, "flashCache", prop("Flash cache",true, listDelimiter)));
        specifications.add(constructSpecification(p, "hpMobileBroadband",
                prop("HP mobile broadband",true, listDelimiter)));
        specifications.add(constructSpecification(p, "labelEnergyStar", prop("Label ENERGY STAR",true, listDelimiter)));
        specifications.add(constructSpecification(p, "laplinkPcmoverSoftware",
                prop("Laplink PCmover Software",true, listDelimiter)));
        specifications.add(constructSpecification(p, "securitySoftware",
                prop("McAfee LiveSafe(TM) Security Software",true, listDelimiter)));
        specifications.add(constructSpecification(p, "memorySlots", prop("Memory slots",true, listDelimiter)));
        specifications.add(constructSpecification(p, "miniCard", prop("Mini card",true, listDelimiter)));
        specifications.add(constructSpecification(p, "miniCardSsd", prop("Mini card SSD",true, listDelimiter)));
        specifications.add(constructSpecification(p, "miscWarrantyDocumentation",
                prop("Misc warranty documentation",true, listDelimiter)));
        specifications.add(constructSpecification(p, "modem", prop("Modem",true, listDelimiter)));
        specifications.add(constructSpecification(p, "nearFieldCommunication",
                prop("Near field communication",true, listDelimiter)));
        specifications.add(constructSpecification(p, "osRecoveryCd", prop("OS recovery CD",true, listDelimiter)));
        specifications.add(constructSpecification(p, "officeSoftware", prop("Office software",true, listDelimiter)));
        specifications.add(constructSpecification(p, "opticalDrive", prop("Optical drive",true, listDelimiter)));
        specifications.add(constructSpecification(p, "outOfBandManagement",
                prop("Out-of-Band management",true, listDelimiter)));
        specifications.add(constructSpecification(p, "personalization", prop("Personalization",true, listDelimiter)));
        specifications.add(constructSpecification(p, "powerCord", prop("Power cord",true, listDelimiter)));
        specifications.add(constructSpecification(p, "processorFamily", prop("Processor family",true, listDelimiter)));
        specifications.add(constructSpecification(p, "processorTechnology",
                prop("Processor technology",true, listDelimiter)));
        specifications.add(constructSpecification(p, "recoveryMediaDriver",
                prop("Recovery media driver",true, listDelimiter)));
        specifications.add(constructSpecification(p, "securityManagement",
                prop("Security management",true, listDelimiter)));
        specifications.add(constructSpecification(p, "sensors", prop("Sensors",true, listDelimiter)));
        specifications.add(constructSpecification(p, "theftProtection",
                prop("Theft protection",true, listDelimiter)));
        specifications.add(constructSpecification(p, "warrantyBattery",
                prop("Warranty battery",true, listDelimiter)));
        specifications.add(constructSpecification(p, "boxContents",
                prop("What\u0027s in the box",true, listDelimiter)));
        specifications.add(constructSpecification(p, "wirelessLan", prop("Wireless LAN",true, listDelimiter)));

        extractProcessorAndGraphics(p, specifications);

        p.setSpecifications(specifications);

        checkParsedProps();
        
        return p;
    }


}
