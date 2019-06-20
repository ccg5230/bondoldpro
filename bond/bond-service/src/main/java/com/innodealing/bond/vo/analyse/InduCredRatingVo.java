package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.model.mongo.dm.bond.rating.RatingIssBondDoc;
import com.innodealing.uilogic.UIAdapter;

import io.swagger.annotations.ApiModelProperty;

/**
 * 同行业违约概率增加
 * 
 * @author zhaozhenglai
 * @since 2016年9月9日 上午10:30:15 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */

@JsonInclude(Include.NON_NULL)
public class InduCredRatingVo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行业id")
    @Indexed
    private Long induId;

    @ApiModelProperty(value = "发行人id")
    @Indexed
    private Long issId;

    @ApiModelProperty(value = "发行人")
    private String issName;

    @ApiModelProperty(value = "最新主体评级")
    @Indexed
    private Integer currR;

    @ApiModelProperty(value = "上期主体评级")
    @Indexed
    private Integer lastR;

    @ApiModelProperty(value = "最新主体评级展望")
    private Integer currP;
    
    @ApiModelProperty(value = "最新更新时间")
    private String lastTime;
    
    @ApiModelProperty(value = "展望信息")
    private String attPoint;
    
    @ApiModelProperty(value = "评级机构")
    private String orgName;
    
    @ApiModelProperty(value = "主体债券流通状态1正在流通，2不在流通")
    @Indexed
    private Integer currStatus = 1;
    
    @ApiModelProperty(value = "最新主体评级")
    private String currRating;

    @ApiModelProperty(value = "上期主体评级")
    private String lastRating;

    public Long getInduId() {
        return induId;
    }

    public String getIssName() {
        return issName;
    }

    public Integer getCurrR() {
        return currR;
    }

    public Integer getLastR() {
        return lastR;
    }

    public void setInduId(Long induId) {
        this.induId = induId;
    }

    public void setIssName(String issName) {
        this.issName = issName;
    }

    public void setCurrR(Integer currR) {
        this.currR = currR;
    }

    public void setLastR(Integer lastR) {
        this.lastR = lastR;
    }

    public Long getIssId() {
        return issId;
    }

    public void setIssId(Long issId) {
        this.issId = issId;
    }

    public Integer getCurrP() {
        return currP;
    }

    public void setCurrP(Integer currP) {
        this.currP = currP;
    }

    
    
    public String getLastTime() {
        return lastTime;
    }

    public String getAttPoint() {
        return attPoint;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public void setAttPoint(String attPoint) {
        this.attPoint = attPoint;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    
    public Integer getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(Integer currStatus) {
        this.currStatus = currStatus;
    }

    
    public String getCurrRating() {
        return currRating;
    }

    public String getLastRating() {
        return lastRating;
    }

    public void setCurrRating(String currRating) {
        this.currRating = currRating;
    }

    public void setLastRating(String lastRating) {
        this.lastRating = lastRating;
    }


    public InduCredRatingVo() {
        super();
    }
    
    public InduCredRatingVo(RatingIssBondDoc rating) {
        if(rating != null){
            
            Integer currR = null;
            Integer lastR = null;
            if(rating.getIssRating90d().size() == 1){
                currR = rating.getIssRating90d().get(0);
            }
            if(rating.getIssRating90d().size() > 1){
                currR = rating.getIssRating90d().get(0);
                lastR = rating.getIssRating90d().get(1);
            }
            Map<String, Integer> ratingMap = UIAdapter.getRatingMaps();
            Map<Integer, String> ratingMapIn = new HashMap<>();
            for (Entry<String, Integer> map : ratingMap.entrySet()) {
                ratingMapIn.put(map.getValue(), map.getKey());
            }
            
            this.setAttPoint(rating.getAttPoint());
            this.setCurrP(currP);
            this.setCurrR(currR);
            this.setCurrRating(ratingMapIn.get(currR));
            this.setCurrStatus(rating.getCurrStatus());
            this.setInduId(rating.getInduId());
            this.setIssId(rating.getIssId());
            this.setIssName(rating.getIssName());
            this.setLastR(lastR);
            this.setLastRating(ratingMapIn.get(lastR));
            this.setLastTime(null);
            this.setOrgName(null);
        }
    }
    

    
}
