package com.hp.inventory.audit.parser.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Entity representing a Laptop
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
public class Laptop extends AbstractProduct implements  ProcessorGraphicsMixin {

    private String operatingSystem;

    private String processor;

    private String graphics;

    private String display;

    private String memory;

    private String hardDrive;

    private String networking;

    private String powerSupply;

    private String battery;

    private String batteryLife;

    private BigDecimal maxBatteryLifeInHours;

    private String ports;

    private String expansionSlots;

    private String energyEfficiency;

    private String webcam;

    private String color;

    private String poitingDevices;

    private String keyboard;

    private String dimensions;

    private BigDecimal dimensionWidthInInches;

    private BigDecimal dimensionDepthInInches;

    private BigDecimal dimensionHeightInInches;

    private String weight;

    private BigDecimal weightInPounds;

    private String warranty;

    private String softwareIncluded;

    private String modelNumber;
    
    private String acAdapter;
    private String accessories;
    private String additionalBay;
    private String audio;
    private String bluetooth;
    private String broadbandServiceProvider;
    private String chipset;
    private String externalIOPorts;
    private String fingerPrintReader;
    private String flashCache;
    private String hpMobileBroadband;
    private String labelEnergyStar;
    private String laplinkPcmoverSoftware;
    private String securitySoftware;
    private String memorySlots;
    private String miniCard;
    private String miniCardSsd;
    private String miscWarrantyDocumentation;
    private String modem;
    private String nearFieldCommunication;
    private String osRecoveryCd;
    private String officeSoftware;
    private String opticalDrive;
    private String outOfBandManagement;
    private String personalization;
    private String powerCord;
    private String processorFamily;
    private String processorTechnology;
    private String recoveryMediaDriver;
    private String securityManagement;
    private String sensors;
    private String theftProtection;
    private String warrantyBattery;
    private String boxContents;
    private String wirelessLan;
    

    public String getAcAdapter() {
		return acAdapter;
	}

