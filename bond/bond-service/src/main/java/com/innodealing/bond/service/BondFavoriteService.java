package com.innodealing.bond.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StopWatch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.innodealing.bond.param.BondFavoriteParam;
import com.innodealing.bond.param.BondFavoriteUpdateList;
import com.innodealing.bond.param.BondFavorityAddList;
import com.innodealing.bond.param.BondFavorityGroupUpdateReq;
import com.innodealing.bond.vo.favorite.BondExportFavorite;
import com.innodealing.bond.vo.favorite.BondFavoriteGroupVo;
import com.innodealing.bond.vo.favorite.BondFavoriteVo;
import com.innodealing.bond.vo.msg.BondNotificationMsgVo;
import com.innodealing.bond.vo.msg.BondNotificationStatistic;
import com.innodealing.bond.vo.msg.BondNotificationStatisticsVo;
import com.innodealing.bond.vo.msg.BondNotificationsVo;
import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bond.BondFavoriteDao;
import com.innodealing.engine.jpa.dm.BondFavoriteGroupRepository;
import com.innodealing.engine.jpa.dm.BondFavoriteRadarSchemaRepository;
import com.innodealing.engine.jpa.dm.BondFavoriteRepository;
import com.innodealing.engine.jpa.dm.BondImpliedRatingRepository;
import com.innodealing.engine.jpa.dm.UserOprRecordRepository;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.dm.bond.BondFavoriteGroup;
import com.innodealing.model.dm.bond.BondFavoriteRadarSchema;
import com.innodealing.model.dm.bond.BondImpliedRating;
import com.innodealing.model.dm.bond.BondNotificationMsg;
import com.innodealing.model.dm.bond.UserOprRecord;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondFavoriteDoc;
import com.innodealing.model.mongo.dm.BondMsgUserStatusDoc;
import com.innodealing.model.mongo.dm.BondNotificationCardMsgDoc;
import com.innodealing.model.mongo.dm.BondNotificationMsgDoc;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

/**
 * @author Administrator
 *
 */
@Service
public class BondFavoriteService {

	private final static Logger LOG = LoggerFactory.getLogger(BondFavoriteService.class);

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	BondFavoriteRepository favoriteReposityory;
	
	@Autowired
	BondFavoriteGroupRepository favoriteGroupReposityory;
	
	@Autowired
	UserOprRecordRepository userOprRecordRepository;

	@Autowired
	BondImpliedRatingRepository impliedRatingRepository;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired 
	protected BondInduService induService;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	 
	@Autowired
	BondFavoriteDao bondFavoriteDao;
	
	@Autowired
	Gson gson;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@Autowired
	BondCityService cityService;
	
	@Autowired
	BondComExtService bondComExtService;

	private @Autowired
	BondFavoriteRadarSchemaRepository bondFavRadarSchemaRepo;
	
	//////////////////////////////////////////////
	///////// Favorite Group/////////////////////////
	
