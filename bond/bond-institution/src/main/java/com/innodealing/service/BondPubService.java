package com.innodealing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.dao.UserOrgInfoDao;
import com.innodealing.model.PageAdapter;
import com.innodealing.model.VBondPubComDetail;
import com.innodealing.model.mysql.BondInstBondPub;
import com.innodealing.model.mysql.BondInstRatingFile;
import com.innodealing.model.mysql.VBondInfo;
import com.innodealing.model.mysql.VBondPub;
import com.innodealing.model.mysql.VPubInfo;
import com.innodealing.model.vo.BondPubList;
import com.innodealing.model.vo.CreditRatingAuditInfo;
import com.innodealing.util.KitCost;
import com.innodealing.util.KitMongdb;
import com.innodealing.util.SQLParam;
import com.innodealing.util.UIAdapter;
import com.mongodb.BasicDBObject;

/**
 * 债券发行人
 * 
 * @author 戴永杰
 *
 * @date 2017年9月14日 上午9:52:38
 * @version V1.0
 *
 */
@Service
public class BondPubService extends BaseService {

	private static final Logger LOG = LoggerFactory.getLogger(BondPubService.class);

	public List<VPubInfo> getBondPubList(String key, int limit) {
		SQLParam param = new SQLParam();
		param.append("SELECT com_uni_code,com_chi_name,com_chi_short_name FROM bond_ccxe.D_PUB_COM_INFO_2 ");
		param.append("WHERE ISVALID = 1 and (com_chi_name like ? or com_uni_code like ?) ");
		if (limit > 0) {
			param.append("limit " + limit);
		}
		param.addLikeArg(key);
		param.addLikeArg(key);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(param.getSql(), param.getArg());
		return KitCost.mapListToBeanList(queryForList, VPubInfo.class);
	}

	public List<VBondInfo> getBondList(String key, int limit) {
		SQLParam param = new SQLParam();
		param.append(
				"SELECT bond_uni_code,com_uni_code,bond_short_name,bond_full_name,sec_mar_par FROM dmdb.t_bond_basic_info ");
		param.append("WHERE bond_full_name LIKE ? OR bond_short_name LIKE ? OR bond_uni_code LIKE ? ");
		if (limit > 0) {
			param.append("limit " + limit);
		}
		param.addLikeArg(key);
		param.addLikeArg(key);
		param.addLikeArg(key);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(param.getSql(), param.getArg());
		List<VBondInfo> mapListToBeanList = KitCost.mapListToBeanList(queryForList, VBondInfo.class);
		mapListToBeanList.forEach(x -> {
			x.setBondCode(x.getBondUniCode() + UIAdapter.cvtSecMar2Postfix(x.getSecMarPar()));
		});
		return mapListToBeanList;
	}

	/**
	 * 
	 * @param bondUniCode
	 *            债券Id
	 * @param comUniCode
	 *            发行人Id
	 * @param orgId
	 *            机构Id
	 * @return
	 */
	public List<BondInstBondPub> getBondInstBondPub(Integer bondUniCode, Integer comUniCode, int orgId) {
		SQLParam param = new SQLParam();
		param.append("SELECT * FROM institution.t_bond_inst_bond_pub WHERE org_id = ? ");
		param.addArg(orgId);
		if (bondUniCode != null) {
			param.append("and bond_uni_code=? ").addArg(bondUniCode);
		}
		if (comUniCode != null) {
			param.append("AND com_uni_code = ? ").addArg(comUniCode);
		}
		return KitCost.mapListToBeanList(jdbcTemplate.queryForList(param.getSql(), param.getArg()),
				BondInstBondPub.class);
	}

