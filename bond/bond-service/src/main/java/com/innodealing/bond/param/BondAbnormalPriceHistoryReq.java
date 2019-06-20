package com.innodealing.bond.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondAbnormalPriceHistoryReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "债券id")
    private Long bondUniCode;

    @ApiModelProperty(value = "价格异常类型:1-高收益债;2-估值偏离")
    private Integer type;

//    @ApiModelProperty(value = "开始日期[yyyy-MM-dd]")
//    private String startDateStr;
//
//    @ApiModelProperty(value = "结束日期[yyyy-MM-dd]")
//    private String endDateStr;

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

//    public String getStartDateStr() {
//        return startDateStr;
//    }
//
//    public void setStartDateStr(String startDateStr) {
//        this.startDateStr = startDateStr;
//    }
//
//    public String getEndDateStr() {
//        return endDateStr;
//    }
//
//    public void setEndDateStr(String endDateStr) {
//        this.endDateStr = endDateStr;
//    }

    public Long getBondUniCode() {
        return bondUniCode;
    }

    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }
}
