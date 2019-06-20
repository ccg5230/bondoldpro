package com.innodealing.model.dm.bond;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class BondCom implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "中诚信|dm发行人id")
    private Long issId;
    
    @ApiModelProperty(value = "安硕主体id")
    private Long amsIssId;
    
    @ApiModelProperty(value = "GICS行业id")
    private Long induId;
    
    @ApiModelProperty(value = "GICS行业4级id")
    private Long induIdL4;
    
    @ApiModelProperty(value = "申万行业id")
    private Long induIdSw;
    
    @ApiModelProperty(value = "DM主体名称")
    private String issName;
    
    public Long getIssId() {
        return issId;
    }
    public Long getAmsIssId() {
        return amsIssId;
    }
    public Long getInduId() {
        return induId;
    }
    public void setIssId(Long issId) {
        this.issId = issId;
    }
    public void setAmsIssId(Long amsIssId) {
        this.amsIssId = amsIssId;
    }
    public void setInduId(Long induId) {
        this.induId = induId;
    }
    public String getIssName() {
        return issName;
    }
    public void setIssName(String issName) {
        this.issName = issName;
    }
	public Long getInduIdSw() {
		return induIdSw;
	}
	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}
	public Long getInduIdL4() {
		return induIdL4;
	}
	public void setInduIdL4(Long induIdL4) {
		this.induIdL4 = induIdL4;
	}
	@Override
	public String toString() {
		return "BondCom [issId=" + issId + ", amsIssId=" + amsIssId + ", induId=" + induId + ", induIdL4=" + induIdL4
				+ ", induIdSw=" + induIdSw + ", issName=" + issName + "]";
	}
    
    
    
}