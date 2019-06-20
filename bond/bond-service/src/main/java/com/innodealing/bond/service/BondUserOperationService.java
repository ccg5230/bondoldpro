/**
 * 
 */
package com.innodealing.bond.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.engine.mongo.bond.BondUserOperationRepository;
import com.innodealing.model.dm.bond.DmUserRole;
import com.innodealing.model.mongo.dm.BondUserOperation;
import com.innodealing.model.mongo.dm.bond.user.UserContactDoc;
import com.innodealing.util.HttpClientUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

import javax.annotation.Resource;

@Service
public class BondUserOperationService {

	private static final Logger LOG = LoggerFactory.getLogger(BondUserOperationService.class);

	@Resource(name="bondMongo")
	protected MongoTemplate bondMongoTemplate;
	
	@Autowired	
	private JdbcTemplate jdbcTemplate;

	
	@Autowired
	private BondUserOperationRepository bondUserOperationRepository;
	
	@Value("${permission.code.bond.free}")
	private String freeCode;
	
	@Value("${permission.code.bond.pay}")
	private String payCode;
	
	@Value("${permission.code.bond.report}")
	private String exreportCode;
	
	@Value("${permission.code.bond.finareport}")
	private String exfinareportCode;
	
	@Value("${config.define.metaservice.accountUrl}")
	private String accountUrl;
	
	@Value("${config.define.metaservice.permissionsUrl}")
	private String permissionsUrl;
	
	@Value("${config.define.metaservice.accountCrmIdUrl}")
	private String accountCrmIdUrl;
	
	@Autowired
	private BondInstitutionInduAdapter bondInstitutionInduAdapter;
	
	public Long insert(BondUserOperation bondUserOperation){
		try{
			
			if(StringUtils.isEmpty(bondUserOperation.getBondUniCode())){
				LOG.info("记录用户操作失败,BondUniCode为空");
				return -1L;
			}else if(StringUtils.isEmpty(bondUserOperation.getBondChiName())){
				LOG.info("记录用户操作失败,BondChiName为空");
				return -1L;
			}else if(StringUtils.isEmpty(bondUserOperation.getUserId())){
				LOG.info("记录用户操作失败,UserId为空");
				return -1L;
			}else if(StringUtils.isEmpty(bondUserOperation.getType())){
				LOG.info("记录用户操作失败,Type为空");
				return -1L;
			}
			
			bondUserOperation.setId(bondUserOperation.toString());
			bondUserOperation.setCreateTime(new Date());
			bondMongoTemplate.insert(bondUserOperation);
		}catch(Exception e){
			LOG.error("记录用户操作失败..",e);
			return -1L;
		}
		return 0L;
	}
	
	public Long delete(Long userId,Long bondUniCode,Integer type){
		BondUserOperation vo = new BondUserOperation(userId,bondUniCode,type);
		bondUserOperationRepository.delete(vo.toString());
		return 0L;
	}

