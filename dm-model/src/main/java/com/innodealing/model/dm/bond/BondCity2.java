package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondCity2 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@ApiModelProperty(value = "地区")
	@Column(name = "area_uni_code")
	private BigInteger areaUniCode;

	@ApiModelProperty(value = "年份")
	private BigInteger bondYear;

	@ApiModelProperty(value = "月份")
	private BigInteger bondMonth;

	@ApiModelProperty(value = "季度")
	private BigInteger bondQuarter;

	@ApiModelProperty(value = "地区生产总值净值（季）")
	private BondCity3 gdpEquityQuarter;

	@ApiModelProperty(value = "地区生产总值(亿元)")
	private BondCity3 gdp;

	@ApiModelProperty(value = "第一产业增加值(亿元)")
	private BondCity3 gdpIndustrialAddedValue1;

	@ApiModelProperty(value = "第二产业增加值(亿元)")
	private BondCity3 gdpIndustrialAddedValue2;

	@ApiModelProperty(value = "第三产业增加值(亿元)")
	private BondCity3 gdpIndustrialAddedValue3;

	@ApiModelProperty(value = "地方财政一般预算收入（亿元）（年）")
	private BondCity3 budgetRevenue;

	@ApiModelProperty(value = "地方财政税收收入(亿元)（年）")
	private BondCity3 taxRevenue;

	@ApiModelProperty(value = "地方财政一般预算支出(亿元)")
	private BondCity3 budgetaryExpenditure;

	@ApiModelProperty(value = "年末常住人口（万）（年）")
	private BondCity3 permanentResidentPopulation;

	@ApiModelProperty(value = "城镇人口（万）（年）")
	private BondCity3 urbanPopulation;

	@ApiModelProperty(value = "乡村人口（万）（年）")
	private BondCity3 ruralPopulation;

	@ApiModelProperty(value = "居民人均可支配收入_净值(元)（季）")
	private BondCity3 disposableIncome;

	@ApiModelProperty(value = "城镇居民人均可支配收入_净值(元)（季)")
	private BondCity3 urbanDisposableIncome;

	@ApiModelProperty(value = "农村居民人均可支配收入_净值(元)（季）")
	private BondCity3 ruralDisposableIncome;

	@ApiModelProperty(value = "财政自给率=一般公共预算/一般公共支出")
	private BondCity3 financeProduces;

	@ApiModelProperty(value = "区域名称")
	private String areaChiName;
	
	@ApiModelProperty(value = "城镇居民人均消费支出（元）")
	private BondCity3 czxfAvgExpenses;

	
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

	public BondCity3 getGdpEquityQuarter() {
		return gdpEquityQuarter;
	}

	public void setGdpEquityQuarter(BondCity3 gdpEquityQuarter) {
		this.gdpEquityQuarter = gdpEquityQuarter;
	}

	public BondCity3 getGdp() {
		return gdp;
	}

	public void setGdp(BondCity3 gdp) {
		this.gdp = gdp;
	}

	public BondCity3 getGdpIndustrialAddedValue1() {
		return gdpIndustrialAddedValue1;
	}

	public void setGdpIndustrialAddedValue1(BondCity3 gdpIndustrialAddedValue1) {
		this.gdpIndustrialAddedValue1 = gdpIndustrialAddedValue1;
	}

	public BondCity3 getGdpIndustrialAddedValue2() {
		return gdpIndustrialAddedValue2;
	}

	public void setGdpIndustrialAddedValue2(BondCity3 gdpIndustrialAddedValue2) {
		this.gdpIndustrialAddedValue2 = gdpIndustrialAddedValue2;
	}

	public BondCity3 getGdpIndustrialAddedValue3() {
		return gdpIndustrialAddedValue3;
	}

	public void setGdpIndustrialAddedValue3(BondCity3 gdpIndustrialAddedValue3) {
		this.gdpIndustrialAddedValue3 = gdpIndustrialAddedValue3;
	}

	public BondCity3 getBudgetRevenue() {
		return budgetRevenue;
	}

	public void setBudgetRevenue(BondCity3 budgetRevenue) {
		this.budgetRevenue = budgetRevenue;
	}

	public BondCity3 getTaxRevenue() {
		return taxRevenue;
	}

	public void setTaxRevenue(BondCity3 taxRevenue) {
		this.taxRevenue = taxRevenue;
	}

	public BondCity3 getBudgetaryExpenditure() {
		return budgetaryExpenditure;
	}

	public void setBudgetaryExpenditure(BondCity3 budgetaryExpenditure) {
		this.budgetaryExpenditure = budgetaryExpenditure;
	}

	public BondCity3 getPermanentResidentPopulation() {
		return permanentResidentPopulation;
	}

	public void setPermanentResidentPopulation(BondCity3 permanentResidentPopulation) {
		this.permanentResidentPopulation = permanentResidentPopulation;
	}

	public BondCity3 getUrbanPopulation() {
		return urbanPopulation;
	}

	public void setUrbanPopulation(BondCity3 urbanPopulation) {
		this.urbanPopulation = urbanPopulation;
	}

	public BondCity3 getRuralPopulation() {
		return ruralPopulation;
	}

	public void setRuralPopulation(BondCity3 ruralPopulation) {
		this.ruralPopulation = ruralPopulation;
	}

	public BondCity3 getDisposableIncome() {
		return disposableIncome;
	}

	public void setDisposableIncome(BondCity3 disposableIncome) {
		this.disposableIncome = disposableIncome;
	}

	public BondCity3 getUrbanDisposableIncome() {
		return urbanDisposableIncome;
	}

	public void setUrbanDisposableIncome(BondCity3 urbanDisposableIncome) {
		this.urbanDisposableIncome = urbanDisposableIncome;
	}

	public BondCity3 getRuralDisposableIncome() {
		return ruralDisposableIncome;
	}

	public void setRuralDisposableIncome(BondCity3 ruralDisposableIncome) {
		this.ruralDisposableIncome = ruralDisposableIncome;
	}

	public BondCity3 getFinanceProduces() {
		return financeProduces;
	}

	public void setFinanceProduces(BondCity3 financeProduces) {
		this.financeProduces = financeProduces;
	}

	public String getAreaChiName() {
		return areaChiName;
	}

	public void setAreaChiName(String areaChiName) {
		this.areaChiName = areaChiName;
	}
	
	public BondCity3 getCzxfAvgExpenses() {
		return czxfAvgExpenses;
	}

	public void setCzxfAvgExpenses(BondCity3 czxfAvgExpenses) {
		this.czxfAvgExpenses = czxfAvgExpenses;
	}

	@Override
	public String toString() {
		return "BondCity [areaUniCode=" + areaUniCode + ", bondYear=" + bondYear + ", bondMonth=" + bondMonth
				+ ", bondQuarter=" + bondQuarter + "]";
	}
	

}
