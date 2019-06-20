//package com.innodealing.model.mongo.dm.bond.detail.analyse;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import com.innodealing.annotation.IndicatorType;
//
//import io.swagger.annotations.ApiModelProperty;
///**
// * 发行人指标
// * @author zhaozhenglai
// * @since 2016年9月19日 上午10:15:42 
// * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
// */
//@Document(collection = "bond_indicator")
//public class IssIndicatorDoc implements Serializable{
//
//    /**
//     * 
//     */
//    private static final long serialVersionUID = 1L;
//    @ApiModelProperty(value = "发行人|公司id")
//    @Indexed
//    private Long issId;
//    
//    @ApiModelProperty(value = "字段")
//    private String field;
//    
//    @ApiModelProperty(value = "字段名")
//    private String fieldName;
//    
//    @ApiModelProperty(value = "指标分类")
//    @Indexed
//    private String category;
//    
//    @ApiModelProperty(value = "行业id")
//    @Indexed
//    private Long induId;
//    
//    @ApiModelProperty(value = "最后报表时间")
//    private String lastFinDate;
//    
//    @ApiModelProperty(value = "是否为负向指标【1是，0不是】")
//    private int negative ;
//    
//    @ApiModelProperty(value = "机构类型(银行、券商、保险、非金融机构)")
//    @Indexed
//    private String orgType;
//    
//    @ApiModelProperty(value = "指标类型,默认为率类型[0]")
//    private int type;
//    
//    @ApiModelProperty(value = "各季度指标(最近六个季度)")
//    private  List<BigDecimal> indicators;
//
//    public Long getIssId() {
//        return issId;
//    }
//
//    public String getField() {
//        return field;
//    }
//
//    public String getFieldName() {
//        return fieldName;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public Long getInduId() {
//        return induId;
//    }
//
//
//    public void setIssId(Long issId) {
//        this.issId = issId;
//    }
//
//    public void setField(String field) {
//        this.field = field;
//    }
//
//    public void setFieldName(String fieldName) {
//        this.fieldName = fieldName;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public void setInduId(Long induId) {
//        this.induId = induId;
//    }
//
//    public List<BigDecimal> getIndicators() {
//        return indicators;
//    }
//
//    public void setIndicators(List<BigDecimal> indicators) {
//        this.indicators = indicators;
//    }
//
//    public String getLastFinDate() {
//        return lastFinDate;
//    }
//
//    public void setLastFinDate(String lastFinDate) {
//        this.lastFinDate = lastFinDate;
//    }
//
//    
//    
//    public int getNegative() {
//        return negative;
//    }
//
//    public void setNegative(int negative) {
//        this.negative = negative;
//    }
//    
//    public String getOrgType() {
//        return orgType;
//    }
//
//    public void setOrgType(String orgType) {
//        this.orgType = orgType;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public IssIndicatorDoc(Long issId, String field, String fieldName, String category, Long induId,
//            List<BigDecimal> indicators, int negative, String orgType, int type) {
//        super();
//        this.issId = issId;
//        this.field = field;
//        this.fieldName = fieldName;
//        this.category = category;
//        this.induId = induId;
//        this.indicators = indicators;
//        this.negative = negative;
//        this.orgType = orgType;
//        this.type = type;
//    }
//
//    public IssIndicatorDoc() {
//        super();
//    }
//    
//    
//    public static void main(String[] args) {
//        System.out.println(IndicatorType.VALUE.getType());
//    }
//    
//}
