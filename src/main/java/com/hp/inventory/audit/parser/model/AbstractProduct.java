/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

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

	@Id
    private String productNumber;

    @Version
    @GeneratedValue
    private Long version;

    @OneToOne
    @JoinColumn(name="productNumber")
    @Transient
    private Product product;

    @Column(nullable = false)
    private Date parseDate;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productUrl;

	private String hpDataSheet;
	


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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
