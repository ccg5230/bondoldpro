package com.innodealing.model;

import io.swagger.annotations.ApiModelProperty;
/**
 * 债券的评级历史信息
 * @author zhaozhenglai
 * @since 2016年9月15日 下午10:14:22 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class BondCredRating {
    @ApiModelProperty(value = "行业id")
    private Long induId;

    @ApiModelProperty(value = "发行人id")
    private Long issId;
    
    @ApiModelProperty(value = "债券id")
    private Long bondUniCode; 

    @ApiModelProperty(value = "发行人")
    private String issName;
    
    @ApiModelProperty(value = "评级展望,用','分开")
    private String prosPap;
    
    @ApiModelProperty(value = "发布日期,用','分开")
    private String publDate;

    @ApiModelProperty(value = "主体评级,用','分开")
    private String rating;


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


    public String getProsPap() {
        return prosPap;
    }


    public String getPublDate() {
        return publDate;
    }


    public String getRating() {
        return rating;
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


    public void setProsPap(String prosPap) {
        this.prosPap = prosPap;
    }


    public void setPublDate(String publDate) {
        this.publDate = publDate;
    }


    public void setRating(String rating) {
        this.rating = rating;
    }


    public BondCredRating() {
        super();
    }


    public BondCredRating(Long induId, Long issId, Long bondUniCode, String issName, String prosPap, String publDate,
            String rating) {
        super();
        this.induId = induId;
        this.issId = issId;
        this.bondUniCode = bondUniCode;
        this.issName = issName;
        this.prosPap = prosPap;
        this.publDate = publDate;
        this.rating = rating;
    }
    
    

}
