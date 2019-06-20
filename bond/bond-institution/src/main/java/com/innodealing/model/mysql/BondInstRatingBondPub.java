package com.innodealing.model.mysql;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class BondInstRatingBondPub implements Serializable {
    private Integer id;

    private Integer bondUniCode;

    private Integer comUniCode;

    private Integer orgId;

    private String remark;

    private Date createTime;

    private Integer createBy;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBondUniCode() {
        return bondUniCode;
    }

    public void setBondUniCode(Integer bondUniCode) {
        this.bondUniCode = bondUniCode;
    }

    public Integer getComUniCode() {
        return comUniCode;
    }

    public void setComUniCode(Integer comUniCode) {
        this.comUniCode = comUniCode;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BondInstRatingBondPub other = (BondInstRatingBondPub) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBondUniCode() == null ? other.getBondUniCode() == null : this.getBondUniCode().equals(other.getBondUniCode()))
            && (this.getComUniCode() == null ? other.getComUniCode() == null : this.getComUniCode().equals(other.getComUniCode()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBondUniCode() == null) ? 0 : getBondUniCode().hashCode());
        result = prime * result + ((getComUniCode() == null) ? 0 : getComUniCode().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bondUniCode=").append(bondUniCode);
        sb.append(", comUniCode=").append(comUniCode);
        sb.append(", orgId=").append(orgId);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}