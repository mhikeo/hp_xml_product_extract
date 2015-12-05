/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that this field should be tracked for changes
 * In case of a change, current value should be saved into field marked as target=PREVIOUS
 * and field marked as target=DATE should be update to current date
 * 
 * @author TCDEVELOPER
 *
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface TrackChanges {

	public enum TrackingTarget {
		CURRENT,
		PREVIOUS,
		DATE
	}
	/**
	 * (Required) key of the tracking entity
	 */
	String key();
	
	/**
	 * (Required) Type of the field
	 * Either CURRENT, PREVIOUS or DATE
	 */
	TrackingTarget target();
}
