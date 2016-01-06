/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The site source of data.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Site {

    @Id
    private Integer id;

    private String siteName;

    private String baseURL;

    private String language;

    private String currency;

    /**
     * Gets site Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets site Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets site name
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * Sets site name
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * Gets site base URL
     */
    public String getBaseURL() {
        return baseURL;
    }

    /**
     * Sets site base URL
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * Gets site language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets site language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets site currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets site currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
