package com.innodealing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bond.BondBasicInfoClassOneDao;
import com.innodealing.model.mongo.dm.BondBasicInfoClassOneDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.uilogic.UIAdapter;

@Service
public class BondBasicInfoClassOneService {

    private static final Logger log = LoggerFactory.getLogger(BondBasicInfoClassOneService.class);
    
//    private static final Double MIN_DOUBLE = new Double(-999999999);

    private @Autowired MongoTemplate mongoTemplate;

    private @Autowired BondBasicInfoClassOneDao bondBasicInfoClassOneDao;

    private @Autowired BondComDataService bondComDataService;

    @SuppressWarnings("serial") // dm评分排序map
    private static final Map<String, Integer> dmScoreSortMap = new HashMap<String, Integer>() {
        {
            put("AAA", 21);
            put("AA+", 20);
            put("AA", 19);
            put("AA-", 18);
            put("A+", 17);
            put("A", 16);
            put("A-", 15);
            put("BBB+", 14);
            put("BBB", 13);
            put("BBB-", 12);
            put("BB+", 11);
            put("BB", 10);
            put("BB-", 9);
            put("B+", 8);
            put("B", 7);
            put("B-", 6);
            put("CCC+", 5);
            put("CCC", 4);
            put("CCC-", 3);
            put("C", 2);
            put("D", 1);
        }
    };

    @SuppressWarnings("serial") // 主体评级排序map
    private static final Map<String, Integer> issSortMap = new HashMap<String, Integer>() {
        {
            put("AAA", 5);
            put("AA+", 4);
            put("AA", 3);
            put("AA-", 2);
            put("A+", 1);
        }
    };

    @SuppressWarnings("serial") // 债项评级排序map
    private static final Map<String, Integer> bondSortMap = new HashMap<String, Integer>() {
        {
            put("AAA", 7);
            put("AA+", 6);
            put("AA", 5);
            put("AA-", 4);
            put("A+", 3);
            put("A-", 2);
            put("A-1", 1);
        }
    };

    @SuppressWarnings("serial") // 兴业评分排序map
    private static final Map<String, Integer> industrialSortMap = new HashMap<String, Integer>() {
        {
            put("1-", 15);
            put("1", 14);
            put("1+", 13);
            put("2-", 12);
            put("2", 11);
            put("2+", 10);
            put("3-", 9);
            put("3", 8);
            put("3+", 7);
            put("4-", 6);
            put("4", 5);
            put("4+", 4);
            put("5-", 3);
            put("5", 2);
            put("5+", 1);
        }
    };

    @SuppressWarnings("serial") // yy评分排序map
    private static final Map<String, Integer> yySortMap = new HashMap<String, Integer>() {
        {
            put("1", 10);
            put("2", 9);
            put("3", 8);
            put("4", 7);
            put("5", 6);
            put("6", 5);
            put("7", 4);
            put("8", 3);
            put("9", 2);
            put("10", 1);
        }
    };

    /**
     * 将一级债券插入mongodb
     * 
     * @return
     */
    public boolean bulid() {
        // 所有一级债券
        List<BondBasicInfoClassOneDoc> list = bondBasicInfoClassOneDao.findAll();
        int total = 0;
        mongoTemplate.remove(new Query(), BondBasicInfoClassOneDoc.class);// 清空原有数据
        if (null != list && !list.isEmpty()) {
            for (BondBasicInfoClassOneDoc doc : list) {
                try {
                    setProperies(doc);
                    total++;
                } catch (Exception e) {
                    log.error("bulid bondBasicInfoClassOne error :" + e.getMessage(), e);
                    log.info(doc.toString());
                    continue;
                }
            }
            mongoTemplate.insertAll(list);
        }

        log.info("本次共新增" + total + "只一级债券到dm-bond.bond_basic_info_class_one[mongodb]");
        return true;
    }

