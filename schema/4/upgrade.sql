ALTER TABLE RelatedAccessory ADD FOREIGN KEY (accessoryProductNumber) REFERENCES Product (productNumber);

CREATE TEXT INDEX ProductFullTextSearch ON Product (productNumber, fullText);