package com.innodealing.datacanal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author 元表和目标表
 *
 */
@ApiModel(description = "元表和目标表")
public class SrcToDestTableVo {

	/**
	 * 源表
	 */
	@ApiModelProperty("源表名")
	private String srcTableName;
	
	/**
	 * 目标表
	 */
	@ApiModelProperty("目标表名")
	private String destTableName;

	public String getSrcTableName() {
		return srcTableName;
	}

	public void setSrcTableName(String srcTableName) {
		this.srcTableName = srcTableName;
	}

	public String getDestTableName() {
		return destTableName;
	}

	public void setDestTableName(String destTableName) {
		this.destTableName = destTableName;
	}

	@Override
	public String toString() {
		return "SrcToDestTableVo [srcTableName=" + srcTableName + ", destTableName=" + destTableName + "]";
	}
	
	
	
}
