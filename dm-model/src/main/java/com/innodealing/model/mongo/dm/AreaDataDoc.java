package com.innodealing.model.mongo.dm;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;


//@JsonInclude(Include.NON_NULL)
//@Document(collection="area_economies")
public class AreaDataDoc {
	
	@Id
	@ApiModelProperty(value="区域经济数据Id")
	private Long id;
	
	@ApiModelProperty(value="是否所在区域 0 - 不是   1-是")
	private Integer selected;
	
	@ApiModelProperty(value="区域名称")
	@Column(name="area_chi_name")
	private String areaChiName;
		
	@Indexed
	@ApiModelProperty(value="区域code")
	@Column(name="area_uni_code")
	private Long areaUniCode ;
		
	@ApiModelProperty(value="年度")
	@Column(name="bond_year")
	private Integer bondYear ;
		
	@ApiModelProperty(value="月度")
	@Column(name="bond_month")
	private Integer bondMonth;
		
	@ApiModelProperty(value="季度")
	@Column(name="bond_quarter")
	private Integer bondQuarter;
	
	@ApiModelProperty(value="数据来源")
	@Column(name="statistics_type")
	private String statisticsType ;
	
	@ApiModelProperty(value="数据类型")
	@Column(name="data_type")
	private String dataType;
	
	@ApiModelProperty(value = "常住人口（万人）")
    @Column(name = "permanent_resident_pop")
    private BigDecimal permanentResidentPop;
    
    @ApiModelProperty(value = "户籍人口（万人）")
    @Column(name = "domicile_pop")
    private BigDecimal domicilePop;
    
    @ApiModelProperty(value = "城镇人口（万人）")
    @Column(name = "urban_pop")
    private BigDecimal urbanPop;
    
    @ApiModelProperty(value = "乡村人口（万人）")
    @Column(name = "rural_pop")
    private BigDecimal ruralPop;
    
    @ApiModelProperty(value = "机场货邮吞吐量（万吨）")
    @Column(name = "air_cargo_throughput")
    private BigDecimal airCargoThroughput;
    
    @ApiModelProperty(value = "机场货邮运输量（万吨）")
    @Column(name = "air_freight_transport_volume")
    private BigDecimal airFreightTransportVolume;
    
    @ApiModelProperty(value = "机场旅客吞吐量（万人次）")
    @Column(name = "air_passenger_throughput")
    private BigDecimal airPassengerThroughput;
    
    @ApiModelProperty(value = "机场旅客运输量（万人次）")
    @Column(name = "air_passenger_volume")
    private BigDecimal airPassengerVolume;
    
    @ApiModelProperty(value = "港口吞吐量（亿吨）")
    @Column(name = "seaports_throughput")
    private BigDecimal seaportsThroughput;
    
    @ApiModelProperty(value = "高速公路里程（公里）")
    @Column(name = "expressway_mileage")
    private BigDecimal expresswayMileage;
    
    @ApiModelProperty(value = "铁路营业里程（公里）")
    @Column(name = "railway_mileage")
    private BigDecimal railwayMileage;
    
    @ApiModelProperty(value = "城镇化率（%）")
    @Column(name = "urbanization_rate")
    private BigDecimal urbanizationRate;
    
    @ApiModelProperty(value = "城镇居民人均消费支出（元）")
    @Column(name = "per_exp_urban_residents")
    private BigDecimal perExpUrbanResidents;
    
    /** 经济 */
    @ApiModelProperty(value = "地区生产总值(亿元)")
    @Column(name = "gdp")
    private BigDecimal gdp;
    
    @ApiModelProperty(value = "第一产业增加值(亿元)")
    @Column(name = "gdp_add_val_primary_indu")
    private BigDecimal gdpAddValPrimaryIndu;
    
    @ApiModelProperty(value = "第二产业增加值(亿元)")
    @Column(name = "gdp_add_val_secondary_indu")
    private BigDecimal gdpAddValSecondaryIndu;
    
    @ApiModelProperty(value = "工业增加值(亿元)")
    @Column(name = "indu_add_val")
    private BigDecimal induAddVal;
    
    @ApiModelProperty(value = "第三产业增加值(亿元)")
    @Column(name = "gdp_add_val_tertiary_indu")
    private BigDecimal gdpAddValTertiaryIndu;
    
    @ApiModelProperty(value = "地区人均生产总值(元)")
    @Column(name = "gdp_per_capita")
    private BigDecimal gdpPerCapita;
    
    @ApiModelProperty(value = "地区生产总值增速(%)")
    @Column(name = "growth_gdp")
    private BigDecimal growthGdp;
    
    @ApiModelProperty(value = "第一产业增加值增速(%)")
    @Column(name = "growth_add_val_primary_indu")
    private BigDecimal growthAddValPrimaryIndu;
    
    @ApiModelProperty(value = "第二产业增加值增速(%)")
    @Column(name = "growth_add_val_secondary_indu")
    private BigDecimal growthAddValSecondaryIndu;
    
    @ApiModelProperty(value = "工业总产值增速(%)")
    @Column(name = "growth_indu_output_val")
    private BigDecimal growthInduOutputVal;
    
    @ApiModelProperty(value = "第三产业增加值增速(%)")
    @Column(name = "growth_add_val_tertiary_indu")
    private BigDecimal growthAddValTertiaryIndu;
    
    @ApiModelProperty(value = "地区人均生产总值增速(%)")
    @Column(name = "growth_gdp_per_capita")
    private BigDecimal growthGdpPerCapita;
    
    @ApiModelProperty(value = "全社会固定资产投资(亿元)")
    @Column(name = "invest_Fix_assets_whole_society")
    private BigDecimal investFixAssetsWholeSociety;
    
