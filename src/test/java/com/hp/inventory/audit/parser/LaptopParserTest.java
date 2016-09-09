/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import com.hp.inventory.audit.parser.parsers.GeneralParser;
import org.junit.Before;
import org.junit.Test;

import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.model.Product;

public class LaptopParserTest extends ParserTest {
	private GeneralParser parser;
	private Config rh;
	private int HP = 1;

	@Before
	public void init() {
		parser = new GeneralParser();
		parser.setProductType("Laptop");
		rh = new Config();
		rh.siteId = HP;
		rh.resultHandler = new JSONResultHandler();
		rh.resultHandler.beforeStart();
	}

	@Test
	public void shouldParseCurrency() throws Exception {
		Product product = findProduct("productPage39.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("USD", laptop.getPrices().get(HP).getCurrency());
	}

	@Test
	public void shouldPopulateProductName() throws Exception {
		Product product = findProduct("productPage347.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		laptop.populateCommonsToProduct(product);
		
		assertEquals("HP ENVY x2 - 13-j002dx", product.getProductName());
	}
	
	@Test
	public void shouldPopulateProductType() throws Exception {
		Product product = findProduct("productPage39.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		laptop.populateCommonsToProduct(product);
		
		assertEquals("Laptop", product.getProductType());
	}
	
	@Test
	public void shouldContainParsingError() throws Exception {
		Product product = findProduct("productPage168.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		
		laptop.populateCommonsToProduct(product);
		
		assertEquals("Could not parse battery life from string: 9 hours and 30 minutes", product.getParsingError());
		assertNotNull(product.getDateOfParsingError());
		checkIsToday(product.getDateOfParsingError());
	}
	
	@Test
	public void shouldUpdatePreviousPrice() throws Exception {
		Product productOriginal = findProduct("productPage1407.html"); //from day1 sample
		Product productUpdated = findProduct("productPage1921.html"); //from day2 sample
		
		Product laptopOriginal = (Product) parser.parse(parseHtml(productOriginal), productOriginal, rh);
		Product laptopUpdated = (Product) parser.parse(parseHtml(productUpdated), productUpdated, rh);
		
		laptopOriginal.populateCommonsToProduct(productOriginal);
		laptopUpdated.populateCommonsToProduct(productUpdated);
		
		assertEquals(new BigDecimal("1694.00"), productOriginal.getPrices().get(HP).getCurrentPrice());
		assertEquals(new BigDecimal("1594.00"), productUpdated.getPrices().get(HP).getCurrentPrice());

		//now updating
		productOriginal.upgradeEntityFrom(productUpdated);
		
		assertEquals(new BigDecimal("1594.00"), productOriginal.getPrices().get(HP).getCurrentPrice());
		assertEquals(new BigDecimal("1694.00"), productOriginal.getPrices().get(HP).getPreviousPrice());
		checkIsToday(productOriginal.getPrices().get(HP).getDateOfPriceChange());

	}
	
	@Test
	public void shouldParseAcAdapter() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("45 Watt Smart nPFC AC Adapter", getSpecification(laptop, "acAdapter"));
	}

	@Test
	public void shouldParseAccessories() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"Permanently Disable Bluetooth Upgrade to Foxit Phantom PDF Business Software HP 3-button USB Laser Mouse HP USB Optical Travel Mouse HP Basic Carrying Case HP Comfort Grip Wireless Mouse HP DisplayPort to HDMI Adapter HP Ultraslim Keyed Cable Lock HP Ultraslim Keyed Cable Lock (2nd) HP Basic Backpack HP Business Backpack HP DisplayPort to VGA Adapter HP Docking Station Cable Lock HP Executive Tablet Pen G2 HP 65 Watt Smart wPC AC Adapter US HP Business Top Load Case HP Mobile NonLS USB DVDRW A3E35AV 6-Cell (44 WHr) Long Life Battery HP UltraSlim Docking Station 2013",
				getSpecification(laptop, "accessories"));
	}

	@Test
	public void shouldParseAdditionalBay() throws Exception {
		Product product = findProduct("productPage1040.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"No Upgrade Bay DVD+/-RW SuperMulti DL Blu-ray ROM DVD+/-RW SuperMulti DL Blu-ray R/RE DVD+/-RW SuperMulti DL 1TB 7200 rpm Hard Drive 256GB SATA-3 Self Encrypted OPAL 2 Solid-State Drive (SSD) 512GB SATA-3 Self Encrypted OPAL 2 Upgrade Bay Solid-State Drive (SSD) 512GB SATA-3 Upgrade Bay Solid-State Drive (SSD)",
				getSpecification(laptop, "additionalBay"));
	}

	@Test
	public void shouldParseBroadbandServiceProvider() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);

