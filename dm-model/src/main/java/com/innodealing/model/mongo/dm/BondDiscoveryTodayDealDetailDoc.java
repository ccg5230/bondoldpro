package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Document(collection = "bond_discovery_today_deal_detail")
public class BondDiscoveryTodayDealDetailDoc extends BondInstDoc implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @Indexed
    private String shortName;

    @ApiModelProperty(value = "企业性质")
    private Integer comAttrPar;

    @ApiModelProperty(value = "企业性质描述:1-7, 描述代码1062")
    private String comAttrParDesc;

    @ApiModelProperty(value = "成交价")
    @Indexed
    private BigDecimal price;

    // 涨跌
    private BigDecimal bp;

    @ApiModelProperty(value = "期限")
    private String tenor;

    @ApiModelProperty(value = "显示期限")
    private String displayTenor;

    @ApiModelProperty(value = "主体量化风险等级")
    private String pd;

    @ApiModelProperty(value = "主体量化风险值")
    private Long pdNum;

    //显示季度时间YYYY/Qn
    @ApiModelProperty(value = "主体量化风险等级-更新时间")
    private String pdTime;

    @ApiModelProperty(value = "历史最高风险等级")
    private String worstPd;

    @ApiModelProperty(value = "历史最高风险值")
    private Long worstPdNum;

    //显示季度时间YYYY/Qn
    @ApiModelProperty(value = "历史最高风险等级-更新时间")
    private String worstPdTime;

    @ApiModelProperty(value = "历史最高风险警告标志, 量化风险等级<=CCC")
    private Boolean worstRiskWarning;

    @ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
    private Boolean riskWarning;

    @ApiModelProperty(value = "昨日加权收益率")
    private BigDecimal bondAddRate;

    @ApiModelProperty(value = "昨日交易量(万元)")
    private BigDecimal bondTradingVolume;

    // 筛选条件
    @ApiModelProperty(value = "GICS行业Id", hidden = true)
    @Indexed
    private Long induId;

    @ApiModelProperty(value = "行业")
    private String induName;

    // 筛选条件
    @ApiModelProperty(value = "申万行业Id", hidden = true)
    @Indexed
    @JsonIgnore
    private Long induIdSw;

    @ApiModelProperty(value = "行业")
    @JsonIgnore
    private String induNameSw;

    @ApiModelProperty(value = "发行人id", hidden = true)
    @Indexed
    private Long issuerId;

    @ApiModelProperty(value = "发行人名称", hidden = true)
    private String issuer;

    @ApiModelProperty(value = "主债评级")
    @Indexed
    private String issBondRating;

    @ApiModelProperty(value = "同业存单")
    @Indexed
    private boolean isNCD;
    
	// 显示项目
	@ApiModelProperty(value = "中债估值")
	private BigDecimal fairValue;

    // 创建日期
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Indexed
    private Date createTime;

    // 涨跌趋势
    private String bpTrend;

    // 量化风险评级
    private String dmRating;

    // 外部评级
    private String extRating;

    @ApiModelProperty(value = "主体量化风险等级变化")
    private Long pdDiff;

    @ApiModelProperty(value = "实际违约事件日期")
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date defaultDate;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public BigDecimal getBondAddRate() {
        return bondAddRate;
    }

    public void setBondAddRate(BigDecimal bondAddRate) {
        this.bondAddRate = bondAddRate;
    }

    public BigDecimal getBondTradingVolume() {
        return bondTradingVolume;
    }

    public void setBondTradingVolume(BigDecimal bondTradingVolume) {
        this.bondTradingVolume = bondTradingVolume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBp() {
        return bp;
    }

    public void setBp(BigDecimal bp) {
        this.bp = bp;
    }

    public String getInduName() {
        return induName;
    }

    public void setInduName(String induName) {
        this.induName = induName;
    }

    public String getIssBondRating() {
        return issBondRating;
    }

    public void setIssBondRating(String issBondRating) {
        this.issBondRating = issBondRating;
    }

    public String getBpTrend() {
        return bpTrend;
    }

    public void setBpTrend(String bpTrend) {
        this.bpTrend = bpTrend;
    }

    public Long getPdDiff() {
        return pdDiff;
    }

    public void setPdDiff(Long pdDiff) {
        this.pdDiff = pdDiff;
    }

    public Date getDefaultDate() {
        return defaultDate;
    }

    public void setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
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
    
    public BigDecimal getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
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

    public Integer getComAttrPar() {
        return comAttrPar;
    }

    public void setComAttrPar(Integer comAttrPar) {
        this.comAttrPar = comAttrPar;
    }

    public String getComAttrParDesc() {
        return comAttrParDesc;
    }

    public void setComAttrParDesc(String comAttrParDesc) {
        this.comAttrParDesc = comAttrParDesc;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
}