    public Boolean deleteMarketedNewBond(Long[] bondUniCodes) {
        Criteria cq = Criteria.where("_id").in(bondUniCodes);
        Query query = Query.query(cq);
        int result = 0;
        try {
            result = mongoTemplate.remove(query, BondBasicInfoClassOneDoc.class).getN();
        } catch (Exception e) {
            log.info("删除bond_basic_info_class_one 数据  _id =" + bondUniCodes + "失败！");
            result = 0;
        }

        return result > 0;
    }

    public Boolean addAndUpdateNewBond(long bondUniCode) {
        Boolean result = true;
        try {
            Criteria cq = Criteria.where("_id").is(bondUniCode);
            Query query = Query.query(cq);
            mongoTemplate.remove(query, BondBasicInfoClassOneDoc.class).getN();
            BondBasicInfoClassOneDoc doc = bondBasicInfoClassOneDao.findByBondUniCode(bondUniCode);
            setProperies(doc);
            mongoTemplate.insert(doc);
            long num = mongoTemplate.count(query, BondBasicInfoClassOneDoc.class);
            if (num <= 0) {
                result = false;
            }

        } catch (Exception e) {
            log.info("新增bond_basic_info_class_one 数据  _id =" + bondUniCode + "失败！");
            result = false;
        }

        return result;
    }

