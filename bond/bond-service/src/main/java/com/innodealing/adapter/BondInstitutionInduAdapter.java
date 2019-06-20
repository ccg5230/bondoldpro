package com.innodealing.adapter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innodealing.bond.param.BondSimilarFilterReq;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.service.BondUserInstitution;
import com.innodealing.consts.Constants;
import com.innodealing.model.mongo.dm.BondDmFilterDoc;

@Component
public class BondInstitutionInduAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(BondInstitutionInduAdapter.class);

	@Autowired
	BondInduService induService;

	@Autowired
	private BondUserInstitution bondUserInstitution;

	/**
	 * 用户行业是否是自定义行业
	 * 
	 * @param userId
	 * @return
	 */
	public Boolean isInstitutionIndu(Long userId) {
		return induService.isInduInstitution(userId);
	}
	
	/**
	 * 用户行业是否是GISC行业
	 * @param userId
	 * @return
	 */
	public boolean isGicsInduClass(Long userId) {
		return induService.isGicsInduClass(userId);
	}

	/**
	 * 根据用户ID取机构ID
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getInstitution(Long userId) {
		return bondUserInstitution.getUserInstitutionByUserId(userId.toString());
	}

	public BondDmFilterDoc conv(BondDmFilterDoc doc, Long userId) {

		if (!isInstitutionIndu(userId)) {
			if (doc.getInduClass() == null) {
				doc.setInduClass(Constants.INDU_CLASS_GICS);
			}
		}

		return doc;
	}

	public List<BondDmFilterDoc> conv(List<BondDmFilterDoc> docs, Long userId) {
		if (!isInstitutionIndu(userId)) {
			for (BondDmFilterDoc doc : docs) {
				if (doc.getInduClass() == null) {
					doc.setInduClass(Constants.INDU_CLASS_GICS);
				}
			}
		}
		return docs;
	}

	public BondSimilarFilterReq conv(BondSimilarFilterReq req, Long userId) {
		if (!isInstitutionIndu(userId)) {
			if (induService.isGicsInduClass(userId))
				return req;
			List<String> fields = req.getSimilarField();
			for (int i = 0; i < fields.size(); ++i) {
				if (fields.get(i).equals(Constants.INDU_CLASS_GICS_ID)) {
					fields.set(i, Constants.INDU_CLASS_SW_ID);
				}
			}
		}
		return req;
	}

	public <T> BondPageAdapter<T> conv(BondPageAdapter<T> page, Long userId) {

		if (!isInstitutionIndu(userId)) {
			if (induService.isGicsInduClass(userId)){
				for (T obj : page.getContent()) {
					copyInstitutionRatingFields(obj,userId);
				}
				return page;
			}
			for (T obj : page.getContent()) {
				convIndu(obj,userId);
			}
		} else {
			for (T obj : page.getContent()) {
				convInstitutionIndu(obj, userId);
			}
		}
		return page;
	}

	public <T> Collection<T> conv(Collection<T> list, Long userId) {
		if (!isInstitutionIndu(userId)) {
			if (induService.isGicsInduClass(userId)){
				for (T obj : list) {
					copyInstitutionRatingFields(obj,userId);
				}
				return list;
			}
				
			for (T obj : list) {
				convIndu(obj,userId);
			}
		} else {
			for (T obj : list) {
				convInstitutionIndu(obj, userId);
			}
		}
		return list;
	}

	public <T> List<T> convList(List<T> sourceList, Long userId) {
		if (!this.isInstitutionIndu(userId)) {
			if(induService.isGicsInduClass(userId)){
				for (T obj : sourceList) {
					copyInstitutionRatingFields(obj,userId);
				}
				return sourceList;
			}
			for (T obj : sourceList) {
				convIndu(obj,userId);
			}
		} else {
			for (T obj : sourceList) {
				this.convInstitutionIndu(obj, userId);
			}
		}
		return sourceList;
	}

	public <T> BondPageAdapter<T> convByInduClass(BondPageAdapter<T> page, Integer induClass, Long userId) {
		if (!isInstitutionIndu(userId)) {
			if ((induClass == null) ? true : !induClass.equals(Constants.INDU_CLASS_SW)){
				for (T obj : page.getContent()) {
					copyInstitutionRatingFields(obj,userId);
				}
			}else{
				for (T obj : page.getContent()) {
					convIndu(obj,userId);
				}
			}
			
		} else {
			for (T obj : page.getContent()) {
				convInstitutionIndu(obj, userId);
			}
		}
		return page;
	}

	@SuppressWarnings("unchecked")
	public <T> T conv(T obj, Long userId) {
		if (!isInstitutionIndu(userId)) {
			if (induService.isGicsInduClass(userId)){
				copyInstitutionRatingFields(obj,userId);
				return obj;
			}
			return (T) copyInduFields(obj,userId);
		} else {
			return (T) convInstitutionIndu(obj, userId);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T convIndu(T obj,Long userId) {
		return (T) copyInduFields(obj,userId);
	}

	private Object copyInduFields(Object obj,Long userId) {
		try {
			Method setInduId = obj.getClass().getMethod("setInduId", Long.class);
			Method getInduIdSw = obj.getClass().getMethod("getInduIdSw");
			if (getInduIdSw != null && setInduId != null) {
				setInduId.invoke(obj, getInduIdSw.invoke(obj));
			}
		} catch (Exception e) {
			LOG.debug("failed to setInduId", e);
		}
		try {
			Method setInduName = obj.getClass().getMethod("setInduName", String.class);
			Method getInduNameSw = obj.getClass().getMethod("getInduNameSw");
			if (getInduNameSw != null && setInduName != null) {
				setInduName.invoke(obj, getInduNameSw.invoke(obj));
			}
		} catch (Exception e) {
			LOG.debug("failed to setInduName", e);
		}
		return copyInstitutionRatingFields(obj,userId);
	}

	@SuppressWarnings("unchecked")
	public <T> T convInstitutionIndu(T obj, Long userId) {
		return (T) copyInstitutionInduFields(obj, userId);
	}

	@SuppressWarnings("unchecked")
	private Object copyInstitutionInduFields(Object obj, Long userId) {
		Integer org_id = getInstitution(userId);
		try {
			// 自定义行业
			Method setInduId = obj.getClass().getMethod("setInduId", Long.class);
			Method setInduName = obj.getClass().getMethod("setInduName", String.class);
			Method getinstitutionInduMap = obj.getClass().getMethod("getInstitutionInduMap");
			Object institutionInduObject = getinstitutionInduMap.invoke(obj);
			if (institutionInduObject != null && setInduId != null) {
				Map<String, Object> institutionInduMap = (Map<String, Object>) institutionInduObject;
				setInduId.invoke(obj, institutionInduMap.get("code" + org_id));
				setInduName.invoke(obj, institutionInduMap.get("name" + org_id));
			}
		} catch (Exception e) {
			LOG.debug("failed to copyInstitutionInduFields", e);
		}
		return copyInstitutionRatingFields(obj,userId);
	}
	
	@SuppressWarnings("unchecked")
	private Object copyInstitutionRatingFields(Object obj, Long userId) {
		Integer org_id = getInstitution(userId);
		try {
			// 评级
			Method setInstRating = obj.getClass().getMethod("setInstRating", String.class);
			Method setInstRatingId = obj.getClass().getMethod("setInstRatingId", Integer.class);
			Method getInstRatingMap = obj.getClass().getMethod("getInstRatingMap");
			Object instRatingObject = getInstRatingMap.invoke(obj);
			if (setInstRating != null && instRatingObject != null) {
				Map<String, Object> instRatingMap = (Map<String, Object>) instRatingObject;
				setInstRating.invoke(obj, instRatingMap.get("name" + org_id));
				setInstRatingId.invoke(obj, instRatingMap.get("code" + org_id));
			}
			// 投资建议
			Method setInstInvestmentAdvice = obj.getClass().getMethod("setInstInvestmentAdvice", String.class);
			Method setInstInvestmentAdviceId = obj.getClass().getMethod("setInstInvestmentAdviceId", Integer.class);
			Method getInstInvestmentAdviceMap = obj.getClass().getMethod("getInstInvestmentAdviceMap");
			Object instInvestmentAdviceObject = getInstInvestmentAdviceMap.invoke(obj);
			if (setInstInvestmentAdvice != null && instInvestmentAdviceObject != null) {
				Map<String, Object> instInvestmentAdviceMap = (Map<String, Object>) instInvestmentAdviceObject;
				setInstInvestmentAdvice.invoke(obj, instInvestmentAdviceMap.get("name" + org_id));
				setInstInvestmentAdviceId.invoke(obj, instInvestmentAdviceMap.get("code" + org_id));
			}
			// 评级说明
			Method setInstRatingTextFlag = obj.getClass().getMethod("setInstRatingDescribe", int.class);
			Method getInstRatingTextFlagMap = obj.getClass().getMethod("getInstRatingMap");
			Object instRatingTextFlagObject = getInstRatingTextFlagMap.invoke(obj);
			if (setInstRatingTextFlag != null && instRatingTextFlagObject != null) {
				Map<String, Object> instRatingTextFlagMap = (Map<String, Object>) instRatingTextFlagObject;
				setInstRatingTextFlag.invoke(obj, instRatingTextFlagMap.get("textFlag" + org_id));
			}
			// 投资建议说明
			Method setInstInvestmentAdviceTextFlag = obj.getClass().getMethod("setInstInvestmentAdviceDescribe",int.class);
			Method getInstInvestmentAdviceTextFlagMap = obj.getClass().getMethod("getInstInvestmentAdviceMap");
			Object instInvestmentAdviceTextFlagObject = getInstInvestmentAdviceTextFlagMap.invoke(obj);
			if (setInstInvestmentAdviceTextFlag != null && instInvestmentAdviceTextFlagObject != null) {
				Map<String, Object> instInvestmentAdviceTextFlagMap = (Map<String, Object>) instInvestmentAdviceTextFlagObject;
				setInstInvestmentAdviceTextFlag.invoke(obj, instInvestmentAdviceTextFlagMap.get("textFlag" + org_id));
			}
		} catch (Exception e) {
			LOG.debug("failed to copyInstitutionInduFields", e);
		}
		return obj;
	}

}
