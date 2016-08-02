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
 * changes in 1.0.1: change the primary key from productNumber to productId.
 * @author TCDEVELOPER
 * @version 1.0.1
 */
public class ProductSpecificationPK implements Serializable {

    private String name;

  /**
   * Represents the product id.
   */
  private String productId;

    public ProductSpecificationPK() {
    }

    public ProductSpecificationPK(String productId, String name) {
        this.productId = productId;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(productId)
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
        		.append(productId, that.productId)
        		.append(name, that.name).isEquals();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
