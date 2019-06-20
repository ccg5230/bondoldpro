package com.innodealing.model.dm.bond;

public class PubAreaCode {
	
	private long AreaUniCode;
	private long SubUniCode;
	private String AreaChiName;
	private String AreaName2;
	private String AreaName3;
	private String AreaName4;
	private String AreaName5;
	public long getAreaUniCode() {
		return AreaUniCode;
	}
	public long getSubUniCode() {
		return SubUniCode;
	}
	public String getAreaChiName() {
		return AreaChiName;
	}
	public String getAreaName2() {
		return AreaName2;
	}
	public String getAreaName3() {
		return AreaName3;
	}
	public String getAreaName4() {
		return AreaName4;
	}
	public String getAreaName5() {
		return AreaName5;
	}
	public void setAreaUniCode(long areaUniCode) {
		AreaUniCode = areaUniCode;
	}
	public void setSubUniCode(long subUniCode) {
		SubUniCode = subUniCode;
	}
	public void setAreaChiName(String areaChiName) {
		AreaChiName = areaChiName;
	}
	public void setAreaName2(String areaName2) {
		AreaName2 = areaName2;
	}
	public void setAreaName3(String areaName3) {
		AreaName3 = areaName3;
	}
	public void setAreaName4(String areaName4) {
		AreaName4 = areaName4;
	}
	public void setAreaName5(String areaName5) {
		AreaName5 = areaName5;
	}
	@Override
	public String toString() {
		return "PubAreaCode [AreaUniCode=" + AreaUniCode + ", SubUniCode=" + SubUniCode + ", AreaChiName=" + AreaChiName
				+ ", AreaName2=" + AreaName2 + ", AreaName3=" + AreaName3 + ", AreaName4=" + AreaName4 + ", AreaName5="
				+ AreaName5 + "]";
	}
}
