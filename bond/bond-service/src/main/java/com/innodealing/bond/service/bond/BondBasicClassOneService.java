package com.innodealing.bond.service.bond;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import com.innodealing.bond.param.bond.BondClassOneParam;
import com.innodealing.bond.param.bond.BondClassOneSearchPage;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.engine.jdbc.bond.BondAnnAttInfoDao;
import com.innodealing.model.dm.bond.BondClassOneAnnAttInfo;
import com.innodealing.model.dm.bond.BondClassOneAnnInfo;
import com.innodealing.model.dm.bond.BondClassOneAttInfoVo;
import com.innodealing.model.mongo.dm.BondBasicInfoClassOneDoc;
import com.innodealing.util.DateUtils;
import com.innodealing.util.SortComparator;

/**
 * 一级发行
 * 
 * @author 赵正来
 *
 */
@Service
public class BondBasicClassOneService {

    private static final Logger log = LoggerFactory.getLogger(BondBasicClassOneService.class);

    private @Autowired MongoTemplate mongoTemplate;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    private BondInduService bondInduService;

    @Autowired
    private BondAnnAttInfoDao bondAnnAttInfoDao;

    public BondClassOneSearchPage find(BondClassOneParam bondClassOneParam, Long userId, Integer page, Integer limit) {
        Criteria cq = getQueryCriteria(bondClassOneParam, userId);
        Query query = Query.query(cq);
        // 排序
        String sortfield = "issStartDate";// 默认发行起始日升序：例如11月27日在11月28日之前
        String sortType = "asc";
        Sort sort = new Sort(Direction.ASC, sortfield);
        if (!StringUtils.isEmpty(bondClassOneParam.getSort())) {
            String[] order = bondClassOneParam.getSort().split(":");
            if (order.length == 2) {
                sort = new Sort(Direction.ASC, order[0]);
                if (Direction.DESC.toString().equalsIgnoreCase(order[1])) {
                    sort = new Sort(Direction.DESC, order[0]);
                    sortType = "desc";
                }
                sortfield = order[0];
            }
        }
        BondClassOneSearchPage resultPage = new BondClassOneSearchPage();
        long count = mongoOperations.count(query, BondBasicInfoClassOneDoc.class);
        if (count > 0) {
            Long totalPage = count % limit == 0 ? count / limit : count / limit + 1;
            if (totalPage.intValue() <= page) {
                page = totalPage.intValue();
            }
            int skip = (page - 1) * limit;// 跳过多少条
            Query listQuery = query;
            // listQuery.with(sort);
            // listQuery.skip(skip).limit(limit);
            List<BondBasicInfoClassOneDoc> result = mongoTemplate.find(listQuery, BondBasicInfoClassOneDoc.class);
            /*
             * 可排序的字段：发行规模，主体/债项评级，DM量化评分，YY评分，YY定价，兴业评分，兴业定价，利率（招标区间），截标时间，
             * 上市日期。 保证所有为空的字段无论升序降序都排在最后。 issScaleSort issRatingSort
             * dmRatingScoreSort yyRationgSort orgPriceLower
             * industSubjectScoreSort referenceReturnsRatio rateSort
             * stopBidEndDate listDate
             */
            SortComparator<BondBasicInfoClassOneDoc> sortOperation = new SortComparator<BondBasicInfoClassOneDoc>(sortfield, sortType);
            Collections.sort(result, sortOperation);
            resultPage.setBondList(result.subList(skip, ((skip + limit) > result.size()) ? result.size() : (skip + limit)));
            resultPage.setCurrentPage(new Long(page));
            resultPage.setTotalCount(count);
            resultPage.setTotalPage(totalPage);
        } else {
            resultPage.setBondList(new ArrayList<BondBasicInfoClassOneDoc>());
            resultPage.setCurrentPage(0L);
            resultPage.setTotalCount(0L);
            resultPage.setTotalPage(0L);
        }

        return resultPage;
    }

