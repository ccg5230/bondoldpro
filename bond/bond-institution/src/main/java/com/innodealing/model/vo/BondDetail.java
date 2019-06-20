package com.innodealing.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;


/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2017年12月28日 下午3:36:00 
 * @version V1.0   
 *
 */
public class BondDetail  implements Serializable {

	private static final long serialVersionUID = 2852178796149277047L;

	  @Id
	@ApiModelProperty(value = "债券id")
    private Long bondUniCode;
    
    @ApiModelProperty(value = "债券代码")
    private String code;
    
    @ApiModelProperty(value = "债券全称")
    private String fullName;
    
    @ApiModelProperty(value = "债券缩写")
    private String shortName;
    
    @ApiModelProperty(value = "券种")
    private String dmBondTypeName;
    
    @ApiModelProperty(value = "流通市场")
    private String market;
    
    
	@ApiModelProperty(value = "当前债券规模")
	private Double newSize;
	
    @ApiModelProperty(value = "是否为城投")
    private Boolean munInvest;
    
    @ApiModelProperty(value = "发行票息")
    private BigDecimal issCoupRate;
    
    @ApiModelProperty(value = "票面利率")
    private BigDecimal newCoupRate;
    
    @ApiModelProperty(value = "利率类型")
    private String rateType;

    @ApiModelProperty(value = "付息方式")
    private String intePayCls;
    
    @ApiModelProperty(value = "行权兑付日") 
    private String exerPayDate;
    
    @ApiModelProperty(value = "到期日")
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date theoEndDate;
    

    @ApiModelProperty(value = "浮息基准")
    private String baseRate;
    
    @ApiModelProperty(value = "每年付息日")
    private String yearPayDate;
    
    @ApiModelProperty(value = "担保人")
    private String guruName;
    
	
    @ApiModelProperty(value = "再担保人")
    private String guraName1;
    
    @ApiModelProperty(value = "质押券简称")
    private String pledgeName;
    
    @ApiModelProperty(value = "质押券代码")
    private String pledgeCode;
    
    @ApiModelProperty(value = "是否可赎回")
    private Integer isRedemPar;
    
    @ApiModelProperty(value = "是否可回售")
    private Integer isResaPar;

	@ApiModelProperty(value = "估价收益率(%)")
	private Double estYield;

	@ApiModelProperty(value = "估价全价")
	private Double estDirtyPrice;

	@ApiModelProperty(value = "估价净价")
	private Double estCleanPrice;

	@ApiModelProperty(value = "单劵利差")
	private Double staticSpread;

	@ApiModelProperty(value = "单劵利差行业分位")
	private Double staticSpreadInduQuantile;

	@ApiModelProperty(value = "久期")
	private Double macd;

	@ApiModelProperty(value = "修正久期")
	private Double modd;

	@ApiModelProperty(value = "凸性")
	private Double convexity;

	public String getBaseRate() {
		return baseRate;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public String getCode() {
		return code;
	}

	public Double getConvexity() {
		return convexity;
	}

	public String getDmBondTypeName() {
		return dmBondTypeName;
	}

	public Double getEstCleanPrice() {
		return estCleanPrice;
	}

	public Double getEstDirtyPrice() {
		return estDirtyPrice;
	}

	public Double getEstYield() {
		return estYield;
	}

	public String getExerPayDate() {
		return exerPayDate;
	}

	public String getFullName() {
		return fullName;
	}

	public String getGuraName1() {
		return guraName1;
	}

	public String getGuruName() {
		return guruName;
	}

	public String getIntePayCls() {
		return intePayCls;
	}

	public Integer getIsRedemPar() {
		return isRedemPar;
	}

	public Integer getIsResaPar() {
		return isResaPar;
	}

	public BigDecimal getIssCoupRate() {
		return issCoupRate;
	}

	public Double getMacd() {
		return macd;
	}

	public String getMarket() {
		return market;
	}

	public Double getModd() {
		return modd;
	}

	public Boolean getMunInvest() {
		return munInvest;
	}

	public BigDecimal getNewCoupRate() {
		return newCoupRate;
	}

	public Double getNewSize() {
		return newSize;
	}

	public String getPledgeCode() {
		return pledgeCode;
	}

	public String getPledgeName() {
		return pledgeName;
	}

	public String getRateType() {
		return rateType;
	}

	public String getShortName() {
		return shortName;
	}

	public Double getStaticSpread() {
		return staticSpread;
	}

	public Double getStaticSpreadInduQuantile() {
		return staticSpreadInduQuantile;
	}

	public Date getTheoEndDate() {
		return theoEndDate;
	}

	public String getYearPayDate() {
		return yearPayDate;
	}

	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setConvexity(Double convexity) {
		this.convexity = convexity;
	}

	public void setDmBondTypeName(String dmBondTypeName) {
		this.dmBondTypeName = dmBondTypeName;
	}

	public void setEstCleanPrice(Double estCleanPrice) {
		this.estCleanPrice = estCleanPrice;
	}
    
	public void setEstDirtyPrice(Double estDirtyPrice) {
		this.estDirtyPrice = estDirtyPrice;
	}

	public void setEstYield(Double estYield) {
		this.estYield = estYield;
	}

	public void setExerPayDate(String exerPayDate) {
		this.exerPayDate = exerPayDate;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setGuraName1(String guraName1) {
		this.guraName1 = guraName1;
	}
	
	public void setGuruName(String guruName) {
		this.guruName = guruName;
	}

	public void setIntePayCls(String intePayCls) {
		this.intePayCls = intePayCls;
	}

	public void setIsRedemPar(Integer isRedemPar) {
		this.isRedemPar = isRedemPar;
	}

	public void setIsResaPar(Integer isResaPar) {
		this.isResaPar = isResaPar;
	}

	public void setIssCoupRate(BigDecimal issCoupRate) {
		this.issCoupRate = issCoupRate;
	}

	public void setMacd(Double macd) {
		this.macd = macd;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public void setModd(Double modd) {
		this.modd = modd;
	}

	public void setMunInvest(Boolean munInvest) {
		this.munInvest = munInvest;
	}

	public void setNewCoupRate(BigDecimal newCoupRate) {
		this.newCoupRate = newCoupRate;
	}

	public void setNewSize(Double newSize) {
		this.newSize = newSize;
	}

	public void setPledgeCode(String pledgeCode) {
		this.pledgeCode = pledgeCode;
	}

	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setStaticSpread(Double staticSpread) {
		this.staticSpread = staticSpread;
	}

	public void setStaticSpreadInduQuantile(Double staticSpreadInduQuantile) {
		this.staticSpreadInduQuantile = staticSpreadInduQuantile;
	}

	public void setTheoEndDate(Date theoEndDate) {
		this.theoEndDate = theoEndDate;
	}

	public void setYearPayDate(String yearPayDate) {
		this.yearPayDate = yearPayDate;
	}
    
	
	
}
