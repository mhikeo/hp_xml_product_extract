/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * PK class for product rating.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class ProductRatingPK implements Serializable {
    private Integer siteId;

    private String productNumber;

    public ProductRatingPK() {
    }

    public ProductRatingPK(Integer siteId, String productNumber) {
        this.siteId = siteId;
        this.productNumber = productNumber;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(siteId)
                .append(productNumber).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductReviewPK))
            return false;

        ProductRatingPK that = (ProductRatingPK) obj;

        return new EqualsBuilder()
                .append(siteId, that.siteId)
                .append(productNumber, that.productNumber).isEquals();
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
