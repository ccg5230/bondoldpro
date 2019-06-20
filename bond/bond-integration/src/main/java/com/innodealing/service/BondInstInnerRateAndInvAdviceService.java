package com.innodealing.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.model.InstInnerRatingInfo;
import com.innodealing.model.InstInvestmentAdviceInfo;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;

/** 
* @author feng.ma
* @date 2017年11月16日 上午11:18:16 
* @describe 
*/
@Service
public class BondInstInnerRateAndInvAdviceService {
	
	public static final Logger LOG = LoggerFactory.getLogger(BondInstInnerRateAndInvAdviceService.class);

	protected @Autowired JdbcTemplate jdbcTemplate;
	
	/**
	 * getInstHistBondsDataMap 获取投资建议
	 * @param insts
	 * @return
	 */
	public Map<Integer, List<InstInvestmentAdviceInfo>> getInstHistBondsDataMap(List<Integer> insts){
		Map<Integer, List<InstInvestmentAdviceInfo>> instBondsData = new HashMap<>();
		insts.stream().forEach(orgId ->{
			Integer version = instDataVersion(orgId);
			if (null != version) {
				List<InstInvestmentAdviceInfo> bondsMap = getLastInvestmentAdvice(orgId, version);
				if (null != bondsMap && !bondsMap.isEmpty()) {
					instBondsData.put(orgId, bondsMap);
				}
			}
		});
		return instBondsData;
	}
	/**
	 * getInstHistIssuersDataMap 获取内部评级
	 * @param insts
	 * @return
	 */
	public Map<Integer, List<InstInnerRatingInfo>> getInstHistIssuersDataMap(List<Integer> insts){
		Map<Integer, List<InstInnerRatingInfo>> instIssuersData = new HashMap<>();
		insts.stream().forEach(orgId ->{
			Integer version = instDataVersion(orgId);
			if (null != version) {
				List<InstInnerRatingInfo> issuerMap = getLastInnerRating(orgId, version);
				if (null != issuerMap && !issuerMap.isEmpty()) {
					instIssuersData.put(orgId, issuerMap);
				}
			}
		});
		return instIssuersData;
	}
	
