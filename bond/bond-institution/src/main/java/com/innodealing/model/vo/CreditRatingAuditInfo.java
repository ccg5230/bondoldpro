package com.innodealing.model.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innodealing.model.mysql.BondInstRatingFile;
import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2018年1月3日 下午3:40:33 
 * @version V1.0   
 *
 */
public class CreditRatingAuditInfo {
	
	
	@ApiModelProperty(value = "债券ID")
	@Column(name="bond_uni_code")
	private int bondUniCode;
	
	@ApiModelProperty(value = "债券名称")
	@Column(name="bond_chi_name")
	private String bondChiName;
	
	@ApiModelProperty(value = "发行人ID")
	@Column(name="com_uni_code")
	private int comUniCode;
	
	@ApiModelProperty(value = "发行人简称")
	@Column(name="com_chi_name")
	private String comChiName;
	
	
	@ApiModelProperty(value = "是否延用上次信凭")
	@Column(name="use_old_rating")
	private int useOldRating;




	@ApiModelProperty(value = "评级说明")
	private String ratingDescribe;




	@ApiModelProperty(value = "评级相关资料")
	private List<BondInstRatingFile> bondInstRatingFiles;




	@ApiModelProperty(value = "评级")
	private Integer rating;




	@ApiModelProperty(value = "评级名称")
	@Column(name="rating_name")
	private String ratingName;

	@JsonIgnore
	private String ratingFile;




	@ApiModelProperty(value = "上次版本号")
	@Column(name="old_version")
	private String oldVersion;




	@ApiModelProperty(value = "上次信评分组")
	@Column(name="old_group_name")
	private String oldGroupName;

	@ApiModelProperty(value = "上次所属行业")
	@Column(name="old_indu_name")
	private String oldInduName;
	
	@ApiModelProperty(value = "上次内部评级名称")
	@Column(name="old_rating_name")
	private String oldRatingName;
	
	@ApiModelProperty(value = "上次信评人员")
	@Column(name="old_rating_by_name")
	private String oldRatingByName;
	


	@ApiModelProperty(value = "上次信评日期")
	@Column(name="old_rating_time")
	private String oldRatingTime;
	
	@ApiModelProperty(value = "上次内部评级")
	@Column(name="old_rating")
	private String oldRating;
	

	@ApiModelProperty(value = "上次关联备注")
	@Column(name="old_related_notes")
	private String oldRelatedNotes;
	
	
	@ApiModelProperty(value = "上次投资建议")
	@Column(name="old_investment_describe")
	private String oldInvestmentDescribe;
	
	

	public List<BondInstRatingFile> getBondInstRatingFiles() {
		return bondInstRatingFiles;
	}
	
	
	public int getBondUniCode() {
		return bondUniCode;
	}

	public String getBondChiName() {
		return bondChiName;
	}


	public void setBondChiName(String bondChiName) {
		this.bondChiName = bondChiName;
	}


	public String getComChiName() {
		return comChiName;
	}


	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}


	public int getComUniCode() {
		return comUniCode;
	}


	public String getOldGroupName() {
		return oldGroupName;
	}
	
	public String getOldInduName() {
		return oldInduName;
	}
	
	public String getOldInvestmentDescribe() {
		return oldInvestmentDescribe;
	}
	
	
	public String getOldRating() {
		return oldRating;
	}
	
	
	

	public String getOldRatingByName() {
		return oldRatingByName;
	}

	public String getOldRatingName() {
		return oldRatingName;
	}
	public String getOldRatingTime() {
		return oldRatingTime;
	}

	public String getOldRelatedNotes() {
		return oldRelatedNotes;
	}
	
	public String getOldVersion() {
		return oldVersion;
	}
	
	

	public Integer getRating() {
		return rating;
	}

	public String getRatingDescribe() {
		return ratingDescribe;
	}

	public String getRatingFile() {
		return ratingFile;
	}

	public String getRatingName() {
		return ratingName;
	}

	public int getUseOldRating() {
		return useOldRating;
	}

	

	public void setBondInstRatingFiles(List<BondInstRatingFile> bondInstRatingFiles) {
		this.bondInstRatingFiles = bondInstRatingFiles;
	}

	public void setBondUniCode(int bondUniCode) {
		this.bondUniCode = bondUniCode;
	}






	public void setComUniCode(int comUniCode) {
		this.comUniCode = comUniCode;
	}




	public void setOldGroupName(String oldGroupName) {
		this.oldGroupName = oldGroupName;
	}




	public void setOldInduName(String oldInduName) {
		this.oldInduName = oldInduName;
	}




	public void setOldInvestmentDescribe(String oldInvestmentDescribe) {
		this.oldInvestmentDescribe = oldInvestmentDescribe;
	}

	public void setOldRating(String oldRating) {
		this.oldRating = oldRating;
	}

	public void setOldRatingByName(String oldRatingByName) {
		this.oldRatingByName = oldRatingByName;
	}

	public void setOldRatingName(String oldRatingName) {
		this.oldRatingName = oldRatingName;
	}

	public void setOldRatingTime(String oldRatingTime) {
		this.oldRatingTime = oldRatingTime;
	}

	public void setOldRelatedNotes(String oldRelatedNotes) {
		this.oldRelatedNotes = oldRelatedNotes;
	}

	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public void setRatingDescribe(String ratingDescribe) {
		this.ratingDescribe = ratingDescribe;
	}

	public void setRatingFile(String ratingFile) {
		this.ratingFile = ratingFile;
	}

	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}

	public void setUseOldRating(int useOldRating) {
		this.useOldRating = useOldRating;
	}

	
	

}
