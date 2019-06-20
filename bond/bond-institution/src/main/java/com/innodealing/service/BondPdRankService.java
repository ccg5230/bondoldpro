package com.innodealing.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.model.mongodb.BondPdRankDoc;
import com.innodealing.model.vo.BondDetailInfo;
import com.innodealing.util.KitCost;
import com.innodealing.util.SQLParam;

/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2018年1月15日 上午11:18:02 
 * @version V1.0   
 *
 */
@Service
public class BondPdRankService extends BaseService{

	public BondPdRankDoc getPdRankDetail(String key) {
		SQLParam sqlParam = new SQLParam();
		sqlParam.append("select * from dmdb.t_bond_basic_info where com_uni_code=? or bond_uni_code like ? or bond_code like ? or bond_short_name like ? or bond_full_name like ? or iss_name like ? limit 1 ");
		sqlParam.addArg(key).addLikeArg(key).addLikeArg(key).addLikeArg(key).addLikeArg(key).addLikeArg(key);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlParam.getSql(), sqlParam.getArg());
		if (queryForList.size() > 0){
			Long comUniCode = Long.parseLong(queryForList.get(0).get("com_uni_code")+"");
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(comUniCode));
			return bondMongoTemplate.findOne(query, BondPdRankDoc.class);
		}
		return null;
	}

}
