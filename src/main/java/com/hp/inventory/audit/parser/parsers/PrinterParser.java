/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Printer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.3
 */
public class PrinterParser extends DocumentParser {
	
	@Override
	protected IProduct extract() throws Exception {
		Printer p = new Printer();

		setParsingErrorsReceiver(p);

		extractCommonProps(p);

		p.setDimensions(prop("Dimensions", listDelimiter));
		BigDecimal[] wdh = parseDimensions(p.getDimensions());
		if (wdh != null) {
			p.setDimensionWidthInInches(wdh[0]);
			p.setDimensionDepthInInches(wdh[1]);
			p.setDimensionHeightInInches(wdh[2]);
		}

		p.setDimensionsMax(prop("Dimensions (Maximum)", listDelimiter));
		wdh = parseDimensions(p.getDimensions());
		if (wdh != null) {
			p.setDimensionWidthInInchesMax(wdh[0]);
			p.setDimensionDepthInInchesMax(wdh[1]);
			p.setDimensionHeightInInchesMax(wdh[2]);
		}

		p.setDisplay(prop("Display", listDelimiter));
		p.setEnergyEfficiency(prop("Energy efficiency", listDelimiter));
		p.setHpDataSheet(propLink("HP Data Sheet"));

		p.setSoftwareIncluded(prop("Software included", listDelimiter));

		p.setWeight(prop("Weight", true, listDelimiter));
		p.setWeightInPounds(parseWeightInPounds(p.getWeight()));

		p.setPackageWeight(prop("Package weight", listDelimiter));
		p.setPackageWeightInPounds(parseWeightInPounds(p.getPackageWeight()));

		p.setWarranty(prop("Warranty", true, listDelimiter));

		p.setAutomaticPaperSensor(prop("Automatic paper sensor", listDelimiter));
		p.setBorderlessPrinting(prop("Borderless printing", listDelimiter));
		p.setCompatibleInkTypes(prop("Compatible ink types", listDelimiter));
		p.setCableIncluded(prop("Cable included", listDelimiter));
		p.setCompatibleOperatingSystems(any(
				prop("Compatible operating systems", listDelimiter),
				prop("Supported operating systems", listDelimiter)));
		p.setConnectivityStd(prop("Connectivity, standard", listDelimiter));
		p.setConnectivityOptional(prop("Connectivity, optional", listDelimiter));
		p.setDuplexPrinting(prop("Duplex printing", listDelimiter));
		p.setEnvelopeInputCapacity(prop("Envelope input capacity", listDelimiter));
		p.setFunctions(prop("Functions", listDelimiter));
		p.setMediaSizesSupported(any(prop("Media sizes supported", listDelimiter),
				prop("Media sizes, standard", listDelimiter)));
		p.setMediaSizesCustom(prop("Media sizes, custom", listDelimiter));
		p.setMediaTypes(prop("Media types", listDelimiter));
		p.setMediaWeightsByPaperPath(prop("Media weights by paper path", listDelimiter));
		p.setMemoryMax(prop("Memory, maximum", listDelimiter));
		p.setMemoryStd(prop("Memory, standard", listDelimiter));
		p.setMinimumSystemRequirements(prop("Minimum system requirements", listDelimiter));
		p.setMobilePrintingCapability(prop("Mobile Printing Capability", listDelimiter));
		p.setMonthlyDutyCycle(prop("Monthly duty cycle", listDelimiter));
		p.setNetworkReady(prop("Network ready", listDelimiter));
		p.setPostWarranty(prop("Post warranty", listDelimiter));
		p.setEnergyEfficiency(prop("Energy efficiency", listDelimiter));

		p.setNumberPrintCartridges(prop("Number of print cartridges", listDelimiter));
		p.setOperatingHumidityRange(prop("Operating humidity range", listDelimiter));
		p.setOperatingTemperatureRange(prop("Operating temperature range", listDelimiter));
		p.setPaperHandlingInputStd(prop("Paper handling input, standard", listDelimiter));
		p.setPaperHandlingOutputStd(prop("Paper handling output, standard", listDelimiter));
		p.setPaperTraysMax(parsePaperTray(prop("Paper trays, maximum", listDelimiter), p));
		p.setPaperTraysStd(parsePaperTray(prop("Paper trays, standard", listDelimiter), p));
		p.setPower(prop("Power", listDelimiter));
		p.setPowerConsumption(prop("Power consumption", listDelimiter));
		p.setPrintTechnology(prop("Print Technology", listDelimiter));

		/**
		 * @since 1.0.1 Printer type
		 */
		if (p.getPrintTechnology() != null
				&& p.getPrintTechnology().contains("Laser")) {
			p.setType("Laser");
		} else {
			p.setType("Inkjet");
		}
		// ----------------------

		p.setPrintLanguages(prop("Print languages", listDelimiter));

		p.setPrintSpeedBlackDraft(prop("Print speed, black (draft)", listDelimiter));
		p.setPrintSpeedBlackISO(any(prop("Print speed, black (normal)", listDelimiter),
				prop("Print speed, black (ISO, laser comparable)", listDelimiter)));
		p.setPrintSpeedColorDraft(any(prop("Print speed, color (draft)", listDelimiter),
				prop("Print speed, color (draft, 4x6 photo)", listDelimiter)));
		p.setPrintSpeedColorISO(any(prop("Print speed, color (normal)", listDelimiter),
				prop("Print speed, color (ISO, laser comparable)", listDelimiter)));

		p.setPrinterManagement(prop("Printer management", listDelimiter));
		p.setPrinterPageYield(prop("Printer page yield", listDelimiter));
		p.setProcessorSpeed(prop("Processor speed", listDelimiter));

		p.setReplacementCartridges(prop("Replacement cartridges", listDelimiter));
		p.setResolutionBlack(prop("Resolution (black)", listDelimiter));
		p.setResolutionColor(prop("Resolution (color)", listDelimiter));
		p.setRecommendedMediaWeight(prop("Recommended media weight", listDelimiter));
		p.setSecurityManagement(prop("Security management", listDelimiter));
		p.setSupportedMediaWeight(prop("Supported media weight", listDelimiter));
		p.setSupportedNetworkProtocols(prop("Supported network protocols", listDelimiter));
		p.setWhatsInTheBox(prop("What's in the box", listDelimiter));
		p.setFcc(prop("FCC", listDelimiter));
		p.setHardDisk(any(prop("Hard disk", listDelimiter), prop("Internal drive", listDelimiter)));

		p.setAdfCapacity(prop("ADF Capacity", true, listDelimiter));
		p.setAutoDocumentFeeder(prop("Auto document feeder", true, listDelimiter));
		p.setBatteryRechargeTime(prop("Battery recharge time", true, listDelimiter));
		p.setBitDepth(prop("Bit depth", true, listDelimiter));
		p.setBroadcastLocations(prop("Broadcast locations", true, listDelimiter));
		p.setBrowserSupported(prop("Browser supported", true, listDelimiter));
		p.setColorStability(prop("Color stability", true, listDelimiter));
		p.setConnectivity(prop("Connectivity", true, listDelimiter));
		p.setControlPanel(prop("Control panel", true, listDelimiter));
		p.setMaximumCopies(prop("Copies, maximum", true, listDelimiter));
		p.setCopyReduceEnlargeSettings(prop("Copy reduce / enlarge settings",
				true, listDelimiter));
		p.setCopyResolutionBlackText(prop("Copy resolution (black text)", true, listDelimiter));
		p.setCopyResolutionColourTextGraphics(prop(
				"Copy resolution (colour text and graphics)", true, listDelimiter));
		p.setCopySpeedBlackDraft(prop("Copy speed black (draft)", true, listDelimiter));
		p.setCopySpeedBlackNormal(prop("Copy speed black (normal)", true, listDelimiter));
		p.setCopySpeedColorDraft(prop("Copy speed color (draft)", true, listDelimiter));
		p.setCopySpeedColorNormal(prop("Copy speed color (normal)", true, listDelimiter));
		p.setDigitalSendFileFormats(prop("Digital send file Formats", true, listDelimiter));
		p.setDigitalSendingFeatures(prop("Digital sending features", true, listDelimiter));
		p.setDuplexAdfScanning(prop("Duplex ADF scanning", true, listDelimiter));
		p.setEnergyStar(prop("ENERGY STAR", true, listDelimiter));
		p.setEmbeddedWebServer(prop("Embedded web server", true, listDelimiter));
		p.setFaxMemory(prop("Fax memory", true, listDelimiter));
		p.setFaxResolution(prop("Fax resolution", true, listDelimiter));
		p.setFaxTransmissionSpeed(prop("Fax transmission speed", true, listDelimiter));
		p.setFaxing(prop("Faxing", true, listDelimiter));
		p.setFinishedOutputHandling(prop("Finished output handling", true, listDelimiter));
		p.setFirstPageOutBlack(prop("First page out (ready) black", true, listDelimiter));
		p.setFirstPageOutColor(prop("First page out (ready) color", true, listDelimiter));
		p.setGuaranteedMinimumLineWidth(prop("Guaranteed minimum line width",
				true, listDelimiter));
		p.setInputType(prop("Input type", true, listDelimiter));
		p.setLineAccuracy(prop("Line accuracy", true, listDelimiter));
		p.setMaximumDocumentScanSize(prop("Maximum document scan size", true, listDelimiter));
		p.setMaximumOpticalDensityBlack(prop("Maximum optical density (black)",
				true, listDelimiter));
		p.setMptUsColorImageBestModeGlossy(prop(
				"Mechanical print time, US D color image, best mode, glossy",
				true, listDelimiter));
		p.setMptUsColorImageDraftModeCoated(prop(
				"Mechanical print time, US D color image, draft mode, coated",
				true, listDelimiter));
		p.setMptUsColorImageNormalModeCoated(prop(
				"Mechanical print time, US D color image, normal mode, coated",
				true, listDelimiter));
		p.setMptUsColorImageNormalModeGlossy(prop(
				"Mechanical print time, US D color image, normal mode, glossy",
				true, listDelimiter));
		p.setMptBWLineDrawingDraftModePlain(prop(
				"Mechanical print time, b\u0026w line drawing, draft mode, plain",
				true, listDelimiter));
		p.setMptColorLineDrawingDraftModePlain(prop(
				"Mechanical print time, color line drawing, draft mode, plain",
				true, listDelimiter));
		p.setMptLineDrawingEconomodePlain(prop(
				"Mechanical print time, line drawing, economode, plain", true, listDelimiter));
		p.setMediaThickness(prop("Media thickness", true, listDelimiter));
		p.setMemoryCardCompatibility(prop("Memory card compatibility", true, listDelimiter));
		p.setNonPrintableArea(prop("Non-printable area (cut-sheet)", true, listDelimiter));
		p.setPorts(prop("Ports", true, listDelimiter));
		p.setPrintRepeatability(prop("Print repeatability", true, listDelimiter));
		p.setPrintSpeedMaximum(prop("Print speed, maximum", true, listDelimiter));
		p.setResolution(prop("Resolution", true, listDelimiter));
		p.setResolutionTechnology(prop("Resolution technology", true, listDelimiter));
		p.setRollExternalDiameter(prop("Roll external diameter", true, listDelimiter));
		p.setRollMaximumOutput(prop("Roll maximum output", true, listDelimiter));
		p.setScanFileFormat(prop("Scan file format", true, listDelimiter));
		p.setScanResolutionHardware(prop("Scan resolution, hardware", true, listDelimiter));
		p.setScanResolutionOptical(prop("Scan resolution, optical", true, listDelimiter));
		p.setScanSizeMaximum(prop("Scan size (ADF), maximum", true, listDelimiter));
		p.setScanSizeFlatbedMaximum(prop("Scan size (flatbed), maximum", true, listDelimiter));
		p.setScanSpeedMaximum(prop("Scan speed, maximum", true, listDelimiter));
		p.setScannableMediaTypes(prop("Scannable media types", true, listDelimiter));
		p.setScannerType(prop("Scanner type", true, listDelimiter));
		p.setSpeedDialsMaximumNumber(prop("Speed dials, maximum number", true, listDelimiter));
		p.setUsColorLineDrawingsDraftPlain(prop(
				"US D color line drawings/hr, draft mode, plain", true, listDelimiter));

		recommendedMonthlyPageVolume(p);

		checkParsedProps();

		return p;
	}

