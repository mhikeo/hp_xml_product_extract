/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
@IdClass(RelatedAccessoryPK.class)
public class RelatedAccessory {

    @Id
    private String url;

    @Id
    private String productNumber;

    @Version
    private Long version;


    private String name;

    @ManyToOne
    @JoinColumn(name="productNumber", insertable = false, updatable = false)
    private Product product;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return new HashCodeBuilder().append(url).append(productNumber).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof RelatedAccessory))
            return false;

        RelatedAccessory that = (RelatedAccessory) obj;

        return new EqualsBuilder().append(url, that.url).append(productNumber, that.productNumber).isEquals();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
