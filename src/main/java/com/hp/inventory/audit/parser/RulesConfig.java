/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import java.util.List;
import java.util.Map;

/**
 * Value object representing the raw de-serialized rules JSON file.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class RulesConfig {

	public Map<String, String> typeMap;
	
	public List<String> urlExclude;
	public List<Map<String, String>> urlMatch;
	
	public List<String> productNameExclude;
	public List<Map<String, String>> productNameMatch;
	
	public List<String> contentExclude;
	public List<Map<String, String>> contentMatch;
	
	public Map<String, String> queriesSpec;

	/**
	 * Configurations for assigning product type on generic products.
	 */
	public Map<String, Object> productType;
}
