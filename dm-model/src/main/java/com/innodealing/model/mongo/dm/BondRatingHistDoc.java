package com.innodealing.model.mongo.dm;

import java.io.Serializable;
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
@Document(collection="bond_rating_hist")
public class BondRatingHistDoc implements Serializable {
	
	@Id
	private Integer id;
	
	@ApiModelProperty(value = "债券id")
	private Integer bond_uni_code;
  
	@ApiModelProperty(value = "评级")
	private String rating;
	
	@ApiModelProperty(value = "评级时间")
	private Date time;
	
	@ApiModelProperty(value = "评级机构")
	private String insu;
}
