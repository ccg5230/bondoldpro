package com.innodealing.engine.jdbc.im;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.innodealing.domain.SysuserAndOrgainfo;
import com.innodealing.domain.SysuserInstDto;
import com.innodealing.engine.jdbc.BaseDao;
import com.innodealing.model.im.user.Sysuser;
import com.innodealing.util.BeanUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

@Component
public class SysuserDao extends BaseDao<Sysuser> {

	/**
	 * findSysuserOrgInfo
	 * @param userId
	 * @return
	 */
	public SysuserAndOrgainfo findSysuserOrgInfo(Long userId){
		SysuserAndOrgainfo sao = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.id as id,s.name as name,s.spellname as spellname,s.usertype as usertype, s.companyshort as companyshort,s.companyphone as companyphone,s.companytype as companytype, s.qq as qq,s.wechatno as wechatno,s.mobile as mobile,s.instprovince as instprovince,s.instcity as instcity,s.onlinestate as onlinestate,");
	    sql.append("s.imflag as imflag,t.id as orgainfoId,t.type as orgainfoType,t.fullname as orgainFullName,t.shortname as orgainShortName,t.provinceid as orgainProvinceid,t.assetscale as assetScale,t.sectiontype as sectionType, a.name AS cityName ");
		sql.append("FROM innodealing.t_sysuser s LEFT JOIN innodealing.t_orgainfo t ON s.company=t.fullname ");
		sql.append("LEFT JOIN innodealing.t_area a ON a.id=s.instcity WHERE s.status=1 ");
		sql.append("AND s.id = ? LIMIT 1 ");
		Map<String, Object> result =  null;
		try {
			result = jdbcTemplate.queryForMap(sql.toString(),userId);
			sao = BeanUtil.map2Bean(result, SysuserAndOrgainfo.class);
		} catch (Exception e) {
            logger.info("findSysuserOrgInfo, the return value is null!");
		}
		return sao;
	}
	
	/**
	 * getTroopidsByUserid
	 * @param userid
	 * @return
	 */
	public String findTroopidsByUserid(Long userid) {
        String troopString = "";
        
        if(null != userid){
            StringBuffer troopids = new StringBuffer();
            
            String qqSql="SELECT troopid FROM innodealing.t_qqsgroup t LEFT JOIN innodealing.t_sysuser s ON t.dmacct=s.spellname WHERE s.id =" + userid;
            List<Map<String,Object>> qqtroops = jdbcTemplate.queryForList(qqSql);
            String qqTroopid = ""; 
            if (qqtroops != null && qqtroops.size() > 0) {
                for(Map qqTroop : qqtroops){
                    qqTroopid =SafeUtils.getString(qqTroop.get("troopid"));
                    troopids.append(qqTroopid);
                    troopids.append(",");
                }
            }
            
            String imSql="SELECT p.troopuin as troopid FROM innodealing.t_im_troop p LEFT JOIN innodealing.t_im_memberinfo m ON p.id = m.troopuin LEFT JOIN innodealing.t_sysuser u ON m.memberuin = u.uin WHERE   p.`status` = 1 AND m.`status` = 1 AND u.id = " + userid ;
            List<Map<String,Object>> imTroops = jdbcTemplate.queryForList(imSql);
            String imTroopid = ""; 
            if (imTroops != null && imTroops.size() > 0) {
                for(Map imTroop : imTroops){
                    imTroopid = SafeUtils.getString(imTroop.get("troopid"));
                    troopids.append(imTroopid);
                    troopids.append(",");
                }
            }
            if(null != troopids && StringUtils.isNotBlank(troopids.toString())){
                troopString = troopids.substring(0, troopids.length() - 1).toString();
            }
        }
        
        logger.info("findTroopidsByUserid, Userid = "+userid+", troopIds = "+troopString);
        
        return troopString;
    }
	
