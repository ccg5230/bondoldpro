/**
 * BondInfo.java
 * com.innodealing.model.dm.bond
 *
 * Function： innodealing.bond_info的实体类  
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年5月12日 		yig
 *
 * Copyright (c) 2017, DealingMatrix All Rights Reserved.
*/

package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:BondInfo 
 * Function: innodealing.bond_info的实体类 
 * Reason:  
 *
 * @author yig
 * @version
 * @since Ver 1.1
 * @Date 2017年5月12日 下午2:20:03
 *
 * @see
 */
public class BondInfo implements Serializable {

    /**
     * serialVersionUID:
     *
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 1L;
    private String code;
    private String name;
    //private String type;
    //private String rating;
   // private String comRating;
    //private String end;
    //private String seq;
    private Double rate;
    private Date updateTime;
   // private Integer status;
    private Date createTime;
   // private Integer fromSpider;
   // private Integer fromFile;
    private Integer bondCode;
    private String exerciseYield;
    private String netValuation;
    private Date data_date;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getBondCode() {
		return bondCode;
	}
	public void setBondCode(Integer bondCode) {
		this.bondCode = bondCode;
	}
	public String getExerciseYield() {
		return exerciseYield;
	}
	public void setExerciseYield(String exerciseYield) {
		this.exerciseYield = exerciseYield;
	}
	public String getNetValuation() {
		return netValuation;
	}
	public void setNetValuation(String netValuation) {
		this.netValuation = netValuation;
	}
	public Date getData_date() {
		return data_date;
	}
	public void setData_date(Date data_date) {
		this.data_date = data_date;
	}
	@Override
	public String toString() {
		return "BondInfo [code=" + code + ", name=" + name + ", rate=" + rate + ", updateTime=" + updateTime
				+ ", createTime=" + createTime + ", bondCode=" + bondCode + ", exerciseYield=" + exerciseYield
				+ ", netValuation=" + netValuation + ", data_date=" + data_date + "]";
	}

    

}
