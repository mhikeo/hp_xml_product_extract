package com.hp.inventory.audit.parser.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
/*
 * Copyright (c) 2016 Topcoder Inc. All rights reserved.
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <p>
 * This is a simple JavaBean that represents a upc.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread safe.
 * </p>
 * @author TCDEVELOPER
 * @version 1.0
 */
@Entity
public class UPC {
    /**
     * Represents the upc id.
     */
    @Id
    private Integer id;

    /**
     * Represents the upc.
     */
    @Column(name = "upc")
    private String upc;

    /**
     * Represents the item number.
     */
    @Column(name = "itemNumber")
    private String itemNumber;

    /**
     * Represents the pimary product type.
     */
    @Column(name = "primaryProductTypeName")
    private String primaryProductType;

    /**
     * Represents the marketing product type.
     */
    @Column(name = "mktgProductCatName")
    private String marketingProductType;

    /**
     * Represents the item number.
     * @return the item number.
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     * Sets the item number.
     * @param itemNumber the item number.
     */
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    /**
     * Gets the primary product type.
     * @return the primary product type.
     */
    public String getPrimaryProductType() {
        return primaryProductType;
    }

    /**
     * Sets the primary product type.
     * @param primaryProductType the primary product type.
     */
    public void setPrimaryProductType(String primaryProductType) {
        this.primaryProductType = primaryProductType;
    }

    /**
     * Gets the marketing product type.
     * @return the marketing product type.
     */
    public String getMarketingProductType() {
        return marketingProductType;
    }

    /**
     * Sets the marketing product type.
     * @param marketingProductType the marketing product type.
     */
    public void setMarketingProductType(String marketingProductType) {
        this.marketingProductType = marketingProductType;
    }

    /**
     * Gets the UPC.
     * @return the upc.
     */
    public String getUpc() {
        return upc;
    }

    /**
     * Sets the UPC.
     * @param upc the upc.
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }

    /**
     * Gets the id.
     * @return the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id the id.
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
