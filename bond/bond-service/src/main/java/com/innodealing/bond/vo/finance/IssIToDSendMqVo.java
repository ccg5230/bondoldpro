package com.innodealing.bond.vo.finance;

import java.io.Serializable;

public class IssIToDSendMqVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主体comunicode
	 */
	private Long comUniCode;
	
	/**
	 * 财报日期
	 */
	private String finDate;

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getFinDate() {
		return finDate;
	}

	public void setFinDate(String finDate) {
		this.finDate = finDate;
	}

	

}
