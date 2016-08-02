/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.gson.annotations.Expose;
import com.hp.inventory.audit.parser.model.annotation.SkipNullUpdate;

import java.util.*;

/**
 * Model class representing any Product present in the HP Store.
 *
 * changes: add the category field and specifications
 * changes in 1.0.7: add primaryProduct field.
 * @author TCDEVELOPER
 * @version 1.0.7
 * 
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="productId")
public class Product extends AbstractProduct {

	/** @since 1.0.2
     */
    @Transient
    private String listDelimiter;

    @Expose
    private Integer id;

  /**
   * Indicates whether this is the primary product. (existing in the main site, or other sites if not in the main site).
   */
  @Expose
    private boolean primaryProduct;

  /**
   * Since 1.0.6
   * This is the category of the product, (Printer, Tablet, Desktop,...)
   */
  @Expose
    private String category;

    /** @since 1.0.3
     */
    @Transient
    private int propertiesThreshold;
	
	public String getListDelimiter() {
		return listDelimiter;
	}
	
    @Expose
    private String sourceFile;

    @JsonIgnore
    private String fullText;
    
    @Expose
    private Date auditTimeStamp;

    /** @since 1.0.1
     */
    @Expose
    private String productType;

    @Expose
    private String itemNumber;

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
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size=100)
    private Set<ProductImage> images = new HashSet<ProductImage>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval=true)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size=100)
    private Set<RelatedAccessory> accessories = new HashSet<RelatedAccessory>();

  /**
   * The specifications.
   * @since 1.0.6
   */
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval=true)
  @Fetch(FetchMode.SUBSELECT)
  @BatchSize(size=100)
  private Set<ProductSpecification> specifications = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval=true)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size=100)
    private Set<ProductReview> reviews = new HashSet<ProductReview>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
	@MapKey(name="siteId")
  @Fetch(FetchMode.SUBSELECT)
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

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) throws Exception {
    	updateSet(this.images, images, false, true);
    }

    public void setAccessories(Set<RelatedAccessory> accessories) throws Exception {
    	updateSet(this.accessories, accessories, false, true);
    }
    
    public void setReviews(Set<ProductReview> reviews) throws Exception {
    	updateSet(this.reviews, reviews, true, false);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<RelatedAccessory> getAccessories() {
        return accessories;
    }
    
    public Date getAuditTimeStamp() {
        return auditTimeStamp;
    }

    public void setAuditTimeStamp(Date dateTime) {
    	this.auditTimeStamp = dateTime;
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

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
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


    /**
     * @inheritDoc
     */
	@Override
	public void initNewEntity() {
		Date now = new Date();
		this.setDateAdded(now);
		if(this.getComingSoonDate()==null) {
			this.setAvailableForSaleDate(now);
		}
	}

    /**
     * @inheritDoc
     */
    @Override
	public void upgradeEntityFrom(AbstractProduct fromIFace) throws Exception {
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
                    AbstractProduct.updateEntity(fromPrice, thisPrice);
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
                    AbstractProduct.updateEntity(fromRating, thisRating);
            }
        }

        // Execute update
        AbstractProduct.updateEntity(from, this);

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

  /**
   * Gets the specification.
   * @return the specification.
   */
  public Set<ProductSpecification> getSpecifications() {
    return specifications;
  }

  /**
   * Sets the specifications
   * @param specifications the specifications.
   * @throws Exception if there are any error.
   */
  public void setSpecifications(Set<ProductSpecification> specifications) throws Exception {
    updateSet(this.specifications, specifications, false, true);
  }

  /**
   * Gets the category.
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Sets the category.
   * @param category the category
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Gets the primary product.
   * @return the primary product.
   */
  public boolean isPrimaryProduct() {
    return primaryProduct;
  }

  /**
   * Sets the primary product.
   * @param primaryProduct the primary product.
   */
  public void setPrimaryProduct(boolean primaryProduct) {
    this.primaryProduct = primaryProduct;
  }

  /**
   * Gets the item number.
   * @return the item number
   */
  public String getItemNumber() {
    return itemNumber;
  }
  /**
   * Sets the item number.
   * @param itemNumber item number
   */
  public void setItemNumber(String itemNumber) {
    this.itemNumber = itemNumber;
  }
}