	/**
	 * 
	 * @param id
	 *            ID [多个用逗号分割]
	 * @param type
	 *            类型 1 债券 2 发行人
	 * @param comUniCode
	 *            关联发行人ID
	 * @param remark
	 *            备注
	 * @param userId
	 *            当前用户
	 * @return
	 */
	public int setBondPub(String id, Integer type, Integer comUniCode, String remark, int userId, int orgId) {
		BondInstBondPub tf = null;
		String[] codeArr = id.split(",");
		if (1 == type) { // 债券

			// 删除重复添加的数据
			deleteRepeatBondPub(codeArr, comUniCode);
			for (String code : codeArr) {
				tf = new BondInstBondPub();
				tf.setCreateBy(userId);
				tf.setComUniCode(comUniCode);
				tf.setBondUniCode(Integer.parseInt(code));
				tf.setRemark(remark);
				tf.setOrgId(orgId);
				tf.setCreateTime(new Date());
				save(KitCost.beanToMap(tf), "t_bond_inst_bond_pub");
			}
		} else if (2 == type) { // 发行人 -- 执行批量操作
			for (String code : codeArr) {

				// 删除重复添加的数据
				SQLParam param = new SQLParam();
				param.append("SELECT bond_uni_code FROM dmdb.t_bond_basic_info WHERE com_uni_code = ?");
				param.addArg(comUniCode);
				List<String> bondUniCodeList = jdbcTemplate.queryForList(param.getSql(), String.class, param.getArg());
				deleteRepeatBondPub(bondUniCodeList.toArray(new String[bondUniCodeList.size()]), comUniCode);

				param.clear();
				param.append(
						"INSERT INTO `t_bond_inst_bond_pub` (com_uni_code,bond_uni_code,org_id,remark,create_by )");
				param.append(" SELECT ?, bond_uni_code, ?,?, ? FROM dmdb.t_bond_basic_info WHERE com_uni_code = ?");
				param.addArg(comUniCode).addArg(orgId).addArg(remark).addArg(userId).addArg(code);
				jdbcTemplate.update(param.getSql(), param.getArg());
			}
		}
		return 1;
	}

	private int deleteRepeatBondPub(String[] id, Integer comUniCode) {

		if (id.length == 0)
			return 0;
		SQLParam param = new SQLParam();
		param.append("DELETE FROM t_bond_inst_bond_pub WHERE bond_uni_code IN(");
		param.placeholder(id.length);
		param.append(") AND com_uni_code = ?");
		param.addArg(id);
		param.addArg(comUniCode);
		return jdbcTemplate.update(param.getSql(), param.getArg());
	}

	public int removeBondPub(String[] id) {
		SQLParam param = new SQLParam();
		param.append("delete from t_bond_inst_bond_pub where id in(");
		param.placeholder(id.length).append(") ");
		param.addArg(id);
		jdbcTemplate.update(param.getSql(), param.getArg());
		return 1;
	}

