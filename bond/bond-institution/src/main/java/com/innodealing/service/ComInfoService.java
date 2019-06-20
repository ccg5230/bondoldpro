package com.innodealing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.innodealing.dao.ComInfoDao;
import com.innodealing.dao.UserOrgInfoDao;
import com.innodealing.model.mysql.BondInstComIndu;
import com.innodealing.observer.SubscriptionSubject;
import com.innodealing.util.SafeUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ComInfoService {

	private static final Logger LOG = LoggerFactory.getLogger(ComInfoService.class);

	@Autowired
	private ComInfoDao comInfoDao;

	@Autowired
	@Qualifier("comInduSubscriptionSubject")
	private SubscriptionSubject comInduSubscriptionSubject;

	@Autowired
	private UserOrgInfoDao userOrgInfoDao;

	private Integer getInstId(Integer userId) {
		return userOrgInfoDao.queryOrgIdByUserId(SafeUtils.getString(userId));
	}

	public PageInfo<List<BondInstComIndu>> queryComListByInstIndu(Integer induUniCode, Integer userId, Integer type,
			String comChiName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer instId = getInstId(userId);
		if (!StringUtils.isEmpty(instId)) {
			map.put("instId", instId);
		}
		if (!StringUtils.isEmpty(induUniCode)) {
			map.put("induUniCode", induUniCode);
		}
		if (!StringUtils.isEmpty(comChiName)) {
			map.put("comChiName", comChiName);
		}
		if (!StringUtils.isEmpty(type)) {
			map.put("type", type);
		}
		PageHelper.startPage(page, limit);
		List<BondInstComIndu> list = comInfoDao.queryComListByInstIndu(map);
		PageInfo<List<BondInstComIndu>> pageinfo = new PageInfo(list);
		return pageinfo;
	}

	public PageInfo<List<BondInstComIndu>> queryComNotInduList(Integer userId, String comChiName, Integer page,
			Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instId", getInstId(userId));
		if (!StringUtils.isEmpty(comChiName)) {
			map.put("comChiName", comChiName);
		}
		PageHelper.startPage(page, limit);
		List<BondInstComIndu> list = comInfoDao.queryComNotInduList(map);
		PageInfo<List<BondInstComIndu>> pageinfo = new PageInfo(list);
		return pageinfo;
	}

	@Transactional
	public Boolean insertBondInstComIndu(Integer userId, List<BondInstComIndu> cominduList) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Long> comUniCodeList = new ArrayList<Long>();
			Integer instId = getInstId(userId);
			for (BondInstComIndu indu : cominduList) {
				indu.setInstId(instId);
				indu.setCreateBy(userId);
				indu.setUpdateBy(userId);
				int count = comInfoDao.queryComInduCount(indu);
				if(indu.getInduUniCode()!=null){
					Boolean result = false;
					if (count > 0) {
						result = comInfoDao.updateBondInstComIndu(indu) > 0;
					} else {
						result = comInfoDao.insertBondInstComIndu(indu) > 0;
					}
					if (!result) {
						throw new Exception("insertBondInstComIndu error.");
					}
				}
				comUniCodeList.add(indu.getComUniCode());
			}
			map.put("instId", instId);
			map.put("comUniCodeList", comUniCodeList);
			if(cominduList.get(0).getInduUniCode()==null){
				comInfoDao.deleteBondInstComInduByComUniCode(map);
			}
			// 通知订阅者
			if (cominduList != null && cominduList.size() > 0) {
				String json = JSONArray.fromObject(cominduList).toString();
				comInduSubscriptionSubject.notify(json, userId.toString());
			}
		} catch (Exception e) {
			LOG.info(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	public Boolean updateBondInstComIndu(Integer userId, List<BondInstComIndu> cominduList) {
		Integer instId = getInstId(userId);
		for (BondInstComIndu indu : cominduList) {
			indu.setInstId(instId);
			indu.setUpdateBy(userId);
			Boolean result = comInfoDao.updateBondInstComIndu(indu) > 0;
			if (result) {
				// 通知订阅者
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("instId", instId);
				map.put("induCode", indu.getInduUniCode());
				map.put("induName", indu.getInduUniName());
				map.put("comUniCode", indu.getComUniCode());
				String json = JSONObject.fromObject(map).toString();
				comInduSubscriptionSubject.notify(json, userId.toString());
			}
		}
		return true;
	}

	public Boolean deleteBondInstComIndu(Map<String, Object> map) {
		Boolean result = comInfoDao.deleteBondInstComIndu(map) > 0;
		return result;
	}

}
