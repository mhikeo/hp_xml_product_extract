/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * Model for related accessories.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
@IdClass(RelatedAccessoryPK.class)
public class RelatedAccessory {

    @Id
    private String productNumber;

    @Id
    private String accessoryProductNumber;

    @Version
    private Long version;

    // When persisting to database, the URL won't be saved, but replaced by the proper accessory product
    // number. This, this field is marked transient
    @Transient
    private String url;

    @ManyToOne
    @JoinColumn(name="productNumber", insertable = false, updatable = false)
    private Product product;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessoryProductNumber() {
        return accessoryProductNumber;
    }

    public void setAccessoryProductNumber(String accessoryProductNumber) {
        this.accessoryProductNumber = accessoryProductNumber;
    }


    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
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
                .append(accessoryProductNumber)
                .append(productNumber)
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
                .append(accessoryProductNumber, that.accessoryProductNumber)
                .append(productNumber, that.productNumber)
                .append(url, that.url)
                .isEquals();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
