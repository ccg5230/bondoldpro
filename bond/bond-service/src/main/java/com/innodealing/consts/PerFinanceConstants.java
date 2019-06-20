/**
 * 
 */
package com.innodealing.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stephen.ma
 * @date 2017年3月20日
 * @clasename PerFinanceConstants.java
 * @decription TODO
 */
public class PerFinanceConstants {
	
	public static final Map<String, String> COMP_CLS_MAP = new HashMap<String, String>() {{ 
        put("QY" , "一般工商企业"); 
        put("YH" , "商业银行"); 
        put("ZQ" , "证券公司"); 
        put("BX" , "保险公司"); }}; 
}