	public List<BondUserOperation> find(Long userId,Integer type){
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("userId").is(userId),Criteria.where("type").is(type));
		query.addCriteria(criteria);
		List<BondUserOperation> list = bondMongoTemplate.find(query, BondUserOperation.class);
		return list;
	}
	
	//返回为1 有中债权限，0没有中债权限
	public String findDebtBasic(Long userId){
		try{
		Integer org_id = bondInstitutionInduAdapter.getInstitution(userId);
		//中债权限
		String sql1 = "SELECT COUNT(1) FROM innodealing.dmms_organization_role WHERE role_id = 8 and organization_id = %1$d";
		if(jdbcTemplate.queryForObject(String.format(sql1, org_id), Long.class)>0)
			return "1";
		}catch(Exception e){
		}
		return "0";
	}
	
	
	/**
	 * 查询用权限
	 * @param userId
	 * @return
	 */
	@Deprecated
	public boolean moduleState(Long userId){
		boolean result = false;
		result = verifyAllCode(userId, new ArrayList<String>(Arrays.asList(payCode)), result);

		return result;    
    }

	private boolean verifyAllCode(Long userId, List<String> vcodes, boolean result) {
		String crmId = getAcmId(userId);

		if (StringUtils.isNotBlank(crmId)) {
			String permUrl = permissionsUrl.replace("crmId", crmId);
			LOG.info("verifyAllCode permissionsUrl:"+permUrl);
			String permJson = HttpClientUtil.doGet(permUrl, null);
			if (StringUtils.isNotBlank(permJson)) {
				//获取permissions 进行对比
				JSONObject permresObj = JSON.parseObject(permJson);
				JSONArray permArr = permresObj.getJSONArray("data");
				
				result = permArr.containsAll(vcodes);
			}else{
				LOG.info("verifyAllCode permissionsUrl接口返回为空，userId： "+userId);
			}
		}
		return result;
	}

	@Deprecated
	private boolean verifyDashboardCode(Long userId, String freeCode, String payCode, boolean result) {
		String crmId = getAcmId(userId);

		if (StringUtils.isNotBlank(crmId)) {
			String permUrl = permissionsUrl.replace("crmId", crmId);
			
			String permJson = HttpClientUtil.doGet(permUrl, null);
			if (StringUtils.isNotBlank(permJson)) {
				//获取permissions 进行对比
				JSONObject permresObj = JSON.parseObject(permJson);
				JSONArray permArr = permresObj.getJSONArray("data");
				
				result = (permArr.contains(freeCode) || permArr.contains(payCode));
			}else{
				LOG.info("permissionsUrl接口返回为空，userId： "+userId);
			}
		}
		return result;
	}
	
	private String getAcmId(Long userId) {
		String crmId = "";
		String actresJson = HttpClientUtil.doPostJson(accountUrl, "["+userId+"]");
		if (StringUtils.isNotBlank(actresJson)) {
			JSONObject accObj = JSON.parseObject(actresJson);
			JSONArray accArr = accObj.getJSONArray("data");
			if (null != accArr && accArr.size() > 0) {
				JSONObject account = (JSONObject) accArr.get(0);
				crmId = SafeUtils.getString(account.get("crmId"));
			}
		}else{
			LOG.info("accountUrl接口返回为空，userId： "+userId);
		}
		return crmId;
	}
	
	/**
     * 联系我们
     * @param user
     * @return
     */
    public boolean contactUs(UserContactDoc user ){
		bondMongoTemplate.save(user);
        return true;
    }

	public boolean getReportPerm(Long userId) {
		boolean result = false;
		result = verifyAllCode(userId, new ArrayList<String>(Arrays.asList(payCode, exreportCode)), result);

		return result;
	}
	
	public boolean getFinaReportPerm(Long userId) {
		boolean result = false;
		result = verifyAllCode(userId, new ArrayList<String>(Arrays.asList(payCode, exreportCode, exfinareportCode)), result);

		return result;
	}

	public boolean getPaymentPerm(Long userId) {
		boolean result = false;
		result = verifyAllCode(userId, new ArrayList<String>(Arrays.asList(payCode)), result);

		return result;    
	}
	
	public Map<String,Object> bondAuthorization(Long userId){
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT func.code as code , 1 as status FROM innodealing.dm_bond_func func \n\t");
		sql.append("INNER JOIN innodealing.dm_bond_role_func rf ON func.id = rf.func_id \n\t");
		sql.append("INNER JOIN innodealing.dm_bond_role role ON role.id = rf.role_id \n\t");
		sql.append("INNER JOIN innodealing.dm_bond_user_role ur ON ur.role_id = role.id AND ur.user_id = " + userId);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String,Object> result = new HashMap<String,Object>();
		list.stream().forEach(obj->{
			result.put(obj.get("code").toString(), obj.get("status"));
		});
		return result;
	}
	
	/**
	 * 根据crmId查找dmId
	 * @param userId
	 * @return
	 */
	private String getDmId(Long userId) {
		String dmId = "";
		String actresJson = HttpClientUtil.doPostJson(accountCrmIdUrl, "["+userId+"]");
		if (StringUtils.isNotBlank(actresJson)) {
			JSONObject accObj = JSON.parseObject(actresJson);
			JSONArray accArr = accObj.getJSONArray("data");
			if (null != accArr && accArr.size() > 0) {
				JSONObject account = (JSONObject) accArr.get(0);
				dmId = SafeUtils.getString(account.get("dmId"));
			}
		}else{
			LOG.info("accountCrmIdUrl接口返回为空，userId： "+userId);
		}
		return dmId;
	}
	
	public Map<String,Object> headAuthorization(Long crmId){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//根据crmid找到dmid
		String dmId = getDmId(crmId);
		if(StringUtils.isEmpty(dmId)){
			return resultMap;
		}
		
		//根据dmid找到债劵板块权限
		Map<String,Object> bondAuthorizationMap =  bondAuthorization(Long.parseLong(dmId));
		if(bondAuthorizationMap==null || bondAuthorizationMap.size()<1){
			return resultMap;
		}
		
		//权限映射
		resultMap = authorizationHandle(bondAuthorizationMap);
		
		//自定义行业权限
		if(!bondInstitutionInduAdapter.isInstitutionIndu(Long.parseLong(dmId.toString()))){
			resultMap.put("BOND.INDUSWITCH", 1);
		}
		//一级发行
		if(resultMap.size()>0){
			resultMap.put("BOND.NEWISSUES", 1);
		}
		
		return resultMap;
	}
	
	private Map<String,Object> authorizationHandle(Map<String,Object> bondAuthorizationMap){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(bondAuthorizationMap.keySet().contains("MarketDynamics")){
			map.put("BOND.CREDITCOMPARISON", 1);
		}
		if(bondAuthorizationMap.keySet().contains("AbnormalDynamics")){
			map.put("BOND.ABNOMALPRICE", 1);
		}
		if(bondAuthorizationMap.keySet().contains("TodayDeal")){
			map.put("BOND.DEALTODAY", 1);
		}
		if(bondAuthorizationMap.keySet().contains("TodayQuote")){
			map.put("BOND.QUOTETODAY", 1);
		}
		if(bondAuthorizationMap.keySet().contains("Sentiment")){
			map.put("BOND.PUBLICOPINION", 1);
		}
		if(bondAuthorizationMap.keySet().contains("RatingChange")){
			map.put("BOND.RATINGCHANGE", 1);
		}
		if(bondAuthorizationMap.keySet().contains("RatingPool")){
			map.put("BOND.CREDITRATINGPOOL", 1);
		}
		if(bondAuthorizationMap.keySet().contains("BondFilter")){
			map.put("BOND.CREDITBONDFILTERING", 1);
		}
		if(bondAuthorizationMap.keySet().contains("Follow")){
			map.put("BOND.FOLLOWING", 1);
		}
		if(bondAuthorizationMap.keySet().contains("EarningsScoring")){
			map.put("BOND.FINANCIALREPORTRATING", 1);
		}
		if(bondAuthorizationMap.keySet().contains("Rating")||
		    bondAuthorizationMap.keySet().contains("BondIndu")||
			bondAuthorizationMap.keySet().contains("BondCom")){
			map.put("BOND.SETTINGS", 1);
		}
		if(bondAuthorizationMap.keySet().contains("BondCompari")){
			map.put("BOND.COMPARISON", 1);
		}
				if(bondAuthorizationMap.keySet().contains("RatingDemandPending")||
			    bondAuthorizationMap.keySet().contains("RatingDemandPendingAudit")||
				bondAuthorizationMap.keySet().contains("RatingDemandComplete")){
				map.put("BOND.CREDITRATING", 1);
		}
		return map;
	}
	
	
	/**
	 * @param id
	 * @param type 类型 1[机构] 2[用户]
	 * @return
	 */
	public Boolean instInduAuthorization(Long id,Integer type){
		
		Boolean flay = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) FROM innodealing.dm_bond_industry_relation \n\t");
		sql.append("WHERE type = %1$d AND  ref_id = %2$d");
		
		flay = jdbcTemplate.queryForObject(String.format(sql.toString(),type,id), Integer.class)>0;
		
		return flay;
	}
	
	public Map<String,Object> instDataAuthorization(Long userId){
		Map<String,Object> result = new HashMap<String,Object>();
		//机构ID
		Integer org_id = bondInstitutionInduAdapter.getInstitution(userId);
		//自定义行业权限
		if(bondInstitutionInduAdapter.isInstitutionIndu(userId))result.put("InstIndu", 1);
		//中债权限
		String sql1 = "SELECT COUNT(1) FROM innodealing.dm_bond_user_rights WHERE TYPE = 1 AND user_id = %1$d AND org_id = %2$d";
		if(jdbcTemplate.queryForObject(String.format(sql1,userId,org_id), Long.class)>0)result.put("ChinaBond", 1);
		//内部评级权限
		String sql2 = "SELECT COUNT(1) FROM institution.t_bond_inst_code WHERE org_id = %1$d";
		if(jdbcTemplate.queryForObject(String.format(sql2, org_id), Long.class)>0)result.put("InstRating", 1);
		//DM量化评分隐藏数据
		String sql3 = "SELECT COUNT(1) FROM innodealing.dm_bond_user_rights WHERE TYPE = 2 AND user_id = %1$d AND org_id = %2$d";
		if(jdbcTemplate.queryForObject(String.format(sql3, userId ,org_id), Long.class)>0)result.put("DmPd", 1);
		//收益率曲线
		String sql4 = "SELECT COUNT(1) FROM innodealing.dm_bond_user_rights WHERE TYPE = 3 AND user_id = %1$d AND org_id = %2$d";
		if(jdbcTemplate.queryForObject(String.format(sql4, userId ,org_id), Long.class)>0)result.put("YieldCurve", 1);
		return result;
	}
}
