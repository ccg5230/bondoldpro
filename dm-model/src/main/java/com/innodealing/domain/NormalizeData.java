package com.innodealing.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NormalizeData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String msg_id;
	private String qq_group;
	private String qq_num;
	private String jid;
	private Date rcv_dt;
	private String message;
	private Integer type;
	private Integer sub_type = 0;
	private Integer side;
	private Integer amount;
	private Integer ratebp;
	private Integer ratetype;
	private BigDecimal rate_high;
	private BigDecimal rate;
	private Integer tenor_low;
	private Integer tenor_high;
	private Integer counter_party_bank;
	private Integer counter_party_rural;
	private Integer counter_party_dvp;
	private Integer counter_party_dep_inst;
	private Integer collateral_rate;
	private Integer collateral_credit;
	private Integer collateral_clear_sh;
	private Integer collateral_clear_cn;
	private Integer collateral_clear_cd;
	private Integer collateral_rating;
	private Integer collateral_cp;
	private Integer appointment;
	private String bond_code;
	private String bond_name;
	private Integer bond_issuer_rating;
	private Integer bond_rating;
	private BigDecimal bond_range_low;
	private BigDecimal bond_range_high;
	private String mobile;
	private String phone;
	private String allphone;
	private Integer source;
	private String Vuser_name;
	private String Vname;
	private String Vinst;
	private String Vsub_inst;
	private String Vqq;
	private String Vphone_office;
	private String Vinst_short;
	private String Vinst_type;
	private String Vinst_addr;
	private Integer status;
	private String rawcontent;
	private String remark;
	private Integer bondissuer;
	private String bond_issuer_rating_agency;
	private String bond_rating_agency;
	private String qq_group_name;
	private Integer anonymous;
	private BigDecimal bond_yield_low;
	private BigDecimal bond_yield_high;
	private Integer postfrom;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getQq_group() {
		return qq_group;
	}
	public void setQq_group(String qq_group) {
		this.qq_group = qq_group;
	}
	public String getQq_num() {
		return qq_num;
	}
	public void setQq_num(String qq_num) {
		this.qq_num = qq_num;
	}
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public Date getRcv_dt() {
		return rcv_dt;
	}
	public void setRcv_dt(Date rcv_dt) {
		this.rcv_dt = rcv_dt;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSub_type() {
		return sub_type;
	}
	public void setSub_type(Integer sub_type) {
		this.sub_type = sub_type;
	}
	public Integer getSide() {
		return side;
	}
	public void setSide(Integer side) {
		this.side = side;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getRatebp() {
		return ratebp;
	}
	public void setRatebp(Integer ratebp) {
		this.ratebp = ratebp;
	}
	public Integer getRatetype() {
		return ratetype;
	}
	public void setRatetype(Integer ratetype) {
		this.ratetype = ratetype;
	}
	public BigDecimal getRate_high() {
		return rate_high;
	}
	public void setRate_high(BigDecimal rate_high) {
		this.rate_high = rate_high;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Integer getTenor_low() {
		return tenor_low;
	}
	public void setTenor_low(Integer tenor_low) {
		this.tenor_low = tenor_low;
	}
	public Integer getTenor_high() {
		return tenor_high;
	}
	public void setTenor_high(Integer tenor_high) {
		this.tenor_high = tenor_high;
	}
	public Integer getCounter_party_bank() {
		return counter_party_bank;
	}
	public void setCounter_party_bank(Integer counter_party_bank) {
		this.counter_party_bank = counter_party_bank;
	}
	public Integer getCounter_party_rural() {
		return counter_party_rural;
	}
	public void setCounter_party_rural(Integer counter_party_rural) {
		this.counter_party_rural = counter_party_rural;
	}
	public Integer getCounter_party_dvp() {
		return counter_party_dvp;
	}
	public void setCounter_party_dvp(Integer counter_party_dvp) {
		this.counter_party_dvp = counter_party_dvp;
	}
	public Integer getCounter_party_dep_inst() {
		return counter_party_dep_inst;
	}
	public void setCounter_party_dep_inst(Integer counter_party_dep_inst) {
		this.counter_party_dep_inst = counter_party_dep_inst;
	}
	public Integer getCollateral_rate() {
		return collateral_rate;
	}
	public void setCollateral_rate(Integer collateral_rate) {
		this.collateral_rate = collateral_rate;
	}
	public Integer getCollateral_credit() {
		return collateral_credit;
	}
	public void setCollateral_credit(Integer collateral_credit) {
		this.collateral_credit = collateral_credit;
	}
	public Integer getCollateral_clear_sh() {
		return collateral_clear_sh;
	}
	public void setCollateral_clear_sh(Integer collateral_clear_sh) {
		this.collateral_clear_sh = collateral_clear_sh;
	}
	public Integer getCollateral_clear_cn() {
		return collateral_clear_cn;
	}
	public void setCollateral_clear_cn(Integer collateral_clear_cn) {
		this.collateral_clear_cn = collateral_clear_cn;
	}
	public Integer getCollateral_clear_cd() {
		return collateral_clear_cd;
	}
	public void setCollateral_clear_cd(Integer collateral_clear_cd) {
		this.collateral_clear_cd = collateral_clear_cd;
	}
	public Integer getCollateral_rating() {
		return collateral_rating;
	}
	public void setCollateral_rating(Integer collateral_rating) {
		this.collateral_rating = collateral_rating;
	}
	public Integer getCollateral_cp() {
		return collateral_cp;
	}
	public void setCollateral_cp(Integer collateral_cp) {
		this.collateral_cp = collateral_cp;
	}
	public Integer getAppointment() {
		return appointment;
	}
	public void setAppointment(Integer appointment) {
		this.appointment = appointment;
	}
	public String getBond_code() {
		return bond_code;
	}
	public void setBond_code(String bond_code) {
		this.bond_code = bond_code;
	}
	public String getBond_name() {
		return bond_name;
	}
	public void setBond_name(String bond_name) {
		this.bond_name = bond_name;
	}
	public Integer getBond_issuer_rating() {
		return bond_issuer_rating;
	}
	public void setBond_issuer_rating(Integer bond_issuer_rating) {
		this.bond_issuer_rating = bond_issuer_rating;
	}
	public Integer getBond_rating() {
		return bond_rating;
	}
	public void setBond_rating(Integer bond_rating) {
		this.bond_rating = bond_rating;
	}
	public BigDecimal getBond_range_low() {
		return bond_range_low;
	}
	public void setBond_range_low(BigDecimal bond_range_low) {
		this.bond_range_low = bond_range_low;
	}
	public BigDecimal getBond_range_high() {
		return bond_range_high;
	}
	public void setBond_range_high(BigDecimal bond_range_high) {
		this.bond_range_high = bond_range_high;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAllphone() {
		return allphone;
	}
	public void setAllphone(String allphone) {
		this.allphone = allphone;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getVuser_name() {
		return Vuser_name;
	}
	public void setVuser_name(String vuser_name) {
		Vuser_name = vuser_name;
	}
	public String getVname() {
		return Vname;
	}
	public void setVname(String vname) {
		Vname = vname;
	}
	public String getVinst() {
		return Vinst;
	}
	public void setVinst(String vinst) {
		Vinst = vinst;
	}
	public String getVsub_inst() {
		return Vsub_inst;
	}
	public void setVsub_inst(String vsub_inst) {
		Vsub_inst = vsub_inst;
	}
	public String getVqq() {
		return Vqq;
	}
	public void setVqq(String vqq) {
		Vqq = vqq;
	}
	public String getVphone_office() {
		return Vphone_office;
	}
	public void setVphone_office(String vphone_office) {
		Vphone_office = vphone_office;
	}
	public String getVinst_short() {
		return Vinst_short;
	}
	public void setVinst_short(String vinst_short) {
		Vinst_short = vinst_short;
	}
	public String getVinst_type() {
		return Vinst_type;
	}
	public void setVinst_type(String vinst_type) {
		Vinst_type = vinst_type;
	}
	public String getVinst_addr() {
		return Vinst_addr;
	}
	public void setVinst_addr(String vinst_addr) {
		Vinst_addr = vinst_addr;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRawcontent() {
		return rawcontent;
	}
	public void setRawcontent(String rawcontent) {
		this.rawcontent = rawcontent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getBondissuer() {
		return bondissuer;
	}
	public void setBondissuer(Integer bondissuer) {
		this.bondissuer = bondissuer;
	}
	public String getBond_issuer_rating_agency() {
		return bond_issuer_rating_agency;
	}
	public void setBond_issuer_rating_agency(String bond_issuer_rating_agency) {
		this.bond_issuer_rating_agency = bond_issuer_rating_agency;
	}
	public String getBond_rating_agency() {
		return bond_rating_agency;
	}
	public void setBond_rating_agency(String bond_rating_agency) {
		this.bond_rating_agency = bond_rating_agency;
	}
	public String getQq_group_name() {
		return qq_group_name;
	}
	public void setQq_group_name(String qq_group_name) {
		this.qq_group_name = qq_group_name;
	}
	public Integer getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}
	public BigDecimal getBond_yield_low() {
		return bond_yield_low;
	}
	public void setBond_yield_low(BigDecimal bond_yield_low) {
		this.bond_yield_low = bond_yield_low;
	}
	public BigDecimal getBond_yield_high() {
		return bond_yield_high;
	}
	public void setBond_yield_high(BigDecimal bond_yield_high) {
		this.bond_yield_high = bond_yield_high;
	}
	public Integer getPostfrom() {
		return postfrom;
	}
	public void setPostfrom(Integer postfrom) {
		this.postfrom = postfrom;
	}
	
}
