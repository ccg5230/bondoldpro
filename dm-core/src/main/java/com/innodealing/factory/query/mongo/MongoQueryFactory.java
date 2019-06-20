package com.innodealing.factory.query.mongo;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.innodealing.annotation.QueryField;
import com.innodealing.util.SafeUtils;

/**
 * mongo query 工厂
 * @author zhaozhenglai
 * @date 2017年2月10日下午4:31:20
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class MongoQueryFactory {

    private static MongoQueryFactory mongoQueryFactory = new MongoQueryFactory();
    
    public static MongoQueryFactory newInstance(){
        if(mongoQueryFactory == null)
            mongoQueryFactory = new MongoQueryFactory();
        return mongoQueryFactory;
    }
    
    private MongoQueryFactory() {

    }

    /**
     * 获取query
     * @param t
     * @param page
     * @param size
     * @return
     */
    public <T> Query getQuery(T t, Integer page, Integer size) {
        Query query = new Query();
        // 分页处理
        if (page != null)
            query.skip((page - 1) * page);
        if(size != null){
        	query.limit(size);
        }
        // 参数处理
        return buildQuery(new Query(), t);
    }

    
    /**
     * 获取query
     * @param t
     */
    public <T> Query getQuery(T t) {
        return buildQuery(new Query(), t);
    }

	private <T> Query buildQuery(Query query,T t) {
        // 参数处理
        BeanInfo beanInfo = null;
        Field[] fields = t.getClass().getDeclaredFields();
        Map<String, Field> fieldsMap = new HashMap<String, Field>();
        for (Field field : fields) {
            fieldsMap.put(field.getName(), field);
        }
        try {
            beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String propertyName = property.getName();
                if (!"class".equals(propertyName)) {
                    Field field = fieldsMap.get(propertyName);
                    QueryField queryField = field.getDeclaredAnnotation(QueryField.class);
                    Method readMethod = property.getReadMethod();
                    Object value = readMethod.invoke(t);
                    buildQuery(query, queryField, field.getName(), value);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return query;
	}
    
    public Query queryById(String id){
        return new Query(Criteria.where("_id").is(id));
    }
    /**
     * 构建query
     * 
     * @param query
     * @param queryField
     * @param field
     * @param value
     */
    private void buildQuery(Query query, QueryField queryField, String field, Object value) {
        if(queryField == null || value == null) return;
        String option = queryField.option();
        field = queryField.columnName() == null ? field : queryField.columnName();
        if ("=".equals(option))
            query.addCriteria(Criteria.where(field).is(value));
        if ("in".equals(option))
        	query.addCriteria(Criteria.where(field).in(toList(value)));
        if (">".equals(option))
            query.addCriteria(Criteria.where(field).gt(value));
        if ("<".equals(option))
            query.addCriteria(Criteria.where(field).lt(value));
        if (">=".equals(option))
            query.addCriteria(Criteria.where(field).gte(value));
        if ("<=".equals(option))
            query.addCriteria(Criteria.where(field).lte(value));
        if ("like".equals(option))
            query.addCriteria(Criteria.where(field).regex(SafeUtils.getString(value)));
    }
    
    /**
     * 将未知value  转为集合
     * @param value
     * @return
     */
    private Collection<Object> toList(Object value){
    	if(value == null) return null;
    	if(value.getClass().isArray())
    		return Arrays.asList(value);
    	if (value instanceof ArrayList) 
    		return (ArrayList<Object>)value;
    	if (value instanceof LinkedList) 
    		return (LinkedList<Object>)value;
    	if (value instanceof HashSet) 
    		return (HashSet<Object>)value;
    	if (value instanceof LinkedHashSet) 
    		return (LinkedHashSet<Object>)value;
    	return null;
    }

}
