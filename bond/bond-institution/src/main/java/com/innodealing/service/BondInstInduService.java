package com.innodealing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.innodealing.dao.BondInstInduDao;
import com.innodealing.dao.ComInfoDao;
import com.innodealing.dao.UserOrgInfoDao;
import com.innodealing.model.mysql.BondInstComIndu;
import com.innodealing.model.mysql.BondInstIndu;
import com.innodealing.observer.SubscriptionSubject;
import com.innodealing.util.SafeUtils;

import net.sf.json.JSONArray;

@Service
public class BondInstInduService {

	@Autowired
	private BondInstInduDao bondInstInduDao;

	@Autowired
	private ComInfoDao comInfoDao;

	@Autowired
	@Qualifier("induSubscriptionSubject")
	private SubscriptionSubject induSubscriptionSubject;

	@Autowired
	private UserOrgInfoDao userOrgInfoDao;

	private Integer getInstId(Integer userId) {
		return userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
	}

	public List<BondInstIndu> queryBondInstInduList(Integer userId, String induClassName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instId", getInstId(userId));
		map.put("induClassName", induClassName);
		return bondInstInduDao.queryBondInstInduList(map);
	}

	@Transactional
	public BondInstIndu insertBondInstIndu(Integer userId, BondInstIndu indu) {
		indu.setCreateBy(userId);
		indu.setUpdateBy(userId);
		indu.setInstId(getInstId(userId));
		bondInstInduDao.updateBondInstInduLevel(indu);
		return bondInstInduDao.insertBondInstIndu(indu);
	}

	@Transactional
	public Boolean updateBondInstIndu(Integer userId, BondInstIndu indu) {
		try {
			indu.setInstId(getInstId(userId));
			bondInstInduDao.updateBondInstIndu(indu);
			BondInstComIndu comindu = new BondInstComIndu();
			comindu.setInstId(indu.getInstId());
			comindu.setInduUniCode(indu.getInduUniCode());
			comindu.setInduUniName(indu.getInduClassName());
			comInfoDao.updateBondInstComIndu(comindu);

			// 通知订阅者
			List<BondInstIndu> list = new ArrayList<BondInstIndu>();
			list.add(indu);
			String json = JSONArray.fromObject(list).toString();
			induSubscriptionSubject.notify(json,userId.toString());

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean deleteBondInstIndu(Integer userId, List<BondInstIndu> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer instId = getInstId(userId);
		map.put("instId", instId);
		List<Long> induUniCodeList = new ArrayList<Long>();
		
		//将当前删除的目标拖动到最后
		updateInduRule(userId,list.get(0));
		
		for (BondInstIndu indu : list) {
			induUniCodeList.add(indu.getInduUniCode());
			indu.setInduClassName(null);
			indu.setInduUniCode(null);
			indu.setInstId(instId);
		}
		map.put("induUniCodeList", induUniCodeList);
		try {
			comInfoDao.deleteBondInstComIndu(map);
			bondInstInduDao.deleteBondInstIndu(map);

			// 通知订阅者
			if (list != null && list.size() > 0) {
				String json = JSONArray.fromObject(list).toString();
				induSubscriptionSubject.notify(json,userId.toString());
			}

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean updateInduRule(Integer userId, BondInstIndu indu) {
		if ((indu.getOldFatUniCode() == 0 && indu.getFatUniCode() != 0)
				|| (indu.getOldFatUniCode() != 0 && indu.getFatUniCode() == 0)) {
			return false;
		}
		indu.setInstId(getInstId(userId));
		try {
			bondInstInduDao.updateOldBondInstIndu(indu);
			bondInstInduDao.updateNewBondInstIndu(indu);
			bondInstInduDao.updateBondInstIndu(indu);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

}
