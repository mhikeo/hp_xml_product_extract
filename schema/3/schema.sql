DROP TABLE Desktop CASCADE;

DROP TABLE Laptop CASCADE;

DROP TABLE Printer CASCADE;

DROP TABLE Product CASCADE;

DROP TABLE ProductImage CASCADE;

DROP TABLE RelatedAccessory CASCADE;

DROP TABLE Tablet CASCADE;

DROP TABLE ProductReview CASCADE;

DROP TABLE Site CASCADE;

DROP TABLE ProductPrice CASCADE;

DROP TABLE ProductRating CASCADE;

DROP TABLE InkAndToner CASCADE;

DROP TABLE Monitor CASCADE;

CREATE TABLE Product (
  productNumber         VARCHAR(5000) NOT NULL,
  version               INTEGER       NOT NULL,
  auditTimeStamp        TIMESTAMP NOT NULL,
  id                    INTEGER NOT NULL,
  productName           VARCHAR(5000) NOT NULL,
  productUrl            VARCHAR(5000) NOT NULL,
  sourceFile            VARCHAR(5000) NOT NULL,
  productType           VARCHAR(5000) NOT NULL,
  dateAdded             DATE NOT NULL, 
  parsingError	        VARCHAR(5000),
  dateOfParsingError    DATE,
  comingSoonDate	      DATE,
  availableForSaleDate  DATE,
  fullText              LONG VARCHAR(1000000)
  PRIMARY KEY (productNumber)
);


CREATE TABLE Desktop (
  productNumber                      VARCHAR(5000) NOT NULL REFERENCES Product,
  version                            INTEGER       NOT NULL,
  hpDataSheet                        VARCHAR(5000),
  parseDate                          TIMESTAMP     NOT NULL,
  productName                        VARCHAR(5000) NOT NULL,
  productUrl                         VARCHAR(5000) NOT NULL,
  rating                             INTEGER,
  audio                              VARCHAR(5000),
  color                              VARCHAR(5000),
  dimensionDepthInInches             NUMERIC(10, 2),
  dimensionHeightInInches            NUMERIC(10, 2),
  dimensionWidthInInches             NUMERIC(10, 2),
  dimensions                         VARCHAR(5000),
  energyEfficiency                   VARCHAR(5000),
  expansionSlots                     VARCHAR(5000),
  externalIOPorts                    VARCHAR(5000),
  graphics                           VARCHAR(5000),
  hardDrive                          VARCHAR(5000),
  keyboard                           VARCHAR(5000),
  memory                             VARCHAR(5000),
  memorySlots                        VARCHAR(5000),
  modelNumber                        VARCHAR(5000),
  networking                         VARCHAR(5000),
  officeSoftware                     VARCHAR(5000),
  operatingSystem                    VARCHAR(5000),
  opticalDrive                       VARCHAR(5000),
  photographySoftware                VARCHAR(5000),
  pointingDevices                    VARCHAR(5000),
  popularSoftware                    VARCHAR(5000),
  ports                              VARCHAR(5000),
  powerSupply                        VARCHAR(5000),
  processor                          VARCHAR(5000),
  softwareIncluded                   VARCHAR(5000),
  softwareUpgrades                   VARCHAR(5000),
  soundCard                          VARCHAR(5000),
  tvTurner                           VARCHAR(5000),
  warranty                           VARCHAR(5000),
  weight                             VARCHAR(5000),
  weightInPounds                     NUMERIC(10, 2),
  accessories			                   VARCHAR(5000),
  additionalApplicationSoftware      VARCHAR(5000),
  additionalNetworkingOptions        VARCHAR(5000),
  battery                            VARCHAR(5000),
  batteryLife                        VARCHAR(5000), 
  cableOptionKits                    VARCHAR(5000),
  chassis                            VARCHAR(5000),
  chinaCccCompliance                 VARCHAR(5000),
  controller                         VARCHAR(5000),
  display                            VARCHAR(5000),
  displayCable                       VARCHAR(5000),
  energyStar                         VARCHAR(5000),
  eightInternalStorage               VARCHAR(5000),
  externalOpticalDrive               VARCHAR(5000),
  fempCompliance                     VARCHAR(5000),
  fifthInternalStorage               VARCHAR(5000),
  flashCache                         VARCHAR(5000),
  fourthInternalStorage              VARCHAR(5000),
  graphicsConnectors                 VARCHAR(5000),
  highPerformanceGpuComputing        VARCHAR(5000),
  integratedCamera                   VARCHAR(5000),
  intelSrtDiskCacheModules           VARCHAR(5000),
  intelSmartResponseTechnology       VARCHAR(5000),
  internalOsLoadStorageOptions       VARCHAR(5000),
  internalPcieStorage                VARCHAR(5000),
  internalStorageOptions             VARCHAR(5000),
  lanTransceivers                    VARCHAR(5000),
  label                              VARCHAR(5000),
  maximumMemory                      VARCHAR(5000),
  securitySoftware                   VARCHAR(5000),
  mediaReader                        VARCHAR(5000),
  memoryCardDevice                   VARCHAR(5000),
  processorTechnology                VARCHAR(5000),
  realTimeDataBackup                 VARCHAR(5000),
  secondDisplayCable                 VARCHAR(5000),
  secondGraphicsCard                 VARCHAR(5000),
  secondInternalStorage              VARCHAR(5000),
  secondaryInternalPcieStorage       VARCHAR(5000),
  secondaryOpticalDrive              VARCHAR(5000),
  secondaryProcessor                 VARCHAR(5000),
  security                           VARCHAR(5000),
  securityEncryption                 VARCHAR(5000),
  sixthInternalStorage               VARCHAR(5000),
  seventhInternalStorage             VARCHAR(5000),
  softwareBundles                    VARCHAR(5000),
  speakers                           VARCHAR(5000),
  stand                              VARCHAR(5000),
  systemRecoverySolutions            VARCHAR(5000),
  tvTuner                            VARCHAR(5000),
  technical                          VARCHAR(5000),
  technicalAV                        VARCHAR(5000),
  thirdGraphicsCard                  VARCHAR(5000),
  thirdInternalStorage               VARCHAR(5000),
  webcam                             VARCHAR(5000),
  wireless                           VARCHAR(5000),
  PRIMARY KEY (productNumber)
);

