package com.innodealing.bond.vo.indu;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.bond.vo.finance.ComQuantileInfoIndicatorItemVo;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class BondComInfoPdVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "DM评级")
	private String pd;
	
	@ApiModelProperty(value = "评级机构")
	private String chiShortName;
	
	@ApiModelProperty(value = "评级时间")
	private String reportTime;
	
	@ApiModelProperty(value = "评级变动")
    private Long pdDiff;
	
	@ApiModelProperty("指标")
	private List<ComQuantileInfoIndicatorItemVo> quantiles;

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getChiShortName() {
		return chiShortName;
	}

	public void setChiShortName(String chiShortName) {
		this.chiShortName = chiShortName;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public Long getPdDiff() {
		return pdDiff;
	}

	public void setPdDiff(Long pdDiff) {
		this.pdDiff = pdDiff;
	}

	public List<ComQuantileInfoIndicatorItemVo> getQuantiles() {
		return quantiles;
	}

	public void setQuantiles(List<ComQuantileInfoIndicatorItemVo> quantiles) {
		this.quantiles = quantiles;
	}
	
	public boolean isNull(Object obj) {
		if (obj == null)
			return true;
		BondComInfoPdVo other = (BondComInfoPdVo) obj;
			if (other.chiShortName != null)
				return false;
			if (other.pd != null)
				return false;
			if (other.pdDiff != null)
				return false;
			if (other.quantiles != null && other.quantiles.size()>0)
				return false;
			if (other.reportTime != null)
				return false;
		return true;
	}
	
	

}
