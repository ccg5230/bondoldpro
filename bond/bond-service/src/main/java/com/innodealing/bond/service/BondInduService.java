package com.innodealing.bond.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.bond.service.finance.BondFinanceInfoService;
import com.innodealing.bond.vo.finance.BondIndicatorExpressionVo;
import com.innodealing.bond.vo.finance.ComQuantileInfoVo;
import com.innodealing.bond.vo.indu.BondComInfoDetailVo;
import com.innodealing.bond.vo.indu.BondComInfoPdVo;
import com.innodealing.bond.vo.indu.BondComInfoRatingVo;
import com.innodealing.bond.vo.indu.InduVo;
import com.innodealing.bond.vo.indu.Indus;
import com.innodealing.consts.Constants;
import com.innodealing.domain.vo.bond.PdMappingVo;
import com.innodealing.engine.jdbc.bond.BondIndicatorExpressionDao;
import com.innodealing.engine.jpa.dm.BondComExtRepository;
import com.innodealing.engine.jpa.dm.BondUserInduClassRepository;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondComExt;
import com.innodealing.model.dm.bond.BondIndicatorExpression;
import com.innodealing.model.dm.bond.BondUserInduClass;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondComInfoRatingInduDoc;
import com.innodealing.model.mongo.dm.BondPdRankDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisCiccDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisGuoJunDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisIndustrialDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisRatingDogDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssPdDoc;
import com.innodealing.model.mongo.dm.bond.inud.BondInduSwMapDoc;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.FindInduRatingDownSortComparator;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;


