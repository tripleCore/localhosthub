package com.tcll.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtils {

	private static final Properties PROPERTIES = new Properties();

	static {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.yml");
			PROPERTIES.load(new BufferedReader(new InputStreamReader(is, "utf-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据参数名获得参数的值，如果为空，则返回null
	 * 
	 * @param name
	 *            参数名
	 * @return
	 */
	public static String getString(String name) {
		String value = PROPERTIES.getProperty(name);
		if (value == null || "".equals(value)) {
			return null;
		}
		return value;
	}

	/**
	 * 根据参数名获得参数的值，如果为空，则返回默认值
	 * 
	 * @param name
	 *            变量名
	 * @param defvalue
	 *            默认值
	 * @return
	 */
	public static String getString(String name, String defvalue) {
		String value = PROPERTIES.getProperty(name);
		if (value == null || "".equals(value)) {
			return defvalue;
		}
		return value;
	}

	/**
	 * 根据名称获得URL对象
	 * 
	 * @param name
	 * @return
	 */
	public static URL getResource(String name) {
		return PropertiesUtils.class.getResource(name);
	}

}
