package com.innodealing.adapter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import com.innodealing.bond.param.BondSimilarFilterReq;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.consts.Constants;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDmFilterDoc;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BondInduAdapter {
    
    private static final Logger LOG = LoggerFactory.getLogger(BondInduAdapter.class);
    
    @Autowired
    BondInduService induService;
    
    public BondDmFilterDoc conv(BondDmFilterDoc doc) {
        if (doc.getInduClass() == null) {
            doc.setInduClass(Constants.INDU_CLASS_GICS);
        }
        return doc;
    }
    
    public List<BondDmFilterDoc> conv(List<BondDmFilterDoc> docs) {
        for(BondDmFilterDoc doc : docs) {
            if (doc.getInduClass() == null) {
                doc.setInduClass(Constants.INDU_CLASS_GICS);
            }
        }
        return docs;
    }
    
    public BondSimilarFilterReq conv(BondSimilarFilterReq req, Long userId) {
        if (induService.isGicsInduClass(userId)) return req;
        
        List<String> fields = req.getSimilarField();
        for(int i = 0; i < fields.size(); ++i) {
            if (fields.get(i).equals(Constants.INDU_CLASS_GICS_ID)) {
                fields.set(i, Constants.INDU_CLASS_SW_ID);
            }
        }
        return req;
    }

    public <T> BondPageAdapter<T> conv(BondPageAdapter<T> page, Long userId) {
        if (induService.isGicsInduClass(userId)) return page;
        
        for(T obj : page.getContent()) {
            conv(obj);
        }
        return page;
    }
    
    public <T> Collection<T> conv(Collection<T> list, Long userId) {
        if (induService.isGicsInduClass(userId)) return list;
        for(T obj : list) {
            conv(obj);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public <T> T conv(T obj, Long userId) {
        if (induService.isGicsInduClass(userId)) return obj;
        return (T) copyInduFields(obj);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T conv(T obj) {
        return (T) copyInduFields(obj);
    }
    
    private Object copyInduFields(Object obj) 
    {
        try {
            Method setInduId = obj.getClass().getMethod("setInduId", Long.class);
            Method getInduIdSw = obj.getClass().getMethod("getInduIdSw");
            if (getInduIdSw != null && setInduId  != null ) {
                setInduId.invoke(obj, getInduIdSw.invoke(obj));
            }
        } catch (Exception e) {
            LOG.debug("failed to setInduId", e);
        }
        try {
            Method setInduName = obj.getClass().getMethod("setInduName", String.class);
            Method getInduNameSw = obj.getClass().getMethod("getInduNameSw");
            if (getInduNameSw != null && setInduName  != null ) {
                setInduName.invoke(obj, getInduNameSw.invoke(obj));
            }
        } catch (Exception e) {
            LOG.debug("failed to setInduName", e);
        }
        return obj;
    }
    
    public <T> BondPageAdapter<T> convByInduClass(BondPageAdapter<T> page,Integer induClass) {
        if ((induClass == null)? true: !induClass.equals(Constants.INDU_CLASS_SW)) return page;
        
        for(T obj : page.getContent()) {
            conv(obj);
        }
        return page;
    }
}
