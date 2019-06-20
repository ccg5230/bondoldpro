package com.innodealing.model.mongo.dm.bond.creditanalysis;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import javax.persistence.Id;


@Document(collection="bond_credit_analysis_industrial")
public class CreditAnalysisIndustrialDoc {
	
	@Id
	@ApiModelProperty(value="id")
    private Long id;
	
	@ApiModelProperty(value="债券代码")
    private String bondCode  ;//债券代码
	
	@ApiModelProperty(value="债券全称")
    private String bondFullName ;// 债券全称
	
	@ApiModelProperty(value="债券简称")
    private String bondShortName;   //债券简称
	
	@ApiModelProperty(value="债券匹配状态")
    private Integer bondMatchState ; //债券匹配状态  0-未匹配 1-匹配
	
	@ApiModelProperty(value="发行人名称")
    private String  issName ; //发行人名称
	
	@ApiModelProperty(value="发行人统一编码")
    private Long comUniCode; //发行人统一编码
	
	@ApiModelProperty(value="发行人匹配状态 ")
    private Integer issMatchState ;//发行人匹配状态  0-未匹配 1-匹配'
	
	@ApiModelProperty(value="主体评分")
    private String subjectScore; //主体评分
	
	@ApiModelProperty(value="债项评分")
    private String bondScore  ; //债项评分
	
	@ApiModelProperty(value="展望 ")
    private Integer expectation ; //展望   0-负面 1-稳定
	
	@ApiModelProperty(value="主体点评")
    private String subjectComment ; //主体点评
	
	@ApiModelProperty(value="个券点评")
    private String ticketComment ; //个券点评
	
	@ApiModelProperty(value="评级日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rateTime  ; //评级日期
	
	@ApiModelProperty(value="更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ; //更新时间
	
	@ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ; //创建时间
	
	@ApiModelProperty(value="编辑人")
    private Integer userId  ; //编辑人
	
	@ApiModelProperty(value="评级变动")
    private Integer ratingChange ;
	
	@ApiModelProperty(value="序号")
	private int no;
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondFullName() {
		return bondFullName;
	}

	public void setBondFullName(String bondFullName) {
		this.bondFullName = bondFullName;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public Integer getBondMatchState() {
		return bondMatchState;
	}

	public void setBondMatchState(Integer bondMatchState) {
		this.bondMatchState = bondMatchState;
	}

	public String getIssName() {
		return issName;
	}

	public void setIssName(String issName) {
		this.issName = issName;
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Integer getIssMatchState() {
		return issMatchState;
	}

	public void setIssMatchState(Integer issMatchState) {
		this.issMatchState = issMatchState;
	}

	public String getSubjectScore() {
		return subjectScore;
	}

	public void setSubjectScore(String subjectScore) {
		this.subjectScore = subjectScore;
	}

	public String getBondScore() {
		return bondScore;
	}

	public void setBondScore(String bondScore) {
		this.bondScore = bondScore;
	}

	public Integer getExpectation() {
		return expectation;
	}

	public void setExpectation(Integer expectation) {
		this.expectation = expectation;
	}

	public String getSubjectComment() {
		return subjectComment;
	}

	public void setSubjectComment(String subjectComment) {
		this.subjectComment = subjectComment;
	}

	public String getTicketComment() {
		return ticketComment;
	}

	public void setTicketComment(String ticketComment) {
		this.ticketComment = ticketComment;
	}

	public Date getRateTime() {
		return rateTime;
	}

	public void setRateTime(Date rateTime) {
		this.rateTime = rateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRatingChange() {
		return ratingChange;
	}

	public void setRatingChange(Integer ratingChange) {
		this.ratingChange = ratingChange;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rateTime == null) ? 0 : rateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditAnalysisIndustrialDoc other = (CreditAnalysisIndustrialDoc) obj;
		if (rateTime == null) {
			if (other.rateTime != null)
				return false;
		} else if (!rateTime.equals(other.rateTime))
			return false;
		return true;
	}
	
	
	

   
}
