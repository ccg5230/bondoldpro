package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_user_operation")
public class BondUserOperation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "代码")
	private Long bondUniCode;

	@ApiModelProperty(value = "简称")
	private String bondChiName;

	@ApiModelProperty(value = "类型:1债劵2发行人")
	private Integer type;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "债券种类")
	private String bondType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getBondChiName() {
		return bondChiName;
	}

	public void setBondChiName(String bondChiName) {
		this.bondChiName = bondChiName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBondType() {
		return bondType;
	}

	public void setBondType(String bondType) {
		this.bondType = bondType;
	}

	@Override
	public String toString() {
		return "BondUserOperation [userId=" + userId + ", bondUniCode=" + bondUniCode + ", type=" + type + "]";
	}

	public BondUserOperation(Long userId, Long bondUniCode, Integer type) {
		super();
		this.userId = userId;
		this.bondUniCode = bondUniCode;
		this.type = type;
		this.id = toString();
	}

	public BondUserOperation() {
		super();
	}

}
