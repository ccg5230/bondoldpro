package com.innodealing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.aop.NoLogging;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.BuildResult;
import com.innodealing.model.IssPd;
import com.innodealing.model.dm.bond.BondCom;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssInduMapSwDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssPdDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssPdQuarter;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssPdWeek;
import com.innodealing.model.mongo.dm.bond.inud.BondInduSwMapDoc;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.SafeUtils;

/**
 * 行业service，用于行业数据转换
 * 
 * @author zhaozhenglai
 * @since 2016年9月12日 下午4:23:18 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
@Service
public class InduService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private IssAnalysisService issAnalysisService;
    
    @Autowired
    private BondComInfoRepository bondComInfoRepository;

    private static Logger log = LoggerFactory.getLogger(InduService.class);
    
    /**
     * 主体映射
     * @return
     */
    @NoLogging
    private Map<Long,BondCom> findBondComMapping(){
        String sql = "SELECT com_uni_code AS issId,ama_com_id AS amsIssId,indu_uni_code AS induId FROM t_bond_com_ext WHERE com_uni_code >0 ";
        List<BondCom> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<BondCom>(BondCom.class));
        Map<Long,BondCom> map = new HashMap<>();
        for (BondCom bondCom : list) {
            map.put(bondCom.getAmsIssId(), bondCom);
        }
        return map;
    }
    
   

    /**
     * 构建行业最近两期信用评级
     * @return
     */
//    public synchronized boolean buildInduCredRating(){
//        StringBuffer sql = new StringBuffer("");
//        sql.append(" SELECT ")
//        .append("   ext.COM_UNI_CODE AS issId, ")
//        .append("   ext.com_chi_name AS issName, ")
//        .append("   ext.indu_uni_code AS induId, ")
//        .append("   orginfo.ORG_CHI_NAME AS orgName, ")
//        .append("   issc.ATT_POINT AS attPoint, ")
//        .append("   GROUP_CONCAT(issc.ISS_CRED_LEVEL ORDER BY issc.RATE_PUBL_DATE DESC) AS rating, ")
//        .append("   GROUP_CONCAT(issc.RATE_PUBL_DATE ORDER BY issc.RATE_PUBL_DATE DESC) AS lastTime, ")
//        .append("   GROUP_CONCAT(issc.RATE_PROS_PAR ORDER BY issc.RATE_PUBL_DATE DESC) AS prosPap ")
//        .append(" FROM ")
//        .append("   bond_ccxe.d_bond_iss_cred_chan issc ")
//        .append(" LEFT JOIN t_bond_com_ext ext ON ext.com_uni_code = issc.COM_UNI_CODE ")
//        .append(" LEFT JOIN bond_ccxe.d_pub_org_info_r orginfo ON orginfo.ORG_UNI_CODE = issc.ORG_UNI_CODE ")
//        .append(" WHERE ext.com_uni_code  > 0 ")
//        .append("   GROUP BY ")
//        .append("   ext.COM_UNI_CODE ");
//        List<IssCredRating> indusCredRating = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<IssCredRating>(IssCredRating.class));
//        //所有流通的iss
//        List<Long> activeIss = getActiveIss();
//        // 行业最近两期评级集合
//        List<InduCredRatingDoc> induCredRatingList = new ArrayList<>();
//        for (IssCredRating induCredRating : indusCredRating) {
//            //行业id
//            Long induId = induCredRating.getInduId();
//            ////发行人|公司id
//            Long issId  = induCredRating.getIssId();
//            //发行人|公司名
//            String issName = induCredRating.getIssName();
//            //本期评级、上期评级
//            int currR = 0 ,lastR = 0;
//            String rating = induCredRating.getRating();
//            //最新更新时间
//            String lastTime = induCredRating.getLastTime().split(",")[0];
//            //展望信息
//            String attPoint = induCredRating.getAttPoint();
//            //最近两期评级
//            String currRating = null ,lastRating =null;
//            if(rating != null && !rating.isEmpty()){
//                String[]  r = rating.split(",");
//                if(r.length == 1){
//                    currR = DmUiDataAdapter.cvtRating2UIOpt(r[0]);
//                    currRating = r[0];
//                }
//                if(r.length > 1){
//                    currR = DmUiDataAdapter.cvtRating2UIOpt(r[0]);
//                    lastR = DmUiDataAdapter.cvtRating2UIOpt(r[1]);
//                    currRating = r[0];
//                    lastRating = r[1];
//                }
//            }
//            //最新评级展望
//            int currP = 0;
//            if(!"".equals(SafeUtils.getString(induCredRating.getProsPap())) ){
//                String[]  p = induCredRating.getProsPap().split(",");
//                currP = SafeUtils.getInt(p[0]);
//            }
//            String orgName = induCredRating.getOrgName();
//            int currStatus = activeIss.contains(issId) ? 2: 1;
//            InduCredRatingDoc icrd = new InduCredRatingDoc(induId, issId, issName, currR, lastR, currP, lastTime, attPoint, orgName, currStatus, currRating, lastRating);
//            induCredRatingList.add(icrd);
//        }
//        
//        // 保存行业最近两期评级集合到mongodb
//        mongoTemplate.remove(new Query(), InduCredRatingDoc.class);
//        mongoTemplate.insert(induCredRatingList, InduCredRatingDoc.class);
//        return true;
//    }
    
    /**
     * 获取有流通债券的主体id集合
     * @return
     */
    public  List<Long> getActiveIss(){
        String sql = " SELECT DISTINCT ORG_UNI_CODE FROM ( "+
                " SELECT DISTINCT ORG_UNI_CODE FROM bond_ccxe.d_bond_basic_info_1 WHERE CURR_STATUS = 1 "+
                " UNION "+
                " SELECT DISTINCT ORG_UNI_CODE FROM bond_ccxe.d_bond_basic_info_2 WHERE CURR_STATUS = 1 "+
                " UNION "+
                " SELECT DISTINCT ORG_UNI_CODE FROM bond_ccxe.d_bond_basic_info_3 WHERE CURR_STATUS = 1 "+
                " UNION "+
                " SELECT DISTINCT ORG_UNI_CODE FROM bond_ccxe.d_bond_basic_info_4 WHERE CURR_STATUS = 1 "+
                " UNION "+
                " SELECT DISTINCT ORG_UNI_CODE FROM bond_ccxe.d_bond_basic_info_5 WHERE CURR_STATUS = 1) te ";
        return jdbcTemplate.queryForList(sql, Long.class);
    }
    
    /**
     * 构建发行人信誉评级和评级展望三周内变化历史
     * @return
     */
