//package com.innodealing.model.mongo.dm.bond.detail.analyse;
//
//import java.io.Serializable;
//
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import io.swagger.annotations.ApiModelProperty;
//
///**
// * 同行业违约概率增加
// * 
// * @author zhaozhenglai
// * @since 2016年9月9日 上午10:30:15 Copyright © 2016 DealingMatrix.cn. All Rights
// *        Reserved.
// */
//
//@Document(collection = "indu_rating")
//public class InduCredRatingDoc implements Serializable {
//    /**
//     * 
//     */
//    private static final long serialVersionUID = 1L;
//
//    @ApiModelProperty(value = "行业id")
//    @Indexed
//    private Long induId;
//
//    @ApiModelProperty(value = "发行人id")
//    @Indexed
//    private Long issId;
//
//    @ApiModelProperty(value = "发行人")
//    private String issName;
//
//    @ApiModelProperty(value = "最新主体评级")
//    @Indexed
//    private int currR;
//
//    @ApiModelProperty(value = "上期主体评级")
//    @Indexed
//    private int lastR;
//
//    @ApiModelProperty(value = "最新主体评级展望")
//    private int currP;
//    
//    @ApiModelProperty(value = "最新更新时间")
//    private String lastTime;
//    
//    @ApiModelProperty(value = "展望信息")
//    private String attPoint;
//    
//    @ApiModelProperty(value = "评级机构")
//    private String orgName;
//    
//    @ApiModelProperty(value = "主体债券流通状态1正在流通，2不在流通")
//    @Indexed
//    private Integer currStatus = 1;
//    
//    @ApiModelProperty(value = "最新主体评级")
//    private String currRating;
//
//    @ApiModelProperty(value = "上期主体评级")
//    private String lastRating;
//
//    public Long getInduId() {
//        return induId;
//    }
//
//    public String getIssName() {
//        return issName;
//    }
//
//    public int getCurrR() {
//        return currR;
//    }
//
//    public int getLastR() {
//        return lastR;
//    }
//
//    public void setInduId(Long induId) {
//        this.induId = induId;
//    }
//
//    public void setIssName(String issName) {
//        this.issName = issName;
//    }
//
//    public void setCurrR(int currR) {
//        this.currR = currR;
//    }
//
//    public void setLastR(int lastR) {
//        this.lastR = lastR;
//    }
//
//    public Long getIssId() {
//        return issId;
//    }
//
//    public void setIssId(Long issId) {
//        this.issId = issId;
//    }
//
//    public int getCurrP() {
//        return currP;
//    }
//
//    public void setCurrP(int currP) {
//        this.currP = currP;
//    }
//
//    public InduCredRatingDoc() {
//        super();
//    }
//    
//    
//    public String getLastTime() {
//        return lastTime;
//    }
//
//    public String getAttPoint() {
//        return attPoint;
//    }
//
//    public void setLastTime(String lastTime) {
//        this.lastTime = lastTime;
//    }
//
//    public void setAttPoint(String attPoint) {
//        this.attPoint = attPoint;
//    }
//
//    public String getOrgName() {
//        return orgName;
//    }
//
//    public void setOrgName(String orgName) {
//        this.orgName = orgName;
//    }
//
//
//    
//    public Integer getCurrStatus() {
//        return currStatus;
//    }
//
//    public void setCurrStatus(Integer currStatus) {
//        this.currStatus = currStatus;
//    }
//
//    
//    public String getCurrRating() {
//        return currRating;
//    }
//
//    public String getLastRating() {
//        return lastRating;
//    }
//
//    public void setCurrRating(String currRating) {
//        this.currRating = currRating;
//    }
//
//    public void setLastRating(String lastRating) {
//        this.lastRating = lastRating;
//    }
//
//    public InduCredRatingDoc(Long induId, Long issId, String issName, int currR, int lastR, int currP, String lastTime,
//            String attPoint, String orgName, int currStatus, String currRating, String lastRating) {
//        super();
//        this.induId = induId;
//        this.issId = issId;
//        this.issName = issName;
//        this.currR = currR;
//        this.lastR = lastR;
//        this.currP = currP;
//        this.lastTime = lastTime;
//        this.attPoint = attPoint;
//        this.orgName = orgName;
//        this.currStatus = currStatus;
//        this.currRating = currRating;
//        this.lastRating = lastRating;
//    }
//
//    
//
//    
//}
