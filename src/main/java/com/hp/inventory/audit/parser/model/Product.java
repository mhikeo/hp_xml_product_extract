/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.gson.annotations.Expose;
import com.hp.inventory.audit.parser.model.annotation.SkipNullUpdate;

import java.util.*;

/**
 * Product entity
 *
 * @author TCDEVELOPER
 * @version 1.0.4
 * 
 */
@Entity
public class Product implements IProduct {

	@Transient
	StringBuilder parsingErrors;

	/** @since 1.0.2
     */
    @Transient
    private String listDelimiter;
	
    /** @since 1.0.3
     */
    @Transient
    private int propertiesThreshold;
	
	@Override
	public StringBuilder getParsingErrors() {
		return parsingErrors;
	}
	
	public String getListDelimiter() {
		return listDelimiter;
	}
	
    @Expose
    private Integer id;

    @Version
    private Long version;

    @Id
    @Expose
    private String productNumber;

    @Expose
    private String productUrl;
    
    @Expose
    private String sourceFile;
    
    @Expose
    private Date auditTimeStamp;

    /** @since 1.0.1
     */
    @Expose
    private String productType;
    @Expose
    private String productName;
    
    @SkipNullUpdate
    @Expose
    private Date dateAdded;
    @Expose
    private String parsingError;
    @Expose
    private Date dateOfParsingError;
    
    @Expose
    private Date comingSoonDate;
    
    @SkipNullUpdate
    @Expose
    private Date availableForSaleDate;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval=true)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size=100)
    private Set<ProductImage> images = new HashSet<ProductImage>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval=true)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size=100)
    private Set<RelatedAccessory> topAccessories = new HashSet<RelatedAccessory>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval=true)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size=100)
    private Set<ProductReview> reviews = new HashSet<ProductReview>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
	@MapKey(name="siteId")
	private Map<Integer, ProductPrice> prices = new HashMap<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    @MapKey(name="siteId")
    private Map<Integer, ProductRating> ratings = new HashMap<>();

    public Map<Integer, ProductRating> getRatings() {
        return ratings;
    }

    public void setRatings(Map<Integer, ProductRating> ratings) throws Exception {
        updateMap(this.ratings, ratings, false, true);
    }


    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) throws Exception {
    	updateSet(this.images, images, false, true);
    }

    public void setTopAccessories(Set<RelatedAccessory> topAccessories) throws Exception {
    	updateSet(this.topAccessories, topAccessories, false, true);
    }
    
    public void setReviews(Set<ProductReview> reviews) throws Exception {
    	updateSet(this.reviews, reviews, true, false);
    }
    
    public Set<RelatedAccessory> getTopAccessories() {
        return topAccessories;
    }
    
    public Date getAuditTimeStamp() {
        return auditTimeStamp;
    }

    public void setAuditTimeStamp(Date dateTime) {
    	this.auditTimeStamp = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
    	this.id = id;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
    	this.productNumber = productNumber;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
    	this.sourceFile = sourceFile;
    }

    public String getProductUrl() {
        return this.productUrl;
    }

    public void setProductUrl(String url) {
    	this.productUrl = url;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getParsingError() {
		return parsingError;
	}
	public void setParsingError(String parsingError) {
		this.parsingError = parsingError;
	}
	public Date getDateOfParsingError() {
		return dateOfParsingError;
	}
	public void setDateOfParsingError(Date dateOfParsingError) {
		this.dateOfParsingError = dateOfParsingError;
	}
	public Date getComingSoonDate() {
		return comingSoonDate;
	}
	public void setComingSoonDate(Date comingSoonDate) {
		this.comingSoonDate = comingSoonDate;
	}
	public Date getAvailableForSaleDate() {
		return availableForSaleDate;
	}
	public void setAvailableForSaleDate(Date availableForSaleDate) {
		this.availableForSaleDate = availableForSaleDate;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}


	public Set<ProductReview> getReviews() {
		return reviews;
	}

	public Map<Integer, ProductPrice> getPrices() {
		return prices;
	}

	public void setPrices(Map<Integer, ProductPrice> prices) throws Exception {
		updateMap(this.prices, prices, false, true);
	}


	@Override
	public void initNewEntity() {
		Date now = new Date();
		this.setDateAdded(now);
		if(this.getComingSoonDate()==null) {
			this.setAvailableForSaleDate(now);
		}
	}

    @Override
	public void upgradeEntityFrom(IProduct fromIFace) throws Exception {
		Date now = new Date();
        Product from = (Product) fromIFace;


        // Iterate over prices to check for changes
        for (ProductPrice thisPrice : prices.values()) {
            ProductPrice fromPrice = from.prices.get(thisPrice.getSiteId());
            if (fromPrice != null) {
                boolean isEquals = new EqualsBuilder()
                        .append(fromPrice.getCurrency(), thisPrice.getCurrency())
                        .append(fromPrice.getCurrentPrice(), thisPrice.getCurrentPrice())
                        .isEquals();
                if (!isEquals)
                    IProduct.updateEntity(fromPrice, thisPrice);
            }
        }

        // Iterate over ratings to check for changes
        for (ProductRating thisRating : ratings.values()) {
            ProductRating fromRating = from.ratings.get(thisRating.getSiteId());
            if (fromRating != null) {
                boolean isEquals = new EqualsBuilder()
                        .append(fromRating.getRating(), thisRating.getRating())
                        .isEquals();
                if (!isEquals)
                    IProduct.updateEntity(fromRating, thisRating);
            }
        }

        // Execute update
        IProduct.updateEntity(from, this);

		if(this.getComingSoonDate()==null) {
			//no 'coming soon' date
			if(this.getAvailableForSaleDate()==null) {
				//if availableForSale is not set, then set it to now
				this.setAvailableForSaleDate(now);
			}
		} else {
			//'coming soon' date is set
			
			if(this.getAvailableForSaleDate()!=null) {
				//reset 'availableForSale' date
				this.setAvailableForSaleDate(null);
			}
		}

	}

	public void setListDelimiter(String listDelimiter) {
		this.listDelimiter = listDelimiter;
	}

	public int getPropertiesThreshold() {
		return propertiesThreshold;
	}

	public void setPropertiesThreshold(int propertiesThreshold) {
		this.propertiesThreshold = propertiesThreshold;
	}
}