CREATE TABLE Laptop (
  productNumber                      VARCHAR(5000) NOT NULL REFERENCES Product,
  version                            INTEGER       NOT NULL,
  hpDataSheet                        VARCHAR(5000),
  parseDate                          TIMESTAMP     NOT NULL,
  productName                        VARCHAR(5000) NOT NULL,
  productUrl                         VARCHAR(5000) NOT NULL,
  rating                             INTEGER,
  battery                            VARCHAR(5000),
  batteryLife                        VARCHAR(5000),
  color                              VARCHAR(5000),
  dimensionDepthInInches             NUMERIC(10, 2),
  dimensionHeightInInches            NUMERIC(10, 2),
  dimensionWidthInInches             NUMERIC(10, 2),
  dimensions                         VARCHAR(5000),
  display                            VARCHAR(5000),
  energyEfficiency                   VARCHAR(5000),
  expansionSlots                     VARCHAR(5000),
  graphics                           VARCHAR(5000),
  hardDrive                          VARCHAR(5000),
  keyboard                           VARCHAR(5000),
  maxBatteryLifeInHours              NUMERIC(10, 2),
  memory                             VARCHAR(5000),
  modelNumber                        VARCHAR(5000),
  networking                         VARCHAR(5000),
  operatingSystem                    VARCHAR(5000),
  pointingDevices                     VARCHAR(5000),
  ports                              VARCHAR(5000),
  powerSupply                        VARCHAR(5000),
  processor                          VARCHAR(5000),
  softwareIncluded                   VARCHAR(5000),
  warranty                           VARCHAR(5000),
  webcam                             VARCHAR(5000),
  weight                             VARCHAR(5000),
  weightInPounds                     NUMERIC(10, 2),
  acAdapter                          VARCHAR(5000),
  accessories                        VARCHAR(5000),
  additionalBay                      VARCHAR(5000),
  audio                              VARCHAR(5000),
  bluetooth                          VARCHAR(5000),
  broadbandServiceProvider           VARCHAR(5000),
  chipset                            VARCHAR(5000),
  externalIOPorts                    VARCHAR(5000),
  fingerPrintReader                  VARCHAR(5000),
  flashCache                         VARCHAR(5000),
  hpMobileBroadband                  VARCHAR(5000),
  labelEnergyStar                    VARCHAR(5000),
  laplinkPcmoverSoftware             VARCHAR(5000),
  securitySoftware                   VARCHAR(5000),
  memorySlots                        VARCHAR(5000),
  miniCard                           VARCHAR(5000),
  miniCardSsd                        VARCHAR(5000),
  miscWarrantyDocumentation          VARCHAR(5000),
  modem                              VARCHAR(5000),
  nearFieldCommunication             VARCHAR(5000),
  osRecoveryCd                       VARCHAR(5000),
  officeSoftware                     VARCHAR(5000),
  opticalDrive                       VARCHAR(5000),
  outOfBandManagement                VARCHAR(5000),
  personalization                    VARCHAR(5000),
  powerCord                          VARCHAR(5000),
  processorFamily                    VARCHAR(5000),
  processorTechnology                VARCHAR(5000),
  recoveryMediaDriver                VARCHAR(5000),
  securityManagement                 VARCHAR(5000),
  sensors                            VARCHAR(5000),
  theftProtection                    VARCHAR(5000),
  warrantyBattery                    VARCHAR(5000),
  boxContents                        VARCHAR(5000),
  wirelessLan                        VARCHAR(5000),
  PRIMARY KEY (productNumber)
);

