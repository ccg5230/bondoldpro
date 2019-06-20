package com.innodealing.bond.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.bond.vo.reportdata.BondReportDataVO;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.BondPersonalRatingDataVO;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.mongo.dm.BondPdRankDoc;
import com.innodealing.util.SafeUtils;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description:
 */
@Service
public class BondReportDataService {
	private static final Logger LOG = LoggerFactory.getLogger(BondReportDataService.class);

	@Autowired
	private BondComInfoService bondComInfoService;

	@Autowired
	private BondAnalysisService bondAnalysisService;

	@Autowired
	private BondInduService bondInduService;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("asbrsPerResultJdbcTemplate")
	protected JdbcTemplate asbrsPerResultJdbcTemplate;

	/**
	 * 获取私人财报数据
	 * 
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public BondReportDataVO findPerBondReportData(Long taskId) {
		BondReportDataVO result = new BondReportDataVO();
		try {
			// 1. 基本信息
			BondPersonalRatingDataVO perComInfo = getPerComInfo(taskId);
			result.setInduClass(0);
			result.setIndustryStandard("GICS");
			result.setIndustryName(perComInfo.getInduName());
			result.setInstName(perComInfo.getCompName());
			result.setFinalRating(perComInfo.getRating());
			String yearQuarter = SafeUtils.getQuarter(perComInfo.getYearMonth() + "");
			result.setEvaluateQuarter(SafeUtils.convertFromYearQnToYearDesc(yearQuarter));
			result.setFileName(SafeUtils.convertFromYearQnToYearTitle(yearQuarter));
			LOG.info("findPerBondReportData: getPerComInfo done");
			// 2. DM主体量化风险等级的概要信息
			Long year = perComInfo.getYear();
			Long quarter = perComInfo.getMonth() / 3L;
			result.getRiskLevel().setShortDesc(bondComInfoService.findBondIssDMPerRatingSummary(taskId, year, quarter, perComInfo)
					.getRatingSummary());
			LOG.info("findPerBondReportData: findBondIssDMPerRatingSummary done");
	
			// 3. 全市场和当前行业评级分布图数据
			result.getRiskLevel().setAllMarketChartMetadata(bondInduService.findInduRatingViewByDate(null, null, year, quarter, true));
			result.getRiskLevel().setIndustryChartMetadata(bondInduService.findInduRatingViewByDate(
					Arrays.asList(perComInfo.getInduId()),null, year, quarter, true));
			LOG.info("findPerBondReportData: findInduRatingViewByDate and findInduRatingViewByDate done");
	
			// 4. 重点风险指标揭示
			BondIssDMRatingSummaryVO dmRatingSummary = bondComInfoService.findPrivateIssDMRatingSummary(taskId);
			result.getRiskIndicator().setRatioTitle(dmRatingSummary.getRatioTitle());
			result.getRiskIndicator().setKeyPoints(dmRatingSummary.getRatioContentList());
			LOG.info("findPerBondReportData: findPrivateIssDMRatingSummary done");
	
			// 5. 同行业信用等级排名Top10+Near5
			List<BondPdRankDoc> sortedPerPdRankList = bondAnalysisService.getPerPdRankList(perComInfo, true, true);
			LOG.info("findPerBondReportData: sortedPerPdRankList done");
			if (sortedPerPdRankList == null || sortedPerPdRankList.isEmpty()) {
				// 同行业仅此一家
				
				sortedPerPdRankList = new ArrayList<BondPdRankDoc>() {
					private static final long serialVersionUID = -807532162622964844L;
					{
						add(new BondPdRankDoc() {
							private static final long serialVersionUID = -839924744177082148L;
							{setIssuer(perComInfo.getCompName());}
						});
					}
				};
			}
			result.getCreditRank().setTopTen(bondAnalysisService.getTop10VO(null, perComInfo.getCompName(), sortedPerPdRankList));
			LOG.info("findPerBondReportData: getTop10VO done");
			if (result.getCreditRank().getTopTen().stream().allMatch(item -> item.getOneself() == 0)) {
				result.getCreditRank().setNeighborFive(bondAnalysisService.getNear5VO(null,
						perComInfo.getCompName(), sortedPerPdRankList));
				LOG.info("findPerBondReportData: getNear5VO done");
			}
			
		} catch (Exception ex) {
			LOG.error(String.format("findPerBondReportData: ex[%s]", ex.getMessage()));
			throw ex;
		}
		
		return result;
	}

	private BondPersonalRatingDataVO getPerComInfo(Long taskId) {
		BondPersonalRatingDataVO result = null;
		try {
			// 获取v_dm_personal_rating_data_sheet视图中的taskId对应的主体评级
			String perSql = "SELECT rating_model_id AS modleId, %1$d AS taskId, custname AS compName,"
					+ " LEFT(induid4,6) AS induPre, induname, `year`, quan_month AS month, rating, ods AS ratingValue,"
					+ " CONCAT(year, LPAD(CAST(quan_month AS CHAR(10)), 2, '0')) AS yearMonth"
					+ " FROM asbrs.v_dm_personal_rating_data_sheet"
					+ " WHERE taskid=%1$d ORDER BY `year` DESC, quan_month DESC LIMIT 1";
			String formatPerSql = String.format(perSql, taskId);
			List<BondPersonalRatingDataVO> perRatingDataList = (List<BondPersonalRatingDataVO>) asbrsPerResultJdbcTemplate
					.query(formatPerSql, new BeanPropertyRowMapper<BondPersonalRatingDataVO>(BondPersonalRatingDataVO.class));
			if (perRatingDataList != null && perRatingDataList.size() > 0) {
				result = perRatingDataList.get(0);
			}
			
			String induSql = String.format(
					"SELECT third_name FROM  /*amaresun*/ dmdb.tbl_industry_classification WHERE industry_code=%d LIMIT 1",
					result.getInduId()
				);
			String induName = jdbcTemplate.queryForObject(induSql, String.class);
			result.setInduName(induName);
		} catch (Exception ex) {
			LOG.error(String.format("getPerComInfo: ex[%s]", ex.getMessage()));
			throw new BusinessException(ex.getMessage());
		}
		return SafeUtils.throwNullBusinessEx(result, "无法获取同行业信用排名前10的数据");
	}
}
