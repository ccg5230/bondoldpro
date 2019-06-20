//package com.innodealing.model.mongo.dm.bond.detail.analyse;
//
//import java.io.Serializable;
//
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import io.swagger.annotations.ApiModelProperty;
//
///**
// * 债券
// * @author zhaozhenglai
// * @since 2016年9月14日 下午5:13:44 Copyright © 2016 DealingMatrix.cn. All Rights
// *        Reserved.
// */
//@Document(collection = "iss_pdraring_three_week_his")
//public class BondPdRaringThreeWeekHisDoc implements Serializable {
//
//    /**
//     * 
//     */
//    private static final long serialVersionUID = 1L;
//
//    @ApiModelProperty(value = "行业id")
//    private Long induId;
//
//    @ApiModelProperty(value = "发行人id")
//    private Long issId;
//
//
//    @ApiModelProperty(value = "本周评级")
//    private String issName;
//
//    @ApiModelProperty(value = "本周评级")
//    private int thisWeekR = 0;
//
//    @ApiModelProperty(value = "上周评级")
//    private int lastWeekR = 0;
//
//    @ApiModelProperty(value = "上上周评级")
//    private int beforeLastWeekR = 0;
//
//    public Long getInduId() {
//        return induId;
//    }
//
//    public Long getIssId() {
//        return issId;
//    }
//
//    public String getIssName() {
//        return issName;
//    }
//
//    public int getThisWeekR() {
//        return thisWeekR;
//    }
//
//    public int getLastWeekR() {
//        return lastWeekR;
//    }
//
//    public int getBeforeLastWeekR() {
//        return beforeLastWeekR;
//    }
//
//    public void setInduId(Long induId) {
//        this.induId = induId;
//    }
//
//    public void setIssId(Long issId) {
//        this.issId = issId;
//    }
//
//    public void setIssName(String issName) {
//        this.issName = issName;
//    }
//
//    public void setThisWeekR(int thisWeekR) {
//        this.thisWeekR = thisWeekR;
//    }
//
//    public void setLastWeekR(int lastWeekR) {
//        this.lastWeekR = lastWeekR;
//    }
//
//    public void setBeforeLastWeekR(int beforeLastWeekR) {
//        this.beforeLastWeekR = beforeLastWeekR;
//    }
//
//    public BondPdRaringThreeWeekHisDoc(Long induId, Long issId, String issName, int thisWeekR, int lastWeekR,
//            int beforeLastWeekR) {
//        super();
//        this.induId = induId;
//        this.issId = issId;
//        this.issName = issName;
//        this.thisWeekR = thisWeekR;
//        this.lastWeekR = lastWeekR;
//        this.beforeLastWeekR = beforeLastWeekR;
//    }
//
//    public BondPdRaringThreeWeekHisDoc() {
//        super();
//    }
//
//    
//    
//
//}
