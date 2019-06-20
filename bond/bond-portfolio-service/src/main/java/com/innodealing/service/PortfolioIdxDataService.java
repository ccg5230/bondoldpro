package com.innodealing.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;
import com.innodealing.domain.enums.FavRadarSchemaEnum;
import com.innodealing.engine.jdbc.bond.PortfolioIdxDataDao;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondFavFinaIdxDocRepository;
import com.innodealing.engine.mongo.bond.BondFavMaturityIdxDocRepository;
import com.innodealing.engine.mongo.bond.BondFavOtherIdxDocRepository;
import com.innodealing.engine.mongo.bond.BondFavPriceIdxDocRepository;
import com.innodealing.engine.mongo.bond.BondFavRatingIdxDocRepository;
import com.innodealing.engine.mongo.bond.BondFavSentimentIdxDocRepository;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondFavFinaIdxDoc;
import com.innodealing.model.mongo.dm.BondFavMaturityIdxDoc;
import com.innodealing.model.mongo.dm.BondFavOtherIdxDoc;
import com.innodealing.model.mongo.dm.BondFavPriceIdxDoc;
import com.innodealing.model.mongo.dm.BondFavRatingIdxDoc;
import com.innodealing.model.mongo.dm.BondFavSentimentIdxDoc;
import com.innodealing.model.mongo.dm.BondFavoriteRadarMappingDoc;
import com.innodealing.util.StringUtils;
import com.innodealing.utils.ExpressionUtil;

