package com.innodealing.model.im.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="t_sysuser")
public class Sysuser implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 
	 */
    @Column(name="version",length=10)
    private Integer version;
    
	/**
	 * 
	 */
    @Column(name="name",length=256)
    private String name;
    
	/**
	 * 
	 */
    @Column(name="spellname",length=64)
    private String spellname;
    
	/**
	 * 
	 */
    @Column(name="checkby",length=256)
    private String checkby;
    
	/**
	 * 
	 */
    @Column(name="sellname",length=256)
    private String sellname;
    
	/**
	 * 
	 */
    @Column(name="usertype",length=10)
    private Integer usertype;
    
	/**
	 * 
	 */
    @Column(name="deaultpwd",length=256)
    private String deaultpwd;
    
	/**
	 * 
	 */
    @Column(name="company",length=256)
    private String company;
    
	/**
	 * 
	 */
    @Column(name="companyshort",length=256)
    private String companyshort;
    
	/**
	 * 
	 */
    @Column(name="companytype",length=50)
    private String companytype;
    
	/**
	 * 
	 */
    @Column(name="companyemail",length=256)
    private String companyemail;
    
	/**
	 * 
	 */
    @Column(name="companyphone",length=256)
    private String companyphone;
    
	/**
	 * 
	 */
    @Column(name="qq",length=256)
    private String qq;
    
	/**
	 * 
	 */
    @Column(name="wechatno",length=64)
    private String wechatno;
    
	/**
	 * 
	 */
    @Column(name="mobile",length=256)
    private String mobile;
    
	/**
	 * 
	 */
    @Column(name="createtime",length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    
	/**
	 * 
	 */
    @Column(name="accountid",length=10)
    private Integer accountid;
    
	/**
	 * 
	 */
    @Column(name="address",length=1000)
    private String address;
    
	/**
	 * 
	 */
    @Column(name="gender",length=256)
    private String gender;
    
	/**
	 * 
	 */
    @Column(name="subinst",length=1000)
    private String subinst;
    
	/**
	 * 
	 */
    @Column(name="title",length=1000)
    private String title;
    
	/**
	 * 
	 */
    @Column(name="status",length=10)
    private Integer status;
    
	/**
	 * 
	 */
    @Column(name="createlinktime",length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createlinktime;
    
	/**
	 * 
	 */
    @Column(name="checktime",length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date checktime;
    
	/**
	 * 
	 */
    @Column(name="remark",length=1000)
    private String remark;
    
	/**
	 * 
	 */
    @Column(name="type",length=10)
    private Integer type;
    
	/**
	 * 
	 */
    @Column(name="proxy",length=256)
    private String proxy;
    
	/**
	 * 
	 */
    @Column(name="visitingcardpath",length=256)
    private String visitingcardpath;
    
	/**
	 * 
	 */
    @Column(name="category",length=256)
    private String category;
    
	/**
	 * 
	 */
    @Column(name="instprovince",length=50)
    private String instprovince;
    
	/**
	 * 
	 */
    @Column(name="exchangeprovince",length=50)
    private String exchangeprovince;
    
	/**
	 * 
	 */
    @Column(name="companyphonestatus",length=3)
    private Integer companyphonestatus;
    
	/**
	 * 
	 */
    @Column(name="mobilestatus",length=3)
    private Integer mobilestatus;
    
	/**
	 * 
	 */
    @Column(name="sharerights",length=3)
    private Integer sharerights;
    
	/**
	 * 
	 */
    @Column(name="shareflag",length=3)
    private Integer shareflag;
    
	/**
	 * 
	 */
    @Column(name="shareactivetime",length=10)
    private Integer shareactivetime;
    
	/**
	 * 
	 */
    @Column(name="sharedeactivetime",length=10)
    private Integer sharedeactivetime;
    
	/**
	 * 
	 */
    @Column(name="shareremark",length=256)
    private String shareremark;
    
	/**
	 * 
	 */
    @Column(name="memo",length=256)
    private String memo;
    
	/**
	 * 
	 */
    @Column(name="show_office",length=3)
    private Integer showOffice;
    
	/**
	 * 
	 */
    @Column(name="show_mobile",length=3)
    private Integer showMobile;
    
	/**
	 * 
	 */
    @Column(name="registerfrom",length=3)
    private Integer registerfrom;
    
	/**
	 * 
	 */
    @Column(name="nickname",length=32)
    private String nickname;
    
	/**
	 * 
	 */
    @Column(name="uin",length=10)
    private Integer uin;
    
	/**
	 * 
	 */
    @Column(name="onlinestate",length=3)
    private Integer onlinestate;
    
	/**
	 * 
	 */
    @Column(name="avatarid",length=10)
    private Integer avatarid;
    
	/**
	 * 
	 */
    @Column(name="imflag",length=3)
    private Integer imflag;
    
	/**
	 * 
	 */
    @Column(name="instcity",length=10)
    private Integer instcity;
    
	/**
	 * 
	 */
    @Column(name="positionstatus",length=3)
    private Integer positionstatus;
    
    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param version the version to set
	 */
    public void setVersion(Integer version){
       this.version = version;
    }
    
    /**
     * @return the version 
     */
    public Integer getVersion(){
       return this.version;
    }
	
	/**
	 * @param name the name to set
	 */
    public void setName(String name){
       this.name = name;
    }
    
    /**
     * @return the name 
     */
    public String getName(){
       return this.name;
    }
	
	/**
	 * @param spellname the spellname to set
	 */
    public void setSpellname(String spellname){
       this.spellname = spellname;
    }
    
    /**
     * @return the spellname 
     */
    public String getSpellname(){
       return this.spellname;
    }
	
	/**
	 * @param checkby the checkby to set
	 */
    public void setCheckby(String checkby){
       this.checkby = checkby;
    }
    
    /**
     * @return the checkby 
     */
    public String getCheckby(){
       return this.checkby;
    }
	
	/**
	 * @param sellname the sellname to set
	 */
    public void setSellname(String sellname){
       this.sellname = sellname;
    }
    
    /**
     * @return the sellname 
     */
    public String getSellname(){
       return this.sellname;
    }
	
	/**
	 * @param usertype the usertype to set
	 */
    public void setUsertype(Integer usertype){
       this.usertype = usertype;
    }
    
    /**
     * @return the usertype 
     */
    public Integer getUsertype(){
       return this.usertype;
    }
	
	/**
	 * @param deaultpwd the deaultpwd to set
	 */
    public void setDeaultpwd(String deaultpwd){
       this.deaultpwd = deaultpwd;
    }
    
    /**
     * @return the deaultpwd 
     */
    public String getDeaultpwd(){
       return this.deaultpwd;
    }
	
	/**
	 * @param company the company to set
	 */
    public void setCompany(String company){
       this.company = company;
    }
    
    /**
     * @return the company 
     */
    public String getCompany(){
       return this.company;
    }
	
	/**
	 * @param companyshort the companyshort to set
	 */
    public void setCompanyshort(String companyshort){
       this.companyshort = companyshort;
    }
    
    /**
     * @return the companyshort 
     */
    public String getCompanyshort(){
       return this.companyshort;
    }
	
	/**
	 * @param companytype the companytype to set
	 */
    public void setCompanytype(String companytype){
       this.companytype = companytype;
    }
    
    /**
     * @return the companytype 
     */
    public String getCompanytype(){
       return this.companytype;
    }
	
	/**
	 * @param companyemail the companyemail to set
	 */
    public void setCompanyemail(String companyemail){
       this.companyemail = companyemail;
    }
    
    /**
     * @return the companyemail 
     */
    public String getCompanyemail(){
       return this.companyemail;
    }
	
	/**
	 * @param companyphone the companyphone to set
	 */
    public void setCompanyphone(String companyphone){
       this.companyphone = companyphone;
    }
    
    /**
     * @return the companyphone 
     */
    public String getCompanyphone(){
       return this.companyphone;
    }
	
	/**
	 * @param qq the qq to set
	 */
    public void setQq(String qq){
       this.qq = qq;
    }
    
    /**
     * @return the qq 
     */
    public String getQq(){
       return this.qq;
    }
	
	/**
	 * @param wechatno the wechatno to set
	 */
    public void setWechatno(String wechatno){
       this.wechatno = wechatno;
    }
    
    /**
     * @return the wechatno 
     */
    public String getWechatno(){
       return this.wechatno;
    }
	
	/**
	 * @param mobile the mobile to set
	 */
    public void setMobile(String mobile){
       this.mobile = mobile;
    }
    
    /**
     * @return the mobile 
     */
    public String getMobile(){
       return this.mobile;
    }
	
	/**
	 * @param createtime the createtime to set
	 */
    public void setCreatetime(Date createtime){
       this.createtime = createtime;
    }
    
    /**
     * @return the createtime 
     */
    public Date getCreatetime(){
       return this.createtime;
    }
	
	/**
	 * @param accountid the accountid to set
	 */
    public void setAccountid(Integer accountid){
       this.accountid = accountid;
    }
    
    /**
     * @return the accountid 
     */
    public Integer getAccountid(){
       return this.accountid;
    }
	
	/**
	 * @param address the address to set
	 */
    public void setAddress(String address){
       this.address = address;
    }
    
    /**
     * @return the address 
     */
    public String getAddress(){
       return this.address;
    }
	
	/**
	 * @param gender the gender to set
	 */
    public void setGender(String gender){
       this.gender = gender;
    }
    
    /**
     * @return the gender 
     */
    public String getGender(){
       return this.gender;
    }
	
	/**
	 * @param subinst the subinst to set
	 */
    public void setSubinst(String subinst){
       this.subinst = subinst;
    }
    
    /**
     * @return the subinst 
     */
    public String getSubinst(){
       return this.subinst;
    }
	
	/**
	 * @param title the title to set
	 */
    public void setTitle(String title){
       this.title = title;
    }
    
    /**
     * @return the title 
     */
    public String getTitle(){
       return this.title;
    }
	
	/**
	 * @param status the status to set
	 */
    public void setStatus(Integer status){
       this.status = status;
    }
    
    /**
     * @return the status 
     */
    public Integer getStatus(){
       return this.status;
    }
	
	/**
	 * @param createlinktime the createlinktime to set
	 */
    public void setCreatelinktime(Date createlinktime){
       this.createlinktime = createlinktime;
    }
    
    /**
     * @return the createlinktime 
     */
    public Date getCreatelinktime(){
       return this.createlinktime;
    }
	
	/**
	 * @param checktime the checktime to set
	 */
    public void setChecktime(Date checktime){
       this.checktime = checktime;
    }
    
    /**
     * @return the checktime 
     */
    public Date getChecktime(){
       return this.checktime;
    }
	
	/**
	 * @param remark the remark to set
	 */
    public void setRemark(String remark){
       this.remark = remark;
    }
    
    /**
     * @return the remark 
     */
    public String getRemark(){
       return this.remark;
    }
	
	/**
	 * @param type the type to set
	 */
    public void setType(Integer type){
       this.type = type;
    }
    
    /**
     * @return the type 
     */
    public Integer getType(){
       return this.type;
    }
	
	/**
	 * @param proxy the proxy to set
	 */
    public void setProxy(String proxy){
       this.proxy = proxy;
    }
    
    /**
     * @return the proxy 
     */
    public String getProxy(){
       return this.proxy;
    }
	
	/**
	 * @param visitingcardpath the visitingcardpath to set
	 */
    public void setVisitingcardpath(String visitingcardpath){
       this.visitingcardpath = visitingcardpath;
    }
    
    /**
     * @return the visitingcardpath 
     */
    public String getVisitingcardpath(){
       return this.visitingcardpath;
    }
	
	/**
	 * @param category the category to set
	 */
    public void setCategory(String category){
       this.category = category;
    }
    
    /**
     * @return the category 
     */
    public String getCategory(){
       return this.category;
    }
	
	/**
	 * @param instprovince the instprovince to set
	 */
    public void setInstprovince(String instprovince){
       this.instprovince = instprovince;
    }
    
    /**
     * @return the instprovince 
     */
    public String getInstprovince(){
       return this.instprovince;
    }
	
	/**
	 * @param exchangeprovince the exchangeprovince to set
	 */
    public void setExchangeprovince(String exchangeprovince){
       this.exchangeprovince = exchangeprovince;
    }
    
    /**
     * @return the exchangeprovince 
     */
    public String getExchangeprovince(){
       return this.exchangeprovince;
    }
	
	/**
	 * @param companyphonestatus the companyphonestatus to set
	 */
    public void setCompanyphonestatus(Integer companyphonestatus){
       this.companyphonestatus = companyphonestatus;
    }
    
    /**
     * @return the companyphonestatus 
     */
    public Integer getCompanyphonestatus(){
       return this.companyphonestatus;
    }
	
	/**
	 * @param mobilestatus the mobilestatus to set
	 */
    public void setMobilestatus(Integer mobilestatus){
       this.mobilestatus = mobilestatus;
    }
    
    /**
     * @return the mobilestatus 
     */
    public Integer getMobilestatus(){
       return this.mobilestatus;
    }
	
	/**
	 * @param sharerights the sharerights to set
	 */
    public void setSharerights(Integer sharerights){
       this.sharerights = sharerights;
    }
    
    /**
     * @return the sharerights 
     */
    public Integer getSharerights(){
       return this.sharerights;
    }
	
	/**
	 * @param shareflag the shareflag to set
	 */
    public void setShareflag(Integer shareflag){
       this.shareflag = shareflag;
    }
    
    /**
     * @return the shareflag 
     */
    public Integer getShareflag(){
       return this.shareflag;
    }
	
	/**
	 * @param shareactivetime the shareactivetime to set
	 */
    public void setShareactivetime(Integer shareactivetime){
       this.shareactivetime = shareactivetime;
    }
    
    /**
     * @return the shareactivetime 
     */
    public Integer getShareactivetime(){
       return this.shareactivetime;
    }
	
	/**
	 * @param sharedeactivetime the sharedeactivetime to set
	 */
    public void setSharedeactivetime(Integer sharedeactivetime){
       this.sharedeactivetime = sharedeactivetime;
    }
    
    /**
     * @return the sharedeactivetime 
     */
    public Integer getSharedeactivetime(){
       return this.sharedeactivetime;
    }
	
	/**
	 * @param shareremark the shareremark to set
	 */
    public void setShareremark(String shareremark){
       this.shareremark = shareremark;
    }
    
    /**
     * @return the shareremark 
     */
    public String getShareremark(){
       return this.shareremark;
    }
	
	/**
	 * @param memo the memo to set
	 */
    public void setMemo(String memo){
       this.memo = memo;
    }
    
    /**
     * @return the memo 
     */
    public String getMemo(){
       return this.memo;
    }
	
	/**
	 * @param showOffice the showOffice to set
	 */
    public void setShowOffice(Integer showOffice){
       this.showOffice = showOffice;
    }
    
    /**
     * @return the showOffice 
     */
    public Integer getShowOffice(){
       return this.showOffice;
    }
	
	/**
	 * @param showMobile the showMobile to set
	 */
    public void setShowMobile(Integer showMobile){
       this.showMobile = showMobile;
    }
    
    /**
     * @return the showMobile 
     */
    public Integer getShowMobile(){
       return this.showMobile;
    }
	
	/**
	 * @param registerfrom the registerfrom to set
	 */
    public void setRegisterfrom(Integer registerfrom){
       this.registerfrom = registerfrom;
    }
    
    /**
     * @return the registerfrom 
     */
    public Integer getRegisterfrom(){
       return this.registerfrom;
    }
	
	/**
	 * @param nickname the nickname to set
	 */
    public void setNickname(String nickname){
       this.nickname = nickname;
    }
    
    /**
     * @return the nickname 
     */
    public String getNickname(){
       return this.nickname;
    }
	
	/**
	 * @param uin the uin to set
	 */
    public void setUin(Integer uin){
       this.uin = uin;
    }
    
    /**
     * @return the uin 
     */
    public Integer getUin(){
       return this.uin;
    }
	
	/**
	 * @param onlinestate the onlinestate to set
	 */
    public void setOnlinestate(Integer onlinestate){
       this.onlinestate = onlinestate;
    }
    
    /**
     * @return the onlinestate 
     */
    public Integer getOnlinestate(){
       return this.onlinestate;
    }
	
	/**
	 * @param avatarid the avatarid to set
	 */
    public void setAvatarid(Integer avatarid){
       this.avatarid = avatarid;
    }
    
    /**
     * @return the avatarid 
     */
    public Integer getAvatarid(){
       return this.avatarid;
    }
	
	/**
	 * @param imflag the imflag to set
	 */
    public void setImflag(Integer imflag){
       this.imflag = imflag;
    }
    
    /**
     * @return the imflag 
     */
    public Integer getImflag(){
       return this.imflag;
    }
	
	/**
	 * @param instcity the instcity to set
	 */
    public void setInstcity(Integer instcity){
       this.instcity = instcity;
    }
    
    /**
     * @return the instcity 
     */
    public Integer getInstcity(){
       return this.instcity;
    }
	
	/**
	 * @param positionstatus the positionstatus to set
	 */
    public void setPositionstatus(Integer positionstatus){
       this.positionstatus = positionstatus;
    }
    
    /**
     * @return the positionstatus 
     */
    public Integer getPositionstatus(){
       return this.positionstatus;
    }
	
}
