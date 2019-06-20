package com.innodealing.bond.service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.innodealing.model.mongo.dm.*;
import com.innodealing.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.vo.msg.BasicBondVo;
import com.innodealing.bond.vo.trends.TrendsBondSentiInfoVO;
import com.innodealing.bond.vo.trends.TrendsBondSkipURLSubVO;
import com.innodealing.bond.vo.trends.TrendsBondSkipURLVO;
import com.innodealing.consts.BondTrendsConst;
import com.innodealing.engine.jdbc.im.IsHolidayDao;
import com.innodealing.exception.BusinessException;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author xiaochao
 * @time 2017年3月29日
 * @description:
 */
@Service
public class BondTrendsService {

	private static final Logger LOG = LoggerFactory.getLogger(BondTrendsService.class);

	@Resource(name="bondMongo")
	protected MongoTemplate bondMongoTemplate;

	@Resource(name="sentimentMongo")
	protected MongoTemplate sentimentMongoTemplate;

	@Autowired
	private BondInstitutionInduAdapter induAdapter;

	@Autowired
	private IsHolidayDao isHolidayDao;

	public static ExecutorService bondServiceFixedThreadPool = Executors.newFixedThreadPool(20);

	/**
	 * 获取今日关注列表
	 *
	 * @param negaTypeList 情感类别[0-空;1-负面;2-中性;3-利好]
	 * @param clsiTypeList 今日动态分类[1=发行、流通、付息与兑付;2=违约;3=评级跟踪;4=其他公告]
	 * @param startDateStr 开始日期[yyyy-MM-dd]
	 * @param endDateStr 结束日期[yyyy-MM-dd]
	 * @param page 页数
	 * @param limit 每页显示数量
	 * @return
	 */
	public Page<TrendsBondSentiInfoVO> getFocusOnTodayList(List<Integer> negaTypeList, List<Integer> clsiTypeList,
															String startDateStr, String endDateStr, Integer page, Integer limit) {
		// 类别
		List<Integer> inputClsiTypeList = (clsiTypeList == null || clsiTypeList.isEmpty()) ?
				BondTrendsConst.DEFAULT_TODAY_TRENDS_CLSI_LIST : clsiTypeList;
		// 正面负面
		List<Integer> inputNegaTypeList = (negaTypeList == null || negaTypeList.isEmpty()) ?
				BondTrendsConst.DEFAULT_TODAY_TRENDS_NEGA_LIST : negaTypeList;
		Date startDate;
		Date endDate;
		try {
			// 默认7天数据
			if (StringUtils.isBlank(startDateStr))
				startDateStr = SafeUtils.getDateFromNow(-6);
			if (StringUtils.isBlank(endDateStr))
				endDateStr = SafeUtils.getCurrDateFmtStr();
			String fromDateStr = String.format("%1$s 00:00:00", startDateStr);
			String toDateStr = String.format("%1$s 23:59:59", endDateStr);
			startDate = SafeUtils.convertStringToDate(fromDateStr, SafeUtils.DATE_TIME_FORMAT1);
			endDate = SafeUtils.convertStringToDate(toDateStr, SafeUtils.DATE_TIME_FORMAT1);
		} catch (Exception ex) {
			LOG.error(String.format("findFocusOnTodayList: exception[%s]", ex.getMessage()));
			throw new BusinessException("获取今日关注列表出错");
		}
		page = page < 0 ? 0 : page;
		Criteria pagedCriteria = Criteria.where("pubDate").gte(startDate).lte(endDate).and("status").is(1)
				.and("display").in(BondTrendsConst.DEFAULT_TODAY_TRENDS_DISPLAY_LIST)
				.and("classification").in(inputClsiTypeList).and("sentiment").in(inputNegaTypeList);
		Query pagedQuery = new Query(pagedCriteria);
		long total = sentimentMongoTemplate.count(pagedQuery, BondBulletinDoc.class);
		List<TrendsBondSentiInfoVO> resultList = this.getPagedFocusOnTodayList(pagedQuery, page, limit);
		resultList = resultList.stream().sorted(Comparator.comparing(TrendsBondSentiInfoVO::getPubDate).reversed())
				.collect(Collectors.toList());
		if (!resultList.isEmpty()) {
			// 忽略选定的类别条件的各项统计
			this.handleStatistics(resultList.get(0), inputNegaTypeList, startDate, endDate);
		}
		return new PageImpl<>(resultList, new PageRequest(page, limit, null), total);
	}

