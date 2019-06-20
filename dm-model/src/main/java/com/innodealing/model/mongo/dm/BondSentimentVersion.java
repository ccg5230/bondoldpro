package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_sentiment_version")
public class BondSentimentVersion implements Serializable {

	@Id
	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "版本号")
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public BondSentimentVersion(Long id, Long version) {
		this.id = id;
		this.version = version;
	}

	public BondSentimentVersion() {
	}

}
