/**
 * SortComparator.java
 * com.innodealing.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年12月11日 		chungaochen
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Comparator;

/**
 * ClassName:SortComparator
 * Function: 排序处理类，空值 null 放最后
 *
 * @author   chungaochen
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年12月11日		上午10:11:23
 *
 * @see 	 
 */
public class SortComparator<T> implements Comparator<T>{
    
    @SuppressWarnings("unused")
    private SortComparator() {
        super();
    }
    
    public SortComparator(String sortColumnName, String sortType) {
        super();
        this.sortColumnName = sortColumnName;
        this.sortType = sortType;
    }

    private String sortColumnName;
    
    private String sortType;

    public String getSortColumnName() {
        return sortColumnName;
    }

    public void setSortColumnName(String sortColumnName) {
        this.sortColumnName = sortColumnName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public int compare(Object o1, Object o2) {
        int rs =0;
        try {
            if(!(BeanUtil.getProperty(o1, sortColumnName) == null && BeanUtil.getProperty(o2, sortColumnName) == null)) {
                if((BeanUtil.getProperty(o1, sortColumnName) instanceof Integer ||
                        BeanUtil.getProperty(o1, sortColumnName) instanceof Float ||
                        BeanUtil.getProperty(o1, sortColumnName) instanceof Double ||
                        BeanUtil.getProperty(o1, sortColumnName) instanceof BigDecimal) ||
                    (BeanUtil.getProperty(o2, sortColumnName) instanceof Integer ||
                        BeanUtil.getProperty(o2, sortColumnName) instanceof Float ||
                        BeanUtil.getProperty(o2, sortColumnName) instanceof Double ||
                        BeanUtil.getProperty(o2, sortColumnName) instanceof BigDecimal)) {
                    BigDecimal b1 = null;
                    BigDecimal b2 = null;
                    if (BeanUtil.getProperty(o1, sortColumnName) != null) {
                        b1 = new BigDecimal(BeanUtil.getProperty(o1, sortColumnName).toString());
                    }
                    if (BeanUtil.getProperty(o2, sortColumnName) != null) {
                        b2 =  new BigDecimal(BeanUtil.getProperty(o2, sortColumnName).toString());
                    }
                    if(null==b1 && null==b2) {
                        rs= 0;
                    }else if("ASC".equalsIgnoreCase(sortType)) {
                        if(null==b1 && null!=b2) {//b1>b2
                            rs= 1;
                        } else if(null!=b1 && null==b2) {//b1<b2
                            rs= -1;
                        } else {
                            rs= b1.compareTo(b2);
                        }
                    } else if("DESC".equalsIgnoreCase(sortType)){
                        if(null==b1 && null!=b2) {//b2>b1
                            rs= 1;
                        } else if(null!=b1 && null==b2) {//b2<b1
                            rs= -1;
                        } else {
                            rs= b2.compareTo(b1);
                        }
                    }
                    
                }else if(BeanUtil.getProperty(o1, sortColumnName) instanceof Date || BeanUtil.getProperty(o2, sortColumnName) instanceof Date) {
                    Date d1 = null;
                    Date d2 = null;
                    if (BeanUtil.getProperty(o1, sortColumnName) != null) {
                        d1 = (Date) BeanUtil.getProperty(o1, sortColumnName);
                    }
                    if (BeanUtil.getProperty(o2, sortColumnName) != null) {
                        d2 = (Date) BeanUtil.getProperty(o2, sortColumnName);
                    }
                    if(null==d1 && null==d2) {
                        rs= 0;
                    } else if("ASC".equalsIgnoreCase(sortType)) {
                        if(null==d1 && null!=d2) {
                            rs= 1;
                        } else if(null!=d1 && null==d2) {
                            rs= -1;
                        } else {
                            rs= d1.compareTo(d2);
                        }
                    } else if("DESC".equalsIgnoreCase(sortType)){
                        if(null==d1 && null!=d2) {
                            rs= 1;
                        } else if(null!=d1 && null==d2) {
                            rs= -1;
                        } else {
                            rs= d2.compareTo(d1);
                        }
                    }
                } 
            } else {
                rs= 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    
}

