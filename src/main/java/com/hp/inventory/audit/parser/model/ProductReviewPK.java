/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * PK for product review.
 *
 * changes in 1.0.1: change the primary key from productNumber to productId.
 * @author TCDEVELOPER
 * @version 1.0.1
 */
public class ProductReviewPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2801064352570669011L;

  /**
   * Represents the product id.
    */
  private String productId;

    private Integer id;

    public ProductReviewPK() {
    }

    public ProductReviewPK(Integer siteId, String productId, Integer id) {
        this.productId = productId;
        this.id = id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(productId)
        		.append(id).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductReviewPK))
            return false;

        ProductReviewPK that = (ProductReviewPK) obj;

        return new EqualsBuilder()
        		.append(productId, that.productId)
        		.append(id, that.id).isEquals();
    }


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
