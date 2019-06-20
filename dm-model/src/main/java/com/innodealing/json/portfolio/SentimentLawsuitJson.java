package com.innodealing.json.portfolio;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/** 
* @author feng.ma
* @date 2017年9月5日 上午11:35:59 
* @describe 
*/
public class SentimentLawsuitJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "公司名称")
	private String comChiName;
	
	@ApiModelProperty(value = "序列号 该条记录的唯一标识号")
	private String serialNo;

	@ApiModelProperty(value = "公司ID")
	private Long comUniCode;
	
	@ApiModelProperty(value = "唯一标识")
	private Long index;
	
	@ApiModelProperty(value = "公告类型名称")
	private String ptypeName;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "案号")
	private String caseNo;
	
	@ApiModelProperty(value = "案由")
	private String caseReason;
	
	@ApiModelProperty(value = "诉讼地位")
	private String lawStatus;

	@ApiModelProperty(value = "发布法院")
	private String court;

	@ApiModelProperty(value = "案件日期")
	private Date caseDate;

	@ApiModelProperty(value = "发布日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date pubDate;

	@ApiModelProperty(value = "省份")
	private String province;

	@ApiModelProperty(value = "城市")
	private String city;

	@ApiModelProperty(value = "关键词")
	private String keyWords;
	
	@ApiModelProperty(value = "公告内容")
	private String pDesc;

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Long getIndex() {
		return index;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getPtypeName() {
		return ptypeName;
	}

	public void setPtypeName(String ptypeName) {
		this.ptypeName = ptypeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getCaseReason() {
		return caseReason;
	}

	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}

	public String getLawStatus() {
		return lawStatus;
	}

	public void setLawStatus(String lawStatus) {
		this.lawStatus = lawStatus;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public Date getCaseDate() {
		return caseDate;
	}

	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}
	
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getpDesc() {
		return pDesc;
	}

	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}
	
}
