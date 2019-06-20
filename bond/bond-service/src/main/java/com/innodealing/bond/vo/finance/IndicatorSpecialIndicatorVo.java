package com.innodealing.bond.vo.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.innodealing.consts.Constants;

import io.swagger.annotations.ApiModelProperty;

/**
 * 财务指标分析->专项指标列表VO
 * 
 * @author zhaozhenglai
 * @date 2017年2月10日下午4:14:25 Copyright © 2016 DealingMatrix.cn. All Rights
 *       Reserved.
 */
public class IndicatorSpecialIndicatorVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@ApiModelProperty("主体名称")
	private String issuserName;
	
	@ApiModelProperty("财报时间")
	private List<String> quarters;

	@ApiModelProperty("指标值")
	private List<BigDecimal> values;
	
	@ApiModelProperty("指标code")
	private String field;
	
	@ApiModelProperty("指标名称")
	private String fieldName;
	
	@ApiModelProperty("是否为负向指标")
	private Integer negative;

	@ApiModelProperty("所属分类")
	private String categoryName;
	
	@ApiModelProperty("所属分类父类")
	private String categoryNameParent;
	
	@ApiModelProperty("是否为率值，1是；0不是")
	private Integer percent;
	
	@ApiModelProperty("指标计算表达式描述")
    private String expressDescription;
	
	@ApiModelProperty("排序字段(辅助)")
	private Integer sort = 100;

	public List<String> getQuarters() {
		return quarters;
	}

	public void setQuarters(List<String> quarters) {
		this.quarters = quarters;
	}

	public List<BigDecimal> getValues() {
		return values;
	}

	public void setValues(List<BigDecimal> values) {
		this.values = values;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getNegative() {
		return negative;
	}

	public void setNegative(Integer negative) {
		this.negative = negative;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCategoryNameParent() {
		return categoryNameParent;
	}

	public void setCategoryNameParent(String categoryNameParent) {
		this.categoryNameParent = categoryNameParent;
	}

	public String getIssuserName() {
		return issuserName;
	}

	public void setIssuserName(String issuserName) {
		this.issuserName = issuserName;
	}

	public Integer getPercent() {
		if(this.fieldName.contains("％")){
    		return Constants.INDICATOR_PERCENT_L;
    	}
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

    public String getExpressDescription() {
        return expressDescription;
    }

    public void setExpressDescription(String expressDescription) {
        this.expressDescription = expressDescription;
    }

	public Integer getSort() {
		//资产负债
		if("资产".equals(categoryNameParent) || "资产".equals(categoryName)){
			sort = 9;
			if("流动资产".equals(categoryName)){
				sort = sort-9;
			}
			if("非流动资产".equals(categoryName)){
				sort = sort-6;
			}
			if("资产总计".equals(categoryName)){
				sort = sort-3;
			}
		}
		if("负债".equals(categoryNameParent) || "负债".equals(categoryName)){
			sort = 18;
			if("流动负债".equals(categoryName)){
				sort = sort-9;
			}
			if("非流动负债".equals(categoryName)){
				sort = sort-6;
			}
			if("负债合计".equals(categoryName)){
				sort = sort-3;
			}
		}
		
		//利润表
		if("营业总收入".equals(categoryNameParent) || "营业总收入".equals(categoryName)){
			sort = 1;
		}
		if("营业总成本".equals(categoryNameParent) || "营业总成本".equals(categoryName)){
			sort =3;
		}
		if("营业利润".equals(categoryNameParent) || "营业利润".equals(categoryName)){
			sort = 6;
		}
		if("利润总额".equals(categoryNameParent) || "利润总额".equals(categoryName)){
			sort = 9;
		}
		if("利润总额".equals(categoryNameParent) || "利润总额".equals(categoryName)){
			sort = 12;
		}
		if("净利润".equals(categoryNameParent) || "净利润".equals(categoryName)){
			sort = 15;
		}
		
		//现金流量表
		if("经营活动现金流量".equals(categoryNameParent) || "经营活动现金流量".equals(categoryName)){
			sort = 1;
		}
		if("投资活动现金流量".equals(categoryNameParent) || "投资活动现金流量".equals(categoryName)){
			sort =3;
		}
		if("筹资活动现金流量".equals(categoryNameParent) || "筹资活动现金流量".equals(categoryName)){
			sort = 6;
		}
		if("现金及现金等价物".equals(categoryNameParent) || "现金及现金等价物".equals(categoryName)){
			sort = 9;
		}
		if("现金流量利润调节".equals(categoryNameParent) || "现金流量利润调节".equals(categoryName)){
			sort = 12;
		}
		return sort;
	}

	public void setSort(Integer sort) {
		
		this.sort = sort;
	}


	
	
	
}
