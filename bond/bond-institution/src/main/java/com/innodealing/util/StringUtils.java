package com.innodealing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;

public class StringUtils {

	/**
	 * 把一个字符串分割成一个int[]
	 * 
	 * @param str
	 * @param sign
	 *            各个标准
	 * @return
	 */
	public final static int[] strToIntArray(String str,String sign){
		if(StringUtils.isBlank(str) || sign == null){
			return null;
		}
		String[] vals = str.split(sign);
		int size = vals.length;
		int[] data = new int[size];
		for(int i = 0;i < size;i++){
			String val = vals[i];
			if(StringUtils.isNotBlank(val)){
				data[i] = Integer.valueOf(val.trim());
			}
		}
		return data;
	}

    public static boolean checkParametersIsNotNull(String...parameters) {
        boolean result = true;
        for (String value : parameters) {
            if (StringUtils.isBlank(value)) {
                result = false;
                break;
            }
        }
        return result;
    }

	/**
	 * 把一个字符串分割成一个List<Integer>
	 * 
	 * @param str
	 * @param sign
	 *            各个标准
	 * @return
	 */
	public final static List<Integer> strToIntList(String str,String sign){
		if(StringUtils.isBlank(str) || sign == null){
			return null;
		}
		String[] vals = str.split(sign);
		int size = vals.length;
		List<Integer> data = new ArrayList<Integer>();
		for(int i = 0;i < size;i++){
			String val = vals[i];
			if(StringUtils.isNotBlank(val)){
				data.add(Integer.valueOf(val.trim()));
			}
		}
		return data;
	}
	
	/**
	 * 把一个字符串分割成一个List<String>
	 * 
	 * @param str
	 * @param sign
	 *            各个标准
	 * @return
	 */
	public final static List<String> strToStringList(String str,String sign){
		if(StringUtils.isBlank(str) || sign == null){
			return null;
		}
		String[] vals = str.split(sign);
		int size = vals.length;
		List<String> data = new ArrayList<String>();
		for(int i = 0;i < size;i++){
			String val = vals[i];
			if(StringUtils.isNotBlank(val)){
				data.add(val.trim());
			}
		}
		return data;
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

	/**
	 * 不为null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str){
		return !StringUtils.isNull(str);
	}
	
	/**
	 * String[] 是否为null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean arrayISNull(String[] str){
		if(null == str || str.length==0){
			return true;
		}
		return false;
	}

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
	 * 不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str){
		return !StringUtils.isBlank(str);
	}
	
	/**
	 * 取得UUID
	 * @return
	 */
	public static String getUUID()
	{
		return UUID.randomUUID().toString();
	}
	
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}
	
	 /**
     * 从默认字符串数据组中从第from个起，取count个值至新的字符数组中，并保证第一个值为value.
     * @param value
     * @param ids
     * @param start
     * @param count
     * @return
     */
    public static StringBuilder addStringOnTop(String value, String[] ids, int start, int count) {


        StringBuilder sb = new StringBuilder();

        int max = start + count;

        if (start < 0 || count < 0) {
//            _logger.error("the start or count value can't less than 0. [{}/{}]", start, count);
            return sb;
        }

        if (ids.length+1 < max ) {
//            _logger.error("the total strings [{}] is less than max[{}] value :{}", ids.length, max);
            return sb;
        }

        int i = 0;
        if (StringUtils.isNotBlank(value)) {
            sb.append(value);
            i++;
        }

        int m = 0;

        for (String id : ids) {
            if ( m >= start) {
                if (count ==0 || m <= max) {
                    if (i > 0)
                        sb.append(",");
                    sb.append(id);
                    i++;
                }
            }
            m++;
        }
        return sb;
    }

    /**
     * 字体串数组转
     * @param values
     * @param separator
     * @return
     */
    public static String strArrayToStringWithSeperator(String[] values, String separator) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String value : values) {
            if (i > 0)
                sb.append(",");

            sb.append(value);
            i++;
        }
        return sb.toString();
    }
    
    /**
     * 字体串数组转
     * separator 以特定符号拼接,例如 ',那么返回的则为 '1','2','3'
     * @param values
     * @param separator
     * @return
     */
    public static String strArrayToStr(String[] values, String separator) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String value : values) {
            if (i > 0)
                sb.append(",");
            sb.append(separator).append(value).append(separator);
            i++;
        }
        return sb.toString();
    }


    /**
     * 从一个字符串里分离出连续的一部分
     * @param value
     * @param start
     * @param count
     * @param separator
     * @return
     */
    public static String separateOutString(String value, int start, int count, String separator) {


        /**
         * 如果记录数取0，则返回全部数据
         */
        if (count == 0) {
            return  value;
        }

        String result = "";

        String[] ids = value.split(separator);
        int length = ids.length;
        int total = start + count;

        if (length > start) {

            if (length > total) {

                int m=0;
                for (int i=start; i<total; i++) {
                    if (m<count) {
                        if (i > start)
                            result = result + separator;
                        result = result + ids[i];
                    }
                    m++;
                }

            } else {
                for (int i=start; i<length; i++) {
                    if (i>start)
                        result = result + separator;
                    result = result + ids[i];
                }
            }
        }
        return result;
    }
    
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
    /// 判断字符是否英文半角字符或标点  
    /// 32    空格  
    /// 33-47    标点  
    /// 48-57    0~9  
    /// 58-64    标点  
    /// 65-90    A~Z  
    /// 91-96    标点  
    /// 97-122    a~z  
    /// 123-126  标点  
    /// </remarks>  
    public static boolean IsBjChar(char c){  
      int i = (int)c;  
      return i >= 32 && i <= 126;  
    }
    
    /// 判断字符是否全角字符或标点  
    /// <para>全角字符 - 65248 = 半角字符</para>  
    /// <para>全角空格例外</para>  
    /// </remarks>  
    public static boolean IsQjChar(char c){  
      if (c == '\u3000') return true;  
      int i = (int)c - 65248;  
      if (i < 32) return false;  
      return IsBjChar((char)i);  
    }  
    
    /// <summary>  
    /// 将字符串中的全角字符转换为半角  
    /// </summary>  
    public static String ToBj(String str){  
      if (isBlank(str)){
    	  return str;
      }
      
      StringBuilder sb = new StringBuilder(str.length());  
      for (int i = 0; i < str.length(); i++){  
    	if (str.charAt(i) == '\u3000'){ 
          sb.append('\u0020');  
    	}else if (IsQjChar(str.charAt(i))){
          sb.append((char)((int)str.charAt(i) - 65248));  
    	}else{  
          sb.append(str.charAt(i));  
    	}
      }  
      return sb.toString();  
    } 
	
    //全角转换成半角 
    public static String QjtoBj(String input) {
    	char c[] = input.toCharArray();  
    	for (int i = 0; i < c.length; i++) {
	    	if (c[i] == '\u3000') {  
	    		c[i] = ' ';  
	    	}else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {  
	    		c[i] = (char) (c[i] - 65248);  
	    	}  
    	}
    	return new String(c);  
    }
}
