package com.innodealing.model;

import java.math.BigDecimal;
import java.util.Date;

public class BondEstYtm {
	
	String code;
	String name; 
	Double rate;
	Double estCleanPrice;
	Double optionYield;
	Date dataDate;
	Long bondCode;


	/**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

	/**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the rate
     */
    public Double getRate() {
        return rate;
    }
    /**
     * @param rate the rate to set
     */
    public void setRate(Double rate) {
        this.rate = rate;
    }
    
	public Double getEstCleanPrice() {
		return estCleanPrice;
	}
	public void setEstCleanPrice(Double estCleanPrice) {
		this.estCleanPrice = estCleanPrice;
	}
	
	public Double getOptionYield() {
		return optionYield;
	}
	
	public void setOptionYield(Double optionYield) {
		this.optionYield = optionYield;
	}

    public Date getDataDate() {
		return dataDate;
	}
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}
	
	public Long getBondCode() {
		return bondCode;
	}
	public void setBondCode(Long bondCode) {
		this.bondCode = bondCode;
	}
	@Override
	public String toString() {
		return "BondEstYtm [" + (code != null ? "code=" + code + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (rate != null ? "rate=" + rate + ", " : "")
				+ (estCleanPrice != null ? "estCleanPrice=" + estCleanPrice + ", " : "")
				+ (optionYield != null ? "optionYield=" + optionYield + ", " : "")
				+ (dataDate != null ? "dataDate=" + dataDate + ", " : "")
				+ (bondCode != null ? "bondCode=" + bondCode : "") + "]";
	}
	

}
