package com.innodealing.mapper;

import com.innodealing.model.mysql.BondInstRatingBondPub;

/**
 * 
 * 信评价 行业关系历史接口
 * @author 戴永杰
 *
 * @date 2018年1月4日 下午12:55:44 
 * @version V1.0   
 *
 */
public interface BondInstRatingBondPubMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(BondInstRatingBondPub record);

	int updateByPrimaryKey(BondInstRatingBondPub record);
}