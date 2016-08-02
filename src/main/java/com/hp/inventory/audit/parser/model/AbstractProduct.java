/*
 * Copyright (c) 2015 - 2016 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hp.inventory.audit.parser.model.annotation.IgnoreUpdate;
import com.hp.inventory.audit.parser.model.annotation.SkipNullUpdate;
import com.hp.inventory.audit.parser.model.annotation.TrackChanges;
import org.apache.commons.lang3.text.WordUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Base class common to all products.
 *
 * changes: remove the Product field.
 * changes in 1.0.2: add siteId and productId, change the primary key from productNumber to productId
 * @author TCDEVELOPER
 * @version 1.0.2
 */
@MappedSuperclass
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="productId")
public abstract class AbstractProduct {

	/** @since 1.0.1
	 * Stores all parsing errors
	 */
	@Transient
	private StringBuilder parsingErrors = new StringBuilder();
	
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
	
    public Date getComingSoonDate() {
		return prvComingSoonDate;
	}

	public void setComingSoonDate(Date comingSoonDate) {
		this.prvComingSoonDate = comingSoonDate;
	}

   /**
    * Represents the productId.
    */
    @Id
    private String productId;

	  @Column(nullable = false)
    private String productNumber;

  /**
   * Represents the site Id.
   */
  @Column(nullable = false)
    private int siteId;

    @Version
    @GeneratedValue
    private Long version;

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


    /**
     * Updates Set content.
     *
     * @param what   destination set
     * @param from   source set
     * @param update TODO
     * @param delete TODO
     * @throws Exception
     */
    protected <T> void updateSet(Set<T> what, Set<T> from, boolean update, boolean delete) throws Exception {
        if (delete) {
            what.retainAll(from);
        }

        if (update) {
            for (T w : what) {
                for (T f : from) {
                    if (w.equals(f)) {
                        updateObject(w, f);
                    }
                }
            }
        }
        what.addAll(from);
    }

    /**
     * Updates Map content, similar to updateSet.
     *
     * @param what   destination map
     * @param from   source map
     * @param update if we should update matching key-value pairs
     * @param delete if we should delete pairs in "what" not found in "from"
     * @throws Exception
     */
    protected <K,V> void updateMap(Map<K,V> what, Map<K,V> from, boolean update, boolean delete) throws Exception {
        Set<Map.Entry<K,V>> whatSet = new HashSet<>(what.entrySet());
        Set<Map.Entry<K,V>> fromSet = new HashSet<>(from.entrySet());
        updateSet(whatSet, fromSet, update, delete);
        what.clear();
        whatSet.forEach((entry) -> {
            what.put(entry.getKey(), entry.getValue());
        });
    }

