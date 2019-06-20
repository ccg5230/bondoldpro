package com.innodealing.datacanal.model;

/**
 * 债券唯一编码映射
 * 
 * @author 赵正来
 * 
 */
public class BondUniCodeMap {

	/**
	 * 中诚信唯一编码
	 */
	private Long bondUniCode;

	/**
	 * Dm数据中心信唯一编码
	 */
	private Long bondUniCodeDataCenter;

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Long getBondUniCodeDataCenter() {
		return bondUniCodeDataCenter;
	}

	public void setBondUniCodeDataCenter(Long bondUniCodeDataCenter) {
		this.bondUniCodeDataCenter = bondUniCodeDataCenter;
	}

	@Override
	public String toString() {
		return "BondUniCodeMap [bondUniCode=" + bondUniCode + ", bondUniCodeDataCenter=" + bondUniCodeDataCenter + "]";
	}

	

}
