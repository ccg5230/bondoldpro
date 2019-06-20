package com.innodealing.model.dm.bond;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_bond_com_ext")
public class BondComExt implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="com_uni_code")
    private Long comUniCode;
    
    @Column(name="com_chi_name")
    private String comChiName;
   
    @Column(name="ama_com_id")
    private Long amaComId;
    
    @Column(name="ama_com_name")
    private String amaComName;

    @Column(name="indu_uni_code")
    private Long induUniCode;

    @Column(name="indu_uni_code_sw")
    private Long induUniCodeSw;
    
    @Column(name="indu_uni_name")
    private String induUniName;
    
    @Column(name="indu_uni_name_l4")
    private String induUniNameL4;
    
    @Column(name="indu_uni_code_l4")
    private Long induUniCodeL4;
 
    
	/**
	 * @return the comUniCode
	 */
	public Long getComUniCode() {
		return comUniCode;
	}

	/**
	 * @param comUniCode the comUniCode to set
	 */
	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	/**
	 * @return the comChiName
	 */
	public String getComChiName() {
		return comChiName;
	}

	/**
	 * @param comChiName the comChiName to set
	 */
	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	/**
	 * @return the amaComId
	 */
	public Long getAmaComId() {
		return amaComId;
	}

	/**
	 * @param amaComId the amaComId to set
	 */
	public void setAmaComId(Long amaComId) {
		this.amaComId = amaComId;
	}

	/**
	 * @return the amaComName
	 */
	public String getAmaComName() {
		return amaComName;
	}

	/**
	 * @param amaComName the amaComName to set
	 */
	public void setAmaComName(String amaComName) {
		this.amaComName = amaComName;
	}

	/**
	 * @return the induUniCode
	 */
	public Long getInduUniCode() {
		return induUniCode;
	}

	/**
	 * @param induUniCode the induUniCode to set
	 */
	public void setInduUniCode(Long induUniCode) {
		this.induUniCode = induUniCode;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondComExt [" + (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
                + (comChiName != null ? "comChiName=" + comChiName + ", " : "")
                + (amaComId != null ? "amaComId=" + amaComId + ", " : "")
                + (amaComName != null ? "amaComName=" + amaComName + ", " : "")
                + (induUniCode != null ? "induUniCode=" + induUniCode + ", " : "")
                + (induUniCodeSw != null ? "induUniCodeSw=" + induUniCodeSw : "") + "]";
    }
    
    /**
     * @return the induUniCodeSw
     */
    public Long getInduUniCodeSw() {
        return induUniCodeSw;
    }

    /**
     * @param induUniCodeSw the induUniCodeSw to set
     */
    public void setInduUniCodeSw(Long induUniCodeSw) {
        this.induUniCodeSw = induUniCodeSw;
    }

	public String getInduUniName() {
		return induUniName;
	}

	public void setInduUniName(String induUniName) {
		this.induUniName = induUniName;
	}

	public String getInduUniNameL4() {
		return induUniNameL4;
	}

	public void setInduUniNameL4(String induUniNameL4) {
		this.induUniNameL4 = induUniNameL4;
	}

	public Long getInduUniCodeL4() {
		return induUniCodeL4;
	}

	public void setInduUniCodeL4(Long induUniCodeL4) {
		this.induUniCodeL4 = induUniCodeL4;
	}
    
}
