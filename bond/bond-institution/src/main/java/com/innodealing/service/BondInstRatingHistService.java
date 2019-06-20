package com.innodealing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.innodealing.consts.InstConstants;
import com.innodealing.dao.BondCreditRatingDao;
import com.innodealing.dao.BondInstCodeDao;
import com.innodealing.dao.BondInstRatingFileDao;
import com.innodealing.dao.BondInstRatingHistDao;
import com.innodealing.dao.UserOrgInfoDao;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mysql.BondInstCode;
import com.innodealing.model.mysql.BondInstRatingFile;
import com.innodealing.model.mysql.BondInstRatingHist;
import com.innodealing.model.socketio.ToRoomsSocketIoMsg;
import com.innodealing.model.vo.CreditRatingParsedBasic;
import com.innodealing.rabbitmq.MqSenderService;
import com.innodealing.util.RedisUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import com.mongodb.WriteResult;

@Service
public class BondInstRatingHistService {

	private final static Logger LOGGER = LoggerFactory.getLogger(BondInstRatingHistService.class);

	private @Resource MongoTemplate bondMongo;

	private @Autowired BondInstRatingHistDao bondInstRatingHistDao;

	private @Autowired BondCreditRatingDao creditRatingDao;

	private @Autowired UserOrgInfoDao userOrgInfoDao;

	private @Autowired FileHandleService fileHandleService;

	private @Autowired RedisUtil redisUtil;

	private @Autowired BondInstRatingFileDao bondInstRatingFileDao;

	private @Autowired BondInduService bondInduService;

	private @Autowired BondInstCodeDao bondInstCodeDao;

	private @Autowired BondCreditRatingService bondCreditRatingService;

	private @Autowired ApplicationEventPublisher publisher;

	private @Autowired BondUserOperationService bondUserOperationService;

	private @Autowired MqSenderService mqSenderService;
	
	protected @Autowired MongoTemplate bondMongoTemplate;

	private Integer getInstId(Integer userId) {
		return userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
	}

	public Boolean insertBondInstRatingHist(Integer userid, BondInstRatingHist entity) {
		Integer instId = getInstId(userid);

		// 信评流程
		entity.setType(2);
		if (entity.getLaunchDescribe() != null) {
			entity.setCreateBy(userid);// 发起信评
		} else {
			entity.setRatingBy(userid);// 债劵关联发行人
			// 提交信评的时候，检验最新评级版本跟当前版本，如果不同，为初评，空的，如果相同则排序比较
			if (entity.getVersion() != null
					&& entity.getVersion().equals(bondInstCodeDao.queryVersionByOrgId(instId))) {
				BondInstCode selectedinstCode = bondCreditRatingService.getRatingBondInstCode(instId,
						entity.getVersion(), entity.getRating());
				CreditRatingParsedBasic basic = new CreditRatingParsedBasic();
				basic.setBondId(entity.getBondUniCode());
				basic.setIssuerId(entity.getComUniCode());
				bondCreditRatingService.setInstRatingDiff(instId, basic, entity.getVersion(), entity, selectedinstCode);
			} else {
				entity.setRatingDiff(null);
			}
		}
		entity.setInstId(instId);
		BondInstRatingHist vo = bondInstRatingHistDao.save(entity);
		// 处理附件
		if (vo != null && vo.getFiles() != null && vo.getFiles().size() > 0) {
			bondInstRatingFileDao.deleteBondInstRatingFileByHistId(vo.getId());
			for (BondInstRatingFile f : vo.getFiles()) {
				f.setInstRatingId(vo.getId());
				bondInstRatingFileDao.insertBondInstRatingFile(f);
			}
		}

		// 用户有审核权限直接审核通过
		Map<String, Object> authorization = bondUserOperationService.bondAuthorization(userid);
		if (vo.getLaunchDescribe() == null && authorization.containsKey("RatingScore")
				&& authorization.containsKey("RatingDemandCheck")) {
			vo.setStatus(3);
			vo.setCheckResult(1);
			checkInstRatingHist(vo, userid);
		}

		sendRatingListOrgMessageRabbitMQ(instId, userid);

		return true;
	}

	// 更新MongoBD中的内部评级
	@Async
	@EventListener
	public void handleInstRatingHist(BondInstRatingHist instRatingHist) {
		if (null == instRatingHist) {
			return;
		}
		LOGGER.info("handleInstRatingHist instRatingHist:" + JSON.toJSONString(instRatingHist));

		// update data in mysql
		updateMysql(instRatingHist);
		// update data in mongodb
		updateMongoDB(instRatingHist);
	}

