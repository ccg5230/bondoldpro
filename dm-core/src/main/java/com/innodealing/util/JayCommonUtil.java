/**
 * JayCommonUtil.java
 * com.innodealing.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年12月1日 		chungaochen
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:JayCommonUtil Function: TODO ADD FUNCTION
 *
 * @author chungaochen
 * @version 常用工具类
 * @since Ver 1.1
 * @Date 2017年12月1日 下午5:10:59
 *
 * @see
 */
public class JayCommonUtil {

    /***
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分**
     * 
     * @param list 待分割集合
     * @param len 子集合大小
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<T>> result = new ArrayList<List<T>>();

        int size = list.size();
        int count = (size + len - 1) / len;

        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
    
   

}
