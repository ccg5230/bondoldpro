package com.innodealing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.innodealing.model.mysql.BondInstCode;
import com.innodealing.util.KitCost;
import com.innodealing.util.SQLParam;

/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2017年9月11日 下午2:12:38
 * @version V1.0
 *
 */
@Service
public class RatingService extends BaseService {

	public List<BondInstCode> getInnerRateList(Integer orgId) {
		SQLParam sqlParam = new SQLParam();
		sqlParam.append(
				"select id,name,sort,version,(SELECT COUNT(1) FROM t_bond_inst_rating_hist WHERE rating = instCode.id  ) `usage` from t_bond_inst_code instCode where type=? and org_id = ? and status = 1 order by sort ");
		sqlParam.addArg(1).addArg(orgId);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlParam.getSql(), sqlParam.getArg());
		return KitCost.mapListToBeanList(queryForList, BondInstCode.class);
	}

	public List<BondInstCode> getInvestSuggestList(Integer orgId) {
		SQLParam sqlParam = new SQLParam();
		sqlParam.append(
				"select id,name,sort,(SELECT COUNT(1) FROM t_bond_inst_rating_hist WHERE investment_advice = instCode.id  ) `usage` from t_bond_inst_code instCode where type=? and org_id = ? and status = 1 order by sort ");
		sqlParam.addArg(2).addArg(orgId);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlParam.getSql(), sqlParam.getArg());
		return KitCost.mapListToBeanList(queryForList, BondInstCode.class);
	}

	/**
	 * 删除 InstCode not id List 内
	 * 
	 * @param orgId
	 * @param type
	 *            1[内部评级] 2[投资建议]
	 * @return
	 */
	private int deleteInstCode(List<BondInstCode> rateList, int orgId, int type) {

		List<Integer> idArr = new ArrayList<>();
		for (BondInstCode code : rateList) {
			if (code.getId() != 0)
				idArr.add(code.getId());
		}

		SQLParam sqlParam = new SQLParam();
		sqlParam.append("DELETE FROM t_bond_inst_code WHERE 1=1 ");

		if (idArr.size() > 0) {
			sqlParam.append("and id not in(").placeholder(idArr.size()).append(") ");
			for (Integer id : idArr) {
				sqlParam.addArg(id);
			}
		}
		sqlParam.append("and org_id = ? and type = ? ");
		sqlParam.addArg(orgId).addArg(type);
		return jdbcTemplate.update(sqlParam.getSql(), sqlParam.getArg());
	}

	public BondInstCode getInstCodeById(int id) {
		SQLParam sqlParam = new SQLParam();
		sqlParam.append("select * from t_bond_inst_code where id = ?");
		sqlParam.addArg(id);
		Map<String, Object> queryForMap = jdbcTemplate.queryForMap(sqlParam.getSql(), sqlParam.getArg());
		if (queryForMap != null)
			return KitCost.mapToBean(queryForMap, BondInstCode.class);
		return null;
	}

	/**
	 * 内部评级
	 * 
	 * @param userId
	 * @param orgId
	 * @param rateList
	 * @return
	 */
	public int setInnerRate(Integer userId, Integer orgId, List<BondInstCode> rateList) {
		return saveInstCode(1, userId, orgId, rateList);
	}

	/**
	 * 
	 * @param type
	 *            1[内部评级] 2[投资建议]
	 * @param userId
	 * @param orgId
	 * @param rateList
	 */
	private int saveInstCode(int type, Integer userId, Integer orgId, List<BondInstCode> rateList) {
		boolean vFlag = false;// 版本号是否发生改变

		int deleteInstCode = deleteInstCode(rateList, orgId, type);
		if (deleteInstCode > 0) // 存在删除记录
			vFlag = true;

		for (BondInstCode tfCode : rateList) {
			if (tfCode.getId() == 0) { // 增加
				tfCode.setCreateBy(userId);
				tfCode.setOrgId(orgId);
				tfCode.setType(type);
				tfCode.setStatus(1);
				save(tfCode);
				vFlag = true;
			} else { // 其他更新

				BondInstCode oldModel = getInstCodeById(tfCode.getId());
				if (oldModel.getSort() != tfCode.getSort()) { // 顺序发生改变了
					vFlag = true;
				} else if (!oldModel.getName().equals(tfCode.getName())) { // 名称重命名
					// 批量修改名称
					if (type == 1) { // 内部评级->更新信评记录表 投资建议不需要->外键关联
						updateInstRatingHist(tfCode.getId(), tfCode.getName());
					}
				}
				tfCode.setUpdateBy(userId);
				tfCode.setUpdateTime(new Date());
				update(tfCode, new String[] { "name", "sort", "update_by", "update_time" }, "id=?", tfCode.getId());
			}
		}

		if (vFlag && (1 == type))// 内部评级修改 版本号+1
			increaseVersion(orgId);
		return 1;
	}

	/**
	 * 更新 信评记录表
	 * 
	 * @param id
	 * @param name
	 */
	private void updateInstRatingHist(int id, String name) {
		SQLParam sqlParam = new SQLParam();
		sqlParam.append("update t_bond_inst_rating_hist set rating_name = ? where rating = ? ");
		sqlParam.addArg(name).addArg(id);
		jdbcTemplate.update(sqlParam.getSql(), sqlParam.getArg());
	}

	/**
	 * 对应的机构 版本号 +1
	 * 
	 * @param orgId
	 * @param type
	 */
	private void increaseVersion(int orgId) {

		SQLParam sqlParam = new SQLParam();
		sqlParam.append("SELECT `version` FROM t_bond_inst_code WHERE org_id = ? LIMIT 1");
		sqlParam.addArg(orgId);
		Integer verOld = jdbcTemplate.queryForObject(sqlParam.getSql(), Integer.class, sqlParam.getArg());
		sqlParam.clear();
		sqlParam.append("UPDATE t_bond_inst_code SET version= ? WHERE org_Id = ? ");
		sqlParam.addArg(verOld + 1);
		sqlParam.addArg(orgId);
		jdbcTemplate.update(sqlParam.getSql(), sqlParam.getArg());
	}

	public int setInvestSuggest(Integer userId, Integer orgId, List<BondInstCode> rateList) {
		return saveInstCode(2, userId, orgId, rateList);
	}
}
