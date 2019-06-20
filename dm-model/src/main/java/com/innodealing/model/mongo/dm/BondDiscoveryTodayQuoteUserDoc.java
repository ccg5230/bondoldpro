package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_discovery_today_quote_user")
public class BondDiscoveryTodayQuoteUserDoc implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @Indexed
    private Long userId;

    @ApiModelProperty(value = "评级类型:1-DM量化评级;2-外部评级")
    @Indexed
    private Integer ratingType;

    @ApiModelProperty(value = "方向:1-bid;2-offer")
    @Indexed
    private Integer side;

    @ApiModelProperty(value = "评级")
    @Indexed
    private String rating;

    @ApiModelProperty(value = "期限")
    @Indexed
    private String tenor;

    @ApiModelProperty(value = "点击时间")
    private Date clickTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }

    public Integer getRatingType() {
        return ratingType;
    }

    public void setRatingType(Integer ratingType) {
        this.ratingType = ratingType;
    }
}
