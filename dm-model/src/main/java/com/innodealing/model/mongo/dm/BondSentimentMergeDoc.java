package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_sentiment_news")
public class BondSentimentMergeDoc extends BondSentimentDoc implements Serializable {

	@Indexed
    @ApiModelProperty(value = "1：重复0：不重复 99:无效")
	private Long reduplicated;
	
	@Indexed
    @ApiModelProperty(value = "是否启用 1启用 0不启用")
	private Long display;

	public Long getDisplay() {
		return display;
	}

	public void setDisplay(Long display) {
		this.display = display;
	}

	public Long getReduplicated() {
		return reduplicated;
	}

	public void setReduplicated(Long reduplicated) {
		this.reduplicated = reduplicated;
	}
	
	

}
