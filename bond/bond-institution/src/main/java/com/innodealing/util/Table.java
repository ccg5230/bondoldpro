package com.innodealing.util;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface Table {
	 /**
     * (Optional) The name of the column. Defaults to
     * the property or field name.
     */
    String name() default "";

}
