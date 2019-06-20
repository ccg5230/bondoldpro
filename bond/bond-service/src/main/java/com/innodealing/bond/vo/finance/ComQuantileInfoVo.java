package com.innodealing.bond.vo.finance;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 主体指标分位值信息
 * @author 赵正来
 *
 */
public class ComQuantileInfoVo{
	
	@ApiModelProperty("指标名称")
	private String comChiName;
	
	@ApiModelProperty("指标名称")
	private Long comUniCode;
	
	@ApiModelProperty("指标名称")
	List<ComQuantileInfoIndicatorItemVo> quantiles;

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public List<ComQuantileInfoIndicatorItemVo> getQuantiles() {
		return quantiles;
	}

	public void setQuantiles(List<ComQuantileInfoIndicatorItemVo> quantiles) {
		this.quantiles = quantiles;
	}

	@Override
	public String toString() {
		return "ComQuantileInfoVo [comChiName=" + comChiName + ", comUniCode=" + comUniCode + ", quantiles="
				+ quantiles + "]";
	}
	
	
}
