package com.innodealing.bond.vo.msg;

import io.swagger.annotations.ApiModelProperty;

public class BasicBondVo {
	
	public BasicBondVo() { }
	
	public BasicBondVo(Long bondId, String bondName) {
		this.bondId = bondId;
		this.bondName = bondName;
	}

    @ApiModelProperty(value = "债券UniCode,bondId")
    private Long bondId;
    
    @ApiModelProperty(value = "债券代码")
    private String bondCode;
    
    @ApiModelProperty(value = "债券名称")
    private String bondName;

	/**
	 * @return the bondId
	 */
	public Long getBondId() {
		return bondId;
	}

	/**
	 * @param bondId the bondId to set
	 */
	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	/**
	 * @return the bondCode
	 */
	public String getBondCode() {
		return bondCode;
	}

	/**
	 * @param bondCode the bondCode to set
	 */
	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	/**
	 * @return the bondName
	 */
	public String getBondName() {
		return bondName;
	}

	/**
	 * @param bondName the bondName to set
	 */
	public void setBondName(String bondName) {
		this.bondName = bondName;
	}
    
}
