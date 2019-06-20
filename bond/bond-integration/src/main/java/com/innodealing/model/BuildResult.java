package com.innodealing.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 构建结果
 * @author zhaozhenglai
 * @since 2016年9月22日 下午2:59:33 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class BuildResult{
    @ApiModelProperty(value = "数据名称")
    private String name;
    
    @ApiModelProperty(value = "结果，成功true,失败false")
    private Boolean success;
    
    @ApiModelProperty(value = "耗时，毫秒ms")
    private String totalTime;
    
    public String getName() {
        return name;
    }
    public Boolean getSuccess() {
        return success;
    }
    public String getTotalTime() {
        return totalTime;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
    
    public BuildResult(String name, Boolean success, String totalTime) {
        super();
        this.name = name;
        this.success = success;
        this.totalTime = totalTime;
    }
    
    
    
}

