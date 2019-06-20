package com.innodealing.bond.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.innodealing.adapter.BondPageAdapter;
import com.innodealing.bond.vo.finance.IssuerSortVo;
import com.innodealing.bond.vo.summary.BondPersonalRatingDataVO;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondAnalysisComRRS;
import com.innodealing.model.mongo.dm.BondPdRankDoc;
import com.innodealing.model.mongo.dm.BondQuoteDoc;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.SimpleStatistics;
import com.innodealing.util.StringUtils;

/**
 * @author Administrator
 *
 */
@Service
public class BondAnalysisService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BondAnalysisService.class);
		
    @Autowired
    private BondInduService induService;
    
	@Autowired
	private MongoTemplate mongoTemplate;
    
	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	protected JdbcTemplate jdbcTemplate;
    
    @Autowired
    @Qualifier("asbrsPerResultJdbcTemplate")
    protected JdbcTemplate asbrsPerResultJdbcTemplate;
	
	class ZScoreParam {
        public ZScoreParam(Double mean, Double stddev) {
            super();
            this.mean = mean;
            this.stddev = stddev;
        }
        private Double mean;   
        private Double stddev;

        public Double getMean() {
            return mean;
        }
        public void setMean(Double mean) {
            this.mean = mean;
        }
        public Double getStddev() {
            return stddev;
        }
        public void setStddev(Double stddev) {
            this.stddev = stddev;
        }
    };

	/**
	 * 违约概率排行
	 * @param sort 
	 * @param limit 
	 * @param i 
	 * @param induId 
	 */
	public BondPageAdapter<BondPdRankDoc> findPdRank(String induIds, int page, Integer limit, String sort, long userId) {
		
		String[] sortPars = sort.split(":");
		String sortField = sortPars[0];
		if (sortField.startsWith("pd")) {
		    sortField = "pdSortRRs";
		}
		Direction sortDir = sortPars[1].toLowerCase().startsWith("des")? 
				Sort.Direction.DESC : Sort.Direction.ASC;	
		PageRequest request = new PageRequest(page, limit, new Sort(sortDir, sortField));

		Query query = new Query();
		
		if (!StringUtils.isBlank(induIds) ) {
		    //处理多induId
		    List<Long> induParams = new ArrayList<Long>();
		    String[] induIdArray = induIds.split(",");
		    
		    for(String induId: induIdArray) induParams.add(Long.valueOf(induId));
		    if (!induParams.isEmpty()) 
		        query.addCriteria(Criteria.where(induService.getInduIdByUser(userId)).in(induParams));
		}
		
        return new BondPageAdapter<BondPdRankDoc>(
                mongoOperations.find(query.with(request), BondPdRankDoc.class), request, 
                mongoOperations.count(query, BondPdRankDoc.class)
                );
	}
	
    @EventListener
    @Async
    public void handleQuoteSaved(BondQuoteDoc event) {
    	LOG.info("event callback:" + event.toString());
    }

    /**
     * 获取主体对应行业的前10条VO数据(公共财报)
     * @param userId DM用户编号
     * @param issuerId 发行人编号
     * @param year 选定的年份(yyyy)
     * @param quarter 选定的季度(1/2/3/4)
     * @return
     */
	public List<IssuerSortVo> getTop10InPubIndu(Long userId, Long issuerId, Long year, Long quarter) {
		List<BondPdRankDoc> selectedPdRankList = getInduPdRankList(userId, issuerId, year, quarter);
		List<IssuerSortVo> result = getTop10VO(issuerId, null, selectedPdRankList);
		return result;
	}

	/**
	 * 获取主体对应行业的相邻的5条VO数据(公共财报)
	 * @param userId DM用户编号
	 * @param issuerId 发行人编号
	 * @param year 选定的年份(yyyy)
	 * @param quarter 选定的季度(1/2/3/4)
	 * @return
	 */
	public List<IssuerSortVo> getNear5InPubIndu(Long userId, Long issuerId, Long year, Long quarter) {
		List<BondPdRankDoc> selectedPdRankList = getInduPdRankList(userId, issuerId, year, quarter);
		List<IssuerSortVo> result = getNear5VO(issuerId, null, selectedPdRankList);
		return result;
	}
	
	/**
	 * 获取主体对应行业的前10条VO数据(私人财报)
	 * @param perComInfo
	 * @return
	 */
	public List<IssuerSortVo> getTop10InPerIndu(BondPersonalRatingDataVO perComInfo) {
		List<IssuerSortVo> result = null;
		List<BondPdRankDoc> sortedPdRankList = getPerPdRankList(perComInfo, true, true);
		if (sortedPdRankList == null || sortedPdRankList.isEmpty()) {
            LOG.error("getPerTop10InCurrIndu: NO sortedPdRankList here");
            return null;
        };
        result = getTop10VO(null, perComInfo.getCompName(), sortedPdRankList);
		return result;
	}
	
	/**
	 * 获取主体对应行业的相邻的5条VO数据(私人财报)
	 * @param perComInfo
	 * @return
	 */
	public List<IssuerSortVo> getNear5InPerIndu(BondPersonalRatingDataVO perComInfo) {
		List<IssuerSortVo> result = null;
		List<BondPdRankDoc> sortedPdRankList = getPerPdRankList(perComInfo, true, true);
		if (sortedPdRankList == null || sortedPdRankList.isEmpty()) {
            LOG.error("getNear5InPerIndu: NO sortedPdRankList here");
            return null;
        };
        result = getNear5VO(null, perComInfo.getCompName(), sortedPdRankList);
		return result;
	}
	
	/**
	 * 获取当前主体所在行业的所有PdRank记录
	 * @param userId DM用户编号
	 * @param issuerId 发行人编号
	 * @param year 选定的年份(yyyy)
	 * @param quarter 选定的季度(1/2/3/4)
	 * @return
	 */
	private List<BondPdRankDoc> getInduPdRankList(Long userId, Long issuerId, Long year, Long quarter) {
		List<BondPdRankDoc> result = getInduLatestPdRankDocList(issuerId, userId);
		Date tempDate = SafeUtils.getLastDayOfQuarter(Integer.valueOf(year.toString()), Integer.valueOf(quarter.toString()));
		// 如果选定的季度在当前时间之前，则强查，否则直接使用mongodb中缓存的数据
		result = SafeUtils.getCurrentTime().getTime() <= tempDate.getTime() ? result :
			getPdRankListByQuar(userId, issuerId, year, quarter, result, true);
        return result;
	}
	
	/**
	 * 从缓存获取同行业最新的排名
	 * @param issuerId
	 * @param userId
	 * @return
	 */
	private List<BondPdRankDoc> getInduLatestPdRankDocList(Long issuerId, Long userId) {
		// 获取当前主体行业
		List<Long> comUniCodeList = induService.findComUniCodeByIndu(issuerId, userId);
		Query query = new Query(Criteria.where("_id").in(comUniCodeList));
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "pdSortRRs")));
		List<BondPdRankDoc> pdRankList = mongoTemplate.find(query, BondPdRankDoc.class);

