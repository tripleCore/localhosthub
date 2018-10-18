package com.test.quartzserver1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext springContext = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml", "classpath:applicationContext-quartz.xml" });
	}
}