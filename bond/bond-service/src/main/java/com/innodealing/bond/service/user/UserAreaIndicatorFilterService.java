package com.innodealing.bond.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bond.IndicatorDao;
import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorField;
import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorFilterDoc;
import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorItem;
import com.innodealing.model.mongo.dm.bond.user.UserAreaIndicatorFilter;

@Service
public class UserAreaIndicatorFilterService {
	
	private @Autowired MongoTemplate mongoTemplate;
	
	
	public boolean save(UserAreaIndicatorFilter userAreaIndicatorFilter){
		mongoTemplate.save(userAreaIndicatorFilter);
		return true;
		
	}
	public UserAreaIndicatorFilter buildUserAreaIndicatorFilter(Long userId,List<String> fields){
		
		if(fields == null){
			System.out.println(1);
		}
		//主体行业模型
		//String type =  indicatorDao.findModelByComUniCode(comUniCode);
		long type = 1;
		//查询模板
		AreaIndicatorFilterDoc filterModel = mongoTemplate.findOne(new Query(Criteria.where("id").is(type)), AreaIndicatorFilterDoc.class);
		List<AreaIndicatorItem> areaIndicatorItems = new ArrayList<>();
		filterModel.getIndicatorFieldGroups().forEach(fieldGroup ->{
			List<AreaIndicatorField> indicatorFields = fieldGroup.getIndicatorFields();
			if(indicatorFields == null || fields == null){
				System.out.println(1);
			}
			//选中设置
			List<AreaIndicatorField> filterFields = new ArrayList<>();
			indicatorFields.forEach(field -> {
				if(field.getField() != null && fields.contains(field.getField())){
					field.setSelected(Constants.TRUE);
				}else{
					field.setSelected(Constants.FALSE);
				}
				filterFields.add(field);
			});
			fieldGroup.setIndicatorFields(filterFields);
			areaIndicatorItems.add(fieldGroup);
		});
		//构建用户自定义的筛选方案
		AreaIndicatorFilterDoc userFilter = new AreaIndicatorFilterDoc();
		userFilter.setId(Long.valueOf(type));
		userFilter.setIndicatorFieldGroups(areaIndicatorItems);
		
		UserAreaIndicatorFilter filter = new UserAreaIndicatorFilter();
		filter.setFilter(userFilter);
		filter.setUserId(userId);
		//filter.setModel(type);
		return filter;
		
	}
	public List<AreaIndicatorItem> findFilter(Long userid,  boolean isDefault){
		//主体行业模型
		//String type =  indicatorDao.findModelByComUniCode(issuerId);
		long type = 1;
		//查询
		AreaIndicatorFilterDoc filterModel = mongoTemplate.findOne(new Query(Criteria.where("id").is(type)), AreaIndicatorFilterDoc.class);
		if(isDefault){
			if (filterModel == null){
				return null;
			}else{
				return filterModel.getIndicatorFieldGroups();
			}
		}else{
			UserAreaIndicatorFilter userFilter = find(userid);
			if(userFilter != null){
				return userFilter.getFilter().getIndicatorFieldGroups();
			}else{
				return filterModel.getIndicatorFieldGroups();
			}
		}
	}
	public UserAreaIndicatorFilter find(Long userId){
		Query query = new Query(Criteria.where("userId").is(userId));
		UserAreaIndicatorFilter userFilter = mongoTemplate.findOne(query, UserAreaIndicatorFilter.class);
		return userFilter;
	}

}
