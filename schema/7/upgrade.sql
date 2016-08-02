ALTER TABLE Product ADD COLUMN productId VARCHAR(5000) NOT NULL default '';
ALTER TABLE Product ADD COLUMN primaryProduct BOOLEAN NOT NULL default true;
ALTER TABLE Product ADD COLUMN siteId INTEGER NOT NULL default 1;

ALTER TABLE ProductSpecification ADD COLUMN productId VARCHAR(5000) NOT NULL default '';
ALTER TABLE ProductSpecification ADD COLUMN displayOrder INT NOT NULL default 0;

ALTER TABLE ProductImage ADD COLUMN productId VARCHAR(5000) NOT NULL default '';

ALTER TABLE RelatedAccessory ADD COLUMN accessoryProductId  VARCHAR(5000) NOT NULL default '';
ALTER TABLE RelatedAccessory ADD COLUMN productId  VARCHAR(5000) NOT NULL default '';

ALTER TABLE ProductReview ADD COLUMN productId VARCHAR(5000) NOT NULL default '';
ALTER TABLE ProductPrice ADD COLUMN productId VARCHAR(5000) NOT NULL default '';

ALTER TABLE ProductRating ADD COLUMN productId VARCHAR(5000) NOT NULL default '';

UPDATE Product set productId = (siteId || ' - ' || productNumber);
UPDATE ProductSpecification set productId = (1 || ' - ' || productNumber);
UPDATE ProductImage set productId = (1 || ' - ' || productNumber);
UPDATE RelatedAccessory set productId = (1 || ' - ' || productNumber);
UPDATE RelatedAccessory set accessoryProductId = (1 || ' - ' || accessoryProductNumber);
UPDATE ProductReview set productId = (siteId || ' - ' || productNumber);
UPDATE ProductPrice set productId = (siteId || ' - ' || productNumber);
UPDATE ProductRating set productId = (siteId || ' - ' || productNumber);

ALTER TABLE ProductSpecification DROP CONSTRAINT C_PRIMARY;
ALTER TABLE ProductSpecification DROP CONSTRAINT C_FOREIGN;

ALTER TABLE ProductImage DROP CONSTRAINT C_PRIMARY;
ALTER TABLE ProductImage DROP CONSTRAINT C_FOREIGN;

ALTER TABLE RelatedAccessory DROP CONSTRAINT C_PRIMARY;
ALTER TABLE RelatedAccessory DROP CONSTRAINT C_FOREIGN;
ALTER TABLE RelatedAccessory DROP CONSTRAINT C_FOREIGN_1;

ALTER TABLE ProductReview DROP CONSTRAINT C_PRIMARY;
ALTER TABLE ProductReview DROP CONSTRAINT C_FOREIGN;
ALTER TABLE ProductReview DROP CONSTRAINT C_FOREIGN_1;

ALTER TABLE ProductPrice DROP CONSTRAINT C_PRIMARY;
ALTER TABLE ProductPrice DROP CONSTRAINT C_FOREIGN;
ALTER TABLE ProductPrice DROP CONSTRAINT C_FOREIGN_1;

ALTER TABLE ProductRating DROP CONSTRAINT C_PRIMARY;
ALTER TABLE ProductRating DROP CONSTRAINT C_FOREIGN;
ALTER TABLE ProductRating DROP CONSTRAINT C_FOREIGN_1;

DROP TEXT INDEX ProductFullTextSearch;

ALTER TABLE Product DROP CONSTRAINT C_PRIMARY;

ALTER TABLE Product ADD PRIMARY KEY (productId);
ALTER TABLE Product ADD UNIQUE (productNumber, siteId);
ALTER TABLE Product ADD FOREIGN KEY(siteId) REFERENCES Site;

ALTER TABLE ProductSpecification ADD FOREIGN KEY(productId) REFERENCES Product;
ALTER TABLE ProductImage ADD FOREIGN KEY(productId) REFERENCES Product;
ALTER TABLE RelatedAccessory ADD FOREIGN KEY(productId) REFERENCES Product;
ALTER TABLE RelatedAccessory ADD FOREIGN KEY(accessoryProductId) REFERENCES Product;

ALTER TABLE ProductReview ADD FOREIGN KEY(productId) REFERENCES Product;
ALTER TABLE ProductReview ADD FOREIGN KEY(siteId) REFERENCES Site;


