package com.innodealing.bond.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bond.IndicatorDao;
import com.innodealing.model.mongo.dm.bond.finance.IndicatorField;
import com.innodealing.model.mongo.dm.bond.finance.IndicatorFieldGroup;
import com.innodealing.model.mongo.dm.bond.finance.SpecialIndicatorFilterDoc;
import com.innodealing.model.mongo.dm.bond.user.UserIndicatorFilter;

@Service
public class UserIndicatorFilterService {

	private @Autowired MongoTemplate mongoTemplate;
	
	private @Autowired IndicatorDao indicatorDao;
	
	/**
	 * 保存
	 * @param userIndicatorFilter
	 * @return
	 */
	public boolean save(UserIndicatorFilter userIndicatorFilter){
		mongoTemplate.save(userIndicatorFilter);
		return true;
	}
	
	/**
	 * 构建UserIndicatorFilter
	 * @param userId
	 * @param comUniCode
	 * @param fields
	 * @return
	 */
	public UserIndicatorFilter buildUserIndicatorFilter(Long userId, Long comUniCode,List<String> fields){
		if(fields == null){
			System.out.println(1);
		}
		//主体行业模型
		String type =  indicatorDao.findModelByComUniCode(comUniCode);
		//查询模板
		SpecialIndicatorFilterDoc filterModel = mongoTemplate.findOne(new Query(Criteria.where("type").is(type)), SpecialIndicatorFilterDoc.class);
		
		List<IndicatorFieldGroup> indicatorFieldGroups = new ArrayList<>();//用户自定义
		filterModel.getIndicatorFieldGroups().forEach(fieldGroup -> {
			List<IndicatorField> indicatorFields = fieldGroup.getIndicatorFields();
			if(indicatorFields == null || fields == null){
				System.out.println(1);
			}
			//选中设置
			List<IndicatorField> filterFields = new ArrayList<>();
			indicatorFields.forEach(field -> {
				if(field.getField() != null && fields.contains(field.getField())){
					field.setSelected(Constants.TRUE);
				}else{
					field.setSelected(Constants.FALSE);
				}
				filterFields.add(field);
			});
			fieldGroup.setIndicatorFields(filterFields);
			indicatorFieldGroups.add(fieldGroup);
		});
		//构建用户自定义的筛选方案
		SpecialIndicatorFilterDoc userFilter = new SpecialIndicatorFilterDoc();
		userFilter.setType(type);
		userFilter.setIndicatorFieldGroups(indicatorFieldGroups);
		
		UserIndicatorFilter filter = new UserIndicatorFilter();
		filter.setFilter(userFilter);
		filter.setUserId(userId);
		filter.setModel(type);
		return filter;
	}
	
	/**
	 * 获取用户筛选条件
	 * @param issuerId
	 * @param userid
	 * @param isDefault
	 * @return
	 */
	public List<IndicatorFieldGroup> findFilter(Long issuerId, Long userid,  boolean isDefault){
		//主体行业模型
		String type =  indicatorDao.findModelByComUniCode(issuerId);
		//查询
		SpecialIndicatorFilterDoc filterModel = mongoTemplate.findOne(new Query(Criteria.where("type").is(type)), SpecialIndicatorFilterDoc.class);
		if(isDefault){
			if (filterModel == null){
				return null;
			}else{
				return filterModel.getIndicatorFieldGroups();
			}
		}else{
			UserIndicatorFilter userFilter = find(userid, type);
			if(userFilter != null){
				return userFilter.getFilter().getIndicatorFieldGroups();
			}else{
				return filterModel.getIndicatorFieldGroups();
			}
		}
	}
	
	/**
	 * 查询
	 * @param userId
	 * @param model 
	 * @return
	 */
	public UserIndicatorFilter find(Long userId, String model){
		Query query = new Query(Criteria.where("userId").is(userId).and("model").is(model));
		UserIndicatorFilter userFilter = mongoTemplate.findOne(query, UserIndicatorFilter.class);
		return userFilter;
	}
	
}
