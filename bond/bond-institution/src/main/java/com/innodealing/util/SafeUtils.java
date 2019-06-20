package com.innodealing.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SafeUtils {
	private static Log log = LogFactory.getLog(SafeUtils.class.getName());
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT1 = "yyyyMMdd";
	public static final String DATE_FORMAT2 = "yyyy年MM月dd日";
	public static final String DATE_FORMAT3 = "MM-dd";
	public static final String DATE_FORMAT4 = "yyyy/MM/dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String TIME_FORMAT2 = "HH:mm";
	public static final String DATE_TIME_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT2 = "yyyyMMddHHmmss";
	public static final String DATE_TIME_FORMAT3 = "yyyy-MM-dd HH:mm";
	
	private static final String SEASON_1 = "一季报";
	private static final String SEASON_2 = "中报";
	private static final String SEASON_3 = "三季报";
	private static final String SEASON_4 = "年报";
	
	private static final String Q1 = "Q1";
	private static final String Q2 = "Q2";
	private static final String Q3 = "Q3";
	private static final String Q4 = "Q4";
	
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	private static final String TIMEZONE = "Asia/Shanghai";
	private static Calendar ca = Calendar.getInstance();

	public static String JSON = "JSON";
	public static String TEXT = "TEXT";

	static {
	    java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone(TIMEZONE));
	    log.info("SafeUtils: updated time zone to " + TIMEZONE);
	}
	   
	public static String generateGUID() {
		UUID tmpuuid = java.util.UUID.randomUUID();
		String tmpstr = tmpuuid.toString();
		tmpstr = tmpstr.replaceAll("-", "");
		return tmpstr;
	}

	public static int getInt(Object obj) {
		int tmpret = 0;
		if (obj != null) {
			try {
				tmpret = Integer.parseInt(obj.toString());
			} catch (Exception ex) {

			}
		}
		return tmpret;
	}
	
	public static Integer[] getIntArray(Object[] objs) {
		if(objs == null){
			return new Integer[]{};
		}
		Integer[] tmpret = new Integer[objs.length];
		for (int i = 0; i < objs.length ; i++) {
			if (objs[i] != null) {
				try {
					tmpret[i] = Integer.parseInt(objs[i].toString());
				} catch (Exception ex) {
					
				}
			}
		}
		return tmpret;
	}

	public static Long getLong(Object obj, Long defvalue) {
		Long tmpret = defvalue;
		if (obj != null) {
			try {
				tmpret = Long.parseLong(obj.toString());
			} catch (Exception ex) {
			}
		}
		return tmpret;
	}

	public static Long getLong(Object obj) {
		Long tmpret = 0L;
		if (obj != null) {
			try {
				if (!obj.toString().equals(""))
					tmpret = Long.parseLong(obj.toString());
			} catch (Exception ex) {
			}
		}
		return tmpret;
	}

	public static int getInt(Object obj, int defvalue) {
		int tmpret = defvalue;
		if (obj != null) {
			try {
				if (!obj.toString().equals(""))
					tmpret = Integer.parseInt(obj.toString());
			} catch (Exception ex) {

			}
		}
		return tmpret;
	}

	public static Integer getInteger(Object obj) {
		Integer tmpret = new Integer(0);
		if (obj != null) {
			try {
				tmpret = Integer.valueOf(obj.toString());
			} catch (Exception ex) {
				log.error("getInteger:" + ex);
			}
		}
		return tmpret;
	}

	public static Integer getInteger(Object obj, int defvalue) {
		Integer tmpret = new Integer(defvalue);
		if (obj != null) {
			try {
				tmpret = Integer.valueOf(obj.toString());
			} catch (Exception ex) {
				log.error("getInteger:" + ex);
			}
		}
		return tmpret;
	}

	public static double getDouble(Object obj) {
		double tmpvalue = 0;
		try {
			if(null != obj)
				tmpvalue = Double.parseDouble(obj.toString());
		} catch (Exception ex) {
		}
		return tmpvalue;
	}

	public static double getDouble(Object obj, double defvalue) {
		double tmpvalue = defvalue;
		try {
			tmpvalue = Double.parseDouble(obj.toString());
		} catch (Exception ex) {
			log.error("getDouble :" + ex);
		}
		return tmpvalue;
	}

	public static String getString(Object obj) {
		String tmpret = "";
		if (obj != null)
			tmpret = obj.toString();
		return tmpret.trim();
	}

	public static String getStringWithUrlEncode(Object obj) {
		String tmpret = "";
		if (obj != null) {
			try {
				tmpret = URLDecoder.decode(obj.toString(), "utf-8");
			} catch (Exception ex) {
				log.error("getStringWithUrlEncode:" + ex);
			}
		}
		return tmpret.trim();
	}

	public static java.sql.Date getSqlDate(Object obj) {
		java.sql.Date tmpdate = null;
		if (obj != null) {
			try {
				tmpdate = java.sql.Date.valueOf(obj.toString());
			} catch (Exception ex) {
				log.error("getDate :" + ex);
			}
		}
		return tmpdate;
	}

	public static java.sql.Date getSqlDate(String obj) {
		java.sql.Date tmpdate = null;
		if (obj != null) {
			try {
				tmpdate = java.sql.Date.valueOf(obj.toString());
			} catch (Exception ex) {
				log.error("getDate :" + ex);
			}
		}
		return tmpdate;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static int getCurrentUnixTime() {
	    checkTimeZone();
		int tmpCurrentTime = (int) (System.currentTimeMillis() / 1000);
		return tmpCurrentTime;
	}
	
	public static java.util.Date getCurrentTime() {
		checkTimeZone();
		Calendar ca = Calendar.getInstance();
		java.util.Date tmpnow = new java.util.Date(ca.getTimeInMillis());
		return tmpnow;
	}

	public static Date getUTCDate(long times) {

		// 1、取得本地时间：
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTimeInMillis(times * 1000);

		// 2、取得时间偏移量：
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

		// 3、取得夏令时差：
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		// 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * long 转换为 datestr
	 * 
	 * @param dateTimes
	 * @param format
	 * @return
	 */
	public static String convertLongToString(long dateTimes, String format) {
		if (dateTimes == 0)
			return "";
		else {
			ca.setTimeInMillis(dateTimes);
			SimpleDateFormat s = new SimpleDateFormat(format);
			String dateString = s.format(ca.getTime());
			return dateString;
		}
	}

	/**
	 * sqltimestamp int 转换为 datestr
	 * 
	 * @param dateTimes
	 * @param format
	 * @return
	 */
	public static String convertIntegerToString(long dateTimes, String format) {
		if (dateTimes == 0)
			return "";
		else {
			ca.setTimeInMillis(dateTimes * 1000);
			SimpleDateFormat s = new SimpleDateFormat(format);
			String dateString = s.format(ca.getTime());
			return dateString;
		}
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
	 * date str 转为 long
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long convertStringToLong(String date, String format)
			throws ParseException {
		try {
			SimpleDateFormat s = new SimpleDateFormat(format);
			ca.setTime(s.parse(date));
			return ca.getTimeInMillis() / 1000;
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * date str 转为 Date
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String date, String format)
			throws ParseException {
		try {
			SimpleDateFormat s = new SimpleDateFormat(format);
			ca.setTime(s.parse(date));
			return convertLongToDate(ca.getTimeInMillis() / 1000);
		} catch (Exception e) {
			return new Date();
		}

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
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);

		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);
		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的前一周最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfLastWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfWeek(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.WEEK_OF_YEAR) - 1);
	}

	/**
	 * 返回指定日期的月的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		return calendar.getTime();
	}

	/**
	 * 返回指定年月的月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month, 1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定年月的月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month, 1, 23, 59, 59);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的上个月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) - 1, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的季的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getFirstDayOfQuarter(calendar.get(Calendar.YEAR),
				getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的季的第一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 1 - 1;
		} else if (quarter == 2) {
			month = 4 - 1;
		} else if (quarter == 3) {
			month = 7 - 1;
		} else if (quarter == 4) {
			month = 10 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的季的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfQuarter(calendar.get(Calendar.YEAR),
				getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的季的最后一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 3 - 1;
		} else if (quarter == 2) {
			month = 6 - 1;
		} else if (quarter == 3) {
			month = 9 - 1;
		} else if (quarter == 4) {
			month = 12 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的上一季的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfLastQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfLastQuarter(calendar.get(Calendar.YEAR),
				getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的上一季的最后一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfLastQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 12 - 1;
		} else if (quarter == 2) {
			month = 3 - 1;
		} else if (quarter == 3) {
			month = 6 - 1;
		} else if (quarter == 4) {
			month = 9 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getQuarterOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) / 3 + 1;
	}

	/**
	 * java.sql.Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * string转 sql.timestamp
	 * 
	 * @param dateStr
	 * @return
	 */
	public static java.sql.Timestamp parseTimestamp(String dateStr) {
		return parseTimestamp(dateStr, DATE_TIME_FORMAT1);
	}

	/**
	 * 格式化日期
	 * 
	 * @param dateStr
	 *            字符型日期
	 * @param format
	 *            格式
	 * @return 返回日期
	 */
	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			//String dt = dateStr.replaceAll("-", "/");
//			if ((!dt.equals("")) && (dt.length() < format.length())) {
//				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
//						"0");
//			}
			date = (java.util.Date) df.parse(dateStr);
		} catch (Exception e) {
		}
		return date;
	}

	public static java.sql.Timestamp parseTimestamp(String dateStr,
			String format) {
		java.util.Date date = parseDate(dateStr, format);
		if (date != null) {
			long t = date.getTime();
			return new java.sql.Timestamp(t);
		} else
			return null;
	}

	public static java.sql.Timestamp parseTimestampSimple(String dateStr,
			String format) {
		Format f = new SimpleDateFormat(format);
		Date d;
		try {
			d = (Date) f.parseObject(dateStr);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		if (fileName == null)
			return "";

		String ext = "";
		String tempFileName = fileName.substring(fileName.lastIndexOf("/") + 1,
				fileName.length());
		int lastIndex = tempFileName.lastIndexOf(".");
		if (lastIndex >= 0) {
			ext = tempFileName.substring(lastIndex + 1).toLowerCase();
		}
		return ext;
	}

//	public static int[] getDisRandomNum(int max, int length) {
//		int N = max;
//		List<Integer> list = new ArrayList<Integer>();
//		for (int i = 0; i < N; i++) {
//			list.add(i + 1);
//		}
//		int count = N;
//		int items[] = new int[N];
//		for (int i = 0; i < length; i++) {
//			//
//			int randomInt = new Random().nextInt(count);
//			items[i] = list.get(randomInt);
//			list.remove(randomInt);
//			count--;
//			// System.out.println(items[i]);
//		}
//
//		return items;
//	}

	/**
	 * 获取星期几
	 * 
	 */
	public static String getWeekday(long date) {
		String result = "";

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 1:
			result = "星期日";
			break;
		case 2:
			result = "星期一";
			break;
		case 3:
			result = "星期二";
			break;
		case 4:
			result = "星期三";
			break;
		case 5:
			result = "星期四";
			break;
		case 6:
			result = "星期五";
			break;
		case 7:
			result = "星期六";
			break;
		}

		return result;
	}

	public static String getFormatDateTimeFromUnixTime(Integer unixtime,
			String format) {
		String tmpdatestr = "";
		try {
			if ((unixtime != null) && (unixtime != 0)) {
				Timestamp tmpdate = new Timestamp(unixtime * 1000L);
				SimpleDateFormat tmpfmt = new SimpleDateFormat(format);
				tmpdatestr = tmpfmt.format(tmpdate);
			}
		} catch (Exception ex) {

		}
		return tmpdatestr;
	}

	public static String getFormatDateTimeFromUnixTime(int unixtime,
			String format) {
		String tmpdatestr = "";
		try {
			if (unixtime != 0) {
				Timestamp tmpdate = new Timestamp(unixtime * 1000L);
				SimpleDateFormat tmpfmt = new SimpleDateFormat(format);
				tmpdatestr = tmpfmt.format(tmpdate);
			}
		} catch (Exception ex) {

		}
		return tmpdatestr;
	}

	/**
	 * 获取当天前N天
	 * 
	 * @param day
	 * @return
	 */
	public static String getCalendar(int day) {
		Date dNow = new Date(); // 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = null;
		if (day == 1) {
			d = sdf.format(dNow) + " 00:00:00";
		} else {
			day = day - 1;
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(dNow);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -day); // 设置为前一天
			dBefore = calendar.getTime(); // 得到前一天的时间
			d = sdf.format(dBefore) + " 00:00:00";
		}

		return d;
	}

	
	public static String getCalendarN(int day) {
		Date dNow = new Date(); // 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = null;
		if (day == 1) {
			d = sdf.format(dNow);
		} else {
			day = day - 1;
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(dNow);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -day); // 设置为前一天
			dBefore = calendar.getTime(); // 得到前一天的时间
			d = sdf.format(dBefore);
		}

		return d;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getDay() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(now);
	}

	public static String getDataTime(String str) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(now);
	}

	/*
	 * 获取请求头部的类型
	 */
	@SuppressWarnings("rawtypes")
	public static String getContentType(HttpServletRequest request) {
		Enumeration e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getHeader(name);
			if("content-type".equals(name)){
				
				log.info("request type is: " + value);
				
				if(value.contains("text/plain") || value.contains("application/x-www-form-urlencoded")){
					return TEXT;
				}
				else if(value.contains("application/json")){
					return JSON;
				}
					
			}
		}
		return "";
	}
	
	public static final String getFormatDate(Date date, String format) {
		String tmpstr = "";
		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		DateFormat format1 = new SimpleDateFormat(format);
		tmpstr = format1.format(date);
		return tmpstr;
	}
	
	/**
	 * 相差多少天
	 * @param date
	 * @return
	 */
	public static Long time_difference(String date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
		    Date d1 = new Date();
		    Date d2 = df.parse(date);
		    long diff = d1.getTime() - d2.getTime();
		    long days = diff / (1000 * 60 * 60 * 24);
		    return days;
		}catch (Exception e){
			return null;
		}
	}

	public static String getUTF8String(String source) {
		String tmpret = "";
		try {
			if (source.indexOf("\\x") != -1) {
				String sourceArr[] = source.split("\\\\"); // 分割拿到形如 xE9 的16进制数据
				if (sourceArr.length > 1) {
					byte[] byteArr = new byte[sourceArr.length - 1];
					for (int i = 1; i < sourceArr.length; i++) {
						Integer hexInt = Integer.decode("0" + sourceArr[i]);
						byteArr[i - 1] = hexInt.byteValue();
					}
					tmpret = new String(byteArr, "UTF-8");
				} else
					tmpret = source;
			} else
				tmpret = source;
			byte[] utf8 = tmpret.getBytes("UTF-8");
			tmpret = new String(utf8, "UTF-8");
			//System.out.println(tmpret);

		} catch (Exception ex) {
			System.out.println(ex);
		}
		return tmpret;
	}
	
	public static String getRandomStr(int length){
		String[] array = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v"};
		Random rand = new Random();
		for (int i = 30; i > 1; i--) {
			//将数组下标随机排列
		    int index = rand.nextInt(i);
		    String tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		StringBuffer sb = new StringBuffer();
		//循环取出六位数
		for(int i = 0; i < length;i++){
			sb.append(array[i]);
		}
		return sb.toString();
	}
	
	public static String getRandomNum(int length){
		String[] array = {"0","1","2","3","4","5","6","7","8","9"};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			//将数组下标随机排列
		    int index = rand.nextInt(i);
		    String tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		StringBuffer sb = new StringBuffer();
		//循环取出六位数
		for(int i = 0; i < length;i++){
			sb.append(array[i]);
		}
		return sb.toString();
	}
	
	public static String stringFormat(int id) {
		String result = String.format("%04d", id);  
		return result;
	}
	
	public static int[] getDisRandomNum(int max,int length) {
		int[] arr = new int[length];
		
		for (int i = 0; i < length; i++) {
			arr[i] = (int) (Math.random() * max);
			
			for (int j = 0; j < i; j++) {
				if (arr[j] == arr[i]) {
					i--;
					break;
				}
			}
		}
		return arr;
	}
	
	public static String getDayTime(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(now);
        return date;
        
    }
	
	public static Date getNowDate() {
	    Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateString = formatter.format(currentTime);
	    ParsePosition pos = new ParsePosition(0);
	    Date currentTime_2 = formatter.parse(dateString, pos);
	    return currentTime_2;
	 }

	 /**
     * 按当前的季度向前(包含当前季度)获取qNum个季度
     * @param date 当前日期{yyyy-MM-dd}
     * @param qNum 季度数量
     * @return 当date为"2016-05-12"qNum为3时返回值为 [2016/2Q, 2016/1Q, 2015/4Q]
     */
    public static String[] getQuarter(String date,int qNum){
        //String findate = "2016-09-12";
        String[] dateArr = date.split("-"); 
        int year = SafeUtils.getInt(dateArr[0]);
        int month = SafeUtils.getInt(dateArr[1]);
        //String [] q = {year+"/4Q",year+"/3Q",year+"/2Q",year+"/1Q"};
        String[] q = new String[qNum + 4];
        int conut = 4;
        for (int i = 0; i < qNum + 4; i++) {
            q[i] = year+"/"+"Q"+conut;
            conut --;
            if(conut == 0){
                conut = 4;
                year--;
            }
        }
        int qIndex = 0;
        if(month>=1 && month<=3){
            qIndex = 3;
        }else if(month>=4 && month<=6){
            qIndex = 2;
        }
        else if(month>=7 && month<=9){
            qIndex = 1;
        }else{
            qIndex = 0;
        }
        
        String[] result = new String[qNum]; 
        int index = 0;
        for (int i = qIndex; i < q.length; i++) {
            if(index + 1 > qNum ){
                break;
            }
            result[index] = q[i];
            index ++;
        }
        
        return result;
    }
    
    /**
     * 根据传入日期获取季度
     * @param date
     * @return yyyy/Qn
     */
    public static String getQuarter(Date date){
    	if(date != null){
    		String strDate = getFormatDate(date, DATE_FORMAT);
    		return getQuarter(strDate, 1)[0];
    	}
    	return "";
    }
    
    /**
     * 根据财报描述获取季度值 
     * @param desc
     * @return
     */
    public static int getQuarterByQuarterDesc(String desc){
    	int quarter = 0;
    	switch(desc.trim()){
    	case SEASON_1:
    		quarter = 1;
    		break;
    	case SEASON_2:
    		quarter = 2;
    		break;
    	case SEASON_3:
    		quarter = 3;
    		break;
    	case SEASON_4:
    		quarter = 4;
    		break;
    	}
    	
    	return quarter;
    }
    
    /**
     * 按当前的季度向前(包含当前季度)获取qNum个季度
     * @param date 当前日期
     * @param qNum 季度数量
     * @return 当date为"2016-05-12"qNum为3时返回值为 [2016/2Q, 2016/1Q, 2015/4Q]
     */
    public static String[] getQuarter(Date date,int qNum){
    	String strDate = getFormatDate(date, DATE_FORMAT);
    	return getQuarter(strDate, qNum);
    }
    
   
    
    public BigDecimal convertDoubleToBigDecimal (Double source) {
    	if (null != source) {
			return new BigDecimal(source);
		} else {
			return new BigDecimal(0);
		}
    }
    
    public Double convertBigDecimalToDouble (BigDecimal source) {
    	if (null != source) {
            return source.doubleValue();
		} else {
			return 0d;
		}
    }
    
    
    public static Date removeTime(Date date) {    
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        cal.set(Calendar.HOUR_OF_DAY, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime(); 
    }


    /**
     * 获取list 取值集合quantile分位值
     * @param list 取值集合
     * @param quantile 分位置[0-100]
     * @return
     */
    public static final int QUANTILE_ASC = 0;
    public static final int QUANTILE_DESC = 1;
    public static BigDecimal getQuantile(List<BigDecimal> list,int quantile, int orderType){
        if(orderType == QUANTILE_DESC){
            quantile = 101 -quantile;
        }
        
        if(list == null){
            return null;
            //throw new BusinessException("参数list不能为空");
        }
        list.removeIf(x -> x == null);
        if(quantile > 100){
        	quantile = 100;
        }
        //去重
//        List<BigDecimal> tep = new ArrayList<>();
//        for (BigDecimal big : list) {
//            if(big!=null && !tep.contains(big)){
//                tep.add(big);
//            }
//        }
//        list = tep;
        if(quantile < 1){
        	quantile = 1;
        }
        Set<BigDecimal> set = new HashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        Collections.sort(list);
        if(list.size() == 0){
            return new BigDecimal(0);
        }
        BigDecimal q = new BigDecimal(quantile);
        
        int index =  q.divide(new BigDecimal(100))
                      .multiply(new BigDecimal(list.size()))
                      //.setScale(0, BigDecimal.ROUND_HALF_UP)
                      .intValue();
        if(index != 0){
            index --;
        }
        /*if(quantile == 1){
            index = 1;
        }*/
        /*if(quantile == 100){
            index = list.size();
        }*/
        //return list.get((index > 0) ? index -1 : 0);
        return list.get(index);
    }
    
    /**
     *  获取list 取值集合quantile分位值
     * @param list
     * @param quantile
     * @param orderBy
     * @return
     */
    public static Long getQuantileLong(List<Long> list,int quantile, int orderBy){
    	if(list == null){
    		return null;
    	}
    	List<BigDecimal> listBigDecimal = new ArrayList<>();
    	for (Long 	l : list) {
    		if(l != null){
    			listBigDecimal.add(new BigDecimal(l));
    		}
    	}
    	BigDecimal result = getQuantile(listBigDecimal, quantile, orderBy);
    	return result == null ? null : result.longValue();
    }
    
    
    
    /**
     * string 转 List
     * @param str
     * @return
     */
    public static List<Long> strToArrLong(String str){
        List<Long> set = new ArrayList<>();
        if(str != null && !"0".equals(str)){
            String[] items = str.split(",");
            for (String item : items) {
                set.add(SafeUtils.getLong(item));
            }
        }
        if(set.size() == 1 && set.get(0) == 0L){
            set.set(0, -1L);
        }
        return set;
    }
    
    /**
     * isPastDate是否是过去的时间，当天MM-DD 00：00：00
     */
    public static boolean isPastDate(Date date){
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		boolean res = ca.getTime().after(date);
		return res;
    }
    
    /**
     * value 在 集合list的分位【1-100】
     * @param list 取值集合
     * @param value 参数值
     * @return
     */
    public static Integer getQuantileValue(List<BigDecimal> list,BigDecimal value, int orderType){
    	
        if(value == null || list == null){
            return null;
        }
       
        List<BigDecimal> tep = new ArrayList<>();
        for (BigDecimal big : list) {
            if(big!=null && !tep.contains(big)){
                tep.add(big);
            }
        }
        list = tep;
        if(Objects.equals(list.size(),0)){
            return 1;
        }
        Collections.sort(list);
        int index = list.indexOf(value);
        int size  = list.size();
        int quantileValue = 0;
		try {
			quantileValue = BigDecimal.valueOf(index).divide(BigDecimal.valueOf(size), 2, BigDecimal.ROUND_HALF_UP)
			    .multiply(BigDecimal.valueOf(100))
			    .intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        quantileValue = quantileValue == 0 ? 1 : quantileValue;
        if(orderType == QUANTILE_DESC){
            quantileValue = 101 -quantileValue;
        }
        return quantileValue;
    }
    
    /**
     * 精确小数点,默认精确2位
     */
    public static BigDecimal convertScale(BigDecimal num) {
    	if (null == num) {
    		return null;
    	}
    	return convertScale(num, 2);
    }
    
    /**
     * 根据参数精确小数点
     */
    public static BigDecimal convertScale(BigDecimal num, Integer round) {
    	if (null == num) {
    		return null;
    	}
    	if (null == round) {
    		return null;
    	}
    	return num.setScale(round, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 计算时间相差的天数
     */
    public static int getDaysBetween(Date d1, Date d2) {
        DateTime from = new DateTime(d1);
        DateTime to = new DateTime(d2);
        return Days.daysBetween(from.withTimeAtStartOfDay(), 
                to.withTimeAtStartOfDay()).getDays();
    }
    
    /**
     * 计算时间相差的小时数
     */
    public static double getHoursBetween(Date begin, Date end){
    	double hours = 9999.0;
    	long offset = end.getTime() - begin.getTime();
    	if (offset > 0) {
    		BigDecimal offsetdeml = new BigDecimal(offset);
    		offsetdeml = offsetdeml.divide(new BigDecimal(1000*3600), 2, BigDecimal.ROUND_HALF_EVEN);
    		hours = offsetdeml.doubleValue();
		}
    	
    	return hours;
    }
    
    public static void checkTimeZone() {
        String currentTimeZone = java.util.TimeZone.getDefault().getID();
        if (!TIMEZONE.equals(currentTimeZone)) {
            java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone(TIMEZONE));
            log.warn("checkTimeZone: correct the timezone to " + TIMEZONE);
        }
    }
    
    
    /**
    * 非法的数值排除
    * @param value
    * @return
    */
    public static Boolean exclusionIllegalValue(String value){
    	return value.replaceAll("[a-z]*[A-Z]*[\u4e00-\u9fa5]*\\d*-*_*\\s*\\.*", "").length()==0;
    }
    /*
    public static void main(String[] args) {
    	 String str = "!!！？？!!!!%*）%￥！KTV去符号标号！！当然,，。!!..**半角";  
         String str2 = "的撒阿萨德1..!";
         System.out.println(str.replaceAll("[a-z]*[A-Z]*[\u4e00-\u9fa5]*\\d*-*_*\\s*\\.*", "").length()==0);
	}
    */
    /**
     * 得到当天剩余时间(秒)
     * @return
     */
    public static Long getRestTodayTime(){
    	LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now();
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
		return TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).toNanos());
    }
    
	public static  Object json2bean(Object object, Object c) {
		Object vo = null;
		if (null == object) {
			return vo;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			vo = mapper.readValue(object.toString(), c.getClass());
		} catch (JsonParseException e) {
			log.error("JsonParseException,json=" + object.toString());
		} catch (JsonMappingException e) {
			log.error("JsonMappingException,json=" + object.toString());
		} catch (IOException e) {
			log.error("IOException,json=" + object.toString());
		}
		return vo;
	}

	/**
	 * 用yyyyMM获取yyyy-MM-dd
	 * @param yearMonth 格式yyyyMM(例如201608)
	 * @return yyyy-MM-dd
	 */
	public static String fromYearMonthToFinDate(String yearMonth) {
		if (StringUtils.isBlank(yearMonth)) return "";
		String year = yearMonth.substring(0, 4);
		String month = yearMonth.substring(4);
		Date date = null;
		try {
			date = convertStringToDate(year + "-" + month + "-01", DATE_FORMAT);
		} catch (Exception ex) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        String result = convertDateToString(cal.getTime(), DATE_FORMAT);
        return result.toString();
	}
	
	/**
	 * 用yyyyMM获取yyyy/Qn
	 * @param yearMonth 格式yyyyMM(例如201608)
	 * @return yyyy/Qn
	 */
	public static String getQuarter(String yearMonth) {
		if (StringUtils.isBlank(yearMonth)) return "";
		String year = yearMonth.substring(0, 4);
		String month = yearMonth.substring(4);
		Date date = null;
		try {
			date = convertStringToDate(year + "-" + month + "-01", DATE_FORMAT);
		} catch (Exception ex) {
			return null;
		}
    	if (date != null) {
    		String strDate = getFormatDate(date, DATE_FORMAT);
    		return getQuarter(strDate, 1)[0];
    	}
    	return "";
    }
	
	/**
	 * 将yyyy/Qn转成yyyy-MM-dd)
	 * @param yearQn 格式yyyy/Qn(例如2016/Q3)
	 * @return
	 */
	public static String convertFromYearQnToDate(String yearQn) {
		if (StringUtils.isBlank(yearQn)) return "";
		Map<String, String> quarterMap = getQuarterMap();
		String result = "";
		try {
			String ext = quarterMap.get(yearQn.split("/")[1].toUpperCase());
			result = yearQn.split("/")[0] + "-" + ext;
		} catch (Exception ex) {
			log.error("convertFromYearQnToDate exception with yearQn[" + yearQn + "]:" + ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 将yyyy/一季报[中报][三季报][年报])转成yyyy-MM-dd)
	 * @param yearQn 格式yyyy/一季报[中报][三季报][年报])(例如2016/一季报)
	 * @return
	 */
	public static String convertFromYearReportToDate(String yearQn) {
		if (StringUtils.isBlank(yearQn)) return "";
		String result = "";
		try {
			String ext = resultQuarterReportMap.get(yearQn.split("/")[1].toUpperCase());
			result = yearQn.split("/")[0] + "-" + ext;
		} catch (Exception ex) {
			log.error("convertFromYearQnToDate exception with yearQn[" + yearQn + "]:" + ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 将yyyy/Qn转成yyyy/一季报[中报][三季报][年报])
	 * @param yearQn 格式yyyy/Qn(例如2016/Q3)
	 * @return
	 */
	public static String convertFromYearQnToYearDesc(String yearQn) {
		if (StringUtils.isBlank(yearQn)) return "";
		Map<Integer, String> seasonMap = getSeasonMap();
		String result = "";
		try {
			String ext = seasonMap.get(Integer.valueOf(yearQn.split("Q")[1]));
			result = String.format("%s/%s", yearQn.split("/")[0], ext);
		} catch (Exception ex) {
			log.error("convertFromYearQnToYearDesc exception with yearQn[" + yearQn + "]:" + ex.getMessage());
		}
		return result;
	}
	
	private static Map<Integer, String> getSeasonMap() {
		Map<Integer, String> result = new HashMap<Integer, String>();
		result.put(1, SEASON_1);
		result.put(2, SEASON_2);
		result.put(3, SEASON_3);
		result.put(4, SEASON_4);
		return result;
	}
	
	private static Map<String, String> resultQuarterMap = new HashMap<String, String>();
	static {
		resultQuarterMap.put(Q1, "03-31");
		resultQuarterMap.put(Q2, "06-30");
		resultQuarterMap.put(Q3, "09-30");
		resultQuarterMap.put(Q4, "12-31");
	}
	private static Map<String, String> resultQuarterReportMap = new HashMap<String, String>();
	static {
		resultQuarterReportMap.put(SEASON_1, "03-31");
		resultQuarterReportMap.put(SEASON_2, "06-30");
		resultQuarterReportMap.put(SEASON_3, "09-30");
		resultQuarterReportMap.put(SEASON_4, "12-31");
	}
	
	private static Map<String, String> getQuarterMap() {
		return resultQuarterMap;
	}
	
	/**
	 * 将yyyy/Qn转成yyyy一季报[中报][三季报][年报]), 没有斜杠
	 * @param yearQn 格式yyyy/Qn(例如2016/Q3)
	 * @return
	 */
	public static String convertFromYearQnToYearTitle(String yearQn) {
		if (StringUtils.isBlank(yearQn)) return "";
		Map<Integer, String> seasonMap = getSeasonMap();
		String result = "";
		try {
			String ext = seasonMap.get(Integer.valueOf(yearQn.split("Q")[1]));
			result = String.format("%s%s", yearQn.split("/")[0], ext);
		} catch (Exception ex) {
			log.error("convertFromYearQnToYearDesc exception with yearQn[" + yearQn + "]:" + ex.getMessage());
		}
		return result;
	}

	/**
	 * 将yyyy/Qn转成yyyy年一季报[中报][三季报][年报])
	 * @return
	 */
	public static String convertFromYearQnToYearTitle2(String yearQn) {
		if (StringUtils.isBlank(yearQn)) return "";
		Map<Integer, String> seasonMap = getSeasonMap();
		String result = "";
		try {
			String ext = seasonMap.get(Integer.valueOf(yearQn.split("Q")[1]));
			result = String.format("%s%s%s", yearQn.split("/")[0], "年", ext);
		} catch (Exception ex) {
			log.error("convertFromYearQnToYearDesc exception with yearQn[" + yearQn + "]:" + ex.getMessage());
		}
		return result;
	}
	
	/**
	 * @param quanMonth
	 * @return
	 */
	public static String getQuarterDescByMonth(Integer quanMonth) {
		String desc = "";
		switch(quanMonth){
		case 1:
		case 2:
		case 3:
			desc = SEASON_1;
			break;
		case 4:
		case 5:
		case 6:
			desc = SEASON_2;
			break;
		case 7:
		case 8:
		case 9:
			desc = SEASON_3;
			break;
		case 10:
		case 11:
		case 12:
			desc = SEASON_4;
			break;
		}
		
		return desc;
	}

	public static <T> T throwNullBusinessEx(T target, String msg) {
		if (null == target) {
			log.error(msg);
			throw new BusinessException(msg);
		}
		return target;
	}
	
	public static String getDateFromNow(int days) {
		String result = null;
		try {
			Calendar cld = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			cld.add(Calendar.DATE, days);
			result = sdf.format(cld.getTime());
		} catch (Exception ex) {
			log.error(String.format("getDateBeforeNow: days[%1$d] exception[%2&s]", days, ex.getMessage()));
		}
		return result;
	}
	
	/**
	 * 获取排序
	 * @param sortParas 排序规则 [sortName:sortDire]
	 * @return
	 */
	public static Sort convertToSort(String sortStr) {
		if (StringUtils.isBlank(sortStr)) return null;
		String[] sortParas = sortStr.split(":");
		String sortField = sortParas[0];
		String sortDireStr = sortParas.length > 1 ? sortParas[1] : "desc";
		Direction sortDir = sortDireStr.toLowerCase().startsWith("des")? 
				Sort.Direction.DESC : Sort.Direction.ASC;
		return new Sort(sortDir, sortField);
	}
	
	/**
	 * 获取当前时间的yyyy-MM-dd格式字符串
	 * @return
	 */
	public static String getCurrDateFmtStr() {
		return getCurrDateFmtStr(DATE_FORMAT);
	}
	
	/**
	 * 获取当前时间的指定格式字符串
	 * @param format 需要返回的日期格式 [默认yyyy-MM-dd]
	 * @return
	 */
	public static String getCurrDateFmtStr(String format) {
		String result = null;
		try {
			format = StringUtils.isBlank(format) ? DATE_FORMAT : format;
			result = convertDateToString(getCurrentTime(), format);
		} catch (Exception ex) {
			log.error(String.format("getCurrFmtDateStr: format[%1$d] exception[%2&s]", format, ex.getMessage()));
		}
		return result;
	}
	
	public static Double getSafeDouble(BigDecimal value) {
		return value != null ? value.doubleValue() : null;
	}
	
	/**
	 * 获取上skip年相同的日期
	 * @param date 种子日期yyyy-mm-dd
	 * @param skip 跳跃年数
	 * @return
	 */
	public static String prevYear(String date,int skip) {
		String regex = "[0-9]{4}[-][0-1][0-9][-][0-3][0-9]";
		//null处理
		if(date == null){
			return null;
		}
		//处理日期
		if( date.matches(regex)){
			String y = date.substring(0, 4);
			Integer year = Integer.valueOf(y);
			return (year - skip) + date.substring(4);
		}else{
			throw new RuntimeException("data 值必须符合" + DATE_FORMAT + ";当前date=" + date);
		}
		
	}
	
	/**
	 * 获取年份
	 * @param date
	 * @return
	 */
	public static Integer getYear(String date){
		String regex = "[0-9]{4}[-][0-1][0-9][-][0-3][0-9]";
		//null处理
		if(date == null){
			return null;
		}
		//处理日期
		if( date.matches(regex)){
			String y = date.substring(0, 4);
			return Integer.valueOf(y);
		}else{
			throw new RuntimeException("date 值必须符合" + DATE_FORMAT + ";当前date=" + date);
		}
		
	}
	
    
  /** 
   * 获取当年
   * @param year 
   * @return 
   */  
  public static int getCurrYear(){  
      Calendar currCal=Calendar.getInstance();    
      int currentYear = currCal.get(Calendar.YEAR);  
      return currentYear;  
  }
  
  /** 
   * 是否是当年的时间
   * @param year 
   * @return 
   */  
  public static boolean isCurrYear(Date date){ 
	  Calendar cal =Calendar.getInstance();
      cal.setTime(date);
      int year = cal.get(Calendar.YEAR);
      if (year == getCurrYear()) {
          return true;
      }
      return false;
  }
  
}

