package com.innodealing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.model.mongo.dm.bond.rating.RatingIssBondDoc;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.SafeUtils;
/**
 * 评级service
 * @author zhaozhenglai
 * @since 2016年10月28日 上午11:52:53 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Service
public class RatingService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private InduService induService;
    
    public boolean buildRatingIssBondDoc(){
        //主体评级和评级展望
        StringBuffer sqlIss90 = new StringBuffer();
        sqlIss90.append(" SELECT ")
        .append("         a.COM_UNI_CODE AS issId, ")
        .append("         b.com_chi_name AS issName, ")
        .append("         GROUP_CONCAT(a.ISS_CRED_LEVEL ORDER BY a.RATE_WRIT_DATE DESC ) AS rating, ")
        .append("         GROUP_CONCAT(a.ISS_CRED_LEVEL_PAR ORDER BY a.RATE_WRIT_DATE DESC ) AS ratingPar, ")
        .append("         b.indu_uni_code AS induId,b.indu_uni_code_sw AS induIdSw ")
        .append("     FROM ")
        .append("         bond_ccxe.d_bond_iss_cred_chan a ")
        .append("     LEFT JOIN dmdb.t_bond_com_ext b ON b.com_uni_code = a.COM_UNI_CODE ")
        .append("     WHERE a.RATE_WRIT_DATE <  now() AND  a.ISVALID=1  and a.com_type_par=1 ") 
        .append("     GROUP BY a.COM_UNI_CODE");
        StringBuffer sqlIss180 = new StringBuffer();
        sqlIss180.append(" SELECT ")
        .append("         a.COM_UNI_CODE AS issId, ")
        //.append("         orginfo.ORG_CHI_NAME AS orgName, ")
        .append("         b.com_chi_name AS issName, ")
        .append("         GROUP_CONCAT(a.ISS_CRED_LEVEL ORDER BY a.RATE_WRIT_DATE DESC ) AS rating, ")
        .append("         GROUP_CONCAT(a.ISS_CRED_LEVEL_PAR ORDER BY a.RATE_WRIT_DATE DESC ) AS ratingPar, ")
        .append("         b.indu_uni_code AS induId ,b.indu_uni_code_sw AS induIdSw ")
        .append("     FROM ")
        .append("         bond_ccxe.d_bond_iss_cred_chan a ")
        .append("     LEFT JOIN dmdb.t_bond_com_ext b ON b.com_uni_code = a.COM_UNI_CODE ")
        .append("     WHERE a.RATE_WRIT_DATE <  date_add(NOW(), interval -90 day) AND  a.ISVALID=1  and a.com_type_par=1  ") 
        .append("     GROUP BY a.COM_UNI_CODE");
        //近3月
        List<Map<String,Object>> ratingIss90 = jdbcTemplate.queryForList(sqlIss90.toString());
        //前3月
        List<Map<String,Object>> ratingIss180 = jdbcTemplate.queryForList(sqlIss180.toString());
        
        //债项评级和展望
        StringBuffer sqlBond90 = new StringBuffer();
        sqlBond90.append(" SELECT ")
        .append("     c.com_uni_code AS issId, ")
       // .append("     orginfo.ORG_CHI_NAME AS orgName, ")
        .append("     c.com_chi_name AS issName, ")
        .append("     GROUP_CONCAT(a.BOND_CRED_LEVEL ORDER BY a.RATE_WRIT_DATE DESC ) AS rating, ")
        .append("     GROUP_CONCAT(a.BOND_CRED_LEVEL_PAR  ORDER BY a.RATE_WRIT_DATE DESC )AS ratingPar, ")
        .append("     GROUP_CONCAT(a.ATT_POINT ORDER BY a.RATE_WRIT_DATE DESC ) AS attPoint, ")
        .append("     c.indu_uni_code AS induId ,c.indu_uni_code_sw AS induIdSw ")
        .append(" FROM ")
        .append("     bond_ccxe.d_bond_cred_chan a ")
        .append(" LEFT JOIN bond_ccxe.d_bond_basic_info_1 b ON a.BOND_UNI_CODE = b.BOND_UNI_CODE ")
        .append(" LEFT JOIN dmdb.t_bond_com_ext c ON b.ORG_UNI_CODE = c.com_uni_code ")
        //.append(" LEFT JOIN bond_ccxe.d_pub_org_info_r orginfo ON orginfo.ORG_UNI_CODE = a.ORG_UNI_CODE ")
        .append(" WHERE a.RATE_WRIT_DATE <  now() ") 
        .append(" GROUP BY c.com_uni_code ");
        
        StringBuffer sqlBond180 = new StringBuffer();
        sqlBond180.append(" SELECT ")
        .append("     c.com_uni_code AS issId, ")
        //.append("     orginfo.ORG_CHI_NAME AS orgName, ")
        .append("     c.com_chi_name AS issName, ")
        .append("     GROUP_CONCAT(a.BOND_CRED_LEVEL ORDER BY a.RATE_WRIT_DATE DESC ) AS rating, ")
        .append("     GROUP_CONCAT(a.BOND_CRED_LEVEL_PAR  ORDER BY a.RATE_WRIT_DATE DESC )AS ratingPar, ")
        .append("     GROUP_CONCAT(a.ATT_POINT ORDER BY a.RATE_WRIT_DATE DESC ) AS attPoint, ")
        .append("     c.indu_uni_code AS induId ,c.indu_uni_code_sw AS induIdSw ")
        .append(" FROM ")
        .append("     bond_ccxe.d_bond_cred_chan a ")
        .append(" LEFT JOIN bond_ccxe.d_bond_basic_info_1 b ON a.BOND_UNI_CODE = b.BOND_UNI_CODE ")
        .append(" LEFT JOIN dmdb.t_bond_com_ext c ON b.ORG_UNI_CODE = c.com_uni_code ")
        //.append(" LEFT JOIN bond_ccxe.d_pub_org_info_r orginfo ON orginfo.ORG_UNI_CODE = a.ORG_UNI_CODE ")
        .append(" WHERE a.RATE_WRIT_DATE < date_add(NOW(), interval -90 day)") 
        .append(" GROUP BY c.com_uni_code ");
        //近3月
        List<Map<String,Object>> ratingBond90 = jdbcTemplate.queryForList(sqlBond90.toString());
        //前3月
        List<Map<String,Object>> ratingBond180 = jdbcTemplate.queryForList(sqlBond180.toString());
        
        
        String sql = "SELECT com_uni_code FROM dmdb.t_bond_com_ext";
        
        List<Long> issIds = jdbcTemplate.queryForList(sql, Long.class);
        Map<Long,Map<String,Object>> issMap90 = getRatingMap(ratingIss90);
        Map<Long,Map<String,Object>> issMap180 = getRatingMap(ratingIss180);
        Map<Long,Map<String,Object>> bondMap90 = getRatingMap(ratingBond90);
        Map<Long,Map<String,Object>> bondMap180 = getRatingMap(ratingBond180);
      //所有流通的iss
        List<Long> activeIss = induService.getActiveIss();
        List<RatingIssBondDoc> ratingToSave = new ArrayList<>();
        for (Long issId : issIds) {
            Long induId = null;
            Long induIdSw = null;
            String issName = null;
            String attPoint = null;
            List<Integer> bondRating180d = new ArrayList<>();
            List<Integer> bondRating90d = new ArrayList<>();
            List<Integer> bondRatingPar180d = new ArrayList<>();
            List<Integer> bondRatingPar90d = new ArrayList<>();
            List<Integer> issRating180d = new ArrayList<>();
            List<Integer> issRating90d = new ArrayList<>();
            List<Integer> issRatingPar180d = new ArrayList<>();
            List<Integer> issRatingPar90d = new ArrayList<>();
            
            
            //评级
            Map<String,Object> iss90 =  issMap90.get(issId);
            getRating(issRating90d, issRatingPar90d, iss90);
            
            Map<String,Object> iss180 =  issMap180.get(issId);
            getRating(issRating180d, issRatingPar180d, iss180);
            
            Map<String,Object> bond90 =  bondMap90.get(issId);
            getRating(bondRating90d, bondRatingPar90d, bond90);
            
            Map<String,Object> bond180 =  bondMap180.get(issId);
            getRating(bondRating180d, bondRatingPar180d, bond180);
            
            if(bond90 != null){
                Object indu = bond90.get("induId");
                Object induSw = bond90.get("induIdSw");
                Object name = bond90.get("issName");
                Object point = bond90.get("attPoint");
                induId = indu == null ? null :SafeUtils.getLong(indu);
                induIdSw = induSw == null ? null :SafeUtils.getLong(induSw);
                issName = name == null ? null :name.toString();
                attPoint = point != null&& point.toString().split(",").length > 0 ? point.toString().split(",")[0] : null;
            }
            
            
            RatingIssBondDoc ratingdoc = new RatingIssBondDoc();
            ratingdoc.setBondRating180d(bondRating180d);
            ratingdoc.setBondRating90d(bondRating90d);
            ratingdoc.setBondRatingPar180d(bondRatingPar180d);
            ratingdoc.setBondRatingPar90d(bondRatingPar90d);
            ratingdoc.setInduId(induId);
            ratingdoc.setInduIdSw(induIdSw);
            ratingdoc.setIssId(issId);
            ratingdoc.setIssRating180d(issRating180d);
            ratingdoc.setIssRating90d(issRating90d);
            ratingdoc.setIssRatingPar180d(issRatingPar180d);
            ratingdoc.setIssRatingPar90d(issRatingPar90d);
            ratingdoc.setIssName(issName);
            ratingdoc.setAttPoint(attPoint);
            ratingdoc.setCurrStatus(activeIss.contains(issId)? 1: 2);
            ratingToSave.add(ratingdoc);
        }
        mongoTemplate.remove(new Query(), RatingIssBondDoc.class);
        mongoTemplate.insert(ratingToSave, RatingIssBondDoc.class);
        return  true;
    }


    /**
     * 获取具体的评级和展望
     * @param issRating90d
     * @param issRatingPar90d
     * @param iss90
     * @return
     */
    private void getRating( List<Integer> issRating90d, List<Integer> issRatingPar90d,
            Map<String, Object> iss90) {
        if(iss90 != null){
            Object rating = iss90.get("rating");
            if(rating != null){
                String[] ratingArr = rating.toString().split(",");
                if(ratingArr.length > 0){
                    if(ratingArr.length == 1){
                        String rc = ratingArr[0];
                        issRating90d.add(UIAdapter.cvtRating2UIOpt(rc));
                        issRating90d.add(null);
                    }else{
                        String rc = ratingArr[0];
                        String rp = ratingArr[1];
                        issRating90d.add(UIAdapter.cvtRating2UIOpt(rc));
                        issRating90d.add(UIAdapter.cvtRating2UIOpt(rp));
                    }
                }else{
                    issRating90d.add(null);
                    issRating90d.add(null);
                }
            }
            //评级展望
            Object ratingPar = iss90.get("ratingPar");
            if(ratingPar != null){
                String[] ratingParArr = ratingPar.toString().split(",");
                if(ratingParArr.length > 0){
                    if(ratingParArr.length == 1){
                        String rpc = ratingParArr[0];
                        issRatingPar90d.add(rpc == null ? null : Integer.valueOf(rpc));
                        issRatingPar90d.add(null);
                    }else{
                        String rpc = ratingParArr[0];
                        String rpp = ratingParArr[1];
                        issRatingPar90d.add(rpc == null ? null : Integer.valueOf(rpc));
                        issRatingPar90d.add(rpp == null ? null : Integer.valueOf(rpp));
                    }
                }else{
                    issRatingPar90d.add(null);
                    issRatingPar90d.add(null);
                }
            }
        }
    }
    
    
    private Map<Long,Map<String,Object>> getRatingMap(List<Map<String,Object>> ratingList){
        Map<Long,Map<String,Object>>  map = new HashMap<>();
        for (Map<String, Object> m : ratingList) {
            Object issId = m.get("issId");
            if(issId != null){
                map.put(SafeUtils.getLong(issId), m);
            }
        }
        return map;
    }
    
}
