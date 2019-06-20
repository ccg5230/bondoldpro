package com.innodealing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;
import com.innodealing.dao.BondUserInduClassDao;
import com.innodealing.dao.UserOrgInfoDao;
import com.innodealing.model.dm.bond.BondUserInduClass;
import com.innodealing.util.RedisUtil;

@Service
public class BondInduService {

	private final static Logger LOG = LoggerFactory.getLogger(BondInduService.class);

	private @Autowired UserOrgInfoDao userOrgInfoDao;

	private @Autowired BondUserOperationService bondUserOperationService;

	private @Autowired BondUserInduClassDao userInduClassDao;
	
	private @Autowired RedisUtil redisUtil;
	
	public Boolean isInduInstitution(Long userid) {
		try {
			Integer orgId = userOrgInfoDao.queryOrgIdByUserId(userid.toString());
			return checkInstInduAuthorization(userid, orgId);
		} catch (Exception ex) {
			LOG.error("isInduInstitution exception with userid[" + userid + "]: " + ex.getMessage());
		}
		return false;
	}

	private Boolean checkInstInduAuthorization(Long userid, Integer orgId) {
		Boolean flay = bondUserOperationService.instInduAuthorization(userid, 2);
		if (flay) {
			flay = bondUserOperationService.instInduAuthorization(Long.parseLong(orgId.toString()), 1);
		}
		return flay;
	}

	public Boolean isInduInstitution(Long userid, Integer orgId) {
		try {
			return checkInstInduAuthorization(userid, orgId);
		} catch (Exception ex) {
			LOG.error(
					"isInduInstitution exception with orgId[" + orgId + "],userid[" + userid + "]: " + ex.getMessage());
		}
		return false;
	}
	
	public boolean isGicsInduClass(Long userId) {
		// 这个函数被内部数据处理调用，缺省使用GICS分类
		Integer induClass = findInduClassByUser(userId);
		return (induClass == null) ? true : !induClass.equals(Constants.INDU_CLASS_SW);
	}
	
	public Integer findInduClassByUser(Long userId) {
//		Integer induClass = (Integer) redisUtil.get(Constants.INDU_CLASS_REDIS_PREFIX + userId);
//		if (induClass == null) {
//			// redis不作为持久化存储，如果不存在该键还需要再检查mysql
//			BondUserInduClass userInduClass = userInduClassDao.queryById(userId);
//			if (null == userInduClass) {
//				return Constants.INDU_CLASS_UNDEFINED;
//			}
//			induClass = userInduClass.getInduClass();
//			if (induClass != null) {
//				redisUtil.set(Constants.INDU_CLASS_REDIS_PREFIX + userId, induClass, (long) 60 * 5);
//			}
//		}
//		return induClass;
		if (null == userId) {
			return Constants.INDU_CLASS_UNDEFINED;
		}
		BondUserInduClass userInduClass = userInduClassDao.queryById(userId);
		if (null == userInduClass) {
			return Constants.INDU_CLASS_UNDEFINED;
		}
		return userInduClass.getInduClass();
	}
	
	/**
	 * 用户所属行业1,GICS 2,SW 3自定义
	 * @param userId
	 * @return
	 */
	public Integer findUserInduClass(Integer userId){
		if (this.isInduInstitution(Long.valueOf(userId))) {
			return 3;
		}else{
			return findInduClassByUser(Long.valueOf(userId));
		}
	}
	
	
}
