package com.innodealing.model.mongo.dm;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.annotations.ApiModelProperty;


//@JsonInclude(Include.NON_NULL)
@Document(collection="area_economies")
public class AreaEconomiesDoc {
	
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
	private List<EconomieIndicator> economieIndicators;

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

	public List<EconomieIndicator> getEconomieIndicators() {
		return economieIndicators;
	}

	public void setEconomieIndicators(List<EconomieIndicator> economieIndicators) {
		this.economieIndicators = economieIndicators;
	}

	
}