ALTER TABLE ProductPrice ADD FOREIGN KEY(siteId) REFERENCES Site;
ALTER TABLE ProductRating ADD FOREIGN KEY(siteId) REFERENCES Site;

ALTER TABLE ProductSpecification ADD PRIMARY KEY (productId, name);
ALTER TABLE ProductImage ADD PRIMARY KEY (url, productId);
ALTER TABLE RelatedAccessory ADD PRIMARY KEY (accessoryProductId, productId);
ALTER TABLE ProductReview ADD PRIMARY KEY (productId, id);
ALTER TABLE ProductPrice ADD PRIMARY KEY (productId);
ALTER TABLE ProductRating ADD PRIMARY KEY (productId);

CREATE PROJECTION Product_txtindex AS SELECT * FROM Product order by productId SEGMENTED BY HASH(productId) ALL NODES KSAFE;

CREATE TEXT INDEX ProductFullTextSearch ON Product (productId, fullText);


INSERT INTO Site (id, siteName, baseURL, language, currency) VALUES (3, 'HP UK Store', 'http://store.hp.com/UKStore', 'en_GB', 'GBP');
INSERT INTO Site (id, siteName, baseURL, language, currency) VALUES (4, 'HP Germany Store', 'http://store.hp.com/GermanyStore', 'de', 'EUR');

