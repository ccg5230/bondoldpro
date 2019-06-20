package com.innodealing.bond.service.rrs;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;

public class BondDocLayout {

	private static final Logger LOG = LoggerFactory.getLogger(BondDocLayout.class);
	
	private String title ;
	private List<String> data = new ArrayList<String>();
	
	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return title; }
	
	public BondDocLayout addLine(String...ratios){
		String line = "";
		for(int i = 0; i < ratios.length; ++i) {
			if (!StringUtils.isEmpty(ratios[i]))
				line += ratios[i];
		}
		if (!StringUtils.isEmpty(line))
			data.add(String.format(" (%1$d) %2$s", data.size()+1, line));
		return this;
	}
	
	public void copy(BondIssDMRatingSummaryVO vo)
	{
		try {
			vo.setRatioTitle(getTitle());
			vo.setRatioContentList(data);
			/*
			for (int i = 0; i < data.size(); ++i) {
				Method method = vo.getClass().getDeclaredMethod("setRatio" + (i+1), String.class);
				if (method != null) {
					method.invoke(vo, data.get(i));
				}
			}
			*/
		}
		catch(Exception ex) {
			LOG.error("BondIssDMRatingSummaryVO clone failed", ex);
		}
	}
	
	public String toDoc()
	{
		String content = title;
		for(String s : data) {
			content += s;
			content += "\n";
		}
		return content;
	}
}
