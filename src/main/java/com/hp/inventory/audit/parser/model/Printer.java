/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
public class Printer extends AbstractProduct {

    private String functions;

    private String power;

    private String powerConsumption;

    private String energyEfficiency;

    private String operatingTemperatureRange;

    private String operatingHumidityRange;

    private String dimensions;

    private BigDecimal dimensionWidthInInches;

    private BigDecimal dimensionDepthInInches;

    private BigDecimal dimensionHeightInInches;

    private String dimensionsMax;

    private BigDecimal dimensionWidthInInchesMax;

    private BigDecimal dimensionDepthInInchesMax;

    private BigDecimal dimensionHeightInInchesMax;

    private String weight;

    private BigDecimal weightInPounds;

    private String packageWeight;

    private BigDecimal packageWeightInPounds;

    private String whatsInTheBox;

    private String cableIncluded;

    private String warranty;

    private String postWarranty;

    private String softwareIncluded;

    private String fcc;

    private String printerPageYield;

    private String resolutionBlack;

    private String resolutionColor;

    private String monthlyDutyCycle;

    private String printSpeedBlackISO;

    private String recommMonthlyPageVolume;

    private Integer recommMonthlyPageVolumeMin;

    private Integer recommMonthlyPageVolumeMax;

    private String printTechnology;

    private String display;

    private String processorSpeed;

    private String numberPrintCartridges;

    private String replacementCartridges;

    private String compatibleInkTypes;

    private String printLanguages;

    private String automaticPaperSensor;

    private String printSpeedColorISO;

    private Integer paperTraysStd;

    private Integer paperTraysMax;

    private String mobilePrintingCapability;

    private String connectivityStd;

    private String connectivityOptional;

    private String networkReady;

    private String securityManagement;

    private String printSpeedBlackDraft;

    private String minimumSystemRequirements;

    private String compatibleOperatingSystems;

    private String memoryStd;

    private String memoryMax;

    private String printerManagement;

    private String printSpeedColorDraft;

    private String supportedNetworkProtocols;

    private String hardDisk;

    private String paperHandlingInputStd;

    private String paperHandlingOutputStd;

    private String duplexPrinting;

    private String envelopeInputCapacity;

    private String borderlessPrinting;

    private String mediaSizesSupported;

    private String mediaSizesCustom;

    private String mediaTypes;

    private String recommendedMediaWeight;

    private String supportedMediaWeight;

    private String mediaWeightsByPaperPath;

    
    private String adfCapacity;
    private String autoDocumentFeeder;
    private String batteryRechargeTime;
    private String bitDepth;
    private String broadcastLocations;
    private String browserSupported;
    private String colorStability;
    private String connectivity;
    private String controlPanel;
    private String maximumCopies;
    private String copyReduceEnlargeSettings;
    private String copyResolutionBlackText;
    private String copyResolutionColourTextGraphics;
    private String copySpeedBlackDraft;
    private String copySpeedBlackNormal;
    private String copySpeedColorDraft;
    private String copySpeedColorNormal;
    private String digitalSendFileFormats;
    private String digitalSendingFeatures;
    private String duplexAdfScanning;
    private String energyStar;
    private String embeddedWebServer;
    private String faxMemory;
    private String faxResolution;
    private String faxTransmissionSpeed;
    private String faxing;
    private String finishedOutputHandling;
    private String firstPageOutBlack;
    private String firstPageOutColor;
    private String guaranteedMinimumLineWidth;
    private String inputType;
    private String lineAccuracy;
    private String maximumDocumentScanSize;
    private String maximumOpticalDensityBlack;
    private String mptUsColorImageBestModeGlossy;
    private String mptUsColorImageDraftModeCoated;
    private String mptUsColorImageNormalModeCoated;
    private String mptUsColorImageNormalModeGlossy;
    private String mptBWLineDrawingDraftModePlain;
    private String mptColorLineDrawingDraftModePlain;
    private String mptLineDrawingEconomodePlain;
    private String mediaThickness;
    private String memoryCardCompatibility;
    private String nonPrintableArea;
    private String ports;
    private String printRepeatability;
    private String printSpeedMaximum;
    private String resolution;
    private String resolutionTechnology;
    private String rollExternalDiameter;
    private String rollMaximumOutput;
    private String scanFileFormat;
    private String scanResolutionHardware;
    private String scanResolutionOptical;
    private String scanSizeMaximum;
    private String scanSizeFlatbedMaximum;
    private String scanSpeedMaximum;
    private String scannableMediaTypes;
    private String scannerType;
    private String speedDialsMaximumNumber;
    private String usColorLineDrawingsDraftPlain;
    private String type;
    
