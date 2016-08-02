/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.Expose;
import com.hp.inventory.audit.parser.model.annotation.TrackChanges;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Product price information for a given site.
 *
 * @author TCDEVELOPER
 * @version 1.0.4
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProductPrice {

    @Column(nullable = false)
    private Integer siteId;

    @Id
    private String productId;

    @Column(nullable = false)
    private String productNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="productId", insertable = false, updatable = false)
    private Product product;

    @TrackChanges(key="price", target= TrackChanges.TrackingTarget.CURRENT)
    @Expose
    private BigDecimal currentPrice;

    @TrackChanges(key="price", target= TrackChanges.TrackingTarget.PREVIOUS)
    @Expose
    private BigDecimal previousPrice;

    @TrackChanges(key="price", target= TrackChanges.TrackingTarget.DATE)
    @Expose
    private Date dateOfPriceChange;

    @Expose
    private String currency;

    @Expose
    private BigDecimal strikedPrice;

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(productId)
                .append(siteId)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductPrice))
            return false;

        ProductPrice that = (ProductPrice) obj;

        return new EqualsBuilder()
                .append(productId, that.productId)
                .append(siteId, that.siteId)
                .isEquals();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(BigDecimal previousPrice) {
        this.previousPrice = previousPrice;
    }

    public Date getDateOfPriceChange() {
        return dateOfPriceChange;
    }

    public void setDateOfPriceChange(Date dateOfPriceChange) {
        this.dateOfPriceChange = dateOfPriceChange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getStrikedPrice() {
        return strikedPrice;
    }

    public void setStrikedPrice(BigDecimal strikedPrice) {
        this.strikedPrice = strikedPrice;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
}
