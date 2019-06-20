package com.innodealing.model.mongo.dm.bond.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
/**
 * 指标变化快照（同期，自身，行业排名）
 * @author dealing093
 *
 */
public class IndicatorChangeInduNotice {
	@ApiModelProperty(name="发行人ID")
    private Long induUniCode;
	
	@ApiModelProperty(name="指标code")
	private String field;
	
	@ApiModelProperty(name="行业指标 list")
	private List<BigDecimal> list;
	
	@ApiModelProperty(name="更新时间")
	private Date updateTime;
	
	public static void main(String[] args) {
		List<A> list = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			list.add(new A("name" + i));
		}
		for (A a : list) {
			a.setName("age");
		}
		for (A a : list) {
			System.out.println(a.getName());
		}
		
		List<Integer> list2 = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			list2.add(i);
		}
		for (Integer a : list2) {
			a = 2 ;
		}
		for (Integer a : list2) {
			System.out.println(a);
		}
	}
	
	
}

class A{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public A(String name) {
		super();
		this.name = name;
	}
	
	
}