    @ApiModelProperty(value = "固定资产投资(亿元)")
    @Column(name = "invest_Fix_assets")
    private BigDecimal investFixAssets;
    
    @ApiModelProperty(value = "社会消费品零售总额(亿元)")
    @Column(name = "total_retail_sales_consumer_goods")
    private BigDecimal totalRetailSalesConsumerGoods;
    
    @ApiModelProperty(value="人均社会消费品零售总额(亿元)")
    @Column(name="total_retail_sales_per_consumer_goods")
    private BigDecimal totalRetailSalesPerConsumerGoods;
    
    @ApiModelProperty(value = "进出口总额(亿美元)")
    @Column(name = "total_import_export_dollar")
    private BigDecimal totalImportExportDollar;
    
    @ApiModelProperty(value="人均进出口总额(亿美元)")
    @Column(name="total_import_export_per_dollar")
    private BigDecimal totalImportExportPerDollar;
    
    @ApiModelProperty(value = "进出口总额(亿元)")
    @Column(name = "total_import_export")
    private BigDecimal totalImportExport;
    
    @ApiModelProperty(value="人均进出口总额(亿元)")
    @Column(name="total_import_per_export")
    private BigDecimal totalImportPerExport;
    
    @ApiModelProperty(value = "年末全省金融机构本外币各项存款余额(亿元)")
    @Column(name = "foreign_currency_deposits_pro")
    private BigDecimal foreignCurrencyDepositsPro;
    
    @ApiModelProperty(value = "年末全省金融机构人民币各项存款余额(亿元)")
    @Column(name = "rmb_deposits_pro")
    private BigDecimal rmbDepositsPro;
    
    @ApiModelProperty(value = "年末全省金融机构本外币各项贷款余额(亿元)")
    @Column(name = "foreign_currency_loan_bal_pro")
    private BigDecimal foreignCurrencyLoanBalPro;
    
    @ApiModelProperty(value = "年末全省金融机构人民币各项贷款余额(亿元)")
    @Column(name = "rmb_loan_bal_pro")
    private BigDecimal rmbLoanBalPro;
    
    /** 财政 */
    @ApiModelProperty(value = "财政收入（亿元）")
    @Column(name = "gov_receipts")
    private BigDecimal govReceipts;
    
    @ApiModelProperty(value = "公共财政收入（亿元）")
    @Column(name = "pub_gov_receipts")
    private BigDecimal pubGovReceipts;
    
    @ApiModelProperty(value = "税收收入(亿元)")
    @Column(name = "tax_rev")
    private BigDecimal taxRev;
    
    @ApiModelProperty(value = "非税收收入(亿元)")
    @Column(name = "nontax_rev")
    private BigDecimal nontaxRev;
    
    @ApiModelProperty(value="税收收入占比 ")
    @Column(name="tax_rev_proportion")
    private BigDecimal taxRevProportion;
    
    @ApiModelProperty(value = "省级一般公共预算收入(亿元)")
    @Column(name = "pro_pub_budget_rev")
    private BigDecimal proPubBudgetRev;
    
    @ApiModelProperty(value = "省级一般公共预算收入税收收入(亿元)")
    @Column(name = "pro_pub_budget_tax_rev")
    private BigDecimal proPubBudgetTaxRev;
    
    @ApiModelProperty(value = "省级一般公共预算收入非税收收入(亿元)")
    @Column(name = "pro_pub_budget_nontax_rev")
    private BigDecimal proPubBudgetNontaxRev;
    
    @ApiModelProperty(value=" 省级税收收入占比")
    @Column(name="tax_rev_pro_proportion")
    private BigDecimal taxRevProProportion;
    
    @ApiModelProperty(value = "省本级公共财政预算收入(亿元)")
    @Column(name = "pub_budget_rev_pro_cor_lev")
    private BigDecimal pubBudgetRevProCorLev;
    
    @ApiModelProperty(value = "省本级税收收入(亿元)")
    @Column(name = "tax_rev_pro_cor_lev")
    private BigDecimal taxRevProCorLev;
    
    @ApiModelProperty(value = "省本级非税收收入(亿元)")
    @Column(name = "nontax_rev_pro_cor_lev")
    private BigDecimal nontaxRevProCorLev;
    
    @ApiModelProperty(value="省本级税收收入占比")
    @Column(name="tax_rev_pro_cor_proportion")
    private BigDecimal taxRevProCorProportion ;
    
    @ApiModelProperty(value = "上级补助收入")
    @Column(name = "grant_higher_authority")
    private BigDecimal grantHigherAuthority;
    
    @ApiModelProperty(value = "返还型收入")
    @Column(name = "return_rev")
    private BigDecimal returnRev;
    
    @ApiModelProperty(value = "一般性转移支付收入")
    @Column(name = "gen_transfer_pay_rev")
    private BigDecimal genTransferPayRev;
    
    @ApiModelProperty(value = "专项转移支付收入")
    @Column(name = "spec_transfer_pay_rev")
    private BigDecimal specTransferPayRev;
    
    @ApiModelProperty(value = "政府性基金收入")
    @Column(name = "gov_fund_rev")
    private BigDecimal govFundRev;
    
    @ApiModelProperty(value = "全省政府性基金预算收入(亿元)")
    @Column(name = "whole_pro_gov_fund_budget_rev")
    private BigDecimal wholeProGovFundBudgetRev;
    
    @ApiModelProperty(value = "省级政府性基金预算收入(亿元)")
    @Column(name = "pro_gov_fund_budget_rev")
    private BigDecimal proGovFundBudgetRev;
    
