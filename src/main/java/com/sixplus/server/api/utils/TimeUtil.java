package com.sixplus.server.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * lguplus.boiler.honeywell.common.util
 * |_ TimeUtil.java
 *
 * @author : 김신훈
 * @version :
 * @date : 2017. 5. 16. 오후 8:50:19
 * @description :
 */

public class TimeUtil {
	public static String timeStamp(int size) {
		return timeStamp().substring(0, size);
	}

	public static String timeStamp() {
		return timeStamp("yyyyMMddHHmmssSSS");
	}

	public static long getMin(long time) {

		long result = 0;
		if (time / 100 > 0) {
			result = (time / 100) * 60;
		}
		if (time % 100 > 0) {
			result += (time % 100);
		}
		return result;
	}

	public static String timeStamp(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static String timeStamp(String format, long date) {
		return new SimpleDateFormat(format).format(new Date(date));
	}

	public static double computePerDay(String openDT, String closeDT) throws Exception {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
		Date openDay = dateFormatter.parse(openDT);
		Date closeDay = dateFormatter.parse(closeDT);

		return ((closeDay.getTime() - openDay.getTime()) / 86400000L);
	}

	public static int dayOfWeek(String date) {
		try {
			GregorianCalendar today = new GregorianCalendar();
			today.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
			return today.get(7);
		} catch (Exception e) {
		}
		return 0;
	}

	public static int dayOfWeek() {
		return new GregorianCalendar().get(7);
	}

	public static int weekOfMon(String date) {
		try {
			GregorianCalendar today = new GregorianCalendar();
			today.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
			return today.get(4);
		} catch (Exception e) {
		}
		return 0;
	}

	public static int weekOfMon() {
		return new GregorianCalendar().get(4);
	}

	public static String getSessionId() {
		return timeStamp() + "." + Math.round(Math.random() * 10000.0D);
	}

	public static String logtimne(String logKey) {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String str = dayTime.format(new Date(time));

		return " [" + str + "] [" + logKey + "]";
	}

	public static String logtimne() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String str = dayTime.format(new Date(time));

		return " [" + str + "] ";
	}
}
