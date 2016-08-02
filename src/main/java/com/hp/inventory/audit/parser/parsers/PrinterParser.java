/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.ProductSpecification;
import com.hp.inventory.audit.parser.utils.LangTranslator;
import org.apache.commons.lang3.StringUtils;

/**
 * Parser for printer products.
 *
 * changes:
 *  - 1.0.4: refactor the columns to specifications.
 *  - 1.0.5: use a general way to extract the specs.
 * @author TCDEVELOPER
 * @version 1.0.5
 */
public class PrinterParser extends DocumentParser {

	private Pattern highEndPrinterPattern = null;

	public PrinterParser() {
		super();
		String pattern = getLocaleString("DesignJet")
						+ "|" + getLocaleString("Latex") + "|" + getLocaleString("PageWide") + "|" + getLocaleString("Scitex");
		highEndPrinterPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
	}

	@Override
	protected AbstractProduct extract() throws Exception {
		Product p = this.definition;
		p.setCategory("Printer");

		setParsingErrorsReceiver(p);

		extractCommonProps(p);

		p.setHpDataSheet(propLink(getLocaleString("HP Data Sheet")));
		String technology = prop(getLocaleString("Print Technology"), listDelimiter);

		if (highEndPrinterPattern.matcher(p.getProductName()).find()) {
			p.setProductType("HighEnd Printer");
		} else if (technology != null
				&& technology.contains("Laser")) {
			p.setProductType("Laser Printer");
		} else {
			p.setProductType("Inkjet Printer");
		}

		checkParsedProps();

		return p;
	}
}
