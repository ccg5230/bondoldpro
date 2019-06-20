package com.innodealing.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.model.mongo.dm.BondFieldGroupMappingDoc;

/**
 * 缓存非金融财务数据
 * 
 * @author liuqi
 *
 */

@Component
public class FieldGroupMappingCache {

    private @Autowired BondDetailService bondDetailService;

    private static final Logger LOG = LoggerFactory.getLogger(FieldGroupMappingCache.class);

    // 缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
    private LoadingCache<String, Map<String, BondFieldGroupMappingDoc>> localCache
    // CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
            = CacheBuilder.newBuilder()
                    // 设置并发级别为8，并发级别是指可以同时写缓存的线程数
                    .concurrencyLevel(8)
                    // 设置写缓存后600秒钟过期
                    .expireAfterWrite(600, TimeUnit.SECONDS)
                    // 设置缓存容器的初始容量为10
                    .initialCapacity(10)
                    // 设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                    .maximumSize(100)
                    // 设置要统计缓存的命中率
                    .recordStats()
                    // 设置缓存的移除通知
                    .removalListener(new RemovalListener<Object, Object>() {
                        @Override
                        public void onRemoval(RemovalNotification<Object, Object> notification) {
                            LOG.info(notification.getKey() + " was removed, cause is " + notification.getCause());
                        }
                    })
                    // build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                    .build(new CacheLoader<String, Map<String, BondFieldGroupMappingDoc>>() {
                        @Override
                        public Map<String, BondFieldGroupMappingDoc> load(String key) throws Exception {

                            Map<String, BondFieldGroupMappingDoc> BondFieldGroupMappingDocs = new HashMap<String, BondFieldGroupMappingDoc>();

                            List<BondFieldGroupMappingDoc> list = new ArrayList<BondFieldGroupMappingDoc>();
                            list.addAll(bondDetailService.queryBondFieldGroupMappingList(1));// 基础指标
                            list.addAll(bondDetailService.queryBondFieldGroupMappingList(2));// 专项指标
                            list.addAll(bondDetailService.queryBondFieldGroupMappingList(3));// 资产负债
                            list.addAll(bondDetailService.queryBondFieldGroupMappingList(4));// 利润
                            list.addAll(bondDetailService.queryBondFieldGroupMappingList(5));// 现金流量

                            list.stream().forEach(doc -> {
                                BondFieldGroupMappingDocs.put(doc.getColumnName(), doc);
                            });

                            return BondFieldGroupMappingDocs;
                        }
                    });

    public Map<String, BondFieldGroupMappingDoc> BOND_FINELD_GROUP_MAPPING() {

        Map<String, BondFieldGroupMappingDoc> map = new HashMap<String, BondFieldGroupMappingDoc>();

        try {
            map = localCache.get("map");
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;

    }

}