    public String getAdfCapacity() {
		return adfCapacity;
	}

	public void setAdfCapacity(String adfCapacity) {
		this.adfCapacity = adfCapacity;
	}

	public String getAutoDocumentFeeder() {
		return autoDocumentFeeder;
	}

	public void setAutoDocumentFeeder(String autoDocumentFeeder) {
		this.autoDocumentFeeder = autoDocumentFeeder;
	}

	public String getBatteryRechargeTime() {
		return batteryRechargeTime;
	}

	public void setBatteryRechargeTime(String batteryRechargeTime) {
		this.batteryRechargeTime = batteryRechargeTime;
	}

	public String getBitDepth() {
		return bitDepth;
	}

	public void setBitDepth(String bitDepth) {
		this.bitDepth = bitDepth;
	}

	public String getBroadcastLocations() {
		return broadcastLocations;
	}

	public void setBroadcastLocations(String broadcastLocations) {
		this.broadcastLocations = broadcastLocations;
	}

	public String getBrowserSupported() {
		return browserSupported;
	}

	public void setBrowserSupported(String browserSupported) {
		this.browserSupported = browserSupported;
	}

	public String getColorStability() {
		return colorStability;
	}

	public void setColorStability(String colorStability) {
		this.colorStability = colorStability;
	}

	public String getConnectivity() {
		return connectivity;
	}

	public void setConnectivity(String connectivity) {
		this.connectivity = connectivity;
	}

	public String getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(String controlPanel) {
		this.controlPanel = controlPanel;
	}

	public String getMaximumCopies() {
		return maximumCopies;
	}

	public void setMaximumCopies(String maximumCopies) {
		this.maximumCopies = maximumCopies;
	}

	public String getCopyReduceEnlargeSettings() {
		return copyReduceEnlargeSettings;
	}

	public void setCopyReduceEnlargeSettings(String copyReduceEnlargeSettings) {
		this.copyReduceEnlargeSettings = copyReduceEnlargeSettings;
	}

	public String getCopyResolutionBlackText() {
		return copyResolutionBlackText;
	}

	public void setCopyResolutionBlackText(String copyResolutionBlackText) {
		this.copyResolutionBlackText = copyResolutionBlackText;
	}

	public String getCopyResolutionColourTextGraphics() {
		return copyResolutionColourTextGraphics;
	}

	public void setCopyResolutionColourTextGraphics(String copyResolutionColourTextGraphics) {
		this.copyResolutionColourTextGraphics = copyResolutionColourTextGraphics;
	}

	public String getCopySpeedBlackDraft() {
		return copySpeedBlackDraft;
	}

	public void setCopySpeedBlackDraft(String copySpeedBlackDraft) {
		this.copySpeedBlackDraft = copySpeedBlackDraft;
	}

	public String getCopySpeedBlackNormal() {
		return copySpeedBlackNormal;
	}

	public void setCopySpeedBlackNormal(String copySpeedBlackNormal) {
		this.copySpeedBlackNormal = copySpeedBlackNormal;
	}

	public String getCopySpeedColorDraft() {
		return copySpeedColorDraft;
	}

	public void setCopySpeedColorDraft(String copySpeedColorDraft) {
		this.copySpeedColorDraft = copySpeedColorDraft;
	}

	public String getCopySpeedColorNormal() {
		return copySpeedColorNormal;
	}

	public void setCopySpeedColorNormal(String copySpeedColorNormal) {
		this.copySpeedColorNormal = copySpeedColorNormal;
	}

	public String getDigitalSendFileFormats() {
		return digitalSendFileFormats;
	}

	public void setDigitalSendFileFormats(String digitalSendFileFormats) {
		this.digitalSendFileFormats = digitalSendFileFormats;
	}

	public String getDigitalSendingFeatures() {
		return digitalSendingFeatures;
	}

	public void setDigitalSendingFeatures(String digitalSendingFeatures) {
		this.digitalSendingFeatures = digitalSendingFeatures;
	}

