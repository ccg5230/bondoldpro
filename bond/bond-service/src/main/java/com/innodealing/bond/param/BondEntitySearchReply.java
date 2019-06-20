package com.innodealing.bond.param;

import java.util.List;

import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class BondEntitySearchReply {
	
	@ApiModelProperty(value = "发行人列表")
	List<BondComInfoDoc> issuers;
	@ApiModelProperty(value = "债券列表")
	List<BondBasicInfoDoc> bonds;
	@ApiModelProperty(value = "发行人总数")
	Long issuerCount;
	@ApiModelProperty(value = "债劵总数")
	Long bondCount;
	
	
	/**
	 * @return the issuers
	 */
	public List<BondComInfoDoc> getIssuers() {
		return issuers;
	}
	/**
	 * @param issuers the issuers to set
	 */
	public void setIssuers(List<BondComInfoDoc> issuers) {
		this.issuers = issuers;
	}
	/**
	 * @return the bonds
	 */
	public List<BondBasicInfoDoc> getBonds() {
		return bonds;
	}
	/**
	 * @param bonds the bonds to set
	 */
	public void setBonds(List<BondBasicInfoDoc> bonds) {
		this.bonds = bonds;
	}
	
	public Long getIssuerCount() {
		return issuerCount;
	}
	
	public void setIssuerCount(Long issuerCount) {
		this.issuerCount = issuerCount;
	}
	
	public Long getBondCount() {
		return bondCount;
	}
	
	public void setBondCount(Long bondCount) {
		this.bondCount = bondCount;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondEntitySearchResult [" + (issuers != null ? "issuers=" + issuers + ", " : "")
				+ (bonds != null ? "bonds=" + bonds : "") + "]";
	}

}
