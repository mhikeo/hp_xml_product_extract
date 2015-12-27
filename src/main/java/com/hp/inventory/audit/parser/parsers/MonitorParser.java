package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Monitor;

/**
 * Document parser for "PDP" type Monitor pages
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class MonitorParser extends DocumentParser {

    /**
     * @inheritDoc
     */
    @Override
    protected AbstractProduct extract() throws Exception {
        Monitor p = new Monitor();

        setParsingErrorsReceiver(p);

        extractCommonProps(p);

        p.setBrightness(prop("Brightness"));
        p.setNativeResolution(prop("Native resolution"));
        p.setContrastRatio(prop("Contrast ratio"));
        p.setPixelPitch(prop("Pixel pitch"));
        p.setResponseTime(prop("Response time"));
        p.setDisplayTiltAndSwivelRange(prop("Display Tilt & Swivel Range"));
        p.setEnergyEfficiency(prop("Energy efficiency"));
        p.setDimensions(prop("Dimensions (W X D X H)"));
        p.setWeight(prop("Weight"));
        p.setWhatsInTheBox(prop("What's in the box"));

        checkParsedProps();

        return p;
    }
}
