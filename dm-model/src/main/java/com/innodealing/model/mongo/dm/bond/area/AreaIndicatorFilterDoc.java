package com.innodealing.model.mongo.dm.bond.area;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zhaopeng
 * 2017年10月25日
 *
 */
@Document(collection = "area_indicator_filter_doc")
public class AreaIndicatorFilterDoc {
	
	@Id
	@ApiModelProperty("id")
	private Long id;
	
	@ApiModelProperty("当前类型的所有专项指标")
	private List<AreaIndicatorItem> indicatorFieldGroups;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AreaIndicatorItem> getIndicatorFieldGroups() {
		return indicatorFieldGroups;
	}

	public void setIndicatorFieldGroups(List<AreaIndicatorItem> indicatorFieldGroups) {
		this.indicatorFieldGroups = indicatorFieldGroups;
	}

	@Override
	public String toString() {
		return "AreaIndicatorFilterDoc [id=" + id + ", indicatorFieldGroups=" + indicatorFieldGroups + "]";
	}
	

}