	@Transactional
	public void updateMysql(BondInstRatingHist instRatingHist) {
		Integer orgId = instRatingHist.getInstId();
		Long bondUniCode = instRatingHist.getBondUniCode();
		Long issuerId = instRatingHist.getComUniCode();
		Integer instinvadvId = instRatingHist.getInvestmentAdvice();
		if (StringUtils.isNotBlank(instRatingHist.getInvestmentDescribe()) && SafeUtils.getInt(instinvadvId) > 0) {
			Map<String, Object> bondmap = new HashMap<>();
			bondmap.put("orgId", orgId);
			bondmap.put("bondUniCode", bondUniCode);
			bondmap.put("instinvadvId", instinvadvId);
			int adviceIdRes = creditRatingDao.updateAdviceId(bondmap);
			LOGGER.info("updateRatingId creditRating bondmap:" + bondmap + ",adviceIdRes:" + adviceIdRes);
		}

		Integer instratingId = instRatingHist.getRating();
		if (null != instratingId && instratingId > 0) {
			Map<String, Object> commap = new HashMap<>();
			commap.put("orgId", orgId);
			commap.put("issuerId", issuerId);
			commap.put("instratingId", instratingId);
			int ratingIdRes = creditRatingDao.updateRatingId(commap);
			LOGGER.info("updateRatingId creditRating commap:" + commap + ",ratingIdRes:" + ratingIdRes);
		}
	}

	private void updateMongoDB(BondInstRatingHist instRatingHist) {
		// update rating in mongodb
		updateComInfo(instRatingHist);
		updateDetailInfo(instRatingHist);
		updateBasicInfo(instRatingHist);
		// update some detail info in mongodb
		updateQuoteTodayDetail(instRatingHist);
		udpdateDealTodayDetail(instRatingHist);
		updateTodayQuoteDetail(instRatingHist);
		updateTrendsPriceChange(instRatingHist);
		updateTrendsImpratingChange(instRatingHist);
	}

	private void updateComInfo(BondInstRatingHist instRatingHist) {
		Update update = getRatingUpdate(instRatingHist);
		WriteResult res = bondMongo.updateMulti(
				new Query().addCriteria(Criteria.where("_id").is(instRatingHist.getComUniCode())), update,
				"bond_com_info");

		LOGGER.info("updateComInfo comUniCode:" + instRatingHist.getComUniCode() + "res.getN:" + res.getN());
	}

	private void updateDetailInfo(BondInstRatingHist instRatingHist) {
		WriteResult res1 = updateComData("comUniCode", "bond_detail_info", instRatingHist);
		WriteResult res2 = updateBondData("_id", "bond_detail_info", instRatingHist);

		LOGGER.info("updateDetailInfo comUniCode:" + instRatingHist.getComUniCode() + "res1.getN:" + res1.getN()
				+ ",bondUniCode:" + instRatingHist.getBondUniCode() + ",res2:" + res2);
	}

	private WriteResult updateBondData(String fieldName, String collectionName, BondInstRatingHist instRatingHist) {
		if (SafeUtils.getLong(instRatingHist.getBondUniCode()) <= 0) {
			return null;
		}
		WriteResult res = null;
		Query advquery = new Query();
		advquery.addCriteria(Criteria.where(fieldName).is(instRatingHist.getBondUniCode()));
		Update advupdate = new Update();

		if (StringUtils.isNotBlank(instRatingHist.getInvestmentDescribe())
				&& SafeUtils.getInt(instRatingHist.getInvestmentAdvice()) > 0) {
			setInvadvUpdate(instRatingHist, advupdate);
			res = bondMongo.updateMulti(advquery, advupdate, collectionName);
		} else {
			// 如果没有投资建议用上一次该债券的投资建议
			BondInstRatingHist lastInstRatingHist = getLastInstInvestAdvHist(instRatingHist);
			if (null != lastInstRatingHist && StringUtils.isNotBlank(lastInstRatingHist.getInvestmentDescribe())
					&& SafeUtils.getInt(lastInstRatingHist.getInvestmentAdvice()) > 0) {
				setInvadvUpdate(instRatingHist, advupdate);
				res = bondMongo.updateMulti(advquery, advupdate, collectionName);
			}
		}
		return res;
	}

	private void setInvadvUpdate(BondInstRatingHist instRatingHist, Update advupdate) {
		int instId = instRatingHist.getInstId();
		String invadvIdKey = InstConstants.INSTINVESTMENTADVICE_INVADVID + instId;
		String invadvKey = InstConstants.INSTINVESTMENTADVICE_INVADVNAME + instId;
		String textFlagKey = InstConstants.INSTINVESTMENTADVICE_TEXTFLAG + instId;
		int textFlag = 1;
		if (StringUtils.isEmpty(instRatingHist.getInvestmentAdviceDesdetail())) {
			textFlag = 0;
		}
		advupdate.set(invadvKey, instRatingHist.getInvestmentDescribe());
		advupdate.set(invadvIdKey, instRatingHist.getInvestmentAdvice());
		advupdate.set(textFlagKey, textFlag);
	}

	private BondInstRatingHist getLastInstInvestAdvHist(BondInstRatingHist instRatingHist) {
		Map<String, Object> map = new HashMap<>();
		map.put("version", instRatingHist.getVersion());
		map.put("status", instRatingHist.getStatus());
		map.put("instId", instRatingHist.getInstId());
		map.put("comUniCode", instRatingHist.getComUniCode());
		map.put("bondUniCode", instRatingHist.getBondUniCode());
		BondInstRatingHist lastInstRatingHist = bondInstRatingHistDao.queryLastInstRatingHistByRatingAdvice(map);
		return lastInstRatingHist;
	}

