package com.innodealing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.model.mongo.dm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.innodealing.amqp.SenderService;
import com.innodealing.bond.param.BrokerBondQuoteParam;
import com.innodealing.bond.service.BondBasicInfoService;
import com.innodealing.bond.service.BondQuantAnalysisService;
import com.innodealing.consts.BondTodayConstants;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.dm.bond.BondDeal;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.BeanUtil;
import com.innodealing.util.BondDealUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

/**
 * 今日成交+今日报价
 */
@Service
public class BondDiscoveryDataService {
    private static final Logger LOG = LoggerFactory.getLogger(BondDiscoveryDataService.class);

    protected @Autowired
    JdbcTemplate jdbcTemplate;

    protected @Autowired
    BondBasicInfoService bondInfoService;

    protected @Autowired
    BondAnalysislIngtService analysisIngtService;

    protected @Autowired
    RedisUtil redisUtil;

    protected @Autowired
    MongoOperations mongoOperations;

    private @Autowired
    SenderService senderService;

    private @Autowired
    BondQuantAnalysisService bondQuantService;

    private @Autowired
    BondBasicInfoRepository bondBasicInfoRepository;
    
    private static final BigDecimal CONVERTNETPRICE_INTEPAYSUM = BigDecimal.TEN.multiply(BigDecimal.TEN);

    /**
     * 今日成交
     * @param dealVO
     */
    @Async
    @EventListener
    public void subscribeSaveBondDealToday(BondDeal dealVO) {
        if (dealVO == null || dealVO.getBondUniCode() == null || dealVO.getBondRate() == null) {
            LOG.warn("subscribeSaveBondDealToday: invalid BondDeal without code or rate");
            return;
        }
        BondBasicInfoDoc basicInfoDoc = bondInfoService.findByBondUniCode(dealVO.getBondUniCode(), 0L);
        if (basicInfoDoc == null || basicInfoDoc.getTenorDays() == null || basicInfoDoc.getTenorDays() < 0) {
            LOG.warn("subscribeSaveBondDealToday: invalid basicInfoDoc with tenorDays");
            return;
        }
        // 今日成交只针对信用债
        if(basicInfoDoc.getDmBondType() == null || !BondDealUtil.isCreditDebt(basicInfoDoc.getDmBondType())){
        	LOG.warn("subscribeSaveBondDealToday: bondType(not credit bond)");
            return;
        }
        String displayTenor = analysisIngtService.getTenor(basicInfoDoc.getTenorDays());
        String dmRating = this.getDMRatingByBondUniCode(basicInfoDoc.getBondUniCode()); // DM量化评级
        String extRating = this.getExtRatingByBondUniCode(basicInfoDoc.getBondUniCode()); // 外部评级
        if (StringUtils.isBlank(displayTenor) || StringUtils.isBlank(dmRating) || StringUtils.isBlank(extRating)) {
            LOG.warn("subscribeSaveBondDealToday: invalid displayTenor/dmRating/extRating");
            return;
        }
        Date currDate = SafeUtils.getCurrentTime();
        BondDetailDoc detailDoc = mongoOperations.findById(dealVO.getBondUniCode(), BondDetailDoc.class);
        if (StringUtils.isBlank(detailDoc.getName()) || detailDoc.getBondUniCode() == null || detailDoc.getCode() == null) {
            LOG.warn("subscribeSaveBondDealToday: invalid name/bondUniCode/code");
            return;
        }
        BondDiscoveryTodayDealDetailDoc todayDealDoc = this.saveBondTodayDealDetailDoc(basicInfoDoc, detailDoc, dealVO,
                displayTenor, dmRating, extRating, currDate);
        // 发送数据，通过ws更新前端
        if (todayDealDoc != null) {
            // String jsonString = JSON.toJSONString(dealDoc, SerializerFeature.WriteDateUseDateFormat);
            senderService.sendBondDiscoveryTodayDeal2RabbitMQ(todayDealDoc);
        }
    }

