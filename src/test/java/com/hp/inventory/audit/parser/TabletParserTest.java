/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.Tablet;
import com.hp.inventory.audit.parser.parsers.TabletParser;

public class TabletParserTest extends ParserTest {
	private static final Integer HP = 1;
	private TabletParser parser;
	private Config config;

	@Before
	public void init() {
		parser = new TabletParser();
		config = new Config();
		config.resultHandler = new JSONResultHandler();
		config.resultHandler.beforeStart();
	}

	@Test
	public void shouldParseCurrency() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("USD", tablet.getProduct().getPrices().get(HP).getCurrency());
	}
	
	@Test
	public void shouldPopulateProductName() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		tablet.populateCommonsToProduct(product);
		
		assertEquals("HP Elite x2 1011 G1 Tablet with Power Keyboard", product.getProductName());
	}
	@Test
	public void shouldPopulateProductType() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		tablet.populateCommonsToProduct(product);
		
		assertEquals("Tablet", product.getProductType());
	}
	
	@Test
	public void shouldParseChipset() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("Chipset is integrated with processor", tablet.getChipset());
	}

	@Test
	public void shouldParseEnergyEfficiency() throws Exception {
		Product product = findProduct("productPage1082.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("ENERGY STAR® certified", tablet.getEnergyEfficiency());
	}

	@Test
	public void shouldParseGraphics() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("Intel® HD Graphics 5300", tablet.getGraphics());
	}

	@Test
	public void shouldParseInternalDrive() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("256 GB M.2 SSD", tablet.getInternalDrive());
	}

	@Test
	public void shouldParsePowerSupply() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("45 W Smart AC adapter; HP ElitePad 4.5 mm AC Adapter (tablet)", tablet.getPowerSupply());
	}

	@Test
	public void shouldParseProcessorFamily() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("Intel® Core™ M processor", tablet.getProcessorFamily());
	}
	
	@Test
	public void shouldParseProcessorTechnology() throws Exception {
		Product product = findProduct("productPage201.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("Intel® Core™ M with vPro™ technology", tablet.getProcessorTechnology());
	}
	
	@Test
	public void shouldParseSecurityManagement() throws Exception {
		Product product = findProduct("productPage2878.html");
		Tablet tablet = (Tablet) parser.parse(parseHtml(product), product, config);
		assertEquals("TPM 1.2/2.0 (Infineon, soldered down); HP Fingerprint Reader (power keyboard); Integrated smart card reader (power keyboard); Preboot Authentication (password, Smart Card); HP Client Security; HP File Sanitizer; Drive Encryption; Absolute Persistence Module (models with Windows only); HP Device Access Manager with Just in Time Authentication; HP SpareKey; Microsoft Security Defender (models with Windows 8.1 only)", tablet.getSecurityManagement());
	}
	
}
