package com.innodealing.bond.service.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.innodealing.bond.param.area.IndicatorAreaInstrucationsFilter;
import com.innodealing.bond.service.user.UserAreaIndicatorFilterService;
import com.innodealing.bond.vo.area.IndicatorAreaIndicatorVo;
import com.innodealing.consts.Constants;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.bond.user.UserAreaIndicatorFilter;

@Service
public class AreaIndicatorService {
	@Autowired 
    private UserAreaIndicatorFilterService userAreaIndicatorFilterService;
	
	

	public boolean findIssuerIndicators(Long userId, IndicatorAreaInstrucationsFilter filter) {
	//保存用户筛选条件
		boolean result = false;	
	if(filter.getFields() != null){
		UserAreaIndicatorFilter userIndicatorFilter = userAreaIndicatorFilterService.buildUserAreaIndicatorFilter(userId, filter.getFields());
		result = userAreaIndicatorFilterService.save(userIndicatorFilter);
		if(Objects.equal(result, true)){
			return true;
		}
	}
	return result;
	
	}

}
