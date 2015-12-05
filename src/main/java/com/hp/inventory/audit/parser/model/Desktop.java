/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

/**
 * Entity representing a Desktop
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
public class Desktop extends AbstractProduct implements ProcessorGraphicsMixin {

    private String operatingSystem;

    private String processor;

    private String graphics;

    private String opticalDrive;

    private String memory;

    private String hardDrive;

    private String wireless;

    private String networking;

    private String powerSupply;

    private String ports;

    private String officeSoftware;

    private String tvTurner;

    private String expansionSlots;

    private String externalIOPorts;

    private String energyEfficiency;

    private String color;

    private String keyboard;

    private String pointingDevices;

    private String popularSoftware;

    private String photographySoftware;

    private String softwareUpgrades;

    private String dimensions;

    private String audio;

    private String memorySlots;

    private BigDecimal dimensionWidthInInches;

    private BigDecimal dimensionDepthInInches;

    private BigDecimal dimensionHeightInInches;

    private String weight;

    private BigDecimal weightInPounds;

    private String warranty;

    private String soundCard;

    private String softwareIncluded;

    private String modelNumber;

    private String accessories;

	private String additionalApplicationSoftware;

	private String additionalNetworkingOptions;

	private String battery;

	private String batteryLife;

	private String cableOptionKits;

	private String chassis;
	
	private String chinaCccCompliance;
	
	private String controller;
	
	private String display;
	
	private String displayCable;
	
	private String energyStar;
	
	private String eightInternalStorage;
	
	private String externalOpticalDrive;
	
	private String fempCompliance;
	
	private String fifthInternalStorage;
	
	private String flashCache;
	
	private String fourthInternalStorage;
	
	private String graphicsConnectors;
	
	private String highPerformanceGpuComputing;
	
	private String integratedCamera;
	
	private String intelSrtDiskCacheModules;
	
	private String intelSmartResponseTechnology;
	
	private String internalOsLoadStorageOptions;
	
	private String internalPcieStorage;
	
	private String internalStorageOptions;
	
	private String lanTransceivers;
	
	private String label;
	
	private String maximumMemory;
	
	private String securitySoftware;
	
	private String mediaReader;
	
	private String memoryCardDevice;
	
	private String processorTechnology;
	
	private String realTimeDataBackup;
	
	private String secondDisplayCable;
	
	private String secondGraphicsCard;
	
	private String secondInternalStorage;
	
	private String secondaryInternalPcieStorage;
	
	private String secondaryOpticalDrive;
	
	private String secondaryProcessor;
	
	private String security;
	
	private String securityEncryption;
	
	private String sixthInternalStorage;
	
	private String seventhInternalStorage;
	
	private String softwareBundles;

	private String speakers;
	
	private String stand;
	
	private String systemRecoverySolutions;
	
	private String tvTuner;
	
	private String technical;
	
	private String technicalAV;
	
	private String thirdGraphicsCard;
	
	private String thirdInternalStorage;
	
	private String webcam;
	
	
    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getDimensionDepthInInches() {
        return dimensionDepthInInches;
    }

    public void setDimensionDepthInInches(BigDecimal dimensionDepthInInches) {
        this.dimensionDepthInInches = dimensionDepthInInches;
    }

    public BigDecimal getDimensionHeightInInches() {
        return dimensionHeightInInches;
    }

    public void setDimensionHeightInInches(BigDecimal dimensionHeightInInches) {
        this.dimensionHeightInInches = dimensionHeightInInches;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public BigDecimal getDimensionWidthInInches() {
        return dimensionWidthInInches;
    }

    public void setDimensionWidthInInches(BigDecimal dimensionWidthInInches) {
        this.dimensionWidthInInches = dimensionWidthInInches;
    }

    public String getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(String energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }

    public String getExpansionSlots() {
        return expansionSlots;
    }

    public void setExpansionSlots(String expansionSlots) {
        this.expansionSlots = expansionSlots;
    }

    public String getExternalIOPorts() {
        return externalIOPorts;
    }

    public void setExternalIOPorts(String externalIOPorts) {
        this.externalIOPorts = externalIOPorts;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getMemorySlots() {
        return memorySlots;
    }

    public void setMemorySlots(String memorySlots) {
        this.memorySlots = memorySlots;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getNetworking() {
        return networking;
    }

    public void setNetworking(String networking) {
        this.networking = networking;
    }

    public String getOfficeSoftware() {
        return officeSoftware;
    }

    public void setOfficeSoftware(String officeSoftware) {
        this.officeSoftware = officeSoftware;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOpticalDrive() {
        return opticalDrive;
    }

    public void setOpticalDrive(String opticalDrive) {
        this.opticalDrive = opticalDrive;
    }

    public String getPhotographySoftware() {
        return photographySoftware;
    }

    public void setPhotographySoftware(String photographySoftware) {
        this.photographySoftware = photographySoftware;
    }

    public String getPopularSoftware() {
        return popularSoftware;
    }

    public void setPopularSoftware(String popularSoftware) {
        this.popularSoftware = popularSoftware;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getSoftwareIncluded() {
        return softwareIncluded;
    }

    public void setSoftwareIncluded(String softwareIncluded) {
        this.softwareIncluded = softwareIncluded;
    }

    public String getSoftwareUpgrades() {
        return softwareUpgrades;
    }

    public void setSoftwareUpgrades(String softwareUpgrades) {
        this.softwareUpgrades = softwareUpgrades;
    }

    public String getSoundCard() {
        return soundCard;
    }

    public void setSoundCard(String soundCard) {
        this.soundCard = soundCard;
    }

    public String getTvTurner() {
        return tvTurner;
    }

    public void setTvTurner(String tvTurner) {
        this.tvTurner = tvTurner;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public BigDecimal getWeightInPounds() {
        return weightInPounds;
    }

    public void setWeightInPounds(BigDecimal weightInPounds) {
        this.weightInPounds = weightInPounds;
    }

    public String getWireless() {
        return wireless;
    }

    public void setWireless(String wireless) {
        this.wireless = wireless;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getPointingDevices() {
        return pointingDevices;
    }

    public void setPointingDevices(String pointingDevices) {
        this.pointingDevices = pointingDevices;
    }

	public String getAdditionalApplicationSoftware() {
		return additionalApplicationSoftware;
	}

	public void setAdditionalApplicationSoftware(String additionalApplicationSoftware) {
		this.additionalApplicationSoftware = additionalApplicationSoftware;
	}

	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}

	public String getAdditionalNetworkingOptions() {
		return additionalNetworkingOptions;
	}

	public void setAdditionalNetworkingOptions(String additionalNetworkingOptions) {
		this.additionalNetworkingOptions = additionalNetworkingOptions;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getBatteryLife() {
		return batteryLife;
	}

	public void setBatteryLife(String batteryLife) {
		this.batteryLife = batteryLife;
	}

	public String getCableOptionKits() {
		return cableOptionKits;
	}

	public void setCableOptionKits(String cableOptionKits) {
		this.cableOptionKits = cableOptionKits;
	}

	public String getChassis() {
		return chassis;
	}

	public void setChassis(String chassis) {
		this.chassis = chassis;
	}

	public String getChinaCccCompliance() {
		return chinaCccCompliance;
	}

	public void setChinaCccCompliance(String chinaCccCompliance) {
		this.chinaCccCompliance = chinaCccCompliance;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getDisplayCable() {
		return displayCable;
	}

	public void setDisplayCable(String displayCable) {
		this.displayCable = displayCable;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getEnergyStar() {
		return energyStar;
	}

	public void setEnergyStar(String energyStar) {
		this.energyStar = energyStar;
	}

	public String getEightInternalStorage() {
		return eightInternalStorage;
	}

	public void setEightInternalStorage(String eightInternalStorage) {
		this.eightInternalStorage = eightInternalStorage;
	}

	public String getExternalOpticalDrive() {
		return externalOpticalDrive;
	}

	public void setExternalOpticalDrive(String externalOpticalDrive) {
		this.externalOpticalDrive = externalOpticalDrive;
	}

	public String getFempCompliance() {
		return fempCompliance;
	}

	public void setFempCompliance(String fempCompliance) {
		this.fempCompliance = fempCompliance;
	}

	public String getFifthInternalStorage() {
		return fifthInternalStorage;
	}

	public void setFifthInternalStorage(String fifthInternalStorage) {
		this.fifthInternalStorage = fifthInternalStorage;
	}

	public String getFlashCache() {
		return flashCache;
	}

	public void setFlashCache(String flashCache) {
		this.flashCache = flashCache;
	}

	public String getFourthInternalStorage() {
		return fourthInternalStorage;
	}

	public void setFourthInternalStorage(String fourthInternalStorage) {
		this.fourthInternalStorage = fourthInternalStorage;
	}

	public String getGraphicsConnectors() {
		return graphicsConnectors;
	}

	public void setGraphicsConnectors(String graphicsConnectors) {
		this.graphicsConnectors = graphicsConnectors;
	}

	public String getHighPerformanceGpuComputing() {
		return highPerformanceGpuComputing;
	}

	public void setHighPerformanceGpuComputing(String highPerformanceGpuComputing) {
		this.highPerformanceGpuComputing = highPerformanceGpuComputing;
	}

	public String getIntegratedCamera() {
		return integratedCamera;
	}

	public void setIntegratedCamera(String integratedCamera) {
		this.integratedCamera = integratedCamera;
	}

	public String getIntelSrtDiskCacheModules() {
		return intelSrtDiskCacheModules;
	}

	public void setIntelSrtDiskCacheModules(String intelSrtDiskCacheModules) {
		this.intelSrtDiskCacheModules = intelSrtDiskCacheModules;
	}

	public String getIntelSmartResponseTechnology() {
		return intelSmartResponseTechnology;
	}

	public void setIntelSmartResponseTechnology(String intelSmartResponseTechnology) {
		this.intelSmartResponseTechnology = intelSmartResponseTechnology;
	}

	public String getInternalOsLoadStorageOptions() {
		return internalOsLoadStorageOptions;
	}

	public void setInternalOsLoadStorageOptions(String internalOsLoadStorageOptions) {
		this.internalOsLoadStorageOptions = internalOsLoadStorageOptions;
	}

	public String getInternalPcieStorage() {
		return internalPcieStorage;
	}

	public void setInternalPcieStorage(String internalPcieStorage) {
		this.internalPcieStorage = internalPcieStorage;
	}

	public String getInternalStorageOptions() {
		return internalStorageOptions;
	}

	public void setInternalStorageOptions(String internalStorageOptions) {
		this.internalStorageOptions = internalStorageOptions;
	}

	public String getLanTransceivers() {
		return lanTransceivers;
	}

	public void setLanTransceivers(String lanTransceivers) {
		this.lanTransceivers = lanTransceivers;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMaximumMemory() {
		return maximumMemory;
	}

	public void setMaximumMemory(String maximumMemory) {
		this.maximumMemory = maximumMemory;
	}

	public String getSecuritySoftware() {
		return securitySoftware;
	}

	public void setSecuritySoftware(String securitySoftware) {
		this.securitySoftware = securitySoftware;
	}

	public String getMediaReader() {
		return mediaReader;
	}

	public void setMediaReader(String mediaReader) {
		this.mediaReader = mediaReader;
	}

	public String getMemoryCardDevice() {
		return memoryCardDevice;
	}

	public void setMemoryCardDevice(String memoryCardDevice) {
		this.memoryCardDevice = memoryCardDevice;
	}

	public String getProcessorTechnology() {
		return processorTechnology;
	}

	public void setProcessorTechnology(String processorTechnology) {
		this.processorTechnology = processorTechnology;
	}

	public String getRealTimeDataBackup() {
		return realTimeDataBackup;
	}

	public void setRealTimeDataBackup(String realTimeDataBackup) {
		this.realTimeDataBackup = realTimeDataBackup;
	}

	public String getSecondDisplayCable() {
		return secondDisplayCable;
	}

	public void setSecondDisplayCable(String secondDisplayCable) {
		this.secondDisplayCable = secondDisplayCable;
	}

	public String getSecondInternalStorage() {
		return secondInternalStorage;
	}

	public void setSecondInternalStorage(String secondInternalStorage) {
		this.secondInternalStorage = secondInternalStorage;
	}

	public String getSecondaryInternalPcieStorage() {
		return secondaryInternalPcieStorage;
	}

	public void setSecondaryInternalPcieStorage(String secondaryInternalPcieStorage) {
		this.secondaryInternalPcieStorage = secondaryInternalPcieStorage;
	}

	public String getSecondaryOpticalDrive() {
		return secondaryOpticalDrive;
	}

	public void setSecondaryOpticalDrive(String secondaryOpticalDrive) {
		this.secondaryOpticalDrive = secondaryOpticalDrive;
	}

	public String getSecondaryProcessor() {
		return secondaryProcessor;
	}

	public void setSecondaryProcessor(String secondaryProcessor) {
		this.secondaryProcessor = secondaryProcessor;
	}

	public String getSecondGraphicsCard() {
		return secondGraphicsCard;
	}

	public void setSecondGraphicsCard(String secondGraphicsCard) {
		this.secondGraphicsCard = secondGraphicsCard;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getSecurityEncryption() {
		return securityEncryption;
	}

	public void setSecurityEncryption(String securityEncryption) {
		this.securityEncryption = securityEncryption;
	}

	public String getSixthInternalStorage() {
		return sixthInternalStorage;
	}

	public void setSixthInternalStorage(String sixthInternalStorage) {
		this.sixthInternalStorage = sixthInternalStorage;
	}

	public String getSeventhInternalStorage() {
		return seventhInternalStorage;
	}

	public void setSeventhInternalStorage(String seventhInternalStorage) {
		this.seventhInternalStorage = seventhInternalStorage;
	}

	public String getSoftwareBundles() {
		return softwareBundles;
	}

	public void setSoftwareBundles(String softwareBundles) {
		this.softwareBundles = softwareBundles;
	}

	public String getSpeakers() {
		return speakers;
	}

	public void setSpeakers(String speakers) {
		this.speakers = speakers;
	}

	public String getStand() {
		return stand;
	}

	public void setStand(String stand) {
		this.stand = stand;
	}

	public String getSystemRecoverySolutions() {
		return systemRecoverySolutions;
	}

	public void setSystemRecoverySolutions(String systemRecoverySolutions) {
		this.systemRecoverySolutions = systemRecoverySolutions;
	}

	public String getTvTuner() {
		return tvTuner;
	}

	public void setTvTuner(String tvTuner) {
		this.tvTuner = tvTuner;
	}

	public String getTechnical() {
		return technical;
	}

	public void setTechnical(String technical) {
		this.technical = technical;
	}

	public String getTechnicalAV() {
		return technicalAV;
	}

	public void setTechnicalAV(String technicalAV) {
		this.technicalAV = technicalAV;
	}

	public String getThirdGraphicsCard() {
		return thirdGraphicsCard;
	}

	public void setThirdGraphicsCard(String thirdGraphicsCard) {
		this.thirdGraphicsCard = thirdGraphicsCard;
	}

	public String getThirdInternalStorage() {
		return thirdInternalStorage;
	}

	public void setThirdInternalStorage(String thirdInternalStorage) {
		this.thirdInternalStorage = thirdInternalStorage;
	}

	public String getWebcam() {
		return webcam;
	}

	public void setWebcam(String webcam) {
		this.webcam = webcam;
	}

	@Override
	public void initNewEntity() {
		// currently no initializations
	}

	@Override
	public void upgradeEntityFrom(IProduct from) throws Exception {
		IProduct.updateEntity(from, this);
	}

}
