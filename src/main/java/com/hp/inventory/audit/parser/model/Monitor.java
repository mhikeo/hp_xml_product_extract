package com.hp.inventory.audit.parser.model;

import javax.persistence.Entity;

/**
 * Model class for HP Monitors.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
public class Monitor extends AbstractProduct implements IProduct {

    private String nativeResolution;
    private String contrastRatio;
    private String brightness;
    private String pixelPitch;
    private String responseTime;
    private String displayTiltAndSwivelRange;
    private String energyEfficiency;
    private String dimensions;
    private String weight;
    private String whatsInTheBox;

    public String getNativeResolution() {
        return nativeResolution;
    }

    public void setNativeResolution(String nativeResolution) {
        this.nativeResolution = nativeResolution;
    }

    public String getContrastRatio() {
        return contrastRatio;
    }

    public void setContrastRatio(String contrastRatio) {
        this.contrastRatio = contrastRatio;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getPixelPitch() {
        return pixelPitch;
    }

    public void setPixelPitch(String pixelPitch) {
        this.pixelPitch = pixelPitch;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getDisplayTiltAndSwivelRange() {
        return displayTiltAndSwivelRange;
    }

    public void setDisplayTiltAndSwivelRange(String displayTiltAndSwivelRange) {
        this.displayTiltAndSwivelRange = displayTiltAndSwivelRange;
    }

    public String getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(String energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWhatsInTheBox() {
        return whatsInTheBox;
    }

    public void setWhatsInTheBox(String whatsInTheBox) {
        this.whatsInTheBox = whatsInTheBox;
    }

    @Override
    public void initNewEntity() {

    }

    @Override
    public void upgradeEntityFrom(IProduct from) throws Exception {
        IProduct.updateEntity(from, this);
    }
}
