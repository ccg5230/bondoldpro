package com.innodealing.model.mongo.dm.bond.user;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 用户联系我们
 * @author zhaozhenglai
 * @since 2016年9月30日 下午2:24:59 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@ApiModel(description = "用户发送联系我们")
@Document(collection = "user_contact")
public class UserContactDoc implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "用户id")
    private Long userId;
    
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    
    @ApiModelProperty(value = "联系方式【手机】")
    private String mobile;
    
    @ApiModelProperty(value = "类型【1付费开通模块权限、2其他】",hidden = true)
    private int type;

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobile() {
        return mobile;
    }

    public int getType() {
        return type;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setType(int type) {
        this.type = type;
    }

   
    
//    @ApiModelProperty(value = "be")
//    private String remark;

}
