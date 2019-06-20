package com.innodealing.model.dm;

import java.io.Serializable;
import java.util.Date;

public class BondCcxeDataChangeEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Date createTime;
    private String tableName;
    private Object data;
    private Integer actType; //1: create 2: update 3: delete
    public final static Integer ACT_CREATE = 1; 
    public final static Integer ACT_UPDATE = 2;
    
    public BondCcxeDataChangeEvent(Date createTime, String tableName, Integer actType, Object data) {
        super();
        this.setCreateTime(createTime);
        this.setTableName(tableName);
        this.setData(data);
        this.setActType(actType);
    }
    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }
    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }
    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }
    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    /**
     * @return the actType
     */
    public Integer getActType() {
        return actType;
    }
    /**
     * @param actType the actType to set
     */
    public void setActType(Integer actType) {
        this.actType = actType;
    }

    
}

class BondCcxeDataUpdateEvent extends BondCcxeDataChangeEvent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public BondCcxeDataUpdateEvent(Date createTime, String tableName, Object data) {
        super(createTime, tableName, ACT_UPDATE, data);
    }}
class BondCcxeDataCreateEvent extends BondCcxeDataChangeEvent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public BondCcxeDataCreateEvent(Date createTime, String tableName, Object data) {
        super(createTime, tableName, ACT_CREATE, data);
    }}
