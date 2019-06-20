package com.innodealing.model.vo;

import java.util.Date;

import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2017年12月28日 下午3:36:05
 * @version V1.0
 *
 */
public class BondDetailBase {

	@ApiModelProperty(value = "公司统一编码")
	@Column(name="com_uni_code")
	private Long comUniCode;
	
	
	@ApiModelProperty(value = "省ID")
	@Column(name="pro_code")
	private Long proCode;
	
	@ApiModelProperty(value = "公司性质 1[中央国有企业] 2[地方国有企业] 3[集体企业] 4[中外合资企业] 5[外商独资企业] 6[民营企业] 7[公众企业]")
	@Column(name="com_attr_par")
	private Long comAttrPar;
	
	
	
	public Long getProCode() {
		return proCode;
	}

	public void setProCode(Long proCode) {
		this.proCode = proCode;
	}

	public Long getComAttrPar() {
		return comAttrPar;
	}

	public void setComAttrPar(Long comAttrPar) {
		this.comAttrPar = comAttrPar;
	}



	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getCityCode() {
		return cityCode;
	}

	public void setCityCode(Long cityCode) {
		this.cityCode = cityCode;
	}


	@ApiModelProperty(value = "省名称")
	@Column(name="pro_name")
	private String proName;
	
	@ApiModelProperty(value = "市Code")
	@Column(name="city_code")
	private Long cityCode;
	
	@ApiModelProperty(value = "市名称")
	@Column(name="city_name")
	private String cityName;
	
	@ApiModelProperty(value = "注册资本(万元)")
	@Column(name="reg_cap")
	private Long regCap;
	
	@ApiModelProperty(value = "货币类型")
	@Column(name="cury_type_par")
	private int curyTypePar;
	
	@ApiModelProperty(value = "货币类型名称")
	@Column(name="cury_chi_name")
	private String curyChiName;
	
	@ApiModelProperty(value = "公司成立日期")
	@Column(name="est_date")
	private String estDate;
	
	@ApiModelProperty(value = "公司状态 1[筹建] 2[持续经营] 3[被新设合并] 4[吸收合并] 5[被吸收合并] 6[分立] 7[清算] 8[破产] 9[注销(原因不明)] 10[新设合并] 11[不详]")
	@Column(name="sta_par")
	private int staPar;
	
	@ApiModelProperty(value = "是否上市公司")
	@Column(name="is_list_par")
	private int isListPar;
	
	@ApiModelProperty(value = "解散日期")
	@Column(name="dis_date")
	private Date disDate;

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Long getRegCap() {
		return regCap;
	}

	public void setRegCap(Long regCap) {
		this.regCap = regCap;
	}

	public int getCuryTypePar() {
		return curyTypePar;
	}

	public void setCuryTypePar(int curyTypePar) {
		this.curyTypePar = curyTypePar;
	}

	public String getCuryChiName() {
		return curyChiName;
	}

	public void setCuryChiName(String curyChiName) {
		this.curyChiName = curyChiName;
	}



	public String getEstDate() {
		return estDate;
	}

	public void setEstDate(String estDate) {
		this.estDate = estDate;
	}

	public int getStaPar() {
		return staPar;
	}

	public void setStaPar(int staPar) {
		this.staPar = staPar;
	}

	public int getIsListPar() {
		return isListPar;
	}

	public void setIsListPar(int isListPar) {
		this.isListPar = isListPar;
	}

	public Date getDisDate() {
		return disDate;
	}

	public void setDisDate(Date disDate) {
		this.disDate = disDate;
	}
	
	
}
