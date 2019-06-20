package com.innodealing.util.pagination;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 查询器
 * @author zhaozhenglai
 * @since 2016年7月1日上午10:02:45 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 */
@ApiModel(description="查询器")
public class Query implements Serializable {

private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value="是否分页，默认false")
    private boolean pageable;
    
    @ApiModelProperty(value="第几页，默认1")
    private Integer pageno = 1;
    
    @ApiModelProperty(value="每页条目数,默认20")
    private Integer pagesize = 20;
    
    @ApiModelProperty(value="查询过滤器")
    private List<Filter> filters;

    @ApiModelProperty(value="排序参数")
    private List<Order> orders;

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean isPageable() {
        return pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }
    
    public  String getFilterSql() {
        StringBuffer sb = new StringBuffer();
        if(this.filters == null){
            return "";
        }
        for (Filter filter : this.filters) {
            if(filter.getName()!=null){
                sb.append(" ")
                  .append(filter.getJoinType())
                  .append(" ")
                  .append(filter.getName())
                  .append(" ")
                  .append(filter.getOption());
                if(filter.getOption()!=null && "like".equals(filter.getOption().toLowerCase())){
                    sb.append(" '%")
                    .append(filter.getValue())
                    .append("%'");
                //in 处理
                }else if(filter.getOption()!=null && "in".equals(filter.getOption().toLowerCase())){
                    String value = "";
                    if(filter.getValue() instanceof String){
                        value = filter.getValue().replace("[", "").replace("]", "");
                    }else{
                        value = filter.getValue().toString().replace("[", "").replace("]", "");
                    }
                    if(!"".equals(value.trim())){
                        sb.append(" (")
                        .append(value)
                        .append(") ");
                    }else{
                        sb = new StringBuffer(sb.substring(0,sb.lastIndexOf(filter.getJoinType())));
                    }
                //not in 处理
                }else if(filter.getOption()!=null && "notin".equals(filter.getOption().toLowerCase().replace(" ",""))){
                    String value = "";
                    if(filter.getOption() instanceof String){
                        value = filter.getValue().replace("[", "").replace("]", "");
                    }else{
                        value = filter.getValue().toString().replace("[", "").replace("]", "");
                    }
                    if(!"".equals(value.trim())){
                        sb.append(" (")
                        .append(value)
                        .append(") ");
                    }else{
                        sb = new StringBuffer(sb.substring(0,sb.lastIndexOf(filter.getJoinType())));
                    }
                }else if(filter.getOption()!=null && "&".equals(filter.getOption().toLowerCase())){
                    sb.append(filter.getValue()).append(" > 0 ");
                }else{
                    sb.append(" '")
                    .append(filter.getValue())
                    .append("'");
                }
                
            }
            
        }
        return sb.toString();
    }
    
    public  String getOrderSql() {
        StringBuffer sb = new StringBuffer();
        if(this.orders !=null && this.orders.size() > 0){
            int count = 1;
            sb.append(" order by ");
            for (Order order : this.orders) {
                sb.append(order.getOrderby())
                .append(" ")
                .append(order.getValue())
                .append(" ");
                if(count != this.orders.size()){
                    sb.append(",");
                }
                count++;
            }
        }
        return sb.toString();
    }
    
    public Integer getIndex() {
        return (pageno-1)*pagesize;
    }
    
}
