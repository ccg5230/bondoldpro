package com.innodealing.tasklet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.innodealing.engine.mongo.bond.ComRiskRepository;
import com.innodealing.model.mongo.dm.ComRiskColumnDoc;
import com.innodealing.model.mongo.dm.ComRiskDoc;
import com.innodealing.processor.ComRiskRecordProcessor;
import com.innodealing.uilogic.UIAdapter;

public class ComRiskRefreshTasklet  implements Tasklet {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComRiskRefreshTasklet.class);
	
	@Autowired
	protected ComRiskRepository bondRiskRepository;
	
	private ComRiskRecordProcessor comRiskRecordProcessor = new ComRiskRecordProcessor();

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		refresh();
		return RepeatStatus.FINISHED;
	}
	
	/**
	 * 重置风险评价对比数据
	 * 
	 * @return
	 */
	public String refresh() {

		LOGGER.info("重置风险评价对比数据 begin....");

		// 清除旧数据
		bondRiskRepository.deleteAll();

		bondRiskRepository.insert(getBondRiskDocList(2017));

		LOGGER.info("重置风险评价对比数据 end....");

		return null;
	}
	
	/**
	 * 初始化数据
	 * 
	 * @param list
	 * @param year
	 * @return
	 */
	private List<ComRiskDoc> getBondRiskDocList(Integer year) {

		List<ComRiskDoc> list = new ArrayList<ComRiskDoc>();

		for (int i = 1; i <= 7; i++) {
			list.add(initBondDealTodayStatsDoc(comRiskRecordProcessor.getComRiskDoc(i, "", year, true, null), comRiskRecordProcessor.getComRiskDocId(i, "", year)));
		}

		Integer level = null;
		for (String rating : UIAdapter.getRiskMap().keySet()) {
			level = UIAdapter.getRiskMap().get(rating);
			list.add(initBondDealTodayStatsDoc(comRiskRecordProcessor.getComRiskDoc(level, rating, year, false, null),
					comRiskRecordProcessor.getComRiskDocId(level, rating, year)));
		}

		return list;
	}
	
	/**
	 * 初始化每列
	 * 
	 * @param doc
	 * @param id
	 * @return
	 */
	private ComRiskDoc initBondDealTodayStatsDoc(ComRiskDoc doc, String id) {
		ComRiskColumnDoc statsDoc = null;
		Method method = null;
		for (int i = 1; i <= 9; i++) {
			try {
				method = ComRiskDoc.class.getMethod("setColumn" + i, ComRiskColumnDoc.class);
				statsDoc = new ComRiskColumnDoc();
				statsDoc.setCondition(id + "*" + "getColumn" + i);// 查询条件
				method.invoke(doc, statsDoc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return doc;
	}

}
