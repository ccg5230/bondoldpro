package com.innodealing.model.dm.bond.ccxe;

import java.util.Date;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;

@Entity
@Table(name="pub_par")
public class BondPubPar implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Column(name="ID", length=36)
    private String id;
    
    /**
     * 
     */
    @Column(name="ISVALID", length=10)
    private Integer isvalid;
    
    /**
     * 
     */
    @Column(name="CREATETIME", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    
    /**
     * 
     */
    @Column(name="UPDATETIME", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;
    
    /**
     * 
     */
    @Column(nullable=false, name="CCXEID", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ccxeid;
    
    @EmbeddedId
    private BondPubParKey bondPubParKey;
    
    @Column(name="PAR_SYS_NAME")
    private String parSysName;

    @Column(name="PAR_NAME")
    private String parName;
 
    @Column(name="PAR_ENG_NAME")
    private String parEndName;
    
    @Column(name="PAR_UNIT")
    private String parUnit;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the isvalid
     */
    public Integer getIsvalid() {
        return isvalid;
    }

    /**
     * @param isvalid the isvalid to set
     */
    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }

    /**
     * @return the createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime the createtime to set
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return the updatetime
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime the updatetime to set
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * @return the ccxeid
     */
    public Date getCcxeid() {
        return ccxeid;
    }

    /**
     * @param ccxeid the ccxeid to set
     */
    public void setCcxeid(Date ccxeid) {
        this.ccxeid = ccxeid;
    }

  

    /**
     * @return the parSysName
     */
    public String getParSysName() {
        return parSysName;
    }

    /**
     * @param parSysName the parSysName to set
     */
    public void setParSysName(String parSysName) {
        this.parSysName = parSysName;
    }

    /**
     * @return the parName
     */
    public String getParName() {
        return parName;
    }

    /**
     * @param parName the parName to set
     */
    public void setParName(String parName) {
        this.parName = parName;
    }

    /**
     * @return the parEndName
     */
    public String getParEndName() {
        return parEndName;
    }

    /**
     * @param parEndName the parEndName to set
     */
    public void setParEndName(String parEndName) {
        this.parEndName = parEndName;
    }

    /**
     * @return the parUnit
     */
    public String getParUnit() {
        return parUnit;
    }

    /**
     * @param parUnit the parUnit to set
     */
    public void setParUnit(String parUnit) {
        this.parUnit = parUnit;
    }

    /**
     * @return the bondPubParKey
     */
    public BondPubParKey getBondPubParKey() {
        return bondPubParKey;
    }

    /**
     * @param bondPubParKey the bondPubParKey to set
     */
    public void setBondPubParKey(BondPubParKey bondPubParKey) {
        this.bondPubParKey = bondPubParKey;
    }

  
}
