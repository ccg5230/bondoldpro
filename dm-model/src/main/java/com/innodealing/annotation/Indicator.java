package com.innodealing.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 指标类型
 * @author zhaozhenglai
 * @since 2016年9月23日 上午10:38:58 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Indicator {
    IndicatorType type();
}