    private void setProperies(BondBasicInfoClassOneDoc doc) {
        BondComInfoDoc comInfoDoc = bondComDataService.get(doc.getComUniCode());
        if (comInfoDoc == null) {
            log.warn("公司信息不存在: " + doc.getComUniCode());
        } else {
            if (comInfoDoc.getPdNum() != null) {
                doc.setRiskWarning(comInfoDoc.getPdNum() >= BondBasicInfoClassOneDoc.RISK_WARNING_PD_THRESHOLD);
            }
            doc.setPdDiff(comInfoDoc.getPdDiff());
        }
        setMatuUnitParDays(doc);
        if (!StringUtils.isEmpty(doc.getDmRatingScore())) {
            String pd = UIAdapter.convAmaRating2UIText(doc.getDmRatingScore());// 违约概率
            doc.setDmRatingScorePD(pd);
        }
        if (null != doc.getDidIntervalLow()) {
            doc.setDidIntervalLow(new BigDecimal(doc.getDidIntervalLow()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (null != doc.getDidIntervalSup()) {
            doc.setDidIntervalSup(new BigDecimal(doc.getDidIntervalSup()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (null != doc.getSubscriptionIntervalLower()) {
            doc.setSubscriptionIntervalLower(
                    new BigDecimal(doc.getSubscriptionIntervalLower()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (null != doc.getSubscriptionIntervalSuper()) {
            doc.setSubscriptionIntervalSuper(
                    new BigDecimal(doc.getSubscriptionIntervalSuper()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (null != doc.getActuFirIssAmut()) {
            doc.setActuFirIssAmut(new BigDecimal(doc.getActuFirIssAmut()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (null != doc.getIssCoupRate()) {
            doc.setIssCoupRate(new BigDecimal(doc.getIssCoupRate()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (null != doc.getReferenceReturnsRatio()) {
            doc.setReferenceReturnsRatio(new BigDecimal(doc.getReferenceReturnsRatio()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        
        if(null != doc.getOrgPriceLower()) {
            doc.setOrgPriceLower(new BigDecimal(doc.getOrgPriceLower()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());

        }
        if(null != doc.getOrgPriceSuper()) {
            doc.setOrgPriceSuper(new BigDecimal(doc.getOrgPriceSuper()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        if (!StringUtils.isEmpty(doc.getDmRatingScore()) || !StringUtils.isEmpty(doc.getDmRatingScorePD())) {//转化DM评分排序值
            String dmRatingScore = doc.getDmRatingScore().replaceAll(" ", "");
            doc.setDmRatingScoreSort(dmScoreSortMap.get(dmRatingScore));
        } 
        
        if(!StringUtils.isEmpty(doc.getIssRating())) {//主体评级排序值
            String issRating = doc.getIssRating().replaceAll(" ", "");
            doc.setIssRatingSort(issSortMap.get(issRating));
        } 
        
        if(!StringUtils.isEmpty(doc.getIndustrialSubjectScore())) {//兴业主体评分排序值
            doc.setIndustSubjectScoreSort(industrialSortMap.get(doc.getIndustrialSubjectScore()));
        } 
        
        if (!StringUtils.isEmpty(doc.getOrgRating())) {//YY评分排序值
            doc.setYyRationgSort(yySortMap.get(doc.getOrgRating()));
        }
        /** 发行规模排序值处理:
         * 发行规模排序值（乘以单位）：计划发行规模和募集金额/发行总额，若募集金额/发行总额不为空则展示募集金额/发行总额，若募集金额/发行总额为空，则展示计划发行规模")
         * 
         * */
        if(null != doc.getActuFirIssAmut()) {
            BigDecimal issScaleSort = new BigDecimal(doc.getActuFirIssAmut());
            if(Constants.BOND_CCXE_YUAN_UNIT.equals(doc.getActuFirIssAmutUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(1));
            } else if(Constants.BOND_CCXE_THOUSAND_UNIT.equals(doc.getActuFirIssAmutUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(1000));
            } else if(Constants.BOND_CCXE_WAN_UNIT.equals(doc.getActuFirIssAmutUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(10* 1000));
            } else if(Constants.BOND_CCXE_MILLION_UNIT.equals(doc.getActuFirIssAmutUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(1000 * 1000));
            } else if(Constants.BOND_CCXE_YI_UNIT.equals(doc.getActuFirIssAmutUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(100 * 1000 * 1000));
            }
            doc.setIssScaleSort(issScaleSort.doubleValue());
            
        } else if(null != doc.getPlanIssScale()){
            BigDecimal issScaleSort = new BigDecimal(doc.getPlanIssScale());
            if(Constants.BOND_CCXE_YUAN_UNIT.equals(doc.getPlanIssScaleUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(1));
            } else if(Constants.BOND_CCXE_THOUSAND_UNIT.equals(doc.getPlanIssScaleUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(1000));
            } else if(Constants.BOND_CCXE_WAN_UNIT.equals(doc.getPlanIssScaleUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(10* 1000));
            } else if(Constants.BOND_CCXE_MILLION_UNIT.equals(doc.getPlanIssScaleUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(1000 * 1000));
            } else if(Constants.BOND_CCXE_YI_UNIT.equals(doc.getPlanIssScaleUnit())) {
                issScaleSort = issScaleSort.multiply(new BigDecimal(100 * 1000 * 1000));
            }
            doc.setIssScaleSort(issScaleSort.doubleValue());
        }

        /**利率排序值处理：
         * 票面利率若不为空，则展示票面利率字段，若票面利率为空，展示利率区间（若为簿记建档，展示申购区间，若为招标发行，展示招标区间） "发行方式  //0-招标  1-薄记建档 ") 
       */
        if(null != doc.getIssCoupRate()) {
            doc.setRateSort(doc.getIssCoupRate());
        }  else {
            //发行方式 :0-招标  1-薄记建档
            if("1".equals(doc.getIssCls())) {
                doc.setRateSort(doc.getSubscriptionIntervalLower());
            } else {//经杨纨与产品确认：后台未选择发行方式，按招标处理
                doc.setRateSort(doc.getDidIntervalLow());
            }
        }
       
        /** 截标日 查询和排序处理*/
        if("1".equals(doc.getIssCls())) {//1-薄记建档
            doc.setFrontBidStartDate(doc.getBookStartDate());
            doc.setFrontBidEndDate(doc.getBookEndDate());
        } else {//默认按招标
            doc.setFrontBidStartDate(doc.getStopBidStartDate());
            doc.setFrontBidEndDate(doc.getStopBidEndDate());
        }
        
    }

    /**
     * 获取期限(单位为天)
     * 
     * @param doc
     *            <p>
     *            期限与筛选条件的对应关系： 若后台返回的是2Y+5Y或2+5这种类型的数字，则计算两个数字的和，用7Y来筛选这个
     *            若后台返回的是3Y+N或3+N这种类型的数字，则用3Y来筛选
     *            若后台返回的是18D这种类型的数字，就计算18/30，然后用相应月份来筛选，这里就是＜3M
     *            若后台返回的是3M这种类型的数字，就直接筛选 若后台返回的是3Y这种类型的数字，就直接筛选
     *            若后台直接返回数字不带单位，按照年来处理 若后台返回的类型是除上面外的其他种类，则只能在全部中查看，筛选不出。
     *            </p>
     * @return
     */
    public void setMatuUnitParDays(BondBasicInfoClassOneDoc doc) {
        try {
            String bondMatu = doc.getBondMatu();
            if (StringUtils.isEmpty(bondMatu)) {
                doc.setMatuUnitParDays(null);
                return;
            }
            bondMatu = bondMatu.trim();
            if (bondMatu.contains("+") || bondMatu.contains("＋")) {
                if (bondMatu.indexOf("+") != -1) {
                    String[] nP = bondMatu.split("\\+");
                    BondBasicInfoClassOneDoc docF = getDaysForString(nP[0].trim());
                    BondBasicInfoClassOneDoc docE = getDaysForString(nP[1].trim());
                    doc.setMatuUnitParDays(docF.getMatuUnitParDays() + docE.getMatuUnitParDays());
                } else if (bondMatu.indexOf("＋") != -1) {
                    String[] nP = bondMatu.split("\\＋");
                    BondBasicInfoClassOneDoc docF = getDaysForString(nP[0].trim());
                    BondBasicInfoClassOneDoc docE = getDaysForString(nP[1].trim());
                    doc.setMatuUnitParDays(docF.getMatuUnitParDays() + docE.getMatuUnitParDays());
                }
            } else {
                BondBasicInfoClassOneDoc temp = getDaysForString(bondMatu);
                doc.setMatuUnitParDays(temp.getMatuUnitParDays());
            }

        } catch (Exception e) {
            log.error("setMatuUnitParDays error: bondMatu= " + doc.getBondMatu());
        }
    }

    private BondBasicInfoClassOneDoc getDaysForString(String dateString) {
        BondBasicInfoClassOneDoc temp = new BondBasicInfoClassOneDoc();
        int days = 0;
        if (dateString.contains("Y") || dateString.contains("y")) {
            int index = dateString.indexOf("Y") == -1 ? dateString.indexOf("y") : dateString.indexOf("Y");
            String num = dateString.substring(0, index);
            days = new BigDecimal(Integer.valueOf(num)).multiply(new BigDecimal(365)).intValue();
        } else if (dateString.contains("M") || dateString.contains("m")) {
            int index = dateString.indexOf("M") == -1 ? dateString.indexOf("m") : dateString.indexOf("M");
            String num = dateString.substring(0, index);
            days = new BigDecimal(Integer.valueOf(num)).multiply(new BigDecimal(30)).intValue();
        } else if (dateString.contains("D") || dateString.contains("d")) {
            int index = dateString.indexOf("D") == -1 ? dateString.indexOf("d") : dateString.indexOf("D");
            String num = dateString.substring(0, index);
            days = Integer.valueOf(num);
        } else if (dateString.contains("N") || dateString.contains("n")) {
            days = 0;
        } else {
            days = new BigDecimal(Integer.valueOf(dateString)).multiply(new BigDecimal(365)).intValue();
        }
        temp.setMatuUnitParDays(days);
        return temp;
    }

    public Boolean deleteStopBidNewBond() {
        int result = 0;
        try {
            List<Long> codeList = new ArrayList<Long>();
            List<BondBasicInfoClassOneDoc> list = bondBasicInfoClassOneDao.findStopBidAll();
            if(null != list && list.size()>0) {
                list.stream().forEach(d -> codeList.add(d.getBondUniCode()));
            }
            Criteria cq = Criteria.where("_id").in(codeList);
            Query query = Query.query(cq);
            result = mongoTemplate.remove(query, BondBasicInfoClassOneDoc.class).getN();
        } catch (Exception e) {
            log.info("deleteStopBidNewBond 失败！");
            result = 0;
        }

        return result > 0;
        
    }

}
