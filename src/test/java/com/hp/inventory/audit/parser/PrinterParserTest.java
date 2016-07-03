/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.PrinterParser;

public class PrinterParserTest extends ParserTest {
	private static final Integer HP = 1;
	private PrinterParser parser;
	private Config config;

	@Before
	public void init() {
		parser = new PrinterParser();
		config = new Config();
		config.resultHandler = new JSONResultHandler();
		config.resultHandler.beforeStart();
	}

	@Test
	public void shouldParseCurrency() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		assertEquals("USD", printer.getPrices().get(HP).getCurrency());
	}

	@Test
	public void shouldParseTypeInkjet() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		assertEquals("Inkjet Printer", printer.getProductType());
	}

	@Test
	public void shouldParseTypeLaser() throws Exception {
		Product product = findProduct("productPage12.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		assertEquals("Laser Printer", printer.getProductType());
	}

	@Test
	public void shouldParsePrintTechnology() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		assertEquals("HP Thermal Inkjet", getSpecification(printer, "printTechnology"));
	}

	@Test
	public void shouldParseAndPopulateRatingAndReviewsCount() throws Exception {
		Product product = findProduct("productPage730.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		assertEquals(Integer.valueOf(4), printer.getRatings().get(HP).getRating());
		assertEquals(Integer.valueOf(89), printer.getRatings().get(HP).getNumberOfReviews());
	}

	@Test
	public void shouldParseAndPopulateProductName() throws Exception {
		Product product = findProduct("productPage730.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		assertEquals("HP Officejet Pro 8610 e-All-in-One Printer", printer.getProductName());

		printer.populateCommonsToProduct(product);
		assertEquals("HP Officejet Pro 8610 e-All-in-One Printer", product.getProductName());
	}

	@Test
	public void shouldPopulateProductTypeLaser() throws Exception {
		Product product = findProduct("productPage12.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		printer.populateCommonsToProduct(product);

		assertEquals("Laser Printer", product.getProductType());
	}
	@Test
	public void shouldPopulateProductTypeInkjet() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		printer.populateCommonsToProduct(product);

		assertEquals("Inkjet Printer", product.getProductType());
	}

	@Test
	public void shouldParseAndPopulateCurrentPrice() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(new BigDecimal("99.99"), printer.getPrices().get(HP).getCurrentPrice());
	}

	@Test
	public void shouldParseAndPopulateStrikedPrice() throws Exception {
		Product product = findProduct("productPage1280.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(new BigDecimal("2453.00"), printer.getPrices().get(HP).getStrikedPrice());
	}


	@Test
	public void shouldParseAdfCapacity() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "adfCapacity"));
		assertEquals("Standard, 35 sheets", getSpecification(printer, "adfCapacity"));
	}

	@Test
	public void shouldParseAutoDocumentFeeder() throws Exception {
		Product product = findProduct("productPage350.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "autoDocumentFeeder"));
		assertEquals("None", getSpecification(printer, "autoDocumentFeeder"));
	}

	@Test
	public void shouldParseBatteryRechargeTime() throws Exception {
		Product product = findProduct("productPage1771.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "batteryRechargeTime"));
		assertEquals("Approximately 3 hours (fully charged)", getSpecification(printer, "batteryRechargeTime"));
	}

	@Test
	public void shouldParseBitDepth() throws Exception {
		Product product = findProduct("productPage350.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "bitDepth"));
		assertEquals("48-bit", getSpecification(printer, "bitDepth"));
	}

	@Test
	public void shouldParseBroadcastLocations() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "broadcastLocations"));
		assertEquals("20 locations", getSpecification(printer, "broadcastLocations"));
	}

	@Test
	public void shouldParseBrowserSupported() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "browserSupported"));
		assertEquals(
				"Internet Explorer 5.0 and higher, Netscape 6.0.1 and higher, Safari for Mac OS 10.3 and higher, Mozilla 1.5 and higher",
				getSpecification(printer, "browserSupported"));
	}

	@Test
	public void shouldParseColorStability() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "colorStability"));
		assertEquals("< 1 dE2000 in less than 5 minutes", getSpecification(printer, "colorStability"));
	}

	@Test
	public void shouldParseConnectivity() throws Exception {
		Product product = findProduct("productPage350.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "connectivity"));
		assertEquals("1 Hi-Speed USB 2.0", getSpecification(printer, "connectivity"));
	}

	@Test
	public void shouldParseControlPanel() throws Exception {
		Product product = findProduct("productPage350.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "controlPanel"));
		assertEquals("4 front-panel buttons (Copy, Scan, Scan to E-mail, Scan to PDF)", getSpecification(printer, "controlPanel"));
	}

	@Test
	public void shouldParseCopyReduceEnlargeSettings() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "copyReduceEnlargeSettings"));
		assertEquals("25 to 400%", getSpecification(printer, "copyReduceEnlargeSettings"));
	}

	@Test
	public void shouldParseCopyResolutionBlackText() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "copyResolutionBlackText"));
		assertEquals("Up to 600 x 600 dpi", getSpecification(printer, "copyResolutionBlackText"));
	}

	@Test
	public void shouldParseCopyResolutionColourTextGraphics() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "copyResolutionColourTextGraphics"));
		assertEquals("Up to 600 x 600 dpi", getSpecification(printer, "copyResolutionColourTextGraphics"));
	}

	@Test
	public void shouldParseCopySpeedBlackDraft() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "copySpeedBlackDraft"));
		assertEquals("Up to 28 cpm", getSpecification(printer, "copySpeedBlackDraft"));
	}

	@Test
	public void shouldParseCopySpeedBlackNormal() throws Exception {
		Product product = findProduct("productPage307.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "copySpeedBlackNormal"));
		assertEquals("Up to 6 cpm", getSpecification(printer, "copySpeedBlackNormal"));
	}

	@Test
	public void shouldParseCopySpeedColorDraft() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "copySpeedColorDraft"));
		assertEquals("Up to 24 cpm", getSpecification(printer, "copySpeedColorDraft"));
	}

	@Test
	public void shouldParseCopySpeedColorNormal() throws Exception {
		Product product = findProduct("productPage307.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "copySpeedColorNormal"));
		assertEquals("Up to 4 cpm", getSpecification(printer, "copySpeedColorNormal"));
	}

	@Test
	public void shouldParseDigitalSendFileFormats() throws Exception {
		Product product = findProduct("productPage307.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "digitalSendFileFormats"));
		assertEquals("PDF; BMP; PNG; TIF; JPG", getSpecification(printer, "digitalSendFileFormats"));
	}

	@Test
	public void shouldParseDigitalSendingFeatures() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "digitalSendingFeatures"));
		assertEquals("Scan to thumb drive/PC", getSpecification(printer, "digitalSendingFeatures"));
	}

	@Test
	public void shouldParseDuplexAdfScanning() throws Exception {
		Product product = findProduct("productPage383.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "duplexAdfScanning"));
		assertEquals("Yes", getSpecification(printer, "duplexAdfScanning"));
	}

	@Test
	public void shouldParseEmbeddedWebServer() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "embeddedWebServer"));
		assertEquals("Yes", getSpecification(printer, "embeddedWebServer"));
	}

	@Test
	public void shouldParseEnergyStar() throws Exception {
		Product product = findProduct("productPage350.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "energyStar"));
		assertEquals("ENERGY STAR® qualified; EPEAT ® Bronze", getSpecification(printer, "energyStar"));
	}

	@Test
	public void shouldParseFaxMemory() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "faxMemory"));
		assertEquals("Up to 100 pages", getSpecification(printer, "faxMemory"));
	}

	@Test
	public void shouldParseFaxResolution() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "faxResolution"));
		assertEquals("Up to 300 x 300 dpi", getSpecification(printer, "faxResolution"));
	}

	@Test
	public void shouldParseFaxTransmissionSpeed() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "faxTransmissionSpeed"));
		assertEquals(
				"4 sec per page [3] Based on standard ITU-T Test Image No. 1 at standard resolution. More complicated pages or higher resolution will take longer and use more memory.",
				getSpecification(printer, "faxTransmissionSpeed"));
	}

	@Test
	public void shouldParseFaxing() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "faxing"));
		assertEquals("Yes, color", getSpecification(printer, "faxing"));
	}

	@Test
	public void shouldParseFinishedOutputHandling() throws Exception {
		Product product = findProduct("productPage12.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "finishedOutputHandling"));
		assertEquals("Sheetfed", getSpecification(printer, "finishedOutputHandling"));
	}

	@Test
	public void shouldParseFirstPageOutBlack() throws Exception {
		Product product = findProduct("productPage12.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "firstPageOutBlack"));
		assertEquals("As fast as 9.5 sec", getSpecification(printer, "firstPageOutBlack"));
	}

	@Test
	public void shouldParseFirstPageOutColor() throws Exception {
		Product product = findProduct("productPage13.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "firstPageOutColor"));
		assertEquals("As fast as 13 sec", getSpecification(printer, "firstPageOutColor"));
	}

	@Test
	public void shouldParseGuaranteedMinimumLineWidth() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "guaranteedMinimumLineWidth"));
		assertEquals("0.0022 in (ISO/IEC 13660:2001(E))", getSpecification(printer, "guaranteedMinimumLineWidth"));
	}

	@Test
	public void shouldParseInputType() throws Exception {
		Product product = findProduct("productPage350.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "inputType"));
		assertEquals("Flatbed", getSpecification(printer, "inputType"));
	}

	@Test
	public void shouldParseLineAccuracy() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "lineAccuracy"));
		assertEquals("+/- 0.2%", getSpecification(printer, "lineAccuracy"));
	}

	@Test
	public void shouldParseMaximumCopies() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "maximumCopies"));
		assertEquals("Up to 99 copies", getSpecification(printer, "maximumCopies"));
	}

	@Test
	public void shouldParseMaximumDocumentScanSize() throws Exception {
		Product product = findProduct("productPage351.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "maximumDocumentScanSize"));
		assertEquals("8.5 x 122 in", getSpecification(printer, "maximumDocumentScanSize"));
	}

	@Test
	public void shouldParseMaximumOpticalDensityBlack() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "maximumOpticalDensityBlack"));
		assertEquals("4 L* min/2.5 D (with HP Premium Instant Dry Photo Gloss media with Original HP inks)",
				getSpecification(printer, "maximumOpticalDensityBlack"));
	}

	@Test
	public void shouldParseMediaThickness() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "mediaThickness"));
		assertEquals("Up to 31.5 mil", getSpecification(printer, "mediaThickness"));
	}

	@Test
	public void shouldParseMemoryCardCompatibility() throws Exception {
		Product product = findProduct("productPage15.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "memoryCardCompatibility"));
		assertEquals("Thumb drive", getSpecification(printer, "memoryCardCompatibility"));
	}

	@Test
	public void shouldParseMptBWLineDrawingDraftModePlain() throws Exception {
		Product product = findProduct("productPage1592.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "mptBWLineDrawingDraftModePlain"));
		assertEquals("70 D prints per hour", getSpecification(printer, "mptBWLineDrawingDraftModePlain"));
	}

	@Test
	public void shouldParseMptColorLineDrawingDraftModePlain() throws Exception {
		Product product = findProduct("productPage1592.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "mptColorLineDrawingDraftModePlain"));
		assertEquals("35 sec/page", getSpecification(printer, "mptColorLineDrawingDraftModePlain"));
	}

	@Test
	public void shouldParseMptUsColorImageBestModeGlossy() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "mptUsColorImageBestModeGlossy"));
		assertEquals("12.4 min/page", getSpecification(printer, "mptUsColorImageBestModeGlossy"));
	}

	@Test
	public void shouldParseMptUsColorImageDraftModeCoated() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "mptUsColorImageDraftModeCoated"));
		assertEquals("2 min/page", getSpecification(printer, "mptUsColorImageDraftModeCoated"));
	}

	@Test
	public void shouldParseMptUsColorImageNormalModeCoated() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "mptUsColorImageNormalModeCoated"));
		assertEquals("3.8 min/page", getSpecification(printer, "mptUsColorImageNormalModeCoated"));
	}

	@Test
	public void shouldParseMptUsColorImageNormalModeGlossy() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "mptUsColorImageNormalModeGlossy"));
		assertEquals("7.2 min/page", getSpecification(printer, "mptUsColorImageNormalModeGlossy"));
	}

	@Test
	public void shouldParseMptLineDrawingEconomodePlain() throws Exception {
		Product product = findProduct("productPage1592.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "mptLineDrawingEconomodePlain"));
		assertEquals("70 D prints per hour", getSpecification(printer, "mptLineDrawingEconomodePlain"));
	}

	@Test
	public void shouldParseNonPrintableArea() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "nonPrintableArea"));
		assertEquals("0.2 x 0.67 x 0.2 x 0.2 in", getSpecification(printer, "nonPrintableArea"));
	}

	@Test
	public void shouldParsePorts() throws Exception {
		Product product = findProduct("productPage12.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "ports"));
		assertEquals("1 Hi-Speed USB 2.0; 1 Fast Ethernet 10/100Base-TX", getSpecification(printer, "ports"));
	}

	@Test
	public void shouldParsePrintRepeatability() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "printRepeatability"));
		assertEquals("Average < 0.5 dE2000, 95% of colors < 1.4 dE2000", getSpecification(printer, "printRepeatability"));
	}

	@Test
	public void shouldParsePrintSpeedMaximum() throws Exception {
		Product product = findProduct("productPage1594.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "printSpeedMaximum"));
		assertEquals("570 ft²/hr", getSpecification(printer, "printSpeedMaximum"));
	}

	@Test
	public void shouldParseResolution() throws Exception {
		Product product = findProduct("productPage350.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "resolution"));
		assertEquals("Up to 4800 x 9600 dpi", getSpecification(printer, "resolution"));
	}

	@Test
	public void shouldParseResolutionTechnology() throws Exception {
		Product product = findProduct("productPage12.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "resolutionTechnology"));
		assertEquals("FastRes 600; FastRes 1200", getSpecification(printer, "resolutionTechnology"));
	}

	@Test
	public void shouldParseRollExternalDiameter() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "rollExternalDiameter"));
		assertEquals("5.3 in", getSpecification(printer, "rollExternalDiameter"));
	}

	@Test
	public void shouldParseRollMaximumOutput() throws Exception {
		Product product = findProduct("productPage379.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "rollMaximumOutput"));
		assertEquals("300 ft", getSpecification(printer, "rollMaximumOutput"));
	}

	@Test
	public void shouldParseScanFileFormat() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "scanFileFormat"));
		assertEquals(
				"Scan File Type supported by Software: Bitmap (.bmp), JPEG (.jpg), PDF (.pdf), PNG (.png), Rich Text (.rtf), Searchable PDF (.pdf), Text (.txt), TIFF (.tif)",
				getSpecification(printer, "scanFileFormat"));
	}

	@Test
	public void shouldParseScanResolutionHardware() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "scanResolutionHardware"));
		assertEquals("Up to 1200 x 1200 dpi", getSpecification(printer, "scanResolutionHardware"));
	}

	@Test
	public void shouldParseScanResolutionOptical() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "scanResolutionOptical"));
		assertEquals("Up to 1200 dpi", getSpecification(printer, "scanResolutionOptical"));
	}

	@Test
	public void shouldParseScanSizeFlatbedMaximum() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "scanSizeFlatbedMaximum"));
		assertEquals("8.5 x 11.7 in", getSpecification(printer, "scanSizeFlatbedMaximum"));
	}

	@Test
	public void shouldParseScanSizeMaximum() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "scanSizeMaximum"));
		assertEquals("8.5 x 14 in", getSpecification(printer, "scanSizeMaximum"));
	}

	@Test
	public void shouldParseScanSpeedMaximum() throws Exception {
		Product product = findProduct("productPage1605.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "scanSpeedMaximum"));
		assertEquals("Up to 1.5 in/sec (color, 200 dpi); up to 4.5 in/sec (grayscale, 200 dpi)",
				getSpecification(printer, "scanSpeedMaximum"));
	}

	@Test
	public void shouldParseScannableMediaTypes() throws Exception {
		Product product = findProduct("productPage350.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "scannableMediaTypes"));
		assertEquals(
				"Paper (inkjet, laser, plain), photographic material (silver halide, pigment-dye), 3-D objects, 35 mm slides and negatives (using transparent media adapter)",
				getSpecification(printer, "scannableMediaTypes"));
	}

	@Test
	public void shouldParseScannerType() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "scannerType"));
		assertEquals("Flatbed, ADF", getSpecification(printer, "scannerType"));
	}

	@Test
	public void shouldParseSpeedDialsMaximumNumber() throws Exception {
		Product product = findProduct("productPage14.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "speedDialsMaximumNumber"));
		assertEquals("99", getSpecification(printer, "speedDialsMaximumNumber"));
	}

	@Test
	public void shouldParseUsColorLineDrawingsDraftPlain() throws Exception {
		Product product = findProduct("productPage1592.html");
		Product printer = (Product) parser.parse(parseHtml(product), product, config);
		System.out.println(getSpecification(printer, "usColorLineDrawingsDraftPlain"));
		assertEquals("70 D prints per hour", getSpecification(printer, "usColorLineDrawingsDraftPlain"));
	}

}
