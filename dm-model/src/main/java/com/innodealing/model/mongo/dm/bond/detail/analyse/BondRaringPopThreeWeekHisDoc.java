package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zhaozhenglai
 * @since 2016年9月14日 下午5:13:44 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
@Document(collection = "bond_raringpop_three_week_his")
public class BondRaringPopThreeWeekHisDoc implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行业id")
    private Long induId;

    @ApiModelProperty(value = "发行人id")
    private Long issId;

    @ApiModelProperty(value = "债券id")
    private Long bondUniCode;

    @ApiModelProperty(value = "本周评级")
    private String issName;

    @ApiModelProperty(value = "本周评级")
    private int thisWeekR = 0;

    @ApiModelProperty(value = "上周评级")
    private int lastWeekR = 0;

    @ApiModelProperty(value = "上上周评级")
    private int beforeLastWeekR = 0;

    @ApiModelProperty(value = "本周评级展望")
    private int thisWeekP = 0;

    @ApiModelProperty(value = "上周评级展望")
    private int lastWeekP = 0;

    @ApiModelProperty(value = "上上周评级展望")
    private int beforeLastWeekP = 0;

    public Long getInduId() {
        return induId;
    }

    public Long getIssId() {
        return issId;
    }

    public Long getBondUniCode() {
        return bondUniCode;
    }

    public String getIssName() {
        return issName;
    }

    public int getThisWeekR() {
        return thisWeekR;
    }

    public int getLastWeekR() {
        return lastWeekR;
    }

    public int getBeforeLastWeekR() {
        return beforeLastWeekR;
    }

    public int getThisWeekP() {
        return thisWeekP;
    }

    public int getLastWeekP() {
        return lastWeekP;
    }

    public int getBeforeLastWeekP() {
        return beforeLastWeekP;
    }

    public void setInduId(Long induId) {
        this.induId = induId;
    }

    public void setIssId(Long issId) {
        this.issId = issId;
    }

    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }

    public void setIssName(String issName) {
        this.issName = issName;
    }

    public void setThisWeekR(int thisWeekR) {
        this.thisWeekR = thisWeekR;
    }

    public void setLastWeekR(int lastWeekR) {
        this.lastWeekR = lastWeekR;
    }

    public void setBeforeLastWeekR(int beforeLastWeekR) {
        this.beforeLastWeekR = beforeLastWeekR;
    }

    public void setThisWeekP(int thisWeekP) {
        this.thisWeekP = thisWeekP;
    }

    public void setLastWeekP(int lastWeekP) {
        this.lastWeekP = lastWeekP;
    }

    public void setBeforeLastWeekP(int beforeLastWeekP) {
        this.beforeLastWeekP = beforeLastWeekP;
    }

    public BondRaringPopThreeWeekHisDoc() {
        super();
    }

    public BondRaringPopThreeWeekHisDoc(Long induId, Long issId, Long bondUniCode, String issName, int thisWeekR,
            int lastWeekR, int beforeLastWeekR, int thisWeekP, int lastWeekP, int beforeLastWeekP) {
        super();
        this.induId = induId;
        this.issId = issId;
        this.bondUniCode = bondUniCode;
        this.issName = issName;
        this.thisWeekR = thisWeekR;
        this.lastWeekR = lastWeekR;
        this.beforeLastWeekR = beforeLastWeekR;
        this.thisWeekP = thisWeekP;
        this.lastWeekP = lastWeekP;
        this.beforeLastWeekP = beforeLastWeekP;
    }
    
    

}
