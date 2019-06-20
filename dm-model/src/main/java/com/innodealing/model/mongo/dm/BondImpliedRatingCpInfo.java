package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document(collection = "bond_implied_rating_cp_info")
public class BondImpliedRatingCpInfo implements Serializable {

	@Id
	@ApiModelProperty(value = "id")
	String id;

	@ApiModelProperty(value = "顺序")
	Integer priority;

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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public BondImpliedRatingCpInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BondImpliedRatingCpInfo(String id,  Integer priority) {
		super();
		this.id = id;
		this.priority = priority;
	}


	

}
