package com.innodealing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.innodealing.bond.service.BondQuantAnalysisService;
import com.innodealing.engine.jpa.dm.BondDurationRepository;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.BondEstYtm;
import com.innodealing.model.FundamentalIndicator;
import com.innodealing.model.dm.bond.BondDuration;
import com.innodealing.model.dm.bond.BondDurationKey;
import com.innodealing.model.dm.bond.BondInfo;
import com.innodealing.model.dm.bond.BondSpread;
import com.innodealing.model.dm.bond.BondStaticSpreadKey;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

@Service
public class BondFundamentalIndicatorCalculateService {

	private static final Logger LOG = LoggerFactory.getLogger(BondFundamentalIndicatorCalculateService.class);

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	private BondBasicInfoRepository basicRepository;

	@Autowired
	private BondDetailRepository detailRepository;

	@Autowired
	private BondQuantAnalysisService quantAnalysisService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	BondDurationRepository durationRepo;
	
    @Autowired
    BondQuantAnalysisService bondQuantService;

	public String rebuildDurationByCode(String bondCode) {
		return rebuildDuraion(bondCode, true);
	}
	
	public String forceRebuildDuration()
	{
		return rebuildDuraion(null, true);
	}

	public String rebuild()
	{
		return rebuildDuraion(null, false);
	}
	
