/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Model for product image.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
@IdClass(ProductImagePK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProductImage {

    @Id
    private String url;

    @Version
    private Long version;

    @Id
    private String productNumber;

    @ManyToOne
    @JoinColumn(name="productNumber", referencedColumnName = "productNumber", insertable = false, updatable = false)
    private Product product;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
    	return new HashCodeBuilder()
    			.append(productNumber)
    			.append(url).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
    	if (this == obj)
            return true;

        if (!(obj instanceof ProductImage))
            return false;

        ProductImage that = (ProductImage) obj;

        return new EqualsBuilder()
        		.append(productNumber, that.productNumber)
        		.append(url, that.url).isEquals();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
