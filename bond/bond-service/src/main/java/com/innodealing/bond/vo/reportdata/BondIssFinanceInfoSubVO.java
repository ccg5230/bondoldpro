package com.innodealing.bond.vo.reportdata;

import java.util.ArrayList;
import java.util.List;

import com.innodealing.bond.vo.analyse.BondFinanceInfoVo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 行业专项财务指标行业对比情况
 */
public class BondIssFinanceInfoSubVO {

	@ApiModelProperty(value = "行业专项财务指标列表")
	private List<BondFinanceInfoVo> cmpItems = new ArrayList<BondFinanceInfoVo>();

	public List<BondFinanceInfoVo> getCmpItems() {
		return cmpItems;
	}

	public void setCmpItems(List<BondFinanceInfoVo> cmpItems) {
		this.cmpItems = cmpItems;
	}
}
