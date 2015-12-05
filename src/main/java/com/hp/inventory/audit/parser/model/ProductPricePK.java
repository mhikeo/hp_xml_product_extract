/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * PK class for product price.
 *
 * @author TCDEVELOPER
 * @version 1.0.4
 */
public class ProductPricePK implements Serializable {

    private Integer siteId;

    private String productNumber;

    public ProductPricePK() {
    }

    public ProductPricePK(Integer siteId, String productNumber) {
        this.siteId = siteId;
        this.productNumber = productNumber;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(productNumber)
                .append(siteId).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductPricePK))
            return false;

        ProductPricePK that = (ProductPricePK) obj;

        return new EqualsBuilder()
                .append(productNumber, that.productNumber)
                .append(siteId, that.siteId).isEquals();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
}
