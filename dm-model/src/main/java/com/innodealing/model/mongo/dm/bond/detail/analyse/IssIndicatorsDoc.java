package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
/**
 * 发行人指标（最终版）
 * @author zhaozhenglai
 * @since 2016年9月19日 上午10:15:42 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection = "iss_indicators")
public class IssIndicatorsDoc implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "发行人|公司id")
    @Indexed
    private Long issId;
    
    @ApiModelProperty(value = "发行人名称")
    private String issName;
    
    @ApiModelProperty(value = "字段")
    private String field;
    
    @ApiModelProperty(value = "字段名")
    private String fieldName;
    
    @ApiModelProperty(value = "指标分类")
    @Indexed
    private String category;
    
    @ApiModelProperty(value = "指标分类父类")
    private String categoryParent;
    
    @ApiModelProperty(value = "GICS行业id")
    @Indexed
    private Long induId;
    
    @ApiModelProperty(value = "申万行业id")
    @Indexed
    private Long induIdSw;
    
    @ApiModelProperty(value = "最后报表时间")
    private String lastFinDate;
    
    @ApiModelProperty(value = "是否为负向指标【1是，0不是】")
    private int negative ;
    
    @ApiModelProperty(value = "机构类型(银行、券商、保险、非金融机构)")
    @Indexed
    private String orgType;
    
    @ApiModelProperty(value = "指标类型,默认为率类型[0]")
    private Integer type = 0;
    
    @ApiModelProperty("主体所在省份")
    @Indexed
    private Long provinceId;
    
    @ApiModelProperty("指标是否为百分比的小数，1是、0否")
    private int percent;
    
    @ApiModelProperty("是否有流通的债券")
    private Integer isActive;
    
    @ApiModelProperty(value = "各季度指标")
    private  List<BigDecimal> indicators;
    
    @ApiModelProperty(value = "季度")
    private  List<String> quarters;
    
    

    public Long getIssId() {
        return issId;
    }

    public String getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getCategory() {
        return category;
    }

    public Long getInduId() {
        return induId;
    }


    public void setIssId(Long issId) {
        this.issId = issId;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setInduId(Long induId) {
        this.induId = induId;
    }

   

    public String getLastFinDate() {
        return lastFinDate;
    }

    public void setLastFinDate(String lastFinDate) {
        this.lastFinDate = lastFinDate;
    }

    
    
    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }
    
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

   


    public List<BigDecimal> getIndicators() {
        return indicators;
    }

    public List<String> getQuarters() {
        return quarters;
    }

    public void setIndicators(List<BigDecimal> indicators) {
        this.indicators = indicators;
    }

    public void setQuarters(List<String> quarters) {
        this.quarters = quarters;
    }

    
    
    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public IssIndicatorsDoc() {
        super();
    }

    public IssIndicatorsDoc(Long issId, String field, String fieldName, String category, Long induId,
            String lastFinDate, int negative, String orgType, int type,int percent, List<BigDecimal> indicators,
            List<String> quarters,  Long induIdSw, String issName) {
        super();
        this.issId = issId;
        this.field = field;
        this.fieldName = fieldName;
        this.category = category;
        this.induId = induId;
        this.lastFinDate = lastFinDate;
        this.negative = negative;
        this.orgType = orgType;
        this.type = type;
        this.percent = percent;
        this.indicators = indicators;
        this.quarters = quarters;
        this.induIdSw = induIdSw;
        this.issName = issName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public String getIssName() {
		return issName;
	}

	public void setIssName(String issName) {
		this.issName = issName;
	}

	public String getCategoryParent() {
		return categoryParent;
	}

	public void setCategoryParent(String categoryParent) {
		this.categoryParent = categoryParent;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}


	
    
    
}