    /**
     * 今日报价(需要最优报价，同步执行)
     * @param quoteVO
     */
    @EventListener
    public void subscribeSaveBondQuoteToday(BrokerBondQuoteParam quoteVO) {
        if (quoteVO == null || quoteVO.getBondUniCode() == null || quoteVO.getBondPrice() == null
                || quoteVO.getSide() == null) {
            LOG.warn("subscribeSaveBondQuoteToday: invalid BrokerBondQuoteParam without code or price or side");
            return;
        }
        BondBasicInfoDoc basicInfoDoc = bondInfoService.findByBondUniCode(quoteVO.getBondUniCode(), 0L);
        if (basicInfoDoc == null || basicInfoDoc.getTenorDays() == null || basicInfoDoc.getTenorDays() < 0) {
            LOG.warn("subscribeSaveBondQuoteToday: invalid basicInfoDoc with tenorDays");
            return;
        }
        // 今日报价只针对信用债
        if(basicInfoDoc.getDmBondType() == null || !BondDealUtil.isCreditDebt(basicInfoDoc.getDmBondType())){
        	LOG.warn("subscribeSaveBondQuoteToday: bondType(not credit bond)");
            return;
        }
        // 剩余期限
        String displayTenor = basicInfoDoc.getTenorDays() / 365 >= 7 ? "7Y"
                : analysisIngtService.getTenor(basicInfoDoc.getTenorDays());
        String dmRating = this.getDMRatingByBondUniCode(basicInfoDoc.getBondUniCode()); // DM量化评级
        String extRating = this.getExtRatingByBondUniCode(basicInfoDoc.getBondUniCode()); // 外部评级
        if (StringUtils.isBlank(displayTenor) || StringUtils.isBlank(dmRating) || StringUtils.isBlank(extRating)) {
            LOG.warn("subscribeSaveBondQuoteToday: invalid displayTenor/dmRating/extRating");
            return;
        }
        Date currDate = SafeUtils.getCurrentTime();
        if (StringUtils.isBlank(basicInfoDoc.getShortName()) || basicInfoDoc.getBondUniCode() == null
                || basicInfoDoc.getCode() == null) {
            LOG.warn("subscribeSaveBondQuoteToday: invalid basic name/bondUniCode/code");
            return;
        }
        BondDetailDoc detailDoc = mongoOperations.findById(basicInfoDoc.getBondUniCode(), BondDetailDoc.class);
        if (StringUtils.isBlank(detailDoc.getName()) || detailDoc.getBondUniCode() == null || detailDoc.getCode() == null) {
            LOG.warn("subscribeSaveBondQuoteToday: invalid detail name/bondUniCode/code");
            return;
        }
        BondDiscoveryTodayQuoteDetailDoc todayQuoteDoc = this.saveBondTodayQuoteDetailDoc(basicInfoDoc, detailDoc, quoteVO,
                displayTenor, dmRating, extRating, currDate);
        // 发送数据给MQ，通过ws更新前端
        if (todayQuoteDoc != null) {
            // String jsonString = JSON.toJSONString(quoteDoc, SerializerFeature.WriteDateUseDateFormat);
            senderService.sendBondDiscoveryTodayQuote2RabbitMQ(todayQuoteDoc);
        }
    }

