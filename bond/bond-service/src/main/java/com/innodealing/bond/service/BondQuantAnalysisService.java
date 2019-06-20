package com.innodealing.bond.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bond.BondSpotRateDao;
import com.innodealing.engine.jdbc.bondccxe.BondCashFlowChartDao;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.model.dm.bond.BondSpotRate;
import com.innodealing.model.dm.bond.ccxe.BondCashFlowChart;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.util.SafeUtils;

/**
 * 量化基础指标计算服务 提供到期收益率，久期，凸性，利差等债券数据的计算
 * 
 * @author yig
 * @since 2017年5月25日
 */
@Service
public class BondQuantAnalysisService {

	private static final Logger LOG = LoggerFactory.getLogger(BondQuantAnalysisService.class);

	private static final int CALCULATE_MAX_TIMES = 100;
	private static final String CURVE_CODE_GOVERMENT = "1";
	private static final String SPOT_RATE_CACHE_NAME = "SPOT_RATE_CACHE";

	private @Autowired BondBasicInfoRepository basicInfoRepo;
	private @Autowired BondCashFlowChartDao cashFlowChartDao;
	private @Autowired BondSpotRateDao bondSpotRateDao;

	/**
	 * 国债即期收益率曲线 
	 * 读入后台上传的离散的国债收益率曲线数据，通过插值法生成多项式计算其他未知点的数据 
	 */
	private class BondSpotRateCurve {

		private UnivariateDifferentiableFunction psf;

		public void init(BondSpotRate spotRate, UnivariateInterpolator interpolator) {
			LOG.info("load Spot-Rate-Treasury-Curve, " + spotRate);

			List<Double> xList = new ArrayList<>(30);
			List<Double> yList = new ArrayList<>(30);

			if (spotRate.getByd_0y() != null) {
				xList.add(new Double(0));
				yList.add(spotRate.getByd_0y().doubleValue());
			}
			if (spotRate.getByd_7d() != null) {
				xList.add(new Double(7));
				yList.add(spotRate.getByd_7d().doubleValue());
			}
			if (spotRate.getByd_14d() != null) {
				xList.add(new Double(14));
				yList.add(spotRate.getByd_14d().doubleValue());
			}
			if (spotRate.getByd_1m() != null) {
				xList.add(new Double(30));
				yList.add(spotRate.getByd_1m().doubleValue());
			}
			if (spotRate.getByd_3m() != null) {
				xList.add(new Double(30 * 3));
				yList.add(spotRate.getByd_3m().doubleValue());
			}
			if (spotRate.getByd_6m() != null) {
				xList.add(new Double(30 * 6));
				yList.add(spotRate.getByd_6m().doubleValue());
			}
			if (spotRate.getByd_9m() != null) {
				xList.add(new Double(30 * 9));
				yList.add(spotRate.getByd_9m().doubleValue());
			}
			if (spotRate.getByd_1y() != null) {
				xList.add(new Double(365));
				yList.add(spotRate.getByd_1y().doubleValue());
			}
			if (spotRate.getByd_2y() != null) {
				xList.add(new Double(365 * 2));
				yList.add(spotRate.getByd_2y().doubleValue());
			}
			if (spotRate.getByd_3y() != null) {
				xList.add(new Double(365 * 3));
				yList.add(spotRate.getByd_3y().doubleValue());
			}
			if (spotRate.getByd_4y() != null) {
				xList.add(new Double(365 * 4));
				yList.add(spotRate.getByd_4y().doubleValue());
			}
			if (spotRate.getByd_5y() != null) {
				xList.add(new Double(365 * 5));
				yList.add(spotRate.getByd_5y().doubleValue());
			}
			if (spotRate.getByd_6y() != null) {
				xList.add(new Double(365 * 6));
				yList.add(spotRate.getByd_6y().doubleValue());
			}
			if (spotRate.getByd_7y() != null) {
				xList.add(new Double(365 * 7));
				yList.add(spotRate.getByd_7y().doubleValue());
			}
			if (spotRate.getByd_8y() != null) {
				xList.add(new Double(365 * 8));
				yList.add(spotRate.getByd_8y().doubleValue());
			}
			if (spotRate.getByd_9y() != null) {
				xList.add(new Double(365 * 9));
				yList.add(spotRate.getByd_9y().doubleValue());
			}
			if (spotRate.getByd_10y() != null) {
				xList.add(new Double(365 * 10));
				yList.add(spotRate.getByd_10y().doubleValue());
			}
			if (spotRate.getByd_15y() != null) {
				xList.add(new Double(365 * 15));
				yList.add(spotRate.getByd_15y().doubleValue());
			}
			if (spotRate.getByd_20y() != null) {
				xList.add(new Double(365 * 20));
				yList.add(spotRate.getByd_20y().doubleValue());
			}
			if (spotRate.getByd_30y() != null) {
				xList.add(new Double(365 * 30));
				yList.add(spotRate.getByd_30y().doubleValue());
			}
			if (spotRate.getByd_40y() != null) {
				xList.add(new Double(365 * 40));
				yList.add(spotRate.getByd_40y().doubleValue());
			}
			if (spotRate.getByd_50y() != null) {
				xList.add(new Double(365 * 50));
				yList.add(spotRate.getByd_50y().doubleValue());
			}
			
			double x[] = xList.stream().mapToDouble(i->i).toArray();
			double y[] = yList.stream().mapToDouble(i->i).toArray();

			psf = (UnivariateDifferentiableFunction) interpolator.interpolate(x, y);
		}
		
