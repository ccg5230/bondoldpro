package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@Entity
@Table(name = "t_bond_city")
@JsonInclude(Include.NON_NULL)
public class BondCity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "地区")
	@Column(name = "area_uni_code")
	private BigInteger areaUniCode;

	@ApiModelProperty(value = "年份")
	@Column(name = "bond_year")
	private BigInteger bondYear;

	@ApiModelProperty(value = "月份")
	@Column(name = "bond_month")
	private BigInteger bondMonth;

	@ApiModelProperty(value = "季度")
	@Column(name = "bond_quarter")
	private BigInteger bondQuarter;

	@ApiModelProperty(value = "工业增加值增速同比增长（月)")
	@Column(name = "industrial_growth_monthly")
	private BigDecimal industrialGrowthMonthly;

	@ApiModelProperty(value = "地区生产总值净值（季）")
	@Column(name = "gdp_equity_quarter")
	private BigDecimal gdpEquityQuarter;

	@ApiModelProperty(value = "地区生产总值(亿元)")
	@Column(name = "gdp")
	private BigDecimal gdp;

	@ApiModelProperty(value = "第一产业增加值(亿元)")
	@Column(name = "gdp_industrial_added_value1")
	private BigDecimal gdpIndustrialAddedValue1;

	@ApiModelProperty(value = "第二产业增加值(亿元)")
	@Column(name = "gdp_industrial_added_value2")
	private BigDecimal gdpIndustrialAddedValue2;

	@ApiModelProperty(value = "第三产业增加值(亿元)")
	@Column(name = "gdp_industrial_added_value3")
	private BigDecimal gdpIndustrialAddedValue3;

	@ApiModelProperty(value = "人均地区生产总值(元/人)")
	@Column(name = "gdp_per_capita")
	private BigDecimal gdpPerCapita;

	@ApiModelProperty(value = "地方财政一般预算收入（亿元）（年）")
	@Column(name = "budget_revenue")
	private BigDecimal budgetRevenue;

	@ApiModelProperty(value = "地方财政税收收入(亿元)（年）")
	@Column(name = "tax_revenue")
	private BigDecimal taxRevenue;

	@ApiModelProperty(value = "地方财政非税收入(亿元)（年）")
	@Column(name = "nontax_revenue")
	private BigDecimal nontaxRevenue;

	@ApiModelProperty(value = "地方财政国防支出(亿元)")
	@Column(name = "defense_expenditure")
	private BigDecimal defenseExpenditure;

	@ApiModelProperty(value = "地方财政一般预算支出(亿元)")
	@Column(name = "budgetary_expenditure")
	private BigDecimal budgetaryExpenditure;

	@ApiModelProperty(value = "地方财政一般公共服务支出(亿元)")
	@Column(name = "public_service_expenditure")
	private BigDecimal publicServiceExpenditure;

	@ApiModelProperty(value = "地方财政外交支出(亿元)")
	@Column(name = "foreign_expenditure")
	private BigDecimal foreignExpenditure;

	@ApiModelProperty(value = "地方财政公共安全支出(亿元)")
	@Column(name = "public_security_expenditure")
	private BigDecimal publicSecurityExpenditure;

	@ApiModelProperty(value = "地方财政教育支出(亿元)")
	@Column(name = "transaction_expenditure")
	private BigDecimal transactionExpenditure;

	@ApiModelProperty(value = "地方财政科学技术支出(亿元)")
	@Column(name = "expenditure_science_technology")
	private BigDecimal expenditureScienceTechnology;

	@ApiModelProperty(value = "地方财政文化体育与传媒支出(亿元)")
	@Column(name = "cultural_media_expenditure")
	private BigDecimal culturalMediaExpenditure;

	@ApiModelProperty(value = "地方财政社会保障和就业支出(亿元)")
	@Column(name = "social_security_expenditure")
	private BigDecimal socialSecurityExpenditure;

	@ApiModelProperty(value = "地方财政医疗卫生支出(亿元)")
	@Column(name = "health_expenditure")
	private BigDecimal healthExpenditure;

	@ApiModelProperty(value = "地方财政环境保护支出(亿元)")
	@Column(name = "ep_expenditure")
	private BigDecimal epExpenditure;

	@ApiModelProperty(value = "地方财政城乡社区事务支出(亿元)")
	@Column(name = "sq_expenditure")
	private BigDecimal sqExpenditure;

	@ApiModelProperty(value = "地方财政农林水事务支出(亿元)")
	@Column(name = "rn_expenditure")
	private BigDecimal rnExpenditure;

	@ApiModelProperty(value = "地方财政交通运输支出(亿元)")
	@Column(name = "jy_expenditure")
	private BigDecimal jyExpenditure;

	@ApiModelProperty(value = "地方财政资源勘探电力信息等事务支出(亿元)")
	@Column(name = "dl_expenditure")
	private BigDecimal dlExpenditure;

	@ApiModelProperty(value = "地方财政商业服务业等事务支出(亿元)")
	@Column(name = "syfw_expenditure")
	private BigDecimal syfwExpenditure;

	@ApiModelProperty(value = "地方财政金融监管支出(亿元)")
	@Column(name = "jrjg_expenditure")
	private BigDecimal jrjgExpenditure;

	@ApiModelProperty(value = "地方财政地震灾后重建支出(亿元)")
	@Column(name = "dzcj_expenditure")
	private BigDecimal dzcjExpenditure;

	@ApiModelProperty(value = "地方财政国土资源气象等事务支出决策数(亿元)")
	@Column(name = "gtzy_expenditure")
	private BigDecimal gtzyExpenditure;

	@ApiModelProperty(value = "地方财政住房保障支出支出(亿元)")
	@Column(name = "zfbz_expenditure")
	private BigDecimal zfbzExpenditure;

	@ApiModelProperty(value = "地方财政粮油物资储备管理等事务(亿元)")
	@Column(name = "wzcb_expenditure")
	private BigDecimal wzcbExpenditure;

	@ApiModelProperty(value = "地方财政国债还本付息支出(亿元)")
	@Column(name = "gz_expenditure")
	private BigDecimal gzExpenditure;

	@ApiModelProperty(value = "地方财政其他支出(亿元)")
	@Column(name = "other_expenses")
	private BigDecimal otherExpenses;

	@Column(name = "permanent_resident_population")
	@ApiModelProperty(value = "年末常住人口（万）（年）")
	private BigDecimal permanentResidentPopulation;

	@Column(name = "urban_population")
	@ApiModelProperty(value = "城镇人口（万）（年）")
	private BigDecimal urbanPopulation;

	@Column(name = "rural_population")
	@ApiModelProperty(value = "乡村人口（万）（年）")
	private BigDecimal ruralPopulation;

	@Column(name = "disposable_income")
	@ApiModelProperty(value = "居民人均可支配收入_净值(元)（季）")
	private BigDecimal disposableIncome;

	@Column(name = "urban_disposable_income")
	@ApiModelProperty(value = "城镇居民人均可支配收入_净值(元)（季)")
	private BigDecimal urbanDisposableIncome;

	@Column(name = "rural_disposable_income")
	@ApiModelProperty(value = "农村居民人均可支配收入_净值(元)（季）")
	private BigDecimal ruralDisposableIncome;
	
	@ApiModelProperty(value = "城镇居民人均消费支出（元）")
	@Column(name = "czxf_avg_expenses")
	private BigDecimal czxfAvgExpenses;

	@Column(name = "create_by")
	@ApiModelProperty(value = "创建人id")
	private Long createBy;

	@Column(length = 19, name = "create_time")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@Column(name = "update_by")
	@ApiModelProperty(value = "修改人id")
	private Long updateBy;

	@Column(length = 19, name = "update_time")
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

	@Column(name = "finance_produces")
	@ApiModelProperty(value = "财政自给率=一般公共预算/一般公共支出")
	private BigDecimal financeProduces;

	@Column(name = "gdp_growth")
	@ApiModelProperty(value = "同比增速=(最新季度数值-去年该季度数值)/去年该季度数值")
	private BigDecimal gdpGrowth;

	@Column(name = "area_chi_name")
	@ApiModelProperty(value = "区域名称")
	private String areaChiName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(BigInteger areaUniCode) {
		this.areaUniCode = areaUniCode;
	}

	public BigInteger getBondYear() {
		return bondYear;
	}

	public void setBondYear(BigInteger bondYear) {
		this.bondYear = bondYear;
	}

	public BigInteger getBondMonth() {
		return bondMonth;
	}

	public void setBondMonth(BigInteger bondMonth) {
		this.bondMonth = bondMonth;
	}

	public BigInteger getBondQuarter() {
		return bondQuarter;
	}

	public void setBondQuarter(BigInteger bondQuarter) {
		this.bondQuarter = bondQuarter;
	}

	public BigDecimal getIndustrialGrowthMonthly() {
		return industrialGrowthMonthly;
	}

	public void setIndustrialGrowthMonthly(BigDecimal industrialGrowthMonthly) {
		this.industrialGrowthMonthly = industrialGrowthMonthly;
	}

	public BigDecimal getGdpEquityQuarter() {
		return gdpEquityQuarter;
	}

	public void setGdpEquityQuarter(BigDecimal gdpEquityQuarter) {
		this.gdpEquityQuarter = gdpEquityQuarter;
	}

	public BigDecimal getGdp() {
		return gdp;
	}

	public void setGdp(BigDecimal gdp) {
		this.gdp = gdp;
	}

	public BigDecimal getGdpIndustrialAddedValue1() {
		return gdpIndustrialAddedValue1;
	}

	public void setGdpIndustrialAddedValue1(BigDecimal gdpIndustrialAddedValue1) {
		this.gdpIndustrialAddedValue1 = gdpIndustrialAddedValue1;
	}

	public BigDecimal getGdpIndustrialAddedValue2() {
		return gdpIndustrialAddedValue2;
	}

	public void setGdpIndustrialAddedValue2(BigDecimal gdpIndustrialAddedValue2) {
		this.gdpIndustrialAddedValue2 = gdpIndustrialAddedValue2;
	}

	public BigDecimal getGdpIndustrialAddedValue3() {
		return gdpIndustrialAddedValue3;
	}

	public void setGdpIndustrialAddedValue3(BigDecimal gdpIndustrialAddedValue3) {
		this.gdpIndustrialAddedValue3 = gdpIndustrialAddedValue3;
	}

	public BigDecimal getGdpPerCapita() {
		return gdpPerCapita;
	}

	public void setGdpPerCapita(BigDecimal gdpPerCapita) {
		this.gdpPerCapita = gdpPerCapita;
	}

	public BigDecimal getBudgetRevenue() {
		return budgetRevenue;
	}

	public void setBudgetRevenue(BigDecimal budgetRevenue) {
		this.budgetRevenue = budgetRevenue;
	}

	public BigDecimal getTaxRevenue() {
		return taxRevenue;
	}

	public void setTaxRevenue(BigDecimal taxRevenue) {
		this.taxRevenue = taxRevenue;
	}

	public BigDecimal getNontaxRevenue() {
		return nontaxRevenue;
	}

	public void setNontaxRevenue(BigDecimal nontaxRevenue) {
		this.nontaxRevenue = nontaxRevenue;
	}

	public BigDecimal getDefenseExpenditure() {
		return defenseExpenditure;
	}

	public void setDefenseExpenditure(BigDecimal defenseExpenditure) {
		this.defenseExpenditure = defenseExpenditure;
	}

	public BigDecimal getBudgetaryExpenditure() {
		return budgetaryExpenditure;
	}

	public void setBudgetaryExpenditure(BigDecimal budgetaryExpenditure) {
		this.budgetaryExpenditure = budgetaryExpenditure;
	}

	public BigDecimal getPublicServiceExpenditure() {
		return publicServiceExpenditure;
	}

	public void setPublicServiceExpenditure(BigDecimal publicServiceExpenditure) {
		this.publicServiceExpenditure = publicServiceExpenditure;
	}

	public BigDecimal getForeignExpenditure() {
		return foreignExpenditure;
	}

	public void setForeignExpenditure(BigDecimal foreignExpenditure) {
		this.foreignExpenditure = foreignExpenditure;
	}

	public BigDecimal getPublicSecurityExpenditure() {
		return publicSecurityExpenditure;
	}

	public void setPublicSecurityExpenditure(BigDecimal publicSecurityExpenditure) {
		this.publicSecurityExpenditure = publicSecurityExpenditure;
	}

	public BigDecimal getTransactionExpenditure() {
		return transactionExpenditure;
	}

	public void setTransactionExpenditure(BigDecimal transactionExpenditure) {
		this.transactionExpenditure = transactionExpenditure;
	}

	public BigDecimal getExpenditureScienceTechnology() {
		return expenditureScienceTechnology;
	}

	public void setExpenditureScienceTechnology(BigDecimal expenditureScienceTechnology) {
		this.expenditureScienceTechnology = expenditureScienceTechnology;
	}

	public BigDecimal getCulturalMediaExpenditure() {
		return culturalMediaExpenditure;
	}

	public void setCulturalMediaExpenditure(BigDecimal culturalMediaExpenditure) {
		this.culturalMediaExpenditure = culturalMediaExpenditure;
	}

	public BigDecimal getSocialSecurityExpenditure() {
		return socialSecurityExpenditure;
	}

	public void setSocialSecurityExpenditure(BigDecimal socialSecurityExpenditure) {
		this.socialSecurityExpenditure = socialSecurityExpenditure;
	}

	public BigDecimal getHealthExpenditure() {
		return healthExpenditure;
	}

	public void setHealthExpenditure(BigDecimal healthExpenditure) {
		this.healthExpenditure = healthExpenditure;
	}

	public BigDecimal getEpExpenditure() {
		return epExpenditure;
	}

	public void setEpExpenditure(BigDecimal epExpenditure) {
		this.epExpenditure = epExpenditure;
	}

	public BigDecimal getSqExpenditure() {
		return sqExpenditure;
	}

	public void setSqExpenditure(BigDecimal sqExpenditure) {
		this.sqExpenditure = sqExpenditure;
	}

	public BigDecimal getRnExpenditure() {
		return rnExpenditure;
	}

	public void setRnExpenditure(BigDecimal rnExpenditure) {
		this.rnExpenditure = rnExpenditure;
	}

	public BigDecimal getJyExpenditure() {
		return jyExpenditure;
	}

	public void setJyExpenditure(BigDecimal jyExpenditure) {
		this.jyExpenditure = jyExpenditure;
	}

	public BigDecimal getDlExpenditure() {
		return dlExpenditure;
	}

	public void setDlExpenditure(BigDecimal dlExpenditure) {
		this.dlExpenditure = dlExpenditure;
	}

	public BigDecimal getSyfwExpenditure() {
		return syfwExpenditure;
	}

	public void setSyfwExpenditure(BigDecimal syfwExpenditure) {
		this.syfwExpenditure = syfwExpenditure;
	}

	public BigDecimal getJrjgExpenditure() {
		return jrjgExpenditure;
	}

	public void setJrjgExpenditure(BigDecimal jrjgExpenditure) {
		this.jrjgExpenditure = jrjgExpenditure;
	}

	public BigDecimal getDzcjExpenditure() {
		return dzcjExpenditure;
	}

	public void setDzcjExpenditure(BigDecimal dzcjExpenditure) {
		this.dzcjExpenditure = dzcjExpenditure;
	}

	public BigDecimal getGtzyExpenditure() {
		return gtzyExpenditure;
	}

	public void setGtzyExpenditure(BigDecimal gtzyExpenditure) {
		this.gtzyExpenditure = gtzyExpenditure;
	}

	public BigDecimal getZfbzExpenditure() {
		return zfbzExpenditure;
	}

	public void setZfbzExpenditure(BigDecimal zfbzExpenditure) {
		this.zfbzExpenditure = zfbzExpenditure;
	}

	public BigDecimal getWzcbExpenditure() {
		return wzcbExpenditure;
	}

	public void setWzcbExpenditure(BigDecimal wzcbExpenditure) {
		this.wzcbExpenditure = wzcbExpenditure;
	}

	public BigDecimal getGzExpenditure() {
		return gzExpenditure;
	}

	public void setGzExpenditure(BigDecimal gzExpenditure) {
		this.gzExpenditure = gzExpenditure;
	}

	public BigDecimal getOtherExpenses() {
		return otherExpenses;
	}

	public void setOtherExpenses(BigDecimal otherExpenses) {
		this.otherExpenses = otherExpenses;
	}

	public BigDecimal getDisposableIncome() {
		return disposableIncome;
	}

	public void setDisposableIncome(BigDecimal disposableIncome) {
		this.disposableIncome = disposableIncome;
	}

	public BigDecimal getUrbanDisposableIncome() {
		return urbanDisposableIncome;
	}

	public void setUrbanDisposableIncome(BigDecimal urbanDisposableIncome) {
		this.urbanDisposableIncome = urbanDisposableIncome;
	}

	public BigDecimal getRuralDisposableIncome() {
		return ruralDisposableIncome;
	}

	public void setRuralDisposableIncome(BigDecimal ruralDisposableIncome) {
		this.ruralDisposableIncome = ruralDisposableIncome;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public BigDecimal getFinanceProduces() {
		return financeProduces;
	}

	public void setFinanceProduces(BigDecimal financeProduces) {
		this.financeProduces = financeProduces;
	}

	public BigDecimal getGdpGrowth() {
		if(gdpGrowth!=null){
			gdpGrowth = gdpGrowth.multiply(new BigDecimal(100));
		}
		return gdpGrowth;
	}

	public void setGdpGrowth(BigDecimal gdpGrowth) {
		this.gdpGrowth = gdpGrowth;
	}

	public String getAreaChiName() {
		return areaChiName;
	}

	public void setAreaChiName(String areaChiName) {
		this.areaChiName = areaChiName;
	}
	

	public BigDecimal getCzxfAvgExpenses() {
		return czxfAvgExpenses;
	}

	public void setCzxfAvgExpenses(BigDecimal czxfAvgExpenses) {
		this.czxfAvgExpenses = czxfAvgExpenses;
	}

	public BigDecimal getPermanentResidentPopulation() {
		return permanentResidentPopulation;
	}

	public void setPermanentResidentPopulation(BigDecimal permanentResidentPopulation) {
		this.permanentResidentPopulation = permanentResidentPopulation;
	}

	public BigDecimal getUrbanPopulation() {
		return urbanPopulation;
	}

	public void setUrbanPopulation(BigDecimal urbanPopulation) {
		this.urbanPopulation = urbanPopulation;
	}

	public BigDecimal getRuralPopulation() {
		return ruralPopulation;
	}

	public void setRuralPopulation(BigDecimal ruralPopulation) {
		this.ruralPopulation = ruralPopulation;
	}

	@Override
	public String toString() {
		return "BondCity [areaUniCode=" + areaUniCode + ", bondYear=" + bondYear + ", bondMonth=" + bondMonth
				+ ", bondQuarter=" + bondQuarter + "]";
	}
	
	

}
