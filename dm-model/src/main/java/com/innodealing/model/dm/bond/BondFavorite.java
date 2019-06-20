package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 用户收藏model
 * @author zhaozhenglai
 * @since 2016年7月1日上午10:02:45 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 */
@ApiModel
@Entity
@Table(name="t_bond_favorite")
public class BondFavorite implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1091464894014932967L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "关注id")
    @Column(name="favorite_id")
    Integer favoriteId;
    
    @ApiModelProperty(value = "用户id")
    @Column(name="user_id")
    Integer userId;
    
    @ApiModelProperty(value = "关注分组id")
    @Column(name="group_id")
    Integer groupId;
    
    @ApiModelProperty(value = "债券唯一编号")
    @Column(name="bond_uni_code")
    Long bondUniCode;
    
    @ApiModelProperty(hidden = true)
    @Column(name="create_time")
    Date createTime;
    
    @ApiModelProperty(hidden = true)
    @Column(name="update_time")
    Date updateTime;
    
    @ApiModelProperty(hidden = true)
    @Column(name="is_delete")
    Integer isDelete;
    
	@ApiModelProperty(value = "持仓量")
	@Column(name = "openinterest", length = 10)
	private Integer openinterest;

	@ApiModelProperty(value = "持仓价格")
	@Column(name = "position_price")
	private BigDecimal positionPrice;

	@ApiModelProperty(value = "持仓日期")
	@Column(name = "position_date")
	private Date positionDate;

	/**
	 * 最后一条已读通知的seq_id
	 */
	@ApiModelProperty(value = "最后一条已读通知的seq_id")
	@Column(nullable = false, name = "bookmark", length = 19)
	private Long bookmark;

	/**
	 * 书签最后的消息的更新时间
	 */
	@ApiModelProperty(value = "书签最后的消息的更新时间")
	@Column(nullable = false, name = "bookmark_update_time", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookmarkUpdateTime;
	
	@ApiModelProperty(value = "备注")
	@Column(name = "remark")
	private String remark;

    @ApiModelProperty(hidden = true)
    @Column(name="is_expired")
    Integer isExpired = 0;
	
	/**
	 * @return the favoriteId
	 */
	public Integer getFavoriteId() {
		return favoriteId;
	}

	/**
	 * @param favoriteId the favoriteId to set
	 */
	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the groupId
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the bondUniCode
	 */
	public Long getBondUniCode() {
		return bondUniCode;
	}

	/**
	 * @param bondUniCode the bondUniCode to set
	 */
	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the openinterest
	 */
	public Integer getOpeninterest() {
		return openinterest;
	}

	/**
	 * @param openinterest the openinterest to set
	 */
	public void setOpeninterest(Integer openinterest) {
		this.openinterest = openinterest;
	}

	/**
	 * @return the bookmark
	 */
	public Long getBookmark() {
		return bookmark;
	}

	/**
	 * @param bookmark the bookmark to set
	 */
	public void setBookmark(Long bookmark) {
		this.bookmark = bookmark;
	}

	/**
	 * @return the bookmarkUpdateTime
	 */
	public Date getBookmarkUpdateTime() {
		return bookmarkUpdateTime;
	}

	/**
	 * @param bookmarkUpdateTime the bookmarkUpdateTime to set
	 */
	public void setBookmarkUpdateTime(Date bookmarkUpdateTime) {
		this.bookmarkUpdateTime = bookmarkUpdateTime;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public BigDecimal getPositionPrice() {
		return positionPrice;
	}

	public void setPositionPrice(BigDecimal positionPrice) {
		this.positionPrice = positionPrice;
	}

	public Date getPositionDate() {
		return positionDate;
	}

	public void setPositionDate(Date positionDate) {
		this.positionDate = positionDate;
	}
}
