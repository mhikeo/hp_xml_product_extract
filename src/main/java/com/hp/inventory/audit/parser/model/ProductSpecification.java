/*
 * Copyright (c) 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * Model for product Specification.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
@IdClass(ProductSpecificationPK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProductSpecification {

  /**
   * Represents the product number.
   */
  @Id
    private String productId;

  @Column(nullable = false)
  private String productNumber;

  /**
   * Represents the name of the specification.
   */
  @Id
    private String name;

  /**
   * Represents the value of the specification.
   */
  private String value;

  private int displayOrder;

  /**
   * Represents the corresponding product.
   */
  @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="productId", referencedColumnName = "productId", insertable = false, updatable = false)
    private Product product;

  /**
   * Gets the product number.
   * @return the product number.
   */
  public String getProductId() {
        return productId;
    }

  /**
   * Sets the product number.
   * @param productId the product number.
   */
  public void setProductId(String productId) {
        this.productId = productId;
    }

  /**
   * Gets the product.
   * @return the product.
   */
  public Product getProduct() {
        return product;
    }

  /**
   * Sets the product.
   * @param product the product.
   */
  public void setProduct(Product product) {
        this.product = product;
    }

  /**
   * Hash the entity.
   * @return the hash code.
   */
  @Override
    public int hashCode() {
    	return new HashCodeBuilder()
    			.append(productId)
    			.append(name).toHashCode();
    }

  /**
   * Check if equals
   * @param obj the object.
   * @return the object.
   */
    @Override
    public boolean equals(final Object obj) {
    	if (this == obj)
            return true;

        if (!(obj instanceof ProductSpecification))
            return false;

        ProductSpecification that = (ProductSpecification) obj;

        return new EqualsBuilder()
        		.append(productId, that.productId)
        		.append(name, that.name).isEquals();
    }


  /**
   * Get the name.
   * @return the name.
   */
  public String getName() {
        return name;
    }

  /**
   * Sets the name.
   * @param name the name.
   */
  public void setName(String name) {
        this.name = name;
    }

  /**
   * Gets the value.
   * @return the value.
   */
  public String getValue() {
        return value;
    }

  /**
   * Sets the value.
   * @param value the value.
   */
  public void setValue(String value) {
        this.value = value;
    }

  public int getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(int displayOrder) {
    this.displayOrder = displayOrder;
  }

  public String getProductNumber() {
    return productNumber;
  }

  public void setProductNumber(String productNumber) {
    this.productNumber = productNumber;
  }
}
