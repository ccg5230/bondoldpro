package com.innodealing.model.req;

import java.io.Serializable;
import java.util.List;

import com.innodealing.model.vo.CreditRatingParsedBasic;

public class CreditRatingImportReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CreditRatingParsedBasic> data;

	public List<CreditRatingParsedBasic> getData() {
		return data;
	}

	public void setData(List<CreditRatingParsedBasic> data) {
		this.data = data;
	}
	
}
