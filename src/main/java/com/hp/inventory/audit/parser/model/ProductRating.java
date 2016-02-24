/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.Expose;
import com.hp.inventory.audit.parser.model.annotation.TrackChanges;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Product overall rating for a given site.
 *
 * @author TCDEVELOPER
 * @version 1.0.4
 */
@Entity
@IdClass(ProductRatingPK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProductRating {

    @Id
    private Integer siteId;

    @Id
    private String productNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="productNumber", insertable = false, updatable = false)
    private Product product;

    @TrackChanges(key="rating", target= TrackChanges.TrackingTarget.CURRENT)
    @Expose
    private Integer rating;

    @TrackChanges(key="rating", target= TrackChanges.TrackingTarget.PREVIOUS)
    @Expose
    private Integer previousRating;

    @TrackChanges(key="rating", target= TrackChanges.TrackingTarget.DATE)
    @Expose
    private Date dateOfRatingChange;

    @Expose
    private Integer numberOfReviews;

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(siteId)
                .append(productNumber)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ProductRating))
            return false;

        ProductRating that = (ProductRating) obj;

        return new EqualsBuilder()
                .append(siteId, that.siteId)
                .append(productNumber, that.productNumber)
                .isEquals();
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getPreviousRating() {
        return previousRating;
    }

    public void setPreviousRating(Integer previousRating) {
        this.previousRating = previousRating;
    }

    public Date getDateOfRatingChange() {
        return dateOfRatingChange;
    }

    public void setDateOfRatingChange(Date dateOfRatingChange) {
        this.dateOfRatingChange = dateOfRatingChange;
    }

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(Integer numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
