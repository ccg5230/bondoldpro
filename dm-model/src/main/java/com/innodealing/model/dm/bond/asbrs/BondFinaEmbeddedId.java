package com.innodealing.model.dm.bond.asbrs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 联合主键
 */
public class BondFinaEmbeddedId implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 公司代码
	 */
    @Column(length=19)
    private Long COMP_ID;
    
	/**
	 * 报表日期
	 */
    @Column(length=10)
	@Temporal(TemporalType.DATE)
    private Date FIN_DATE;

	public Long getCOMP_ID() {
		return COMP_ID;
	}

	public void setCOMP_ID(Long cOMP_ID) {
		COMP_ID = cOMP_ID;
	}

	public Date getFIN_DATE() {
		return FIN_DATE;
	}

	public void setFIN_DATE(Date fIN_DATE) {
		FIN_DATE = fIN_DATE;
	}

}
