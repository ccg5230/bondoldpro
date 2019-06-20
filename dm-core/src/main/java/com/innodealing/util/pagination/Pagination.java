package com.innodealing.util.pagination;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 分页类
 * @author zhaozhenglai
 * @since 2016年7月1日上午10:02:45 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 * @param <T>
 */
@ApiModel(description="分页类")
public class Pagination<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="总数")
	private long total;
	
	@ApiModelProperty(value="行数")
	private List<T> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Pagination() {
		super();
	}

	public Pagination(long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "Pagination [total=" + total + ", rows=" + rows + "]";
	}
	
	
	
}
