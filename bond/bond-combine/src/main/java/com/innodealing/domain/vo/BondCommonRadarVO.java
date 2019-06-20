package com.innodealing.domain.vo;

import com.innodealing.model.dm.bond.BondFavoriteRadarMapping;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年6月7日
 * @description: 
 */
public class BondCommonRadarVO extends BondFavoriteRadarMapping {
	private static final long serialVersionUID = 7547911290965426093L;
	
	@ApiModelProperty(value = "状态[0-未选中;1-选中]")
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
