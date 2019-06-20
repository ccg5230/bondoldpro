package com.innodealing.processor;

import java.lang.reflect.Method;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.innodealing.engine.mongo.bond.ComRiskRepository;
import com.innodealing.model.mongo.dm.ComRiskColumnDoc;
import com.innodealing.model.mongo.dm.ComRiskComInfo;
import com.innodealing.model.mongo.dm.ComRiskDoc;
import com.innodealing.uilogic.UIAdapter;

public class ComRiskRecordProcessor implements ItemProcessor<ComRiskComInfo, ComRiskDoc> {

	@Autowired
	protected ComRiskRepository comRiskRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComRiskRecordProcessor.class);

	@Override
	public ComRiskDoc process(ComRiskComInfo cominfo) throws Exception {
		
//		LOGGER.info("ComRiskRecordProcessor {}",cominfo);

		String rating = "";
		if (cominfo.getType() == null) {
			rating = cominfo.getRating(); // pd评级
		}

		if (!"".equals(rating) && !UIAdapter.Ratings.contains(rating)) {
			rating = "CCC及以下";
		}

		Integer level = cominfo.getLevel();// pd等级

		Integer year = cominfo.getPdTime(); // 年份

		String id = getComRiskDocId(level, rating, year);

		ComRiskDoc comRiskDoc = this.getBondDealTodayById(id);
		if (comRiskDoc == null) {
			comRiskDoc = new ComRiskDoc();
		}
		ComRiskColumnDoc columnDoc = null;

		// 列
		String column = getIssCredLevel(cominfo.getIssCredLevel());

		Method method = comRiskDoc.getClass().getDeclaredMethod("getColumn" + column);
		if (method != null) {
			columnDoc = (ComRiskColumnDoc) method.invoke(comRiskDoc);
		}else{
			columnDoc = new ComRiskColumnDoc();
		}
		method = ComRiskDoc.class.getMethod("setColumn" + getIssCredLevel(cominfo.getIssCredLevel()),
				ComRiskColumnDoc.class);

		// 统计数量
		if (columnDoc.getCount() == null) {
			columnDoc.setCount(1);
		} else {
			columnDoc.setCount(columnDoc.getCount() + 1);
		}
		columnDoc.getComUniCodeList().add(cominfo.getComUniCode()); // 发行人集合
		columnDoc.setCondition(id + "*" + "getColumn" + column);// 查询条件

		comRiskDoc = getComRiskDoc(level, rating, year, cominfo.getType() != null ? true : false, comRiskDoc);
		method.invoke(comRiskDoc, columnDoc);
		
		comRiskRepository.save(comRiskDoc);
		LOGGER.info("bondRiskRepository save {}",comRiskDoc.toString());

		return comRiskDoc;
	}

	/**
	 * 得到唯一主键
	 * 
	 * @param level
	 * @param rating
	 * @param pdTime
	 * @return
	 */
	public String getComRiskDocId(Integer level, String rating, Integer pdTime) {
		return pdTime + "_" + level + "_" + rating;
	}

	/**
	 * 查询列里面各自的属性
	 * 
	 * @param key
	 * @return
	 */
	public ComRiskDoc getBondDealTodayById(String key) {

		ComRiskDoc doc = null;

		doc = comRiskRepository.findOne(key);

		return doc;

	}

	/**
	 * 字段匹配
	 * 
	 * @param issCred
	 * @return
	 */
	public String getIssCredLevel(String issCred) {
		String issCredLevel = "";
		switch (issCred) {
		case "AAA":
			issCredLevel = "1";
			break;
		case "AAA-":
			issCredLevel = "2";
			break;
		case "AA+":
			issCredLevel = "3";
			break;
		case "AA":
			issCredLevel = "4";
			break;
		case "AA-":
			issCredLevel = "5";
			break;
		case "A+":
			issCredLevel = "6";
			break;
		case "A":
			issCredLevel = "7";
			break;
		case "A-":
			issCredLevel = "8";
			break;
		default:
			issCredLevel = "9";
			break;
		}
		return issCredLevel;
	}

	/**
	 * 构建对象
	 * 
	 * @param level
	 * @param rating
	 * @param pdTime
	 * @param isParent
	 * @return
	 */
	public ComRiskDoc getComRiskDoc(Integer level, String rating, Integer pdTime, Boolean isParent,
			ComRiskDoc doc) {

		String id = getComRiskDocId(level, rating, pdTime);
		// 顺序
		Integer sort = (Integer) UIAdapter.getPriorityAndName(rating, level).get("sort");
		// 名称
		String name = (String) UIAdapter.getPriorityAndName(rating, level).get("name");

		if (doc == null) {
			doc = new ComRiskDoc(id, BigInteger.valueOf(level), rating, sort, pdTime, isParent, name);
		} else {
			doc.setId(id);
			doc.setLevel(BigInteger.valueOf(level));
			doc.setRating(rating);
			doc.setPriority(sort);
			doc.setName(name);
			doc.setPdTime(pdTime);
			doc.setIsParent(isParent);
		}

		return doc;
	}

}