    @ApiModelProperty(value = "省本级政府性基金预算收入(亿元)")
    @Column(name = "fund_budget_rev_pro_cor_lev")
    private BigDecimal fundBudgetRevProCorLev;
    
    @ApiModelProperty(value = "土地出让收入(亿元)")
    @Column(name = "land_leasing_rev")
    private BigDecimal landLeasingRev;
    
    @ApiModelProperty(value = "预算外财政专户收入(亿元)")
    @Column(name = "extra_budget_finance_spec_account_rev")
    private BigDecimal extraBudgetFinanceSpecAccountRev;
    
    @ApiModelProperty(value = "财政支出(亿元)")
    @Column(name = "fiscal_exp")
    private BigDecimal fiscalExp;
    
    @ApiModelProperty(value = "公共财政支出(亿元)")
    @Column(name = "pub_fiscal_exp")
    private BigDecimal pubFiscalExp;
    
    @ApiModelProperty(value = "省级一般公共预算支出(亿元)")
    @Column(name = "pro_budget_exp")
    private BigDecimal proBudgetExp;
    
    @ApiModelProperty(value = "省本级公共财政预算支出(亿元)")
    @Column(name = "budget_exp_pro_cor_lev")
    private BigDecimal budgetExpProCorLev;
    
    @ApiModelProperty(value = "政府性基金支出")
    @Column(name = "gov_fund_exp")
    private BigDecimal govFundExp;
    
    @ApiModelProperty(value = "全省政府性基金预算支出(亿元)")
    @Column(name = "whole_pro_gov_fund_budget_exp")
    private BigDecimal wholeProGovFundBudgetExp;
    
    @ApiModelProperty(value = "省级政府性基金预算支出(亿元)")
    @Column(name = "pro_gov_fund_budget_exp")
    private BigDecimal proGovFundBudgetExp;
    
    @ApiModelProperty(value = "省本级政府性基金预算支出(亿元)")
    @Column(name = "fund_budget_exp_pro_cor_lev")
    private BigDecimal fundBudgetExpProCorLev;
    
    @ApiModelProperty(value = "预算外财政专户支出(亿元)")
    @Column(name = "extra_budget_finance_spec_account_exp")
    private BigDecimal extraBudgetFinanceSpecAccountExp;
    
    @ApiModelProperty(value="财政自给率")
    @Column(name="finance_self_sufficiency_rate")
    private BigDecimal financeSelfSufficiencyRate;
    
    /** 负债 */
    @ApiModelProperty(value="地方债券总发行量")
    @Column(name="total_issuance_local_bonds")
    private BigDecimal totalIssuanceLocalBonds;
    
    @ApiModelProperty(value = "地方政府债务")
    @Column(name = "gov_debt")
    private BigDecimal govDebt;
    
    @ApiModelProperty(value = "地方政府债务一般债务")
    @Column(name = "gov_debt_gen")
    private BigDecimal govDebtGen;
    
    @ApiModelProperty(value = "地方政府债务专项债务")
    @Column(name = "gov_debt_spec")
    private BigDecimal govDebtSpec;
    
    @ApiModelProperty(value = "全省政府债务余额(亿元)")
    @Column(name = "whole_pro_gov_debt_bal")
    private BigDecimal wholeProGovDebtBal;
    
    @ApiModelProperty(value = "全省政府债务余额一般债务(亿元)")
    @Column(name = "whole_pro_gov_debt_bal_gen")
    private BigDecimal wholeProGovDebtBalGen;
    
    @ApiModelProperty(value = "全省政府债务余额专项债务(亿元)")
    @Column(name = "whole_pro_gov_debt_bal_spec")
    private BigDecimal wholeProGovDebtBalSpec;
    
    @ApiModelProperty(value = "省本级政府债务余额(亿元)")
    @Column(name = "debt_bal_pro_cor_lev")
    private BigDecimal debtBalProCorLev;
    
    @ApiModelProperty(value = "省本级政府债务余额一般债务(亿元)")
    @Column(name = "debt_bal_pro_cor_lev_gen")
    private BigDecimal debtBalProCorLevGen;
    
    @ApiModelProperty(value = "省本级政府债务余额专项债务(亿元)")
    @Column(name = "debt_bal_pro_cor_lev_spec")
    private BigDecimal debtBalProCorLevSpec;
    
    @ApiModelProperty(value = "地方政府债务限额(亿元)")
    @Column(name = "gov_debt_limit")
    private BigDecimal govDebtLimit;
    
    @ApiModelProperty(value = "地方政府债务限额一般债务(亿元)")
    @Column(name = "gov_debt_limit_gen")
    private BigDecimal govDebtLimitGen;
    
    @ApiModelProperty(value = "地方政府债务限额专项债务(亿元)")
    @Column(name = "gov_debt_limit_spec")
    private BigDecimal govDebtLimitSpec;
    
    @ApiModelProperty(value = "省本级政府债务限额(亿元)")
    @Column(name = "debt_limit_pro_cor_lev")
    private BigDecimal debtLimitProCorLev;
    
    @ApiModelProperty(value = "省本级政府债务限额一般债务(亿元)")
    @Column(name = "debt_limit_pro_cor_lev_gen")
    private BigDecimal debtLimitProCorLevGen;
    
    @ApiModelProperty(value = "省本级政府债务限额专项债务(亿元)")
    @Column(name = "debt_limit_pro_cor_lev_spec")
    private BigDecimal debtLimitProCorLevSpec;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaChiName() {
		return areaChiName;
	}

	public void setAreaChiName(String areaChiName) {
		this.areaChiName = areaChiName;
	}

