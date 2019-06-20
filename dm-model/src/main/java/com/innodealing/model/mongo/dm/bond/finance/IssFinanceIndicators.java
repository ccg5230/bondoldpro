package com.innodealing.model.mongo.dm.bond.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * 发行人指标信息
 * @author zhaozhenglai
 * @date 2017年2月17日下午3:31:13
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection = "iss_finance_indicators")
public class IssFinanceIndicators implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Indexed
	private Long issId;
	
	@ApiModelProperty(value = "发行人名称")
	private String issName;
	@ApiModelProperty(value = "GICS行业id")
	@Indexed
	private Long induId;

	@ApiModelProperty(value = "申万行业id")
	@Indexed
	private Long induIdSw;
	
	@ApiModelProperty(value = "各个季度")
	private List<String> quarters;
	
	List<FinanceIndicator> indicators;

	public Long getIssId() {
		return issId;
	}

	public void setIssId(Long issId) {
		this.issId = issId;
	}

	public List<FinanceIndicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<FinanceIndicator> indicators) {
		this.indicators = indicators;
	}

	public String getIssName() {
		return issName;
	}

	public void setIssName(String issName) {
		this.issName = issName;
	}

	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public List<String> getQuarters() {
		return quarters;
	}

	public void setQuarters(List<String> quarters) {
		this.quarters = quarters;
	}
	
	
	
}
