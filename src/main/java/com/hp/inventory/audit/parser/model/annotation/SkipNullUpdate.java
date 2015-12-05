/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that this field should not be updated if it is NULL
 * 
 * @author TCDEVELOPER
 *
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface SkipNullUpdate {

}
