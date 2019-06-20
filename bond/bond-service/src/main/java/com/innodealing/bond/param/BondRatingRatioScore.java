package com.innodealing.bond.param;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondRatingRatioScore {

	@ApiModelProperty(value = "")
	private Long compId;

	@ApiModelProperty(value = "")
	private Integer year;

	@ApiModelProperty(value = "")
	private Integer modelId;

	@ApiModelProperty(value = "")
	private String modelName;

	@ApiModelProperty(value = "")
	private BigDecimal ratio1;

	@ApiModelProperty(value = "")
	private BigDecimal ratio2;

	@ApiModelProperty(value = "")
	private BigDecimal ratio3;

	@ApiModelProperty(value = "")
	private BigDecimal ratio4;

	@ApiModelProperty(value = "")
	private BigDecimal ratio5;

	@ApiModelProperty(value = "")
	private BigDecimal ratio6;

	@ApiModelProperty(value = "")
	private BigDecimal ratio7;

	@ApiModelProperty(value = "")
	private BigDecimal ratio8;

	@ApiModelProperty(value = "")
	private BigDecimal ratio9;

	@ApiModelProperty(value = "")
	private BigDecimal ratio10;

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public BigDecimal getRatio1() {
		return ratio1;
	}

	public void setRatio1(BigDecimal ratio1) {
		this.ratio1 = ratio1;
	}

	public BigDecimal getRatio2() {
		return ratio2;
	}

	public void setRatio2(BigDecimal ratio2) {
		this.ratio2 = ratio2;
	}

	public BigDecimal getRatio3() {
		return ratio3;
	}

	public void setRatio3(BigDecimal ratio3) {
		this.ratio3 = ratio3;
	}

	public BigDecimal getRatio4() {
		return ratio4;
	}

	public void setRatio4(BigDecimal ratio4) {
		this.ratio4 = ratio4;
	}

	public BigDecimal getRatio5() {
		return ratio5;
	}

	public void setRatio5(BigDecimal ratio5) {
		this.ratio5 = ratio5;
	}

	public BigDecimal getRatio6() {
		return ratio6;
	}

	public void setRatio6(BigDecimal ratio6) {
		this.ratio6 = ratio6;
	}

	public BigDecimal getRatio7() {
		return ratio7;
	}

	public void setRatio7(BigDecimal ratio7) {
		this.ratio7 = ratio7;
	}

	public BigDecimal getRatio8() {
		return ratio8;
	}

	public void setRatio8(BigDecimal ratio8) {
		this.ratio8 = ratio8;
	}

	public BigDecimal getRatio9() {
		return ratio9;
	}

	public void setRatio9(BigDecimal ratio9) {
		this.ratio9 = ratio9;
	}

	public BigDecimal getRatio10() {
		return ratio10;
	}

	public void setRatio10(BigDecimal ratio10) {
		this.ratio10 = ratio10;
	}

}