//    @Deprecated
//    public synchronized boolean buildInduRatingAndPop(){
//        List<InduRaringPopThreeWeekHisDoc> irptwhList = new ArrayList<>();
//        List<BondCredRating>  credRatings = findInduRatingAndPop();
//        for (BondCredRating icr : credRatings) {
//            if("".equals(SafeUtils.getString(icr.getPublDate()))){
//                continue;
//            }
//            //各个周评级、评级展望数据
//            int[] ratings = buildRating(icr);
//            InduRaringPopThreeWeekHisDoc rphis = new InduRaringPopThreeWeekHisDoc(icr.getInduId(), icr.getIssId(), icr.getIssName(),
//                    ratings[0], ratings[1], ratings[2], ratings[3], ratings[4], ratings[5]);
//            irptwhList.add(rphis);
//        }
//        mongoTemplate.remove(new Query(), InduRaringPopThreeWeekHisDoc.class);
//        mongoTemplate.insert(irptwhList, InduRaringPopThreeWeekHisDoc.class);
//        return true;
//    }
    
    
    
    /**
     * 构建债券信誉评级和评级展望三周的变化
     * @return
     */
//    @Deprecated
//    public synchronized boolean buildBondRatingAndPop(){
//        List<BondRaringPopThreeWeekHisDoc> irptwhList = new ArrayList<>();
//        List<BondCredRating>  credRatings = findBondRatingAndPor();
//        for (BondCredRating icr : credRatings) {
//            if("".equals(SafeUtils.getString(icr.getPublDate()))){
//                continue;
//            }
//            int[] ratings = buildRating(icr);
//            BondRaringPopThreeWeekHisDoc rphis = new BondRaringPopThreeWeekHisDoc(icr.getInduId(), icr.getIssId(),icr.getBondUniCode(), icr.getIssName(),
//                    ratings[0], ratings[1], ratings[2], ratings[3], ratings[4], ratings[5]);
//            irptwhList.add(rphis);
//        }
//        mongoTemplate.remove(new Query(), BondRaringPopThreeWeekHisDoc.class);
//        mongoTemplate.insert(irptwhList, BondRaringPopThreeWeekHisDoc.class);
//        return true;
//    }
    
   
    
    
    
    
//    private int[] buildRating(BondCredRating icr){
//      //发布时间
//        String publDate = icr.getPublDate();
//        String[] publDateArr = null;
//        //评级展望
//        String prosPap = icr.getProsPap();
//        String[] ratingPapArr = {"0","0","0"};
//        //评级
//        String rating = icr.getRating();
//        String[] ratingArr = {"0","0","0"};
//        String d = publDate.substring(publDate.lastIndexOf(",")+1, publDate.length());
//        publDate += ","+d+","+d+","+d;
//        publDateArr = publDate.split(",");
//        if(prosPap != null){
//            String pp = prosPap.substring(prosPap.lastIndexOf(",")+1, prosPap.length());
//            prosPap += ","+pp+","+pp+","+pp;
//            ratingPapArr = prosPap.split(",");
//        }
//        if(rating != null){
//            String r = rating.substring(rating.lastIndexOf(",")+1, rating.length());
//            rating += ","+r+","+r+","+r;
//            ratingArr = rating.split(",");
//        }
//        
//        Map<String,String> map = new HashMap<>();
//        for (int i = 0; i < 3 ; i++) {
//            map.put(publDateArr[i], ratingArr[i] + ":" + ratingPapArr[i]);
//        }
//        //最近三周，各周最新评级 tWeek、lWeek 、 blWeek 
//        Map<String,String> induDate = dateString(publDateArr);
//        //本周评级、上周评级、上上周评级
//        int thisWeekR = 0, lastWeekR = 0, beforeLastWeekR = 0;
//        //本周评级展望、上周评级展望、上上周评级展望
//        int thisWeekP = 0, lastWeekP = 0, beforeLastWeekP = 0;
//        String tWeek = map.get(induDate.get("tWeek"));
//        String lWeek = map.get(induDate.get("lWeek"));
//        String blWeek = map.get(induDate.get("blWeek"));
//        if(tWeek != null){
//            String[] rp = tWeek.split(":");
//            thisWeekR =  UIAdapter.cvtRating2UIOpt(rp[0]);
//            thisWeekP = SafeUtils.getInt(rp[1]);
//        }
//        if(lWeek != null){
//            String[] rp = lWeek.split(":");
//            lastWeekR =  UIAdapter.cvtRating2UIOpt(rp[0]);
//            thisWeekP = SafeUtils.getInt(rp[1]);
//        }
//        if(blWeek != null){
//            String[] rp = blWeek.split(":");
//            beforeLastWeekR =  UIAdapter.cvtRating2UIOpt(rp[0]);
//            beforeLastWeekP = SafeUtils.getInt(rp[1]);
//        } 
//        int[] ratings = {thisWeekR, lastWeekR, beforeLastWeekR, thisWeekP, lastWeekP, beforeLastWeekP};
//        return ratings;
//    }
    
    
    /**
     * 获取发行人的评级和评级展望
     * @return
     */
