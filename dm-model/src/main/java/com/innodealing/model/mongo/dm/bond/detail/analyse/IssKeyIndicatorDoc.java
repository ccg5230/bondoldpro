package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.innodealing.model.mongo.dm.bond.finance.FinanceIndicator;

import io.swagger.annotations.ApiModelProperty;
/**
 * 重点财务指标
 * @author 赵正来
 *
 */
@Document(collection = "iss_key_ndicator")
public class IssKeyIndicatorDoc implements Serializable{
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
	
	@ApiModelProperty("主体所在省份")
    @Indexed
    private Long provinceId;
	
	@ApiModelProperty("主体大分类（bank indu insu  secu）")
    private String orgType;
	
	@ApiModelProperty(value = "是否有流通的债券")
	private Integer isActive;
	
	@ApiModelProperty(value = "各个季度")
	private List<String> quarters;
	
	List<FinanceIndicator> indicators;

	public Long getIssId() {
		return issId;
	}

	public void setIssId(Long issId) {
		this.issId = issId;
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

	public List<FinanceIndicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<FinanceIndicator> indicators) {
		this.indicators = indicators;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	
}