		public Double findByTerm(Double term) {
			return  psf.value(term);
		}
	}

	// 使用自动超时失效的缓存是因为国债即期收益率每天更新一次
	private LoadingCache<String, BondSpotRateCurve> spotRateCache = CacheBuilder.newBuilder().concurrencyLevel(30)
			.weakKeys().maximumSize(10000).expireAfterWrite(60, TimeUnit.MINUTES)
			.build(new CacheLoader<String, BondSpotRateCurve>() {
				public BondSpotRateCurve load(String key) throws Exception {
					BondSpotRateCurve spotRate = new BondSpotRateCurve();
					spotRate.init(bondSpotRateDao.findLastSpotRateByCurveCode(CURVE_CODE_GOVERMENT), new SplineInterpolator());
					return spotRate;
				}
			});

	/**
	 * 债券现金流包装类, 所有计算的基本单位，包含债券以及债券的未来现金流
	 */
	public class BondCashFlows {
		public BondCashFlows(BondBasicInfoDoc bond, List<BondCashFlowChart> cashFlows) {
			this.bond = bond;
			this.cashFlows = cashFlows;
		}

		public BondBasicInfoDoc getBond() {
			return bond;
		}

		public void setBond(BondBasicInfoDoc bond) {
			this.bond = bond;
		}

		public List<BondCashFlowChart> getCashFlows() {
			return cashFlows;
		}

		public void setCashFlows(List<BondCashFlowChart> cashFlows) {
			this.cashFlows = cashFlows;
		}

		public BondBasicInfoDoc bond;
		public List<BondCashFlowChart> cashFlows; // 未来现金流
	}

	public BondCashFlows findBondCashFlowsById(Long bondUniCode) {
		StopWatch watch = new StopWatch();
		watch.start();

		BondBasicInfoDoc bond = basicInfoRepo.findByBondUniCode(bondUniCode.toString());
		if (bond == null) {
			LOG.warn("bondUniCode invalid, bondUinCode:" + bondUniCode);
			return null;
		}
		List<BondCashFlowChart> cashFlows = cashFlowChartDao.findFutureCashFlow(bondUniCode.toString());
		Integer futureCashFlowCount = cashFlows.size();
		if (futureCashFlowCount.equals(0)) {
			LOG.warn("bond cash flow is empty, bondUinCode:" + bondUniCode);
			return null;
		}
		watch.stop();
		LOG.debug("findBondQuantUnit using StopWatch in millis: " + watch.getTotalTimeMillis());

		return new BondCashFlows(bond, cashFlows);
	}