    /**
     * Updates 'this' object's fields with fromObject's fields using setters in
     * 'this' object
     *
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    static <T> void updateObject(T where, T from) throws Exception {

        Class clazz = from.getClass();

        while (clazz != null && !clazz.equals(Object.class)
                && !clazz.equals(String.class) && !clazz.equals(Integer.class)
                && !clazz.equals(Long.class) && !clazz.equals(BigInteger.class)
                && !clazz.equals(BigDecimal.class) && !clazz.equals(Date.class)
                && !clazz.equals(Calendar.class)) {
            for (Field f : clazz.getDeclaredFields()) {

                // skip static fields
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                Transient[] t = f.getAnnotationsByType(Transient.class);

                IgnoreUpdate[] ignore = f
                        .getAnnotationsByType(IgnoreUpdate.class);

                if ((t != null && t.length > 0)
                        || (ignore != null && ignore.length > 0)) {
                    // skip fields, marked as @Transient or @IgnoreUpdate
                    continue;
                }

                SkipNullUpdate[] nullUpdate = f
                        .getAnnotationsByType(SkipNullUpdate.class);

                f.setAccessible(true);

                String setterName = "set" + WordUtils.capitalize(f.getName());

                Object newValue = f.get(from);

                if (nullUpdate != null && nullUpdate.length > 0) {
                    if (newValue == null)
                        continue;
                }

                java.beans.Statement stmt = new java.beans.Statement(where,
                        setterName, new Object[]{f.get(from)});

                stmt.execute();

            }

            clazz = clazz.getSuperclass();
        }
    }


    /**
     * Updates 'this' object's fields with fromObject's fields
     * using setters in 'this' object
     *
     * @throws Exception
     */
    static void updateEntity(Object from, Object to) throws Exception {
        Map<String, Object> trackingCurrent = new HashMap<String, Object>();
        Map<String, Object> previousValue = new HashMap<String, Object>();
        Map<String, Field> trackingPrevious = new HashMap<String, Field>();
        Map<String, Field> trackingDateOfChange = new HashMap<String, Field>();

        Class clazz = from.getClass();

        while (clazz != null && !clazz.equals(Object.class)) {
            for (Field f : clazz.getDeclaredFields()) {

                Transient[] t = f.getAnnotationsByType(Transient.class);

                if (t != null && t.length > 0) {
                    //skip fields, marked as @Transient
                    continue;
                }

                boolean trackCurrent = false;

                String keyName = null;

                TrackChanges[] tc = f.getAnnotationsByType(TrackChanges.class);
                if (tc != null && tc.length > 0) {
                    if (tc.length > 1) throw new RuntimeException("@TrackChanges must specified only once");
                    keyName = tc[0].key();

                    switch (tc[0].target()) {
                        case CURRENT:
                            if (trackingCurrent.containsKey(keyName)) {
                                throw new RuntimeException("@TrackChanges must specified only one CURRENT with certain key");
                            }
                            trackingCurrent.put(keyName, f);
                            trackCurrent = true;
                            break;
                        case PREVIOUS:
                            if (trackingPrevious.containsKey(keyName)) {
                                throw new RuntimeException("@TrackChanges must specified only one PREVIOUS with certain key");
                            }
                            trackingPrevious.put(keyName, f);
                            //skip update for this field
                            continue;
                        case DATE:
                            if (trackingDateOfChange.containsKey(keyName)) {
                                throw new RuntimeException("@TrackChanges must specified only one DATE with certain key");
                            }
                            trackingDateOfChange.put(keyName, f);
                            //skip update for this field
                            continue;
                    }
                }

                SkipNullUpdate[] nullUpdate = f.getAnnotationsByType(SkipNullUpdate.class);

                f.setAccessible(true);

                String setterName = "set" + WordUtils.capitalize(f.getName());

                Object currentValue = f.get(to);
                Object newValue = f.get(from);

                if (nullUpdate != null && nullUpdate.length > 0) {
                    if (newValue == null) continue;
                }

                if (trackCurrent) {
                    boolean changed = false;
                    if (currentValue != null && newValue != null) {
                        if (!currentValue.equals(newValue)) {
                            changed = true;
                        }
                    } else {
                        if (currentValue != null || newValue != null) {
                            changed = true;
                        }
                    }

                    if (changed) {
                        previousValue.put(keyName, currentValue);
                    }
                }

                java.beans.Statement stmt = new java.beans.Statement(to, setterName, new Object[]{f.get(from)});

                stmt.execute();

            }

            clazz = clazz.getSuperclass();
        }
        for (String keyName : trackingCurrent.keySet()) {
            Field previous = trackingPrevious.get(keyName);
            Field dateOfChange = trackingDateOfChange.get(keyName);

            if (previous == null) {
                throw new RuntimeException(String.format("@TrackChanges must specify associated PREVIOUS with the same CURRENT key %s", keyName));
            }
            if (dateOfChange == null) {
                throw new RuntimeException(String.format("@TrackChanges must specify associated DATE with the same CURRENT key %s", keyName));
            }

            if (previousValue.containsKey(keyName)) {
                //was changed

                previous.setAccessible(true);
                dateOfChange.setAccessible(true);

                Date now = new Date();

                java.beans.Statement stmt = new java.beans.Statement(to, "set" + WordUtils.capitalize(previous.getName()), new Object[]{previousValue.get(keyName)});
                stmt.execute();

                stmt = new java.beans.Statement(to, "set" + WordUtils.capitalize(dateOfChange.getName()), new Object[]{now});
                stmt.execute();
            }
        }
    }

    /**
     * Populates properties from this entity into to the Product entity
     *
     * @param productDefinition
     */
    public void populateCommonsToProduct(Product productDefinition) {

        productDefinition.setProductNumber(this.getProductNumber());
        productDefinition.setProductName(this.getProductName());
        productDefinition.setProductId(this.getProductId());
        productDefinition.setSiteId(this.getSiteId());

        String productType = productDefinition.getProductType();

        productDefinition.setProductType(productType);

        String parsingError = this.getParsingErrors().toString();

        if (parsingError.isEmpty()) {
            productDefinition.setParsingError(null);
            productDefinition.setDateOfParsingError(null);
        } else {
            Date now = new Date();

            parsingError = parsingError.substring(0, parsingError.length() - 1);

            productDefinition.setParsingError(parsingError);
            productDefinition.setDateOfParsingError(now);
        }

        productDefinition.setComingSoonDate(this.getComingSoonDate());
        productDefinition.setHpDataSheet(this.getHpDataSheet());
        productDefinition.setParseDate(this.getParseDate());
    }

    /**
     * Initializes new Entity
     * Should set some default values for the new entity
     */
    public abstract void initNewEntity();

    /**
     * Upgrades current existing entity
     * from newly parsed entity
     *
     * @throws Exception
     */
    public abstract void upgradeEntityFrom(AbstractProduct from) throws Exception;

  /**
   * Gets the product id.
   * @return the product id.
   */
  public String getProductId() {
        return productId;
    }

  /**
   * Sets the product id.
   * @param productId the product id.
   */
  public void setProductId(String productId) {
        this.productId = productId;
    }

  /**
   * Gets the site id.
   * @return the site id.
   */
  public int getSiteId() {
        return siteId;
    }

  /**
   * Sets the site id.
   * @param siteId the site id.
   */
  public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