	public Long getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
	}
	
	public Integer getBondYear() {
		return bondYear;
	}

	public void setBondYear(Integer bondYear) {
		this.bondYear = bondYear;
	}

	public Integer getBondMonth() {
		return bondMonth;
	}

	public void setBondMonth(Integer bondMonth) {
		this.bondMonth = bondMonth;
	}

	public Integer getBondQuarter() {
		return bondQuarter;
	}

	public void setBondQuarter(Integer bondQuarter) {
		this.bondQuarter = bondQuarter;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BigDecimal getPermanentResidentPop() {
		return permanentResidentPop;
	}

	public void setPermanentResidentPop(BigDecimal permanentResidentPop) {
		this.permanentResidentPop = permanentResidentPop;
	}

	public BigDecimal getDomicilePop() {
		return domicilePop;
	}

	public void setDomicilePop(BigDecimal domicilePop) {
		this.domicilePop = domicilePop;
	}

	public BigDecimal getUrbanPop() {
		return urbanPop;
	}

	public void setUrbanPop(BigDecimal urbanPop) {
		this.urbanPop = urbanPop;
	}

	public BigDecimal getRuralPop() {
		return ruralPop;
	}

	public void setRuralPop(BigDecimal ruralPop) {
		this.ruralPop = ruralPop;
	}

	public BigDecimal getAirCargoThroughput() {
		return airCargoThroughput;
	}

	public void setAirCargoThroughput(BigDecimal airCargoThroughput) {
		this.airCargoThroughput = airCargoThroughput;
	}

	public BigDecimal getAirFreightTransportVolume() {
		return airFreightTransportVolume;
	}

	public void setAirFreightTransportVolume(BigDecimal airFreightTransportVolume) {
		this.airFreightTransportVolume = airFreightTransportVolume;
	}

	public BigDecimal getAirPassengerThroughput() {
		return airPassengerThroughput;
	}

	public void setAirPassengerThroughput(BigDecimal airPassengerThroughput) {
		this.airPassengerThroughput = airPassengerThroughput;
	}

	public BigDecimal getAirPassengerVolume() {
		return airPassengerVolume;
	}

	public void setAirPassengerVolume(BigDecimal airPassengerVolume) {
		this.airPassengerVolume = airPassengerVolume;
	}

	public BigDecimal getSeaportsThroughput() {
		return seaportsThroughput;
	}

	public void setSeaportsThroughput(BigDecimal seaportsThroughput) {
		this.seaportsThroughput = seaportsThroughput;
	}

	public BigDecimal getExpresswayMileage() {
		return expresswayMileage;
	}

	public void setExpresswayMileage(BigDecimal expresswayMileage) {
		this.expresswayMileage = expresswayMileage;
	}

	public BigDecimal getRailwayMileage() {
		return railwayMileage;
	}

	public void setRailwayMileage(BigDecimal railwayMileage) {
		this.railwayMileage = railwayMileage;
	}

	public BigDecimal getUrbanizationRate() {
		return urbanizationRate;
	}

	public void setUrbanizationRate(BigDecimal urbanizationRate) {
		this.urbanizationRate = urbanizationRate;
	}

	public BigDecimal getPerExpUrbanResidents() {
		return perExpUrbanResidents;
	}

	public void setPerExpUrbanResidents(BigDecimal perExpUrbanResidents) {
		this.perExpUrbanResidents = perExpUrbanResidents;
	}

	public BigDecimal getGdp() {
		return gdp;
	}

	public void setGdp(BigDecimal gdp) {
		this.gdp = gdp;
	}

	public BigDecimal getGdpAddValPrimaryIndu() {
		return gdpAddValPrimaryIndu;
	}

	public void setGdpAddValPrimaryIndu(BigDecimal gdpAddValPrimaryIndu) {
		this.gdpAddValPrimaryIndu = gdpAddValPrimaryIndu;
	}

	public BigDecimal getGdpAddValSecondaryIndu() {
		return gdpAddValSecondaryIndu;
	}

	public void setGdpAddValSecondaryIndu(BigDecimal gdpAddValSecondaryIndu) {
		this.gdpAddValSecondaryIndu = gdpAddValSecondaryIndu;
	}

	public BigDecimal getInduAddVal() {
		return induAddVal;
	}

	public void setInduAddVal(BigDecimal induAddVal) {
		this.induAddVal = induAddVal;
	}

	public BigDecimal getGdpAddValTertiaryIndu() {
		return gdpAddValTertiaryIndu;
	}

	public void setGdpAddValTertiaryIndu(BigDecimal gdpAddValTertiaryIndu) {
		this.gdpAddValTertiaryIndu = gdpAddValTertiaryIndu;
	}

	public BigDecimal getGdpPerCapita() {
		return gdpPerCapita;
	}

	public void setGdpPerCapita(BigDecimal gdpPerCapita) {
		this.gdpPerCapita = gdpPerCapita;
	}

	public BigDecimal getGrowthGdp() {
		return growthGdp;
	}

	public void setGrowthGdp(BigDecimal growthGdp) {
		this.growthGdp = growthGdp;
	}

	public BigDecimal getGrowthAddValPrimaryIndu() {
		return growthAddValPrimaryIndu;
	}

	public void setGrowthAddValPrimaryIndu(BigDecimal growthAddValPrimaryIndu) {
		this.growthAddValPrimaryIndu = growthAddValPrimaryIndu;
	}

	public BigDecimal getGrowthAddValSecondaryIndu() {
		return growthAddValSecondaryIndu;
	}

	public void setGrowthAddValSecondaryIndu(BigDecimal growthAddValSecondaryIndu) {
		this.growthAddValSecondaryIndu = growthAddValSecondaryIndu;
	}

	public BigDecimal getGrowthInduOutputVal() {
		return growthInduOutputVal;
	}

	public void setGrowthInduOutputVal(BigDecimal growthInduOutputVal) {
		this.growthInduOutputVal = growthInduOutputVal;
	}

	public BigDecimal getGrowthAddValTertiaryIndu() {
		return growthAddValTertiaryIndu;
	}

	public void setGrowthAddValTertiaryIndu(BigDecimal growthAddValTertiaryIndu) {
		this.growthAddValTertiaryIndu = growthAddValTertiaryIndu;
	}

	public BigDecimal getGrowthGdpPerCapita() {
		return growthGdpPerCapita;
	}

	public void setGrowthGdpPerCapita(BigDecimal growthGdpPerCapita) {
		this.growthGdpPerCapita = growthGdpPerCapita;
	}

	public BigDecimal getInvestFixAssetsWholeSociety() {
		return investFixAssetsWholeSociety;
	}

	public void setInvestFixAssetsWholeSociety(BigDecimal investFixAssetsWholeSociety) {
		this.investFixAssetsWholeSociety = investFixAssetsWholeSociety;
	}

	public BigDecimal getInvestFixAssets() {
		return investFixAssets;
	}

	public void setInvestFixAssets(BigDecimal investFixAssets) {
		this.investFixAssets = investFixAssets;
	}

	public BigDecimal getTotalRetailSalesConsumerGoods() {
		return totalRetailSalesConsumerGoods;
	}

	public void setTotalRetailSalesConsumerGoods(BigDecimal totalRetailSalesConsumerGoods) {
		this.totalRetailSalesConsumerGoods = totalRetailSalesConsumerGoods;
	}

	public BigDecimal getTotalRetailSalesPerConsumerGoods() {
		return totalRetailSalesPerConsumerGoods;
	}

	public void setTotalRetailSalesPerConsumerGoods(BigDecimal totalRetailSalesPerConsumerGoods) {
		this.totalRetailSalesPerConsumerGoods = totalRetailSalesPerConsumerGoods;
	}

	public BigDecimal getTotalImportExportDollar() {
		return totalImportExportDollar;
	}

	public void setTotalImportExportDollar(BigDecimal totalImportExportDollar) {
		this.totalImportExportDollar = totalImportExportDollar;
	}

	public BigDecimal getTotalImportExportPerDollar() {
		return totalImportExportPerDollar;
	}

	public void setTotalImportExportPerDollar(BigDecimal totalImportExportPerDollar) {
		this.totalImportExportPerDollar = totalImportExportPerDollar;
	}

	public BigDecimal getTotalImportExport() {
		return totalImportExport;
	}

	public void setTotalImportExport(BigDecimal totalImportExport) {
		this.totalImportExport = totalImportExport;
	}

	public BigDecimal getTotalImportPerExport() {
		return totalImportPerExport;
	}

	public void setTotalImportPerExport(BigDecimal totalImportPerExport) {
		this.totalImportPerExport = totalImportPerExport;
	}

	public BigDecimal getForeignCurrencyDepositsPro() {
		return foreignCurrencyDepositsPro;
	}

	public void setForeignCurrencyDepositsPro(BigDecimal foreignCurrencyDepositsPro) {
		this.foreignCurrencyDepositsPro = foreignCurrencyDepositsPro;
	}

	public BigDecimal getRmbDepositsPro() {
		return rmbDepositsPro;
	}

	public void setRmbDepositsPro(BigDecimal rmbDepositsPro) {
		this.rmbDepositsPro = rmbDepositsPro;
	}

	public BigDecimal getForeignCurrencyLoanBalPro() {
		return foreignCurrencyLoanBalPro;
	}

	public void setForeignCurrencyLoanBalPro(BigDecimal foreignCurrencyLoanBalPro) {
		this.foreignCurrencyLoanBalPro = foreignCurrencyLoanBalPro;
	}

	public BigDecimal getRmbLoanBalPro() {
		return rmbLoanBalPro;
	}

	public void setRmbLoanBalPro(BigDecimal rmbLoanBalPro) {
		this.rmbLoanBalPro = rmbLoanBalPro;
	}

	public BigDecimal getGovReceipts() {
		return govReceipts;
	}

	public void setGovReceipts(BigDecimal govReceipts) {
		this.govReceipts = govReceipts;
	}

	public BigDecimal getPubGovReceipts() {
		return pubGovReceipts;
	}

	public void setPubGovReceipts(BigDecimal pubGovReceipts) {
		this.pubGovReceipts = pubGovReceipts;
	}

	public BigDecimal getTaxRev() {
		return taxRev;
	}

	public void setTaxRev(BigDecimal taxRev) {
		this.taxRev = taxRev;
	}

	public BigDecimal getNontaxRev() {
		return nontaxRev;
	}

	public void setNontaxRev(BigDecimal nontaxRev) {
		this.nontaxRev = nontaxRev;
	}

	public BigDecimal getTaxRevProportion() {
		return taxRevProportion;
	}

	public void setTaxRevProportion(BigDecimal taxRevProportion) {
		this.taxRevProportion = taxRevProportion;
	}

	public BigDecimal getProPubBudgetRev() {
		return proPubBudgetRev;
	}

	public void setProPubBudgetRev(BigDecimal proPubBudgetRev) {
		this.proPubBudgetRev = proPubBudgetRev;
	}

	public BigDecimal getProPubBudgetTaxRev() {
		return proPubBudgetTaxRev;
	}

	public void setProPubBudgetTaxRev(BigDecimal proPubBudgetTaxRev) {
		this.proPubBudgetTaxRev = proPubBudgetTaxRev;
	}

	public BigDecimal getProPubBudgetNontaxRev() {
		return proPubBudgetNontaxRev;
	}

	public void setProPubBudgetNontaxRev(BigDecimal proPubBudgetNontaxRev) {
		this.proPubBudgetNontaxRev = proPubBudgetNontaxRev;
	}

	public BigDecimal getTaxRevProProportion() {
		return taxRevProProportion;
	}

	public void setTaxRevProProportion(BigDecimal taxRevProProportion) {
		this.taxRevProProportion = taxRevProProportion;
	}

	public BigDecimal getPubBudgetRevProCorLev() {
		return pubBudgetRevProCorLev;
	}

	public void setPubBudgetRevProCorLev(BigDecimal pubBudgetRevProCorLev) {
		this.pubBudgetRevProCorLev = pubBudgetRevProCorLev;
	}

	public BigDecimal getTaxRevProCorLev() {
		return taxRevProCorLev;
	}

	public void setTaxRevProCorLev(BigDecimal taxRevProCorLev) {
		this.taxRevProCorLev = taxRevProCorLev;
	}

	public BigDecimal getNontaxRevProCorLev() {
		return nontaxRevProCorLev;
	}

	public void setNontaxRevProCorLev(BigDecimal nontaxRevProCorLev) {
		this.nontaxRevProCorLev = nontaxRevProCorLev;
	}

	public BigDecimal getTaxRevProCorProportion() {
		return taxRevProCorProportion;
	}

	
	public void setTaxRevProCorProportion(BigDecimal taxRevProCorProportion) {
		this.taxRevProCorProportion = taxRevProCorProportion;
	}

	public BigDecimal getGrantHigherAuthority() {
		return grantHigherAuthority;
	}

	public void setGrantHigherAuthority(BigDecimal grantHigherAuthority) {
		this.grantHigherAuthority = grantHigherAuthority;
	}

	public BigDecimal getReturnRev() {
		return returnRev;
	}

	public void setReturnRev(BigDecimal returnRev) {
		this.returnRev = returnRev;
	}

	public BigDecimal getGenTransferPayRev() {
		return genTransferPayRev;
	}

	public void setGenTransferPayRev(BigDecimal genTransferPayRev) {
		this.genTransferPayRev = genTransferPayRev;
	}

	public BigDecimal getSpecTransferPayRev() {
		return specTransferPayRev;
	}

	public void setSpecTransferPayRev(BigDecimal specTransferPayRev) {
		this.specTransferPayRev = specTransferPayRev;
	}

	public BigDecimal getGovFundRev() {
		return govFundRev;
	}

	public void setGovFundRev(BigDecimal govFundRev) {
		this.govFundRev = govFundRev;
	}

	public BigDecimal getWholeProGovFundBudgetRev() {
		return wholeProGovFundBudgetRev;
	}

	public void setWholeProGovFundBudgetRev(BigDecimal wholeProGovFundBudgetRev) {
		this.wholeProGovFundBudgetRev = wholeProGovFundBudgetRev;
	}

	public BigDecimal getProGovFundBudgetRev() {
		return proGovFundBudgetRev;
	}

	public void setProGovFundBudgetRev(BigDecimal proGovFundBudgetRev) {
		this.proGovFundBudgetRev = proGovFundBudgetRev;
	}

	public BigDecimal getFundBudgetRevProCorLev() {
		return fundBudgetRevProCorLev;
	}

	public void setFundBudgetRevProCorLev(BigDecimal fundBudgetRevProCorLev) {
		this.fundBudgetRevProCorLev = fundBudgetRevProCorLev;
	}

	public BigDecimal getLandLeasingRev() {
		return landLeasingRev;
	}

	public void setLandLeasingRev(BigDecimal landLeasingRev) {
		this.landLeasingRev = landLeasingRev;
	}

	public BigDecimal getExtraBudgetFinanceSpecAccountRev() {
		return extraBudgetFinanceSpecAccountRev;
	}

	public void setExtraBudgetFinanceSpecAccountRev(BigDecimal extraBudgetFinanceSpecAccountRev) {
		this.extraBudgetFinanceSpecAccountRev = extraBudgetFinanceSpecAccountRev;
	}

	public BigDecimal getFiscalExp() {
		return fiscalExp;
	}

	public void setFiscalExp(BigDecimal fiscalExp) {
		this.fiscalExp = fiscalExp;
	}

	public BigDecimal getPubFiscalExp() {
		return pubFiscalExp;
	}

	public void setPubFiscalExp(BigDecimal pubFiscalExp) {
		this.pubFiscalExp = pubFiscalExp;
	}

	public BigDecimal getProBudgetExp() {
		return proBudgetExp;
	}

	public void setProBudgetExp(BigDecimal proBudgetExp) {
		this.proBudgetExp = proBudgetExp;
	}

	public BigDecimal getBudgetExpProCorLev() {
		return budgetExpProCorLev;
	}

	public void setBudgetExpProCorLev(BigDecimal budgetExpProCorLev) {
		this.budgetExpProCorLev = budgetExpProCorLev;
	}

	public BigDecimal getGovFundExp() {
		return govFundExp;
	}

	public void setGovFundExp(BigDecimal govFundExp) {
		this.govFundExp = govFundExp;
	}

	public BigDecimal getWholeProGovFundBudgetExp() {
		return wholeProGovFundBudgetExp;
	}

	public void setWholeProGovFundBudgetExp(BigDecimal wholeProGovFundBudgetExp) {
		this.wholeProGovFundBudgetExp = wholeProGovFundBudgetExp;
	}

	public BigDecimal getProGovFundBudgetExp() {
		return proGovFundBudgetExp;
	}

	public void setProGovFundBudgetExp(BigDecimal proGovFundBudgetExp) {
		this.proGovFundBudgetExp = proGovFundBudgetExp;
	}

	public BigDecimal getFundBudgetExpProCorLev() {
		return fundBudgetExpProCorLev;
	}

	public void setFundBudgetExpProCorLev(BigDecimal fundBudgetExpProCorLev) {
		this.fundBudgetExpProCorLev = fundBudgetExpProCorLev;
	}

	public BigDecimal getExtraBudgetFinanceSpecAccountExp() {
		return extraBudgetFinanceSpecAccountExp;
	}

	public void setExtraBudgetFinanceSpecAccountExp(BigDecimal extraBudgetFinanceSpecAccountExp) {
		this.extraBudgetFinanceSpecAccountExp = extraBudgetFinanceSpecAccountExp;
	}

	public BigDecimal getFinanceSelfSufficiencyRate() {
		return financeSelfSufficiencyRate;
	}

	public void setFinanceSelfSufficiencyRate(BigDecimal financeSelfSufficiencyRate) {
		this.financeSelfSufficiencyRate = financeSelfSufficiencyRate;
	}

	public BigDecimal getTotalIssuanceLocalBonds() {
		return totalIssuanceLocalBonds;
	}

	public void setTotalIssuanceLocalBonds(BigDecimal totalIssuanceLocalBonds) {
		this.totalIssuanceLocalBonds = totalIssuanceLocalBonds;
	}

	public BigDecimal getGovDebt() {
		return govDebt;
	}

	public void setGovDebt(BigDecimal govDebt) {
		this.govDebt = govDebt;
	}

	public BigDecimal getGovDebtGen() {
		return govDebtGen;
	}

	public void setGovDebtGen(BigDecimal govDebtGen) {
		this.govDebtGen = govDebtGen;
	}

	public BigDecimal getGovDebtSpec() {
		return govDebtSpec;
	}

	public void setGovDebtSpec(BigDecimal govDebtSpec) {
		this.govDebtSpec = govDebtSpec;
	}

	public BigDecimal getWholeProGovDebtBal() {
		return wholeProGovDebtBal;
	}

	public void setWholeProGovDebtBal(BigDecimal wholeProGovDebtBal) {
		this.wholeProGovDebtBal = wholeProGovDebtBal;
	}

	public BigDecimal getWholeProGovDebtBalGen() {
		return wholeProGovDebtBalGen;
	}

	public void setWholeProGovDebtBalGen(BigDecimal wholeProGovDebtBalGen) {
		this.wholeProGovDebtBalGen = wholeProGovDebtBalGen;
	}

	public BigDecimal getWholeProGovDebtBalSpec() {
		return wholeProGovDebtBalSpec;
	}

	public void setWholeProGovDebtBalSpec(BigDecimal wholeProGovDebtBalSpec) {
		this.wholeProGovDebtBalSpec = wholeProGovDebtBalSpec;
	}

	public BigDecimal getDebtBalProCorLev() {
		return debtBalProCorLev;
	}

	public void setDebtBalProCorLev(BigDecimal debtBalProCorLev) {
		this.debtBalProCorLev = debtBalProCorLev;
	}

	public BigDecimal getDebtBalProCorLevGen() {
		return debtBalProCorLevGen;
	}

	public void setDebtBalProCorLevGen(BigDecimal debtBalProCorLevGen) {
		this.debtBalProCorLevGen = debtBalProCorLevGen;
	}

	public BigDecimal getDebtBalProCorLevSpec() {
		return debtBalProCorLevSpec;
	}

	public void setDebtBalProCorLevSpec(BigDecimal debtBalProCorLevSpec) {
		this.debtBalProCorLevSpec = debtBalProCorLevSpec;
	}

	public BigDecimal getGovDebtLimit() {
		return govDebtLimit;
	}

	public void setGovDebtLimit(BigDecimal govDebtLimit) {
		this.govDebtLimit = govDebtLimit;
	}

	public BigDecimal getGovDebtLimitGen() {
		return govDebtLimitGen;
	}

	public void setGovDebtLimitGen(BigDecimal govDebtLimitGen) {
		this.govDebtLimitGen = govDebtLimitGen;
	}

	public BigDecimal getGovDebtLimitSpec() {
		return govDebtLimitSpec;
	}

	public void setGovDebtLimitSpec(BigDecimal govDebtLimitSpec) {
		this.govDebtLimitSpec = govDebtLimitSpec;
	}

	public BigDecimal getDebtLimitProCorLev() {
		return debtLimitProCorLev;
	}

	public void setDebtLimitProCorLev(BigDecimal debtLimitProCorLev) {
		this.debtLimitProCorLev = debtLimitProCorLev;
	}

	public BigDecimal getDebtLimitProCorLevGen() {
		return debtLimitProCorLevGen;
	}

	public void setDebtLimitProCorLevGen(BigDecimal debtLimitProCorLevGen) {
		this.debtLimitProCorLevGen = debtLimitProCorLevGen;
	}

	public BigDecimal getDebtLimitProCorLevSpec() {
		return debtLimitProCorLevSpec;
	}

	public void setDebtLimitProCorLevSpec(BigDecimal debtLimitProCorLevSpec) {
		this.debtLimitProCorLevSpec = debtLimitProCorLevSpec;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "AreaDataDoc [id=" + id + ", selected=" + selected + ", areaChiName=" + areaChiName + ", areaUniCode="
				+ areaUniCode + ", bondYear=" + bondYear + ", bondMonth=" + bondMonth + ", bondQuarter=" + bondQuarter
				+ ", statisticsType=" + statisticsType + ", dataType=" + dataType + ", permanentResidentPop="
				+ permanentResidentPop + ", domicilePop=" + domicilePop + ", urbanPop=" + urbanPop + ", ruralPop="
				+ ruralPop + ", airCargoThroughput=" + airCargoThroughput + ", airFreightTransportVolume="
				+ airFreightTransportVolume + ", airPassengerThroughput=" + airPassengerThroughput
				+ ", airPassengerVolume=" + airPassengerVolume + ", seaportsThroughput=" + seaportsThroughput
				+ ", expresswayMileage=" + expresswayMileage + ", railwayMileage=" + railwayMileage
				+ ", urbanizationRate=" + urbanizationRate + ", perExpUrbanResidents=" + perExpUrbanResidents + ", gdp="
				+ gdp + ", gdpAddValPrimaryIndu=" + gdpAddValPrimaryIndu + ", gdpAddValSecondaryIndu="
				+ gdpAddValSecondaryIndu + ", induAddVal=" + induAddVal + ", gdpAddValTertiaryIndu="
				+ gdpAddValTertiaryIndu + ", gdpPerCapita=" + gdpPerCapita + ", growthGdp=" + growthGdp
				+ ", growthAddValPrimaryIndu=" + growthAddValPrimaryIndu + ", growthAddValSecondaryIndu="
				+ growthAddValSecondaryIndu + ", growthInduOutputVal=" + growthInduOutputVal
				+ ", growthAddValTertiaryIndu=" + growthAddValTertiaryIndu + ", growthGdpPerCapita="
				+ growthGdpPerCapita + ", investFixAssetsWholeSociety=" + investFixAssetsWholeSociety
				+ ", investFixAssets=" + investFixAssets + ", totalRetailSalesConsumerGoods="
				+ totalRetailSalesConsumerGoods + ", totalRetailSalesPerConsumerGoods="
				+ totalRetailSalesPerConsumerGoods + ", totalImportExportDollar=" + totalImportExportDollar
				+ ", totalImportExportPerDollar=" + totalImportExportPerDollar + ", totalImportExport="
				+ totalImportExport + ", totalImportPerExport=" + totalImportPerExport + ", foreignCurrencyDepositsPro="
				+ foreignCurrencyDepositsPro + ", rmbDepositsPro=" + rmbDepositsPro + ", foreignCurrencyLoanBalPro="
				+ foreignCurrencyLoanBalPro + ", rmbLoanBalPro=" + rmbLoanBalPro + ", govReceipts=" + govReceipts
				+ ", pubGovReceipts=" + pubGovReceipts + ", taxRev=" + taxRev + ", nontaxRev=" + nontaxRev
				+ ", taxRevProportion=" + taxRevProportion + ", proPubBudgetRev=" + proPubBudgetRev
				+ ", proPubBudgetTaxRev=" + proPubBudgetTaxRev + ", proPubBudgetNontaxRev=" + proPubBudgetNontaxRev
				+ ", taxRevProProportion=" + taxRevProProportion + ", pubBudgetRevProCorLev=" + pubBudgetRevProCorLev
				+ ", taxRevProCorLev=" + taxRevProCorLev + ", nontaxRevProCorLev=" + nontaxRevProCorLev
				+ ", taxRevProCorProportion=" + taxRevProCorProportion + ", grantHigherAuthority="
				+ grantHigherAuthority + ", returnRev=" + returnRev + ", genTransferPayRev=" + genTransferPayRev
				+ ", specTransferPayRev=" + specTransferPayRev + ", govFundRev=" + govFundRev
				+ ", wholeProGovFundBudgetRev=" + wholeProGovFundBudgetRev + ", proGovFundBudgetRev="
				+ proGovFundBudgetRev + ", fundBudgetRevProCorLev=" + fundBudgetRevProCorLev + ", landLeasingRev="
				+ landLeasingRev + ", extraBudgetFinanceSpecAccountRev=" + extraBudgetFinanceSpecAccountRev
				+ ", fiscalExp=" + fiscalExp + ", pubFiscalExp=" + pubFiscalExp + ", proBudgetExp=" + proBudgetExp
				+ ", budgetExpProCorLev=" + budgetExpProCorLev + ", govFundExp=" + govFundExp
				+ ", wholeProGovFundBudgetExp=" + wholeProGovFundBudgetExp + ", proGovFundBudgetExp="
				+ proGovFundBudgetExp + ", fundBudgetExpProCorLev=" + fundBudgetExpProCorLev
				+ ", extraBudgetFinanceSpecAccountExp=" + extraBudgetFinanceSpecAccountExp
				+ ", financeSelfSufficiencyRate=" + financeSelfSufficiencyRate + ", totalIssuanceLocalBonds="
				+ totalIssuanceLocalBonds + ", govDebt=" + govDebt + ", govDebtGen=" + govDebtGen + ", govDebtSpec="
				+ govDebtSpec + ", wholeProGovDebtBal=" + wholeProGovDebtBal + ", wholeProGovDebtBalGen="
				+ wholeProGovDebtBalGen + ", wholeProGovDebtBalSpec=" + wholeProGovDebtBalSpec + ", debtBalProCorLev="
				+ debtBalProCorLev + ", debtBalProCorLevGen=" + debtBalProCorLevGen + ", debtBalProCorLevSpec="
				+ debtBalProCorLevSpec + ", govDebtLimit=" + govDebtLimit + ", govDebtLimitGen=" + govDebtLimitGen
				+ ", govDebtLimitSpec=" + govDebtLimitSpec + ", debtLimitProCorLev=" + debtLimitProCorLev
				+ ", debtLimitProCorLevGen=" + debtLimitProCorLevGen + ", debtLimitProCorLevSpec="
				+ debtLimitProCorLevSpec + "]";
	}
	
	

}