//		String induKey = induService.isGicsInduClass(userId) ? "induId" : "induIdSw";
//		BondPdRankDoc pdRankDoc = mongoTemplate.findOne(new Query(Criteria.where("issuerId").is(issuerId)), BondPdRankDoc.class);
//		if (null == pdRankDoc) {
//			return null;
//		}
//		Integer induId = induService.isGicsInduClass(userId) ? pdRankDoc.getInduId() : pdRankDoc.getInduIdSw();
//		// 获取当前行业的记录列表
//		Query query = new Query(Criteria.where(induKey).is(induId));
//		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "pdSortRRs")));
//		List<BondPdRankDoc> pdRankList = mongoTemplate.find(query, BondPdRankDoc.class);
		return pdRankList;
	}
	
	/**
	 * 按照给定的季度获取全市场排名
	 * @param issuerId DM用户编号，若isIndu为true, 则用于生成限制行业的约束条件
	 * @param userId 发行人编号, 若isIndu为true, 则用于生成限制行业的约束条件
	 * @param year 选定的年份(yyyy)
	 * @param quarter 选定的季度(1/2/3/4)
	 * @param mongoResult
	 * @param isIndu 是否限制为某一特定行业
	 * @return
	 */
	public List<BondPdRankDoc> getPdRankListByQuar(Long userId, Long issuerId, Long year, Long quarter,
			List<BondPdRankDoc> mongoResult, boolean isIndu) {
		List<BondPdRankDoc> result = null;
		List<BondAnalysisComRRS> bondRatingRRSList = getBondRatingRRSListByQuar(userId, issuerId, year, quarter, isIndu, null);
		result = calcAndSortPdRankListWithSql(bondRatingRRSList, mongoResult);
		return result;
	}
	
	/**
	 * 根据传入参数获取定制的RRS列表
	 * @param userId DM用户编号，若isIndu为true, 则用于生成限制行业的约束条件
	 * @param issuerId 发行人编号, 若isIndu为true, 则用于生成限制行业的约束条件
	 * @param year 选定的年份(yyyy)
	 * @param quarter 选定的季度(1/2/3/4)
	 * @param isIndu 是否限制为某一特定行业
	 * @param perInduKey 私人财报行业的Key, 若不为空则isIndu+userId+issuerId会被忽略
	 * @return
	 */
	private List<BondAnalysisComRRS> getBondRatingRRSListByQuar(Long userId, Long issuerId, Long year,
			Long quarter, boolean isIndu, String perInduKey) {
		List<BondAnalysisComRRS> result = null;
		String finalSql = getQueryRatingRRSSql(userId, issuerId, year, quarter, isIndu, perInduKey);
		if (StringUtils.isNotBlank(finalSql)) {
			result = (List<BondAnalysisComRRS>) jdbcTemplate.query(finalSql, 
	                new BeanPropertyRowMapper<BondAnalysisComRRS>(BondAnalysisComRRS.class));
			if (result == null || result.isEmpty()) {
	            LOG.error("getBondRatingRRSList: NO bondRatingRRSList here");
	        };
		}
        return result;
	}

	/**
	 * 根据参数获取查询RRS定制的sql
	 * @param userId DM用户编号，若isIndu为true, 则用于生成限制行业的约束条件
	 * @param issuerId 发行人编号, 若isIndu为true, 则用于生成限制行业的约束条件
	 * @param year 选定的年份(yyyy)
	 * @param quarter 选定的季度(1/2/3/4)
	 * @param isIndu 是否限制为某一特定行业
	 * @param perInduKey 私人财报行业的Key, 若不为空则isIndu+userId+issuerId会被忽略
	 * @return
	 */
	private String getQueryRatingRRSSql(Long userId, Long issuerId, Long year, Long quarter,
			boolean isIndu, String perInduKey) {
		String result = null;
		String induCriteriaStr = "";
		if (StringUtils.isNotBlank(perInduKey)) {
			// 私人财报
			induCriteriaStr = String.format("AND Comp_ID IN (SELECT DISTINCT ama_com_id FROM dmdb.t_bond_com_ext"
					+ " WHERE ama_com_id IS NOT NULL AND indu_uni_code LIKE '%%%s%%')", perInduKey);
		} else if (isIndu) {
			// 限制行业
			List<Long> induAllComUniCodeList = induService.getAmaComIdListByIndu(issuerId, userId);
			String induAllComUniCodeStr = induAllComUniCodeList.isEmpty() ? "''"
					: org.apache.commons.lang.StringUtils.join(induAllComUniCodeList.toArray(), ",");
			induCriteriaStr = String.format("AND Comp_ID IN (%1$s)", induAllComUniCodeStr);
		}
		
		Long finalQuar = quarter * 3;
        // 只取两年数据做标准
		Long validYear = year - 1;
		String sql = "SELECT P.Comp_ID, P.Comp_Name, P.year, X.com_uni_code, P.Rating, R.id as pdOrder, S.model_id, S.model_name,"
				+ " IFNULL(RATIO1, 0) + IFNULL(RATIO2, 0) + IFNULL(RATIO3, 0) + IFNULL(RATIO4, 0) + IFNULL(RATIO5, 0)"
				+ " + IFNULL(RATIO6, 0) + IFNULL(RATIO7, 0) + IFNULL(RATIO8, 0) + IFNULL(RATIO9, 0) + IFNULL(RATIO10, 0) AS score"
				+ " FROM (SELECT * FROM ("	
				+ " SELECT Comp_ID, Comp_Name, Rating, year, concat(year, LPAD(CAST(quan_month AS CHAR(10)), 2, '0')) AS ym"
				+ " FROM dmdb.dm_bond"
				+ " WHERE (`YEAR`<%1$d || (`YEAR`=%1$d && quan_month<=%2$d)) && `YEAR`>=%3$d"
				+ " %4$s" // 行业限制
				+ " AND Rating IS NOT NULL"
				+ " ORDER BY dm_bond.year DESC, dm_bond.quan_month DESC"
				+ " ) AS T GROUP BY Comp_ID) AS P"
				+ " LEFT JOIN dmdb.rating_ratio_score AS S ON P.Comp_ID = S.Comp_ID AND P.ym = S.YEAR"
				+ " LEFT JOIN dmdb.t_bond_com_ext AS X ON P.Comp_ID = X.ama_com_id"
                + " LEFT JOIN dmdb.t_bond_pd_par AS R ON P.Rating = R.rating"
                + " WHERE X.com_uni_code IS NOT NULL AND S.model_id IS NOT NULL AND R.id IS NOT NULL ORDER BY model_id, Comp_ID;";
		result = String.format(sql, year, finalQuar, validYear, induCriteriaStr);
		return result;
	}
	
	/**
	 * 取出sql的结果并进行计算标准差和排序
	 * @param records
	 * @param mongoResult mongo中缓存的结果, 用于取得正在流通的债券数量
	 * @return
	 */
	private List<BondPdRankDoc> calcAndSortPdRankListWithSql(List<BondAnalysisComRRS> records,
			List<BondPdRankDoc> mongoResult) {
		HashMap<Long, Double> comRrsMap = new HashMap<Long, Double>();
	    HashMap<Long, Long> comPdMap = new HashMap<Long, Long>();
	    HashMap<Long, ZScoreParam> modelStdDevMap = new HashMap<Long, ZScoreParam>();
	    HashMap<Long, String> comIssMap = new HashMap<Long, String>();
	    
	    HashMap<Long, Long> comBondMap = new HashMap<Long, Long>();
	    if (null != mongoResult) {
	    	mongoResult.forEach(item -> comBondMap.put(item.getIssuerId(), item.getEffectiveBondCount()));
	    }
        
        //计算模型score标准差
        Long curModelId = null;
        BondAnalysisComRRS curCompRrs = null;
        List<Double> modelScores = new ArrayList<Double>();

        for (int i = 0; i < records.size(); ++ i) {
            curCompRrs = records.get(i);
            
            //跳过score缺最新一期的数据
            if (curCompRrs.getModelId() == null) {
                LOG.warn("rating_ratio_score 缺少最新一期数据, ComUniCode:" + curCompRrs.getComUniCode());
                continue;
            }
            
            // 保存属性
            comIssMap.put(curCompRrs.getComUniCode(), curCompRrs.getCompName());
            if (null != curCompRrs.getEffeBondCount()) {
            	// 如果根据sql查出来的流通债券数有值，则覆盖
            	comBondMap.put(curCompRrs.getComUniCode(), curCompRrs.getEffeBondCount());
            }
            
            if (curModelId == null) {
                curModelId = curCompRrs.getModelId();
            } else if (!curModelId.equals(curCompRrs.getModelId())) {
                saveModelDev(curModelId, modelScores, modelStdDevMap);
                modelScores = new ArrayList<Double>();
                curModelId = curCompRrs.getModelId();
            }
            modelScores.add(curCompRrs.getScore());
        }

        saveModelDev(curModelId, modelScores, modelStdDevMap);

        //计算公司score在行业中的位置
        for (BondAnalysisComRRS r : records) {
            Double sortScore = null;
            if (r.getModelId() != null) {
                ZScoreParam zScoreParam = modelStdDevMap.get(r.getModelId());
                if (zScoreParam != null) {
                    if (r.getScore() != null) {
                    	sortScore = (r.getScore() - zScoreParam.getMean())/zScoreParam.getStddev();
                    	sortScore = Double.isNaN(sortScore) ? 0.0 : sortScore;	// 样本数量只有1个
                        comRrsMap.put(r.getComUniCode(), r.getPdOrder()* 10 - sortScore);
                    }
                }
                else {
                    LOG.error("modelId " + r.getModelId() + " does not exist");
                }
            }
            comPdMap.put(r.getComUniCode(), r.getPdOrder());
            LOG.debug("compId:" + r.getComUniCode() + "comp:" + r.getCompName() + " sort-score:" + sortScore);
        }
        
        // 倒序，需求要求评级高的排在最上面
        List<Map.Entry<Long, Double>> sortParamList = new ArrayList<Map.Entry<Long, Double>>(comRrsMap.entrySet());
		Collections.sort(sortParamList, (v1, v2) -> v1.getValue().compareTo(v2.getValue()));
        
        return assemblyToPdRankDocList(sortParamList, comIssMap, comBondMap);
	}
	
	/**
	 * 将标准差映射封装成BondPdRankDoc对象
	 * @param sortedMap
	 * @param comIssMap
	 * @param comBondMap
	 * @return
	 */
	private List<BondPdRankDoc> assemblyToPdRankDocList(List<Entry<Long, Double>> sortedMap,
			HashMap<Long, String> comIssMap, HashMap<Long, Long> comBondMap) {
		List<BondPdRankDoc> result = new ArrayList<BondPdRankDoc>();
		sortedMap.forEach(item -> {
			Long issuerId = item.getKey();
			BondPdRankDoc doc = new BondPdRankDoc();
			doc.setIssuerId(issuerId);
			doc.setPdSortRRs(item.getValue());
			doc.setIssuer(comIssMap.get(issuerId));
			Long effectiveBondCount = 0L;
			if (null != comBondMap.get(issuerId)) effectiveBondCount = comBondMap.get(issuerId);
			doc.setEffectiveBondCount(effectiveBondCount);
			result.add(doc);
		});
		return result;
	}
    
    private void saveModelDev(Long curModelId, List<Double> modelScores, HashMap<Long, ZScoreParam> modelStdDevMap) {
        Double stdDev = new SimpleStatistics(modelScores.toArray(new Double[modelScores.size()])).getStdDev();
        Double mean = new SimpleStatistics(modelScores.toArray(new Double[modelScores.size()])).getMean();
        LOG.info("model:" + curModelId + " mean:" + mean + " stddev:" + stdDev);
        modelStdDevMap.put(curModelId, new ZScoreParam(mean, stdDev));
    }
	
    /**
     * 获取前10条数据
     * @param issuerId 如果issuerId不为空，则以此判断是否标记为oneself
     * @param issuerName 如果issuerId为空，则以此判断是否标记为oneself(私人财报数据没有issuerId)
     * @param pdRankList
     * @return
     */
	public List<IssuerSortVo> getTop10VO(Long issuerId, String issuerName, List<BondPdRankDoc> pdRankList) {
		if(pdRankList == null) throw new BusinessException("无法获取同行业信用排名前10的数据");
		List<IssuerSortVo> result = new ArrayList<IssuerSortVo>();
		try {
			// 取前10条Doc
			pdRankList = pdRankList.size() < 10 ? pdRankList : pdRankList.subList(0, 10);
			// 装配IssuerSortVo
			int count = 1;
			for (BondPdRankDoc pdRank : pdRankList) {
				IssuerSortVo issuerSort = new IssuerSortVo();
				issuerSort.setIdx(count);
				issuerSort.setIssuerId(pdRank.getIssuerId());
				issuerSort.setIssuerName(pdRank.getIssuer());
				issuerSort.setPdSortRRs(pdRank.getPdSortRRs());
				int isOneself = 0;
				if (issuerId != null || StringUtils.isNotBlank(issuerName)) {
					isOneself = null != issuerId ?
						(0 == pdRank.getIssuerId().compareTo(issuerId) ? 1 : 0) :
						(issuerName.equals(pdRank.getIssuer()) ? 1 : 0);
				}
				issuerSort.setOneself(isOneself);
				issuerSort.setEffectiveBondCount(pdRank.getEffectiveBondCount());
				count++;
				result.add(issuerSort);
			}
		} catch (Exception ex) {
			LOG.error(String.format("getPerPdRankList: ex[%s]", ex.getMessage()));
			throw new BusinessException("获取同行业信用排名前10数据时出错");
		}
		return result;
	}
	
	/**
	 * 获取当前目标附近5条记录(肯定包括自己)
	 * @param issuerId 如果issuerId不为空，则以此判断是否标记为oneself
	 * @param issuerName 如果issuerId为空，则以此判断是否标记为oneself(私人财报数据没有issuerId)
	 * @param pdRankList
	 * @return
	 */
	public List<IssuerSortVo> getNear5VO(Long issuerId, String issuerName, List<BondPdRankDoc> pdRankList) {
		if(pdRankList == null) throw new BusinessException("无法获取同行业信用排名Near5的数据");
		List<IssuerSortVo> result = new ArrayList<IssuerSortVo>();
		try {
			if (pdRankList.size() < 6) {
				// 小于6条记录，直接装配返回
				return convertFromDocListToVOList(1, issuerId, issuerName, pdRankList);
			}
			int myIndex = 0;
			for (int i = 0; i < pdRankList.size(); i++) {
				if (issuerId != null ? 0 == pdRankList.get(i).getIssuerId().compareTo(issuerId) :
					issuerName.equals(pdRankList.get(i).getIssuer())) {
					myIndex = i;
					break;
				}
			}
			if (myIndex == 0) {
				// 第一条
				result = convertFromDocListToVOList(1, issuerId, issuerName, pdRankList.subList(0, 5));
			} else if (myIndex == 1) {
				// 第二条
				result = convertFromDocListToVOList(2, issuerId, issuerName, pdRankList.subList(1, 6));
			} else if (myIndex == (pdRankList.size() - 1)) {
				// 最后一条
				result.add(convertFromDocToVO(myIndex - 3, issuerId, issuerName, pdRankList.get(myIndex - 4)));
				result.add(convertFromDocToVO(myIndex - 2, issuerId, issuerName, pdRankList.get(myIndex - 3)));
				result.add(convertFromDocToVO(myIndex - 1, issuerId, issuerName, pdRankList.get(myIndex - 2)));
				result.add(convertFromDocToVO(myIndex, issuerId, issuerName, pdRankList.get(myIndex - 1)));
				result.add(convertFromDocToVO(myIndex + 1, issuerId, issuerName, pdRankList.get(myIndex)));
			} else if (myIndex == (pdRankList.size() - 2)) {
				// 倒数第二条
				result = new ArrayList<>();
				result.add(convertFromDocToVO(myIndex - 2, issuerId, issuerName, pdRankList.get(myIndex - 3)));
				result.add(convertFromDocToVO(myIndex - 1, issuerId, issuerName, pdRankList.get(myIndex - 2)));
				result.add(convertFromDocToVO(myIndex, issuerId, issuerName, pdRankList.get(myIndex - 1)));
				result.add(convertFromDocToVO(myIndex + 1, issuerId, issuerName, pdRankList.get(myIndex)));
				result.add(convertFromDocToVO(myIndex + 2, issuerId, issuerName, pdRankList.get(myIndex + 1)));
			} else {
				// 其他情况，当前主体记录放在中间一条
				result = new ArrayList<>();
				result.add(convertFromDocToVO(myIndex - 1, issuerId, issuerName, pdRankList.get(myIndex - 2)));
				result.add(convertFromDocToVO(myIndex, issuerId, issuerName, pdRankList.get(myIndex - 1)));
				result.add(convertFromDocToVO(myIndex + 1, issuerId, issuerName, pdRankList.get(myIndex)));
				result.add(convertFromDocToVO(myIndex + 2, issuerId, issuerName, pdRankList.get(myIndex + 1)));
				result.add(convertFromDocToVO(myIndex + 3, issuerId, issuerName, pdRankList.get(myIndex + 2)));
			}
		} catch (Exception ex) {
			LOG.error(String.format("getPerPdRankList: ex[%s]", ex.getMessage()));
			throw new BusinessException("获取同行业信用排名Near5数据时出错");
		}
		return result;
	}
	
	/**
	 * 将BondPdRankDoc转成IssuerSortVo
	 * @param index 当前索引
	 * @param issuerId 如果issuerId不为空，则以此判断是否标记为oneself
	 * @param issuerName 如果issuerId为空，则以此判断是否标记为oneself(私人财报数据没有issuerId)
	 * @param pdRankDoc 目标BondPdRankDoc
	 * @return
	 */
	private IssuerSortVo convertFromDocToVO(int index, Long issuerId, String issuerName, BondPdRankDoc pdRankDoc) {
		IssuerSortVo result = new IssuerSortVo();
		result.setIdx(index);
		result.setIssuerId(pdRankDoc.getIssuerId());
		result.setIssuerName(pdRankDoc.getIssuer());
		result.setPdSortRRs(pdRankDoc.getPdSortRRs());
		int isOneself = 0;
		if (issuerId != null || StringUtils.isNotBlank(issuerName)) {
			isOneself = null != issuerId ?
				(0 == pdRankDoc.getIssuerId().compareTo(issuerId) ? 1 : 0) :
				(issuerName.equals(pdRankDoc.getIssuer()) ? 1 : 0);
		}
		result.setOneself(isOneself);
		result.setEffectiveBondCount(pdRankDoc.getEffectiveBondCount());
		return result;
	}
	
	/**
	 * 将BondPdRankDoc列表转成IssuerSortVo列表
	 * @param startIndex 开始索引位置
	 * @param issuerId 如果issuerId不为空，则以此判断是否标记为oneself
	 * @param issuerName 如果issuerId为空，则以此判断是否标记为oneself(私人财报数据没有issuerId)
	 * @param pdRankDocList 目标BondPdRankDoc列表
	 * @return
	 */
	private List<IssuerSortVo> convertFromDocListToVOList(int startIndex, Long issuerId, String issuerName, List<BondPdRankDoc> pdRankDocList) {
		List<IssuerSortVo> result = new ArrayList<IssuerSortVo>();
		int count = startIndex;
		for (BondPdRankDoc pdRand : pdRankDocList) {
			result.add(convertFromDocToVO(count, issuerId, issuerName, pdRand));
			count++;
		}
		return result;
	}
	
	/**
	 * 获取私人财报的得分数据集
	 * @param perComInfo
	 * @param isIndu 是否单个行业
	 * @param isNeedMongoDocList 是否需要取出mongo中的BondPdRankDoc（用于辅助计算流通债券数量）
	 * @return
	 */
	public List<BondPdRankDoc> getPerPdRankList(BondPersonalRatingDataVO perComInfo, boolean isIndu, boolean isNeedMongoDocList) {
		List<BondPdRankDoc> result = null;
		try {
	        Long perYear = perComInfo.getYear();
	        Long quarter = perComInfo.getMonth() / 3L;
	        String compName = perComInfo.getCompName();
	        
	        // 根据perRatingData获取最终得分
	        String perModelSql = "SELECT '%2$s' AS compName, %3$d AS `year`, '%4$s' AS Rating,"
	        		+ " '%5$s' AS pdOrder, rrs.model_id, rrs.model_name,"
	        		+ " IFNULL(RATIO1, 0) + IFNULL(RATIO2, 0) + IFNULL(RATIO3, 0) + IFNULL(RATIO4, 0) + IFNULL(RATIO5, 0)"
	        		+ " + IFNULL(RATIO6, 0) + IFNULL(RATIO7, 0) + IFNULL(RATIO8, 0) + IFNULL(RATIO9, 0) + IFNULL(RATIO10, 0) AS score"
	        		+ " FROM v_dm_personal_rating_ratio_score AS rrs"
	        		+ " WHERE rrs.taskid=%1$d AND rrs.`YEAR`=%6$d";
	        String formatPerModelSql = String.format(perModelSql, perComInfo.getTaskId(), compName, perYear, perComInfo.getRating(),
	        		perComInfo.getRatingValue(), perComInfo.getYearMonth());
			List<BondAnalysisComRRS> perRRSList = (List<BondAnalysisComRRS>) asbrsPerResultJdbcTemplate.query(formatPerModelSql, 
	                new BeanPropertyRowMapper<BondAnalysisComRRS>(BondAnalysisComRRS.class));
			if (perRRSList == null || perRRSList.isEmpty()) {
	            LOG.error("getPerPdRankList: NO perRRSList here");
	            return null;
	        };
	        BondAnalysisComRRS perRRS = perRRSList.get(0);
	        
	        // 获取同行业的评分列表，未排名
	        String querySql = "";
	        if (isIndu) {
	        	querySql = getQueryRatingRRSSql(null, perRRS.getComUniCode(), perYear, quarter, true, perComInfo.getInduPre());
	        } else {
	        	querySql = getQueryRatingRRSSql(null, perRRS.getComUniCode(), perYear, quarter, false, null);
	        }
	        List<BondAnalysisComRRS> pubRRSList = null;
	        if (StringUtils.isBlank(querySql)) {
	        	LOG.error("getPerPdRankList: cannot generate querySql");
	            return null;
	        }
	    	pubRRSList = (List<BondAnalysisComRRS>) jdbcTemplate.query(querySql, 
	                new BeanPropertyRowMapper<BondAnalysisComRRS>(BondAnalysisComRRS.class));
			if (pubRRSList == null || pubRRSList.isEmpty()) {
	            LOG.warn("getPerPdRankList: NO pubRRSList here");
	            return null;
	        };
	        // 排名之前，按照公司名称，用私人数据替换掉公共数据
	        int count = 0;
	        for (BondAnalysisComRRS item : pubRRSList) {
	        	if (compName.equals(item.getCompName())) break;
	        	count++;
	        }
	        if (count < pubRRSList.size())
	        	pubRRSList.set(count, perRRS);
	        else
	        	pubRRSList.add(perRRS);
	        // 获取排名结果
	        List<BondPdRankDoc> pdRankList = null;
	        if (isNeedMongoDocList) {
		        Query query = new Query();
		        if (isIndu) {
		        	query.addCriteria(Criteria.where("induId").is(perComInfo.getInduId()));
		        }
				query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "pdSortRRs")));
				pdRankList = mongoTemplate.find(query, BondPdRankDoc.class);
				if (pdRankList == null || pdRankList.isEmpty()) {
		            LOG.error("getPerPdRankList: NO pdRankList here");
		            return null;
		        };
	        }
			result = calcAndSortPdRankListWithSql(pubRRSList, pdRankList);
		} catch (Exception ex) {
			LOG.error(String.format("getPerPdRankList: ex[%s]", ex.getMessage()));
		}
        return SafeUtils.throwNullBusinessEx(result, "无法获取私人财报的得分数据集");
	}

	
}
