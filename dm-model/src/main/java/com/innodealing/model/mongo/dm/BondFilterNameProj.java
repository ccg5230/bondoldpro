package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@Document
@JsonInclude(Include.NON_NULL)
public class BondFilterNameProj  implements Serializable {

	@ApiModelProperty(value = "筛选id")
	private String filterId;
	
	@ApiModelProperty(value = "筛选方案名")
	private String filterName; 

}