//    private List<BondCredRating> findInduRatingAndPop(){
//        //最近三周的开始和结束日期
//        String start = SafeUtils.getCalendar(21);
//        String end  = SafeUtils.getCalendar(1);
//        StringBuffer sql = new StringBuffer("");
//        sql.append(" SELECT ")
//        .append("     brc.*, ")
//        .append("     ext.com_chi_name AS issName, ")
//        .append("     ext.indu_uni_code AS induId ")
//        .append(" FROM ")
//        .append("     ( ")
//        .append("         SELECT ")
//        .append("             COM_UNI_CODE AS issId, ")
//        .append("             GROUP_CONCAT( ")
//        .append("                 RATE_PROS_PAR ")
//        .append("                 ORDER BY ")
//        .append("                     RATE_PUBL_DATE DESC ")
//        .append("             ) AS prosPap, ")
//        .append("             GROUP_CONCAT( ")
//        .append("                 ISS_CRED_LEVEL ")
//        .append("                 ORDER BY ")
//        .append("                     RATE_PUBL_DATE DESC ")
//        .append("             ) AS rating, ")
//        .append("             GROUP_CONCAT( ")
//        .append("                 RATE_PUBL_DATE ")
//        .append("                 ORDER BY ")
//        .append("                     RATE_PUBL_DATE DESC ")
//        .append("             ) AS publDate ")
//        .append("         FROM ")
//        .append("             bond_ccxe.d_bond_iss_cred_chan ")
//        .append("         WHERE RATE_PUBL_DATE BETWEEN '"+ start +"' AND '"+ end +"' ")
//        .append("         GROUP BY ")
//        .append("             COM_UNI_CODE ")
//        .append("     ) brc ")
//        .append(" LEFT JOIN t_bond_com_ext AS ext ON ext.com_uni_code = brc.issId ");
//        
//        List<BondCredRating> credRatings = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<BondCredRating>(BondCredRating.class));
//        
//        return  credRatings;
//    }
    
    /**
     * 获取债券的评级和评级展望
     * @return
     */
