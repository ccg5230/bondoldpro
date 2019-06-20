package com.innodealing.model.mongo.dm.bond.finance;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Objects;

/**
 * 状态的发起者
 * 
 * @author 赵正来
 *
 */
public class IndicatorOriginator {

	/**
	 * 财报时间
	 */
	private String state; 
	
	/**
	 * 指标快照集合
	 */
	private LinkedList<IndicatorSnapshot> list;
	
	public void setState(String state, LinkedList<IndicatorSnapshot> list){
		this.state = state;
		this.list = list;
	}
	
	public IndicatorMemento saveStateToMemento(){
		return new IndicatorMemento(state, list, new Date());
	}
	
	/**
	 * 获取备忘录中的某一指标信息
	 * @param memento
	 * @param field
	 * @return
	 */
	public IndicatorSnapshot getStateFromIndicatorMemento(IndicatorMemento memento, String field){
		List<IndicatorSnapshot> indicators =  memento.getList();
		for (IndicatorSnapshot indicatorChange : indicators) {
			if(Objects.equal(indicatorChange.getField(), field)){
				return indicatorChange;
			}
		}
		return null;
	}
}