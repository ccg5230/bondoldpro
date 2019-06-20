package com.innodealing.domain;

public class Node {

	public String idx = "";
	public String id = "";
	public String actiontype = "";
	public String type = "0";
	public String sub_type = "0";
	public String side = "0";
	public String amount = "0";
	public String amount_high = "0";//金额最大值
	public String rate = "0";
	public String ratetype = "0";//利率类型
	public String rate_high = "0";//利率最大值
	public String tenor_low = "0";
	public String tenor_high = "0";
	public String counter_party_bank = "0";
	public String counter_party_rural = "0";
	public String counter_party_dvp = "0";
	public String hkd = "0";
	public String usd = "0";
	public String eur = "0";
	public String rmb = "0";
	public String counter_party_dep_inst = "0";
	public String collateral_rate = "0";
	public String appointment = "0";
	public String collateral_credit = "0";
	public String collateral_clear_sh = "0";
	public String collateral_clear_cn = "0";
	public String collateral_clear_cd = "0";
	public String collateral_rating = "0";
	public String collateral_cp = "0";
	public String prefer_im = "0";
	public String paper_dir_repo = "0";
	public String paper_expire = "0";
	public String paper_dir_buyout = "0";
	public String paper_dir_sellout = "0";
	public String paper_form_paper = "0";
	public String paper_form_electric = "0";
	public String paper_catalog_bank = "0";
	public String paper_catalog_merchant = "0";
	public String paper_accepting_rural = "0";
	public String mobile = "";
	public String phone = "";
	public String paper_accepting_bank = "";
	public String paper_accepting_merchant = "";
	public String typenum = "";
	public String typename = "";
	public String amountnum = "";
	public String binznum = "";
	public String pazunum = "";
	public String ratenum = "";
	public String terounum = "";
	public String bond_code = ""; // 债券代码
	public String bond_name = ""; // 债券名称
	public String bond_issuer_rating = ""; // 主体评级
	public String bond_rating = ""; // 债券评级
	public String bond_range_low = ""; // 区间范围，低
	public String bond_range_high = ""; // 估值范围，高
	public String bond_range_highnum = "";
	public String bond_range_lownum = "";
	public String bond_ratingnum = "";
	public String bond_issuer_ratingnum = "";
	public String letterguarantee = "";

	public String ratebp = "";
	public String rawcontent = "";
	public String message = "";

	public String incr_id = "0";
	public String msg_id = "";
	public String source = "";
	public String allphone;
	public String recommendlevel;
	public String status;
	
	//DM2.0需求增加字段
	public String appointmentdate = "";
	public String negotiable = "";
	public String remark = "";
	public String bondmarketib = "";
	public String bondmarketsh = "";
	public String bondmarketsz = "";
	public String bondissuer = "";
	public String bond_issuer_rating_agency = "";
	public String bond_rating_agency = "";
	public String guaranteed = "";
	public String closingdate = "";
	public String bookkeepingdate = "";
	public String prospectus = "";
	public String brokerage = "";
	
	public String bond_yield_low = "";
	public String bond_yield_high = "";
	
	public String vinst_city = "";
	public String bookkeepdate_seq = "";
	public String anonymous = "";
	public String imflag = "";
	public String asset_scale = "";
	
	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSub_type() {
		return sub_type;
	}

	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getTenor_low() {
		return tenor_low;
	}

	public void setTenor_low(String tenor_low) {
		this.tenor_low = tenor_low;
	}

	public String getTenor_high() {
		return tenor_high;
	}

	public void setTenor_high(String tenor_high) {
		this.tenor_high = tenor_high;
	}

	public String getCounter_party_bank() {
		return counter_party_bank;
	}

	public void setCounter_party_bank(String counter_party_bank) {
		this.counter_party_bank = counter_party_bank;
	}

	public String getCounter_party_rural() {
		return counter_party_rural;
	}

	public void setCounter_party_rural(String counter_party_rural) {
		this.counter_party_rural = counter_party_rural;
	}

	public String getCounter_party_dvp() {
		return counter_party_dvp;
	}

	public void setCounter_party_dvp(String counter_party_dvp) {
		this.counter_party_dvp = counter_party_dvp;
	}

	public String getHkd() {
		return hkd;
	}

	public void setHkd(String hkd) {
		this.hkd = hkd;
	}

	public String getUsd() {
		return usd;
	}

	public void setUsd(String usd) {
		this.usd = usd;
	}

	public String getEur() {
		return eur;
	}

	public void setEur(String eur) {
		this.eur = eur;
	}

	public String getRmb() {
		return rmb;
	}

	public void setRmb(String rmb) {
		this.rmb = rmb;
	}

	public String getCounter_party_dep_inst() {
		return counter_party_dep_inst;
	}

	public void setCounter_party_dep_inst(String counter_party_dep_inst) {
		this.counter_party_dep_inst = counter_party_dep_inst;
	}

	public String getCollateral_rate() {
		return collateral_rate;
	}

