package com.innodealing.model.dm.bond;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.innodealing.model.dm.bond.BondCity3;

import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondCitySort implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @ApiModelProperty(value="区域经济数据Id")
    private Long id;
	
	@ApiModelProperty(value="是否为所属地区")
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
    private String bondYear ;
        
    @ApiModelProperty(value="月度")
    @Column(name="bond_month")
    private String bondMonth;
        
    @ApiModelProperty(value="季度")
    @Column(name="bond_quarter")
    private String bondQuarter;
    
    @ApiModelProperty(value="数据来源")
    @Column(name="statistics_type")
    private String statisticsType ;
    
    @ApiModelProperty(value="数据类型")
    @Column(name="data_type")
    private String dataType;
    
    @ApiModelProperty(value = "常住人口（万人）")
    @Column(name = "permanent_resident_pop")
    private BondCity3 permanentResidentPop;
    
    @ApiModelProperty(value = "户籍人口（万人）")
    @Column(name = "domicile_pop")
    private BondCity3 domicilePop;
    
    @ApiModelProperty(value = "城镇人口（万人）")
    @Column(name = "urban_pop")
    private BondCity3 urbanPop;
    
    @ApiModelProperty(value = "乡村人口（万人）")
    @Column(name = "rural_pop")
    private BondCity3 ruralPop;
    
    @ApiModelProperty(value = "机场货邮吞吐量（万吨）")
    @Column(name = "air_cargo_throughput")
    private BondCity3 airCargoThroughput;
    
    @ApiModelProperty(value = "机场货邮运输量（万吨）")
    @Column(name = "air_freight_transport_volume")
    private BondCity3 airFreightTransportVolume;
    
    @ApiModelProperty(value = "机场旅客吞吐量（万人次）")
    @Column(name = "air_passenger_throughput")
    private BondCity3 airPassengerThroughput;
    
    @ApiModelProperty(value = "机场旅客运输量（万人次）")
    @Column(name = "air_passenger_volume")
    private BondCity3 airPassengerVolume;
    
    @ApiModelProperty(value = "港口吞吐量（亿吨）")
    @Column(name = "seaports_throughput")
    private BondCity3 seaportsThroughput;
    
    @ApiModelProperty(value = "高速公路里程（公里）")
    @Column(name = "expressway_mileage")
    private BondCity3 expresswayMileage;
    
    @ApiModelProperty(value = "铁路营业里程（公里）")
    @Column(name = "railway_mileage")
    private BondCity3 railwayMileage;
    
    @ApiModelProperty(value = "城镇化率（%）")
    @Column(name = "urbanization_rate")
    private BondCity3 urbanizationRate;
    
    @ApiModelProperty(value = "城镇居民人均消费支出（元）")
    @Column(name = "per_exp_urban_residents")
    private BondCity3 perExpUrbanResidents;
    
    /** 经济 */
    @ApiModelProperty(value = "地区生产总值(亿元)")
    @Column(name = "gdp")
    private BondCity3 gdp;
    
    @ApiModelProperty(value = "地区生产总值增速(%)")
    @Column(name = "growth_gdp")
    private BondCity3 growthGdp;
    
    @ApiModelProperty(value = "地区人均生产总值(元)")
    @Column(name = "gdp_per_capita")
    private BondCity3 gdpPerCapita;
    
    @ApiModelProperty(value = "地区人均生产总值增速(%)")
    @Column(name = "growth_gdp_per_capita")
    private BondCity3 growthGdpPerCapita;
    
    
    @ApiModelProperty(value = "第一产业增加值(亿元)")
    @Column(name = "gdp_add_val_primary_indu")
    private BondCity3 gdpAddValPrimaryIndu;
    
    @ApiModelProperty(value = "第一产业增加值增速(%)")
    @Column(name = "growth_add_val_primary_indu")
    private BondCity3 growthAddValPrimaryIndu;
    
    
    @ApiModelProperty(value = "第二产业增加值(亿元)")
    @Column(name = "gdp_add_val_secondary_indu")
    private BondCity3 gdpAddValSecondaryIndu;
    
    @ApiModelProperty(value = "第二产业增加值增速(%)")
    @Column(name = "growth_add_val_secondary_indu")
    private BondCity3 growthAddValSecondaryIndu;
    
    
    
    @ApiModelProperty(value = "第三产业增加值(亿元)")
    @Column(name = "gdp_add_val_tertiary_indu")
    private BondCity3 gdpAddValTertiaryIndu;
       
    @ApiModelProperty(value = "第三产业增加值增速(%)")
    @Column(name = "growth_add_val_tertiary_indu")
    private BondCity3 growthAddValTertiaryIndu;
    
    @ApiModelProperty(value = "工业增加值(亿元)")
    @Column(name = "indu_add_val")
    private BondCity3 induAddVal;
        
    @ApiModelProperty(value = "工业总产值增速(%)")
    @Column(name = "growth_indu_output_val")
    private BondCity3 growthInduOutputVal;
          
    @ApiModelProperty(value = "全社会固定资产投资(亿元)")
    @Column(name = "invest_Fix_assets_whole_society")
    private BondCity3 investFixAssetsWholeSociety;
    
    @ApiModelProperty(value = "固定资产投资(亿元)")
    @Column(name = "invest_Fix_assets")
    private BondCity3 investFixAssets;
    
    @ApiModelProperty(value = "社会消费品零售总额(亿元)")
    @Column(name = "total_retail_sales_consumer_goods")
    private BondCity3 totalRetailSalesConsumerGoods;
    
    @ApiModelProperty(value="人均社会消费品零售总额(亿元)")
    @Column(name="total_retail_sales_per_consumer_goods")
    private BondCity3 totalRetailSalesPerConsumerGoods;
    
    @ApiModelProperty(value = "进出口总额(亿美元)")
    @Column(name = "total_import_export_dollar")
    private BondCity3 totalImportExportDollar;
    
    @ApiModelProperty(value="人均进出口总额(亿美元)")
    @Column(name="total_import_export_per_dollar")
    private BondCity3 totalImportExportPerDollar;
    
    @ApiModelProperty(value = "进出口总额(亿元)")
    @Column(name = "total_import_export")
    private BondCity3 totalImportExport;
    
    @ApiModelProperty(value="人均进出口总额(亿元)")
    @Column(name="total_import_per_export")
    private BondCity3 totalImportPerExport;
    
    @ApiModelProperty(value = "年末全省金融机构本外币各项存款余额(亿元)")
    @Column(name = "foreign_currency_deposits_pro")
    private BondCity3 foreignCurrencyDepositsPro;
    
    @ApiModelProperty(value = "年末全省金融机构人民币各项存款余额(亿元)")
    @Column(name = "rmb_deposits_pro")
    private BondCity3 rmbDepositsPro;
    
    @ApiModelProperty(value = "年末全省金融机构本外币各项贷款余额(亿元)")
    @Column(name = "foreign_currency_loan_bal_pro")
    private BondCity3 foreignCurrencyLoanBalPro;
    
    @ApiModelProperty(value = "年末全省金融机构人民币各项贷款余额(亿元)")
    @Column(name = "rmb_loan_bal_pro")
    private BondCity3 rmbLoanBalPro;
    
    /** 财政 */
    @ApiModelProperty(value = "财政收入（亿元）")
    @Column(name = "gov_receipts")
    private BondCity3 govReceipts;
    
    @ApiModelProperty(value = "公共财政收入（亿元）")
    @Column(name = "pub_gov_receipts")
    private BondCity3 pubGovReceipts;
    
    @ApiModelProperty(value = "税收收入(亿元)")
    @Column(name = "tax_rev")
    private BondCity3 taxRev;
    
    @ApiModelProperty(value = "非税收收入(亿元)")
    @Column(name = "nontax_rev")
    private BondCity3 nontaxRev;
    
    @ApiModelProperty(value="税收收入占比 ")
    @Column(name="tax_rev_proportion")
    private BondCity3 taxRevProportion;
    
    @ApiModelProperty(value = "省级一般公共预算收入(亿元)")
    @Column(name = "pro_pub_budget_rev")
    private BondCity3 proPubBudgetRev;
    
    @ApiModelProperty(value = "省级一般公共预算收入税收收入(亿元)")
    @Column(name = "pro_pub_budget_tax_rev")
    private BondCity3 proPubBudgetTaxRev;
    
    @ApiModelProperty(value = "省级一般公共预算收入非税收收入(亿元)")
    @Column(name = "pro_pub_budget_nontax_rev")
    private BondCity3 proPubBudgetNontaxRev;
    
    @ApiModelProperty(value=" 省级税收收入占比")
    @Column(name="tax_rev_pro_proportion")
    private BondCity3 taxRevProProportion;
    
    @ApiModelProperty(value = "省本级公共财政预算收入(亿元)")
    @Column(name = "pub_budget_rev_pro_cor_lev")
    private BondCity3 pubBudgetRevProCorLev;
    
    @ApiModelProperty(value = "省本级税收收入(亿元)")
    @Column(name = "tax_rev_pro_cor_lev")
    private BondCity3 taxRevProCorLev;
    
    @ApiModelProperty(value = "省本级非税收收入(亿元)")
    @Column(name = "nontax_rev_pro_cor_lev")
    private BondCity3 nontaxRevProCorLev;
    
    @ApiModelProperty(value="省本级税收收入占比")
    @Column(name="tax_rev_pro_cor_proportion")
    private BondCity3 taxRevProCorProportion ;
    
    @ApiModelProperty(value = "上级补助收入")
    @Column(name = "grant_higher_authority")
    private BondCity3 grantHigherAuthority;
    
    @ApiModelProperty(value = "返还型收入")
    @Column(name = "return_rev")
    private BondCity3 returnRev;
    
    @ApiModelProperty(value = "一般性转移支付收入")
    @Column(name = "gen_transfer_pay_rev")
    private BondCity3 genTransferPayRev;
    
    @ApiModelProperty(value = "专项转移支付收入")
    @Column(name = "spec_transfer_pay_rev")
    private BondCity3 specTransferPayRev;
    
    @ApiModelProperty(value = "政府性基金收入")
    @Column(name = "gov_fund_rev")
    private BondCity3 govFundRev;
    
    @ApiModelProperty(value = "全省政府性基金预算收入(亿元)")
    @Column(name = "whole_pro_gov_fund_budget_rev")
    private BondCity3 wholeProGovFundBudgetRev;
    
    @ApiModelProperty(value = "省级政府性基金预算收入(亿元)")
    @Column(name = "pro_gov_fund_budget_rev")
    private BondCity3 proGovFundBudgetRev;
    
    @ApiModelProperty(value = "省本级政府性基金预算收入(亿元)")
    @Column(name = "fund_budget_rev_pro_cor_lev")
    private BondCity3 fundBudgetRevProCorLev;
    
    @ApiModelProperty(value = "土地出让收入(亿元)")
    @Column(name = "land_leasing_rev")
    private BondCity3 landLeasingRev;
    
    @ApiModelProperty(value = "预算外财政专户收入(亿元)")
    @Column(name = "extra_budget_finance_spec_account_rev")
    private BondCity3 extraBudgetFinanceSpecAccountRev;
    
    @ApiModelProperty(value = "财政支出(亿元)")
    @Column(name = "fiscal_exp")
    private BondCity3 fiscalExp;
    
    @ApiModelProperty(value = "公共财政支出(亿元)")
    @Column(name = "pub_fiscal_exp")
    private BondCity3 pubFiscalExp;
    
    @ApiModelProperty(value = "省级一般公共预算支出(亿元)")
    @Column(name = "pro_budget_exp")
    private BondCity3 proBudgetExp;
    
    @ApiModelProperty(value = "省本级公共财政预算支出(亿元)")
    @Column(name = "budget_exp_pro_cor_lev")
    private BondCity3 budgetExpProCorLev;
    
    @ApiModelProperty(value = "政府性基金支出")
    @Column(name = "gov_fund_exp")
    private BondCity3 govFundExp;
    
    @ApiModelProperty(value = "全省政府性基金预算支出(亿元)")
    @Column(name = "whole_pro_gov_fund_budget_exp")
    private BondCity3 wholeProGovFundBudgetExp;
    
    @ApiModelProperty(value = "省级政府性基金预算支出(亿元)")
    @Column(name = "pro_gov_fund_budget_exp")
    private BondCity3 proGovFundBudgetExp;
    
    @ApiModelProperty(value = "省本级政府性基金预算支出(亿元)")
    @Column(name = "fund_budget_exp_pro_cor_lev")
    private BondCity3 fundBudgetExpProCorLev;
    
    @ApiModelProperty(value = "预算外财政专户支出(亿元)")
    @Column(name = "extra_budget_finance_spec_account_exp")
    private BondCity3 extraBudgetFinanceSpecAccountExp;
    
    @ApiModelProperty(value="财政自给率")
    @Column(name="finance_self_sufficiency_rate")
    private BondCity3 financeSelfSufficiencyRate;
    
    /** 负债 */
    @ApiModelProperty(value="地方债券总发行量")
    @Column(name="total_issuance_local_bonds")
    private BondCity3 totalIssuanceLocalBonds;
    
    @ApiModelProperty(value = "地方政府债务")
    @Column(name = "gov_debt")
    private BondCity3 govDebt;
    
    @ApiModelProperty(value = "地方政府债务一般债务")
    @Column(name = "gov_debt_gen")
    private BondCity3 govDebtGen;
    
    @ApiModelProperty(value = "地方政府债务专项债务")
    @Column(name = "gov_debt_spec")
    private BondCity3 govDebtSpec;
    
    @ApiModelProperty(value = "全省政府债务余额(亿元)")
    @Column(name = "whole_pro_gov_debt_bal")
    private BondCity3 wholeProGovDebtBal;
    
    @ApiModelProperty(value = "全省政府债务余额一般债务(亿元)")
    @Column(name = "whole_pro_gov_debt_bal_gen")
    private BondCity3 wholeProGovDebtBalGen;
    
    @ApiModelProperty(value = "全省政府债务余额专项债务(亿元)")
    @Column(name = "whole_pro_gov_debt_bal_spec")
    private BondCity3 wholeProGovDebtBalSpec;
    
    @ApiModelProperty(value = "省本级政府债务余额(亿元)")
    @Column(name = "debt_bal_pro_cor_lev")
    private BondCity3 debtBalProCorLev;
    
    @ApiModelProperty(value = "省本级政府债务余额一般债务(亿元)")
    @Column(name = "debt_bal_pro_cor_lev_gen")
    private BondCity3 debtBalProCorLevGen;
    
    @ApiModelProperty(value = "省本级政府债务余额专项债务(亿元)")
    @Column(name = "debt_bal_pro_cor_lev_spec")
    private BondCity3 debtBalProCorLevSpec;
    
    @ApiModelProperty(value = "地方政府债务限额(亿元)")
    @Column(name = "gov_debt_limit")
    private BondCity3 govDebtLimit;
    
    @ApiModelProperty(value = "地方政府债务限额一般债务(亿元)")
    @Column(name = "gov_debt_limit_gen")
    private BondCity3 govDebtLimitGen;
    
    @ApiModelProperty(value = "地方政府债务限额专项债务(亿元)")
    @Column(name = "gov_debt_limit_spec")
    private BondCity3 govDebtLimitSpec;
    
    @ApiModelProperty(value = "省本级政府债务限额(亿元)")
    @Column(name = "debt_limit_pro_cor_lev")
    private BondCity3 debtLimitProCorLev;
    
    @ApiModelProperty(value = "省本级政府债务限额一般债务(亿元)")
    @Column(name = "debt_limit_pro_cor_lev_gen")
    private BondCity3 debtLimitProCorLevGen;
    
    @ApiModelProperty(value = "省本级政府债务限额专项债务(亿元)")
    @Column(name = "debt_limit_pro_cor_lev_spec")
    private BondCity3 debtLimitProCorLevSpec;

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

    public String getBondYear() {
        return bondYear;
    }

    public void setBondYear(String bondYear) {
        this.bondYear = bondYear;
    }

    public String getBondMonth() {
        return bondMonth;
    }

    public void setBondMonth(String bondMonth) {
        this.bondMonth = bondMonth;
    }

    public String getBondQuarter() {
        return bondQuarter;
    }

    public void setBondQuarter(String bondQuarter) {
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

    public BondCity3 getPermanentResidentPop() {
        return permanentResidentPop;
    }

    public void setPermanentResidentPop(BondCity3 permanentResidentPop) {
        this.permanentResidentPop = permanentResidentPop;
    }

    public BondCity3 getDomicilePop() {
        return domicilePop;
    }

    public void setDomicilePop(BondCity3 domicilePop) {
        this.domicilePop = domicilePop;
    }

    public BondCity3 getUrbanPop() {
        return urbanPop;
    }

    public void setUrbanPop(BondCity3 urbanPop) {
        this.urbanPop = urbanPop;
    }

    public BondCity3 getRuralPop() {
        return ruralPop;
    }

    public void setRuralPop(BondCity3 ruralPop) {
        this.ruralPop = ruralPop;
    }

    public BondCity3 getAirCargoThroughput() {
        return airCargoThroughput;
    }

    public void setAirCargoThroughput(BondCity3 airCargoThroughput) {
        this.airCargoThroughput = airCargoThroughput;
    }

    public BondCity3 getAirFreightTransportVolume() {
        return airFreightTransportVolume;
    }

    public void setAirFreightTransportVolume(BondCity3 airFreightTransportVolume) {
        this.airFreightTransportVolume = airFreightTransportVolume;
    }

    public BondCity3 getAirPassengerThroughput() {
        return airPassengerThroughput;
    }

    public void setAirPassengerThroughput(BondCity3 airPassengerThroughput) {
        this.airPassengerThroughput = airPassengerThroughput;
    }

    public BondCity3 getAirPassengerVolume() {
        return airPassengerVolume;
    }

    public void setAirPassengerVolume(BondCity3 airPassengerVolume) {
        this.airPassengerVolume = airPassengerVolume;
    }

    public BondCity3 getSeaportsThroughput() {
        return seaportsThroughput;
    }

    public void setSeaportsThroughput(BondCity3 seaportsThroughput) {
        this.seaportsThroughput = seaportsThroughput;
    }

    public BondCity3 getExpresswayMileage() {
        return expresswayMileage;
    }

    public void setExpresswayMileage(BondCity3 expresswayMileage) {
        this.expresswayMileage = expresswayMileage;
    }

    public BondCity3 getRailwayMileage() {
        return railwayMileage;
    }

    public void setRailwayMileage(BondCity3 railwayMileage) {
        this.railwayMileage = railwayMileage;
    }

    public BondCity3 getUrbanizationRate() {
        return urbanizationRate;
    }

    public void setUrbanizationRate(BondCity3 urbanizationRate) {
        this.urbanizationRate = urbanizationRate;
    }

    public BondCity3 getPerExpUrbanResidents() {
        return perExpUrbanResidents;
    }

    public void setPerExpUrbanResidents(BondCity3 perExpUrbanResidents) {
        this.perExpUrbanResidents = perExpUrbanResidents;
    }

    public BondCity3 getGdp() {
        return gdp;
    }

    public void setGdp(BondCity3 gdp) {
        this.gdp = gdp;
    }

    public BondCity3 getGdpAddValPrimaryIndu() {
        return gdpAddValPrimaryIndu;
    }

    public void setGdpAddValPrimaryIndu(BondCity3 gdpAddValPrimaryIndu) {
        this.gdpAddValPrimaryIndu = gdpAddValPrimaryIndu;
    }

    public BondCity3 getGdpAddValSecondaryIndu() {
        return gdpAddValSecondaryIndu;
    }

    public void setGdpAddValSecondaryIndu(BondCity3 gdpAddValSecondaryIndu) {
        this.gdpAddValSecondaryIndu = gdpAddValSecondaryIndu;
    }

    public BondCity3 getInduAddVal() {
        return induAddVal;
    }

    public void setInduAddVal(BondCity3 induAddVal) {
        this.induAddVal = induAddVal;
    }

    public BondCity3 getGdpAddValTertiaryIndu() {
        return gdpAddValTertiaryIndu;
    }

    public void setGdpAddValTertiaryIndu(BondCity3 gdpAddValTertiaryIndu) {
        this.gdpAddValTertiaryIndu = gdpAddValTertiaryIndu;
    }

    public BondCity3 getGdpPerCapita() {
        return gdpPerCapita;
    }

    public void setGdpPerCapita(BondCity3 gdpPerCapita) {
        this.gdpPerCapita = gdpPerCapita;
    }

    public BondCity3 getGrowthGdp() {
        return growthGdp;
    }

    public void setGrowthGdp(BondCity3 growthGdp) {
        this.growthGdp = growthGdp;
    }

    public BondCity3 getGrowthAddValPrimaryIndu() {
        return growthAddValPrimaryIndu;
    }

    public void setGrowthAddValPrimaryIndu(BondCity3 growthAddValPrimaryIndu) {
        this.growthAddValPrimaryIndu = growthAddValPrimaryIndu;
    }

    public BondCity3 getGrowthAddValSecondaryIndu() {
        return growthAddValSecondaryIndu;
    }

    public void setGrowthAddValSecondaryIndu(BondCity3 growthAddValSecondaryIndu) {
        this.growthAddValSecondaryIndu = growthAddValSecondaryIndu;
    }

    public BondCity3 getGrowthInduOutputVal() {
        return growthInduOutputVal;
    }

    public void setGrowthInduOutputVal(BondCity3 growthInduOutputVal) {
        this.growthInduOutputVal = growthInduOutputVal;
    }

    public BondCity3 getGrowthAddValTertiaryIndu() {
        return growthAddValTertiaryIndu;
    }

    public void setGrowthAddValTertiaryIndu(BondCity3 growthAddValTertiaryIndu) {
        this.growthAddValTertiaryIndu = growthAddValTertiaryIndu;
    }

    public BondCity3 getGrowthGdpPerCapita() {
        return growthGdpPerCapita;
    }

    public void setGrowthGdpPerCapita(BondCity3 growthGdpPerCapita) {
        this.growthGdpPerCapita = growthGdpPerCapita;
    }

    public BondCity3 getInvestFixAssetsWholeSociety() {
        return investFixAssetsWholeSociety;
    }

    public void setInvestFixAssetsWholeSociety(BondCity3 investFixAssetsWholeSociety) {
        this.investFixAssetsWholeSociety = investFixAssetsWholeSociety;
    }

    public BondCity3 getInvestFixAssets() {
        return investFixAssets;
    }

    public void setInvestFixAssets(BondCity3 investFixAssets) {
        this.investFixAssets = investFixAssets;
    }

    public BondCity3 getTotalRetailSalesConsumerGoods() {
        return totalRetailSalesConsumerGoods;
    }

    public void setTotalRetailSalesConsumerGoods(BondCity3 totalRetailSalesConsumerGoods) {
        this.totalRetailSalesConsumerGoods = totalRetailSalesConsumerGoods;
    }

    public BondCity3 getTotalRetailSalesPerConsumerGoods() {
        return totalRetailSalesPerConsumerGoods;
    }

    public void setTotalRetailSalesPerConsumerGoods(BondCity3 totalRetailSalesPerConsumerGoods) {
        this.totalRetailSalesPerConsumerGoods = totalRetailSalesPerConsumerGoods;
    }

    public BondCity3 getTotalImportExportDollar() {
        return totalImportExportDollar;
    }

    public void setTotalImportExportDollar(BondCity3 totalImportExportDollar) {
        this.totalImportExportDollar = totalImportExportDollar;
    }

    public BondCity3 getTotalImportExportPerDollar() {
        return totalImportExportPerDollar;
    }

    public void setTotalImportExportPerDollar(BondCity3 totalImportExportPerDollar) {
        this.totalImportExportPerDollar = totalImportExportPerDollar;
    }

    public BondCity3 getTotalImportExport() {
        return totalImportExport;
    }

    public void setTotalImportExport(BondCity3 totalImportExport) {
        this.totalImportExport = totalImportExport;
    }

    public BondCity3 getTotalImportPerExport() {
        return totalImportPerExport;
    }

    public void setTotalImportPerExport(BondCity3 totalImportPerExport) {
        this.totalImportPerExport = totalImportPerExport;
    }

    public BondCity3 getForeignCurrencyDepositsPro() {
        return foreignCurrencyDepositsPro;
    }

    public void setForeignCurrencyDepositsPro(BondCity3 foreignCurrencyDepositsPro) {
        this.foreignCurrencyDepositsPro = foreignCurrencyDepositsPro;
    }

    public BondCity3 getRmbDepositsPro() {
        return rmbDepositsPro;
    }

    public void setRmbDepositsPro(BondCity3 rmbDepositsPro) {
        this.rmbDepositsPro = rmbDepositsPro;
    }

    public BondCity3 getForeignCurrencyLoanBalPro() {
        return foreignCurrencyLoanBalPro;
    }

    public void setForeignCurrencyLoanBalPro(BondCity3 foreignCurrencyLoanBalPro) {
        this.foreignCurrencyLoanBalPro = foreignCurrencyLoanBalPro;
    }

    public BondCity3 getRmbLoanBalPro() {
        return rmbLoanBalPro;
    }

    public void setRmbLoanBalPro(BondCity3 rmbLoanBalPro) {
        this.rmbLoanBalPro = rmbLoanBalPro;
    }

    public BondCity3 getGovReceipts() {
        return govReceipts;
    }

    public void setGovReceipts(BondCity3 govReceipts) {
        this.govReceipts = govReceipts;
    }

    public BondCity3 getPubGovReceipts() {
        return pubGovReceipts;
    }

    public void setPubGovReceipts(BondCity3 pubGovReceipts) {
        this.pubGovReceipts = pubGovReceipts;
    }

    public BondCity3 getTaxRev() {
        return taxRev;
    }

    public void setTaxRev(BondCity3 taxRev) {
        this.taxRev = taxRev;
    }

    public BondCity3 getNontaxRev() {
        return nontaxRev;
    }

    public void setNontaxRev(BondCity3 nontaxRev) {
        this.nontaxRev = nontaxRev;
    }

    public BondCity3 getTaxRevProportion() {
        return taxRevProportion;
    }

    public void setTaxRevProportion(BondCity3 taxRevProportion) {
        this.taxRevProportion = taxRevProportion;
    }

    public BondCity3 getProPubBudgetRev() {
        return proPubBudgetRev;
    }

    public void setProPubBudgetRev(BondCity3 proPubBudgetRev) {
        this.proPubBudgetRev = proPubBudgetRev;
    }

    public BondCity3 getProPubBudgetTaxRev() {
        return proPubBudgetTaxRev;
    }

    public void setProPubBudgetTaxRev(BondCity3 proPubBudgetTaxRev) {
        this.proPubBudgetTaxRev = proPubBudgetTaxRev;
    }

    public BondCity3 getProPubBudgetNontaxRev() {
        return proPubBudgetNontaxRev;
    }

    public void setProPubBudgetNontaxRev(BondCity3 proPubBudgetNontaxRev) {
        this.proPubBudgetNontaxRev = proPubBudgetNontaxRev;
    }

    public BondCity3 getTaxRevProProportion() {
        return taxRevProProportion;
    }

    public void setTaxRevProProportion(BondCity3 taxRevProProportion) {
        this.taxRevProProportion = taxRevProProportion;
    }

    public BondCity3 getPubBudgetRevProCorLev() {
        return pubBudgetRevProCorLev;
    }

    public void setPubBudgetRevProCorLev(BondCity3 pubBudgetRevProCorLev) {
        this.pubBudgetRevProCorLev = pubBudgetRevProCorLev;
    }

    public BondCity3 getTaxRevProCorLev() {
        return taxRevProCorLev;
    }

    public void setTaxRevProCorLev(BondCity3 taxRevProCorLev) {
        this.taxRevProCorLev = taxRevProCorLev;
    }

    public BondCity3 getNontaxRevProCorLev() {
        return nontaxRevProCorLev;
    }

    public void setNontaxRevProCorLev(BondCity3 nontaxRevProCorLev) {
        this.nontaxRevProCorLev = nontaxRevProCorLev;
    }

    public BondCity3 getTaxRevProCorProportion() {
        return taxRevProCorProportion;
    }

    public void setTaxRevProCorProportion(BondCity3 taxRevProCorProportion) {
        this.taxRevProCorProportion = taxRevProCorProportion;
    }

    public BondCity3 getGrantHigherAuthority() {
        return grantHigherAuthority;
    }

    public void setGrantHigherAuthority(BondCity3 grantHigherAuthority) {
        this.grantHigherAuthority = grantHigherAuthority;
    }

    public BondCity3 getReturnRev() {
        return returnRev;
    }

    public void setReturnRev(BondCity3 returnRev) {
        this.returnRev = returnRev;
    }

    public BondCity3 getGenTransferPayRev() {
        return genTransferPayRev;
    }

    public void setGenTransferPayRev(BondCity3 genTransferPayRev) {
        this.genTransferPayRev = genTransferPayRev;
    }

    public BondCity3 getSpecTransferPayRev() {
        return specTransferPayRev;
    }

    public void setSpecTransferPayRev(BondCity3 specTransferPayRev) {
        this.specTransferPayRev = specTransferPayRev;
    }

    public BondCity3 getGovFundRev() {
        return govFundRev;
    }

    public void setGovFundRev(BondCity3 govFundRev) {
        this.govFundRev = govFundRev;
    }

    public BondCity3 getWholeProGovFundBudgetRev() {
        return wholeProGovFundBudgetRev;
    }

    public void setWholeProGovFundBudgetRev(BondCity3 wholeProGovFundBudgetRev) {
        this.wholeProGovFundBudgetRev = wholeProGovFundBudgetRev;
    }

    public BondCity3 getProGovFundBudgetRev() {
        return proGovFundBudgetRev;
    }

    public void setProGovFundBudgetRev(BondCity3 proGovFundBudgetRev) {
        this.proGovFundBudgetRev = proGovFundBudgetRev;
    }

    public BondCity3 getFundBudgetRevProCorLev() {
        return fundBudgetRevProCorLev;
    }

    public void setFundBudgetRevProCorLev(BondCity3 fundBudgetRevProCorLev) {
        this.fundBudgetRevProCorLev = fundBudgetRevProCorLev;
    }

    public BondCity3 getLandLeasingRev() {
        return landLeasingRev;
    }

    public void setLandLeasingRev(BondCity3 landLeasingRev) {
        this.landLeasingRev = landLeasingRev;
    }

    public BondCity3 getExtraBudgetFinanceSpecAccountRev() {
        return extraBudgetFinanceSpecAccountRev;
    }

    public void setExtraBudgetFinanceSpecAccountRev(BondCity3 extraBudgetFinanceSpecAccountRev) {
        this.extraBudgetFinanceSpecAccountRev = extraBudgetFinanceSpecAccountRev;
    }

    public BondCity3 getFiscalExp() {
        return fiscalExp;
    }

    public void setFiscalExp(BondCity3 fiscalExp) {
        this.fiscalExp = fiscalExp;
    }

    public BondCity3 getPubFiscalExp() {
        return pubFiscalExp;
    }

    public void setPubFiscalExp(BondCity3 pubFiscalExp) {
        this.pubFiscalExp = pubFiscalExp;
    }

    public BondCity3 getProBudgetExp() {
        return proBudgetExp;
    }

    public void setProBudgetExp(BondCity3 proBudgetExp) {
        this.proBudgetExp = proBudgetExp;
    }

    public BondCity3 getBudgetExpProCorLev() {
        return budgetExpProCorLev;
    }

    public void setBudgetExpProCorLev(BondCity3 budgetExpProCorLev) {
        this.budgetExpProCorLev = budgetExpProCorLev;
    }

    public BondCity3 getGovFundExp() {
        return govFundExp;
    }

    public void setGovFundExp(BondCity3 govFundExp) {
        this.govFundExp = govFundExp;
    }

    public BondCity3 getWholeProGovFundBudgetExp() {
        return wholeProGovFundBudgetExp;
    }

    public void setWholeProGovFundBudgetExp(BondCity3 wholeProGovFundBudgetExp) {
        this.wholeProGovFundBudgetExp = wholeProGovFundBudgetExp;
    }

    public BondCity3 getProGovFundBudgetExp() {
        return proGovFundBudgetExp;
    }

    public void setProGovFundBudgetExp(BondCity3 proGovFundBudgetExp) {
        this.proGovFundBudgetExp = proGovFundBudgetExp;
    }

    public BondCity3 getFundBudgetExpProCorLev() {
        return fundBudgetExpProCorLev;
    }

    public void setFundBudgetExpProCorLev(BondCity3 fundBudgetExpProCorLev) {
        this.fundBudgetExpProCorLev = fundBudgetExpProCorLev;
    }

    public BondCity3 getExtraBudgetFinanceSpecAccountExp() {
        return extraBudgetFinanceSpecAccountExp;
    }

    public void setExtraBudgetFinanceSpecAccountExp(BondCity3 extraBudgetFinanceSpecAccountExp) {
        this.extraBudgetFinanceSpecAccountExp = extraBudgetFinanceSpecAccountExp;
    }

    public BondCity3 getFinanceSelfSufficiencyRate() {
        return financeSelfSufficiencyRate;
    }

    public void setFinanceSelfSufficiencyRate(BondCity3 financeSelfSufficiencyRate) {
        this.financeSelfSufficiencyRate = financeSelfSufficiencyRate;
    }

    public BondCity3 getTotalIssuanceLocalBonds() {
        return totalIssuanceLocalBonds;
    }

    public void setTotalIssuanceLocalBonds(BondCity3 totalIssuanceLocalBonds) {
        this.totalIssuanceLocalBonds = totalIssuanceLocalBonds;
    }

    public BondCity3 getGovDebt() {
        return govDebt;
    }

    public void setGovDebt(BondCity3 govDebt) {
        this.govDebt = govDebt;
    }

    public BondCity3 getGovDebtGen() {
        return govDebtGen;
    }

    public void setGovDebtGen(BondCity3 govDebtGen) {
        this.govDebtGen = govDebtGen;
    }

    public BondCity3 getGovDebtSpec() {
        return govDebtSpec;
    }

    public void setGovDebtSpec(BondCity3 govDebtSpec) {
        this.govDebtSpec = govDebtSpec;
    }

    public BondCity3 getWholeProGovDebtBal() {
        return wholeProGovDebtBal;
    }

    public void setWholeProGovDebtBal(BondCity3 wholeProGovDebtBal) {
        this.wholeProGovDebtBal = wholeProGovDebtBal;
    }

    public BondCity3 getWholeProGovDebtBalGen() {
        return wholeProGovDebtBalGen;
    }

    public void setWholeProGovDebtBalGen(BondCity3 wholeProGovDebtBalGen) {
        this.wholeProGovDebtBalGen = wholeProGovDebtBalGen;
    }

    public BondCity3 getWholeProGovDebtBalSpec() {
        return wholeProGovDebtBalSpec;
    }

    public void setWholeProGovDebtBalSpec(BondCity3 wholeProGovDebtBalSpec) {
        this.wholeProGovDebtBalSpec = wholeProGovDebtBalSpec;
    }

    public BondCity3 getDebtBalProCorLev() {
        return debtBalProCorLev;
    }

    public void setDebtBalProCorLev(BondCity3 debtBalProCorLev) {
        this.debtBalProCorLev = debtBalProCorLev;
    }

    public BondCity3 getDebtBalProCorLevGen() {
        return debtBalProCorLevGen;
    }

    public void setDebtBalProCorLevGen(BondCity3 debtBalProCorLevGen) {
        this.debtBalProCorLevGen = debtBalProCorLevGen;
    }

    public BondCity3 getDebtBalProCorLevSpec() {
        return debtBalProCorLevSpec;
    }

    public void setDebtBalProCorLevSpec(BondCity3 debtBalProCorLevSpec) {
        this.debtBalProCorLevSpec = debtBalProCorLevSpec;
    }

    public BondCity3 getGovDebtLimit() {
        return govDebtLimit;
    }

    public void setGovDebtLimit(BondCity3 govDebtLimit) {
        this.govDebtLimit = govDebtLimit;
    }

    public BondCity3 getGovDebtLimitGen() {
        return govDebtLimitGen;
    }

    public void setGovDebtLimitGen(BondCity3 govDebtLimitGen) {
        this.govDebtLimitGen = govDebtLimitGen;
    }

    public BondCity3 getGovDebtLimitSpec() {
        return govDebtLimitSpec;
    }

    public void setGovDebtLimitSpec(BondCity3 govDebtLimitSpec) {
        this.govDebtLimitSpec = govDebtLimitSpec;
    }

    public BondCity3 getDebtLimitProCorLev() {
        return debtLimitProCorLev;
    }

    public void setDebtLimitProCorLev(BondCity3 debtLimitProCorLev) {
        this.debtLimitProCorLev = debtLimitProCorLev;
    }

    public BondCity3 getDebtLimitProCorLevGen() {
        return debtLimitProCorLevGen;
    }

    public void setDebtLimitProCorLevGen(BondCity3 debtLimitProCorLevGen) {
        this.debtLimitProCorLevGen = debtLimitProCorLevGen;
    }

    public BondCity3 getDebtLimitProCorLevSpec() {
        return debtLimitProCorLevSpec;
    }

    public void setDebtLimitProCorLevSpec(BondCity3 debtLimitProCorLevSpec) {
        this.debtLimitProCorLevSpec = debtLimitProCorLevSpec;
    }

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

}
