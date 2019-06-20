package com.innodealing.bond.vo.trends;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.bond.vo.msg.BasicBondVo;
import com.innodealing.model.mongo.dm.BondBulletinDoc;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月29日
 * @description: 债券动态-今日动态(舆情)
 */
@JsonInclude(Include.NON_NULL)
public class TrendsBondSentiInfoVO extends BondBulletinDoc {
	private static final long serialVersionUID = -4793960425330904863L;

	@ApiModelProperty(value = "关联t_mine_field_tags的ID")
	@Transient
	private List<String> tagList = new ArrayList<>();

	@ApiModelProperty(value = "关联债券列表")
	@Transient
	private List<BasicBondVo> bondList = new ArrayList<>();
	
	@ApiModelProperty(value = "跳转地址列表")
	private List<TrendsBondSkipURLVO> skipURLList = new ArrayList<>();

	@ApiModelProperty(value = "各类别的统计")
	private Map<Integer, Long> statistics;

	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}

	public List<BasicBondVo> getBondList() {
		return bondList;
	}

	public void setBondList(List<BasicBondVo> bondList) {
		this.bondList = bondList;
	}

	public List<TrendsBondSkipURLVO> getSkipURLList() {
		return skipURLList;
	}

	public void setSkipURLList(List<TrendsBondSkipURLVO> skipURLList) {
		this.skipURLList = skipURLList;
	}

	public Map<Integer, Long> getStatistics() {
		return statistics;
	}

	public void setStatistics(Map<Integer, Long> statistics) {
		this.statistics = statistics;
	}
}
