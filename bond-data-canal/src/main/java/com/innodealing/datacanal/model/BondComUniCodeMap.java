package com.innodealing.datacanal.model;
/**
 * 主体唯一编码映射
 * @author 赵正来
 * 
 */
public class BondComUniCodeMap {

	/**
	 * 中诚信唯一编码
	 */
	private Long comUniCode;
	
	/**
	 * Dm数据中心信唯一编码
	 */
	private Long  comUniCodeDataCenter;
	
	/**
	 * 申万行业id
	 */
	private Long induUniCodeSw;

	public Long getComUniCode() {
		return comUniCode;
	}

	

	public Long getComUniCodeDataCenter() {
		return comUniCodeDataCenter;
	}



	public void setComUniCodeDataCenter(Long comUniCodeDataCenter) {
		this.comUniCodeDataCenter = comUniCodeDataCenter;
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
		return "BondComUniCodeMap [comUniCode=" + comUniCode + ", comUniCodeDataCenter=" + comUniCodeDataCenter
				+ ", induUniCodeSw=" + induUniCodeSw + "]";
	}

	

	
	
}
