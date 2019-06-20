package com.innodealing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT1 = "yyyy-MM-dd HH:mm:ss";

	private static Calendar ca = Calendar.getInstance();
    
	/**
	 * date str 转为 Date
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date convertStringToDate(String date, String format) {
		try {
			SimpleDateFormat s = new SimpleDateFormat(format);
			ca.setTime(s.parse(date));
			return convertLongToDate(ca.getTimeInMillis() / 1000);
		} catch (Exception e) {
			return new Date();
		}
	}
	
	/**
	 * date 转为 string,格式:yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convertDateToString(Date date) {
		return convertDateToString(date, DATE_TIME_FORMAT1);
	}
	
	/**
	 * date 转为 string
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convertDateToString(Date date, String format) {
		SimpleDateFormat s = new SimpleDateFormat(format);
		String dateString = s.format(date);
		return dateString;
	}
	
	/**
	 * long 转换为 date
	 * 
	 * @param dateTimes
	 * @return
	 */
	public static Date convertLongToDate(long dateTimes) {
		if (dateTimes == 0)
			return new Date();
		else {
			ca.setTimeInMillis(Long.parseLong(dateTimes + "000"));
			return ca.getTime();
		}
	}
	
	/**
	 * 次日
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextDay(Date date) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(date); 
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }
}
