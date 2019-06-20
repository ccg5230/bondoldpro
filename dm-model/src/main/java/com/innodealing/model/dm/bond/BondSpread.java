package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_bond_spread")
public class BondSpread implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BondStaticSpreadKey staticSpreadKey;

	@Column(name = "bond_short_name")
	private String bondShortName;

	@Column(name = "static_spread")
	private BigDecimal staticSpread;

	@Column(name = "static_spread_indu_quartile")
	private Integer staticSpreadInduQuartile;
	
	@Column(name = "static_spread_indu_quartile_sw")
	private Integer staticSpreadInduQuartileSw;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	//induId, induIdSw不存储到数据库，仅仅用户计算行业分位的临时数据
	private Long induId;
	private Long induIdSw;
	
	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public BondStaticSpreadKey getStaticSpreadKey() {
		return staticSpreadKey;
	}

	public void setStaticSpreadKey(BondStaticSpreadKey staticSpreadKey) {
		this.staticSpreadKey = staticSpreadKey;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public BigDecimal getStaticSpread() {
		return staticSpread;
	}

	public void setStaticSpread(BigDecimal staticSpread) {
		this.staticSpread = staticSpread;
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
	
	public Integer getStaticSpreadInduQuartile() {
		return staticSpreadInduQuartile;
	}

	public void setStaticSpreadInduQuartile(Integer staticSpreadInduQuartile) {
		this.staticSpreadInduQuartile = staticSpreadInduQuartile;
	}

	public Integer getStaticSpreadInduQuartileSw() {
		return staticSpreadInduQuartileSw;
	}

	public void setStaticSpreadInduQuartileSw(Integer staticSpreadInduQuartileSw) {
		this.staticSpreadInduQuartileSw = staticSpreadInduQuartileSw;
	}

	@Override
	public String toString() {
		return "BondStaticSpread [staticSpreadKey=" + staticSpreadKey + ", bondShortName=" + bondShortName
				+ ", staticSpread=" + staticSpread + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}
