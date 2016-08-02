/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * Model for related accessories.
 *
 * changes in 1.0.1: change the primary/forgein keys from productNumber to productId.
 * @author TCDEVELOPER
 * @version 1.0.1
 */
@Entity
@IdClass(RelatedAccessoryPK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class RelatedAccessory {

    @Id
    private String productId;

    @Id
    private String accessoryProductId;

    @Column(nullable = false)
    private String productNumber;

    @Column(nullable = false)
    private String accessoryProductNumber;

    // When persisting to database, the URL won't be saved, but replaced by the proper accessory product
    // number. This, this field is marked transient
    @Transient
    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="productId", referencedColumnName = "productId", insertable = false, updatable = false)
    private Product product;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessoryProductId() {
        return accessoryProductId;
    }

    public void setAccessoryProductId(String accessoryProductId) {
        this.accessoryProductId = accessoryProductId;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    protected void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(accessoryProductId)
                .append(productId)
                .append(url).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof RelatedAccessory))
            return false;

        RelatedAccessory that = (RelatedAccessory) obj;

        return new EqualsBuilder()
                .append(accessoryProductId, that.accessoryProductId)
                .append(productId, that.productId)
                .append(url, that.url)
                .isEquals();
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getAccessoryProductNumber() {
        return accessoryProductNumber;
    }

    public void setAccessoryProductNumber(String accessoryProductNumber) {
        this.accessoryProductNumber = accessoryProductNumber;
    }
}
