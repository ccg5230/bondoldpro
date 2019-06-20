package com.innodealing.util;

public class StringUtils {
	/**
	 * 是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		if(!StringUtils.isNull(str) && !"".equals(str.trim())){
			return false;
		}
		return true;
	}

	/**
	 * 是否为null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str == null){
			return true;
		}
		return false;
	}
}
