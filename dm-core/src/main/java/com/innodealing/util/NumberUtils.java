package com.innodealing.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class NumberUtils {
    
    /**
     * 讲结果保留两位有效小数
     * @param field
     * @return
     */
    public static BigDecimal KeepTwoDecimal(BigDecimal field , int percent){
        if(field == null){
            return null;
        }else{
            BigDecimal c = field;
            //是百分比的需要乘以100
            if(percent == 1){
                c = c.multiply(new BigDecimal(100));
            }
            //保留两位有效小数为0的话则取原值
            if(c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != 0){
                c = c.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            return c;
        }
    }
    
    /**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){  
		if(str == null || "".equals(str.replaceAll(" ", ""))){
			return false;
		}
	    Pattern pattern = Pattern.compile("(-)?[0-9]*(.)?[0-9]*");  
	    return pattern.matcher(str).matches();     
	}  
}
