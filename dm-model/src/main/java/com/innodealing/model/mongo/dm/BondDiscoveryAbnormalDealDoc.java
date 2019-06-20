package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaochao
 * @date 2017年11月06日 上午11:33:01
 * @describe
 */
@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_discovery_abnormal_deal")
public class BondDiscoveryAbnormalDealDoc extends BondInstDoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Indexed
	@ApiModelProperty(value = "成交价的公布时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date pubDate;

	@ApiModelProperty(value = "展示的成交价的公布时间")
	private String displayPubDate;

	@Indexed
	@ApiModelProperty(value = "债券id")
	private Long bondUniCode;

	@ApiModelProperty(value = "债券代码")
	private String code;

	@ApiModelProperty(value = "债券简称")
	private String shortName;

	@ApiModelProperty(value = "剩余期限")
	private String tenor;

	@Indexed
	@ApiModelProperty(value = "期限（天数），用于排序", hidden = true)
	private Long tenorDays;

	@ApiModelProperty(value = "成交净价")
	private Double dealNetPrice;

	@ApiModelProperty(value = "成交价收益率")
	private Double dealPriceYield;

	@ApiModelProperty(value = "估值净价")
	private Double valuationNetPrice;

	@ApiModelProperty(value = "估值收益率")
	private Double valuationYield;

	@ApiModelProperty(value = "估值偏离")
	private Double valuationDeviation;

	@ApiModelProperty(value = "偏离方向:1-高估;2-低估")
	private Integer deviationDirection;

	@ApiModelProperty(value = "净价偏离")
	private Double netPriceDeviation;

	@ApiModelProperty(value = "票面利率")
	private Double couponRate;

	@ApiModelProperty(value = "应计利息")
	private Double accruedInterest;

	@ApiModelProperty(value = "久期")
	private Double macd;

	@ApiModelProperty(value = "修正久期")
	private Double modd;

	@ApiModelProperty(value = "主体债项评级，格式 [主体评级]/[债项评级]")
	private String issBondRating;

	@ApiModelProperty(value = "评级展望")
	private String ratePros;

	@ApiModelProperty(value = "主体评级展望")
	private String issRatePros;

	@ApiModelProperty(value = "企业性质")
	private Integer comAttrPar;

	@ApiModelProperty(value = "企业性质描述:1-7, 描述代码1062")
	private String comAttrParDesc;

	@ApiModelProperty(value = "GICS行业Id", hidden = true)
	private Long induId;

	@ApiModelProperty(value = "GICS行业")
	private String induName;

	@ApiModelProperty(value = "申万行业Id", hidden = true)
	private Long induIdSw;

	@ApiModelProperty(value = "申万行业", hidden = true)
	private String induNameSw;

	@ApiModelProperty(value = "最新收益率,chinamoney上的成交价")
	private Double bondDealRate;

	@ApiModelProperty(value = "债券估值，bond_info中的数据")
	private Double valuationRate;

	@ApiModelProperty(value = "发行票息")
	private Double issCoupRate;

	@ApiModelProperty(value = "发行人id")
	private Long issuerId;

	@ApiModelProperty(value = "发行人")
	private String issuerName;

	@ApiModelProperty(value = "高估统计")
	private Long OverStati;

	@ApiModelProperty(value = "低估统计")
	private Long underStati;
	
    @ApiModelProperty(value = "同业存单")
    @Indexed
    private boolean isNCD;
    
	@ApiModelProperty(value = "折算净价", hidden=true)
    @Indexed
	private Double convertNetPrice;
    
	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Double getBondDealRate() {
		return bondDealRate;
	}

	public void setBondDealRate(Double bondDealRate) {
		this.bondDealRate = bondDealRate;
	}

	public Double getValuationRate() {
		return valuationRate;
	}

	public void setValuationRate(Double valuationRate) {
		this.valuationRate = valuationRate;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getInduNameSw() {
		return induNameSw;
	}

	public void setInduNameSw(String induNameSw) {
		this.induNameSw = induNameSw;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public Double getIssCoupRate() {
		return issCoupRate;
	}

	public void setIssCoupRate(Double issCoupRate) {
		this.issCoupRate = issCoupRate;
	}

	public String getIssRatePros() {
		return issRatePros;
	}

	public void setIssRatePros(String issRatePros) {
		this.issRatePros = issRatePros;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public Double getModd() {
		return modd;
	}

	public void setModd(Double modd) {
		this.modd = modd;
	}

	public Long getTenorDays() {
		return tenorDays;
	}

	public void setTenorDays(Long tenorDays) {
		this.tenorDays = tenorDays;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public Long getOverStati() {
		return OverStati;
	}

	public void setOverStati(Long overStati) {
		OverStati = overStati;
	}

	public Long getUnderStati() {
		return underStati;
	}

	public void setUnderStati(Long underStati) {
		this.underStati = underStati;
	}

	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public Double getDealNetPrice() {
		return dealNetPrice;
	}

	public void setDealNetPrice(Double dealNetPrice) {
		this.dealNetPrice = dealNetPrice;
	}

	public Double getValuationNetPrice() {
		return valuationNetPrice;
	}

	public void setValuationNetPrice(Double valuationNetPrice) {
		this.valuationNetPrice = valuationNetPrice;
	}

	public Double getDealPriceYield() {
		return dealPriceYield;
	}

	public void setDealPriceYield(Double dealPriceYield) {
		this.dealPriceYield = dealPriceYield;
	}

	public Double getValuationDeviation() {
		return valuationDeviation;
	}

	public void setValuationDeviation(Double valuationDeviation) {
		this.valuationDeviation = valuationDeviation;
	}

	public Double getNetPriceDeviation() {
		return netPriceDeviation;
	}

	public void setNetPriceDeviation(Double netPriceDeviation) {
		this.netPriceDeviation = netPriceDeviation;
	}

	public Double getCouponRate() {
		return couponRate;
	}

	public void setCouponRate(Double couponRate) {
		this.couponRate = couponRate;
	}

	public Double getAccruedInterest() {
		return accruedInterest;
	}

	public void setAccruedInterest(Double accruedInterest) {
		this.accruedInterest = accruedInterest;
	}

	public Integer getDeviationDirection() {
		return deviationDirection;
	}

	public void setDeviationDirection(Integer deviationDirection) {
		this.deviationDirection = deviationDirection;
	}

	public Double getMacd() {
		return macd;
	}

	public void setMacd(Double macd) {
		this.macd = macd;
	}

	public Integer getComAttrPar() {
		return comAttrPar;
	}

	public void setComAttrPar(Integer comAttrPar) {
		this.comAttrPar = comAttrPar;
	}

	public String getRatePros() {
		return ratePros;
	}

	public void setRatePros(String ratePros) {
		this.ratePros = ratePros;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getValuationYield() {
		return valuationYield;
	}

	public void setValuationYield(Double valuationYield) {
		this.valuationYield = valuationYield;
	}

	public String getIssBondRating() {
		return issBondRating;
	}

	public void setIssBondRating(String issBondRating) {
		this.issBondRating = issBondRating;
	}

	public String getDisplayPubDate() {
		return displayPubDate;
	}

	public void setDisplayPubDate(String displayPubDate) {
		this.displayPubDate = displayPubDate;
	}

	public String getComAttrParDesc() {
		return comAttrParDesc;
	}

	public void setComAttrParDesc(String comAttrParDesc) {
		this.comAttrParDesc = comAttrParDesc;
	}

	public boolean isNCD() {
		return isNCD;
	}

	public void setNCD(boolean isNCD) {
		this.isNCD = isNCD;
	}

	public Double getConvertNetPrice() {
		return convertNetPrice;
	}

	public void setConvertNetPrice(Double convertNetPrice) {
		this.convertNetPrice = convertNetPrice;
	}
}
