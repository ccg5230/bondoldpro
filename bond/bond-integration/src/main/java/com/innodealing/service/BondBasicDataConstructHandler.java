package com.innodealing.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import com.innodealing.aop.NoLogging;
import com.innodealing.bond.service.BondCityService;
import com.innodealing.engine.jpa.dm.BondBasicInfoRepositoryDm;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.BondCcxeBasicInfo;
import com.innodealing.model.InstInnerRatingInfo;
import com.innodealing.model.InstInvestmentAdviceInfo;
import com.innodealing.model.dm.bond.BondArea;
import com.innodealing.model.dm.bond.BondTypeXRef;
import com.innodealing.model.mongo.dm.BondBasicInfoClassOneDoc;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.dm.bond.BondBasicInfo;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDealDataDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondDetailVO;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.uilogic.UIAdapter.PdOpt;
import com.innodealing.util.BeanUtil;
@Service
public class BondBasicDataConstructHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BondBasicDataConstructHandler.class);

	@Autowired
	protected  JdbcTemplate jdbcTemplate;

	@Autowired
	private BondDetailRepository detailRepository;

	@Autowired 
	private BondBasicInfoRepository basicInfolRepository;
	
	@Autowired 
	private BondBasicInfoRepositoryDm basicInfoRepositoryDm;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	private CcxeParCache cvt;

	@Autowired
	BondComDataService comDataService;

	@Autowired
	BondTypeXRefCache bondTypeCache;

	@Autowired
	NegSentimentCache negSentimentCache;
	
	@Autowired 
	BondComSortRrsService rrsService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private BondInstInnerRateAndInvAdviceService instInnerRateAndInvAdviceService;
	
	@Autowired
	BondCityService bondCityService;

	private static AtomicLong totalProcessedBond = new AtomicLong(0);
	private static long processStartTime = System.currentTimeMillis();
	
	private static long getDifferenceDays(Date d1, Date d2, Long bondUniCode) {
	    DateTime from = new DateTime(d1);
	    DateTime to = new DateTime(d2);
	    //to = to.plusMillis(1);
	    //from = from.plusMillis(1);
	    long diff = Days.daysBetween(from.withZone(DateTimeZone.forID("Asia/Shanghai")), 
                to.withZone(DateTimeZone.forID("Asia/Shanghai"))).getDays();
	    if (from.isBefore(to)) {
	        diff += 1;
	    }
	    LOG.debug("bondUniCode:" + bondUniCode + 
	            ", d1:" + from + ", d1_l:" + from.toLocalDate() + ", dl_s:" + from.withZone(DateTimeZone.forID("Asia/Shanghai")) + 
	            ", d2:" + to + ", d2_l:" + to.toLocalDate() + ", d2_s:" + to.withZone(DateTimeZone.forID("Asia/Shanghai")) + 
	            ", diff:" + diff
	            );
   
	    return diff;
	}

	private static String formatTenorDays(long days, Boolean bool) {

		long years = days/365;
		if(years>0 && bool){
			Double newDay = (double) days/365;
			return (new BigDecimal(newDay).setScale(
					2, BigDecimal.ROUND_DOWN)).floatValue() + "Y";
		}else if(years>0 && !bool){
			return years + "Y";
		}
		
		return days + "D";
	}

	private String replaceNull(String input) {
		return input == null ? "" : input;
	}

	private String replaceEmptyCred(String input) {
		if (StringUtils.isEmpty(input)) {
			return "--";
		}
		return input;
	}

	@NoLogging
	public void refreshStatics()
	{
		processStartTime = System.currentTimeMillis();
		totalProcessedBond.set(0);
		
	}
	
	@NoLogging
	public void saveBondInfo2Mongo(List<BondCcxeBasicInfo> bondBasics) {
		List<BondBasicInfo> oBasicInfos = new ArrayList<BondBasicInfo>();
		Iterator<BondCcxeBasicInfo> iterator = bondBasics.iterator();
		
		List<Integer> insts = this.insts();
		Map<Integer, List<InstInvestmentAdviceInfo>> instBondsDataMap = instInnerRateAndInvAdviceService.getInstHistBondsDataMap(insts);
		Map<Integer, List<InstInnerRatingInfo>> instIssuersDataMap = instInnerRateAndInvAdviceService.getInstHistIssuersDataMap(insts);
		
		while (iterator.hasNext()) {
			BondCcxeBasicInfo bondCcxeInfo = iterator.next();
			BondBasicInfo oBasicInfo = new BondBasicInfo();
			saveBondInfo2Mongo(bondCcxeInfo, oBasicInfo, insts, instBondsDataMap, instIssuersDataMap);
			if (oBasicInfo.getBondUniCode() != null)
				oBasicInfos.add(oBasicInfo);
		}
		
		{
			StopWatch watch = new StopWatch();
			watch.start();
			boolean isBatchSuccess = true;
			try {
				basicInfoRepositoryDm.save(oBasicInfos); 
			}
			catch (Exception e) {
				e.printStackTrace();
				LOG.error("failed to batch save, ", e);
				isBatchSuccess = false;
			}
			if (!isBatchSuccess) {
				for(BondBasicInfo i : oBasicInfos) {
					try {
						basicInfoRepositoryDm.save(i);
					}
					catch (Exception e) {
						e.printStackTrace();	
						LOG.error("failed to save bond:" +  i.getBondShortName() + ", com:" + i.getIssName(), e );
					}
				}
			}
			watch.stop();
			LOG.info( "syncronized to t_bond_basic_info, batch size:" + oBasicInfos.size() + ", batch elapsed:" + watch.getTotalTimeMillis() + ", Total Count:" + totalProcessedBond.addAndGet(oBasicInfos.size()) + 
					", Total Elapsed:" + (System.currentTimeMillis() - processStartTime));
		}
		
	}
	
	@NoLogging
    public void saveBondInfo2DB(List<BondCcxeBasicInfo> bondBasics) {
        List<BondBasicInfo> oBasicInfos = new ArrayList<BondBasicInfo>();
        Iterator<BondCcxeBasicInfo> iterator = bondBasics.iterator();
        
        List<Integer> insts = this.insts();
        Map<Integer, List<InstInvestmentAdviceInfo>> instBondsDataMap = instInnerRateAndInvAdviceService.getInstHistBondsDataMap(insts);
        Map<Integer, List<InstInnerRatingInfo>> instIssuersDataMap = instInnerRateAndInvAdviceService.getInstHistIssuersDataMap(insts);
        
        while (iterator.hasNext()) {
            BondCcxeBasicInfo bondCcxeInfo = iterator.next();
            BondBasicInfo oBasicInfo = new BondBasicInfo();
            saveBondInfo2Mongo(bondCcxeInfo, oBasicInfo, insts, instBondsDataMap, instIssuersDataMap);
            if (oBasicInfo.getBondUniCode() != null)
                oBasicInfos.add(oBasicInfo);
        }
        // TODO bondCcxe2BondDmdb
        {
            StopWatch watch = new StopWatch();
            watch.start();
            boolean isBatchSuccess = true;
            try {
                basicInfoRepositoryDm.save(oBasicInfos); 
            }
            catch (Exception e) {
                e.printStackTrace();
                LOG.error("failed to batch save, ", e);
                isBatchSuccess = false;
            }
            if (!isBatchSuccess) {
                for(BondBasicInfo i : oBasicInfos) {
                    try {
                        basicInfoRepositoryDm.save(i);
                    }
                    catch (Exception e) {
                        e.printStackTrace();    
                        LOG.error("failed to save dmdb bond:" +  i.getBondShortName() + ", com:" + i.getIssName(), e );
                    }
                }
            }
            watch.stop();
            LOG.info( "syncronized to t_bond_basic_info_new, batch size:" + oBasicInfos.size() + ", batch elapsed:" + watch.getTotalTimeMillis() + ", Total Count:" + totalProcessedBond.addAndGet(oBasicInfos.size()) + 
                    ", Total Elapsed:" + (System.currentTimeMillis() - processStartTime));
        }
        
    }
	
	/**
	 * 查询所有机构ID
	 * @return
	 */
	public List<Integer> insts(){
		final String sql = "SELECT inst_id FROM institution.t_bond_inst_indu GROUP BY inst_id";
		List<Integer> insts = jdbcTemplate.queryForList(sql, Integer.class);
		return insts;
	}

	
	
	/**
	 * @param bondCcxeInfo
	 */
	@NoLogging
	public void saveBondInfo2Mongo(BondCcxeBasicInfo bondCcxeInfo, BondBasicInfo oBasicInfo, 
			List<Integer> insts, Map<Integer, List<InstInvestmentAdviceInfo>> instBondsDataMap, Map<Integer, List<InstInnerRatingInfo>> instIssuersDataMap) {

        StopWatch watch = new StopWatch();
        watch.start();
		try {
			Long comUniCode = bondCcxeInfo.getComUniCode();
			if (comUniCode == null) {
				LOG.error("com_uni_code 为 null, 跳过该记录:" + bondCcxeInfo.toString() );
				return;
			}

			BondComInfoDoc comInfoDoc = comDataService.get(comUniCode);
			if (comInfoDoc == null) {
				LOG.warn("公司信息不存在: " + bondCcxeInfo.toString() );
			}

			if (bondCcxeInfo.getSecMarPar()!=null && bondCcxeInfo.getSecMarPar() == 5) {
				LOG.warn("排除一级市场债:" + bondCcxeInfo.toString() );
				return;
			}

			//tenor 
			String tenor = null;
			Long tenorDays = null;
			String exerPayDateStr = bondCcxeInfo.getExerPayDate();

			//2. 期限=剩余期限（+行权剩余期限），≥1年的以Y结尾，＜1年的以D显示，若债券当天到期，则显示为0D。（筛选只针对剩余期限）
			//	如16吴中经发MTN001，兑付日2021/09/02，行权兑付日2023/09/02，则期限显示为5Y+2Y。
			Date matuPayDate =  bondCcxeInfo.getMatuPayDate();
			// （1）剩余期限：兑付日-今天。
			if (matuPayDate != null) {
			    tenorDays = getDifferenceDays(new Date(), matuPayDate, bondCcxeInfo.getBondUniCode());
			    if (tenorDays < 0) {
			        tenor = "已到期";
			    }
			    else { 
			        //行权兑付日
			        Date exerPayDate = null;
			        if (exerPayDateStr != null) { 
			            final List<DateFormat> formatters = new ArrayList<>(Arrays.asList(
			                    new SimpleDateFormat("yyyy-MM-dd"), 
			                    new SimpleDateFormat("yyyy年MM月dd日"),
			                    new SimpleDateFormat("yyyy年MM月dd号")));
			            for(DateFormat formatter : formatters) {
			                try {
			                    exerPayDate = (Date) formatter.parse(exerPayDateStr);
			                } catch (java.text.ParseException e) {//nothing
			                    continue;
			                }
			                break;
			            }
			            if (exerPayDate != null) {
	                        Long exerDist = getDifferenceDays(new Date(), exerPayDate, bondCcxeInfo.getBondUniCode());
	                        if (exerDist >= 0 ) {
	                            tenor = formatTenorDays(exerDist, true);
	                            if (StringUtils.isEmpty(tenor)) {
	                                //已经过了行权日，但还没有到到期日, 直接显示剩余时间
	                                tenor =  formatTenorDays(getDifferenceDays(exerPayDate, matuPayDate, bondCcxeInfo.getBondUniCode()), true);
	                            }
	                            else {
	                              //没有到行权日(XX+XX), 包括了行权日当天0D
	                              tenor +=  "+" + formatTenorDays(getDifferenceDays(exerPayDate, matuPayDate, bondCcxeInfo.getBondUniCode()), true);
	                            }
	                        }
	                        else {
	                            //不可能到达的情况, 外面已经去除了到期的情况
	                            tenor = formatTenorDays(tenorDays, true);
	                        }
	                    }
	                    else {
	                      //没有到到期日，而且行权日存在且无法解析，那么就是XX+N
	                      tenor =  formatTenorDays(tenorDays, true) + "+N";
	                    }
			        }
			        else {
			            //没有行权，直接显示剩余时间
			            tenor =  formatTenorDays(tenorDays, true);
			        }
                    LOG.info("exerPayDateStr:" + exerPayDateStr + ", exerPayDate:" + exerPayDate + ", matuPayDate:" + matuPayDate 
                            + ", tenorDays:" + tenorDays + ", tenor:" + tenor );      
			    }
			}
			if (StringUtils.isEmpty(tenor)) {
			    tenor = null;
			}
			//rating
			String issCredLevel = (comInfoDoc == null)? "--":replaceEmptyCred(comInfoDoc.getIssCredLevel()) ;
			bondCcxeInfo.setIssCredLevel(issCredLevel);
			String bondCredLevel = replaceEmptyCred(bondCcxeInfo.getBondCredLevel());
			bondCcxeInfo.setNewRate(bondCredLevel);

			String issBondRating = issCredLevel + "/" + bondCredLevel;
			if (issBondRating.equalsIgnoreCase("--/--")) {
				issBondRating = "--";
			}

			//mulInvest
			Boolean isMulInvest = false;
			if(bondCcxeInfo.getIsTypePar() != null) 
				isMulInvest = (bondCcxeInfo.getIsTypePar() == 1);

			//code 
			String code = null;
			if (bondCcxeInfo.getBondCode() != null) {
				code = bondCcxeInfo.getBondCode() +
						UIAdapter.cvtSecMar2Postfix(bondCcxeInfo.getSecMarPar());
			}

			//dmBondType 
			BondTypeXRef dmBondTypeXRef = null;
			Integer bondTypePar = bondCcxeInfo.getBondTypePar();
			if(bondTypePar != null) {
				dmBondTypeXRef = bondTypeCache.get(bondTypePar);
			}
			
			
			Double newSize = new BigDecimal(bondCcxeInfo.getNewSize()/10000.00).setScale(4, RoundingMode.HALF_EVEN).doubleValue();
			
			BondDetailDoc detail = makeBondDetail(bondCcxeInfo, tenor, tenorDays, exerPayDateStr, issCredLevel, bondCredLevel, issBondRating,
					isMulInvest, code, comInfoDoc, dmBondTypeXRef, newSize);
			instInnerRateAndInvAdviceService.makeBondDetailWithInstDataMap(detail, instBondsDataMap, instIssuersDataMap);
			
	        if (detail != null) {
	        	BondDetailVO vo = new BondDetailVO();
	        	BeanUtils.copyProperties(detail,vo);
	        	
	        	Update update = new Update();
	        	getObjectValue(vo,update);
				update.set("updateTime", null);//重置今日报价
				
				if (comInfoDoc != null) {
					Map<String,Object> map = comInfoDoc.getInstitutionInduMap();
					update.set("institutionInduMap", map);
				}
				
				update.set("instRating", null);
				update.set("instInvestmentAdvice", null);
	        	mongoOperations.upsert(new Query(Criteria.where("_id").is(detail.getBondUniCode())),
	        			update,BondDetailDoc.class);
	        	
	            //pub 已经过期的BondDetailDoc
    			if ((null != detail.getCurrStatus() && detail.getCurrStatus().intValue() == 1)
    					&& (null != detail.getIssStaPar() && detail.getIssStaPar().intValue() == 1)) {
    				//未过期
    			} else {
    				//过期
    				publisher.publishEvent(detail);
    			}
	        }
	        
			BondBasicInfoDoc basicInfo = makeBasicInfo(bondCcxeInfo, tenor, tenorDays, issBondRating, isMulInvest, code, comInfoDoc, dmBondTypeXRef, newSize);
			instInnerRateAndInvAdviceService.makeBondBasicInfolWithInstDataMap(basicInfo, instBondsDataMap, instIssuersDataMap);
			
			if (basicInfo != null ) {
				if (comInfoDoc != null) {
					basicInfo.setInstitutionInduMap(comInfoDoc.getInstitutionInduMap());
					//风险警告标志
		            if (comInfoDoc.getPdNum() != null) {
		            	basicInfo.setRiskWarning(comInfoDoc.getPdNum() >= BondBasicInfoClassOneDoc.RISK_WARNING_PD_THRESHOLD);
		            }
				}
				basicInfolRepository.insert(basicInfo);
			}
			
			buildBondBasicInfo(bondCcxeInfo, comInfoDoc, oBasicInfo);
		}
		catch (Exception ex) {
			LOG.error("处理债券信息发生异常:" + bondCcxeInfo.toString(), ex);
			ex.printStackTrace();
		}
		watch.stop();
		LOG.info( bondCcxeInfo.getBondShortName() + " imported. ");
	}
	
	public void bondCcxe2BondDmdb(BondCcxeBasicInfo bondCcxeInfo, BondBasicInfo dmdbBasicInfo//,
	        //List<Integer> insts, 
	        //Map<Integer, List<InstInvestmentAdviceInfo>> instBondsDataMap, Map<Integer, List<InstInnerRatingInfo>> instIssuersDataMap
	        ) {

        StopWatch watch = new StopWatch();
        watch.start();
        try {
            Long comUniCode = bondCcxeInfo.getComUniCode();
            if (comUniCode == null) {
                LOG.error("com_uni_code 为 null, 跳过该记录:" + bondCcxeInfo.toString() );
                return;
            }

            BondComInfoDoc comInfoDoc = comDataService.get(comUniCode);
            if (comInfoDoc == null) {
                LOG.warn("公司信息不存在: " + bondCcxeInfo.toString() );
            }

            if (bondCcxeInfo.getSecMarPar()!=null && bondCcxeInfo.getSecMarPar() == 5) {
                LOG.warn("排除一级市场债:" + bondCcxeInfo.toString() );
                return;
            }

            //tenor 
            String tenor = null;
            Long tenorDays = null;
            String exerPayDateStr = bondCcxeInfo.getExerPayDate();

            //2. 期限=剩余期限（+行权剩余期限），≥1年的以Y结尾，＜1年的以D显示，若债券当天到期，则显示为0D。（筛选只针对剩余期限）
            //  如16吴中经发MTN001，兑付日2021/09/02，行权兑付日2023/09/02，则期限显示为5Y+2Y。
            Date matuPayDate =  bondCcxeInfo.getMatuPayDate();
            // （1）剩余期限：兑付日-今天。
            if (matuPayDate != null) {
                tenorDays = getDifferenceDays(new Date(), matuPayDate, bondCcxeInfo.getBondUniCode());
                if (tenorDays < 0) {
                    tenor = "已到期";
                }
                else { 
                    //行权兑付日
                    Date exerPayDate = null;
                    if (exerPayDateStr != null) { 
                        final List<DateFormat> formatters = new ArrayList<>(Arrays.asList(
                                new SimpleDateFormat("yyyy-MM-dd"), 
                                new SimpleDateFormat("yyyy年MM月dd日"),
                                new SimpleDateFormat("yyyy年MM月dd号")));
                        for(DateFormat formatter : formatters) {
                            try {
                                exerPayDate = (Date) formatter.parse(exerPayDateStr);
                            } catch (java.text.ParseException e) {//nothing
                                continue;
                            }
                            break;
                        }
                        if (exerPayDate != null) {
                            Long exerDist = getDifferenceDays(new Date(), exerPayDate, bondCcxeInfo.getBondUniCode());
                            if (exerDist >= 0 ) {
                                tenor = formatTenorDays(exerDist, true);
                                if (StringUtils.isEmpty(tenor)) {
                                    //已经过了行权日，但还没有到到期日, 直接显示剩余时间
                                    tenor =  formatTenorDays(getDifferenceDays(exerPayDate, matuPayDate, bondCcxeInfo.getBondUniCode()), true);
                                }
                                else {
                                  //没有到行权日(XX+XX), 包括了行权日当天0D
                                  tenor +=  "+" + formatTenorDays(getDifferenceDays(exerPayDate, matuPayDate, bondCcxeInfo.getBondUniCode()), true);
                                }
                            }
                            else {
                                //不可能到达的情况, 外面已经去除了到期的情况
                                tenor = formatTenorDays(tenorDays, true);
                            }
                        }
                        else {
                          //没有到到期日，而且行权日存在且无法解析，那么就是XX+N
                          tenor =  formatTenorDays(tenorDays, true) + "+N";
                        }
                    }
                    else {
                        //没有行权，直接显示剩余时间
                        tenor =  formatTenorDays(tenorDays, true);
                    }
                    LOG.info("exerPayDateStr:" + exerPayDateStr + ", exerPayDate:" + exerPayDate + ", matuPayDate:" + matuPayDate 
                            + ", tenorDays:" + tenorDays + ", tenor:" + tenor );      
                }
            }
            if (StringUtils.isEmpty(tenor)) {
                tenor = null;
            }
            dmdbBasicInfo.setTenor(tenor);
            //rating
            String issCredLevel = (comInfoDoc == null)? "--":replaceEmptyCred(comInfoDoc.getIssCredLevel()) ;
            bondCcxeInfo.setIssCredLevel(issCredLevel);
            String bondCredLevel = replaceEmptyCred(bondCcxeInfo.getBondCredLevel());
            bondCcxeInfo.setNewRate(bondCredLevel);

//            String issBondRating = issCredLevel + "/" + bondCredLevel;
//            if (issBondRating.equalsIgnoreCase("--/--")) {
//                issBondRating = "--";
//            }
//
//            //mulInvest
//            Boolean isMulInvest = false;
//            if(bondCcxeInfo.getIsTypePar() != null) 
//                isMulInvest = (bondCcxeInfo.getIsTypePar() == 1);
//
//            //code 
//            String code = null;
//            if (bondCcxeInfo.getBondCode() != null) {
//                code = bondCcxeInfo.getBondCode() +
//                        UIAdapter.cvtSecMar2Postfix(bondCcxeInfo.getSecMarPar());
//            }

            //dmBondType 
//            BondTypeXRef dmBondTypeXRef = null;
//            Integer bondTypePar = bondCcxeInfo.getBondTypePar();
//            if(bondTypePar != null) {
//                dmBondTypeXRef = bondTypeCache.get(bondTypePar);
//            }
            
            
//            Double newSize = new BigDecimal(bondCcxeInfo.getNewSize()/10000.00).setScale(4, RoundingMode.HALF_EVEN).doubleValue();
            
//            BondDetailDoc detail = makeBondDetail(bondCcxeInfo, tenor, tenorDays, exerPayDateStr, issCredLevel, bondCredLevel, issBondRating,
//                    isMulInvest, code, comInfoDoc, dmBondTypeXRef, newSize);
//            instInnerRateAndInvAdviceService.makeBondDetailWithInstDataMap(detail, instBondsDataMap, instIssuersDataMap);
            
//            if (detail != null) {
//                BondDetailVO vo = new BondDetailVO();
//                BeanUtils.copyProperties(detail,vo);
//                
//                Update update = new Update();
//                getObjectValue(vo,update);
//                update.set("updateTime", null);//重置今日报价
//                
//                if (comInfoDoc != null) {
//                    Map<String,Object> map = comInfoDoc.getInstitutionInduMap();
//                    update.set("institutionInduMap", map);
//                }
//                
//                update.set("instRating", null);
//                update.set("instInvestmentAdvice", null);
//                mongoOperations.upsert(new Query(Criteria.where("_id").is(detail.getBondUniCode())),
//                        update,BondDetailDoc.class);
//                
//                //pub 已经过期的BondDetailDoc
//                if ((null != detail.getCurrStatus() && detail.getCurrStatus().intValue() == 1)
//                        && (null != detail.getIssStaPar() && detail.getIssStaPar().intValue() == 1)) {
//                    //未过期
//                } else {
//                    //过期
//                    publisher.publishEvent(detail);
//                }
//            }
            
//            BondBasicInfoDoc basicInfo = makeBasicInfo(bondCcxeInfo, tenor, tenorDays, issBondRating, isMulInvest, code, comInfoDoc, dmBondTypeXRef, newSize);
//            instInnerRateAndInvAdviceService.makeBondBasicInfolWithInstDataMap(basicInfo, instBondsDataMap, instIssuersDataMap);
            
//            if (basicInfo != null ) {
//                if (comInfoDoc != null) {
//                    basicInfo.setInstitutionInduMap(comInfoDoc.getInstitutionInduMap());
//                    //风险警告标志
//                    if (comInfoDoc.getPdNum() != null) {
//                        basicInfo.setRiskWarning(comInfoDoc.getPdNum() >= BondBasicInfoClassOneDoc.RISK_WARNING_PD_THRESHOLD);
//                    }
//                }
//                basicInfolRepository.insert(basicInfo);
//            }
            
            buildBondBasicInfo(bondCcxeInfo, comInfoDoc, dmdbBasicInfo);
        }
        catch (Exception ex) {
            LOG.error("bondCcxe2BondDmdb 处理债券信息发生异常:" + bondCcxeInfo.toString(), ex);
            ex.printStackTrace();
        }
        watch.stop();
//        LOG.info( bondCcxeInfo.getBondShortName() + " imported. ");
        //LOG.info( bondCcxeInfo.getBondShortName() + " imported, " + " Elapsed:" + watch.getTotalTimeMillis() + ", Total Count:" + totalProcessedBond.incrementAndGet() + ", Total Elapsed:" + (System.currentTimeMillis() - processStartTime));
        //doc.setAreaUniCode(basicInfo.get));
    }


	private BondBasicInfo buildBondBasicInfo(BondCcxeBasicInfo src, BondComInfoDoc comInfoDoc, BondBasicInfo basic) {

		Date currentTime = new Date();
		basic.setCreateTime(currentTime);

		//设置公司信息
		BeanUtil.copyProperties(src, basic);
		Integer matuUnitPar = src.getMatuUnitPar();//单位：年(1) 月(2) 日(3)
		if(null != matuUnitPar) {
			BigDecimal bondMatu = src.getBondMatu();
			if(null != bondMatu) {
				bondMatu = bondMatu.divide(new BigDecimal(1), 0, BigDecimal.ROUND_DOWN);
				if(1 == matuUnitPar) {
					basic.setBondMatu(bondMatu+"Y");
				} else if(2 == matuUnitPar) {
					basic.setBondMatu(bondMatu+"M");
				} else if(3 == matuUnitPar) {
					basic.setBondMatu(bondMatu+"D");
				}
			} else {
				basic.setBondMatu(null);
			}
		} else {
			basic.setBondMatu(null);
		}
		//设置发行方式,因为新债IssCls不够
		basic.setIssClsDes(basic.getIssCls());
		basic.setIssCls(null);
		//设置债券期限
		//放着copyProperties后面，因为src里的issname可能为空
		if(comInfoDoc != null) {
			basic.setComUniCode(comInfoDoc.getComUniCode());
			basic.setIssName(comInfoDoc.getComChiName());
		}
		
		basic.setIssStatus("1");
		basic.setPushStatus("0");
		basic.setEditStatus("0");
		basic.setIsNew("0");
		basic.setLastUpdateTime(new Date());
		basic.setCreateUser(1L);
		return basic;
	}

	private BondDetailDoc makeBondDetail(BondCcxeBasicInfo basicInfo, 
			String tenor, 
			Long tenorDays,
			String exerPayDateStr,
			String issCredLevel, 
			String bondCredLevel, 
			String issBondRating, 
			Boolean isMulInvest, 
			String code, 
			BondComInfoDoc comInfoDoc, 
			BondTypeXRef dmBondTypeXRef, Double newSize) {

		Date currentTime = new Date();
		BondDetailDoc detail = new BondDetailDoc();
		detail.setCreateTime(currentTime);

		//设置公司信息
		if (comInfoDoc != null) {
			detail.setComUniCode(comInfoDoc.getComUniCode());
			detail.setComName(comInfoDoc.getComChiName());
			detail.setInduId(comInfoDoc.getInduId());
			detail.setInduName(comInfoDoc.getInduName());
			detail.setInduIdSw(comInfoDoc.getInduIdSw());
			detail.setInduNameSw(comInfoDoc.getInduNameSw());
			
			Boolean isInterestBond = dmBondTypeXRef != null && dmBondTypeXRef.getDmFilterCode() != null && ArrayUtils.contains( 
			        new Integer[] {3,  4, 5, 6}, dmBondTypeXRef.getDmFilterCode());
			
			//TODO: 
			//利率债:1 信用债:2
			comInfoDoc.setBondType(isInterestBond? 1: 2);
			
			//利率债没有违约概率
			if (!isInterestBond) {
				if (comInfoDoc.getPd() != null) {
					detail.setPd(comInfoDoc.getPd());
					detail.setPdNum(comInfoDoc.getPdNum());
					detail.setPdUiOpt(PdOpt.fromPdNum(comInfoDoc.getPdNum()).code());
					detail.setPdTime(comInfoDoc.getPdTime());
					detail.setPdSortRRs(rrsService.findSortRrsByComUniCode(comInfoDoc.getComUniCode()));
				}
				if (comInfoDoc.getPdDiff() != null) {
					detail.setPdDiff(comInfoDoc.getPdDiff());
				}
				if (comInfoDoc.getDefaultDate() != null) {
					//TODO (next release)
					//detail.setDefaultDate(comInfoDoc.getDefaultDate());
					//detail.setPd(UIAdapter.DEFAULT_TEXT);
					//detail.setPdUiOpt(PdOpt.E_DEFAULT.code());
				}
				if (comInfoDoc.getDefaultEvent() != null) {
					//detail.setDefaultEvent(comInfoDoc.getDefaultEvent());
				}
				if (comInfoDoc.getWorstPd() != null) {
					detail.setWorstPd(comInfoDoc.getWorstPd());
					detail.setWorstPdNum(comInfoDoc.getWorstPdNum());
					detail.setWorstPdTime(comInfoDoc.getWorstPdTime());
				}
			}

			detail.setIssRatingUiOpt(UIAdapter.cvtCred2UIOpt(issCredLevel));
			detail.setOwnerType(UIAdapter.cvtComAttr2UIOpt(comInfoDoc.getComAttrPar()));
			detail.setRegion(comInfoDoc.getAreaUniCode1());
			detail.setIssRating(replaceNull(comInfoDoc.getIssCredLevel()));
			detail.setPoNegtive(comInfoDoc.getSentimentNegative());
			detail.setPoNeutral(comInfoDoc.getSentimentNeutral());
			detail.setPoPositive(comInfoDoc.getSentimentPositive());
			detail.setSentimentMonthCount(comInfoDoc.getSentimentMonthCount());
		}

		//设置债券信息
		//detail.setUpdateTime(currentTime);
		detail.setBondUniCode(basicInfo.getBondUniCode());
		detail.setName(basicInfo.getBondShortName());
		detail.setTenor(tenor);
		detail.setTenorDays(tenorDays);
		detail.setExerPayDate(exerPayDateStr);
		detail.setTenorUiOpt(UIAdapter.cvtTenor2StrictOpt2(tenorDays));
		detail.setCode(code);
		if (basicInfo.getNewCoupRate() != null) {
			detail.setCoupRate(basicInfo.getNewCoupRate().doubleValue());
		}
		detail.setIssBondRating(issBondRating);
		detail.setBondRating(replaceNull(basicInfo.getBondCredLevel()));
		if (dmBondTypeXRef != null) {
			detail.setDmBondType(dmBondTypeXRef.getDmFilterCode());
		}
		detail.setMarket(UIAdapter.cvtSecMar2UIOpt(basicInfo.getSecMarPar()));
		detail.setPledgeCode(basicInfo.getPledgeCode());
		detail.setIsCrossMar(basicInfo.getIsCrosMarPar());
		detail.setBondRatingUiOpt(UIAdapter.cvtCred2UIOpt(bondCredLevel));
		detail.setMunInvest(isMulInvest);
		detail.setYearPayDate(basicInfo.getYearPayDate());
		detail.setLatelyPayDate(getLatelyYearPayDate(basicInfo.getYearPayDate()));
		detail.setTheoEndDate(basicInfo.getTheoEndDate());
		detail.setNewSize(newSize);
		detail.setIssStartDate(basicInfo.getIssStartDate());  //设置债券发行日期
		
		// 地区信息
		BondDetailDoc doc = bondCityService.isBondCity(comInfoDoc.getComUniCode(), 2);
		if (doc != null) {
			detail.setAreaCode1(doc.getAreaCode1());
			detail.setAreaCode2(doc.getAreaCode2());
			detail.setAreaName1(doc.getAreaName1());
			detail.setAreaName2(doc.getAreaName2());
		}

		detail.setCurrStatus(basicInfo.getCurrStatus());
		detail.setIssStaPar(basicInfo.getIssStaPar());
		detail.setListStaPar(basicInfo.getListStaPar());
		detail.setListPar(basicInfo.getListPar().equals(1));

		//估值
		if(!StringUtils.isEmpty(basicInfo.getFairValue())){
			detail.setFairValue(basicInfo.getFairValue().setScale(2,BigDecimal.ROUND_HALF_UP));
			detail.setEstYield(detail.getFairValue().doubleValue());
		}
		detail.setOptionYield(basicInfo.getOptionYield());
		detail.setEstCleanPrice(basicInfo.getEstCleanPrice());
		detail.setModd(basicInfo.getModd());
		detail.setMacd(basicInfo.getMacd());
		detail.setConvexity(basicInfo.getConvexity());
		detail.setStaticSpread(basicInfo.getStaticSpread());
		detail.setConvRatio(basicInfo.getConvRatio());
		
		//隐含评级
		detail.setImpliedRating(basicInfo.getImpliedRating());
		detail.setImpliedRatingUiOpt(UIAdapter.cvtCred2UIOpt(basicInfo.getImpliedRating()));

        //得到当前债劵昨日成交信息
		PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "createTime"));
		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(basicInfo.getBondUniCode()));
		query.with(request);
		BondDealDataDoc dealdata = mongoOperations.findOne(query, BondDealDataDoc.class);
		if(dealdata!=null){
		    //昨日加权
		    detail.setBondAddRate(dealdata.getBondAddRate());
		    //昨日成交量
		    if (dealdata.getBondTradingVolume() != null)
		        detail.setBondTradingVolume(dealdata.getBondTradingVolume().multiply(new BigDecimal(10000)));
		}

		return detail;
	}

	private BondArea bondArea(Long comUniCode){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.*,e.city_type AS cityType,q.area_name1 AS areaName1,q.area_name2 AS areaName2,q.area_uni_code AS areaUniCode");
		sb.append("\n\t,q.sub_uni_code AS subUniCode FROM (");
		sb.append("\n\tSELECT c.COM_CHI_NAME AS comName,c.COM_UNI_CODE comUniCode,");
		sb.append("\n\tIFNULL(e2.area_uni_code,IFNULL(e1.area_uni_code,e0.area_uni_code))  AS area_uni_code2");
		sb.append("\n\t,CASE WHEN b.comp_id IS NOT NULL THEN 1 ELSE 0 END AS munInvest ");
		sb.append("\n\tFROM dmdb.t_bond_com_ext a");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_city_annex b");
		sb.append("\n\tON a.ama_com_id = b.comp_id");
		sb.append("\n\tLEFT JOIN bond_ccxe.d_pub_com_info_2 c ON a.com_uni_code = c.com_uni_code");
		sb.append("\n\tLEFT JOIN bond_ccxe.pub_area_code d ON d.area_uni_code = c.AREA_UNI_CODE");
		sb.append("\n\tLEFT JOIN bond_ccxe.pub_area_code d1 ON d1.area_uni_code = d.sub_uni_code");
		sb.append("\n\tLEFT JOIN bond_ccxe.pub_area_code d2 ON d2.area_uni_code = d1.sub_uni_code");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_area e2  ON d.area_uni_code = e2.area_uni_code");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_area e1  ON d1.area_uni_code = e1.area_uni_code");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_area e0  ON d2.area_uni_code = e0.area_uni_code");
		sb.append("\n\tWHERE a.com_uni_code = " + comUniCode);
		sb.append("\n\t) t ");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_city_type e ON e.area_uni_code = t.area_uni_code2");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_area q ON q.area_uni_code =  t.area_uni_code2 AND q.sub_uni_code IS NOT NULL");
		sb.append("\n\tGROUP BY area_uni_code2");
		sb.append("\n\tLIMIT 1");
		BondArea vo = null;
		try{
			vo = jdbcTemplate.queryForObject(sb.toString(),
					new BeanPropertyRowMapper<BondArea>(BondArea.class));
		}catch(Exception e){
			LOG.info("找不到区域数据!comUniCode:" + comUniCode);
		}
		return vo;
	}

	private BondBasicInfoDoc makeBasicInfo(BondCcxeBasicInfo basicInfo, 
			String tenor, 
			Long tenorDays,
			String issBondRating,
			Boolean isMulInvest, 
			String code, 
			BondComInfoDoc comInfoDoc, 
			BondTypeXRef dmBondTypeXRef, Double newSize) {

		Date currentTime = new Date();
		BondBasicInfoDoc basic = new BondBasicInfoDoc();
		basic.setCreateTime(currentTime);

		//设置公司信息
		if(comInfoDoc != null) {
			basic.setIssuerId(comInfoDoc.getComUniCode());
			basic.setIssuer(comInfoDoc.getComChiName());
			basic.setInduId(comInfoDoc.getInduId());
			basic.setInduName(comInfoDoc.getInduName());
			basic.setInduIdSw(comInfoDoc.getInduIdSw());
			basic.setInduNameSw(comInfoDoc.getInduNameSw());
			basic.setComAttrPar(comInfoDoc.getComAttrPar());
			basic.setComPro(basicInfo.getComPro());
			basic.setMainBus(basicInfo.getMainBus());
			basic.setSidBus(basicInfo.getSidBus());
			if (dmBondTypeXRef != null && dmBondTypeXRef.getDmFilterCode() != null && 
					!ArrayUtils.contains( new Integer[] {3,  4, 5, 6}, dmBondTypeXRef.getDmFilterCode())) {
				basic.setPd(comInfoDoc.getPd());
			}
			basic.setComAttrPar(comInfoDoc.getComAttrPar());
			basic.setIssRatePros(comInfoDoc.getRateProsPar());
		}

		//设置债券信息
		//basic.setUpdateTime(currentTime);
		basic.setBondUniCode(basicInfo.getBondUniCode());
		basic.setCode(code);
		basic.setBaseRate(cvt.convert(CcxeParCache.SysParEnum.BASERATE.code(), basicInfo.getBaseRatePar()));
		basic.setGuruName(basicInfo.getGuraName());
		basic.setIntePayCls(cvt.convert(CcxeParCache.SysParEnum.INTERPAYCLAS.code(), 
				basicInfo.getIntePayClsPar()));
		basic.setIntePayFreq(basicInfo.getInteParFreq());
		basic.setIssBondRating(issBondRating);
		basic.setIssCoupRate(basicInfo.getIssCoupRate());
		basic.setInteStartDate(basicInfo.getInteStartDate());
		basic.setMarket(cvt.convert(CcxeParCache.SysParEnum.SECMAR.code(), 
				basicInfo.getSecMarPar()));
		if (dmBondTypeXRef != null) {
			basic.setDmBondType(dmBondTypeXRef.getDmFilterCode());
			basic.setDmBondTypeName(dmBondTypeXRef.getDmInfoName());
		}
		//pub_par par_sys_code 3096, 1表示城投债
		basic.setMunInvest(isMulInvest);
		basic.setNewCoupRate(basicInfo.getNewCoupRate());
		basic.setPledgeCode(basicInfo.getPledgeCode());
		basic.setPledgeName(basicInfo.getPledgeName());
		basic.setRatePros(cvt.convert(CcxeParCache.SysParEnum.RATEPROS.code(), 
				basicInfo.getRatePros()));
		
		basic.setRateType(cvt.convert(CcxeParCache.SysParEnum.RATETYPE.code(),
				basicInfo.getRateTypePar()));
		basic.setShortName(basicInfo.getBondShortName());
		basic.setFullName(basicInfo.getBondFullName());
		basic.setTenor(tenor);
		basic.setTenorDays(tenorDays);
		basic.setTheoEndDate(basicInfo.getTheoEndDate());
		basic.setYearPayDate(basicInfo.getYearPayDate());
		basic.setOrgCode(basicInfo.getBondCode());
		basic.setCurrStatus(basicInfo.getCurrStatus());
		basic.setIssStaPar(basicInfo.getIssStaPar());
		basic.setListStaPar(basicInfo.getListStaPar());
		basic.setNewSize(newSize);
		basic.setGuraName1(basicInfo.getGuraName1());
		basic.setIsRedemPar(basicInfo.getIsRedemPar());
		basic.setIsResaPar(basicInfo.getIsResaPar());
		basic.setExerPayDate(basicInfo.getExerPayDate());
		basic.setIssCls(basicInfo.getIssCls());
		basic.setBondCredLevel(basicInfo.getBondCredLevel());
		basic.setBondRateWritDate(basicInfo.getBondRateWritDate());
		basic.setBondRateOrgName(basicInfo.getBondRateOrgName());
		
		//估值
		if(!StringUtils.isEmpty(basicInfo.getFairValue())){
			basic.setFairValue(basicInfo.getFairValue().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		//隐含评级
		basic.setImpliedRating(basicInfo.getImpliedRating());
		basic.setParVal(basicInfo.getBondParVal());
		basic.setIssPri(basicInfo.getIssPri());
		if (newSize != null && basicInfo.getPayAmount() != null)
			basic.setPayAmount(newSize*basicInfo.getPayAmount()/100);
		return basic;
	}
	
	public static void main(String[] args) throws ParseException {
		Double newDay = (double) 456/365;
		
		System.out.println(newDay);
		
		java.text.DecimalFormat  df  =new  java.text.DecimalFormat("#.00");  
		String s = df.format(newDay) + "Y";
		System.out.println(s);
		
		float value = (new BigDecimal(newDay).setScale(
				2, BigDecimal.ROUND_DOWN)).floatValue();
		System.out.println(value);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		
		Long str = sdf.parse("02-20").getTime();
		
		System.out.println(str);
		
		System.out.println(sdf.format(new Date(str)));
		
		String sd = "2013年7月23日(如遇法定节假日或休息日,则顺延至其后的第1个工作日)。";
		final List<DateFormat> formatters = new ArrayList<>(Arrays.asList(
                new SimpleDateFormat("yyyy-MM-dd"), 
                new SimpleDateFormat("yyyy年MM月dd日"),
                new SimpleDateFormat("yyyy年MM月dd号")));
        for(DateFormat formatter : formatters) {
            try {
            	Date exerPayDate = (Date) formatter.parse(sd);
            	System.out.println("df=" + exerPayDate);
            } catch (java.text.ParseException e) {//nothing
                continue;
            }
            break;
        }
		
	}
	
	/**
	 * 取最近付息日
	 * @return
	 * @throws ParseException 
	 */
	private String getLatelyYearPayDate(String yearPayDate){
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		
		if(StringUtils.isEmpty(yearPayDate) ||  yearPayDate.indexOf(",")==-1)
			return yearPayDate;
		
		Calendar calendar = Calendar.getInstance();
		String str = null;
		String[] yearPayDates = yearPayDate.split(",");
		
		List<Long> dates = new ArrayList<Long>();
		List<Long> olddates = new ArrayList<Long>();
		
		try {
			Long nowDate = sdf.parse((calendar.get(Calendar.MONTH) + 1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)).getTime();
			for(int i=0;i<yearPayDates.length;i++){
				Long time  = sdf.parse(yearPayDates[i]).getTime();
				if(time>=nowDate){
					dates.add(time);
				}else{
					olddates.add(time);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Long[] arr = null;
		
		if(dates.size()>1)
			arr = (Long[])dates.toArray(new Long[dates.size()]);
		else
			arr = (Long[])olddates.toArray(new Long[olddates.size()]);
		
		Arrays.sort(arr);
		str = sdf.format(new Date(arr[0]));
		
		return str;
		
	}
	
	public static void getObjectValue(Object object, Update update) throws Exception {
		if (object != null) {
			// 拿到该类
			Class<?> clz = object.getClass();
			// 获取实体类的所有属性，返回Field数组
			Field[] fields = clz.getDeclaredFields();

			for (Field field : fields) {// --for() begin

				// 如果类型是String
				if (field.getGenericType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
																							// "，后面跟类名
					// 拿到该属性的gettet方法

					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));

					String val = (String) m.invoke(object);// 调用getter方法获取属性值
					if (val != null) {
						update.set(field.getName(), val);
					}

				}

				// 如果类型是Integer
				if (field.getGenericType().toString().equals("class java.lang.Integer")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Integer val = (Integer) m.invoke(object);
					if (val != null) {
						update.set(field.getName(), val);
					}

				}

				// 如果类型是Double
				if (field.getGenericType().toString().equals("class java.lang.Double")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Double val = (Double) m.invoke(object);
					if (val != null) {
						update.set(field.getName(), val);
					}

				}

				// 如果类型是Boolean 是封装类
				if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Boolean val = (Boolean) m.invoke(object);
					if (val != null) {
						update.set(field.getName(), val);
					}

				}

				// 如果类型是Date
				if (field.getGenericType().toString().equals("class java.util.Date")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Date val = (Date) m.invoke(object);
					if (val != null) {
						update.set(field.getName(), val);
					}

				}
				// 如果类型是Long
				if (field.getGenericType().toString().equals("class java.lang.Long")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Long val = (Long) m.invoke(object);
					if (val != null) {
						update.set(field.getName(), val);
					}

				}
				
				// 如果类型是BigDecimal
				if (field.getGenericType().toString().equals("class java.math.BigDecimal")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Object obj =m.invoke(object);
					if (obj != null) {
						BigDecimal val = new BigDecimal(obj.toString());
						if(val!=null){
							update.set(field.getName(), val);
						}
					}

				}
				
				// 如果类型是BigInteger
				if (field.getGenericType().toString().equals("class java.math.BigInteger")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Object obj =m.invoke(object);
					if (obj != null) {
						BigInteger val = new BigInteger(obj.toString());
						if(val!=null){
							update.set(field.getName(), val);
						}
					}

				}
				

			} // for() --end

		} // if (object!=null ) ----end
	}

	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

}
