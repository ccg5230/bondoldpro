package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

import io.swagger.annotations.ApiModelProperty;

public class BondFavoriteRadarMappingDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Indexed
	@ApiModelProperty(value = "关联t_bond_favorite的主键ID")
	private Long favoriteId;

    @Indexed
	@ApiModelProperty(value = " 关联t_bond_favorite_radar_schema")
	private Long radarId;

	@ApiModelProperty(value = "默认提醒阈值")
	private Integer threshold;

	@ApiModelProperty(value = "创建时间")
    @Indexed
	private Date createTime;

    @Indexed
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    
    @Indexed
    @ApiModelProperty(value = "关注分组id")
    private Long groupId;
    
    @Indexed
    @ApiModelProperty(value = "债券唯一编号")
    private Long bondUniCode;
    
    @Indexed
	@ApiModelProperty(value = "债券主体id")
	private Long comUniCode;
    
	@ApiModelProperty(value = "持仓量")
	private Integer openinterest;

	@ApiModelProperty(value = "最后一条已读通知的seq_id")
	private Long bookmark;

	@ApiModelProperty(value = "书签最后的消息的更新时间")
	private Date bookmarkUpdateTime;
	
	@ApiModelProperty(value = "备注")
	private String remark;

    @ApiModelProperty(value = "是否过期")
    private Integer isExpired = 0;
    
	@Indexed
	@ApiModelProperty(value = "投组是否开启通知 1 开启，0 不开启")
	private Integer notifiedEnable;

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Long getRadarId() {
		return radarId;
	}

	public void setRadarId(Long radarId) {
		this.radarId = radarId;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Integer getOpeninterest() {
		return openinterest;
	}

	public void setOpeninterest(Integer openinterest) {
		this.openinterest = openinterest;
	}

	public Long getBookmark() {
		return bookmark;
	}

	public void setBookmark(Long bookmark) {
		this.bookmark = bookmark;
	}

	public Date getBookmarkUpdateTime() {
		return bookmarkUpdateTime;
	}

	public void setBookmarkUpdateTime(Date bookmarkUpdateTime) {
		this.bookmarkUpdateTime = bookmarkUpdateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}
	
}
