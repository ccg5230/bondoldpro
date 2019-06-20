package com.innodealing.domain.bond;

import java.util.Date;

public class MaturityDto {
	
	private Long bondUniCode;
	//付息日
	private String yearPayDate;
	//行权日
	private String exerPayDate;
	//理论到期日
	private Date theoEndDate;
	//到期日与当前相差的天数
	private Integer theoDiffdays;
	
	public Long getBondUniCode() {
		return bondUniCode;
	}
	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}
	public String getYearPayDate() {
		return yearPayDate;
	}
	public void setYearPayDate(String yearPayDate) {
		this.yearPayDate = yearPayDate;
	}
	public String getExerPayDate() {
		return exerPayDate;
	}
	public void setExerPayDate(String exerPayDate) {
		this.exerPayDate = exerPayDate;
	}
	public Date getTheoEndDate() {
		return theoEndDate;
	}
	public void setTheoEndDate(Date theoEndDate) {
		this.theoEndDate = theoEndDate;
	}
	public Integer getTheoDiffdays() {
		return theoDiffdays;
	}
	public void setTheoDiffdays(Integer theoDiffdays) {
		this.theoDiffdays = theoDiffdays;
	}
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MaturityDto [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
                + (yearPayDate != null ? "yearPayDate=" + yearPayDate + ", " : "")
                + (exerPayDate != null ? "exerPayDate=" + exerPayDate + ", " : "")
                + (theoEndDate != null ? "theoEndDate=" + theoEndDate + ", " : "")
                + (theoDiffdays != null ? "theoDiffdays=" + theoDiffdays : "") + "]";
    }
	public MaturityDto() {
	}
}
