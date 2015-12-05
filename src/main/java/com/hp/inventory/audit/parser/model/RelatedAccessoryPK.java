/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Primary key for related accessory
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class RelatedAccessoryPK implements Serializable {

    private String url;

    private String productNumber;

    public RelatedAccessoryPK() {
    }

    public RelatedAccessoryPK(String productNumber, String url) {
        this.productNumber = productNumber;
        this.url = url;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(url).append(productNumber).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof RelatedAccessoryPK))
            return false;

        RelatedAccessoryPK that = (RelatedAccessoryPK) obj;

        return new EqualsBuilder().append(url, that.url).append(productNumber, that.productNumber).isEquals();
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
