package com.innodealing.model;

import io.swagger.annotations.ApiModelProperty;

public class IssCredRating {
    @ApiModelProperty(value = "行业id")
    private Long induId;

    @ApiModelProperty(value = "发行人id")
    private Long issId;

    @ApiModelProperty(value = "发行人")
    private String issName;
    
    @ApiModelProperty(value = "评级展望")
    private String prosPap;
    
    @ApiModelProperty(value = "发布日期")
    private String publDate;

    @ApiModelProperty(value = "主体评级,用','分开")
    private String rating;
    
    @ApiModelProperty(value = "最新更新时间")
    private String lastTime;
    
    @ApiModelProperty(value = "展望信息")
    private String attPoint;
    
    @ApiModelProperty(value = "展望信息")
    private String orgName;

    public Long getInduId() {
        return induId;
    }

    public Long getIssId() {
        return issId;
    }

    public String getIssName() {
        return issName;
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

    public void setIssName(String issName) {
        this.issName = issName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProsPap() {
        return prosPap;
    }

    public String getPublDate() {
        return publDate;
    }

    public void setProsPap(String prosPap) {
        this.prosPap = prosPap;
    }

    public void setPublDate(String publDate) {
        this.publDate = publDate;
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

    public IssCredRating() {
        super();
    }

}
