package com.innodealing.bond.param.bond;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 一级债券首页查询参数
 * 
 * @author 赵正来
 *
 */
public class BondClassOneParam {
	
    @ApiModelProperty(value = "债券id")
	private Long bondUniCode;
    
    @ApiModelProperty("发行起始日")
    private Date issStartDate;

    @ApiModelProperty("发行结束日")
    private Date issEndDate;

    @ApiModelProperty(value = "债券类型:短融 9，超短融23  中票8 公司债5  企业债4 0:其他（bond_ccxe.pub_par where PAR_SYS_CODE=3051）")
    private List<Integer> bondTypePar;

    @ApiModelProperty(value = "债券期限([全部不传]、[1:＜3M]、[2:3-6M] | [3:6-9M] [4:9-12M] [5:1-3Y] [6:3-5Y] [7:5-7Y] [8:7-10Y] [9:≥10Y]) [0:暂无]")
    private List<Integer> bondMatu;

    @ApiModelProperty(value = "行业id")
    private List<Long> induId;

    @ApiModelProperty(value = "发行方式  //0-招标  1-薄记建档  9999未设置 （ 非纯数字的为老的债券） ")
    private List<String> issCls;

    @ApiModelProperty(value = "企业性质(所有制), 取值范围1央企 2国企 6民企  0其他, 含义参考bond_ccxe.pub_par(PAR_SYS_CODE='1062')")
    private List<Integer> comAttrPar;

    @ApiModelProperty(value = "rating狗评分：1/10 到  10/10")
    private List<String> orgRating;

    @ApiModelProperty(value = "dm量化评分:AAA C AA+ AA- AA A+ A A- BBB+ BBB BBB- 到CCC- 共计20个评级 ")
    private List<String> dmRatingScore;

    @ApiModelProperty(value = "搜索关键字 ")
    private String searchKey;
    
    @ApiModelProperty(value = "排序字段,格式为“字段:desc|asc”。")
    private String sort;
    
    @ApiModelProperty(value = "兴业主体评分:5+ 5 4+ 4 到 1+ 1 共计10个评级 ")
    private List<String> industrialSubjectScore;
    
    @ApiModelProperty(value = "兴业债项评分:5+ 5 4+ 4 到 1+ 1 共计10个评级 ")
    private List<String> industrialBondScore;
    
    @ApiModelProperty(value = "主体评级 ：AAA AA+ AA AA- A+ 其他(传0)，AAA最好")
    private List<String> issRating;
    
    @ApiModelProperty(value = "债项评级：AAA AA+ AA AA- A+ A- A-1 其他(传0)，AAA最好  ")
    private List<String> bondRating;
    
    @ApiModelProperty(value = "兴业展望：0-负面 1-稳定")
    private List<String> industExpectation;

    public Date getIssStartDate() {
        return issStartDate;
    }

    public void setIssStartDate(Date issStartDate) {
        this.issStartDate = issStartDate;
    }

    public List<Integer> getBondTypePar() {
        return bondTypePar;
    }

    public void setBondTypePar(List<Integer> bondTypePar) {
        this.bondTypePar = bondTypePar;
    }

    public List<Integer> getBondMatu() {
        return bondMatu;
    }

    public void setBondMatu(List<Integer> bondMatu) {
        this.bondMatu = bondMatu;
    }

    public List<Long> getInduId() {
        return induId;
    }

    public void setInduId(List<Long> induId) {
        this.induId = induId;
    }

    public List<String> getIssCls() {
        return issCls;
    }

    public void setIssCls(List<String> issCls) {
        this.issCls = issCls;
    }

    public List<Integer> getComAttrPar() {
        return comAttrPar;
    }

    public void setComAttrPar(List<Integer> comAttrPar) {
        this.comAttrPar = comAttrPar;
    }

    public List<String> getOrgRating() {
        return orgRating;
    }

    public void setOrgRating(List<String> orgRating) {
        this.orgRating = orgRating;
    }

    public List<String> getDmRatingScore() {
        return dmRatingScore;
    }

    public void setDmRatingScore(List<String> dmRatingScore) {
        this.dmRatingScore = dmRatingScore;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Date getIssEndDate() {
        return issEndDate;
    }

    public void setIssEndDate(Date issEndDate) {
        this.issEndDate = issEndDate;
    }

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

    public List<String> getIndustrialSubjectScore() {
        return industrialSubjectScore;
    }

    public void setIndustrialSubjectScore(List<String> industrialSubjectScore) {
        this.industrialSubjectScore = industrialSubjectScore;
    }

    public List<String> getIndustrialBondScore() {
        return industrialBondScore;
    }

    public void setIndustrialBondScore(List<String> industrialBondScore) {
        this.industrialBondScore = industrialBondScore;
    }

    public List<String> getIssRating() {
        return issRating;
    }

    public void setIssRating(List<String> issRating) {
        this.issRating = issRating;
    }

    public List<String> getBondRating() {
        return bondRating;
    }

    public void setBondRating(List<String> bondRating) {
        this.bondRating = bondRating;
    }

    public List<String> getIndustExpectation() {
        return industExpectation;
    }

    public void setIndustExpectation(List<String> industExpectation) {
        this.industExpectation = industExpectation;
    }

	
}
