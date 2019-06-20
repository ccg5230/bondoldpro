package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lihao
 * @date 2016年9月11日
 * @ClassName BondYildCurvesName
 * @Description: 债券编号和名称结果集Model
 */
@JsonInclude(Include.NON_NULL)
@Document(collection="t_pub_par")
public class BondPubPar implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name="曲线编号")
	private Integer curveId;
	
	@ApiModelProperty(value="债券曲线名称")
	private String name;

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