	public void setAcAdapter(String acAdapter) {
		this.acAdapter = acAdapter;
	}

	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}

	public String getAdditionalBay() {
		return additionalBay;
	}

	public void setAdditionalBay(String additionalBay) {
		this.additionalBay = additionalBay;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}

	public String getBroadbandServiceProvider() {
		return broadbandServiceProvider;
	}

	public void setBroadbandServiceProvider(String broadbandServiceProvider) {
		this.broadbandServiceProvider = broadbandServiceProvider;
	}

	public String getChipset() {
		return chipset;
	}

	public void setChipset(String chipset) {
		this.chipset = chipset;
	}

	public String getExternalIOPorts() {
		return externalIOPorts;
	}

	public void setExternalIOPorts(String externalIOPorts) {
		this.externalIOPorts = externalIOPorts;
	}

	public String getFingerPrintReader() {
		return fingerPrintReader;
	}

	public void setFingerPrintReader(String fingerPrintReader) {
		this.fingerPrintReader = fingerPrintReader;
	}

	public String getFlashCache() {
		return flashCache;
	}

	public void setFlashCache(String flashCache) {
		this.flashCache = flashCache;
	}

	public String getHpMobileBroadband() {
		return hpMobileBroadband;
	}

	public void setHpMobileBroadband(String hpMobileBroadband) {
		this.hpMobileBroadband = hpMobileBroadband;
	}

	public String getLabelEnergyStar() {
		return labelEnergyStar;
	}

	public void setLabelEnergyStar(String labelEnergyStar) {
		this.labelEnergyStar = labelEnergyStar;
	}

	public String getLaplinkPcmoverSoftware() {
		return laplinkPcmoverSoftware;
	}

	public void setLaplinkPcmoverSoftware(String laplinkPcmoverSoftware) {
		this.laplinkPcmoverSoftware = laplinkPcmoverSoftware;
	}

	public String getSecuritySoftware() {
		return securitySoftware;
	}

	public void setSecuritySoftware(String securitySoftware) {
		this.securitySoftware = securitySoftware;
	}

	public String getMemorySlots() {
		return memorySlots;
	}

	public void setMemorySlots(String memorySlots) {
		this.memorySlots = memorySlots;
	}

	public String getMiniCard() {
		return miniCard;
	}

	public void setMiniCard(String miniCard) {
		this.miniCard = miniCard;
	}

	public String getMiniCardSsd() {
		return miniCardSsd;
	}

	public void setMiniCardSsd(String miniCardSsd) {
		this.miniCardSsd = miniCardSsd;
	}

	public String getMiscWarrantyDocumentation() {
		return miscWarrantyDocumentation;
	}

	public void setMiscWarrantyDocumentation(String miscWarrantyDocumentation) {
		this.miscWarrantyDocumentation = miscWarrantyDocumentation;
	}

	public String getModem() {
		return modem;
	}

	public void setModem(String modem) {
		this.modem = modem;
	}

	public String getNearFieldCommunication() {
		return nearFieldCommunication;
	}

	public void setNearFieldCommunication(String nearFieldCommunication) {
		this.nearFieldCommunication = nearFieldCommunication;
	}

	public String getOsRecoveryCd() {
		return osRecoveryCd;
	}

	public void setOsRecoveryCd(String osRecoveryCd) {
		this.osRecoveryCd = osRecoveryCd;
	}

	public String getOfficeSoftware() {
		return officeSoftware;
	}

	public void setOfficeSoftware(String officeSoftware) {
		this.officeSoftware = officeSoftware;
	}

	public String getOpticalDrive() {
		return opticalDrive;
	}

	public void setOpticalDrive(String opticalDrive) {
		this.opticalDrive = opticalDrive;
	}

	public String getOutOfBandManagement() {
		return outOfBandManagement;
	}

	public void setOutOfBandManagement(String outOfBandManagement) {
		this.outOfBandManagement = outOfBandManagement;
	}

	public String getPersonalization() {
		return personalization;
	}

	public void setPersonalization(String personalization) {
		this.personalization = personalization;
	}

	public String getPowerCord() {
		return powerCord;
	}

	public void setPowerCord(String powerCord) {
		this.powerCord = powerCord;
	}

	public String getProcessorFamily() {
		return processorFamily;
	}

	public void setProcessorFamily(String processorFamily) {
		this.processorFamily = processorFamily;
	}

	public String getProcessorTechnology() {
		return processorTechnology;
	}

	public void setProcessorTechnology(String processorTechnology) {
		this.processorTechnology = processorTechnology;
	}

	public String getRecoveryMediaDriver() {
		return recoveryMediaDriver;
	}

	public void setRecoveryMediaDriver(String recoveryMediaDriver) {
		this.recoveryMediaDriver = recoveryMediaDriver;
	}

	public String getSecurityManagement() {
		return securityManagement;
	}

	public void setSecurityManagement(String securityManagement) {
		this.securityManagement = securityManagement;
	}

	public String getSensors() {
		return sensors;
	}

	public void setSensors(String sensors) {
		this.sensors = sensors;
	}

	public String getTheftProtection() {
		return theftProtection;
	}

	public void setTheftProtection(String theftProtection) {
		this.theftProtection = theftProtection;
	}

	public String getWarrantyBattery() {
		return warrantyBattery;
	}

	public void setWarrantyBattery(String warrantyBattery) {
		this.warrantyBattery = warrantyBattery;
	}

	public String getBoxContents() {
		return boxContents;
	}

	public void setBoxContents(String boxContents) {
		this.boxContents = boxContents;
	}

	public String getWirelessLan() {
		return wirelessLan;
	}

	public void setWirelessLan(String wirelessLan) {
		this.wirelessLan = wirelessLan;
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

    public BigDecimal getDimensionWidthInInches() {
        return dimensionWidthInInches;
    }

    public void setDimensionWidthInInches(BigDecimal dimensionWidthInInches) {
        this.dimensionWidthInInches = dimensionWidthInInches;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
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

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public BigDecimal getMaxBatteryLifeInHours() {
        return maxBatteryLifeInHours;
    }

    public void setMaxBatteryLifeInHours(BigDecimal maxBatteryLifeInHours) {
        this.maxBatteryLifeInHours = maxBatteryLifeInHours;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getPoitingDevices() {
        return poitingDevices;
    }

    public void setPoitingDevices(String poitingDevices) {
        this.poitingDevices = poitingDevices;
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

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getWebcam() {
        return webcam;
    }

    public void setWebcam(String webcam) {
        this.webcam = webcam;
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

    public String getNetworking() {
        return networking;
    }

    public void setNetworking(String wireless) {
        this.networking = wireless;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
    
    @Override
	public void initNewEntity() {
		// currently no initializations
	}

	@Override
	public void upgradeEntityFrom(IProduct from) throws Exception {
		this.updateFrom(from);
	}
}
