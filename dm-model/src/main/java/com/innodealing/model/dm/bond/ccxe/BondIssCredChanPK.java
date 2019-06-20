package com.innodealing.model.dm.bond.ccxe;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class BondIssCredChanPK implements Serializable {

    
	/**
	 * 
	 */
    @Column(name="RATE_ID", length=19)
    private Long rateId;
    
	/**
	 * 
	 */
    @Column(name="COM_UNI_CODE", length=19)
    private Long comUniCode;

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
     * @return the comUniCode
     */
    public Long getComUniCode() {
        return comUniCode;
    }

    /**
     * @param comUniCode the comUniCode to set
     */
    public void setComUniCode(Long comUniCode) {
        this.comUniCode = comUniCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comUniCode == null) ? 0 : comUniCode.hashCode());
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
        BondIssCredChanPK other = (BondIssCredChanPK) obj;
        if (comUniCode == null) {
            if (other.comUniCode != null)
                return false;
        } else if (!comUniCode.equals(other.comUniCode))
            return false;
        if (rateId == null) {
            if (other.rateId != null)
                return false;
        } else if (!rateId.equals(other.rateId))
            return false;
        return true;
    }
   
}