	/**
	 * 查询机构内部评级的数据版本
	 * @return
	 */
	private Integer instDataVersion(Integer orgId){
		Integer version = null;
		try {
			String sql = "SELECT MAX(t.version) AS version FROM institution.t_bond_inst_code t WHERE t.status=1 AND t.org_id="+orgId;
			version = jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (Exception e) {
			LOG.error("instDataVersion error:"+e.getMessage(), e);
		}
		return version;
	}
	
	/**
	 * getLastInvestmentAdvice 获取最新的投资建议
	 * @param orgId
	 * @param version
	 * @return
	 */
	private List<InstInvestmentAdviceInfo> getLastInvestmentAdvice(Integer orgId, Integer version){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.bondId,a.adviceId,a.adviceName,a.textFlag FROM( ");
		sql.append("	SELECT t.bond_uni_code AS bondId,t.investment_advice AS adviceId,t.investment_describe AS adviceName,(CASE t.INVESTMENT_ADVICE_DESDETAIL WHEN NULL THEN 0 WHEN '' THEN 0 ELSE 1 END) AS textFlag  "); 
		sql.append("	FROM institution.t_bond_inst_rating_hist t WHERE t.status=3 AND t.inst_id=").append(orgId).append(" AND t.version=").append(version).append(" AND t.bond_uni_code > 0 AND t.investment_advice > 0 ORDER BY t.rating_time DESC) a ");
		sql.append("GROUP BY a.bondId");
		LOG.info("getLastInvestmentAdvice sql:"+sql.toString());
		return jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<InstInvestmentAdviceInfo>(InstInvestmentAdviceInfo.class));
	}
	
	/**
	 * getLastInnerRating 获取最新的内部评级
	 * @param orgId
	 * @param version
	 * @return
	 */
	private List<InstInnerRatingInfo> getLastInnerRating(Integer orgId, Integer version){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.issuerId,a.ratingId,a.ratingName,a.textFlag FROM(  ");
		sql.append("	SELECT t.com_uni_code AS issuerId,t.rating AS ratingId,t.rating_name AS ratingName,(CASE t.RATING_DESCRIBE WHEN NULL THEN 0 WHEN '' THEN 0 ELSE 1 END) AS textFlag  ");
		sql.append("	FROM institution.t_bond_inst_rating_hist t WHERE t.status=3 AND t.inst_id=").append(orgId).append(" AND t.version=").append(version).append(" AND t.com_uni_code > 0 AND t.rating > 0 ORDER BY t.rating_time DESC) a  ");
		sql.append("GROUP BY a.issuerId ");
		LOG.info("getLastInnerRating sql:"+sql.toString());
		return jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<InstInnerRatingInfo>(InstInnerRatingInfo.class));

	}

	public void makeBondBasicInfolWithInstDataMap(BondBasicInfoDoc basicInfo,
			Map<Integer, List<InstInvestmentAdviceInfo>> instBondsData,
			Map<Integer, List<InstInnerRatingInfo>> instIssuersData) {
		Map<String, Object> instInvestmentAdviceMap = new HashMap<>();
		instBondsData.entrySet().stream().forEach(bondsAdvMap->{
			Integer orgId = bondsAdvMap.getKey();
			List<InstInvestmentAdviceInfo> bondData = bondsAdvMap.getValue();
			List<InstInvestmentAdviceInfo> advDataInfos = bondData.stream().filter(advInfo -> null != advInfo && advInfo.getBondId().compareTo(basicInfo.getBondUniCode() == null ? 0L:basicInfo.getBondUniCode()) == 0).collect(Collectors.toList());
			if (null != advDataInfos && !advDataInfos.isEmpty()) {
				InstInvestmentAdviceInfo advDataInfo = advDataInfos.get(0);
				instInvestmentAdviceMap.put("code"+orgId, advDataInfo.getAdviceId());
				instInvestmentAdviceMap.put("name"+orgId, advDataInfo.getAdviceName());
				instInvestmentAdviceMap.put("textFlag"+orgId, advDataInfo.getTextFlag());
			}
		});
		if (null != instInvestmentAdviceMap && !instInvestmentAdviceMap.isEmpty()) {
			basicInfo.setInstInvestmentAdviceMap(instInvestmentAdviceMap);
		}
		
		Map<String, Object> instRatingMap = new HashMap<>();
		instIssuersData.entrySet().stream().forEach(issuersRatMap ->{
			Integer orgId = issuersRatMap.getKey();
			List<InstInnerRatingInfo> issuerData = issuersRatMap.getValue();
			List<InstInnerRatingInfo> ratDataInfos = issuerData.stream().filter(issuerInfo -> null != issuerInfo && issuerInfo.getIssuerId().compareTo(basicInfo.getIssuerId() == null ? 0L:basicInfo.getIssuerId()) == 0).collect(Collectors.toList());
			if (null != ratDataInfos && !ratDataInfos.isEmpty()) {
				InstInnerRatingInfo ratDataInfo = ratDataInfos.get(0);
				instRatingMap.put("code"+orgId, ratDataInfo.getRatingId());
				instRatingMap.put("name"+orgId, ratDataInfo.getRatingName());
				instRatingMap.put("textFlag"+orgId, ratDataInfo.getTextFlag());
			}
		});
		if (null != instRatingMap && !instRatingMap.isEmpty()) {
			basicInfo.setInstRatingMap(instRatingMap);
		}
	}
	
	public void makeBondDetailWithInstDataMap(BondDetailDoc bondDetailDoc, Map<Integer, List<InstInvestmentAdviceInfo>> instBondsData,
			Map<Integer, List<InstInnerRatingInfo>> instIssuersData) {
		Map<String, Object> instInvestmentAdviceMap = new HashMap<>();
		instBondsData.entrySet().stream().forEach(bondsAdvMap->{
			Integer orgId = bondsAdvMap.getKey();
			List<InstInvestmentAdviceInfo> bondData = bondsAdvMap.getValue();
			List<InstInvestmentAdviceInfo> advDataInfos = bondData.stream().filter(advInfo -> null != advInfo && advInfo.getBondId().compareTo(bondDetailDoc.getBondUniCode() == null ? 0L:bondDetailDoc.getBondUniCode()) == 0).collect(Collectors.toList());
			if (null != advDataInfos && !advDataInfos.isEmpty()) {
				InstInvestmentAdviceInfo advDataInfo = advDataInfos.get(0);
				instInvestmentAdviceMap.put("code"+orgId, advDataInfo.getAdviceId());
				instInvestmentAdviceMap.put("name"+orgId, advDataInfo.getAdviceName());
				instInvestmentAdviceMap.put("textFlag"+orgId, advDataInfo.getTextFlag());
			}
		});
		if (null != instInvestmentAdviceMap && !instInvestmentAdviceMap.isEmpty()) {
			bondDetailDoc.setInstInvestmentAdviceMap(instInvestmentAdviceMap);
		}
		
		Map<String, Object> instRatingMap = new HashMap<>();
		instIssuersData.entrySet().stream().forEach(issuersRatMap ->{
			Integer orgId = issuersRatMap.getKey();
			List<InstInnerRatingInfo> issuerData = issuersRatMap.getValue();
			List<InstInnerRatingInfo> ratDataInfos = issuerData.stream().filter(issuerInfo -> null != issuerInfo && issuerInfo.getIssuerId().compareTo(bondDetailDoc.getComUniCode() == null ? 0L:bondDetailDoc.getComUniCode()) == 0).collect(Collectors.toList());
			if (null != ratDataInfos && !ratDataInfos.isEmpty()) {
				InstInnerRatingInfo ratDataInfo = ratDataInfos.get(0);
				instRatingMap.put("code"+orgId, ratDataInfo.getRatingId());
				instRatingMap.put("name"+orgId, ratDataInfo.getRatingName());
				instRatingMap.put("textFlag"+orgId, ratDataInfo.getTextFlag());
			}
		});
		if (null != instRatingMap && !instRatingMap.isEmpty()) {
			bondDetailDoc.setInstRatingMap(instRatingMap);
		}
	}
	
	/**
	 * makeComInfoWithInstDataMap构建主体发行人的内部评级
	 * @param comInfoDoc
	 * @param instIssuersDataMap
	 */
	public void makeComInfoWithInstDataMap(BondComInfoDoc comInfoDoc,
			Map<Integer, List<InstInnerRatingInfo>> instIssuersDataMap) {
		Map<String, Object> instRatingMap = new HashMap<>();
		instIssuersDataMap.entrySet().stream().forEach(issuersRatMap ->{
			Integer orgId = issuersRatMap.getKey();
			List<InstInnerRatingInfo> issuerData = issuersRatMap.getValue();
			List<InstInnerRatingInfo> ratDataInfos = issuerData.stream().filter(issuerInfo -> null != issuerInfo && issuerInfo.getIssuerId().compareTo(comInfoDoc.getComUniCode()==null ? 0L:comInfoDoc.getComUniCode()) == 0).collect(Collectors.toList());
			if (null != ratDataInfos && !ratDataInfos.isEmpty()) {
				InstInnerRatingInfo ratDataInfo = ratDataInfos.get(0);
				instRatingMap.put("code"+orgId, ratDataInfo.getRatingId());
				instRatingMap.put("name"+orgId, ratDataInfo.getRatingName());
				instRatingMap.put("textFlag"+orgId, ratDataInfo.getTextFlag());
			}
		});
		if (null != instRatingMap && !instRatingMap.isEmpty()) {
			comInfoDoc.setInstRatingMap(instRatingMap);
		}
	}
	
}
