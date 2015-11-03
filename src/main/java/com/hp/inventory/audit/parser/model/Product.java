package com.hp.inventory.audit.parser.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.hp.inventory.audit.parser.model.annotation.SkipNullUpdate;
import com.hp.inventory.audit.parser.model.annotation.TrackChanges;
import com.hp.inventory.audit.parser.model.annotation.TrackChanges.TrackingTarget;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Product entity
 *
 * @author TCDEVELOPER
 * @version 1.0.1
 * 
 */
@Entity
public class Product implements IProduct{

	@Transient
	StringBuilder parsingErrors;

	/** @since 1.0.2
     */
    @Transient
    private String listDelimiter;
	
	@Override
	public StringBuilder getParsingErrors() {
		return parsingErrors;
	}
	
	public String getListDelimiter() {
		return listDelimiter;
	}
	
    private Integer id;

    @Version
    private Long version;

    @Id
    private String productNumber;
    private String productUrl;
    
    private String sourceFile;
    private Date auditTimeStamp;

    /** @since 1.0.1
     */
    private String productType;
    private String productName;
    
    private String currency;
    private BigDecimal strikedPrice;
    
    @SkipNullUpdate
    private Date dateAdded;
    
    
    @TrackChanges(key="price", target=TrackingTarget.CURRENT)
    private BigDecimal currentPrice;
    
    @TrackChanges(key="price", target=TrackingTarget.PREVIOUS)
    private BigDecimal previousPrice;
    
    @TrackChanges(key="price", target=TrackingTarget.DATE)
    private Date dateOfPriceChange;
    
    @TrackChanges(key="rating", target=TrackingTarget.CURRENT)
    private Integer rating;
    
    @TrackChanges(key="rating", target=TrackingTarget.PREVIOUS)
    private Integer previousRating;
    
    @TrackChanges(key="rating", target=TrackingTarget.DATE)
    private Date dateOfRatingChange;

    private Integer numberOfReviews;
    private String parsingError;
    private Date dateOfParsingError;
    
    private Date comingSoonDate;
    
    @SkipNullUpdate
    private Date availableForSaleDate;
    
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
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getStrikedPrice() {
		return strikedPrice;
	}
	public void setStrikedPrice(BigDecimal strikedPrice) {
		this.strikedPrice = strikedPrice;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public BigDecimal getPreviousPrice() {
		return previousPrice;
	}
	public void setPreviousPrice(BigDecimal previousPrice) {
		this.previousPrice = previousPrice;
	}
	public Date getDateOfPriceChange() {
		return dateOfPriceChange;
	}
	public void setDateOfPriceChange(Date dateOfPriceChange) {
		this.dateOfPriceChange = dateOfPriceChange;
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
	public Integer getNumberOfReviews() {
		return numberOfReviews;
	}
	public void setNumberOfReviews(Integer numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}
	public Date getDateOfRatingChange() {
		return dateOfRatingChange;
	}
	public void setDateOfRatingChange(Date dateOfRatingChange) {
		this.dateOfRatingChange = dateOfRatingChange;
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

	@Override
	public void initNewEntity() {
		Date now = new Date();
		this.setDateAdded(now);
		if(this.getComingSoonDate()==null) {
			this.setAvailableForSaleDate(now);
		}
	}

	@Override
	public void upgradeEntityFrom(IProduct from) throws Exception {
		Date now = new Date();
		this.updateFrom(from);
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
}
