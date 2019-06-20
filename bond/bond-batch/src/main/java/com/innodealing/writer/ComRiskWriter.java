package com.innodealing.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innodealing.engine.mongo.bond.ComRiskRepository;
import com.innodealing.model.mongo.dm.ComRiskDoc;

@Component
public class ComRiskWriter implements ItemWriter<ComRiskDoc>{
	
	@Autowired
//	protected ComRiskRepository comRiskRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComRiskWriter.class);

	@Override
	public void write(List<? extends ComRiskDoc> items) throws Exception {
		
		items.forEach(item->{
//			comRiskRepository.save(item);
//			LOGGER.info("bondRiskRepository save {}",item.toString());
		});
		
	}

}
