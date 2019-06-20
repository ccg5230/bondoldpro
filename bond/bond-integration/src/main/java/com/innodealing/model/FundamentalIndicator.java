package com.innodealing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * @author feng.ma
 * @date 2017年6月30日 下午5:06:12
 * @describe
 */
@JsonInclude(Include.NON_NULL)
public class FundamentalIndicator {

	private Long bondUniCode;
	private Double estYield;
	private Double estCleanPrice;
	private Double optionYield;
	private Double macd;
	private Double modd;
	private Double convexity;
	private Double staticSpread;
	private Integer staticSpreadInduQuantile;

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Double getEstYield() {
		return estYield;
	}

	public void setEstYield(Double estYield) {
		this.estYield = estYield;
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

	public Double getMacd() {
		return macd;
	}

	public void setMacd(Double macd) {
		this.macd = macd;
	}

	public Double getModd() {
		return modd;
	}

	public void setModd(Double modd) {
		this.modd = modd;
	}

	public Double getConvexity() {
		return convexity;
	}

	public void setConvexity(Double convexity) {
		this.convexity = convexity;
	}

	public Double getStaticSpread() {
		return staticSpread;
	}

	public void setStaticSpread(Double staticSpread) {
		this.staticSpread = staticSpread;
	}

	public Integer getStaticSpreadInduQuantile() {
		return staticSpreadInduQuantile;
	}

	public void setStaticSpreadInduQuantile(Integer staticSpreadInduQuantile) {
		this.staticSpreadInduQuantile = staticSpreadInduQuantile;
	}

	@Override
	public String toString() {
		return "FundamentalIndicator [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
				+ (estYield != null ? "estYield=" + estYield + ", " : "")
				+ (estCleanPrice != null ? "estCleanPrice=" + estCleanPrice + ", " : "")
				+ (optionYield != null ? "optionYield=" + optionYield + ", " : "")
				+ (macd != null ? "macd=" + macd + ", " : "") + (modd != null ? "modd=" + modd + ", " : "")
				+ (convexity != null ? "convexity=" + convexity + ", " : "")
				+ (staticSpread != null ? "staticSpread=" + staticSpread + ", " : "")
				+ (staticSpreadInduQuantile != null ? "staticSpreadInduQuantile=" + staticSpreadInduQuantile : "")
				+ "]";
	}

}
