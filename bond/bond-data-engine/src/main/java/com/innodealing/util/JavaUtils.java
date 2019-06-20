package com.innodealing.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射
 */
public class JavaUtils {
	private static final Logger LOG = LoggerFactory.getLogger(JavaUtils.class);
	
	public static Object getFieldValue(Object obj, String fieldName) {    
    	Field field = null;
    	Object fieldValue = null;
		try {
			field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true); // 解除封装
			fieldValue = field.get(obj);
		} catch (Exception e) {
			LOG.error("JavaUtils.getFieldValue error, fieldName is " + fieldName + "... error is ", e);
			e.printStackTrace();
		}
        return fieldValue;
    }
	
	public static Date getFieldValueAsDate(Object obj, String fieldName) {    
		Date fieldValue = null;
		try {			
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			//先将要格式化的字符串转为Date类型
			fieldValue = dateFormat.parse(getFieldValue(obj, fieldName).toString());

			//然后再格式化
			//dateFormat.format(date)
		} catch (Exception e) {
			LOG.error("JavaUtils.getFieldValueAsDate error, fieldName is " + fieldName + "... error is ", e);
			e.printStackTrace();
		}
        return fieldValue;
    }
	
	public static String getFieldValueAsString(Object obj, String fieldName) {    
    	String fieldValue = null;
		try {			
			fieldValue = String.valueOf(getFieldValue(obj, fieldName));
		} catch (Exception e) {
			LOG.error("JavaUtils.getFieldValueAsString error, fieldName is " + fieldName + "... error is ", e);
			e.printStackTrace();
		}
        return fieldValue;
    }
	
}
