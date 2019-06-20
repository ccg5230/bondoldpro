package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author feng.ma
 * @date 2017年5月12日 下午2:04:06
 * @describe
 */
@Service("BondFavFinaIdxDoc")
@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_favorite_finaidx")
public class BondFavFinaIdxDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Indexed
	@ApiModelProperty(value = "favoriteId")
	private Long favoriteId;

	@ApiModelProperty(value = "groupId")
	private Long groupId;


	@ApiModelProperty(value = "指标类型:1-固有指标;2-组合指标")
	private Integer indexType;

	@ApiModelProperty(value = "指标代码表达式")
	private String indexCodeExpr;

	@ApiModelProperty(value = "指标名称")
	private String indexName;

	@Indexed
	@ApiModelProperty(value = "数值类型:1-指标自身;2-同比;3-行业排名")
	private Integer indexValueType;

	@ApiModelProperty(value = "数值单位:1-%;2-万元")
	private Integer indexValueUnit;

	@ApiModelProperty(value = "数值下限")
	private BigDecimal indexValueLow;

	@ApiModelProperty(value = "数值上限")
	private BigDecimal indexValueHigh;

	@ApiModelProperty(value = "当前状态:0-无效;1-有效")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "用户id")
	private Integer userId;

	@ApiModelProperty(value = "债券唯一编号")
	private Long bondUniCode;

	@ApiModelProperty(value = "t_bond_favorite是否删除")
	private Integer isDelete;

	@Indexed
	@ApiModelProperty(name = "发行人ID")
	private Long comUniCode;

	@ApiModelProperty(value = "条件关系: 1-gteA; 2-lteA; 3-A和B的闭区间; 4-lteA或者gteB; 5-全部; 6-前A; 7-后A;")
	private Integer indexValueNexus;

	@Indexed
	@ApiModelProperty(value = "表达式内含变量列表")
	private List<String> variables; // 从表达式中提取的变量列表
	
	@Indexed
	@ApiModelProperty(value = "投组是否开启通知 1 开启，0 不开启")
	private Integer notifiedEnable;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Integer getIndexType() {
		return indexType;
	}

	public void setIndexType(Integer indexType) {
		this.indexType = indexType;
	}

	public String getIndexCodeExpr() {
		return indexCodeExpr;
	}

	public void setIndexCodeExpr(String indexCodeExpr) {
		this.indexCodeExpr = indexCodeExpr;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Integer getIndexValueType() {
		return indexValueType;
	}

	public void setIndexValueType(Integer indexValueType) {
		this.indexValueType = indexValueType;
	}

	public Integer getIndexValueUnit() {
		return indexValueUnit;
	}

	public void setIndexValueUnit(Integer indexValueUnit) {
		this.indexValueUnit = indexValueUnit;
	}

	public BigDecimal getIndexValueLow() {
		return indexValueLow;
	}

	public void setIndexValueLow(BigDecimal indexValueLow) {
		this.indexValueLow = indexValueLow;
	}

	public BigDecimal getIndexValueHigh() {
		return indexValueHigh;
	}

	public void setIndexValueHigh(BigDecimal indexValueHigh) {
		this.indexValueHigh = indexValueHigh;
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

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Integer getIndexValueNexus() {
		return indexValueNexus;
	}

	public void setIndexValueNexus(Integer indexValueNexus) {
		this.indexValueNexus = indexValueNexus;
	}

	public List<String> getVariables() {
		return variables;
	}

	public void setVariables(List<String> variables) {
		this.variables = variables;
	}

	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}

	@Override
	public String toString() {
		return "BondFavFinaIdxDoc [groupId=" + groupId + ", favoriteId=" + favoriteId + ", indexType="
				+ indexType + ", indexCodeExpr=" + indexCodeExpr + ", indexName=" + indexName + ", indexValueType="
				+ indexValueType + ", indexValueUnit=" + indexValueUnit + ", indexValueLow=" + indexValueLow
				+ ", indexValueHigh=" + indexValueHigh + ", status=" + status + ", createTime=" + createTime
				+ ", userId=" + userId + ", bondUniCode=" + bondUniCode + ", isDelete=" + isDelete + ", comUniCode="
				+ comUniCode + ", indexValueNexus=" + indexValueNexus + ", variables=" + variables + ", notifiedEnable="
				+ notifiedEnable + "]";
	}
	
}
