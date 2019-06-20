package com.innodealing.bond.vo.quote;

import java.util.List;

import com.innodealing.model.mongo.dm.BondSingleComparisonDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月20日
 * @clasename BondSingleComparisonVo.java
 * @decription TODO
 */
@ApiModel
public class BondSingleComparisonVo {
	
	@ApiModelProperty(value = "债券代码")
	private String bondCode;

	@ApiModelProperty(value = "债券名称")
	private String bondShortName;
	
	@ApiModelProperty(value = "6天的ofr,bid数据")
	private List<BondSingleComparisonDoc> values;

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public List<BondSingleComparisonDoc> getValues() {
		return values;
	}

	public void setValues(List<BondSingleComparisonDoc> values) {
		this.values = values;
	}

}