UPDATE ProductSpecification SET name='Native resolution' where name='nativeResolution';
UPDATE ProductSpecification SET name='Contrast ratio' where name='contrastRatio';
UPDATE ProductSpecification SET name='Brightness' where name='brightness';
UPDATE ProductSpecification SET name='Pixel pitch' where name='pixelPitch';
UPDATE ProductSpecification SET name='Response time' where name='responseTime';
UPDATE ProductSpecification SET name='Display Tilt &amp; Swivel Range' where name='displayTiltAndSwivelRange';
UPDATE ProductSpecification SET name='Energy efficiency' where name='energyEfficiency';
UPDATE ProductSpecification SET name='Dimensions (W x D x H)' where name='dimensions';
UPDATE ProductSpecification SET name='Weight' where name='weight';
UPDATE ProductSpecification SET name='Color(s) of print cartridges' where name='colorsOfPrintCartridges';
UPDATE ProductSpecification SET name='Page yield' where name='pageYield';
UPDATE ProductSpecification SET name='Page yield footnote' where name='pageYieldFootnote';
UPDATE ProductSpecification SET name='Ink drop' where name='inkDrop';
UPDATE ProductSpecification SET name='Compatible ink types' where name='compatibleInkTypes';
UPDATE ProductSpecification SET name='Operating temperature range' where name='operatingTemperatureRange';
UPDATE ProductSpecification SET name='Operating humidity range' where name='operatingHumidityRange';
UPDATE ProductSpecification SET name='Storage temperature range' where name='storageTemperatureRange';
UPDATE ProductSpecification SET name='Storage humidity' where name='storageHumidity';
UPDATE ProductSpecification SET name='Package dimensions (W x D x H)' where name='packageDimensions';
UPDATE ProductSpecification SET name='Package weight' where name='packageWeight';
UPDATE ProductSpecification SET name='Dimensions' where name='dimensions';
UPDATE ProductSpecification SET name='Weight' where name='weight';
UPDATE ProductSpecification SET name='Display' where name='display';
UPDATE ProductSpecification SET name='Security management' where name='securityManagement';
UPDATE ProductSpecification SET name='Audio' where name='audio';
UPDATE ProductSpecification SET name='Battery' where name='battery';
UPDATE ProductSpecification SET name='Battery life' where name='batteryLife';
UPDATE ProductSpecification SET name='Color' where name='color';
UPDATE ProductSpecification SET name='Dimension depth in inches' where name='dimensionDepthInInches';
UPDATE ProductSpecification SET name='Dimension height in inches' where name='dimensionHeightInInches';
UPDATE ProductSpecification SET name='Dimension width in inches' where name='dimensionWidthInInches';
UPDATE ProductSpecification SET name='Expansion slots' where name='expansionSlots';
UPDATE ProductSpecification SET name='Integrated camera' where name='integratedCamera';
UPDATE ProductSpecification SET name='Internal storage' where name='internalStorage';
UPDATE ProductSpecification SET name='Max battery life in hours' where name='maxBatteryLifeInHours';
UPDATE ProductSpecification SET name='Memory' where name='memory';
UPDATE ProductSpecification SET name='Model number' where name='modelNumber';
UPDATE ProductSpecification SET name='Operating system' where name='operatingSystem';
UPDATE ProductSpecification SET name='Ports' where name='ports';
UPDATE ProductSpecification SET name='Processor' where name='processor';
UPDATE ProductSpecification SET name='Sensors' where name='sensors';
UPDATE ProductSpecification SET name='Weight in pounds' where name='weightInPounds';
UPDATE ProductSpecification SET name='Wireless' where name='wireless';
UPDATE ProductSpecification SET name='Chipset' where name='chipset';
UPDATE ProductSpecification SET name='Energy efficiency' where name='energyEfficiency';
UPDATE ProductSpecification SET name='Graphics' where name='graphics';
UPDATE ProductSpecification SET name='Internal drive' where name='internalDrive';
UPDATE ProductSpecification SET name='Power supply' where name='powerSupply';
UPDATE ProductSpecification SET name='Processor family' where name='processorFamily';
UPDATE ProductSpecification SET name='Processor technology' where name='processorTechnology';
UPDATE ProductSpecification SET name='Dimensions' where name='dimensions';
UPDATE ProductSpecification SET name='Weight' where name='weight';
UPDATE ProductSpecification SET name='Display' where name='display';
UPDATE ProductSpecification SET name='Security management' where name='securityManagement';
UPDATE ProductSpecification SET name='Battery' where name='battery';
UPDATE ProductSpecification SET name='Battery life' where name='batteryLife';
UPDATE ProductSpecification SET name='Color' where name='color';
UPDATE ProductSpecification SET name='Dimension depth in inches' where name='dimensionDepthInInches';
UPDATE ProductSpecification SET name='Dimension height in inches' where name='dimensionHeightInInches';
UPDATE ProductSpecification SET name='Dimension width in inches' where name='dimensionWidthInInches';
UPDATE ProductSpecification SET name='Energy efficiency' where name='energyEfficiency';
UPDATE ProductSpecification SET name='Expansion slots' where name='expansionSlots';
UPDATE ProductSpecification SET name='Graphics' where name='graphics';
UPDATE ProductSpecification SET name='Hard drive' where name='hardDrive';
UPDATE ProductSpecification SET name='Keyboard' where name='keyboard';
UPDATE ProductSpecification SET name='Max battery life in hours' where name='maxBatteryLifeInHours';
UPDATE ProductSpecification SET name='Memory' where name='memory';
UPDATE ProductSpecification SET name='Model number' where name='modelNumber';
UPDATE ProductSpecification SET name='Networking' where name='networking';
UPDATE ProductSpecification SET name='Operating system' where name='operatingSystem';
UPDATE ProductSpecification SET name='Pointing devices' where name='pointingDevices';
UPDATE ProductSpecification SET name='Ports' where name='ports';
UPDATE ProductSpecification SET name='Power supply' where name='powerSupply';
UPDATE ProductSpecification SET name='Processor' where name='processor';
UPDATE ProductSpecification SET name='Webcam' where name='webcam';
UPDATE ProductSpecification SET name='Weight in pounds' where name='weightInPounds';
UPDATE ProductSpecification SET name='Ac adapter' where name='acAdapter';
UPDATE ProductSpecification SET name='Accessories' where name='accessories';
UPDATE ProductSpecification SET name='Additional bay' where name='additionalBay';
UPDATE ProductSpecification SET name='Audio' where name='audio';
UPDATE ProductSpecification SET name='Bluetooth' where name='bluetooth';
UPDATE ProductSpecification SET name='Broadband service provider' where name='broadbandServiceProvider';
UPDATE ProductSpecification SET name='Chipset' where name='chipset';
UPDATE ProductSpecification SET name='External iO ports' where name='externalIOPorts';
UPDATE ProductSpecification SET name='Finger print reader' where name='fingerPrintReader';
UPDATE ProductSpecification SET name='Flash cache' where name='flashCache';
UPDATE ProductSpecification SET name='Hp mobile broadband' where name='hpMobileBroadband';
UPDATE ProductSpecification SET name='Label energy star' where name='labelEnergyStar';
UPDATE ProductSpecification SET name='Laplink pcmover software' where name='laplinkPcmoverSoftware';
UPDATE ProductSpecification SET name='Security software' where name='securitySoftware';
UPDATE ProductSpecification SET name='Memory slots' where name='memorySlots';
UPDATE ProductSpecification SET name='Mini card' where name='miniCard';
UPDATE ProductSpecification SET name='Mini card ssd' where name='miniCardSsd';
UPDATE ProductSpecification SET name='Misc warranty documentation' where name='miscWarrantyDocumentation';
UPDATE ProductSpecification SET name='Modem' where name='modem';
UPDATE ProductSpecification SET name='Near field communication' where name='nearFieldCommunication';
UPDATE ProductSpecification SET name='Os recovery cd' where name='osRecoveryCd';
UPDATE ProductSpecification SET name='Office software' where name='officeSoftware';
UPDATE ProductSpecification SET name='Optical drive' where name='opticalDrive';
UPDATE ProductSpecification SET name='Out of band management' where name='outOfBandManagement';
UPDATE ProductSpecification SET name='Personalization' where name='personalization';
UPDATE ProductSpecification SET name='Power cord' where name='powerCord';
UPDATE ProductSpecification SET name='Processor family' where name='processorFamily';
UPDATE ProductSpecification SET name='Processor technology' where name='processorTechnology';
UPDATE ProductSpecification SET name='Recovery media driver' where name='recoveryMediaDriver';
UPDATE ProductSpecification SET name='Sensors' where name='sensors';
UPDATE ProductSpecification SET name='Theft protection' where name='theftProtection';
UPDATE ProductSpecification SET name='Warranty battery' where name='warrantyBattery';
UPDATE ProductSpecification SET name='Box contents' where name='boxContents';
UPDATE ProductSpecification SET name='Wireless lan' where name='wirelessLan';

