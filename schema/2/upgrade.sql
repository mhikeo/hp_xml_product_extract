-- Insert required data in table Site
INSERT INTO Site (id, siteName, baseURL, language, currency) VALUES (1, 'HP', 'http://store.hp.com', 'English', 'USD');
INSERT INTO Site (id, siteName, baseURL, language, currency) VALUES (2, 'Best Buy', 'http://bestbuy.com', 'English', 'USD');

-- Create new tables ProductPrice and ProductRating

DROP TABLE IF EXISTS ProductPrice;

DROP TABLE IF EXISTS ProductRating;

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
  rating                INTEGER, -- Allow null values according to thread http://apps.topcoder.com/forums/?module=Thread&threadID=871029
  scale                 INTEGER, -- Allow null values according to thread http://apps.topcoder.com/forums/?module=Thread&threadID=871029
  previousRating        INTEGER,
  numberOfReviews       INTEGER, -- Allow null values according to thread http://apps.topcoder.com/forums/?module=Thread&threadID=871029
  dateOfRatingChange    DATE,
  PRIMARY KEY (siteId, productNumber)
);

-- Insert data into ProductPrice
INSERT INTO ProductPrice (siteId, productNumber, currentPrice, currency, strikedPrice, previousPrice, dateOfPriceChange) SELECT 1, productNumber, currentPrice, currency, strikedPrice, previousPrice, dateOfPriceChange FROM Product;

-- Insert data into ProductRating
INSERT INTO ProductRating (siteId, productNumber, rating, scale, previousRating, numberOfReviews, dateOfRatingChange) SELECT DISTINCT 1, p.productNumber, p.rating, pr.scale, p.previousRating, p.numberOfReviews, p.dateOfRatingChange FROM Product p LEFT JOIN ProductReview pr ON p.productNumber = pr.productNumber;

-- Remove columns in Table Product
ALTER TABLE Product DROP currentPrice;
ALTER TABLE Product DROP currency;
ALTER TABLE Product DROP strikedPrice;
ALTER TABLE Product DROP previousPrice;
ALTER TABLE Product DROP dateOfPriceChange;
ALTER TABLE Product DROP rating;
ALTER TABLE Product DROP previousRating;
ALTER TABLE Product DROP numberOfReviews;
ALTER TABLE Product DROP dateOfRatingChange;

-- Remove columns in Table Desktop
ALTER TABLE Desktop DROP currency;
ALTER TABLE Desktop DROP currentPrice;
ALTER TABLE Desktop DROP strikedPrice;

-- Remove columns in Table Laptop
ALTER TABLE Laptop DROP currency;
ALTER TABLE Laptop DROP currentPrice;
ALTER TABLE Laptop DROP strikedPrice;

-- Remove columns in Table Printer
ALTER TABLE Printer DROP currency;
ALTER TABLE Printer DROP currentPrice;
ALTER TABLE Printer DROP strikedPrice;

-- Remove columns in Table Tablet
ALTER TABLE Tablet DROP currency;
ALTER TABLE Tablet DROP currentPrice;
ALTER TABLE Tablet DROP strikedPrice;

-- Rename columns in Table ProductReview
ALTER TABLE ProductReview RENAME COLUMN Id TO id1;
ALTER TABLE ProductReview RENAME COLUMN id1 TO id;
ALTER TABLE ProductReview RENAME COLUMN hpResponse TO response;
ALTER TABLE ProductReview RENAME COLUMN hpResponseDate TO responseDate;
ALTER TABLE ProductReview RENAME COLUMN hpResponseUser TO responseUser;

ALTER TABLE ProductReview DROP CONSTRAINT C_PRIMARY;
ALTER TABLE ProductReview ADD CONSTRAINT C_PRIMARY PRIMARY KEY (siteId, productNumber, id);