		assertEquals("No Service Provider Service Provider information for ATT Service Provider information for Verizon", getSpecification(laptop, "broadbandServiceProvider"));
	}

	@Test
	public void shouldParseAudio() throws Exception {
		Product product = findProduct("productPage125.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);

		assertEquals("Beats Audio™ with 2 speakers", getSpecification(laptop, "audio"));
	}

	@Test
	public void shouldParseBluetooth() throws Exception {
		Product product = findProduct("productPage1025.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);

		assertEquals("Permanently Disable Bluetooth", getSpecification(laptop, "bluetooth"));
	}

	@Test
	public void shouldParseChipset() throws Exception {
		Product product = findProduct("productPage28.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);

		assertEquals("Mobile Intel® QM87", getSpecification(laptop, "chipset"));
	}

	@Test
	public void shouldParseExternalIOPorts() throws Exception {
		Product product = findProduct("productPage125.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);

		assertEquals("2 USB 3.0; 1 USB 2.0; 1 HDMI; 1 RJ-45; 1 headphone/microphone combo",
				getSpecification(laptop, "externalIOPorts"));
	}

	@Test
	public void shouldParseFingerPrintReader() throws Exception {
		Product product = findProduct("productPage1036.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);

		assertEquals("No Integrated Fingerprint Reader Integrated Fingerprint Reader", getSpecification(laptop, "fingerPrintReader"));
	}

	@Test
	public void shouldParseFlashCache() throws Exception {
		Product product = findProduct("productPage1036.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Flash Cache 32GB Flash Cache", getSpecification(laptop, "flashCache"));
	}

	@Test
	public void shouldParseHpMobileBroadband() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("No HP Mobile Broadband HP hs3110 HSPA+ w/GPS Huawei MU736 - 3G HP lt4211 LTE HSPA+ EVDO w/GPS Foxconn NA-1-S3 - 4G", getSpecification(laptop, "hpMobileBroadband"));
	}

	@Test
	public void shouldParseLabelEnergyStar() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("ENERGY STAR Qualified Configuration", getSpecification(laptop, "labelEnergyStar"));
	}

	@Test
	public void shouldParseLaplinkPcmoverSoftware() throws Exception {
		Product product = findProduct("productPage125.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Laplink PCmover CD only -The easiest way to moves your programs, files and settings",
				getSpecification(laptop, "laplinkPcmoverSoftware"));
	}

	@Test
	public void shouldParseSecuritySoftware() throws Exception {
		Product product = findProduct("productPage125.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals(
				"Security Software Trial McAfee LiveSafe 12 months McAfee LiveSafe 24 months McAfee LiveSafe 36 months",
				getSpecification(laptop, "securitySoftware"));
	}

	@Test
	public void shouldParseMemorySlots() throws Exception {
		Product product = findProduct("productPage23.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("1 user-accessible", getSpecification(laptop, "memorySlots"));
	}

	@Test
	public void shouldParseMiniCard() throws Exception {
		Product product = findProduct("productPage675.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("128GB Z Turbo Drive PCIe Solid-State Drive (SSD) 256GB Z Turbo Drive PCIe Solid-State Drive (SSD)", getSpecification(laptop, "miniCard"));
	}

	@Test
	public void shouldParseMiniCardSsd() throws Exception {
		Product product = findProduct("productPage1040.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals(
				"128GB Z Turbo Drive PCIe Solid-State Drive (SSD) 256GB Z Turbo Drive PCIe Solid-State Drive (SSD)",
				getSpecification(laptop, "miniCardSsd"));
	}

	@Test
	public void shouldParseMiscWarrantyDocumentation() throws Exception {
		Product product = findProduct("productPage1025.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Extended Warranty Service Selected", getSpecification(laptop, "miscWarrantyDocumentation"));
	}

	@Test
	public void shouldParseModem() throws Exception {
		Product product = findProduct("productPage1090.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Modem 56K v.92 Modem", getSpecification(laptop, "modem"));
	}

	@Test
	public void shouldParseNearFieldCommunication() throws Exception {
		Product product = findProduct("productPage1025.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Near Field Communication Near Field Communication Module", getSpecification(laptop, "nearFieldCommunication"));
	}

	@Test
	public void shouldParseOsRecoveryCd() throws Exception {
		Product product = findProduct("productPage1025.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("System Recovery DVD for Windows 7 Professional 32-bit System Recovery DVD for Windows 7 Professional 64-bit", getSpecification(laptop, "osRecoveryCd"));
	}

	@Test
	public void shouldParseOfficeSoftware() throws Exception {
		Product product = findProduct("productPage125.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals(
				"Office Software Trial $16 off retail price for Microsoft(R) Office 365 Personal 1-year, Activation required Microsoft(R) Office 365 Home 1-year, Activation required Microsoft(R) Office Home and Student 2013 Microsoft(R) Office Home and Business 2013 Microsoft(R) Office Professional 2013",
				getSpecification(laptop, "officeSoftware"));
	}

	@Test
	public void shouldParseOpticalDrive() throws Exception {
		Product product = findProduct("productPage28.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("DVD+/-RW SuperMulti DL", getSpecification(laptop, "opticalDrive"));
	}

	@Test
	public void shouldParseOutOfBandManagement() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Intel(R) vPro(TM) Technology", getSpecification(laptop, "outOfBandManagement"));
	}

	@Test
	public void shouldParsePersonalization() throws Exception {
		Product product = findProduct("productPage125.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("HP TrueVision HD Webcam with Digital Microphone (natural silver) HP TrueVision HD Webcam with Digital Microphone (vibrant red)", getSpecification(laptop, "personalization"));
	}

	@Test
	public void shouldParsePowerCord() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Power Cord - 1.0 Meters Power Cord - 1.8 Meters", getSpecification(laptop, "powerCord"));
	}

	@Test
	public void shouldParseProcessorFamily() throws Exception {
		Product product = findProduct("productPage28.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Intel® Core™ i7 processor", getSpecification(laptop, "processorFamily"));
	}
	
	@Test
	public void shouldParseOperatingSystem() throws Exception {
		Product product = findProduct("productPage39.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Windows 10 Home 64", getSpecification(laptop, "operatingSystem"));
	}
	
	@Test
	public void shouldParseProcessorTechnology() throws Exception {
		Product product = findProduct("productPage20.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Intel Turbo Boost Technology", getSpecification(laptop, "processorTechnology"));
	}

	@Test
	public void shouldParseRecoveryMediaDriver() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Driver DVD for Windows 7 without OS Media Driver DVD for Windows 8.1 without OS Media", getSpecification(laptop, "recoveryMediaDriver"));
	}

	@Test
	public void shouldParseSecurityManagement() throws Exception {
		Product product = findProduct("productPage28.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Standard: HP Client Security (Windows 7 & 8 only); HP Sure Start; HP Fingerprint Sensor; Integrated Smart Card Reader; Enhanced Pre-Boot Security (multiuser/multifactor); HP Spare Key (requires initial user setup); One-Step Logon; Common Criteria EAL4+ Augmented Certified Discrete TPM 1.2 Embedded Security Chip; Security lock slot; Support for Intel AT; Optional: Computrace with GPS Tracking (sold separately and requires the purchase of subscription)", getSpecification(laptop, "securityManagement"));
	}

	@Test
	public void shouldParseSensors() throws Exception {
		Product product = findProduct("productPage3.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Accelerometer", getSpecification(laptop, "sensors"));
	}

	@Test
	public void shouldParseTheftProtection() throws Exception {
		Product product = findProduct("productPage125.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Computrace LoJack for Laptops, One Year Computrace LoJack for Laptops, Two Years Computrace LoJack for Laptops, Four Years ($60 savings)",
						getSpecification(laptop, "theftProtection"));
	}

	@Test
	public void shouldParseWarrantyBattery() throws Exception {
		Product product = findProduct("productPage675.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("3 year Battery Warranty Card", getSpecification(laptop, "warrantyBattery"));
	}

	@Test
	public void shouldParseBoxContents() throws Exception {
		Product product = findProduct("productPage2.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals("Gift card", getSpecification(laptop, "boxContents"));
	}

	@Test
	public void shouldParseWirelessLan() throws Exception {
		Product product = findProduct("productPage616.html");
		Product laptop = (Product) parser.parse(parseHtml(product), product, rh);
		assertEquals(
				"No WLAN Module and No Bluetooth(R) Wireless Technology Intel(R)7265 802.11 a/b/g/n/ac (2x2) with Bluetooth Intel(R)7265AN 802.11 a/b/g/n (2x2) with Bluetooth",
				getSpecification(laptop, "wirelessLan"));
	}

}
