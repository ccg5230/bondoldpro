package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document(collection = "bond_risk_info")
public class BondRiskDoc implements Serializable {

	@Id
	@ApiModelProperty(value = "id")
	String id;

	@ApiModelProperty(value = "信用等级")
	BigInteger level;

	@ApiModelProperty(value = "信用等级 AAA AA+")
	String rating;

	@ApiModelProperty(value = "顺序")
	Integer priority;

	@ApiModelProperty(value = "年份")
	Integer pdTime;
	
	//是否是根节点
	@ApiModelProperty(value="isParent")
	Boolean isParent;
	
	@ApiModelProperty(value="name")
	String name;

	@ApiModelProperty(value = "AAA")
	BondRiskColumnDoc column1;
	@ApiModelProperty(value = "AAA-")
	BondRiskColumnDoc column2;
	@ApiModelProperty(value = "AA+")
	BondRiskColumnDoc column3;
	@ApiModelProperty(value = "AA")
	BondRiskColumnDoc column4;
	@ApiModelProperty(value = "AA-")
	BondRiskColumnDoc column5;
	@ApiModelProperty(value = "A+")
	BondRiskColumnDoc column6;
	@ApiModelProperty(value = "A")
	BondRiskColumnDoc column7;
	@ApiModelProperty(value = "A-")
	BondRiskColumnDoc column8;
	@ApiModelProperty(value = "≤BBB+")
	BondRiskColumnDoc column9;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getLevel() {
		return level;
	}

	public void setLevel(BigInteger level) {
		this.level = level;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getPdTime() {
		return pdTime;
	}

	public void setPdTime(Integer pdTime) {
		this.pdTime = pdTime;
	}

	public BondRiskColumnDoc getColumn1() {
		return column1;
	}

	public void setColumn1(BondRiskColumnDoc column1) {
		this.column1 = column1;
	}

	public BondRiskColumnDoc getColumn2() {
		return column2;
	}

	public void setColumn2(BondRiskColumnDoc column2) {
		this.column2 = column2;
	}

	public BondRiskColumnDoc getColumn3() {
		return column3;
	}

	public void setColumn3(BondRiskColumnDoc column3) {
		this.column3 = column3;
	}

	public BondRiskColumnDoc getColumn4() {
		return column4;
	}

	public void setColumn4(BondRiskColumnDoc column4) {
		this.column4 = column4;
	}

	public BondRiskColumnDoc getColumn5() {
		return column5;
	}

	public void setColumn5(BondRiskColumnDoc column5) {
		this.column5 = column5;
	}

	public BondRiskColumnDoc getColumn6() {
		return column6;
	}

	public void setColumn6(BondRiskColumnDoc column6) {
		this.column6 = column6;
	}

	public BondRiskColumnDoc getColumn7() {
		return column7;
	}

	public void setColumn7(BondRiskColumnDoc column7) {
		this.column7 = column7;
	}

	public BondRiskColumnDoc getColumn8() {
		return column8;
	}

	public void setColumn8(BondRiskColumnDoc column8) {
		this.column8 = column8;
	}

	public BondRiskColumnDoc getColumn9() {
		return column9;
	}

	public void setColumn9(BondRiskColumnDoc column9) {
		this.column9 = column9;
	}

	public BondRiskDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BondRiskDoc(String id, BigInteger level, String rating, Integer priority, Integer pdTime, Boolean isParent,
			String name) {
		super();
		this.id = id;
		this.level = level;
		this.rating = rating;
		this.priority = priority;
		this.pdTime = pdTime;
		this.isParent = isParent;
		this.name = name;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
