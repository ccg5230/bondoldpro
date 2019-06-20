package com.innodealing.bond.vo.analyse;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document(collection = "indu_pd_compare")
@ApiModel(description = "行业主体违约概率分布对比DOC")
public class PdInduCompareVo {

    @ApiModelProperty(value = "评级")
    private String rating;
    
    @ApiModelProperty(value = "行业1数量")
    private int ownIssNum;
    
    @ApiModelProperty(value = "行业2数量")
    private int compIssNum;

    public String getRating() {
        return rating;
    }

    public int getOwnIssNum() {
        return ownIssNum;
    }

    public int getCompIssNum() {
        return compIssNum;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setOwnIssNum(int ownIssNum) {
        this.ownIssNum = ownIssNum;
    }

    public void setCompIssNum(int compIssNum) {
        this.compIssNum = compIssNum;
    }

   
    
}
