package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.innodealing.json.JsonDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_discovery_today_quote_detail")
public class BondDiscoveryTodayQuoteDetailDoc extends BondInstDoc implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@ApiModelProperty(value = "债券id")
	@Indexed
	private Long bondUniCode;

	@ApiModelProperty(value = "债券代码")
	@Indexed
	private String code;

	@ApiModelProperty(value = "债券简称")
	private String shortName;

	@ApiModelProperty(value = "企业性质")
	private Integer comAttrPar;

	@ApiModelProperty(value = "企业性质描述:1-7, 描述代码1062")
	private String comAttrParDesc;

	@ApiModelProperty(value = "债券价格")
	private BigDecimal bondPrice = BigDecimal.ZERO;

	@ApiModelProperty(value = "最优报价")
	private BigDecimal bestPrice = BigDecimal.ZERO;

	@ApiModelProperty(value = "债券发行量，单位：万")
	private BigDecimal bondVol = BigDecimal.ZERO;

	@ApiModelProperty(value = "估值")
	private BigDecimal fairValue;

	@ApiModelProperty(value = "主体量化风险等级变化")
	@Indexed
	private Long pdDiff;

	@JsonSerialize(using = JsonDateSerializer.class)
	@ApiModelProperty(value = "更新时间")
	@Indexed
	private Date updateTime;

	@ApiModelProperty(value = "期限")
	private String tenor;

	@ApiModelProperty(value = "主体量化风险等级")
	private String pd;

	@ApiModelProperty(value = "主体量化风险值")
	private Long pdNum;

	@ApiModelProperty(value = "主体量化风险等级-更新时间")
	private String pdTime;

	@ApiModelProperty(value = "主债评级")
	@Indexed
	private String issBondRating;

	@ApiModelProperty(value = "主体评级")
	private String issRating;

	@ApiModelProperty(value = "债项评级")
	private String bondRating;

	@ApiModelProperty(value = "dm债券种类", hidden = true)
	@Indexed
	private Integer dmBondType;

	@ApiModelProperty(value = "行业Id", hidden = true)
	@Indexed
	@JsonIgnore
	private Long induId;

	@ApiModelProperty(value = "行业")
	private String induName;
	
    @ApiModelProperty(value = "申万行业Id", hidden = true)
    @Indexed
	@JsonIgnore
    private Integer induIdSw;

    @ApiModelProperty(value = "申万行业", hidden = true)
    @JsonIgnore
    private String induNameSw;

	@ApiModelProperty(value = "发行人id", hidden = true)
	@Indexed
	private Long issuerId;

	@ApiModelProperty(value = "发行人名称", hidden = true)
	private String issuer;

	@ApiModelProperty(value = "期限（天数），用于排序", hidden = true)
	@Indexed
	private Long tenorDays;

	@ApiModelProperty(value = "历史最高风险等级")
	private String worstPd;

	@ApiModelProperty(value = "历史最高风险值")
	private Long worstPdNum;

	@ApiModelProperty(value = "历史最高风险等级-更新时间")
	private String worstPdTime;

	@ApiModelProperty(value = "历史最高风险警告标志, 量化风险等级<=CCC")
	private Boolean worstRiskWarning;

	@ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
	private Boolean riskWarning;

	@ApiModelProperty(value = "类型 1利率债 2信用债")
	private Integer type;

	@ApiModelProperty(value = "方向:1-bid收券;2-offer出券", required = true)
	@Indexed
	private Integer side;

	@ApiModelProperty(value = "DM量化风险评级")
	private String dmRating;

	@ApiModelProperty(value = "外部评级")
	private String extRating;

	@ApiModelProperty(value = "显示期限")
	private String displayTenor;

	@ApiModelProperty(value = "票面利率")
	private BigDecimal newCoupRate;

	@ApiModelProperty(value = "同业存单")
	@Indexed
	private boolean isNCD;

	@ApiModelProperty(value = "单边报价")
	@Indexed
	private boolean isUnilateralOffer;

	// 创建日期
	@JsonSerialize(using = JsonDateSerializer.class)
	@ApiModelProperty(value = "创建时间")
	@Indexed
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getBondPrice() {
		return bondPrice;
	}

	public void setBondPrice(BigDecimal bondPrice) {
		this.bondPrice = bondPrice;
	}

	public BigDecimal getBondVol() {
		return bondVol;
	}

	public void setBondVol(BigDecimal bondVol) {
		this.bondVol = bondVol;
	}

	public BigDecimal getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
	}

	public Long getPdDiff() {
		return pdDiff;
	}

	public void setPdDiff(Long pdDiff) {
		this.pdDiff = pdDiff;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public Long getPdNum() {
		return pdNum;
	}

	public void setPdNum(Long pdNum) {
		this.pdNum = pdNum;
	}

	public String getPdTime() {
		return pdTime;
	}

	public void setPdTime(String pdTime) {
		this.pdTime = pdTime;
	}

	public String getIssBondRating() {
		return issBondRating;
	}

	public void setIssBondRating(String issBondRating) {
		this.issBondRating = issBondRating;
	}

	public String getIssRating() {
		return issRating;
	}

	public void setIssRating(String issRating) {
		this.issRating = issRating;
	}

	public String getBondRating() {
		return bondRating;
	}

	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	public Integer getDmBondType() {
		return dmBondType;
	}

	public void setDmBondType(Integer dmBondType) {
		this.dmBondType = dmBondType;
	}

	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public Long getTenorDays() {
		return tenorDays;
	}

	public void setTenorDays(Long tenorDays) {
		this.tenorDays = tenorDays;
	}

	public String getWorstPd() {
		return worstPd;
	}

	public void setWorstPd(String worstPd) {
		this.worstPd = worstPd;
	}

	public Long getWorstPdNum() {
		return worstPdNum;
	}

	public void setWorstPdNum(Long worstPdNum) {
		this.worstPdNum = worstPdNum;
	}

	public String getWorstPdTime() {
		return worstPdTime;
	}

	public void setWorstPdTime(String worstPdTime) {
		this.worstPdTime = worstPdTime;
	}

	public Boolean getWorstRiskWarning() {
		return worstRiskWarning;
	}

	public void setWorstRiskWarning(Boolean worstRiskWarning) {
		this.worstRiskWarning = worstRiskWarning;
	}

	public Boolean getRiskWarning() {
		return riskWarning;
	}

	public void setRiskWarning(Boolean riskWarning) {
		this.riskWarning = riskWarning;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    /**
     * @return the induIdSw
     */
    public Integer getInduIdSw() {
        return induIdSw;
    }

    /**
     * @param induIdSw the induIdSw to set
     */
    public void setInduIdSw(Integer induIdSw) {
        this.induIdSw = induIdSw;
    }

    /**
     * @return the induNameSw
     */
    public String getInduNameSw() {
        return induNameSw;
    }

    /**
     * @param induNameSw the induNameSw to set
     */
    public void setInduNameSw(String induNameSw) {
        this.induNameSw = induNameSw;
    }

	public String getDmRating() {
		return dmRating;
	}

	public void setDmRating(String dmRating) {
		this.dmRating = dmRating;
	}

	public String getExtRating() {
		return extRating;
	}

	public void setExtRating(String extRating) {
		this.extRating = extRating;
	}

	public String getDisplayTenor() {
		return displayTenor;
	}

	public void setDisplayTenor(String displayTenor) {
		this.displayTenor = displayTenor;
	}

	public boolean getIsNCD() {
		return isNCD;
	}

	public void setIsNCD(boolean isNCD) {
		this.isNCD = isNCD;
	}

	public boolean getIsUnilateralOffer() {
		return isUnilateralOffer;
	}

	public void setIsUnilateralOffer(boolean isUnilateralOffer) {
		this.isUnilateralOffer = isUnilateralOffer;
	}

	public BigDecimal getBestPrice() {
		return bestPrice;
	}

	public void setBestPrice(BigDecimal bestPrice) {
		this.bestPrice = bestPrice;
	}

	public Integer getComAttrPar() {
		return comAttrPar;
	}

	public void setComAttrPar(Integer comAttrPar) {
		this.comAttrPar = comAttrPar;
	}

	public BigDecimal getNewCoupRate() {
		return newCoupRate;
	}

	public void setNewCoupRate(BigDecimal newCoupRate) {
		this.newCoupRate = newCoupRate;
	}

	public String getComAttrParDesc() {
		return comAttrParDesc;
	}

	public void setComAttrParDesc(String comAttrParDesc) {
		this.comAttrParDesc = comAttrParDesc;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