	private WriteResult updateComData(String fieldName, String collectionName, BondInstRatingHist instRatingHist) {
		Query ratquery = new Query();
		ratquery.addCriteria(Criteria.where(fieldName).is(instRatingHist.getComUniCode()));
		Update ratupdate = getRatingUpdate(instRatingHist);
		return bondMongo.updateMulti(ratquery, ratupdate, collectionName);
	}

	private Update getRatingUpdate(BondInstRatingHist instRatingHist) {
		int instId = instRatingHist.getInstId();
		String ratingIdKey = InstConstants.INSTRATING_RATEID + instId;
		String ratingKey = InstConstants.INSTRATING_RATENAME + instId;
		String textFlagKey = InstConstants.INSTRATING_TEXTFLAG + instId;
		int textFlag = 1;
		if (StringUtils.isEmpty(instRatingHist.getRatingDescribe())) {
			textFlag = 0;
		}
		Update ratupdate = new Update();
		ratupdate.set(ratingKey, instRatingHist.getRatingName());
		ratupdate.set(ratingIdKey, instRatingHist.getRating());
		ratupdate.set(textFlagKey, textFlag);
		return ratupdate;
	}

	private void updateBasicInfo(BondInstRatingHist instRatingHist) {
		WriteResult res1 = updateComData("issuerId", "bond_basic_info", instRatingHist);
		WriteResult res2 = updateBondData("_id", "bond_basic_info", instRatingHist);

		LOGGER.info("updateBasicInfo comUniCode:" + instRatingHist.getComUniCode() + "res1.getN:" + res1.getN()
				+ ",bondUniCode:" + instRatingHist.getBondUniCode() + "res2:" + res2);
	}

	private void updateTrendsPriceChange(BondInstRatingHist instRatingHist) {
		WriteResult res1 = updateComData("issuerId", "bond_trends_price_change", instRatingHist);
		WriteResult res2 = updateBondData("bondUniCode", "bond_trends_price_change", instRatingHist);
		LOGGER.info("updateTrendsPriceChange res1:" + res1 + "res2:" + res2);
	}

	private void udpdateDealTodayDetail(BondInstRatingHist instRatingHist) {
		WriteResult res1 = updateComData("comUniCode", "bond_deal_today_detail_info", instRatingHist);
		WriteResult res2 = updateBondData("bondUniCode", "bond_deal_today_detail_info", instRatingHist);
		LOGGER.info("udpdateDealTodayDetail res1:" + res1 + "res2:" + res2);

		WriteResult res3 = updateComData("comUniCode", "bond_deal_today_not_ncd_detail_info", instRatingHist);
		WriteResult res4 = updateBondData("bondUniCode", "bond_deal_today_not_ncd_detail_info", instRatingHist);
		LOGGER.info("udpdateDealTodayDetail res3:" + res3 + "res4:" + res4);
	}

	private void updateTodayQuoteDetail(BondInstRatingHist instRatingHist) {
		WriteResult res1 = updateComData("comUniCode", "bond_today_quote_detail_info", instRatingHist);
		WriteResult res2 = updateBondData("bondUniCode", "bond_today_quote_detail_info", instRatingHist);
		LOGGER.info("updateTodayQuoteDetail res1:" + res1 + "res2:" + res2);
	}

	private void updateQuoteTodayDetail(BondInstRatingHist instRatingHist) {
		WriteResult res1 = updateComData("comUniCode", "bond_quote_today_detail_info", instRatingHist);
		WriteResult res2 = updateBondData("bondUniCode", "bond_quote_today_detail_info", instRatingHist);
		LOGGER.info("updateQuoteTodayDetail res1:" + res1 + "res2:" + res2);

		WriteResult res3 = updateComData("comUniCode", "bond_not_ncd_quote_today_detail_info", instRatingHist);
		WriteResult res4 = updateBondData("bondUniCode", "bond_not_ncd_quote_today_detail_info", instRatingHist);
		LOGGER.info("updateQuoteTodayDetail res3:" + res3 + "res4:" + res4);
	}

	private void updateTrendsImpratingChange(BondInstRatingHist instRatingHist) {
		WriteResult res1 = updateComData("issuerId", "bond_trends_imprating_change", instRatingHist);
		WriteResult res2 = updateBondData("bondUniCode", "bond_trends_imprating_change", instRatingHist);
		LOGGER.info("updateTrendsImpratingChange res1:" + res1 + "res2:" + res2);
	}

	public List<BondInstRatingHist> findInstRatingHistList(Integer userid, Long uniCode, Integer type,
			Integer requestType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instId", getInstId(userid));
		map.put("uniCode", uniCode);
		map.put("type", type);
		map.put("requestType", requestType);
		return bondInstRatingHistDao.findInstRatingHistList(map);
	}

