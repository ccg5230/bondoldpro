package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="iss_pd_rating") 
public class IssPdRatingDoc  implements Serializable {
	
	@ApiModelProperty(value = "安硕债券主体id")
	@Indexed
	private Long compId;

	@ApiModelProperty(value = "债券主体id")
	private Long comUniCode;

	@ApiModelProperty(value = "安硕债券主体名称")
	private String compName;
	
	@ApiModelProperty(value = "债券主体风险num")
	private Integer pdParNum;
	
	@ApiModelProperty(value = "债券主体风险分数")
	private BigDecimal pdPar;
	
	@ApiModelProperty(value = "安硕债券主体风险评级")
	private String pdRating;
	
	@ApiModelProperty(value = "安硕债券主体风险评级时间")
	private String worstPdTime;
	
	@ApiModelProperty(value = "安硕债券主体风险评级最新更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;

	/**
	 * @return the compId
	 */
	public Long getCompId() {
		return compId;
	}

	/**
	 * @param compId the compId to set
	 */
	public void setCompId(Long compId) {
		this.compId = compId;
	}

	/**
	 * @return the comUniCode
	 */
	public Long getComUniCode() {
		return comUniCode;
	}

	/**
	 * @param comUniCode the comUniCode to set
	 */
	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * @return the pdParNum
	 */
	public Integer getPdParNum() {
		return pdParNum;
	}

	/**
	 * @param pdParNum the pdParNum to set
	 */
	public void setPdParNum(Integer pdParNum) {
		this.pdParNum = pdParNum;
	}

	/**
	 * @return the pdPar
	 */
	public BigDecimal getPdPar() {
		return pdPar;
	}

	/**
	 * @param pdPar the pdPar to set
	 */
	public void setPdPar(BigDecimal pdPar) {
		this.pdPar = pdPar;
	}

	/**
	 * @return the pdRating
	 */
	public String getPdRating() {
		return pdRating;
	}

	/**
	 * @param pdRating the pdRating to set
	 */
	public void setPdRating(String pdRating) {
		this.pdRating = pdRating;
	}

	/**
	 * @return the worstPdTime
	 */
	public String getWorstPdTime() {
		return worstPdTime;
	}

	/**
	 * @param worstPdTime the worstPdTime to set
	 */
	public void setWorstPdTime(String worstPdTime) {
		this.worstPdTime = worstPdTime;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "IssPdRatingDoc [" + (compId != null ? "compId=" + compId + ", " : "")
                + (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
                + (compName != null ? "compName=" + compName + ", " : "")
                + (pdParNum != null ? "pdParNum=" + pdParNum + ", " : "")
                + (pdPar != null ? "pdPar=" + pdPar + ", " : "")
                + (pdRating != null ? "pdRating=" + pdRating + ", " : "")
                + (worstPdTime != null ? "worstPdTime=" + worstPdTime + ", " : "")
                + (lastUpdateTime != null ? "lastUpdateTime=" + lastUpdateTime : "") + "]";
    }

	public IssPdRatingDoc() {
	}
}


