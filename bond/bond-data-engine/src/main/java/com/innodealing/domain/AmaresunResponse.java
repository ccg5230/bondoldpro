package com.innodealing.domain;

import java.io.Serializable;

public class AmaresunResponse  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AmaresunResult result;

	/**
	 * @return the result
	 */
	public AmaresunResult getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(AmaresunResult result) {
		this.result = result;
	}
	
}
