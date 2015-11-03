package com.hp.inventory.audit.parser.model;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * !!Description
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@MappedSuperclass
public abstract class AbstractProduct implements IProduct {

	/** @since 1.0.1
	 * Stores all parsing errors
	 */
	@Transient
	private StringBuilder parsingErrors = new StringBuilder();
	
	@Override
    public StringBuilder getParsingErrors() {
		return parsingErrors;
	}

	public void setParsingErrors(StringBuilder parsingErrors) {
		this.parsingErrors = parsingErrors;
	}

	/** @since 1.0.1
	 * Stores coming soon date
	 * Non-transient only exists in Product entity
	 */
	@Transient
	private Date prvComingSoonDate;
	
	@Override
    public Date getComingSoonDate() {
		return prvComingSoonDate;
	}

	public void setComingSoonDate(Date comingSoonDate) {
		this.prvComingSoonDate = comingSoonDate;
	}
	/** @since 1.0.1
	 * Stores number of reviews
	 * Non-transient only exists in Product entity
	 */
	@Transient
	private Integer prvNumberOfReviews;
	
	@Override
    public Integer getNumberOfReviews() {
        return prvNumberOfReviews;
    }

    public void setNumberOfReviews(Integer numberOfReviews) {
        this.prvNumberOfReviews = numberOfReviews;
    }
	
	@Id
    private String productNumber;

    @Version
    @GeneratedValue
    private Long version;

    @OneToOne
    @JoinColumn(name="productNumber")
    @Transient
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval=true)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size=100)
    private Set<ProductImage> images = new HashSet<ProductImage>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval=true)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size=100)
    private Set<RelatedAccessory> topAccessories = new HashSet<RelatedAccessory>();

    @Column(nullable = false)
    private Date parseDate;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productUrl;

    private BigDecimal currentPrice;

    private BigDecimal strikedPrice;

    private String currency;

    private Integer rating;

	private String hpDataSheet;
	
    @Override
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getHpDataSheet() {
        return hpDataSheet;
    }

    public void setHpDataSheet(String hpDataSheet) {
        this.hpDataSheet = hpDataSheet;
    }

    public Date getParseDate() {
        return parseDate;
    }

    public void setParseDate(Date parseDate) {
        this.parseDate = parseDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
	public String getProductName() {
		return this.productName;
	}
    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    @Override
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) {
    	updateSet(this.images, images);
    }

    public void setTopAccessories(Set<RelatedAccessory> topAccessories) {
    	updateSet(this.topAccessories, topAccessories);
    }
    
    public Set<RelatedAccessory> getTopAccessories() {
        return topAccessories;
    }
    
    @Override
    public BigDecimal getStrikedPrice() {
        return strikedPrice;
    }

    public void setStrikedPrice(BigDecimal strikedPrice) {
        this.strikedPrice = strikedPrice;
    }
    
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
