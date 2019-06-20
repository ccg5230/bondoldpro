package com.innodealing.datacanal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class DateUtil {
	
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String getFormatDate(Date date, String format) {
		String tmpstr = "";
		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		DateFormat format1 = new SimpleDateFormat(format);
		tmpstr = format1.format(date);
		return tmpstr;
	}
	
	
	public static void main(String[] args) {
		
		
		System.out.println(getFormatDate(new Date(1513655610000L), YYYY_MM_DD_HH_MM_SS));
		//1513611743000
	}
}
