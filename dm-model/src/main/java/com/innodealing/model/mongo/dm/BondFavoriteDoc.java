package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_favorite")
public class BondFavoriteDoc implements Serializable{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    @Id
	@Indexed
    @ApiModelProperty(value = "关注id")
    private Integer favoriteId;
    
	@Indexed
    @ApiModelProperty(value = "用户id")
    private Integer userId;

	@Indexed
    @ApiModelProperty(value = "关注分组id")
    private Integer groupId;

    @Indexed
    @ApiModelProperty(value = "债券唯一编号")
    private Long bondUniCode;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

	@Indexed
    @ApiModelProperty(hidden = true)
    private Integer isDelete;
	
	@ApiModelProperty(value = "持仓量")
	private Integer openinterest;

    @Indexed
    @ApiModelProperty(hidden = true)
    private Long bookmark;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date bookmarkUpdateTime;
    
    @ApiModelProperty(value = "备注")
    private String remark;
    
    
    public Integer getFavoriteId() {
		return favoriteId;
	}

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
     * @param userId
     *            the userId to set
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
     * @param groupId
     *            the groupId to set
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
     * @param bondUniCode
     *            the bondUniCode to set
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
     * @param createTime
     *            the createTime to set
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
     * @param updateTime
     *            the updateTime to set
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
     * @param isDelete
     *            the isDelete to set
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
     * @param bookmark
     *            the bookmark to set
     */
    public void setBookmark(Long bookmark) {
        this.bookmark = bookmark;
    }

    /**
     * @return the bookmark
     */
    public Long getBookmark() {
        return this.bookmark;
    }

    /**
     * @param bookmarkUpdateTime
     *            the bookmarkUpdateTime to set
     */
    public void setBookmarkUpdateTime(Date bookmarkUpdateTime) {
        this.bookmarkUpdateTime = bookmarkUpdateTime;
    }

    /**
     * @return the bookmarkUpdateTime
     */
    public Date getBookmarkUpdateTime() {
        return this.bookmarkUpdateTime;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondFavoriteDoc [" + (favoriteId != null ? "favoriteId=" + favoriteId + ", " : "")
                + (userId != null ? "userId=" + userId + ", " : "")
                + (groupId != null ? "groupId=" + groupId + ", " : "")
                + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
                + (createTime != null ? "createTime=" + createTime + ", " : "")
                + (updateTime != null ? "updateTime=" + updateTime + ", " : "")
                + (isDelete != null ? "isDelete=" + isDelete + ", " : "")
                + (openinterest != null ? "openinterest=" + openinterest + ", " : "")
                + (bookmark != null ? "bookmark=" + bookmark + ", " : "")
                + (bookmarkUpdateTime != null ? "bookmarkUpdateTime=" + bookmarkUpdateTime + ", " : "")
                + (remark != null ? "remark=" + remark : "") + "]";
    }

}

