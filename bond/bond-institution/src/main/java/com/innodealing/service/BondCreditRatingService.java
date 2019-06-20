package com.innodealing.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.innodealing.consts.InstConstants;
import com.innodealing.dao.BondCreditRatingDao;
import com.innodealing.dao.BondCreditRatingGroupDao;
import com.innodealing.dao.BondFavoriteDao;
import com.innodealing.dao.BondInstCodeDao;
import com.innodealing.dao.BondInstRatingHistDao;
import com.innodealing.dao.UserOrgInfoDao;
import com.innodealing.model.ExcelColumn;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondComparisonDoc;
import com.innodealing.model.mysql.BondCreditRating;
import com.innodealing.model.mysql.BondCreditRatingGroup;
import com.innodealing.model.mysql.BondInstCode;
import com.innodealing.model.mysql.BondInstRatingHist;
import com.innodealing.model.req.CreditRatingGroupReq;
import com.innodealing.model.req.CreditRatingGroupchangeReq;
import com.innodealing.model.req.CreditRatingImportReq;
import com.innodealing.model.vo.CreditRatingBasic;
import com.innodealing.model.vo.CreditRatingBondVo;
import com.innodealing.model.vo.CreditRatingGroupVo;
import com.innodealing.model.vo.CreditRatingIssuerVo;
import com.innodealing.model.vo.CreditRatingParsed;
import com.innodealing.model.vo.CreditRatingParsedBasic;
import com.innodealing.model.vo.CreditRatingParsedInfoVo;
import com.innodealing.model.vo.IssuerGroupInfoVo;
import com.innodealing.util.BusinessException;
import com.innodealing.util.CreditRatingIssuerExcelUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

@Service
public class BondCreditRatingService extends BaseService {

	private final static Logger LOGGER = LoggerFactory.getLogger(BondCreditRatingService.class);

	private @Autowired BondCreditRatingGroupDao creditRatingGroupDao;

	private @Autowired BondCreditRatingDao creditRatingDao;

	private @Autowired BondInstCodeDao instCodeDao;

	private @Autowired UserOrgInfoDao userOrgInfoDao;
	
	private @Autowired BondInstRatingHistDao instRatingHistDao;

	private @Autowired BondFavoriteDao bondFavoriteDao;
	
	private @Autowired MongoOperations mongoOperations;

	private @Autowired ExcelParseToolService excelParseTool;
	
	private @Autowired ApplicationEventPublisher publisher;
	
	private @Autowired FileHandleService fileHandleService;
	
	private @Autowired BondComparisonService comparisonService;
	
	private @Autowired BondInduService induService;

	@Transactional
	public Integer addGroup(Integer userId, CreditRatingGroupReq req) {
		if (null == req) {
			return 0;
		}

		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		if (orgId.intValue() == 0) {
			throw new BusinessException("系统中没有该账号所在的机构");
		}

		BondCreditRatingGroup group = creditRatingGroupDao.queryByName(req.getGroupName(), orgId);
		if (null != group) {
			throw new BusinessException("系统中有相同的组名称");
		}

		BondCreditRatingGroup entity = new BondCreditRatingGroup();
		entity.setCreatedBy(userId);
		entity.setOrgId(orgId);
		entity.setGroupName(req.getGroupName());
		entity.setGroupType(req.getGroupType());
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		return creditRatingGroupDao.save(entity);
	}

	@Transactional
	public Integer deleteGroup(Long groupId) {
		LOGGER.info("deleteGroup groupId:" + groupId);
		BondCreditRatingGroup currGroup = creditRatingGroupDao.queryById(groupId);
		if (null != currGroup) {
			Integer orgId = currGroup.getOrgId();
			BondCreditRatingGroup defaultOne = creditRatingGroupDao.queryDefaultOneByOrgId(orgId);
			if (null != defaultOne) {
				creditRatingDao.updateGroupId(groupId, defaultOne.getId());
			}
		}
		return creditRatingGroupDao.delete(groupId);
	}

	@Transactional
	public Integer updateGroup(Long groupId, CreditRatingGroupReq req) {
		if (null == req) {
			return 0;
		}

		BondCreditRatingGroup entity = new BondCreditRatingGroup();
		entity.setGroupName(req.getGroupName());
		entity.setGroupType(req.getGroupType());
		entity.setId(groupId);
		entity.setUpdateTime(new Date());
		return creditRatingGroupDao.update(entity);
	}

	public List<CreditRatingGroupVo> findGroups(Integer userId) {
		List<CreditRatingGroupVo> groupVos = new ArrayList<>();
		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		List<BondCreditRatingGroup> groups = creditRatingGroupDao.queryByOrgId(orgId);

		groups.stream().forEachOrdered(group -> {
			CreditRatingGroupVo vo = new CreditRatingGroupVo();
			Map<String, Object> param = new HashMap<>();
			param.put("orgId", orgId);
			param.put("groupId", group.getId());
			param.put("groupType", group.getGroupType());

			vo.setGroupId(group.getId());
			vo.setGroupName(group.getGroupName());
			vo.setGroupType(group.getGroupType());
			vo.setIssuerCount(creditRatingDao.queryIssuercountByGroupId(param));
			vo.setBondCount(creditRatingDao.queryBondcountByGroupId(param));

			groupVos.add(vo);
		});

		return groupVos;
	}

	public Page<CreditRatingIssuerVo> findIssuers(Integer userId, Long groupId, Integer groupedFlag, Integer ratingId,
			Integer induId, String query, Integer pageIndex, Integer pageSize) {
		LOGGER.info("findIssuers groupId:" + groupId + ",userId:" + userId + ",groupedFlag:" + groupedFlag
				+ ",ratingId:" + ratingId + ",induId:" + induId);
		List<CreditRatingIssuerVo> results = new ArrayList<>();
		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		Integer version = instCodeDao.queryVersionByOrgId(orgId);
		List<BondCreditRatingGroup> allGroups = creditRatingGroupDao.queryByOrgId(orgId);
		allGroups.stream().forEachOrdered(group -> {
			if(group.getGroupType().intValue() == 0){
				group.setGroupName(InstConstants.NOTJOIN_GROUP);
			}
		});
		
		BondCreditRatingGroup currGroup = creditRatingGroupDao.queryById(groupId);
		if (null != currGroup && currGroup.getGroupType().intValue() == 0) {
			currGroup.setGroupName(InstConstants.NOTJOIN_GROUP);
		}
		
		Map<String, Object> param = setCommonIssuerParam(userId, groupId, groupedFlag, ratingId, induId, query, orgId,
				currGroup);
		
		param.put("pageIndex", pageIndex*pageSize);
		param.put("pageSize", pageSize);

		results = creditRatingDao.queryIssuers(param);
		int count = creditRatingDao.queryIssuersCount(param);
		
		List<Long> issuerIdList = results.stream().map(CreditRatingIssuerVo::getIssuerId).collect(Collectors.toList());
		Map<Long, BondInstRatingHist> issuerHistMap = getIssuerHistMap(issuerIdList, orgId, version);
		
		Query comquery = new Query();
		comquery.addCriteria(Criteria.where("comUniCode").in(issuerIdList));
		comquery.fields().include("comChiName").include("worstPd").include("worstPdNum").include("worstPdTime").include("issCredLevel").include("pdDiff").include("pdTime").include("pd")
				.include("rateProsPar").include("comUniCode");
		List<BondComInfoDoc> comInfoList = mongoOperations.find(comquery, BondComInfoDoc.class);
		Map<Long, BondComInfoDoc> comInfoMap = comInfoList.stream().collect(Collectors.toMap(BondComInfoDoc::getComUniCode, (p) -> p));
		
		handleIssuerResults(results, orgId, allGroups, currGroup, issuerHistMap, comInfoMap);
		
		return new PageImpl<CreditRatingIssuerVo>(results, new PageRequest(pageIndex, pageSize), count);
	}

	private Map<String, Object> setCommonIssuerParam(Integer userId, Long groupId, Integer groupedFlag,
			Integer ratingId, Integer induId, String query, Integer orgId, BondCreditRatingGroup currGroup) {
		Map<String, Object> param = new HashMap<>();
		setInduClassParam(userId, orgId, param);
		// 通过userId 获取机构inst_id
		param.put("orgId", orgId);
		param.put("groupId", groupId);

		// 通过ratingId 查找 rating
		if (null != ratingId && ratingId > 0) {
			param.put("ratingId", ratingId);
		}
		
		List<Long> issuerIds = null;
		if (!StringUtils.isBlank(query)) {
			issuerIds = searchComInfoByQuery(query);
		}
		
		if (null != issuerIds && !issuerIds.isEmpty()) {
			param.put("issuerIds", issuerIds);
		}
		
		if (null != induId && induId > 0) {
			param.put("induId", induId);
		}
		
		if (currGroup.getGroupType().intValue() == 0) {
			param.put("groupedFlag", groupedFlag);
		}
		return param;
	}

	private List<Long> searchComInfoByQuery(String query) {
		Query issquery = new Query();
		query = query.trim().replaceAll("(?=[]\\[+&|!(){}^\"~*?:\\\\-])", "\\\\");
		Pattern pattern = Pattern.compile("^.*" + query + ".*$", Pattern.CASE_INSENSITIVE);
		issquery.addCriteria(Criteria.where("comChiName").regex(pattern));
		issquery.fields().include("comUniCode");
		List<Long> issuerIdList = mongoOperations.find(issquery, BondComInfoDoc.class).stream().map(BondComInfoDoc::getComUniCode).collect(Collectors.toList());
		return issuerIdList;
	}

	private void setComInfo(Long issuerId, Map<Long, BondComInfoDoc> comInfoMap, CreditRatingIssuerVo creditRatingIssuer) {
		BondComInfoDoc comInfo = comInfoMap.get(issuerId);
		if (null != comInfo) {
			creditRatingIssuer.setPdTime(comInfo.getPdTime());
			creditRatingIssuer.setPd(comInfo.getPd());
			creditRatingIssuer.setPdDiff(comInfo.getPdDiff());
			creditRatingIssuer.setRateProsPar(comInfo.getRateProsPar());
			creditRatingIssuer.setWorstPd(comInfo.getWorstPd());
			creditRatingIssuer.setWorstPdNum(comInfo.getWorstPdNum());
			creditRatingIssuer.setWorstPdTime(comInfo.getWorstPdTime());
			creditRatingIssuer.setIssCredLevel(comInfo.getIssCredLevel());
			creditRatingIssuer.setIssuerName(comInfo.getComChiName());
		}
	}

	private void setGroupData(List<BondCreditRatingGroup> allGroups, BondCreditRatingGroup currGroup,
			Map<String, Object> grpparam, CreditRatingIssuerVo creditRatingIssuer, Long issuerId) {
		creditRatingIssuer.setAllGroup(allGroups);
		if (currGroup.getGroupType().intValue() == 0) {
			grpparam.put("issuerId", issuerId);
			BondCreditRatingGroup ccgroup = creditRatingGroupDao.queryByIssuerId(grpparam);
			if (ccgroup != null && ccgroup.getGroupType().intValue() == 0) {
				ccgroup.setGroupName(InstConstants.NOTJOIN_GROUP);
			}
			creditRatingIssuer.setCurrGroup(ccgroup);
		}else{
			creditRatingIssuer.setCurrGroup(currGroup);
		}
	}

