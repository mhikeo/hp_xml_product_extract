/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import javax.persistence.Entity;

/**
 * Model for Ink and Toner products.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
public class InkAndToner extends AbstractProduct {

    public String colorsOfPrintCartridges;
    public String pageYield;
    public String pageYieldFootnote;
    public String inkDrop;
    public String compatibleInkTypes;
    public String operatingTemperatureRange;
    public String storageTemperatureRange;
    public String operatingHumidityRange;
    public String storageHumidity;
    public String packageDimensions;
    public String packageWeight;
    public String warranty;
    public String whatsInTheBox;


    public String getColorsOfPrintCartridges() {
        return colorsOfPrintCartridges;
    }

    public void setColorsOfPrintCartridges(String colorsOfPrintCartridges) {
        this.colorsOfPrintCartridges = colorsOfPrintCartridges;
    }

    public String getPageYield() {
        return pageYield;
    }

    public void setPageYield(String pageYieldBlackAndWhite) {
        this.pageYield = pageYieldBlackAndWhite;
    }

    public String getPageYieldFootnote() {
        return pageYieldFootnote;
    }

    public void setPageYieldFootnote(String pageYieldFootnote) {
        this.pageYieldFootnote = pageYieldFootnote;
    }

    public String getInkDrop() {
        return inkDrop;
    }

    public void setInkDrop(String inkDrop) {
        this.inkDrop = inkDrop;
    }

    public String getCompatibleInkTypes() {
        return compatibleInkTypes;
    }

    public void setCompatibleInkTypes(String compatibleInkTypes) {
        this.compatibleInkTypes = compatibleInkTypes;
    }

    public String getOperatingTemperatureRange() {
        return operatingTemperatureRange;
    }

    public void setOperatingTemperatureRange(String operatingTemperatureRange) {
        this.operatingTemperatureRange = operatingTemperatureRange;
    }

    public String getStorageTemperatureRange() {
        return storageTemperatureRange;
    }

    public void setStorageTemperatureRange(String storageTemperatureRange) {
        this.storageTemperatureRange = storageTemperatureRange;
    }

    public String getOperatingHumidityRange() {
        return operatingHumidityRange;
    }

    public void setOperatingHumidityRange(String operatingHumidityRange) {
        this.operatingHumidityRange = operatingHumidityRange;
    }

    public String getStorageHumidity() {
        return storageHumidity;
    }

    public void setStorageHumidity(String storageHumidity) {
        this.storageHumidity = storageHumidity;
    }

    public String getPackageDimensions() {
        return packageDimensions;
    }

    public void setPackageDimensions(String packageDimensions) {
        this.packageDimensions = packageDimensions;
    }

    public String getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(String packageWeight) {
        this.packageWeight = packageWeight;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getWhatsInTheBox() {
        return whatsInTheBox;
    }

    public void setWhatsInTheBox(String whatsInTheBox) {
        this.whatsInTheBox = whatsInTheBox;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void initNewEntity() {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void upgradeEntityFrom(AbstractProduct from) throws Exception {
        AbstractProduct.updateEntity(from, this);
    }
}
