package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * 单个指标
 * 
 * @author zhaozhenglai
 * @since 2016年9月8日 上午11:57:23 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
@JsonInclude(Include.NON_NULL)
public class FinanceInfoIndicator implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "指标名称")
    private String indicator;

    @ApiModelProperty(value = "公司数值")
    private BigDecimal issVal;

    @ApiModelProperty(value = "行业|地区水平")
    private BigDecimal induVal;

    @ApiModelProperty(value = "是否为负向指标(1是，0不是)")
    private int isNegative;

    @ApiModelProperty(value = "是否优行业|地区水平(1优于，0低于)")
    private int isUpInduVal;

    @ApiModelProperty(value = "较上期指标相比(-1下降，0不变，1上升)")
    private int toOrevious;

    @ApiModelProperty(value = "公司财务指标在行业所处位置")
    private BigDecimal induPos;

    @ApiModelProperty(value = "所属种类")
    private String category;

    @ApiModelProperty(value = "主体指标所在的分位值【1-100】")
    private Integer quantile;

    @ApiModelProperty(value = "指标类型")
    private int type;
    
    @ApiModelProperty("指标是否为百分比的小数，1是、0否")
    private int percent;
    
    @ApiModelProperty(value = "各分位行业|地区数值[min - max]")
    private List<BigDecimal> induValVes;
    
    @ApiModelProperty("指标计算表达式描述")
    private String expressDescription;

    public String getIndicator() {
        return indicator;
    }

    public BigDecimal getIssVal() {
        return  issVal;
    }

    public BigDecimal getInduVal() {
        return induVal;
    }

    public BigDecimal getInduPos() {
        return induPos;
    }

    public List<BigDecimal> getInduValVes() {
        return induValVes;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public void setIssVal(BigDecimal issVal) {
        this.issVal = issVal;
    }

    public void setInduVal(BigDecimal induVal) {
        this.induVal = induVal;
    }

    public void setInduPos(BigDecimal induPos) {
        this.induPos = induPos;
    }

    public void setInduValVes(List<BigDecimal> induValVes) {
        this.induValVes = induValVes;
    }

    public int getIsNegative() {
        return isNegative;
    }

    public int getIsUpInduVal() {
        return isUpInduVal;
    }

    public int getToOrevious() {
        if(isNegative == 1 && toOrevious != 0){
            toOrevious = -toOrevious;
        }
        return toOrevious;
    }

    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }

    public void setIsUpInduVal(int isUpInduVal) {
        this.isUpInduVal = isUpInduVal;
    }

    public void setToOrevious(int toOrevious) {
        this.toOrevious = toOrevious;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    public Integer getQuantile() {
        return quantile;
    }

    public void setQuantile(Integer quantile) {
        this.quantile = quantile;
    }

    
    
    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getExpressDescription() {
        return expressDescription;
    }

    public void setExpressDescription(String expressDescription) {
        this.expressDescription = expressDescription;
    }

    public FinanceInfoIndicator(String indicator, BigDecimal issVal, BigDecimal induVal, int isNegative,
            int toOrevious, BigDecimal induPos, List<BigDecimal> induValVes, String category, int type, Integer quantile, int percent,
            String expressDescription) {
        super();
        this.indicator = indicator;
        this.issVal = issVal;
        this.induVal = induVal;
        this.isNegative = isNegative;
        this.toOrevious = toOrevious;
        this.induPos = induPos;
        this.induValVes = induValVes;
        this.category = category;
        this.type = type;
        this.quantile = quantile;
        this.percent =percent;
        this.expressDescription = expressDescription;
    }

    public FinanceInfoIndicator() {
        super();
    }

   

    public enum IsNegativeEnum {
        NO(0, "不是"), YES(1, "是");
        private final int b;
        private final String msg;

        private IsNegativeEnum(int _b, String _msg) {
            this.b = _b;
            this.msg = _msg;
        }

        public int getCode() {
            return this.b;
        }

        public String getMsg() {
            return this.msg;
        }
    }

    public enum ToOreviousEnum {
        DOWN(1, "下降"), FLAT(0, "持平"), UP(1, "上升");
        private final int b;
        private final String msg;

        private ToOreviousEnum(int _b, String _msg) {
            this.b = _b;
            this.msg = _msg;
        }

        public int getCode() {
            return this.b;
        }

        public String getMsg() {
            return this.msg;
        }
    }

    public enum IsUpInduValEnum {
        NO(0, "不是"), YES(1, "是");
        private final int b;
        private final String msg;

        private IsUpInduValEnum(int _b, String _msg) {
            this.b = _b;
            this.msg = _msg;
        }

        public int getCode() {
            return this.b;
        }

        public String getMsg() {
            return this.msg;
        }
    }
    
    

}