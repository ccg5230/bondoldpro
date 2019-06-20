package com.innodealing.bond.vo.trends;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author xiaochao
 * @time 2017年4月17日
 * @description: 
 */
@JsonInclude(Include.NON_NULL)
public class TrendsBondSkipURLSubVO {
	private String name;
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
