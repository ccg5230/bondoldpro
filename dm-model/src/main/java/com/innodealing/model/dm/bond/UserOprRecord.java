/**
 * 
 */
package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2017年1月5日
 * @clasename UserOprRecord.java
 * @decription TODO
 */
@ApiModel
@Entity
@Table(name="t_user_opr_record")
public class UserOprRecord implements Serializable {
    
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "用户id")
    @Column(name="user_id")
    private Integer userId;
    
    @ApiModelProperty(value = "最新更新时间")
    @Column(name="update_time")
    private Date updateTime;
    
    @ApiModelProperty(value = "bookmark游标")
    @Column(name="bookmark_cursor")
	private Long bookmarkCursor;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getBookmarkCursor() {
		return bookmarkCursor;
	}

	public void setBookmarkCursor(Long bookmarkCursor) {
		this.bookmarkCursor = bookmarkCursor;
	}

	/**
	 * 
	 */
	public UserOprRecord() {
	}

	/**
	 * @param userId
	 * @param updateTime
	 */
	public UserOprRecord(Integer userId, Date updateTime, Long bookmarkCursor) {
		this.userId = userId;
		this.updateTime = updateTime;
		this.bookmarkCursor = bookmarkCursor;
	}
    
}
