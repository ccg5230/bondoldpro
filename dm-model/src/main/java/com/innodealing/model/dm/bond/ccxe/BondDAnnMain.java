/**
 * BondDAnnMain.java
 * com.innodealing.model.dm.bond.ccxe
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年12月14日 		chungaochen
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.model.dm.bond.ccxe;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:BondDAnnMain 
 *  中诚信D_ANN_MAIN公告主表domain
 *
 * @author chungaochen
 * @version
 * @since Ver 1.1
 * @Date 2017年12月14日 上午9:58:19
 *
 * @see
 */
public class BondDAnnMain implements Serializable {

    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 1L;

    private String id;
    private Integer isValid;
    private Date createTime;
    private Date updateTime;
    private Date ccxeId;
    private Long annId;//公告唯一标识
    private Date declDate;//公告日期
    private String annTitle;//公告标题
    private Long oriCode;//公告来源,关联PUB_INFO_ORI.INFO_ORI_CODE
    private Date perfRepDate;//报告截止日期
    private Integer isAtt;//是否附带附件:bond_ccxe.pub_par where PAR_SYS_CODE=1013 
    private String textConte;//全文内容
    private String attTitle;//附件标题
    private Integer attTypePar;//附件类型参数:bond_ccxe.pub_par where PAR_SYS_CODE=2010
    private String filePath;//附件路径
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getIsValid() {
        return isValid;
    }
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Date getCcxeId() {
        return ccxeId;
    }
    public void setCcxeId(Date ccxeId) {
        this.ccxeId = ccxeId;
    }
    public Long getAnnId() {
        return annId;
    }
    public void setAnnId(Long annId) {
        this.annId = annId;
    }
    public Date getDeclDate() {
        return declDate;
    }
    public void setDeclDate(Date declDate) {
        this.declDate = declDate;
    }
    public String getAnnTitle() {
        return annTitle;
    }
    public void setAnnTitle(String annTitle) {
        this.annTitle = annTitle;
    }
    public Long getOriCode() {
        return oriCode;
    }
    public void setOriCode(Long oriCode) {
        this.oriCode = oriCode;
    }
    public Date getPerfRepDate() {
        return perfRepDate;
    }
    public void setPerfRepDate(Date perfRepDate) {
        this.perfRepDate = perfRepDate;
    }
    public Integer getIsAtt() {
        return isAtt;
    }
    public void setIsAtt(Integer isAtt) {
        this.isAtt = isAtt;
    }
    public String getTextConte() {
        return textConte;
    }
    public void setTextConte(String textConte) {
        this.textConte = textConte;
    }
    public String getAttTitle() {
        return attTitle;
    }
    public void setAttTitle(String attTitle) {
        this.attTitle = attTitle;
    }
    public Integer getAttTypePar() {
        return attTypePar;
    }
    public void setAttTypePar(Integer attTypePar) {
        this.attTypePar = attTypePar;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public String toString() {
        return "BondDAnnMain [id=" + id + ", isValid=" + isValid + ", createTime=" + createTime + ", updateTime=" + updateTime + ", ccxeId=" + ccxeId
                + ", annId=" + annId + ", declDate=" + declDate + ", annTitle=" + annTitle + ", oriCode=" + oriCode + ", perfRepDate=" + perfRepDate
                + ", isAtt=" + isAtt + ", textConte=" + textConte + ", attTitle=" + attTitle + ", attTypePar=" + attTypePar + ", filePath=" + filePath + "]";
    }

    
}