CREATE TABLE Printer (
  productNumber                        VARCHAR(5000) NOT NULL REFERENCES Product,
  version                              INTEGER       NOT NULL,
  hpDataSheet                          VARCHAR(5000),
  parseDate                            TIMESTAMP     NOT NULL,
  productName                          VARCHAR(5000) NOT NULL,
  productUrl                           VARCHAR(5000) NOT NULL,
  type                                 VARCHAR(5000) NOT NULL, 
  rating                               INTEGER,
  automaticPaperSensor                 VARCHAR(5000),
  borderlessPrinting                   VARCHAR(5000),
  cableIncluded                        VARCHAR(5000),
  compatibleInkTypes                   VARCHAR(5000),
  compatibleOperatingSystems           VARCHAR(5000),
  connectivityOptional                 VARCHAR(5000),
  connectivityStd                      VARCHAR(5000),
  dimensionDepthInInches               NUMERIC(10, 2),
  dimensionDepthInInchesMax            NUMERIC(10, 2),
  dimensionHeightInInches              NUMERIC(10, 2),
  dimensionHeightInInchesMax           NUMERIC(10, 2),
  dimensionWidthInInches               NUMERIC(10, 2),
  dimensionWidthInInchesMax            NUMERIC(10, 2),
  dimensions                           VARCHAR(5000),
  dimensionsMax                        VARCHAR(5000),
  display                              VARCHAR(5000),
  duplexPrinting                       VARCHAR(5000),
  energyEfficiency                     VARCHAR(5000),
  envelopeInputCapacity                VARCHAR(5000),
  fcc                                  VARCHAR(5000),
  functions                            VARCHAR(5000),
  hardDisk                             VARCHAR(5000),
  mediaSizesCustom                     VARCHAR(5000),
  mediaSizesSupported                  VARCHAR(5000),
  mediaTypes                           VARCHAR(5000),
  mediaWeightsByPaperPath              VARCHAR(5000),
  memoryMax                            VARCHAR(5000),
  memoryStd                            VARCHAR(5000),
  minimumSystemRequirements            VARCHAR(5000),
  mobilePrintingCapability             VARCHAR(5000),
  networkReady                         VARCHAR(5000),
  numberPrintCartridges                VARCHAR(5000),
  operatingHumidityRange               VARCHAR(5000),
  operatingTemperatureRange            VARCHAR(5000),
  packageWeight                        VARCHAR(5000),
  packageWeightInPounds                NUMERIC(10, 2),
  paperHandlingInputStd                VARCHAR(5000),
  paperHandlingOutputStd               VARCHAR(5000),
  paperTraysMax                        INTEGER,
  paperTraysStd                        INTEGER,
  postWarranty                         VARCHAR(5000),
  power                                VARCHAR(5000),
  powerConsumption                     VARCHAR(5000),
  printerManagement                    VARCHAR(5000),
  printLanguages                       VARCHAR(5000),
  printSpeedBlackDraft                 VARCHAR(5000),
  printSpeedBlackISO                   VARCHAR(5000),
  printSpeedColorDraft                 VARCHAR(5000),
  printSpeedColorISO                   VARCHAR(5000),
  printTechnology                      VARCHAR(5000),
  printerPageYield                     VARCHAR(5000),
  processorSpeed                       VARCHAR(5000),
  recommMonthlyPageVolume              VARCHAR(5000),
  recommMonthlyPageVolumeMax           INTEGER,
  recommMonthlyPageVolumeMin           INTEGER,
  recommendedMediaWeight               VARCHAR(5000),
  replacementCartridges                VARCHAR(5000),
  resolutionBlack                      VARCHAR(5000),
  resolutionColor                      VARCHAR(5000),
  securityManagement                   VARCHAR(5000),
  softwareIncluded                     VARCHAR(5000),
  supportedMediaWeight                 VARCHAR(5000),
  supportedNetworkProtocols            VARCHAR(5000),
  warranty                             VARCHAR(5000),
  weight                               VARCHAR(5000),
  weightInPounds                       NUMERIC(10, 2),
  whatsInTheBox                        VARCHAR(5000),   
  adfCapacity                          VARCHAR(5000),                  
  autoDocumentFeeder                   VARCHAR(5000),
  batteryRechargeTime                  VARCHAR(5000),          
  bitDepth                             VARCHAR(5000),                     
  broadcastLocations                   VARCHAR(5000),           
  browserSupported                     VARCHAR(5000),             
  colorStability                       VARCHAR(5000),               
  connectivity                         VARCHAR(5000),                 
  controlPanel                         VARCHAR(5000),                 
  maximumCopies                        VARCHAR(5000),                
  copyReduceEnlargeSettings            VARCHAR(5000),    
  copyResolutionBlackText              VARCHAR(5000),      
  copyResolutionColourTextGraphics     VARCHAR(5000),
  copySpeedBlackDraft                  VARCHAR(5000),          
  copySpeedBlackNormal                 VARCHAR(5000),         
  copySpeedColorDraft                  VARCHAR(5000),          
  copySpeedColorNormal                 VARCHAR(5000),         
  digitalSendFileFormats               VARCHAR(5000),       
  digitalSendingFeatures               VARCHAR(5000),       
  duplexAdfScanning                    VARCHAR(5000),            
  energyStar                           VARCHAR(5000),                   
  embeddedWebServer                    VARCHAR(5000),            
  faxMemory                            VARCHAR(5000),                    
  faxResolution                        VARCHAR(5000),                
  faxTransmissionSpeed                 VARCHAR(5000),         
  faxing                               VARCHAR(5000),                       
  finishedOutputHandling               VARCHAR(5000),       
  firstPageOutBlack                    VARCHAR(5000),            
  firstPageOutColor                    VARCHAR(5000),            
  guaranteedMinimumLineWidth           VARCHAR(5000),   
  inputType                            VARCHAR(5000),                    
  lineAccuracy                         VARCHAR(5000),                 
  maximumDocumentScanSize              VARCHAR(5000),      
  maximumOpticalDensityBlack           VARCHAR(5000),   
  mptUsColorImageBestModeGlossy        VARCHAR(5000),
  mptUsColorImageDraftModeCoated       VARCHAR(5000),
  mptUsColorImageNormalModeCoated      VARCHAR(5000),
  mptUsColorImageNormalModeGlossy      VARCHAR(5000),
  mptBWLineDrawingDraftModePlain       VARCHAR(5000),
  mptColorLineDrawingDraftModePlain    VARCHAR(5000),
  mptLineDrawingEconomodePlain         VARCHAR(5000), 
  mediaThickness                       VARCHAR(5000),               
  memoryCardCompatibility              VARCHAR(5000),  
  monthlyDutyCycle                     VARCHAR(5000),      
  nonPrintableArea                     VARCHAR(5000),             
  ports                                VARCHAR(5000),                        
  printRepeatability                   VARCHAR(5000),           
  printSpeedMaximum                    VARCHAR(5000),            
  resolution                           VARCHAR(5000),                   
  resolutionTechnology                 VARCHAR(5000),         
  rollExternalDiameter                 VARCHAR(5000),         
  rollMaximumOutput                    VARCHAR(5000),            
  scanFileFormat                       VARCHAR(5000),               
  scanResolutionHardware               VARCHAR(5000),       
  scanResolutionOptical                VARCHAR(5000),        
  scanSizeMaximum                      VARCHAR(5000),              
  scanSizeFlatbedMaximum               VARCHAR(5000),       
  scanSpeedMaximum                     VARCHAR(5000),             
  scannableMediaTypes                  VARCHAR(5000),          
  scannerType                          VARCHAR(5000),                  
  speedDialsMaximumNumber              VARCHAR(5000),      
  usColorLineDrawingsDraftPlain        VARCHAR(5000),
  PRIMARY KEY (productNumber)
);

