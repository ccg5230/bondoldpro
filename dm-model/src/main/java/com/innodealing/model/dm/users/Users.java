package com.innodealing.model.dm.users;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表users
 * @author zhaozhenglai
 * @since 2016年12月15日 上午10:51:07 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class Users implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 密码 md5
     */
    private String password;
    
    /**
     * 创建时间
     */
    private Date createdAt;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    } 

    
    
}