    public synchronized void addPositionGroupAsDefault(Integer userId)
    {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            try {
                int num = favoriteGroupReposityory.countFavoriteGroupByUserIdAndGrouptype(userId, 0);
                if (num <= 0) {
                    BondFavoriteGroup positionGroup = new BondFavoriteGroup();
                    positionGroup.setUserId(userId);
                    positionGroup.setGroupType(0);
                    positionGroup.setCreateTime(new Date());
                    positionGroup.setUpdateTime(new Date());
                    positionGroup.setGroupName("我的持仓");
                    positionGroup.setIsDelete(0);
                    positionGroup.setNotifiedEnable(1);
                    positionGroup.setEmailEnable(0);
                    gson.toJson(Constants.EVENMSG_TYPE_LISTS);
                    positionGroup.setNotifiedEventtype(gson.toJson(Constants.EVENMSG_TYPE_LISTS));
                    return favoriteGroupReposityory.save(positionGroup);
                }
            } 
            catch (JpaSystemException ex) {
                if (ex.getCause() != null && ex.getCause().getCause() != null) {
                    if (ex.getCause().getCause() instanceof SQLException) {
                        SQLException sqlEx = (SQLException)ex.getCause().getCause();
                        if (sqlEx.getSQLState().equals("45000")) {
                            LOG.info("添加默认持仓投资组合有重复， userId:" + userId); 
                            return null;
                        }
                    }
                }
                throw ex;
            }
            return null;
        });
    }

	public List<BondFavoriteGroupVo> findFavoriteGroupsByUserId(Integer userId) {
		// 取消“我的持仓”
	    // addPositionGroupAsDefault(userId);

		List<BondFavoriteGroupVo> reslist = new ArrayList<>();
		List<BondFavoriteGroup> result = favoriteGroupReposityory.findByUserId(userId);
		
		result.stream().forEach(favoriteGroup -> {
			BondFavoriteGroupVo bfgvo = new BondFavoriteGroupVo();
			BeanUtils.copyProperties(favoriteGroup, bfgvo, "notifiedEventtype");
			if (StringUtils.isNotBlank(favoriteGroup.getNotifiedEventtype())) {
				bfgvo.setNotifiedEventtype(gson.fromJson(favoriteGroup.getNotifiedEventtype(), new TypeToken<List<Integer>>(){}.getType()));
			}
			long bondCount = SafeUtils.getLong(favoriteReposityory.getFavoriteCountByGroupIdAndUserId(favoriteGroup.getGroupId(), favoriteGroup.getUserId()));
			bfgvo.setBondCount(bondCount);
			
			if (favoriteGroup.getNotifiedEnable() == 1) {
				Long count = this.findBondFavGroupEventMsgCount(userId, favoriteGroup.getGroupId(), bfgvo.getNotifiedEventtype());
				bfgvo.setEventMsgCount(count);
			}
			
			reslist.add(bfgvo);
		});
		
		return reslist;
	}

	public List<BondFavorite> findFavoriteByGroupId(Integer groupId) {
		return favoriteReposityory.findByGroupId(groupId);
	}
	
	public List<BondFavorite> findFavoriteByGroupIdAndLimit(Integer groupId, Integer index, Integer limit) {
		return favoriteReposityory.findByGroupIdAndLimit(groupId, index, limit);
	}

	public List<BondFavorite> findFavoriteByUserId(Integer userId) {
		return favoriteReposityory.findByUserId(userId);
	}

	public List<BondFavorite> findFavoriteByBondUniCode(Long bondUniCode) {
		return favoriteReposityory.findByBondUniCode(bondUniCode);
	}
	
	public List<BondFavorite> findFavoriteByGroupIdAndBondUniCode(Integer groupId, Long bondUniCode) {
		return favoriteReposityory.findFavoriteByGroupIdAndBondUniCode(groupId, bondUniCode);
	}

	public int getFavoriteCountByGroupId(Integer groupId){
		return favoriteReposityory.getFavoriteCountByGroupId(groupId);
	}
	
	public Boolean isValidGroupName(String groupName) {
		// return favoriteReposityory.findByGroupId(groupId);
		return true;
	}

	@Transactional
	public BondFavoriteGroupVo addFavoriteGroup(BondFavoriteGroup favoriteGroup) {
		BondFavoriteGroupVo bfGroupVo = new BondFavoriteGroupVo();
		try {
			BondFavoriteGroup groupResult = favoriteGroupReposityory.save(favoriteGroup);
			if (null != groupResult) {
				BeanUtils.copyProperties(groupResult, bfGroupVo, "notifiedEventtype");
				if (StringUtils.isNotBlank(groupResult.getNotifiedEventtype())) {
					bfGroupVo.setNotifiedEventtype(gson.fromJson(groupResult.getNotifiedEventtype(), new TypeToken<List<Integer>>(){}.getType()));
				}
				
				long bondCount = SafeUtils.getLong(favoriteReposityory.getFavoriteCountByGroupIdAndUserId(groupResult.getGroupId(), groupResult.getUserId()));
				bfGroupVo.setBondCount(bondCount);
				
				if (groupResult.getNotifiedEnable() == 1) {
					Long count = this.findBondFavGroupEventMsgCount(groupResult.getUserId(), groupResult.getGroupId(), bfGroupVo.getNotifiedEventtype());
					bfGroupVo.setEventMsgCount(count);
				}
			}
		} catch (Exception ex) {
			// LOG.error("failed to addFavoriteGroup", ex);
			// ex.printStackTrace();
			throw ex;
		}
		return bfGroupVo;
	}

	@Transactional
	public BondFavoriteGroup updateFavoriteGroup(BondFavoriteGroup favoriteGroup, BondFavorityGroupUpdateReq req) {
		favoriteGroup.setGroupName(req.getGroupName());
		favoriteGroup.setIsDelete(0);
		favoriteGroup.setUpdateTime(new Date());
		favoriteGroup.setNotifiedEnable(req.getNotifiedEnable());
		if (null != req.getNotifiedEventtypes() && req.getNotifiedEventtypes().size() > 0) {
			favoriteGroup.setNotifiedEventtype(gson.toJson(req.getNotifiedEventtypes()));
		}else{
			favoriteGroup.setNotifiedEventtype(null);
		}
		
		BondFavoriteGroup entity = favoriteGroupReposityory.save(favoriteGroup);
		
		List<Long> list = bondFavoriteDao.findDifbondIdByUserId(entity.getUserId());
		if (null == list || list.size() == 0) {
			publisher.publishEvent(entity.getUserId());
		}
		
		return entity;
	}

	@Transactional
	public void deleteFavoriteGroup(Integer groupId) {
		favoriteReposityory.deleteByGroupId(groupId);
		favoriteGroupReposityory.deleteByGroupId(groupId);
		LOG.info("deleteFavoriteGroup method groupId="+groupId);
	}

	///////// Favorite/////////////////////////
	@Transactional
	public void deleteFavorite(Integer favoriteId) {
		favoriteReposityory.deleteByFavoriteId(favoriteId);
	}

	@Transactional
	public void addFavorite(BondFavorite favorite) {
		favoriteReposityory.save(favorite);
	}


	@Transactional
	public void updateFavoriteByfavoriteId(Date date, Integer favoriteId) {
		favoriteReposityory.updateByFavoriteId(date, favoriteId);
	}
	
	
	public void updateFavoriteByGroupIdAndBondUniCode(List<BondFavorite> list) {
		Date updateDt = new Date();
		list.stream().forEach(bondFavorite -> {
			updateFavoriteDatetime(updateDt, bondFavorite);
		});
	}

	/**
	 * @param updateDt
	 * @param bondFavorite
	 */
	@Transactional
	protected void updateFavoriteDatetime(Date updateDt, BondFavorite bondFavorite) {
		bondFavorite.setUpdateTime(updateDt);
		this.addFavorite(bondFavorite);
	}

	@Transactional
	public void deleteFavorite(Integer userId, Integer favoriteId) {
		LOG.info("deleteFavorite userId="+userId+", favoriteId="+favoriteId);
		favoriteReposityory.deleteByFavoriteId(favoriteId);
	}
	
	@Transactional
	public void updateBondStatusByBondUniCode(Date date, Long bondUniCode, long id) {
		Query query = new Query();
		Update update = new Update();
		query.addCriteria(Criteria.where("bondUniCode").is(bondUniCode));
		update.addToSet("bookmark", id);
		update.addToSet("bookmarkUpdateTime", date);
		mongoOperations.updateFirst(query, update, BondFavoriteDoc.class);
	}
	
	public Long findBondNumsByBondUniCode(Long bondUniCode) {
		BondFavoriteDoc bf = findBoodByBondUniCode(bondUniCode);
		Long result = null;
		if (null != bf) {
			Query query = new Query();

			Criteria criteria = new Criteria().andOperator(Criteria.where("bondId").is(bondUniCode),
					Criteria.where("id").gt(bf.getBookmark()));
			query.addCriteria(criteria);
			result = mongoOperations.count(query, BondNotificationMsg.class);
		}
		return result;
	}


	public BondFavoriteDoc findBoodByBondUniCode(Long bondUniCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(bondUniCode));
		query.fields().exclude("bookmark").exclude("bookmarkUpdateTime");
		mongoOperations.findOne(query, BondFavorite.class);
		BondFavoriteDoc bf = mongoOperations.findOne(query, BondFavoriteDoc.class);
		return bf;
	}

	public List<BondFavoriteDoc> findFavoriteDoc(BondFavoriteDoc favoriteDoc) {
		Query query = new Query();
		Criteria criteria = new Criteria();

		criteria.andOperator(Criteria.where("userId").is(favoriteDoc.getUserId()),
				Criteria.where("groupId").is(favoriteDoc.getGroupId()),
				Criteria.where("bondUniCode").is(favoriteDoc.getBondUniCode()));
		query.addCriteria(criteria);
		return mongoOperations.find(query, BondFavoriteDoc.class);
	}
	
	public void save(BondFavoriteDoc favorite) {
		mongoOperations.save(favorite);
	}

	private Long findBondFavGroupEventMsgCount(Integer userId, Integer groupId, List<Integer> eventTypes) {
		Long totalNum = 0L;
		Long unreadcount = bondFavoriteDao.getMsgCountByUserIdAndGroupIdAndEventTypes(userId, groupId, eventTypes);
		Long readCount = getTotalReadBondEventMsgCount(userId, groupId);
		
		totalNum = unreadcount - readCount;
		if (totalNum < 0) {
			totalNum = 0L;
		}
		
		LOG.info("findBondFavGroupEventMsgCount userId:"+userId+",groupId:"+groupId+",totalNum:"+totalNum);
		return totalNum;
	}

	public Long findBondEventMsgCount(Integer userId, Long bondId, Long bookmark, Date createTime, List<Integer> eventTypes) {
		Long unreadMsgCount = getUnreadBondEventMsgCount(bondId, bookmark, createTime, eventTypes);
		Long readMsgCount = getReadBondEventMsgCount(bookmark, userId, bondId);
		
		unreadMsgCount = unreadMsgCount - readMsgCount;
		if (unreadMsgCount < 0) {
			unreadMsgCount = 0L;
		}
		
		LOG.info("findBondEventMsgCount bookmark:"+bookmark+",unreadMsgCount:"+unreadMsgCount+",readMsgCount:"+readMsgCount);
		return unreadMsgCount;
	}
	
	private Long getTotalReadBondEventMsgCount(Integer userId, Integer groupId){
		Long readCount = 0L;
		List<Long> bondIds = bondFavoriteDao.findDifbondIdByUserIdAndGroupId(userId, groupId);
		if (null != bondIds && bondIds.size() > 0 ) {
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId).and("bondId").in(bondIds));
			
			List<BondMsgUserStatusDoc> bmusDocs = mongoOperations.find(query, BondMsgUserStatusDoc.class);
			if (null != bmusDocs) {
				for(BondMsgUserStatusDoc bmusDoc : bmusDocs){
					BondFavorite bondFav = favoriteReposityory.findByUserIdAndGroupIdAndBondUniCode(userId, groupId, bmusDoc.getBondId());
					if (null != bondFav && bondFav.getBookmark() < bmusDoc.getNotifyMsgId() ) {
						readCount ++;
					}
				}
			}
		}
		
		return readCount;
	}
	
	private Long getUnreadBondEventMsgCount(Long bondId, Long bookmark, Date createTime, List<Integer> eventTypes) {
		Long totalNum = 0L;
		Query query = new Query();
		query.addCriteria(Criteria.where("bondId").is(bondId).and("id").gt(bookmark)
		        .and("createTime").gt(createTime));
		if (null != eventTypes && eventTypes.size() > 0) {
			query.addCriteria(Criteria.where("eventType").in(eventTypes));
		}
		totalNum = mongoOperations.count(query, BondNotificationMsgDoc.class);
		
		LOG.info("getUnreadBondEventMsgCount totalNum:"+totalNum);
		return totalNum;
	}
	
	private Long getReadBondEventMsgCount(Long bookmark, Integer userId, Long bondId) {
		Long totalNum = 0L;
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("bondId").is(bondId).and("notifyMsgId").gt(bookmark));
		totalNum = mongoOperations.count(query, BondMsgUserStatusDoc.class);
		
		LOG.info("findReadBondEventMsgCount totalNum:"+totalNum);
		return totalNum;
	}
	
	public Long findAvailableBondEventMsgCount(Long bondId, Long bookmark, Date createTime, List<Integer> typeList) {
		Long totalNum = 0L;
		Query query = new Query();
		query.addCriteria(Criteria.where("bondId").is(bondId).and("createTime").gt(createTime));
		if (null != bookmark && bookmark > 0) {
			query.addCriteria(Criteria.where("id").lte(bookmark));
		}
		if (null != typeList && typeList.size() > 0) {
			query.addCriteria(Criteria.where("eventType").in(typeList));
		}
		totalNum = mongoOperations.count(query, BondNotificationMsgDoc.class);
		
		LOG.info("findAvailableBondEventMsgCount bookmark:"+bookmark+",totalNum:"+totalNum);
		return totalNum;
	}

	public Integer updateFavorite(Integer favoriteId, BondFavoriteParam param) {
		BondFavorite bondFavorite = favoriteReposityory.findOne(favoriteId);
		
		if (null != bondFavorite) {
			if (param != null) {
				Integer openinst = param.getOpeninterest();
				String rmk = param.getRemark();
				
				if (null != openinst && openinst > 0) {
					bondFavorite.setOpeninterest(openinst);
				}
				if (StringUtils.isNotBlank(rmk)) {
					bondFavorite.setRemark(rmk);
				}
				favoriteReposityory.save(bondFavorite);
			}
		}
		
		return favoriteId;
	}

	public List<BondFavorite> addFavoriteBatch(Integer userId, BondFavorityAddList addList) {
		List<BondFavorite> resList = new ArrayList<>();
        List<Integer> groupIds = addList.getGroupId();
        
        groupIds.stream().forEach(groupId -> {
            List<BondFavorite> bondFavoriteList = favoriteReposityory.findByGroupId(groupId);
            List<Long> bondUniCodes = new ArrayList<>();
            bondFavoriteList.stream().forEach(bondFavorite -> {
            	bondUniCodes.add(bondFavorite.getBondUniCode());
            });
            
            bondFavoriteList.clear();
            addList.getBondIds().stream().forEach(bondId -> {
            	if (!bondUniCodes.contains(bondId)) {
            		BondFavorite favorite = new BondFavorite();
            		favorite.setBondUniCode(bondId);
            		favorite.setGroupId(groupId);
            		favorite.setUserId(userId);
            		favorite.setIsDelete(0);
            		favorite.setCreateTime(new Date());
            		favorite.setUpdateTime(new Date());
            		favorite.setBookmark(0L);
            		favorite.setBookmarkUpdateTime(new Date());
            		favorite.setOpeninterest(0);
            		favorite.setRemark("");
            	
            		bondFavoriteList.add(favorite);
            		resList.add(favorite);
    			}else{
    				List<BondFavorite> bondFavorites = findFavoriteByGroupIdAndBondUniCode(groupId, bondId);
    				this.updateFavoriteByGroupIdAndBondUniCode(bondFavorites);
    				resList.addAll(bondFavorites);
    			}
            });
    		
            if (bondFavoriteList.size() > 0) {
            	favoriteReposityory.save(bondFavoriteList);
    		}
        });
        
		return resList;
	}

	public List<BondExportFavorite> exportFavoritesByGroupId(Integer userId, Integer groupId) {
		SimpleDateFormat rateWritDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<BondExportFavorite> list = new ArrayList<>();
		List<BondFavorite> favList = findFavoriteByGroupId(groupId);
		for (BondFavorite fav : favList){
			BondExportFavorite exp = new BondExportFavorite();
			exp.setOpenInterest(fav.getOpeninterest()!= null? 
					fav.getOpeninterest().toString():null );
			
			Long comUniCode = null;
			{
				BondBasicInfoDoc bond = mongoOperations.findOne(
						new Query(Criteria.where("_id").is(fav.getBondUniCode())), 
						BondBasicInfoDoc.class);
				if (bond != null) {
					exp.setIssAttr(UIAdapter.cvtComAttr2UIStr(bond.getComAttrPar()));
					exp.setIssClass(bond.getIssCls());
					exp.setIssuer(bond.getIssuer());
					exp.setIsGuar(StringUtils.isBlank(bond.getGuruName()) ?"否":"是");
					exp.setRegion(bond.getGuruName());
					exp.setBondCredRating(bond.getBondCredLevel());
					exp.setBondRateOrgName(bond.getBondRateOrgName());
					if(bond.getBondRateWritDate() != null)
						exp.setBondCredRatingDate(rateWritDateFormat.format(bond.getBondRateWritDate()));
					comUniCode = bond.getIssuerId();
				}
			}
			{
				BondDetailDoc bond = mongoOperations.findOne(
						new Query(Criteria.where("_id").is(fav.getBondUniCode())), 
						BondDetailDoc.class);
				if (bond != null) {
					exp.setBondCode(bond.getCode());
					exp.setBondCredRating(bond.getBondRating());
					exp.setIssCredRating(bond.getIssRating());
					exp.setBondName(bond.getName());
					exp.setInduName(induService.isGicsInduClass((long)userId)? bond.getInduName():bond.getInduNameSw());
					exp.setIsMunicipal(bond.getMunInvest()? "是":"否");
					exp.setPd(bond.getPd());
					exp.setPdDate(bond.getPdTime());
					exp.setWorstPd(bond.getWorstPd());
					exp.setWorstPdDate(bond.getWorstPdTime());
				}
			}
			if (comUniCode != null)
			{
				BondComInfoDoc com = mongoOperations.findOne(
						new Query(Criteria.where("_id").is(comUniCode)), 
						BondComInfoDoc.class);
				if (com != null) {
					exp.setRegion(com.getAreaName1());
					exp.setIssCredRating(com.getIssCredLevel());
					exp.setRatePros(com.getRateProsPar());
					if(com.getRateWritDate() != null)
						exp.setIssCredRatingDate(rateWritDateFormat.format(com.getRateWritDate()));
				}
				
				//资产负债表计算
				Map<String, Object> mapdata = bondComExtService.getDebtAssetByIssuerId(SafeUtils.getString(comUniCode));
				if (null != mapdata) {
					BigDecimal totalLiabilities = new BigDecimal(0);
					BigDecimal totalAssets = new BigDecimal(0);
					if (null != mapdata.get("totalLiabilities")) {
						totalLiabilities = (BigDecimal) mapdata.get("totalLiabilities");
					}
					if (null != mapdata.get("totalLiabilities")) {
						totalAssets = (BigDecimal) mapdata.get("totalAssets");
					}
					if (totalAssets.doubleValue() != 0) {
						BigDecimal debtAssetRatio = totalLiabilities.divide(totalAssets, 2, RoundingMode.HALF_UP);
						exp.setDebtAssetRatio(debtAssetRatio.toString());
					}
				}
			}
			{
				BondImpliedRating r = impliedRatingRepository.findLastByBondCode(fav.getBondUniCode());
				if (r != null) {
					exp.setImpliedRating(r.getImpliedRating());
					exp.setImpliedRatingDate(rateWritDateFormat.format(r.getDataDate()));
				}
			}

			list.add(exp);
		}
		return list;
	}
	
	public List<BondFavoriteVo> findFavoritesByGroupId(Integer userId, Integer groupId) {
		List<BondFavoriteVo> list = new ArrayList<>();
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("groupId").is(groupId).and("isDelete").is(0));
		List<BondFavoriteDoc> reslist = mongoOperations.find(query, BondFavoriteDoc.class);
		
		reslist.stream().forEachOrdered(bondFavoriteDoc -> {
			BondFavoriteVo bfVo = new BondFavoriteVo();
			BeanUtils.copyProperties(bondFavoriteDoc, bfVo);
			long count = this.findBondEventMsgCount(bondFavoriteDoc.getUserId(), bondFavoriteDoc.getBondUniCode(), bondFavoriteDoc.getBookmark(), bondFavoriteDoc.getCreateTime(), null);
			bfVo.setEventMsgCount(count);
			list.add(bfVo);
		});
		return list;
	}

	//变更债券关注的消息已读状态
	@Transactional
	public List<Integer> updateMsgReadStatus(Integer userId, BondFavoriteUpdateList param) {
		try{
			param.getFavoriteIds().stream().forEach(favoriteId -> {
				updateBookmarkByFavoriteIdInDB(favoriteId);
			});
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("updateMsgReadStatus userId:"+e.getMessage(), e);
		}
		return param.getFavoriteIds();
	}

	private BondFavorite updateBookmarkByFavoriteIdInDB(Integer favoriteId) {
		BondFavorite bondFavorite = favoriteReposityory.findOne(favoriteId);
		BondNotificationMsgDoc bnmDoc = this.findLastNotificationMsg(bondFavorite.getBondUniCode());
		if (null != bnmDoc) {
			bondFavorite.setBookmark(bnmDoc.getId());
			bondFavorite.setBookmarkUpdateTime(new Date());
		}
		
		return favoriteReposityory.save(bondFavorite);
	}
	
	@Transactional
	public void saveBondFavorite(BondFavorite bondFavorite){
		List<BondFavorite> bfList = favoriteReposityory.findFavoriteByGroupIdAndBondUniCode(bondFavorite.getGroupId(), bondFavorite.getBondUniCode());
		if (null != bfList && bfList.size() > 0) {
			bfList.stream().forEach(bondFav -> {
				bondFav.setUpdateTime(new Date());
				favoriteReposityory.save(bondFav);
			});
		}else{
			favoriteReposityory.save(bondFavorite);
		}
	}
	
	private BondNotificationMsgDoc findLastNotificationMsg(Long bondId){
		Query query = new Query();
		query.addCriteria(Criteria.where("bondId").is(bondId));
		query.with(new Sort(new Order(Direction.DESC, "id")));
		query.limit(1);
		
		BondNotificationMsgDoc bnMsgDoc = null;
		List<BondNotificationMsgDoc> bnsDocs = mongoOperations.find(query, BondNotificationMsgDoc.class);
		if (null != bnsDocs && bnsDocs.size() > 0) {
			bnMsgDoc = bnsDocs.get(0);
		}
		
		return bnMsgDoc;
	}

	public Page<BondNotificationMsgVo> findBondNotificationMsg(Integer favoriteId, List<Integer> typeList, Integer page,
			Integer limit) {
		long count = 0L;
		List<BondNotificationMsgVo> resultVos = new ArrayList<>();
		
		BondFavorite bondFav = favoriteReposityory.findOne(favoriteId);
		
		if (null != bondFav) {
			Long bondUniCode = bondFav.getBondUniCode();
			Long bookmark = bondFav.getBookmark();
			
			BondFavoriteGroup bondFavGroup = favoriteGroupReposityory.findOne(bondFav.getGroupId());
			
			Query query = new Query();
			PageRequest request = new PageRequest(page, limit, new Sort(Sort.Direction.DESC, "id"));
			if(bondFavGroup.getNotifiedEnable() == 1){
				// 通知开启，则只取创建时间之后的消息列表
				count= this.findAvailableBondEventMsgCount(bondUniCode, null, bondFav.getCreateTime(), typeList);
				query.addCriteria(Criteria.where("bondId").is(bondUniCode).and("createTime").gt(bondFav.getCreateTime()));
			}else{
				// 通知未开启，获取在创建时间之后标记位置之前的消息列表
				count = this.findAvailableBondEventMsgCount(bondUniCode, bookmark, bondFav.getCreateTime(), typeList);
				query.addCriteria(Criteria.where("bondId").is(bondUniCode).and("id").lte(bookmark).and("createTime").gt(bondFav.getCreateTime()));
			}
			if (null != typeList && typeList.size() > 0) {
				query.addCriteria(Criteria.where("eventType").in(typeList));
			}
			query.with(request);
			// 获取
			List<BondNotificationMsgDoc> results = mongoOperations.find(query, BondNotificationMsgDoc.class);
			
			results.stream().forEach(bondNotificationMsgDoc -> {
				BondNotificationMsgVo bnmVo = new BondNotificationMsgVo();
				BeanUtils.copyProperties(bondNotificationMsgDoc, bnmVo);
				// 如果id在标记位置之前，已读；BondMsgUserStatusDoc中存在记录，已读
				bnmVo.setReadStatus(1);
				// 如果id在标记位置之后，同时不在BondMsgUserStatusDoc中，未读
				if (bondNotificationMsgDoc.getId() > bookmark && !this.isReadMsg(bondFav.getUserId(), bondNotificationMsgDoc)) {
					bnmVo.setReadStatus(0);
				}
				bnmVo.setImportant(SafeUtils.getInteger(bnmVo.getImportant()));
				
				resultVos.add(bnmVo);
			});
			
		}
			
		return new PageImpl<BondNotificationMsgVo>(resultVos, 
				new PageRequest(page, limit, new Sort(Sort.Direction.DESC, "createTime")), count);
	}

	public Page<BondNotificationsVo> findBondNotifications(Integer userId, Integer page, Integer limit) {
		Long bookmarkCursor = 0L;
		List<BondNotificationsVo> resultVos = new ArrayList<>();
//		Date todayBegin = SafeUtils.parseDate(SafeUtils.getCalendarN(1)+" 09:00:00", SafeUtils.DATE_TIME_FORMAT1);
		// 获取当前用户保存的游标, 如果不存在则取最大的消息id
		UserOprRecord userOprRecord = userOprRecordRepository.findOne(userId);
		bookmarkCursor = null != userOprRecord ? userOprRecord.getBookmarkCursor() : bondFavoriteDao.findMaxBookmarkCursor();
		// 获取当前用户选择的消息类型
		//List<Integer> eventTypes = getEventTypeListByUserid(userId);
		// 获取当前用户需要通知的bond_uni_code列表
		List<Long> list = bondFavoriteDao.findDifbondIdByUserId(userId);
		// 获取BondDetailDoc列表
		Long totalcount = 0L;
		
		Map<Long, BondDetailDoc> bondsmap = new HashMap<>();
		
		list.stream().forEach(bondUniCode -> {
			Query bdquery = new Query();
			bdquery.addCriteria(Criteria.where("bondUniCode").is(bondUniCode));
			BondDetailDoc bondDetailDoc = mongoOperations.findOne(bdquery, BondDetailDoc.class);
			if (null != bondDetailDoc) {
				bondsmap.put(bondUniCode, bondDetailDoc);
			}
		});
		// 获取消息数目
		totalcount = mongoOperations.count(new Query(Criteria.where("bondId").in(list).and("id").gt(bookmarkCursor)), BondNotificationMsgDoc.class);
		// 分页获取消息
		Query msgquery = new Query();
		PageRequest request = new PageRequest(page, limit, new Sort(Sort.Direction.ASC, "id"));
		msgquery.addCriteria(Criteria.where("bondId").in(list).and("id").gt(bookmarkCursor));
		msgquery.with(request);
		List<BondNotificationMsgDoc> results = mongoOperations.find(msgquery, BondNotificationMsgDoc.class);
		
		results.stream().forEach(bondNotificationMsg -> {
				BondNotificationsVo msgVo = new BondNotificationsVo();
				BeanUtils.copyProperties(bondNotificationMsg, msgVo);
				BondDetailDoc bdDoc = bondsmap.get(bondNotificationMsg.getBondId());
				msgVo.setBondCode(bdDoc.getCode());
				msgVo.setBondName(bdDoc.getName());
				msgVo.setTenor(bdDoc.getTenor());
				msgVo.setImportant(SafeUtils.getInteger(bondNotificationMsg.getImportant()));
				msgVo.setRadarTypeName(this.getRadarTypeName(SafeUtils.getLong(msgVo.getEventType())));
				resultVos.add(msgVo);
		});
		// 游标置到最后一条消息
		if (null != results && results.size() > 0) {
			BondNotificationMsgDoc lastbondMsg = results.get(results.size() - 1);
			bookmarkCursor = lastbondMsg.getId();
		}
		// 保存当前用户新的游标
		updateUserOprRecord(userOprRecord,userId, bookmarkCursor);
		
		return new PageImpl<BondNotificationsVo>(resultVos, 
				new PageRequest(page, limit, new Sort(Sort.Direction.DESC, "createTime")), SafeUtils.getInt(totalcount));
	}

	//拿到该用户下的分组的消息类型。后期用缓存优化
	private List<Integer> getEventTypeListByUserid(Integer userId) {
		List<Integer> eventTypeList = null;
		String eventtypes = bondFavoriteDao.findEventtypeStrInGroup(userId);
		if (StringUtils.isNotBlank(eventtypes)) {
			eventTypeList = new ArrayList<>();
			List<String> originalEventList = Arrays.asList(eventtypes.replaceAll("[\\[\\]]", "").split(","));
			for (String item : originalEventList) {
				eventTypeList.add(Integer.valueOf(item));
			}
		}
		
		return eventTypeList;
	}

	/**
	 * @param userId
	 */
	@Transactional
	protected void updateUserOprRecord(UserOprRecord userOprRecord, Integer userId, Long newBookmarkCursor) {
		Date currDate = SafeUtils.getCurrentTime();
		if (null != userOprRecord) {
			userOprRecord.setUpdateTime(currDate);
			userOprRecord.setBookmarkCursor(newBookmarkCursor);
		} else {
			userOprRecord = new UserOprRecord(userId, currDate, newBookmarkCursor);
		}
		userOprRecordRepository.save(userOprRecord);
	}

	private boolean isReadMsg(Integer userId, BondNotificationMsgDoc bondNotificationMsg) {
		boolean result = false;
		Query bnmquery = new Query();
		bnmquery.addCriteria(Criteria.where("notifyMsgId").is(bondNotificationMsg.getId()).and("userId").is(userId));
		BondMsgUserStatusDoc bmusDoc = mongoOperations.findOne(bnmquery, BondMsgUserStatusDoc.class);
		if (null != bmusDoc) {
			//该消息已读
			result = true;
		}
		return result;
	}
	
	//批量删除关注
	public List<Integer> deleteFavorites(Integer userId, BondFavoriteUpdateList param) {
		LOG.info("deleteFavorites userId:"+userId);
		if (null != param.getFavoriteIds() && param.getFavoriteIds().size() > 0) {
			param.getFavoriteIds().stream().forEach(favoriteId -> {
				Date updateDt = new Date();
				BondFavorite bondFavorite = favoriteReposityory.findOne(favoriteId);
				bondFavorite.setIsDelete(1);
				bondFavorite.setUpdateTime(updateDt);
				favoriteReposityory.save(bondFavorite);
			});
		}
		return param.getFavoriteIds();
	}

	public String addNotificationStatus(Integer userId, Long bondId, Long msgId, Integer operation) {
		LOG.info("addNotificationStatus userId:"+userId+",msgId"+msgId+",operation"+operation);
		BondMsgUserStatusDoc bmusDoc = new BondMsgUserStatusDoc();
		bmusDoc.setUserId(userId);
		bmusDoc.setBondId(bondId);
		bmusDoc.setNotifyMsgId(msgId);
		bmusDoc.setOperation(operation);
		mongoOperations.save(bmusDoc);
		
		return msgId.toString();
	}
	
	/**
	 * @param userId
	 * @return
	 */
	public BondNotificationStatisticsVo getNotificationStatistics(Integer userId) {
		Long bookmarkCursor = 0L; 
		UserOprRecord userOprRecord = userOprRecordRepository.findOne(userId);
		if (null != userOprRecord) {
			bookmarkCursor = userOprRecord.getBookmarkCursor();
		}
		
		BondNotificationStatisticsVo statisticVO = new BondNotificationStatisticsVo();
		bondFavRadarSchemaRepo.findAllByParentIdAndStatus(1L, 1);

		// 存续1
		statisticVO.setMaturityStatistic(getCommonFavMsgStatisticByType(userId, bookmarkCursor,
				Constants.BOND_FAV_GROUP_MATURITY_TYPE));
		// 评级2
		statisticVO.setRatingStatistic(getCommonFavMsgStatisticByType(userId, bookmarkCursor,
				Constants.BOND_FAV_GROUP_RATING_TYPE));
		// 舆情3
		statisticVO.setSentimentStatistic(getCommonFavMsgStatisticByType(userId, bookmarkCursor,
				Constants.BOND_FAV_GROUP_SENTIMENT_TYPE));
		// 其他6
		statisticVO.setOtherStatistic(getCommonFavMsgStatisticByType(userId, bookmarkCursor,
				Constants.BOND_FAV_GROUP_OTHER_TYPE));
		// 价格指标4
		statisticVO.setPriceIdxStatistic(getPriceFavMsgStatistic(userId, bookmarkCursor));
		// 财务指标5
		statisticVO.setFinaIdxStatistic(getFinaFavMsgStatistic(userId, bookmarkCursor));

//		handleMaturityStatistic(userId, bookmarkCursor, statisticVo);
//		handleRiskChangeStatistic(userId, bookmarkCursor, statisticVo);
//		handleBondNewsStatistic(userId, bookmarkCursor, statisticVo);
//		handleBondQuoteStatistic(userId, bookmarkCursor, statisticVo);
		
		bookmarkCursor = bondFavoriteDao.findMaxBookmarkCursor();
		updateUserOprRecord(userOprRecord,userId, bookmarkCursor);
		return statisticVO;
	}

	private BondNotificationStatistic getFinaFavMsgStatistic(Integer userId, Long bookmarkCursor) {
		BondNotificationStatistic result = new BondNotificationStatistic();
		List<Long> finaDiffBondIdList = bondFavoriteDao.getFinaDiffBondIds(userId);
		this.handleIndexMsgStatisticVO(userId, finaDiffBondIdList,
				SafeUtils.getInteger(Constants.BOND_FAV_GROUP_FINA_TYPE), bookmarkCursor, result);
		return result;
	}

	private BondNotificationStatistic getPriceFavMsgStatistic(Integer userId, Long bookmarkCursor) {
		BondNotificationStatistic result = new BondNotificationStatistic();
		List<Long> priceDiffBondIdList = bondFavoriteDao.getPriceDiffBondIds(userId);
		this.handleIndexMsgStatisticVO(userId, priceDiffBondIdList,
				SafeUtils.getInteger(Constants.BOND_FAV_GROUP_PRICE_TYPE), bookmarkCursor, result);
		return result;
	}

	private void handleIndexMsgStatisticVO(Integer userId, List<Long> indexDiffBondIdList, Integer indexType,
										   Long bookmarkCursor, BondNotificationStatistic notiStatistic) {
		if (null != indexDiffBondIdList && indexDiffBondIdList.size() > 0) {
			int riskBondCount = 0;
			long riskMsgCount = 0L;
			Query commonQuery = new Query(Criteria.where("bondId").in(indexDiffBondIdList)
					.and("eventType").is(indexType).and("_id").gt(bookmarkCursor).and("userId").is(userId));
			List<?> commonDisList = mongoOperations.getCollection("bond_notification_cardmsg")
					.distinct("bondId", commonQuery.getQueryObject());

			if (null != commonDisList && commonDisList.size() > 0) {
				riskBondCount = commonDisList.size();
			}
			if (riskBondCount > 0) {
				riskMsgCount = mongoOperations.count(commonQuery, BondNotificationCardMsgDoc.class);
			}

			notiStatistic.setBondCount(SafeUtils.getLong(riskBondCount));
			notiStatistic.setEventMsgCount(riskMsgCount);
		}
	}

	private BondNotificationStatistic getCommonFavMsgStatisticByType(Integer userId, Long bookmarkCursor, Long eventType) {
		BondNotificationStatistic result = new BondNotificationStatistic();
		List<Integer> targetEventTypeList = this.getEventTypeList(eventType);
		String eventTypes = targetEventTypeList.stream().map(type -> type.toString()).collect(Collectors.joining(","));
		List<Long> commonDiffBondIdList = bondFavoriteDao.getCommonDiffBondIds(userId, eventTypes);
		if (null != commonDiffBondIdList && commonDiffBondIdList.size() > 0) {
			int riskBondCount = 0;
			long riskMsgCount = 0L;
			Query commonQuery = new Query(Criteria.where("bondId").in(commonDiffBondIdList)
					.and("eventType").in(targetEventTypeList).and("_id").gt(bookmarkCursor).and("userId").is(userId));
			List<?> commonDisList = mongoOperations.getCollection("bond_notification_cardmsg")
					.distinct("bondId", commonQuery.getQueryObject());
			if (null != commonDisList && commonDisList.size() > 0) {
				riskBondCount = commonDisList.size();
				if (riskBondCount > 0) {
					riskMsgCount = mongoOperations.count(commonQuery, BondNotificationCardMsgDoc.class);
				}
			}
			result.setBondCount(SafeUtils.getLong(riskBondCount));
			result.setEventMsgCount(riskMsgCount);
		}
		return result;
	}

	private List<Integer> getEventTypeList(Long parentType) {
		List<Integer> result = new ArrayList<>();
		List<BondFavoriteRadarSchema> favoriteRadarSchemaListList = bondFavRadarSchemaRepo.findAllByParentIdAndStatus(parentType, 1);
		if (favoriteRadarSchemaListList.isEmpty()) {
			result.add(SafeUtils.getInteger(parentType));
		} else {
			result = favoriteRadarSchemaListList.stream().map(item -> SafeUtils.getInteger(item.getId())).collect(Collectors.toList());
		}
		return result;
	}
	
	/**
	 * @param bookmarkCursor
	 * @param statisticVo
	 */
	private void handleSentimentStatistic(Integer userId, Long bookmarkCursor, BondNotificationStatisticsVo statisticVo) {
		List<Long> bondSentibondIds = bondFavoriteDao.findDifbondIdByUserIdAndEventtype(userId, "3");
		BondNotificationStatistic sentiStatistic = new BondNotificationStatistic();
		if (!bondSentibondIds.isEmpty()) {
			long sentimsgcount = 0L;
			int sentibondcount = 0;
			Query newsQuery = new Query(Criteria.where("bondId").in(bondSentibondIds).and("eventType").gte(14).lte(17).and("_id").gt(bookmarkCursor));

			List<?> newsdisList = mongoOperations.getCollection("bond_notification_cardmsg").distinct("bondId", newsQuery.getQueryObject());

			if (null != newsdisList && newsdisList.size() > 0) {
				sentibondcount = newsdisList.size();
			}
			if (sentibondcount > 0) {
				sentimsgcount = mongoOperations.count(newsQuery, BondNotificationCardMsgDoc.class);
			}

			sentiStatistic.setBondCount(SafeUtils.getLong(sentibondcount));
			sentiStatistic.setEventMsgCount(sentimsgcount);
		}
		statisticVo.setSentimentStatistic(sentiStatistic);
	}

	/**
	 * @param bookmarkCursor
	 * @param statisticVo
	 */
	private void handlePriceChangeStatistic(Integer userId, Long bookmarkCursor, BondNotificationStatisticsVo statisticVo) {
		List<Long> pricebondIds = bondFavoriteDao.findDifbondIdByUserIdAndEventtype(userId, "4");
		BondNotificationStatistic priceChangeStatistic = new BondNotificationStatistic();
		if (!pricebondIds.isEmpty()) {
			int pricebondcount = 0;
			long pricemsgcount = 0L;
			Query riskQuery = new Query(Criteria.where("bondId").in(pricebondIds).and("eventType").is(4).and("_id").gt(bookmarkCursor));

			List<?> riskdisList = mongoOperations.getCollection("bond_notification_cardmsg").distinct("bondId", riskQuery.getQueryObject());

			if (null != riskdisList && riskdisList.size() > 0) {
				pricebondcount = riskdisList.size();
			}
			if (pricebondcount > 0) {
				pricemsgcount = mongoOperations.count(riskQuery, BondNotificationCardMsgDoc.class);
			}

			priceChangeStatistic.setBondCount(SafeUtils.getLong(pricebondcount));
			priceChangeStatistic.setEventMsgCount(pricemsgcount);
		}
		statisticVo.setPriceIdxStatistic(priceChangeStatistic);
	}

	/**
	 * @param bookmarkCursor
	 * @param statisticVo
	 */
	private void handleMaturityStatistic(Integer userId, Long bookmarkCursor, BondNotificationStatisticsVo statisticVo) {
		List<Long> maturitybondIds = bondFavoriteDao.findDifbondIdByUserIdAndEventtype(userId, "1");
		BondNotificationStatistic maturityStatistic = new BondNotificationStatistic();
		if (!maturitybondIds.isEmpty()) {
			int matbondcount = 0;
			long matmsgcount = 0L;
			Query matQuery = new Query(Criteria.where("bondId").in(maturitybondIds).and("eventType").gte(7).lte(9).and("_id").gt(bookmarkCursor));

			List<?> matdisList = mongoOperations.getCollection("bond_notification_cardmsg").distinct("bondId", matQuery.getQueryObject());

			if (null != matdisList && matdisList.size() > 0) {
				matbondcount = matdisList.size();
			}
			if (matbondcount > 0) {
				matmsgcount = mongoOperations.count(matQuery, BondNotificationCardMsgDoc.class);
			}

			maturityStatistic.setBondCount(SafeUtils.getLong(matbondcount));
			maturityStatistic.setEventMsgCount(matmsgcount);
		}
		statisticVo.setMaturityStatistic(maturityStatistic);
	}
	
	@Async
	@EventListener
	public void handleUserOprRecord(Integer userId) {
		Long bookmarkCursor = 0L;
		if (null == userId) {
			return;
		}
		UserOprRecord userOprRecord = userOprRecordRepository.findOne(userId);
		if (null != userOprRecord) {
			bookmarkCursor = bondFavoriteDao.findMaxBookmarkCursor();
			userOprRecord.setBookmarkCursor(bookmarkCursor);
			userOprRecordRepository.save(userOprRecord);
		}
	}

	/**
	 * [消息卡片V2.0]推送全局提醒的消息-轮询方式接口调用新接口
	 * @param userId
	 * @param page
	 * @param limit
	 * @return
	 */
	public Page<BondNotificationsVo> findCardMsgList(Integer userId, Integer page, Integer limit) {
		StopWatch sw = new StopWatch();
		sw.start("cardMsg");
		Long bookmarkCursor = 0L;
		Long maxbookmarkCursor = findMaxCardMsgBookmarkCursor(userId);	

		List<BondNotificationsVo> resultVO = new ArrayList<>();
		// 获取当前用户保存的游标, 如果不存在则取最大的消息id
		UserOprRecord userOprRecord = userOprRecordRepository.findOne(userId);
		if (null != userOprRecord) {
			bookmarkCursor = userOprRecord.getBookmarkCursor();
		} else {
			bookmarkCursor = maxbookmarkCursor;
		}
		LOG.info("findCardMsgList userId:"+userId+",bookmarkCursor:"+bookmarkCursor+",maxbookmarkCursor:"+maxbookmarkCursor);
		
		if (bookmarkCursor.compareTo(maxbookmarkCursor) == 0) {
			return new PageImpl<>(resultVO,
					new PageRequest(page, limit, new Sort(Sort.Direction.DESC, "createTime")), 0);
		}
		// 获取当前用户需要通知的bond_uni_code列表
		List<Long> uniqueBondIdList = bondFavoriteDao.findDifbondIdByUserId(userId);

		Long totalCount = 0L;
		Map<Long, BondDetailDoc> bondsMap = new HashMap<>();
		Query query = new Query(Criteria.where("bondUniCode").in(uniqueBondIdList));
		query.fields().include("bondUniCode").include("code").include("name").include("tenor");
		List<BondDetailDoc> bondDetailDocList = mongoOperations.find(query, BondDetailDoc.class);
		bondDetailDocList.stream().forEach(doc -> bondsMap.put(doc.getBondUniCode(), doc));
		// 获取消息数目
		totalCount = mongoOperations.count(new Query(Criteria.where("userId").is(userId).
				and("bondId").in(uniqueBondIdList).and("id").gt(bookmarkCursor)), BondNotificationCardMsgDoc.class);

		// 分页获取消息
		Query msgquery = new Query();
		PageRequest request = new PageRequest(page, limit, new Sort(Sort.Direction.ASC, "id"));
		msgquery.addCriteria(Criteria.where("userId").is(userId).and("bondId").in(uniqueBondIdList).and("id").gt(bookmarkCursor));
		msgquery.with(request);
		List<BondNotificationCardMsgDoc> results = mongoOperations.find(msgquery, BondNotificationCardMsgDoc.class);
		
		results.stream().forEach(cardMsg -> {
				BondNotificationsVo msgVo = new BondNotificationsVo();
				BeanUtils.copyProperties(cardMsg, msgVo);
				BondDetailDoc bdDoc = bondsMap.get(cardMsg.getBondId());
				msgVo.setBondCode(bdDoc.getCode());
				msgVo.setBondName(bdDoc.getName());
				msgVo.setTenor(bdDoc.getTenor());
				msgVo.setNewsIndex(cardMsg.getNewsIndex());
				msgVo.setImportant(SafeUtils.getInteger(cardMsg.getImportant()));
				msgVo.setRadarTypeName(this.getRadarTypeName(SafeUtils.getLong(msgVo.getEventType())));
				resultVO.add(msgVo);
		});
		// 游标置到最后一条消息
		if (null != results && results.size() > 0) {
			BondNotificationMsgDoc lastbondMsg = results.get(results.size() - 1);
			bookmarkCursor = lastbondMsg.getId();
		}
		// 保存当前用户新的游标
		updateUserOprRecord(userOprRecord,userId, bookmarkCursor);
		sw.stop();
		LOG.info(String.format("findCardMsgList with userId[%1$d], spent [%2$d]ms", userId, sw.getTotalTimeMillis()));
		return new PageImpl<>(resultVO,
				new PageRequest(page, limit, new Sort(Sort.Direction.DESC, "createTime")), 0);
	}

	private Long findMaxCardMsgBookmarkCursor(Integer userId) {
		Long cardMsgBookmark = 0L;
		Query query = new Query();
		PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id"));
		query.addCriteria(Criteria.where("userId").is(userId).and("_id").gt(0L));
		query.with(request);
		
		BondNotificationCardMsgDoc bnCardMsgDoc = mongoOperations.findOne(query.limit(1), BondNotificationCardMsgDoc.class);
		if (null != bnCardMsgDoc) {
			cardMsgBookmark = bnCardMsgDoc.getId();
		}
		return cardMsgBookmark;
	}
	
	/**
	 * [消息卡片V2.0]通知消息卡片统计新接口
	 * @param userId
	 * @return
	 */
	public BondNotificationStatisticsVo getCardMsgStatistics(Integer userId) {
		BondNotificationStatisticsVo statisticVO = new BondNotificationStatisticsVo();
		Long bookmarkCursor = 0L; 
		UserOprRecord userOprRecord = userOprRecordRepository.findOne(userId);
		if (null != userOprRecord) {
			bookmarkCursor = userOprRecord.getBookmarkCursor();
		}
		// 存续1
		statisticVO.setMaturityStatistic(getCommonFavMsgStatisticByType(userId, bookmarkCursor,
				Constants.BOND_FAV_GROUP_MATURITY_TYPE));
		// 评级2
		statisticVO.setRatingStatistic(getCommonFavMsgStatisticByType(userId, bookmarkCursor,
				Constants.BOND_FAV_GROUP_RATING_TYPE));
		// 舆情3
		statisticVO.setSentimentStatistic(getCommonFavMsgStatisticByType(userId, bookmarkCursor,
				Constants.BOND_FAV_GROUP_SENTIMENT_TYPE));
		// 其他6
		statisticVO.setOtherStatistic(getCommonFavMsgStatisticByType(userId, bookmarkCursor,
				Constants.BOND_FAV_GROUP_OTHER_TYPE));
		// 价格指标4
		statisticVO.setPriceIdxStatistic(getPriceFavMsgStatistic(userId, bookmarkCursor));
		// 财务指标5
		statisticVO.setFinaIdxStatistic(getFinaFavMsgStatistic(userId, bookmarkCursor));
		
		bookmarkCursor = findMaxCardMsgBookmarkCursor(userId);
		updateUserOprRecord(userOprRecord,userId, bookmarkCursor);
		LOG.info("getCardMsgStatistics userId:"+userId+",bookmarkCursor:"+bookmarkCursor);
		
		return statisticVO;
	}

	private void handleFinaIdxStatistic(Integer userId, Long bookmarkCursor, BondNotificationStatisticsVo statisticVo) {
		List<Long> finabondIds = bondFavoriteDao.findDifbondIdByUserIdAndEventtype(userId, "5");
		BondNotificationStatistic finaIdxStatistic = new BondNotificationStatistic();
		if (!finabondIds.isEmpty()) {
			int finabondcount = 0;
			long finamsgcount= 0L;
			Query finaQuery = new Query(Criteria.where("bondId").in(finabondIds).and("eventType").is(5).and("_id").gt(bookmarkCursor));

			List<?> rateList = mongoOperations.getCollection("bond_notification_cardmsg").distinct("bondId", finaQuery.getQueryObject());

			if (null != rateList && rateList.size() > 0) {
				finabondcount = rateList.size();
			}
			if (finabondcount > 0) {
				finamsgcount = mongoOperations.count(finaQuery, BondNotificationCardMsgDoc.class);
			}
			
			finaIdxStatistic.setBondCount(SafeUtils.getLong(finabondcount));
			finaIdxStatistic.setEventMsgCount(finamsgcount);
		}
		statisticVo.setFinaIdxStatistic(finaIdxStatistic);
	}

	private void handleRatingStatistic(Integer userId, Long bookmarkCursor, BondNotificationStatisticsVo statisticVo) {
		List<Long> ratingbondIds = bondFavoriteDao.findDifbondIdByUserIdAndEventtype(userId, "2");
		BondNotificationStatistic ratingStatistic = new BondNotificationStatistic();
		if (!ratingbondIds.isEmpty()) {
			int ratbondcount = 0;
			long ratmsgcount = 0L;
			Query matQuery = new Query(Criteria.where("bondId").in(ratingbondIds).and("eventType").gte(10).lte(13).and("_id").gt(bookmarkCursor));

			List<?> rateList = mongoOperations.getCollection("bond_notification_cardmsg").distinct("bondId", matQuery.getQueryObject());

			if (null != rateList && rateList.size() > 0) {
				ratbondcount = rateList.size();
			}
			if (ratbondcount > 0) {
				ratmsgcount = mongoOperations.count(matQuery, BondNotificationCardMsgDoc.class);
			}

			ratingStatistic.setBondCount(SafeUtils.getLong(ratbondcount));
			ratingStatistic.setEventMsgCount(ratmsgcount);
		}
		statisticVo.setMaturityStatistic(ratingStatistic);
	}

	private static Map<Long, String> radarTypeNameMap = new HashMap<>();
	private String getRadarTypeName(Long radarId) {
		if (radarTypeNameMap.isEmpty()) {
			List<BondFavoriteRadarSchema> schemaList = bondFavRadarSchemaRepo.findAllByStatus(1);
			radarTypeNameMap = schemaList.stream()
					.collect(Collectors.toMap(BondFavoriteRadarSchema::getId, BondFavoriteRadarSchema::getTypeName));
		}
		if (radarTypeNameMap.containsKey(radarId)) {
			return radarTypeNameMap.get(radarId);
		}
		return "未知";
	}
	
}