CREATE TABLE ProductImage (
  url           VARCHAR(5000) NOT NULL,
  version       INTEGER       NOT NULL,
  productNumber VARCHAR(5000) NOT NULL REFERENCES Product,
  PRIMARY KEY (url, productNumber)
);

CREATE TABLE RelatedAccessory (
  accessoryProductNumber  VARCHAR(5000),
  productNumber           VARCHAR(5000) NOT NULL REFERENCES Product,
  PRIMARY KEY (accessoryProductNumber, productNumber)
);

CREATE TABLE Tablet (
  productNumber           VARCHAR(5000) NOT NULL REFERENCES Product,
  version                 INTEGER       NOT NULL,
  hpDataSheet             VARCHAR(5000),
  parseDate               TIMESTAMP     NOT NULL,
  productName             VARCHAR(5000) NOT NULL,
  productUrl              VARCHAR(5000) NOT NULL,
  rating                  INTEGER,
  audio                   VARCHAR(5000),
  battery                 VARCHAR(5000),
  batteryLife             VARCHAR(5000),
  color                   VARCHAR(5000),
  dimensionDepthInInches  NUMERIC(10, 2),
  dimensionHeightInInches NUMERIC(10, 2),
  dimensionWidthInInches  NUMERIC(10, 2),
  dimensions              VARCHAR(5000),
  display                 VARCHAR(5000),
  expansionSlots          VARCHAR(5000),
  integratedCamera        VARCHAR(5000),
  internalStorage         VARCHAR(5000),
  maxBatteryLifeInHours   NUMERIC(10, 2),
  memory                  VARCHAR(5000),
  modelNumber             VARCHAR(5000),
  operatingSystem         VARCHAR(5000),
  ports                   VARCHAR(5000),
  processor               VARCHAR(5000),
  sensors                 VARCHAR(5000),
  softwareIncluded        VARCHAR(5000),
  warranty                VARCHAR(5000),
  weight                  VARCHAR(5000),
  weightInPounds          NUMERIC(10, 2),
  whatsInTheBox           VARCHAR(5000),
  wireless                VARCHAR(5000),
  chipset                 VARCHAR(5000),
  energyEfficiency        VARCHAR(5000),
  graphics                VARCHAR(5000),
  internalDrive           VARCHAR(5000),
  powerSupply             VARCHAR(5000),
  processorFamily         VARCHAR(5000),
  processorTechnology     VARCHAR(5000),
  securityManagement      VARCHAR(5000),
  PRIMARY KEY (productNumber)
);
  
 CREATE TABLE Site (
  id                    INTEGER NOT NULL,
  siteName              VARCHAR(5000) NOT NULL,
  baseURL               VARCHAR(5000) NOT NULL,
  language              VARCHAR(10) NOT NULL,
  currency              VARCHAR(3),
  PRIMARY KEY (id)
);


