package com.analysis.startup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configuration {

	private static final Logger LOGGER = LogManager.getLogger(Configuration.class);

	private static final Properties properties = new Properties();

	static {
		try {
//			String fileSeparator = System.getProperty("file.separator");
//			String configs = System.getProperty("user.dir") + fileSeparator + "config.properties";
//			InputStream input = new FileInputStream(new File(configs));  
			InputStream input = Configuration.class.getClassLoader().getResourceAsStream("config.properties");
			if (input == null) {
				input = Configuration.class.getResourceAsStream("config.properties");
			}
			LOGGER.info("Loading configure file from :" + input);
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Load config file failed", e);
		}
	}

	private Configuration() {
	}

	private static void printAllConfigure() {
		Set<Object> keys = properties.keySet();
		Iterator<Object> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			LOGGER.info(key + " = " + properties.getProperty(key));
		}
	}

	public static String get(String key, String defaultValue) {
		String value = properties.getProperty(key, defaultValue);
		if (value == null || value.equals("")) {
			value = value.replaceAll("\\s", "");
		}
		return value;
	}

	public static int getInt(String key, int defaultValue) {
		String value = properties.getProperty(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		return Integer.parseInt(value.replaceAll("\\s", ""));
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		String value = properties.getProperty(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value.replaceAll("\\s", ""));
	}
}