	/**
	 * 今日动态各类别统计
	 * @param headerSentiInfo
	 * @param inputNegaTypeList
	 * @param startDate
	 * @param endDate
	 */
	private void handleStatistics(TrendsBondSentiInfoVO headerSentiInfo, List<Integer> inputNegaTypeList,
								  Date startDate, Date endDate) {
		Map<Integer, Long> statisticsMap = new HashMap<>();
		CompletableFuture[] statisticsFutures = BondTrendsConst.DEFAULT_TODAY_TRENDS_CLSI_LIST.stream().map(clsi ->
			CompletableFuture.runAsync(() -> {
				Criteria clsiCriteria = Criteria.where("pubDate").gte(startDate).lte(endDate).and("status").is(1)
						.and("display").in(BondTrendsConst.DEFAULT_TODAY_TRENDS_DISPLAY_LIST)
						.and("classification").is(clsi)
						.and("sentiment").in(inputNegaTypeList);
				Long count = sentimentMongoTemplate.count(new Query(clsiCriteria), BondBulletinDoc.class);
				statisticsMap.put(clsi, count);
			}, bondServiceFixedThreadPool)).toArray(CompletableFuture[]::new);
		CompletableFuture.allOf(statisticsFutures).join();
		headerSentiInfo.setStatistics(statisticsMap);
	}

