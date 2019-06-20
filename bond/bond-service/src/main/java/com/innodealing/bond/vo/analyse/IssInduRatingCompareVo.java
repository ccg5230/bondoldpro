package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zhaozhenglai
 * @since 2016年9月9日 下午3:19:53 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
public class IssInduRatingCompareVo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评级(信誉评级  AAA:1,AA+:2,AA:3,AA-:4,A+:5,A-:6)")
    private Integer rating;

    @ApiModelProperty(value = "基准行业主体数")
    private Long issCount;

    @ApiModelProperty(value = "对比行业（全部）主体数")
    private Long comCount;

    public Integer getRating() {
        return rating;
    }

    public Long getIssCount() {
        return issCount;
    }

    public Long getComCount() {
        return comCount;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setIssCount(Long issCount) {
        this.issCount = issCount;
    }

    public void setComCount(Long comCount) {
        this.comCount = comCount;
    }

    public IssInduRatingCompareVo(Integer rating, Long issCount, Long comCount) {
        super();
        this.rating = rating;
        this.issCount = issCount;
        this.comCount = comCount;
    }

    public IssInduRatingCompareVo() {
        super();
    }

    @Override
    public String toString() {
        return "IssInduRatingCompareVo [rating=" + rating + ", issCount=" + issCount + ", comCount=" + comCount + "]";
    }

    public static void main(String[] args) {
        String start = "2016-08-23";
        String end = "2016-08-24";
        Date date = new Date();
        System.out.println(date.getTime());
        System.out.println(end.compareTo(start));
    }

}
