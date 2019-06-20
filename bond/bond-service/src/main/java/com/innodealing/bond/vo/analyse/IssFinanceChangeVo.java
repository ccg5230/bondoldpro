package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.util.NumberUtils;
import com.innodealing.util.SafeUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * 主体财务指标变动情况VO
 * 
 * @author zhaozhenglai
 * @since 2016年9月19日 下午5:33:54 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
@JsonInclude(Include.NON_NULL)
public class IssFinanceChangeVo implements Serializable {
    /**
     * 
     */
    private static final Logger LOG = LoggerFactory.getLogger(IssFinanceChangeVo.class);
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发行人|公司id")
    private Long issId;

    @ApiModelProperty(value = "指标类别")
    private String category;

    @ApiModelProperty(value = "本期指标")
    private BigDecimal currIn;

    @ApiModelProperty(value = "本期指标名称")
    private String currInName;

    @ApiModelProperty(value = "上期指标")
    private BigDecimal LastIn;

    @ApiModelProperty(value = "较上期")
    private BigDecimal toLastIn;

    @ApiModelProperty(value = "本季度")
    private String currQ;

    @ApiModelProperty(value = "上期季度")
    private String lastQ;

    @ApiModelProperty(value = "0:率|1:值")
    private int type;

    @ApiModelProperty(value = "财报时间")
    private String finDate;

    @ApiModelProperty(value = "是否为负向指标(1是，0不是)")
    private int isNegative;
    
    @ApiModelProperty("指标是否为百分比的小数，1是、0否")
    private int percent;

    public Long getIssId() {
        return issId;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getToLastIn() {
        return toLastIn;
    }

    public void setIssId(Long issId) {
        this.issId = issId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setToLastIn(BigDecimal toLastIn) {
        this.toLastIn = toLastIn;
    }

    public String getCurrQ() {
        return currQ;
    }

    public String getLastQ() {
        return lastQ;
    }

    public void setCurrQ(String currQ) {
        this.currQ = currQ;
    }

    public void setLastQ(String lastQ) {
        this.lastQ = lastQ;
    }

    public String getCurrInName() {
        return currInName;
    }

    public void setCurrInName(String currInName) {
        this.currInName = currInName;
    }

    public int getType() {
        return type;
    }

    public int getIsNegative() {
        return isNegative;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }
    
    

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public IssFinanceChangeVo(Long issId, String category, BigDecimal currIn, BigDecimal lastIn, BigDecimal toLastIn,
            String currQ, String lastQ, String currInName, int type, int isNegative, int percent) {
        super();
        this.issId = issId;
        this.category = category;
        this.currIn = NumberUtils.KeepTwoDecimal(currIn, percent);
        this.currInName = currInName;
        this.LastIn = NumberUtils.KeepTwoDecimal(lastIn, percent);
        this.toLastIn = NumberUtils.KeepTwoDecimal(toLastIn, percent);
        this.currQ = currQ;
        this.lastQ = lastQ;
        this.type = type;
        this.isNegative = isNegative;
        this.percent = percent;
    }

    public IssFinanceChangeVo() {
        super();
    }

    public String getFinDate() {
        return finDate;
    }

    public void setFinDate(String finDate) {
    	  SimpleDateFormat inFormat = new SimpleDateFormat("yyy-MM-dd");
          SimpleDateFormat outFormat = new SimpleDateFormat("yyy/MM/dd");
          try {
          	if(finDate != null && finDate.contains("Q")){
          		finDate = SafeUtils.convertFromYearQnToDate(finDate);
          	}
              this.finDate = outFormat.format(inFormat.parse(finDate));
          } catch (ParseException e) {
              e.printStackTrace();
              LOG.error("failed to convert finDate:" + finDate, e );
          }
    }

    public BigDecimal getCurrIn() {
        return currIn;
    }

    public BigDecimal getLastIn() {
        return LastIn;
    }

    public void setCurrIn(BigDecimal currIn) {
        this.currIn = currIn;
    }

    public void setLastIn(BigDecimal lastIn) {
        LastIn = lastIn;
    }
    
    

}
