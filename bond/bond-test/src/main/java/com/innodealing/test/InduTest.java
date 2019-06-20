package com.innodealing.test;

import java.io.Serializable;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.innodealing.BondApp;
import com.innodealing.service.InduService;

import io.swagger.annotations.ApiModelProperty;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class InduTest {
    @Autowired
    private InduService induService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

//    @Test
//    public void buildPdCompareDocTest() {
//        long start = System.currentTimeMillis();
//        boolean result = induService.buildPdCompareDoc();
//        long end = System.currentTimeMillis();
//        System.out.println(" buildPdCompareDocTest 总时间：" + (end - start) + "ms");
//        Assert.isTrue(result, "数据构建失败");
//    }

//    @Test
//    public void buildInduCredRatingTest() {
//        long start = System.currentTimeMillis();
//        boolean result = induService.buildInduCredRating();
//        long end = System.currentTimeMillis();
//        System.out.println(" buildInduCredRating 总时间：" + (end - start) + "ms");
//        Assert.isTrue(result, "数据构建失败");
//    }

//    @Test
//    public void buildInduRatingAndPopTest() {
//        long start = System.currentTimeMillis();
//        boolean result = induService.buildInduRatingAndPop();
//        long end = System.currentTimeMillis();
//        System.out.println(" buildInduRatingAndPop 总时间：" + (end - start) + "ms");
//        Assert.isTrue(result, "数据构建失败");
//    }

//    @Test
//    public void buildBondRatingAndPopTest() {
//        long start = System.currentTimeMillis();
//        boolean result = induService.buildBondRatingAndPop();
//        long end = System.currentTimeMillis();
//        System.out.println(" buildBondRatingAndPop 总时间：" + (end - start) + "ms");
//        Assert.isTrue(result, "数据构建失败");
//    }

    
    @Test
    public void buildPdRatingTest() {
        long start = System.currentTimeMillis();
        boolean result = induService.buildPdRating();
        long end = System.currentTimeMillis();
        System.out.println(" findPdRating 总时间：" + (end - start) + "ms");
        Assert.isTrue(result, "数据构建失败");
    }
    
    
    @Test
    public void buildInduMapSwTest() {
        long start = System.currentTimeMillis();
        boolean result = induService.buildInduMapSw();
        long end = System.currentTimeMillis();
        System.out.println(" buildInduMapSw 总时间：" + (end - start) + "ms");
        Assert.isTrue(result, "buildInduMapSw");
    }

    @Test
    public void t() throws ParseException {
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//         Query query = new Query(Criteria.where("pubDate").gt(sf.parse("2016-09-28")));
//         //bond_sentiment_date_top
//         //BondSentimentTopDateDoc
//         List<BondSentimentTopDateDoc> bd = mongoTemplate.find(query, BondSentimentTopDateDoc.class);
//        System.out.println(bd);
        
    }

    
    
}

