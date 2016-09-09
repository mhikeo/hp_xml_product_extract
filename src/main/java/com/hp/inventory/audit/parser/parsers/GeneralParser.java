/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers;

import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.parsers.rules.TypeSpec;
import org.apache.commons.lang3.text.WordUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generic document parser. The extract method return the product definition itself.
 * <p>
 * changes:
 * - 1.0.5: support UPC product types and remove concrete parsers.
 *
 * @author TCDEVELOPER
 * @version 1.0.5
 */
public class GeneralParser extends DocumentParser {

    Pattern productTypePatternFINAL = Pattern.compile("catGroupTraversalByName FINAL: (.*?)~.*");
    Pattern productTypePatternPR = Pattern.compile("<!-- catGroupTraversalByName not empty PR: (.*?) -->");

    private boolean skipExtractProductType = false;

    private Pattern highEndPrinterPattern = null;

    public GeneralParser() {
        super();
        String pattern = getLocaleString("DesignJet")
                + "|" + getLocaleString("Latex") + "|" + getLocaleString("PageWide") + "|" + getLocaleString("Scitex");
        highEndPrinterPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Extract the product data directly into the product definition itself.
     *
     * @throws Exception
     */
    @Override
    protected AbstractProduct extract() throws Exception {

        setParsingErrorsReceiver(definition);

        extractCommonProps(definition);

        boolean isCategory = false;
        if (this.getProductType() != null) {
            isCategory = true;
            definition.setProductType(this.getProductType());
        }

        // extract other product types
        if (!skipExtractProductType && this.getProductType() == null)
            extractProductType();


        // handle printer special type
        if ("Printer".equals(definition.getProductType())) {
            String technology = prop(getLocaleString("Print Technology"), listDelimiter);
            if (highEndPrinterPattern.matcher(definition.getProductName()).find()) {
                definition.setProductType("HighEnd Printer");
            } else if (technology != null
                    && technology.contains("Laser")) {
                definition.setProductType("Laser Printer");
            } else {
                definition.setProductType("Inkjet Printer");
            }
        }

        if (!isSkipCheckProps() && isCategory) {
            checkParsedProps();
        }
        return definition;
    }

    /**
     * Try to extract a more meaningful product type than "Other".
     * <p>
     * This method tries to use category information embedded in pages. Since these are
     * not perfect, we provide a number of rules for fine-tuning the category assignment in
     * the TypeSpec class.
     *
     * @see TypeSpec
     */
    private void extractProductType() {
        String head = this.doc.head().html();
        Matcher m = productTypePatternFINAL.matcher(head);
        String ptype = null;
        if (m.find()) {
            String cat = m.group(1).toLowerCase();
            if (!shouldExcludeCategory(cat))
                ptype = m.group(1);
        }

        if (ptype == null) {
            // Some products have no suitable FINAL category. We'll try to find the "PR" entries with the most
            // deep entry as the category.
            m = productTypePatternPR.matcher(head);
            Set<String> candidates = new HashSet<>();
            while (m.find()) {
                String c = m.group(1).toLowerCase();
                if (!shouldExcludeCategory(c))
                    candidates.add(c);
            }

            int max = 0;
            for (String candidate : candidates) {
                String[] cats = candidate.split("~");
                if (cats.length > max) {
                    max = cats.length;
                    ptype = cats[0];
                }
            }
        }

        if (ptype == null) {
            // Try product name matching
            String name = definition.getProductName();
            for (Map.Entry<Pattern, String> entry : TypeSpec.productNameMatch.entrySet()) {
                if (entry.getKey().matcher(name).find()) {
                    ptype = entry.getValue();
                    break;
                }
            }
        }

        if (ptype != null) {
            ptype = ptype
                    .replaceAll("<br>", " ")
                    .replaceAll("hp", "")
                    .trim();

            // Check if we should typeReplace the type
            for (Map.Entry<Pattern, String> entry : TypeSpec.typeReplace.entrySet()) {
                if (entry.getKey().matcher(ptype).find()) {
                    ptype = entry.getValue();
                    break;
                }
            }

            ptype = WordUtils.capitalizeFully(ptype);
            this.definition.setProductType(ptype);

        } else {
            // We did our best. :(
            this.definition.setProductType("Other");
        }
    }

    /**
     * If a category should be not be used.
     */
    private boolean shouldExcludeCategory(String cat) {

        for (Pattern pattern : TypeSpec.categoryExclude) {
            if (pattern.matcher(cat).find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets if this parser should skip product type extraction.
     */
    public boolean isSkipExtractProductType() {
        return skipExtractProductType;
    }

    /**
     * Sets if this parser should skip product type extraction.
     */
    public void setSkipExtractProductType(boolean skipExtractProductType) {
        this.skipExtractProductType = skipExtractProductType;
    }
}