DELETE FROM ProductSpecification WHERE name NOT IN (
'Native resolution',
'Contrast ratio',
'Brightness',
'Pixel pitch',
'Response time',
'Display Tilt &amp; Swivel Range',
'Energy efficiency',
'Dimensions (W x D x H)',
'Weight',
'Color(s) of print cartridges',
'Page yield',
'Page yield footnote',
'Ink drop',
'Compatible ink types',
'Operating temperature range',
'Operating humidity range',
'Storage temperature range',
'Storage humidity',
'Package dimensions (W x D x H)',
'Package weight',
'Dimensions',
'Weight',
'Display',
'Security management',
'Audio',
'Battery',
'Battery life',
'Color',
'Dimension depth in inches',
'Dimension height in inches',
'Dimension width in inches',
'Expansion slots',
'Integrated camera',
'Internal storage',
'Max battery life in hours',
'Memory',
'Model number',
'Operating system',
'Ports',
'Processor',
'Sensors',
'Weight in pounds',
'Wireless',
'Chipset',
'Energy efficiency',
'Graphics',
'Internal drive',
'Power supply',
'Processor family',
'Processor technology',
'Dimensions',
'Weight',
'Display',
'Security management',
'Battery',
'Battery life',
'Color',
'Dimension depth in inches',
'Dimension height in inches',
'Dimension width in inches',
'Energy efficiency',
'Expansion slots',
'Graphics',
'Hard drive',
'Keyboard',
'Max battery life in hours',
'Memory',
'Model number',
'Networking',
'Operating system',
'Pointing devices',
'Ports',
'Power supply',
'Processor',
'Webcam',
'Weight in pounds',
'Ac adapter',
'Accessories',
'Additional bay',
'Audio',
'Bluetooth',
'Broadband service provider',
'Chipset',
'External iO ports',
'Finger print reader',
'Flash cache',
'Hp mobile broadband',
'Label energy star',
'Laplink pcmover software',
'Security software',
'Memory slots',
'Mini card',
'Mini card ssd',
'Misc warranty documentation',
'Modem',
'Near field communication',
'Os recovery cd',
'Office software',
'Optical drive',
'Out of band management',
'Personalization',
'Power cord',
'Processor family',
'Processor technology',
'Recovery media driver',
'Sensors',
'Theft protection',
'Warranty battery',
'Box contents',
'Wireless lan'
);



