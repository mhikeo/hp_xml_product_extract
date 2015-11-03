package com.hp.inventory.audit.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.model.Laptop;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.LaptopParser;

public class LaptopParserTest extends ParserTest {
	private LaptopParser parser;
	private JSONResultHandler rh;

	@Before
	public void init() {
		parser = new LaptopParser();
		rh = new JSONResultHandler();
		rh.beforeStart();
	}

	@Test
	public void shouldParseCurrency() throws IOException {
		Product product = findProduct("productPage39.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("USD", laptop.getCurrency());
	}

	@Test
	public void shouldPopulateProductName() throws Exception {
		Product product = findProduct("productPage347.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		laptop.populateCommonsToProduct(product);
		
		assertEquals("HP ENVY x2 - 13-j002dx", product.getProductName());
	}
	
	@Test
	public void shouldPopulateProductType() throws Exception {
		Product product = findProduct("productPage39.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		laptop.populateCommonsToProduct(product);
		
		assertEquals("Laptop", product.getProductType());
	}
	
	@Test
	public void shouldParseAndPopulateCurrentPrice() throws IOException {
		Product product = findProduct("productPage39.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new BigDecimal("659.99"), laptop.getCurrentPrice());
		
		laptop.populateCommonsToProduct(product);
		
		assertEquals(new BigDecimal("659.99"), product.getCurrentPrice());
	}
	
	@Test
	public void shouldParseAndPopulateStrikedPrice() throws IOException {
		Product product = findProduct("productPage1743.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new BigDecimal("2930.00"), laptop.getStrikedPrice());
		
		laptop.populateCommonsToProduct(product);
		
		assertEquals(new BigDecimal("2930.00"), product.getStrikedPrice());
	}
	
	@Test
	public void shouldParseAndPopulateRatingAndReviewsCount() throws IOException {
		Product product = findProduct("productPage347.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new Integer(3), laptop.getRating());
		
		laptop.populateCommonsToProduct(product);
		
		assertEquals(new Integer(3), product.getRating());
		assertEquals(Integer.valueOf(5), product.getNumberOfReviews());
	}
	
	@Test
	public void shouldContainParsingError() throws IOException {
		Product product = findProduct("productPage168.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		
		laptop.populateCommonsToProduct(product);
		
		assertEquals("Could not parse battery life from string: 9 hours and 30 minutes", product.getParsingError());
		assertNotNull(product.getDateOfParsingError());
		checkIsToday(product.getDateOfParsingError());
	}
	
	@Test
	public void shouldUpdatePreviousPrice() throws Exception {
		Product productOriginal = findProduct("productPage1407.html"); //from day1 sample
		Product productUpdated = findProduct("productPage1921.html"); //from day2 sample
		
		Laptop laptopOriginal = (Laptop) parser.parse(parseHtml(productOriginal), productOriginal, rh);
		Laptop laptopUpdated = (Laptop) parser.parse(parseHtml(productUpdated), productUpdated, rh);
		
		laptopOriginal.populateCommonsToProduct(productOriginal);
		laptopUpdated.populateCommonsToProduct(productUpdated);
		
		assertEquals(new BigDecimal("1694.00"), productOriginal.getCurrentPrice());
		assertEquals(new BigDecimal("1594.00"), productUpdated.getCurrentPrice());

		//now updating
		productOriginal.upgradeEntityFrom(productUpdated);
		
		assertEquals(new BigDecimal("1594.00"), productOriginal.getCurrentPrice());
		assertEquals(new BigDecimal("1694.00"), productOriginal.getPreviousPrice());
		checkIsToday(productOriginal.getDateOfPriceChange());

	}
	
	@Test
	public void shouldParseAcAdapter() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("45 Watt Smart nPFC AC Adapter", laptop.getAcAdapter());
	}

	@Test
	public void shouldParseAccessories() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"Permanently Disable Bluetooth Upgrade to Foxit Phantom PDF Business Software HP 3-button USB Laser Mouse HP USB Optical Travel Mouse HP Basic Carrying Case HP Comfort Grip Wireless Mouse HP DisplayPort to HDMI Adapter HP Ultraslim Keyed Cable Lock HP Ultraslim Keyed Cable Lock (2nd) HP Basic Backpack HP Business Backpack HP DisplayPort to VGA Adapter HP Docking Station Cable Lock HP Executive Tablet Pen G2 HP 65 Watt Smart wPC AC Adapter US HP Business Top Load Case HP Mobile NonLS USB DVDRW A3E35AV 6-Cell (44 WHr) Long Life Battery HP UltraSlim Docking Station 2013",
				laptop.getAccessories());
	}

	@Test
	public void shouldParseAdditionalBay() throws IOException {
		Product product = findProduct("productPage1040.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"No Upgrade Bay DVD+/-RW SuperMulti DL Blu-ray ROM DVD+/-RW SuperMulti DL Blu-ray R/RE DVD+/-RW SuperMulti DL 1TB 7200 rpm Hard Drive 256GB SATA-3 Self Encrypted OPAL 2 Solid-State Drive (SSD) 512GB SATA-3 Self Encrypted OPAL 2 Upgrade Bay Solid-State Drive (SSD) 512GB SATA-3 Upgrade Bay Solid-State Drive (SSD)",
				laptop.getAdditionalBay());
	}

	@Test
	public void shouldParseBroadbandServiceProvider() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);

		assertEquals("No Service Provider Service Provider information for ATT Service Provider information for Verizon", laptop.getBroadbandServiceProvider());
	}

	@Test
	public void shouldParseAudio() throws IOException {
		Product product = findProduct("productPage125.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Beats Audio™ with 2 speakers", laptop.getAudio());
	}

	@Test
	public void shouldParseBluetooth() throws IOException {
		Product product = findProduct("productPage1025.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Permanently Disable Bluetooth", laptop.getBluetooth());
	}

	@Test
	public void shouldParseChipset() throws IOException {
		Product product = findProduct("productPage28.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Mobile Intel® QM87", laptop.getChipset());
	}

	@Test
	public void shouldParseExternalIOPorts() throws IOException {
		Product product = findProduct("productPage125.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);

		assertEquals("2 USB 3.0; 1 USB 2.0; 1 HDMI; 1 RJ-45; 1 headphone/microphone combo",
				laptop.getExternalIOPorts());
	}

	@Test
	public void shouldParseFingerPrintReader() throws IOException {
		Product product = findProduct("productPage1036.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);

		assertEquals("No Integrated Fingerprint Reader Integrated Fingerprint Reader", laptop.getFingerPrintReader());
	}

	@Test
	public void shouldParseFlashCache() throws IOException {
		Product product = findProduct("productPage1036.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Flash Cache 32GB Flash Cache", laptop.getFlashCache());
	}

	@Test
	public void shouldParseHpMobileBroadband() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("No HP Mobile Broadband HP hs3110 HSPA+ w/GPS Huawei MU736 - 3G HP lt4211 LTE HSPA+ EVDO w/GPS Foxconn NA-1-S3 - 4G", laptop.getHpMobileBroadband());
	}

	@Test
	public void shouldParseLabelEnergyStar() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("ENERGY STAR Qualified Configuration", laptop.getLabelEnergyStar());
	}

	@Test
	public void shouldParseLaplinkPcmoverSoftware() throws IOException {
		Product product = findProduct("productPage125.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Laplink PCmover CD only -The easiest way to moves your programs, files and settings",
				laptop.getLaplinkPcmoverSoftware());
	}

	@Test
	public void shouldParseSecuritySoftware() throws IOException {
		Product product = findProduct("productPage125.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals(
				"Security Software Trial McAfee LiveSafe 12 months McAfee LiveSafe 24 months McAfee LiveSafe 36 months",
				laptop.getSecuritySoftware());
	}

	@Test
	public void shouldParseMemorySlots() throws IOException {
		Product product = findProduct("productPage23.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("1 user-accessible", laptop.getMemorySlots());
	}

	@Test
	public void shouldParseMiniCard() throws IOException {
		Product product = findProduct("productPage675.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("128GB Z Turbo Drive PCIe Solid-State Drive (SSD) 256GB Z Turbo Drive PCIe Solid-State Drive (SSD)", laptop.getMiniCard());
	}

	@Test
	public void shouldParseMiniCardSsd() throws IOException {
		Product product = findProduct("productPage1040.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals(
				"128GB Z Turbo Drive PCIe Solid-State Drive (SSD) 256GB Z Turbo Drive PCIe Solid-State Drive (SSD)",
				laptop.getMiniCardSsd());
	}

	@Test
	public void shouldParseMiscWarrantyDocumentation() throws IOException {
		Product product = findProduct("productPage1025.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Extended Warranty Service Selected", laptop.getMiscWarrantyDocumentation());
	}

	@Test
	public void shouldParseModem() throws IOException {
		Product product = findProduct("productPage1090.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Modem 56K v.92 Modem", laptop.getModem());
	}

	@Test
	public void shouldParseNearFieldCommunication() throws IOException {
		Product product = findProduct("productPage1025.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Near Field Communication Near Field Communication Module", laptop.getNearFieldCommunication());
	}

	@Test
	public void shouldParseOsRecoveryCd() throws IOException {
		Product product = findProduct("productPage1025.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("System Recovery DVD for Windows 7 Professional 32-bit System Recovery DVD for Windows 7 Professional 64-bit", laptop.getOsRecoveryCd());
	}

	@Test
	public void shouldParseOfficeSoftware() throws IOException {
		Product product = findProduct("productPage125.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals(
				"Office Software Trial $16 off retail price for Microsoft(R) Office 365 Personal 1-year, Activation required Microsoft(R) Office 365 Home 1-year, Activation required Microsoft(R) Office Home and Student 2013 Microsoft(R) Office Home and Business 2013 Microsoft(R) Office Professional 2013",
				laptop.getOfficeSoftware());
	}

	@Test
	public void shouldParseOpticalDrive() throws IOException {
		Product product = findProduct("productPage28.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("DVD+/-RW SuperMulti DL", laptop.getOpticalDrive());
	}

	@Test
	public void shouldParseOutOfBandManagement() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("No Intel(R) vPro(TM) Technology", laptop.getOutOfBandManagement());
	}

	@Test
	public void shouldParsePersonalization() throws IOException {
		Product product = findProduct("productPage125.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("HP TrueVision HD Webcam with Digital Microphone (natural silver) HP TrueVision HD Webcam with Digital Microphone (vibrant red)", laptop.getPersonalization());
	}

	@Test
	public void shouldParsePowerCord() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Power Cord - 1.0 Meters Power Cord - 1.8 Meters", laptop.getPowerCord());
	}

	@Test
	public void shouldParseProcessorFamily() throws IOException {
		Product product = findProduct("productPage28.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Intel® Core™ i7 processor", laptop.getProcessorFamily());
	}
	
	@Test
	public void shouldParseOperatingSystem() throws IOException {
		Product product = findProduct("productPage39.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Windows 10 Home 64", laptop.getOperatingSystem());
	}
	
	@Test
	public void shouldParseProcessorTechnology() throws IOException {
		Product product = findProduct("productPage20.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Intel Turbo Boost Technology", laptop.getProcessorTechnology());
	}

	@Test
	public void shouldParseRecoveryMediaDriver() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Driver DVD for Windows 7 without OS Media Driver DVD for Windows 8.1 without OS Media", laptop.getRecoveryMediaDriver());
	}

	@Test
	public void shouldParseSecurityManagement() throws IOException {
		Product product = findProduct("productPage28.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Standard: HP Client Security (Windows 7 & 8 only); HP Sure Start; HP Fingerprint Sensor; Integrated Smart Card Reader; Enhanced Pre-Boot Security (multiuser/multifactor); HP Spare Key (requires initial user setup); One-Step Logon; Common Criteria EAL4+ Augmented Certified Discrete TPM 1.2 Embedded Security Chip; Security lock slot; Support for Intel AT; Optional: Computrace with GPS Tracking (sold separately and requires the purchase of subscription)", laptop.getSecurityManagement());
	}

	@Test
	public void shouldParseSensors() throws IOException {
		Product product = findProduct("productPage3.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Accelerometer", laptop.getSensors());
	}

	@Test
	public void shouldParseTheftProtection() throws IOException {
		Product product = findProduct("productPage125.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Computrace LoJack for Laptops, One Year Computrace LoJack for Laptops, Two Years Computrace LoJack for Laptops, Four Years ($60 savings)", laptop.getTheftProtection());
	}

	@Test
	public void shouldParseWarrantyBattery() throws IOException {
		Product product = findProduct("productPage675.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("3 year Battery Warranty Card", laptop.getWarrantyBattery());
	}

	@Test
	public void shouldParseBoxContents() throws IOException {
		Product product = findProduct("productPage2.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals("Gift card", laptop.getBoxContents());
	}

	@Test
	public void shouldParseWirelessLan() throws IOException {
		Product product = findProduct("productPage616.html");
		Laptop laptop = (Laptop) parser.parse(parseHtml(product), product, rh);
		assertEquals(
				"No WLAN Module and No Bluetooth(R) Wireless Technology Intel(R)7265 802.11 a/b/g/n/ac (2x2) with Bluetooth Intel(R)7265AN 802.11 a/b/g/n (2x2) with Bluetooth",
				laptop.getWirelessLan());
	}

}
