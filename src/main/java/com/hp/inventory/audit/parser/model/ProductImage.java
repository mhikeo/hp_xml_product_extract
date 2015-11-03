package com.hp.inventory.audit.parser.model;

import javax.persistence.*;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
@IdClass(ProductImagePK.class)
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
        return getUrl().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductImage))
            return false;

        return getUrl().equals(((ProductImage) obj).getUrl());
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