	/**
	 * 计算 Z-利差 零波动率利差 zero-volatility spread 也称为静态利差(static spread)
	 * 假设未来的利率不发生变化，即波动率为0，那么此时债券的现金流也不发生变动 用于衡量非国债债券与国债债券之间的价格差异
	 */
	public Double calStaticSpreadByCleanPrice(Long bondUniCode, Double cleanPrice) {

		try {
			BondCashFlows bondCashFlow = findBondCashFlowsById(bondUniCode);
			if (bondCashFlow == null) {
				LOG.warn("bondUniCode invalid, bondUinCode:" + bondUniCode);
				return null;
			}

			// 二分法
			int calculateTimes = 0; // 表示试探计算次数
			final double SPREAD_SCALE = 0.01;

			// 这里的利差的上下限设定还是有可能会被溢出，所以最佳方法是动态测量得到范围，类似calYTMByDirtyPrice
			// 但因为利差的本质决定了它必然大于0（国债收益率为无风险利率，而利差是由信用风险流动性风险等带来的正向溢价)，所以最小取值为0是合理的
			double ssMax = 1000.00, ssMin = 0; // 'ss' 代表 static spread
			double ss = (ssMax + ssMin) / 2;

			BondSpotRateCurve spotRateData = spotRateCache.get(SPOT_RATE_CACHE_NAME);

			while (true) {
				// TODO 计算次数过多情况下的降级处理，一般由bug引起
				if (calculateTimes++ > CALCULATE_MAX_TIMES) {
					LOG.error("calStaticSpreadByCleanPrice dead loop, bondUniCode:"
							+ bondCashFlow.getBond().getBondUniCode());
					return null;
				}
				// 尝试根据给定利差计算现金流现值
				double pv = calculatePvBySpread(ss, bondCashFlow, spotRateData);
				Double pvDiff = pv - cleanPrice;
				LOG.debug(String.format(
						"------------ calculate %1$d times, cleanPrice:%7$f, spreadMax: %2$f, spreadMin: %3$f, spread: %4$f, pv: %5$f, pvDiff: %6$f",
						calculateTimes, ssMax, ssMin, ss, pv, pvDiff, cleanPrice));
				// 如果计算结果和预期相差较小，则接受该值
				if (Math.abs(pvDiff) < SPREAD_SCALE) {
					return round(ss);
				}
				// 如果计算结果和预期相差过大，则根据差异的方向调整下一次试探的取值范围
				if (pv < cleanPrice) {
					ssMax = ss;
				} else {
					ssMin = ss;
				}
				ss = (ssMax + ssMin) / 2;
			}
		} catch (Exception ex) {
			LOG.error("failed to calculate spread, bondUniCode:" + bondUniCode + ", cleanPrice:" + cleanPrice, ex);
			return null;
		}
	}

