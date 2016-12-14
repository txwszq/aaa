package com.kedacom.expenses.service.webserver.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间与字符串互转工具
 * @author huangyin
 */
public class DateFormatUtil {

	/**
	 * 把日期转化成字符串(不带时分秒)
	 * @param d
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String toDateFormat(Date date) {
		StringBuffer myDate = new StringBuffer();
		myDate.append(date.getYear() + 1900).append("-").append(addZero(date.getMonth() + 1)).append("-").append(addZero(date.getDate()));
		return myDate.toString();
	}

	/**
	 * 把日期转化成字符串(带时分秒)
	 * @param d
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String toTimeFormat(Date date) {
		StringBuffer myDate = new StringBuffer();
		myDate.append(date.getYear() + 1900).append("-").append(addZero(date.getMonth() + 1)).append("-").append(addZero(date.getDate())).append(" ").append(
				addZero(date.getHours())).append(":").append(addZero(date.getMinutes())).append(":").append(addZero(date.getSeconds()));
		return myDate.toString();
	}

	/**
	 * 把日期转化成字符串(带时分秒 1999/01/01 23:59:59)
	 * @param d
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String toTimeFormat2(Date date) {
		StringBuffer myDate = new StringBuffer();
		myDate.append(addZero(date.getMonth() + 1)).append("/").append(addZero(date.getDate())).append("/").append(date.getYear() + 1900).append(" ").append(
				addZero(date.getHours())).append(":").append(addZero(date.getMinutes())).append(":").append(addZero(date.getSeconds()));
		return myDate.toString();
	}

	/**
	 * 把日期字符串转换成Date(时分秒均为0)
	 * @param dateFormat 形如 [ 2012-02-03 ]
	 * @return
	 */
	public static Date toDate(String dateFormat) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return (Date) myFmt.parse(dateFormat);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 把日期字符串转换成Date
	 * @param dateFormat 形如 [ 2012-02-03 12:12:08 ]
	 * @return
	 */
	public static Date toTime(String dateFormat) {
		// 规范格式
		dateFormat = dateFormat.replaceAll("/", "-");
		if (dateFormat.split(":").length < 3) {
			dateFormat = new StringBuffer(dateFormat).append(":00").toString();
		}
		//
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return (Date) myFmt.parse(dateFormat);
		} catch (ParseException e) {
			return null;
		}
	}

	private static String addZero(int i) {
		if (i < 10) {
			return new StringBuffer("0").append(i).toString();
		}
		return Integer.toString(i);
	}
}
