/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * PK for product image
 *
 * changes in 1.0.1: change the primary key from productNumber to productId.
 * @author TCDEVELOPER
 * @version 1.0.1
 */
public class ProductImagePK implements Serializable {

    private String url;

  /**
   * Represents the product id.
   */
  private String productId;

    public ProductImagePK() {
    }

    public ProductImagePK(String productId, String url) {
        this.productId = productId;
        this.url = url;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(productId)
        		.append(url).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductImagePK))
            return false;

        ProductImagePK that = (ProductImagePK) obj;

        return new EqualsBuilder()
        		.append(productId, that.productId)
        		.append(url, that.url).isEquals();
    }

  /**
   * Gets the product id.
   * @return the product id.
   */
  public String getProductId() {
        return productId;
    }

  /**
   * Sets the product id.
   * @param productId the product id.
   */
  public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
