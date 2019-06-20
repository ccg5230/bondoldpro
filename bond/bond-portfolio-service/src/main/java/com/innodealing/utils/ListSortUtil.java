package com.innodealing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ListSortUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(ListSortUtil.class);

    public static void sort(List targetList, final String sortField, final String sortMode, Class clazz) {
        try {
            final String fieldType = clazz.getDeclaredField(sortField).getType().getSimpleName();
            Collections.sort(targetList, new Comparator() {
                public int compare(Object obj1, Object obj2) {
                    double retVal = 0;
                    try {
                        String methodName = "get" + sortField.substring(0, 1).toUpperCase() + sortField.substring(1);

                        Method method1 = (obj1).getClass().getMethod(methodName, null);
                        Method method2 = (obj2).getClass().getMethod(methodName, null);
                        Object o1 = method1.invoke((obj1), null);
                        Object o2 = method2.invoke((obj2), null);

                        if (o1 == null && o2 == null) {
                            return 0;
                        }
                        if (o1 == null) {
                            return 1;
                        }
                        if (o2 == null) {
                            return -1;
                        }
                        boolean isDesc = sortMode != null && "desc".equals(sortMode);
                        if ("String".equals(fieldType)) {
                            retVal = isDesc ? ((String) o2).compareTo((String) o1) : ((String) o1).compareTo((String) o2);
                        } else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
                            retVal = isDesc ? (Integer) o2 - (Integer) o1 : (Integer) o1 - (Integer) o2;
                        } else if ("Double".equals(fieldType) || "double".equals(fieldType)) {
                            retVal = isDesc ? (Double) o2 - (Double) o1 : (Double) o1 - (Double) o2;
                        } else if ("Float".equals(fieldType) || "float".equals(fieldType)) {
                            retVal = isDesc ? (Float) o2 - (Float) o1 : (Float) o1 - (Float) o2;
                        } else if ("BigDecimal".equals(fieldType)) {
                            retVal = isDesc ? ((BigDecimal) o2).compareTo((BigDecimal) o1) : ((BigDecimal) o1).compareTo((BigDecimal) o2);
                        } else if ("Date".equals(fieldType)) {
                            retVal = isDesc ? ((Date) o2).getTime() - ((Date) o1).getTime() : ((Date) o1).getTime() - ((Date) o2).getTime();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }

                    // 确定返回值
                    if (retVal > 0) {
                        return 1;
                    } else if (retVal < 0) {
                        return -1;
                    }
                    return 0;
                }
            });
        } catch (Exception ex) {
            LOGGER.error("ListSortUtil sort error: " + ex.getMessage());
        }
    }
}
