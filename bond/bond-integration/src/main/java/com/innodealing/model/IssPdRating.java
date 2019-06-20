package com.innodealing.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class IssPdRating implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "违约评级")
    private String rating;
    
    @ApiModelProperty(value = "发行人|公司id")
    private Long comId;
    
    @ApiModelProperty(value = "行业id")
    private Long induId;
    
    @ApiModelProperty(value = "公司|主体名称")
    private String compName;
    public String getRating() {
        return rating;
    }
    public Long getComId() {
        return comId;
    }
    public Long getInduId() {
        return induId;
    }
    public String getCompName() {
        return compName;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public void setComId(Long comId) {
        this.comId = comId;
    }
    public void setInduId(Long induId) {
        this.induId = induId;
    }
    public void setCompName(String compName) {
        this.compName = compName;
    }
    
    
}
