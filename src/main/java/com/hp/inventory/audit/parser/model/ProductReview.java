/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

import javax.persistence.*;

/**
 * Product review model.
 *
 * @author TCDEVELOPER
 * @version 1.0.4
 */
@Entity
@IdClass(ProductReviewPK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProductReview {

	@Id
	private Integer siteId;

	@Id
	private String productNumber;

	@Id
	private Integer id;

	private Date reviewDate;

	private Integer rating;
	private Integer scale;

	private String title;
	private String comments;
	private String username;
	private String location;

	private String response;
	private Date responseDate;
	private String responseUser;
	private Integer reviewHelpfulYesCount;
	private Integer reviewHelpfulNoCount;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="productNumber", insertable = false, updatable = false)
	private Product product;

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(siteId)
				.append(productNumber)
				.append(id).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof ProductReview))
			return false;

		ProductReview that = (ProductReview) obj;

		return new EqualsBuilder()
				.append(siteId, that.siteId)
				.append(productNumber, that.productNumber)
				.append(id, that.id).isEquals();
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
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getResponseUser() {
		return responseUser;
	}

	public void setResponseUser(String responseUser) {
		this.responseUser = responseUser;
	}

	public Integer getReviewHelpfulYesCount() {
		return reviewHelpfulYesCount;
	}

	public void setReviewHelpfulYesCount(Integer reviewHelpfulYesCount) {
		this.reviewHelpfulYesCount = reviewHelpfulYesCount;
	}

	public Integer getReviewHelpfulNoCount() {
		return reviewHelpfulNoCount;
	}

	public void setReviewHelpfulNoCount(Integer reviewHelpfulNoCount) {
		this.reviewHelpfulNoCount = reviewHelpfulNoCount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
