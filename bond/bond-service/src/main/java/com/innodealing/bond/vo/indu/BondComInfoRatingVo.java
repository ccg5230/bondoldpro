package com.innodealing.bond.vo.indu;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class BondComInfoRatingVo  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "评级机构")
	private String chiShortName;
	
	@ApiModelProperty(value = "评级时间")
	@JsonFormat(pattern="yyyy/MM/dd")
	private Date rateWritDate;
	
	@ApiModelProperty(value = "展望")
	private String rateProsPar;
	
	@ApiModelProperty(value = "评级")
	private String rating;
	
	@ApiModelProperty(value = "评级观点")
	private String ratePoint;
	
	@ApiModelProperty(value = "正面信息")
	private String cceAdvt;
	
	@ApiModelProperty(value = "负面信息")
	private String cceDisadvt;
	
	@ApiModelProperty(value = "关注点")
	private String attPoint;
	
	@ApiModelProperty(value = "评级变动")
    private Long ratingDiff;

	public String getChiShortName() {
		return chiShortName;
	}

	public void setChiShortName(String chiShortName) {
		this.chiShortName = chiShortName;
	}

	public Date getRateWritDate() {
		return rateWritDate;
	}

	public void setRateWritDate(Date rateWritDate) {
		this.rateWritDate = rateWritDate;
	}

	public String getRateProsPar() {
		return rateProsPar;
	}

	public void setRateProsPar(String rateProsPar) {
		this.rateProsPar = rateProsPar;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRatePoint() {
		return ratePoint;
	}

	public void setRatePoint(String ratePoint) {
		this.ratePoint = ratePoint;
	}

	public String getCceAdvt() {
		return cceAdvt;
	}

	public void setCceAdvt(String cceAdvt) {
		this.cceAdvt = cceAdvt;
	}

	public String getCceDisadvt() {
		return cceDisadvt;
	}

	public void setCceDisadvt(String cceDisadvt) {
		this.cceDisadvt = cceDisadvt;
	}

	public String getAttPoint() {
		return attPoint;
	}

	public void setAttPoint(String attPoint) {
		this.attPoint = attPoint;
	}

	public Long getRatingDiff() {
		return ratingDiff;
	}

	public void setRatingDiff(Long ratingDiff) {
		this.ratingDiff = ratingDiff;
	}
	
	public boolean isNull(Object obj) {
		if (obj == null)
			return true;
		BondComInfoRatingVo other = (BondComInfoRatingVo) obj;
			if (other.attPoint != null)
				return false;
			if (other.cceAdvt != null)
				return false;
			if (other.cceDisadvt != null)
				return false;
			if (other.chiShortName != null)
				return false;
			if (other.ratePoint != null)
				return false;
			if (other.rateProsPar != null)
				return false;
			if (other.rateWritDate != null)
				return false;
			if (other.rating != null)
				return false;
			if (other.ratingDiff != null)
				return false;
		return true;
	}

}
