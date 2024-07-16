package com.sixplus.server.api.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionLoggingUtil {
	public static String getExceptionMessage(Exception e) {
		return ExceptionUtils.getStackTrace(e);
	}
}