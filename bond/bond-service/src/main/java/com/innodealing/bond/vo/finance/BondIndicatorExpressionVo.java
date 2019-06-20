package com.innodealing.bond.vo.finance;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/** 
* @Title: BondIndicatorExpressionVo.java 
* @Package com.innodealing.bond.vo.finance 
* @Description: 专项指标计算公式VO
* <p>Copyright: Copyright (c) 2017</p> *
* <p>Company: Dealing Matrix</p> *
* @author chungaochen
* @date 2017年5月11日 下午3:06:24 
* @version V1.0 
*/
public class BondIndicatorExpressionVo implements Serializable{

	/** 
	* @Fields serialVersionUID :
	*/ 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	private Long id;
	
	@ApiModelProperty(value = "表名")
	private String tableName;
	
	@ApiModelProperty(value = "指标代码")
	private String field;
	
	@ApiModelProperty(value = "指标名称")
	private String fieldName;
	
	@ApiModelProperty(value = "表达式")
	private String expression;
	
	@ApiModelProperty(value = "备注说明")
	private String remark;
	
	@ApiModelProperty(value = "表达式格式化")
	private String expressFormat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExpressFormat() {
		return expressFormat;
	}

	public void setExpressFormat(String expressFormat) {
		this.expressFormat = expressFormat;
	}

	@Override
	public String toString() {
		return "BondIndicatorExpressionVo [id=" + id + ", tableName=" + tableName + ", field=" + field + ", fieldName="
				+ fieldName + ", expression=" + expression + ", remark=" + remark + ", expressFormat=" + expressFormat
				+ "]";
	}

	
}
