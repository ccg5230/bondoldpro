package com.innodealing.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 字段 别称
 * 
 * @author 戴永杰
 *
 * @date 2017年10月12日 下午2:39:03 
 * @version V1.0   
 *
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface Column {
	 /**
     * (Optional) The name of the column. Defaults to
     * the property or field name.
     */
    String name() default "";

}
