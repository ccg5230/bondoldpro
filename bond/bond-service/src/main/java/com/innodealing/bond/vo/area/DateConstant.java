package com.innodealing.bond.vo.area;

public class DateConstant {
	
	public static final String YEAR = "年度";
	public static final String QUARTER = "季度";
	public static final String MONTH = "月度";
	
	public enum quarterCode{
		ONE_QUARTER(1,"Q1"),
		TWO_QUARTER(2,"Q2"),
		THREE_QUARTER(3,"Q3"),
		FOUR_QUARTER(4,"Q4");
		
		private int quarter;
		private String name;
				
		quarterCode(int quarter, String name) {			
			this.quarter = quarter;
			this.name = name;
		}
		
		public int getQuarter() {
			return quarter;
		}
		public void setQuarter(int quarter) {
			this.quarter = quarter;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
