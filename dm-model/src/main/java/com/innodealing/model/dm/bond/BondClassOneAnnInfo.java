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
public class BondClassOneAnnInfo {

    @Id
    @Column(name="id")
    private Long id;
    
    @ApiModelProperty(value = "公告标题")
    @Column(name="ann_title")
    private String annTitle;
    
    @ApiModelProperty(value = "公告标题链接")
    @Column(name="ann_title_link")
    private String annTitleLink;
    
    @ApiModelProperty(value = "公告时间")
    @Column(name="decl_date_time")
    private Date declDateTime;
    
    @ApiModelProperty(value = "公告来源网站网址")
    @Column(name="ann_site_url")
    private String annSiteUrl;
    
    @ApiModelProperty(value = "公告来源网站名称")
    @Column(name="ann_site_name")
    private String annSiteName;
    
    @ApiModelProperty(value = "公告所处网站板块路径")
    @Column(name="ann_site_path")
    private String annSitePath;
    
    @ApiModelProperty(value = "是否关联债券:0-否 1-是")
    @Column(name="is_relevance_bond")
    private String isRelevanceBond;
    
    @ApiModelProperty(value = "关联债券id:t_bond_basic_info表bond_uni_code")
    @Column(name="bond_uni_code")
    private Long bondUniCode;
    
    @ApiModelProperty(value = "是否附带附件:0-否 1-是")
    @Column(name="is_att")
    private String isAtt;
    
    @ApiModelProperty(value = "创建时间")
    @Column(name="create_time")
    private Date createTime;
    
    @ApiModelProperty(value = "附件连接MD5值，用于防止重复 insert")
    @Column(name="title_link_md5")
    private String titleLinkMd5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnnTitle() {
        return annTitle;
    }

    public void setAnnTitle(String annTitle) {
        this.annTitle = annTitle;
    }

    public String getAnnTitleLink() {
        return annTitleLink;
    }

    public void setAnnTitleLink(String annTitleLink) {
        this.annTitleLink = annTitleLink;
    }

    public Date getDeclDateTime() {
        return declDateTime;
    }

    public void setDeclDateTime(Date declDateTime) {
        this.declDateTime = declDateTime;
    }

    public String getAnnSiteUrl() {
        return annSiteUrl;
    }

    public void setAnnSiteUrl(String annSiteUrl) {
        this.annSiteUrl = annSiteUrl;
    }

    public String getAnnSiteName() {
        return annSiteName;
    }

    public void setAnnSiteName(String annSiteName) {
        this.annSiteName = annSiteName;
    }

    public String getAnnSitePath() {
        return annSitePath;
    }

    public void setAnnSitePath(String annSitePath) {
        this.annSitePath = annSitePath;
    }

    public String getIsRelevanceBond() {
        return isRelevanceBond;
    }

    public void setIsRelevanceBond(String isRelevanceBond) {
        this.isRelevanceBond = isRelevanceBond;
    }

    public Long getBondUniCode() {
        return bondUniCode;
    }

    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }

    public String getIsAtt() {
        return isAtt;
    }

    public void setIsAtt(String isAtt) {
        this.isAtt = isAtt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitleLinkMd5() {
        return titleLinkMd5;
    }

    public void setTitleLinkMd5(String titleLinkMd5) {
        this.titleLinkMd5 = titleLinkMd5;
    }
    
    
    
}

