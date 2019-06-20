package com.innodealing.bond.vo.summary;

import java.util.ArrayList;
import java.util.List;

import com.innodealing.model.mongo.dm.BondDetailDoc;

/**
 * @author xiaochao
 * @time 2017年11月21日
 * @description: 
 */
public class BondDetailSummary {
	private String summary;
	
	private List<BondDetailDoc> onlineBondList = new ArrayList<>();

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<BondDetailDoc> getOnlineBondList() {
		return onlineBondList;
	}

	public void setOnlineBondList(List<BondDetailDoc> onlineBondList) {
		this.onlineBondList = onlineBondList;
	}
}
