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
 * changes in 1.0.1: change the primary/forgein keys from productNumber to productId.
 * @author TCDEVELOPER
 * @version 1.0.1
 */
public class RelatedAccessoryPK implements Serializable {

    private String accessoryProductId;

    private String productId;

    public RelatedAccessoryPK() {
    }

    public RelatedAccessoryPK(String productId, String url) {
        this.productId = productId;
        this.accessoryProductId = url;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accessoryProductId).append(productId).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof RelatedAccessoryPK))
            return false;

        RelatedAccessoryPK that = (RelatedAccessoryPK) obj;

        return new EqualsBuilder().append(accessoryProductId, that.accessoryProductId).append(productId, that.productId).isEquals();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAccessoryProductId() {
        return accessoryProductId;
    }

    public void setAccessoryProductId(String accessoryProductId) {
        this.accessoryProductId = accessoryProductId;
    }
}
