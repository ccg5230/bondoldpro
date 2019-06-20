package com.innodealing.bond.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BondUserIndu {
	
	@ApiModelProperty(value = "行业分类")
	private Integer induClass;

    /**
     * @return the induClass
     */
    public Integer getInduClass() {
        return induClass;
    }

    /**
     * @param induClass the induClass to set
     */
    public void setInduClass(Integer induClass) {
        this.induClass = induClass;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondUserIndu [" + (induClass != null ? "induClass=" + induClass : "") + "]";
    }

	
}
