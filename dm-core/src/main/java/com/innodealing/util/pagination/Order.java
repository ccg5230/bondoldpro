package com.innodealing.util.pagination;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zhaozhenglai
 * @since 2016年7月1日上午10:02:45 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 */
@ApiModel(description="排序工具类")
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value="需要排序的列名")
	private String orderby;
	
	
	@ApiModelProperty(value="排序的方式(ASC、DESC))")
	private String value;

	public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Order(String orderby, String value) {
		super();
		this.orderby = orderby;
		this.value = value;
	}

	public Order() {
		super();
	}
	
}
