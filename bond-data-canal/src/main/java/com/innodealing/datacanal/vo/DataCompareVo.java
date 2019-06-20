package com.innodealing.datacanal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据同步内容比较VO
 * 
 * @author 赵正来
 *
 */
@ApiModel("数据同步内容比较VO")
public class DataCompareVo {

	@ApiModelProperty("源表db")
	private String srcDbName;

	@ApiModelProperty("源表table")
	private String srcTableName;

	@ApiModelProperty("源表总行数")
	private long srcTotal;

	@ApiModelProperty("目标db")
	private String destDbName;

	@ApiModelProperty("目标table")
	private String destTableName;

	@ApiModelProperty("目标表总行数")
	private long destTotal;

	@ApiModelProperty("未同步的行数")
	private long missing;

	public String getSrcDbName() {
		return srcDbName;
	}

	public void setSrcDbName(String srcDbName) {
		this.srcDbName = srcDbName;
	}

	public String getSrcTableName() {
		return srcTableName;
	}

	public void setSrcTableName(String srcTableName) {
		this.srcTableName = srcTableName;
	}

	public long getSrcTotal() {
		return srcTotal;
	}

	public void setSrcTotal(long srcTotal) {
		this.srcTotal = srcTotal;
	}

	public String getDestDbName() {
		return destDbName;
	}

	public void setDestDbName(String destDbName) {
		this.destDbName = destDbName;
	}

	public String getDestTableName() {
		return destTableName;
	}

	public void setDestTableName(String destTableName) {
		this.destTableName = destTableName;
	}

	public long getDestTotal() {
		return destTotal;
	}

	public void setDestTotal(long destTotal) {
		this.destTotal = destTotal;
	}

	public long getMissing() {
		return missing;
	}

	public void setMissing(long missing) {
		this.missing = missing;
	}

	@Override
	public String toString() {
		return "DataCompareVo [srcDbName=" + srcDbName + ", srcTableName=" + srcTableName + ", srcTotal=" + srcTotal
				+ ", destDbName=" + destDbName + ", destTableName=" + destTableName + ", destTotal=" + destTotal
				+ ", missing=" + missing + "]";
	}

}