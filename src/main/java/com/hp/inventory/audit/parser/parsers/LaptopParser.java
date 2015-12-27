/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Laptop;

import java.math.BigDecimal;

/**
 * Document parser for "PDP" type Laptop pages
 *
 * @author TCDEVELOPER
 * @version 1.0.3
 */
public class LaptopParser extends DocumentParser {
	
    /**
     * @throws Exception 
     * @inheritDoc
     */
    @Override
    protected AbstractProduct extract() throws Exception {

        Laptop p = new Laptop();
        
        setParsingErrorsReceiver(p);
        
        extractCommonProps(p);
        
        p.setBattery(any(prop("Battery", true, listDelimiter), prop("Primary battery", listDelimiter)));
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
        p.setEnergyEfficiency(prop("Energy efficiency", listDelimiter));
        p.setExpansionSlots(prop("Expansion slots", listDelimiter));
        p.setHardDrive(any(prop("Hard drive", listDelimiter), prop("Internal drive", listDelimiter), prop("Internal storage", listDelimiter)));
        p.setHpDataSheet(propLink("HP Data Sheet"));

        p.setKeyboard(prop("Keyboard", listDelimiter));
        p.setMemory(prop("Memory", listDelimiter));
        p.setModelNumber(prop("Model number", listDelimiter));
        p.setOperatingSystem(prop("Operating system", listDelimiter));
        p.setPointingDevices(prop("Pointing devices", listDelimiter));
        p.setPorts(prop("Ports", listDelimiter));
        p.setPowerSupply(prop("Power supply", listDelimiter));
        p.setSoftwareIncluded(any(prop("Software included", listDelimiter), prop("Software", listDelimiter)));
        
        p.setWebcam(any(prop("Webcam", listDelimiter), prop("Integrated camera", listDelimiter), prop("Camera", listDelimiter)));
        p.setWeight(prop("Weight", true, listDelimiter));
        p.setWeightInPounds(parseWeightInPounds(p.getWeight()));
        p.setWarranty(prop("Warranty",true, listDelimiter));
        p.setNetworking(any(prop("Wireless", listDelimiter), prop("Networking", listDelimiter), prop("Network interface", listDelimiter)));

        p.setAcAdapter(prop("AC adapter",true, listDelimiter));
        p.setAccessories(prop("Accessories",true, listDelimiter));
        p.setAdditionalBay(prop("Additional bay",true, listDelimiter));
        p.setAudio(prop("Audio",true, listDelimiter));
        p.setBluetooth(prop("Bluetooth",true, listDelimiter));
        p.setBroadbandServiceProvider(prop("Broadband service provider",true, listDelimiter));
        p.setChipset(prop("Chipset",true, listDelimiter));
        p.setExternalIOPorts(prop("External I/O Ports",true, listDelimiter));
        p.setFingerPrintReader(prop("Finger print reader",true, listDelimiter));
        p.setFlashCache(prop("Flash cache",true, listDelimiter));
        p.setHpMobileBroadband(prop("HP mobile broadband",true, listDelimiter));
        p.setLabelEnergyStar(prop("Label ENERGY STAR",true, listDelimiter));
        p.setLaplinkPcmoverSoftware(prop("Laplink PCmover Software",true, listDelimiter));
        p.setSecuritySoftware(prop("McAfee LiveSafe(TM) Security Software",true, listDelimiter));
        p.setMemorySlots(prop("Memory slots",true, listDelimiter));
        p.setMiniCard(prop("Mini card",true, listDelimiter));
        p.setMiniCardSsd(prop("Mini card SSD",true, listDelimiter));
        p.setMiscWarrantyDocumentation(prop("Misc warranty documentation",true, listDelimiter));
        p.setModem(prop("Modem",true, listDelimiter));
        p.setNearFieldCommunication(prop("Near field communication",true, listDelimiter));
        p.setOsRecoveryCd(prop("OS recovery CD",true, listDelimiter));
        p.setOfficeSoftware(prop("Office software",true, listDelimiter));
        p.setOpticalDrive(prop("Optical drive",true, listDelimiter));
        p.setOutOfBandManagement(prop("Out-of-Band management",true, listDelimiter));
        p.setPersonalization(prop("Personalization",true, listDelimiter));
        p.setPowerCord(prop("Power cord",true, listDelimiter));
        p.setProcessorFamily(prop("Processor family",true, listDelimiter));
        p.setProcessorTechnology(prop("Processor technology",true, listDelimiter));
        p.setRecoveryMediaDriver(prop("Recovery media driver",true, listDelimiter));
        p.setSecurityManagement(prop("Security management",true, listDelimiter));
        p.setSensors(prop("Sensors",true, listDelimiter));
        p.setTheftProtection(prop("Theft protection",true, listDelimiter));
        p.setWarrantyBattery(prop("Warranty battery",true, listDelimiter));
        p.setBoxContents(prop("What\u0027s in the box",true, listDelimiter));
        p.setWirelessLan(prop("Wireless LAN",true, listDelimiter));

        extractProcessorAndGraphics(p);
        
        checkParsedProps();
        
        return p;
    }


}