//    private List<BondCredRating> findBondRatingAndPor(){
//        //最近三周的开始和结束日期
//        String start = SafeUtils.getCalendar(51);
//        String end  = SafeUtils.getCalendar(1);
//        StringBuffer sql = new StringBuffer("");
//        sql.append(" SELECT ")
//        .append("         * ")
//        .append("     FROM ")
//        .append("         ( ")
//        .append("             SELECT ")
//        .append("                 GROUP_CONCAT( ")
//        .append("                     bcc.BOND_CRED_LEVEL ")
//        .append("                     ORDER BY ")
//        .append("                         bcc.RATE_PUBL_DATE DESC ")
//        .append("                 ) AS rating, ")
//        .append("                 GROUP_CONCAT( ")
//        .append("                     bcc.RATE_PROS_PAR ")
//        .append("                     ORDER BY ")
//        .append("                         bcc.RATE_PUBL_DATE DESC ")
//        .append("                 ) AS prosPar, ")
//        .append("                 GROUP_CONCAT( ")
//        .append("                     bcc.RATE_PUBL_DATE ")
//        .append("                     ORDER BY ")
//        .append("                         bcc.RATE_PUBL_DATE DESC ")
//        .append("                 ) AS publDate, ")
//        .append("                 bcc.BOND_UNI_CODE AS bondUniCode, ")
//        .append("                 bce.com_uni_code AS issId, ")
//        .append("                 bce.indu_uni_code AS induId ")
//        .append("             FROM ")
//        .append("                 bond_ccxe.d_bond_cred_chan bcc ")
//        .append("             LEFT JOIN ( ")
//        .append("                 SELECT ")
//        .append("                     BOND_UNI_CODE, ")
//        .append("                     ORG_UNI_CODE ")
//        .append("                 FROM ")
//        .append("                     bond_ccxe.d_bond_basic_info_1 ")
//        .append("                 WHERE ")
//        .append("                     ORG_UNI_CODE > 0 ")
//        .append("             ) bbi ON bbi.BOND_UNI_CODE = bcc.BOND_UNI_CODE ")
//        .append("             LEFT JOIN t_bond_com_ext bce ON bce.com_uni_code = bbi.ORG_UNI_CODE ")
//        .append("             WHERE ")
//        .append("                 bcc.RATE_PUBL_DATE BETWEEN '" + start + "' ")
//        .append("             AND '" + end + "' ")
//        .append("             GROUP BY ")
//        .append("                 bce.com_uni_code ")
//        .append("         ) AS tem ")
//        .append("     WHERE ")
//        .append("         induId > 0 ");
//        
//        List<BondCredRating> credRatings = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<BondCredRating>(BondCredRating.class));
//        return credRatings;
//    }
    
    
    
    /**
     * 最近三周各周最新的数据date
     * @param dates
     * @return
     */
