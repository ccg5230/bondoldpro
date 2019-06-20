package com.innodealing.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author xiaochao
 * @time 2017年8月25日
 * @description:
 */
@JsonInclude(Include.NON_NULL)
public class BondRadarSchemaVO implements Serializable {
	private static final long serialVersionUID = -3311402107675389837L;

	@ApiModelProperty(value = "radarId")
	private Integer radarId;

	@ApiModelProperty(value = "雷达类型名称")
	private String radarTypeName;

	public Integer getRadarId() {
		return radarId;
	}

	public void setRadarId(Integer radarId) {
		this.radarId = radarId;
	}

	public String getRadarTypeName() {
		return radarTypeName;
	}

	public void setRadarTypeName(String radarTypeName) {
		this.radarTypeName = radarTypeName;
	}
}
