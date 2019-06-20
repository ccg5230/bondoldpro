package com.innodealing.model.req;

import java.io.Serializable;
import java.util.List;

import com.innodealing.model.vo.CreditRatingBasic;

public class CreditRatingGroupchangeReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CreditRatingBasic> dataList;

	public List<CreditRatingBasic> getDataList() {
		return dataList;
	}

	public void setDataList(List<CreditRatingBasic> dataList) {
		this.dataList = dataList;
	}

}
