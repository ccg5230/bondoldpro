package com.innodealing.model.dm.bond;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_bond_type_xref")
public class BondTypeXRef implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6283275245930862304L;

	@Id
    @Column(name="ccxe_code")
    private Integer ccxeCode;
    
    @Column(name="ccxe_name")
    private String ccxeName;
   
    @Column(name="dm_filter_code")
    private Integer dmFilterCode;
    
    @Column(name="dm_filter_name")
    private String dmFilterName;

    @Column(name="dm_info_code")
    private Integer dmInfoCode;
    
    @Column(name="dm_info_name")
    private String dmInfoName;

	/**
	 * @return the ccxeCode
	 */
	public Integer getCcxeCode() {
		return ccxeCode;
	}

	/**
	 * @param ccxeCode the ccxeCode to set
	 */
	public void setCcxeCode(Integer ccxeCode) {
		this.ccxeCode = ccxeCode;
	}

	/**
	 * @return the ccxeName
	 */
	public String getCcxeName() {
		return ccxeName;
	}

	/**
	 * @param ccxeName the ccxeName to set
	 */
	public void setCcxeName(String ccxeName) {
		this.ccxeName = ccxeName;
	}

	/**
	 * @return the dmFilterCode
	 */
	public Integer getDmFilterCode() {
		return dmFilterCode;
	}

	/**
	 * @param dmFilterCode the dmFilterCode to set
	 */
	public void setDmFilterCode(Integer dmFilterCode) {
		this.dmFilterCode = dmFilterCode;
	}

	/**
	 * @return the dmFilterName
	 */
	public String getDmFilterName() {
		return dmFilterName;
	}

	/**
	 * @param dmFilterName the dmFilterName to set
	 */
	public void setDmFilterName(String dmFilterName) {
		this.dmFilterName = dmFilterName;
	}

	/**
	 * @return the dmInfoCode
	 */
	public Integer getDmInfoCode() {
		return dmInfoCode;
	}

	/**
	 * @param dmInfoCode the dmInfoCode to set
	 */
	public void setDmInfoCode(Integer dmInfoCode) {
		this.dmInfoCode = dmInfoCode;
	}

	/**
	 * @return the dmInfoName
	 */
	public String getDmInfoName() {
		return dmInfoName;
	}

	/**
	 * @param dmInfoName the dmInfoName to set
	 */
	public void setDmInfoName(String dmInfoName) {
		this.dmInfoName = dmInfoName;
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
		return "BondTypeXRef [" + (ccxeCode != null ? "ccxeCode=" + ccxeCode + ", " : "")
				+ (ccxeName != null ? "ccxeName=" + ccxeName + ", " : "")
				+ (dmFilterCode != null ? "dmFilterCode=" + dmFilterCode + ", " : "")
				+ (dmFilterName != null ? "dmFilterName=" + dmFilterName + ", " : "")
				+ (dmInfoCode != null ? "dmInfoCode=" + dmInfoCode + ", " : "")
				+ (dmInfoName != null ? "dmInfoName=" + dmInfoName : "") + "]";
	}


}
