package com.innodealing.bond.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.innodealing.bond.param.BaseQuoteParam;
import com.innodealing.bond.param.BaseQuoteResendParam;
import com.innodealing.bond.param.BondQuoteAddParam;
import com.innodealing.bond.param.BondQuoteStatusParam;
import com.innodealing.bond.param.BrokerBondQuoteParam;
import com.innodealing.bond.service.BondQuantAnalysisService.BondCashFlows;
import com.innodealing.bond.validation.MessageValidation;
import com.innodealing.bond.vo.quote.BondQuoteInfoVo;
import com.innodealing.consts.Constants;
import com.innodealing.domain.Node;
import com.innodealing.domain.SysuserAndOrgainfo;
import com.innodealing.domain.SysuserInstDto;
import com.innodealing.engine.jdbc.im.SysuserDao;
import com.innodealing.engine.jpa.dm.BondFavoriteGroupRepository;
import com.innodealing.engine.jpa.dm.BondQuoteRepository;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.engine.mongo.bond.BondQuoteDocRepository;
import com.innodealing.engine.redis.RedisMsgService;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.dm.bond.BondFavoriteGroup;
import com.innodealing.model.dm.bond.BondNotificationMsg;
import com.innodealing.model.dm.bond.BondQuote;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondBestQuoteDoc;
import com.innodealing.model.mongo.dm.BondBestQuoteNetpriceDoc;
import com.innodealing.model.mongo.dm.BondBestQuoteYieldDoc;
import com.innodealing.model.mongo.dm.BondDealDataDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondQuoteDoc;
import com.innodealing.rabbitmq.MqSenderService;
import com.innodealing.util.HttpClientUtil;
import com.innodealing.util.RawmsgUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import com.mongodb.WriteResult;
import com.mysql.jdbc.log.Log;

/**
 * @author stephen.ma
 * @date 2016年9月1日
 * @clasename BondQuoteService.java
 * @decription TODO
 */
@Service
public class BondQuoteService {

	private final static Logger logger = LoggerFactory.getLogger(BondQuoteService.class);

	@Value("${config.define.pythonUrl}")
	private String pythonUrl;

	private @Autowired Gson gson;

	private @Autowired BondQuoteRepository bondQuoteRepository;

	private @Autowired BondQuoteDocRepository bondQuoteDocRepository;

	private @Autowired BondBasicInfoRepository bondBasicInfoRepository;

	private @Autowired BondDetailRepository bondDetailRepository;

	private @Autowired SysuserDao sysuserDao;

	private @Autowired MessageValidation messageValidation;

	private @Autowired MongoOperations mongoOperations;

	private @Autowired ApplicationEventPublisher publisher;

	private @Autowired BondFavoriteGroupRepository bondFavoriteGroupRepository;

	protected @Autowired RedisMsgService redisUtil;
	
	private @Autowired BondFavoriteService favoriteService;
	
	private @Autowired BondQuantAnalysisService bondQuantService;
	
	private @Autowired MqSenderService mqSenderService;
	
    @Resource(name="bondMongo")
    protected MongoTemplate bondMongoTemplate;
	
	public List<Long> addDemand(Long userId, BondQuoteAddParam params) {

		List<Long> list = saveDemand(userId, params);

		return list;
	}

	/**
	 * @param userId
	 * @param params
	 * @return
	 */
	@Transactional
	private List<Long> saveDemand(Long userId, BondQuoteAddParam params) {
		List<Long> list = null;

		SysuserAndOrgainfo sysuser = sysuserDao.findSysuserOrgInfo(userId);

		if (null != sysuser && 1 == sysuser.getUsertype()) {
			SysuserInstDto sysuserInst = sysuserDao.findSysuserInstById(SafeUtils.getInteger(userId));

			List<BondQuote> bondQuotes = new ArrayList<>();
			params.getQuotes().forEach(quoteParam -> {
				if (!messageValidation.isSameQuoteParam(userId, quoteParam)) {
					BondQuote bondQuote = handleBondQuoteWithParam(userId, sysuser, quoteParam, null, sysuserInst, 0);
					bondQuotes.add(bondQuote);

				} else {
					logger.info("saveDemand remove duplicates,BondUniCode:"+quoteParam.getBondUniCode());
				}
			});

			list = saveBondQuotes(bondQuotes, sysuser);
		}else{
			throw new BusinessException("您的权限不够，交易员才能发报价");
		}

		return list;
	}

