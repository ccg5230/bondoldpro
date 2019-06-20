package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_field_group_mapping_doc")
public class BondFieldGroupMappingDoc implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ApiModelProperty(value = "列")
    private String columnName;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "列名")
    private String fieldName;

    @ApiModelProperty(value = "分组")
    private String groupName;

    @ApiModelProperty(value = "1-率正向 2-率负向 3-值正向 4-值负向")
    private Integer fieldType;

    @ApiModelProperty(value = "是否是百分比(率)1百分比 , 默认0 否")
    private Integer percent;

    @ApiModelProperty(value = "上级分类")
    private String groupNameParent;

    @ApiModelProperty(value = "指标单位")
    private String company;

    @ApiModelProperty(value = "指标单位类型")
    private Integer companyType;

    @ApiModelProperty(value = "1.市场指标2.专项指标3.资产负债4.利润5现金流量")
    private Integer type;

    public BondFieldGroupMappingDoc() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BondFieldGroupMappingDoc(String columnName, String fieldName, Integer percent, Integer type,Integer companyType) {
        super();
        this.columnName = columnName;
        this.fieldName = fieldName;
        this.percent = percent;
        this.type = type;
        this.companyType = companyType;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getCompany() {
        return company;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupNameParent() {
        return groupNameParent;
    }

    public String getId() {
        return id;
    }

    public Integer getPercent() {
        return percent;
    }

    public String getTableName() {
        return tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupNameParent(String groupNameParent) {
        this.groupNameParent = groupNameParent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

}
