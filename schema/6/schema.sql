DROP TABLE IF EXISTS Product CASCADE;

DROP TABLE IF EXISTS ProductSpecification CASCADE;

DROP TABLE IF EXISTS ProductImage CASCADE;

DROP TABLE IF EXISTS RelatedAccessory CASCADE;

DROP TABLE IF EXISTS ProductReview CASCADE;

DROP TABLE IF EXISTS Site CASCADE;

DROP TABLE IF EXISTS ProductPrice CASCADE;

DROP TABLE IF EXISTS ProductRating CASCADE;

DROP INDEX IF EXISTS ProductFullTextSearch;

CREATE TABLE Product (
  productNumber         VARCHAR(5000) NOT NULL,
  category VARCHAR(100),
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
  hpDataSheet           VARCHAR(5000),
  parseDate             TIMESTAMP NOT NULL,
  fullText              LONG VARCHAR(1000000),
  PRIMARY KEY (productNumber)
);

CREATE TABLE ProductSpecification (
  productNumber VARCHAR(5000) NOT NULL REFERENCES Product,
  name VARCHAR(200) NOT NULL,
  value VARCHAR(5000),
  PRIMARY KEY (productNumber, name)
);

CREATE TABLE ProductImage (
  url               VARCHAR(5000) NOT NULL,
  version           INTEGER       NOT NULL,
  productNumber     VARCHAR(5000) NOT NULL REFERENCES Product,
  fileName          VARCHAR(100),
  downloadTimestamp TIMESTAMP,
  PRIMARY KEY (url, productNumber)
);

CREATE TABLE RelatedAccessory (
  accessoryProductNumber  VARCHAR(5000) NOT NULL REFERENCES Product,
  productNumber           VARCHAR(5000) NOT NULL REFERENCES Product,
  PRIMARY KEY (accessoryProductNumber, productNumber)
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


CREATE TEXT INDEX ProductFullTextSearch ON Product (productNumber, fullText);
