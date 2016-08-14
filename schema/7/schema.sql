DROP TABLE IF EXISTS Product CASCADE;

DROP TABLE IF EXISTS ProductSpecification CASCADE;

DROP TABLE IF EXISTS ProductImage CASCADE;

DROP TABLE IF EXISTS RelatedAccessory CASCADE;

DROP TABLE IF EXISTS ProductReview CASCADE;

DROP TABLE IF EXISTS Site CASCADE;

DROP TABLE IF EXISTS ProductPrice CASCADE;

DROP TABLE IF EXISTS ProductRating CASCADE;

DROP INDEX IF EXISTS ProductFullTextSearch;

CREATE TABLE Site (
  id                    INTEGER NOT NULL,
  siteName              VARCHAR(5000) NOT NULL,
  baseURL               VARCHAR(5000) NOT NULL,
  language              VARCHAR(10) NOT NULL,
  currency              VARCHAR(3),
  PRIMARY KEY (id)
);

CREATE TABLE Product (
  productId             VARCHAR(5000) NOT NULL,
  productNumber         VARCHAR(5000) NOT NULL,
  primaryProduct        BOOLEAN       NOT NULL,
  siteId                INTEGER       NOT NULL REFERENCES Site,
  category              VARCHAR(100),
  version               INTEGER       NOT NULL,
  auditTimeStamp        TIMESTAMP     NOT NULL,
  id                    INTEGER       NOT NULL,
  productName           VARCHAR(5000) NOT NULL,
  productUrl            VARCHAR(5000) NOT NULL,
  sourceFile            VARCHAR(5000) NOT NULL,
  productType           VARCHAR(5000) NOT NULL,
  dateAdded             DATE          NOT NULL, 
  parsingError          VARCHAR(5000),
  dateOfParsingError    DATE,
  comingSoonDate        DATE,
  availableForSaleDate  DATE,
  hpDataSheet           VARCHAR(5000),
  parseDate             TIMESTAMP     NOT NULL,
  fullText              LONG VARCHAR(1000000),
  itemNumber            VARCHAR(100),
  PRIMARY KEY (productId),
  UNIQUE (productNumber, siteId)
);

CREATE TABLE ProductSpecification (
  productId VARCHAR(5000) NOT NULL REFERENCES Product,
  productNumber         VARCHAR(5000) NOT NULL,
  name VARCHAR(200) NOT NULL,
  value VARCHAR(5000),
  displayOrder INT NOT NULL,
  PRIMARY KEY (productId, name)
);

CREATE TABLE ProductImage (
  url               VARCHAR(5000) NOT NULL,
  productId         VARCHAR(5000) NOT NULL REFERENCES Product,
  productNumber         VARCHAR(5000) NOT NULL,
  version           INTEGER       NOT NULL,
  fileName          VARCHAR(100),
  downloadTimestamp TIMESTAMP,
  PRIMARY KEY (url, productId)
);

CREATE TABLE RelatedAccessory (
  accessoryProductId  VARCHAR(5000) NOT NULL REFERENCES Product,
  productId         VARCHAR(5000) NOT NULL REFERENCES Product,
  accessoryProductNumber         VARCHAR(5000) NOT NULL,
  productNumber         VARCHAR(5000) NOT NULL,
  PRIMARY KEY (accessoryProductId, productId)
);


CREATE TABLE ProductReview (
  siteId                  INTEGER       NOT NULL REFERENCES Site,
  productId               VARCHAR(5000) NOT NULL REFERENCES Product,
  productNumber           VARCHAR(5000) NOT NULL,
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
  PRIMARY KEY (productId, id)
);
  
CREATE TABLE ProductPrice (
  siteId                INTEGER NOT NULL REFERENCES Site,
  productId             VARCHAR(5000) NOT NULL REFERENCES Product,
  productNumber         VARCHAR(5000) NOT NULL,
  currentPrice          NUMERIC(10, 2) NOT NULL,
  currency              VARCHAR(3),
  strikedPrice          NUMERIC(10, 2),
  previousPrice         NUMERIC(10, 2),
  dateOfPriceChange     DATE,
  PRIMARY KEY (productId)
);

CREATE TABLE ProductRating (
  siteId                INTEGER NOT NULL REFERENCES Site,
  productId             VARCHAR(5000) NOT NULL REFERENCES Product,
  productNumber         VARCHAR(5000) NOT NULL,
  rating                INTEGER,
  scale                 INTEGER,
  previousRating        INTEGER,
  numberOfReviews       INTEGER,
  dateOfRatingChange    DATE,
  PRIMARY KEY (productId)
);


CREATE TEXT INDEX ProductFullTextSearch ON Product (productId, fullText);


CREATE TABLE UPC (
    itemNumber VARCHAR(1000),
    upc VARCHAR(1000),
    sku VARCHAR(1000),
    skuDescription VARCHAR(1000),
    skuName VARCHAR(1000),
    model VARCHAR(1000),
    modelDescription VARCHAR(1000),
    modelName VARCHAR(1000),
    product VARCHAR(1000),
    productDescription VARCHAR(1000),
    productName VARCHAR(1000),
    family VARCHAR(1000),
    familyDescription VARCHAR(1000),
    familyName VARCHAR(1000),
    line VARCHAR(1000),
    lineDescription VARCHAR(1000),
    lineName VARCHAR(1000),
    type VARCHAR(1000),
    typeDescription VARCHAR(1000),
    typeName VARCHAR(1000),
    division VARCHAR(1000),
    divisionDescription VARCHAR(1000),
    divisionName VARCHAR(1000),
    groupId VARCHAR(1000),
    groupDescription VARCHAR(1000),
    groupName VARCHAR(1000),
    productSeriesName VARCHAR(1000),
    productBigseriesName VARCHAR(1000),
    platformName VARCHAR(1000),
    plcWwProductReleaseDt VARCHAR(1000),
    productModelCcbscDt VARCHAR(1000),
    mktgProductSubcatName VARCHAR(1000),
    productModelName VARCHAR(1000),
    productModelDescription VARCHAR(1000),
    productClassificationName VARCHAR(1000),
    mktgProductCatName VARCHAR(1000),
    primaryProductTypeName VARCHAR(1000)
);