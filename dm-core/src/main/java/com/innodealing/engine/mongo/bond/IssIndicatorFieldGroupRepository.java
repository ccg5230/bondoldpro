package com.innodealing.engine.mongo.bond;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.model.mongo.dm.bond.detail.analyse.IssIndicatorFieldGroupMapping;
/**
 * 发行人性能指标字段分组映射
 * @author zhaozhenglai
 * @since 2016年9月20日 上午10:31:32 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */

@Component
public class IssIndicatorFieldGroupRepository{
    
    @Autowired private MongoTemplate mongoTemplate;
    
    /**
     * 获取映射关系
     * @return
     */
    public  Map<String,IssIndicatorFieldGroupMapping> findIndicatorFieldGroupMapping(){
        List<IssIndicatorFieldGroupMapping> list = mongoTemplate.findAll(IssIndicatorFieldGroupMapping.class);
        Map<String,IssIndicatorFieldGroupMapping> mapping = new HashMap<>();
        for (IssIndicatorFieldGroupMapping issIndicatorFieldGroupMapping : list) {
            String columnName = issIndicatorFieldGroupMapping.getColumnName();
            if(columnName != null){
                mapping.put(columnName.replace(" ", ""), issIndicatorFieldGroupMapping);
            }
        }
        return mapping;
    }
}
