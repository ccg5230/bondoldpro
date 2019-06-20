package com.innodealing.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.model.vo.BondDetail;
import com.innodealing.model.vo.BondDetailBase;
import com.innodealing.model.vo.BondDetailInfo;
import com.innodealing.util.KitCost;
import com.innodealing.util.KitMongdb;
import com.innodealing.util.SQLParam;
import com.mongodb.BasicDBObject;

@Service
public class BondDetailService extends BaseService {

	public BondDetailInfo getBondDetailInfo(String comUniCode) {

		SQLParam sqlParam = new SQLParam();
		sqlParam.append("SELECT com_uni_code ,com_chi_name, com_chi_short_name,leg_per,ic_reg_code,com_tel,staff_sum,com_web,reg_addr,com_pro FROM bond_ccxe.d_pub_com_info_2 WHERE com_uni_code = ? ");
		sqlParam.addArg(comUniCode);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlParam.getSql(), sqlParam.getArg());
		if (queryForList.size() > 0)
			return KitCost.mapToBean(queryForList.get(0), BondDetailInfo.class);
		return null;
	}

	public BondDetail getBondDetail(String bondId) {
		BasicDBObject buildFixedCols = KitMongdb.buildFixedCols("_id","bondUniCode", "code", "fullName", "shortName", "dmBondTypeName", "market", "newSize", "munInvest", "issCoupRate", "newCoupRate", "rateType", "intePayCls", "exerPayDate", "theoEndDate", "baseRate", "yearPayDate", "guruName", "guraName1", "pledgeName", "pledgeCode", "isRedemPar", "isResaPar"
		);
		BasicDBObject queryCondition = new BasicDBObject();

		Criteria criatira = new Criteria();
		criatira.and("_id").is(Long.valueOf(bondId));
		Query query = new BasicQuery(queryCondition, buildFixedCols);
		query.addCriteria(criatira);

		BondDetail bondDetail = bondMongoTemplate.findOne(query, BondDetail.class, "bond_basic_info");
		
		buildFixedCols = KitMongdb.buildFixedCols("estYield","estDirtyPrice","estCleanPrice","staticSpread","staticSpreadInduQuantile","macd","modd","convexity");
		query = new BasicQuery(queryCondition, buildFixedCols);
		query.addCriteria(criatira);
		
		BondDetail bondDetail2 = bondMongoTemplate.findOne(query, BondDetail.class, "bond_detail_info");
		bondDetail.setEstYield(bondDetail2.getEstYield());
		bondDetail.setEstDirtyPrice(bondDetail2.getEstDirtyPrice());
		bondDetail.setEstCleanPrice(bondDetail2.getEstCleanPrice());
		bondDetail.setStaticSpread(bondDetail2.getStaticSpread());
		bondDetail.setStaticSpreadInduQuantile(bondDetail2.getStaticSpreadInduQuantile());
		bondDetail.setMacd(bondDetail2.getMacd());
		bondDetail.setModd(bondDetail2.getModd());
		bondDetail.setConvexity(bondDetail2.getConvexity());

		return bondDetail;
	}

	public BondDetailBase getBondDetailBase(String comUniCode) {
		SQLParam sqlParam = new SQLParam();
		sqlParam.append("SELECT com_uni_code,reg_cap,com_attr_par,comInfo.cury_type_par,curyType.cury_chi_name,est_date,sta_par,is_list_par,comInfo.dis_date,code2.AREA_UNI_CODE pro_code,code2.area_name2 pro_name, code1.AREA_UNI_CODE city_code,code1.area_name3 city_name ");
		sqlParam.append("FROM bond_ccxe.d_pub_com_info_2 comInfo LEFT JOIN bond_ccxe.PUB_CURY_TYPE curyType ON comInfo.cury_type_par = curyType.cury_type_par ");
		sqlParam.append("LEFT JOIN bond_ccxe.PUB_AREA_CODE code1 ON code1.AREA_UNI_CODE = comInfo.AREA_UNI_CODE ");
		sqlParam.append("LEFT JOIN bond_ccxe.PUB_AREA_CODE code2 ON code2.AREA_UNI_CODE = comInfo.AREA_UNI_CODE1 ");
		sqlParam.append("WHERE com_uni_code = ? ");
		sqlParam.addArg(comUniCode);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlParam.getSql(), sqlParam.getArg());
		if (queryForList.size() > 0)
			return KitCost.mapToBean(queryForList.get(0), BondDetailBase.class);
		return null;
	}

}
