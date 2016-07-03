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

/**
 * Document parser for "PDP" type Desktop pages
 *
 * changes:
 *  - 1.0.4: refactor the columns to specifications.
 * 
 * @author TCDEVELOPER
 * @version 1.0.4
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

        p.setHpDataSheet(propLink("HP Data Sheet"));


        Set<ProductSpecification> specifications = new HashSet<>();


        specifications.add(constructSpecification(p, "color", prop("Color", listDelimiter)));
        specifications.add(constructSpecification(p, "dimensions", prop("Dimensions", listDelimiter)));
        BigDecimal[] wdh = parseDimensions(prop("Dimensions", listDelimiter));
        if(wdh != null) {
            specifications.add(constructSpecification(p, "dimensionWidthInInches", String.valueOf(wdh[0])));
            specifications.add(constructSpecification(p, "dimensionDepthInInches", String.valueOf(wdh[1])));
            specifications.add(constructSpecification(p, "dimensionHeightInInches", String.valueOf(wdh[2])));
        }
        
        specifications.add(constructSpecification(p, "energyEfficiency", prop("Energy efficiency", listDelimiter)));
        specifications.add(constructSpecification(p, "expansionSlots", prop("Expansion slots", listDelimiter)));
        specifications.add(constructSpecification(p, "hardDrive", any(prop("Hard drive", listDelimiter),
                prop("Internal drive", listDelimiter),
                prop("Internal storage", listDelimiter))));

        specifications.add(constructSpecification(p, "memory", prop("Memory", listDelimiter)));
        specifications.add(constructSpecification(p, "modelNumber", prop("Model number", listDelimiter)));
        specifications.add(constructSpecification(p, "operatingSystem", prop("Operating system", listDelimiter)));
        specifications.add(constructSpecification(p, "ports", prop("Ports", listDelimiter)));
        specifications.add(constructSpecification(p, "powerSupply", prop("Power supply", listDelimiter)));
        specifications.add(constructSpecification(p, "softwareIncluded", prop("Software included", listDelimiter)));
        
        specifications.add(constructSpecification(p, "weight", prop("Weight", listDelimiter)));
        specifications.add(constructSpecification(p, "weightInPounds",
                String.valueOf(parseWeightInPounds(prop("Weight", listDelimiter)))));
        specifications.add(constructSpecification(p, "warranty", prop("Warranty", listDelimiter)));
        specifications.add(constructSpecification(p, "wireless", prop("Wireless", listDelimiter)));
        specifications.add(constructSpecification(p, "keyboard", any(prop("Keyboard", listDelimiter),
                prop("Keyboard and mouse", listDelimiter))));
        specifications.add(constructSpecification(p, "pointingDevices",
                any(prop("Pointing devices", listDelimiter), prop("Mouse", listDelimiter))));
        specifications.add(constructSpecification(p, "opticalDrive",
                prop("Optical drive", listDelimiter)));
        specifications.add(constructSpecification(p, "memorySlots",
                prop("Memory slots", listDelimiter)));
        specifications.add(constructSpecification(p, "networking", any(
                prop("Networking", listDelimiter),
                prop("Network card", listDelimiter),
                prop("Network interface", listDelimiter),
                prop("Integrated network", listDelimiter),
                prop("LAN", listDelimiter))));
        specifications.add(constructSpecification(p, "officeSoftware", prop("Office software", listDelimiter)));
        specifications.add(constructSpecification(p, "photographySoftware",
                prop("Photography software", listDelimiter)));
        specifications.add(constructSpecification(p, "popularSoftware",
                prop("Popular, useful and fun software", listDelimiter)));
        specifications.add(constructSpecification(p, "audio", prop("Audio", listDelimiter)));
        specifications.add(constructSpecification(p, "softwareUpgrades", prop("Software upgrades", listDelimiter)));
        specifications.add(constructSpecification(p, "soundCard", prop("Sound card", listDelimiter)));
        specifications.add(constructSpecification(p, "tvTurner", prop("TV turner", listDelimiter)));
        
        specifications.add(constructSpecification(p, "additionalApplicationSoftware",
                prop("Additional application software", true, listDelimiter)));
        specifications.add(constructSpecification(p, "additionalNetworkingOptions",
                prop("Additional networking options", true, listDelimiter)));
        specifications.add(constructSpecification(p, "battery", prop("Battery", true, listDelimiter)));
        specifications.add(constructSpecification(p, "batteryLife", prop("Battery life", true, listDelimiter)));
        specifications.add(constructSpecification(p, "cableOptionKits",
                prop("Cable option kits", true, listDelimiter)));
        specifications.add(constructSpecification(p, "chassis", prop("Chassis", true, listDelimiter)));
        specifications.add(constructSpecification(p, "chinaCccCompliance",
                prop("China CCC compliance", true, listDelimiter)));
        specifications.add(constructSpecification(p, "controller", prop("Controller", true, listDelimiter)));
        specifications.add(constructSpecification(p, "display", prop("Display", true, listDelimiter)));
        specifications.add(constructSpecification(p, "displayCable", prop("Display cable", true, listDelimiter)));
        specifications.add(constructSpecification(p, "energyStar", prop("ENERGY STAR", true, listDelimiter)));
        specifications.add(constructSpecification(p, "eightInternalStorage",
                prop("Eight internal storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "externalIOPorts",
                prop("External I/O Ports", true, listDelimiter)));
        specifications.add(constructSpecification(p, "externalOpticalDrive",
                prop("External optical drive", true, listDelimiter)));
        specifications.add(constructSpecification(p, "fempCompliance",
                prop("FEMP compliance", true, listDelimiter)));
        specifications.add(constructSpecification(p, "fifthInternalStorage",
                prop("Fifth internal storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "flashCache",
                prop("Flash cache", true, listDelimiter)));
        specifications.add(constructSpecification(p, "fourthInternalStorage",
                prop("Fourth internal storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "graphicsConnectors",
                prop("Graphics connectors", true, listDelimiter)));
        specifications.add(constructSpecification(p, "highPerformanceGpuComputing",
                prop("High performance GPU computing", true, listDelimiter)));
        specifications.add(constructSpecification(p, "integratedCamera",
                prop("Integrated camera", true, listDelimiter)));
        specifications.add(constructSpecification(p, "intelSrtDiskCacheModules",
                prop("Intel SRT disk cache modules", true, listDelimiter)));
        specifications.add(constructSpecification(p, "intelSmartResponseTechnology",
                prop("Intel Smart Response Technology", true, listDelimiter)));
        specifications.add(constructSpecification(p, "internalOsLoadStorageOptions",
                prop("Internal OS load storage options", true, listDelimiter)));
        specifications.add(constructSpecification(p, "internalPcieStorage",
                prop("Internal PCIe storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "internalStorageOptions",
                prop("Internal storage options", true, listDelimiter)));
        specifications.add(constructSpecification(p, "lanTransceivers",
                prop("LAN Transceivers", true, listDelimiter)));
        specifications.add(constructSpecification(p, "label",
                prop("Label", true, listDelimiter)));
        specifications.add(constructSpecification(p, "maximumMemory",
                prop("Maximum memory", true, listDelimiter)));
        specifications.add(constructSpecification(p, "securitySoftware",
                prop("McAfee LiveSafe(TM) Security Software", true, listDelimiter)));
        specifications.add(constructSpecification(p, "mediaReader",
                prop("Media reader", true, listDelimiter)));
        specifications.add(constructSpecification(p, "memoryCardDevice",
                prop("Memory card device", true, listDelimiter)));
        specifications.add(constructSpecification(p, "processorTechnology",
                prop("Processor technology", true, listDelimiter)));
        specifications.add(constructSpecification(p, "realTimeDataBackup",
                prop("Real-time data backup", true, listDelimiter)));
        specifications.add(constructSpecification(p, "secondDisplayCable",
                prop("Second display cable", true, listDelimiter)));
        specifications.add(constructSpecification(p, "secondGraphicsCard",
                prop("Second graphics card", true, listDelimiter)));
        specifications.add(constructSpecification(p, "secondaryOpticalDrive",
                prop("Secondary optical drive", true, listDelimiter)));
        specifications.add(constructSpecification(p, "secondInternalStorage",
                prop("Second internal storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "secondaryProcessor",
                prop("Secondary processor", true, listDelimiter)));
        specifications.add(constructSpecification(p, "secondaryInternalPcieStorage",
                prop("Secondary internal PCIe storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "security",
                prop("Security", true, listDelimiter)));
        specifications.add(constructSpecification(p, "securityEncryption",
                prop("Security encryption", true, listDelimiter)));
        specifications.add(constructSpecification(p, "sixthInternalStorage",
                prop("Sixth internal storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "seventhInternalStorage",
                prop("Seventh iternal storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "softwareBundles",
                prop("Software bundles", true, listDelimiter)));
        specifications.add(constructSpecification(p, "speakers",
                prop("Speakers", true, listDelimiter)));
        specifications.add(constructSpecification(p, "stand",
                prop("Stand", true, listDelimiter)));
        specifications.add(constructSpecification(p, "systemRecoverySolutions",
                prop("System recovery solutions", true, listDelimiter)));
        specifications.add(constructSpecification(p, "tvTuner",
                prop("TV tuner", true, listDelimiter)));
        specifications.add(constructSpecification(p, "technical",
                prop("Technical", true, listDelimiter)));
        specifications.add(constructSpecification(p, "technicalAV",
                prop("Technical AV", true, listDelimiter)));
        specifications.add(constructSpecification(p, "thirdGraphicsCard",
                prop("Third graphics card", true, listDelimiter)));
        specifications.add(constructSpecification(p, "thirdInternalStorage",
                prop("Third internal storage", true, listDelimiter)));
        specifications.add(constructSpecification(p, "webcam", prop("Webcam", true, listDelimiter)));
        specifications.add(constructSpecification(p, "accessories", prop("Accessories", true, listDelimiter)));

        extractProcessorAndGraphics(p, specifications);

        p.setSpecifications(specifications);

        checkParsedProps();
        return p;
    }


}
