package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * 违约概率历史
 *
 */
@JsonInclude(Include.NON_NULL)
public class BondPdHistRec implements Serializable {
	
	@ApiModelProperty(value = "主体量化风险等级")
	private String pd;
	
	@ApiModelProperty(value = "主体量化风险值")
	private Long pdNum;
	
	@ApiModelProperty(value = "上期对比")
	private Long pdDiff;
	
	@ApiModelProperty(value = "评级时间")
	@JsonFormat(pattern="yyyy/MM/dd")
	private Date date;

	@ApiModelProperty(value = "财务质量评级")
	private Double finQuality;
	
	@ApiModelProperty(value = "风险警告标志, 量化风险等级<=CCC")
	private Boolean riskWarning;
	
	static Integer RISK_WARNING_PD_THRESHOLD = 18; //CCC 
	
	@ApiModelProperty(value = "评级时间(季度)")
	private String quarter;
	
	/**
	 * @return the pd
	 */
	public String getPd() {
	    //两位有效小数
	    //if(pd != null){
	    //    return pd.setScale(2, BigDecimal.ROUND_HALF_UP);
	    //}
		return pd;
	}

	/**
	 * @param pd the pd to set
	 */
	public void setPd(String pd) {
		this.pd = pd;
	}

	/**
	 * @return the pdDiff
	 */
	public Long getPdDiff() {
	    //if(pdDiff != null){
        //    return pdDiff.setScale(2, BigDecimal.ROUND_HALF_UP);
       // }
		return pdDiff;
	}

	/**
	 * @param pdDiff the pdDiff to set
	 */
	public void setPdDiff(Long pdDiff) {
		this.pdDiff = -pdDiff;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the finQuality
	 */
	public Double getFinQuality() {
		return finQuality;
	}

	/**
	 * @param finQuality the finQuality to set
	 */
	public void setFinQuality(Double finQuality) {
		this.finQuality = finQuality;
	}

	/**
	 * @return the pdNum
	 */
	public Long getPdNum() {
		return pdNum;
	}

	/**
	 * @param pdNum the pdNum to set
	 */
	public void setPdNum(Long pdNum) {
		this.pdNum = pdNum;
		if(pdNum != null){
			this.riskWarning = (pdNum >= RISK_WARNING_PD_THRESHOLD);
		}
	}

	/**
	 * @return the riskWarning
	 */
	public Boolean getRiskWarning() {
		return riskWarning;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public void setRiskWarning(Boolean riskWarning) {
		this.riskWarning = riskWarning;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return "BondPdHistRec [pd=" + pd + ", pdNum=" + pdNum + ", pdDiff=" + pdDiff + ", date=" + dateFormat.format(date)
				+ ", finQuality=" + finQuality + ", riskWarning=" + riskWarning + ", quarter=" + quarter + "]";
	}
	
	

	/**
	 * 季度转财报日期
	 * @param quarter
	 * @return
	 */
	public static Date quarterToDate(String quarter){
		if(quarter == null) return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] finDate = {"yyyy-03-31", "yyyy-06-30", "yyyy-09-30", "yyyy-12-31"};
		int index = quarter.indexOf("/");
		String year = quarter.substring(0, index);
		String qdesc = quarter.substring(quarter.indexOf("/")+1, quarter.length());
		//季度值
		int q = getQuarterByQuarterDesc(qdesc);
		if(q > 0 && q < 5){
			try {
				return dateFormat.parse(finDate[q -1].replace("yyyy", year));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}
	
    private static int getQuarterByQuarterDesc(String desc){
    	int quarter = 0;
    	switch(desc.trim()){
    	case "一季报":
    		quarter = 1;
    		break;
    	case "中报":
    		quarter = 2;
    		break;
    	case "三季报":
    		quarter = 3;
    		break;
    	case "年报":
    		quarter = 4;
    		break;
    	}
    	
    	return quarter;
    }
	
	public static void main(String[] args) {
		System.out.println(quarterToDate("2016/年报"));
		System.out.println(quarterToDate("2016/三季报"));
//		System.out.println(quarterToDate("2016/Q2"));
//		System.out.println(quarterToDate("2016/Q1"));
	}
	
	
}