	public void setCollateral_rate(String collateral_rate) {
		this.collateral_rate = collateral_rate;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getCollateral_credit() {
		return collateral_credit;
	}

	public void setCollateral_credit(String collateral_credit) {
		this.collateral_credit = collateral_credit;
	}

	public String getCollateral_clear_sh() {
		return collateral_clear_sh;
	}

	public void setCollateral_clear_sh(String collateral_clear_sh) {
		this.collateral_clear_sh = collateral_clear_sh;
	}

	public String getCollateral_clear_cn() {
		return collateral_clear_cn;
	}

	public void setCollateral_clear_cn(String collateral_clear_cn) {
		this.collateral_clear_cn = collateral_clear_cn;
	}

	public String getCollateral_rating() {
		return collateral_rating;
	}

	public void setCollateral_rating(String collateral_rating) {
		this.collateral_rating = collateral_rating;
	}

	public String getCollateral_cp() {
		return collateral_cp;
	}

	public void setCollateral_cp(String collateral_cp) {
		this.collateral_cp = collateral_cp;
	}

	public String getPrefer_im() {
		return prefer_im;
	}

	public void setPrefer_im(String prefer_im) {
		this.prefer_im = prefer_im;
	}

	public String getPaper_dir_repo() {
		return paper_dir_repo;
	}

	public void setPaper_dir_repo(String paper_dir_repo) {
		this.paper_dir_repo = paper_dir_repo;
	}

	public String getPaper_expire() {
		return paper_expire;
	}

	public void setPaper_expire(String paper_expire) {
		this.paper_expire = paper_expire;
	}

	public String getPaper_dir_buyout() {
		return paper_dir_buyout;
	}

	public void setPaper_dir_buyout(String paper_dir_buyout) {
		this.paper_dir_buyout = paper_dir_buyout;
	}

	public String getPaper_dir_sellout() {
		return paper_dir_sellout;
	}

	public void setPaper_dir_sellout(String paper_dir_sellout) {
		this.paper_dir_sellout = paper_dir_sellout;
	}

	public String getPaper_form_paper() {
		return paper_form_paper;
	}

	public void setPaper_form_paper(String paper_form_paper) {
		this.paper_form_paper = paper_form_paper;
	}

	public String getPaper_form_electric() {
		return paper_form_electric;
	}

	public void setPaper_form_electric(String paper_form_electric) {
		this.paper_form_electric = paper_form_electric;
	}

	public String getPaper_catalog_bank() {
		return paper_catalog_bank;
	}

	public void setPaper_catalog_bank(String paper_catalog_bank) {
		this.paper_catalog_bank = paper_catalog_bank;
	}

	public String getPaper_catalog_merchant() {
		return paper_catalog_merchant;
	}

	public void setPaper_catalog_merchant(String paper_catalog_merchant) {
		this.paper_catalog_merchant = paper_catalog_merchant;
	}

	public String getPaper_accepting_rural() {
		return paper_accepting_rural;
	}

	public void setPaper_accepting_rural(String paper_accepting_rural) {
		this.paper_accepting_rural = paper_accepting_rural;
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

	public String getPaper_accepting_bank() {
		return paper_accepting_bank;
	}

	public void setPaper_accepting_bank(String paper_accepting_bank) {
		this.paper_accepting_bank = paper_accepting_bank;
	}

	public String getPaper_accepting_merchant() {
		return paper_accepting_merchant;
	}

	public void setPaper_accepting_merchant(String paper_accepting_merchant) {
		this.paper_accepting_merchant = paper_accepting_merchant;
	}

	public String getTypenum() {
		return typenum;
	}

	public void setTypenum(String typenum) {
		this.typenum = typenum;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getAmountnum() {
		return amountnum;
	}

	public void setAmountnum(String amountnum) {
		this.amountnum = amountnum;
	}

	public String getBinznum() {
		return binznum;
	}

	public void setBinznum(String binznum) {
		this.binznum = binznum;
	}

	public String getPazunum() {
		return pazunum;
	}

	public void setPazunum(String pazunum) {
		this.pazunum = pazunum;
	}

	public String getRatenum() {
		return ratenum;
	}

	public void setRatenum(String ratenum) {
		this.ratenum = ratenum;
	}

	public String getTerounum() {
		return terounum;
	}

	public void setTerounum(String terounum) {
		this.terounum = terounum;
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

	public String getBond_issuer_rating() {
		return bond_issuer_rating;
	}

	public void setBond_issuer_rating(String bond_issuer_rating) {
		this.bond_issuer_rating = bond_issuer_rating;
	}

	public String getBond_rating() {
		return bond_rating;
	}

	public void setBond_rating(String bond_rating) {
		this.bond_rating = bond_rating;
	}

	public String getBond_range_low() {
		return bond_range_low;
	}

	public void setBond_range_low(String bond_range_low) {
		this.bond_range_low = bond_range_low;
	}

	public String getBond_range_high() {
		return bond_range_high;
	}

	public void setBond_range_high(String bond_range_high) {
		this.bond_range_high = bond_range_high;
	}

	public String getBond_range_highnum() {
		return bond_range_highnum;
	}

	public void setBond_range_highnum(String bond_range_highnum) {
		this.bond_range_highnum = bond_range_highnum;
	}

	public String getBond_range_lownum() {
		return bond_range_lownum;
	}

	public void setBond_range_lownum(String bond_range_lownum) {
		this.bond_range_lownum = bond_range_lownum;
	}

	public String getBond_ratingnum() {
		return bond_ratingnum;
	}

	public void setBond_ratingnum(String bond_ratingnum) {
		this.bond_ratingnum = bond_ratingnum;
	}

	public String getBond_issuer_ratingnum() {
		return bond_issuer_ratingnum;
	}

	public void setBond_issuer_ratingnum(String bond_issuer_ratingnum) {
		this.bond_issuer_ratingnum = bond_issuer_ratingnum;
	}

	public String getCollateral_clear_cd() {
		return collateral_clear_cd;
	}

	public void setCollateral_clear_cd(String collateral_clear_cd) {
		this.collateral_clear_cd = collateral_clear_cd;
	}

	public String getLetterguarantee() {
		return letterguarantee;
	}

	public void setLetterguarantee(String letterguarantee) {
		this.letterguarantee = letterguarantee;
	}


	public String getRatebp() {
		return ratebp;
	}

	public void setRatebp(String ratebp) {
		this.ratebp = ratebp;
	}

	public String getRawcontent() {
		return rawcontent;
	}

	public void setRawcontent(String rawcontent) {
		this.rawcontent = rawcontent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIncr_id() {
		return incr_id;
	}

	public void setIncr_id(String incr_id) {
		this.incr_id = incr_id;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAllphone() {
		return allphone;
	}

	public void setAllphone(String allphone) {
		this.allphone = allphone;
	}

	public String getAmount_high() {
		return amount_high;
	}

	public void setAmount_high(String amount_high) {
		this.amount_high = amount_high;
	}

	public String getRatetype() {
		return ratetype;
	}

	public void setRatetype(String ratetype) {
		this.ratetype = ratetype;
	}

	public String getRate_high() {
		return rate_high;
	}

	public void setRate_high(String rate_high) {
		this.rate_high = rate_high;
	}

	public String getRecommendlevel() {
		return recommendlevel;
	}

	public void setRecommendlevel(String recommendlevel) {
		this.recommendlevel = recommendlevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppointmentdate() {
		return appointmentdate;
	}

	public void setAppointmentdate(String appointmentdate) {
		this.appointmentdate = appointmentdate;
	}

	public String getNegotiable() {
		return negotiable;
	}

	public void setNegotiable(String negotiable) {
		this.negotiable = negotiable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBondmarketib() {
		return bondmarketib;
	}

	public void setBondmarketib(String bondmarketib) {
		this.bondmarketib = bondmarketib;
	}

	public String getBondmarketsh() {
		return bondmarketsh;
	}

	public void setBondmarketsh(String bondmarketsh) {
		this.bondmarketsh = bondmarketsh;
	}

	public String getBondmarketsz() {
		return bondmarketsz;
	}

	public void setBondmarketsz(String bondmarketsz) {
		this.bondmarketsz = bondmarketsz;
	}

	public String getBondissuer() {
		return bondissuer;
	}

	public void setBondissuer(String bondissuer) {
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

	public String getGuaranteed() {
		return guaranteed;
	}

	public void setGuaranteed(String guaranteed) {
		this.guaranteed = guaranteed;
	}

	public String getClosingdate() {
		return closingdate;
	}

	public void setClosingdate(String closingdate) {
		this.closingdate = closingdate;
	}

	public String getBookkeepingdate() {
		return bookkeepingdate;
	}

	public void setBookkeepingdate(String bookkeepingdate) {
		this.bookkeepingdate = bookkeepingdate;
	}

	public String getProspectus() {
		return prospectus;
	}

	public void setProspectus(String prospectus) {
		this.prospectus = prospectus;
	}

	public String getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(String brokerage) {
		this.brokerage = brokerage;
	}
	
	public String getBond_yield_low() {
		return bond_yield_low;
	}

	public void setBond_yield_low(String bond_yield_low) {
		this.bond_yield_low = bond_yield_low;
	}

	public String getBond_yield_high() {
		return bond_yield_high;
	}

	public void setBond_yield_high(String bond_yield_high) {
		this.bond_yield_high = bond_yield_high;
	}

	public String getVinst_city() {
		return vinst_city;
	}

	public void setVinst_city(String vinst_city) {
		this.vinst_city = vinst_city;
	}

	public String getBookkeepdate_seq() {
		return bookkeepdate_seq;
	}

	public void setBookkeepdate_seq(String bookkeepdate_seq) {
		this.bookkeepdate_seq = bookkeepdate_seq;
	}

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public String getImflag() {
		return imflag;
	}

	public void setImflag(String imflag) {
		this.imflag = imflag;
	}

	public String getAsset_scale() {
		return asset_scale;
	}

	public void setAsset_scale(String asset_scale) {
		this.asset_scale = asset_scale;
	}
}
