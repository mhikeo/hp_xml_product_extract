/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.model.Desktop;
import com.hp.inventory.audit.parser.model.Laptop;
import com.hp.inventory.audit.parser.model.Printer;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.DesktopParser;

public class DesktopParserTest extends ParserTest{
	private DesktopParser parser;
	private JSONResultHandler rh;

	@Before
	public void init() {
		parser = new DesktopParser();
		rh = new JSONResultHandler();
		rh.beforeStart();
	}

	@Test
	public void shouldParseCurrency() throws IOException {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);
		assertEquals("USD", desktop.getCurrency());
	}
	
	@Test
	public void shouldPopulateProductName() throws Exception {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);
		desktop.populateCommonsToProduct(product);
		
		assertEquals("HP EliteDesk 800 G1 Ultra-slim Desktop PC", product.getProductName());
	}
	@Test
	public void shouldPopulateProductType() throws Exception {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);
		desktop.populateCommonsToProduct(product);
		
		assertEquals("Desktop", product.getProductType());
	}
	@Test
	public void shouldParseAndPopulateCurrentPrice() throws IOException {
		Product product = findProduct("productPage153.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new BigDecimal("399.99"), desktop.getCurrentPrice());
		
		desktop.populateCommonsToProduct(product);
		
		assertEquals(new BigDecimal("399.99"), product.getCurrentPrice());
	}
	
	@Test
	public void shouldParseAndPopulateStrikedPrice() throws IOException {
		Product product = findProduct("productPage2277.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);
		
		assertEquals(new BigDecimal("3147.00"), desktop.getStrikedPrice());
		
		desktop.populateCommonsToProduct(product);
		
		assertEquals(new BigDecimal("3147.00"), product.getStrikedPrice());
	}
	
	@Test
	public void shouldParseAndPopulateRatingAndReviewsCount() throws IOException {
		Product product = findProduct("productPage2961.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);
		assertEquals(Integer.valueOf(4), desktop.getRating());
		
		desktop.populateCommonsToProduct(product);
		
		assertEquals(Integer.valueOf(4), product.getRating());
		assertEquals(Integer.valueOf(20), product.getNumberOfReviews());
	}
	
	@Test
	public void shouldUpdatePreviousRating() throws Exception {
		Product productOriginal = findProduct("productPage2961.html"); //from day2 sample
		Product productUpdated = findProduct("productPage4000.html"); //modified previous file just for testing purposes
		
		Desktop desktopOriginal = (Desktop) parser.parse(parseHtml(productOriginal), productOriginal, rh);
		Desktop desktopUpdated = (Desktop) parser.parse(parseHtml(productUpdated), productUpdated, rh);
		
		desktopOriginal.populateCommonsToProduct(productOriginal);
		desktopUpdated.populateCommonsToProduct(productUpdated);
		
		assertEquals(Integer.valueOf(4), productOriginal.getRating());
		assertEquals(Integer.valueOf(5), productUpdated.getRating());

		//now updating
		productOriginal.upgradeEntityFrom(productUpdated);
		
		assertEquals(Integer.valueOf(5), productOriginal.getRating());
		assertEquals(Integer.valueOf(4), productOriginal.getPreviousRating());
		checkIsToday(productOriginal.getDateOfRatingChange());

	}
	
	@Test
	public void shouldSetDateAddedAndAvailableForSale() throws Exception {
		Product productOriginal = new Product();
		
		Desktop desktopOriginal = new Desktop();
		
		desktopOriginal.populateCommonsToProduct(productOriginal);
		
		//now updating
		productOriginal.initNewEntity();
		
		checkIsToday(productOriginal.getDateAdded());
		checkIsToday(productOriginal.getAvailableForSaleDate());
	}
	
	@Test
	public void shouldSetDateAddedAndNullAvailableForSale() throws Exception {
		Product productOriginal = new Product();
		Product productUpdated = new Product();
		
		Desktop desktopOriginal = new Desktop();
		Desktop desktopUpdated = new Desktop();
		
		desktopOriginal.setComingSoonDate(new Date());
		
		desktopOriginal.populateCommonsToProduct(productOriginal);
		desktopUpdated.populateCommonsToProduct(productUpdated);
		
		//now updating
		productOriginal.initNewEntity();
		
		checkIsToday(productOriginal.getDateAdded());
		assertNull(productOriginal.getAvailableForSaleDate());
	}
	
	@Test
	public void shouldRemoveAvailableForSale() throws Exception {
		Product productOriginal = new Product();
		Product productUpdated = new Product();
		
		Desktop desktopOriginal = new Desktop();
		Desktop desktopUpdated = new Desktop();
		
		desktopUpdated.setComingSoonDate(new Date());
		
		desktopOriginal.populateCommonsToProduct(productOriginal);
		desktopUpdated.populateCommonsToProduct(productUpdated);
		
		//now updating
		productOriginal.initNewEntity();
		
		
		checkIsToday(productOriginal.getDateAdded());
		checkIsToday(productOriginal.getAvailableForSaleDate());
		
		productOriginal.upgradeEntityFrom(productUpdated);
		assertNull(productOriginal.getAvailableForSaleDate());
	}
	
	@Test
	public void shouldNotUpdateDateAdded() throws Exception {
		Product productOriginal = new Product();
		Product productUpdated = new Product();
		
		Desktop desktopOriginal = new Desktop();
		Desktop desktopUpdated = new Desktop();
		
		desktopOriginal.populateCommonsToProduct(productOriginal);
		desktopUpdated.populateCommonsToProduct(productUpdated);
		
		//now updating
		productOriginal.initNewEntity();
		
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		
		productOriginal.setDateAdded(yesterday.getTime());
		
		productOriginal.upgradeEntityFrom(productUpdated);
		checkIsYesterday(productOriginal.getDateAdded());
		
	}
	@Test
	public void shouldNotResetAvailableForSale() throws Exception {
		Product productOriginal = new Product();
		Product productUpdated = new Product();
		
		Desktop desktopOriginal = new Desktop();
		Desktop desktopUpdated = new Desktop();
		
		desktopUpdated.setComingSoonDate(null);
		
		desktopOriginal.populateCommonsToProduct(productOriginal);
		desktopUpdated.populateCommonsToProduct(productUpdated);
		
		//now updating
		productOriginal.initNewEntity();
		checkIsToday(productOriginal.getDateAdded());
		checkIsToday(productOriginal.getAvailableForSaleDate());
		
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		
		productOriginal.setAvailableForSaleDate(yesterday.getTime());
		
		productOriginal.upgradeEntityFrom(productUpdated);
		assertNotNull(productOriginal.getAvailableForSaleDate());
		checkIsYesterday(productOriginal.getAvailableForSaleDate());
	}
	
	@Test
	public void shouldParseAccessories() throws IOException {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"HP Mouse PadHP (2013) USDT Rear Port CoverUSB to Serial AdapterHP DisplayPort To HDMI 4k AdapterHP DisplayPort To HDMI 4k Adapter 2nd",
				desktop.getAccessories());
	}

	@Test
	public void shouldParseAdditionalApplicationSoftware() throws IOException {
		Product product = findProduct("productPage1369.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Foxit Phantom PDF Business", desktop.getAdditionalApplicationSoftware());
	}

	@Test
	public void shouldParseAdditionalNetworkingOptions() throws IOException {
		Product product = findProduct("productPage1369.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"Intel(R) Ethernet I210-T1 PCIe x1 Gb NIC HP WLAN 802.11 a/g/n 2x2 DB PCIe x1 Card Intel(R) 7260NB 802.11 a/b/g/n PCIe x1 NIC",
				desktop.getAdditionalNetworkingOptions());
	}

	@Test
	public void shouldParseBattery() throws IOException {
		Product product = findProduct("productPage253.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("3-cell, 43 Wh prismatic", desktop.getBattery());
	}

	@Test
	public void shouldParseBatteryLife() throws IOException {
		Product product = findProduct("productPage253.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Up to 7 hours and 45 minutes", desktop.getBatteryLife());
	}

	@Test
	public void shouldParseCableOptionKits() throws IOException {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("USDT Slim Optical Device Drive Cable Kit", desktop.getCableOptionKits());
	}

	@Test
	public void shouldParseChassis() throws IOException {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("HP EliteDesk 800 USDT Chassis",
				desktop.getChassis());
	}

	@Test
	public void shouldParseChinaCCCCompliance() throws IOException {
		Product product = findProduct("productPage1318.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("China Regulatory CCC Compliance Mark", desktop.getChinaCccCompliance());
	}

	@Test
	public void shouldParseController() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("LSI 9270-8i SAS 6Gb/s ROC RAID Card", desktop.getController());
	}

	@Test
	public void shouldParseDisplay() throws IOException {
		Product product = findProduct("productPage10.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("23\" diagonal IPS FHD touch-enabled LED-backlit (1920 x 1080)", desktop.getDisplay());
	}

	@Test
	public void shouldParseDisplayCable() throws IOException {
		Product product = findProduct("productPage1361.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"DMS-59 To Dual VGA Y-Cable Adapter HP DisplayPort Cable HP DisplayPort To DVI-D Adapter DMS-59 To Dual DVI Y-Cable Adapter HP DisplayPort To HDMI Adapter HP DisplayPort To HDMI 1.4 Adapter HP DisplayPort To VGA Adapter",
				desktop.getDisplayCable());
	}

	@Test
	public void shouldParseEnergyStar() throws IOException {
		Product product = findProduct("productPage1318.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("ENERGY STAR Qualified Configuration", desktop.getEnergyStar());
	}

	@Test
	public void shouldParseEightInternalStorage() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"300GB 10K RPM SAS SFF 8th Hard Drive 256GB SATA 8th Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 8th Hard Drive 600GB 10K RPM SAS SFF 8th Hard Drive Samsung Enterprise 240GB SATA 8th Solid-State Drive 512GB SATA 8th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 8th Hard Drive 600GB 15k RPM SAS SFF 8th Hard Drive Samsung Enterprise 480GB SATA 8th Solid-State Drive (SSD) 1TB SATA 8th Solid-State Drive (SSD)",
				desktop.getEightInternalStorage());
	}

	@Test
	public void shouldParseExternalIOPorts() throws IOException {
		Product product = findProduct("productPage150.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("2 USB 3.0; 4 USB 2.0", desktop.getExternalIOPorts());
	}

	@Test
	public void shouldParseExternalOpticalDrive() throws IOException {
		Product product = findProduct("productPage152.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("External USB DVD-RW", desktop.getExternalOpticalDrive());
	}

	@Test
	public void shouldParseFEMPCompliance() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("HP FEMP and ErP Qualifying S5 Low Power Mode Enabled Configuration", desktop.getFempCompliance());
	}

	@Test
	public void shouldParseFifthInternalStorage() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"500GB 7200 RPM SATA 5th Hard Drive 1TB 7200 RPM SATA 5th Hard Drive 2TB 7200 RPM SATA 5th Hard Drive 300GB 10K RPM SAS SFF 5th Hard Drive 300GB 15k RPM SAS 5th Hard Drive 3TB 7200 RPM SATA 5th Hard Drive 256GB SATA 5th Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 5th Hard Drive 600GB 10K RPM SAS SFF 5th Hard Drive 4TB 7200 RPM SATA 5th Hard Drive 512GB SATA 5th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 5th Hard Drive 600GB 15k RPM SAS SFF 5th Hard Drive Samsung Enterprise 240GB SATA 5th Solid-State Drive (SSD) 1TB SATA 5th Solid-State Drive (SSD) Samsung Enterprise 480GB SATA 5th Solid-State Drive (SSD)",
				desktop.getFifthInternalStorage());
	}

	@Test
	public void shouldParseFlashCache() throws IOException {
		Product product = findProduct("productPage261.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("8 GB", desktop.getFlashCache());
	}

	@Test
	public void shouldParseFourthInternalStorage() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"500GB 7200 RPM SATA 4th Hard Drive 1TB 7200 RPM SATA 4th Hard Drive 2TB 7200 RPM SATA 4th Hard Drive 300GB 10K RPM SAS SFF 4th Hard Drive 300GB 15k RPM SAS 4th Hard Drive 3TB 7200 RPM SATA 4th Hard Drive 256GB SATA 4th Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 4th Hard Drive 600GB 10K RPM SAS SFF 4th Hard Drive Samsung Enterprise 240GB SATA 4th Solid-State Drive (SSD) 4TB 7200 RPM SATA 4th Hard Drive 512GB SATA 4th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 4th Hard Drive 600GB 15k RPM SAS SFF 4th Hard Drive Samsung Enterprise 480GB SATA 4th Solid-State Drive (SSD) 1TB SATA 4th Solid-State Drive (SSD)",
				desktop.getFourthInternalStorage());
	}

	@Test
	public void shouldParseGraphicsConnectors() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"HP DisplayPort To DVI-D Adapter HP DisplayPort To DVI-D Adapter (2-Pack) HP DisplayPort To VGA Adapter HP DisplayPort To VGA Adapter 2nd HP DisplayPort To DVI-D Adapter (4-Pack) HP DisplayPort To DVI-D Adapter (6-Pack) NVIDIA SLI Graphics Connector HP DisplayPort to Dual Link DVI Adapter",
				desktop.getGraphicsConnectors());
	}

	@Test
	public void shouldParseHighPerformanceGPUComputing() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"NVIDIA Tesla K40 1st Compute Processor - PCIe x2 NVIDIA Tesla K40 2nd Compute Processor - PCIe x2",
				desktop.getHighPerformanceGpuComputing());
	}

	@Test
	public void shouldParseIntegratedCamera() throws IOException {
		Product product = findProduct("productPage261.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"HP Illuminator: includes 4-camera system including Intel® RealSense™ 3D Camera and 14.6 MP high-resolution camera, HP DLP Projector, LED desk lamp",
				desktop.getIntegratedCamera());
	}

	@Test
	public void shouldParseIntelSrtDiskCacheModules() throws IOException {
		Product product = findProduct("productPage1318.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("64GB SSD Disk Cache Module", desktop.getIntelSrtDiskCacheModules());
	}

	@Test
	public void shouldParseIntelSmartResponseTechnology() throws IOException {
		Product product = findProduct("productPage1384.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("32GB mSata Smart Response Technology", desktop.getIntelSmartResponseTechnology());
	}

	@Test
	public void shouldParseInternalOsLoadStorageOptions() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Operating System Load to PCIe Operating System Load to SATA/SAS",
				desktop.getInternalOsLoadStorageOptions());
	}

	@Test
	public void shouldParseInternalPcieStorage() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"HP Z Turbo Drive 256GB PCIe 1st Solid-State Drive (SSD) HP Z Turbo Drive 512GB PCIe 1st Solid-State Drive (SSD)",
				desktop.getInternalPcieStorage());
	}

	@Test
	public void shouldParseInternalStorageOptions() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"PCIe Boot w/ HDD/SSD RAID 0 Data Array RAID 0 Data Array Configuration RAID 0 Data Array PCIe SSD Configuration RAID 0 Striped Array Configuration RAID 1 Mirrored Array Configuration RAID 5 BootHDD+Parity Array Configuration RAID 5 Parity Array Configuration RAID10 Striped/Mirrored Configuration",
				desktop.getInternalStorageOptions());
	}

	@Test
	public void shouldParseLanTransceivers() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("HP 10GbE SFP+ SR 1st Transceiver HP 10GbE SFP+ SR 2nd Transceiver", desktop.getLanTransceivers());
	}

	@Test
	public void shouldParseLabel() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("FDA Class 1 Medical Device Registration", desktop.getLabel());
	}

	@Test
	public void shouldParseMaximumMemory() throws IOException {
		Product product = findProduct("productPage10.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Upgradeable to 16 GB", desktop.getMaximumMemory());
	}

	@Test
	public void shouldParseSecuritySoftware() throws IOException {
		Product product = findProduct("productPage256.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"No Additional Security Software Preinstalled McAfee Livesafe 12 months Preinstalled McAfee Livesafe 24 months Preinstalled McAfee Livesafe 36 months",
				desktop.getSecuritySoftware());
	}

	@Test
	public void shouldParseMediaReader() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("HP 15-In-1 Media Card Reader", desktop.getMediaReader());
	}

	@Test
	public void shouldParseMemoryCardDevice() throws IOException {
		Product product = findProduct("productPage10.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("7-in-1 memory card reader", desktop.getMemoryCardDevice());
	}

	@Test
	public void shouldParseProcessorTechnology() throws IOException {
		Product product = findProduct("productPage1389.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Intel® Core™ i7 with vPro technology", desktop.getProcessorTechnology());
	}

	@Test
	public void shouldParseRealTimeDataBackup() throws IOException {
		Product product = findProduct("productPage1318.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("RAID 0 Striped Array Configuration RAID 1 Mirrored Array Configuration",
				desktop.getRealTimeDataBackup());
	}

	@Test
	public void shouldParseSecondDisplayCable() throws IOException {
		Product product = findProduct("productPage1369.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"DMS-59 To Dual VGA Y-Cable Adapter 2nd HP DisplayPort Cable 2nd HP DisplayPort To DVI-D Adapter 2nd DMS-59 To Dual DVI Y-Cable Adapter 2nd HP DisplayPort To HDMI Adapter 2nd HP DisplayPort To HDMI 1.4 Adapter 2nd HP DisplayPort To VGA Adapter 2nd",
				desktop.getSecondDisplayCable());
	}

	@Test
	public void shouldParseSecondGraphicsCard() throws IOException {
		Product product = findProduct("productPage2651.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"NVIDIA NVS 310 512MB 2nd Graphics - PCIeNVIDIA NVS 315 1GB DMS59 2nd DMS59-2xDVI cable Graphics - PCIeNVIDIA Quadro K420 1GB DL-DVI(I)+DP 2nd No cables included Graphics - PCIeAMD FirePro W2100 2GB 2xDP 2nd No cables included GraphicsNVIDIA Quadro K620 2GB DL-DVI(I)+DP 2nd No cables included Graphics - PCIeAMD FirePro W5100 4GB 4xDP 2nd No cables included GraphicsNVIDIA Quadro K2200 4GB DL-DVI(I)+2xDP 2nd No cables included Graphics - PCIeAMD FirePro W7100 8GB 4xDP 2nd No cables included GraphicsNVIDIA Quadro K4200 4GB DL-DVI(I)+2xDP 2nd No cables included Graphics - PCIeNVIDIA Quadro K5200 8GB DL-DVI(I)+DL-DVI(D)+2xDP 2nd No cables included Graphics - PCIeNVIDIA Quadro K6000 12GB DL-DVI(I)+DL-DVI(D)+DP+DP 2nd No cable included Graphics - PCIeNVIDIA Quadro M6000 12GB DL-DVI(I)+4xDP 2nd No cable included Graphics",
				desktop.getSecondGraphicsCard());
	}

	@Test
	public void shouldParseSecondInternalStorage() throws IOException {
		Product product = findProduct("productPage2651.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"500GB 7200 RPM SATA 2nd Hard Drive1TB 7200 RPM SATA 2nd Hard Drive128GB SATA 2nd Solid-State Drive (SSD)2TB 7200 RPM SATA 2nd Hard Drive300GB 10K RPM SAS SFF 2nd Hard Drive3TB 7200 RPM SATA 2nd Hard Drive256GB SATA 2nd Solid-State Drive (SSD)300GB 15k RPM SAS SFF 2nd Hard Drive600GB 10K RPM SAS SFF 2nd Hard DriveSamsung Enterprise 240GB SATA 2nd Solid-State Drive (SSD)4TB 7200 RPM SATA 2nd Hard Drive512GB SATA 2nd Solid-State Drive (SSD)1.2TB 10K RPM SAS SFF 2nd Hard Drive600GB 15k RPM SAS SFF 2nd Hard DriveSamsung Enterprise 480GB SATA 2nd Solid-State Drive (SSD)1TB SATA 2nd Solid-State Drive (SSD)",
				desktop.getSecondInternalStorage());
	}

	@Test
	public void shouldParseSecondaryInternalPcieStorage() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"HP Z Turbo Drive 256GB PCIe 2nd Solid-State Drive (SSD) HP Z Turbo Drive 512GB PCIe 2nd Solid-State Drive (SSD)",
				desktop.getSecondaryInternalPcieStorage());
	}

	@Test
	public void shouldParseSecondaryOpticalDrive() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"9.5mm Slim SuperMulti DVDRW SATA 2nd Optical Disc Drive 9.5mm Slim Blu-ray Writer SATA 2nd Optical Disc Drive",
				desktop.getSecondaryOpticalDrive());
	}

	@Test
	public void shouldParseSecondaryProcessor() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"Intel(R) Xeon(R) E5-2603v3 1.6 1600 6C 2nd CPU Intel(R) Xeon(R) E5-2609v3 1.9 1600 6C 2nd CPU Intel(R) Xeon(R) E5-2620v3 2.4 1866 6C 2nd CPU Intel(R) Xeon(R) E5-2623v3 3.0 1866 4C 2nd CPU Intel(R) Xeon(R) E5-2630v3 2.4 1866 8C 2nd CPU Intel(R) Xeon(R) E5-2640v3 2.6 1866 8C 2nd CPU Intel(R) Xeon(R) E5-2637v3 3.5 2133 4C 2nd CPU Intel(R) Xeon(R) E5-2650v3 2.3 2133 10C 2nd CPU Intel(R) Xeon(R) E5-2660v3 2.6 2133 10C 2nd CPU Intel(R) Xeon(R) E5-2643v3 3.4 2133 6C 2nd CPU Intel(R) Xeon(R) E5-2670v3 2.3 2133 12C 2nd CPU Intel(R) Xeon(R) E5-2680v3 2.5 2133 12C 2nd CPU Intel(R) Xeon(R) E5-2683v3 2.0 2133 14C 2nd CPU Intel(R) Xeon(R) E5-2690v3 2.6 2133 12C 2nd CPU Intel(R) Xeon(R) E5-2687Wv3 3.1 2133 10C 2nd CPU Intel(R) Xeon(R) E5-2667v3 3.2 2133 8C 2nd CPU Intel(R) Xeon(R) E5-2695v3 2.3 2133 14C 2nd CPU Intel(R) Xeon(R) E5-2697v3 2.6 2133 14C 2nd CPU Intel(R) Xeon(R) E5-2699v3 2.3 2133 18C 2nd CPU",
				desktop.getSecondaryProcessor());
	}

	@Test
	public void shouldParseSecurity() throws IOException {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"USB port disableHP Solenoid Lock and Hood (USDT) SensorHP UltraSlim Cable Lock",
				desktop.getSecurity());
	}

	@Test
	public void shouldParseSecurityEncryption() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("HP TPM Disabled", desktop.getSecurityEncryption());
	}

	@Test
	public void shouldParseSixthInternalStorage() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"300GB 10K RPM SAS SFF 6th Hard Drive 256GB SATA 6th Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 6th Hard Drive 600GB 10K RPM SAS SFF 6th Hard Drive 512GB SATA 6th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 6th Hard Drive 600GB 15k RPM SAS SFF 6th Hard Drive Samsung Enterprise 240GB SATA 6th Solid-State Drive (SSD) 1TB SATA 6th Solid-State Drive (SSD) Samsung Enterprise 480GB SATA 6th Solid-State Drive (SSD)",
				desktop.getSixthInternalStorage());
	}

	@Test
	public void shouldParseSeventhInternalStorage() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"300GB 10K RPM SAS SFF 7th Hard Drive 300GB 15k RPM SAS SFF 7th Hard Drive 600GB 10K RPM SAS SFF 7th Hard Drive Samsung Enterprise 240GB SATA 7th Solid-State Drive (SSD) 256GB SATA 7th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 7th Hard Drive 600GB 15k RPM SAS SFF 7th Hard Drive Samsung Enterprise 480GB SATA 7th Solid-State Drive (SSD) 512GB SATA 7th Solid-State Drive (SSD) 1TB SATA 7th Solid-State Drive (SSD)",
				desktop.getSeventhInternalStorage());
	}

	@Test
	public void shouldParseSoftwareBundles() throws IOException {
		Product product = findProduct("productPage238.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("WordPerfect Office X7 Home + Student Edition", desktop.getSoftwareBundles());
	}

	@Test
	public void shouldParseSpeakers() throws IOException {
		Product product = findProduct("productPage1384.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Remove Integrated Speakers", desktop.getSpeakers());
	}

	@Test
	public void shouldParseStand() throws IOException {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("HP USDT Chassis Tower Stand", desktop.getStand());
	}

	@Test
	public void shouldParseSystemRecoverySolutions() throws IOException {
		Product product = findProduct("productPage2556.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"Windows 7 Professional 32-bit OS DVD+ DRDVDWindows 7 Professional 64-bit OS DVD+ DRDVD",
				desktop.getSystemRecoverySolutions());
	}

	@Test
	public void shouldParseTvTuner() throws IOException {
		Product product = findProduct("productPage152.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("No TV Tuner Card TV Tuner- ATSC/NTSC with PVR, External USB Stick", desktop.getTvTuner());
	}

	@Test
	public void shouldParseTechnical() throws IOException {
		Product product = findProduct("productPage1384.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("Microsoft Lync Label", desktop.getTechnical());
	}

	@Test
	public void shouldParseTechnicalAV() throws IOException {
		Product product = findProduct("productPage1444.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals("HP Processor Air Cooling Kit", desktop.getTechnicalAV());
	}

	@Test
	public void shouldParseThirdGraphicsCard() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"AMD FirePro W5100 4GB 4xDP 3rd No cables included Graphics NVIDIA Quadro K2200 4GB DL-DVI(I)+2xDP 3rd No cables included Graphics - PCIe NVIDIA Quadro K5200 8GB DL-DVI(I)+DL-DVI(D)+2xDP 3rd No cables included Graphics - PCIe",
				desktop.getThirdGraphicsCard());
	}

	@Test
	public void shouldParseThirdInternalStorage() throws IOException {
		Product product = findProduct("productPage1306.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"500GB 7200 RPM SATA 3rd Hard Drive 1TB 7200 RPM SATA 3rd Hard Drive 2TB 7200 RPM SATA 3rd Hard Drive 300GB 10K RPM SAS SFF 3rd Hard Drive 300GB 15k RPM SAS 3rd Hard Drive 3TB 7200 RPM SATA 3rd Hard Drive 256GB SATA 3rd Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 3rd Hard Drive 600GB 10K RPM SAS SFF 3rd Hard Drive Samsung Enterprise 240GB SATA 3rd Solid-State Drive (SSD) 4TB 7200 RPM SATA 3rd Hard Drive 512GB SATA 3rd Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 3rd Hard Drive 600GB 15k RPM SAS SFF 3rd Hard Drive Samsung Enterprise 480GB SATA 3rd Solid-State Drive (SSD) 1TB SATA 3rd Solid-State Drive (SSD)",
				desktop.getThirdInternalStorage());
	}

	@Test
	public void shouldParseWebcam() throws IOException {
		Product product = findProduct("productPage10.html");
		Desktop desktop = (Desktop) parser.parse(parseHtml(product), product, rh);

		assertEquals(
				"720p HP TrueVision HD Webcam with integrated microphones",
				desktop.getWebcam());
	}


}