	private void setRatingData( CreditRatingIssuerVo creditRatingIssuer,Long issuerId,Map<Long, BondInstRatingHist> issuerHistMap) {
		if (null != issuerHistMap && !issuerHistMap.isEmpty()) {
			BondInstRatingHist bondInstRatingHist = issuerHistMap.get(issuerId);
			if (null != bondInstRatingHist) {
				creditRatingIssuer.setCurrentRating(bondInstRatingHist.getRatingName());
				creditRatingIssuer.setCurrentRatingDate(SafeUtils.convertDateToString(bondInstRatingHist.getRatingTime(), SafeUtils.DATE_FORMAT));
				creditRatingIssuer.setCurrentRatingDiff(bondInstRatingHist.getRatingDiff());
				creditRatingIssuer.setCurrentRatingDesc(SafeUtils.getString(bondInstRatingHist.getRatingDescribe()));
			}else{
				setCurrentRateBlank(creditRatingIssuer);
			}
		}else{
			setCurrentRateBlank(creditRatingIssuer);
		}
	}

	private void setCurrentRateBlank(CreditRatingIssuerVo creditRatingIssuer) {
		creditRatingIssuer.setCurrentRating("");
		creditRatingIssuer.setCurrentRatingDate(null);
		creditRatingIssuer.setCurrentRatingDiff(null);
		creditRatingIssuer.setCurrentRatingDesc("");
	}

