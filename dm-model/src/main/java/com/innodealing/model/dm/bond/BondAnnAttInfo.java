package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;


public class BondAnnAttInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String srcUrl;
	
	private Date publishDate;
	
	private String ftpFilePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getFtpFilePath() {
		return ftpFilePath;
	}

	public void setFtpFilePath(String ftpFilePath) {
		this.ftpFilePath = ftpFilePath;
	}

	@Override
	public String toString() {
		return "BondAnnAttInfo [id=" + id + ", srcUrl=" + srcUrl + ", publishDate=" + publishDate + ", ftpFilePath="
				+ ftpFilePath + "]";
	}
	
	
}
