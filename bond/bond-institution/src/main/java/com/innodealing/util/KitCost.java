package com.innodealing.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KitCost {

	static {
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);
	}

	public static String objToJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T jsonToObject(String json, Class<T> cls) {
		T t = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			t = mapper.readValue(json, cls);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;

	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return
	 */
	public static String urlDecoder(String str) {
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 编码
	 * 
	 * @param str
	 * @return
	 */
	public static String urlEncoder(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date strToDate(String data, String pattern) {
		Date result = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			result = sf.parse(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String dateToStr(Date date, String pattern) {
		String result = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			result = sf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 时间转换 yyyy-MM-dd 00:00:00:000
	 * 
	 * @param data
	 * @param pattern
	 * @return
	 */
	public static Date strToDateStart(String data, String pattern) {
		Date result = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			result = sf.parse(data);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(result);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			result = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 时间转换 yyyy-MM-dd 23:59:59:999
	 * 
	 * @param data
	 * @param pattern
	 * @return
	 */
	public static Date strToDateEnd(String data, String pattern) {
		Date result = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			result = sf.parse(data);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(result);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			result = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 字符串分割
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] split(String str, String regex) {
		List<String> result = new ArrayList<String>();
		if (StringUtils.isNotBlank(str)) {
			String[] split = str.split(regex);
			for (String s : split) {
				if (StringUtils.isNotBlank(s))
					result.add(s);
			}
		}
		return result.toArray(new String[result.size()]);
	}

	public static <T> List<T> mapListToBeanList(List<Map<String, Object>> map, Class<T> cls) {
		List<T> result = new ArrayList<>();

		map.forEach(x -> {
			result.add(mapToBean(x, cls));
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T mapToBean(Map<String, Object> map, Class<T> cls) {

		if (map == null) {
			return null;
		}

		Map<String, String> filedColumnMap = getFiledColumnMap(cls);
		Set<String> keySet = filedColumnMap.keySet();
		for (String key : keySet) {
			if (map.containsKey(key)) {
				map.put(key, map.get(key));
			} else {
				map.put(key, map.get(filedColumnMap.get(key)));
				map.remove(filedColumnMap.get(key));
			}
		}

		try {
			Object obj = cls.newInstance();
			BeanUtils.populate(obj, map);
			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Map<Class<?>, Map<String, String>> filedColumn = new HashMap<>();

	private static Map<String, String> getFiledColumnMap(Class<?> cls) {
		if (filedColumn.get(cls) == null) {
			Map<String, String> column = new HashMap<>();
			List<Field> fieldList = new ArrayList<>();
			Class<?> tempClass = cls;
			while (tempClass != null) {
				fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
				tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
			}

			for (Field field : fieldList) {

				Column declaredAnnotation = field.getDeclaredAnnotation(Column.class);
				if (declaredAnnotation == null) {
					column.put(field.getName(), field.getName());
				} else {
					column.put(field.getName(), declaredAnnotation.name());
				}
				filedColumn.put(cls, column);
			}
		}
		return filedColumn.get(cls);
	}

	private static Field getField(Class<?> cls, String name) {
		while (cls != null) {
			try {
				return cls.getDeclaredField(name);
			} catch (Exception e) {
				cls = cls.getSuperclass();
			}
		}
		return null;
	}

	public static Map<String, Object> beanToMap(Object obj) {
		return beanToMap(obj, null);

	}

	public static Map<String, Object> beanToMap(Object obj, Map<?, ?> fixedCols) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				String columnKey = null;
				// 过滤class属性
				if (!key.equals("class")) {
					if ((fixedCols == null) || fixedCols.containsKey(key)) {
						// 得到property对应的getter方法
						Method getter = property.getReadMethod();
						Object value = getter.invoke(obj);

						columnKey = getFiledColumnMap(obj.getClass()).get(key);

						Field field = getField(obj.getClass(), key);
						ColumnIgnore columnIgnore = field.getDeclaredAnnotation(ColumnIgnore.class);
						if (columnIgnore != null)
							continue;

						if (value instanceof Date) {

							if (field != null) {
								JsonFormat dateFormat = field.getDeclaredAnnotation(JsonFormat.class);
								if (dateFormat != null)
									map.put(columnKey, dateToStr((Date) value, dateFormat.pattern()));
								map.put(columnKey, value);
							}
						} else {
							map.put(columnKey, value);
						}

					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
