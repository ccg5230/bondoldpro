//package com.innodealing.engine.mongo.bond;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Component;
//
//import com.innodealing.model.mongo.dm.bond.detail.analyse.InduCredRatingDoc;
///**
// * 行业分析-同行业主体评级下降
// * @author zhaozhenglai
// * @since 2016年9月9日 下午2:10:51 
// * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
// */
//@Component
//public interface InduCredRatingRepository extends MongoRepository<InduCredRatingDoc, Long> {
//    
//    public List<InduCredRatingDoc> findByInduId(String induId);
//    
//    public Page<InduCredRatingDoc> findByInduId(Long induId, Pageable pageable); 
//    
//    
//    public Page<InduCredRatingDoc> findByInduIdAndCurrPAndCurrStatus(Long induId,int currP, int currStatus, Pageable pageable);
//    
//    public Page<InduCredRatingDoc> findByInduIdInAndCurrPAndCurrStatus(List<Long> induIds,int currP, int currStatus, Pageable pageable);
//}