//    private static Map<String,String> dateString(String[] dates){
//        Long thisWeek = SafeUtils.getLong(SafeUtils.getCalendar(7).substring(0, 10).replace("-", ""));
//        Long lastWeek = SafeUtils.getLong(SafeUtils.getCalendar(14).substring(0, 10).replace("-", ""));
//        Long beforeLastWeek = SafeUtils.getLong(SafeUtils.getCalendar(21).substring(0, 10).replace("-", ""));
//        String tWeek = "";
//        String lWeek = "";
//        String blWeek = "";
//        Map<String,String> result = new HashMap<>();
//        for (String publDate : dates) { 
//            Long date = SafeUtils.getLong(publDate.substring(0, 10).replace("-", ""));
//            if(tWeek.equals("") && date > thisWeek){
//                tWeek = publDate;
//            }
//            
//            if(lWeek.equals("") && date > lastWeek && date < thisWeek){
//                lWeek = publDate;
//            }
//            
//            if(blWeek.equals("") && date > beforeLastWeek && date < lastWeek ){
//                blWeek = publDate;
//            }
//        }
////        if(lWeek.equals("")){
////            lWeek = tWeek;
////        }
////        if(blWeek.equals("")){
////            blWeek = lWeek;
////        }
//        result.put("tWeek", tWeek);
//        result.put("lWeek", lWeek);
//        result.put("blWeek", blWeek);
//        return result;
//        
//    }
    
    public boolean buildPdRating(){
        boolean result = true;
        try {
            StringBuffer sql = new StringBuffer("");
            sql.append(" SELECT ")
            .append("         GROUP_CONCAT(dmrating.rating ORDER BY dmrating.publDate DESC) AS ratingQ, ")
            .append("         GROUP_CONCAT(dmrating.rating ORDER BY dmrating.createTime DESC) AS ratingW, ")
            .append("         GROUP_CONCAT(dmrating.createTime ORDER BY dmrating.createTime) AS createTime, ")
            .append("         dmrating.amaIssId, ")
            .append("         GROUP_CONCAT(dmrating.publDate ORDER BY dmrating.publDate DESC) AS publDate ")
            .append("     FROM ")
            .append("         ( ")
            .append("             SELECT ")
            .append("                 CONCAT(`year`,'-',CASE WHEN quan_month < 10 THEN CONCAT('0', quan_month) WHEN quan_month > 10 THEN quan_month END,'-01') AS publDate, ")
            .append("                 Rating AS rating, ")
            .append("                 create_time AS createTime, ")
            .append("                 Comp_ID AS amaIssId ")
            .append("             FROM ")
            .append("                 /*amaresun*/ dmdb.dm_bond WHERE `year` > 2014 ")
            .append("         ) dmrating ")
            .append("     GROUP BY dmrating.amaIssId ");
            List<IssPd> issPds = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<IssPd>(IssPd.class));
            List<IssPdDoc> issPdList = new ArrayList<>();
            Map<Long, BondCom> mapping = issAnalysisService.findBondComMapping();
            for (IssPd issPd : issPds) {
                
                BondCom bc = mapping.get(issPd.getAmaIssId());
                Long induId = bc == null ? null : bc.getInduId();
                Long induIdSw = bc == null ? null : bc.getInduIdSw();
                Long issId = bc == null ? null : bc.getIssId();
                if(issId == null){
                	continue;
                }
                String issName = bc == null ? null : bc.getIssName();
                //季度违约概率
                String ratingQ = issPd.getRatingQ();
                if("".equals(SafeUtils.getString(ratingQ))){
                    log.error("原始季度违约概率信息数据有误！！！{amaIssId:" + issPd.getAmaIssId() + "}" );
                    continue;
                }
                String[] ratingArr = ratingQ.split(",");
                //最近6期违约数据
                List<Double> pds = new ArrayList<>();
                for (String vl : ratingArr) {
                    boolean isNull = SafeUtils.getDouble((UIAdapter.convAmaRating2Pd(vl))) == 0;
                    pds.add(isNull ? null : SafeUtils.getDouble((UIAdapter.convAmaRating2Pd(vl))));
                    if(pds.size() == 6){
                        break;
                    }
                }
                while (pds.size() < 6 ) {
                    pds.add( null);
                }
                  
                String publDate = issPd.getPublDate();
                if("".equals(SafeUtils.getString(publDate))){
                    throw new BusinessException("原始季度违约概率信息时间数据有误！！！{amaIssId:" + issPd.getAmaIssId() + "}");
                }
                String[] publDateArr = publDate.split(",");
                //最近两季度时间
                String[] nearTwoQ =  SafeUtils.getQuarter(publDateArr[0], 2);
                String onePdQ = nearTwoQ[0];
                String twoPdQ = nearTwoQ[1];
                double onePd1 = UIAdapter.convAmaRating2Pd(ratingArr[0]).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                double twoPd1 = new BigDecimal( -1 ).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();//初始值-1表示没有违约概率
                if(publDateArr.length > 1 && ratingArr.length > 1){//如果上季度有数据
                    twoPd1 = UIAdapter.convAmaRating2Pd(ratingArr[1]).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                IssPdQuarter pdQ = new IssPdQuarter(onePdQ, twoPdQ, onePd1, twoPd1);
              
                long num = mongoTemplate.count(new Query(Criteria.where("issuerId").is(issId).and("currStatus").is(1)), BondBasicInfoDoc.class);
                
                int currStatus = num == 0L ? 2: 1;
                //添加到临时List
                IssPdDoc iss=  new IssPdDoc(/*issId, induId, pdQ, new IssPdWeek(), issName, currStatus*/);
                BondComInfoDoc  bondComInfo = bondComInfoRepository.findOne(issId);
                iss.setCurrStatus(currStatus);
                iss.setInduId(induId);
                iss.setInduIdSw(induIdSw);
                iss.setIssId(issId);
                iss.setIssName(issName);
                iss.setPdQ(pdQ);
                iss.setPdW(new IssPdWeek());
                String[] q = SafeUtils.getQuarter(publDateArr[0], 6);
                //iss.setQuarters(Arrays.asList(q));
                iss.setPds(pds);
                if(bondComInfo != null){
                	iss.setInstitutionInduMap(bondComInfo.getInstitutionInduMap());
                }
                issPdList.add(iss);
            }
            mongoTemplate.remove(new Query(), IssPdDoc.class);
            mongoTemplate.insert(issPdList, IssPdDoc.class);
        } catch (DataAccessException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    } 
    
    
    
    
    /**
     * 构建GICS分类和申万分类映射关系
     */
    public boolean buildInduMapSw(){
    	String sql = "SELECT com_uni_code AS issuerId,indu_uni_code AS induId,indu_uni_code_sw AS induIdSw FROM t_bond_com_ext WHERE com_uni_code >0";
    	boolean result = true;
    	try {
			List<BondInduSwMapDoc> induSwMapList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<BondInduSwMapDoc>(BondInduSwMapDoc.class));
			Map<Long,IssInduMapSwDoc> issInduMapSwDocMap = getIssInduMapSwDoc(induSwMapList);
			issInduMapSwDocMap.forEach((k,v) -> mongoTemplate.save(v));
		} catch (DataAccessException e) {
			e.printStackTrace();
			result = false;
		}
    	return result;
    	
    }
    
    /**
     * 获取IssInduMapSwDoc
     * @param induSwMapList
     * @return
     */
    private Map<Long,IssInduMapSwDoc> getIssInduMapSwDoc(List<BondInduSwMapDoc> induSwMapList){
    	Map<Long,IssInduMapSwDoc> issInduMapSwDocMap = new HashMap<>();
    	for (BondInduSwMapDoc bondInduSwMapDoc : induSwMapList) {
			if(issInduMapSwDocMap.get(bondInduSwMapDoc.getInduIdSw()) == null){
				Set<Long> issIds = new HashSet<>();
				issIds.add(bondInduSwMapDoc.getIssuerId());
				IssInduMapSwDoc map  = new IssInduMapSwDoc(bondInduSwMapDoc.getInduIdSw(), issIds);
				issInduMapSwDocMap.put(bondInduSwMapDoc.getInduIdSw(), map);
			}else{
				issInduMapSwDocMap.get(bondInduSwMapDoc.getInduIdSw()).getIssIds().add(bondInduSwMapDoc.getIssuerId());
			}
		}
    	return issInduMapSwDocMap;
    }
    
    /**
     * 构建数据总入口
     * @return
     */
    public List<BuildResult> build(){
        
        List<BuildResult> result = new ArrayList<>();
        
       
        
        
        long start1 = System.currentTimeMillis();
        boolean result1 = buildPdRating();
        long end1 = System.currentTimeMillis();
        String name1 ="构建发行人违约概率";
        result.add(new BuildResult(name1, result1, (end1 - start1) +"ms") );
        
        
        
        long start2 = System.currentTimeMillis();
        boolean result2 = buildInduMapSw();
        long end2 = System.currentTimeMillis();
        String name2 ="构建GICS分类和申万分类映射关系";
        result.add(new BuildResult(name2, result2, (end2 - start2) +"ms") );
        
        
        
//        long start3 = System.currentTimeMillis();
//        boolean result3 = buildInduRatingAndPop();
//        long end3 = System.currentTimeMillis();
//        String name3 ="构建发行人信誉评级和评级展望三周内变化历史数据";
//        result.add(new BuildResult(name3, result3, (end3 - start3) +"ms") );
        
        
        
//        long start4 = System.currentTimeMillis();
//        boolean result4 = buildBondRatingAndPop();
//        long end4 = System.currentTimeMillis();
//        String name4 ="构建债券信誉评级和评级展望三周的变化";
//        result.add(new BuildResult(name4, result4, (end4 - start4) +"ms") );
        
        
        
        
// TODO 删除       
//        long start5 = System.currentTimeMillis();
//        boolean result5 = buildBondPdRating();
//        long end5 = System.currentTimeMillis();
//        String name5 ="构建债券信誉评级和评级展望三周的变化";
//        result.add(new BuildResult(name5, result5, (end5 - start5) +"ms") );
        
        
//        long start6 = System.currentTimeMillis();
//        boolean result6 = buildBondRatingAndPop();
//        long end6= System.currentTimeMillis();
//        String name6 ="构建债券主体违约数据信息";
//        result.add(new BuildResult(name6, result6, (end6 - start6) +"ms") );
          return result;
    }
    
    
