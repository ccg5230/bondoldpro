package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;

@Entity
@Table(name = "t_bond_deal_data")
public class BondDeal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** 债券代码 */
	@Column(length = 20, name = "bond_uni_code")
	private Long bondUniCode;

	/** 债券简称 */
	@Column(length = 50, name = "bond_name")
	private String bondName;

	/** 最新收益率 */
	@Column(length = 12, name = "bond_rate")
	private BigDecimal bondRate;

	/** 涨跌 */
	@Column(length = 12, name = "bond_bp")
	private BigDecimal bondBp;

	/** 加权收益率 */
	@Column(length = 12, name = "bond_weighted_rate")
	private BigDecimal bondAddRate;

	/** 交易量(亿) */
	@Column(length = 12, name = "bond_dealing_volume")
	private BigDecimal bondTradingVolume;

	/** 创建时间 */
	@Column(length = 20, name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	/** 修改时间 */
	@Column(length = 20, name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	/** 涨跌趋势 */
	@Column(length = 5, name = "bond_bp_trend")
	private String bondBpTrend;

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public BigDecimal getBondRate() {
		return bondRate;
	}

	public void setBondRate(BigDecimal bondRate) {
		this.bondRate = bondRate;
	}

	public BigDecimal getBondBp() {
		return bondBp;
	}

	public void setBondBp(BigDecimal bondBp) {
		this.bondBp = bondBp;
	}

	public BigDecimal getBondAddRate() {
		return bondAddRate;
	}

	public void setBondAddRate(BigDecimal bondAddRate) {
		this.bondAddRate = bondAddRate;
	}

	public BigDecimal getBondTradingVolume() {
		return bondTradingVolume;
	}

	public void setBondTradingVolume(BigDecimal bondTradingVolume) {
		this.bondTradingVolume = bondTradingVolume;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public String getBondBpTrend() {
		return bondBpTrend;
	}

	public void setBondBpTrend(String bondBpTrend) {
		this.bondBpTrend = bondBpTrend;
	}

	public Boolean isNewDeal(BondDeal rhs) {
		return !Objects.equal(this.bondRate, rhs.getBondRate()) || !Objects.equal(this.bondBp, rhs.getBondBp())
				|| !Objects.equal(this.bondTradingVolume, rhs.getBondTradingVolume())
				|| !Objects.equal(this.bondBpTrend, rhs.getBondBpTrend());
	}

	@Override
	public String toString() {
		return "BondDeal [bondName=" + bondName + ", bondRate=" + bondRate + ", bondBp=" + bondBp + ", bondAddRate="
				+ bondAddRate + ", bondTradingVolume=" + bondTradingVolume + "]";
	}

}