    /**
     * 
     * findReoport:(查询导出：提供report-service使用)
     * 
     * @param @param
     *            bondClassOneParam
     * @param @param
     *            userId
     * @param @param
     *            page
     * @param @param
     *            limit
     * @param @return
     *            设定文件
     * @return BondClassOneSearchPage DOM对象
     * @throws @since
     *             CodingExample Ver 1.1
     */
    public List<BondBasicInfoClassOneDoc> findReoport(String jsonStringParam, Long userId) {
        List<BondBasicInfoClassOneDoc> result = new ArrayList<BondBasicInfoClassOneDoc>();
        BondClassOneParam bondClassOneParam = JSONObject.toJavaObject(JSONObject.parseObject(jsonStringParam), BondClassOneParam.class);
        Criteria cq = getQueryCriteria(bondClassOneParam, userId);
        Query query = Query.query(cq);
        // 排序
        String sortfield = "issStartDate";// 默认发行起始日升序：例如11月27日在11月28日之前
        String sortType = "asc";
        Sort sort = new Sort(Direction.ASC, sortfield);
        if (!StringUtils.isEmpty(bondClassOneParam.getSort())) {
            String[] order = bondClassOneParam.getSort().split(":");
            if (order.length == 2) {
                sort = new Sort(Direction.ASC, order[0]);
                if (Direction.DESC.toString().equalsIgnoreCase(order[1])) {
                    sort = new Sort(Direction.DESC, order[0]);
                    sortType = "desc";
                }
                sortfield = order[0];
            }
        }
        long count = mongoOperations.count(query, BondBasicInfoClassOneDoc.class);
        if (count > 0) {
            result = mongoTemplate.find(query, BondBasicInfoClassOneDoc.class);
            SortComparator<BondBasicInfoClassOneDoc> sortOperation = new SortComparator<BondBasicInfoClassOneDoc>(sortfield, sortType);
            Collections.sort(result, sortOperation);
        }

        return result;
    }

    /**
     * 获取新债公告附件分类
     */
    public BondClassOneAttInfoVo getAttInfoList(Long bondUniCode) {
        BondClassOneAttInfoVo vo = new BondClassOneAttInfoVo();
        List<BondClassOneAnnInfo> annList = bondAnnAttInfoDao.queryAnnInfoList(bondUniCode);

        List<BondClassOneAnnAttInfo> mjAttList = null;
        List<BondClassOneAnnAttInfo> sgAttList = null;
        List<BondClassOneAnnAttInfo> otherAttList = null;
        if (null != annList && !annList.isEmpty()) {
            mjAttList = bondAnnAttInfoDao.queryAttInfoList(annList, "募集");
            sgAttList = bondAnnAttInfoDao.queryAttInfoList(annList, "申购");
            otherAttList = bondAnnAttInfoDao.queryOtherAttInfoList(annList);
        }
        vo.setBondUniCode(bondUniCode);
        vo.setApplyPurchaseList(sgAttList == null ? new ArrayList<>() : sgAttList);
        vo.setRaiseList(mjAttList == null ? new ArrayList<>() : mjAttList);
        vo.setOtherList(otherAttList == null ? new ArrayList<>() : otherAttList);
        return vo;

    }