	public String getDuplexAdfScanning() {
		return duplexAdfScanning;
	}

	public void setDuplexAdfScanning(String duplexAdfScanning) {
		this.duplexAdfScanning = duplexAdfScanning;
	}

	public String getEnergyStar() {
		return energyStar;
	}

	public void setEnergyStar(String energyStar) {
		this.energyStar = energyStar;
	}

	public String getEmbeddedWebServer() {
		return embeddedWebServer;
	}

	public void setEmbeddedWebServer(String embeddedWebServer) {
		this.embeddedWebServer = embeddedWebServer;
	}

	public String getFaxMemory() {
		return faxMemory;
	}

	public void setFaxMemory(String faxMemory) {
		this.faxMemory = faxMemory;
	}

	public String getFaxResolution() {
		return faxResolution;
	}

	public void setFaxResolution(String faxResolution) {
		this.faxResolution = faxResolution;
	}

	public String getFaxTransmissionSpeed() {
		return faxTransmissionSpeed;
	}

	public void setFaxTransmissionSpeed(String faxTransmissionSpeed) {
		this.faxTransmissionSpeed = faxTransmissionSpeed;
	}

	public String getFaxing() {
		return faxing;
	}

	public void setFaxing(String faxing) {
		this.faxing = faxing;
	}

	public String getFinishedOutputHandling() {
		return finishedOutputHandling;
	}

	public void setFinishedOutputHandling(String finishedOutputHandling) {
		this.finishedOutputHandling = finishedOutputHandling;
	}

	public String getFirstPageOutBlack() {
		return firstPageOutBlack;
	}

	public void setFirstPageOutBlack(String firstPageOutBlack) {
		this.firstPageOutBlack = firstPageOutBlack;
	}

	public String getFirstPageOutColor() {
		return firstPageOutColor;
	}

	public void setFirstPageOutColor(String firstPageOutColor) {
		this.firstPageOutColor = firstPageOutColor;
	}

	public String getGuaranteedMinimumLineWidth() {
		return guaranteedMinimumLineWidth;
	}