//    public static void main(String[] args) {
//        String u = null;
//        String c = SafeUtils.getCalendar(1);
//        String c2 = SafeUtils.getCalendar(20);
//        System.out.println(c+ "-----"+c2);
//        Long thisWeek = SafeUtils.getLong(SafeUtils.getCalendar(7).substring(0, 10).replace("-", ""));
//        Long lastWeek = SafeUtils.getLong(SafeUtils.getCalendar(14).substring(0, 10).replace("-", ""));
//        Long beforeLastWeek = SafeUtils.getLong(SafeUtils.getCalendar(21).substring(0, 10).replace("-", ""));
//        System.out.println(thisWeek +"----"+lastWeek +"----"+beforeLastWeek);
//        String[] dates = {"2016-09-14 00:00:00",
//                "2016-09-13 00:00:00"
//                ,"2016-09-13 00:00:00"
//                ,"2016-09-08 00:00:00"
//                ,"2016-09-04 00:00:00"
//                ,"2016-09-02 00:00:00"
//                ,"2016-09-02 00:00:00"
//                ,"2016-09-01 00:00:00"
//                ,"2016-08-27 00:00:00"
//                ,"2016-08-26 00:00:00"};
//        System.out.println(dateString(dates));
//        
//        String i = "erer,erer,5";
//        System.out.println(i.substring(i.lastIndexOf(",")+1,i.length()));
//    }
    
    
    
   
}




