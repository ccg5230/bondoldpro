package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 违约概率历史
 *
 */
@Document(collection="bond_pd_hist")
public class BondPdHistDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7350513135645574096L;
	
	private final static int QUARTER_TO_VIEW = 5;

	@Id
	private Long comUniCode;

	private List<BondPdHistRec> pd;

	/**
	 * @return the com_uni_code
	 */
	public Long getComUniCode() { 
		return comUniCode;
	}

	/**
	 * @param com_uni_code the com_uni_code to set
	 */
	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	/**
	 * @return the pd
	 */
	public List<BondPdHistRec> getPd() {
		return pd;
	}

	/**
	 * @param pd the pd to set
	 */
	public void setPd(List<BondPdHistRec> pd) {
		this.pd = pd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondPdHistDoc [" + (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
				+ (pd != null ? "pd=" + pd : "") + "]";
	}

	


}
