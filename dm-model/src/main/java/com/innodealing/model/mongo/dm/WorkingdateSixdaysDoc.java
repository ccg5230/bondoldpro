package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月20日
 * @clasename WorkingdateSixdays.java
 * @decription TODO
 */
@JsonInclude(value=Include.NON_NULL)
@Document(collection="workingdate_sixdays")
public class WorkingdateSixdaysDoc implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name="t_is_holiday中的ID")
	private Integer tableId;
	
	@ApiModelProperty(name="工作日日期")
	private String date;
	
	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
