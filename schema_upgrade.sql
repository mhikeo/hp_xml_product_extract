DROP TABLE ProductReview CASCADE;
DROP TABLE Site CASCADE;

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
  Id                      INTEGER       NOT NULL,
  reviewDate              DATE          NOT NULL,
  rating                  INTEGER       NOT NULL,
  scale                   INTEGER       NOT NULL,
  title                   VARCHAR(5000),
  comments                VARCHAR(5000),
  username                VARCHAR(5000),
  location                VARCHAR(5000),
  hpResponse              VARCHAR(5000),
  hpResponseDate          DATE,
  hpResponseUser          VARCHAR(100),
  reviewHelpfulYesCount   INTEGER,
  reviewHelpfulNoCount    INTEGER,
  PRIMARY KEY (productNumber, Id));
  