	public String rebuildDuraion(String bondCode, boolean isForce) {

		Date dataDate = getDataDate();
	
		String logstart = "开始构建最新的基础（市场）指标， 数据的最新时间:" + date2String(dataDate);
		if (!StringUtils.isEmpty(bondCode)) {
			logstart += ", bondCode:" + bondCode;
		}
		LOG.info(logstart);
		
		if(!isForce && isAlreadyBuilded(dataDate)) {
			LOG.info("当天的数据已经构建，如果需要强制覆盖，请使用强制模式");
			return "当天的数据已经构建，如果需要强制覆盖，请使用强制模式";
		}

		if (!removeTodayData(dataDate, bondCode))
			return "删除数据失败";

		String sqlBase = "select code, name, rate, net_valuation as estCleanPrice, exercise_yield as optionYield, data_date from innodealing.bond_info  ";
		if (!StringUtils.isEmpty(bondCode)) {
			sqlBase = String.format(sqlBase + " where code ='%1$s'", bondCode);
		}
		ExecutorService pool = Executors.newFixedThreadPool(32);
		int position = 0;
		int count = 100;
		for (;;) {
			String sql = sqlBase.concat("limit ").concat(position + "").concat(" , ").concat(count + "");
			position += count;
			LOG.info(sql);
			List<BondEstYtm> estYtms = (List<BondEstYtm>) jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<BondEstYtm>(BondEstYtm.class));
			if (estYtms == null) {
				LOG.error("empty result set");
				return "empty result set";
			}
			if (estYtms.isEmpty()) {
				LOG.info("task has been done successfully");
				break;
			}
			pool.execute(new Runnable() {
				@Override
				public void run() {
					calcuteFundamentalIndicator(estYtms, dataDate);
				}
			});
		}
		pool.shutdown();

		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOG.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
			return e.toString();
		}
		LOG.info("构建最新的基础（市场）指标结束");
		return "构建最新的基础（市场）指标结束";
	}

	private boolean isAlreadyBuilded(Date dataDate) {
		List<BondDuration> durations = durationRepo.findLastestDuration();
		if (durations == null || durations.isEmpty())
			return false;
		
		Date lastDate = durations.get(0).getDurationKey().getDurationDate();
		Date l = SafeUtils.removeTime(lastDate);
		Date n = SafeUtils.removeTime(dataDate);
		if (l.compareTo(n) >= 0)
			return true;
		return false;
	}

	private Date getDataDate() {
		String sql = "select * from innodealing.bond_info order by data_date desc limit 1";
		List<BondInfo> bondInfos = (List<BondInfo>) jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<BondInfo>(BondInfo.class));
		if (bondInfos == null || bondInfos.isEmpty()) {
			throw new BusinessException("bond_info 数据异常");
		}

		return bondInfos.get(0).getData_date();
	}

	private boolean removeTodayData(Date dataDate, String bondCode) {
		try {
			Long bondUniCode = null;
			if (!StringUtils.isEmpty(bondCode)) {
				BondBasicInfoDoc bond = basicRepository.findByCode(bondCode);
				if (bond == null) {
					LOG.warn("code:" + bondCode + " not found");
					return false;
				}
				bondUniCode = bond.getBondUniCode();
			}

			String sqlTmpl = "delete from dmdb.t_bond_duration  where duration_date  = '%1$s'";
			String sql = String.format(sqlTmpl, date2String(dataDate));
			if (bondUniCode != null) {
				sql += " and bond_uni_code=" + bondUniCode;
			}
			int rows = jdbcTemplate.update(sql);
			LOG.info("完成删除t_bond_duration久期/凸性数据: " + rows + "条");

			sqlTmpl = "delete from dmdb.t_bond_spread where update_date  = '%1$s'";
			sql = String.format(sqlTmpl, date2String(dataDate));
			if (bondUniCode != null) {
				sql += " and bond_uni_code=" + bondUniCode;
			}

			rows = jdbcTemplate.update(String.format(sql, date2String(dataDate)));
			LOG.info("完成删除t_bond_spread利差数据: " + rows + "条");

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("删除发生异常 ", e);
			return false;
		}
		return true;
	}

	protected void calcuteFundamentalIndicator(List<BondEstYtm> estYtms, Date dataDate) {

		List<BondDuration> durationList = new ArrayList<BondDuration>();
		List<BondSpread> spreadList = new ArrayList<BondSpread>();

		SpreadStatistics spreadStatistics = new SpreadStatistics();
		Map<Long, FundamentalIndicator> indicatorUpdates = new HashMap<Long, FundamentalIndicator>();

		BondBasicInfoDoc bond;
		for (BondEstYtm estYtm : estYtms) {
			try {
				if (estYtm.getCode() == null || estYtm.getRate() == null) {
					continue;
				}
				if (estYtm.getRate() <= 0) {
					LOG.warn("code:" + estYtm.getCode() + " invalid rate:" + estYtm.getRate());
					continue;
				}
				bond = basicRepository.findByCode(estYtm.getCode());
				if (bond == null) {
					LOG.warn("code:" + estYtm.getCode() + " not found");
					continue;
				}
				estYtm.setBondCode(bond.getBondUniCode());
				LOG.info("正在计算基础指标 " + estYtm.toString());

				// 计算麦氏久期，修正久期，凸性
				{
					Map<String, Double> ret = quantAnalysisService.calDurationByYield(bond.getBondUniCode(),
							estYtm.getRate());
					if (ret == null) {
						LOG.warn("code:" + estYtm.getCode() + " failed to calculate duration");
					} else {
						BondDurationKey durationKey = new BondDurationKey();
						durationKey.setBondUniCode(bond.getBondUniCode());
						durationKey.setDurationDate(SafeUtils.removeTime(dataDate));
						BondDuration duration = new BondDuration();
						duration.setBondShortName(bond.getShortName());
						duration.setYield(new BigDecimal(estYtm.getRate()));
						duration.setMacd(new BigDecimal(ret.get("macd")).setScale(4, RoundingMode.CEILING));
						duration.setModd(new BigDecimal(ret.get("modd")).setScale(4, RoundingMode.CEILING));
						duration.setConvexity(new BigDecimal(ret.get("convexity")).setScale(4, RoundingMode.CEILING));
						duration.setDurationKey(durationKey);
						duration.setCreateTime(new Date());
						duration.setUpdateTime(new Date());
						LOG.info("久期计算完成:" + duration.toString());
						durationList.add(duration);
					}
				}
				// 计算单券利差
				{
					//应计利息
					Double accruedInterest = bondQuantService.getAccruedInterest(bond.getBondUniCode()); // 应计利息
					
					Double zSpread = quantAnalysisService.calStaticSpreadByCleanPrice(bond.getBondUniCode(),
							estYtm.getEstCleanPrice()+accruedInterest);
					if (zSpread == null) {
						LOG.warn("code:" + estYtm.getCode() + " failed to calculate Spread");
					} else {
						BondStaticSpreadKey staticSpreadKey = new BondStaticSpreadKey();
						staticSpreadKey.setBondUniCode(bond.getBondUniCode());
						staticSpreadKey.setUpdateDate(SafeUtils.removeTime(dataDate));
						BondSpread staticSpread = new BondSpread();
						staticSpread.setBondShortName(bond.getShortName());
						staticSpread.setStaticSpreadKey(staticSpreadKey);
						staticSpread.setStaticSpread(new BigDecimal(zSpread).setScale(4, RoundingMode.CEILING));
						staticSpread.setCreateTime(new Date());
						staticSpread.setUpdateTime(new Date());

						LOG.info("单券利差计算完成:" + staticSpread.toString());
						spreadList.add(staticSpread);
						spreadStatistics.addSpread(bond, staticSpread);
					}
				}

			} catch (Exception ex) {
				LOG.error("failed to save duration", ex);
				ex.printStackTrace();
			}
		}
		//spreadStatistics.analyze();
		buildBondInfoChanges(estYtms, indicatorUpdates);
		buildDurationChanges(durationList, indicatorUpdates);
		buildSpreadChanges(spreadList, indicatorUpdates);
		batchSaveDuration(durationList);
		batchSaveStaticSpread(spreadList);
		batchUpdateMongodb(indicatorUpdates);
	}

	public void batchUpdateMongodb(Map<Long, FundamentalIndicator> mongoUpdates) {
		if (!mongoUpdates.isEmpty()) {
			for (Map.Entry<Long, FundamentalIndicator> entry : mongoUpdates.entrySet()) {
				entry.getValue().setBondUniCode(entry.getKey());
				LOG.info("基础指标变化字段:" + entry.getValue().toString());
				publisher.publishEvent(entry.getValue());
			}
		}
	}

	public void batchSaveDuration(final List<BondDuration> durationList) {

		if (durationList.isEmpty())
			return;
		try {
			StringBuilder sqlStr = new StringBuilder();
			List<Object> params = new ArrayList<>();
			sqlStr.append(
					"insert into dmdb.t_bond_duration (bond_uni_code, bond_short_name, yield, macd, modd, convexity, duration_date) values ");
	
			for (BondDuration d : durationList) {
				sqlStr.append(" (?, ?, ?, ?, ?, ?, ?) ,");
				params.add(d.getDurationKey().getBondUniCode());
				params.add(d.getBondShortName());
				params.add(d.getYield().toString());
				params.add(d.getMacd().toString());
				params.add(d.getModd().toString());
				params.add(d.getConvexity().toString());
				params.add(new SimpleDateFormat("yyyy-MM-dd").format(d.getDurationKey().getDurationDate()));
			}
			sqlStr.setLength(sqlStr.length() - 1);
			jdbcTemplate.update(sqlStr.toString(), params.toArray());
		}
		catch(Exception ex) {
			LOG.error("failed to batchSaveDuration", ex);
		}
	}

	private void batchSaveStaticSpread(List<BondSpread> spreadList) {
		if (spreadList.isEmpty())
			return;
		try {
			StringBuilder sqlStr = new StringBuilder();
			List<Object> params = new ArrayList<>();
			sqlStr.append(
					" insert into dmdb.t_bond_spread (bond_uni_code, bond_short_name, static_spread, update_date, static_spread_indu_quartile, static_spread_indu_quartile_sw) values ");
	
			for (BondSpread d : spreadList) {
				sqlStr.append(" (?, ?, ?, ?, ?, ?) ,");
				params.add(d.getStaticSpreadKey().getBondUniCode());
				params.add(d.getBondShortName());
				params.add(d.getStaticSpread().toString());
				params.add(new SimpleDateFormat("yyyy-MM-dd").format(d.getStaticSpreadKey().getUpdateDate()));
				params.add(d.getStaticSpreadInduQuartile());
				params.add(d.getStaticSpreadInduQuartileSw());
			}
			sqlStr.setLength(sqlStr.length() - 1);
			jdbcTemplate.update(sqlStr.toString(), params.toArray());
		}
		catch(Exception ex) {
			LOG.error("failed to batchSaveDuration", ex);
		}
	}

	private void buildBondInfoChanges(List<BondEstYtm> newEstYtms, Map<Long, FundamentalIndicator> diffIndicatorMap) {
		for (BondEstYtm newEstYtm : newEstYtms) {
			try {
				getFundamentalIndicator(diffIndicatorMap, newEstYtm.getBondCode())
						.setEstCleanPrice(newEstYtm.getEstCleanPrice());
				getFundamentalIndicator(diffIndicatorMap, newEstYtm.getBondCode())
						.setEstYield(newEstYtm.getRate());
				getFundamentalIndicator(diffIndicatorMap, newEstYtm.getBondCode())
						.setOptionYield(newEstYtm.getOptionYield());
			}
			catch (Exception ex) {
				LOG.info("buildBondInfoChanges error." , ex);
			}
		}
	}

	private void buildDurationChanges(List<BondDuration> newDurations,
			Map<Long, FundamentalIndicator> diffIndicatorMap) {
		for (BondDuration newDuration : newDurations) {
			try {
				getFundamentalIndicator(diffIndicatorMap, newDuration.getDurationKey().getBondUniCode())
						.setMacd(nullSafeBigDecimal2Double(newDuration.getMacd()));
				getFundamentalIndicator(diffIndicatorMap, newDuration.getDurationKey().getBondUniCode())
						.setModd(nullSafeBigDecimal2Double(newDuration.getModd()));
				getFundamentalIndicator(diffIndicatorMap, newDuration.getDurationKey().getBondUniCode())
						.setConvexity(nullSafeBigDecimal2Double(newDuration.getConvexity()));
			}
			catch (Exception ex) {
				LOG.info("buildDurationChanges error." , ex);
			}
		}
	}

	private Double nullSafeBigDecimal2Double(BigDecimal v)
	{
		return (v == null)? null : v.doubleValue();
	}
	
	private void buildSpreadChanges(List<BondSpread> newSpeads, Map<Long, FundamentalIndicator> diffIndicatorMap) {
		for (BondSpread newSpead : newSpeads) {
			try {
				getFundamentalIndicator(diffIndicatorMap, newSpead.getStaticSpreadKey().getBondUniCode())
					.setStaticSpread(nullSafeBigDecimal2Double(newSpead.getStaticSpread()));
			}
			catch (Exception ex) {
				LOG.info("buildSpreadChanges error." , ex);
			}
		}
	}

	private synchronized FundamentalIndicator getFundamentalIndicator(
			Map<Long, FundamentalIndicator> fundamentalIndicators, Long bondUniCode) {
		if (!fundamentalIndicators.containsKey(bondUniCode)) {
			fundamentalIndicators.put(bondUniCode, new FundamentalIndicator());
		}
		return fundamentalIndicators.get(bondUniCode);
	}

	private void insertNewDuration(BondDuration newDuration) {
		jdbcTemplate.update(
				"insert into dmdb.t_bond_duration (bond_uni_code, bond_short_name, yield, macd, modd, duration_date) values (?, ?, ?, ?, ?, ?)",
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setLong(1, newDuration.getDurationKey().getBondUniCode());
						ps.setString(2, newDuration.getBondShortName());
						ps.setString(3, newDuration.getYield().toString());
						ps.setString(4, newDuration.getMacd().toString());
						ps.setString(5, newDuration.getModd().toString());
						ps.setString(6, date2String(newDuration.getDurationKey().getDurationDate()));
					}
				});
	}

	private String date2String(Date d) {
		return new SimpleDateFormat("yyyy-MM-dd").format(d);
	}

	private class SpreadStatistics {

		final static boolean GCIS = true;
		final static boolean SW = false;

		public class InduSpreadStatistics {
			private boolean isGics = false;
			private Map<Long, List<BondSpread>> induSpreadSet = new HashMap<Long, List<BondSpread>>();

			public InduSpreadStatistics(boolean isGics) {
				this.isGics = isGics;
			}

			public void addSpread(BondBasicInfoDoc bond, BondSpread zSpread) {
				Long induId = isGics ? bond.getInduId() : bond.getInduIdSw();
				if (induId == null) {
					LOG.error(
							"bond doesn't have induId, bond_uni_code:" + bond.getBondUniCode() + ", isGics:" + isGics);
					return;
				}
				if (!induSpreadSet.containsKey(induId)) {
					induSpreadSet.put(induId, new ArrayList<BondSpread>());
				}
				List<BondSpread> induSpreadList = induSpreadSet.get(induId);
				induSpreadList.add(zSpread);
			}

			public void analyzeInduSpreadSet(List<BondSpread> induSpreadSet) {

				Collections.sort(induSpreadSet, new Comparator<BondSpread>() {
					@Override
					public int compare(BondSpread lhs, BondSpread rhs) {
						return ObjectUtils.compare(lhs.getStaticSpread(), rhs.getStaticSpread());
					}
				});

				int size = induSpreadSet.size();
				for (int i = 0; i < size; ++i) {
					BondSpread bondSpread = induSpreadSet.get(i);
					if (isGics)
						bondSpread.setStaticSpreadInduQuartile(i * 100 / size + 1);
					else
						bondSpread.setStaticSpreadInduQuartileSw(i * 100 / size + 1);
				}
			}

			public void analyze() {
				Iterator it = induSpreadSet.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					analyzeInduSpreadSet((List<BondSpread>) pair.getValue());
					it.remove(); // avoids a ConcurrentModificationException
				}
			}
		}

		private InduSpreadStatistics[] InduSpreadStatisticsList = { new InduSpreadStatistics(GCIS),
				new InduSpreadStatistics(SW) };

		public void addSpread(BondBasicInfoDoc bond, BondSpread zSpread) {
			for (InduSpreadStatistics statis : InduSpreadStatisticsList) {
				statis.addSpread(bond, zSpread);
			}
		}

		public void analyze() {
			for (InduSpreadStatistics statis : InduSpreadStatisticsList) {
				statis.analyze();
			}
		}
	}

	private boolean isFieldChanged(Comparable org, Comparable cur) {
		// 按新需求，无论是否有变化，无条件给投组推送
		return true;
		// if (org == null && cur != null) return true;
		// else return (org != null && cur != null && org.compareTo(cur) != 0);
	}

}