	/**
	 * Z-利差是债券所实现的收益率会在国债到期收益曲线之上高多少个基点。 指假定投资者持有至偿还期
	 * 不是债券与国债在到期收益率曲线一个点上的差别，而是反映债券收益率曲线超过国债收益率曲线的程度
	 */
	private Double calculatePvBySpread(Double spread, BondCashFlows calUnit, BondSpotRateCurve spotRateData) {

		BondBasicInfoDoc bond = calUnit.getBond();
		List<BondCashFlowChart> cashFlows = calUnit.getCashFlows();

		int intePayFreq = Math.max(bond.getIntePayFreq(), 1);
		LOG.debug("IntePayFreq:" + intePayFreq);
		int firstFlowsRemainDays = SafeUtils.getDaysBetween(new Date(), cashFlows.get(0).getInteEndDate()) + 1;
		LOG.debug("the first cash flow remains " + firstFlowsRemainDays + " days");
		double firstPeriPos = ((double) firstFlowsRemainDays) * intePayFreq / 365;
		LOG.debug("the first cash flow PeriPos " + firstPeriPos);

		double pvSum = 0.00;
		if (cashFlows.size() == 1) {
			BondCashFlowChart cashFlow = cashFlows.get(0);
			LOG.debug("CashFlow:" + cashFlow.toString());
			double adjPeriPos = firstPeriPos + 0.00;
			// 计算得到国债即期利率的插值
			double spotRate = spotRateData.findByTerm((double) firstFlowsRemainDays);
			double discountRate = 1 + (spread + spotRate) * ((double) firstFlowsRemainDays / 365) / 100;
			LOG.debug("Discount rate:" + String.valueOf(discountRate));
			double pv = cashFlow.getPayAmut() / discountRate;
			LOG.debug("pv:" + pv);
			pvSum = pv;
		} else {
			for (int periPos = 0; periPos < cashFlows.size(); ++periPos) {
				BondCashFlowChart cashFlow = cashFlows.get(periPos);
				LOG.debug("CashFlow:" + cashFlow.toString());
				double adjPeriPos = firstPeriPos + periPos;
				double term = (double) firstFlowsRemainDays + periPos * (365 / intePayFreq);
				LOG.debug("term:" + term + ", spread:" + spread + ", adjPeriPos:" + adjPeriPos);
				double spotRate = spotRateData.findByTerm(term);
				LOG.debug("spotRate:" + spotRate);
				LOG.debug("base:" + (1 + (spotRate + spread) / intePayFreq / 100));
				double discountRate = Math.pow((1 + (spotRate + spread) / intePayFreq / 100), adjPeriPos);
				LOG.debug("Discount rate:" + String.valueOf(discountRate));
				double pv = cashFlow.getPayAmut() / discountRate;
				pvSum += pv;
				LOG.debug("pv:" + pv);
				LOG.debug("pvSum:" + pvSum);
			}
		}

		return round(pvSum);
	}

	/**
	 * 计算利率敏感性相关指标，包括麦考利久期，修正久期，凸性
	 * 修正久期和凸性均为债券的价格收益率函数的泰勒展开得到，其中修正久期为一阶导参数，即曲线斜率，凸性为二阶导参数，即曲率
	 * 斜率在自变量利率变化程度小的时候相对有效，当利率变化程度变大时需要结合凸性做判断
	 * 麦考利久期和修正久期类似，但是本质含义是债券到期时间的加权（现金流金额为权重）平均
	 */
	public Map<String, Double> calDurationByYield(Long bondUniCode, Double yield) {
		BondCashFlows calUnit = findBondCashFlowsById(bondUniCode);
		if (calUnit == null) {
			LOG.warn("bondUniCode invalid, bondUinCode:" + bondUniCode);
			return null;
		}

		Double macD = calMacDurationByYield(yield, calUnit);
		Double modD = calModDurationByYield(yield, calUnit, macD);
		Double convexity = calConvexityByYield(yield, calUnit);
		return ImmutableMap.of("macd", macD, "modd", modD, "convexity", convexity);
	}

	/**
	 * 计算麦考利久期 先加总所有加权现金流金额的时间，再除以加总现金流总额，得到加权债券到期时间
	 */
	public Double calMacDurationByYield(Double yield, BondCashFlows calUnit) {
		Double timedWeightPv = calculatePvByIRR(yield, calUnit, true);
		Double pv = calculatePvByIRR(yield, calUnit, false);
		Double macD = timedWeightPv / (pv * Math.max(calUnit.getBond().getIntePayFreq(), 1));
		LOG.info("bondUniCode:" + calUnit.getBond().getBondUniCode() + ", timed-weight pv:" + timedWeightPv + ", pv:"
				+ pv + ", macD:" + macD);
		return macD;
	}

