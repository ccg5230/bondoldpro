package com.innodealing.bond.vo.indu;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
/**
 * 行业简要信息VO
 * @author zhaozhenglai
 * @since 2016年9月19日 下午4:18:37 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class InduVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行业id")
    private Long induId;
    
    @ApiModelProperty(value = "行业名称")
    private String induName;

    public Long getInduId() {
        return induId;
    }

    public String getInduName() {
        return induName;
    }

    public void setInduId(Long induId) {
        this.induId = induId;
    }

    public void setInduName(String induName) {
        this.induName = induName;
    }

    public InduVo(Long induId, String induName) {
        super();
        this.induId = induId;
        this.induName = induName;
    }

    public InduVo() {
        super();
    }

}
