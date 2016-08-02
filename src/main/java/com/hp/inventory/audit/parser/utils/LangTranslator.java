/*
 * Copyright (c) 2016 Topcoder Inc. All rights reserved.
 */
package com.hp.inventory.audit.parser.utils;

import com.google.gson.Gson;
import com.hp.inventory.audit.parser.Config;
import com.hp.inventory.audit.parser.RulesConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This is a class to support multiple languages.
 */
public class LangTranslator {

  /**
   * Represents the instance.
   */
  private static LangTranslator instance;

  /**
   * Represents the translator.
   */
  private static Map<Locale, ResourceBundle> translators = new HashMap<>();

  /**
   * Represents the resource bundle.
   */
  private final ResourceBundle baseTranslator;

  /**
   * Represents the translator.
   */
  private ResourceBundle translator;

  /**
   * Gets the singleton instance.
   * @return the instance.
   */
  public static LangTranslator getInstance() {
    if (instance == null) {
      instance = new LangTranslator();
    }
    return instance;
  }

  /**
   * Constructs the instance.
   */
  private LangTranslator() {
    // load the lang files
    baseTranslator = ResourceBundle.getBundle("strings", Locale.US);
    translators.put(Locale.US, baseTranslator);
    translators.put(Locale.UK, ResourceBundle.getBundle("strings", Locale.UK));
    translators.put(Locale.GERMANY, ResourceBundle.getBundle("strings", Locale.GERMANY));
  }

  /**
   * Initialize the translator.
   * @param config the config.
   */
  public void init(Config config) {
    String country = "US";
    try (BufferedReader reader = new BufferedReader(new FileReader(config.rulesConfig))) {
      RulesConfig rulesCfg = (new Gson()).fromJson(reader, RulesConfig.class);
      if (rulesCfg.locale != null) {
        country = rulesCfg.locale;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (country.equals("US")) {
      translator = translators.get(Locale.US);
    } else if (country.equals("UK")) {
      translator = translators.get(Locale.UK);
    } else if (country.equals("GERMANY")) {
      translator = translators.get(Locale.GERMANY);
    } else {
      throw new IllegalArgumentException("This country is not supported yet. :" + country);
    }
  }

  /**
   * Gets the string.
   * @param str the str.
   * @return the string.
   */
  public String getString(String str) {
    String value;
    try {
      value = translator.getString(str);
    } catch (MissingResourceException e) {
      value = null;
    }

    if (value == null && translator != baseTranslator) {
      try {
        value = baseTranslator.getString(str);
      } catch (MissingResourceException e) {
        value = null;
      }
    }
    if (value == null) {
      value = str;
    }
    return value;
  }

}
