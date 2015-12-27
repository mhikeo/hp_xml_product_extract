package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.InkAndToner;
import com.hp.inventory.audit.parser.model.Monitor;

/**
 * Document parser for "PDP" type Ink and Toner pages
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class InkAndTonerParser extends DocumentParser {

    /**
     * @inheritDoc
     */
    @Override
    protected AbstractProduct extract() throws Exception {
        InkAndToner p = new InkAndToner();

        setParsingErrorsReceiver(p);

        extractCommonProps(p);

        p.setColorsOfPrintCartridges(prop("Color(s) of print cartridges"));
        p.setPageYieldBlackAndWhite(prop("Page yield (black and white)"));
        p.setPageYieldFootnote(prop("Page yield footnote"));
        p.setInkDrop(prop("Ink drop"));
        p.setCompatibleInkTypes(prop("Compatible ink types"));
        p.setOperatingTemperatureRange(prop("Operating temperature range"));
        p.setStorageTemperatureRange(prop("Storage temperature range"));
        p.setOperatingTemperatureRange(prop("Operating humidity range"));
        p.setStorageHumidity(prop("Storage humidity"));
        p.setPackageDimensions(prop("Package dimensions (W x D x H)"));
        p.setPackageWeight(prop("Package weight"));
        p.setWarranty(prop("Warranty"));
        p.setWhatsInTheBox(prop("What's in the box"));

        checkParsedProps();

        return p;
    }
}
