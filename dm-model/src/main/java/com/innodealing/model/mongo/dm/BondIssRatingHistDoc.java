package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * 债项评级历史
 *
 */
@JsonInclude(Include.NON_NULL)
@Document(collection="bond_iss_rating_hist")
public class BondIssRatingHistDoc implements Serializable {
	
	@Id
	private Integer id;
	
	@ApiModelProperty(value = "发行人id")
	private Integer com_uni_code;
  
	@ApiModelProperty(value = "评级")
	private String rating;
	
	@ApiModelProperty(value = "评级时间")
	private Date time;
	
	@ApiModelProperty(value = "评级机构")
	private String insu;
}