	public Page<CreditRatingBondVo> findBonds(Integer userId, Long groupId, Integer adviceId, Integer ratingId,
			Integer induId, String query, Integer pageIndex, Integer pageSize) {
		LOGGER.info("findBonds groupId:" + groupId + ",userId:" + userId + ",adviceId:" + adviceId + ",ratingId:"
				+ ratingId + ",induId:" + induId + ",query:" + query);
		List<CreditRatingBondVo> results = new ArrayList<>();
		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		Integer version = instCodeDao.queryVersionByOrgId(orgId);
		BondCreditRatingGroup currGroup = creditRatingGroupDao.queryById(groupId);
		Map<String, Object> param = setCommonBondParam(userId, groupId, adviceId, ratingId, induId, query, orgId,
				currGroup);

		param.put("pageIndex", pageIndex*pageSize);
		param.put("pageSize", pageSize);

		results = creditRatingDao.queryBonds(param);
		int count = creditRatingDao.queryBondsCount(param);
		List<Long> bondIdList = results.stream().map(CreditRatingBondVo::getBondId).collect(Collectors.toList());

		Query bondquery = new Query();
		bondquery.addCriteria(Criteria.where("bondUniCode").in(bondIdList));
		bondquery.fields().include("impliedRating").include("issuer").include("issBondRating").include("pd").include("issuerId")
				.include("issRatePros");
		List<BondBasicInfoDoc> dataList = mongoOperations.find(bondquery, BondBasicInfoDoc.class);
		Map<Long, BondBasicInfoDoc> bondInfoMap = dataList.stream().collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, (p) -> p));
		
		Set<Long> issuerIdSet = dataList.stream().map(BondBasicInfoDoc::getIssuerId).collect(Collectors.toCollection(HashSet::new));
		
		List<Long> issuerIds = issuerIdSet.stream().collect(Collectors.toList());
		Map<Long, BondInstRatingHist> bondHistMap = getBondHistMap(bondIdList,orgId,version);
		Map<Long, BondInstRatingHist> issuerHistMap = getIssuerHistMap(issuerIds, orgId, version);
		
		Query comquery = new Query();
		comquery.addCriteria(Criteria.where("comUniCode").in(issuerIdSet));
		comquery.fields().include("comUniCode").include("comChiName").include("pdDiff").include("pd").include("pdTime").include("rateProsPar");
		
		Map<Long, BondComInfoDoc> comMap = mongoOperations.find(comquery, BondComInfoDoc.class).stream().collect(Collectors.toMap(BondComInfoDoc::getComUniCode, (p) -> p));
		handleBondResults(userId, results, bondInfoMap, bondHistMap, issuerHistMap, comMap);

		return new PageImpl<CreditRatingBondVo>(results, new PageRequest(pageIndex, pageSize), count);
	}

	private void handleBondResults(Integer userId, List<CreditRatingBondVo> results,
			Map<Long, BondBasicInfoDoc> bondInfoMap, Map<Long, BondInstRatingHist> bondHistMap,
			Map<Long, BondInstRatingHist> issuerHistMap, Map<Long, BondComInfoDoc> comMap) {
		if (null != results && !results.isEmpty()) {
			for (int i = 0; i < results.size(); i++) {
				CreditRatingBondVo creditRatingBond = results.get(i);
				setBondData(bondInfoMap, creditRatingBond, userId, comMap);
//				setInstRatingData(instRatingHistMap, creditRatingBond);
				setInstRatingData(creditRatingBond, bondHistMap, issuerHistMap);
			}
		}
	}

	private Map<String, Object> setCommonBondParam(Integer userId, Long groupId, Integer adviceId, Integer ratingId,
			Integer induId, String query, Integer orgId, BondCreditRatingGroup currGroup) {
		Map<String, Object> param = new HashMap<>();
		setInduClassParam(userId, orgId, param);
		param.put("orgId", orgId);

		// 通过ratingId 查找 rating
		if (null != ratingId && ratingId > 0) {
			param.put("ratingId", ratingId);
		}
		
		// 通过ratingId 查找 invest advice
		if (null != adviceId && adviceId > 0) {
			param.put("adviceId", adviceId);
		}
		
		if (null != induId && induId > 0) {
			param.put("induId", induId);
		}

		// groupId 区分 全部和非全部 的组,信评组类型, 默认0-全部，默认1-禁投组，9-信评分组
		if (currGroup.getGroupType().intValue() > 0) {
			// 非全部
			param.put("groupId", groupId);
		}

		if (!StringUtils.isBlank(query)) {
			param.put("queryKeyws", query.trim());
			List<Long> issuerIdList = searchComInfoByQuery(query);
			if (null != issuerIdList && !issuerIdList.isEmpty()) {
				param.put("queryKeyIssuerIds", issuerIdList);
			}
		}
		return param;
	}

	private void setInduClassParam(Integer userId, Integer orgId, Map<String, Object> param) {
		if (induService.isInduInstitution(SafeUtils.getLong(userId), orgId)) {
			param.put("induClass", 3);
		}else if(induService.isGicsInduClass(SafeUtils.getLong(userId))){
			param.put("induClass", 1);
		}else{
			param.put("induClass", 2);
		}
	}

	private void setInstRatingData(CreditRatingBondVo creditRatingBond, Map<Long, BondInstRatingHist> bondHistMap,
			Map<Long, BondInstRatingHist> issuerHistMap) {
		if (null != bondHistMap && !bondHistMap.isEmpty()) {
			BondInstRatingHist bondHist = bondHistMap.get(creditRatingBond.getBondId());
			if (null != bondHist) {
				BondInstCode instCode = instCodeDao.queryById(bondHist.getInvestmentAdvice());
				creditRatingBond.setInvestmentAdvice(instCode != null ? instCode.getName() : "");
				creditRatingBond.setInvestmentAdviceDate(SafeUtils.convertDateToString(bondHist.getRatingTime(), SafeUtils.DATE_FORMAT));
				creditRatingBond.setInvestmentAdviceDesc(SafeUtils.getString(bondHist.getInvestmentAdviceDesdetail()));
			}else{
				setInvestmentAdviceBlank(creditRatingBond);
			}
		}else{
			setInvestmentAdviceBlank(creditRatingBond);
		}
		if (null != issuerHistMap && !issuerHistMap.isEmpty()) {
			BondInstRatingHist issuerHist = issuerHistMap.get(creditRatingBond.getIssuerId());
			if (null != issuerHist) {
				creditRatingBond.setCurrentRating(issuerHist.getRatingName());
				creditRatingBond.setCurrentRatingDate(SafeUtils.convertDateToString(issuerHist.getRatingTime(), SafeUtils.DATE_FORMAT));
				creditRatingBond.setCurrentRatingDiff(issuerHist.getRatingDiff());
				creditRatingBond.setCurrentRatingDesc(SafeUtils.getString(issuerHist.getRatingDescribe()));
			}else{
				setCurrentRateBlank(creditRatingBond);
			}
		}else{
			setCurrentRateBlank(creditRatingBond);
		}
	}

	private void setInvestmentAdviceBlank(CreditRatingBondVo creditRatingBond) {
		creditRatingBond.setInvestmentAdvice("");
		creditRatingBond.setInvestmentAdviceDate(null);
		creditRatingBond.setInvestmentAdviceDesc("");
	}

	private void setCurrentRateBlank(CreditRatingBondVo creditRatingBond) {
		creditRatingBond.setCurrentRating("");
		creditRatingBond.setCurrentRatingDate(null);
		creditRatingBond.setCurrentRatingDiff(null);
		creditRatingBond.setCurrentRatingDesc("");
	}

	private void setBondData(Map<Long, BondBasicInfoDoc> bondInfoMap, CreditRatingBondVo creditRatingBond, Integer userId, Map<Long, BondComInfoDoc> comMap) {
		Long bondId = creditRatingBond.getBondId();
		BondBasicInfoDoc bondInfo = bondInfoMap.get(bondId);
		if (null != bondInfo) {
			isFavoritedAndCompared(userId, bondId, bondInfo);
			creditRatingBond.setIsCompared(bondInfo.getIsCompared());
			creditRatingBond.setIsFavorited(bondInfo.getIsFavorited());
			creditRatingBond.setFavoriteId(bondInfo.getFavoriteId());
			creditRatingBond.setIssBondRating(bondInfo.getIssBondRating());
			creditRatingBond.setImpliedRating(bondInfo.getImpliedRating());
//			creditRatingBond.setIssRatePros(bondInfo.getIssRatePros());
		}else{
			creditRatingBond.setIsCompared(false);
			creditRatingBond.setIsFavorited(false);
		}
		BondComInfoDoc comInfo = comMap.get(creditRatingBond.getIssuerId());
		if (null != comInfo) {
			creditRatingBond.setPd(comInfo.getPd());
			creditRatingBond.setPdDiff(comInfo.getPdDiff());
			creditRatingBond.setPdTime(comInfo.getPdTime());
			creditRatingBond.setIssuer(comInfo.getComChiName());
			creditRatingBond.setIssRatePros(comInfo.getRateProsPar());
		}
	}

	private void isFavoritedAndCompared(Integer userId, Long bondId, BondBasicInfoDoc basicInfo) {
		if (userId == null) {
			throw new BusinessException("用户 id不能为空");
		}
		// 是否关注
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("bondId", bondId);
		BondFavorite bondFav = bondFavoriteDao.queryOneByUserIdAndBondId(param);
		if (bondFav != null) {
			basicInfo.setIsFavorited(true);
			basicInfo.setFavoriteId(bondFav.getFavoriteId());
		} else {
			basicInfo.setIsFavorited(false);
		}

		// 对比列表
		Set<Long> comparisonSet = new HashSet<Long>();
		List<BondComparisonDoc> comps = comparisonService.findComparisonByUserId(userId);
		if (comps != null) {
			comps.forEach(compDoc -> {
				comparisonSet.add(compDoc.getBondId());
			});
		}
		basicInfo.setIsCompared(comparisonSet.contains(bondId));
	}

	private void setInstRatingData(Map<Long, BondInstRatingHist> instRatingHistMap, CreditRatingBondVo creditRatingBond) {
		Long bondId = creditRatingBond.getBondId();
		if (null != instRatingHistMap && !instRatingHistMap.isEmpty()) {
			BondInstRatingHist bondInstRatingHist = instRatingHistMap.get(bondId);
			creditRatingBond.setCurrentRating(bondInstRatingHist.getRatingName());
			creditRatingBond.setCurrentRatingDate(SafeUtils.convertDateToString(bondInstRatingHist.getRatingTime(), SafeUtils.DATE_FORMAT));
			creditRatingBond.setCurrentRatingDiff(bondInstRatingHist.getRatingDiff());
			creditRatingBond.setInvestmentAdvice(bondInstRatingHist.getInvestmentAdvice().toString());
			creditRatingBond.setInvestmentAdviceDate(SafeUtils.convertDateToString(bondInstRatingHist.getRatingTime(), SafeUtils.DATE_FORMAT));
		}
	}

	private List<BondInstRatingHist> findInstRatingHistByRatingAdvice(Integer adviceId, Integer ratingId, Integer orgId,Integer version) {
		Map<String, Object> ratparam = new HashMap<>();
		ratparam.put("instId", orgId);
		ratparam.put("version", version);
		ratparam.put("ratingId", ratingId);
		ratparam.put("adviceId", adviceId);
		ratparam.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		List<BondInstRatingHist> instRatingHistList = instRatingHistDao.queryInstRatingHistByRatingAdvice(ratparam);
		return instRatingHistList;
	}

	public CreditRatingParsedInfoVo parseExcel(Integer userId, Long groupId, MultipartFile sourceFile) {
		LOGGER.info("parseExcel userId:" + userId + ",groupId:" + groupId + ",sourceFile:"
				+ sourceFile.getOriginalFilename());
		CreditRatingParsedInfoVo parsedInfoVo = new CreditRatingParsedInfoVo();
		// 解析excel
		List<ExcelColumn> list = parseTargetExcel(sourceFile);
		list = list.stream().filter(excelColumn -> StringUtils.isNotBlank(excelColumn.getColumn1()) || StringUtils.isNotBlank(excelColumn.getColumn2()) 
				|| StringUtils.isNotBlank(excelColumn.getColumn3()) || StringUtils.isNotBlank(excelColumn.getColumn5())).collect(Collectors.toList());
		
		LOGGER.info("parseExcel outData:" + JSON.toJSONString(list));
		// 获取excel数据,数据业务处理
		handleColumnData(userId, list, parsedInfoVo);

		return parsedInfoVo;
	}

	private void handleColumnData(Integer userId, List<ExcelColumn> list, CreditRatingParsedInfoVo parsedInfoVo) {
		List<CreditRatingParsed> validData = new ArrayList<>();
		List<CreditRatingParsed> invalidData = new ArrayList<>();
		// ------------------------
		// 根据userId获取所在机构ID instId
		Integer instId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		Integer version = instCodeDao.queryVersionByOrgId(instId);

		list.stream().forEach(columnData -> {
			
			handleColumnDataLogic(instId, columnData, validData, invalidData, version);
		});

		List<BondInstCode> allInadv = getAllInvestmentAdvice(instId, version);
		List<BondInstCode>  allRat = getAllRating(instId, version);
		List<Long> bondIds = validData.stream().filter(creditRat -> null != creditRat.getBondId() && creditRat.getBondId() > 0).map(CreditRatingParsed::getBondId).collect(Collectors.toList());
		List<Long> issuerIds = validData.stream().filter(creditRat -> null != creditRat.getIssuerId() && creditRat.getIssuerId() > 0).map(CreditRatingParsed::getIssuerId).collect(Collectors.toList());
		Map<Long, BondInstRatingHist> bondHistMap = getBondHistMap(bondIds, instId, version);
		Map<Long, BondInstRatingHist> issuerHistMap = getIssuerHistMap(issuerIds, instId, version);
		
		validData.stream().forEach(validDataCr -> {
			validDataCr.setAllInvestmentAdvice(allInadv);
			validDataCr.setAllRating(allRat);
			setRatingHist(issuerHistMap, validDataCr);
			setInvestmentAdviceHist(bondHistMap, validDataCr);
		});
		
		parsedInfoVo.setValidCount(validData == null ? 0 : validData.size());
		parsedInfoVo.setValidData(validData);
		parsedInfoVo.setInvalidCount(invalidData == null ? 0 : invalidData.size());
		parsedInfoVo.setInvalidData(invalidData);
	}

	private void setInvestmentAdviceHist(Map<Long, BondInstRatingHist> bondHistMap, CreditRatingParsed validDataCr) {
		if (null!=validDataCr.getBondId() && validDataCr.getBondId() > 0 &&
				null!=bondHistMap && !bondHistMap.isEmpty()) {
			BondInstRatingHist bondHist = bondHistMap.get(validDataCr.getBondId());
			if (null != bondHist) {
				validDataCr.setInvestmentAdvice(bondHist.getInvestmentDescribe());
				validDataCr.setInvestmentAdviceId(bondHist.getInvestmentAdvice());
			}
		}
	}

	private void setRatingHist(Map<Long, BondInstRatingHist> issuerHistMap, CreditRatingParsed validDataCr) {
		if (null != validDataCr.getIssuerId() && validDataCr.getIssuerId() > 0 &&
				null != issuerHistMap && !issuerHistMap.isEmpty()) {
			BondInstRatingHist issuerHist = issuerHistMap.get(validDataCr.getIssuerId());
			if (null != issuerHist) {
				validDataCr.setCurrentRating(issuerHist.getRatingName());
				validDataCr.setCurrentRatingId(issuerHist.getRating());
			}
		}
	}

	private Map<Long, BondInstRatingHist> getBondHistMap(List<Long> bondIds, Integer instId, Integer version) {
		if (bondIds.isEmpty()) {
			return null;
		}
		Map<String, Object> bondparam = new HashMap<>();
		bondparam.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		bondparam.put("instId", instId);
		bondparam.put("version", version);
		bondparam.put("bondIds", bondIds);
		List<BondInstRatingHist> bondHistList = instRatingHistDao.queryLastInstRatingHistList(bondparam);
		Map<Long, BondInstRatingHist> bondHistMap = bondHistList.stream().collect(Collectors.toMap(BondInstRatingHist::getBondUniCode, (p) -> p));
		return bondHistMap;
	}

	private Map<Long, BondInstRatingHist> getIssuerHistMap(List<Long> issuerIds, Integer instId, Integer version) {
		if (issuerIds.isEmpty()) {
			return null;
		}
		Map<String, Object> issuerparam = new HashMap<>();
		issuerparam.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		issuerparam.put("instId", instId);
		issuerparam.put("version", version);
		issuerparam.put("issuerIds", issuerIds);
		List<BondInstRatingHist> issuerHistList = instRatingHistDao.queryLastInstRatingHistList(issuerparam);
		Map<Long, BondInstRatingHist> issuerHistMap = issuerHistList.stream().collect(Collectors.toMap(BondInstRatingHist::getComUniCode, (p) -> p));
		return issuerHistMap;
	}

	private List<BondInstCode> getAllRating(Integer instId, Integer version) {
		Map<String, Object> ratparam = new HashMap<>();
		ratparam.put("type", InstConstants.INST_CODE_TYPE_RATING);
		ratparam.put("orgId", instId);
		ratparam.put("version", version);
		List<BondInstCode> allRat = instCodeDao.queryByTypeAndOrgId(ratparam);
		return allRat;
	}

	private List<BondInstCode> getAllInvestmentAdvice(Integer instId, Integer version) {
		Map<String, Object> invparam = new HashMap<>();
		invparam.put("type", InstConstants.INST_CODE_TYPE_INVADC);
		invparam.put("orgId", instId);
		List<BondInstCode> allInadv = instCodeDao.queryByTypeAndOrgId(invparam);
		return allInadv;
	}

	private void handleColumnDataLogic(Integer instId, ExcelColumn columnData,
			List<CreditRatingParsed> validData, List<CreditRatingParsed> invalidData, Integer version) {
		String bondCode = columnData.getColumn1();
		String isserName = columnData.getColumn2();
		String selectedrating = columnData.getColumn3();
		String selectedinvadvice = columnData.getColumn5();
		String ratingDesc = columnData.getColumn4();
		String selectedinvadvDesc = columnData.getColumn6();
		if (!StringUtils.isBlank(selectedrating)) {
			selectedrating = StringUtils.QjtoBj(selectedrating.replace(" ", ""));
		}

		StringBuffer reminderHint = new StringBuffer();
		if (StringUtils.isBlank(bondCode) && StringUtils.isBlank(isserName)) {
			reminderHint.append(InstConstants.REMINDERHINT_BONDCODEISSUER_EMPTY).append(",");
		}
		
		if (!StringUtils.isBlank(bondCode)) {
			bondCode = bondCode.trim().toUpperCase();
			BondBasicInfoDoc bondBasicInfo = checkBondByBondCode(bondCode);
			if (null != bondBasicInfo) {
				
				handleDataByBondInfo(instId, validData, invalidData, bondCode, isserName, selectedrating, selectedinvadvice,
						bondBasicInfo, version, ratingDesc, selectedinvadvDesc,reminderHint);
			} else if (!StringUtils.isBlank(isserName)) {
				
				reminderHint.append(InstConstants.REMINDERHINT_BONDCODE_NOEXSIT).append(",");
				handleDataByIsserName(instId, validData, invalidData, bondCode, isserName, selectedrating,
						selectedinvadvice, bondBasicInfo, version, ratingDesc, selectedinvadvDesc,reminderHint);
			} else {
				
				reminderHint.append(InstConstants.REMINDERHINT_BONDCODE_NOEXSIT).append(",").append(InstConstants.REMINDERHINT_ISSUER_EMPTY).append(",");
				packageRatingInvadvHint(instId, version, selectedrating, selectedinvadvice, reminderHint);
				invalidData.add(setInvalidRat(instId, bondCode, isserName, selectedrating, selectedinvadvice, ratingDesc, selectedinvadvDesc,reminderHint));
			}
		} else if (!StringUtils.isBlank(isserName)) {
			
			handleDataByIsserName(instId, validData, invalidData, bondCode, isserName, selectedrating,
					selectedinvadvice, null, version, ratingDesc, selectedinvadvDesc,reminderHint);
		} else {
			
			packageRatingInvadvHint(instId, version, selectedrating, selectedinvadvice, reminderHint);
			invalidData.add(setInvalidRat(instId, bondCode, isserName, selectedrating, selectedinvadvice, ratingDesc, selectedinvadvDesc,reminderHint));
		}
	}

	private void packageRatingInvadvHint(Integer instId, Integer version, String selectedrating,
			String selectedinvadvice, StringBuffer reminderHint) {
		BondInstCode instCodeRat = checkRatOrInvadvByName(selectedrating, InstConstants.INST_CODE_TYPE_RATING,instId, version);
		BondInstCode instCodeInv = checkRatOrInvadvByName(selectedinvadvice, InstConstants.INST_CODE_TYPE_INVADC, instId, version);
		setRatingHint(selectedrating, reminderHint, instCodeRat);
		setInvadvHint(selectedinvadvice, reminderHint, instCodeInv);
	}

	private void handleDataByBondInfo(Integer instId, List<CreditRatingParsed> validData,
			List<CreditRatingParsed> invalidData, String bondCode, String isserName, String selectedrating,
			String selectedinvadvice, BondBasicInfoDoc bondBasicInfo, Integer version,String ratingDes, String selectedinvadvDesc,
			StringBuffer reminderHint) {
		BondInstCode instCodeRat = checkRatOrInvadvByName(selectedrating, InstConstants.INST_CODE_TYPE_RATING, instId, version);
		BondInstCode instCodeInv = checkRatOrInvadvByName(selectedinvadvice, InstConstants.INST_CODE_TYPE_INVADC, instId, null);
		
		if (null != bondBasicInfo.getIssuerId() && bondBasicInfo.getIssuerId() > 0 && null != bondBasicInfo.getCurrStatus() && bondBasicInfo.getCurrStatus().compareTo(1) == 0 
				&& null != bondBasicInfo.getIssStaPar() && bondBasicInfo.getIssStaPar().compareTo(1) == 0) {
			if (null != instCodeRat) {
				
				validData.add(setValidRat(instId, bondCode, bondBasicInfo, bondBasicInfo.getIssuer(), bondBasicInfo.getIssuerId(),instCodeRat, instCodeInv, ratingDes, selectedinvadvDesc));
			} else {
				
				handleInvalidDataSetting(instId, invalidData, bondCode, isserName, selectedrating, selectedinvadvice,
						ratingDes, selectedinvadvDesc, reminderHint, instCodeRat, instCodeInv);
			}
		}else{
			
			reminderHint.append(InstConstants.REMINDERHINT_BOND_EXPIRED).append(",");
			handleInvalidDataSetting(instId, invalidData, bondCode, isserName, selectedrating, selectedinvadvice,
					ratingDes, selectedinvadvDesc, reminderHint, instCodeRat, instCodeInv);
		} 
	}

	private void setRatingHint(String selectedrating, StringBuffer reminderHint, BondInstCode instCodeRat) {
		if (StringUtils.isEmpty(selectedrating)) {
			reminderHint.append(InstConstants.REMINDERHINT_RATING_EMPTY).append(",");
		}else{
			if (null == instCodeRat) {
				reminderHint.append(InstConstants.REMINDERHINT_RATING_NOEXSIT).append(",");
			}
		}
	}

	private void handleDataByIsserName(Integer instId, List<CreditRatingParsed> validData,
			List<CreditRatingParsed> invalidData, String bondCode, String isserName, String currrating,
			String invadvice, BondBasicInfoDoc bondBasicInfo, Integer version, String ratingDesc,String selectedinvadvDesc,
			StringBuffer reminderHint) {
		BondComInfoDoc comInfo = checkBondByIsserName(isserName.trim());
		BondInstCode instCodeRat = checkRatOrInvadvByName(currrating, InstConstants.INST_CODE_TYPE_RATING,instId, version);
		BondInstCode instCodeInv = checkRatOrInvadvByName(invadvice, InstConstants.INST_CODE_TYPE_INVADC, instId, version);
		
		if (null != comInfo) {
			if (null != instCodeRat) {
				validData.add(setValidRat(instId, bondCode, bondBasicInfo,comInfo.getComChiName(), comInfo.getComUniCode(),instCodeRat, instCodeInv,ratingDesc, null));
			} else {
				handleInvalidDataSetting(instId, invalidData, bondCode, isserName, currrating, invadvice, ratingDesc,
						selectedinvadvDesc, reminderHint, instCodeRat, instCodeInv);
			}
		} else {
			reminderHint.append(InstConstants.REMINDERHINT_ISSUER_NOEXSIT).append(",");
			handleInvalidDataSetting(instId, invalidData, bondCode, isserName, currrating, invadvice, ratingDesc,
					selectedinvadvDesc, reminderHint, instCodeRat, instCodeInv);
		}
	}

	private void handleInvalidDataSetting(Integer instId, List<CreditRatingParsed> invalidData, String bondCode,
			String isserName, String currrating, String invadvice, String ratingDesc, String selectedinvadvDesc,
			StringBuffer reminderHint, BondInstCode instCodeRat, BondInstCode instCodeInv) {
		setRatingHint(currrating, reminderHint, instCodeRat);
		setInvadvHint(invadvice, reminderHint, instCodeInv);
		invalidData.add(setInvalidRat(instId, bondCode, isserName, currrating, invadvice, ratingDesc, selectedinvadvDesc,reminderHint));
	}

	private void setInvadvHint(String invadvice, StringBuffer reminderHint, BondInstCode instCodeInv) {
		if (!StringUtils.isBlank(invadvice) && null == instCodeInv) {
			reminderHint.append(InstConstants.REMINDERHINT_INVADV_NOEXSIT).append(",");
		}
	}

	private BondInstCode checkRatOrInvadvByName(String name, Integer type, Integer orgId, Integer version) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		Map<String, Object> param = new HashMap<>();
		param.put("name", name.trim());
		param.put("type", type);
		param.put("orgId", orgId);
		param.put("version", version);
		return instCodeDao.queryByNameAndTypeAndOrgId(param);
	}

	private CreditRatingParsed setValidRat(Integer orgId, String bondCode, BondBasicInfoDoc bondBasicInfo,String issuer,Long issuerId, BondInstCode instCodeRat,
			BondInstCode invadvice,String ratingDes, String selectedinvadvDesc) {
		CreditRatingParsed validRat = new CreditRatingParsed();
		//债券信息
		if (null != bondBasicInfo) {
			validRat.setBondCode(bondCode);
			validRat.setBondId(bondBasicInfo.getBondUniCode());
			validRat.setBondName(bondBasicInfo.getShortName());
		}
		//主体信息
		validRat.setIssuer(issuer);
		validRat.setIssuerId(issuerId);
		validRat.setCurrGroup(findGroupByOrgIdIssuerId(orgId, issuerId));
		// 获取内部评级
		validRat.setSelectedRating(instCodeRat);
		// 获取投资建议
		validRat.setSelectedInvestmentAdvice(invadvice);
		
		validRat.setCurrentRatingDes(SafeUtils.getString(ratingDes));
		validRat.setInvestmentAdviceDesDetail(SafeUtils.getString(selectedinvadvDesc));
		return validRat;
	}

	private BondCreditRatingGroup findGroupByOrgIdIssuerId(Integer orgId, Long issuerId) {
		BondCreditRatingGroup currGroup = null;
		Long groupId = getGroupIdByOrgIdAndIssuerId(issuerId, orgId);
		if (null != groupId && groupId > 0) {
			currGroup = creditRatingGroupDao.queryById(groupId);
		}
		LOGGER.info("findGroupByOrgIdIssuerId orgId:"+orgId+",groupId:"+groupId+",currGroup:"+JSON.toJSONString(currGroup));
		return currGroup;
	}

	private CreditRatingParsed setInvalidRat(Integer instId, String bondCode, String isserName,
			String selectedrating, String selectedinvadvice, String ratingDes, String selectedinvadvDesc,
			StringBuffer reminderHint) {
		CreditRatingParsed invalidRat = new CreditRatingParsed();
		invalidRat.setBondCode(bondCode);
		invalidRat.setIssuer(isserName);
		invalidRat.setSelectedRating(new BondInstCode(selectedrating,InstConstants.INST_CODE_TYPE_RATING,instId,0));
		invalidRat.setSelectedInvestmentAdvice(new BondInstCode(selectedinvadvice,InstConstants.INST_CODE_TYPE_INVADC,instId,0));
		invalidRat.setCurrentRatingDes(SafeUtils.getString(ratingDes));
		invalidRat.setInvestmentAdviceDesDetail(SafeUtils.getString(selectedinvadvDesc));
		if (!StringUtils.isBlank(reminderHint.toString())) {
			invalidRat.setReminderHint(reminderHint.toString().substring(0, reminderHint.toString().length()-1));
		}
		return invalidRat;
	}

	private BondComInfoDoc checkBondByIsserName(String isserName) {
		Query comquery = new Query();
		comquery.addCriteria(Criteria.where("comChiName").is(isserName));
		comquery.fields().include("comUniCode").include("comChiName");
		BondComInfoDoc comInfo = mongoOperations.findOne(comquery, BondComInfoDoc.class);
		if (null == comInfo) {
			return null;
		}
		return comInfo;
	}

	private BondBasicInfoDoc checkBondByBondCode(String bondCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("code").is(bondCode));
		query.fields().include("bondUniCode").include("issuerId").include("issuer").include("shortName").include("tenorDays").include("currStatus").include("issStaPar");
		BondBasicInfoDoc bondBasicInfo = mongoOperations.findOne(query, BondBasicInfoDoc.class);
		if (null == bondBasicInfo) {
			return null;
		}
		return bondBasicInfo;
	}

	private List<ExcelColumn> parseTargetExcel(MultipartFile sourceFile) {
		List<ExcelColumn> outData = new ArrayList<>();
		try {
			Workbook workbook = excelParseTool.initWorkBook(sourceFile.getOriginalFilename(),
					(FileInputStream) sourceFile.getInputStream());

			if (workbook != null) {
				excelParseTool.parseWorkbook(workbook, outData);
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("parseTargetExcel error:" + e.getMessage(), e);
		}
		return outData;
	}
	
	@Transactional
	public int handleSavedCreditRating(Integer userId, Long currentGroupId, Integer usedFlag, Integer orgId,
			List<CreditRatingParsedBasic> list,Integer version) throws Exception{
		for (int i = 0; i < list.size(); i++) {
			CreditRatingParsedBasic basicCreditRating = list.get(i);
			// 是否忽略属于用于其他信评组来获取GroupId
			Long groupId = getGroupIdByUsedFlag(usedFlag, currentGroupId, basicCreditRating);
			// 验证数据,在系统中存在的 or 在系统中不存在的 saveOrUpdate
			handleCreditRating(userId, orgId, basicCreditRating, groupId);
		}
		return 1;
	}

	public String importExcel(Integer userId, Long currentGroupId, Integer usedFlag, Integer repeatFlag, Integer importFlag,
			CreditRatingImportReq data) throws Exception {
		String result = "done";
		if (data.getData().isEmpty()) {
			result = "数据为空";
			return result;
		}
		try {
			Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
			Integer version = instCodeDao.queryVersionByOrgId(orgId);
			if (null == version) {
				throw new Exception("评级数据版本不存在");
			}
			
			handleImportedCreditRating(userId, currentGroupId, usedFlag, repeatFlag, orgId, data.getData(),version, importFlag);
		} catch (Exception e) {
			LOGGER.error("handleImportedCreditRating error:" + e.getMessage(), e);
			result = "失败";
			throw e;
		}
		return result;
	}
	
	@Transactional
	public int handleImportedCreditRating(Integer userId, Long currentGroupId, Integer usedFlag, Integer repeatFlag, Integer orgId,
			List<CreditRatingParsedBasic> list,Integer version,Integer importFlag) throws Exception{
		Date today = new Date();
		for (int i = 0; i < list.size(); i++) {
			CreditRatingParsedBasic basicCreditRating = list.get(i);
			// 是否忽略属于用于其他信评组来获取GroupId
			Long groupId = getGroupIdByUsedFlag(usedFlag, currentGroupId, basicCreditRating);

			// 验证数据,在系统中存在的 or 在系统中不存在的 saveOrUpdate
			handleCreditRatings(userId, orgId, basicCreditRating, groupId, importFlag);
			// 处理建议记录 和 内部评级级记录
			handleInstRatingHist(userId, orgId, version, today, basicCreditRating, repeatFlag, importFlag);
		}
		return 1;
	}
	
	private void handleCreditRatings(Integer userId, Integer orgId, CreditRatingParsedBasic basicCreditRating,
			Long groupId, Integer importFlag) {
		int result = 0;
		if (importFlag == 0 || (importFlag == 1 && basicCreditRating.getBondId().compareTo(0L) > 0)) {
			BondCreditRating entity = packageCeditRatings(userId, orgId, basicCreditRating, groupId, importFlag);
			int count = creditRatingDao.queryIssuerCountByOrgid(entity);
			if (count == 0) {
				result = creditRatingDao.saveCreditRating(entity);
				LOGGER.info("handleCreditRating save result:" + result);
			}
			result = creditRatingDao.updateGroupIdByOrgid(entity);
			LOGGER.info("handleCreditRating entity:"+JSON.toJSONString(entity)+",count:"+count+",groupId:"+groupId+",update result:" + result);
		}else{
			LOGGER.info("handleCreditRatings,importFlag and BondId does not match,importFlag:"+importFlag+",BondId:"+basicCreditRating.getBondId());
		}
	}

	private BondCreditRating packageCeditRatings(Integer userId, Integer orgId,
			CreditRatingParsedBasic basicCreditRating, Long groupId, Integer importFlag) {
		BondCreditRating creditRating = new BondCreditRating();
		if (importFlag == 0) {
			//从主体方向导入，只作为主体
			creditRating.setBondUniCode(0L);
			creditRating.setInstinvadvId(0L);
		}else{
			//从债券方向导入
			creditRating.setBondCode(basicCreditRating.getBondCode());
			creditRating.setBondShortname(basicCreditRating.getBondName());
			creditRating.setBondUniCode(SafeUtils.getLong(basicCreditRating.getBondId()));
			BondInstCode investmentAdv = basicCreditRating.getSelectedInvestmentAdvice();
			if (null != investmentAdv) {
				creditRating.setInstinvadvId(SafeUtils.getLong(investmentAdv.getId()));
			}else{
				creditRating.setInstinvadvId(0L);
			}
		}
		creditRating.setCreateTime(new Date());
		creditRating.setGroupId(groupId);
		creditRating.setIssuerId(basicCreditRating.getIssuerId());
		creditRating.setOrgId(orgId);
		creditRating.setUserId(userId);
		BondInstCode rating  = basicCreditRating.getSelectedRating();
		if (null != rating) {
			creditRating.setInstratingId(SafeUtils.getLong(rating.getId()));
		} else {
			creditRating.setInstratingId(0L);
		}
		return creditRating;
	}

	private void handleCreditRating(Integer userId, Integer orgId, CreditRatingParsedBasic basicCreditRating,
			Long groupId) {
		int result = 0;
		BondCreditRating entity = packageCeditRating(userId, orgId, basicCreditRating, groupId);
		int count = creditRatingDao.queryCountByOrgid(entity);
		if (count == 0) {
			result = creditRatingDao.saveCreditRating(entity);
			LOGGER.info("handleCreditRating save result:" + result);
		}
		result = creditRatingDao.updateGroupIdByOrgid(entity);
		LOGGER.info("handleCreditRating entity:"+JSON.toJSONString(entity)+",count:"+count+",groupId:"+groupId+",update result:" + result);
	}
	
	private void handleInstRatingHist(Integer userId, Integer orgId, Integer version, Date today,
			CreditRatingParsedBasic basicCreditRating, Integer repeatFlag, Integer importFlag) {
		BondInstRatingHist instRatingHist = packageInstRatingHist(userId, orgId, basicCreditRating, version, today, repeatFlag, importFlag);
		
		BondInstRatingHist res = saveOrUpdateInstRatingHist(orgId, version, today, basicCreditRating, repeatFlag, importFlag,
				instRatingHist);
		if (res != null) {
			//pub creditrating, update rating in mongodb
			publisher.publishEvent(instRatingHist);
		}
	}

	private BondInstRatingHist saveOrUpdateInstRatingHist(Integer orgId, Integer version, Date today,
			CreditRatingParsedBasic basicCreditRating, Integer repeatFlag, Integer importFlag, BondInstRatingHist instRatingHist) {
		BondInstRatingHist resu = null;
		switch(importFlag.intValue()){
		case 0:
			//发行人入口导入，只关注发行人
			resu = rehandleIssuerHist(orgId, version, today, basicCreditRating, repeatFlag, instRatingHist, resu, importFlag);
			break;
		case 1:
			//债券入口导入
			resu = rehandleBondHist(orgId, version, today, basicCreditRating, repeatFlag, instRatingHist, resu, importFlag);
			break;
		}
		return resu;
	}

	private BondInstRatingHist rehandleBondHist(Integer orgId, Integer version, Date today,
			CreditRatingParsedBasic basicCreditRating, Integer repeatFlag, BondInstRatingHist instRatingHist,
			BondInstRatingHist resu, Integer importFlag) {
		switch(repeatFlag.intValue()){
		case 0:
			resu = instRatingHistDao.save(instRatingHist);
			LOGGER.info("[bond]repeatFlag == 0,saveOrUpdateInstRatingHist save:" + JSON.toJSONString(resu));
			break;
		case 1:
			//涉及到今天重复导入的数据更新
			if (basicCreditRating.getBondId().compareTo(0L) > 0) {
				//判断导入的是债券,其中含有主体的评级和投资建议
				BondInstRatingHist hist = findLastInstRatingHistByDate(orgId, version, basicCreditRating.getIssuerId(),basicCreditRating.getBondId(),today);
				if (null != hist) {
					//如果导入的该债券有今天的信评历史记录，update该条债券的今天的信评历史记录（只针对从导入过来的债券数据）
					instRatingHist.setId(hist.getId());
					instRatingHist.setRatingTime(today);
					resetOldRating(orgId, version, basicCreditRating, instRatingHist);
					resetOldInvestmentDescribe(orgId, version, basicCreditRating, instRatingHist);
					int updateRes = instRatingHistDao.updateLastRatingHistInvAdvByDate(instRatingHist);
					resu = instRatingHist;
					LOGGER.info("[bond]repeatFlag == 1,saveOrUpdateInstRatingHist update instRatingHist:" + JSON.toJSONString(instRatingHist)+",updateRes:"+updateRes);
				}else{
					//如果导入的该债券没有今天的信评历史记录，保存该债券的今天的信评历史记录
					resu = instRatingHistDao.save(instRatingHist);
					LOGGER.info("[bond]repeatFlag == 1,saveOrUpdateInstRatingHist save res:" + JSON.toJSONString(resu));
				}
			}else{
				resu = instRatingHist;
			}
			break;
		}
		return resu;
	}

	private void resetOldRating(Integer orgId, Integer version, CreditRatingParsedBasic basicCreditRating,
			BondInstRatingHist instRatingHist) {
		BondInstRatingHist oldhist = findInstRatingHistAfterRatingDate(orgId, version, basicCreditRating.getIssuerId(),null,instRatingHist.getRatingTime());
		if (null != oldhist) {
			instRatingHist.setOldRatingName(oldhist.getRatingName());
			instRatingHist.setOldRating(oldhist.getRating());
			instRatingHist.setOldRatingTime(oldhist.getRatingTime());
			instRatingHist.setOldRatingByName(userOrgInfoDao.getUserNameById(oldhist.getRatingBy()));
		}else{
			instRatingHist.setOldRatingName(null);
			instRatingHist.setOldRating(null);
			instRatingHist.setOldRatingTime(null);
			instRatingHist.setOldRatingByName(null);
		}
	}

	private void resetOldInvestmentDescribe(Integer orgId, Integer version, CreditRatingParsedBasic basicCreditRating,
			BondInstRatingHist instRatingHist) {
		BondInstRatingHist oldInvhist = findInstRatingHistAfterRatingDate(orgId, version, basicCreditRating.getIssuerId(),basicCreditRating.getBondId(),instRatingHist.getRatingTime());
		if (oldInvhist != null) {
			instRatingHist.setOldInvestmentDescribe(oldInvhist.getInvestmentDescribe());
			instRatingHist.setOldInvestmentDescribeTime(oldInvhist.getRatingTime());
			instRatingHist.setOldInvestmentDescribeByName(userOrgInfoDao.getUserNameById(oldInvhist.getRatingBy()));
		}else{
			instRatingHist.setOldInvestmentDescribe(null);
			instRatingHist.setOldInvestmentDescribeTime(null);
			instRatingHist.setOldInvestmentDescribeByName(null);
		}
	}

	private BondInstRatingHist rehandleIssuerHist(Integer orgId, Integer version, Date today,
			CreditRatingParsedBasic basicCreditRating, Integer repeatFlag, BondInstRatingHist instRatingHist,
			BondInstRatingHist resu, Integer importFlag) {
		switch(repeatFlag.intValue()){
		case 0:
			resu = instRatingHistDao.save(instRatingHist);
			LOGGER.info("[issuer]repeatFlag == 0,saveOrUpdateInstRatingHist save:" + JSON.toJSONString(resu));
			break;
		case 1:
			//判断导入的是主体
			BondInstRatingHist hist = findLastInstRatingHistByDate(orgId, version, basicCreditRating.getIssuerId(),0L,today);
			if (null != hist) {
				instRatingHist.setId(hist.getId());
				instRatingHist.setRatingTime(today);
				resetOldRating(orgId, version, basicCreditRating, instRatingHist);
				int updateRes = instRatingHistDao.updateLastRatingHistInvAdvByDate(instRatingHist);
				resu = instRatingHist;
				LOGGER.info("[issuer]repeatFlag == 1,saveOrUpdateInstRatingHist update instRatingHist:" + JSON.toJSONString(instRatingHist)+",updateRes:"+updateRes);
			}else{
				//如果导入的该债券没有今天的信评历史记录，保存该债券的今天的信评历史记录
				resu = instRatingHistDao.save(instRatingHist);
				LOGGER.info("[issuer]repeatFlag == 1,saveOrUpdateInstRatingHist save res:" + JSON.toJSONString(resu));
			}
			break;
		}
		return resu;
	}

	private BondInstRatingHist findTodayInstRatingHistByType(Integer orgId, Integer version, Date today,
			CreditRatingParsedBasic basicCreditRating) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		map.put("instId", orgId);
		map.put("version", version);
		map.put("createDate", SafeUtils.convertDateToString(today, SafeUtils.DATE_FORMAT));
		map.put("comUniCode", basicCreditRating.getIssuerId());
		map.put("bondUniCode", SafeUtils.getLong(basicCreditRating.getBondId()));
		map.put("type", 1);
		return instRatingHistDao.queryLastInstRatingHistByType(map);
	}

	private int updateLastRatingHistRatingByDate(Date today, BondInstRatingHist instRatingHist, Integer version) {
		Map<String, Object> map = new HashMap<>();
		map.put("ratingTime", instRatingHist.getRatingTime());
		map.put("rating", instRatingHist.getRating());
		map.put("ratingName", instRatingHist.getRatingName());
		map.put("ratingDescribe", instRatingHist.getRatingDescribe());
		map.put("ratingBy", instRatingHist.getRatingBy());
		map.put("ratingDiff", instRatingHist.getRatingDiff());
		map.put("instId", instRatingHist.getInstId());
		map.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		map.put("comUniCode", instRatingHist.getComUniCode());
		map.put("bondUniCode", SafeUtils.getLong(instRatingHist.getBondUniCode()));
		map.put("createDate", SafeUtils.convertDateToString(today, SafeUtils.DATE_FORMAT));
		map.put("version", version);
		int updateRatingHistRes = instRatingHistDao.updateLastRatingHistRatingByDate(map);
		return updateRatingHistRes;
	}
	
	private void resetInstRatingDiff(Integer orgId, Integer version, BondInstRatingHist instRatingHist,
			List<BondInstRatingHist> resList, BondInstRatingHist res, BondInstCode firstInstCode) {
		if (resList.size() > 1) {
			//resetInstRatingDiff
			BondInstRatingHist secondRatingHist = resList.get(1);
			BondInstCode secondInstCode = getRatingBondInstCode(orgId, version, secondRatingHist.getRating());
			if (null != firstInstCode && null != secondInstCode) {
				int diff = secondInstCode.getSort() - firstInstCode.getSort();
				instRatingHist.setRatingDiff(diff);
			}
		}else{
			instRatingHist.setRatingDiff(res.getRatingDiff());
		}
	}
	
	private List<BondInstRatingHist> findLastTwoInstRatingHist(Integer orgId, Integer version, CreditRatingParsedBasic basicCreditRating) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		map.put("instId", orgId);
		map.put("version", version);
		map.put("comUniCode", basicCreditRating.getIssuerId());