	static Pattern paperTrayPattern0 = Pattern.compile("^(\\d+)");
	static Pattern paperTrayPattern1 = Pattern.compile("Up to (\\d+)");

	/**
	 * Parse the paper tray quantity
	 */
	private Integer parsePaperTray(String prop, AbstractProduct p) {
		if (prop == null || prop.trim().isEmpty())
			return null;
		Integer r = null;

		Matcher matcher = paperTrayPattern0.matcher(prop);
		if (matcher.find()) {
			r = Integer.valueOf(matcher.group(1));
		}

		if (r == null && (matcher = paperTrayPattern1.matcher(prop)).find()) {
			r = Integer.valueOf(matcher.group(1));
		}

		if (r == null) {
			p.getParsingErrors().append(
					String.format("Unparseable paper tray string: %s\n", prop));
			return null;
			// throw new
			// DocumentParseException("Unparseable paper tray string: " + prop);
		} else {
			return r;
		}

	}

	static Pattern rmpvPattern = Pattern
			.compile("^([\\d,\\.]+) to ([\\d,\\.]+)");

	/**
	 * Tries to set the fields recommMonthlyPageVolume,
	 * recommMonthlyPageVolumeMin and recommMonthlyPageVolumeMax
	 */
	private void recommendedMonthlyPageVolume(Printer p) {
		String[] rmpvs = props("Recommended monthly page volume");
		String mpvsJoined = (rmpvs == null) ? null : StringUtils.join(rmpvs, "\n");
		p.setRecommMonthlyPageVolume(mpvsJoined);

		if (rmpvs != null) {
			boolean parsed = false;

			for (String rmpv : rmpvs) {
				if (rmpv != null && !rmpv.trim().isEmpty()) {
					Matcher matcher = rmpvPattern.matcher(rmpv);
					if (matcher.find()) {
						p.setRecommMonthlyPageVolumeMin(Integer.valueOf(matcher
								.group(1).replace(",", "")));
						p.setRecommMonthlyPageVolumeMax(Integer.valueOf(matcher
								.group(2).replace(",", "")));
						parsed = true;
						break;
					}
				} else {
					parsed = true;
				}
			}
			if (!parsed) {
				p.getParsingErrors()
						.append(String
								.format("Unparseable recommendedMonthlyPageVolume from: %s\n",
										mpvsJoined));
			}
		}
	}
}