    private Criteria getQueryCriteria(BondClassOneParam bondClassOneParam, Long userId) {
        List<Criteria> cqL = new ArrayList<>();

        // 发行起始日: 交叉时间查询
        if (bondClassOneParam.getIssStartDate() != null && bondClassOneParam.getIssEndDate() != null) {
            Date issStartDate = DateUtils.convertStringToDate(
                    DateUtils.convertDateToString(bondClassOneParam.getIssStartDate(), DateUtils.DATE_FORMAT) + " 00:00:00", DateUtils.DATE_TIME_FORMAT1);// 加上时间
            Date issEndDate = DateUtils.convertStringToDate(
                    DateUtils.convertDateToString(bondClassOneParam.getIssEndDate(), DateUtils.DATE_FORMAT) + " 23:59:59", DateUtils.DATE_TIME_FORMAT1);// 加上时间
            Criteria cS = Criteria.where("frontBidStartDate").gte(issStartDate).lte(issEndDate);
            Criteria cE = Criteria.where("frontBidEndDate").gte(issStartDate).lte(issEndDate);
            Criteria cqS = Criteria.where("frontBidStartDate").lte(issStartDate).and("frontBidEndDate").gte(issStartDate);
            Criteria cqE = Criteria.where("frontBidStartDate").lte(issEndDate).and("frontBidEndDate").gte(issEndDate);
            Criteria ciSd =  new Criteria();
            ciSd.orOperator(cS,cE,cqS,cqE);
            cqL.add(ciSd);
        } else if (bondClassOneParam.getIssStartDate() != null && bondClassOneParam.getIssEndDate() == null) {//查询开始时间落在区间，或截标开始时间》=查询开始时间
            Date issStartDate = DateUtils.convertStringToDate(
                    DateUtils.convertDateToString(bondClassOneParam.getIssStartDate(), DateUtils.DATE_FORMAT) + " 00:00:00", DateUtils.DATE_TIME_FORMAT1);// 加上时间
            Criteria cS = Criteria.where("frontBidStartDate").lte(issStartDate).and("frontBidEndDate").gte(issStartDate);
            Criteria cE = Criteria.where("frontBidStartDate").gte(issStartDate);
            Criteria ciSd =  new Criteria();
            ciSd.orOperator(cS,cE);
            cqL.add(ciSd);
        } else if (bondClassOneParam.getIssStartDate() == null && bondClassOneParam.getIssEndDate() != null) {//查询结束时间落在区间，或截标结束时间《=查询结束时间
            Date issEndDate = DateUtils.convertStringToDate(
                    DateUtils.convertDateToString(bondClassOneParam.getIssEndDate(), DateUtils.DATE_FORMAT) + " 23:59:59", DateUtils.DATE_TIME_FORMAT1);// 加上时间
            Criteria cS = Criteria.where("frontBidStartDate").lte(issEndDate).and("frontBidEndDate").gte(issEndDate);
            Criteria cE = Criteria.where("frontBidEndDate").lte(issEndDate);
            Criteria ciSd =  new Criteria();
            ciSd.orOperator(cS,cE);
            cqL.add(ciSd);
        } else {// 选择全部 只展示截止日期为今天（含）之后的数据,加上截标日期为空的数据
            // Date currentDate =
            // DateUtils.convertStringToDate(DateUtils.convertDateToString(
            // new Date(),DateUtils.DATE_FORMAT)+" 00:00:00",
            // DateUtils.DATE_TIME_FORMAT1);
            // Criteria ciDate = new Criteria();
            // Criteria ciSd =
            // Criteria.where("frontBidStartDate").gte(currentDate);
            // Criteria ciNull = Criteria.where("frontBidStartDate").is(null);
            // ciDate.orOperator(ciSd,ciNull);
            // cqL.add(ciDate);
        }
        // 债券类型
        if (bondClassOneParam.getBondTypePar() != null && bondClassOneParam.getBondTypePar().size() > 0) {
            Criteria ciBT = new Criteria();
            if (bondClassOneParam.getBondTypePar().contains(new Integer(0))) {// 其他
                bondClassOneParam.getBondTypePar().remove(new Integer(0));
                List<Integer> getOtherList = new ArrayList<>();
                getOtherList.add(9);
                getOtherList.add(23);
                getOtherList.add(8);
                getOtherList.add(5);
                getOtherList.add(4);
                if (bondClassOneParam.getBondTypePar().size() == 0) {
                    ciBT = Criteria.where("bondTypePar").nin(getOtherList);
                } else {
                    Criteria ci = Criteria.where("bondTypePar").in(bondClassOneParam.getBondTypePar());
                    Criteria co = Criteria.where("bondTypePar").nin(getOtherList);
                    ciBT.orOperator(ci, co);
                }
            } else {
                ciBT = Criteria.where("bondTypePar").in(bondClassOneParam.getBondTypePar());

            }
            cqL.add(ciBT);

        }
        // 债券期限
        if (bondClassOneParam.getBondMatu() != null && bondClassOneParam.getBondMatu().size() > 0) {
            Criteria criteria = getMatuUnitCriteria(bondClassOneParam.getBondMatu());
            cqL.add(criteria);
        }
        // 行业id
        if (bondClassOneParam.getInduId() != null && bondClassOneParam.getInduId().size() > 0) {
            List<Long> comUniCodes = new ArrayList<>();
            for (Long induId : bondClassOneParam.getInduId()) {
                comUniCodes.addAll(bondInduService.findComUniCodeByInduCode(induId, userId));
            }
            Criteria cIn = Criteria.where("comUniCode").in(comUniCodes);
            cqL.add(cIn);
        }
        // 发行方式
        if (null != bondClassOneParam.getIssCls() && bondClassOneParam.getIssCls().size() > 0) {
            Criteria cIss = Criteria.where("issCls").in(bondClassOneParam.getIssCls());
            cqL.add(cIss);
        }
        // 企业性质
        if (bondClassOneParam.getComAttrPar() != null && bondClassOneParam.getComAttrPar().size() > 0) {
            Criteria cCom = new Criteria();
            if (bondClassOneParam.getComAttrPar().contains(new Integer(0))) {// 1央企
                                                                             // 2国企
                                                                             // 6民企
                                                                             // 0其他
                bondClassOneParam.getComAttrPar().remove(new Integer(0));
                List<Integer> getOtherList = new ArrayList<>();
                getOtherList.add(1);
                getOtherList.add(2);
                getOtherList.add(6);
                if (bondClassOneParam.getComAttrPar().size() == 0) {
                    cCom = Criteria.where("comAttrPar").nin(getOtherList);
                } else {
                    Criteria ci = Criteria.where("comAttrPar").in(bondClassOneParam.getComAttrPar());
                    Criteria co = Criteria.where("comAttrPar").nin(getOtherList);
                    cCom.orOperator(ci, co);
                }
            } else {
                cCom = Criteria.where("comAttrPar").in(bondClassOneParam.getComAttrPar());
            }
            cqL.add(cCom);
        }
        // 模糊查询
        if (!StringUtils.isEmpty(bondClassOneParam.getSearchKey())) {
            Criteria cs = new Criteria();
            String queryString = bondClassOneParam.getSearchKey().replaceAll("(?=[]\\[+&|!(){}^\"~*?:\\\\-])", "\\\\");
            Pattern pattern = Pattern.compile("^.*" + queryString, Pattern.CASE_INSENSITIVE);
            Criteria cc = Criteria.where("bondCode").regex(pattern);
            Criteria cSn = Criteria.where("bondShortName").regex(pattern);
            Criteria cIn = Criteria.where("issName").regex(pattern);
            cs.orOperator(cc, cSn, cIn);
            cqL.add(cs);
        }
        // rating狗评分
        if (null != bondClassOneParam.getOrgRating() && bondClassOneParam.getOrgRating().size() > 0) {
            Criteria cR = Criteria.where("orgRating").in(bondClassOneParam.getOrgRating());
            cqL.add(cR);
        }
        // dm量化评分
        if (null != bondClassOneParam.getDmRatingScore() && bondClassOneParam.getDmRatingScore().size() > 0) {
            Criteria cRs = Criteria.where("dmRatingScore").in(bondClassOneParam.getDmRatingScore());
            cqL.add(cRs);
        }
        // 兴业主体评分
        if (null != bondClassOneParam.getIndustrialSubjectScore() && bondClassOneParam.getIndustrialSubjectScore().size() > 0) {
            Criteria cRIss = Criteria.where("industrialSubjectScore").in(bondClassOneParam.getIndustrialSubjectScore());
            cqL.add(cRIss);
        }
        // 兴业债项评分
        if (null != bondClassOneParam.getIndustrialBondScore() && bondClassOneParam.getIndustrialBondScore().size() > 0) {
            Criteria cRIss = Criteria.where("industrialBondScore").in(bondClassOneParam.getIndustrialBondScore());
            cqL.add(cRIss);
        }
        // 主体评级
        if (bondClassOneParam.getIssRating() != null && bondClassOneParam.getIssRating().size() > 0) {
            Criteria cCom = new Criteria();
            if (bondClassOneParam.getIssRating().contains("0")) {// 0其他
                bondClassOneParam.getIssRating().remove("0");
                List<String> getOtherList = new ArrayList<>();
                getOtherList.add("AAA");
                getOtherList.add("AA+");
                getOtherList.add("AA");
                getOtherList.add("AA-");
                getOtherList.add("A+");
                getOtherList.add("A-");
                getOtherList.add("A-1");
                if (bondClassOneParam.getIssRating().size() == 0) {
                    cCom = Criteria.where("issRating").nin(getOtherList);
                } else {
                    Criteria ci = Criteria.where("issRating").in(bondClassOneParam.getIssRating());
                    Criteria co = Criteria.where("issRating").nin(getOtherList);
                    cCom.orOperator(ci, co);
                }
            } else {
                cCom = Criteria.where("issRating").in(bondClassOneParam.getIssRating());
            }
            cqL.add(cCom);
        }
        // 债项评级
        if (bondClassOneParam.getBondRating() != null && bondClassOneParam.getBondRating().size() > 0) {
            Criteria cCom = new Criteria();
            if (bondClassOneParam.getBondRating().contains("0")) {// 0其他
                bondClassOneParam.getBondRating().remove("0");
                List<String> getOtherList = new ArrayList<>();
                getOtherList.add("AAA");
                getOtherList.add("AA+");
                getOtherList.add("AA");
                getOtherList.add("AA-");
                getOtherList.add("A+");
                if (bondClassOneParam.getBondRating().size() == 0) {
                    cCom = Criteria.where("bondRating").nin(getOtherList);
                } else {
                    Criteria ci = Criteria.where("bondRating").in(bondClassOneParam.getBondRating());
                    Criteria co = Criteria.where("bondRating").nin(getOtherList);
                    cCom.orOperator(ci, co);
                }
            } else {
                cCom = Criteria.where("bondRating").in(bondClassOneParam.getBondRating());
            }
            cqL.add(cCom);
        }
        // 兴业展望
        if (null != bondClassOneParam.getIndustExpectation() && bondClassOneParam.getIndustExpectation().size() > 0) {
            Criteria cIE = Criteria.where("industExpectation").in(bondClassOneParam.getIndustExpectation());
            cqL.add(cIE);
        }
        Criteria cq = new Criteria();
        if (null != cqL && cqL.size() > 0) {
            Criteria[] andCr = new Criteria[cqL.size()];
            for (int i = 0, size = cqL.size(); i < size; i++) {
                andCr[i] = cqL.get(i);
            }
            cq.andOperator(andCr);
        }
        return cq;
    }