	/**
	 * @param userId
	 * @param sysuser
	 * @param quoteParam
	 * @return
	 */
	private BondQuote handleBondQuoteWithParam(Long userId, SysuserAndOrgainfo sysuser, BaseQuoteParam quoteParam,
			Date sendTime, SysuserInstDto sysuserInst, Integer brokerType) {
		BondQuote bondQuote = new BondQuote();

		String quoteId = Constants.QUOTEID_PREFIX + SafeUtils.getRandomNum(2)
		+ SafeUtils.convertDateToString(new Date(), SafeUtils.DATE_TIME_FORMAT2) + SafeUtils.getRandomNum(8);
		BeanUtils.copyProperties(quoteParam, bondQuote);
		bondQuote.setUserId(userId);
		bondQuote.setSubType(0);
		bondQuote.setQuoteId(quoteId);
		bondQuote.setMsgId(SafeUtils.getRandomStr(24));
		if (null == bondQuote.getBondPrice()) {
			bondQuote.setBondPrice(new BigDecimal(0));
		}

		if (null != sysuser) {
			//			String companyshort = sysuser.getCompanyshort();
			//			if (null != sysuser.getCityName() && StringUtils.isNotBlank(sysuser.getCityName())) {
			//				companyshort = companyshort + sysuser.getCityName();
			//			}
			bondQuote.setInstId(sysuser.getOrgainfoId());
			//			bondQuote.setInstShort(companyshort);
			bondQuote.setInstType(SafeUtils.getInteger(sysuser.getCompanytype()));
			bondQuote.setUserName(sysuser.getName());
			bondQuote.setWechatno(sysuser.getWechatno());
			bondQuote.setQqNum(sysuser.getQq());

			String companyshort = SafeUtils.getString(sysuser.getCompanyshort());
			if (null != sysuserInst) {
				if (1 == sysuserInst.getOrganType() && 1 == sysuserInst.getInstGrade()) {
					companyshort = companyshort + "总行";
				} else if (0 == sysuserInst.getInstGrade()){
					companyshort = companyshort + SafeUtils.getString(sysuserInst.getCityName());
				}
			}
			bondQuote.setInstShort(companyshort);
		} else {
			bondQuote.setInstId(0);
			bondQuote.setInstType(0);
			bondQuote.setUserName("DM数据源");
		}

		bondQuote.setApproval(0);
		bondQuote.setTenor(0);
		bondQuote.setOpenQuote(1);
		bondQuote.setIsHideRate(0);
		if (null != bondQuote.getBondPrice() && bondQuote.getBondPrice().doubleValue() > 50) {
			bondQuote.setPriceUnit(2);
		} else {
			bondQuote.setPriceUnit(1);
		}
		bondQuote.setStatus(1);
		bondQuote.setSource(2);
		bondQuote.setTroopId("0");
		if (null != sendTime) {
			bondQuote.setSendTime(sendTime);
		} else {
			bondQuote.setSendTime(new Date());
		}
		bondQuote.setUpdateTime(new Date());
		bondQuote.setLastUpdateby("add by " + userId + ";");
		if (StringUtils.isNotBlank(quoteParam.getBondCode())) {
			String bondOrgCode = quoteParam.getBondCode().split("\\.")[0];
			bondQuote.setBondOrgCode(SafeUtils.getInteger(bondOrgCode));
		} else {
			bondQuote.setBondOrgCode(0);

		}
		bondQuote.setRawcontent(RawmsgUtil.generateRawmsg_Type9(bondQuote));
		bondQuote.setBrokerType(SafeUtils.getInteger(brokerType));

		return bondQuote;
	}

	private List<Long> saveBondQuotes(List<BondQuote> bondQuotes, SysuserAndOrgainfo sysuser) {
		List<Long> list = new ArrayList<>();

		if (null != bondQuotes && bondQuotes.size() > 0) {
			bondQuotes.forEach(quoteObj -> {
				Long bondId = saveBondQuote(quoteObj, sysuser.getImflag(), sysuser.getCompanyphone(),
						sysuser.getMobile(), sysuser.getQq());
				list.add(bondId);
			});

		}

		return list;
	}

	@Transactional
	public Long saveBondQuote(BondQuote bondQuote, Integer imFlag, String phone, String mobile, String qqNum) {
		Long bondId = 0L;
		
		calculateQuotePrices(bondQuote);
		
		if (null == bondQuote.getBrokerType()) {
			bondQuote.setBrokerType(0);
		}
		BondQuote entity = bondQuoteRepository.save(bondQuote);
		if (null != entity) {
			//放入MQ
			mqSenderService.sendBondQuoteDmmsRabbitMQ(JSON.toJSONString(bondQuote));
			
			bondId = entity.getId();
			saveBondQuoteDoc(entity, imFlag, phone, mobile, qqNum);
		}

		return bondId;
	}

