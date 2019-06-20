package com.innodealing.service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisCiccDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisGuoJunDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisIndustrialDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisRatingDogDoc;
import com.innodealing.util.StringUtils;

@Service
public class BondCreditAnalysisIntergationService {

	private static final Logger LOG = LoggerFactory.getLogger(BondCreditAnalysisIntergationService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Integer initValue = 100;
	private static final Map<String, Integer> MAP = new HashMap<>();
	private static final Map<String, Integer> YYMAP = new HashMap<>();
	private static final Map<String, Integer> CICCMAP = new HashMap<>();
	
	static {
		MAP.put("1-", 1);
		MAP.put("1", 2);
		MAP.put("1+", 3);
		MAP.put("2-", 4);
		MAP.put("2", 5);
		MAP.put("2+", 6);
		MAP.put("3-", 7);
		MAP.put("3", 8);
		MAP.put("3+", 9);
		MAP.put("4-", 10);
		MAP.put("4", 11);
		MAP.put("4+", 12);
		MAP.put("5-", 13);
		MAP.put("5", 14);
		MAP.put("5+", 15);
		
		YYMAP.put("1", 10);
		YYMAP.put("2",9);
		YYMAP.put("3", 8);
		YYMAP.put("4",7);
		YYMAP.put("5", 6);
		YYMAP.put("6",5);
		YYMAP.put("7", 4);
		YYMAP.put("8", 3);
		YYMAP.put("9",2);
		YYMAP.put("10", 1);
		
		CICCMAP.put("1-", 15);
		CICCMAP.put("1", 14);
		CICCMAP.put("1+", 13);
		CICCMAP.put("2-", 12);
		CICCMAP.put("2", 11);
		CICCMAP.put("2+", 10);
		CICCMAP.put("3-", 9);
		CICCMAP.put("3", 8);
		CICCMAP.put("3+", 7);
		CICCMAP.put("4-", 6);
		CICCMAP.put("4", 5);
		CICCMAP.put("4+", 4);
		CICCMAP.put("5-", 3);
		CICCMAP.put("5", 2);
		CICCMAP.put("5+", 1);
		
	}
	
	

	// 构建ratingdog 数据
	public Boolean buildRatingDog() {
		boolean result = true;
		String sql1 = "select distinct(com_uni_code) from t_bond_creditanalysis_ratingdog where com_uni_code is not null  ";
		List<Long> comUniCodeList = jdbcTemplate.queryForList(sql1, Long.class);
		List<CreditAnalysisRatingDogDoc> docList = new ArrayList<>();

		if (comUniCodeList == null || comUniCodeList.size() == 0) {
			return true;
		}
		String sql2 = "select id id, bond_code bondCode,bond_full_name bondFullName,bond_short_name bondShortName,"
				+ "     bond_match_state bondMatchState,iss_name issName,com_uni_code comUniCode,"
				+ "     iss_match_state issMatchState,yy_rating  yyRating,yy_classification yyClassification,"
				+ "     first_maj_shareholder_name firstMajShareholderName,trade trade,enterprise_nature enterpriseNature,"
				+ "     shareholder_relations shareholderRelations,state_operation stateOperation,financial_standing financialStanding,"
				+ "     focus_on focusOn, " + "		DATE_FORMAT(rate_time,%s) rateTime, update_time updateTime,"
				+ "     create_time createTime,user_id userId ,city_invest_status cityInvestStatus "
				+ "from t_bond_creditanalysis_ratingdog where com_uni_code = %2$d order by rate_time asc";

		comUniCodeList.forEach(item -> {
			String formatSql = String.format(sql2, "'%Y-%m-%d'", item);
			List<CreditAnalysisRatingDogDoc> list = jdbcTemplate.query(formatSql,
					new BeanPropertyRowMapper(CreditAnalysisRatingDogDoc.class));
			// 删选同一天有多个评级的数据
			List<CreditAnalysisRatingDogDoc> ratingdogList = list.stream().distinct().collect(Collectors.toList());

			if (ratingdogList == null || ratingdogList.size() == 0) {

			} else if (ratingdogList.size() == 1) {
				CreditAnalysisRatingDogDoc doc = ratingdogList.get(0);
				doc.setRatingChange(initValue);
				doc.setNo(0);
				docList.add(doc);
			} else {
				for (int i = 0; i < ratingdogList.size(); i++) {
					CreditAnalysisRatingDogDoc yy_1 = ratingdogList.get(i);
					CreditAnalysisRatingDogDoc yy_2 = ratingdogList.get(i + 1);

					if (i == 0) {
						// 第一天数据初始值为null
						yy_1.setRatingChange(initValue);
						yy_1.setNo(i);
						docList.add(yy_1);
					}

					if (StringUtils.isEmpty(yy_2.getYyRating()) || Objects.equal("0", yy_2.getYyRating())) {
						yy_2.setRatingChange(null);
						yy_2.setNo(i + 1);
						docList.add(yy_2);
					} else {
						for (int k = i + 1; k >= 1; k--) {
							CreditAnalysisRatingDogDoc yy_compare = ratingdogList.get(k - 1);
							if (!StringUtils.isEmpty(yy_compare.getYyRating())
									&& !Objects.equal("0", yy_compare.getYyRating() )) {
								
								int value = YYMAP.get(yy_2.getYyRating())- YYMAP.get(yy_compare.getYyRating());
								yy_2.setRatingChange(value);
								break;
							}
						}
						yy_2.setNo(i + 1);
						docList.add(yy_2);
					}

					if (ratingdogList.size() == i + 2) {
						break;
					}
				}
			}
		});

		if (docList.size() == 0 || docList == null) {
			return false;
		}

		try {
			LOG.info("start clear mongodb data");
			mongoTemplate.remove(new Query(), "bond_credit_analysis_ratingdog");
			LOG.info("remove all end");
			mongoTemplate.insertAll(docList);
			LOG.info("insert in mongodb end ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error("插入数据失败" + e.getMessage());
			return false;
		}

		return true;
	}

	// 删除ratingdog 数据
	public Boolean deleteRatingdog(Long id) {
		List<CreditAnalysisRatingDogDoc> docList = new ArrayList<>();
				
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));		
		CreditAnalysisRatingDogDoc docRatingDog = mongoTemplate.findOne(query, CreditAnalysisRatingDogDoc.class);
		if(null == docRatingDog){
			return true;
		}
		Long comUniCode = docRatingDog.getComUniCode();
		
		List< CreditAnalysisRatingDogDoc> list= mongoTemplate.find(new Query().addCriteria(Criteria.where("comUniCode").is(comUniCode)), CreditAnalysisRatingDogDoc.class);
		if(list.size() == 1){
			mongoTemplate.remove(query, CreditAnalysisRatingDogDoc.class);
			return true;
		}else{
			docList = calculateRatingdog(comUniCode);
		}
		
		if (docList == null || docList.size() == 0) {
			return true;
		}

		try {
			Query query2 = new Query();
			query2.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			mongoTemplate.remove(query2, CreditAnalysisRatingDogDoc.class);
			LOG.info("start insert data---------------");
			mongoTemplate.insertAll(docList);
			LOG.info("insert data end ---------------");
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("同步数据失败 id = " + id + "--msg =" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 新增或者添加数据，删除mongodb中改发行人数据
	// 根据id 查找该发行人,重建改发行人数据，构建到mongodb 中
	// 并且重新计算评级变动的值
	public Boolean addAndUpdateRatingdog(Long id) {

		List<CreditAnalysisRatingDogDoc> docList = new ArrayList<>();
		// 从MySQL中查询出该条记录的comUniCode
		String sql1 = "select com_uni_code comUniCode from t_bond_creditanalysis_ratingdog where id = %1$d ";
		String formatSql1 = String.format(sql1, id);		
		List<Long> list = (List<Long>)jdbcTemplate.queryForList(formatSql1,Long.class);
		if (list == null || list.size()==0) {
			return true;
		} else {
			Long comUniCode = list.get(0);
			docList = calculateRatingdog(comUniCode);			
		}

		if (docList == null || docList.size() == 0) {
			return true;
		}

		try {
			Long comUniCode = list.get(0);
			Query query = new Query();
			query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			mongoTemplate.remove(query, CreditAnalysisRatingDogDoc.class);
			LOG.info("start insert data---------------");
			mongoTemplate.insertAll(docList);
			LOG.info("insert data end ---------------");
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("同步数据失败 id = " + id + "--msg =" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
		
	public List<CreditAnalysisRatingDogDoc> calculateRatingdog(Long comUniCode){
		List<CreditAnalysisRatingDogDoc> docList = new ArrayList<>();
		
		String sql2 = "select id id, bond_code bondCode,bond_full_name bondFullName,bond_short_name bondShortName,"
				+ "     bond_match_state bondMatchState,iss_name issName,com_uni_code comUniCode,"
				+ "     iss_match_state issMatchState,yy_rating  yyRating,yy_classification yyClassification,"
				+ "     first_maj_shareholder_name firstMajShareholderName,trade trade,enterprise_nature enterpriseNature,"
				+ "     shareholder_relations shareholderRelations,state_operation stateOperation,financial_standing financialStanding,"
				+ "     focus_on focusOn, " + "		DATE_FORMAT(rate_time,%s) rateTime, update_time updateTime,"
				+ "     create_time createTime,user_id userId ,city_invest_status cityInvestStatus  "
				+ "from t_bond_creditanalysis_ratingdog where com_uni_code = %2$d order by rate_time asc";
		
		String formatSql2 = String.format(sql2, "'%Y-%m-%d'",comUniCode);
		List<CreditAnalysisRatingDogDoc> list = jdbcTemplate.query(formatSql2,
				new BeanPropertyRowMapper(CreditAnalysisRatingDogDoc.class));
		// 删选同一天有多个评级的数据
		List<CreditAnalysisRatingDogDoc> ratingdogList = list.stream().distinct().collect(Collectors.toList());

		if (ratingdogList == null || ratingdogList.size() == 0) {

		} else if (ratingdogList.size() == 1) {
			CreditAnalysisRatingDogDoc doc = ratingdogList.get(0);
			doc.setRatingChange(initValue);
			doc.setNo(0);
			docList.add(doc);
		} else {
			for (int i = 0; i < ratingdogList.size(); i++) {
				CreditAnalysisRatingDogDoc yy_1 = ratingdogList.get(i);
				CreditAnalysisRatingDogDoc yy_2 = ratingdogList.get(i + 1);

				if (i == 0) {
					// 第一天数据初始值为null
					yy_1.setRatingChange(initValue);
					yy_1.setNo(i);
					docList.add(yy_1);
				}

				if (StringUtils.isEmpty(yy_2.getYyRating()) || Objects.equal("0", yy_2.getYyRating())) {
					yy_2.setRatingChange(null);
					yy_2.setNo(i + 1);
					docList.add(yy_2);
				} else {
					for (int k = i + 1; k >= 1; k--) {
						CreditAnalysisRatingDogDoc yy_compare = ratingdogList.get(k - 1);
						if (!StringUtils.isEmpty(yy_compare.getYyRating())
								&& !Objects.equal("0", yy_compare.getYyRating())) {
							int value = YYMAP.get(yy_2.getYyRating())
									- YYMAP.get(yy_compare.getYyRating());
							yy_2.setRatingChange(value);
							break;
						}
					}
					yy_2.setNo(i + 1);
					docList.add(yy_2);
				}

				if (ratingdogList.size() == i + 2) {
					break;
				}
			}
		}
		return docList;
	}
	
	
	// 构建兴业数据
	public boolean bulidIndustrial() {
		boolean result = true;
		String sql1 = "select distinct(com_uni_code) from t_bond_creditanalysis_industrial where com_uni_code is not null  ";
		List<Long> comUniCodeList = jdbcTemplate.queryForList(sql1, Long.class);
		List<CreditAnalysisIndustrialDoc> docList = new ArrayList<>();

		if (comUniCodeList == null || comUniCodeList.size() == 0) {
			return true;
		}

		String sql2 = "select id id,bond_code bondCode, bond_full_name bondFullName,"
				+ "   bond_short_name bondShortName,bond_match_state bondMatchState ,iss_name issName,"
				+ "   com_uni_code comUniCode,iss_match_state issMatchState,subject_score subjectScore,"
				+ "   bond_score bondScore,expectation expectation,subject_comment subjectComment,"
				+ "   ticket_comment ticketComment," + "   DATE_FORMAT(rate_time,%s) rateTime,update_time updateTime,"
				+ "   create_time createTime,user_id userId  " + "\n\tfrom t_bond_creditanalysis_industrial  "
				+ "\n\twhere com_uni_code = %2$d order by rate_time asc";

		comUniCodeList.forEach(item -> {
			String formatSql = String.format(sql2, "'%Y-%m-%d'", item);
			List<CreditAnalysisIndustrialDoc> list = jdbcTemplate.query(formatSql,
					new BeanPropertyRowMapper(CreditAnalysisIndustrialDoc.class));
			// 删选同一天有多个评级的数据
			List<CreditAnalysisIndustrialDoc> industrialList = list.stream().distinct().collect(Collectors.toList());

			if (industrialList == null || industrialList.size() == 0) {

			} else if (industrialList.size() == 1) {
				CreditAnalysisIndustrialDoc doc = industrialList.get(0);
				doc.setRatingChange(initValue);
				doc.setNo(0);
				docList.add(doc);
			} else {
				for (int i = 0; i < industrialList.size(); i++) {
					CreditAnalysisIndustrialDoc yy_1 = industrialList.get(i);
					CreditAnalysisIndustrialDoc yy_2 = industrialList.get(i + 1);

					if (i == 0) {
						// 第一天数据初始值为null
						yy_1.setRatingChange(initValue);
						yy_1.setNo(i);
						docList.add(yy_1);
					}
					if (StringUtils.isEmpty(yy_2.getSubjectScore()) || Objects.equal("0", yy_2.getSubjectScore())) {
						yy_2.setRatingChange(null);
						yy_2.setNo(i + 1);
						docList.add(yy_2);
					} else {
						for (int k = i + 1; k >= 1; k--) {
							CreditAnalysisIndustrialDoc yy_compare = industrialList.get(k - 1);
							if (!StringUtils.isEmpty(yy_compare.getSubjectScore())
									&& !Objects.equal("0", yy_compare.getSubjectScore())) {
								// 设置yy_2的评级变动
								Integer val1 = Integer.valueOf(MAP.get(yy_compare.getSubjectScore()));
								Integer val2 = Integer.valueOf(MAP.get(yy_2.getSubjectScore()));
								yy_2.setRatingChange(val2.intValue() - val1.intValue());
								break;
							}
						}
						yy_2.setNo(i + 1);
						docList.add(yy_2);
					}

					if (industrialList.size() == i + 2) {
						break;
					}
				}
			}
		});

		if (docList.size() == 0 || docList == null) {
			return false;
		}

		try {
			LOG.info("start clear mongodb data");
			mongoTemplate.remove(new Query(), "bond_credit_analysis_industrial");
			LOG.info("remove all end");
			mongoTemplate.insertAll(docList);
			LOG.info("insert in mongodb end ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error("插入数据失败" + e.getMessage());
			return false;
		}

		return true;
	}

	// 删除mongodb 兴业数据
	public Boolean deleteIndustrial(Long id) {
		List<CreditAnalysisIndustrialDoc> docList = new ArrayList<>();

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		CreditAnalysisIndustrialDoc docIndustrial = mongoTemplate.findOne(query, CreditAnalysisIndustrialDoc.class);
		if(null == docIndustrial){
			return true;
		}
		Long comUniCode = docIndustrial.getComUniCode();

		List<CreditAnalysisIndustrialDoc> list = mongoTemplate.find(
				new Query().addCriteria(Criteria.where("comUniCode").is(comUniCode)),
				CreditAnalysisIndustrialDoc.class);
		if (list.size() == 1) {
			mongoTemplate.remove(query, CreditAnalysisIndustrialDoc.class);
		} else {
			docList = calculateIndustrial(comUniCode);
		}

		if (docList == null || docList.size() == 0) {
			return true;
		}

		try {
			Query query2 = new Query();
			query2.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			mongoTemplate.remove(query2, CreditAnalysisIndustrialDoc.class);
			LOG.info("start insert data -----");
			mongoTemplate.insertAll(docList);
			LOG.info("insert data end ------");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.info("同步数据失败 id =" + id + "-- msg =" + e.getMessage());
			return false;
		}

		return true;
	}

	// 更新兴业数据
	public Boolean addAndUpdateIndustrial(Long id) {
		List<CreditAnalysisIndustrialDoc> docList = new ArrayList<>();
		// 获取发行人comUniCode
		String sql1 = "select com_uni_code comUniCode from  t_bond_creditanalysis_industrial where id = %1$d ";
		String formatSql1 = String.format(sql1, id);
		List<Long> list = (List<Long>)jdbcTemplate.queryForList(formatSql1,Long.class);

		if (list == null || list.size()==0) {
			return true;
		} else {
			Long comUniCode = list.get(0);
			docList = calculateIndustrial(comUniCode);
		}

		if (docList == null || docList.size() == 0) {
			return true;
		}

		try {
			Long comUniCode = list.get(0);
			Query query = new Query();
			query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			mongoTemplate.remove(query, CreditAnalysisIndustrialDoc.class);
			LOG.info("start insert data -----");
			mongoTemplate.insertAll(docList);
			LOG.info("insert data end ------");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.info("同步数据失败 id =" + id + "-- msg =" + e.getMessage());
			return false;
		}

		return true;
	}

	public List<CreditAnalysisIndustrialDoc> calculateIndustrial(Long comUniCode) {
		List<CreditAnalysisIndustrialDoc> docList = new ArrayList<>();

		String sql2 = "select id id,bond_code bondCode, bond_full_name bondFullName,"
				+ "   bond_short_name bondShortName,bond_match_state bondMatchState ,iss_name issName,"
				+ "   com_uni_code comUniCode,iss_match_state issMatchState,subject_score subjectScore,"
				+ "   bond_score bondScore,expectation expectation,subject_comment subjectComment,"
				+ "   ticket_comment ticketComment," + "   DATE_FORMAT(rate_time,%s) rateTime,update_time updateTime,"
				+ "   create_time createTime,user_id userId  " + "\n\tfrom t_bond_creditanalysis_industrial  "
				+ "\n\twhere com_uni_code = %2$d order by rate_time asc";

		String formatSql2 = String.format(sql2, "'%Y-%m-%d'", comUniCode);
		List<CreditAnalysisIndustrialDoc> list = jdbcTemplate.query(formatSql2,
				new BeanPropertyRowMapper(CreditAnalysisIndustrialDoc.class));
		// 删选同一天有多个评级的数据
		List<CreditAnalysisIndustrialDoc> industrialList = list.stream().distinct().collect(Collectors.toList());

		if (industrialList == null || industrialList.size() == 0) {

		} else if (industrialList.size() == 1) {
			CreditAnalysisIndustrialDoc doc = industrialList.get(0);
			doc.setRatingChange(initValue);
			doc.setNo(0);
			docList.add(doc);
		} else {
			for (int i = 0; i < industrialList.size(); i++) {
				CreditAnalysisIndustrialDoc yy_1 = industrialList.get(i);
				CreditAnalysisIndustrialDoc yy_2 = industrialList.get(i + 1);

				if (i == 0) {
					// 第一天数据初始值为null
					yy_1.setRatingChange(initValue);
					yy_1.setNo(i);
					docList.add(yy_1);
				}
				if (StringUtils.isEmpty(yy_2.getSubjectScore()) || Objects.equal("0", yy_2.getSubjectScore())) {
					yy_2.setRatingChange(null);
					yy_2.setNo(i + 1);
					docList.add(yy_2);
				} else {
					for (int k = i + 1; k >= 1; k--) {
						CreditAnalysisIndustrialDoc yy_compare = industrialList.get(k - 1);
						if (!StringUtils.isEmpty(yy_compare.getSubjectScore())
								&& !Objects.equal("0", yy_compare.getSubjectScore())) {
							// 设置yy_2的评级变动
							Integer val1 = Integer.valueOf(MAP.get(yy_compare.getSubjectScore()));
							Integer val2 = Integer.valueOf(MAP.get(yy_2.getSubjectScore()));
							yy_2.setRatingChange(val2.intValue() - val1.intValue());
							break;
						}
					}
					yy_2.setNo(i + 1);
					docList.add(yy_2);
				}

				if (industrialList.size() == i + 2) {
					break;
				}
			}

		}
		return docList;
	}

	// 构建中金数据
	public boolean bulidCicc() {
		boolean result = true;
		String sql1 = "select distinct(com_uni_code) from t_bond_creditanalysis_cicc where com_uni_code is not null ";
		List<Long> comUniCodeList = jdbcTemplate.queryForList(sql1, Long.class);
		List<CreditAnalysisCiccDoc> docList = new ArrayList<>();

		if (comUniCodeList == null || comUniCodeList.size() == 0) {
			return true;
		}

		String sql2 = "select id id,bond_code bondCode, bond_full_name bondFullName,bond_short_name bondShortName,"
				+ "   bond_match_state bondMatchState,iss_name issName,com_uni_code comUniCode,"
				+ "	  iss_match_state issMatchState,cicc_score ciccScore,iss_introduction issIntroduction,"
				+ "	  bond_and_rate_history bondAndRateHistory,profit_and_cash_flow profitAndCashFlow,"
				+ "	  capital_structure_and_debt_paying_ability capitalStructureAndDebtPayingAbility,"
				+ "	  DATE_FORMAT(rate_time,%s) rateTime," + "   update_time updateTime,"
				+ "   create_time createTime,user_id userId  " + "\n\tfrom t_bond_creditanalysis_cicc  "
				+ "\n\twhere com_uni_code = %2$d order by rate_time asc";

		comUniCodeList.forEach(item -> {
			String formatSql = String.format(sql2, "'%Y-%m-%d'", item);
			List<CreditAnalysisCiccDoc> list = jdbcTemplate.query(formatSql,
					new BeanPropertyRowMapper(CreditAnalysisCiccDoc.class));
			// 删选同一天有多个评级的数据
			List<CreditAnalysisCiccDoc> ciccList = list.stream().distinct().collect(Collectors.toList());

			if (ciccList == null || ciccList.size() == 0) {

			} else if (ciccList.size() == 1) {
				CreditAnalysisCiccDoc doc = ciccList.get(0);
				doc.setRatingChange(initValue);
				doc.setNo(0);
				docList.add(doc);
			} else {
				for (int i = 0; i < ciccList.size(); i++) {
					CreditAnalysisCiccDoc yy_1 = ciccList.get(i);
					CreditAnalysisCiccDoc yy_2 = ciccList.get(i + 1);

					if (i == 0) {
						// 第一天数据初始值为null
						yy_1.setRatingChange(initValue);
						yy_1.setNo(i);
						docList.add(yy_1);
					}

					if (StringUtils.isEmpty(yy_2.getCiccScore()) || Objects.equal("0", yy_2.getCiccScore())) {
						yy_2.setRatingChange(null);
						yy_2.setNo(i + 1);
						docList.add(yy_2);
					} else {
						for (int k = i + 1; k >= 1; k--) {
							CreditAnalysisCiccDoc yy_compare = ciccList.get(k - 1);
							if (!StringUtils.isEmpty(yy_compare.getCiccScore())
									&& !Objects.equal("0", yy_compare.getCiccScore())) {
								// 设置yy_2的评级变动
								Integer val1 = Integer.valueOf(CICCMAP.get(yy_compare.getCiccScore()));
								Integer val2 = Integer.valueOf(CICCMAP.get(yy_2.getCiccScore()));
								yy_2.setRatingChange(val2.intValue() - val1.intValue());
								break;
							}
						}
						yy_2.setNo(i + 1);
						docList.add(yy_2);
					}

					if (ciccList.size() == i + 2) {
						break;
					}
				}
			}
		});

		if (docList.size() == 0 || docList == null) {
			return false;
		}

		try {
			LOG.info("start clear mongodb data");
			mongoTemplate.remove(new Query(), "bond_credit_analysis_cicc");
			LOG.info("remove all end");
			mongoTemplate.insertAll(docList);
			LOG.info("insert in mongodb end ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error("插入数据失败" + e.getMessage());
			return false;
		}

		return true;
	}

	// 删除中金数据
	public Boolean deleteCicc(Long id) {
		List<CreditAnalysisCiccDoc> docList = new ArrayList<>();

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		CreditAnalysisCiccDoc docCicc = mongoTemplate.findOne(query, CreditAnalysisCiccDoc.class);
		if(null == docCicc){
			return true;
		}
		Long comUniCode = docCicc.getComUniCode();

		List<CreditAnalysisCiccDoc> list = mongoTemplate.find(
				new Query().addCriteria(Criteria.where("comUniCode").is(comUniCode)), CreditAnalysisCiccDoc.class);
		if (list.size() == 1) {
			mongoTemplate.remove(query, CreditAnalysisCiccDoc.class);
			return true;
		} else {
			docList = calculateCicc(comUniCode);
		}

		if (docList.size() == 0 || docList == null) {
			return false;
		}

		try {
			Query query2 = new Query();
			query2.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			mongoTemplate.remove(query2, "bond_credit_analysis_cicc");

			LOG.info("start insert data");
			mongoTemplate.insertAll(docList);
			LOG.info("insert data end ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error("同步数据失败" + e.getMessage());
			return false;
		}

		return true;
	}

	// 更新中金数据
	public Boolean addAndUpdateCicc(Long id) {
		List<CreditAnalysisCiccDoc> docList = new ArrayList<>();

		String sql1 = "select com_uni_code comUniCode from t_bond_creditanalysis_cicc where id = %1$d  ";
		String formatSql1 = String.format(sql1, id);
		List<Long> list = (List<Long>) jdbcTemplate.queryForList(formatSql1,Long.class);
		
		if ( list == null || list.size()==0) {
			return true;
		} else {
			Long comUniCode = list.get(0);
			docList = calculateCicc(comUniCode);
		}

		if (docList.size() == 0 || docList == null) {
			return false;
		}

		try {
			Long comUniCode = list.get(0);
			Query query = new Query();
			query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			mongoTemplate.remove(query, "bond_credit_analysis_cicc");

			LOG.info("start insert data");
			mongoTemplate.insertAll(docList);
			LOG.info("insert data end ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error("同步数据失败" + e.getMessage());
			return false;
		}

		return true;
	}

	public List<CreditAnalysisCiccDoc> calculateCicc(Long comUniCode) {
		List<CreditAnalysisCiccDoc> docList = new ArrayList<>();

		String sql2 = "select id id,bond_code bondCode, bond_full_name bondFullName,bond_short_name bondShortName,"
				+ "   bond_match_state bondMatchState,iss_name issName,com_uni_code comUniCode,"
				+ "	  iss_match_state issMatchState,cicc_score ciccScore,iss_introduction issIntroduction,"
				+ "	  bond_and_rate_history bondAndRateHistory,profit_and_cash_flow profitAndCashFlow,"
				+ "	  capital_structure_and_debt_paying_ability capitalStructureAndDebtPayingAbility,"
				+ "	  DATE_FORMAT(rate_time,%s) rateTime," + "   update_time updateTime,"
				+ "   create_time createTime,user_id userId  " + "\n\tfrom t_bond_creditanalysis_cicc  "
				+ "\n\twhere com_uni_code = %2$d order by rate_time asc";

		String formatSql2 = String.format(sql2, "'%Y-%m-%d'", comUniCode);
		List<CreditAnalysisCiccDoc> list = jdbcTemplate.query(formatSql2,
				new BeanPropertyRowMapper(CreditAnalysisCiccDoc.class));
		// 删选同一天有多个评级的数据
		List<CreditAnalysisCiccDoc> ciccList = list.stream().distinct().collect(Collectors.toList());

		if (ciccList == null || ciccList.size() == 0) {

		} else if (ciccList.size() == 1) {
			CreditAnalysisCiccDoc doc = ciccList.get(0);
			doc.setRatingChange(initValue);
			doc.setNo(0);
			docList.add(doc);
		} else {
			for (int i = 0; i < ciccList.size(); i++) {
				CreditAnalysisCiccDoc yy_1 = ciccList.get(i);
				CreditAnalysisCiccDoc yy_2 = ciccList.get(i + 1);

				if (i == 0) {
					// 第一天数据初始值为null
					yy_1.setRatingChange(initValue);
					yy_1.setNo(i);
					docList.add(yy_1);
				}

				if (StringUtils.isEmpty(yy_2.getCiccScore()) || Objects.equal("0", yy_2.getCiccScore())) {
					yy_2.setRatingChange(null);
					yy_2.setNo(i + 1);
					docList.add(yy_2);
				} else {
					for (int k = i + 1; k >= 1; k--) {
						CreditAnalysisCiccDoc yy_compare = ciccList.get(k - 1);
						if (!StringUtils.isEmpty(yy_compare.getCiccScore())
								&& !Objects.equal("0", yy_compare.getCiccScore())) {
							// 设置yy_2的评级变动
							Integer val1 = Integer.valueOf(CICCMAP.get(yy_compare.getCiccScore()));
							Integer val2 = Integer.valueOf(CICCMAP.get(yy_2.getCiccScore()));
							yy_2.setRatingChange(val2.intValue() - val1.intValue());
							break;
						}
					}
					yy_2.setNo(i + 1);
					docList.add(yy_2);
				}

				if (ciccList.size() == i + 2) {
					break;
				}
			}
		}

		return docList;
	}

	// 构建国君数据
	public boolean bulidGuoJun() {
		boolean result = true;
		String sql1 = "select distinct(com_uni_code) from t_bond_creditanalysis_guojun where com_uni_code is not null ";
		List<Long> comUniCodeList = jdbcTemplate.queryForList(sql1, Long.class);
		List<CreditAnalysisGuoJunDoc> docList = new ArrayList<>();

		if (comUniCodeList == null || comUniCodeList.size() == 0) {
			return true;
		}

		String sql2 = "select id id,bond_code bondCode, bond_full_name bondFullName,bond_short_name bondShortName,"
				+ "bond_match_state bondMatchState,iss_name issName,com_uni_code comUniCode,iss_match_state issMatchState,"
				+ "echelon echelon,guo_jun_indu guoJunIndu,"
				+ "guo_jun_score guoJunScore,guo_jun_operate_score guoJunOperateScore,guo_jun_finance_score guoJunFinanceScore,"
				+ "guo_jun_comment guoJunComment," + "DATE_FORMAT(rate_time,%s) rateTime,"
				+ "update_time updateTime,create_time createTime,user_id userId  "
				+ "from t_bond_creditanalysis_guojun where com_uni_code = %2$d order by rate_time asc";

		comUniCodeList.forEach(item -> {
			String formatSql = String.format(sql2, "'%Y-%m-%d'", item);
			List<CreditAnalysisGuoJunDoc> list = jdbcTemplate.query(formatSql,
					new BeanPropertyRowMapper(CreditAnalysisGuoJunDoc.class));
			// 删选同一天有多个评级的数据
			List<CreditAnalysisGuoJunDoc> guoJunList = list.stream().distinct().collect(Collectors.toList());

			if (guoJunList == null || guoJunList.size() == 0) {

			} else if (guoJunList.size() == 1) {
				CreditAnalysisGuoJunDoc doc = guoJunList.get(0);
				doc.setRatingChange(new BigDecimal(initValue).setScale(0, RoundingMode.HALF_UP));
				doc.setNo(0);
				docList.add(doc);
			} else {
				for (int i = 0; i < guoJunList.size(); i++) {
					CreditAnalysisGuoJunDoc yy_1 = guoJunList.get(i);
					CreditAnalysisGuoJunDoc yy_2 = guoJunList.get(i + 1);

					if (i == 0) {
						// 第一天数据初始值为null
						yy_1.setRatingChange(new BigDecimal(initValue).setScale(0, RoundingMode.HALF_UP));
						yy_1.setNo(i);
						docList.add(yy_1);
					}

					if (null == yy_2.getGuoJunScore()
							|| Objects.equal(yy_2.getGuoJunScore().setScale(0, BigDecimal.ROUND_HALF_UP),
									new BigDecimal(0).setScale(0, BigDecimal.ROUND_HALF_UP))) {
						// 设置yy_2的评级变动
						yy_2.setRatingChange(null);
						yy_2.setNo(i + 1);
						docList.add(yy_2);
					} else {
						for (int k = i + 1; k >= 1; k--) {
							CreditAnalysisGuoJunDoc yy_compare = guoJunList.get(k - 1);
							if (null != yy_compare.getGuoJunScore()
									&& !Objects.equal(yy_compare.getGuoJunScore().setScale(0, BigDecimal.ROUND_HALF_UP),
											new BigDecimal(0).setScale(0, BigDecimal.ROUND_HALF_UP))) {
								// 设置yy_2的评级变动
								BigDecimal val1 = yy_compare.getGuoJunScore();
								BigDecimal val2 = yy_2.getGuoJunScore();
								yy_2.setRatingChange(val2.subtract(val1).setScale(2, RoundingMode.HALF_UP));
								break;
							}
						}

						yy_2.setNo(i + 1);
						docList.add(yy_2);
					}

					if (guoJunList.size() == i + 2) {
						break;
					}
				}
			}
		});

		if (docList.size() == 0 || docList == null) {
			return false;
		}

		try {
			LOG.info("start clear mongodb data");
			mongoTemplate.remove(new Query(), "bond_credit_analysis_guojun");
			LOG.info("remove all end");
			mongoTemplate.insertAll(docList);
			LOG.info("insert in mongodb end ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error("插入数据失败" + e.getMessage());
			return false;
		}

		return true;
	}

	// 删除国君数据
	public Boolean deleteGuoJun(Long id) {
		List<CreditAnalysisGuoJunDoc> docList = new ArrayList<>();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		CreditAnalysisGuoJunDoc docGuoJun = mongoTemplate.findOne(query, CreditAnalysisGuoJunDoc.class);
		if(docGuoJun==null){
			return true;
		}
		Long comUniCode = docGuoJun.getComUniCode();
		
		List<CreditAnalysisGuoJunDoc> list= mongoTemplate.find(new Query().addCriteria(Criteria.where("comUniCode").is(comUniCode)), CreditAnalysisGuoJunDoc.class);
		if(list.size()==1){
			mongoTemplate.remove(query, CreditAnalysisGuoJunDoc.class);			
		}else{
			docList = calculateGuoJun(comUniCode);
		}
		
		if (docList.size() == 0 || docList == null) {
			return false;
		}

		try {
			
			Query query2 = new Query();
			query2.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			mongoTemplate.remove(query2, "bond_credit_analysis_guojun");

			LOG.info("remove all end");
			mongoTemplate.insertAll(docList);
			LOG.info("insert in mongodb end ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error("插入数据失败" + e.getMessage());
			return false;
		}

		return true;
	}
	
	//更新国君数据
	public Boolean addAndUpdateGuoJun(Long id){
		List<CreditAnalysisGuoJunDoc> docList = new ArrayList<>();
		
		String sql1 = "select com_uni_code comUniCode from t_bond_creditanalysis_guojun where id = %1$d";
		String formatSql1 = String.format(sql1, id);
		List<Long> list= jdbcTemplate.queryForList(formatSql1,Long.class);
		if(list == null || list.size()==0){
			return true;
		}else{
			Long comUniCode = list.get(0);
			docList = calculateGuoJun(comUniCode);
		}
		
		if (docList.size() == 0 || docList == null) {
			return false;
		}

		try {
			Long comUniCode = list.get(0);
			Query query = new Query();
			query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			mongoTemplate.remove(query, "bond_credit_analysis_guojun");

			LOG.info("remove all end");
			mongoTemplate.insertAll(docList);
			LOG.info("insert in mongodb end ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error("插入数据失败" + e.getMessage());
			return false;
		}

		return true;
	}

	public List<CreditAnalysisGuoJunDoc> calculateGuoJun(Long comUniCode) {
		List<CreditAnalysisGuoJunDoc> docList = new ArrayList<>();

		String sql2 = "select id id,bond_code bondCode, bond_full_name bondFullName,bond_short_name bondShortName,"
				+ "bond_match_state bondMatchState,iss_name issName,com_uni_code comUniCode,iss_match_state issMatchState,"
				+ "echelon echelon,guo_jun_indu guoJunIndu,"
				+ "guo_jun_score guoJunScore,guo_jun_operate_score guoJunOperateScore,guo_jun_finance_score guoJunFinanceScore,"
				+ "guo_jun_comment guoJunComment," + "DATE_FORMAT(rate_time,%s) rateTime,"
				+ "update_time updateTime,create_time createTime,user_id userId  "
				+ "from t_bond_creditanalysis_guojun where com_uni_code = %2$d order by rate_time asc";

		String formatSql = String.format(sql2, "'%Y-%m-%d'", comUniCode);
		List<CreditAnalysisGuoJunDoc> list = jdbcTemplate.query(formatSql,
				new BeanPropertyRowMapper(CreditAnalysisGuoJunDoc.class));
		// 删选同一天有多个评级的数据
		List<CreditAnalysisGuoJunDoc> guoJunList = list.stream().distinct().collect(Collectors.toList());

		if (guoJunList == null || guoJunList.size() == 0) {

		} else if (guoJunList.size() == 1) {
			CreditAnalysisGuoJunDoc doc = guoJunList.get(0);
			doc.setRatingChange(new BigDecimal(initValue).setScale(0, RoundingMode.HALF_UP));
			doc.setNo(0);
			docList.add(doc);
		} else {
			for (int i = 0; i < guoJunList.size(); i++) {
				CreditAnalysisGuoJunDoc yy_1 = guoJunList.get(i);
				CreditAnalysisGuoJunDoc yy_2 = guoJunList.get(i + 1);

				if (i == 0) {
					// 第一天数据初始值为null
					yy_1.setRatingChange(new BigDecimal(initValue).setScale(0, RoundingMode.HALF_UP));
					yy_1.setNo(i);
					docList.add(yy_1);
				}

				if (null == yy_2.getGuoJunScore()
						|| Objects.equal(yy_2.getGuoJunScore().setScale(0, BigDecimal.ROUND_HALF_UP),
								new BigDecimal(0).setScale(0, BigDecimal.ROUND_HALF_UP))) {
					// 设置yy_2的评级变动
					yy_2.setRatingChange(null);
					yy_2.setNo(i + 1);
					docList.add(yy_2);
				} else {
					for (int k = i + 1; k >= 1; k--) {
						CreditAnalysisGuoJunDoc yy_compare = guoJunList.get(k - 1);
						if (null != yy_compare.getGuoJunScore()
								&& !Objects.equal(yy_compare.getGuoJunScore().setScale(0, BigDecimal.ROUND_HALF_UP),
										new BigDecimal(0).setScale(0, BigDecimal.ROUND_HALF_UP))) {
							// 设置yy_2的评级变动
							BigDecimal val1 = yy_compare.getGuoJunScore();
							BigDecimal val2 = yy_2.getGuoJunScore();
							yy_2.setRatingChange(val2.subtract(val1).setScale(2, RoundingMode.HALF_UP));
							break;
						}
					}

					yy_2.setNo(i + 1);
					docList.add(yy_2);
				}

				if (guoJunList.size() == i + 2) {
					break;
				}
			}
		}

		return docList;
	}
	

}
