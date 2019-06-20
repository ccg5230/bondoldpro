package com.innodealing.model.mongo.dm.bond.finance;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Objects;

/**
 * 存储状态
 * 
 * @author 赵正来
 *
 */
public class IndicatorMemento {

	/**
	 * 财报时间
	 */
	private String state;

	/**
	 * 更新是时间
	 */
	private Date updateTime;

	/**
	 * 指标
	 */
	private LinkedList<IndicatorSnapshot> list;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<IndicatorSnapshot> getList() {
		return list;
	}

	public void setList(LinkedList<IndicatorSnapshot> list) {
		this.list = list;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public IndicatorMemento() {
		super();
	}

	public IndicatorSnapshot getIndicatorSnapshot(String field){
		for (IndicatorSnapshot snapshot : list) {
			if(Objects.equal(snapshot.getField(), field)){
				return snapshot;
			}
		}
		return null;
	}
	
	public IndicatorMemento(String state, LinkedList<IndicatorSnapshot> list, Date updateTime) {
		super();
		this.state = state;
		this.list = list;
		this.updateTime = updateTime;
	}

	
	
}
