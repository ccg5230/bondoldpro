package com.innodealing.model.dm.bond;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.*;

@JsonInclude(Include.NON_NULL)
public class BondTodayItem {
    @ApiModelProperty(value = "发现类型:1-成交;2-报价")
    private Integer bondType;

    @ApiModelProperty(value = "最小成交价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "最大成交价")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "成交笔数")
    private Long count = 0L;

    @ApiModelProperty(value = "债劵名称")
    private String shortName;

    @ApiModelProperty(value = "报价价格")
    private BigDecimal quotePrice;

    @ApiModelProperty(value = "最新成交时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value="状态")
    private Boolean isRead = true;

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getBondType() {
        return bondType;
    }

    public void setBondType(Integer bondType) {
        this.bondType = bondType;
    }

    public BigDecimal getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(BigDecimal quotePrice) {
        this.quotePrice = quotePrice;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
