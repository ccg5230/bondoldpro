package com.innodealing.model.dm.bond.ccxe;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BondPubParKey implements Serializable{
    
    public BondPubParKey(Long parSysCode, Long parCode) {
        super();
        this.parSysCode = parSysCode;
        this.parCode = parCode;
    }

    private static final long serialVersionUID = 1L;

    @Column(name="PAR_SYS_CODE", length=20)
    private Long parSysCode;
    
    @Column(name="PAR_CODE", length=20)
    private Long parCode;

    /**
     * @return the parSysCode
     */
    public Long getParSysCode() {
        return parSysCode;
    }

    /**
     * @param parSysCode the parSysCode to set
     */
    public void setParSysCode(Long parSysCode) {
        this.parSysCode = parSysCode;
    }

    /**
     * @return the parCode
     */
    public Long getParCode() {
        return parCode;
    }

    /**
     * @param parCode the parCode to set
     */
    public void setParCode(Long parCode) {
        this.parCode = parCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondPubParKey [" + (parSysCode != null ? "parSysCode=" + parSysCode + ", " : "")
                + (parCode != null ? "parCode=" + parCode : "") + "]";
    }

    
}
