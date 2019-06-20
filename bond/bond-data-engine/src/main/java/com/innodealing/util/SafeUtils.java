package com.innodealing.util;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SafeUtils {
	
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

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
    
	public static String getString(Object obj) {
		String tmpret = "";
		if (obj != null)
			tmpret = obj.toString();
		return tmpret.trim();
	}
    
    /**
	 * 
	 * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
	 * 实现步骤: <br>
	 * 
	 * @param paraMap
	 *            要排序的Map对象
	 * @param urlEncode
	 *            是否需要URLENCODE
	 * @param keyToLower
	 *            是否需要将Key转换为全小写 true:key转化成小写，false:不转化
	 * @return
	 */
	public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
		String buff = "";
		Map<String, String> tmpMap = paraMap;
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

				@Override
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			// 构造URL 键值对的格式
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, String> item : infoIds) {
				if (!StringUtils.isBlank(item.getKey())) {
					String key = item.getKey();
					String val = item.getValue();
					if (urlEncode) {
						val = URLEncoder.encode(val, "utf-8");
					}
					if (keyToLower) {
						buf.append(key.toLowerCase() + "=" + val);
					} else {
						buf.append(key + "=" + val);
					}
					buf.append("&");
				}

			}
			buff = buf.toString();
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			return null;
		}
		return buff;
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
}
