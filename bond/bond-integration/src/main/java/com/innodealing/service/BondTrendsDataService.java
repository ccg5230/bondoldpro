package com.innodealing.service;

import java.util.*;
import java.util.stream.Collectors;

import com.innodealing.model.mongo.dm.*;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.engine.mongo.bond.BondTrendsImpRatingChangeRepository;

/**
 * @author xiaochao
 * @date 2017年4月13日 下午4:24:53
 * @describe
 */
@Service
public class BondTrendsDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BondTrendsDataService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
    private BondTrendsImpRatingChangeRepository bondTrendsImpRatingChangeRepo;

	/**
	 * 将今天上传到主表的数据，对比历史表的上一个日期数据，在保证数据不重复的前提下，生成隐含评级变动信息
	 * @return
	 */
	public String buildTodayBondImpRatingChange() {
		LOGGER.info("Start to exec buildBondImpRatingChangeHistDoc...");
		long begin = System.currentTimeMillis();
		try {
			List<BondTrendsImpRatingChangeDoc> impRatingHistList = this.getTodayImpRatingChangeDocList();
			if (impRatingHistList != null && !impRatingHistList.isEmpty()) {
				// duplicate check
				if (this.isDuplicatedImpRatingChange(impRatingHistList)) {
					LOGGER.info(String.format("buildBondImpRatingChangeHistDoc: all %1$d items are duplicated, just ignore", impRatingHistList.size()));
				} else {
					this.handleImpRatingDocWithComInfo(impRatingHistList);
					bondTrendsImpRatingChangeRepo.save(impRatingHistList);
					LOGGER.info(String.format("buildBondImpRatingChangeHistDoc: saved %1$d items into mongodb", impRatingHistList.size()));
				}
			}
		} catch (Exception ex) {
			LOGGER.error(String.format("buildBondImpRatingChangeHistDoc exception[%1%s]", ex.getMessage()));
			ex.printStackTrace();
		}
		long end = System.currentTimeMillis();
		LOGGER.info(String.format("End to exec buildBondImpRatingChangeHistDoc in %1$d ms", end-begin));
		return "done";
	}

	/**
	 * 检查首尾记录是否存在，来判断是否重复
	 * @param impRatingHistList
	 * @return
	 */
	private boolean isDuplicatedImpRatingChange(List<BondTrendsImpRatingChangeDoc> impRatingHistList) {
		Query headQuery = new Query() {
			{ addCriteria(Criteria.where("bondUniCode").is(impRatingHistList.get(0).getBondUniCode())
					.and("pubDate").is(impRatingHistList.get(0).getPubDate())); }
		};
		BondTrendsImpRatingChangeDoc headBTIRCDoc = mongoOperations.findOne(headQuery, BondTrendsImpRatingChangeDoc.class);
		Query tailQuery = new Query() {
			{ addCriteria(Criteria.where("bondUniCode").is(impRatingHistList.get(impRatingHistList.size() - 1).getBondUniCode())
					.and("pubDate").is(impRatingHistList.get(impRatingHistList.size() - 1).getPubDate())); }
		};
		BondTrendsImpRatingChangeDoc tailBTIRCDoc = mongoOperations.findOne(tailQuery, BondTrendsImpRatingChangeDoc.class);
		return headBTIRCDoc != null || tailBTIRCDoc != null;
	}

	/**
	 * 初始化历史数据
	 * @param key
	 * @return
	 */
	public String initBondImpRatingChangeHistData(String key, String startDateStr, String endDateStr) {
		LOGGER.info("Start to exec initBondImpRatingChangeHistDoc...");
		long begin = System.currentTimeMillis();
		try {
			if ("xiaocptimpratingbtptpinit".equals(key)) {
				if (StringUtils.isBlank(startDateStr) && StringUtils.isBlank(endDateStr)) {
					bondTrendsImpRatingChangeRepo.deleteAll();
					LOGGER.info("initBondImpRatingChangeHistData: removed all data");
				}
				String dateCriteria = StringUtils.isBlank(startDateStr) ? "1=1" : String.format("data_date>='%1$s'", startDateStr);
				dateCriteria += StringUtils.isBlank(endDateStr) ? " AND 1=1" : String.format(" AND data_date<='%1$s'", endDateStr);
				String sql = String.format("SELECT DISTINCT data_date FROM dmdb.t_bond_implied_rating_hist WHERE %1$s ORDER BY data_date", dateCriteria);
				List<String> dateList = jdbcTemplate.queryForList(sql, String.class);
				LOGGER.info("initBondImpRatingChangeHistData: found {} date items between [{}] and [{}]",
						dateList.size(), startDateStr, endDateStr);
				dateList.forEach(date -> {
					try {
						if (StringUtils.isNotBlank(startDateStr) || StringUtils.isNotBlank(endDateStr)) {
							Date dateStart = SafeUtils.convertStringToDate(date, SafeUtils.DATE_TIME_FORMAT1);
							Date dateEnd = SafeUtils.getDateFromSpecifiedDateStr(date, 1, SafeUtils.DATE_TIME_FORMAT1);
							mongoOperations.remove(new Query(Criteria.where("pubDate").gte(dateStart).lt(dateEnd)),
									BondTrendsImpRatingChangeDoc.class);
							LOGGER.info("initBondImpRatingChangeHistData: removed data in date[{}]", date);
						}
						List<BondTrendsImpRatingChangeDoc> impRatingHistList = this.getImpRatingChangeHistByDate(date);
						if (impRatingHistList != null && !impRatingHistList.isEmpty()) {
							this.handleImpRatingDocWithComInfo(impRatingHistList);
							bondTrendsImpRatingChangeRepo.save(impRatingHistList);
							LOGGER.info(String.format("initBondImpRatingChangeHistDoc: saved %1$d items into mongodb with date[%2$s]", impRatingHistList.size(), date));
						}
					} catch (Exception ex) {
						LOGGER.error("initBondImpRatingChangeHistData with date[{}], ex[{}]", date, ex);
					}
				});
			}
		} catch (Exception ex) {
			LOGGER.error("initBondImpRatingChangeHistDoc exception[{}]", ex.getMessage());
			ex.printStackTrace();
		}
		long end = System.currentTimeMillis();
		LOGGER.info(String.format("End to exec initBondImpRatingChangeHistDoc in %1$d ms", end-begin));
		return "done";
	}
	
	/**
	 * 匹配主体信息
	 * @param impRatingHistList
	 */
	private void handleImpRatingDocWithComInfo(List<BondTrendsImpRatingChangeDoc> impRatingHistList) {
		Query query = new Query() {
			{ addCriteria(Criteria.where("_id").in(impRatingHistList.stream()
					.map(BondTrendsImpRatingChangeDoc::getIssuerId).collect(Collectors.toList()))); }
		};
		List<BondComInfoDoc> bondComInfoDocList = mongoOperations.find(query, BondComInfoDoc.class);
		impRatingHistList.forEach(item -> {
			Optional<BondComInfoDoc> bondInfoOpt = bondComInfoDocList.parallelStream()
					.filter(t -> t.getComUniCode().equals(item.getIssuerId())).findFirst();
			BondComInfoDoc bondInfo = bondInfoOpt.orElse(new BondComInfoDoc());
			item.setDmPd(bondInfo.getPd());
			item.setDmPdNum(bondInfo.getPdNum());
			item.setDmPdTime(bondInfo.getPdTime());
			item.setDmPdDiff(bondInfo.getPdDiff());
			item.setRiskWarning(bondInfo.getRiskWarning());
			item.setDmWorstPd(bondInfo.getWorstPd());
			item.setDmWorstPdNum(bondInfo.getWorstPdNum());
			item.setDmWorstPdTime(bondInfo.getWorstPdTime());
			item.setWorstRiskWarning(bondInfo.getWorstRiskWarning());
		});
	}

	/**
	 * 获取当天有隐含评级变动的数据集合
	 * @return
	 */
	private List<BondTrendsImpRatingChangeDoc> getTodayImpRatingChangeDocList() {
		List<BondTrendsImpRatingChangeDoc> result = new ArrayList<>();
		try {
			String mainSql = "SELECT mm1.data_date AS pubDate,"
					+ " mm1.bond_name AS bondName, mm1.bondUniCode, mm1.bondCode,"
					+ " (mm1.ratingId-mm2.ratingId) AS rateDiff, mm2.implied_rating AS lastRating, mm2.ratingId AS lastRatingPar,"
					+ " mm1.implied_rating AS currRating, mm1.ratingId AS currRatingPar, mm2.data_date AS date2, mm1.rate AS valuation,"
					+ " CASE WHEN (mm1.ratingId-mm2.ratingId)>0 THEN '下调' ELSE '上调' END AS ratingResult"
					+ " FROM (%1$s) AS mm1, (%2$s) AS mm2"
					+ " WHERE mm1.bondUniCode = mm2.bond_code AND (mm1.ratingId-mm2.ratingId)!=0";
			
			String formatFstSql = "SELECT t.bond_id AS bondCode, t.bond_code AS bondUnicode, t.bond_name, t.implied_rating,"
					+ " p1.id AS ratingId, t.data_date, bi.rate"
					+ " FROM (SELECT bond_id, bond_code, bond_name, implied_rating, data_date"
					+ " FROM dmdb.t_bond_implied_rating"
					+ " WHERE bond_code>0 AND implied_rating IS NOT NULL"
					+ " AND implied_rating NOT LIKE '%%(%%' AND implied_rating!='0') AS t"
					+ " LEFT JOIN dmdb.t_bond_rating_par p1 ON t.implied_rating = p1.rating"
					+ " LEFT JOIN innodealing.bond_info AS bi ON bi.bond_code=t.bond_code";
			
			String formatScdSql = "SELECT t.bond_code,t.bond_name, t.implied_rating, p1.id AS ratingId, t.data_date"
					+ " FROM (SELECT bond_id, bond_code, bond_name, issuer_name, implied_rating, data_date"
					+ " FROM dmdb.t_bond_implied_rating_hist"
					+ " WHERE data_date=(SELECT data_date FROM dmdb.t_bond_implied_rating_hist WHERE "
					+ " DATEDIFF((SELECT data_date FROM dmdb.t_bond_implied_rating LIMIT 1),data_date)>0 ORDER BY data_date DESC LIMIT 1)"
					+ " AND bond_code>0 AND implied_rating IS NOT NULL"
					+ " AND implied_rating NOT LIKE '%%(%%' AND implied_rating!='0') AS t"
					+ " LEFT JOIN dmdb.t_bond_rating_par p1 ON t.implied_rating = p1.rating";
			String finalSql = String.format(mainSql, formatFstSql, formatScdSql);
			result = jdbcTemplate.query(finalSql, new BeanPropertyRowMapper<>(BondTrendsImpRatingChangeDoc.class));
			// 填充发行人id和发行人名称
			if (!result.isEmpty()) {
				Set<Long> bondUniCodeSet = result.stream().map(BondTrendsImpRatingChangeDoc::getBondUniCode)
						.collect(Collectors.toSet());
				Query query = new Query(Criteria.where("bondUniCode").in(bondUniCodeSet));
				query.fields().include("bondUniCode").include("issuerId").include("issuer");
				List<BondBasicInfoDoc> basicInfoDocList = mongoOperations.find(query, BondBasicInfoDoc.class);
				// 发行人id
				Map<Long, Long> bond2IssuerIdMap = basicInfoDocList.stream().filter(item -> item.getIssuerId() != null)
						.collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, BondBasicInfoDoc::getIssuerId));
				// 发行人名称
				Map<Long, String> bond2IssuerNameMap = basicInfoDocList.stream().filter(item -> item.getIssuerId() != null)
						.collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, BondBasicInfoDoc::getIssuer));
				result.forEach(impRatingChangeDoc -> {
					Long bondUniCode = impRatingChangeDoc.getBondUniCode();
					if (bond2IssuerIdMap.containsKey(bondUniCode)) {
						impRatingChangeDoc.setIssuerId(bond2IssuerIdMap.get(bondUniCode));
						impRatingChangeDoc.setIssuerName(bond2IssuerNameMap.get(bondUniCode));
					}
				});
			}
			LOGGER.info(String.format("getTodayImpRatingChangeDocList: found %1$d items", result.size()));
		} catch (Exception ex) {
			LOGGER.error(String.format("getTodayImpRatingChangeDocList exception[%2$s]", ex.getMessage()));
			ex.printStackTrace();
		}
		return result;
	}

	private List<BondTrendsImpRatingChangeDoc> getImpRatingChangeHistByDate(String date) {
		List<BondTrendsImpRatingChangeDoc> result = new ArrayList<>();
		try {
			String mainSql = "SELECT mm1.data_date AS pubDate, mm1.issuer_id AS issuerId, mm1.issuer_name AS issuerName, mm1.bond_name AS bondName,"
					+ " mm1.bondUniCode, mm1.bondCode, (mm1.ratingId - mm2.ratingId) AS rateDiff, mm2.implied_rating AS lastRating,"
					+ " mm2.ratingId AS lastRatingPar, mm1.implied_rating AS currRating, mm1.ratingId AS currRatingPar, mm2.data_date AS date2,"
					+ " mm1.rate AS valuation, CASE WHEN (mm1.ratingId - mm2.ratingId)>0 THEN '下调' ELSE '上调' END AS ratingResult"
					+ " FROM "
					+ "(SELECT t.bond_id AS bondCode, t.bond_code AS bondUnicode, t.bond_name, t.implied_rating, p1.id AS ratingId,"
					+ " t.data_date, t.issuer_id, t.issuer_name, bi.rate"
					+ " FROM (SELECT bond_id, bond_code, bond_name, issuer_id, issuer_name, implied_rating, data_date FROM dmdb.t_bond_implied_rating_hist"
					+ " WHERE DATEDIFF(data_date,'%1$s')=0 AND bond_code>0 AND implied_rating IS NOT NULL"
					+ " AND implied_rating NOT LIKE '%%(%%' AND implied_rating!='0') AS t"
					+ " LEFT JOIN dmdb.t_bond_rating_par p1 ON t.implied_rating = p1.rating"
					+ " LEFT JOIN innodealing.bond_info AS bi ON bi.bond_code = t.bond_code"
					+ ") AS mm1, "
					+ "(SELECT t.bond_code AS bondUniCode, t.bond_name, t.implied_rating, p1.id AS ratingId, t.data_date"
					+ " FROM (SELECT bond_id, bond_code, bond_name, issuer_name, implied_rating, data_date FROM dmdb.t_bond_implied_rating_hist"
					+ " WHERE data_date=(SELECT data_date FROM dmdb.t_bond_implied_rating_hist WHERE DATEDIFF('%1$s',data_date)>0 ORDER BY data_date DESC LIMIT 1)"
					+ " AND bond_code>0 AND implied_rating IS NOT NULL AND implied_rating NOT LIKE '%%(%%' AND implied_rating!='0') AS t"
					+ " LEFT JOIN dmdb.t_bond_rating_par p1 ON t.implied_rating = p1.rating"
					+ ") AS mm2 "
					+ "WHERE mm1.bondUniCode=mm2.bondUniCode AND mm1.ratingId-mm2.ratingId!=0";
			String finalSql = String.format(mainSql, date);
			result = jdbcTemplate.query(finalSql, new BeanPropertyRowMapper<>(BondTrendsImpRatingChangeDoc.class));
			LOGGER.info(String.format("getImpRatingChangeHistByDate: found %1$d items with date[%2$s]", result.size(), date));
		} catch (Exception ex) {
			LOGGER.error(String.format("handleImpRatingList currDate[%1$s] exception[%3$s]",
					date, ex.getMessage()));
			ex.printStackTrace();
		}
		return result;
	}
}