    /**
     * 异常价格：成交价
     * @param dealVO
     */
    @Async
    @EventListener
    public void subscribeSaveBondDealAbnormalPrice(BondDeal dealVO) {
        // 时间，债券简称，剩余期限，成交净价，成交价收益率，估值净价，中债估值，中债估值收益率，估值偏离，净价偏离
        // 票面利率，应计利息，久期，修正久期，投资建议，内部评级，债项评级，主体评级，展望，企业性质，所处行业
        // 净价偏离=成交净价-中债估值
        // 估值偏离=成交价收益率-中债估值收益率
        Date pubDate = dealVO.getCreateTime();
        Long bondUniCode = dealVO.getBondUniCode();
        BigDecimal bondRate = dealVO.getBondRate();
        // 1339 异常成交价只显示8:30-17:00
        if (!BondDealUtil.isInTime0830And1700(pubDate)) {
            return;
        }
        BondBasicInfoDoc basicInfoDoc = bondInfoService.findByBondUniCode(bondUniCode, 0L);
        // 1344 价格异常只针对信用债
        if(basicInfoDoc == null || basicInfoDoc.getDmBondType() == null ||
                !BondDealUtil.isCreditDebt(basicInfoDoc.getDmBondType())){
            return;
        }
        String code = basicInfoDoc.getCode();
        try {
            BondDiscoveryAbnormalDealDoc abnormalDealDoc = new BondDiscoveryAbnormalDealDoc();
            abnormalDealDoc.setPubDate(pubDate);
            // BondRate为最新收益率, http://www.chinamoney.com.cn/fe-c/vol10BondDealQuotesMoreAction.do?lang=cn
            Double dealPriceYield = bondRate == null ? new Double(0) : bondRate.doubleValue(); // 成交价收益率(到期收益率)
            abnormalDealDoc.setDealPriceYield(this.getScaledDouble(dealPriceYield));
            Double dealNetPrice = bondQuantService.calCleanPriceByYTM(bondUniCode, dealPriceYield); // 成交价净价
            dealNetPrice = dealNetPrice == null ? new Double(0) : this.getScaledDouble(dealNetPrice);
            abnormalDealDoc.setDealNetPrice(dealNetPrice);
            if (dealNetPrice != null) { // 折算净价=实际净价/剩余本金比率， 换言之，折算净价>=实际净价
                abnormalDealDoc.setConvertNetPrice(this.calcConvertNetPrice(bondUniCode, dealNetPrice));
            }
            BigDecimal valuationNetPrice = this.getValuationNetPriceInBondInfo(code); // 估值净价
            abnormalDealDoc.setValuationNetPrice(this.getScaledDouble(SafeUtils.getSafeDouble(valuationNetPrice)));
            BigDecimal valuationYield = this.getValuationYieldInBondInfo(code); // 估值收益率
            Double valuationYieldD = SafeUtils.getSafeDouble(valuationYield);
            abnormalDealDoc.setValuationYield(this.getScaledDouble(valuationYieldD));
            if (bondRate != null && valuationYield != null) {
                // 估值偏离=最新收益率bondRate - bondinfo.exercise_yield或者bondinfo.rate
                BigDecimal diffBP = bondRate.subtract(valuationYield).multiply(new BigDecimal(100))
                        .setScale(0, BigDecimal.ROUND_HALF_UP);
                abnormalDealDoc.setValuationDeviation(SafeUtils.getSafeDouble(diffBP)); // 估值偏离=成交价收益率-估值收益率
                if (diffBP.intValue() > 0) {
                    abnormalDealDoc.setDeviationDirection(1); // 偏离方向:1-低估
                } else if (diffBP.intValue() < 0) {
                    abnormalDealDoc.setDeviationDirection(2); // 偏离方向:2-高估
                }
            }

            // 估值偏离 = 最新收益率bondRate - bondinfo.exercise_yield或者bondinfo.rate
            // 净价偏离 = bondQuantService.calCleanPriceByYTM - bondinfo.net_valuation

            // 净价偏离=成交净价-中债估值净价
            BigDecimal netPriceDeviation = null;
            if (dealNetPrice != null && valuationNetPrice != null) {
                netPriceDeviation = new BigDecimal(dealNetPrice).subtract(valuationNetPrice)
                        .setScale(4, BigDecimal.ROUND_HALF_UP);
            }
            abnormalDealDoc.setNetPriceDeviation(SafeUtils.getSafeDouble(netPriceDeviation));
            Double couponRate = SafeUtils.getSafeDouble(basicInfoDoc.getNewCoupRate()); // 票面利率
            abnormalDealDoc.setCouponRate(couponRate);
            Double accruedInterest = bondQuantService.getAccruedInterest(bondUniCode); // 应计利息
            abnormalDealDoc.setAccruedInterest(accruedInterest);
            BigDecimal macd = this.getDurationInBond(bondUniCode, "macd"); // 久期
            abnormalDealDoc.setMacd(SafeUtils.getSafeDouble(macd));
            BigDecimal modd = this.getDurationInBond(bondUniCode, "modd"); // 修正久期
            abnormalDealDoc.setModd(SafeUtils.getSafeDouble(modd));
            // 复制下列字段：
            // bondUniCode，债券代码code，债券简称shortName，剩余期限tenor,剩余期限天数tenorDays,内部评级instRatingMap
            // 投资建议instInvestmentAdviceMap, 所处行业institutionInduMap, 债项评级bondRating, 主体评级issBondRating
            // 债项展望ratePros, 主体展望issRatePros, 企业性质comAttrPar
            BeanUtil.deepCopyProperties(basicInfoDoc, abnormalDealDoc);
            if (abnormalDealDoc.getComAttrPar() != null) {
                abnormalDealDoc.setComAttrParDesc(UIAdapter.cvtComAttr2OfficialUIStr(abnormalDealDoc.getComAttrPar()));
            }
            // 是否同业存单
            abnormalDealDoc.setNCD(BondTodayConstants.NCD_TYPE.equals(basicInfoDoc.getDmBondType()));
            
            if (abnormalDealDoc.getCode() != null && abnormalDealDoc.getBondUniCode() != null
                    && abnormalDealDoc.getShortName() != null) {
                mongoOperations.save(abnormalDealDoc);
                // 发送数据给MQ，通过ws更新前端
                // String jsonString = JSON.toJSONString(abnormalDealDoc, SerializerFeature.WriteDateUseDateFormat);
                senderService.sendBondDiscoveryAbnormalDeal2RabbitMQ(abnormalDealDoc);
            }
        } catch (Exception ex) {
            LOG.error("subscribeSaveBondDealAbnormalPrice error: " + dealVO.toString(), ex);
        }
    }

