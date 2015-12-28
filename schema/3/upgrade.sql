ALTER TABLE Product ADD fullText LONG VARCHAR(1000000);

ALTER TABLE Laptop RENAME COLUMN poitingDevices TO pointingDevices;

ALTER TABLE RelatedAccessory ADD accessoryProductNumber VARCHAR(5000) NOT NULL;
ALTER TABLE RelatedAccessory DROP CONSTRAINT "C_PRIMARY";
ALTER TABLE RelatedAccessory ADD PRIMARY KEY ("productNumber", "accessoryProductNumber");
ALTER TABLE RelatedAccessory DROP COLUMN url;
ALTER TABLE RelatedAccessory DROP COLUMN name;
ALTER TABLE RelatedAccessory DROP COLUMN version;

ALTER TABLE Product ADD hpDataSheet VARCHAR(5000);
ALTER TABLE Product ADD parseDate TIMESTAMP NOT NULL;

DROP TABLE IF EXISTS Monitor;
CREATE TABLE Monitor (
  productNumber                      VARCHAR(5000) NOT NULL REFERENCES Product,
  version                            INTEGER       NOT NULL,
  hpDataSheet                        VARCHAR(5000),
  parseDate                          TIMESTAMP     NOT NULL,
  productName                        VARCHAR(5000) NOT NULL,
  productUrl                         VARCHAR(5000) NOT NULL,
  nativeResolution                   VARCHAR(5000),
  contrastRatio                      VARCHAR(5000),
  brightness                         VARCHAR(5000),
  pixelPitch                         VARCHAR(5000),
  responseTime                       VARCHAR(5000),
  displayTiltAndSwivelRange          VARCHAR(5000),
  energyEfficiency                   VARCHAR(5000),
  dimensions                         VARCHAR(5000),
  weight                             VARCHAR(5000),
  whatsInTheBox                      VARCHAR(5000),
  PRIMARY KEY (productNumber)
);

DROP TABLE IF EXISTS InkAndToner;
CREATE TABLE InkAndToner (
  productNumber                      VARCHAR(5000) NOT NULL REFERENCES Product,
  version                            INTEGER       NOT NULL,
  hpDataSheet                        VARCHAR(5000),
  parseDate                          TIMESTAMP     NOT NULL,
  productName                        VARCHAR(5000) NOT NULL,
  productUrl                         VARCHAR(5000) NOT NULL,
  colorsOfPrintCartridges            VARCHAR(5000),
  pageYield                          VARCHAR(5000),
  pageYieldFootnote                  VARCHAR(5000),
  inkDrop                            VARCHAR(5000),
  compatibleInkTypes                 VARCHAR(5000),
  operatingTemperatureRange          VARCHAR(5000),
  storageTemperatureRange            VARCHAR(5000),
  operatingHumidityRange             VARCHAR(5000),
  storageHumidity                    VARCHAR(5000),
  packageDimensions                  VARCHAR(5000),
  packageWeight                      VARCHAR(5000),
  warranty                           VARCHAR(5000),
  whatsInTheBox                      VARCHAR(5000),
  PRIMARY KEY (productNumber)
);
