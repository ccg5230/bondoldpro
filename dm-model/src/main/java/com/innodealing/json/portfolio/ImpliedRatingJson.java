package com.innodealing.json.portfolio;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
//隐含评级
public class ImpliedRatingJson implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "债券id")
    private Long bondUniCode;
	
	@ApiModelProperty(value = "债券名称")
    private String bondName;
	
	@ApiModelProperty(value = "原先的隐含评级")
	private String fstImpliedRat;
	
	@ApiModelProperty(value = "目前的隐含评级")
	private String secImpliedRat;
	
	@ApiModelProperty(value = "评级差值")
	private Integer rateDiff;

	@ApiModelProperty(value = "债券主体")
	private Long issuerId;

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public String getFstImpliedRat() {
		return fstImpliedRat;
	}

	public void setFstImpliedRat(String fstImpliedRat) {
		this.fstImpliedRat = fstImpliedRat;
	}

	public String getSecImpliedRat() {
		return secImpliedRat;
	}

	public void setSecImpliedRat(String secImpliedRat) {
		this.secImpliedRat = secImpliedRat;
	}

	public Integer getRateDiff() {
		return rateDiff;
	}

	public void setRateDiff(Integer rateDiff) {
		this.rateDiff = rateDiff;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public ImpliedRatingJson() {
	}
}
