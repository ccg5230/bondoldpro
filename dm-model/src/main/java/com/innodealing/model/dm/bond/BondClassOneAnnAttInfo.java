/**
 * BondClassOneAttInfo.java
 * com.innodealing.model.dm.bond
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年11月15日 		chungaochen
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.model.dm.bond;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

/**
 * ClassName:BondClassOneAttInfo
 * Function: 
 * Reason:	 
 *
 * @author   chungaochen
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年11月15日		上午11:50:00
 *
 * @see 	 
 */
public class BondClassOneAnnAttInfo {

    @Id
    @Column(name="id")
    private Long id;
    
    @ApiModelProperty(value = "公告id: t_bond_ann_info表id")
    @Column(name="ann_id")
    private Long annId;
    
    @ApiModelProperty(value = "附件类型:pdf txt xls等")
    @Column(name="att_type")
    private String attType;
    
    @ApiModelProperty(value = "附件来源链接url")
    @Column(name="src_url")
    private String srcUrl;
    
    @ApiModelProperty(value = "附件上传FTP服务器下载地址")
    @Column(name="ftp_file_path")
    private String ftpFilePath;
    
    @ApiModelProperty(value = "附件名")
    @Column(name="file_name")
    private String fileName;
    
    @ApiModelProperty(value = "发布日期")
    @Column(name="publish_date")
    private Date publishDate;
    
    @ApiModelProperty(value = "最后更新时间")
    @Column(name="last_update_time")
    private Date lastUpdateTime;
    
    @ApiModelProperty(value = "上传次数")
    @Column(name="upload_times")
    private Integer uploadTimes;
    
    @ApiModelProperty(value = "公告连接MD5值，用于防止重复 insert")
    @Column(name="src_url_md5")
    private String srcUrlMd5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnnId() {
        return annId;
    }

    public void setAnnId(Long annId) {
        this.annId = annId;
    }

    public String getAttType() {
        return attType;
    }

    public void setAttType(String attType) {
        this.attType = attType;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public String getFtpFilePath() {
        return ftpFilePath;
    }

    public void setFtpFilePath(String ftpFilePath) {
        this.ftpFilePath = ftpFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getSrcUrlMd5() {
        return srcUrlMd5;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getUploadTimes() {
        return uploadTimes;
    }

    public void setUploadTimes(Integer uploadTimes) {
        this.uploadTimes = uploadTimes;
    }

    public void setSrcUrlMd5(String srcUrlMd5) {
        this.srcUrlMd5 = srcUrlMd5;
    }

    
    
}

