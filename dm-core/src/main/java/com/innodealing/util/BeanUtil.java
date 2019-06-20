package com.innodealing.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 
 * @author 赵正来
 * @since 2016年7月25日下午6:34:36 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 */
public class BeanUtil extends BeanUtils {
	private static final Log logger = LogFactory.getLog(BeanUtil.class);

	/**
	 * BeanUtils.copyProperties之后，再将source父类中的属性copy到target中
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void deepCopyProperties(Object source, Object target) {
		copyProperties(source, target);
		// 复制父类的属性
		Field[] superFields = source.getClass().getSuperclass().getDeclaredFields();
		try {
			for (Field field : superFields) {
				String varName = field.getName();
				Class superClazz = source.getClass().getSuperclass();
				PropertyDescriptor sourcePd = getPropertyDescriptor(superClazz, varName);
				PropertyDescriptor targetPd = getPropertyDescriptor(superClazz, varName);
				if (sourcePd != null && targetPd != null) {
					Method readMethod = sourcePd.getReadMethod();
					Method writeMethod = targetPd.getWriteMethod();
					if (readMethod != null && writeMethod != null) {
						Object superValue = readMethod.invoke(source);
						if (superValue != null) {
							writeMethod.invoke(target, superValue);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("deepCopyProperties error [" + ex.getMessage() + "]");
		}
	}
	 
	/**
	 * bean 转map
	 * @param object
	 * @return
	 */
	public static  Map<String,Object> bean2Map(Object object){
		if(object==null){
			return null;
		}
		Class<? extends Object> clazz = object.getClass();
		Method[] methods= clazz.getDeclaredMethods();
		Map<String,Object> map = new HashMap<String, Object>();
		for (Method method : methods) {
			if(method.getName().contains("get")){
				try {
					String key = method.getName().replace("get", "").toLowerCase();
					Object value = method.invoke(object);
					map.put(key, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	
	/**
	 * map转bean
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T map2Bean(Map<String,Object> map,Class<T> clazz){
		if(map==null){
			return null;
		}
		T obj = null;
		try {
			obj = clazz.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				  String key = property.getName();  
				  if (map.containsKey(key)) {  
	                    Object value = map.get(key);  
	                    // 得到property对应的setter方法  
	                    Method setter = property.getWriteMethod();  
	                    setter.invoke(obj, value);  
	                }  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * list bean 转list map 
	 * @param list
	 * @return
	 */
	public static List<Map<String,Object>> bean2ListMap(List<Object> list){
		if(list==null){
			return null;
		}
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for (Object object : list) {
			result.add(bean2Map(object));
		}
		return result;
	}
	
	/**
	 * list map  转 list bean
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> map2ListBean(List<Map<String,Object>> list,Class<T> clazz){
		if(list==null){
			return null;
		}
		List<T> result = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			result.add(map2Bean(map, clazz));
		}
		return result;
	}
	
	/*该方法用于传入某实例对象以及对象方法名，通过反射调用该对象的某个get方法*/
    public static Object getProperty(Object beanObj, String property) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        //此处应该判断beanObj,property不为null
        PropertyDescriptor pd = new PropertyDescriptor(property, beanObj.getClass());
        Method getMethod = pd.getReadMethod();
        if(getMethod == null){

        }
        return getMethod.invoke(beanObj);
    }
    /*该方法用于传入某实例对象以及对象方法名、修改值，通过放射调用该对象的某个set方法设置修改值*/
    public static Object setProperty(Object beanObj, String property, Object value) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        //此处应该判断beanObj,property不为null
        PropertyDescriptor pd = new PropertyDescriptor(property, beanObj.getClass());
        Method setMethod = pd.getWriteMethod();
        if(setMethod == null){

        }
        return setMethod.invoke(beanObj, value);
    }
    
	public static <T> void setProperty2(T bean, String varName, T object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, NoSuchMethodException, SecurityException {
		
		//此处应该判断beanObj,property不为null
        PropertyDescriptor pd = new PropertyDescriptor(varName, bean.getClass());
        Method setMethod = pd.getWriteMethod();
        if(setMethod == null){

        }
        String type = setMethod.getParameterTypes()[0].toString().replace(" ", "");
		if ("classjava.math.BigDecimal".equals(type)) {
			setMethod.invoke(bean, new BigDecimal(object.toString()));
		}else if ("classjava.math.BigInteger".equals(type)) {
			setMethod.invoke(bean, new BigInteger(object.toString()));
		}else{
			setMethod.invoke(bean, object);
		}
       
	}
	
	/**
	 * DBObject转换成bean对象
	 * 
	 * @param dbObject
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static <T> T dbObject2Bean(DBObject dbObject, T bean)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException,
			NoSuchMethodException, SecurityException {
		if (bean == null) {
			return null;
		}
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			String varName = field.getName();
			Object object = dbObject.get(varName);
			if (object != null) {
				setProperty2(bean, varName, object);
			}
		}
		Field[] superFields = bean.getClass().getSuperclass().getDeclaredFields();
		for (Field field : superFields) {
			String varName = field.getName();
			Object object = dbObject.get(varName);
			if (object != null) {
				setProperty2(bean, varName, object);
			}
		}
		return bean;
	}
	
	/** 
	   * 把实体bean对象转换成DBObject 
	   * @param bean 
	   * @return 
	   * @throws IllegalArgumentException 
	   * @throws IllegalAccessException 
	   */  
	  public static <T> DBObject bean2DBObject(T bean) throws IllegalArgumentException,  
	      IllegalAccessException {  
	    if (bean == null) {  
	      return null;  
	    }  
	    DBObject dbObject = new BasicDBObject();  
	    // 获取对象对应类中的所有属性域  
	    Field[] fields = bean.getClass().getDeclaredFields();  
	    for (Field field : fields) {  
	      // 获取属性名  
	      String varName = field.getName();  
	      // 修改访问控制权限  
	      boolean accessFlag = field.isAccessible();  
	      if (!accessFlag) {  
	        field.setAccessible(true);  
	      }  
	      Object param = field.get(bean);  
	      if (param == null) {  
	        continue;  
	      } else if (param instanceof Integer) {//判断变量的类型  
	        int value = ((Integer) param).intValue();  
	        dbObject.put(varName, value);  
	      } else if (param instanceof String) {  
	        String value = (String) param;  
	        dbObject.put(varName, value);  
	      } else if (param instanceof Double) {  
	        double value = ((Double) param).doubleValue();  
	        dbObject.put(varName, value);  
	      } else if (param instanceof Float) {  
	        float value = ((Float) param).floatValue();  
	        dbObject.put(varName, value);  
	      } else if (param instanceof Long) {  
	        long value = ((Long) param).longValue();  
	        dbObject.put(varName, value);  
	      } else if (param instanceof Boolean) {  
	        boolean value = ((Boolean) param).booleanValue();  
	        dbObject.put(varName, value);  
	      } else if (param instanceof Date) {  
	        Date value = (Date) param;  
	        dbObject.put(varName, value);  
	      }  
	      // 恢复访问控制权限  
	      field.setAccessible(accessFlag);  
	    }  
	    return dbObject;  
	  }  
	
	/*public static void main(String[] args) {
		User user = new User();
		user.setId(1L);
		user.setName("张三丰");
		System.out.println(bean2Map(user));
		Map<String,Object> m = new HashMap<>();
		m.put("id", 1L);
		m.put("name", "123");
		m.put("age", 34);
		System.out.println(map2Bean(m, User.class));
	}*/
}