	public BondInstRatingFile uploadFileOss(Integer userid, Integer type, MultipartFile sourceFile) throws Exception {
		return fileHandleService.uploadFileOss(userid, type, sourceFile.getBytes(), sourceFile.getOriginalFilename(),
				sourceFile.getContentType());
	}

	public Boolean updateBondInstRatingHist(Integer userid, BondInstRatingHist entity) {
		
		LOGGER.info("updateBondInstRatingHist  userid:" + userid + "groupLeaderDescribe:"+entity.getGroupLeaderDescribe());

		Integer instId = getInstId(userid);

		Boolean flays = true; //信评状态
		
		if(entity.getGroupLeaderDescribe()!=null){
			flays = false;
			BondInstRatingHist vo = new BondInstRatingHist();
			vo.setId(entity.getId());
			vo.setGroupLeaderBy(entity.getGroupLeaderBy());
			vo.setGroupLeaderDescribe(entity.getGroupLeaderDescribe());
			vo.setGroupLeaderByName(entity.getGroupLeaderByName());
			vo.setStatus(entity.getStatus());
			entity = vo;
		}
		
		// 信评流程
		if(flays){
			entity.setType(2);
		}
		
		LOGGER.info(flays.toString());
		if (entity.getCheckResult() != null && flays) { // 审核
			entity.setCheckBy(userid);
		} else if (entity.getRating() != null && flays) { // 信评
			entity.setRatingBy(userid);
		}
		
		entity.setInstId(instId);

		// 处理附件
		if (entity != null && entity.getFiles() != null && entity.getFiles().size() > 0) {
			bondInstRatingFileDao.deleteBondInstRatingFileByHistId(entity.getId());
			for (BondInstRatingFile f : entity.getFiles()) {
				f.setInstRatingId(entity.getId());
				bondInstRatingFileDao.insertBondInstRatingFile(f);
			}
		}

		// 提交信评的时候，检验最新评级版本跟当前版本，如果不同，为初评，空的，如果相同则排序比较
		if (entity.getVersion() != null && entity.getVersion().equals(bondInstCodeDao.queryVersionByOrgId(instId))) {
			BondInstCode selectedinstCode = bondCreditRatingService.getRatingBondInstCode(instId, entity.getVersion(),
					entity.getRating());
			CreditRatingParsedBasic basic = new CreditRatingParsedBasic();
			basic.setBondId(entity.getBondUniCode());
			basic.setIssuerId(entity.getComUniCode());
			bondCreditRatingService.setInstRatingDiff(instId, basic, entity.getVersion(), entity, selectedinstCode);
		} else {
			entity.setRatingDiff(null);
		}
		Boolean flay = bondInstRatingHistDao.updateBondInstRatingHist(entity) > 0;

		// 用户有审核权限直接审核通过
		Map<String, Object> authorization = bondUserOperationService.bondAuthorization(userid);
		if (entity.getRating() != null && authorization.containsKey("RatingScore")
				&& authorization.containsKey("RatingDemandCheck")) {
			entity.setStatus(3);
			entity.setCheckResult(1);
			checkInstRatingHist(entity, userid);
		}

		sendRatingListOrgMessageRabbitMQ(instId, userid);

		return true;
	}

	public PageInfo<List<BondInstRatingHist>> findInstRatingHistAll(Integer userid, Integer status, Integer type,
			Boolean flay, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instId", getInstId(userid));
		map.put("status", status);
		if (flay) {
			map.put("userid", userid);
			map.put("typeAll", type);
		}
		map.put("induClass", bondInduService.findUserInduClass(userid));
		map.put("requestType", 1);
		PageHelper.startPage(page, limit);
		List<BondInstRatingHist> list = bondInstRatingHistDao.findInstRatingHistAll(map);
		if (list == null) {
			return null;
		}
		List<Long> bondUniCodeList = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			bondUniCodeList.add(list.get(i).getBondUniCode());
		}
		// 债劵编号
		Query query = new Query().addCriteria(Criteria.where("_id").in(bondUniCodeList));
		List<BondDetailDoc> bondList = bondMongo.find(query, BondDetailDoc.class);
		Map<Long, String> codeMap = new HashMap<Long, String>();
		if (bondList != null && bondList.size() > 0) {
			for (BondDetailDoc bond : bondList) {
				codeMap.put(bond.getBondUniCode(), bond.getCode());
			}
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setBondCode(codeMap.get(list.get(i).getBondUniCode()));
		}

		exec(list, userid);