	private void calculateQuotePrices(BondQuote bondQuote)
	{
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			Long bondUniCode = bondQuote.getBondUniCode();
			BigDecimal bondPrice = bondQuote.getBondPrice();
			Integer priceUnit = bondQuote.getPriceUnit();

			// 有报价且大于0
//			if (null == bondPrice || BigDecimal.ZERO.compareTo(bondPrice) != -1) {
//				return;
//			}
			
			Double cleanPrice = null;
			Double dirtyPrice = null;
			Double ytm = null;
			
			BondCashFlows quantUnit = bondQuantService.findBondCashFlowsById(bondUniCode);
			if (quantUnit == null) return;
			
			// 1=%;2=元
			switch (priceUnit) {
			case 1: //YTM
				cleanPrice = bondQuantService.calCleanPriceByYTM(quantUnit, bondPrice.doubleValue());
				dirtyPrice = bondQuantService.calDirtyPriceByCleanPrice(quantUnit, cleanPrice.doubleValue());
				ytm = bondPrice.doubleValue();
				break;
			case 2: //Clean Price
				cleanPrice = bondPrice.doubleValue();
				dirtyPrice = bondQuantService.calDirtyPriceByCleanPrice(quantUnit, cleanPrice);
				ytm = bondQuantService.calYTMByDirtyPrice(quantUnit, dirtyPrice);
				break;
			default:
				return;
			}
			
			bondQuote.setCleanPrice(new BigDecimal(cleanPrice).setScale(4, RoundingMode.HALF_EVEN));
			bondQuote.setDirtyPrice(new BigDecimal(dirtyPrice).setScale(4, RoundingMode.HALF_EVEN));
			bondQuote.setYtm(new BigDecimal(ytm).setScale(4, RoundingMode.HALF_EVEN));
			watch.stop();
			logger.debug("calculateQuotePrices using StopWatch in millis: " + 
					watch.getTotalTimeMillis() + ", bond name:" + quantUnit.getBond().getShortName());

		} catch (Exception ex) {
			logger.error(String.format("calculateQuotePrices BondUniCode[%d]", bondQuote.getBondUniCode()), ex);
		}
	}

	private void saveBondQuoteDoc(BondQuote bondQuote, Integer imFlag, String phone, String mobile, String qqNum) {
		BondQuoteDoc bondQuoteDoc = new BondQuoteDoc();
		BeanUtils.copyProperties(bondQuote, bondQuoteDoc);
		bondQuoteDoc.setQuoteId(bondQuote.getId());
		bondQuoteDoc.setSendTimeFormat(SafeUtils.convertDateToString(bondQuote.getSendTime(), SafeUtils.DATE_FORMAT));
		bondQuoteDoc.setSendTime(SafeUtils.convertDateToString(bondQuote.getSendTime(), SafeUtils.DATE_TIME_FORMAT1));
		if (bondQuote.getSide() == 1) {
			bondQuoteDoc.setBidPrice(bondQuote.getBondPrice());
			bondQuoteDoc.setBidVol(bondQuote.getBondVol());
			bondQuoteDoc.setOfrPrice(new BigDecimal(0));
			bondQuoteDoc.setOfrVol(new BigDecimal(0));
		} else if (bondQuote.getSide() == 2) {
			bondQuoteDoc.setBidPrice(new BigDecimal(0));
			bondQuoteDoc.setBidVol(new BigDecimal(0));
			bondQuoteDoc.setOfrPrice(bondQuote.getBondPrice());
			bondQuoteDoc.setOfrVol(bondQuote.getBondVol());
		}
		bondQuoteDoc.setImFlag(imFlag);
		bondQuoteDoc.setMobile(mobile);
		bondQuoteDoc.setPhone(phone);
		bondQuoteDoc.setQqNum(qqNum);
		bondQuoteDocRepository.save(bondQuoteDoc);
		//将其放在的我的持仓里
		addToGroup(bondQuote);
		this.publisher.publishEvent(bondQuoteDoc);
	}

	/**
	 * 将其放在的我的持仓里
	 */
	@Transactional
	private void addToGroup(BondQuote bondQuote){
		try {
			if(bondQuote == null) return ;
			if (bondQuote.getUserId() != null && bondQuote.getUserId() <=0)  return;
	
			Long userId = bondQuote.getUserId();
			Long bondUniCode = bondQuote.getBondUniCode();
			BondFavoriteGroup favoriteGroup = bondFavoriteGroupRepository.findByUserIdAndGroupType(SafeUtils.getInteger(userId), 0);
			Integer groupId = null;
			if(favoriteGroup == null){
				// 取消“我的持仓”
			    // favoriteService.addPositionGroupAsDefault(SafeUtils.getInteger(userId));
			}else{
				groupId = favoriteGroup.getGroupId();
			}
			
			handleBondFavorite(userId, bondUniCode, groupId);
		}
		catch (Exception ex) {
			logger.info("failed to add quote to portfolio", ex);
		}
	}

	private void handleBondFavorite(Long userId, Long bondUniCode, Integer groupId) {
		BondFavorite  bondFavorite = new BondFavorite();
		bondFavorite.setBondUniCode(bondUniCode);
		bondFavorite.setCreateTime(new Date());
		bondFavorite.setUpdateTime(new Date());
		bondFavorite.setGroupId(groupId);
		bondFavorite.setUserId(SafeUtils.getInteger(userId));
		bondFavorite.setIsDelete(0);
		bondFavorite.setOpeninterest(0);
		bondFavorite.setBookmark(0L);
		bondFavorite.setBookmarkUpdateTime(new Date());
		favoriteService.saveBondFavorite(bondFavorite);
	}

	/**
	 * saveBrokerBondQuote
	 * 
	 * @param params
	 * @return
	 */
	public Long saveBrokerBondQuote(BrokerBondQuoteParam brokerBuoteParam) {
		
		Long res = 0L;
		if(!StringUtils.isEmpty(redisUtil.get("BondQuote"+brokerBuoteParam.toString()))){
			return res;
		}else{
			redisUtil.saveMsgWithTimeout("BondQuote"+brokerBuoteParam.toString(),brokerBuoteParam.getBondPrice().toString(),SafeUtils.getRestTodayTime());
		}
		logger.info("saveBrokerBondQuote : "+brokerBuoteParam.toString());

		BondDetailDoc bondDetailDoc = bondDetailRepository.findByCode(brokerBuoteParam.getBondCode());

		if (null != bondDetailDoc ) { 
			if (StringUtils.isBlank(bondDetailDoc.getName())) {
				logger.info("saveBrokerBondQuote name is empty, BondCode:" + brokerBuoteParam.getBondCode());
				return res;
			}
			brokerBuoteParam.setBondUniCode(bondDetailDoc.getBondUniCode());
			brokerBuoteParam.setBondShortName(bondDetailDoc.getName());
			BondQuote bondQuote = handleBondQuoteWithParam(0L, null, brokerBuoteParam, brokerBuoteParam.getSendTime(), null, brokerBuoteParam.getBrokerType());
			res = saveBondQuote(bondQuote, 0, null, null, null);
			
			publisher.publishEvent(brokerBuoteParam);
		} else {
			logger.info("saveBrokerBondQuote BondCode is not in Sys, BondCode:" + brokerBuoteParam.getBondCode());
		}

		return res;
	}

	public List<BaseQuoteParam> addDemandText(Integer postfrom, Long userId, String content) {
		List<BaseQuoteParam> list = new ArrayList<>();

		parseDemandText(postfrom, userId, content, list);

		return list;
	}

	/**
	 * @param postfrom
	 * @param userId
	 * @param content
	 * @param list
	 */
	private void parseDemandText(Integer postfrom, Long userId, String content, List<BaseQuoteParam> list) {
		SysuserAndOrgainfo sysuser = sysuserDao.findSysuserOrgInfo(userId);

		if (null != sysuser) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("senderName", sysuser.getName());
			params.put("sendContent", content);
			params.put("inst_short", sysuser.getCompanyshort());
			params.put("postfrom", SafeUtils.getString(postfrom));

			String result = HttpClientUtil.doPost(pythonUrl, params);
			if (StringUtils.isNotBlank(result) && !"[]".equals(result)) {
				List<Node> nodelist = gson.fromJson(result, new TypeToken<List<Node>>() {
				}.getType());

				nodelist.forEach(node -> {
					int type = SafeUtils.getInt(node.getType());

					if (9 == type) {
						handleBondInfo(list, node);

					} else {
						logger.info("addDemandText type is not 9 but " + type + ",content : " + content);
					}
				});

			} else {
				throw new BusinessException("亲，您输入的需求无法识别，请重新输入！");
			}
		}
	}

	/**
	 * @param list
	 * @param node
	 */
	private void handleBondInfo(List<BaseQuoteParam> list, Node node) {
		BondBasicInfoDoc bondBasicInfo = bondBasicInfoRepository.findByOrgCode(node.getBond_code());
		if (null != bondBasicInfo) {
			BaseQuoteParam param = new BaseQuoteParam();
			param.setBondCode(bondBasicInfo.getCode());
			param.setBondUniCode(bondBasicInfo.getBondUniCode());
			param.setBondShortName(node.getBond_name());
			param.setSide(SafeUtils.getInteger(node.getSide()));
			if (StringUtils.isNotBlank(node.getBond_range_low())) {
				param.setBondPrice(new BigDecimal(node.getBond_range_low()));
			} else if (StringUtils.isNotBlank(node.getBond_yield_low())) {
				param.setBondPrice(new BigDecimal(node.getBond_yield_low()));
			}
			param.setBondVol(new BigDecimal(node.getAmount()));

			list.add(param);
		} else {
			logger.info("The bondCode[" + node.getBond_code() + "] is not in DM system.");
		}
	}

	/**
	 * findBestOffer
	 * 
	 * @return BondBestQuoteDoc
	 */
	public BondBestQuoteDoc findBestOffer(Long bondId) {

		BondBestQuoteDoc bbqDoc = new BondBestQuoteDoc();

		BondBestQuoteYieldDoc bbqyDoc = mongoOperations.findOne(new Query(Criteria.where("bondUniCode").is(bondId)
				.and("sendTime").is(SafeUtils.convertDateToString(new Date(), SafeUtils.DATE_FORMAT))), BondBestQuoteYieldDoc.class);

		if (null == bbqyDoc) {

			BondBestQuoteNetpriceDoc bbqnDoc = mongoOperations.findOne(new Query(Criteria.where("bondUniCode").is(bondId)
					.and("sendTime").is(SafeUtils.convertDateToString(new Date(), SafeUtils.DATE_FORMAT))), BondBestQuoteNetpriceDoc.class);

			if (null != bbqnDoc) {
				BeanUtils.copyProperties(bbqnDoc, bbqDoc);
			}
		} else {

			BeanUtils.copyProperties(bbqyDoc, bbqDoc);
		}

		return bbqDoc;
	}

	/**
	 * findQuotes
	 * 
	 * @param userId
	 * @param date
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<BondQuoteDoc> findQuotes(Long userId, Long bondId, Integer istaday, Integer page,
			Integer limit) {
		List<String> troopIds = sysuserDao.findTroopidListByUserid(userId);
		String today = SafeUtils.convertDateToString(new Date(), SafeUtils.DATE_FORMAT);

		PageRequest request = new PageRequest(page, limit, new Sort(Sort.Direction.DESC, "sendTime"));

		Query query = new Query();
		if (null != istaday && 1 == istaday) {
			if (null != troopIds && troopIds.size() > 0) {
				query.addCriteria(Criteria.where("status").is(1).and("bondUniCode").is(bondId).and("sendTimeFormat")
						.is(today).orOperator(Criteria.where("source").in(Constants.PUBLIC_SOURCE),
								Criteria.where("source").in(Constants.PRIVATE_SOURCE).and("troopId").in(troopIds))).with(request);
			} else {
				query.addCriteria(Criteria.where("status").is(1).and("bondUniCode").is(bondId).and("sendTimeFormat")
						.is(today).and("source").in(Constants.PUBLIC_SOURCE)).with(request);
			}
		} else {
			if (null != troopIds && troopIds.size() > 0) {
				query.addCriteria(Criteria.where("status").is(1).and("bondUniCode").is(bondId).and("sendTimeFormat")
						.ne(today).orOperator(Criteria.where("source").in(Constants.PUBLIC_SOURCE),
								Criteria.where("source").in(Constants.PRIVATE_SOURCE).and("troopId").in(troopIds))).with(request);
			} else {
				query.addCriteria(Criteria.where("status").is(1).and("bondUniCode").is(bondId).and("sendTimeFormat")
						.ne(today).and("source").in(Constants.PUBLIC_SOURCE)).with(request);
			}
		}

		List<BondQuoteDoc> content = mongoOperations.find(query, BondQuoteDoc.class);

		long total = mongoOperations.count(query, BondQuoteDoc.class);

		Page<BondQuoteDoc> pages = new PageImpl<>(content, new PageRequest(page, limit), total);

		return pages;
	}

	/**
	 * @param userId
	 * @param bondUniCode
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<BondQuoteDoc> findQuotesByBondUniCode(Long userId, Long bondUniCode, Integer side, Integer pageIndex,
			Integer pageSize) {
		List<String> troopIds = sysuserDao.findTroopidListByUserid(userId);

		PageRequest request = new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.DESC, "sendTime"));
		Query query = new Query();
		if (null != troopIds && troopIds.size() > 0) {
			query.addCriteria(Criteria.where("status").in(Constants.VALID_DEALED_STATUS).and("bondUniCode").is(bondUniCode).and("side").is(side)
					.orOperator(Criteria.where("source").in(Constants.PUBLIC_SOURCE),
							Criteria.where("source").in(Constants.PRIVATE_SOURCE).and("troopId").in(troopIds))).with(request);
		} else {
			query.addCriteria(Criteria.where("status").in(Constants.VALID_DEALED_STATUS).and("bondUniCode").is(bondUniCode).and("side").is(side)
					.and("source").in(Constants.PUBLIC_SOURCE)).with(request);
		}

		return mongoOperations.find(query, BondQuoteDoc.class);
	}

	/**
	 * @param quoteId
	 * @param action
	 * @return
	 */
	@Transactional
	public Integer handleQuoteStatus(Long quoteId, BondQuoteStatusParam params) {
		Integer result = 0;
		Integer statusAction = params.getAction();
		BondQuote bondQuote = bondQuoteRepository.findOne(quoteId);
		if (null != bondQuote) {
			bondQuote.setStatus(statusAction);
			int res = bondQuoteRepository.updateQuoteStatus(statusAction, quoteId);
			WriteResult wr = mongoOperations.updateFirst(new Query(Criteria.where("quoteId").is(quoteId)),
					Update.update("status", statusAction), BondQuoteDoc.class);
			if (res != 0 && wr.getN() != 0) {
				result = 1;
			} else {
				throw new BusinessException("更新失败");
			}
		} else {
			logger.error("handleQuoteStatus, this bondQuote is not in db, quoteId:" + quoteId);
			throw new BusinessException("该数据不存在");
		}

		return result;
	}

	/**
	 * @param quoteId
	 * @return
	 */
	public Long resendQuote(Long quoteId, BaseQuoteResendParam baseQuoteResendParam) {
		Long result = 0L;
		BondQuote bondQuote = bondQuoteRepository.findOne(quoteId);
		if (null != bondQuote) {
			result = handleResendQuote(quoteId, baseQuoteResendParam, bondQuote);

		} else {
			logger.error("resendQuote, this bondQuote is not in db.");
			throw new BusinessException("该数据不存在");
		}
		return result;
	}

	/**
	 * @param quoteId
	 * @param baseQuoteResendParam
	 * @param bondQuote
	 */
	private Long handleResendQuote(Long quoteId, BaseQuoteResendParam baseQuoteResendParam, BondQuote bondQuote) {
		switch(bondQuote.getStatus().intValue()){
		case 1:
			if (SafeUtils.isPastDate(bondQuote.getSendTime())) {
				//新报价
				quoteId = newResendedBondQuote(quoteId, baseQuoteResendParam, bondQuote);
			} else {
				//更新报价
				updateResendedBondQuote(quoteId, baseQuoteResendParam, bondQuote);
			}
			break;
		case 2:
		case 99:
			//新报价
			quoteId = newResendedBondQuote(quoteId, baseQuoteResendParam, bondQuote);
			break;

		default:
			break;
		}
		return quoteId;
	}

	/**
	 * @param quoteId
	 * @param baseQuoteResendParam
	 * @param bondQuote
	 */
	private void updateResendedBondQuote(Long quoteId, BaseQuoteResendParam baseQuoteResendParam, BondQuote bondQuote) {
		if (StringUtils.isBlank(messageValidation.getQuotePriceFlag(quoteId, baseQuoteResendParam))) {
			updateResendedQuote(bondQuote, baseQuoteResendParam);
			messageValidation.saveQuotePrice(bondQuote.getId(), baseQuoteResendParam);
		} else {
			throw new BusinessException("10分钟内不能重发相同报价，请稍后再试！");
		}
	}

	/**
	 * @param quoteId
	 * @param baseQuoteResendParam
	 * @param bondQuote
	 */
	private Long newResendedBondQuote(Long quoteId, BaseQuoteResendParam baseQuoteResendParam, BondQuote bondQuote) {
		Long newquoteId = 0L;
		if (StringUtils.isBlank(messageValidation.getQuotePriceFlag(quoteId, baseQuoteResendParam))) {
			newquoteId = saveResendedQuote(bondQuote, baseQuoteResendParam);
			messageValidation.saveQuotePrice(newquoteId, baseQuoteResendParam);
		} else {
			throw new BusinessException("10分钟内不能重发相同报价，请稍后再试！");
		}
		return newquoteId;
	}

	/**
	 * @param quoteId
	 * @param baseQuoteResendParam
	 */
	@Transactional
	private Long saveResendedQuote(BondQuote bondQuote, BaseQuoteResendParam baseQuoteResendParam) {
		Long res = 0L;
		SysuserAndOrgainfo sysuser = sysuserDao.findSysuserOrgInfo(bondQuote.getUserId());
		if (null != sysuser) {
			BondQuote newBondQuote = handleResendedQuoteWithParam(baseQuoteResendParam, bondQuote);

			res = saveBondQuote(newBondQuote, sysuser.getImflag(), sysuser.getCompanyphone(), sysuser.getMobile(),
					sysuser.getQq());
		}
		return res;
	}

	/**
	 * @param bondQuote
	 * @param baseQuoteResendParam
	 */
	@Transactional
	private void updateResendedQuote(BondQuote bondQuote, BaseQuoteResendParam baseQuoteResendParam) {
		Date updatedate = new Date();
		BeanUtils.copyProperties(baseQuoteResendParam, bondQuote);
		if (null != baseQuoteResendParam.getBondPrice() && baseQuoteResendParam.getBondPrice().doubleValue() > 50) {
			bondQuote.setPriceUnit(2);
		} else {
			bondQuote.setPriceUnit(1);
		}
		bondQuote.setSendTime(updatedate);
		bondQuote.setUpdateTime(updatedate);
		if (null == bondQuote.getBrokerType()) {
			bondQuote.setBrokerType(0);
		}
		BondQuote entity = bondQuoteRepository.save(bondQuote);

		if (null != entity) {
			BondQuoteDoc bondQuoteDoc = bondQuoteDocRepository.findByQuoteId(entity.getId());
			bondQuoteDoc.setSide(entity.getSide());
			bondQuoteDoc.setSendTimeFormat(SafeUtils.convertDateToString(entity.getSendTime(), SafeUtils.DATE_FORMAT));
			bondQuoteDoc.setSendTime(SafeUtils.convertDateToString(entity.getSendTime(), SafeUtils.DATE_TIME_FORMAT1));
			if (bondQuote.getSide() == 1) {
				bondQuoteDoc.setBidPrice(entity.getBondPrice());
				bondQuoteDoc.setBidVol(entity.getBondVol());
				bondQuoteDoc.setOfrPrice(new BigDecimal(0));
				bondQuoteDoc.setOfrVol(new BigDecimal(0));
			} else if (bondQuote.getSide() == 2) {
				bondQuoteDoc.setBidPrice(new BigDecimal(0));
				bondQuoteDoc.setBidVol(new BigDecimal(0));
				bondQuoteDoc.setOfrPrice(entity.getBondPrice());
				bondQuoteDoc.setOfrVol(entity.getBondVol());
			}
			bondQuoteDocRepository.save(bondQuoteDoc);
		}
	}

	/**
	 * @param baseQuoteResendParam
	 * @param sendDate
	 * @param bondQuote
	 * @return
	 */
	private BondQuote handleResendedQuoteWithParam(BaseQuoteResendParam baseQuoteResendParam, BondQuote bondQuote) {
		Date sendDate = new Date();
		BondQuote newBondQuote = new BondQuote();
		int side = SafeUtils.getInt(baseQuoteResendParam.getSide());
		newBondQuote.setSide(side);
		newBondQuote.setBondVol(baseQuoteResendParam.getBondVol());
		newBondQuote.setBondPrice(baseQuoteResendParam.getBondPrice());
		newBondQuote.setSendTime(sendDate);
		newBondQuote.setUpdateTime(sendDate);
		String remark = baseQuoteResendParam.getRemark();
		if (StringUtils.isNotBlank(remark)) {
			newBondQuote.setRemark(remark);
		}
		newBondQuote.setPostfrom(baseQuoteResendParam.getPostfrom());

		newBondQuote.setAnonymous(bondQuote.getAnonymous());
		newBondQuote.setApproval(bondQuote.getApproval());
		newBondQuote.setBondCode(bondQuote.getBondCode());
		newBondQuote.setBondOrgCode(bondQuote.getBondOrgCode());
		newBondQuote.setBondUniCode(bondQuote.getBondUniCode());
		newBondQuote.setBondShortName(bondQuote.getBondShortName());
		newBondQuote.setInstId(bondQuote.getInstId());
		newBondQuote.setInstShort(bondQuote.getInstShort());
		newBondQuote.setInstType(bondQuote.getInstType());
		newBondQuote.setIsHideRate(bondQuote.getIsHideRate());
		newBondQuote.setLastUpdateby(bondQuote.getLastUpdateby() + "resend by" + bondQuote.getUserId() + ";");
		newBondQuote.setMsgId(bondQuote.getMsgId());
		newBondQuote.setNaturalmsg(bondQuote.getNaturalmsg());
		newBondQuote.setOpenQuote(bondQuote.getOpenQuote());
		if (null != baseQuoteResendParam.getBondPrice() && baseQuoteResendParam.getBondPrice().doubleValue() > 50) {
			newBondQuote.setPriceUnit(2);
		} else {
			newBondQuote.setPriceUnit(1);
		}
		newBondQuote.setQqNum(bondQuote.getQqNum());
		String quoteIdStr = Constants.QUOTEID_PREFIX + SafeUtils.getRandomNum(2)
		+ SafeUtils.convertDateToString(new Date(), SafeUtils.DATE_TIME_FORMAT2)
		+ SafeUtils.getRandomNum(8);
		newBondQuote.setQuoteId(quoteIdStr);
		newBondQuote.setRawcontent(bondQuote.getRawcontent());
		newBondQuote.setSource(bondQuote.getSource());
		newBondQuote.setStatus(1);
		newBondQuote.setSubType(bondQuote.getSubType());
		newBondQuote.setTenor(bondQuote.getTenor());
		newBondQuote.setUserId(bondQuote.getUserId());
		newBondQuote.setUserName(bondQuote.getUserName());
		newBondQuote.setWechatno(bondQuote.getWechatno());
		newBondQuote.setTroopId(bondQuote.getTroopId());
		newBondQuote.setTroopName(bondQuote.getTroopName());
		return newBondQuote;
	}

	/**
	 * @param userId
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<BondQuoteInfoVo> findOwnquotes(Long userId, Integer status, Integer page, Integer limit) {
		List<BondQuoteInfoVo> list = new ArrayList<>();
		Query query = new Query();

		PageRequest request = new PageRequest(page, limit, new Sort(Sort.Direction.DESC, "sendTime"));
		if (status > 0) {
			query.addCriteria(Criteria.where("status").is(status).and("userId").is(userId)).with(request);
		} else {
			query.addCriteria(Criteria.where("userId").is(userId)).with(request);
		}

		long total = mongoOperations.count(query, BondQuoteDoc.class);

		List<BondQuoteDoc> bondQuoteDocs = mongoOperations.find(query, BondQuoteDoc.class);

		bondQuoteDocs.forEach(bondQuoteDoc -> {
			BondQuoteInfoVo bondQuoteInfoVo = new BondQuoteInfoVo();
			BeanUtils.copyProperties(bondQuoteDoc, bondQuoteInfoVo);
			bondQuoteInfoVo.setName(bondQuoteDoc.getBondShortName());
			bondQuoteInfoVo.setCode(bondQuoteDoc.getBondCode());
			bondQuoteInfoVo.setSendDatetime(bondQuoteDoc.getSendTime());
			bondQuoteInfoVo.setBondId(bondQuoteDoc.getBondUniCode());
			//bondDetailRepository.findOne(bondQuoteDoc.getBondUniCode());
			BondDetailDoc bondDetail = bondDetailRepository.findByBondUniCode(bondQuoteDoc.getBondUniCode());
			if (null != bondDetail) {
				//利率债违约概率不显示
				int dmBondType = bondDetail.getDmBondType();
				bondQuoteInfoVo.setPd(bondDetail.getPd());
				bondQuoteInfoVo.setPdTime(bondDetail.getPdTime());
				if (null != bondDetail.getPdDiff()) {
					bondQuoteInfoVo.setPdDiff(bondDetail.getPdDiff());
				}
				bondQuoteInfoVo.setWorstPd(bondDetail.getWorstPd());
				bondQuoteInfoVo.setWorstPdNum(bondDetail.getWorstPdNum());
				bondQuoteInfoVo.setWorstPdTime(bondDetail.getWorstPdTime());
				bondQuoteInfoVo.setWorstRiskWarning(bondDetail.getWorstRiskWarning());
				bondQuoteInfoVo.setDefaultBondName(bondDetail.getDefaultBondName());
				bondQuoteInfoVo.setDefaultDate(bondDetail.getDefaultDate());
				bondQuoteInfoVo.setDefaultEvent(bondDetail.getDefaultEvent());
				bondQuoteInfoVo.setRiskWarning(bondDetail.getRiskWarning());
				bondQuoteInfoVo.setTenor(bondDetail.getTenor());
				bondQuoteInfoVo.setPoNegtive(bondDetail.getPoNegtive());
				bondQuoteInfoVo.setPoNeutral(bondDetail.getPoNeutral());
				bondQuoteInfoVo.setPoPositive(bondDetail.getPoPositive());
				bondQuoteInfoVo.setPoCount(bondDetail.getSentimentMonthCount());
			}

			list.add(bondQuoteInfoVo);
		});

		Page<BondQuoteInfoVo> pages = new PageImpl<>(list, new PageRequest(page, limit), total);

		return pages;
	}

	/**
	 * @param quoteId
	 * @return
	 */
	public BondQuote findBondQuoteById(Long quoteId) {
		return bondQuoteRepository.findOne(quoteId);
	}

	/**
	 * @param id
	 * @param status
	 */
	@Transactional
	public void updateBondQuoteStatusFromNorData(String quoteId, String status) {
		BondQuote bondQuote = bondQuoteRepository.findByQuoteId(quoteId);
		bondQuote.setStatus(SafeUtils.getInteger(status));
		bondQuote.setUpdateTime(new Date());
		bondQuote.setLastUpdateby(SafeUtils.getString(bondQuote.getLastUpdateby())+"update status;");
		if (null == bondQuote.getBrokerType()) {
			bondQuote.setBrokerType(0);
		}
		BondQuote entity = bondQuoteRepository.save(bondQuote);
		if (null != entity) {
			updateBondQuoteDocStatus(entity.getId(), SafeUtils.getInteger(status));
		}
	}

	/**
	 * @param id
	 * @param integer
	 */
	private void updateBondQuoteDocStatus(Long id, Integer status) {
		BondQuoteDoc bondQuoteDoc = bondQuoteDocRepository.findOne(id);
		if (null != bondQuoteDoc) {
			bondQuoteDoc.setStatus(status);
			bondQuoteDocRepository.save(bondQuoteDoc);
		}
	}

	/**
	 * @param oldQuoteId
	 * @return
	 */
	public BondQuote findBondQuoteByQuoteId(String oldQuoteId) {
		return bondQuoteRepository.findByQuoteId(oldQuoteId);
	}

	public List<BondDealDataDoc> getDealsByBondUniCode(Long userId, Long bondUniCode, int pageIndex, Integer pageSize) {
		PageRequest request = new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.DESC, "createTime"));
		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(bondUniCode)).with(request);
		return mongoOperations.find(query, BondDealDataDoc.class);
	}
}