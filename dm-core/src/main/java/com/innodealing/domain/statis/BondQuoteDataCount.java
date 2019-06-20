package com.innodealing.domain.statis;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class BondQuoteDataCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int count;
	private Date quoteDate;
	private int postfrom;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getQuoteDate() {
		return quoteDate;
	}

	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}

	public int getPostfrom() {
		return postfrom;
	}

	public void setPostfrom(int postfrom) {
		this.postfrom = postfrom;
	}

	public BondQuoteDataCount(@JsonProperty("count") int count, @JsonProperty("quoteDate") Date quoteDate,@JsonProperty("postfrom") int postfrom) {
		this.count = count;
		this.quoteDate = quoteDate;
		this.postfrom = postfrom;
	}
}