    private Double getScaledDouble(Double sourceDouble) {
        if (sourceDouble == null) return null;
        return new BigDecimal(sourceDouble).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 异常价格：报价
     * @param quoteVO
     */
    @Async
    @EventListener
    public void subscribeSaveBondQuoteAbnormalPrice(BrokerBondQuoteParam quoteVO) {
        // 时间，债券简称，剩余期限
        // （Bid净价，Bid收益率）/（Ofr净价，Ofr收益率）
        // 估值净价，中债估值，中债估值收益率，估值偏离，净价偏离
        // 票面利率，应计利息，久期，修正久期，投资建议，内部评级，债项评级，主体评级，展望，企业性质，所处行业
        // 净价偏离=成交净价-中债估值
        // 估值偏离=成交价收益率-中债估值收益率
        Date pubDate = quoteVO.getSendTime();
        Integer side = quoteVO.getSide();
        Long bondUniCode = quoteVO.getBondUniCode();
        BigDecimal originalBondPrice = quoteVO.getBondPrice();
        if (originalBondPrice == null) {
            return;
        }
        //1339 异常成交价只显示8:30-17:00
        if (!BondDealUtil.isInTime0830And1700(pubDate)) {
            return;
        }
        BondBasicInfoDoc basicInfoDoc = bondInfoService.findByBondUniCode(bondUniCode, 0L);
        //1344 价格异常只针对信用债
        if(basicInfoDoc == null || basicInfoDoc.getDmBondType() == null ||
                !BondDealUtil.isCreditDebt(basicInfoDoc.getDmBondType())){
            return;
        }
        String code = basicInfoDoc.getCode();
        try {
            BondDiscoveryAbnormalQuoteDoc abnormalQuoteDoc = new BondDiscoveryAbnormalQuoteDoc();
            abnormalQuoteDoc.setSide(side); // 报价类型：1-Bid出券;2-Ofr收券
            abnormalQuoteDoc.setPubDate(pubDate);
            BigDecimal bondPrice = quoteVO.getBondPrice().setScale(4, BigDecimal.ROUND_HALF_UP);
            Double quoteNetPrice; // 报价净价
            Double quotePriceYield; // 报价收益率
            if (bondPrice != null && bondPrice.doubleValue() > 50) {
                quoteNetPrice = bondPrice.doubleValue(); // bondPrice是报价净价
                quotePriceYield = this.getScaledDouble(bondQuantService.calYTMByCleanPrice(bondUniCode, quoteNetPrice)); // 报价收益率
            } else {
                quotePriceYield = bondPrice == null ? new Double(0) : bondPrice.doubleValue(); // bondPrice是报价收益率
                quoteNetPrice = this.getScaledDouble(bondQuantService.calCleanPriceByYTM(bondUniCode, quotePriceYield)); // 报价净价
            }
            if (side == 1) {
                // Bid出券
                abnormalQuoteDoc.setBidNetPrice(quoteNetPrice); // Bid净价
                abnormalQuoteDoc.setBidPriceYield(quotePriceYield); // Bid收益率
            } else {
                // Ofr收券
                abnormalQuoteDoc.setOfrNetPrice(quoteNetPrice); // Ofr净价
                abnormalQuoteDoc.setOfrPriceYield(quotePriceYield); // Ofr收益率
            }
            if (quoteNetPrice != null) { // 折算净价
                abnormalQuoteDoc.setConvertNetPrice(this.calcConvertNetPrice(bondUniCode, quoteNetPrice));
            }

            BigDecimal valuationNetPrice = this.getValuationNetPriceInBondInfo(code); // 估值净价
            abnormalQuoteDoc.setValuationNetPrice(this.getScaledDouble(SafeUtils.getSafeDouble(valuationNetPrice)));
            BigDecimal valuationYield = this.getValuationYieldInBondInfo(code); // 估值收益率
            Double valuationYieldD = SafeUtils.getSafeDouble(valuationYield);
            abnormalQuoteDoc.setValuationYield(this.getScaledDouble(valuationYieldD));
            if (quotePriceYield != null && valuationYield != null) {
                BigDecimal diffBP = new BigDecimal(quotePriceYield).subtract(valuationYield).multiply(new BigDecimal(100))
                        .setScale(0, BigDecimal.ROUND_HALF_UP);
                abnormalQuoteDoc.setValuationDeviation(SafeUtils.getSafeDouble(diffBP)); // 估值偏离=报价收益率-估值收益率
                if (diffBP.intValue() > 0) {
                    abnormalQuoteDoc.setDeviationDirection(1); // 偏离方向:1-低估
                } else if (diffBP.intValue() < 0) {
                    abnormalQuoteDoc.setDeviationDirection(2); // 偏离方向:2-高估
                }
            }
            // 净价偏离=Bid/Ofr净价-中债估值净价
            BigDecimal netPriceDeviation = null;
            if (quoteNetPrice != null && valuationNetPrice!= null) {
                netPriceDeviation = new BigDecimal(quoteNetPrice).subtract(valuationNetPrice)
                        .setScale(4, BigDecimal.ROUND_HALF_UP);
            }
            abnormalQuoteDoc.setNetPriceDeviation(SafeUtils.getSafeDouble(netPriceDeviation));
            Double couponRate = SafeUtils.getSafeDouble(basicInfoDoc.getNewCoupRate()); // 票面利率
            abnormalQuoteDoc.setCouponRate(couponRate);
            Double accruedInterest = bondQuantService.getAccruedInterest(bondUniCode); // 应计利息
            abnormalQuoteDoc.setAccruedInterest(accruedInterest);
            BigDecimal macd = this.getDurationInBond(bondUniCode, "macd"); // 久期
            abnormalQuoteDoc.setMacd(SafeUtils.getSafeDouble(macd));
            BigDecimal modd = this.getDurationInBond(bondUniCode, "modd"); // 修正久期
            abnormalQuoteDoc.setModd(SafeUtils.getSafeDouble(modd));
            // 复制下列字段：
            // bondUniCode，债券代码code，债券简称shortName，剩余期限tenor,剩余期限天数tenorDays,内部评级instRatingMap
            // 投资建议instInvestmentAdviceMap, 所处行业institutionInduMap, 债项评级bondRating, 主体评级issBondRating
            // 债项展望ratePros, 主体展望issRatePros, 企业性质comAttrPar
            BeanUtil.deepCopyProperties(basicInfoDoc, abnormalQuoteDoc);
            if (abnormalQuoteDoc.getComAttrPar() != null) {
                abnormalQuoteDoc.setComAttrParDesc(UIAdapter.cvtComAttr2OfficialUIStr(abnormalQuoteDoc.getComAttrPar()));
            }
            // 是否同业存单
            abnormalQuoteDoc.setIsNCD(BondTodayConstants.NCD_TYPE.equals(basicInfoDoc.getDmBondType()));
            
            if (abnormalQuoteDoc.getCode() != null && abnormalQuoteDoc.getBondUniCode() != null
                    && abnormalQuoteDoc.getShortName() != null) {
                mongoOperations.save(abnormalQuoteDoc);
                // 发送数据给MQ，通过ws更新前端
                // String jsonString = JSON.toJSONString(abnormalQuoteDoc, SerializerFeature.WriteDateUseDateFormat);
                senderService.sendBondDiscoveryAbnormalQuote2RabbitMQ(abnormalQuoteDoc);
            }
        } catch (Exception ex) {
            LOG.error("subscribeSaveBondQuoteAbnormalPrice error: " + quoteVO.toString(), ex);
        }
    }

    /**
     * 获取折算净价
     * @return
     */
    private Double calcConvertNetPrice(Long bondUniCode, Double netPrice) {
        Double result = 0D;
        BigDecimal payedTotalInterest = this.getIntePayRateEndSum(bondUniCode);
        BigDecimal leftTotalInterest = CONVERTNETPRICE_INTEPAYSUM.subtract(payedTotalInterest);
        if (leftTotalInterest.compareTo(BigDecimal.ZERO) != 0) {
            result = CONVERTNETPRICE_INTEPAYSUM.divide(leftTotalInterest, 4, BigDecimal.ROUND_HALF_DOWN)
                    .multiply(new BigDecimal(netPrice)).setScale(4, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
        }
        return result;
    }

    /**
     * 获取久期和修正久期
     * @param bondId
     * @param key
     * @return
     */
    private BigDecimal getDurationInBond(Long bondId, String key) {
        try {
            String sql = "SELECT t.%1$s FROM dmdb.t_bond_duration t WHERE t.bond_uni_code=%2$d " +
                    "ORDER BY t.update_time DESC LIMIT 1;";
            String formatSql = String.format(sql, key, bondId);
            return jdbcTemplate.queryForObject(formatSql, BigDecimal.class);
        } catch (Exception ex) {
            //LOGGER.error("getDurationInBond error," + ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 获取中债估值收益率
     * @param bondCode
     * @return
     */
    private BigDecimal getValuationYieldInBondInfo(String bondCode) {
        try {
            String sql = "SELECT CASE WHEN b.exercise_yield IS NOT NULL AND b.exercise_yield !=0 THEN b.exercise_yield " +
                    "WHEN b.rate IS NOT NULL AND b.rate !=0 THEN b.rate ELSE 0 END AS rate " +
                    "FROM innodealing.bond_info b WHERE b.code='%1$s' ORDER BY b.create_time DESC LIMIT 1;";
            String formatSql = String.format(sql, bondCode);
            return jdbcTemplate.queryForObject(formatSql, BigDecimal.class);
        } catch (Exception ex) {
            //LOGGER.error("getValuationInBondInfo error," + ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 获取中债估值的净价
     * @param bondCode
     * @return
     */
    private BigDecimal getValuationNetPriceInBondInfo(String bondCode) {
        try {
            String sql = "SELECT net_valuation FROM innodealing.bond_info WHERE code='%1$s' ORDER BY create_time DESC LIMIT 1;";
            String formatSql = String.format(sql, bondCode);
            return jdbcTemplate.queryForObject(formatSql, BigDecimal.class);
        } catch (Exception ex) {
            //LOGGER.error("getValuationInBondInfo error," + ex.getMessage(), ex);
        }
        return null;
    }
    
    /**
     * 获取折算净价中已经还本的总百分比
     * @param bondUniCode
     * @return
     */
    private BigDecimal getIntePayRateEndSum(Long bondUniCode) {
    	BigDecimal result = new BigDecimal(0);
    	try {
    	    // 需求1845：取 INTE_PAY_END 小于等于 现在时间的 INTE_PAY_END 之和 =已付本金 (小于等于)
            String sql = "SELECT IFNULL(SUM(t.INTE_PAY_END),0) AS intePayEndSum" +
                    " FROM bond_ccxe.d_bond_cash_flow_chart t WHERE t.BOND_UNI_CODE=%1$d" +
                    " AND DATEDIFF(t.INTE_END_DATE,DATE_FORMAT(NOW(),'%%Y-%%m-%%d 23:59:59'))<=0";
            String formatSql = String.format(sql, bondUniCode);
            result = jdbcTemplate.queryForObject(formatSql, BigDecimal.class);
            LOG.info("getIntePayRateEndSum formatSql:"+formatSql+",result:"+result);
        } catch (Exception ex) {
        	LOG.error("getIntePayRateEndSum error," + ex.getMessage(), ex);
        }
        return result;
    }

    /**
     *
     * @param basicDoc
     * @param detailDoc
     * @param quoteVO
     * @param displayTenor
     * @param dmRating
     * @param extRating
     * @param currDate
     * @return
     */
    private BondDiscoveryTodayQuoteDetailDoc saveBondTodayQuoteDetailDoc(BondBasicInfoDoc basicDoc, BondDetailDoc detailDoc,
                                                                         BrokerBondQuoteParam quoteVO, String displayTenor,
                                                                         String dmRating, String extRating, Date currDate) {
        Integer side = quoteVO.getSide();
        BigDecimal newPrice = quoteVO.getBondPrice().setScale(4, BigDecimal.ROUND_HALF_UP);
        Long bondUniCode= quoteVO.getBondUniCode();
        BondDiscoveryTodayQuoteDetailDoc doc = new BondDiscoveryTodayQuoteDetailDoc();
        BeanUtil.copyProperties(detailDoc, doc);
        BeanUtil.deepCopyProperties(basicDoc, doc);

        doc.setSide(side);
        doc.setBondPrice(newPrice);// 报价
        doc.setBondVol(quoteVO.getBondVol());//债券发行量
        doc.setDisplayTenor(displayTenor); // 显示期限
        doc.setDmRating(dmRating); // 主体量化评级
        doc.setExtRating(extRating); // 外部评级
        doc.setCreateTime(currDate); // 保存当前时间
        if (doc.getComAttrPar() != null) { // 企业性质描述
            doc.setComAttrParDesc(UIAdapter.cvtComAttr2OfficialUIStr(doc.getComAttrPar()));
        }
        // 是否同业存单
        doc.setIsNCD(BondTodayConstants.NCD_TYPE.equals(basicDoc.getDmBondType()));
        // 是否单边报价
        doc.setIsUnilateralOffer(true);
        Integer otherSide = side == 1 ? 2 : 1;
        Query unilateralQuery = new Query(Criteria.where("bondUniCode").is(bondUniCode).and("side").is(otherSide));
        long otherSideQuoteDocCount = mongoOperations.count(unilateralQuery, BondDiscoveryTodayQuoteDetailDoc.class);
        if (otherSideQuoteDocCount > 0) {
            mongoOperations.updateMulti(unilateralQuery, Update.update("isUnilateralOffer", false),
                    BondDiscoveryTodayQuoteDetailDoc.class);
            doc.setIsUnilateralOffer(false);
        }
        // 最优报价
        doc.setBestPrice(newPrice);
        // 注释以下代码：中债估值已经在basicInfo中复制过来了
//        //中债估值
//        doc.setFairValue(detailDoc.getFairValue());
        
        Query bestPriceQuery = new Query(Criteria.where("bondUniCode").is(bondUniCode).and("side").is(side));
        List<BondDiscoveryTodayQuoteDetailDoc> bestPriceQuoteDocList = mongoOperations.find(bestPriceQuery, BondDiscoveryTodayQuoteDetailDoc.class);
        if (!bestPriceQuoteDocList.isEmpty()) {
            BigDecimal oldBestPrice = bestPriceQuoteDocList.get(0).getBestPrice();
            if ((side == 1 && newPrice.compareTo(oldBestPrice) == -1) || (side == 2 && newPrice.compareTo(oldBestPrice) == 1)) {
                // bid，最小的为最优报价; offer，最大的为最优报价
                mongoOperations.updateMulti(bestPriceQuery, Update.update("bestPrice", newPrice), BondDiscoveryTodayQuoteDetailDoc.class);
            } else {
                doc.setBestPrice(oldBestPrice);
            }
        }
        LOG.info("BondDiscoveryTodayQuoteDetailDoc before save:"+JSON.toJSONString(doc));
        mongoOperations.save(doc);
        
        return doc;
    }

    /**
     * 查找DM主体信用评级
     *
     * @param bondUniCode
     * @return
     */
    private String getDMRatingByBondUniCode(Long bondUniCode) {
        String result;
        String sql = "SELECT a.rating FROM dmdb.dm_bond AS a" +
                " INNER JOIN dmdb.t_bond_com_ext b ON b.ama_com_id = a.comp_id" +
                " INNER JOIN bond_ccxe.bond_isser_info c ON b.com_uni_code=c.org_uni_code AND c.BOND_UNI_CODE=%1$d" +
                " WHERE a.rating IS NOT NULL ORDER BY a.year DESC, a.quan_month DESC LIMIT 1";
        String formatSql = String.format(sql, bondUniCode);
        try {
            result = jdbcTemplate.queryForObject(formatSql, String.class);
        } catch (Exception ex) {
            result = "CCC及以下";
        }
        return result;
    }

    /**
     * 查找主体外部信用评级
     *
     * @param bondUniCode
     * @return
     */
    private String getExtRatingByBondUniCode(Long bondUniCode) {
        String result;
        String sql = "SELECT c.iss_cred_level as rating FROM bond_ccxe.d_bond_iss_cred_chan c" +
                " LEFT JOIN bond_ccxe.bond_isser_info d ON c.com_uni_code = d.ORG_UNI_CODE" +
                " WHERE c.IS_NEW_RATE = 1 AND c.ISVALID=1 and com_type_par=1  AND d.BOND_UNI_CODE=%1$d" +
                " ORDER BY c.RATE_WRIT_DATE DESC LIMIT 1";
        String formatSql = String.format(sql, bondUniCode);
        try {
            result = jdbcTemplate.queryForObject(formatSql, String.class);
        } catch (Exception ex) {
            result = "≤A+";
        }
        return result;
    }

    /**
     * 创建今日成交数据
     *
     * @param detailDoc
     * @param dealVO
     * @param dmRating
     * @param extRating
     * @param displayTenor
     * @param currDate
     */
    private BondDiscoveryTodayDealDetailDoc saveBondTodayDealDetailDoc(BondBasicInfoDoc basicDoc, BondDetailDoc detailDoc,
                                                                       BondDeal dealVO, String displayTenor, String dmRating,
                                                                       String extRating, Date currDate) {
        BondDiscoveryTodayDealDetailDoc doc = new BondDiscoveryTodayDealDetailDoc();
        BeanUtil.copyProperties(detailDoc, doc);
        BeanUtil.deepCopyProperties(basicDoc, doc);

        doc.setPrice(dealVO.getBondRate()); // 当前成交价
        doc.setBp(dealVO.getBondBp()); // 涨跌幅
        doc.setBpTrend(dealVO.getBondBpTrend()); // 涨跌趋势
        doc.setDisplayTenor(displayTenor); // 显示期限
        doc.setDmRating(dmRating); // 主体量化评级
        doc.setExtRating(extRating); // 外部评级
        doc.setCreateTime(currDate); // 保存当前时间
        if (basicDoc.getComAttrPar() != null) { // 企业性质描述
            doc.setComAttrParDesc(UIAdapter.cvtComAttr2OfficialUIStr(basicDoc.getComAttrPar()));
        }
        // 是否是同业存单
        doc.setIsNCD(BondTodayConstants.NCD_TYPE.equals(detailDoc.getDmBondType()));
        // 注释以下代码：中债估值已经在basicInfo中复制过来了
//        //添加中债估值
//        doc.setFairValue(detailDoc.getFairValue());
        mongoOperations.save(doc);
        return doc;
    }


    public void initBondDiscoveryTodayDealData() {
        Query query = new Query();
        mongoOperations.remove(query, BondDiscoveryTodayDealDetailDoc.class);
        mongoOperations.remove(query, BondDiscoveryTodayDealUserDoc.class);
    }

    public void initBondDiscoveryTodayQuoteData() {
        Query query = new Query();
        mongoOperations.remove(query, BondDiscoveryTodayQuoteDetailDoc.class);
        mongoOperations.remove(query, BondDiscoveryTodayQuoteUserDoc.class);
    }

    public void removeUselessBondDiscoveryTodayDealAndQuoteData(String dayString) {
        try {
            Date currDay = SafeUtils.convertStringToDate(dayString, SafeUtils.DATE_FORMAT);
            Query query = new Query(Criteria.where("createTime").lt(currDay));
            mongoOperations.remove(query, BondDiscoveryTodayDealDetailDoc.class);
            mongoOperations.remove(query, BondDiscoveryTodayDealUserDoc.class);
            mongoOperations.remove(query, BondDiscoveryTodayQuoteDetailDoc.class);
            mongoOperations.remove(query, BondDiscoveryTodayQuoteUserDoc.class);
        } catch (Exception ex) {
            // DO NOTHING
        }
    }

    public void removeUselessBondDiscoveryAbnormalDealAndQuoteData(String dayString) {
        try {
            Date currDay = SafeUtils.convertStringToDate(dayString, SafeUtils.DATE_FORMAT);
            Query query = new Query(Criteria.where("pubDate").lt(currDay));
            mongoOperations.remove(query, BondDiscoveryAbnormalDealDoc.class);
            mongoOperations.remove(query, BondDiscoveryAbnormalQuoteDoc.class);
        } catch (Exception ex) {
            // DO NOTHING
        }
    }

    public void initBondDiscoveryAbnormalDealIsNCD() {
        List<Long> bondUniCodeList = mongoOperations.find(new Query(Criteria.where("isNCD").exists(false)), BondDiscoveryAbnormalDealDoc.class)
                .stream().map(BondDiscoveryAbnormalDealDoc::getBondUniCode).collect(Collectors.toList());
        List<Long> notNCDBondUniCodeList = new ArrayList<>();
        bondBasicInfoRepository.findAllByBondUniCodeIn(bondUniCodeList).stream().forEach(basic -> {
            if (!BondTodayConstants.NCD_TYPE.equals(basic.getDmBondType())) {
                notNCDBondUniCodeList.add(basic.getBondUniCode());
            }
        });
        // 更新
        Query query = new Query(Criteria.where("bondUniCode").in(notNCDBondUniCodeList));
        mongoOperations.updateMulti(query, Update.update("isNCD", false), BondDiscoveryAbnormalDealDoc.class);
    }

    public void initBondDiscoveryAbnormalQuoteIsNCD() {
        List<Long> bondUniCodeList = mongoOperations.find(new Query(Criteria.where("isNCD").exists(false)), BondDiscoveryAbnormalQuoteDoc.class)
                .stream().map(BondDiscoveryAbnormalQuoteDoc::getBondUniCode).collect(Collectors.toList());
        // 找到不是NCD的列表
        List<Long> notNCDBondUniCodeList = new ArrayList<>();
        bondBasicInfoRepository.findAllByBondUniCodeIn(bondUniCodeList).stream().forEach(basic -> {
            if (!BondTodayConstants.NCD_TYPE.equals(basic.getDmBondType())) {
                notNCDBondUniCodeList.add(basic.getBondUniCode());
            }
        });
        // 更新
        Query query = new Query(Criteria.where("bondUniCode").in(notNCDBondUniCodeList));
        mongoOperations.updateMulti(query, Update.update("isNCD", false), BondDiscoveryAbnormalQuoteDoc.class);
    }

    public String initBondDiscoveryAbnormalDealConvertNetPrice() {
        Query query = new Query(Criteria.where("dealNetPrice").exists(true));
        List<BondDiscoveryAbnormalDealDoc> dealDocList = mongoOperations.find(query, BondDiscoveryAbnormalDealDoc.class);
        dealDocList.stream().forEach(item -> {
            Long bondUniCode = item.getBondUniCode();
            Double convertNetPrice = this.calcConvertNetPrice(bondUniCode, item.getDealNetPrice());
            Query itemQuery = new Query(Criteria.where("bondUniCode").is(bondUniCode)
                    .and("dealNetPrice").is(item.getDealNetPrice()).and("pubDate").is(item.getPubDate()));
            mongoOperations.updateMulti(itemQuery, Update.update("convertNetPrice", convertNetPrice), BondDiscoveryAbnormalDealDoc.class);
        });
        return String.format("完善了%1$d条成交价。", dealDocList.size());
    }

    public String initBondDiscoveryAbnormalQuoteConvertNetPrice() {
        Query bidQuery = new Query(Criteria.where("side").is(1).and("bidNetPrice").exists(true));
        List<BondDiscoveryAbnormalQuoteDoc> bidDocList = mongoOperations.find(bidQuery, BondDiscoveryAbnormalQuoteDoc.class);
        bidDocList.stream().forEach(item -> {
            Long bondUniCode = item.getBondUniCode();
            Double bidNetPrice = item.getBidNetPrice();
            Double convertNetPrice = this.calcConvertNetPrice(bondUniCode, bidNetPrice);
            Query itemQuery = new Query(Criteria.where("bondUniCode").is(bondUniCode).and("side").is(1)
                    .and("bidNetPrice").is(bidNetPrice).and("pubDate").is(item.getPubDate()));
            mongoOperations.updateMulti(itemQuery, Update.update("convertNetPrice", convertNetPrice), BondDiscoveryAbnormalQuoteDoc.class);
        });
        // Ofr
        Query ofrQuery = new Query(Criteria.where("side").is(2).and("ofrNetPrice").exists(true));
        List<BondDiscoveryAbnormalQuoteDoc> ofrDocList = mongoOperations.find(ofrQuery, BondDiscoveryAbnormalQuoteDoc.class);
        ofrDocList.stream().forEach(item -> {
            Long bondUniCode = item.getBondUniCode();
            Double ofrNetPrice = item.getOfrNetPrice();
            Double convertNetPrice = this.calcConvertNetPrice(bondUniCode, ofrNetPrice);
            Query itemQuery = new Query(Criteria.where("bondUniCode").is(bondUniCode).and("side").is(2)
                    .and("ofrNetPrice").is(ofrNetPrice).and("pubDate").is(item.getPubDate()));
            mongoOperations.updateMulti(itemQuery, Update.update("convertNetPrice", convertNetPrice), BondDiscoveryAbnormalQuoteDoc.class);
        });
        return String.format("完善了%1$d条收券。完善了%2$d条出券。", bidDocList.size(), ofrDocList.size());
    }
}
