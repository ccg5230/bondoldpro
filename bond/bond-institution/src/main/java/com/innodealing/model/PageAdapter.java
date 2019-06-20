package com.innodealing.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageAdapter<T> {
    
    List<T> content;
    Pageable pageable;
    long total;
    
    public PageAdapter(List<T> content, Pageable pageable, long total) {
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
