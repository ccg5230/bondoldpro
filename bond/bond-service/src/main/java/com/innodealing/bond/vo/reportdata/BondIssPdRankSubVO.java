package com.innodealing.bond.vo.reportdata;

import java.util.ArrayList;
import java.util.List;

import com.innodealing.bond.vo.finance.IssuerSortVo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 同行业信用等级排名
 */
public class BondIssPdRankSubVO {

	@ApiModelProperty(value = "同行业信用等级排名TOP10")
	private List<IssuerSortVo> topTen = new ArrayList<IssuerSortVo>();

	@ApiModelProperty(value = "同行业信用等级排名NEAR5")
	private List<IssuerSortVo> neighborFive = new ArrayList<IssuerSortVo>();

	public List<IssuerSortVo> getTopTen() {
		return topTen;
	}

	public void setTopTen(List<IssuerSortVo> topTen) {
		this.topTen = topTen;
	}

	public List<IssuerSortVo> getNeighborFive() {
		return neighborFive;
	}

	public void setNeighborFive(List<IssuerSortVo> neighborFive) {
		this.neighborFive = neighborFive;
	}
}
