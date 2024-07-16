package com.sixplus.server.api.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class ApplicationContextManager implements ApplicationContextAware {

	private static final ApplicationContextManager instance = new ApplicationContextManager();
	private ApplicationContext applicationContext = null;

	public static ApplicationContextManager getInstance() {
		return instance;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		instance.applicationContext = applicationContext;
	}

	public <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public Object getBean(String className) {
		return applicationContext.getBean(className);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String className, Class<T> clazz) {
		return (T) applicationContext.getBean(className);
	}

	/**
	 * get Applcation Name
	 *
	 * @return
	 */
	public String getName() {
		return instance.applicationContext.getId();
	}

	/**
	 * property에 지정된 메세지 가져오기
	 *
	 * @param key
	 * @return String
	 */
	public String getMessage(String key) {
		String returnMessage = null;
		try {
			returnMessage = instance.applicationContext.getMessage(key, null, Locale.KOREA);
		} catch (NoSuchMessageException e) {
			throw e;
		}
		return returnMessage;
	}

	/**
	 * property에 지정된 메세지 가져오기
	 *
	 * @param key
	 * @param args
	 * @return String
	 */
	public String getMessage(String key, Object[] args) {
		String returnMessage = null;
		try {
			returnMessage = instance.applicationContext.getMessage(key, args, Locale.KOREA);
		} catch (NoSuchMessageException e) {
			throw e;
		}
		return returnMessage;
	}

	/**
	 * property에 지정된 메세지 가져오기
	 *
	 * @param key
	 * @return String
	 */
	public String getMessage(String key, String expMessage) {
		String returnMessage = null;

		try {
			returnMessage = instance.applicationContext.getMessage(key, null, Locale.KOREA);
		} catch (NoSuchMessageException e) {
			if (expMessage != null) {
				returnMessage = expMessage;
			} else {
				returnMessage = "";
			}
		}

		return returnMessage;
	}

	public String getEnvMessage(String key) {
		return applicationContext.getEnvironment().getProperty(key);
	}
}
