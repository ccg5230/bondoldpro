package com.innodealing.model.im;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_orgainfo")
public class Orgainfo implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 
	 */
    @Column(name="type",length=10)
    private Integer type;
    
	/**
	 * 
	 */
    @Column(name="fullname",length=64)
    private String fullname;
    
	/**
	 * 
	 */
    @Column(name="shortname",length=64)
    private String shortname;
    
	/**
	 * 
	 */
    @Column(name="shortname2",length=64)
    private String shortname2;
    
	/**
	 * 
	 */
    @Column(name="shortname3",length=64)
    private String shortname3;
    
	/**
	 * 
	 */
    @Column(name="shortname4",length=64)
    private String shortname4;
    
	/**
	 * 
	 */
    @Column(name="branchname",length=64)
    private String branchname;
    
	/**
	 * 
	 */
    @Column(name="branchsname",length=64)
    private String branchsname;
    
	/**
	 * 
	 */
    @Column(name="provinceid",length=10)
    private Integer provinceid;
    
	/**
	 * 
	 */
    @Column(name="status",length=3)
    private Integer status;
    
	/**
	 * 
	 */
    @Column(name="assetscale",length=10)
    private Integer assetscale;
    
	/**
	 * 
	 */
    @Column(name="sectiontype",length=10)
    private Integer sectiontype;
    
	/**
	 * 
	 */
    @Column(name="createtime",length=10)
    private String createtime;
    
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
	 * @param fullname the fullname to set
	 */
    public void setFullname(String fullname){
       this.fullname = fullname;
    }
    
    /**
     * @return the fullname 
     */
    public String getFullname(){
       return this.fullname;
    }
	
	/**
	 * @param shortname the shortname to set
	 */
    public void setShortname(String shortname){
       this.shortname = shortname;
    }
    
    /**
     * @return the shortname 
     */
    public String getShortname(){
       return this.shortname;
    }
	
	/**
	 * @param shortname2 the shortname2 to set
	 */
    public void setShortname2(String shortname2){
       this.shortname2 = shortname2;
    }
    
    /**
     * @return the shortname2 
     */
    public String getShortname2(){
       return this.shortname2;
    }
	
	/**
	 * @param shortname3 the shortname3 to set
	 */
    public void setShortname3(String shortname3){
       this.shortname3 = shortname3;
    }
    
    /**
     * @return the shortname3 
     */
    public String getShortname3(){
       return this.shortname3;
    }
	
	/**
	 * @param shortname4 the shortname4 to set
	 */
    public void setShortname4(String shortname4){
       this.shortname4 = shortname4;
    }
    
    /**
     * @return the shortname4 
     */
    public String getShortname4(){
       return this.shortname4;
    }
	
	/**
	 * @param branchname the branchname to set
	 */
    public void setBranchname(String branchname){
       this.branchname = branchname;
    }
    
    /**
     * @return the branchname 
     */
    public String getBranchname(){
       return this.branchname;
    }
	
	/**
	 * @param branchsname the branchsname to set
	 */
    public void setBranchsname(String branchsname){
       this.branchsname = branchsname;
    }
    
    /**
     * @return the branchsname 
     */
    public String getBranchsname(){
       return this.branchsname;
    }
	
	/**
	 * @param provinceid the provinceid to set
	 */
    public void setProvinceid(Integer provinceid){
       this.provinceid = provinceid;
    }
    
    /**
     * @return the provinceid 
     */
    public Integer getProvinceid(){
       return this.provinceid;
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
	 * @param assetscale the assetscale to set
	 */
    public void setAssetscale(Integer assetscale){
       this.assetscale = assetscale;
    }
    
    /**
     * @return the assetscale 
     */
    public Integer getAssetscale(){
       return this.assetscale;
    }
	
	/**
	 * @param sectiontype the sectiontype to set
	 */
    public void setSectiontype(Integer sectiontype){
       this.sectiontype = sectiontype;
    }
    
    /**
     * @return the sectiontype 
     */
    public Integer getSectiontype(){
       return this.sectiontype;
    }
	
	/**
	 * @param createtime the createtime to set
	 */
    public void setCreatetime(String createtime){
       this.createtime = createtime;
    }
    
    /**
     * @return the createtime 
     */
    public String getCreatetime(){
       return this.createtime;
    }
}
