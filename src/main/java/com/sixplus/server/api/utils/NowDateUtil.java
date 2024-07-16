/**
 *
 */
package com.sixplus.server.api.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @systemName : uvms-comm
 * @programName : NowDateDto
 * @author : 이성진
 * @createDate : 2021. 8. 18.
 * @description :
 */
public class NowDateUtil {
	private String nowDate;
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	private Integer dayOfWeek;

	public NowDateUtil() {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		SimpleDateFormat sdfCurrent = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setNowDate(sdfCurrent.format(today));
		this.year = nowDate.substring(0, 4);
		this.month = nowDate.substring(5, 7);
		this.day = nowDate.substring(8, 10);
		this.hour = nowDate.substring(11, 13);
		this.minute = nowDate.substring(14, 16);
		this.second = nowDate.substring(17, 19);
		this.dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	}

	public String getNowTime() {
		return hour + ":" + minute + ":" + second;
	}

	public String getYear() {
		return year;
	}

	public Integer getIntYear() {
		return Integer.parseInt(year);
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public Integer getIntMonth() {
		return Integer.parseInt(month);
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public Integer getIntDay() {
		return Integer.parseInt(day);
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public Integer getIntHour() {
		return Integer.parseInt(hour);
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public Integer getIntMinute() {
		return Integer.parseInt(minute);
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getSecond() {
		return second;
	}

	public Integer getIntSecond() {
		return Integer.parseInt(second);
	}

	public void setSecond(String second) {
		this.second = second;
	}

	/**
	 * @return the nowDate
	 */
	public String getNowDate() {
		return nowDate;
	}

	/**
	 * @param nowDate the nowDate to set
	 */
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	/**
	 * @return the dayOfWeek
	 */
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
}
