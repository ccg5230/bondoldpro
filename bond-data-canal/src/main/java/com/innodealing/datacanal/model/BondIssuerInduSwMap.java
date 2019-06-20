package com.innodealing.datacanal.model;

/**
 * 主体和申万行业关系
 * 
 * @author 赵正来
 *
 */
public class BondIssuerInduSwMap {

	/**
	 * 主体唯一编码
	 */
	private Long comUniCode;

	/**
	 * 申万行业编码
	 */
	private Long induUniCodeSw;

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Long getInduUniCodeSw() {
		return induUniCodeSw;
	}

	public void setInduUniCodeSw(Long induUniCodeSw) {
		this.induUniCodeSw = induUniCodeSw;
	}

	@Override
	public String toString() {
		return "BondIssuerInduSwMap [comUniCode=" + comUniCode + ", induUniCodeSw=" + induUniCodeSw + "]";
	}

}
