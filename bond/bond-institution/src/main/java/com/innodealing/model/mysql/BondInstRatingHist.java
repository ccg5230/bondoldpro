package com.innodealing.model.mysql;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.util.StringUtils;

import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评级记录
 * 
 * @author liuqi
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Table(name = "t_bond_inst_rating_hist")
public class BondInstRatingHist extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@ApiModelProperty(value = "债劵ID")
	@Column(name = "bond_uni_code")
	private Long bondUniCode;

	@ApiModelProperty(value = "债劵简称")
	@Column(name = "bondChiName")
	private String bondChiName;

	@ApiModelProperty(value = "发行人ID")
	@Column(name = "com_uni_code")
	private Long comUniCode;

	@ApiModelProperty(value = "发行人简称")
	@Column(name = "comChiName")
	private String comChiName;

	@ApiModelProperty(value = "机构ID")
	@Column(name = "inst_id")
	private Integer instId;

	@ApiModelProperty(value = "紧迫度 1普通 2加急")
	@Column(name = "urgency")
	private Integer urgency;

	@ApiModelProperty(value = "发起说明")
	@Column(name = "launchDescribe")
	private String launchDescribe;

	@ApiModelProperty(value = "评级")
	@Column(name = "rating")
	private Integer rating;

	@ApiModelProperty(value = "评级说明发行人分析")
	@Column(name = "rating_describe")
	private String ratingDescribe;

	@ApiModelProperty(value = "评级时间")
	@Column(name = "rating_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date ratingTime;

	@ApiModelProperty(value = "投资建议")
	@Column(name = "investment_advice")
	private Integer investmentAdvice;

	@ApiModelProperty(value = "投资建议名称")
	@Column(name = "investment_describe")
	private String investmentDescribe;

	@ApiModelProperty(value = "投资建议个券分析")
	@Column(name = "investment_advice_desdetail", length = 65535)
	private String investmentAdviceDesdetail;

	@ApiModelProperty(value = "信评状态 1待信评，2待审核，3已完成 ")
	@Column(name = "status")
	private Integer status;

	@ApiModelProperty(value = "评级数据的版本号 ")
	@Column(name = "version")
	private Integer version;

	@ApiModelProperty(value = "评级名称 ")
	@Column(name = "rating_name")
	private String ratingName;

	@ApiModelProperty(value = "评级变动")
	@Column(name = "rating_diff")
	private Integer ratingDiff;

	@ApiModelProperty(value = "审核人")
	@Column(name = "check_by")
	private Integer checkBy;

	@ApiModelProperty(value = "审核时间")
	@Column(name = "check_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date checkTime;

	@ApiModelProperty(value = "审核结果 null未审核1审核成功0审核失败")
	@Column(name = "check_result")
	private Integer checkResult;

	@ApiModelProperty(value = "组长")
	@Column(name = "group_leader_by")
	private Integer groupLeaderBy;
	
	@ApiModelProperty(value = "组长姓名")
	private String groupLeaderByName;

	@ApiModelProperty(value = "组长建议")
	@Column(name = "group_leader_describe", length = 65535)
	private String groupLeaderDescribe;

	@ApiModelProperty(value = "发起人名称")
	private String createName;

	@ApiModelProperty(value = "组长建议时间")
	@Column(name = "group_leader_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date groupLeaderDate;

	@ApiModelProperty(value = "是否延用上次内部评级")
	@Column(name = "use_old_rating")
	private Integer useOldRating;

	@ApiModelProperty(value = "父评级")
	@Column(name = "fat_id")
	private Long fatId;

	@ApiModelProperty(value = "退回理由")
	@Column(name = "return_reason")
	private String returnReason;

	@ApiModelProperty(value = "评级人员")
	@Column(name = "rating_by")
	private Integer ratingBy;

	@ApiModelProperty(value = "评级人员名称")
	private String ratingByName;

	@ApiModelProperty(value = "债劵代码")
	private String bondCode;

	@ApiModelProperty(value = "评级说明相关资料")
	private List<BondInstRatingFile> RatingFiles;

	@ApiModelProperty(value = "投资建议相关资料")
	private List<BondInstRatingFile> investmentDescribeFiles;

	@ApiModelProperty(value = "发起说明相关资料")
	private List<BondInstRatingFile> launchDescribeFiles;

	@ApiModelProperty(value = "相关资料")
	private List<BondInstRatingFile> files;

	@ApiModelProperty(value = "审核人")
	private String checkName;

	@ApiModelProperty(value = "信评分组")
	private String groupName;
	
	@ApiModelProperty(value = "信评分组id")
	private Long groupId;

	@ApiModelProperty(value = "所属行业")
	private String induUniName;

	@ApiModelProperty(value = "上次信评分组")
	@Column(name = "old_group_name")
	private String oldGroupName;

	@ApiModelProperty(value = "上次所属行业")
	@Column(name = "old_indu_name")
	private String oldInduName;

	@ApiModelProperty(value = "上次信评人员")
	@Column(name = "old_rating_by_name")
	private String oldRatingByName;

	@ApiModelProperty(value = "上次信评日期")
	@Column(name = "old_rating_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date oldRatingTime;

	@ApiModelProperty(value = "上次内部评级")
	@Column(name = "old_rating")
	private Integer oldRating;

	@ApiModelProperty(value = "上次内部评级名称")
	@Column(name = "old_rating_name")
	private String oldRatingName;

	@ApiModelProperty(value = "上次版本号")
	@Column(name = "old_version")
	private Integer oldVersion;

	@ApiModelProperty(value = "上次投资建议")
	@Column(name = "old_investment_describe")
	private String oldInvestmentDescribe;

	@ApiModelProperty(value = "上次关系备注")
	@Column(name = "old_related_notes")
	private String oldRelatedNotes;
	
	@ApiModelProperty(value = "上次信评日期字符串")
	private String oldRatingTimeStr;
	
	@ApiModelProperty(value = "类型,1信评池导入2发起流程")
	@Column(name = "type")
	private Integer type;
	
	@ApiModelProperty(value = "上次投资建议日期")
	@Column(name = "old_investment_describe_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date oldInvestmentDescribeTime;
	
	@ApiModelProperty(value = "上次投资建议日期字符串")
	private String oldInvestmentDescribeTimeStr;
	
	@ApiModelProperty(value = "上次投资建议信评人员")
	@Column(name = "old_investment_describe_by_name")
	private String oldInvestmentDescribeByName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getBondChiName() {
		return bondChiName;
	}

	public void setBondChiName(String bondChiName) {
		this.bondChiName = bondChiName;
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public Integer getInstId() {
		return instId;
	}

	public void setInstId(Integer instId) {
		this.instId = instId;
	}

	public Integer getUrgency() {
		return urgency;
	}

	public void setUrgency(Integer urgency) {
		this.urgency = urgency;
	}

	public String getLaunchDescribe() {
		return launchDescribe;
	}

	public void setLaunchDescribe(String launchDescribe) {
		this.launchDescribe = launchDescribe;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getRatingDescribe() {
		return ratingDescribe;
	}

	public void setRatingDescribe(String ratingDescribe) {
		this.ratingDescribe = ratingDescribe;
	}

	public Date getRatingTime() {
		return ratingTime;
	}

	public void setRatingTime(Date ratingTime) {
		this.ratingTime = ratingTime;
	}

	public Integer getInvestmentAdvice() {
		return investmentAdvice;
	}

	public void setInvestmentAdvice(Integer investmentAdvice) {
		this.investmentAdvice = investmentAdvice;
	}

	public String getInvestmentDescribe() {
		return investmentDescribe;
	}

	public void setInvestmentDescribe(String investmentDescribe) {
		this.investmentDescribe = investmentDescribe;
	}

	public String getInvestmentAdviceDesdetail() {
		return investmentAdviceDesdetail;
	}

	public void setInvestmentAdviceDesdetail(String investmentAdviceDesdetail) {
		this.investmentAdviceDesdetail = investmentAdviceDesdetail;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getRatingName() {
		return ratingName;
	}

	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}

	public Integer getRatingDiff() {
		return ratingDiff;
	}

	public void setRatingDiff(Integer ratingDiff) {
		this.ratingDiff = ratingDiff;
	}

	public Integer getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(Integer checkBy) {
		this.checkBy = checkBy;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	public Integer getGroupLeaderBy() {
		return groupLeaderBy;
	}

	public void setGroupLeaderBy(Integer groupLeaderBy) {
		this.groupLeaderBy = groupLeaderBy;
	}

	public String getGroupLeaderDescribe() {
		return groupLeaderDescribe;
	}

	public void setGroupLeaderDescribe(String groupLeaderDescribe) {
		this.groupLeaderDescribe = groupLeaderDescribe;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getGroupLeaderDate() {
		return groupLeaderDate;
	}

	public void setGroupLeaderDate(Date groupLeaderDate) {
		this.groupLeaderDate = groupLeaderDate;
	}

	public Integer getUseOldRating() {
		return useOldRating;
	}

	public void setUseOldRating(Integer useOldRating) {
		this.useOldRating = useOldRating;
	}

	public Long getFatId() {
		return fatId;
	}

	public void setFatId(Long fatId) {
		this.fatId = fatId;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public Integer getRatingBy() {
		return ratingBy;
	}

	public void setRatingBy(Integer ratingBy) {
		this.ratingBy = ratingBy;
	}

	public String getRatingByName() {
		return ratingByName;
	}

	public void setRatingByName(String ratingByName) {
		this.ratingByName = ratingByName;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public List<BondInstRatingFile> getFiles() {
		return files;
	}

	public void setFiles(List<BondInstRatingFile> files) {
		this.files = files;
	}

	public List<BondInstRatingFile> getRatingFiles() {
		return RatingFiles;
	}

	public void setRatingFiles(List<BondInstRatingFile> ratingFiles) {
		RatingFiles = ratingFiles;
	}

	public List<BondInstRatingFile> getInvestmentDescribeFiles() {
		return investmentDescribeFiles;
	}

	public void setInvestmentDescribeFiles(List<BondInstRatingFile> investmentDescribeFiles) {
		this.investmentDescribeFiles = investmentDescribeFiles;
	}

	public List<BondInstRatingFile> getLaunchDescribeFiles() {
		return launchDescribeFiles;
	}

	public void setLaunchDescribeFiles(List<BondInstRatingFile> launchDescribeFiles) {
		this.launchDescribeFiles = launchDescribeFiles;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getOldGroupName() {
		return oldGroupName;
	}

	public void setOldGroupName(String oldGroupName) {
		this.oldGroupName = oldGroupName;
	}

	public String getOldInduName() {
		return oldInduName;
	}

	public void setOldInduName(String oldInduName) {
		this.oldInduName = oldInduName;
	}

	public String getOldRatingByName() {
		return oldRatingByName;
	}

	public void setOldRatingByName(String oldRatingByName) {
		this.oldRatingByName = oldRatingByName;
	}

	public Date getOldRatingTime() {
		return oldRatingTime;
	}

	public void setOldRatingTime(Date oldRatingTime) {
		this.oldRatingTime = oldRatingTime;
	}

	public Integer getOldRating() {
		return oldRating;
	}

	public void setOldRating(Integer oldRating) {
		this.oldRating = oldRating;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getInduUniName() {
		return induUniName;
	}

	public void setInduUniName(String induUniName) {
		this.induUniName = induUniName;
	}

	public String getOldInvestmentDescribe() {
		return oldInvestmentDescribe;
	}

	public void setOldInvestmentDescribe(String oldInvestmentDescribe) {
		this.oldInvestmentDescribe = oldInvestmentDescribe;
	}

	public String getOldRelatedNotes() {
		return oldRelatedNotes;
	}

	public void setOldRelatedNotes(String oldRelatedNotes) {
		this.oldRelatedNotes = oldRelatedNotes;
	}

	public String getOldRatingName() {
		return oldRatingName;
	}

	public void setOldRatingName(String oldRatingName) {
		this.oldRatingName = oldRatingName;
	}

	public Integer getOldVersion() {
		return oldVersion;
	}

	public void setOldVersion(Integer oldVersion) {
		this.oldVersion = oldVersion;
	}

	public String getGroupLeaderByName() {
		return groupLeaderByName;
	}

	public void setGroupLeaderByName(String groupLeaderByName) {
		this.groupLeaderByName = groupLeaderByName;
	}

	public String getOldRatingTimeStr() {
		return oldRatingTimeStr;
	}

	public void setOldRatingTimeStr(String oldRatingTimeStr) {
		this.oldRatingTimeStr = oldRatingTimeStr;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StringUtils.isEmpty(oldRatingTimeStr)){
			try {
				this.oldRatingTime = df.parse(oldRatingTimeStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Date getOldInvestmentDescribeTime() {
		return oldInvestmentDescribeTime;
	}

	public void setOldInvestmentDescribeTime(Date oldInvestmentDescribeTime) {
		this.oldInvestmentDescribeTime = oldInvestmentDescribeTime;
	}

	public String getOldInvestmentDescribeTimeStr() {
		return oldInvestmentDescribeTimeStr;
	}

	public void setOldInvestmentDescribeTimeStr(String oldInvestmentDescribeTimeStr) {
		this.oldInvestmentDescribeTimeStr = oldInvestmentDescribeTimeStr;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StringUtils.isEmpty(oldInvestmentDescribeTimeStr)){
			try {
				this.oldInvestmentDescribeTime = df.parse(oldInvestmentDescribeTimeStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getOldInvestmentDescribeByName() {
		return oldInvestmentDescribeByName;
	}

	public void setOldInvestmentDescribeByName(String oldInvestmentDescribeByName) {
		this.oldInvestmentDescribeByName = oldInvestmentDescribeByName;
	}
	

}
