package com.hp.inventory.audit.parser.parsers;

import java.math.BigDecimal;
import java.util.Date;

import com.hp.inventory.audit.parser.model.Desktop;
import com.hp.inventory.audit.parser.model.IProduct;

/**
 * Document parser for "PDP" type Desktop pages
 *
 * @author TCDEVELOPER
 * @version 1.0.3
 */
public class DesktopParser extends DocumentParser {

    /**
     * @throws Exception 
     * @inheritDoc
     */
    @Override
    protected IProduct extract() throws Exception {

    	Desktop p = new Desktop();
        
        setParsingErrorsReceiver(p);
        
        extractCommonProps(p);
        
        p.setColor(prop("Color", listDelimiter));
        
        p.setDimensions(prop("Dimensions", listDelimiter));
        BigDecimal[] wdh = parseDimensions(p.getDimensions());
        if(wdh != null) {
            p.setDimensionWidthInInches(wdh[0]);
            p.setDimensionDepthInInches(wdh[1]);
            p.setDimensionHeightInInches(wdh[2]);
        }
        
        p.setEnergyEfficiency(prop("Energy efficiency", listDelimiter));
        p.setExpansionSlots(prop("Expansion slots", listDelimiter));
        p.setHardDrive(any(prop("Hard drive", listDelimiter), prop("Internal drive", listDelimiter), prop("Internal storage", listDelimiter)));
        p.setHpDataSheet(propLink("HP Data Sheet"));
        
        p.setMemory(prop("Memory", listDelimiter));
        p.setModelNumber(prop("Model number", listDelimiter));
        p.setOperatingSystem(prop("Operating system", listDelimiter));
        p.setPorts(prop("Ports", listDelimiter));
        p.setPowerSupply(prop("Power supply", listDelimiter));
        
        p.setSoftwareIncluded(prop("Software included", listDelimiter));
        
        p.setWeight(prop("Weight", listDelimiter));
        p.setWeightInPounds(parseWeightInPounds(p.getWeight()));
        p.setWarranty(prop("Warranty", listDelimiter));
        p.setWireless(prop("Wireless", listDelimiter));
        p.setKeyboard(any(prop("Keyboard", listDelimiter), prop("Keyboard and mouse", listDelimiter)));
        p.setPointingDevices(any(prop("Pointing devices", listDelimiter), prop("Mouse", listDelimiter)));
        p.setOpticalDrive(prop("Optical drive", listDelimiter));
        p.setMemorySlots(prop("Memory slots", listDelimiter));
        p.setNetworking(any(
                prop("Networking", listDelimiter),
                prop("Network card", listDelimiter),
                prop("Network interface", listDelimiter),
                prop("Integrated network", listDelimiter),
                prop("LAN", listDelimiter)));
        p.setOfficeSoftware(prop("Office software", listDelimiter));
        p.setPhotographySoftware(prop("Photography software", listDelimiter));
        p.setPopularSoftware(prop("Popular, useful and fun software", listDelimiter));
        p.setAudio(prop("Audio", listDelimiter));
        p.setSoftwareUpgrades(prop("Software upgrades", listDelimiter));
        p.setSoundCard(prop("Sound card", listDelimiter));
        p.setTvTurner(prop("TV turner", listDelimiter));
        p.setAccessories(prop("Accessories", listDelimiter));
        p.setAdditionalApplicationSoftware(prop("Additional application software", true, listDelimiter));
        p.setAdditionalNetworkingOptions(prop("Additional networking options", true, listDelimiter));
        p.setBattery(prop("Battery", true, listDelimiter));
        p.setBatteryLife(prop("Battery life", true, listDelimiter));
        p.setCableOptionKits(prop("Cable option kits", true, listDelimiter));
        p.setChassis(prop("Chassis", true, listDelimiter));
        p.setChinaCccCompliance(prop("China CCC compliance", true, listDelimiter));
        p.setController(prop("Controller", true, listDelimiter));
        p.setDisplay(prop("Display", true, listDelimiter));
        p.setDisplayCable(prop("Display cable", true, listDelimiter));
        p.setEnergyStar(prop("ENERGY STAR", true, listDelimiter));
        p.setEightInternalStorage(prop("Eight internal storage", true, listDelimiter));
        p.setExternalIOPorts(prop("External I/O Ports", true, listDelimiter));
        p.setExternalOpticalDrive(prop("External optical drive", true, listDelimiter));
        p.setFempCompliance(prop("FEMP compliance", true, listDelimiter));
        p.setFifthInternalStorage(prop("Fifth internal storage", true, listDelimiter));
        p.setFlashCache(prop("Flash cache", true, listDelimiter));
        p.setFourthInternalStorage(prop("Fourth internal storage", true, listDelimiter));
        p.setGraphicsConnectors(prop("Graphics connectors", true, listDelimiter));
        p.setHighPerformanceGpuComputing(prop("High performance GPU computing", true, listDelimiter));
        p.setIntegratedCamera(prop("Integrated camera", true, listDelimiter));
        p.setIntelSrtDiskCacheModules(prop("Intel SRT disk cache modules", true, listDelimiter));
        p.setIntelSmartResponseTechnology(prop("Intel Smart Response Technology", true, listDelimiter));
        p.setInternalOsLoadStorageOptions(prop("Internal OS load storage options", true, listDelimiter));
        p.setInternalPcieStorage(prop("Internal PCIe storage", true, listDelimiter));
        p.setInternalStorageOptions(prop("Internal storage options", true, listDelimiter));
        p.setLanTransceivers(prop("LAN Transceivers", true, listDelimiter));
        p.setLabel(prop("Label", true, listDelimiter));
        p.setMaximumMemory(prop("Maximum memory", true, listDelimiter));
        p.setSecuritySoftware(prop("McAfee LiveSafe(TM) Security Software", true, listDelimiter));
        p.setMediaReader(prop("Media reader", true, listDelimiter));
        p.setMemoryCardDevice(prop("Memory card device", true, listDelimiter));
        p.setProcessorTechnology(prop("Processor technology", true, listDelimiter));
        p.setRealTimeDataBackup(prop("Real-time data backup", true, listDelimiter));
        p.setSecondDisplayCable(prop("Second display cable", true, listDelimiter));
        p.setSecondGraphicsCard(prop("Second graphics card", true, listDelimiter));
        p.setSecondaryOpticalDrive(prop("Secondary optical drive", true, listDelimiter));
        p.setSecondInternalStorage(prop("Second internal storage", true, listDelimiter));
        p.setSecondaryProcessor(prop("Secondary processor", true, listDelimiter));
        p.setSecondaryInternalPcieStorage(prop("Secondary internal PCIe storage", true, listDelimiter));
        p.setSecurity(prop("Security", true, listDelimiter));
        p.setSecurityEncryption(prop("Security encryption", true, listDelimiter));
        p.setSixthInternalStorage(prop("Sixth internal storage", true, listDelimiter));
        p.setSeventhInternalStorage(prop("Seventh iternal storage", true, listDelimiter));
        p.setSoftwareBundles(prop("Software bundles", true, listDelimiter));
        p.setSpeakers(prop("Speakers", true, listDelimiter));
        p.setStand(prop("Stand", true, listDelimiter));
        p.setSystemRecoverySolutions(prop("System recovery solutions", true, listDelimiter));
        p.setTvTuner(prop("TV tuner", true, listDelimiter));
        p.setTechnical(prop("Technical", true, listDelimiter));
        p.setTechnicalAV(prop("Technical AV", true, listDelimiter));
        p.setThirdGraphicsCard(prop("Third graphics card", true, listDelimiter));
        p.setThirdInternalStorage(prop("Third internal storage", true, listDelimiter));
        p.setWebcam(prop("Webcam", true, listDelimiter));

        extractProcessorAndGraphics(p);

        checkParsedProps();
        
        return p;
    }


}
