package com.innodealing.bond.vo.favorite;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(Include.NON_NULL)
public class BondExportFavorite {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "债券代码")
	private String bondCode = new String();
	
	@ApiModelProperty(value = "债券名称 ")
	private String bondName = new String();
	
	@ApiModelProperty(value = "持仓量（万）")
	private String openInterest = new String();
    
    @ApiModelProperty(value = "发行主体")
    private String issuer = new String();
    
    @ApiModelProperty(value = "是否城投")
    private String isMunicipal = new String();
    
    @ApiModelProperty(value = "是否有担保")
    private String isGuar = new String();
	
    @ApiModelProperty(value = "发行方式")
    private String issClass = new String();
    
    @ApiModelProperty(value = "行业")
    private String induName = new String();
    
    @ApiModelProperty(value = "主体性质")
    private String issAttr = new String();
    
    @ApiModelProperty(value = "区域")
    private String region = new String();
    
    @ApiModelProperty(value = "主体评级")
    private String issCredRating = new String();
    
    @ApiModelProperty(value = "主体评级时间")
    private String issCredRatingDate = new String();
    
    @ApiModelProperty(value = "债项评级")
    private String bondCredRating = new String();
    
    @ApiModelProperty(value = "债项评级时间")
    private String bondCredRatingDate = new String();

    @ApiModelProperty(value = "债项评级机构")
    private String bondRateOrgName = new String();

	@ApiModelProperty(value = "发行人最新展望")
    private String ratePros = new String();
    
    @ApiModelProperty(value = "资产负债率")
    private String debtAssetRatio = new String();
    
    @ApiModelProperty(value = "主体量化风险等级")
    private String pd = new String();
    
    @ApiModelProperty(value = "主体量化风险评级时间")
    private String pdDate = new String();
    
    @ApiModelProperty(value = "历史最高风险")
    private String worstPd = new String();
    
    @ApiModelProperty(value = "历史最高风险时间")
    private String worstPdDate = new String();
    
    @ApiModelProperty(value = "隐含评级")
    private String impliedRating = new String();
    
    @ApiModelProperty(value = "隐含评级时间")
    private String impliedRatingDate = new String();

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public String getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(String openInterest) {
		this.openInterest = openInterest;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getIsMunicipal() {
		return isMunicipal;
	}

	public void setIsMunicipal(String isMunicipal) {
		this.isMunicipal = isMunicipal;
	}

	public String getIsGuar() {
		return isGuar;
	}

	public void setIsGuar(String isGuar) {
		this.isGuar = isGuar;
	}

	public String getIssClass() {
		return issClass;
	}

	public void setIssClass(String issClass) {
		this.issClass = issClass;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public String getIssAttr() {
		return issAttr;
	}

	public void setIssAttr(String issAttr) {
		this.issAttr = issAttr;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIssCredRating() {
		return issCredRating;
	}

	public void setIssCredRating(String issCredRating) {
		this.issCredRating = issCredRating;
	}

	public String getIssCredRatingDate() {
		return issCredRatingDate;
	}

	public void setIssCredRatingDate(String issCredRatingDate) {
		this.issCredRatingDate = issCredRatingDate;
	}

	public String getBondCredRating() {
		return bondCredRating;
	}

	public void setBondCredRating(String bondCredRating) {
		this.bondCredRating = bondCredRating;
	}

	public String getBondCredRatingDate() {
		return bondCredRatingDate;
	}

	public void setBondCredRatingDate(String bondCredRatingDate) {
		this.bondCredRatingDate = bondCredRatingDate;
	}

	public String getRatePros() {
		return ratePros;
	}

	public void setRatePros(String ratePros) {
		this.ratePros = ratePros;
	}

	public String getDebtAssetRatio() {
		return debtAssetRatio;
	}

	public void setDebtAssetRatio(String debtAssetRatio) {
		this.debtAssetRatio = debtAssetRatio;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getPdDate() {
		return pdDate;
	}

	public void setPdDate(String pdDate) {
		this.pdDate = pdDate;
	}

	public String getWorstPd() {
		return worstPd;
	}

	public void setWorstPd(String worstPd) {
		this.worstPd = worstPd;
	}

	public String getWorstPdDate() {
		return worstPdDate;
	}

	public void setWorstPdDate(String worstPdDate) {
		this.worstPdDate = worstPdDate;
	}

	public String getImpliedRating() {
		return impliedRating;
	}

	public void setImpliedRating(String impliedRating) {
		this.impliedRating = impliedRating;
	}

	public String getImpliedRatingDate() {
		return impliedRatingDate;
	}

	public void setImpliedRatingDate(String impliedRatingDate) {
		this.impliedRatingDate = impliedRatingDate;
	}

	public String getBondRateOrgName() {
		return bondRateOrgName;
	}

	public void setBondRateOrgName(String bondRateOrgName) {
		this.bondRateOrgName = bondRateOrgName;
	}
	
 
}