	/**
	 * 今日动态分页数据
	 * @param pagedQuery
	 * @param page
	 * @param limit
	 * @return
	 */
	private List<TrendsBondSentiInfoVO> getPagedFocusOnTodayList(Query pagedQuery, int page, int limit) {
		List<TrendsBondSentiInfoVO> resultList = new ArrayList<>();
		// 单页数据
		List<BondBulletinDoc> bulletinDocList = sentimentMongoTemplate.find(
				pagedQuery.with(new PageRequest(page, limit, new Sort(Direction.DESC, "pubDate", "editTime"))),
				BondBulletinDoc.class);

		if (!bulletinDocList.isEmpty()) {
			List<CompletableFuture> bulletinDocFuture = bulletinDocList.stream()
					.map(doc -> CompletableFuture.runAsync(() -> {
				TrendsBondSentiInfoVO sentiInfoVO = new TrendsBondSentiInfoVO();
				// 获取TagList
				if (StringUtils.isNotBlank(doc.getTagIds())) {
					List<String> tagList = Arrays.asList(doc.getTags().split(",")).stream()
							.filter(tag -> StringUtils.isNotBlank(tag)).collect(Collectors.toList());
					sentiInfoVO.setTagList(tagList);
				}
				// 获取BondList
				if (StringUtils.isNotBlank(doc.getBondIds())) {
					List<Long> bondIdList = Arrays.asList(doc.getBondIds().split(",")).stream()
							.filter(id -> StringUtils.isNotBlank(id)).map(id -> Long.parseLong(id.trim()))
							.collect(Collectors.toList());
					List<String> bondNameList = Arrays.asList(doc.getBondNames().split(","));
					for (int i = 0; i < bondIdList.size(); i++) {
						sentiInfoVO.getBondList().add(new BasicBondVo(bondIdList.get(i), bondNameList.get(i)));
					}
				}
				BeanUtils.copyProperties(doc, sentiInfoVO);
				handleSkipJson(sentiInfoVO);
				resultList.add(sentiInfoVO);
			}, bondServiceFixedThreadPool)).collect(Collectors.toList());
			bulletinDocFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	private void handleSkipJson(TrendsBondSentiInfoVO sentiInfoVO) {
		String skipJson = sentiInfoVO.getSkipJson();
		Map<String, Map<String, String>> simpleMap = JSON.parseObject(skipJson, Map.class);
		if (simpleMap == null)
			return;
		try {
			simpleMap.forEach((k, v) -> {
				sentiInfoVO.getSkipURLList().add(new TrendsBondSkipURLVO() {
					{
						setName(k);
						v.forEach((sk, sv) -> {
							getUrlList().add(new TrendsBondSkipURLSubVO() {
								{
									setName(sk);
									setUrl(sv);
								}
							});
						});

					}
				});
			});
		} catch (ClassCastException ccex) {
			Map<String, String> stringMap = JSON.parseObject(skipJson, Map.class);
			stringMap.forEach((k, v) -> {
				// 单条记录
				sentiInfoVO.getSkipURLList().add(new TrendsBondSkipURLVO() {
					{
						setName(k);
						getUrlList().add(new TrendsBondSkipURLSubVO() {
							{
								setName(k);
								setUrl(v);
							}
						});
					}
				});
				;
			});
		}
	}

	/**
	 * 获取隐含评级变动列表
	 * 
	 * @param pubDate 指定日期
	 * @param type 隐含评级变动类型[0=全部;1=上调;2=下调]
	 * @param page 页数
	 * @param limit 每页显示数量
	 * @return
	 */
	public Page<BondTrendsImpRatingChangeDoc> findImpRatingList(Long userId, String pubDate, int type, String sortParaStr,
			Integer page, Integer limit) {
		// 获取历史隐含评级变动列表
		if (StringUtils.isBlank(pubDate)) {
			pubDate = SafeUtils.getCurrDateFmtStr();
		}
		Sort sort = SafeUtils.convertToSort(sortParaStr);
		if (sort == null)
			sort = new Sort(Sort.Direction.DESC, "pubDate");

		Date pubBgnDate = SafeUtils.parseDate(pubDate + " 00:00:00", SafeUtils.DATE_TIME_FORMAT1);
		Date pubEndDate = SafeUtils.parseDate(pubDate + " 23:59:59", SafeUtils.DATE_TIME_FORMAT1);
		Query query = new Query() {
			{
				addCriteria(Criteria.where("pubDate").gte(pubBgnDate).lte(pubEndDate));
				if (type == 1) // 上调-差值小于0
					addCriteria(Criteria.where("rateDiff").lt(0L));
				else if (type == 2) // 下调-差值大于0
					addCriteria(Criteria.where("rateDiff").gt(0L));
			}
		};
		Long total = bondMongoTemplate.count(query, BondTrendsImpRatingChangeDoc.class);
		query.with(new PageRequest(page, limit, sort));
		List<BondTrendsImpRatingChangeDoc> impRatingChangeDocList = bondMongoTemplate.find(query,
				BondTrendsImpRatingChangeDoc.class);

		if (impRatingChangeDocList.isEmpty()) {
			LOG.warn(String.format(
					"findImpRatingList: empty result with pubDate[%1$s], type[%2$d], page[%3$d] and limit[%4$d]",
					pubDate, type, page, limit));
		}

		// 计算内部评级+投资建议+行业
		Set<Long> bondUniCodeSet = impRatingChangeDocList.stream().map(BondTrendsImpRatingChangeDoc::getBondUniCode)
				.collect(Collectors.toSet());
		Query basicQuery = new Query(Criteria.where("bondUniCode").in(bondUniCodeSet));
		basicQuery.fields().include("bondUniCode").include("institutionInduMap").include("instRatingMap").include("issuerId")
				.include("instInvestmentAdviceMap").include("instRatingDescribe").include("instInvestmentAdviceDescribe");
		List<BondBasicInfoDoc> bondDetailDocList = bondMongoTemplate.find(basicQuery, BondBasicInfoDoc.class);
		Map<Long, BondBasicInfoDoc> bondUniCode2BasicInfoMap = bondDetailDocList.stream()
				.collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, item -> item));
		impRatingChangeDocList.forEach(resultDoc -> {
			Long bondUniCode = resultDoc.getBondUniCode();
			if (bondUniCode2BasicInfoMap.containsKey(bondUniCode)) {
				BeanUtil.deepCopyProperties(bondUniCode2BasicInfoMap.get(bondUniCode), resultDoc);
			}
		});
		induAdapter.conv(impRatingChangeDocList, userId);
		return new PageImpl<>(impRatingChangeDocList, new PageRequest(page, limit, null), total);
	}

	/**
	 * 获取上一个交易日的日期
	 * 
	 * @return
	 */
	public Date getLastTradingDay() {
		Date lastTradingDay = isHolidayDao.findLastTradingDay();
		return lastTradingDay;
	}
}
