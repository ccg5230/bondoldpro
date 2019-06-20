package com.innodealing.adapter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import com.innodealing.bond.service.BondInduService;
import com.innodealing.model.mongo.dm.BondComInfoDoc;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class BondPageAdapter<T> {
    
    List<T> content;
    Pageable pageable;
    long total;
    
    public BondPageAdapter(List<T> content, Pageable pageable, long total) {
        this.content = content;
        this.pageable = pageable;
        this.total = total;
    }
    
    public Page<T> createPage() {
       return new PageImpl<>(content, pageable, total);
    }

    /**
     * @return the content
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * @return the pageable
     */
    public Pageable getPageable() {
        return pageable;
    }

    /**
     * @param pageable the pageable to set
     */
    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    /**
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }
}
