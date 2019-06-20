package com.innodealing.model.mysql;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;

public class BaseModel {
	@ApiModelProperty(value = "创建时间")
	@Column(name="create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@ApiModelProperty(value = "创建人")
	@Column(name="create_by")
	private Integer createBy;
	
	@ApiModelProperty(value = "修改时间")
	@Column(name="update_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	@ApiModelProperty(value = "修改人")
	@Column(name="update_by")
	private Integer updateBy;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	
	
	
}
