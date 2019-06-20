package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@Document(collection = "iss_fin_dates")
@ApiModel(value ="发行人财报日期纪录")
public class IssFinDatesDoc {
    
    @ApiModelProperty(value = "主体id")
    private Long issId;

    @ApiModelProperty(value = "财报历史纪录")
    private Collection<String> finDates;

    public Long getIssId() {
        return issId;
    }

    public Collection<String> getFinDates() {
        return finDates;
    }

    public void setIssId(Long issId) {
        this.issId = issId;
    }

    public void setFinDates(Collection<String> finDates) {
        this.finDates = finDates;
    }
}
