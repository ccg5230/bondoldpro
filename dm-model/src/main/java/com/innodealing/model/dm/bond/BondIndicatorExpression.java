package com.innodealing.model.dm.bond;

import java.io.Serializable;

import javax.persistence.Column;

import io.swagger.annotations.ApiModelProperty;

/** 
* @Title: BondIndicatorExpression.java 
* @Package com.innodealing.model.dm.bond 
* @Description: 专项指标计算表达式实体
* <p>Copyright: Copyright (c) 2017</p> *
* <p>Company: Dealing Matrix</p> *
* @author chungaochen
* @date 2017年5月11日 上午11:48:19 
* @version V1.0 
*/
public class BondIndicatorExpression implements Serializable {

	/** 
	* @Fields serialVersionUID :
	*/ 
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	@Column(name = "id")
	private Long id;
	
	@ApiModelProperty(value = "表名")
	@Column(name = "table_name")
	private String tableName;
	
	@ApiModelProperty(value = "指标代码")
	@Column(name = "field")
	private String field;
	
	@ApiModelProperty(value = "指标名称")
	@Column(name = "field_name")
	private String fieldName;
	
	@ApiModelProperty(value = "表达式")
	@Column(name = "expression")
	private String expression;
	
	@ApiModelProperty(value = "表达式说明")
    @Column(name = "express_description")
    private String expressDescription;
	
	@ApiModelProperty(value = "备注说明")
	@Column(name = "remark")
	private String remark;
	
	@ApiModelProperty(value = "表达式格式化")
	@Column(name = "express_format")
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

	
	public String getExpressDescription() {
        return expressDescription;
    }

    public void setExpressDescription(String expressDescription) {
        this.expressDescription = expressDescription;
    }

    @Override
    public String toString() {
        return "BondIndicatorExpression [id=" + id + ", tableName=" + tableName + ", field=" + field + ", fieldName=" + fieldName + ", expression=" + expression
                + ", expressDescription=" + expressDescription + ", remark=" + remark + ", expressFormat=" + expressFormat + "]";
    }
	
}
