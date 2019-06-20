package com.innodealing.util.pagination;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.innodealing.annotation.QueryField;
import com.innodealing.util.SafeUtils;
/**
 * 查询helper
 * @author zhaozhenglai
 * @since 2016年7月1日上午10:02:45 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 */
public class QueryHelper {
    
    /**
     * 获得Query {@link #Query}
     * 
     * @param queryBean
     * @return
     * @throws Exception
     */
    public static Query getQuery(Object queryBean) throws Exception {
        Field[] fields = queryBean.getClass().getDeclaredFields();

        Map<String, Field> fieldsMap = new HashMap<String, Field>();
        for (Field field : fields) {
            fieldsMap.put(field.getName(), field);
        }
        // 创建一个Query
        Query query = new Query();
        List<Filter> filters = new ArrayList<Filter>();
        List<Order> orders = new ArrayList<Order>();
        BeanInfo beanInfo = Introspector.getBeanInfo(queryBean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String propertyName = property.getName();
            if (!"class".equals(propertyName)) {
                Field field = fieldsMap.get(propertyName);
                QueryField queryField = field.getDeclaredAnnotation(QueryField.class);
                Method readMethod = property.getReadMethod();
                Object value = readMethod.invoke(queryBean);
                if (queryField.isSort()) {
                    Order order = new Order(queryField.tableName() + "." + queryField.columnName(), queryField.sort());
                    orders.add(order);
                }
                if (value == null) {
                    continue;
                }
                // 添加过滤条件和排序条件
                Filter filter = new Filter(queryField.tableName() + "." + queryField.columnName(),
                        SafeUtils.getString(value), SafeUtils.getString(queryField.joinType()), queryField.option());
                filters.add(filter);
            }
        }
        query.setFilters(filters);
        query.setOrders(orders);
        return query;
    
    }
}