	public Double calModDurationByYield(Double yield, BondCashFlows calUnit, Double macD) {
		Double modD = macD / (1 + yield / (100 * Math.max(calUnit.getBond().getIntePayFreq(), 1)));
		LOG.info("bondUniCode:" + calUnit.getBond().getBondUniCode() + ", modD:" + modD);
		return modD;
	}

	private Double calConvexityByYield(Double yield, BondCashFlows calUnit) {
		BondBasicInfoDoc bond = calUnit.getBond();
		List<BondCashFlowChart> cashFlows = calUnit.getCashFlows();
		final int intePayFreq = Math.max(bond.getIntePayFreq(), 1);
		LOG.debug("IntePayFreq:" + intePayFreq);
		int firstFlowsRemainDays = SafeUtils.getDaysBetween(new Date(), cashFlows.get(0).getInteEndDate()) + 1;
		LOG.debug("the first cash flow remains " + firstFlowsRemainDays + " days");
		double firstPeriPos = ((double) firstFlowsRemainDays) * intePayFreq / 365;
		LOG.debug("the first cash flow PeriPos " + firstPeriPos);
		
		Double pvSum = new Double(0.00);
		for (int periPos = 0; periPos < cashFlows.size(); ++periPos) {
			BondCashFlowChart cashFlow = cashFlows.get(periPos);
			LOG.debug("CashFlow:" + cashFlow.toString());
			double i = firstPeriPos + periPos;
			double numerator = i * (i + 1) * cashFlow.getPayAmut();
			double denominator = Math.pow((1 + yield / intePayFreq / 100), i + 2);
			pvSum += numerator / denominator;
			LOG.debug("pvSum:" + pvSum.toString());
		}

		double pv = calculatePvByIRR(yield, calUnit, false); // 根据收益率折现得到价格
		LOG.debug("pv:" + pv);
		//以年为基础的债券凸性系数=以期为基础的债券凸性系数/每年付息次数的平方
		double convexity = pvSum / pv / Math.pow(intePayFreq, 2);
		LOG.info("bond:" + bond.getShortName() + ", convexity:" + convexity);

		return round(convexity);
	}

	/**
	 * 债券到期收益率即内部回报率（Internal Rate of Return） 到期收益率（英语：yield to
	 * maturity，缩写为YTM）是指投资者持有某一债券或其他定息证券至到期日，
	 * 并假定其本金与利息都按时支付时，以买入价格计算的内部收益率。按照到期收益率对债券未来所有的现金流量（包括利息和本金）
	 * 进行折现，所得到的现值与买入价格相等 https://en.wikipedia.org/wiki/Yield_to_maturity
	 */
	private Double calculatePvByIRR(Double irr, BondCashFlows calUnit, Boolean isTimeWeighted) {

		BondBasicInfoDoc bond = calUnit.getBond();
		List<BondCashFlowChart> cashFlows = calUnit.getCashFlows();

		// 参考https://192.168.8.189:18080/svn/Doc/产品/债券重构/债券数据计算/应计利息和到期收益率的计算方法.doc
		int intePayFreq = Math.max(bond.getIntePayFreq(), 1);
		LOG.debug("IntePayFreq:" + intePayFreq);
		int firstFlowsRemain = SafeUtils.getDaysBetween(new Date(), cashFlows.get(0).getInteEndDate()) + 1;
		LOG.debug("the first cash flow remains " + firstFlowsRemain + " days");
		double firstPeriPos = ((double) firstFlowsRemain) * intePayFreq / 365;
		LOG.debug("the first cash flow PeriPos " + firstPeriPos);

		Double pvSum = new Double(0.00);
		if (cashFlows.size() == 1) {
			BondCashFlowChart cashFlow = cashFlows.get(0);
			LOG.debug("CashFlow:" + cashFlow.toString());
			double adjPeriPos = firstPeriPos;
			Double discountRate = 1 + irr * ((double) firstFlowsRemain / 365) / 100;
			LOG.debug("Discount rate:" + String.valueOf(discountRate));
			Double pv = cashFlow.getPayAmut() / discountRate;
			LOG.debug("pv:" + pv.toString());
			pvSum = pv;
			if (isTimeWeighted)
				pvSum = adjPeriPos * pv;
		} else {
			for (int periPos = 0; periPos < cashFlows.size(); ++periPos) {
				BondCashFlowChart cashFlow = cashFlows.get(periPos);
				LOG.debug("CashFlow:" + cashFlow.toString());
				double adjPeriPos = firstPeriPos + periPos;
				Double discountRate = Math.pow((1 + irr / intePayFreq / 100), adjPeriPos);
				LOG.debug("Discount rate:" + String.valueOf(discountRate));
				Double pv = cashFlow.getPayAmut() / discountRate;
				if (isTimeWeighted)
					pv = adjPeriPos * pv;
				pvSum += pv;
				LOG.debug("pv:" + pv.toString());
				LOG.debug("pvSum:" + pvSum.toString());
			}
		}

		return round(pvSum);
	}