		PageInfo<List<BondInstRatingHist>> pageinfo = new PageInfo(list);
		return pageinfo;
	}

	public BondInstRatingHist findInstRatingHist(Integer userid, Long uniCode, Integer type, Long id) {

		BondInstRatingHist vo = null;
		
		if(type==2){
			vo = getBondInstRatingHist(userid, uniCode, 2); // 最新的投资建议
			if(vo!=null){
				vo.setOldInvestmentDescribeTime(vo.getRatingTime());
				vo.setOldInvestmentDescribeByName(getName(vo.getRatingBy()));
				vo.setOldGroupName(vo.getGroupName());
				vo.setOldInduName(vo.getInduUniName());
				vo.setOldVersion(vo.getVersion());
			}else{
				vo = new BondInstRatingHist();
				Query query = new Query();
	            query.addCriteria(Criteria.where("bondUniCode").is(uniCode));
	            BondDetailDoc detailDoc = bondMongoTemplate.findOne(query, BondDetailDoc.class);
	            if(detailDoc!=null){
	            	vo.setComUniCode(detailDoc.getComUniCode());
	            }
			}
		}
		
		if(type==1 || (vo!=null && vo.getComUniCode()!=null)){
			BondInstRatingHist ratingVo=null;
			if(vo!=null && vo.getComUniCode()!=null){
				ratingVo = getBondInstRatingHist(userid, vo.getComUniCode(), 1); // 最新的内部评级
			}else{
				ratingVo = getBondInstRatingHist(userid, uniCode, 1); // 最新的内部评级
			}
			if(vo==null){
				vo = new BondInstRatingHist();
			}
			if(type==1 && ratingVo!=null){
				vo = ratingVo;
			}
			
			if(ratingVo==null){
				ratingVo = new BondInstRatingHist();
			}else{
				vo.setOldRating(ratingVo.getRating());
				vo.setOldRatingByName(getName(ratingVo.getRatingBy()));
				vo.setOldRatingTime(ratingVo.getRatingTime());
				vo.setOldRatingName(ratingVo.getRatingName());
			}
			
		}

		BondInstRatingHist hist = null;

		if (id != null) {
			// 评级说明 延用上次评级 相关资料 评级结果 取当前的
			hist = findInstRatingHistById(id, userid); // 当前的
		}

		if (hist == null) {
			hist = vo;
		}

		// 最新数据
		hist.setOldGroupName(vo.getGroupName());
		hist.setOldInduName(vo.getInduUniName());
		hist.setOldVersion(vo.getVersion());
		hist.setGroupId(vo.getGroupId());
		// 投资建议
		hist.setOldInvestmentDescribe(vo.getInvestmentDescribe());
		hist.setOldInvestmentDescribeTime(vo.getOldInvestmentDescribeTime());
		hist.setOldInvestmentDescribeByName(vo.getOldInvestmentDescribeByName());
		// 内部评级
		hist.setOldRatingTime(vo.getOldRatingTime());
		hist.setOldRatingName(vo.getOldRatingName());
		hist.setOldRating(vo.getOldRating());
		hist.setOldRatingByName(vo.getOldRatingByName());

		return hist == null ? new BondInstRatingHist() : hist;

	}

	public Integer deleteInstRatingHistById(Integer userid, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		// 是否正在发起信评
		BondInstRatingHist vo = bondInstRatingHistDao.findInstRatingHistById(map);
		if (this.isCommentary(userid, vo.getBondUniCode(), vo.getComUniCode()).get("status") == 0) {
			return 0;
		}
		map.clear();
		map.put("instId", getInstId(userid));
		map.put("id", id);
		// 信评记录为待信评存在信评时间的最新一条记录不能撤销 （撤销条件）
		Integer count = bondInstRatingHistDao.deleteInstRatingHistById(map);

		sendRatingListOrgMessageRabbitMQ(getInstId(userid), userid);
		return count;
	}

	public Map<String, Object> findInstRatingHistStatus(Integer userid, Long bondUniCode, Long comUniCode,
			Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("instId", getInstId(userid));
		if (type == 1) {
			map.put("uniCode", comUniCode);
		} else if (type == 2) {
			map.put("uniCode", bondUniCode);
		}
		map.put("type", type);
		BondInstRatingHist hist = bondInstRatingHistDao.findInstRatingHistStatus(map);
		map.clear();
		if (hist != null) {
			map.put("id", hist.getId());
			map.put("status", hist.getStatus());
		}

		if (type == 2) {
			StringBuffer sb = new StringBuffer("isCommentary");
			if (comUniCode != null) {
				sb.append(comUniCode);
			}
			if (bondUniCode != null) {
				sb.append(bondUniCode);
			}
			String key = sb.toString();
			// 已经有人在信评
			if (redisUtil.get(key) != null) {
				map.put("status", 4);
			}
		}
		return map;
	}

	public List<Map<String, Integer>> findInstRatingHistStatusCount(Integer userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instId", getInstId(userid));
		List<Map<String, Integer>> list = bondInstRatingHistDao.findInstRatingHistStatusCount(map);

		if (list == null || list.size() < 1) {
			Map<String, Integer> m1 = new HashMap<String, Integer>();
			m1.put("status", 1);
			m1.put("count", 0);
			list.add(m1);
			Map<String, Integer> m2 = new HashMap<String, Integer>();
			m2.put("status", 2);
			m2.put("count", 0);
			list.add(m2);
			Map<String, Integer> m3 = new HashMap<String, Integer>();
			m3.put("status", 3);
			m3.put("count", 0);
			list.add(m3);
		} else {
			List<Integer> keylist = new ArrayList<Integer>();
			for (Map<String, Integer> m : list) {
				keylist.add(m.get("status"));
			}
			if (!keylist.contains(1)) {
				Map<String, Integer> m1 = new HashMap<String, Integer>();
				m1.put("status", 1);
				m1.put("count", 0);
				list.add(m1);
			}
			if (!keylist.contains(2)) {
				Map<String, Integer> m2 = new HashMap<String, Integer>();
				m2.put("status", 2);
				m2.put("count", 0);
				list.add(m2);
			}
			if (!keylist.contains(3)) {
				Map<String, Integer> m3 = new HashMap<String, Integer>();
				m3.put("status", 3);
				m3.put("count", 0);
				list.add(m3);
			}
		}

		return list;
	}

	public Map<String, Integer> isCommentary(Integer userid, Long bondUniCode, Long comUniCode) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		StringBuffer sb = new StringBuffer("isCommentary");
		if (comUniCode != null) {
			sb.append(comUniCode);
		}
		if (bondUniCode != null) {
			sb.append(bondUniCode);
		}
		String key = sb.toString();
		sb.append(userid);
		// 已经有人在信评 ,新打开的用户不是信评用户
		if (redisUtil.get(key) != null && redisUtil.get(sb.toString()) == null) {
			map.put("status", 0);
		} else {
			map.put("status", 1);
		}
		return map;
	}

	public String commentaryStatus(Integer userid, Integer status, Long bondUniCode, Long comUniCode) {
		StringBuffer sb = new StringBuffer("isCommentary");
		if (comUniCode != null) {
			sb.append(comUniCode);
		}
		if (bondUniCode != null) {
			sb.append(bondUniCode);
		}
		String key = sb.toString();
		sb.append(userid);
		if (status == 1) {
			redisUtil.set(sb.toString(), "ing", 600L);
			redisUtil.set(key, "ing", 600L);
		}
		if (status == 0) {
			redisUtil.remove(sb.toString());
			redisUtil.remove(key);
		}
		return "success";
	}

	public BondInstRatingHist findInstRatingHistById(Long id, Integer userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		BondInstRatingHist vo = bondInstRatingHistDao.findInstRatingHistById(map);
		BondInstRatingFile bondInstRatingFile = new BondInstRatingFile();
		bondInstRatingFile.setInstRatingId(vo.getId());
		List<BondInstRatingFile> ratingFiles = bondInstRatingHistDao.queryBondInstRatingFileList(bondInstRatingFile);
		vo.setInvestmentDescribeFiles(new ArrayList<BondInstRatingFile>());
		vo.setRatingFiles(new ArrayList<BondInstRatingFile>());
		vo.setLaunchDescribeFiles(new ArrayList<BondInstRatingFile>());

		List<Integer> userIdList = new ArrayList<Integer>();
		if (!StringUtils.isEmpty(vo.getGroupLeaderBy())) {
			userIdList.add(vo.getGroupLeaderBy());
			List<Map<String, Object>> namelist = userOrgInfoDao.queryUserByUserIdList(userIdList, userid);
			if (namelist != null && namelist.size() > 0) {
				vo.setGroupLeaderByName(namelist.get(0).get("name").toString());
			}
		}
		if (ratingFiles != null && ratingFiles.size() > 0) {
			for (BondInstRatingFile file : ratingFiles) {
				if (file.getType() == 1) {
					vo.getLaunchDescribeFiles().add(file);
				} else if (file.getType() == 2) {
					vo.getRatingFiles().add(file);
				} else if (file.getType() == 3) {
					vo.getInvestmentDescribeFiles().add(file);
				}
			}
		}

		List<BondInstRatingHist> list = new ArrayList<BondInstRatingHist>();
		list.add(vo);
		exec(list, userid);
		return list.get(0);
	}

	public BondInstRatingHist findBondInstRatingHist(Integer userid, Long uniCode, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instId", getInstId(userid));
		map.put("uniCode", uniCode);
		map.put("type", type);
		map.put("induClass", bondInduService.findUserInduClass(userid));
		BondInstRatingHist vo = bondInstRatingHistDao.findBondInstRatingHist(map);
		if (vo != null) {
			List<Integer> userIdList = new ArrayList<Integer>();
			userIdList.add(vo.getCreateBy());
			List<Map<String, Object>> namelist = userOrgInfoDao.queryUserByUserIdList(userIdList, userid);
			if (namelist != null && namelist.size() > 0) {
				vo.setCreateName(namelist.get(0).get("name").toString());
			}
			BondInstRatingFile bondInstRatingFile = new BondInstRatingFile();
			bondInstRatingFile.setType(1);
			bondInstRatingFile.setInstRatingId(vo.getId());
			List<BondInstRatingFile> ratingFiles = bondInstRatingHistDao
					.queryBondInstRatingFileList(bondInstRatingFile);
			vo.setLaunchDescribeFiles(ratingFiles);
		}
		return vo;
	}

	@Transactional
	public Boolean checkInstRatingHist(BondInstRatingHist bondInstRatingHist, Integer userid) {
		Integer instId = getInstId(userid);
		bondInstRatingHist.setCheckBy(userid);
		bondInstRatingHist.setInstId(instId);
		if (bondInstRatingHist != null && bondInstRatingHist.getCheckResult() != null) {
			BondInstRatingHist hist = new BondInstRatingHist();
			hist.setId(bondInstRatingHist.getId());
			hist.setCheckBy(bondInstRatingHist.getCheckBy());
			hist.setCheckResult(bondInstRatingHist.getCheckResult());
			hist.setReturnReason(bondInstRatingHist.getReturnReason());
			hist.setInstId(instId);
			hist.setStatus(bondInstRatingHist.getStatus());
			// 更新审核人,审核结果,退回理由,状态
			this.updateBondInstRatingHist(userid, hist);
			// 更新债劵关联发行人关系
			if (bondInstRatingHist.getBondUniCode() != null) {
				bondInstRatingHistDao.updateBondInstRatingHistByFatId(hist);
			}
			if (bondInstRatingHist.getCheckResult() == 1) { // 审核成功
				// 添加到对应信评分组
				addRatingGroup(bondInstRatingHist, instId, userid);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", bondInstRatingHist.getId());
				map.put("instId", instId);
				// 查找当前信评的子类,并修改内部评级以及投资建议
				List<BondInstRatingHist> list = bondInstRatingHistDao.findBondInstRatingHistByFatId(map);
				if (list != null && list.size() > 0) {
					for (BondInstRatingHist h : list) {
						publisher.publishEvent(h);
					}
				}
				// 修改该机构内部评级以及投资建议
				publisher.publishEvent(bondInstRatingHist);

				// 通知前端刷新信评
				this.sendBondOrgMessageRabbitMQ(instId, bondInstRatingHist.getBondUniCode(),
						bondInstRatingHist.getComUniCode());
			}
		}
		return true;
	}

	/**
	 * 处理发起人,信评人,审核人
	 * 
	 * @param list
	 * @param userid
	 */
	private void exec(List<BondInstRatingHist> list, Integer userid) {
		// 发起人
		List<BondInstRatingHist> createList = new ArrayList<BondInstRatingHist>();
		List<Integer> createIdList = new ArrayList<Integer>();

		// 信评人
		List<BondInstRatingHist> ratingList = new ArrayList<BondInstRatingHist>();
		List<Integer> ratingIdList = new ArrayList<Integer>();

		// 审核人
		List<BondInstRatingHist> checkList = new ArrayList<BondInstRatingHist>();
		List<Integer> checkIdList = new ArrayList<Integer>();

		for (int i = 0; i < list.size(); i++) {
			if (!StringUtils.isEmpty(list.get(i).getCreateBy())) {
				createIdList.add(list.get(i).getCreateBy());
				createList.add(list.get(i));
			}
			if (!StringUtils.isEmpty(list.get(i).getRatingBy())) {
				ratingIdList.add(list.get(i).getRatingBy());
				ratingList.add(list.get(i));
			}
			if (!StringUtils.isEmpty(list.get(i).getCheckBy())) {
				checkIdList.add(list.get(i).getCheckBy());
				checkList.add(list.get(i));
			}
		}
		// 发起人
		List<Map<String, Object>> namelist = null;
		if (createIdList != null && createIdList.size() > 0) {
			namelist = userOrgInfoDao.queryUserByUserIdList(createIdList, userid);
		}
		if (namelist != null) {
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < createList.size(); j++) {
					if (!StringUtils.isEmpty(list.get(i).getCreateBy())
							&& list.get(i).getCreateBy().equals(createList.get(j).getCreateBy())) {
						if (namelist.get(j) != null && !StringUtils.isEmpty(namelist.get(j).get("name"))) {
							list.get(i).setCreateName(namelist.get(j).get("name").toString());
							break;
						}
					}
				}
			}
		}

		// 信评人
		namelist = null;
		if (ratingIdList != null && ratingIdList.size() > 0) {
			namelist = userOrgInfoDao.queryUserByUserIdList(ratingIdList, userid);
		}
		if (namelist != null) {
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < ratingList.size(); j++) {
					if (!StringUtils.isEmpty(list.get(i).getRatingBy())
							&& list.get(i).getRatingBy().equals(ratingList.get(j).getRatingBy())) {
						if (namelist.get(j) != null && !StringUtils.isEmpty(namelist.get(j).get("name"))) {
							list.get(i).setRatingByName(namelist.get(j).get("name").toString());
							break;
						}
					}
				}
			}
		}

		// 审核人
		namelist = null;
		if (checkIdList != null && checkIdList.size() > 0) {
			namelist = userOrgInfoDao.queryUserByUserIdList(checkIdList, userid);
		}
		if (namelist != null) {
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < checkList.size(); j++) {
					if (!StringUtils.isEmpty(list.get(i).getCheckBy())
							&& list.get(i).getCheckBy().equals(checkList.get(j).getCheckBy())) {
						if (namelist.get(j) != null && !StringUtils.isEmpty(namelist.get(j).get("name"))) {
							list.get(i).setCheckName(namelist.get(j).get("name").toString());
							break;
						}
					}
				}
			}
		}

	}

	// 添加信评分组
	private void addRatingGroup(BondInstRatingHist bondInstRatingHist, Integer instId, Integer userid) {
		List<CreditRatingParsedBasic> creditRatingParsedBasicList = new ArrayList<CreditRatingParsedBasic>();
		CreditRatingParsedBasic basic = new CreditRatingParsedBasic();
		basic.setGroupId(bondInstRatingHist.getGroupId());
		basic.setBondId(bondInstRatingHist.getBondUniCode() == null ? 0 : bondInstRatingHist.getBondUniCode());
		setBondBasicInfo(basic);
		basic.setIssuerId(bondInstRatingHist.getComUniCode());
		basic.setIssuer(bondInstRatingHist.getBondChiName());
		BondInstCode rating = bondCreditRatingService.getRatingBondInstCode(instId, bondInstRatingHist.getVersion(),
				bondInstRatingHist.getRating());
		BondInstCode investmentAdvice = null;
		if (bondInstRatingHist.getInvestmentAdvice() != null) {
			investmentAdvice = bondCreditRatingService.getRatingBondInstCode(instId, bondInstRatingHist.getVersion(),
					bondInstRatingHist.getInvestmentAdvice());
		} else {
			investmentAdvice = new BondInstCode();
		}
		basic.setSelectedRating(rating);
		basic.setSelectedInvestmentAdvice(investmentAdvice);
		basic.setCurrentRatingDes(bondInstRatingHist.getRatingDescribe());
		basic.setInvestmentAdviceDesDetail(bondInstRatingHist.getInvestmentAdviceDesdetail());
		creditRatingParsedBasicList.add(basic);
		try {
			bondCreditRatingService.handleSavedCreditRating(userid, bondInstRatingHist.getGroupId(), 0, instId,
					creditRatingParsedBasicList, bondInstRatingHist.getVersion());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.info("addRatingGroup is error , BondInstRatingHist = " + bondInstRatingHist.toString() + ",instId:"
					+ instId);
		}
	}

	private void setBondBasicInfo(CreditRatingParsedBasic basic) {
		Query query = new Query().addCriteria(Criteria.where("_id").in(basic.getBondId()));
		query.fields().include("_id").include("code").include("shortName");
		BondBasicInfoDoc basicInfo = bondMongo.findOne(query, BondBasicInfoDoc.class);
		if (null != basicInfo) {
			basic.setBondCode(basicInfo.getCode());
			basic.setBondName(basicInfo.getShortName());
		}
	}

	private void sendRatingListOrgMessageRabbitMQ(Integer instId, Integer userid) {
		ToRoomsSocketIoMsg msg = new ToRoomsSocketIoMsg();
		msg.setMessageType("bondRefreshOrgRatingListMessage");
		msg.setNamespaces("dm_client_web_bond");
		msg.setRooms("org_" + instId);
		List<Map<String, Integer>> list = this.findInstRatingHistStatusCount(userid);
		msg.setData(JSONArray.toJSON(list));
		String json = JSON.toJSONString(msg.toJsonMessage());
		mqSenderService.sendBondOrgMessageRabbitMQ(json);
	}

	private void sendBondOrgMessageRabbitMQ(Integer instId, Long bondUniCode, Long comUniCode) {
		ToRoomsSocketIoMsg msg = new ToRoomsSocketIoMsg();
		msg.setMessageType("bondRefreshOrgInfoMessage");
		msg.setNamespaces("dm_client_web_bond");
		msg.setRooms("org_" + instId);
		JSONObject o = new JSONObject();
		o.put("bondUniCode", bondUniCode);
		o.put("comUniCode", comUniCode);
		msg.setData(o);
		String json = JSON.toJSONString(msg.toJsonMessage());
		mqSenderService.sendBondOrgMessageRabbitMQ(json);
	}

	private BondInstRatingHist getBondInstRatingHist(Integer userid, Long uniCode, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instId", getInstId(userid));
		map.put("uniCode", uniCode);
		map.put("type", type);
		map.put("status", 3); // 已完成
		map.put("induClass", bondInduService.findUserInduClass(userid));
		map.put("requestType", 2);
		List<BondInstRatingHist> list = bondInstRatingHistDao.findInstRatingHistAll(map);
		BondInstRatingHist vo = null;
		if (list != null && list.size() > 0) {
			vo = list.get(0);
		}
		return vo;
	}

	private String getName(Integer userId) {
		// 上次内部评级信评人员
		List<Integer> userIdList = new ArrayList<Integer>();
		if (!StringUtils.isEmpty(userId)) {
			userIdList.add(userId);
			List<Map<String, Object>> namelist = userOrgInfoDao.queryUserByUserIdList(userIdList, userId);
			if (namelist != null && namelist.size() > 0) {
				return namelist.get(0).get("name").toString();
			}
		}
		return null;
	}

}