@Service
public class PortfolioIdxDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioIdxDataService.class);

	private static final String MATURITY = "MATURITY";
	private static final String RATING = "RATING";
	private static final String SENTIMENT = "SENTIMENT";
	private static final String OTHER = "OTHER";


	private Map<String, MongoRepository> idxRepMap;

	@PostConstruct
	public void init() {
		idxRepMap = new LinkedHashMap<String, MongoRepository>();
		idxRepMap.put(MATURITY, favMaturityIdxRep);
		idxRepMap.put(RATING, favRatingIdxRep);
		idxRepMap.put(SENTIMENT, favSensIdxRep);
		idxRepMap.put(OTHER, favOtherIdxRep);
	}

	@Autowired
	private BondBasicInfoRepository bondBasicInfoRep;

	@Autowired
	private PortfolioIdxDataDao portfolioIdxDataDao;

	@Autowired
	private BondFavMaturityIdxDocRepository favMaturityIdxRep;

	@Autowired
	private BondFavRatingIdxDocRepository favRatingIdxRep;

	@Autowired
	private BondFavSentimentIdxDocRepository favSensIdxRep;

	@Autowired
	private BondFavPriceIdxDocRepository favPriceIdxRep;

	@Autowired
	private BondFavFinaIdxDocRepository favFinaIdxRep;
	
	@Autowired
	private BondFavOtherIdxDocRepository favOtherIdxRep;
	
	@Autowired
	private RedisTemplate redisTemplate;

	public String buildFavRatingIdx() {
		favRatingIdxRep.deleteAll();

		int count = portfolioIdxDataDao.getBondFavRadarMappingCount(FavRadarSchemaEnum.ISSU_RAT.getCode(),
				FavRadarSchemaEnum.IMPL_RAT.getCode());
		int limit = 1000;
		int num = count / limit + 1;

		ExecutorService exec = Executors.newFixedThreadPool(5);
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					saveBondFavRatingIdxByPage(limit, workingPage, RATING);
				}
			});
		}

		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("buildFavRatingIdx end...");
		return "done";
	}

	protected void saveBondFavRatingIdxByPage(int limit, int index, String repKey) {
		List<BondFavRatingIdxDoc> entityList = portfolioIdxDataDao.findBondFavRadarIdxList(index * limit, limit,
				FavRadarSchemaEnum.ISSU_RAT.getCode(), FavRadarSchemaEnum.IMPL_RAT.getCode(),
				BondFavRatingIdxDoc.class);
		
		if (null != entityList && entityList.size() > 0) {
			entityList.parallelStream().forEach(favRatingIdx -> {
				Long bondUniCode = favRatingIdx.getBondUniCode();

				saveIdxEntity(repKey, favRatingIdx, bondUniCode);
			});
		}
	}

	public String buildFavPriceIdx() {
		favPriceIdxRep.deleteAll();

		int count = portfolioIdxDataDao.getBondFavPriceIdxCount();
		int limit = 1000;
		int num = count / limit + 1;

		ExecutorService exec = Executors.newFixedThreadPool(5);
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					saveBondFavPriceIdxByPage(limit, workingPage);
				}
			});
		}

		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("buildFavPriceIdx end...");
		return "done";
	}

	protected void saveBondFavPriceIdxByPage(int limit, int index) {
		List<BondFavPriceIdxDoc> entites = portfolioIdxDataDao.findBondFavPriceIdxList(index * limit, limit);
		if (null != entites && entites.size() > 0) {
			favPriceIdxRep.save(entites);
		}
	}

	public String buildFavFinIdx() {
		favFinaIdxRep.deleteAll();

		int count = portfolioIdxDataDao.getBondFavFinIdxCount();
		int limit = 1000;
		int num = count / limit + 1;

		ExecutorService exec = Executors.newFixedThreadPool(30);
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					saveBondFavFinIdxByPage(limit, workingPage);
				}
			});
		}

		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("buildFavFinIdx end...");
		return "done";
	}

	protected void saveBondFavFinIdxByPage(int limit, int index) {
		List<BondFavFinaIdxDoc> entites = portfolioIdxDataDao.findBondFavFinIdxList(index * limit, limit);
		if (null != entites && entites.size() > 0) {
			entites.parallelStream().forEach(favFinaIdx -> {
				if (!StringUtils.isBlank(favFinaIdx.getIndexCodeExpr())) {
					favFinaIdx.setVariables(ExpressionUtil.extractFieldsInExpression(favFinaIdx.getIndexCodeExpr()));
				}
				BondBasicInfoDoc bondBasicInfo = bondBasicInfoRep.findOne(favFinaIdx.getBondUniCode());
				if (null != bondBasicInfo) {
					favFinaIdx.setComUniCode(bondBasicInfo.getIssuerId());
					favFinaIdxRep.save(favFinaIdx);
				}
			});
		}
	}

	public String buildFavMaturityIdx() {
		favMaturityIdxRep.deleteAll();

		int count = portfolioIdxDataDao.getBondFavRadarMappingCount(FavRadarSchemaEnum.YEAR_PAY.getCode(),
				FavRadarSchemaEnum.EXER_PAY.getCode());
		int limit = 1000;
		int num = count / limit + 1;

		ExecutorService exec = Executors.newFixedThreadPool(5);
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					saveBondFavMaturityIdxByPage(limit, workingPage, MATURITY);
				}
			});
		}

		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("buildFavMaturityIdx end...");
		return "done";
	}

	protected void saveBondFavMaturityIdxByPage(int limit, int index, String repKey) {
		List<BondFavMaturityIdxDoc> entityList = portfolioIdxDataDao.findBondFavRadarIdxList(index * limit, limit,
				FavRadarSchemaEnum.YEAR_PAY.getCode(), FavRadarSchemaEnum.EXER_PAY.getCode(),
				BondFavMaturityIdxDoc.class);
		
		if (null != entityList && entityList.size() > 0) {
			entityList.parallelStream().forEach(favMaturityIdx -> {
				Long bondUniCode = favMaturityIdx.getBondUniCode();

				saveIdxEntity(repKey, favMaturityIdx, bondUniCode);
			});
		}
	}

	private void saveIdxEntity(String repKey, BondFavoriteRadarMappingDoc entity, Long bondUniCode) {
		BondBasicInfoDoc bondBasicInfo = bondBasicInfoRep.findOne(bondUniCode);
		if (null != bondBasicInfo) {
			entity.setComUniCode(bondBasicInfo.getIssuerId());
			idxRepMap.get(repKey).save(entity);
		}
	}

	public String buildFavSentimentIdx() {
		favSensIdxRep.deleteAll();

		int count = portfolioIdxDataDao.getBondFavRadarMappingCount(FavRadarSchemaEnum.ANOUNCE_SENS.getCode(),
				FavRadarSchemaEnum.COMLGN_SENS.getCode());
		int limit = 1000;
		int num = count / limit + 1;

		ExecutorService exec = Executors.newFixedThreadPool(5);
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					saveBondFavSentimentIdxByPage(limit, workingPage, SENTIMENT);
				}
			});
		}

		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("buildFavSentimentIdx end...");
		return "done";
	}

	protected void saveBondFavSentimentIdxByPage(int limit, int index, String repKey) {
		List<BondFavSentimentIdxDoc> entityList = portfolioIdxDataDao.findBondFavRadarIdxList(index * limit, limit,
				FavRadarSchemaEnum.ANOUNCE_SENS.getCode(), FavRadarSchemaEnum.COMLGN_SENS.getCode(),
				BondFavSentimentIdxDoc.class);

		if (null != entityList && entityList.size() > 0) {
			entityList.parallelStream().forEach(favSensIdx -> {
				Long bondUniCode = favSensIdx.getBondUniCode();

				saveIdxEntity(repKey, favSensIdx, bondUniCode);
			});
		}
	}

	public String initUserDebtRole() {
		List<Integer> idList = portfolioIdxDataDao.findDebtRoleUserIds();
		idList.stream().forEach(userId -> {
			redisTemplate.opsForValue().set(Constants.DM_USER_DEBTROLE+userId, "1");
		});
		return "done";
	}

	public String buildFavOtherIdx() {
		favOtherIdxRep.deleteAll();

		int count = portfolioIdxDataDao.getBondFavRadarMappingCount(FavRadarSchemaEnum.FIN_DISCLOSURE.getCode(),
				FavRadarSchemaEnum.UNCOLLECTED_EARNINGRPT.getCode());
		int limit = 1000;
		int num = count / limit + 1;

		ExecutorService exec = Executors.newFixedThreadPool(5);
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					saveBondFavOtherIdxByPage(limit, workingPage, OTHER);
				}
			});
		}

		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("buildFavOtherIdx end...");
		return "done";
	
	}

	protected void saveBondFavOtherIdxByPage(int limit, int index, String repKey) {
		List<BondFavOtherIdxDoc> entityList = portfolioIdxDataDao.findBondFavRadarIdxList(index * limit, limit,
				FavRadarSchemaEnum.FIN_DISCLOSURE.getCode(), FavRadarSchemaEnum.UNCOLLECTED_EARNINGRPT.getCode(),
				BondFavOtherIdxDoc.class);
		
		if (null != entityList && entityList.size() > 0) {
			entityList.parallelStream().forEach(favOtherIdx -> {
				Long bondUniCode = favOtherIdx.getBondUniCode();

				saveIdxEntity(repKey, favOtherIdx, bondUniCode);
			});
		}
	}
}
