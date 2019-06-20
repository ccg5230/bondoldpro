package com.innodealing.service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.domain.NormalizeData;
import com.innodealing.domain.SysuserAndOrgainfo;
import com.innodealing.engine.jdbc.im.SysuserDao;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondQuoteDocRepository;
import com.innodealing.model.dm.bond.BondQuote;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondQuoteDoc;
import com.innodealing.util.BeanUtil;
import com.innodealing.util.SafeUtils;

/**
 * @author stephen.ma
 * @date 2016年9月22日
 * @clasename BondQuoteDataMigrationService.java
 * @decription TODO
 */
@Service
public class BondQuoteDataMigrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondQuoteDataMigrationService.class);

	private @Autowired JdbcTemplate jdbcTemplate;
	
	private @Autowired SysuserDao sysuserDao;

	private @Autowired BondBasicInfoRepository bondBasicInfoRepository;
	
	private @Autowired BondQuoteDocRepository bondQuoteDocRepository;

	@Transactional
	private String batchAddUsingJdbcTemplate(List<BondQuote> list) {

		final List<BondQuote> tempList = list;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO dmdb.t_bond_quote(  ");
		sql.append("	quote_id,              ");
		sql.append("	msg_id,                ");
		sql.append("	sub_type,              ");
		sql.append("	bond_uni_code,         ");
		sql.append("	bond_code,             ");
		sql.append("	bond_short_name,       ");
		sql.append("	side,                  ");
		sql.append("	bond_price,            ");
		sql.append("	bond_vol,              ");
		sql.append("	price_unit,            ");
		sql.append("	tenor,                 ");
		sql.append("	anonymous,              ");
		sql.append("	is_hide_rate,          ");
		sql.append("	approval,              ");
		sql.append("	source,                ");
		sql.append("	remark,                ");
		sql.append("	naturalmsg,            ");
		sql.append("	rawcontent,            ");
		sql.append("	troop_id,              ");
		sql.append("	troop_name,            ");
		sql.append("	inst_type,             ");
		sql.append("	inst_short,            ");
		sql.append("	inst_id,               ");
		sql.append("	user_id,               ");
		sql.append("	user_name,             ");
		sql.append("	wechatno,              ");
		sql.append("	postfrom,              ");
		sql.append("	STATUS,                ");
		sql.append("	open_quote,            ");
		sql.append("	send_time,             ");
		sql.append("	update_time            ");
		sql.append(")                          ");
		sql.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		long bengin = System.currentTimeMillis();

		jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, tempList.get(i).getQuoteId());
				ps.setString(2, tempList.get(i).getMsgId());
				ps.setInt(3, tempList.get(i).getSubType() == null ? 0: tempList.get(i).getSubType());
				ps.setLong(4, tempList.get(i).getBondUniCode() == null ? 0L : tempList.get(i).getBondUniCode());
				ps.setString(5, tempList.get(i).getBondCode());
				ps.setString(6, tempList.get(i).getBondShortName());
				ps.setInt(7, tempList.get(i).getSide() == null ? 0:tempList.get(i).getSide());
				ps.setBigDecimal(8, tempList.get(i).getBondPrice());
				ps.setBigDecimal(9, tempList.get(i).getBondVol());
				ps.setInt(10, tempList.get(i).getPriceUnit());
				ps.setInt(11, tempList.get(i).getTenor() == null ? 0:tempList.get(i).getTenor());
				ps.setInt(12, tempList.get(i).getAnonymous() == null ? 0:tempList.get(i).getAnonymous());
				ps.setInt(13, tempList.get(i).getIsHideRate() == null ? 0:tempList.get(i).getAnonymous());
				ps.setInt(14, tempList.get(i).getApproval());
				ps.setInt(15, tempList.get(i).getSource());
				ps.setString(16, tempList.get(i).getRemark());
				ps.setString(17, tempList.get(i).getNaturalmsg());
				ps.setString(18, tempList.get(i).getRawcontent());
				ps.setString(19, tempList.get(i).getTroopId());
				ps.setString(20, tempList.get(i).getTroopName());
				ps.setInt(21, tempList.get(i).getInstType() == null ? 0:tempList.get(i).getInstType());
				ps.setString(22, tempList.get(i).getInstShort());
				ps.setInt(23, tempList.get(i).getInstId());
				ps.setLong(24, tempList.get(i).getUserId() == null ? 0L:tempList.get(i).getInstType());
				ps.setString(25, tempList.get(i).getUserName());
				ps.setString(26, tempList.get(i).getWechatno());
				ps.setInt(27, tempList.get(i).getPostfrom());
				ps.setInt(28, tempList.get(i).getStatus());
				ps.setInt(29, tempList.get(i).getOpenQuote());
				ps.setDate(30, new java.sql.Date(tempList.get(i).getSendTime().getTime()));
				ps.setDate(31, new java.sql.Date(System.currentTimeMillis()));
			}

			@Override
			public int getBatchSize() {
				return tempList.size();
			}

		});

		long end = System.currentTimeMillis();

		LOGGER.info("batchAddUsingJdbcTemplate spending time is :" + (end - bengin));

		return "Task Done";
	}

	public void handleBondQuotes() {
		int limit = 1000;
		int count = this.getCount();
		int num = count / limit + 1;

		for (int i = 0; i < num; i++) {
			List<BondQuote> bondQuoteList = new ArrayList<>();
			
			String sql = "SELECT t.id,t.msg_id,t.qq_group,t.qq_num,t.jid,t.rcv_dt,t.message,t.type,t.sub_type,t.side,t.amount,t.ratebp,t.ratetype,t.rate_high,t.rate,t.tenor_low,"
			 +"t.tenor_high,t.counter_party_bank,t.counter_party_rural,t.counter_party_dvp,t.counter_party_dep_inst,t.collateral_rate,t.collateral_credit,t.collateral_clear_sh,"
			 +"t.collateral_clear_cn,t.collateral_clear_cd,t.collateral_rating,t.collateral_cp,t.appointment,t.bond_code,t.bond_name,t.bond_issuer_rating,t.bond_rating,t.bond_range_low,"
			 +"t.bond_range_high,t.mobile,t.phone,t.allphone,t.source,t.Vuser_name,t.Vname,t.Vinst,t.Vsub_inst,t.Vqq,t.Vphone_office,t.Vinst_short,t.Vinst_type,t.Vinst_addr,"
			 +"t.status,t.rawcontent,t.remark,t.bondissuer,t.bond_issuer_rating_agency,t.qq_group_name,t.anonymous,t.bond_yield_low,t.bond_yield_high,t.postfrom"
		     +" FROM innodealing.normalize_data t WHERE t.type=9 ORDER BY t.rcv_dt DESC LIMIT "	+ (i * limit) + "," + limit;

			List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);
			List<NormalizeData> data = BeanUtil.map2ListBean(listMap, NormalizeData.class);
			
			LOGGER.info("handleBondQuotes sql is :" + sql);

			for (int j = 0; j < data.size(); j++) {
				BondQuote bondQuote = new BondQuote();
				NormalizeData normalizeData = data.get(j);
				SysuserAndOrgainfo sysuser = sysuserDao.findSysuserOrgInfoByAccount(normalizeData.getVuser_name());
				String bondCode = normalizeData.getBond_code();
				if (StringUtils.isNotBlank(bondCode)) {
					BondBasicInfoDoc bbiDoc = bondBasicInfoRepository.findByOrgCode(bondCode);
					if (null != bbiDoc) {
						bondQuote.setBondCode(bbiDoc.getCode());
						bondQuote.setBondOrgCode(SafeUtils.getInteger(bbiDoc.getOrgCode()));
						bondQuote.setBondUniCode(bbiDoc.getBondUniCode());
						bondQuote.setBondShortName(bbiDoc.getShortName());
					} else {
						bondQuote.setBondUniCode(0L);
						bondQuote.setBondOrgCode(SafeUtils.getInteger(bondCode));
						bondQuote.setBondShortName(normalizeData.getBond_name());
					}
				}else{
					bondQuote.setBondUniCode(0L);
					bondQuote.setBondOrgCode(0);
				}
				bondQuote.setAnonymous(normalizeData.getAnonymous());
				bondQuote.setApproval(0);
				if (null != normalizeData.getBond_yield_low() && normalizeData.getBond_yield_low().doubleValue() > 0) {
					bondQuote.setBondPrice(normalizeData.getBond_yield_low());
					bondQuote.setPriceUnit(2);
				} else {
					bondQuote.setBondPrice(normalizeData.getBond_range_low());
					bondQuote.setPriceUnit(1);
				}
				bondQuote.setBondVol(new BigDecimal(normalizeData.getAmount()));
				bondQuote.setIsHideRate(0);
				bondQuote.setLastUpdateby("add from data migration");
				bondQuote.setMsgId(normalizeData.getMsg_id());
				bondQuote.setNaturalmsg(normalizeData.getMessage());
				bondQuote.setOpenQuote(1);
				bondQuote.setPostfrom(normalizeData.getPostfrom());
				if (null != sysuser) {
					bondQuote.setUserId(SafeUtils.getLong(sysuser.getId()));;
					bondQuote.setInstId(sysuser.getOrgainfoId() == null ? 0 : sysuser.getOrgainfoId());
					bondQuote.setInstShort(sysuser.getCompanyshort());
					bondQuote.setInstType(sysuser.getOrgainfoType() == null ? 0 : sysuser.getOrgainfoType());
					bondQuote.setWechatno(sysuser.getWechatno());
				} else {
					bondQuote.setUserId(0L);;
					bondQuote.setInstId(0);
					bondQuote.setInstShort(normalizeData.getVinst_short());
					bondQuote.setInstType(SafeUtils.getInteger(normalizeData.getVinst_type()));
				}
				bondQuote.setQqNum(normalizeData.getQq_num());
				bondQuote.setQuoteId(normalizeData.getId());
				bondQuote.setRawcontent(normalizeData.getRawcontent());
				bondQuote.setRemark(normalizeData.getRemark());
				bondQuote.setSendTime(normalizeData.getRcv_dt());
				bondQuote.setSide(normalizeData.getSide());
				bondQuote.setSource(normalizeData.getSource());
				bondQuote.setStatus(normalizeData.getStatus());
				bondQuote.setSubType(normalizeData.getSub_type());
				bondQuote.setTenor(normalizeData.getTenor_low());
				bondQuote.setTroopId(normalizeData.getQq_group());
				bondQuote.setTroopName(normalizeData.getQq_group());
				bondQuote.setUpdateTime(new Date());
				bondQuote.setUserName(normalizeData.getVname());
				
				bondQuoteList.add(bondQuote);
			}
			
			if (null != bondQuoteList && bondQuoteList.size() > 0) {
				batchAddUsingJdbcTemplate(bondQuoteList);
				bondQuoteList.clear();
			}
		}

	}

	private int getCount() {
		String countsql = "SELECT COUNT(1) FROM innodealing.normalize_data t WHERE t.type=9";
		return jdbcTemplate.queryForObject(countsql, Integer.class);
	}

	
	public void rebuildBondQuotesInMongo() {
		try{
			bondQuoteDocRepository.deleteAll();
			int limit = 100000;
			int count = this.getBondQuoteCount();
			int num = count / limit + 1;
			for (int i = 0; i <num; i++) {
				long begin = System.currentTimeMillis();
				
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT ");
				sql.append("	t.id AS quoteId, ");
				sql.append("	t.side AS side, ");
				sql.append("	t.bond_uni_code AS bondUniCode, ");
				sql.append("	t.bond_code AS bondCode, ");
				sql.append("	t.bond_short_name AS bondShortName, ");
				sql.append("	CASE WHEN t.bond_price IS NULL THEN 0 ELSE t.bond_price END AS bondPrice, ");
				sql.append("	CASE WHEN t.bond_vol IS NULL THEN 0 ELSE t.bond_vol END AS bondVol, ");
				sql.append("	t.anonymous AS anonymous, ");
				sql.append("	t.source AS source, ");
				sql.append("	t.rawcontent AS rawcontent, ");
				sql.append("	t.inst_short AS instShort, ");
				sql.append("	t.user_id AS userId, ");
				sql.append("	t.user_name AS userName, ");
				sql.append("	t.qq_num AS qqNum, ");
				sql.append("	t.postfrom AS postfrom, ");
				sql.append("	t.troop_id AS troopId, ");
				sql.append("	t.`status` AS status, ");
				sql.append("	t.send_time AS sendTime, ");
				sql.append("	t.price_unit AS priceUnit, ");
				sql.append("	t.remark AS remark ");
				sql.append("FROM ");
				sql.append("	dmdb.t_bond_quote t ORDER BY t.send_time DESC LIMIT " + (i * limit) + "," + limit);
				
				List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql.toString());
				listMap.forEach(mapData -> {
					//List<BondQuoteDoc> entites = new ArrayList<>();
					BondQuoteDoc bondQuoteDoc = new BondQuoteDoc();
					int side = (Integer) mapData.get("side");
					BigDecimal bondPrice = ((BigDecimal)mapData.get("bondPrice")) == null ? new BigDecimal(0) : (BigDecimal)mapData.get("bondPrice");
					BigDecimal bondVol = ((BigDecimal)mapData.get("bondVol")) == null ? new BigDecimal(0) : (BigDecimal)mapData.get("bondVol");
					Date sendTime = (Date)mapData.get("sendTime");
					Long userId = SafeUtils.getLong(mapData.get("userId"));
					if (userId.longValue() > 0) {
						SysuserAndOrgainfo sysuser = sysuserDao.findSysuserOrgInfo(userId);
						if (null != sysuser) {
							bondQuoteDoc.setImFlag(SafeUtils.getInteger(sysuser.getImflag()));
							bondQuoteDoc.setMobile(SafeUtils.getString(sysuser.getMobile()));
							bondQuoteDoc.setPhone(SafeUtils.getString(sysuser.getCompanyphone()));
						} else {
							bondQuoteDoc.setImFlag(0);
							bondQuoteDoc.setMobile("");
							bondQuoteDoc.setPhone("");
						}
					} else {
						bondQuoteDoc.setImFlag(0);
						bondQuoteDoc.setMobile("");
						bondQuoteDoc.setPhone("");
					}
					
					bondQuoteDoc.setAnonymous(SafeUtils.getInteger((Integer)mapData.get("anonymous")));
					if (1 ==  side) {
						bondQuoteDoc.setBidPrice(bondPrice);
						bondQuoteDoc.setBidVol(bondVol);
						bondQuoteDoc.setOfrPrice(new BigDecimal(0));
						bondQuoteDoc.setOfrVol(new BigDecimal(0));
					} else {
						bondQuoteDoc.setBidPrice(new BigDecimal(0));
						bondQuoteDoc.setBidVol(new BigDecimal(0));
						bondQuoteDoc.setOfrPrice(bondPrice);
						bondQuoteDoc.setOfrVol(bondVol);
					}
					bondQuoteDoc.setBondCode((String)mapData.get("bondCode"));
					bondQuoteDoc.setBondShortName((String)mapData.get("bondShortName"));
					bondQuoteDoc.setBondUniCode(((Long)mapData.get("bondUniCode")) == null ? 0 : (Long)mapData.get("bondUniCode"));
					bondQuoteDoc.setInstShort((String)mapData.get("instShort"));
					bondQuoteDoc.setPostfrom((Integer)mapData.get("postfrom"));
					bondQuoteDoc.setPriceUnit((Integer)mapData.get("priceUnit"));
					bondQuoteDoc.setQqNum((String)mapData.get("qqNum"));
					bondQuoteDoc.setQuoteId(SafeUtils.getLong(mapData.get("quoteId")));
					bondQuoteDoc.setRawcontent(SafeUtils.getString(mapData.get("rawcontent")));
					bondQuoteDoc.setSide(side);
					bondQuoteDoc.setRemark(SafeUtils.getString(mapData.get("remark")));
					bondQuoteDoc.setSendTime(SafeUtils.convertDateToString(sendTime, SafeUtils.DATE_TIME_FORMAT1));
					bondQuoteDoc.setSendTimeFormat(SafeUtils.convertDateToString(sendTime, SafeUtils.DATE_FORMAT));
					bondQuoteDoc.setSource((Integer)mapData.get("source"));
					bondQuoteDoc.setStatus((Integer)mapData.get("status"));
					bondQuoteDoc.setTroopId(SafeUtils.getString(mapData.get("troopId")));
					bondQuoteDoc.setUserId(userId);
					bondQuoteDoc.setUserName(SafeUtils.getString(mapData.get("userName")));
					
					//entites.add(bondQuoteDoc);
					
					bondQuoteDocRepository.save(bondQuoteDoc);
				});
				long end = System.currentTimeMillis();
				LOGGER.info("rebuildBondQuotesInMongo loop["+i+"] insert 10W in mongodb, timecost is "+(end-begin));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			LOGGER.error("rebuildBondQuotesInMongo error:"+ex.getMessage(), ex);
		}
	}
	
	private int getBondQuoteCount() {
		String countsql = "SELECT count(1) FROM dmdb.t_bond_quote ";
		return jdbcTemplate.queryForObject(countsql, Integer.class);
	}

}