	/**
	 * 
	 * @param key
	 * @param type
	 *            1债券简称/发行人 2 关联发行人
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageAdapter<VBondPub> getBondPubListPage(int orgId, String key, Integer type, int pageNumber, int pageSize) {
		SQLParam param = new SQLParam();
		param.append(
				"SELECT pb.id,pb.bond_uni_code, bInfo.bond_short_name,bInfo.bond_full_name ,binfo.iss_name,pb.remark,pInfo.com_chi_name FROM `t_bond_inst_bond_pub` pb ");
		param.append("LEFT JOIN  bond_ccxe.D_PUB_COM_INFO_2 pInfo ON pb.com_uni_code = pInfo.com_uni_code ");
		param.append("LEFT JOIN dmdb.t_bond_basic_info bInfo ON pb.bond_uni_code = bInfo.bond_uni_code ");
		param.append("WHERE 1=1 and org_id = ? ");
		param.addArg(orgId);
		if (StringUtils.isNotBlank(key)) {
			if (1 == type) {
				param.append("and pb.bond_uni_code = ? OR bInfo.bond_short_name like ? OR bInfo.iss_name like ? ");
				param.addArg(key);
				param.addLikeArg(key);
				param.addLikeArg(key);
			} else if (2 == type) {
				param.append("and pInfo.com_chi_name LIKE ? ");
				param.addLikeArg(key);
			} else { // 其他情况
				param.append("and 1=2");
			}

		}
		param.append("order by id desc");

		return new PageAdapter<>(
				KitCost.mapListToBeanList(
						jdbcTemplate.queryForList(param.getPageSQL(pageNumber, pageSize), param.getArg()),
						VBondPub.class),
				new PageRequest(pageNumber, pageSize),
				jdbcTemplate.queryForObject(param.getPageCountSQL(), param.getArg(), long.class));
	}

	public PageAdapter<VBondPubComDetail> getBondPubOnBondComDetail(int orgId, String comUniCode, Integer pageNumber,
			Integer pageSize) {
		SQLParam param = new SQLParam();
		param.append("SELECT rating,investment_advice,investment_advice_desdetail,rating_describe,bPub.id,bPub.bond_uni_code,bPub.remark,rating.rating_time investDate ,instInvestmentAdvice,rating.rating_name FROM institution.t_bond_inst_bond_pub bPub ");
		param.append("left join ( select rating,investment_advice,investment_advice_desdetail,rating_describe,bond_uni_code,rating_time,rating_name,inst_id ,c.name instInvestmentAdvice ");
		param.append("from t_bond_inst_rating_hist rating left join institution.t_bond_inst_code c on c.id = rating.investment_advice  where rating.status = 3 and rating.fat_id is null order by rating.id desc) ");
		param.append("rating on bPub.bond_uni_code = rating.bond_uni_code and rating.inst_id = bPub.org_id ");
		param.append("where bPub.com_uni_code =?  and bPub.org_Id = ? group by bPub.bond_uni_code order by bPub.id desc");
		param.addArg(comUniCode);
		param.addArg(orgId);
		PageAdapter<VBondPubComDetail> pageAdapter = new PageAdapter<>(
				KitCost.mapListToBeanList(
						jdbcTemplate.queryForList(param.getPageSQL(pageNumber, pageSize), param.getArg()),
						VBondPubComDetail.class),
				new PageRequest(pageNumber, pageSize),
				jdbcTemplate.queryForObject("select count(*) from ("+ param.getPageCountSQL().replace("count(*)", "1")+") tt", param.getArg(), Long.class));

		pageAdapter.getContent().forEach(x -> {
			try {
				if (x.getBondUniCode() != 0) {
					Query query = new BasicQuery(new BasicDBObject(), buildFixedCols);
					query.addCriteria(Criteria.where("_id").is(x.getBondUniCode()));
					VBondPubComDetail t = bondMongoTemplate.findOne(query, VBondPubComDetail.class);
					x.setName(t.getName());
					x.setCode(t.getCode());
					x.setTenor(t.getTenor());
					x.setCoupRate(t.getCoupRate());
					x.setBondRating(t.getBondRating());
					x.setImpliedRating(t.getImpliedRating());
					x.setFairValue(t.getFairValue());

					x.setBidPrice(t.getBidPrice());
					x.setBidVol(t.getBidVol());
					x.setBidOrderCnt(t.getBidOrderCnt());
					x.setOfrOrderCnt(t.getOfrOrderCnt());
					x.setOfrVol(t.getOfrVol());
					x.setOfrPrice(t.getOfrPrice());
					x.setPrice(t.getPrice());
					x.setLatelyPayDate(t.getLatelyPayDate());

					if (x.getInvestmentAdviceDesdetail() != null) {
						x.setInstInvestmentAdviceDescribe(1L);
					}
					if (x.getRatingDescribe() != null) {
						x.setInstRatingDescribe(1L);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		return pageAdapter;
	}

	private final static BasicDBObject buildFixedCols = KitMongdb.buildFixedCols("name", "code", "tenor", "coupRate",
			"instRatingDescribe", "instInvestmentAdviceDescribe", "bondRating", "impliedRating", "fairValue",
			"bidOrderCnt", "bidVol", "bidPrice", "ofrVol", "ofrPrice", "ofrOrderCnt", "price", "latelyPayDate");

	public Integer checkBondPubValid(String comUniCode) {

		SQLParam param = new SQLParam();
		param.append("SELECT COUNT(*) FROM dmdb.t_bond_basic_info WHERE com_uni_code = ? ");
		param.addArg(comUniCode);
		return jdbcTemplate.queryForObject(param.getSql(), param.getArg(), Integer.class);
	}

	/**
	 * 
	 * @param userId
	 * @param induType
	 * @param bondUniCode
	 * @param orgId
	 * @return
	 */
	public List<BondPubList> getBondPubListByBondCode(int userId, int induType, String bondUniCode, int orgId) {
		SQLParam param = new SQLParam();
		param.append(
				"SELECT bondPub.bond_uni_code, pInfo.com_chi_name,bondPub.com_uni_code,remark ,ratingGroup.group_name,rating_hist.rating_id,rating_hist.rating_name,rating_hist.rating_time,rating_hist.rating_by,indu_uni_code,indu_uni_name,rating_hist.fat_id,rating_hist.version  ");
		param.append("FROM institution.t_bond_inst_bond_pub  bondPub ");
		param.append("left join  bond_ccxe.D_PUB_COM_INFO_2 pInfo ON bondPub.com_uni_code = pInfo.com_uni_code ");
		param.append(
				"left join ( select group_id, issuer_id,org_id from t_bond_credit_rating where bond_uni_code!=0 group by group_id,issuer_id,org_id ) rating ON bondPub.com_uni_code = rating.issuer_id and bondPub.org_id = rating.org_id ");
		param.append(
				"LEFT JOIN institution.t_bond_credit_rating_group ratingGroup ON rating.group_id = ratingGroup.id  ");
		param.append("LEFT JOIN (SELECT *  FROM ( ");
		param.append(
				"	SELECT rating_by,com_uni_code,rating rating_id,rating_name,rating_time,inst_id,use_old_rating,fat_id,version  FROM institution.t_bond_inst_rating_hist where status = 3 ORDER BY rating_time DESC ) rating_hist GROUP BY com_uni_code ");
		param.append(
				"	) rating_hist ON rating_hist.com_uni_code = bondPub.com_uni_code and bondPub.org_id = rating_hist.inst_id ");
		if (induType == 0) { // GICS 行业
			param.append(
					"LEFT JOIN (select com_uni_code,indu_uni_code,indu_uni_name from dmdb.t_bond_com_ext ) custIndu ");
			param.append("ON custIndu.com_uni_code = bondPub.com_uni_code  ");
		} else if (induType == 1) { // 申万
			param.append(
					"LEFT JOIN (SELECT com_uni_code,indu_uni_code_sw indu_uni_code,indu_uni_name_sw indu_uni_name FROM dmdb.t_bond_com_ext ) custIndu ");
			param.append("ON custIndu.com_uni_code = bondPub.com_uni_code ");
		} else if (induType == 2) {// 自定义
			param.append(
					"left join (select com_uni_code,indu_uni_code,indu_uni_name,inst_id from institution.t_bond_inst_com_indu) custIndu ");
			param.append("ON custIndu.com_uni_code = bondPub.com_uni_code and custIndu.inst_id = bondPub.org_id ");
		}
		param.append("WHERE bondPub.bond_uni_code = ? AND bondPub.org_id = ? ");
		param.addArg(bondUniCode).addArg(orgId);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(param.getSql(), param.getArg());
		List<BondPubList> list = KitCost.mapListToBeanList(queryForList, BondPubList.class);

		List<Integer> userIds = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRatingBy() != 0) {
				userIds.clear();
				userIds.add(list.get(i).getRatingBy());
				List<Map<String, Object>> userInfo = userOrgInfoDao.queryUserByUserIdList(userIds, userId);
				list.get(i).setRatingByName(userInfo.get(0).get("name").toString());
			}
		}
		return list;
	}

	public static void main(String[] args) {

		new BondPubService().getBondPubListByBondCode(11, 1, "", 1);

	}

	/**
	 * 获取信审核信息
	 * 
	 * @param comUniCode
	 * @param fatId
	 * @param orgId
	 * @return
	 */
	public List<CreditRatingAuditInfo> getRatingAuditInfo(String bondUniCode, long fatId) {
		SQLParam param = new SQLParam();
		param.append(
				"SELECT bond_uni_code,bond_chi_name,com_uni_code,com_chi_name,old_version,old_rating,old_related_notes,old_group_name,old_indu_name,old_rating_by_name,old_rating_time,old_rating_name,old_rating,old_investment_describe,IFNULL(use_old_rating,0) use_old_rating,rating,rating_name,rating_describe,ratingFile FROM institution.`t_bond_inst_rating_hist`  ratingHist ");
		param.append(
				"LEFT JOIN (SELECT inst_rating_id,GROUP_CONCAT(TYPE,'|',file_path,'|',file_name,'|',oss_key) AS ratingFile FROM institution.`t_bond_inst_rating_file` WHERE STATUS = 1 AND TYPE =2 ");
		param.append("GROUP BY inst_rating_id ) ratingFile on ratingFile.inst_rating_id = ratingHist.id ");
		param.append("where ratingHist.bond_uni_code = ? and ratingHist.fat_id = ?");
		param.addArg(bondUniCode).addArg(fatId);

		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(param.getSql(), param.getArg());

		List<CreditRatingAuditInfo> auditInfoList = KitCost.mapListToBeanList(queryForList,
				CreditRatingAuditInfo.class);

		for (CreditRatingAuditInfo auditInfo : auditInfoList) {
			// 信凭附件
			String ratingFile = auditInfo.getRatingFile();

			List<BondInstRatingFile> bondInstRatingFilesNew = new ArrayList<>();
			BondInstRatingFile bondInstRatingFile = null;

			if (StringUtils.isNotBlank(ratingFile)) {
				String[] split = ratingFile.split(",");
				for (String string : split) {
					bondInstRatingFile = new BondInstRatingFile();
					String[] split2 = string.split("|");
					bondInstRatingFile.setType(Integer.parseInt(split2[0]));
					bondInstRatingFile.setFilePath(split2[1]);
					bondInstRatingFile.setFileName(split2[2]);
					bondInstRatingFile.setOssKey(split2[3]);
					bondInstRatingFilesNew.add(bondInstRatingFile);
				}
			}
			auditInfo.setBondInstRatingFiles(bondInstRatingFilesNew);
		}

		return auditInfoList;

	}

	private @Autowired UserOrgInfoDao userOrgInfoDao;

}
