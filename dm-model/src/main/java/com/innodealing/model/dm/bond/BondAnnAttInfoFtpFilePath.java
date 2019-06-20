package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
@Entity
@Table(name="t_bond_ann_att_info")
public class BondAnnAttInfoFtpFilePath implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "id")
    @Column(name="id")
	private Integer id;
	
	@ApiModelProperty(value = "公告id: t_bond_ann_info表id")
    @Column(name="ann_id")
	private Integer annId;
	
	@ApiModelProperty(value = "附件类型:pdf txt xls等")
    @Column(name="att_type")
	private String attType;
	
	@ApiModelProperty(value = "附件来源链接url")
    @Column(name="src_url")
	private String srcUrl;
	
	@ApiModelProperty(value = "附件上传oss服务器下载地址")
    @Column(name="ftp_file_path")
	private String ftpFilePath;
	
	@ApiModelProperty(value = "附件名")
    @Column(name="file_name")
	private String fileName;
	
	@ApiModelProperty(value = "公告连接MD5值，用于防止重复 insert")
    @Column(name="src_url_md5")
	private String srcUrlMd5;

	@ApiModelProperty(value = "发布日期")
	@Column(name="publish_date")
	private String publishDate;
//	
//	@ApiModelProperty(value = "最后更新时间")
//	@Column(name="last_update_time")
//	private Date lastUpdateTime;
	
	@ApiModelProperty(value = "file_md5")
	@Column(name="file_md5")
	private String fileMd5;
	
	
	
	
	public Integer getId() {
		return id;
	}

	public String getPublishDate() {
//		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
//		return myFmt.format(publishDate);
		return publishDate;
	}

	public String getFtpFilePath() {
//		StringBuffer sb = new StringBuffer("");
//		if(ftpFilePath != null || ftpFilePath!=""){
//			String ftpFilePaths = null;
//			ftpFilePaths = "http://"+configOss.bucketName+".oss-cn-hangzhou.aliyuncs.com"+ftpFilePath;
//			sb.append(ftpFilePaths);
//		}
//		return sb.toString();
		return ftpFilePath;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPublishDate(String string) {
		this.publishDate = string;
	}

	public void setFtpFilePath(String ftpFilePath) {
		this.ftpFilePath = ftpFilePath;
	}

	

	public Integer getAnnId() {
		return annId;
	}

	public String getAttType() {
		return attType;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setAnnId(Integer annId) {
		this.annId = annId;
	}

	public void setAttType(String attType) {
		this.attType = attType;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public String getSrcUrlMd5() {
		return srcUrlMd5;
	}

//	public String getLastUpdateTime() {
//		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//		return myFmt.format(lastUpdateTime);
//	}

	public String getFileMd5() {
		return fileMd5;
	}

	

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setSrcUrlMd5(String srcUrlMd5) {
		this.srcUrlMd5 = srcUrlMd5;
	}

//	public void setLastUpdateTime(Date lastUpdateTime) {
//		this.lastUpdateTime = lastUpdateTime;
//	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	@Override
	public String toString() {
		return "BondAnnAttInfoFtpFilePath [id=" + id + ", annId=" + annId + ", attType=" + attType + ", srcUrl="
				+ srcUrl + ", ftpFilePath=" + ftpFilePath + ", fileName=" + fileName + ", srcUrlMd5=" + srcUrlMd5
				+ ", publishDate=" + publishDate + ", fileMd5=" + fileMd5 + "]";
	}

	
}
