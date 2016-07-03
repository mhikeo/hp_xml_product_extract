/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * PK for product specification
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class ProductSpecificationPK implements Serializable {

    private String name;

    private String productNumber;

    public ProductSpecificationPK() {
    }

    public ProductSpecificationPK(String productNumber, String name) {
        this.productNumber = productNumber;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(productNumber)
        		.append(name).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductSpecificationPK))
            return false;

        ProductSpecificationPK that = (ProductSpecificationPK) obj;

        return new EqualsBuilder()
        		.append(productNumber, that.productNumber)
        		.append(name, that.name).isEquals();
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
