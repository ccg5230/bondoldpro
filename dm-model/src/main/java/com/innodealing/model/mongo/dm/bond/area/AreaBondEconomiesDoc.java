package com.innodealing.model.mongo.dm.bond.area;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document(collection="area_economies")
public class AreaBondEconomiesDoc {
	
	
	@Id
	@ApiModelProperty(value="区域经济数据Id")
	private Long id;
		
	@Indexed
	@ApiModelProperty(value="区域code")
	private Long areaUniCode ;
	
	@ApiModelProperty(value="地区名称")
	private String areaChiName;
		
	@ApiModelProperty(value="年度")
	private Integer bondYear ;
		
	@ApiModelProperty(value="月度")
	private Integer bondMonth;
		
	@ApiModelProperty(value="季度")
	private Integer bondQuarter;
	
	@ApiModelProperty(value="数据来源")
	private String statisticsType ;
	
	@ApiModelProperty(value="数据类型")
	private String dataType ;
	
	@ApiModelProperty(value="区域经济指标集合")
	private List<BondEconomieIndicator> economieIndicators;
	
	@ApiModelProperty(value="排序字段（辅助）")
	private String sortField;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
	}

	public String getAreaChiName() {
		return areaChiName;
	}

	public void setAreaChiName(String areaChiName) {
		this.areaChiName = areaChiName;
	}

	public Integer getBondYear() {
		return bondYear;
	}

	public void setBondYear(Integer bondYear) {
		this.bondYear = bondYear;
	}

	public Integer getBondMonth() {
		return bondMonth;
	}

	public void setBondMonth(Integer bondMonth) {
		this.bondMonth = bondMonth;
	}

	public Integer getBondQuarter() {
		return bondQuarter;
	}

	public void setBondQuarter(Integer bondQuarter) {
		this.bondQuarter = bondQuarter;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public List<BondEconomieIndicator> getEconomieIndicators() {
		return economieIndicators;
	}

	public void setEconomieIndicators(List<BondEconomieIndicator> economieIndicators) {
		this.economieIndicators = economieIndicators;
	}

	public String getSortField() {
		StringBuffer sort = new StringBuffer("");
		if(bondYear != null){
			sort.append(bondYear);
		}
		if(bondQuarter != null){
			sort.append(bondQuarter);
		}
		if(bondMonth != null){
			//sort.append(bondMonth);
			String  bondMonths = null;
			if( bondMonth == 12){
				bondMonths = "12";
			}
			if( bondMonth == 11){
				bondMonths = "11";
			}
			if( bondMonth == 10){
				bondMonths = "10";
			}
			if( bondMonth == 9){
				bondMonths = "09";
			}if( bondMonth == 8){
				bondMonths = "08";
			}
			if( bondMonth == 7){
				bondMonths = "07";
			}
			if( bondMonth == 6){
				bondMonths = "06";
			}
			if( bondMonth == 5){
				bondMonths = "05";
			}
			if( bondMonth == 4){
				bondMonths = "04";
			}
			if( bondMonth == 3){
				bondMonths = "03";
			}
			if( bondMonth == 2){
				bondMonths = "02";
			}
			if( bondMonth == 1){
				bondMonths = "01";
			}
			sort.append(bondMonths);
		}
		return sort.toString();
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	@Override
	public String toString() {
		return "AreaBondEconomiesDoc [id=" + id + ", areaUniCode=" + areaUniCode + ", areaChiName=" + areaChiName
				+ ", bondYear=" + bondYear + ", bondMonth=" + bondMonth + ", bondQuarter=" + bondQuarter
				+ ", statisticsType=" + statisticsType + ", dataType=" + dataType + ", economieIndicators="
				+ economieIndicators + ", sortField=" + sortField + "]";
	}
}
