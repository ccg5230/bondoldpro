package com.innodealing.bond.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondAbnormalPriceFilterReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "价格异常类型:1-高收益债;2-估值偏离")
    private Integer type;

    @ApiModelProperty(value = "来源:1-成交价;2-买入报价Bid;3-卖出报价Ofr")
    private Integer sourceType;

    @ApiModelProperty(value = "偏离类型:1-收益率;2-净价")
    private Integer deviationType;

    @ApiModelProperty(value = "偏离方向:0-全部;1-低估;2-高估")
    private Integer deviationDirection;

    @ApiModelProperty(value = "偏离值")
    private Double deviationFilterValue;

    @ApiModelProperty(value = "净价方向:1-小于等于;2-大于等于")
    private Integer netPriceDirection;

    @ApiModelProperty(value = "净价值")
    private Double netPriceFilterValue;

    @ApiModelProperty(value = "债券值:债券简称或代码")
    private String bondFilterValue;

    @ApiModelProperty(value = "开始日期[yyyy-MM-dd]")
    private String startDateStr;

    @ApiModelProperty(value = "结束日期[yyyy-MM-dd]")
    private String endDateStr;

	@ApiModelProperty(value = "是否包含存单")
	private Boolean isContainsNCD = false;
    
    @ApiModelProperty(value = "剩余期限选项,天数")
    private Integer tenorDays;
	
    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDeviationType() {
        return deviationType;
    }

    public void setDeviationType(Integer deviationType) {
        this.deviationType = deviationType;
    }

    public Integer getDeviationDirection() {
        return deviationDirection;
    }

    public void setDeviationDirection(Integer deviationDirection) {
        this.deviationDirection = deviationDirection;
    }

    public Integer getNetPriceDirection() {
        return netPriceDirection;
    }

    public void setNetPriceDirection(Integer netPriceDirection) {
        this.netPriceDirection = netPriceDirection;
    }

    public String getBondFilterValue() {
        return bondFilterValue;
    }

    public void setBondFilterValue(String bondFilterValue) {
        this.bondFilterValue = bondFilterValue;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Double getNetPriceFilterValue() {
        return netPriceFilterValue;
    }

    public void setNetPriceFilterValue(Double netPriceFilterValue) {
        this.netPriceFilterValue = netPriceFilterValue;
    }

    public Double getDeviationFilterValue() {
        return deviationFilterValue;
    }

    public void setDeviationFilterValue(Double deviationFilterValue) {
        this.deviationFilterValue = deviationFilterValue;
    }

	public Boolean getIsContainsNCD() {
		return isContainsNCD;
	}

	public void setIsContainsNCD(Boolean isContainsNCD) {
		this.isContainsNCD = isContainsNCD;
	}

	public Integer getTenorDays() {
		return tenorDays;
	}

	public void setTenorDays(Integer tenorDays) {
		this.tenorDays = tenorDays;
	}
}
