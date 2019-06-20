package com.innodealing.annotation;
/**
 * 债券财务指标类型
 * @author zhaozhenglai
 * @since 2016年9月23日 上午10:58:19 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public enum IndicatorType{
    /**
     * 率
     */
    RATE(0),
    
    /**
     * 值
     */
    VALUE(1);
    private int type;
    IndicatorType(int val){
        type = val;
    }
    
    public int getType(){
        return type;
    }
    
    
}