//		map.put("bondUniCode", basicCreditRating.getBondId());
		return instRatingHistDao.queryLastTwoInstRatingHistByDate(map);
	}
	
	private BondInstRatingHist findLastInstRatingHistByDate(Integer orgId,Integer version,Long comUniCode,Long bondUniCode,Date today) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		map.put("instId", orgId);
		map.put("version", version);
		map.put("createDate", SafeUtils.convertDateToString(today, SafeUtils.DATE_FORMAT));
		map.put("comUniCode", comUniCode);
		map.put("bondUniCode", bondUniCode);
		map.put("type", 1);
		List<BondInstRatingHist> list = instRatingHistDao.queryLastTwoInstRatingHistByDate(map);
		return (list != null && !list.isEmpty()) ? list.get(0): null;
	}
	
	
	private BondInstRatingHist findInstRatingHistAfterRatingDate(Integer orgId,Integer version,Long comUniCode,Long bondUniCode,Date ratingTime) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		map.put("instId", orgId);
		map.put("version", version);
		map.put("ratingTime", ratingTime);
		map.put("comUniCode", comUniCode);
		map.put("bondUniCode", bondUniCode);
		map.put("version", version);
		return instRatingHistDao.queryInstRatingHistAfterRatingDate(map);
	}
	
	private BondInstRatingHist packageInstRatingHist(Integer userId, Integer orgId, CreditRatingParsedBasic basicCreditRating,
			Integer version,Date today,Integer repeatFlag, Integer importFlag) {
		BondInstRatingHist instRatingHist = new BondInstRatingHist();
		if (importFlag == 0) {
			instRatingHist.setBondUniCode(0L);
		}else{
			instRatingHist.setBondChiName(basicCreditRating.getBondName());
			instRatingHist.setBondUniCode(basicCreditRating.getBondId());
			instRatingHist.setBondCode(basicCreditRating.getBondCode());
			BondInstRatingHist lastInstInadv = findLastInstRatingHist(basicCreditRating.getBondId(), orgId, version, basicCreditRating.getIssuerId());
			if (lastInstInadv != null ) {
				instRatingHist.setOldInvestmentDescribe(lastInstInadv.getInvestmentDescribe());
				instRatingHist.setOldInvestmentDescribeTime(lastInstInadv.getRatingTime());
				instRatingHist.setOldInvestmentDescribeByName(userOrgInfoDao.getUserNameById(lastInstInadv.getRatingBy()));
			}
		}
		instRatingHist.setComChiName(basicCreditRating.getIssuer());
		instRatingHist.setComUniCode(basicCreditRating.getIssuerId());
		instRatingHist.setCreateBy(userId);
		instRatingHist.setCreateTime(today);
		instRatingHist.setUpdateBy(userId);
		instRatingHist.setUpdateTime(today);
		instRatingHist.setInstId(orgId);
		instRatingHist.setStatus(InstConstants.INST_RATING_HIST_STATUS_3);
		instRatingHist.setVersion(version);
		instRatingHist.setRatingBy(userId);
		instRatingHist.setCheckBy(userId);
		instRatingHist.setCheckTime(today);
		BondInstRatingHist lastInstRatingHist = findLastInstRatingHist(null, orgId, version, basicCreditRating.getIssuerId());
		if (null != lastInstRatingHist) {
			instRatingHist.setOldRatingName(lastInstRatingHist.getRatingName());
			instRatingHist.setOldRating(lastInstRatingHist.getRating());
			instRatingHist.setOldRatingTime(lastInstRatingHist.getRatingTime());
			instRatingHist.setOldRatingByName(userOrgInfoDao.getUserNameById(lastInstRatingHist.getRatingBy()));
		}
		// 生成内部评级级记录
		if (null != basicCreditRating.getSelectedRating()) {
			int selectedRatingId = SafeUtils.getInt(basicCreditRating.getSelectedRating().getId());
			BondInstCode selectedinstCode  = basicCreditRating.getSelectedRating();
			setInstRatingDiff(orgId, basicCreditRating, version, instRatingHist, selectedinstCode, lastInstRatingHist);
			instRatingHist.setRating(selectedRatingId);
			instRatingHist.setRatingName(basicCreditRating.getSelectedRating().getName());
			instRatingHist.setRatingTime(today);
		}
		// 生成投资建议记录，选定投资建议时候赋值
		if (null != basicCreditRating.getSelectedInvestmentAdvice()) {
			int selectedInvestmentAdvId = SafeUtils.getInt(basicCreditRating.getSelectedInvestmentAdvice().getId());
			instRatingHist.setInvestmentAdvice(selectedInvestmentAdvId);
			instRatingHist.setInvestmentDescribe(basicCreditRating.getSelectedInvestmentAdvice().getName());
		}
		
		instRatingHist.setRatingDescribe(SafeUtils.getString(basicCreditRating.getCurrentRatingDes()));
		instRatingHist.setInvestmentAdviceDesdetail(SafeUtils.getString(basicCreditRating.getInvestmentAdviceDesDetail()));
		
		LOGGER.info("packageInstRatingHist instRatingHist:"+JSON.toJSONString(instRatingHist));
		return instRatingHist;
	}

	private Integer findHistRatingsort(Integer orgId, CreditRatingParsedBasic basicCreditRating, Integer version) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		map.put("instId", orgId);
		map.put("version", version);
		map.put("comUniCode", basicCreditRating.getIssuerId());
		return instRatingHistDao.queryRatingSortByHistdate(map);
	}

	public void setInstRatingDiff(Integer orgId, CreditRatingParsedBasic basicCreditRating, Integer version,
			BondInstRatingHist instRatingHist, BondInstCode selectedinstCode) {
		//Rating是针对主体的
		BondInstRatingHist lastInstRatingHist = findLastInstRatingHist(null, orgId, version, basicCreditRating.getIssuerId());
		if (null != lastInstRatingHist && SafeUtils.getInt(lastInstRatingHist.getRating()) > 0) {
			BondInstCode lastInstCode = getRatingBondInstCode(orgId, version, SafeUtils.getInt(lastInstRatingHist.getRating()));
			if (null != lastInstCode) {
				int diff = lastInstCode.getSort() - selectedinstCode.getSort();
				instRatingHist.setRatingDiff(diff);
			}
		}
	}
	
	private void setInstRatingDiff(Integer orgId, CreditRatingParsedBasic basicCreditRating, Integer version,
			BondInstRatingHist instRatingHist, BondInstCode selectedinstCode, BondInstRatingHist lastInstRatingHist) {
		//Rating是针对主体的
		if (null != lastInstRatingHist && SafeUtils.getInt(lastInstRatingHist.getRating()) > 0) {
			BondInstCode lastInstCode = getRatingBondInstCode(orgId, version, SafeUtils.getInt(lastInstRatingHist.getRating()));
			if (null != lastInstCode) {
				int diff = lastInstCode.getSort() - selectedinstCode.getSort();
				instRatingHist.setRatingDiff(diff);
			}
		}
	}

	public BondInstCode getRatingBondInstCode(Integer orgId, Integer version, int ratingId) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", ratingId);
		map.put("type", InstConstants.INST_CODE_TYPE_RATING);
		map.put("orgId", orgId);
		map.put("version", version);
		return instCodeDao.queryByIdAndTypeAndOrgId(map);
	}

	private BondCreditRating packageCeditRating(Integer userId, Integer orgId, CreditRatingParsedBasic basicCreditRating,
			Long groupId) {
		BondCreditRating creditRating = new BondCreditRating();
		creditRating.setBondCode(basicCreditRating.getBondCode());
		creditRating.setBondShortname(basicCreditRating.getBondName());
		creditRating.setBondUniCode(basicCreditRating.getBondId());
		creditRating.setCreateTime(new Date());
		creditRating.setGroupId(groupId);
		creditRating.setIssuerId(basicCreditRating.getIssuerId());
		creditRating.setOrgId(orgId);
		creditRating.setUserId(userId);
		BondInstCode investmentAdv = basicCreditRating.getSelectedInvestmentAdvice();
		if (null != investmentAdv) {
			creditRating.setInstinvadvId(SafeUtils.getLong(investmentAdv.getId()));
		}else{
			creditRating.setInstinvadvId(0L);
		}
		BondInstCode rating  = basicCreditRating.getSelectedRating();
		if (null != rating) {
			creditRating.setInstratingId(SafeUtils.getLong(rating.getId()));
		} else {
			creditRating.setInstratingId(0L);
		}
		return creditRating;
	}

	private Long getGroupIdByUsedFlag(Integer usedFlag, Long currentGroupId,
			CreditRatingParsedBasic basicCreditRating) {
		// 是否忽略属于用于其他信评组, 1=是，所选的数据进入已经选择的信评组，0=否，所有的数据强制导入到目前的信评组
		if (usedFlag.intValue() == 0) {
			return currentGroupId;
		} else {
			return basicCreditRating.getGroupId() == null ? currentGroupId : basicCreditRating.getGroupId();
		}
	}

	public String downloadExcel(Integer userId) {
		LOGGER.info("downloadExcel userId:" + userId);
		return fileHandleService.getFilepath();
	}

	public CreditRatingParsed parseItem(Integer userId, Long bondId, Long issuerId) throws Exception {
		LOGGER.info("parseItem userId:" + userId + ",bondId:" + bondId + ",issuerId:" + issuerId);
		CreditRatingParsed parsedCreditRating = new CreditRatingParsed();
		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		
		Integer version = instCodeDao.queryVersionByOrgId(orgId);
		if (null == version) {
			throw new Exception("评级数据版本不存在");
		}
		
		BondInstRatingHist instInvadvHist = findLastInstRatingHist(bondId, orgId, version, issuerId);
		BondInstRatingHist instRatingHist = findLastInstRatingHist(null, orgId, version, issuerId);
		if (null != bondId && bondId > 0) {
			BondBasicInfoDoc bondInfo = getBondInfoByBondId(bondId);
			parsedCreditRating.setBondCode(bondInfo.getCode());
			parsedCreditRating.setBondId(bondId);
			parsedCreditRating.setBondName(bondInfo.getShortName());
			parsedCreditRating.setIssuer(bondInfo.getIssuer());
			parsedCreditRating.setIssuerId(bondInfo.getIssuerId());
			
			// 获取债券的最新投资建议
			if (null != instInvadvHist) {
				parsedCreditRating.setInvestmentAdvice(SafeUtils.getString(instInvadvHist.getInvestmentDescribe()));
				parsedCreditRating.setInvestmentAdviceId(SafeUtils.getInteger(instInvadvHist.getInvestmentAdvice()));
//				parsedCreditRating.setInvestmentAdviceDesDetail(SafeUtils.getString(instInvadvHist.getInvestmentAdviceDesdetail()));
			}
		}
		BondComInfoDoc comInfo = getComInfoByIssuerId(issuerId);
		parsedCreditRating.setIssuerId(issuerId);
		parsedCreditRating.setIssuer(comInfo.getComChiName());
		
		parsedCreditRating.setAllInvestmentAdvice(getAllInvestmentAdvice(orgId, version));
		parsedCreditRating.setAllRating(getAllRating(orgId,version));
		// 获取主体的最新内部评级
		if (null != instRatingHist) {
			parsedCreditRating.setCurrentRating(SafeUtils.getString(instRatingHist.getRatingName()));
			parsedCreditRating.setCurrentRatingId(SafeUtils.getInteger(instRatingHist.getRating()));
//			parsedCreditRating.setCurrentRatingDes(SafeUtils.getString(instRatingHist.getRatingDescribe()));
		}
		
		Long groupId = getGroupIdByOrgIdAndIssuerId(issuerId, orgId);
		BondCreditRatingGroup credRatgroup = creditRatingGroupDao.queryById(groupId);
		parsedCreditRating.setGroupId(groupId);
		parsedCreditRating.setGroupName(credRatgroup == null ? null : credRatgroup.getGroupName());
		parsedCreditRating.setCurrGroup(credRatgroup);

		return parsedCreditRating;
	}

	private BondInstRatingHist findLastInstRatingHist(Long bondId, Integer orgId, Integer version,
			Long issuerId) {
		Map<String, Object> param = new HashMap<>();
		param.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		param.put("instId", orgId);
		param.put("comUniCode", issuerId);
		param.put("bondUniCode", bondId);
		param.put("version", version);

		BondInstRatingHist instRatingHist = instRatingHistDao.queryLastInstRatingHist(param);
		return instRatingHist;
	}

	private BondBasicInfoDoc getBondInfoByBondId(Long bondId) {
		Query bondquery = new Query();
		bondquery.addCriteria(Criteria.where("bondUniCode").is(bondId));
		bondquery.fields().include("code").include("shortName").include("issuerId").include("issuer");
		BondBasicInfoDoc bondInfo = mongoOperations.findOne(bondquery, BondBasicInfoDoc.class);
		return bondInfo;
	}

	private BondComInfoDoc getComInfoByIssuerId(Long issuerId) {
		Query comquery = new Query();
		comquery.addCriteria(Criteria.where("comUniCode").is(issuerId));
		comquery.fields().include("comChiName");
		BondComInfoDoc comInfo = mongoOperations.findOne(comquery, BondComInfoDoc.class);
		return comInfo;
	}

	private Long getGroupIdByOrgIdAndIssuerId(Long issuerId, Integer orgId) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgId", orgId);
		param.put("issuerId", issuerId);
		Long groupId = creditRatingDao.queryGroupIdByOrgIdAndIssuerId(param);
		return groupId;
	}
	
	//查找信评组
	public BondCreditRatingGroup findCreditRatingGroup(Integer userId, Long issuerId){
		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		Map<String, Object> param = new HashMap<>();
		param.put("orgId", orgId);
		param.put("issuerId", issuerId);
		Long groupId = creditRatingDao.queryGroupIdByOrgIdAndIssuerId(param);
		return groupId != null ? creditRatingGroupDao.queryById(groupId) : null;
	}

	public String uploadExcelOss(String tokenCode, MultipartFile sourceFile) throws Exception {
		return fileHandleService.uploadFileOss(tokenCode, sourceFile.getBytes(), sourceFile.getOriginalFilename(), sourceFile.getContentType());
	}

	public String changeGroup(Integer userId, CreditRatingGroupchangeReq req) {
		if (null == req || null == req.getDataList() || req.getDataList().isEmpty()) {
			return "数据不合法";
		}
		
		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		req.getDataList().stream().forEach(data ->{
			if (data.getGroupId() > 0 && data.getIssuerId() > 0) {
				changeGroupData(userId, orgId, data);
			}
		});
		
		return "done";
	}

	@Transactional
	private void changeGroupData(Integer userId, Integer orgId, CreditRatingBasic data) {
		Map<String, Object> param = new HashMap<>();
		param.put("orgId", orgId);
		param.put("userId", userId);
		param.put("groupId", data.getGroupId());
		param.put("issuerId", data.getIssuerId());
		int result = creditRatingDao.updateGroup(param);
		LOGGER.info("changeGroupData issuerId:"+data.getIssuerId()+",groupId:"+data.getGroupId()+",result:"+result);
	}

	public void exportIssuers(Integer userId, Long groupId, Integer groupedFlag, Integer ratingId, Integer induId,
			String query, HttpServletResponse response) throws Exception {
		LOGGER.info("exportIssuers groupId:" + groupId + ",userId:" + userId + ",groupedFlag:" + groupedFlag
				+ ",ratingId:" + ratingId + ",induId:" + induId);
		List<CreditRatingIssuerVo> results = getExportIssuersData(userId, groupId, groupedFlag, ratingId, induId, query);
		handleExportIssuers(response, results);
	}

	private void handleExportIssuers(HttpServletResponse response, List<CreditRatingIssuerVo> results)
			throws Exception{
		OutputStream out = response.getOutputStream();
		setExcelResponse(response);
		//Create the workbook
		HSSFWorkbook  workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("导出数据");
		HSSFCellStyle headerStyle = CreditRatingIssuerExcelUtil.setHeaderStyle(workbook);
		setHeaderData(sheet, headerStyle, InstConstants.CREDITRATING_ROWSNAME);
		HSSFCellStyle cellStyle = CreditRatingIssuerExcelUtil.setCellStyle(workbook);
		//Create the sheet
		setIssuerExcelColumnData(results, sheet, cellStyle);

        workbook.write(out);
		out.flush();
		out.close();
	}

	private void setExcelResponse(HttpServletResponse response) throws UnsupportedEncodingException {
		String fileName = java.net.URLEncoder.encode(SafeUtils.getCurrDateFmtStr(), "UTF-8") + ".xls";
		String headStr = "attachment; filename=\"" + fileName + "\"";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", headStr);
		response.setCharacterEncoding("UTF-8");
	}

	private void setIssuerExcelColumnData(List<CreditRatingIssuerVo> results, HSSFSheet sheet, HSSFCellStyle cellStyle) {
		Map<Integer, Integer> columnWidthMap = new HashMap<>();
		for (int i = 0; i < InstConstants.CREDITRATING_ROWSNAME.length; i++) {
			int length = InstConstants.CREDITRATING_ROWSNAME[i].getBytes().length;
			columnWidthMap.put(i, length);
		}
		if (null != results && !results.isEmpty()) {
			for (int k = 0; k < results.size(); k++) {
				CreditRatingIssuerVo issuerVo = results.get(k);
				setIssuerRowData(sheet, (k+1), issuerVo, cellStyle, columnWidthMap);
			}
		}
		for (int m = 0; m < InstConstants.CREDITRATING_ROWSNAME.length; m++) {
			if (m == 3) {
				int colWidth = InstConstants.CREDITRATING_ROWSNAME[2].getBytes().length + 10;
				sheet.setColumnWidth(m, (colWidth + 4) * 256);
			}else{
				int columnWidth = columnWidthMap.get(m);
				sheet.setColumnWidth(m, (columnWidth + 4) * 256);
			}
		}
	}

	private void setHeaderData(HSSFSheet sheet, HSSFCellStyle headerStyle, String[] headerNames) {
		//Create Wor Headers
		HSSFRow header = sheet.createRow(0);
		for (int j = 0; j < headerNames.length; j++) {
			String headername = headerNames[j];
			HSSFCell cell =  header.createCell(j);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(new HSSFRichTextString(headername));
		}
	}

	private void setIssuerRowData(HSSFSheet sheet, int num, CreditRatingIssuerVo issuerVo, HSSFCellStyle cellStyle, Map<Integer, Integer> columnWidthMap) {
		HSSFRow dataRow = sheet.createRow(num);
		for (int j = 0; j < InstConstants.CREDITRATING_ROWSNAME.length; j++) {
			int columnWidth = columnWidthMap.get(j);
			HSSFCell hsscell = dataRow.createCell(j);
			hsscell.setCellType(Cell.CELL_TYPE_STRING);
			switch(j){
			case 0:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(issuerVo.getIssuerName())));
				break;
			case 1:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(issuerVo.getInduName())));
				break;
			case 2:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(issuerVo.getCurrentRating())+" "+SafeUtils.getString(issuerVo.getCurrentRatingDate())));
				break;
			case 3:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(issuerVo.getCurrentRatingDesc())));
				break;
			case 4:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(issuerVo.getPd())+" "+SafeUtils.getString(issuerVo.getPdTime())));
				break;
			case 5:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(issuerVo.getIssCredLevel())));
				break;
			case 6:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(issuerVo.getRateProsPar())));
				break;
			case 7:
				hsscell.setCellValue(new HSSFRichTextString(issuerVo.getCurrGroup() == null ? "":SafeUtils.getString(issuerVo.getCurrGroup().getGroupName())));
				break;
			}
			hsscell.setCellStyle(cellStyle);
			
			int length = hsscell.getStringCellValue().getBytes().length;
			if (length > columnWidth) {
				columnWidth = length;
				columnWidthMap.put(j, columnWidth);
			}
		}
	}

	private List<CreditRatingIssuerVo> getExportIssuersData(Integer userId, Long groupId, Integer groupedFlag, Integer ratingId, Integer induId,
			String query) {
		List<CreditRatingIssuerVo> results = new ArrayList<>();

		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		Integer version = instCodeDao.queryVersionByOrgId(orgId);
		List<BondCreditRatingGroup> allGroups = creditRatingGroupDao.queryByOrgId(orgId);
		allGroups.stream().forEachOrdered(group -> {
			if(group.getGroupType().intValue() == 0){
				group.setGroupName(InstConstants.NOTJOIN_GROUP);
			}
		});
		BondCreditRatingGroup currGroup = creditRatingGroupDao.queryById(groupId);
		if (null != currGroup && currGroup.getGroupType().intValue() == 0) {
			currGroup.setGroupName(InstConstants.NOTJOIN_GROUP);
		}
		Map<String, Object> param = setCommonIssuerParam(userId, groupId, groupedFlag, ratingId, induId, query, orgId,
				currGroup);
		
		results = creditRatingDao.queryIssuers(param);
		
		List<Long> issuerIdList = results.stream().map(CreditRatingIssuerVo::getIssuerId).collect(Collectors.toList());
		Map<Long, BondInstRatingHist> issuerHistMap = getIssuerHistMap(issuerIdList, orgId, version);
		
		Query comquery = new Query();
		comquery.addCriteria(Criteria.where("comUniCode").in(issuerIdList));
		comquery.fields().include("comChiName").include("worstPd").include("worstPdNum").include("worstPdTime").include("issCredLevel").include("pdDiff").include("pdTime").include("pd")
				.include("rateProsPar").include("comUniCode");
		List<BondComInfoDoc> comInfoList = mongoOperations.find(comquery, BondComInfoDoc.class);
		Map<Long, BondComInfoDoc> comInfoMap = comInfoList.stream().collect(Collectors.toMap(BondComInfoDoc::getComUniCode, (p) -> p));
		
		handleIssuerResults(results, orgId, allGroups, currGroup, issuerHistMap, comInfoMap);
		
		return results;
	}

	private void handleIssuerResults(List<CreditRatingIssuerVo> results, Integer orgId,
			List<BondCreditRatingGroup> allGroups, BondCreditRatingGroup currGroup,
			Map<Long, BondInstRatingHist> issuerHistMap, Map<Long, BondComInfoDoc> comInfoMap) {
		if (null != results && !results.isEmpty()) {
			// groupId 区分 全部和非全部 的组,信评组类型, 默认0-全部，默认1-禁投组，9-信评分组
			Map<String, Object> grpparam = new HashMap<>();
			grpparam.put("orgId", orgId);
			for (int i = 0; i < results.size(); i++) {
				CreditRatingIssuerVo creditRatingIssuer = results.get(i);
				Long issuerId = creditRatingIssuer.getIssuerId();
				setComInfo(issuerId, comInfoMap, creditRatingIssuer);
				setRatingData(creditRatingIssuer, issuerId, issuerHistMap);
				setGroupData(allGroups, currGroup, grpparam, creditRatingIssuer, issuerId);
			}
		}
	}

	public void exportBonds(Integer userId, Long groupId, Integer adviceId, Integer ratingId, Integer induId,
			String query, HttpServletResponse response) throws Exception {
		List<CreditRatingBondVo> results = getExportBondsData(userId, groupId, adviceId, ratingId, induId, query);
		handleExportBonds(response, results);
	}

	private void handleExportBonds(HttpServletResponse response, List<CreditRatingBondVo> results)
			throws Exception{
		OutputStream out = response.getOutputStream();
		setExcelResponse(response);
		//Create the workbook
		HSSFWorkbook  workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("导出数据");
		HSSFCellStyle headerStyle = CreditRatingIssuerExcelUtil.setHeaderStyle(workbook);
		setHeaderData(sheet, headerStyle, InstConstants.CREDITRATING_BOND_ROWSNAME);
		HSSFCellStyle cellStyle = CreditRatingIssuerExcelUtil.setCellStyle(workbook);
		//Create the sheet
		setBondExcelColumnData(results, sheet, cellStyle);

        workbook.write(out);
		out.flush();
		out.close();
	}

	private void setBondExcelColumnData(List<CreditRatingBondVo> results, HSSFSheet sheet, HSSFCellStyle cellStyle) {
		Map<Integer, Integer> columnWidthMap = new HashMap<>();
		for (int i = 0; i < InstConstants.CREDITRATING_BOND_ROWSNAME.length; i++) {
			int length = InstConstants.CREDITRATING_BOND_ROWSNAME[i].getBytes().length;
			columnWidthMap.put(i, length);
		}
		if (null != results && !results.isEmpty()) {
			for (int k = 0; k < results.size(); k++) {
				CreditRatingBondVo bondVo = results.get(k);
				setBondRowData(sheet, (k+1), bondVo, cellStyle, columnWidthMap);
			}
		}
		for (int m = 0; m < InstConstants.CREDITRATING_BOND_ROWSNAME.length; m++) {
			if (m == 3 || m == 8) {
				int colWidth = InstConstants.CREDITRATING_ROWSNAME[2].getBytes().length + 10;
				sheet.setColumnWidth(m, (colWidth + 4) * 256);
			}else{
				int columnWidth = columnWidthMap.get(m);
				sheet.setColumnWidth(m, (columnWidth + 4) * 256);
			}
		}
	}

	private void setBondRowData(HSSFSheet sheet, int num, CreditRatingBondVo bondVo, HSSFCellStyle cellStyle,
			Map<Integer, Integer> columnWidthMap) {
		HSSFRow dataRow = sheet.createRow(num);
		for (int j = 0; j < InstConstants.CREDITRATING_BOND_ROWSNAME.length; j++) {
			int columnWidth = columnWidthMap.get(j);
			HSSFCell hsscell = dataRow.createCell(j);
			hsscell.setCellType(Cell.CELL_TYPE_STRING);
			switch(j){
			case 0:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getBondName())));
				break;
			case 1:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getBondCode())));
				break;
			case 2:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getInvestmentAdvice())+" "+SafeUtils.getString(bondVo.getInvestmentAdviceDate())));
				break;
			case 3:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getInvestmentAdviceDesc())));
				break;
			case 4:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getImpliedRating())));
				break;
			case 5:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getIssuer())));
				break;
			case 6:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getInduName())));
				break;
			case 7:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getCurrentRating())+" "+SafeUtils.getString(bondVo.getCurrentRatingDate())));
				break;
			case 8:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getCurrentRatingDesc())));
				break;
			case 9:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getPd())+" "+SafeUtils.getString(bondVo.getPdTime())));
				break;
			case 10:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getIssBondRating())));
				break;
			case 11:
				hsscell.setCellValue(new HSSFRichTextString(SafeUtils.getString(bondVo.getIssRatePros())));
				break;
			}
			hsscell.setCellStyle(cellStyle);
			
			int length = hsscell.getStringCellValue().getBytes().length;
			if (length > columnWidth) {
				columnWidth = length;
				columnWidthMap.put(j, columnWidth);
			}
		}
	}

	private List<CreditRatingBondVo> getExportBondsData(Integer userId, Long groupId, Integer adviceId, Integer ratingId, Integer induId,
			String query) {
		List<CreditRatingBondVo> results = new ArrayList<>();
		Integer orgId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		Integer version = instCodeDao.queryVersionByOrgId(orgId);
		BondCreditRatingGroup currGroup = creditRatingGroupDao.queryById(groupId);
		
		Map<String, Object> param = setCommonBondParam(userId, groupId, adviceId, ratingId, induId, query, orgId,
				currGroup);

		results = creditRatingDao.queryBonds(param);
		
		List<Long> bondIdList = results.stream().map(CreditRatingBondVo::getBondId).collect(Collectors.toList());

		Query bondquery = new Query();
		bondquery.addCriteria(Criteria.where("bondUniCode").in(bondIdList));
		bondquery.fields().include("impliedRating").include("issuer").include("issBondRating").include("pd").include("issuerId")
				.include("issRatePros");
		List<BondBasicInfoDoc> dataList = mongoOperations.find(bondquery, BondBasicInfoDoc.class);
		Map<Long, BondBasicInfoDoc> bondInfoMap = dataList.stream().collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, (p) -> p));
		
		Set<Long> issuerIdSet = dataList.stream().map(BondBasicInfoDoc::getIssuerId).collect(Collectors.toCollection(HashSet::new));
		
		List<Long> issuerIds = issuerIdSet.stream().collect(Collectors.toList());
		Map<Long, BondInstRatingHist> bondHistMap = getBondHistMap(bondIdList,orgId,version);
		Map<Long, BondInstRatingHist> issuerHistMap = getIssuerHistMap(issuerIds, orgId, version);
		
		Query comquery = new Query();
		comquery.addCriteria(Criteria.where("comUniCode").in(issuerIdSet));
		comquery.fields().include("comUniCode").include("comChiName").include("pdDiff").include("pd").include("pdTime").include("rateProsPar");
		
		Map<Long, BondComInfoDoc> comMap = mongoOperations.find(comquery, BondComInfoDoc.class).stream().collect(Collectors.toMap(BondComInfoDoc::getComUniCode, (p) -> p));
		handleBondResults(userId, results, bondInfoMap, bondHistMap, issuerHistMap, comMap);
		
		return results;
	}

	public String findAnalysisText(Integer userId, Long bondId, Integer requestId, Integer textFlag) {
		String analysisText = "";
		if (requestId <= 0) {
			return "";
		}
		
		Map<String, Object> param = commonQueryParam(userId, bondId, null, requestId, textFlag);
		BondInstRatingHist instRatingHist = instRatingHistDao.queryInstRatingHistAnalysisText(param);
		if (textFlag.intValue() == 1) {
			if (null != instRatingHist) {
				analysisText = SafeUtils.getString(instRatingHist.getInvestmentAdviceDesdetail());
			}
		}else{
			Query bondquery = new Query();
			bondquery.addCriteria(Criteria.where("bondUniCode").is(bondId));
			bondquery.fields().include("issuerId");
			BondBasicInfoDoc basicInfoDoc = mongoOperations.findOne(bondquery, BondBasicInfoDoc.class);
			if (null != basicInfoDoc && null != basicInfoDoc.getIssuerId()) {
				analysisText = getIssuerAnalysisText(userId, basicInfoDoc.getIssuerId(), requestId);
			}
		}
		
		return analysisText;
	}

	private Map<String, Object> commonQueryParam(Integer userId, Long bondId, Long issuerId, Integer requestId, Integer textFlag) {
		Integer instId = userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
		Integer version = instCodeDao.queryVersionByOrgId(instId);
		Map<String, Object> param = new HashMap<>();
		param.put("instId", instId);
		param.put("version", version);
		param.put("status", InstConstants.INST_RATING_HIST_STATUS_3);
		if (null != bondId) {
			param.put("bondId", bondId);
		}
		if (null != issuerId) {
			param.put("issuerId", issuerId);
		}
		if (textFlag.intValue() == 1) {
			param.put("adviceId", requestId);
		}else{
			param.put("ratingId", requestId);
		}
		return param;
	}

	public String findIssuerAnalysisText(Integer userId, Long issuerId, Integer requestId) {
		if (issuerId <= 0) {
			return "";
		}
		
		return getIssuerAnalysisText(userId, issuerId, requestId);
	}

	private String getIssuerAnalysisText(Integer userId, Long issuerId, Integer requestId) {
		String analysisText = "";
		Map<String, Object> param = commonQueryParam(userId, null, issuerId, requestId, 0);
		BondInstRatingHist instRatingHist = instRatingHistDao.queryInstRatingHistAnalysisText(param);
		if (null != instRatingHist) {
			analysisText = SafeUtils.getString(instRatingHist.getRatingDescribe());
		}
		return analysisText;
	}

	@Transactional
	public String updateIssuersGroup() {
		StringBuffer upres = new StringBuffer();
		List<Map<String, Object>> result = creditRatingDao.querySameIssuerGroup();
		Map<String, String> updateData = new HashMap<>();
		if (!result.isEmpty()) {
			result.forEach(data ->{
				Set<String> groupId = new HashSet<>();
				String issuerId = "";
				String orgId = "";
				for (Map.Entry<String, Object> entry : data.entrySet()) { 
					if ("groups".equals(entry.getKey())) {
						String groups = SafeUtils.getString(entry.getValue());
						String[] gid = groups.split(",");
						for(int i=0;i<gid.length;i++){
							groupId.add(gid[i]);
						}
					}
					if ("issuerId".equals(entry.getKey())) {
						issuerId = SafeUtils.getString(entry.getValue());
					}
					if ("orgId".equals(entry.getKey())) {
						orgId = SafeUtils.getString(entry.getValue());
					}
				}
				if (!groupId.isEmpty() && groupId.size() > 1 && !StringUtils.isBlank(issuerId) && !StringUtils.isBlank(orgId)) {
					updateData.put(issuerId, orgId);
				}
				groupId.clear();
			});
		}
		LOGGER.info("updateData:"+JSON.toJSONString(updateData));
		if (!updateData.isEmpty()) {
			for (String key : updateData.keySet()) {  
				String value = updateData.get(key);  
				BondCreditRating res = queryIssuerGroupByMap(key, value);
				if (null != res) {
					Map<String, Object> param = new HashMap<>();
					param.put("groupId", res.getGroupId());
					param.put("issuerId", key);
					param.put("orgId", value);
					int re = creditRatingDao.updateIssuerGroup(param);
					upres.append(key+"--"+value).append("||re="+re).append(",");
				}
			}
		}
		
		return upres.toString();
	}

	private BondCreditRating queryIssuerGroupByMap(String key, String value) {
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("issuerId", key);
		mapParam.put("orgId", value);
		BondCreditRating res = creditRatingDao.queryIssuerGroup(mapParam);
		return res;
	}
	
	@Cacheable(cacheNames="issuerGroupInfoCache", key="#issuerId + '_' + #orgId")
	public IssuerGroupInfoVo findIssuerGroupInfo(Long issuerId, Long orgId){
		Map<String, Object> param = new HashMap<>();
		param.put("issuerId", issuerId);
		param.put("orgId", orgId);
		IssuerGroupInfoVo vo = creditRatingDao.queryIssuerGroupInfo(param);
		System.out.println(JSON.toJSON(vo));
		return creditRatingDao.queryIssuerGroupInfo(param);
	}

	//map, key 存放issuerId，value 存放orgId
	public List<IssuerGroupInfoVo> findIssuerOneGroupInfo(Map<Long, Long> map){
		List<IssuerGroupInfoVo> list = new ArrayList<>();
		if (!map.isEmpty()) {
			map.forEach((k,v) ->{
				list.add(this.findIssuerGroupInfo(k,v));
			});
		}
		return list;
	}
	
}
