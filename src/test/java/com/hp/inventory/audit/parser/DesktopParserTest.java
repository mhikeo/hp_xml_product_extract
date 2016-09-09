/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.hp.inventory.audit.parser.parsers.GeneralParser;
import org.junit.Before;
import org.junit.Test;

import com.hp.inventory.audit.parser.handlers.JSONResultHandler;
import com.hp.inventory.audit.parser.model.Product;

public class DesktopParserTest extends ParserTest{
	private static final Integer HP = 1;
	private GeneralParser parser;
	private Config config;

	@Before
	public void init() {
		parser = new GeneralParser();
		parser.setProductType("Desktop");
		config = new Config();
		config.resultHandler = new JSONResultHandler();
		config.resultHandler.beforeStart();
	}

	@Test
	public void shouldParseCurrency() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);
		assertEquals("USD", desktop.getPrices().get(HP).getCurrency());
	}
	
	@Test
	public void shouldPopulateProductName() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);
		desktop.populateCommonsToProduct(product);
		
		assertEquals("HP EliteDesk 800 G1 Ultra-slim Desktop PC", product.getProductName());
	}
	@Test
	public void shouldPopulateProductType() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);
		desktop.populateCommonsToProduct(product);
		
		assertEquals("Desktop", product.getProductType());
	}
	@Test
	public void shouldParseAndPopulateCurrentPrice() throws Exception {
		Product product = findProduct("productPage153.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);
		
		assertEquals(new BigDecimal("399.99"), desktop.getPrices().get(HP).getCurrentPrice());
		
	}
	
	@Test
	public void shouldParseAndPopulateStrikedPrice() throws Exception {
		Product product = findProduct("productPage2277.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);
		
		assertEquals(new BigDecimal("3147.00"), desktop.getPrices().get(HP).getStrikedPrice());
	}
	
	@Test
	public void shouldParseAndPopulateRatingAndReviewsCount() throws Exception {
		Product product = findProduct("productPage2961.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);
		assertEquals(Integer.valueOf(4), desktop.getRatings().get(HP).getRating());
		assertEquals(Integer.valueOf(20), desktop.getRatings().get(HP).getNumberOfReviews());
	}
	
	@Test
	public void shouldUpdatePreviousRating() throws Exception {
		Product productOriginal = findProduct("productPage2961.html"); //from day2 sample
		Product productUpdated = findProduct("productPage4000.html"); //modified previous file just for testing purposes
		
		Product desktopOriginal = (Product) parser.parse(parseHtml(productOriginal), productOriginal, config);
		Product desktopUpdated = (Product) parser.parse(parseHtml(productUpdated), productUpdated, config);
		
		desktopOriginal.populateCommonsToProduct(productOriginal);
		desktopUpdated.populateCommonsToProduct(productUpdated);
		
		assertEquals(Integer.valueOf(4), productOriginal.getRatings().get(HP).getRating());
		assertEquals(Integer.valueOf(5), productUpdated.getRatings().get(HP).getRating());

		//now updating
		productOriginal.upgradeEntityFrom(productUpdated);
		
		assertEquals(Integer.valueOf(5), productOriginal.getRatings().get(HP).getRating());
		assertEquals(Integer.valueOf(4), productOriginal.getRatings().get(HP).getPreviousRating());
		checkIsToday(productOriginal.getRatings().get(HP).getDateOfRatingChange());

	}
	
	@Test
	public void shouldSetDateAddedAndAvailableForSale() throws Exception {
		Product productOriginal = new Product();
		
		Product desktopOriginal = new Product();
		
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
		
		Product desktopOriginal = new Product();
		Product desktopUpdated = new Product();
		
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
		
		Product desktopOriginal = new Product();
		Product desktopUpdated = new Product();
		
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
		
		Product desktopOriginal = new Product();
		Product desktopUpdated = new Product();
		
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
		
		Product desktopOriginal = new Product();
		Product desktopUpdated = new Product();
		
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
	public void shouldParseAccessories() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"HP Mouse PadHP (2013) USDT Rear Port CoverUSB to Serial AdapterHP DisplayPort To HDMI 4k AdapterHP DisplayPort To HDMI 4k Adapter 2nd",
				getSpecification(desktop, "accessories"));
	}

	@Test
	public void shouldParseAdditionalApplicationSoftware() throws Exception {
		Product product = findProduct("productPage1369.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("Foxit Phantom PDF Business", getSpecification(desktop, "additionalApplicationSoftware"));
	}

	@Test
	public void shouldParseAdditionalNetworkingOptions() throws Exception {
		Product product = findProduct("productPage1369.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"Intel(R) Ethernet I210-T1 PCIe x1 Gb NIC HP WLAN 802.11 a/g/n 2x2 DB PCIe x1 Card Intel(R) 7260NB 802.11 a/b/g/n PCIe x1 NIC",
				getSpecification(desktop, "additionalNetworkingOptions"));
	}

	@Test
	public void shouldParseBattery() throws Exception {
		Product product = findProduct("productPage253.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("3-cell, 43 Wh prismatic", getSpecification(desktop, "battery"));
	}

	@Test
	public void shouldParseBatteryLife() throws Exception {
		Product product = findProduct("productPage253.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("Up to 7 hours and 45 minutes", getSpecification(desktop, "batteryLife"));
	}

	@Test
	public void shouldParseCableOptionKits() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("USDT Slim Optical Device Drive Cable Kit", getSpecification(desktop, "cableOptionKits"));
	}

	@Test
	public void shouldParseChassis() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("HP EliteDesk 800 USDT Chassis",
				getSpecification(desktop, "chassis"));
	}

	@Test
	public void shouldParseChinaCCCCompliance() throws Exception {
		Product product = findProduct("productPage1318.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("China Regulatory CCC Compliance Mark", getSpecification(desktop, "chinaCccCompliance"));
	}

	@Test
	public void shouldParseController() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("LSI 9270-8i SAS 6Gb/s ROC RAID Card", getSpecification(desktop, "controller"));
	}

	@Test
	public void shouldParseDisplay() throws Exception {
		Product product = findProduct("productPage10.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("23\" diagonal IPS FHD touch-enabled LED-backlit (1920 x 1080)", getSpecification(desktop, "display"));
	}

	@Test
	public void shouldParseDisplayCable() throws Exception {
		Product product = findProduct("productPage1361.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"DMS-59 To Dual VGA Y-Cable Adapter HP DisplayPort Cable HP DisplayPort To DVI-D Adapter DMS-59 To Dual DVI Y-Cable Adapter HP DisplayPort To HDMI Adapter HP DisplayPort To HDMI 1.4 Adapter HP DisplayPort To VGA Adapter",
				getSpecification(desktop, "displayCable"));
	}

	@Test
	public void shouldParseEnergyStar() throws Exception {
		Product product = findProduct("productPage1318.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("ENERGY STAR Qualified Configuration", getSpecification(desktop, "energyStar"));
	}

	@Test
	public void shouldParseEightInternalStorage() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"300GB 10K RPM SAS SFF 8th Hard Drive 256GB SATA 8th Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 8th Hard Drive 600GB 10K RPM SAS SFF 8th Hard Drive Samsung Enterprise 240GB SATA 8th Solid-State Drive 512GB SATA 8th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 8th Hard Drive 600GB 15k RPM SAS SFF 8th Hard Drive Samsung Enterprise 480GB SATA 8th Solid-State Drive (SSD) 1TB SATA 8th Solid-State Drive (SSD)",
				getSpecification(desktop, "eightInternalStorage"));
	}

	@Test
	public void shouldParseExternalIOPorts() throws Exception {
		Product product = findProduct("productPage150.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("2 USB 3.0; 4 USB 2.0", getSpecification(desktop, "externalIOPorts"));
	}

	@Test
	public void shouldParseExternalOpticalDrive() throws Exception {
		Product product = findProduct("productPage152.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("External USB DVD-RW", getSpecification(desktop, "externalOpticalDrive"));
	}

	@Test
	public void shouldParseFEMPCompliance() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("HP FEMP and ErP Qualifying S5 Low Power Mode Enabled Configuration", getSpecification(desktop, "fempCompliance"));
	}

	@Test
	public void shouldParseFifthInternalStorage() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"500GB 7200 RPM SATA 5th Hard Drive 1TB 7200 RPM SATA 5th Hard Drive 2TB 7200 RPM SATA 5th Hard Drive 300GB 10K RPM SAS SFF 5th Hard Drive 300GB 15k RPM SAS 5th Hard Drive 3TB 7200 RPM SATA 5th Hard Drive 256GB SATA 5th Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 5th Hard Drive 600GB 10K RPM SAS SFF 5th Hard Drive 4TB 7200 RPM SATA 5th Hard Drive 512GB SATA 5th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 5th Hard Drive 600GB 15k RPM SAS SFF 5th Hard Drive Samsung Enterprise 240GB SATA 5th Solid-State Drive (SSD) 1TB SATA 5th Solid-State Drive (SSD) Samsung Enterprise 480GB SATA 5th Solid-State Drive (SSD)",
				getSpecification(desktop, "fifthInternalStorage"));
	}

	@Test
	public void shouldParseFlashCache() throws Exception {
		Product product = findProduct("productPage261.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("8 GB", getSpecification(desktop, "flashCache"));
	}

	@Test
	public void shouldParseFourthInternalStorage() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"500GB 7200 RPM SATA 4th Hard Drive 1TB 7200 RPM SATA 4th Hard Drive 2TB 7200 RPM SATA 4th Hard Drive 300GB 10K RPM SAS SFF 4th Hard Drive 300GB 15k RPM SAS 4th Hard Drive 3TB 7200 RPM SATA 4th Hard Drive 256GB SATA 4th Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 4th Hard Drive 600GB 10K RPM SAS SFF 4th Hard Drive Samsung Enterprise 240GB SATA 4th Solid-State Drive (SSD) 4TB 7200 RPM SATA 4th Hard Drive 512GB SATA 4th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 4th Hard Drive 600GB 15k RPM SAS SFF 4th Hard Drive Samsung Enterprise 480GB SATA 4th Solid-State Drive (SSD) 1TB SATA 4th Solid-State Drive (SSD)",
				getSpecification(desktop, "fourthInternalStorage"));
	}

	@Test
	public void shouldParseGraphicsConnectors() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"HP DisplayPort To DVI-D Adapter HP DisplayPort To DVI-D Adapter (2-Pack) HP DisplayPort To VGA Adapter HP DisplayPort To VGA Adapter 2nd HP DisplayPort To DVI-D Adapter (4-Pack) HP DisplayPort To DVI-D Adapter (6-Pack) NVIDIA SLI Graphics Connector HP DisplayPort to Dual Link DVI Adapter",
				getSpecification(desktop, "graphicsConnectors"));
	}

	@Test
	public void shouldParseHighPerformanceGPUComputing() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"NVIDIA Tesla K40 1st Compute Processor - PCIe x2 NVIDIA Tesla K40 2nd Compute Processor - PCIe x2",
				getSpecification(desktop, "highPerformanceGpuComputing"));
	}

	@Test
	public void shouldParseIntegratedCamera() throws Exception {
		Product product = findProduct("productPage261.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"HP Illuminator: includes 4-camera system including Intel® RealSense™ 3D Camera and 14.6 MP high-resolution camera, HP DLP Projector, LED desk lamp",
				getSpecification(desktop, "integratedCamera"));
	}

	@Test
	public void shouldParseIntelSrtDiskCacheModules() throws Exception {
		Product product = findProduct("productPage1318.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("64GB SSD Disk Cache Module", getSpecification(desktop, "intelSrtDiskCacheModules"));
	}

	@Test
	public void shouldParseIntelSmartResponseTechnology() throws Exception {
		Product product = findProduct("productPage1384.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("32GB mSata Smart Response Technology", getSpecification(desktop, "intelSmartResponseTechnology"));
	}

	@Test
	public void shouldParseInternalOsLoadStorageOptions() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("Operating System Load to PCIe Operating System Load to SATA/SAS",
				getSpecification(desktop, "internalOsLoadStorageOptions"));
	}

	@Test
	public void shouldParseInternalPcieStorage() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"HP Z Turbo Drive 256GB PCIe 1st Solid-State Drive (SSD) HP Z Turbo Drive 512GB PCIe 1st Solid-State Drive (SSD)",
				getSpecification(desktop, "internalPcieStorage"));
	}

	@Test
	public void shouldParseInternalStorageOptions() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"PCIe Boot w/ HDD/SSD RAID 0 Data Array RAID 0 Data Array Configuration RAID 0 Data Array PCIe SSD Configuration RAID 0 Striped Array Configuration RAID 1 Mirrored Array Configuration RAID 5 BootHDD+Parity Array Configuration RAID 5 Parity Array Configuration RAID10 Striped/Mirrored Configuration",
				getSpecification(desktop, "internalStorageOptions"));
	}

	@Test
	public void shouldParseLanTransceivers() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("HP 10GbE SFP+ SR 1st Transceiver HP 10GbE SFP+ SR 2nd Transceiver", getSpecification(desktop, "lanTransceivers"));
	}

	@Test
	public void shouldParseLabel() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("FDA Class 1 Medical Device Registration", getSpecification(desktop, "label"));
	}

	@Test
	public void shouldParseMaximumMemory() throws Exception {
		Product product = findProduct("productPage10.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("Upgradeable to 16 GB", getSpecification(desktop, "maximumMemory"));
	}

	@Test
	public void shouldParseSecuritySoftware() throws Exception {
		Product product = findProduct("productPage256.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"No Additional Security Software Preinstalled McAfee Livesafe 12 months Preinstalled McAfee Livesafe 24 months Preinstalled McAfee Livesafe 36 months",
				getSpecification(desktop, "securitySoftware"));
	}

	@Test
	public void shouldParseMediaReader() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("HP 15-In-1 Media Card Reader", getSpecification(desktop, "mediaReader"));
	}

	@Test
	public void shouldParseMemoryCardDevice() throws Exception {
		Product product = findProduct("productPage10.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("7-in-1 memory card reader", getSpecification(desktop, "memoryCardDevice"));
	}

	@Test
	public void shouldParseProcessorTechnology() throws Exception {
		Product product = findProduct("productPage1389.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("Intel® Core™ i7 with vPro technology", getSpecification(desktop, "processorTechnology"));
	}

	@Test
	public void shouldParseRealTimeDataBackup() throws Exception {
		Product product = findProduct("productPage1318.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("RAID 0 Striped Array Configuration RAID 1 Mirrored Array Configuration",
				getSpecification(desktop, "realTimeDataBackup"));
	}

	@Test
	public void shouldParseSecondDisplayCable() throws Exception {
		Product product = findProduct("productPage1369.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"DMS-59 To Dual VGA Y-Cable Adapter 2nd HP DisplayPort Cable 2nd HP DisplayPort To DVI-D Adapter 2nd DMS-59 To Dual DVI Y-Cable Adapter 2nd HP DisplayPort To HDMI Adapter 2nd HP DisplayPort To HDMI 1.4 Adapter 2nd HP DisplayPort To VGA Adapter 2nd",
				getSpecification(desktop, "secondDisplayCable"));
	}

	@Test
	public void shouldParseSecondGraphicsCard() throws Exception {
		Product product = findProduct("productPage2651.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"NVIDIA NVS 310 512MB 2nd Graphics - PCIeNVIDIA NVS 315 1GB DMS59 2nd DMS59-2xDVI cable Graphics - PCIeNVIDIA Quadro K420 1GB DL-DVI(I)+DP 2nd No cables included Graphics - PCIeAMD FirePro W2100 2GB 2xDP 2nd No cables included GraphicsNVIDIA Quadro K620 2GB DL-DVI(I)+DP 2nd No cables included Graphics - PCIeAMD FirePro W5100 4GB 4xDP 2nd No cables included GraphicsNVIDIA Quadro K2200 4GB DL-DVI(I)+2xDP 2nd No cables included Graphics - PCIeAMD FirePro W7100 8GB 4xDP 2nd No cables included GraphicsNVIDIA Quadro K4200 4GB DL-DVI(I)+2xDP 2nd No cables included Graphics - PCIeNVIDIA Quadro K5200 8GB DL-DVI(I)+DL-DVI(D)+2xDP 2nd No cables included Graphics - PCIeNVIDIA Quadro K6000 12GB DL-DVI(I)+DL-DVI(D)+DP+DP 2nd No cable included Graphics - PCIeNVIDIA Quadro M6000 12GB DL-DVI(I)+4xDP 2nd No cable included Graphics",
				getSpecification(desktop, "secondGraphicsCard"));
	}

	@Test
	public void shouldParseSecondInternalStorage() throws Exception {
		Product product = findProduct("productPage2651.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"500GB 7200 RPM SATA 2nd Hard Drive1TB 7200 RPM SATA 2nd Hard Drive128GB SATA 2nd Solid-State Drive (SSD)2TB 7200 RPM SATA 2nd Hard Drive300GB 10K RPM SAS SFF 2nd Hard Drive3TB 7200 RPM SATA 2nd Hard Drive256GB SATA 2nd Solid-State Drive (SSD)300GB 15k RPM SAS SFF 2nd Hard Drive600GB 10K RPM SAS SFF 2nd Hard DriveSamsung Enterprise 240GB SATA 2nd Solid-State Drive (SSD)4TB 7200 RPM SATA 2nd Hard Drive512GB SATA 2nd Solid-State Drive (SSD)1.2TB 10K RPM SAS SFF 2nd Hard Drive600GB 15k RPM SAS SFF 2nd Hard DriveSamsung Enterprise 480GB SATA 2nd Solid-State Drive (SSD)1TB SATA 2nd Solid-State Drive (SSD)",
				getSpecification(desktop, "secondInternalStorage"));
	}

	@Test
	public void shouldParseSecondaryInternalPcieStorage() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"HP Z Turbo Drive 256GB PCIe 2nd Solid-State Drive (SSD) HP Z Turbo Drive 512GB PCIe 2nd Solid-State Drive (SSD)",
				getSpecification(desktop, "secondaryInternalPcieStorage"));
	}

	@Test
	public void shouldParseSecondaryOpticalDrive() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"9.5mm Slim SuperMulti DVDRW SATA 2nd Optical Disc Drive 9.5mm Slim Blu-ray Writer SATA 2nd Optical Disc Drive",
				getSpecification(desktop, "secondaryOpticalDrive"));
	}

	@Test
	public void shouldParseSecondaryProcessor() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"Intel(R) Xeon(R) E5-2603v3 1.6 1600 6C 2nd CPU Intel(R) Xeon(R) E5-2609v3 1.9 1600 6C 2nd CPU Intel(R) Xeon(R) E5-2620v3 2.4 1866 6C 2nd CPU Intel(R) Xeon(R) E5-2623v3 3.0 1866 4C 2nd CPU Intel(R) Xeon(R) E5-2630v3 2.4 1866 8C 2nd CPU Intel(R) Xeon(R) E5-2640v3 2.6 1866 8C 2nd CPU Intel(R) Xeon(R) E5-2637v3 3.5 2133 4C 2nd CPU Intel(R) Xeon(R) E5-2650v3 2.3 2133 10C 2nd CPU Intel(R) Xeon(R) E5-2660v3 2.6 2133 10C 2nd CPU Intel(R) Xeon(R) E5-2643v3 3.4 2133 6C 2nd CPU Intel(R) Xeon(R) E5-2670v3 2.3 2133 12C 2nd CPU Intel(R) Xeon(R) E5-2680v3 2.5 2133 12C 2nd CPU Intel(R) Xeon(R) E5-2683v3 2.0 2133 14C 2nd CPU Intel(R) Xeon(R) E5-2690v3 2.6 2133 12C 2nd CPU Intel(R) Xeon(R) E5-2687Wv3 3.1 2133 10C 2nd CPU Intel(R) Xeon(R) E5-2667v3 3.2 2133 8C 2nd CPU Intel(R) Xeon(R) E5-2695v3 2.3 2133 14C 2nd CPU Intel(R) Xeon(R) E5-2697v3 2.6 2133 14C 2nd CPU Intel(R) Xeon(R) E5-2699v3 2.3 2133 18C 2nd CPU",
				getSpecification(desktop, "secondaryProcessor"));
	}

	@Test
	public void shouldParseSecurity() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"USB port disableHP Solenoid Lock and Hood (USDT) SensorHP UltraSlim Cable Lock",
				getSpecification(desktop, "security"));
	}

	@Test
	public void shouldParseSecurityEncryption() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("HP TPM Disabled", getSpecification(desktop, "securityEncryption"));
	}

	@Test
	public void shouldParseSixthInternalStorage() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"300GB 10K RPM SAS SFF 6th Hard Drive 256GB SATA 6th Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 6th Hard Drive 600GB 10K RPM SAS SFF 6th Hard Drive 512GB SATA 6th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 6th Hard Drive 600GB 15k RPM SAS SFF 6th Hard Drive Samsung Enterprise 240GB SATA 6th Solid-State Drive (SSD) 1TB SATA 6th Solid-State Drive (SSD) Samsung Enterprise 480GB SATA 6th Solid-State Drive (SSD)",
				getSpecification(desktop, "sixthInternalStorage"));
	}

	@Test
	public void shouldParseSeventhInternalStorage() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"300GB 10K RPM SAS SFF 7th Hard Drive 300GB 15k RPM SAS SFF 7th Hard Drive 600GB 10K RPM SAS SFF 7th Hard Drive Samsung Enterprise 240GB SATA 7th Solid-State Drive (SSD) 256GB SATA 7th Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 7th Hard Drive 600GB 15k RPM SAS SFF 7th Hard Drive Samsung Enterprise 480GB SATA 7th Solid-State Drive (SSD) 512GB SATA 7th Solid-State Drive (SSD) 1TB SATA 7th Solid-State Drive (SSD)",
				getSpecification(desktop, "seventhInternalStorage"));
	}

	@Test
	public void shouldParseSoftwareBundles() throws Exception {
		Product product = findProduct("productPage238.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("WordPerfect Office X7 Home + Student Edition", getSpecification(desktop, "softwareBundles"));
	}


	@Test
	public void shouldParseStand() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("HP USDT Chassis Tower Stand", getSpecification(desktop, "stand"));
	}

	@Test
	public void shouldParseSystemRecoverySolutions() throws Exception {
		Product product = findProduct("productPage2556.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"Windows 7 Professional 32-bit OS DVD+ DRDVDWindows 7 Professional 64-bit OS DVD+ DRDVD",
				getSpecification(desktop, "systemRecoverySolutions"));
	}

	@Test
	public void shouldParseTvTuner() throws Exception {
		Product product = findProduct("productPage152.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("No TV Tuner Card TV Tuner- ATSC/NTSC with PVR, External USB Stick", getSpecification(desktop, "tvTuner"));
	}

	@Test
	public void shouldParseTechnical() throws Exception {
		Product product = findProduct("productPage1384.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("Microsoft Lync Label", getSpecification(desktop, "technical"));
	}

	@Test
	public void shouldParseTechnicalAV() throws Exception {
		Product product = findProduct("productPage1444.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals("HP Processor Air Cooling Kit", getSpecification(desktop, "technicalAV"));
	}

	@Test
	public void shouldParseThirdGraphicsCard() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"AMD FirePro W5100 4GB 4xDP 3rd No cables included Graphics NVIDIA Quadro K2200 4GB DL-DVI(I)+2xDP 3rd No cables included Graphics - PCIe NVIDIA Quadro K5200 8GB DL-DVI(I)+DL-DVI(D)+2xDP 3rd No cables included Graphics - PCIe",
				getSpecification(desktop, "thirdGraphicsCard"));
	}

	@Test
	public void shouldParseThirdInternalStorage() throws Exception {
		Product product = findProduct("productPage1306.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"500GB 7200 RPM SATA 3rd Hard Drive 1TB 7200 RPM SATA 3rd Hard Drive 2TB 7200 RPM SATA 3rd Hard Drive 300GB 10K RPM SAS SFF 3rd Hard Drive 300GB 15k RPM SAS 3rd Hard Drive 3TB 7200 RPM SATA 3rd Hard Drive 256GB SATA 3rd Solid-State Drive (SSD) 300GB 15k RPM SAS SFF 3rd Hard Drive 600GB 10K RPM SAS SFF 3rd Hard Drive Samsung Enterprise 240GB SATA 3rd Solid-State Drive (SSD) 4TB 7200 RPM SATA 3rd Hard Drive 512GB SATA 3rd Solid-State Drive (SSD) 1.2TB 10K RPM SAS SFF 3rd Hard Drive 600GB 15k RPM SAS SFF 3rd Hard Drive Samsung Enterprise 480GB SATA 3rd Solid-State Drive (SSD) 1TB SATA 3rd Solid-State Drive (SSD)",
				getSpecification(desktop, "thirdInternalStorage"));
	}

	@Test
	public void shouldParseWebcam() throws Exception {
		Product product = findProduct("productPage10.html");
		Product desktop = (Product) parser.parse(parseHtml(product), product, config);

		assertEquals(
				"720p HP TrueVision HD Webcam with integrated microphones",
				getSpecification(desktop, "webcam"));
	}


}
