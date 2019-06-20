package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author feng.ma
 * @date 2017年5月10日 下午2:47:37
 * @describe
 */
@Service("BondFavPriceIdxDoc")
@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_favorite_priceidx")
public class BondFavPriceIdxDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Indexed
	@ApiModelProperty(value = "favorite_id")
	private Long favoriteId;

	@Indexed
	@ApiModelProperty(value = "价格指标 ")
	private Integer priceIndex;

	@ApiModelProperty(value = "group_id")
	private Long groupId;

	@ApiModelProperty(value = "价格类型 ")
	private Integer priceType;

	@ApiModelProperty(value = "1 - 大于 2 - 小于 ")
	private Integer priceCondi;

	@ApiModelProperty(value = "价格指标数据 ")
	private BigDecimal indexValue;

	@ApiModelProperty(value = "指标数据单位 ")
	private Integer indexUnit;

	@Indexed
	@ApiModelProperty(value = "当前状态:0-无效;1-有效")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@ApiModelProperty(value = "用户id")
	private Integer userId;

	@Indexed
	@ApiModelProperty(value = "债券唯一编号")
	private Long bondUniCode;

	@Indexed
	@ApiModelProperty(value = "t_bond_favorite是否删除")
	private Integer isDelete;
	
	@Indexed
	@ApiModelProperty(value = "投组是否开启通知 1 开启，0 不开启")
	private Integer notifiedEnable;

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Integer getPriceIndex() {
		return priceIndex;
	}

	public void setPriceIndex(Integer priceIndex) {
		this.priceIndex = priceIndex;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Integer getPriceCondi() {
		return priceCondi;
	}

	public void setPriceCondi(Integer priceCondi) {
		this.priceCondi = priceCondi;
	}

	public BigDecimal getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(BigDecimal indexValue) {
		this.indexValue = indexValue;
	}

	public Integer getIndexUnit() {
		return indexUnit;
	}

	public void setIndexUnit(Integer indexUnit) {
		this.indexUnit = indexUnit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}

	@Override
	public String toString() {
		return "BondFavPriceIdxDoc [favoriteId=" + favoriteId + ", priceIndex=" + priceIndex
				+ ", groupId=" + groupId + ", priceType=" + priceType + ", priceCondi=" + priceCondi + ", indexValue="
				+ indexValue + ", indexUnit=" + indexUnit + ", status=" + status + ", createTime=" + createTime
				+ ", userId=" + userId + ", bondUniCode=" + bondUniCode + ", isDelete=" + isDelete + "]";
	}
	
}
