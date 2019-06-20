package com.innodealing.model.dm.bond.ccxe;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class BondCredChanPK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Column(name="RATE_ID", length=19)
    private Long rateId;
    
    /**
     * 
     */
    @Column(name="BOND_UNI_CODE", length=19)
    private Long bondUniCode;

    /**
     * @return the rateId
     */
    public Long getRateId() {
        return rateId;
    }

    /**
     * @param rateId the rateId to set
     */
    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }

    /**
     * @return the bondUniCode
     */
    public Long getBondUniCode() {
        return bondUniCode;
    }

    /**
     * @param bondUniCode the bondUniCode to set
     */
    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bondUniCode == null) ? 0 : bondUniCode.hashCode());
        result = prime * result + ((rateId == null) ? 0 : rateId.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BondCredChanPK other = (BondCredChanPK) obj;
        if (bondUniCode == null) {
            if (other.bondUniCode != null)
                return false;
        } else if (!bondUniCode.equals(other.bondUniCode))
            return false;
        if (rateId == null) {
            if (other.rateId != null)
                return false;
        } else if (!rateId.equals(other.rateId))
            return false;
        return true;
    }
    
    
}
