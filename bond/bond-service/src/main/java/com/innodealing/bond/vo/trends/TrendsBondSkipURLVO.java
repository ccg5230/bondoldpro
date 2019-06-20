package com.innodealing.bond.vo.trends;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author xiaochao
 * @time 2017年4月17日
 * @description: 
 */

@JsonInclude(Include.NON_NULL)
public class TrendsBondSkipURLVO {
	private String name;
	private List<TrendsBondSkipURLSubVO> urlList = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TrendsBondSkipURLSubVO> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<TrendsBondSkipURLSubVO> urlList) {
		this.urlList = urlList;
	}
}
