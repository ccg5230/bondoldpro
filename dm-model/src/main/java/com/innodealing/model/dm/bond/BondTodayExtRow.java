package com.innodealing.model.dm.bond;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class BondTodayExtRow {
    @ApiModelProperty(value = "评级")
    private String rating;

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value = "1M")
    private BondTodayItem c1M;

    @ApiModelProperty(value = "3M")
    private BondTodayItem c3M;

    @ApiModelProperty(value = "6M")
    private BondTodayItem c6M;

    @ApiModelProperty(value = "9M")
    private BondTodayItem c9M;

    @ApiModelProperty(value = "1Y")
    private BondTodayItem c1Y;

    @ApiModelProperty(value = "3Y")
    private BondTodayItem c3Y;

    @ApiModelProperty(value = "5Y")
    private BondTodayItem c5Y;

    @ApiModelProperty(value = "7Y")
    private BondTodayItem c7Y;

    @ApiModelProperty(value = "10Y")
    private BondTodayItem c10Y;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BondTodayItem getC1M() {
        return c1M;
    }

    public void setC1M(BondTodayItem c1M) {
        this.c1M = c1M;
    }

    public BondTodayItem getC3M() {
        return c3M;
    }

    public void setC3M(BondTodayItem c3M) {
        this.c3M = c3M;
    }

    public BondTodayItem getC6M() {
        return c6M;
    }

    public void setC6M(BondTodayItem c6M) {
        this.c6M = c6M;
    }

    public BondTodayItem getC9M() {
        return c9M;
    }

    public void setC9M(BondTodayItem c9M) {
        this.c9M = c9M;
    }

    public BondTodayItem getC1Y() {
        return c1Y;
    }

    public void setC1Y(BondTodayItem c1Y) {
        this.c1Y = c1Y;
    }

    public BondTodayItem getC3Y() {
        return c3Y;
    }

    public void setC3Y(BondTodayItem c3Y) {
        this.c3Y = c3Y;
    }

    public BondTodayItem getC5Y() {
        return c5Y;
    }

    public void setC5Y(BondTodayItem c5Y) {
        this.c5Y = c5Y;
    }

    public BondTodayItem getC7Y() {
        return c7Y;
    }

    public void setC7Y(BondTodayItem c7Y) {
        this.c7Y = c7Y;
    }

    public BondTodayItem getC10Y() {
        return c10Y;
    }

    public void setC10Y(BondTodayItem c10Y) {
        this.c10Y = c10Y;
    }
}
