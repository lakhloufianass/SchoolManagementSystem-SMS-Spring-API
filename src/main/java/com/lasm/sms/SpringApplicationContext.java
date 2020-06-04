package com.lasm.sms;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Class to get bean from context
 * @author macpro
 *
 */
public class SpringApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT = applicationContext;
	}

	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}	
}