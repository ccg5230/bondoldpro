package com.innodealing.model.dm.bond;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class BondAbnormalPriceHistoryVO {
    @ApiModelProperty(value = "债券简称")
    private String shortName;

    @ApiModelProperty(value = "债券代码")
    private String code;

    @ApiModelProperty(value = "剩余期限")
    private String tenor;

    @ApiModelProperty(value = "债项评级")
    private String bondRating;

    @ApiModelProperty(value = "主体评级")
    private String issRating;

    @ApiModelProperty(value = "日期群组")
    private String[] dateArr;

    @ApiModelProperty(value = "估值净价群组")
    private Double[] valuationNetPriceArr;

    @ApiModelProperty(value = "成交价净价群组")
    private Double[] dealNetPriceArr;

    @ApiModelProperty(value = "买入报价Bid净价群组")
    private Double[] bidNetPriceArr;

    @ApiModelProperty(value = "卖出报价Ofr净价群组")
    private Double[] ofrNetPriceArr;

    @ApiModelProperty(value = "估值收益率群组")
    private Double[] valuationYieldArr;

    @ApiModelProperty(value = "成交价收益率群组")
    private Double[] dealPriceYieldArr;

    @ApiModelProperty(value = "买入报价Bid收益率群组")
    private Double[] bidPriceYieldArr;

    @ApiModelProperty(value = "卖出报价Ofr收益率群组")
    private Double[] ofrPriceYieldArr;

    @ApiModelProperty(value = "day对应的成交价总数组")
    private Integer[] dealCountArr;

    @ApiModelProperty(value = "day对应的买入报价总数组")
    private Integer[] bidCountArr;

    @ApiModelProperty(value = "day对应的卖出报价总数组")
    private Integer[] ofrCountArr;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public String getBondRating() {
        return bondRating;
    }

    public void setBondRating(String bondRating) {
        this.bondRating = bondRating;
    }

    public String getIssRating() {
        return issRating;
    }

    public void setIssRating(String issRating) {
        this.issRating = issRating;
    }

    public String[] getDateArr() {
        return dateArr;
    }

    public void setDateArr(String[] dateArr) {
        this.dateArr = dateArr;
    }

    public Double[] getValuationNetPriceArr() {
        return valuationNetPriceArr;
    }

    public void setValuationNetPriceArr(Double[] valuationNetPriceArr) {
        this.valuationNetPriceArr = valuationNetPriceArr;
    }

    public Double[] getDealNetPriceArr() {
        return dealNetPriceArr;
    }

    public void setDealNetPriceArr(Double[] dealNetPriceArr) {
        this.dealNetPriceArr = dealNetPriceArr;
    }

    public Double[] getBidNetPriceArr() {
        return bidNetPriceArr;
    }

    public void setBidNetPriceArr(Double[] bidNetPriceArr) {
        this.bidNetPriceArr = bidNetPriceArr;
    }

    public Double[] getOfrNetPriceArr() {
        return ofrNetPriceArr;
    }

    public void setOfrNetPriceArr(Double[] ofrNetPriceArr) {
        this.ofrNetPriceArr = ofrNetPriceArr;
    }

    public Double[] getValuationYieldArr() {
        return valuationYieldArr;
    }

    public void setValuationYieldArr(Double[] valuationYieldArr) {
        this.valuationYieldArr = valuationYieldArr;
    }

    public Double[] getDealPriceYieldArr() {
        return dealPriceYieldArr;
    }

    public void setDealPriceYieldArr(Double[] dealPriceYieldArr) {
        this.dealPriceYieldArr = dealPriceYieldArr;
    }

    public Double[] getBidPriceYieldArr() {
        return bidPriceYieldArr;
    }

    public void setBidPriceYieldArr(Double[] bidPriceYieldArr) {
        this.bidPriceYieldArr = bidPriceYieldArr;
    }

    public Double[] getOfrPriceYieldArr() {
        return ofrPriceYieldArr;
    }

    public void setOfrPriceYieldArr(Double[] ofrPriceYieldArr) {
        this.ofrPriceYieldArr = ofrPriceYieldArr;
    }

    public Integer[] getDealCountArr() {
        return dealCountArr;
    }

    public void setDealCountArr(Integer[] dealCountArr) {
        this.dealCountArr = dealCountArr;
    }

    public Integer[] getBidCountArr() {
        return bidCountArr;
    }

    public void setBidCountArr(Integer[] bidCountArr) {
        this.bidCountArr = bidCountArr;
    }

    public Integer[] getOfrCountArr() {
        return ofrCountArr;
    }

    public void setOfrCountArr(Integer[] ofrCountArr) {
        this.ofrCountArr = ofrCountArr;
    }
}
