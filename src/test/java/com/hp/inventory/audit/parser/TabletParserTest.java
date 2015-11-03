package com.hp.inventory.audit.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.model.Desktop;
import com.hp.inventory.audit.parser.model.Printer;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.Tablet;
import com.hp.inventory.audit.parser.parsers.TabletParser;

public class TabletParserTest extends ParserTest {
	private TabletParser parser;
	private JSONResultHandler rh;

	@Before
	public void init() {
		parser = new TabletParser();
		rh = new JSONResultHandler();
		rh.beforeStart();
	}

	@Test
	public void shouldParseCurrency() throws IOException {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("USD", tablet.getCurrency());
	}
	
	@Test
	public void shouldPopulateProductName() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		tablet.populateCommonsToProduct(product);
		
		assertEquals("HP Elite x2 1011 G1 Tablet with Power Keyboard", product.getProductName());
	}
	@Test
	public void shouldPopulateProductType() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		tablet.populateCommonsToProduct(product);
		
		assertEquals("Tablet", product.getProductType());
	}
	
	@Test
	public void shouldParseAndPopulateCurrentPrice() throws IOException {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new BigDecimal("1629.00"), tablet.getCurrentPrice());
		
		tablet.populateCommonsToProduct(product);
		
		assertEquals(new BigDecimal("1629.00"), product.getCurrentPrice());
	}
	
	@Test
	public void shouldParseAndPopulateStrikedPrice() throws IOException {
		Product product = findProduct("productPage29.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new BigDecimal("699.99"), tablet.getStrikedPrice());
		
		tablet.populateCommonsToProduct(product);
		
		assertEquals(new BigDecimal("699.99"), product.getStrikedPrice());
	}
	
	@Test
	public void shouldParseAndPopulateRatingAndReviewsCount() throws IOException {
		Product product = findProduct("productPage549.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(Integer.valueOf(3), tablet.getRating());
		
		tablet.populateCommonsToProduct(product);
		assertEquals(Integer.valueOf(3), product.getRating());
		assertEquals(Integer.valueOf(11), product.getNumberOfReviews());
	}
	
	@Test
	public void shouldParseChipset() throws IOException {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("Chipset is integrated with processor", tablet.getChipset());
	}

	@Test
	public void shouldParseEnergyEfficiency() throws IOException {
		Product product = findProduct("productPage1082.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("ENERGY STAR® certified", tablet.getEnergyEfficiency());
	}

	@Test
	public void shouldParseGraphics() throws IOException {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("Intel® HD Graphics 5300", tablet.getGraphics());
	}

	@Test
	public void shouldParseInternalDrive() throws IOException {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("256 GB M.2 SSD", tablet.getInternalDrive());
	}

	@Test
	public void shouldParsePowerSupply() throws IOException {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("45 W Smart AC adapter; HP ElitePad 4.5 mm AC Adapter (tablet)", tablet.getPowerSupply());
	}

	@Test
	public void shouldParseProcessorFamily() throws IOException {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("Intel® Core™ M processor", tablet.getProcessorFamily());
	}
	
	@Test
	public void shouldParseProcessorTechnology() throws IOException {
		Product product = findProduct("productPage201.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("Intel® Core™ M with vPro™ technology", tablet.getProcessorTechnology());
	}
	
	@Test
	public void shouldParseSecurityManagement() throws IOException {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, rh);
		assertEquals("TPM 1.2/2.0 (Infineon, soldered down); HP Fingerprint Reader (power keyboard); Integrated smart card reader (power keyboard); Preboot Authentication (password, Smart Card); HP Client Security; HP File Sanitizer; Drive Encryption; Absolute Persistence Module (models with Windows only); HP Device Access Manager with Just in Time Authentication; HP SpareKey; Microsoft Security Defender (models with Windows 8.1 only)", tablet.getSecurityManagement());
	}
	
}
