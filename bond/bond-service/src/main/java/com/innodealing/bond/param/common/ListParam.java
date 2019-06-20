package com.innodealing.bond.param.common;

import java.io.Serializable;
import java.util.List;

public  class ListParam <T>  implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    
    
}
