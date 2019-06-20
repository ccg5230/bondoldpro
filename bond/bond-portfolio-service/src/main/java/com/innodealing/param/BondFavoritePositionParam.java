package com.innodealing.param;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class BondFavoritePositionParam {

    @ApiModelProperty(value = "持仓量")
    private Integer openinterest;

    @ApiModelProperty(value = "持仓价格")
    private BigDecimal positionPrice;

    @ApiModelProperty(value = "持仓日期")
    private Date positionDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * @return the openinterest
     */
    public Integer getOpeninterest() {
        return openinterest;
    }

    /**
     * @param openinterest the openinterest to set
     */
    public void setOpeninterest(Integer openinterest) {
        this.openinterest = openinterest;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getPositionPrice() {
        return positionPrice;
    }

    public void setPositionPrice(BigDecimal positionPrice) {
        this.positionPrice = positionPrice;
    }

    public Date getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(Date positionDate) {
        this.positionDate = positionDate;
    }
}
