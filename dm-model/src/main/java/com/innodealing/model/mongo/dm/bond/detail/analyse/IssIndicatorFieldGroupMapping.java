package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * 发行人性能指标字段分组映射
 * @author zhaozhenglai
 * @since 2016年9月20日 上午10:31:48 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection = "bond_indicator_field_group_mapping")
public class IssIndicatorFieldGroupMapping implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "列")
    private String columnName;
    
    @ApiModelProperty(value = "列名")
    private String fieldName;
    
    @ApiModelProperty(value = "所属表")
    private String tableName;
    
    @ApiModelProperty(value = "所属组")
    private String groupName;
    
    @ApiModelProperty(value = "所属组id")
    private String groupId;
    
    @ApiModelProperty(value = "1-率正向 2-率负向  3-值正向 4-值负向")
    private int fieldType;
    
    @ApiModelProperty("指标是否为百分比的小数，1是、0否")
    private int percent;
    
   

    public String getColumnName() {
        return columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
    
}
