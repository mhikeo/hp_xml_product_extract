package com.hp.inventory.audit.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.model.Desktop;
import com.hp.inventory.audit.parser.model.Laptop;
import com.hp.inventory.audit.parser.model.Printer;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.PrinterParser;

public class PrinterParserTest extends ParserTest {
	private PrinterParser parser;
	private JSONResultHandler rh;

	@Before
	public void init() {
		parser = new PrinterParser();
		rh = new JSONResultHandler();
		rh.beforeStart();
	}

	@Test
	public void shouldParseCurrency() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		assertEquals("USD", printer.getCurrency());
	}

	@Test
	public void shouldParseTypeInkjet() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		assertEquals("Inkjet", printer.getType());
	}

	@Test
	public void shouldParseTypeLaser() throws IOException {
		Product product = findProduct("productPage12.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		assertEquals("Laser", printer.getType());
	}
	
	@Test
	public void shouldParsePrintTechnology() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		assertEquals("HP Thermal Inkjet", printer.getPrintTechnology());
	}

	@Test
	public void shouldParseAndPopulateRatingAndReviewsCount() throws IOException {
		Product product = findProduct("productPage730.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		assertEquals(Integer.valueOf(4), printer.getRating());
		
		printer.populateCommonsToProduct(product);
		assertEquals(Integer.valueOf(4), product.getRating());
		
		assertEquals(Integer.valueOf(89), product.getNumberOfReviews());
	}

	@Test
	public void shouldParseAndPopulateProductName() throws Exception {
		Product product = findProduct("productPage730.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		assertEquals("HP Officejet Pro 8610 e-All-in-One Printer", printer.getProductName());
		
		printer.populateCommonsToProduct(product);
		assertEquals("HP Officejet Pro 8610 e-All-in-One Printer", product.getProductName());
	}
	
	@Test
	public void shouldPopulateProductTypeLaser() throws Exception {
		Product product = findProduct("productPage12.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		printer.populateCommonsToProduct(product);
		
		assertEquals("Laser Printer", product.getProductType());
	}
	@Test
	public void shouldPopulateProductTypeInkjet() throws Exception {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		printer.populateCommonsToProduct(product);
		
		assertEquals("Inkjet Printer", product.getProductType());
	}
	
	@Test
	public void shouldParseAndPopulateCurrentPrice() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new BigDecimal("99.99"), printer.getCurrentPrice());
		
		printer.populateCommonsToProduct(product);
		
		assertEquals(new BigDecimal("99.99"), product.getCurrentPrice());
	}
	
	@Test
	public void shouldParseAndPopulateStrikedPrice() throws IOException {
		Product product = findProduct("productPage2499.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new BigDecimal("1999.99"), printer.getStrikedPrice());
		
		printer.populateCommonsToProduct(product);
		
		assertEquals(new BigDecimal("1999.99"), product.getStrikedPrice());
	}
	
	
	@Test
	public void shouldParseAdfCapacity() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getAdfCapacity());
		assertEquals("Standard, 35 sheets", printer.getAdfCapacity());
	}

	@Test
	public void shouldParseAutoDocumentFeeder() throws IOException {
		Product product = findProduct("productPage350.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getAutoDocumentFeeder());
		assertEquals("None", printer.getAutoDocumentFeeder());
	}

	@Test
	public void shouldParseBatteryRechargeTime() throws IOException {
		Product product = findProduct("productPage1771.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getBatteryRechargeTime());
		assertEquals("Approximately 3 hours (fully charged)", printer.getBatteryRechargeTime());
	}

	@Test
	public void shouldParseBitDepth() throws IOException {
		Product product = findProduct("productPage350.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getBitDepth());
		assertEquals("48-bit", printer.getBitDepth());
	}

	@Test
	public void shouldParseBroadcastLocations() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getBroadcastLocations());
		assertEquals("20 locations", printer.getBroadcastLocations());
	}

	@Test
	public void shouldParseBrowserSupported() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getBrowserSupported());
		assertEquals(
				"Internet Explorer 5.0 and higher, Netscape 6.0.1 and higher, Safari for Mac OS 10.3 and higher, Mozilla 1.5 and higher",
				printer.getBrowserSupported());
	}

	@Test
	public void shouldParseColorStability() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getColorStability());
		assertEquals("< 1 dE2000 in less than 5 minutes", printer.getColorStability());
	}

	@Test
	public void shouldParseConnectivity() throws IOException {
		Product product = findProduct("productPage350.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getConnectivity());
		assertEquals("1 Hi-Speed USB 2.0", printer.getConnectivity());
	}

	@Test
	public void shouldParseControlPanel() throws IOException {
		Product product = findProduct("productPage350.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getControlPanel());
		assertEquals("4 front-panel buttons (Copy, Scan, Scan to E-mail, Scan to PDF)", printer.getControlPanel());
	}

	@Test
	public void shouldParseCopyReduceEnlargeSettings() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getCopyReduceEnlargeSettings());
		assertEquals("25 to 400%", printer.getCopyReduceEnlargeSettings());
	}

	@Test
	public void shouldParseCopyResolutionBlackText() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getCopyResolutionBlackText());
		assertEquals("Up to 600 x 600 dpi", printer.getCopyResolutionBlackText());
	}

	@Test
	public void shouldParseCopyResolutionColourTextGraphics() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getCopyResolutionColourTextGraphics());
		assertEquals("Up to 600 x 600 dpi", printer.getCopyResolutionColourTextGraphics());
	}

	@Test
	public void shouldParseCopySpeedBlackDraft() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getCopySpeedBlackDraft());
		assertEquals("Up to 28 cpm", printer.getCopySpeedBlackDraft());
	}

	@Test
	public void shouldParseCopySpeedBlackNormal() throws IOException {
		Product product = findProduct("productPage307.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getCopySpeedBlackNormal());
		assertEquals("Up to 6 cpm", printer.getCopySpeedBlackNormal());
	}

	@Test
	public void shouldParseCopySpeedColorDraft() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getCopySpeedColorDraft());
		assertEquals("Up to 24 cpm", printer.getCopySpeedColorDraft());
	}

	@Test
	public void shouldParseCopySpeedColorNormal() throws IOException {
		Product product = findProduct("productPage307.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getCopySpeedColorNormal());
		assertEquals("Up to 4 cpm", printer.getCopySpeedColorNormal());
	}

	@Test
	public void shouldParseDigitalSendFileFormats() throws IOException {
		Product product = findProduct("productPage307.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getDigitalSendFileFormats());
		assertEquals("PDF; BMP; PNG; TIF; JPG", printer.getDigitalSendFileFormats());
	}

	@Test
	public void shouldParseDigitalSendingFeatures() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getDigitalSendingFeatures());
		assertEquals("Scan to thumb drive/PC", printer.getDigitalSendingFeatures());
	}

	@Test
	public void shouldParseDuplexAdfScanning() throws IOException {
		Product product = findProduct("productPage383.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getDuplexAdfScanning());
		assertEquals("Yes", printer.getDuplexAdfScanning());
	}

	@Test
	public void shouldParseEmbeddedWebServer() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getEmbeddedWebServer());
		assertEquals("Yes", printer.getEmbeddedWebServer());
	}

	@Test
	public void shouldParseEnergyStar() throws IOException {
		Product product = findProduct("productPage350.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getEnergyStar());
		assertEquals("ENERGY STAR® qualified; EPEAT ® Bronze", printer.getEnergyStar());
	}

	@Test
	public void shouldParseFaxMemory() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getFaxMemory());
		assertEquals("Up to 100 pages", printer.getFaxMemory());
	}

	@Test
	public void shouldParseFaxResolution() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getFaxResolution());
		assertEquals("Up to 300 x 300 dpi", printer.getFaxResolution());
	}

	@Test
	public void shouldParseFaxTransmissionSpeed() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getFaxTransmissionSpeed());
		assertEquals(
				"4 sec per page [3] Based on standard ITU-T Test Image No. 1 at standard resolution. More complicated pages or higher resolution will take longer and use more memory.",
				printer.getFaxTransmissionSpeed());
	}

	@Test
	public void shouldParseFaxing() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getFaxing());
		assertEquals("Yes, color", printer.getFaxing());
	}

	@Test
	public void shouldParseFinishedOutputHandling() throws IOException {
		Product product = findProduct("productPage12.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getFinishedOutputHandling());
		assertEquals("Sheetfed", printer.getFinishedOutputHandling());
	}

	@Test
	public void shouldParseFirstPageOutBlack() throws IOException {
		Product product = findProduct("productPage12.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getFirstPageOutBlack());
		assertEquals("As fast as 9.5 sec", printer.getFirstPageOutBlack());
	}

	@Test
	public void shouldParseFirstPageOutColor() throws IOException {
		Product product = findProduct("productPage13.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getFirstPageOutColor());
		assertEquals("As fast as 13 sec", printer.getFirstPageOutColor());
	}

	@Test
	public void shouldParseGuaranteedMinimumLineWidth() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getGuaranteedMinimumLineWidth());
		assertEquals("0.0022 in (ISO/IEC 13660:2001(E))", printer.getGuaranteedMinimumLineWidth());
	}

	@Test
	public void shouldParseInputType() throws IOException {
		Product product = findProduct("productPage350.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getInputType());
		assertEquals("Flatbed", printer.getInputType());
	}

	@Test
	public void shouldParseLineAccuracy() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getLineAccuracy());
		assertEquals("+/- 0.2%", printer.getLineAccuracy());
	}

	@Test
	public void shouldParseMaximumCopies() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMaximumCopies());
		assertEquals("Up to 99 copies", printer.getMaximumCopies());
	}

	@Test
	public void shouldParseMaximumDocumentScanSize() throws IOException {
		Product product = findProduct("productPage351.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMaximumDocumentScanSize());
		assertEquals("8.5 x 122 in", printer.getMaximumDocumentScanSize());
	}

	@Test
	public void shouldParseMaximumOpticalDensityBlack() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMaximumOpticalDensityBlack());
		assertEquals("4 L* min/2.5 D (with HP Premium Instant Dry Photo Gloss media with Original HP inks)",
				printer.getMaximumOpticalDensityBlack());
	}

	@Test
	public void shouldParseMediaThickness() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMediaThickness());
		assertEquals("Up to 31.5 mil", printer.getMediaThickness());
	}

	@Test
	public void shouldParseMemoryCardCompatibility() throws IOException {
		Product product = findProduct("productPage15.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMemoryCardCompatibility());
		assertEquals("Thumb drive", printer.getMemoryCardCompatibility());
	}

	@Test
	public void shouldParseMptBWLineDrawingDraftModePlain() throws IOException {
		Product product = findProduct("productPage1592.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMptBWLineDrawingDraftModePlain());
		assertEquals("70 D prints per hour", printer.getMptBWLineDrawingDraftModePlain());
	}

	@Test
	public void shouldParseMptColorLineDrawingDraftModePlain() throws IOException {
		Product product = findProduct("productPage1592.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMptColorLineDrawingDraftModePlain());
		assertEquals("35 sec/page", printer.getMptColorLineDrawingDraftModePlain());
	}

	@Test
	public void shouldParseMptUsColorImageBestModeGlossy() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMptUsColorImageBestModeGlossy());
		assertEquals("12.4 min/page", printer.getMptUsColorImageBestModeGlossy());
	}

	@Test
	public void shouldParseMptUsColorImageDraftModeCoated() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMptUsColorImageDraftModeCoated());
		assertEquals("2 min/page", printer.getMptUsColorImageDraftModeCoated());
	}

	@Test
	public void shouldParseMptUsColorImageNormalModeCoated() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMptUsColorImageNormalModeCoated());
		assertEquals("3.8 min/page", printer.getMptUsColorImageNormalModeCoated());
	}

	@Test
	public void shouldParseMptUsColorImageNormalModeGlossy() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMptUsColorImageNormalModeGlossy());
		assertEquals("7.2 min/page", printer.getMptUsColorImageNormalModeGlossy());
	}

	@Test
	public void shouldParseMptLineDrawingEconomodePlain() throws IOException {
		Product product = findProduct("productPage1592.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getMptLineDrawingEconomodePlain());
		assertEquals("70 D prints per hour", printer.getMptLineDrawingEconomodePlain());
	}

	@Test
	public void shouldParseNonPrintableArea() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getNonPrintableArea());
		assertEquals("0.2 x 0.67 x 0.2 x 0.2 in", printer.getNonPrintableArea());
	}

	@Test
	public void shouldParsePorts() throws IOException {
		Product product = findProduct("productPage12.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getPorts());
		assertEquals("1 Hi-Speed USB 2.0; 1 Fast Ethernet 10/100Base-TX", printer.getPorts());
	}

	@Test
	public void shouldParsePrintRepeatability() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getPrintRepeatability());
		assertEquals("Average < 0.5 dE2000, 95% of colors < 1.4 dE2000", printer.getPrintRepeatability());
	}

	@Test
	public void shouldParsePrintSpeedMaximum() throws IOException {
		Product product = findProduct("productPage1594.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getPrintSpeedMaximum());
		assertEquals("570 ft²/hr", printer.getPrintSpeedMaximum());
	}

	@Test
	public void shouldParseResolution() throws IOException {
		Product product = findProduct("productPage350.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getResolution());
		assertEquals("Up to 4800 x 9600 dpi", printer.getResolution());
	}

	@Test
	public void shouldParseResolutionTechnology() throws IOException {
		Product product = findProduct("productPage12.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getResolutionTechnology());
		assertEquals("FastRes 600; FastRes 1200", printer.getResolutionTechnology());
	}

	@Test
	public void shouldParseRollExternalDiameter() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getRollExternalDiameter());
		assertEquals("5.3 in", printer.getRollExternalDiameter());
	}

	@Test
	public void shouldParseRollMaximumOutput() throws IOException {
		Product product = findProduct("productPage379.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getRollMaximumOutput());
		assertEquals("300 ft", printer.getRollMaximumOutput());
	}

	@Test
	public void shouldParseScanFileFormat() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getScanFileFormat());
		assertEquals(
				"Scan File Type supported by Software: Bitmap (.bmp), JPEG (.jpg), PDF (.pdf), PNG (.png), Rich Text (.rtf), Searchable PDF (.pdf), Text (.txt), TIFF (.tif)",
				printer.getScanFileFormat());
	}

	@Test
	public void shouldParseScanResolutionHardware() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getScanResolutionHardware());
		assertEquals("Up to 1200 x 1200 dpi", printer.getScanResolutionHardware());
	}

	@Test
	public void shouldParseScanResolutionOptical() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getScanResolutionOptical());
		assertEquals("Up to 1200 dpi", printer.getScanResolutionOptical());
	}

	@Test
	public void shouldParseScanSizeFlatbedMaximum() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getScanSizeFlatbedMaximum());
		assertEquals("8.5 x 11.7 in", printer.getScanSizeFlatbedMaximum());
	}

	@Test
	public void shouldParseScanSizeMaximum() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getScanSizeMaximum());
		assertEquals("8.5 x 14 in", printer.getScanSizeMaximum());
	}

	@Test
	public void shouldParseScanSpeedMaximum() throws IOException {
		Product product = findProduct("productPage1605.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getScanSpeedMaximum());
		assertEquals("Up to 1.5 in/sec (color, 200 dpi); up to 4.5 in/sec (grayscale, 200 dpi)",
				printer.getScanSpeedMaximum());
	}

	@Test
	public void shouldParseScannableMediaTypes() throws IOException {
		Product product = findProduct("productPage350.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getScannableMediaTypes());
		assertEquals(
				"Paper (inkjet, laser, plain), photographic material (silver halide, pigment-dye), 3-D objects, 35 mm slides and negatives (using transparent media adapter)",
				printer.getScannableMediaTypes());
	}

	@Test
	public void shouldParseScannerType() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getScannerType());
		assertEquals("Flatbed, ADF", printer.getScannerType());
	}

	@Test
	public void shouldParseSpeedDialsMaximumNumber() throws IOException {
		Product product = findProduct("productPage14.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getSpeedDialsMaximumNumber());
		assertEquals("99", printer.getSpeedDialsMaximumNumber());
	}

	@Test
	public void shouldParseUsColorLineDrawingsDraftPlain() throws IOException {
		Product product = findProduct("productPage1592.html");
		Printer printer = (Printer) parser.parse(parseHtml(product), product, rh);
		System.out.println(printer.getUsColorLineDrawingsDraftPlain());
		assertEquals("70 D prints per hour", printer.getUsColorLineDrawingsDraftPlain());
	}

}