CREATE TABLE ProductReview (
  siteId                  INTEGER       NOT NULL REFERENCES Site,
  productNumber           VARCHAR(5000) NOT NULL REFERENCES Product,
  id                      INTEGER       NOT NULL,
  reviewDate              DATE          NOT NULL,
  rating                  INTEGER       NOT NULL,
  scale                   INTEGER       NOT NULL,
  title                   VARCHAR(5000),
  comments                VARCHAR(5000),
  username                VARCHAR(5000),
  location                VARCHAR(5000),
  response                VARCHAR(5000),
  responseDate            DATE,
  responseUser            VARCHAR(100),
  reviewHelpfulYesCount   INTEGER,
  reviewHelpfulNoCount    INTEGER,
  PRIMARY KEY (siteId, productNumber, id));
  
 CREATE TABLE ProductPrice (
  siteId                INTEGER NOT NULL REFERENCES Site,
  productNumber         VARCHAR(5000) NOT NULL REFERENCES Product,
  currentPrice          NUMERIC(10, 2) NOT NULL,
  currency              VARCHAR(3),
  strikedPrice          NUMERIC(10, 2),
  previousPrice         NUMERIC(10, 2),
  dateOfPriceChange     DATE,
  PRIMARY KEY (siteId, productNumber)
);


 CREATE TABLE ProductRating (
  siteId                INTEGER NOT NULL REFERENCES Site,
  productNumber         VARCHAR(5000) NOT NULL REFERENCES Product,
  rating                INTEGER,
  scale                 INTEGER,
  previousRating        INTEGER,
  numberOfReviews       INTEGER,
  dateOfRatingChange    DATE,
  PRIMARY KEY (siteId, productNumber)
);


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

CREATE TABLE InkAndToner (
  productNumber                      VARCHAR(5000) NOT NULL REFERENCES Product,
  version                            INTEGER       NOT NULL,
  hpDataSheet                        VARCHAR(5000),
  parseDate                          TIMESTAMP     NOT NULL,
  productName                        VARCHAR(5000) NOT NULL,
  productUrl                         VARCHAR(5000) NOT NULL,
  colorsOfPrintCartridges            VARCHAR(5000),
  pageYieldBlackAndWhite             VARCHAR(5000),
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
