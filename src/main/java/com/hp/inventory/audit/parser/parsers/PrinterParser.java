/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.ProductSpecification;
import org.apache.commons.lang3.StringUtils;

/**
 * Parser for printer products.
 *
 * changes:
 *  - 1.0.4: refactor the columns to specifications.
 * 
 * @author TCDEVELOPER
 * @version 1.0.4
 */
public class PrinterParser extends DocumentParser {

	private Pattern highEndPrinterPattern = Pattern.compile("DesignJet|Latex|PageWide|Scitex", Pattern.CASE_INSENSITIVE);

	@Override
	protected AbstractProduct extract() throws Exception {
		Product p = this.definition;
		p.setCategory("Printer");

		setParsingErrorsReceiver(p);

		extractCommonProps(p);

		p.setHpDataSheet(propLink("HP Data Sheet"));

		Set<ProductSpecification> specifications = new HashSet<>();

		specifications.add(constructSpecification(p, "dimensions", prop("Dimensions", listDelimiter)));
		BigDecimal[] wdh = parseDimensions(prop("Dimensions", listDelimiter));
		if (wdh != null) {
			specifications.add(constructSpecification(p, "dimensionWidthInInches", String.valueOf(wdh[0])));
			specifications.add(constructSpecification(p, "dimensionDepthInInches", String.valueOf(wdh[1])));
			specifications.add(constructSpecification(p, "dimensionHeightInInches", String.valueOf(wdh[2])));
		}

		specifications.add(constructSpecification(p, "dimensionsMax", prop("Dimensions (Maximum)", listDelimiter)));
		wdh = parseDimensions(prop("Dimensions", listDelimiter));
		if (wdh != null) {
			specifications.add(constructSpecification(p, "dimensionWidthInInchesMax", String.valueOf(wdh[0])));
			specifications.add(constructSpecification(p, "dimensionDepthInInchesMax", String.valueOf(wdh[1])));
			specifications.add(constructSpecification(p, "dimensionHeightInInchesMax", String.valueOf(wdh[2])));
		}

		specifications.add(constructSpecification(p, "display", prop("Display", listDelimiter)));
		specifications.add(constructSpecification(p, "energyEfficiency", prop("Energy efficiency", listDelimiter)));


		specifications.add(constructSpecification(p, "softwareIncluded", prop("Software included", listDelimiter)));

		specifications.add(constructSpecification(p, "weight", prop("Weight", true, listDelimiter)));
		specifications.add(constructSpecification(p, "weightInPounds",
						String.valueOf(parseWeightInPounds(prop("Weight", true, listDelimiter)))));

		specifications.add(constructSpecification(p, "packageWeight", prop("Package weight", listDelimiter)));
		specifications.add(constructSpecification(p, "packageWeightInPounds", 
						String.valueOf(parseWeightInPounds(prop("Package weight", listDelimiter)))));
		specifications.add(constructSpecification(p, "warranty", prop("Warranty", true, listDelimiter)));

		specifications.add(constructSpecification(p, "automaticPaperSensor", prop("Automatic paper sensor", listDelimiter)));
		specifications.add(constructSpecification(p, "borderlessPrinting", prop("Borderless printing", listDelimiter)));
		specifications.add(constructSpecification(p, "compatibleInkTypes", prop("Compatible ink types", listDelimiter)));
		specifications.add(constructSpecification(p, "cableIncluded", prop("Cable included", listDelimiter)));
		specifications.add(constructSpecification(p, "compatibleOperatingSystems", any(
				prop("Compatible operating systems", listDelimiter),
				prop("Supported operating systems", listDelimiter))));
		specifications.add(constructSpecification(p, "connectivityStd", prop("Connectivity, standard", listDelimiter)));
		specifications.add(constructSpecification(p, "connectivityOptional",
						prop("Connectivity, optional", listDelimiter)));
		specifications.add(constructSpecification(p, "duplexPrinting", prop("Duplex printing", listDelimiter)));
		specifications.add(constructSpecification(p,
						"envelopeInputCapacity", prop("Envelope input capacity", listDelimiter)));
		specifications.add(constructSpecification(p, "functions", prop("Functions", listDelimiter)));
		specifications.add(constructSpecification(p, "mediaSizesSupported", any(prop("Media sizes supported", listDelimiter),
				prop("Media sizes, standard", listDelimiter))));
		specifications.add(constructSpecification(p, "mediaSizesCustom", prop("Media sizes, custom", listDelimiter)));
		specifications.add(constructSpecification(p, "mediaTypes", prop("Media types", listDelimiter)));
		specifications.add(constructSpecification(p,
						"mediaWeightsByPaperPath", prop("Media weights by paper path", listDelimiter)));
		specifications.add(constructSpecification(p, "memoryMax", prop("Memory, maximum", listDelimiter)));
		specifications.add(constructSpecification(p, "memoryStd", prop("Memory, standard", listDelimiter)));
		specifications.add(constructSpecification(p,
						"minimumSystemRequirements", prop("Minimum system requirements", listDelimiter)));
		specifications.add(constructSpecification(p,
						"mobilePrintingCapability", prop("Mobile Printing Capability", listDelimiter)));
		specifications.add(constructSpecification(p, "monthlyDutyCycle", prop("Monthly duty cycle", listDelimiter)));
		specifications.add(constructSpecification(p, "networkReady", prop("Network ready", listDelimiter)));
		specifications.add(constructSpecification(p, "postWarranty", prop("Post warranty", listDelimiter)));
		specifications.add(constructSpecification(p, "energyEfficiency", prop("Energy efficiency", listDelimiter)));

		specifications.add(constructSpecification(p, "numberPrintCartridges",
						prop("Number of print cartridges", listDelimiter)));
		specifications.add(constructSpecification(p, "operatingHumidityRange", prop("Operating humidity range", listDelimiter)));
		specifications.add(constructSpecification(p, "operatingTemperatureRange",
						prop("Operating temperature range", listDelimiter)));
		specifications.add(constructSpecification(p, "paperHandlingInputStd",
						prop("Paper handling input, standard", listDelimiter)));
		specifications.add(constructSpecification(p, "paperHandlingOutputStd",
						prop("Paper handling output, standard", listDelimiter)));
		specifications.add(constructSpecification(p, "paperTraysMax",
						String.valueOf(parsePaperTray(prop("Paper trays, maximum", listDelimiter), p))));
		specifications.add(constructSpecification(p, "paperTraysStd",
						String.valueOf(parsePaperTray(prop("Paper trays, standard", listDelimiter), p))));
		specifications.add(constructSpecification(p, "power", prop("Power", listDelimiter)));
		specifications.add(constructSpecification(p, "powerConsumption", prop("Power consumption", listDelimiter)));
		specifications.add(constructSpecification(p, "printTechnology", prop("Print Technology", listDelimiter)));

		String technology = prop("Print Technology", listDelimiter);
		if (highEndPrinterPattern.matcher(p.getProductName()).find()) {
			p.setProductType("HighEnd Printer");
		} else if (technology != null
				&& technology.contains("Laser")) {
			p.setProductType("Laser Printer");
		} else {
			p.setProductType("Inkjet Printer");
		}
		// ----------------------

		specifications.add(constructSpecification(p, "printLanguages", prop("Print languages", listDelimiter)));

		specifications.add(constructSpecification(p, "printSpeedBlackDraft", prop("Print speed, black (draft)", listDelimiter)));
		specifications.add(constructSpecification(p, "printSpeedBlackISO", any(prop("Print speed, black (normal)", listDelimiter),
				prop("Print speed, black (ISO, laser comparable)", listDelimiter))));
		specifications.add(constructSpecification(p, "printSpeedColorDraft", any(prop("Print speed, color (draft)", listDelimiter),
				prop("Print speed, color (draft, 4x6 photo)", listDelimiter))));
		specifications.add(constructSpecification(p, "printSpeedColorISO", any(prop("Print speed, color (normal)", listDelimiter),
				prop("Print speed, color (ISO, laser comparable)", listDelimiter))));

		specifications.add(constructSpecification(p, "printerManagement", prop("Printer management", listDelimiter)));
		specifications.add(constructSpecification(p, "printerPageYield", prop("Printer page yield", listDelimiter)));
		specifications.add(constructSpecification(p, "processorSpeed", prop("Processor speed", listDelimiter)));

		specifications.add(constructSpecification(p, "replacementCartridges", prop("Replacement cartridges", listDelimiter)));
		specifications.add(constructSpecification(p, "resolutionBlack", prop("Resolution (black)", listDelimiter)));
		specifications.add(constructSpecification(p, "resolutionColor", prop("Resolution (color)", listDelimiter)));
		specifications.add(constructSpecification(p, "recommendedMediaWeight", prop("Recommended media weight", listDelimiter)));
		specifications.add(constructSpecification(p, "securityManagement", prop("Security management", listDelimiter)));
		specifications.add(constructSpecification(p, "supportedMediaWeight", prop("Supported media weight", listDelimiter)));
		specifications.add(constructSpecification(p, "supportedNetworkProtocols", prop("Supported network protocols", listDelimiter)));
		specifications.add(constructSpecification(p, "whatsInTheBox", prop("What's in the box", listDelimiter)));
		specifications.add(constructSpecification(p, "fcc", prop("FCC", listDelimiter)));
		specifications.add(constructSpecification(p, "hardDisk", any(prop("Hard disk", listDelimiter), prop("Internal drive", listDelimiter))));

		specifications.add(constructSpecification(p, "adfCapacity", prop("ADF Capacity", true, listDelimiter)));
		specifications.add(constructSpecification(p, "autoDocumentFeeder", prop("Auto document feeder", true, listDelimiter)));
		specifications.add(constructSpecification(p, "batteryRechargeTime", prop("Battery recharge time", true, listDelimiter)));
		specifications.add(constructSpecification(p, "bitDepth", prop("Bit depth", true, listDelimiter)));
		specifications.add(constructSpecification(p, "broadcastLocations", prop("Broadcast locations", true, listDelimiter)));
		specifications.add(constructSpecification(p, "browserSupported", prop("Browser supported", true, listDelimiter)));
		specifications.add(constructSpecification(p, "colorStability", prop("Color stability", true, listDelimiter)));
		specifications.add(constructSpecification(p, "connectivity", prop("Connectivity", true, listDelimiter)));
		specifications.add(constructSpecification(p, "controlPanel", prop("Control panel", true, listDelimiter)));
		specifications.add(constructSpecification(p, "maximumCopies", prop("Copies, maximum", true, listDelimiter)));
		specifications.add(constructSpecification(p, "copyReduceEnlargeSettings", prop("Copy reduce / enlarge settings",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "copyResolutionBlackText", prop("Copy resolution (black text)", true, listDelimiter)));
		specifications.add(constructSpecification(p, "copyResolutionColourTextGraphics", prop(
				"Copy resolution (colour text and graphics)", true, listDelimiter)));
		specifications.add(constructSpecification(p, "copySpeedBlackDraft", prop("Copy speed black (draft)", true, listDelimiter)));
		specifications.add(constructSpecification(p, "copySpeedBlackNormal", prop("Copy speed black (normal)", true, listDelimiter)));
		specifications.add(constructSpecification(p, "copySpeedColorDraft", prop("Copy speed color (draft)", true, listDelimiter)));
		specifications.add(constructSpecification(p, "copySpeedColorNormal", prop("Copy speed color (normal)", true, listDelimiter)));
		specifications.add(constructSpecification(p, "digitalSendFileFormats", prop("Digital send file Formats", true, listDelimiter)));
		specifications.add(constructSpecification(p, "digitalSendingFeatures", prop("Digital sending features", true, listDelimiter)));
		specifications.add(constructSpecification(p, "duplexAdfScanning", prop("Duplex ADF scanning", true, listDelimiter)));
		specifications.add(constructSpecification(p, "energyStar", prop("ENERGY STAR", true, listDelimiter)));
		specifications.add(constructSpecification(p, "embeddedWebServer", prop("Embedded web server", true, listDelimiter)));
		specifications.add(constructSpecification(p, "faxMemory", prop("Fax memory", true, listDelimiter)));
		specifications.add(constructSpecification(p, "faxResolution", prop("Fax resolution", true, listDelimiter)));
		specifications.add(constructSpecification(p, "faxTransmissionSpeed", prop("Fax transmission speed", true, listDelimiter)));
		specifications.add(constructSpecification(p, "faxing", prop("Faxing", true, listDelimiter)));
		specifications.add(constructSpecification(p, "finishedOutputHandling", prop("Finished output handling", true, listDelimiter)));
		specifications.add(constructSpecification(p, "firstPageOutBlack", prop("First page out (ready) black", true, listDelimiter)));
		specifications.add(constructSpecification(p, "firstPageOutColor", prop("First page out (ready) color", true, listDelimiter)));
		specifications.add(constructSpecification(p, "guaranteedMinimumLineWidth", prop("Guaranteed minimum line width",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "inputType", prop("Input type", true, listDelimiter)));
		specifications.add(constructSpecification(p, "lineAccuracy", prop("Line accuracy", true, listDelimiter)));
		specifications.add(constructSpecification(p, "maximumDocumentScanSize", prop("Maximum document scan size", true, listDelimiter)));
		specifications.add(constructSpecification(p, "maximumOpticalDensityBlack", prop("Maximum optical density (black)",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "mptUsColorImageBestModeGlossy", prop(
				"Mechanical print time, US D color image, best mode, glossy",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "mptUsColorImageDraftModeCoated", prop(
				"Mechanical print time, US D color image, draft mode, coated",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "mptUsColorImageNormalModeCoated", prop(
				"Mechanical print time, US D color image, normal mode, coated",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "mptUsColorImageNormalModeGlossy", prop(
				"Mechanical print time, US D color image, normal mode, glossy",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "mptBWLineDrawingDraftModePlain", prop(
				"Mechanical print time, b\u0026w line drawing, draft mode, plain",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "mptColorLineDrawingDraftModePlain", prop(
				"Mechanical print time, color line drawing, draft mode, plain",
				true, listDelimiter)));
		specifications.add(constructSpecification(p, "mptLineDrawingEconomodePlain", prop(
				"Mechanical print time, line drawing, economode, plain", true, listDelimiter)));
		specifications.add(constructSpecification(p, "mediaThickness", prop("Media thickness", true, listDelimiter)));
		specifications.add(constructSpecification(p, "memoryCardCompatibility", prop("Memory card compatibility", true, listDelimiter)));
		specifications.add(constructSpecification(p, "nonPrintableArea", prop("Non-printable area (cut-sheet)", true, listDelimiter)));
		specifications.add(constructSpecification(p, "ports", prop("Ports", true, listDelimiter)));
		specifications.add(constructSpecification(p, "printRepeatability", prop("Print repeatability", true, listDelimiter)));
		specifications.add(constructSpecification(p, "printSpeedMaximum", prop("Print speed, maximum", true, listDelimiter)));
		specifications.add(constructSpecification(p, "resolution", prop("Resolution", true, listDelimiter)));
		specifications.add(constructSpecification(p, "resolutionTechnology", prop("Resolution technology", true, listDelimiter)));
		specifications.add(constructSpecification(p, "rollExternalDiameter", prop("Roll external diameter", true, listDelimiter)));
		specifications.add(constructSpecification(p, "rollMaximumOutput", prop("Roll maximum output", true, listDelimiter)));
		specifications.add(constructSpecification(p, "scanFileFormat", prop("Scan file format", true, listDelimiter)));
		specifications.add(constructSpecification(p, "scanResolutionHardware", prop("Scan resolution, hardware", true, listDelimiter)));
		specifications.add(constructSpecification(p, "scanResolutionOptical", prop("Scan resolution, optical", true, listDelimiter)));
		specifications.add(constructSpecification(p, "scanSizeMaximum", prop("Scan size (ADF), maximum", true, listDelimiter)));
		specifications.add(constructSpecification(p, "scanSizeFlatbedMaximum", prop("Scan size (flatbed), maximum", true, listDelimiter)));
		specifications.add(constructSpecification(p, "scanSpeedMaximum", prop("Scan speed, maximum", true, listDelimiter)));
		specifications.add(constructSpecification(p, "scannableMediaTypes", prop("Scannable media types", true, listDelimiter)));
		specifications.add(constructSpecification(p, "scannerType", prop("Scanner type", true, listDelimiter)));
		specifications.add(constructSpecification(p, "speedDialsMaximumNumber", prop("Speed dials, maximum number", true, listDelimiter)));
		specifications.add(constructSpecification(p, "usColorLineDrawingsDraftPlain", prop(
				"US D color line drawings/hr, draft mode, plain", true, listDelimiter)));

		recommendedMonthlyPageVolume(p, specifications);

		p.setSpecifications(specifications);
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
	private void recommendedMonthlyPageVolume(Product p, Set<ProductSpecification> specifications) {
		String[] rmpvs = props("Recommended monthly page volume");
		String mpvsJoined = (rmpvs == null) ? null : StringUtils.join(rmpvs, "\n");
		specifications.add(constructSpecification(p, "recommMonthlyPageVolume", mpvsJoined));

		if (rmpvs != null) {
			boolean parsed = false;

			for (String rmpv : rmpvs) {
				if (rmpv != null && !rmpv.trim().isEmpty()) {
					Matcher matcher = rmpvPattern.matcher(rmpv);
					if (matcher.find()) {
						specifications.add(constructSpecification(p, "recommMonthlyPageVolumeMin", matcher
								.group(1).replace(",", "")));
						specifications.add(constructSpecification(p, "recommMonthlyPageVolumeMax", matcher
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
