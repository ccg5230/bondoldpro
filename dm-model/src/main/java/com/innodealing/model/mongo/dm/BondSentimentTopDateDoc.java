package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_sentiment_date_top")
public class BondSentimentTopDateDoc extends BondSentimentTopDoc implements Serializable {
	
	@Id
	@ApiModelProperty(value = "id")
	private String id;
    
    @ApiModelProperty(value = "发行时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date pubDate;
    
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String str = new SimpleDateFormat("yyyy-MM-dd").format(pubDate);
		return "BondSentimentTopDateDoc [pubDate=" + str + ", getComUniCode()=" + getComUniCode() + "]";
	}

    
}
