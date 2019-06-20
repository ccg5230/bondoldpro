package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bond_risk_info")
public class ComRiskDoc implements Serializable {

	@Id
	// @ApiModelProperty(value = "id")
	String id;

	// @ApiModelProperty(value = "信用等级")
	BigInteger level;

	// @ApiModelProperty(value = "信用等级 AAA AA+")
	String rating;

	// @ApiModelProperty(value = "顺序")
	Integer priority;

	// @ApiModelProperty(value = "年份")
	Integer pdTime;

	// 是否是根节点
	// @ApiModelProperty(value="isParent")
	Boolean isParent;

	// @ApiModelProperty(value="name")
	String name;

	// @ApiModelProperty(value = "AAA")
	ComRiskColumnDoc column1;
	// @ApiModelProperty(value = "AAA-")
	ComRiskColumnDoc column2;
	// @ApiModelProperty(value = "AA+")
	ComRiskColumnDoc column3;
	// @ApiModelProperty(value = "AA")
	ComRiskColumnDoc column4;
	// @ApiModelProperty(value = "AA-")
	ComRiskColumnDoc column5;
	// @ApiModelProperty(value = "A+")
	ComRiskColumnDoc column6;
	// @ApiModelProperty(value = "A")
	ComRiskColumnDoc column7;
	// @ApiModelProperty(value = "A-")
	ComRiskColumnDoc column8;
	// @ApiModelProperty(value = "≤BBB+")
	ComRiskColumnDoc column9;

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

	public ComRiskColumnDoc getColumn1() {
		return column1;
	}

	public void setColumn1(ComRiskColumnDoc column1) {
		this.column1 = column1;
	}

	public ComRiskColumnDoc getColumn2() {
		return column2;
	}

	public void setColumn2(ComRiskColumnDoc column2) {
		this.column2 = column2;
	}

	public ComRiskColumnDoc getColumn3() {
		return column3;
	}

	public void setColumn3(ComRiskColumnDoc column3) {
		this.column3 = column3;
	}

	public ComRiskColumnDoc getColumn4() {
		return column4;
	}

	public void setColumn4(ComRiskColumnDoc column4) {
		this.column4 = column4;
	}

	public ComRiskColumnDoc getColumn5() {
		return column5;
	}

	public void setColumn5(ComRiskColumnDoc column5) {
		this.column5 = column5;
	}

	public ComRiskColumnDoc getColumn6() {
		return column6;
	}

	public void setColumn6(ComRiskColumnDoc column6) {
		this.column6 = column6;
	}

	public ComRiskColumnDoc getColumn7() {
		return column7;
	}

	public void setColumn7(ComRiskColumnDoc column7) {
		this.column7 = column7;
	}

	public ComRiskColumnDoc getColumn8() {
		return column8;
	}

	public void setColumn8(ComRiskColumnDoc column8) {
		this.column8 = column8;
	}

	public ComRiskColumnDoc getColumn9() {
		return column9;
	}

	public void setColumn9(ComRiskColumnDoc column9) {
		this.column9 = column9;
	}

	public ComRiskDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComRiskDoc(String id, BigInteger level, String rating, Integer priority, Integer pdTime, Boolean isParent,
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
