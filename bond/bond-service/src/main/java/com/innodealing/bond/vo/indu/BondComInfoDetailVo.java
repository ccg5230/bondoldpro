package com.innodealing.bond.vo.indu;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisCiccDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisGuoJunDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisIndustrialDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisRatingDogDoc;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class BondComInfoDetailVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "公司名")
    private String comChiName;
	
	@ApiModelProperty(value = "是否城投债")
	private Boolean munInvest;
	
	@ApiModelProperty(value = "省份")
	private String province;
	
	@ApiModelProperty(value = "性质")
	private String ownerType;
	
	@ApiModelProperty(value = "行业")
	private String induName;
	
	@ApiModelProperty(value = "外部评级")
	private BondComInfoRatingVo bondComInfoRatingDoc;
	
	@ApiModelProperty(value = "DM评级")
	private BondComInfoPdVo bondComInfoPdDoc;
	
	@ApiModelProperty(value = "中金")
	private CreditAnalysisCiccDoc creditAnalysisCiccDoc;
	
	@ApiModelProperty(value = "YY")
	private CreditAnalysisRatingDogDoc creditAnalysisRatingDogDoc;
	
	@ApiModelProperty(value = "兴业")
	private CreditAnalysisIndustrialDoc creditAnalysisIndustrialDoc;
	
	@ApiModelProperty(value = "国君")
	private CreditAnalysisGuoJunDoc creditAnalysisGuoJunDoc;

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public Boolean getMunInvest() {
		return munInvest;
	}

	public void setMunInvest(Boolean munInvest) {
		this.munInvest = munInvest;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public BondComInfoRatingVo getBondComInfoRatingDoc() {
		return bondComInfoRatingDoc;
	}

	public void setBondComInfoRatingDoc(BondComInfoRatingVo bondComInfoRatingDoc) {
		this.bondComInfoRatingDoc = bondComInfoRatingDoc;
	}

	public BondComInfoPdVo getBondComInfoPdDoc() {
		return bondComInfoPdDoc;
	}

	public void setBondComInfoPdDoc(BondComInfoPdVo bondComInfoPdDoc) {
		this.bondComInfoPdDoc = bondComInfoPdDoc;
	}

	public CreditAnalysisCiccDoc getCreditAnalysisCiccDoc() {
		return creditAnalysisCiccDoc;
	}

	public void setCreditAnalysisCiccDoc(CreditAnalysisCiccDoc creditAnalysisCiccDoc) {
		this.creditAnalysisCiccDoc = creditAnalysisCiccDoc;
	}

	public CreditAnalysisRatingDogDoc getCreditAnalysisRatingDogDoc() {
		return creditAnalysisRatingDogDoc;
	}

	public void setCreditAnalysisRatingDogDoc(CreditAnalysisRatingDogDoc creditAnalysisRatingDogDoc) {
		this.creditAnalysisRatingDogDoc = creditAnalysisRatingDogDoc;
	}

	public CreditAnalysisIndustrialDoc getCreditAnalysisIndustrialDoc() {
		return creditAnalysisIndustrialDoc;
	}

	public void setCreditAnalysisIndustrialDoc(CreditAnalysisIndustrialDoc creditAnalysisIndustrialDoc) {
		this.creditAnalysisIndustrialDoc = creditAnalysisIndustrialDoc;
	}

	public CreditAnalysisGuoJunDoc getCreditAnalysisGuoJunDoc() {
		return creditAnalysisGuoJunDoc;
	}

	public void setCreditAnalysisGuoJunDoc(CreditAnalysisGuoJunDoc creditAnalysisGuoJunDoc) {
		this.creditAnalysisGuoJunDoc = creditAnalysisGuoJunDoc;
	}
	
	
	

}