	/**
	 * findTroopidListByUserid
	 * @param userid
	 * @return
	 */
	public List<String> findTroopidListByUserid(Long userid) {
        List<String> troops = new ArrayList<String>();
        
        if(null != userid){
            
            String qqSql="SELECT troopid FROM innodealing.t_qqsgroup t LEFT JOIN innodealing.t_sysuser s ON t.dmacct=s.spellname WHERE s.id =" + userid;
            List<Map<String,Object>> qqtroops = jdbcTemplate.queryForList(qqSql);
             
            if (qqtroops != null && qqtroops.size() > 0) {
                for(Map qqTroop : qqtroops){
                	String qqTroopid =SafeUtils.getString(qqTroop.get("troopid"));
                    troops.add(qqTroopid);
                }
            }
            
            String imSql="SELECT p.troopuin as troopid FROM innodealing.t_im_troop p LEFT JOIN innodealing.t_im_memberinfo m ON p.id = m.troopuin LEFT JOIN innodealing.t_sysuser u ON m.memberuin = u.uin WHERE   p.`status` = 1 AND m.`status` = 1 AND u.id = " + userid ;
            List<Map<String,Object>> imTroops = jdbcTemplate.queryForList(imSql);
          
            if (imTroops != null && imTroops.size() > 0) {
                for(Map imTroop : imTroops){
                	String imTroopid = SafeUtils.getString(imTroop.get("troopid"));
                	troops.add(imTroopid);
                }
            }
            
        }
        
        logger.info("findTroopidListByUserid, Userid = "+userid+", troops = "+troops);
        
        return troops;
    }
	
	/**
	 * findSysuserAccount
	 * @param dmaccount
	 * @return
	 */
	public SysuserAndOrgainfo findSysuserOrgInfoByAccount(String dmaccount){
		SysuserAndOrgainfo sao = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.id as id,s.name as name,s.spellname as spellname,s.usertype as usertype, s.companyshort as companyshort,s.companyphone as companyphone,s.companytype as companytype, s.qq as qq,s.wechatno as wechatno,s.mobile as mobile,s.instprovince as instprovince,s.instcity as instcity,s.onlinestate as onlinestate,");
	    sql.append("s.imflag as imflag,t.id as orgainfoId,t.type as orgainfoType,t.fullname as orgainFullName,t.shortname as orgainShortName,t.provinceid as orgainProvinceid,t.assetscale as assetScale,t.sectiontype as sectionType ");
		sql.append("FROM innodealing.t_sysuser s LEFT JOIN innodealing.t_orgainfo t ON s.company=t.fullname WHERE s.status=1 ");
		sql.append("AND s.spellname = ? LIMIT 1 ");
		Map<String, Object> result =  null;
		try {
			result = jdbcTemplate.queryForMap(sql.toString(),dmaccount);
			sao = BeanUtil.map2Bean(result, SysuserAndOrgainfo.class);
		} catch (Exception e) {
            logger.info("findSysuserAccount, the return value is null!");
		}
		return sao;
	}
	
    public SysuserInstDto findSysuserInstById(Integer sysuserId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("    s.id AS userId, ");
        sql.append("    s.name AS userName, ");
        sql.append("    s.spellname AS spellName, ");
        sql.append("    s.company, ");
        sql.append("    s.companyshort AS companyShort, ");
        sql.append("    CASE WHEN s.orgainfo_id IS NULL THEN 0 ELSE s.orgainfo_id END AS orgainfoId, ");
        sql.append("    CASE WHEN a.name IS NULL THEN '' ELSE a.name END AS cityName, ");
        sql.append("    CASE WHEN s.inst_grade=0 THEN 1 ELSE 0 END AS instGrade, ");
        sql.append("    CASE WHEN t.type=3 THEN 1 ELSE 0 END AS organType, ");
        sql.append("    CASE WHEN t.assetscale IS NOT NULL THEN t.assetscale ELSE 0 END AS assetScope, ");
        sql.append("    CASE WHEN t.sectiontype IS NOT NULL THEN t.sectiontype ELSE 0 END AS sectionType ");
        sql.append("FROM innodealing.t_sysuser s ");
        sql.append("LEFT JOIN innodealing.t_orgainfo t ON t.id=s.orgainfo_id ");
        sql.append("LEFT JOIN innodealing.t_area a ON s.instcity = a.id ");
        sql.append("WHERE s.status=1 AND s.id=? LIMIT 1");

        return jdbcTemplate.queryForObject(sql.toString(), new Object[] { sysuserId },
                new BeanPropertyRowMapper<SysuserInstDto>(SysuserInstDto.class));
    }

}