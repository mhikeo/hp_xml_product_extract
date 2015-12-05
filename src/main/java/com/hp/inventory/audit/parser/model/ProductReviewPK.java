/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class ProductReviewPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2801064352570669011L;

	private Integer siteId;
	
    private String productNumber;

    private Integer Id;

    public ProductReviewPK() {
    }

    public ProductReviewPK(Integer siteId, String productNumber, Integer Id) {
        this.siteId = siteId;
        this.productNumber = productNumber;
        this.Id = Id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(siteId)
        		.append(Id).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductReviewPK))
            return false;

        ProductReviewPK that = (ProductReviewPK) obj;

        return new EqualsBuilder()
        		.append(siteId, that.siteId)
        		.append(Id, that.Id).isEquals();
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

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

}