	//////////////////////////////////////////////////////////////////////////
	////// YTM/////////////////////////////////////////////////////////////////
	public Double calYTMByCleanPrice(Long bondUniCode, Double cleanPrice) {
		LOG.debug("-----calCleanPriceByYTM, bondUinCode:" + bondUniCode + ", cleanPrice:" + cleanPrice + "------");

		BondCashFlows calUnit = findBondCashFlowsById(bondUniCode);
		if (calUnit == null) {
			LOG.warn("bondUniCode invalid, bondUinCode:" + bondUniCode);
			return null;
		}

		return calYTMByCleanPrice(calUnit, cleanPrice);
	}

	public Double calYTMByCleanPrice(BondCashFlows calUnit, Double cleanPrice) {
		Double accruedInte = calAccruedInte(calUnit);
		Double dirtyPrice = cleanPrice + accruedInte;
		LOG.debug(String.format(
				"calYTMByCleanPrice start bisection  method, [bondUinCode:%1$s, cleanPrice:%2$f, accruedInte:%3$f, dirtyPrice:%4$f ]",
				calUnit.getBond().getShortName(), cleanPrice, accruedInte, dirtyPrice));

		return calYTMByDirtyPrice(calUnit, dirtyPrice);
	}

	public Double calYTMByDirtyPrice(BondCashFlows calUnit, Double dirtyPrice) {
		BondBasicInfoDoc bond = calUnit.getBond();
		final boolean isUseHardMaxMin = false;
		LOG.debug(String.format("calYTMByDirtyPrice start bisection  method, [bondUinCode:%1$s, dirtyPrice:%2$f ]",
				bond.getShortName(), dirtyPrice));

		Double ytmMax = 1000.00, ytmMin = -1000.00;
		Double ytm = 5.00, pv = null;
		int calculateTimes = 0; // 表示试探计算次数

		// 试探法找到YTM的上下限, 因为ytmMax = 100.00, ytmMin = -100.00 在极端情况下不够可靠
		if (!isUseHardMaxMin) {
			pv = calculatePvByIRR(ytm, calUnit, false);
			if (pv < dirtyPrice) {
				ytmMax = ytm;
				do {
					ytm = (-1) * Math.abs(Math.pow(ytm, 2));
					if (calculateTimes++ > CALCULATE_MAX_TIMES) {
						LOG.error("calYTMByDirtyPrice dead loop in find uplow limit, bondUniCode:"
								+ calUnit.getBond().getBondUniCode());
						return null;
					}
				} while ((pv = calculatePvByIRR(ytm, calUnit, false)) < dirtyPrice);
				ytmMin = ytm;
			} else if (pv > dirtyPrice) {
				ytmMin = ytm;
				do {
					ytm = Math.abs(Math.pow(ytm, 2));
					if (calculateTimes++ > CALCULATE_MAX_TIMES) {
						LOG.error("calYTMByDirtyPrice dead loop in find uplow limit, bondUniCode:"
								+ calUnit.getBond().getBondUniCode());
						return null;
					}
				} while ((pv = calculatePvByIRR(ytm, calUnit, false)) > dirtyPrice);
				ytmMax = ytm;
			}
			if (pv.equals(dirtyPrice)) {
				return round(ytm);
			}
			calculateTimes += 2;
		}

		// 二分法找到满足精度的YTM
		final double YTM_SCALE = 0.0001;
		ytm = (ytmMax + ytmMin) / 2;

		while (true) {
			if (calculateTimes++ > CALCULATE_MAX_TIMES) {
				LOG.error("calYTMByDirtyPrice dead loop, bondUniCode:" + calUnit.getBond().getBondUniCode());
				return null;
			}
			pv = calculatePvByIRR(ytm, calUnit, false);
			Double pvDiff = pv - dirtyPrice;
			LOG.debug(String.format(
					"calculate %1$d times, ytmMax: %2$f, ytmMin: %3$f, ytm: %4$f, pv:: %5$f, pvDiff: %6$f",
					calculateTimes, ytmMax, ytmMin, ytm, pv, pvDiff));
			if (Math.abs(pvDiff) < YTM_SCALE)
				return round(ytm);
			if (pv < dirtyPrice) {
				ytmMax = ytm;
				ytm = (ytmMax + ytmMin) / 2;
			} else {
				ytmMin = ytm;
				ytm = (ytmMax + ytmMin) / 2;
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////
	////// Clean price//////////////////////////////////////////////////////////
	public Double calCleanPriceByYTM(Long bondUniCode, Double ytm) {
		LOG.debug("-----calCleanPriceByYTM, bondUinCode:" + bondUniCode + ", ytm:" + ytm + "------");

		BondCashFlows calUnit = findBondCashFlowsById(bondUniCode);
		if (calUnit == null) {
			LOG.warn("bondUniCode invalid, bondUinCode:" + bondUniCode);
			return null;
		}

		return calCleanPriceByYTM(calUnit, ytm);
	}

	public Double calCleanPriceByYTM(BondCashFlows calUnit, Double ytm) {
		try {
			// https://en.wikipedia.org/wiki/Yield_to_maturity
			Double dirtyPrice = calDirtyPriceByYtm(ytm, calUnit);
			Double accruedInte = calAccruedInte(calUnit);
			Double cleanPrice = dirtyPrice - accruedInte;

			LOG.debug("cleanPrice=" + cleanPrice + "(" + dirtyPrice + " - " + accruedInte.doubleValue() + ")");

			// https://en.wikipedia.org/wiki/Clean_price
			// Dirty Price = Clean Price + Accrued Interest
			return dirtyPrice - accruedInte.doubleValue();
		} catch (Exception ex) {
			LOG.error("failed to calDirtyPriceByYTM ", ex);
			return null;
		}
	}

	public Double calCleanPriceByDirtyPrice(BondCashFlows calUnit, Double dirtyPrice) {
		Double accruedInte = calAccruedInte(calUnit);
		return dirtyPrice - accruedInte;
	}

	//////////////////////////////////////////////////////////////////////////
	////// dirty price//////////////////////////////////////////////////////////
	public Double calDirtyPriceByYtm(Double ytm, BondCashFlows calUnit) {
		return calculatePvByIRR(ytm, calUnit, false);
	}

	public Double calDirtyPriceByCleanPrice(BondCashFlows calUnit, Double cleanPrice) {
		Double accruedInte = calAccruedInte(calUnit);
		return cleanPrice + accruedInte;
	}

	//////////////////////////////////////////////////////////////////////////
	////// accrued Interest////////////////////////////////////////////////////
	////// https://en.wikipedia.org/wiki/Accrued_interest //////
	private Double calAccruedInte(BondCashFlows calUnit) {

		BondBasicInfoDoc bond = calUnit.getBond();
		List<BondCashFlowChart> futureCashFlow = calUnit.getCashFlows();

		BondCashFlowChart nextIntePayFlow = futureCashFlow.get(0);
		int accruedDays = SafeUtils.getDaysBetween(nextIntePayFlow.getInteStartDate(), new Date());
		int period = SafeUtils.getDaysBetween(nextIntePayFlow.getInteStartDate(), nextIntePayFlow.getInteEndDate()) + 1;

		BigDecimal intePay = new BigDecimal(0.00);
		if (bond.getRateType().equals(Constants.RATE_TYPE_DISCOUNT_BOND)) {
			intePay = new BigDecimal(100 - bond.getIssPri());
		} else {
			if (nextIntePayFlow.getIntePay() == null) {
				LOG.debug("skip accrued interest since there is no interest pay in cashflow");
				return 0.00;
			}
			intePay = new BigDecimal(nextIntePayFlow.getIntePay());
		}

		BigDecimal accruedRatio = new BigDecimal(((double) accruedDays) / ((double) period));
		BigDecimal accruedInte = intePay.multiply(accruedRatio);

		LOG.debug("intePay:" + intePay);
		LOG.debug("accruedDays:" + accruedDays + ", period:" + period + ", accruedRatio:" + accruedRatio);
		LOG.debug("accruedInte:" + accruedInte.toString());
		return round(accruedInte.doubleValue());
	}

	/**
	 * 获取应计利息
	 * @param bondUniCode
	 * @return
	 */
	public Double getAccruedInterest(Long bondUniCode) {
		BondCashFlows calUnit = findBondCashFlowsById(bondUniCode);
		if (calUnit == null) {
			return null;
		}
		return calAccruedInte(calUnit);
	}

	static public Double round(Double in) {
		try {
			return new BigDecimal(in).setScale(4, RoundingMode.HALF_EVEN).doubleValue();
		} catch (Exception ex) {
			LOG.error("failed to round, in:" + in, ex);
			throw ex;
		}
	}

	public static void main(String[] args) {
		BondSpotRate spotRate = new BondSpotRate();
		spotRate.setByd_0y(new BigDecimal(2.5218));
		spotRate.setByd_1m(new BigDecimal(2.7077));
		spotRate.setByd_3m(new BigDecimal(3.2517));
		spotRate.setByd_6m(new BigDecimal(3.4133));
		spotRate.setByd_9m(new BigDecimal(3.3771));
		spotRate.setByd_1y(new BigDecimal(3.4916));
		spotRate.setByd_2y(new BigDecimal(3.6889));
		spotRate.setByd_3y(new BigDecimal(3.7117));
		spotRate.setByd_4y(new BigDecimal(3.7017));
		spotRate.setByd_5y(new BigDecimal(3.6951));
		spotRate.setByd_6y(new BigDecimal(3.7409));
		spotRate.setByd_7y(new BigDecimal(3.7688));
		spotRate.setByd_8y(new BigDecimal(3.7433));
		spotRate.setByd_9y(new BigDecimal(3.7063));
		spotRate.setByd_10y(new BigDecimal(3.689));
		spotRate.setByd_15y(new BigDecimal(4.0986));
		spotRate.setByd_20y(new BigDecimal(4.1099));
		spotRate.setByd_30y(new BigDecimal(4.1143));
		spotRate.setByd_40y(new BigDecimal(4.2463));
		spotRate.setByd_50y(new BigDecimal(4.2854));
		BondSpotRateCurve spotRateCurve = new BondQuantAnalysisService().new BondSpotRateCurve();
		spotRateCurve.init(spotRate, new LinearInterpolator());
		for (int i = 0; i < 365*50; ++i)
			System.out.println(spotRateCurve.findByTerm((double) i));
	}

}
