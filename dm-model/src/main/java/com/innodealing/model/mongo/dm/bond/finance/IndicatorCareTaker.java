package com.innodealing.model.mongo.dm.bond.finance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;


/**
 * 备忘录持有者
 * @author 趙正來 
 *
 */
@Document(collection =  "iss_indicator_care_taker")
public class IndicatorCareTaker implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主体唯一标识
	 */
	@Id
	private Long comUniCode;
	
	private LinkedList<IndicatorMemento> mementoList = new LinkedList<>();
	
	
	
	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public LinkedList<IndicatorMemento> getMementoList() {
		return mementoList;
	}

	public void setMementoList(LinkedList<IndicatorMemento> mementoList) {
		this.mementoList = mementoList;
	}

	public void add(IndicatorMemento memento){
		mementoList.addFirst(memento);
	}
	
	public IndicatorMemento get(int index){
		if(mementoList == null){
			return null;
		}
		return mementoList.get(index);
	}
	
	/**
	 * 获取最新数据
	 * @param finDate
	 * @return
	 */
	public IndicatorMemento get(String finDate){
		IndicatorMemento indicatorMemento = null;
		for (IndicatorMemento m : mementoList) {
			if(Objects.equal(finDate, m.getState())){
				indicatorMemento = m;
				break;
			}
		}
		return indicatorMemento;
	}
	
	/**
	 * 获取第二次出现的数据
	 * @param finDate
	 * @return
	 */
	public IndicatorMemento getSecond(String finDate){
		IndicatorMemento indicatorMemento = null;
		int index = 0;
		for (IndicatorMemento m : mementoList) {
			if(Objects.equal(finDate, m.getState())){
				index++;
				if(index == 1){
					indicatorMemento = m;
					break;
				}
			}
		}
		return indicatorMemento;
	}
	
	/**
	 * 获取第二次出现的数据
	 * @param finDate
	 * @return
	 */
	public IndicatorMemento getYoy(String finDate){
		IndicatorMemento indicatorMemento = null;
		if(finDate != null){
			String yoyDate = Integer.valueOf(finDate.substring(0,4))-1  + finDate.substring(4,finDate.length());
			for (IndicatorMemento m : mementoList) {
				if(Objects.equal(yoyDate, m.getState())){
					indicatorMemento = m;
					break;
				}
			}
		}
		return indicatorMemento;
	}
	
	
	public String getLastFinDate(){
		List<String> dates = new ArrayList<>();
		for (IndicatorMemento m : mementoList) {
			if(m != null){
				dates.add(m.getState());
			}
		}
		if(dates.size() > 0){
			dates.sort(new Comparator<String>() {
				@Override
				public int compare(String arg0, String arg1) {
					return arg1.compareTo(arg0);
				}
			});
			return dates.get(0);
		}
		return null;
	}
	public static void main(String[] args) {
		String finDate = "2016-12-31";
		String yoyDate = Integer.valueOf(finDate.substring(0,4)) -1  + finDate.substring(4,finDate.length());
		System.out.println(yoyDate);
		List<String> dates = new ArrayList<>();
		dates.add("2016-12-31");
		dates.add("2015-12-31");
		if(dates.size() > 0){
			dates.sort(new Comparator<String>() {
				@Override
				public int compare(String arg0, String arg1) {
					return arg1.compareTo(arg0);
				}
			});
		}
		System.out.println(dates.get(0));
	}
	
}