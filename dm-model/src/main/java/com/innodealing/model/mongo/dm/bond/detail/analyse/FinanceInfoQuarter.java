package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * 近2期重点财务指标
 * @author zhaozhenglai
 * @since 2016年9月8日 下午3:51:09 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@JsonInclude(Include.NON_NULL)
public class FinanceInfoQuarter implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "指标名称")
    private String indicator;
    
    @ApiModelProperty(value = "指标code")
    private String code;
    
    @ApiModelProperty(value = "本季度数分位值")
    private BigDecimal currentVal;

    @ApiModelProperty(value = "上季度数分位值")
    private BigDecimal previousVal;
    
    @ApiModelProperty(value = "本季度数")
    private String current;

    @ApiModelProperty(value = "上季度数")
    private String previous;
    
    @ApiModelProperty(value = "指标类型")
    private int type;
    
    @ApiModelProperty(value = "本季度数数值")
    private BigDecimal currentNV;

    @ApiModelProperty(value = "上季度数数值")
    private BigDecimal previousNV;
    
    @ApiModelProperty(value = "是否为负向指标【1是，0不是】")
    private int isNegative;
    
    @ApiModelProperty(value = "本季度数行业分位值")
    private BigDecimal currentInduVal;
    
    @ApiModelProperty("指标是否为百分比的小数，1是、0否")
    private int percent;

    public String getIndicator() {
        return indicator;
    }

    public BigDecimal getCurrentVal() {
        if(currentVal == null){
            return currentVal;
        }
        if(currentVal.doubleValue() < 0){
            return new BigDecimal(0.00);
        }
        if(currentVal.doubleValue() > 1){
            return new BigDecimal(1.00);
        }
        if(currentVal != null){
            return currentVal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return currentVal;
    }

    public BigDecimal getPreviousVal() {
        if(previousVal == null){
            return previousVal;
        }
        if(previousVal.doubleValue() < 0){
            return new BigDecimal(0);
        }
        if(previousVal.doubleValue() > 1){
            return new BigDecimal(1);
        }
        if(previousVal != null){
            return previousVal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return previousVal;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public void setCurrentVal(BigDecimal currentVal) {
        this.currentVal = currentVal;
    }

    public void setPreviousVal(BigDecimal previousVal) {
        this.previousVal = previousVal;
    }

    
    
    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getCurrent() {
        return current;
//        if(current == null){
//            return current;
//        }
//        BigDecimal c = new BigDecimal(current);
//        if(type == IndicatorType.RATE.getType()){
//            c = c.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
//           /* if(c.doubleValue() == 0 && new BigDecimal(current).doubleValue() != 0){
//                return current + "%";
//            }*/
//            return c + "%";
//        }else{
//            //BigDecimal c = new BigDecimal( current).setScale(2, BigDecimal.ROUND_HALF_UP);
//            /*if(c.doubleValue() == 0 && new BigDecimal(current).doubleValue() != 0){
//                return current + "";
//            }*/
//            c = c.setScale(2, BigDecimal.ROUND_HALF_UP);
//            return  c + "";
//        }
    }

    public String getPrevious() {
        return previous;
//        if(previous == null){
//            return previous;
//        }
//        BigDecimal c = new BigDecimal(previous);
//        if(type == IndicatorType.RATE.getType()){
//            c = c.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
//            /*if(c.doubleValue() == 0 && new BigDecimal(previous).doubleValue() != 0){
//                return previous + "%";
//            }*/
//            return c + "%";
//        }else{
//            return c.setScale(2, BigDecimal.ROUND_HALF_UP) + "";
//        }
//        /*if(previous!=null){
//            BigDecimal c = new BigDecimal( previous).setScale(2, BigDecimal.ROUND_HALF_UP);
//            if(c.doubleValue() == 0 && new BigDecimal(previous).doubleValue() != 0){
//                return previous + "";
//            }
//            return new BigDecimal( previous).setScale(2, BigDecimal.ROUND_HALF_UP) + "";
//        }else{
//            return previous;
//        }*/
    }

    public void setCurrent(String current) {
        
        this.current = current;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsNegative() {
        return isNegative;
    }
    
    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }
    
   /* public FinanceInfoQuarter(String indicator, BigDecimal currentVal, BigDecimal previousVal) {
        super();
        this.indicator = indicator;
        this.currentVal = currentVal;
        this.previousVal = previousVal;
    }*/


  /*  public FinanceInfoQuarter(String indicator, BigDecimal currentVal, BigDecimal previousVal, String current,
            String previous, int isNegative) {
        super();
        this.indicator = indicator;
        this.currentVal = currentVal;
        this.previousVal = previousVal;
        this.current = current;
        this.previous = previous;
        this.isNegative = isNegative;
    }*/

    
    
    /*public FinanceInfoQuarter(String indicator, String code, BigDecimal currentVal, BigDecimal previousVal,
            String current, String previous) {
        super();
        this.indicator = indicator;
        this.code = code;
        this.currentVal = currentVal;
        this.previousVal = previousVal;
        this.current = current;
        this.previous = previous;
    }*/
    

    public FinanceInfoQuarter(String indicator, String code, BigDecimal currentVal, BigDecimal previousVal,
            String current, String previous, int type, int isNegative ,BigDecimal currentInduVal, int percent) {
        super();
        this.indicator = indicator;
        this.code = code;
        this.currentVal = currentVal;
        this.previousVal = previousVal;
        this.current = current;
        this.previous = previous;
        this.type = type;
        this.isNegative = isNegative;
        this.currentInduVal = currentInduVal;
        this.percent = percent;
    }

    public FinanceInfoQuarter() {
        super();
    }

    public BigDecimal getCurrentNV() {
        if(current != null){
            return new BigDecimal(current);
        }else{
            return null;
        }
    }

    public BigDecimal getPreviousNV() {
        if(previous != null){
            return new BigDecimal(previous);
        }else{
            return null;
        }
    }

    public void setCurrentNV(BigDecimal currentNV) {
        this.currentNV = currentNV;
    }

    public void setPreviousNV(BigDecimal previousNV) {
        this.previousNV = previousNV;
    }

    public BigDecimal getCurrentInduVal() {
        if(currentInduVal==null){
            return null;
        }
        if(currentInduVal.doubleValue() < 0){
            return new BigDecimal(0.00);
        }
        if(currentInduVal.doubleValue() > 1){
            return new BigDecimal(1.00);
        }
        if(currentInduVal != null){
            return currentInduVal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return currentInduVal;
    }

    public void setCurrentInduVal(BigDecimal currentInduVal) {
        this.currentInduVal = currentInduVal;
    }
    
    
}