	public void setGuaranteedMinimumLineWidth(String guaranteedMinimumLineWidth) {
		this.guaranteedMinimumLineWidth = guaranteedMinimumLineWidth;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getLineAccuracy() {
		return lineAccuracy;
	}

	public void setLineAccuracy(String lineAccuracy) {
		this.lineAccuracy = lineAccuracy;
	}

	public String getMaximumDocumentScanSize() {
		return maximumDocumentScanSize;
	}

	public void setMaximumDocumentScanSize(String maximumDocumentScanSize) {
		this.maximumDocumentScanSize = maximumDocumentScanSize;
	}

	public String getMaximumOpticalDensityBlack() {
		return maximumOpticalDensityBlack;
	}

	public void setMaximumOpticalDensityBlack(String maximumOpticalDensityBlack) {
		this.maximumOpticalDensityBlack = maximumOpticalDensityBlack;
	}

	public String getMptUsColorImageBestModeGlossy() {
		return mptUsColorImageBestModeGlossy;
	}

	public void setMptUsColorImageBestModeGlossy(String mptUsColorImageBestModeGlossy) {
		this.mptUsColorImageBestModeGlossy = mptUsColorImageBestModeGlossy;
	}

	public String getMptUsColorImageDraftModeCoated() {
		return mptUsColorImageDraftModeCoated;
	}

	public void setMptUsColorImageDraftModeCoated(String mptUsColorImageDraftModeCoated) {
		this.mptUsColorImageDraftModeCoated = mptUsColorImageDraftModeCoated;
	}

	public String getMptUsColorImageNormalModeCoated() {
		return mptUsColorImageNormalModeCoated;
	}

	public void setMptUsColorImageNormalModeCoated(String mptUsColorImageNormalModeCoated) {
		this.mptUsColorImageNormalModeCoated = mptUsColorImageNormalModeCoated;
	}

	public String getMptUsColorImageNormalModeGlossy() {
		return mptUsColorImageNormalModeGlossy;
	}

	public void setMptUsColorImageNormalModeGlossy(String mptUsColorImageNormalModeGlossy) {
		this.mptUsColorImageNormalModeGlossy = mptUsColorImageNormalModeGlossy;
	}

	public String getMptBWLineDrawingDraftModePlain() {
		return mptBWLineDrawingDraftModePlain;
	}

	public void setMptBWLineDrawingDraftModePlain(String mptBWLineDrawingDraftModePlain) {
		this.mptBWLineDrawingDraftModePlain = mptBWLineDrawingDraftModePlain;
	}

	public String getMptColorLineDrawingDraftModePlain() {
		return mptColorLineDrawingDraftModePlain;
	}

	public void setMptColorLineDrawingDraftModePlain(String mptColorLineDrawingDraftModePlain) {
		this.mptColorLineDrawingDraftModePlain = mptColorLineDrawingDraftModePlain;
	}

	public String getMptLineDrawingEconomodePlain() {
		return mptLineDrawingEconomodePlain;
	}

	public void setMptLineDrawingEconomodePlain(String mptLineDrawingEconomodePlain) {
		this.mptLineDrawingEconomodePlain = mptLineDrawingEconomodePlain;
	}

	public String getMediaThickness() {
		return mediaThickness;
	}

	public void setMediaThickness(String mediaThickness) {
		this.mediaThickness = mediaThickness;
	}

	public String getMemoryCardCompatibility() {
		return memoryCardCompatibility;
	}

	public void setMemoryCardCompatibility(String memoryCardCompatibility) {
		this.memoryCardCompatibility = memoryCardCompatibility;
	}

	public String getNonPrintableArea() {
		return nonPrintableArea;
	}

	public void setNonPrintableArea(String nonPrintableArea) {
		this.nonPrintableArea = nonPrintableArea;
	}

	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public String getPrintRepeatability() {
		return printRepeatability;
	}

	public void setPrintRepeatability(String printRepeatability) {
		this.printRepeatability = printRepeatability;
	}

	public String getPrintSpeedMaximum() {
		return printSpeedMaximum;
	}

	public void setPrintSpeedMaximum(String printSpeedMaximum) {
		this.printSpeedMaximum = printSpeedMaximum;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getResolutionTechnology() {
		return resolutionTechnology;
	}

	public void setResolutionTechnology(String resolutionTechnology) {
		this.resolutionTechnology = resolutionTechnology;
	}

	public String getRollExternalDiameter() {
		return rollExternalDiameter;
	}

	public void setRollExternalDiameter(String rollExternalDiameter) {
		this.rollExternalDiameter = rollExternalDiameter;
	}

	public String getRollMaximumOutput() {
		return rollMaximumOutput;
	}

	public void setRollMaximumOutput(String rollMaximumOutput) {
		this.rollMaximumOutput = rollMaximumOutput;
	}

	public String getScanFileFormat() {
		return scanFileFormat;
	}

	public void setScanFileFormat(String scanFileFormat) {
		this.scanFileFormat = scanFileFormat;
	}

	public String getScanResolutionHardware() {
		return scanResolutionHardware;
	}

	public void setScanResolutionHardware(String scanResolutionHardware) {
		this.scanResolutionHardware = scanResolutionHardware;
	}

	public String getScanResolutionOptical() {
		return scanResolutionOptical;
	}

	public void setScanResolutionOptical(String scanResolutionOptical) {
		this.scanResolutionOptical = scanResolutionOptical;
	}

	public String getScanSizeMaximum() {
		return scanSizeMaximum;
	}

	public void setScanSizeMaximum(String scanSizeMaximum) {
		this.scanSizeMaximum = scanSizeMaximum;
	}

	public String getScanSizeFlatbedMaximum() {
		return scanSizeFlatbedMaximum;
	}

	public void setScanSizeFlatbedMaximum(String scanSizeFlatbedMaximum) {
		this.scanSizeFlatbedMaximum = scanSizeFlatbedMaximum;
	}

	public String getScanSpeedMaximum() {
		return scanSpeedMaximum;
	}

	public void setScanSpeedMaximum(String scanSpeedMaximum) {
		this.scanSpeedMaximum = scanSpeedMaximum;
	}

	public String getScannableMediaTypes() {
		return scannableMediaTypes;
	}

	public void setScannableMediaTypes(String scannableMediaTypes) {
		this.scannableMediaTypes = scannableMediaTypes;
	}

	public String getScannerType() {
		return scannerType;
	}

	public void setScannerType(String scannerType) {
		this.scannerType = scannerType;
	}

	public String getSpeedDialsMaximumNumber() {
		return speedDialsMaximumNumber;
	}

	public void setSpeedDialsMaximumNumber(String speedDialsMaximumNumber) {
		this.speedDialsMaximumNumber = speedDialsMaximumNumber;
	}

    public String getAutomaticPaperSensor() {
        return automaticPaperSensor;
    }

    public void setAutomaticPaperSensor(String automaticPaperSensor) {
        this.automaticPaperSensor = automaticPaperSensor;
    }

    public String getBorderlessPrinting() {
        return borderlessPrinting;
    }

    public void setBorderlessPrinting(String borderlessPrinting) {
        this.borderlessPrinting = borderlessPrinting;
    }

    public String getCableIncluded() {
        return cableIncluded;
    }

    public void setCableIncluded(String cableIncluded) {
        this.cableIncluded = cableIncluded;
    }

    public String getCompatibleInkTypes() {
        return compatibleInkTypes;
    }

    public void setCompatibleInkTypes(String compatibleInkTypes) {
        this.compatibleInkTypes = compatibleInkTypes;
    }

    public String getCompatibleOperatingSystems() {
        return compatibleOperatingSystems;
    }

    public void setCompatibleOperatingSystems(String compatibleOperatingSystems) {
        this.compatibleOperatingSystems = compatibleOperatingSystems;
    }

    public String getConnectivityOptional() {
        return connectivityOptional;
    }

    public void setConnectivityOptional(String connectivityOptional) {
        this.connectivityOptional = connectivityOptional;
    }

    public String getConnectivityStd() {
        return connectivityStd;
    }

    public void setConnectivityStd(String connectivityStd) {
        this.connectivityStd = connectivityStd;
    }

    public BigDecimal getDimensionDepthInInches() {
        return dimensionDepthInInches;
    }

    public void setDimensionDepthInInches(BigDecimal dimensionDepthInInches) {
        this.dimensionDepthInInches = dimensionDepthInInches;
    }

    public BigDecimal getDimensionDepthInInchesMax() {
        return dimensionDepthInInchesMax;
    }

    public void setDimensionDepthInInchesMax(BigDecimal dimensionDepthInInchesMax) {
        this.dimensionDepthInInchesMax = dimensionDepthInInchesMax;
    }

    public BigDecimal getDimensionHeightInInches() {
        return dimensionHeightInInches;
    }

    public void setDimensionHeightInInches(BigDecimal dimensionHeightInInches) {
        this.dimensionHeightInInches = dimensionHeightInInches;
    }

    public BigDecimal getDimensionHeightInInchesMax() {
        return dimensionHeightInInchesMax;
    }

    public void setDimensionHeightInInchesMax(BigDecimal dimensionHeightInInchesMax) {
        this.dimensionHeightInInchesMax = dimensionHeightInInchesMax;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDimensionsMax() {
        return dimensionsMax;
    }

    public void setDimensionsMax(String dimensionsMax) {
        this.dimensionsMax = dimensionsMax;
    }

    public BigDecimal getDimensionWidthInInches() {
        return dimensionWidthInInches;
    }

    public void setDimensionWidthInInches(BigDecimal dimensionWidthInInches) {
        this.dimensionWidthInInches = dimensionWidthInInches;
    }

    public BigDecimal getDimensionWidthInInchesMax() {
        return dimensionWidthInInchesMax;
    }

    public void setDimensionWidthInInchesMax(BigDecimal dimensionWidthInInchesMax) {
        this.dimensionWidthInInchesMax = dimensionWidthInInchesMax;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDuplexPrinting() {
        return duplexPrinting;
    }

    public void setDuplexPrinting(String duplexPrinting) {
        this.duplexPrinting = duplexPrinting;
    }

    public String getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(String energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }

    public String getEnvelopeInputCapacity() {
        return envelopeInputCapacity;
    }

    public void setEnvelopeInputCapacity(String envelopeInputCapacity) {
        this.envelopeInputCapacity = envelopeInputCapacity;
    }

    public String getFcc() {
        return fcc;
    }

    public void setFcc(String fcc) {
        this.fcc = fcc;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }


    public String getMediaSizesCustom() {
        return mediaSizesCustom;
    }

    public void setMediaSizesCustom(String mediaSizesCustom) {
        this.mediaSizesCustom = mediaSizesCustom;
    }

    public String getMediaSizesSupported() {
        return mediaSizesSupported;
    }

    public void setMediaSizesSupported(String mediaSizesSupported) {
        this.mediaSizesSupported = mediaSizesSupported;
    }

    public String getMediaTypes() {
        return mediaTypes;
    }

    public void setMediaTypes(String mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public String getMediaWeightsByPaperPath() {
        return mediaWeightsByPaperPath;
    }

    public void setMediaWeightsByPaperPath(String mediaWeightsByPaperPath) {
        this.mediaWeightsByPaperPath = mediaWeightsByPaperPath;
    }

    public String getMemoryMax() {
        return memoryMax;
    }

    public void setMemoryMax(String memoryMax) {
        this.memoryMax = memoryMax;
    }

    public String getMemoryStd() {
        return memoryStd;
    }

    public void setMemoryStd(String memoryStd) {
        this.memoryStd = memoryStd;
    }

    public String getMinimumSystemRequirements() {
        return minimumSystemRequirements;
    }

    public void setMinimumSystemRequirements(String minimumSystemRequirements) {
        this.minimumSystemRequirements = minimumSystemRequirements;
    }

    public String getMobilePrintingCapability() {
        return mobilePrintingCapability;
    }

    public void setMobilePrintingCapability(String mobilePrintingCapability) {
        this.mobilePrintingCapability = mobilePrintingCapability;
    }

    public String getMonthlyDutyCycle() {
        return monthlyDutyCycle;
    }

    public void setMonthlyDutyCycle(String monthlyDutyCycle) {
        this.monthlyDutyCycle = monthlyDutyCycle;
    }

    public String getNetworkReady() {
        return networkReady;
    }

    public void setNetworkReady(String networkReady) {
        this.networkReady = networkReady;
    }

    public String getNumberPrintCartridges() {
        return numberPrintCartridges;
    }

    public void setNumberPrintCartridges(String numberPrintCartridges) {
        this.numberPrintCartridges = numberPrintCartridges;
    }

    public String getOperatingHumidityRange() {
        return operatingHumidityRange;
    }

    public void setOperatingHumidityRange(String operatingHumidityRange) {
        this.operatingHumidityRange = operatingHumidityRange;
    }

    public String getOperatingTemperatureRange() {
        return operatingTemperatureRange;
    }

    public void setOperatingTemperatureRange(String operatingTemperatureRange) {
        this.operatingTemperatureRange = operatingTemperatureRange;
    }

    public String getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(String packageWeight) {
        this.packageWeight = packageWeight;
    }

    public BigDecimal getPackageWeightInPounds() {
        return packageWeightInPounds;
    }

    public void setPackageWeightInPounds(BigDecimal packageWeightInPounds) {
        this.packageWeightInPounds = packageWeightInPounds;
    }

    public String getPaperHandlingInputStd() {
        return paperHandlingInputStd;
    }

    public void setPaperHandlingInputStd(String paperHandlingInputStd) {
        this.paperHandlingInputStd = paperHandlingInputStd;
    }

    public String getPaperHandlingOutputStd() {
        return paperHandlingOutputStd;
    }

    public void setPaperHandlingOutputStd(String paperHandlingOutputStd) {
        this.paperHandlingOutputStd = paperHandlingOutputStd;
    }

    public Integer getPaperTraysMax() {
        return paperTraysMax;
    }

    public void setPaperTraysMax(Integer paperTraysMax) {
        this.paperTraysMax = paperTraysMax;
    }

    public Integer getPaperTraysStd() {
        return paperTraysStd;
    }

    public void setPaperTraysStd(Integer paperTraysStd) {
        this.paperTraysStd = paperTraysStd;
    }


    public String getPostWarranty() {
        return postWarranty;
    }

    public void setPostWarranty(String postWarranty) {
        this.postWarranty = postWarranty;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(String powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public String getPrinterManagement() {
        return printerManagement;
    }

    public void setPrinterManagement(String printerManagement) {
        this.printerManagement = printerManagement;
    }

    public String getPrinterPageYield() {
        return printerPageYield;
    }

    public void setPrinterPageYield(String printerPageYield) {
        this.printerPageYield = printerPageYield;
    }

    public String getPrintLanguages() {
        return printLanguages;
    }

    public void setPrintLanguages(String printLanguages) {
        this.printLanguages = printLanguages;
    }

    public String getPrintSpeedBlackDraft() {
        return printSpeedBlackDraft;
    }

    public void setPrintSpeedBlackDraft(String printSpeedBlackDraft) {
        this.printSpeedBlackDraft = printSpeedBlackDraft;
    }

    public String getPrintSpeedBlackISO() {
        return printSpeedBlackISO;
    }

    public void setPrintSpeedBlackISO(String printSpeedBlackISO) {
        this.printSpeedBlackISO = printSpeedBlackISO;
    }

    public String getPrintSpeedColorDraft() {
        return printSpeedColorDraft;
    }

    public void setPrintSpeedColorDraft(String printSpeedColorDraft) {
        this.printSpeedColorDraft = printSpeedColorDraft;
    }

    public String getPrintSpeedColorISO() {
        return printSpeedColorISO;
    }

    public void setPrintSpeedColorISO(String printSpeedColorISO) {
        this.printSpeedColorISO = printSpeedColorISO;
    }

    public String getPrintTechnology() {
        return printTechnology;
    }

    public void setPrintTechnology(String printTechnology) {
        this.printTechnology = printTechnology;
    }

    public String getProcessorSpeed() {
        return processorSpeed;
    }

    public void setProcessorSpeed(String processorSpeed) {
        this.processorSpeed = processorSpeed;
    }

    public String getRecommendedMediaWeight() {
        return recommendedMediaWeight;
    }

    public void setRecommendedMediaWeight(String recommendedMediaWeight) {
        this.recommendedMediaWeight = recommendedMediaWeight;
    }

    public String getRecommMonthlyPageVolume() {
        return recommMonthlyPageVolume;
    }

    public void setRecommMonthlyPageVolume(String recommMonthlyPageVolume) {
        this.recommMonthlyPageVolume = recommMonthlyPageVolume;
    }

    public Integer getRecommMonthlyPageVolumeMax() {
        return recommMonthlyPageVolumeMax;
    }

    public void setRecommMonthlyPageVolumeMax(Integer recommMonthlyPageVolumeMax) {
        this.recommMonthlyPageVolumeMax = recommMonthlyPageVolumeMax;
    }

    public Integer getRecommMonthlyPageVolumeMin() {
        return recommMonthlyPageVolumeMin;
    }

    public void setRecommMonthlyPageVolumeMin(Integer recommMonthlyPageVolumeMin) {
        this.recommMonthlyPageVolumeMin = recommMonthlyPageVolumeMin;
    }

    public String getReplacementCartridges() {
        return replacementCartridges;
    }

    public void setReplacementCartridges(String replacementCartridges) {
        this.replacementCartridges = replacementCartridges;
    }

    public String getResolutionBlack() {
        return resolutionBlack;
    }

    public void setResolutionBlack(String resolutionBlack) {
        this.resolutionBlack = resolutionBlack;
    }

    public String getResolutionColor() {
        return resolutionColor;
    }

    public void setResolutionColor(String resolutionColor) {
        this.resolutionColor = resolutionColor;
    }

    public String getSecurityManagement() {
        return securityManagement;
    }

    public void setSecurityManagement(String securityManagement) {
        this.securityManagement = securityManagement;
    }

    public String getSoftwareIncluded() {
        return softwareIncluded;
    }

    public void setSoftwareIncluded(String softwareIncluded) {
        this.softwareIncluded = softwareIncluded;
    }

    public String getSupportedMediaWeight() {
        return supportedMediaWeight;
    }

    public void setSupportedMediaWeight(String supportedMediaWeight) {
        this.supportedMediaWeight = supportedMediaWeight;
    }

    public String getSupportedNetworkProtocols() {
        return supportedNetworkProtocols;
    }

    public void setSupportedNetworkProtocols(String supportedNetworkProtocols) {
        this.supportedNetworkProtocols = supportedNetworkProtocols;
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

    public String getWhatsInTheBox() {
        return whatsInTheBox;
    }

    public void setWhatsInTheBox(String whatsInTheBox) {
        this.whatsInTheBox = whatsInTheBox;
    }

	public String getUsColorLineDrawingsDraftPlain() {
		return usColorLineDrawingsDraftPlain;
	}

	public void setUsColorLineDrawingsDraftPlain(String usColorLineDrawingsDraftPlain) {
		this.usColorLineDrawingsDraftPlain = usColorLineDrawingsDraftPlain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
