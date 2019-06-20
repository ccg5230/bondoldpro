package com.innodealing.model.mongo.dm.bond.detail.analyse;

import io.swagger.annotations.ApiModelProperty;

public class InduAnalyse{
    
    @ApiModelProperty(value="评价项")
    private String evaluItem;
    
    @ApiModelProperty(value="本周|近3月")
    private long thisWeek;
    
    @ApiModelProperty(value="上周|上三月")
    private long lastWeek;

    public long getThisWeek() {
        return thisWeek;
    }

    public long getLastWeek() {
        return lastWeek;
    }


    public void setThisWeek(long thisWeek) {
        this.thisWeek = thisWeek;
    }

    public void setLastWeek(long lastWeek) {
        this.lastWeek = lastWeek;
    }
    

    public String getEvaluItem() {
        return evaluItem;
    }
    
    public void setEvaluItem(String evaluItem) {
        this.evaluItem = evaluItem;
    }

    public InduAnalyse(String evaluItem, long thisWeek, long lastWeek) {
        super();
        this.evaluItem = evaluItem;
        this.thisWeek = thisWeek;
        this.lastWeek = lastWeek;
    }

    public InduAnalyse() {
        super();
    }
    
    
    
}