/**
 * 行业service
 * 
 * @author zhaozhenglai
 * @since 2016年9月19日 下午4:10:07 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
@Service
public class BondInduService {

	private final static Logger LOG = LoggerFactory.getLogger(BondInduService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private BondUserInduClassRepository useInfoRepository;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	PdMappingService pdMappingService;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private BondIndicatorExpressionDao bondIndicatorExpressionDao;

	@Autowired
	BondUserInstitution bondUserInstitution;
	
	@Autowired 
	private BondComInfoRepository bondComInfoRepository;

	@Autowired
	protected BondComExtRepository comExtRepository;
	
	@Autowired
	private BondUserOperationService bondUserOperationService;
	
	@Autowired 
	private BondComInfoService bondComInfoService;
	
	@Autowired
	private BondFinanceInfoService bondFinanceInfoService;

	public List<InduVo> findAllSubIndu() {
		String sql = "SELECT indu_uni_code AS induId,indu_class_name AS induName FROM t_pub_indu_code WHERE fat_uni_code > 0 ORDER BY fat_uni_code DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<InduVo>(InduVo.class));
	}

	public boolean isGicsInduClass(Long userId) {
		// 这个函数被内部数据处理调用，缺省使用GICS分类
		Integer induClass = findInduClassByUser(userId);
		return (induClass == null) ? true : !induClass.equals(Constants.INDU_CLASS_SW);
	}

	// 注意： 函数缺省是GICS分类，用于处理数据时发现行业分类不存在
	public String getInduIdByClass(Integer induClass,Long userid) {
		String str = null;
		if (!isInduInstitution(userid)) {
			str = (induClass == null) ? Constants.INDU_CLASS_GICS_ID
					: induClass.equals(Constants.INDU_CLASS_SW) ? Constants.INDU_CLASS_SW_ID : Constants.INDU_CLASS_GICS_ID;
		}else{
			Integer org_id = getInstitution(userid);
			str = Constants.INSTIRUTION_INDU_CODE+org_id;
		}
		return str;
	}

	/**
	 * 获取行业name，用于按照行业name排序
	 * @param userId
	 * @return
	 */
	public String getInduNameByUserId(Long userId) {
		String result;
		if (!this.isInduInstitution(userId)) {
			result = this.isGicsInduClass(userId) ? Constants.INDU_CLASS_GICS_NAME : Constants.INDU_CLASS_SW_NAME;
		} else {
			Integer orgId = this.getInstitution(userId);
			result = Constants.INSTIRUTION_INDU_NAME + orgId;
		}
		return result;
	}

	// 注意： 函数缺省值是未定义，可用于判断用户是否设置了行业分类
	public String getInduIdByUser(Long userId) {
		return isGicsInduClass(userId) ? Constants.INDU_CLASS_GICS_ID : Constants.INDU_CLASS_SW_ID;
	}

	/**
	 * 根据userId获取用户所属的行业分类：1-GICS;2-SW;3-CUSTOM
	 * 
	 * @param userId
	 * @return
	 */
	public Integer findInduClassByUser(Long userId) {
//		Integer induClass = (Integer) redisUtil.get(Constants.INDU_CLASS_REDIS_PREFIX + userId);
//		if (induClass == null) {
//			// redis不作为持久化存储，如果不存在该键还需要再检查mysql
//			if (!useInfoRepository.exists(userId))
//				return Constants.INDU_CLASS_UNDEFINED;
//			induClass = useInfoRepository.findOne(userId).getInduClass();
//			if (induClass != null) {
//				redisUtil.set(Constants.INDU_CLASS_REDIS_PREFIX + userId, induClass, (long) 60 * 5);
//			}
//		}
//		return induClass;
		
		if (this.isInduInstitution(userId)) {
			// 自定义行业
			return Constants.INDU_CLASS_CUSTOM;
		} else {
			if (!useInfoRepository.exists(userId)) {
				// 用户不存在
				return Constants.INDU_CLASS_UNDEFINED;
			} else {
				return useInfoRepository.findOne(userId).getInduClass();
			}
		}
	}

	public void updateInduClassByUser(Long userId, Integer induClass) {
		BondUserInduClass userIndu = new BondUserInduClass();
		userIndu.setUpdateTime(new Date());
		userIndu.setUserId(userId);
		userIndu.setInduClass(induClass);
		useInfoRepository.save(userIndu);
		// redis缓存, 提高访问速度
		redisUtil.set(Constants.INDU_CLASS_REDIS_PREFIX + userId, induClass);
	}

	public List<Indus> findInduMetaInfo(Long userId) {
		if (!useInfoRepository.exists(userId))
			throw new BusinessException("未设置行业分类");
		Integer induClass = useInfoRepository.findOne(userId).getInduClass();
		String tableName = induClass.equals(Constants.INDU_CLASS_GICS) ? "t_pub_indu_code"
				: induClass.equals(Constants.INDU_CLASS_SW) ? "t_pub_indu_code_sw" : "";
		if (StringUtils.isEmpty(tableName)) {
			throw new BusinessException("未设置行业分类");
		}
		String sql = String.format("select A.indu_uni_code AS id, A.indu_class_name as label, "
				+ "B.indu_uni_code AS groupId, B.indu_class_name as 'group'  "
				+ "from %1$s A left join %1$s B on A.fat_uni_code = B.indu_uni_code\r\n" + "where A.indu_level = 1 ",
				tableName);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Indus>(Indus.class));
	}

	@Cacheable(value = "induCompClassCache", key = "#gicsInduUniCode")
	public Map<String, Object> getCompClass(String gicsInduUniCode) {
		try {
			String compClassSql = "SELECT CASE  WHEN LOCATE('银行', comp_cls_name)>0 THEN 1\r\n"
					+ "                     WHEN LOCATE('证券', comp_cls_name)>0 THEN 2\r\n"
					+ "                     WHEN LOCATE('保险', comp_cls_name)>0 THEN 3\r\n"
					+ "                     WHEN LOCATE('企业', comp_cls_name)>0 THEN 4  ELSE 4 END AS compClsName\r\n"
					+ "                     FROM  /*amaresun*/ dmdb.tbl_industry_classification C\r\n"
					+ "                     WHERE C.industry_code = %1$s";
			return jdbcTemplate.queryForMap(String.format(compClassSql, gicsInduUniCode));
			// return jdbcTemplate.queryForObject(compClassSql, Integer.class);
		} catch (Exception ex) {
			LOG.error("getCompClass exception with gicsInduUniCode[" + gicsInduUniCode + "]: " + ex.getMessage(), ex);
		}
		return null;
	}

	/**
	 * 获取申万的行业ids集合
	 * 
	 * @param induIds
	 *            GICS行业分类ids
	 * @return
	 */
	public Collection<Long> findInduSwIdsByGics(Collection<Long> induIds) {
		if (induIds == null || induIds.size() == 0)
			return new HashSet<>();
		List<BondInduSwMapDoc> list = mongoTemplate.find(new Query(Criteria.where("induId").in(induIds)),
				BondInduSwMapDoc.class);
		Set<Long> induIdSw = new HashSet<>();
		list.forEach(induSwMap -> induIdSw.add(induSwMap.getInduIdSw()));
		return induIdSw;

	}

	/**
	 * 获取申万的行业ids集合
	 * 
	 * @userId 用户id
	 * @param induIds
	 *            GICS行业分类ids
	 * @return
	 */
	public Collection<Long> findInduSwIdsByGics(Long userId, Collection<Long> induIds) {
		if (isGicsInduClass(userId))
			return induIds;
		else
			return findInduSwIdsByGics(induIds);
	}

	/**
	 * findInduIdSwByIssuerId
	 * 
	 * @param issuerId
	 *            发行人id
	 * @return
	 */
	public Long findInduIdSwByIssuerId(Long issuerId) {
		BondInduSwMapDoc bondInduSwMapDoc = mongoTemplate.findOne(new Query(Criteria.where("issuerId").is(issuerId)),
				BondInduSwMapDoc.class);
		if (bondInduSwMapDoc == null) {
			return null;
		} else {
			return bondInduSwMapDoc.getInduIdSw();
		}
	}

	/**
	 * 通过发行人获取申万行业id
	 * 
	 * @userId 用户id
	 * @param issuerId
	 *            发行人id
	 * @return
	 */
	public Long findInduIdSwByIssuerId(Long userId, Long issuerId) {
		if (isGicsInduClass(userId))
			return userId;
		else
			return findInduIdSwByIssuerId(issuerId);
	}

	/**
	 * 行业分析
	 * 
	 * @param induIds
	 * @param page
	 * @param size
	 * @param userId
	 * @return
	 */
	public Page<BondPdRankDoc> findInduRatingDown(List<Long> induIds, int page, int size, String sort, Integer type,Integer munInvest,
			Long userId) {
		
		List<BondPdRankDoc> list = new ArrayList<BondPdRankDoc>();
		
		String induField = null;
		if (!isInduInstitution(userId)) {
			induField = this.isGicsInduClass(userId) ? "induId" : "induIdSw";
		}else{
			Integer org_id = getInstitution(userId);
			induField = Constants.INSTIRUTION_INDU_CODE+org_id;
		}
		
		if(StringUtils.isEmpty(induField)){
			return new PageImpl<>(list, new PageRequest(page - 1, size), 0); 
		}
		
		Query query = new Query();
		Criteria criteria = new Criteria();
		
		List<Criteria> criteriaList = new ArrayList<Criteria>();
		criteriaList.add(Criteria.where(induField).in(induIds));
		criteriaList.add(Criteria.where("effectiveBondCount").gt(0));
		criteriaList.add(Criteria.where("attPoint").ne(null));
		switch (type) {
		case 2:// 同行业外部主体评级下降
			criteriaList.add(Criteria.where("$where").is("this.currR < this.lastR"));
			break;
//		case 3:// 同行业主体展望负面
//			criteriaList.add(Criteria.where("cceDisadvt").gt(0));
//			break;
//		case 4:// 同行业主体展望正面
//			criteriaList.add(Criteria.where("cceAdvt").gt(0));
//			break;
		case 5:// 同行业主体量化风险等级下降
			criteriaList.add(Criteria.where("$where").is("this.currPdNum > this.lastPdNum"));
			break;
		case 6:// 同行业主体量化风险等级上升
			criteriaList.add(Criteria.where("$where").is("this.currPdNum < this.lastPdNum"));
			break;
		case 7:// 同行业主体关注点
			List<Criteria> orCriteria = new ArrayList<>();
			orCriteria.add(Criteria.where("cceDisadvt").gt(0));
			orCriteria.add(Criteria.where("cceAdvt").gt(0));
			criteria.orOperator(orCriteria.toArray(new Criteria[orCriteria.size()]));
			break;
		default:
			break;
		}

		if(munInvest==1){
			criteriaList.add(Criteria.where("munInvest").ne(true));
		}

		criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
		query.addCriteria(criteria);
		
		long total = mongoTemplate.count(query, BondPdRankDoc.class);
		// 排序处理
		String[] sortPars = sort.split(":");
		String sortField = sortPars[0];
		String sortDir = sortPars[1].toLowerCase().startsWith("des") ? "desc" : "asc";

		if (StringUtils.isEmpty(sortField) || StringUtils.isEmpty(sortDir))
			return null;

		Integer pageNum = (page - 1) * size;
		if (pageNum > total)
			return new PageImpl<>(list, new PageRequest(page - 1, size), total);

		list = mongoTemplate.find(query, BondPdRankDoc.class);
		long pageSize = list.size() >= page * size ? page * size : list.size();

		if (pageSize >= total)
			pageSize = total;
		// 排序规则
		FindInduRatingDownSortComparator comparator = new FindInduRatingDownSortComparator(sortField, sortDir);
		Collections.sort(list, comparator);

		return new PageImpl<>(list.subList(pageNum, new Long(pageSize).intValue()), new PageRequest(page - 1, size),
				total);
	}
	

	/**
	 * 主体量化分析等级分析-->分布图
	 * 
	 * @param induIds
	 * @param userId
	 * @return
	 */
	public List<BondComInfoRatingInduDoc> findInduRatingView(List<Long> induIds, Long userId) {

		List<BondComInfoRatingInduDoc> BondComInfoRatingInduDocs = new ArrayList<BondComInfoRatingInduDoc>();

		// 得到当前用户所选行业类型 true是GICS false是申万
		String induField = null;
		if (!isInduInstitution(userId)) {
			induField = this.isGicsInduClass(userId) ? "induId" : "induIdSw";
		}else{
			Integer org_id = getInstitution(userId);
			induField = Constants.INSTIRUTION_INDU_CODE+org_id;
		}

		Query query = new Query();

		List<Criteria> criteriaList = new ArrayList<Criteria>();
		if (induIds != null && induIds.size() > 0) {
			criteriaList.add(Criteria.where(induField).in(induIds));
		} else {
			criteriaList.add(Criteria.where(induField).ne(null));
		}
		criteriaList.add(Criteria.where("currStatus").is(1));

		query.addCriteria(new Criteria()
				.andOperator(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]))));

		List<PdMappingVo> listPdMap = pdMappingService.getPdMapping();
		Map<BigDecimal, String> map = new HashMap<>();
		Map<String, Integer> mapLink = new LinkedHashMap<>();

		for (PdMappingVo pdMappingVo : listPdMap) {
			map.put(pdMappingVo.getPd(), pdMappingVo.getRating());
			mapLink.put(pdMappingVo.getRating(), 0);
		}

		List<IssPdDoc> list = mongoTemplate.find(query, IssPdDoc.class);
		for (IssPdDoc issPdDoc : list) {
			List<Double> pds = issPdDoc.getPds();
			if (pds != null && pds.size() > 0) {
				if (pds.get(0) != null) {
					String key = map.get(new BigDecimal(pds.get(0)).setScale(4, BigDecimal.ROUND_HALF_UP));
					int count = mapLink.get(key);
					count++;
					mapLink.put(key, count);
				}
			}
		}

		BondComInfoRatingInduDoc bondComInfoRatingInduDoc = null;
		for (String key : mapLink.keySet()) {
			bondComInfoRatingInduDoc = new BondComInfoRatingInduDoc(key, mapLink.get(key));
			BondComInfoRatingInduDocs.add(bondComInfoRatingInduDoc);
		}

		return BondComInfoRatingInduDocs;
	}

	/**
	 * 
	 * @param induIds
	 * @param userId
	 * @param year
	 * @param quarter
	 * @param isPer
	 *            是否私人财报, true则不使用userId, 默认GICS分类
	 * @return
	 */
	public List<BondComInfoRatingInduDoc> findInduRatingViewByDate(List<Long> induIds, Long userId, Long year,
			Long quarter, boolean isPer) {
		List<BondComInfoRatingInduDoc> result = null;
		try {
			// 得到当前用户行业类型
			
			String induField = null;
			String induCode = null;
			Integer org_id = null;
			
			if (!isInduInstitution(userId)) {
				induField = isPer || "induId".equals(this.getInduIdByUser(userId)) ? "dmdb.t_pub_indu_code"
						: "dmdb.t_pub_indu_code_sw";
				induCode = isPer || "induId".equals(this.getInduIdByUser(userId)) ? "t_bond_com_ext.indu_uni_code"
						: "t_bond_com_ext.indu_uni_code_sw";
			}else{
				org_id = getInstitution(userId);
				induField = "institution.t_bond_inst_indu";
				induCode = "comindu.indu_uni_code";
			}

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT  \r\n");
			sql.append("t.rating,COUNT(1) AS COUNT,t.Comp_ID \r\n");
			sql.append("FROM ( \r\n");
			sql.append("SELECT  \r\n");
			sql.append("dm_bond.rating,COUNT(1) AS COUNT,dm_bond.Comp_ID \r\n");
			sql.append("FROM  \r\n");
			sql.append("( \r\n");
			sql.append("SELECT dm_bond.rating,dm_bond.Comp_ID FROM  \r\n");
			sql.append(" /*amaresun*/ dmdb.dm_bond AS dm_bond  \r\n");
			sql.append("INNER JOIN dmdb.t_bond_com_ext AS t_bond_com_ext  \r\n");
			sql.append(" ON dm_bond.Comp_ID = t_bond_com_ext.ama_com_id  \r\n");
			sql.append("INNER JOIN bond_ccxe.d_pub_com_info_2 AS d_pub_com_info_2  \r\n");
			sql.append(
					" ON t_bond_com_ext.com_uni_code = d_pub_com_info_2.COM_UNI_CODE AND d_pub_com_info_2.ISVALID = 1  \r\n");
			if(org_id==null){
				sql.append("LEFT JOIN " + induField + " AS INDU ON INDU.indu_uni_code = " + induCode
						+ "  \r\n");
			}else{
				sql.append("INNER JOIN institution.t_bond_inst_com_indu comindu ON d_pub_com_info_2.com_uni_code = comindu.com_uni_code AND d_pub_com_info_2.ISVALID = 1   \r\n");
				sql.append("LEFT JOIN " + induField + " AS INDU ON INDU.indu_uni_code = " + induCode
						+ "  \r\n");
				sql.append("AND INDU.inst_id = "+org_id+" \r\n");
			}
			sql.append("WHERE  1=1  \r\n");
			if (year != null && quarter != null)
				sql.append("AND (dm_bond.year<" + year + " || (dm_bond.year=" + year + " && dm_bond.quan_month<="
						+ (quarter * 3) + ")) AND dm_bond.year>=" + (year - 1) + "  \r\n");
			if (induIds != null && induIds.size() > 0) {
				String unicodes = "";
				for (int i = 0; i < induIds.size(); i++) {
					unicodes += induIds.get(i);
					if (i < induIds.size() - 1)
						unicodes += ",";
				}
				sql.append("\r\n AND INDU.indu_uni_code IN (" + unicodes + ") \r\n");
			}
			sql.append("AND  dm_bond.rating IS NOT NULL ORDER BY dm_bond.year,dm_bond.quan_month \r\n");
			sql.append(" ) dm_bond  \r\n");
			sql.append(" GROUP BY dm_bond.Comp_ID ) t GROUP BY t.rating \r\n");

			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());

			Map<String, Long> maps = new HashMap<String, Long>();

			for (Map<String, Object> map : list) {
				String rating = (String) map.getOrDefault("rating", "");
				Long count = (Long) map.getOrDefault("count", 0);
				maps.put(rating, count);
			}

			List<String> ratingList = new ArrayList<String>();

			List<PdMappingVo> listPdMap = pdMappingService.getPdMapping();
			for (PdMappingVo pdMappingVo : listPdMap) {
				ratingList.add(pdMappingVo.getRating());
			}

			BondComInfoRatingInduDoc doc = null;
			if (!ratingList.isEmpty())
				result = new ArrayList<BondComInfoRatingInduDoc>();

			for (String ratings : ratingList) {
				if (maps.get(ratings) != null) {
					doc = new BondComInfoRatingInduDoc(ratings, maps.get(ratings).intValue());
				} else {
					doc = new BondComInfoRatingInduDoc(ratings, 0);
				}
				result.add(doc);
			}
		} catch (Exception ex) {
			LOG.error(String.format("findInduRatingViewByDate: ex[%s]", ex.getMessage()));
		}

		return SafeUtils.throwNullBusinessEx(result, "无法生成评级分布图数据");

	}

	/**
	 * 
	 * @Title: findIssPdHis @Description: 获取专项指标计算公式 @author
	 * ChungaoChen @param @param field @param @return @return
	 * BondIndicatorExpressionVo @throws
	 */
	public BondIndicatorExpressionVo getSpecialsExpression(String field) {
		BondIndicatorExpressionVo vo = new BondIndicatorExpressionVo();
		List<BondIndicatorExpression> list = null;
		list = bondIndicatorExpressionDao.listFindByField(field);
		if (null != list && !list.isEmpty()) {
			BondIndicatorExpression bean = list.get(0);
			BeanUtils.copyProperties(bean, vo);
		}
		return vo;
	}

	public Boolean isInduInstitution(Long userid) {

		try {
			Integer org_id = bondUserInstitution.getUserInstitutionByUserId(userid.toString());
//			String format = "SELECT COUNT(1) FROM institution.t_bond_inst_indu WHERE inst_id = %1$d";
//			return jdbcTemplate.queryForObject(String.format(format, org_id), Long.class) > 0;
			Boolean flay = bondUserOperationService.instInduAuthorization(userid, 2);
			if(flay){
				flay = bondUserOperationService.instInduAuthorization(Long.parseLong(org_id.toString()), 1);
			}
			return flay;
		} catch (Exception ex) {
			LOG.error("isInduInstitution exception with userid[" + userid + "]: " + ex.getMessage());
		}

		return false;

	}
	
	/**
	 * 根据用户id和和主体id，获取用户设置行业类型下的所有主体id
	 * @param comUinCode
	 * @param userid
	 * @return
	 */
	public List<Long> findComUniCodeByIndu(Long comUinCode, Long userid){
		BondComInfoDoc bondComInfo = bondComInfoRepository.findOne(comUinCode);
		List<BondComInfoDoc> bondComInfoDocs = null;
		if(userid == null){
			bondComInfoDocs = bondComInfoRepository.findByInduIdSw(bondComInfo.getInduIdSw());
		}else{
			Boolean isInduInstitution = isInduInstitution(userid);
			if(isInduInstitution && bondComInfo != null && bondComInfo.getInstitutionInduMap() != null){
				Map<String,Object> induMap = bondComInfo.getInstitutionInduMap();
				String induCode = getInduNameByUserId(userid);
				String code = null;
				try {
					code = induMap.get(induCode.split("\\.")[1].replaceAll("name", "code")).toString();
				} catch (NullPointerException e) {
					
					LOG.error("主体[" + bondComInfo.getComChiName() + "]没有用户[" + userid + "]的行业信息" + induCode );;
				}
				Query query = new Query(Criteria.where(induCode.replace("name", "code")).is(code == null ? code : Long.valueOf(code)));
				query.fields().include("comUniCode");
				bondComInfoDocs = mongoTemplate.find(query, BondComInfoDoc.class);
			}else{
				//如果不是其他机构的话，判断是否为Gics
				if(isGicsInduClass(userid)){
					bondComInfoDocs = bondComInfoRepository.findByInduId(bondComInfo.getInduId());
				}else{
					bondComInfoDocs = bondComInfoRepository.findByInduIdSw(bondComInfo.getInduIdSw());
				}
			}
		}
		//首先判断是否为非dm机构
		List<Long> result =  new ArrayList<>();
		if(bondComInfoDocs != null){
			bondComInfoDocs.forEach(comInfo ->{
				result.add(comInfo.getComUniCode());
			});
		}
		
		return result;
		
	}
	
	/**
	 * 根据用户id和和主体id，获取用户设置行业类型下的所有主体对应的安硕id列表
	 * @param comUinCode
	 * @param userId
	 * @return
	 */
	public List<Long> getAmaComIdListByIndu(Long comUinCode, Long userId) {
		List<Long> result = new ArrayList<>();
		List<Long> comUniCodeList = this.findComUniCodeByIndu(comUinCode, userId);
		if (comUniCodeList != null && !comUniCodeList.isEmpty()) {
			String induComUniCodeStr = comUniCodeList.isEmpty() ? "''"
					: org.apache.commons.lang.StringUtils.join(comUniCodeList.toArray(), ",");
			String sql = "SELECT ama_com_id FROM dmdb.t_bond_com_ext WHERE com_uni_code IN (%1$s)";
			String fmtSql = String.format(sql, induComUniCodeStr);
			result = jdbcTemplate.queryForList(fmtSql, Long.class);
		}
		return result;
	}

	/**
	 * 根据用户id和和主体id，获取用户设置行业的上一级类型下的所有主体id
	 * @param comUinCode
	 * @param userId
	 * @return
	 */
	public List<Long> getHighLevelComUniCodeListByIndu(Long comUinCode, Long userId){
		BondComInfoDoc bondComInfo = bondComInfoRepository.findOne(comUinCode);
		List<BondComInfoDoc> bondComInfoDocList;
		List<Long> result =  new ArrayList<>();
		// 首先判断是否为非dm机构
		Boolean isThirdParty = this.isInduInstitution(userId);
		if (isThirdParty && bondComInfo != null && bondComInfo.getInstitutionInduMap() != null) {
			String code = this.getThirdPartyCodeValue(userId, comUinCode);
			if (StringUtils.isNotBlank(code) && code != "0") {
				List<Long> parallelInduCodeList = this.getThirdPartyParallelInduUniCodeList(code);
				String induCode = this.getInduNameByUserId(userId); // induCode=institutionInduMap.name123
				Query query = new Query(Criteria.where(induCode.replace("name", "code")).in(parallelInduCodeList));
				query.fields().include("comUniCode");
				bondComInfoDocList = mongoTemplate.find(query, BondComInfoDoc.class);
				if (!bondComInfoDocList.isEmpty()) {
					result = bondComInfoDocList.stream().map(BondComInfoDoc::getComUniCode).collect(Collectors.toList());
				}
			}
		} else if (!isThirdParty){
			result = this.getDMHighLevelComUniCodeList(userId, comUinCode);
		}
		return result;
	}

	public String getThirdPartyCodeValue(Long userId, Long comUinCode) {
		BondComInfoDoc bondComInfo = bondComInfoRepository.findOne(comUinCode);
		Map<String, Object> induMap = bondComInfo.getInstitutionInduMap();
		String induCode = this.getInduNameByUserId(userId); // induCode=institutionInduMap.name123
		String code = null;
		try {
			// code123 -> code=200002
			code = induMap.get(induCode.split("\\.")[1].replaceAll("name", "code")).toString();
		} catch (NullPointerException e) {
			LOG.error("主体[" + bondComInfo.getComChiName() + "]没有用户[" + userId + "]的行业信息" + induCode );
		}
		return code;
	}

	/**
	 * 根据给定的code获取第三方平行机构的uniCode列表
	 * @param code
	 * @return
	 */
	private List<Long> getThirdPartyParallelInduUniCodeList(String code) {
		String sql = "SELECT indu_uni_code FROM institution.t_bond_inst_indu WHERE fat_uni_code=" +
				" (SELECT fat_uni_code FROM institution.t_bond_inst_indu WHERE indu_uni_code=%1$s)";
		String formatSql = String.format(sql, code);
		List<Long> result = jdbcTemplate.queryForList(formatSql, Long.class);
		return result;
	}

	/**
	 * 获取GICS或者SW的上一级的发行人uniCode列表
	 * @param userId
	 * @param comUinCode
	 * @return
	 */
	private List<Long> getDMHighLevelComUniCodeList(Long userId, Long comUinCode) {
		boolean isGics = this.isGicsInduClass(userId);
		BondComExt comExt = comExtRepository.findByComUniCode(comUinCode);
		String finalInduName = isGics ? "indu_uni_code" : "indu_uni_code_sw";
		Long finalInduUniCodeValue = isGics ? comExt.getInduUniCode() : comExt.getInduUniCodeSw();
		String sql = "SELECT com_uni_code FROM dmdb.t_bond_com_ext WHERE %1$s=%2$d";
		String formatSql = String.format(sql, finalInduName, finalInduUniCodeValue);
		List<Long> result = jdbcTemplate.queryForList(formatSql, Long.class);
		return result;
	}
	
	/**
	 * 根据用户id和和行业id，获取该行业下的所有主体id
	 * @param induCode
	 * @param userid
	 * @return
	 */
	public List<Long> findComUniCodeByInduCode(Long induCode, Long userid){
		List<Long> result = getComUniCode(induCode, userid);
		return result;
		
	}

	/**
	 * 获取主体id
	 * @param induCode
	 * @param userid
	 * @return
	 */
	private List<Long> getComUniCode(Long induCode, Long userid) {
		List<BondComInfoDoc> bondComInfoDocs = null;
		Boolean isInduInstitution = isInduInstitution(userid);
		if(isInduInstitution){
			Query query = new Query(Criteria.where(getInduNameByUserId(userid).replace("name", "code")).is(induCode));
			query.fields().include("comUniCode");
			bondComInfoDocs = mongoTemplate.find(query, BondComInfoDoc.class);
		}else{
		//如果不是其他机构的话，判断是否为Gics
			if(isGicsInduClass(userid)){
				bondComInfoDocs = bondComInfoRepository.findByInduId(induCode);
			}else{
				bondComInfoDocs = bondComInfoRepository.findByInduIdSw(induCode);
			}
		}
		List<Long> result =  new ArrayList<>();
		if(bondComInfoDocs != null){
			bondComInfoDocs.forEach(comInfo ->{
				result.add(comInfo.getComUniCode());
			});
		}
		return result;
	}
	
	public Integer getInstitution(Long userId) {
		return bondUserInstitution.getUserInstitutionByUserId(userId.toString());
	}
	
	public BondPdRankDoc getPdRankDetail(String key) {

		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from dmdb.t_bond_basic_info where com_uni_code=? or bond_uni_code = ? or bond_code = ? or bond_short_name = ? or bond_full_name = ? or iss_name = ? limit 1 ");
	
		List<Object> argParams = new ArrayList<>();
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql.toString(), argParams.toArray(new Object[argParams.size()]));
		if (queryForList.size() > 0){
			Long comUniCode = Long.parseLong(queryForList.get(0).get("com_uni_code")+"");
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(comUniCode));
			BondPdRankDoc doc = mongoTemplate.findOne(query, BondPdRankDoc.class);
			if(doc==null)
				return null;
			doc.setPd(UIAdapter.numPdMaping.get(Integer.parseInt(doc.getPdNum()+"")));
			return doc;
		}
		
		return null;
	}
	
	/**
	 * key匹配发行人
	 * @param key
	 * @return
	 */
	public Map<String,Object> getComChiNameBykey(String key) {
		Map<String,Object> resultMap =  queryBondIssuserRelaDetail(key);
		
		if(resultMap==null || resultMap.keySet().size()<1){
			resultMap = queryBondBasicInfo(key);
		}
		
		if(resultMap!=null && resultMap.get("comUinCode")!=null){
			Long comUniCode = Long.parseLong(resultMap.get("comUinCode")+"");
			Query query = new Query();
			query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			BondComInfoDoc doc = mongoTemplate.findOne(query, BondComInfoDoc.class);
			if(doc==null)
				return resultMap;
			if(doc.getPdNum()!=null){
				resultMap.put("pd",UIAdapter.numPdMaping.get(Integer.parseInt(doc.getPdNum()+"")));
			}
		}
		return resultMap;
	}
	
	/**
	 * 债劵匹配发行人
	 * @param key
	 * @return
	 */
	private Map<String,Object> queryBondBasicInfo(String key){
		
		if(!StringUtils.isEmpty(key)){
			key = StringUtils.filter(key); //过滤特殊字符
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dmdb.t_bond_basic_info where "
				+ "( (is_new = 1 AND push_status = 1 ) OR is_new=0 )"
				+ "and CONCAT(com_uni_code)=? or CONCAT(bond_uni_code) = ? or CONCAT(bond_code) = ? or REPLACE(REPLACE(bond_short_name,\"(\",\"\"),\")\",\"\") = ? or  REPLACE(REPLACE(iss_name,\"(\",\"\"),\")\",\"\") = ? limit 1");
		List<Object> argParams = new ArrayList<>();
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		try{
			Map<String, Object> basicMap = jdbcTemplate.queryForMap(sql.toString(), argParams.toArray(new Object[argParams.size()]));
			if (basicMap != null){
				resultMap.put("comUinCode", Long.parseLong(basicMap.get("com_uni_code"+"")+""));
				resultMap.put("comChiName", basicMap.get("iss_name"));
			}
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return resultMap;
	}
	
	/**
	 * 发行人简称匹配查询
	 * @param key
	 * @return
	 */
	private Map<String,Object> queryBondIssuserRelaDetail(String key){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT com_uni_code,com_chi_name FROM dmdb.bond_issuser_rela_detail where  wind_short = ? or ccxe_short = ? or sentiment_short = ? or short_name1 = ? or short_name2 = ? or FIND_IN_SET(?,extension_column)  limit 1 ");
		List<Object> argParams = new ArrayList<>();
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		argParams.add(key);
		try{
			Map<String, Object> issuserMap = jdbcTemplate.queryForMap(sql.toString(), argParams.toArray(new Object[argParams.size()]));
			if(issuserMap!=null){
				resultMap.put("comUinCode", Long.parseLong(issuserMap.get("com_uni_code"+"")+""));
				resultMap.put("comChiName", issuserMap.get("com_chi_name"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return resultMap;
	}
	
	/**
	 * 查询发行人详情信息
	 * @param comUniCode
	 * @return
	 */
	public BondComInfoDetailVo getComDetail(Long comUniCode) {
		
		BondComInfoDetailVo detailDoc = new BondComInfoDetailVo();
		
		//查询基础数据
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		BondComInfoDoc doc = mongoTemplate.findOne(query, BondComInfoDoc.class);
		if(doc!=null){
			detailDoc.setComChiName(doc.getComChiName());//发行人简称
			detailDoc.setMunInvest(doc.getMunInvest());//城投
			detailDoc.setProvince(doc.getAreaName1());//省份
			detailDoc.setOwnerType(UIAdapter.cvtComAttr2UIStr(doc.getComAttrPar()));//性质
			detailDoc.setInduName(doc.getInduNameSw());//行业
		}
		//外部 //DM
		getRating(detailDoc,comUniCode,doc);
		//中金
		getCicc(detailDoc, comUniCode);
		//YY
		getRatingDog(detailDoc, comUniCode);
		//兴业
		getIndustrial(detailDoc, comUniCode);
		//国君
		getGuojun(detailDoc, comUniCode);
		
		return detailDoc;
	}
	
	//外部评级,DM评级
	private void getRating(BondComInfoDetailVo detailDoc,Long comUniCode,BondComInfoDoc doc){
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM      (  ");
		sql.append("\r\nSELECT * FROM (  ");
		sql.append("\r\nSELECT d_pub_com_info_2.COM_UNI_CODE AS issuerId, d_pub_com_info_2.COM_CHI_NAME AS issuer,dm_bond.Comp_ID AS compId, t_pub_indu_code.indu_uni_code AS induId,       ");
		sql.append("\r\nt_pub_indu_code.indu_class_name AS induName, t_pub_indu_code_sw.indu_uni_code AS induIdSw, t_pub_indu_code_sw.indu_class_name AS induNameSw, dm_bond.Rating AS pd, t_bond_pd_par.id AS pdNum, CONCAT(dm_bond.year, '/Q',QUARTER(CONCAT(dm_bond.year, '/', dm_bond.quan_month, '/1'))) AS reportTime ,   ");   
		sql.append("\r\nd_pub_com_info_2.COM_ATTR_PAR AS comAttrPar, pub_area_code.AREA_NAME2 AS province, pub_area_code.AREA_NAME3 AS city      ");
		sql.append("\r\n FROM bond_ccxe.d_pub_com_info_2 AS d_pub_com_info_2   ");
		sql.append("\r\n LEFT JOIN dmdb.t_bond_com_ext AS t_bond_com_ext ON t_bond_com_ext.com_uni_code = d_pub_com_info_2.COM_UNI_CODE   ");
		sql.append("\r\n  LEFT JOIN  /*amaresun*/ dmdb.dm_bond AS dm_bond ON dm_bond.Comp_ID = t_bond_com_ext.ama_com_id    AND dm_bond.Rating  IS NOT NULL        ");
		sql.append("\r\n  LEFT JOIN dmdb.t_pub_indu_code AS t_pub_indu_code ON t_bond_com_ext.indu_uni_code = t_pub_indu_code.indu_uni_code      ");
		sql.append("\r\n  LEFT JOIN dmdb.t_pub_indu_code_sw AS t_pub_indu_code_sw ON t_bond_com_ext.indu_uni_code_sw = t_pub_indu_code_sw.indu_uni_code      ");
		sql.append("\r\n   LEFT JOIN dmdb.t_bond_pd_par AS t_bond_pd_par ON t_bond_pd_par.rating = dm_bond.Rating      ");
		sql.append("\r\n   LEFT JOIN bond_ccxe.pub_area_code AS pub_area_code ON d_pub_com_info_2.AREA_UNI_CODE = pub_area_code.AREA_UNI_CODE  ");    
		sql.append("\r\nWHERE  d_pub_com_info_2.ISVALID=1 AND d_pub_com_info_2.COM_UNI_CODE IS NOT NULL  AND d_pub_com_info_2.COM_UNI_CODE =   " + comUniCode);
		sql.append("\r\n ORDER BY ISSUER ASC , reportTime DESC    ");
		sql.append("\r\n) AS R     ");
		sql.append("\r\n GROUP BY R.issuerId      ");
		sql.append("\r\n ORDER BY R.pdNum DESC       ");
		sql.append("\r\n) AS PD                         ");         
		sql.append("\r\nLEFT JOIN  (    ");
		sql.append("\r\nSELECT COM_UNI_CODE, ISS_CRED_LEVEL AS rating,ORG_UNI_CODE,RATE_WRIT_DATE AS rateWritDate, ATT_POINT attPoint,CCE_ADVT AS cceAdvt,   ");
		sql.append("\r\nCCE_DISADVT  AS cceDisadvt,RATE_POINT AS ratePoint,(CASE rate_pros_par WHEN  1 THEN '正面'  WHEN  2 THEN '稳定'  WHEN 3 THEN '观望'  WHEN 4 THEN '负面'  ELSE '' END ) AS rateProsPar FROM      ");
		sql.append("\r\n	(SELECT * FROM bond_ccxe.d_bond_iss_cred_chan   WHERE ISVALID = 1   and com_type_par=1     ");
		sql.append("\r\n	 ORDER BY bond_ccxe.d_bond_iss_cred_chan.RATE_WRIT_DATE DESC     ");
		sql.append("\r\n) AS ISS    ");
		sql.append("\r\n GROUP BY ISS.COM_UNI_CODE    ");
	    sql.append("\r\n ) AS ISS_CRED   			  ");				   
		sql.append("\r\n ON PD.issuerId = ISS_CRED.COM_UNI_CODE   ");
		sql.append("\r\nLEFT JOIN  (SELECT CHI_SHORT_NAME AS chiShortName,ORG_UNI_CODE FROM bond_ccxe.d_pub_org_info_r) AS orginfo ON  orginfo.ORG_UNI_CODE = ISS_CRED.ORG_UNI_CODE  ");

		Map<String, Object> map = null;
		
		try{
			map = jdbcTemplate.queryForMap(sql.toString());
		}catch(EmptyResultDataAccessException e){
			map = new HashMap<String, Object>();
		}
		
		BondComInfoRatingVo ratingDoc = new BondComInfoRatingVo();
		BondComInfoPdVo pdDoc = new BondComInfoPdVo();
		
		if(doc!=null){
			pdDoc.setPdDiff(doc.getPdDiff()==null?100:doc.getPdDiff());//pd评级变动
		}else{
			detailDoc.setComChiName(convObj2Str(map.get("issuer")));//发行人简称
		}
		
		ratingDoc.setChiShortName(convObj2Str(map.get("chiShortName")));//评级机构
		ratingDoc.setRateWritDate(!StringUtils.isEmpty("rateWritDate")?(Date)map.get("rateWritDate"):null);//评级时间
		ratingDoc.setRateProsPar(convObj2Str(map.get("rateProsPar")));//展望
		ratingDoc.setRating(convObj2Str(map.get("rating")));//评级
		ratingDoc.setRatePoint(convObj2Str(map.get("ratePoint")));//评级观点
		ratingDoc.setCceAdvt(convObj2Str(map.get("cceAdvt")));//正面信息
		ratingDoc.setCceDisadvt(convObj2Str(map.get("cceDisadvt")));//负面信息
		ratingDoc.setAttPoint(convObj2Str(map.get("attPoint")));//关注点
		Long ratingDiff = Long.parseLong(getRatingDiff(comUniCode)+"");
		if(ratingDiff!=null && ratingDiff!=100){
			ratingDoc.setRatingDiff(Long.parseLong(getRatingDiff(comUniCode)+""));//rating评级变动
		}
		
		pdDoc.setPd(convObj2Str(map.get("pd")));//pd
		pdDoc.setReportTime(!StringUtils.isEmpty(map.get("reportTime"))?SafeUtils.convertFromYearQnToYearDesc(map.get("reportTime")+""):null);//评级时间
		//指标分位
		List<String> fields = Arrays.asList("Currency_funds2Current_assets","Liab2Asst","Quck_Ratio","EBITDA_Intrst_Cov","Grss_Mrgn","NetAsst_Rtrn",
				"Tot_Asst_Turnvr","Invntry_Day","Oprtg_CF_Debt_Cov");
		ComQuantileInfoVo comQuantileInfoVo = bondFinanceInfoService.findComQuantile(comUniCode,fields,null);
		pdDoc.setQuantiles(comQuantileInfoVo!=null?comQuantileInfoVo.getQuantiles():null);
		if(!ratingDoc.isNull(ratingDoc)){
			detailDoc.setBondComInfoRatingDoc(ratingDoc);
		}
		if(!pdDoc.isNull(pdDoc)){
			pdDoc.setChiShortName("Dealing Matrix");//评分机构
			detailDoc.setBondComInfoPdDoc(pdDoc);
		}
	}
	//中金
	private void getCicc(BondComInfoDetailVo detailDoc,Long comUniCode){
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		query.with(new Sort(Sort.Direction.DESC, "rateTime"));
		CreditAnalysisCiccDoc doc = mongoTemplate.findOne(query,
				CreditAnalysisCiccDoc.class);
		detailDoc.setCreditAnalysisCiccDoc(doc);
	}
	//YY
	private void getRatingDog(BondComInfoDetailVo detailDoc,Long comUniCode){
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		query.with(new Sort(Sort.Direction.DESC, "rateTime"));
		CreditAnalysisRatingDogDoc doc = mongoTemplate.findOne(query,
				CreditAnalysisRatingDogDoc.class);
		detailDoc.setCreditAnalysisRatingDogDoc(doc);
	}
	//兴业
	private void getIndustrial(BondComInfoDetailVo detailDoc,Long comUniCode){
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		query.with(new Sort(Sort.Direction.DESC, "rateTime"));
		CreditAnalysisIndustrialDoc doc = mongoTemplate.findOne(query,
				CreditAnalysisIndustrialDoc.class);
		detailDoc.setCreditAnalysisIndustrialDoc(doc);
	}
	//国君
	private void getGuojun(BondComInfoDetailVo detailDoc,Long comUniCode){
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
		query.with(new Sort(Sort.Direction.DESC, "rateTime"));
		CreditAnalysisGuoJunDoc doc = mongoTemplate.findOne(query,
				CreditAnalysisGuoJunDoc.class);
		detailDoc.setCreditAnalysisGuoJunDoc(doc);
	}
	
	//rating评级变动
	private Integer getRatingDiff(Long comUniCode){
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT iss_cred_level as rating,rate_writ_date FROM bond_ccxe.d_bond_iss_cred_chan WHERE  ISVALID=1 and com_type_par=1 and COM_UNI_CODE="+comUniCode);
		sql.append("\r\nORDER BY rate_writ_date DESC LIMIT 2");
		
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString());
		
		if(list!=null && list.size()<2){
			return 100;
		}else{
			Integer oldRatingNum = UIAdapter.getRatingMaps().get(list.get(1).get("rating"));
			Integer newRatingNum = UIAdapter.getRatingMaps().get(list.get(0).get("rating"));
			return newRatingNum - oldRatingNum;
		}
		
	}
	
	private String convObj2Str(Object o){
		
		if(StringUtils.isEmpty(o))
			return null;
		
		return o.toString();
	} 
}