    /**
     * 格式化期限条件
     * 
     * @param bondMatu
     * @return
     */
    @SuppressWarnings("null")
    private Criteria getMatuUnitCriteria(List<Integer> bondMatuList) {
        Criteria criteria = null;
        if (bondMatuList != null && bondMatuList.size() > 0) {
            criteria = new Criteria();
            if (bondMatuList.size() == 1) {
                criteria = getCriteria(bondMatuList.get(0));
            } else {
                Criteria[] cL = new Criteria[bondMatuList.size()];
                for (int i = 0; i < bondMatuList.size(); i++) {
                    cL[i] = getCriteria(bondMatuList.get(i));
                }
                criteria.orOperator(cL);
            }

        }
        return criteria;
    }

    private Criteria getCriteria(Integer bondMatu) {
        if (Objects.equal(1, bondMatu)) {
            return Criteria.where("matuUnitParDays").gt(0).lt(90);
        } else if (Objects.equal(2, bondMatu)) {
            return Criteria.where("matuUnitParDays").gte(90).lt(180);
        } else if (Objects.equal(3, bondMatu)) {
            return Criteria.where("matuUnitParDays").gte(180).lt(270);
        } else if (Objects.equal(4, bondMatu)) {
            return Criteria.where("matuUnitParDays").gte(270).lt(365);
        } else if (Objects.equal(5, bondMatu)) {
            return Criteria.where("matuUnitParDays").gte(365).lt(1095);
        } else if (Objects.equal(6, bondMatu)) {
            return Criteria.where("matuUnitParDays").gte(1095).lt(1825);
        } else if (Objects.equal(7, bondMatu)) {
            return Criteria.where("matuUnitParDays").gte(1825).lt(2555);
        } else if (Objects.equal(8, bondMatu)) {
            return Criteria.where("matuUnitParDays").gte(2555).lt(3650);
        } else if (Objects.equal(9, bondMatu)) {
            return Criteria.where("matuUnitParDays").gte(3650);
        } else if (Objects.equal(0, bondMatu)) {
            return Criteria.where("matuUnitParDays").lte(0);
        } else {
            return null;
        }
    }

}
