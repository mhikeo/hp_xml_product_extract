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
 * changes in 1.0.1: change the primary key from productNumber to product id.
 * @author TCDEVELOPER
 * @version 1.0.1
 */
@Entity
@IdClass(ProductImagePK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProductImage {

    @Id
    private String url;

    @Version
    private Long version;

  /**
   * Represents the product id.
   */
  @Id
    private String productId;

    @Column(nullable = false)
    private String productNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="productId", referencedColumnName = "productId", insertable = false, updatable = false)
    private Product product;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

        if (!(obj instanceof ProductImage))
            return false;

        ProductImage that = (ProductImage) obj;

        return new EqualsBuilder()
        		.append(productId, that.productId)
        		.append(url, that.url).isEquals();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
}
