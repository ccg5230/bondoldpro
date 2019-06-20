package com.innodealing.dao.jdbc.dm.base;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jdbc.BaseDao;
import com.innodealing.domain.BondComExtVO;
import com.innodealing.model.dm.bond.BondComExt;

/**
 * @author kunkun.zhou
 * @date 2016年12月07日
 * @clasename BondComExtDao.java
 * @decription TODO
 */
@Component
public class BondComExtDao extends BaseDao<BondComExt> {
	
	public BondComExtVO getComInfoByComUniCode(Long comUniCode) {
		String sql = "SELECT a.ama_com_id as amaComId,"
				+ " case when LOCATE('银行', b.comp_cls_name)>0 then 'bank' "
				+ " when LOCATE('证券', b.comp_cls_name)>0 then 'secu' "
				+ " when LOCATE('保险', b.comp_cls_name)>0 then 'insu' "
				+ " when LOCATE('企业', b.comp_cls_name)>0 then 'manu' "
				+ " else null end as compClsName "
				+ " FROM t_bond_com_ext a left join amaresun.tbl_industry_classification b "
				+ " on a.indu_uni_code_l4=b.industry_code where a.com_uni_code = " + comUniCode + " LIMIT 1";
		List<BondComExtVO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(BondComExtVO.class));
		if (null == list || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
