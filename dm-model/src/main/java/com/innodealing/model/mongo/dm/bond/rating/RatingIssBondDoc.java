package com.innodealing.model.mongo.dm.bond.rating;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
/**
 * 主体和债项评级和评级展望document
 * @author zhaozhenglai
 * @since 2016年10月28日 上午11:46:43 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection = "rating_iss_bond")
public class RatingIssBondDoc implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主体id")
    @Indexed
    Long issId;
    
    @ApiModelProperty(value = "主体所在GICS行业份类")
    @Indexed
    Long induId;
    
    @ApiModelProperty(value = "主体所在申万行业分类")
    @Indexed
    Long induIdSw;
    
    @ApiModelProperty(value = "主体债券流通状态1正在流通，2不在流通")
    @Indexed
    private Integer currStatus = 1;
    
    @ApiModelProperty(value = "发行人")
    private String issName;
    
    @ApiModelProperty(value = "展望信息")
    private String attPoint;
  
    @ApiModelProperty(value = "评级机构")
    private String orgName;
    
    @ApiModelProperty(value = "主体近三个月的近两期评级")
    @Indexed
    List<Integer> issRating90d;
    
    @ApiModelProperty(value = "主体近三个月的近两期评级展望")
    List<Integer> issRatingPar90d;
    
    @ApiModelProperty(value = "主体上三个月的近两期评级")
    @Indexed
    List<Integer> issRating180d;
    
    @ApiModelProperty(value = "主体上三个月的近两期评级展望")
    List<Integer> issRatingPar180d;
    
    @ApiModelProperty(value = "债项近三个月的近两期评级")
    @Indexed
    List<Integer> bondRating90d;
    
    @ApiModelProperty(value = "债项近三个月的近两期评级展望")
    List<Integer> bondRatingPar90d;
    
    @ApiModelProperty(value = "债项上三个月的近两期评级")
    @Indexed
    List<Integer> bondRating180d;
    
    @ApiModelProperty(value = "债项上三个月的近两期评级展望")
    List<Integer> bondRatingPar180d;

    public Long getIssId() {
        return issId;
    }

    public Long getInduId() {
        return induId;
    }

    public List<Integer> getIssRating90d() {
        return issRating90d;
    }

    public List<Integer> getIssRatingPar90d() {
        return issRatingPar90d;
    }

    public List<Integer> getIssRating180d() {
        return issRating180d;
    }

    public List<Integer> getIssRatingPar180d() {
        return issRatingPar180d;
    }

    public List<Integer> getBondRating90d() {
        return bondRating90d;
    }

    public List<Integer> getBondRatingPar90d() {
        return bondRatingPar90d;
    }

    public List<Integer> getBondRating180d() {
        return bondRating180d;
    }

    public List<Integer> getBondRatingPar180d() {
        return bondRatingPar180d;
    }

    public void setIssId(Long issId) {
        this.issId = issId;
    }

    public void setInduId(Long induId) {
        this.induId = induId;
    }

    public void setIssRating90d(List<Integer> issRating90d) {
        this.issRating90d = issRating90d;
    }

    public void setIssRatingPar90d(List<Integer> issRatingPar90d) {
        this.issRatingPar90d = issRatingPar90d;
    }

    public void setIssRating180d(List<Integer> issRating180d) {
        this.issRating180d = issRating180d;
    }

    public void setIssRatingPar180d(List<Integer> issRatingPar180d) {
        this.issRatingPar180d = issRatingPar180d;
    }

    public void setBondRating90d(List<Integer> bondRating90d) {
        this.bondRating90d = bondRating90d;
    }

    public void setBondRatingPar90d(List<Integer> bondRatingPar90d) {
        this.bondRatingPar90d = bondRatingPar90d;
    }

    public void setBondRating180d(List<Integer> bondRating180d) {
        this.bondRating180d = bondRating180d;
    }

    public void setBondRatingPar180d(List<Integer> bondRatingPar180d) {
        this.bondRatingPar180d = bondRatingPar180d;
    }

    public Integer getCurrStatus() {
        return currStatus;
    }

    public String getIssName() {
        return issName;
    }

    public String getAttPoint() {
        return attPoint;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setCurrStatus(Integer currStatus) {
        this.currStatus = currStatus;
    }

    public void setIssName(String issName) {
        this.issName = issName;
    }

    public void setAttPoint(String attPoint) {
        this.attPoint = attPoint;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}
    
    
    
    
}
