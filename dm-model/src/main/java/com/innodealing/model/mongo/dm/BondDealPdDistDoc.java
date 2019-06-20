package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document(collection="pd_deal_dist")
public class BondDealPdDistDoc implements Serializable {

	@Id
	@ApiModelProperty(value = "id")
	Integer id;
	
	@ApiModelProperty(value = "违约概率范围")
	String pdRange;
	
	@ApiModelProperty(value = "1M")
	BondPdDealStatsDoc _1M;

	@ApiModelProperty(value = "3M")
	BondPdDealStatsDoc _3M;

	@ApiModelProperty(value = "6M")
	BondPdDealStatsDoc _6M;
	
	@ApiModelProperty(value = "9M")
	BondPdDealStatsDoc _9M;

	@ApiModelProperty(value = "1Y")
	BondPdDealStatsDoc _1Y;

	@ApiModelProperty(value = "3Y")
	BondPdDealStatsDoc _3Y;

	@ApiModelProperty(value = "5Y")
	BondPdDealStatsDoc _5Y;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the _1M
	 */
	public BondPdDealStatsDoc get_1M() {
		return _1M;
	}

	/**
	 * @param _1m the _1M to set
	 */
	public void set_1M(BondPdDealStatsDoc _1m) {
		_1M = _1m;
	}

	/**
	 * @return the _3M
	 */
	public BondPdDealStatsDoc get_3M() {
		return _3M;
	}

	/**
	 * @param _3m the _3M to set
	 */
	public void set_3M(BondPdDealStatsDoc _3m) {
		_3M = _3m;
	}

	/**
	 * @return the _6M
	 */
	public BondPdDealStatsDoc get_6M() {
		return _6M;
	}

	/**
	 * @param _6m the _6M to set
	 */
	public void set_6M(BondPdDealStatsDoc _6m) {
		_6M = _6m;
	}

	/**
	 * @return the _1Y
	 */
	public BondPdDealStatsDoc get_1Y() {
		return _1Y;
	}

	/**
	 * @param _1y the _1Y to set
	 */
	public void set_1Y(BondPdDealStatsDoc _1y) {
		_1Y = _1y;
	}

	/**
	 * @return the _3Y
	 */
	public BondPdDealStatsDoc get_3Y() {
		return _3Y;
	}

	/**
	 * @param _3y the _3Y to set
	 */
	public void set_3Y(BondPdDealStatsDoc _3y) {
		_3Y = _3y;
	}

	/**
	 * @return the _5Y
	 */
	public BondPdDealStatsDoc get_5Y() {
		return _5Y;
	}

	/**
	 * @param _5y the _5Y to set
	 */
	public void set_5Y(BondPdDealStatsDoc _5y) {
		_5Y = _5y;
	}

	/**
	 * @return the pdRange
	 */
	public String getPdRange() {
		return pdRange;
	}

	/**
	 * @param pdRange the pdRange to set
	 */
	public void setPdRange(String pdRange) {
		this.pdRange = pdRange;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondDealPdDistDoc [" + (id != null ? "id=" + id + ", " : "")
				+ (pdRange != null ? "pdRange=" + pdRange + ", " : "") + (_1M != null ? "_1M=" + _1M + ", " : "")
				+ (_3M != null ? "_3M=" + _3M + ", " : "") + (_6M != null ? "_6M=" + _6M + ", " : "")
				+ (_9M != null ? "_9M=" + _9M + ", " : "") + (_1Y != null ? "_1Y=" + _1Y + ", " : "")
				+ (_3Y != null ? "_3Y=" + _3Y + ", " : "") + (_5Y != null ? "_5Y=" + _5Y + ", " : "")
				+ "]";
	}

	/**
	 * @return the _9M
	 */
	public BondPdDealStatsDoc get_9M() {
		return _9M;
	}

	/**
	 * @param _9m the _9M to set
	 */
	public void set_9M(BondPdDealStatsDoc _9m) {
		_9M = _9m;
	}


}
