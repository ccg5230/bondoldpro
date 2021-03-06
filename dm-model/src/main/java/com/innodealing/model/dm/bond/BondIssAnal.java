package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="t_bond_iss_anal")
public class BondIssAnal implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = -1299146315832389781L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=19)
    private Long comUniCode;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String comChiName;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String comShiShortName;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer isNew;
    
	/**
	 * 总资产
	 */
    @Column(length=20)
    private BigDecimal totalAssets;
    
	/**
	 * 收益
	 */
    @Column(length=20)
    private BigDecimal revenue;
    
	/**
	 * 当天比率
	 */
    @Column(length=5)
    private BigDecimal currentRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal loanDepositRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal cashAssetsRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal debtAssetRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal interestBearingDebtRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal interestCoverageRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal operatingCashFlowRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal operatingProfitRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal grossProfitRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal returnOnTotalAssets;
    
	/**
	 * 
	 */
    @Column(length=7)
    private BigDecimal receivableTurnoverPeriod;
    
	/**
	 * 
	 */
    @Column(length=7)
    private BigDecimal daysSalesOfInventory;
    
	/**
	 * 
	 */
    @Column(length=7)
    private BigDecimal daysPayablesOutstanding;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal totalAssetsTurnover;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal salesGrowthRatio;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal debtPayingAbility;
    
	/**
	 * 
	 */
    @Column(length=5)
    private BigDecimal debtPayingAbilityChg;
    
	/**
	 * 
	 */
    @Column(length=10)
	@Temporal(TemporalType.DATE)
    private Date analysisDate;
    
    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param comUniCode the comUniCode to set
	 */
    public void setComUniCode(Long comUniCode){
       this.comUniCode = comUniCode;
    }
    
    /**
     * @return the comUniCode 
     */
    public Long getComUniCode(){
       return this.comUniCode;
    }
	
	/**
	 * @param comChiName the comChiName to set
	 */
    public void setComChiName(String comChiName){
       this.comChiName = comChiName;
    }
    
    /**
     * @return the comChiName 
     */
    public String getComChiName(){
       return this.comChiName;
    }
	
	/**
	 * @param comShiShortName the comShiShortName to set
	 */
    public void setComShiShortName(String comShiShortName){
       this.comShiShortName = comShiShortName;
    }
    
    /**
     * @return the comShiShortName 
     */
    public String getComShiShortName(){
       return this.comShiShortName;
    }
	
	/**
	 * @param isNew the isNew to set
	 */
    public void setIsNew(Integer isNew){
       this.isNew = isNew;
    }
    
    /**
     * @return the isNew 
     */
    public Integer getIsNew(){
       return this.isNew;
    }
	
	/**
	 * @param totalAssets the totalAssets to set
	 */
    public void setTotalAssets(BigDecimal totalAssets){
       this.totalAssets = totalAssets;
    }
    
    /**
     * @return the totalAssets 
     */
    public BigDecimal getTotalAssets(){
       return this.totalAssets;
    }
	
	/**
	 * @param revenue the revenue to set
	 */
    public void setRevenue(BigDecimal revenue){
       this.revenue = revenue;
    }
    
    /**
     * @return the revenue 
     */
    public BigDecimal getRevenue(){
       return this.revenue;
    }
	
	/**
	 * @param currentRatio the currentRatio to set
	 */
    public void setCurrentRatio(BigDecimal currentRatio){
       this.currentRatio = currentRatio;
    }
    
    /**
     * @return the currentRatio 
     */
    public BigDecimal getCurrentRatio(){
       return this.currentRatio;
    }
	
	/**
	 * @param loanDepositRatio the loanDepositRatio to set
	 */
    public void setLoanDepositRatio(BigDecimal loanDepositRatio){
       this.loanDepositRatio = loanDepositRatio;
    }
    
    /**
     * @return the loanDepositRatio 
     */
    public BigDecimal getLoanDepositRatio(){
       return this.loanDepositRatio;
    }
	
	/**
	 * @param cashAssetsRatio the cashAssetsRatio to set
	 */
    public void setCashAssetsRatio(BigDecimal cashAssetsRatio){
       this.cashAssetsRatio = cashAssetsRatio;
    }
    
    /**
     * @return the cashAssetsRatio 
     */
    public BigDecimal getCashAssetsRatio(){
       return this.cashAssetsRatio;
    }
	
	/**
	 * @param debtAssetRatio the debtAssetRatio to set
	 */
    public void setDebtAssetRatio(BigDecimal debtAssetRatio){
       this.debtAssetRatio = debtAssetRatio;
    }
    
    /**
     * @return the debtAssetRatio 
     */
    public BigDecimal getDebtAssetRatio(){
       return this.debtAssetRatio;
    }
	
	/**
	 * @param interestBearingDebtRatio the interestBearingDebtRatio to set
	 */
    public void setInterestBearingDebtRatio(BigDecimal interestBearingDebtRatio){
       this.interestBearingDebtRatio = interestBearingDebtRatio;
    }
    
    /**
     * @return the interestBearingDebtRatio 
     */
    public BigDecimal getInterestBearingDebtRatio(){
       return this.interestBearingDebtRatio;
    }
	
	/**
	 * @param interestCoverageRatio the interestCoverageRatio to set
	 */
    public void setInterestCoverageRatio(BigDecimal interestCoverageRatio){
       this.interestCoverageRatio = interestCoverageRatio;
    }
    
    /**
     * @return the interestCoverageRatio 
     */
    public BigDecimal getInterestCoverageRatio(){
       return this.interestCoverageRatio;
    }
	
	/**
	 * @param operatingCashFlowRatio the operatingCashFlowRatio to set
	 */
    public void setOperatingCashFlowRatio(BigDecimal operatingCashFlowRatio){
       this.operatingCashFlowRatio = operatingCashFlowRatio;
    }
    
    /**
     * @return the operatingCashFlowRatio 
     */
    public BigDecimal getOperatingCashFlowRatio(){
       return this.operatingCashFlowRatio;
    }
	
	/**
	 * @param operatingProfitRatio the operatingProfitRatio to set
	 */
    public void setOperatingProfitRatio(BigDecimal operatingProfitRatio){
       this.operatingProfitRatio = operatingProfitRatio;
    }
    
    /**
     * @return the operatingProfitRatio 
     */
    public BigDecimal getOperatingProfitRatio(){
       return this.operatingProfitRatio;
    }
	
	/**
	 * @param grossProfitRatio the grossProfitRatio to set
	 */
    public void setGrossProfitRatio(BigDecimal grossProfitRatio){
       this.grossProfitRatio = grossProfitRatio;
    }
    
    /**
     * @return the grossProfitRatio 
     */
    public BigDecimal getGrossProfitRatio(){
       return this.grossProfitRatio;
    }
	
	/**
	 * @param returnOnTotalAssets the returnOnTotalAssets to set
	 */
    public void setReturnOnTotalAssets(BigDecimal returnOnTotalAssets){
       this.returnOnTotalAssets = returnOnTotalAssets;
    }
    
    /**
     * @return the returnOnTotalAssets 
     */
    public BigDecimal getReturnOnTotalAssets(){
       return this.returnOnTotalAssets;
    }
	
	/**
	 * @param receivableTurnoverPeriod the receivableTurnoverPeriod to set
	 */
    public void setReceivableTurnoverPeriod(BigDecimal receivableTurnoverPeriod){
       this.receivableTurnoverPeriod = receivableTurnoverPeriod;
    }
    
    /**
     * @return the receivableTurnoverPeriod 
     */
    public BigDecimal getReceivableTurnoverPeriod(){
       return this.receivableTurnoverPeriod;
    }
	
	/**
	 * @param daysSalesOfInventory the daysSalesOfInventory to set
	 */
    public void setDaysSalesOfInventory(BigDecimal daysSalesOfInventory){
       this.daysSalesOfInventory = daysSalesOfInventory;
    }
    
    /**
     * @return the daysSalesOfInventory 
     */
    public BigDecimal getDaysSalesOfInventory(){
       return this.daysSalesOfInventory;
    }
	
	/**
	 * @param daysPayablesOutstanding the daysPayablesOutstanding to set
	 */
    public void setDaysPayablesOutstanding(BigDecimal daysPayablesOutstanding){
       this.daysPayablesOutstanding = daysPayablesOutstanding;
    }
    
    /**
     * @return the daysPayablesOutstanding 
     */
    public BigDecimal getDaysPayablesOutstanding(){
       return this.daysPayablesOutstanding;
    }
	
	/**
	 * @param totalAssetsTurnover the totalAssetsTurnover to set
	 */
    public void setTotalAssetsTurnover(BigDecimal totalAssetsTurnover){
       this.totalAssetsTurnover = totalAssetsTurnover;
    }
    
    /**
     * @return the totalAssetsTurnover 
     */
    public BigDecimal getTotalAssetsTurnover(){
       return this.totalAssetsTurnover;
    }
	
	/**
	 * @param salesGrowthRatio the salesGrowthRatio to set
	 */
    public void setSalesGrowthRatio(BigDecimal salesGrowthRatio){
       this.salesGrowthRatio = salesGrowthRatio;
    }
    
    /**
     * @return the salesGrowthRatio 
     */
    public BigDecimal getSalesGrowthRatio(){
       return this.salesGrowthRatio;
    }
	
	/**
	 * @param debtPayingAbility the debtPayingAbility to set
	 */
    public void setDebtPayingAbility(BigDecimal debtPayingAbility){
       this.debtPayingAbility = debtPayingAbility;
    }
    
    /**
     * @return the debtPayingAbility 
     */
    public BigDecimal getDebtPayingAbility(){
       return this.debtPayingAbility;
    }
	
	/**
	 * @param debtPayingAbilityChg the debtPayingAbilityChg to set
	 */
    public void setDebtPayingAbilityChg(BigDecimal debtPayingAbilityChg){
       this.debtPayingAbilityChg = debtPayingAbilityChg;
    }
    
    /**
     * @return the debtPayingAbilityChg 
     */
    public BigDecimal getDebtPayingAbilityChg(){
       return this.debtPayingAbilityChg;
    }
	
	/**
	 * @param analysisDate the analysisDate to set
	 */
    public void setAnalysisDate(Date analysisDate){
       this.analysisDate = analysisDate;
    }
    
    /**
     * @return the analysisDate 
     */
    public Date getAnalysisDate(){
       return this.analysisDate;
    }
}
