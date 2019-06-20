package com.innodealing.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.innodealing.BondApp;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.service.IssAnalysisService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class BondAnalysisServiceTest {
    @Autowired
    private IssAnalysisService issAnalysisService;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    
    private JdbcTemplate jdbcTemplate;
    /************************************数据转换开始*******************************************/
    
    @Test
    public void buildIndicatorFieldMappingTest() {
        long start = System.currentTimeMillis();
        boolean result = false;
        result = issAnalysisService.buildIndicatorFieldMapping();
        long end = System.currentTimeMillis();
        System.out.println(" buildIndicatorFieldMapping 总时间：" + (end - start) + "ms");
        Assert.isTrue(result, "数据构建失败");
    }
    
    
    @Test
    public void buildIssFinanceDateTest() {
        long start = System.currentTimeMillis();
        boolean result = false;
        result = issAnalysisService.buildIssFinanceDate();
        long end = System.currentTimeMillis();
        System.out.println(" buildIndicatorFieldMapping 总时间：" + (end - start) + "ms");
        Assert.isTrue(result, "数据构建失败");
    }
//    @Test
//    public void bulidIssIndicatorTest() {
//        long start = System.currentTimeMillis();
//        boolean result = false;
//        try {
//            result = issAnalysisService.bulidIssIndicator();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IntrospectionException e) {
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println(" bulidIssIndicator 总时间：" + (end - start) + "ms");
//        Assert.isTrue(result, "数据构建失败");
//    }
    
   
    
    
   
    @Test
   public void testIndicator(){
        long start = System.currentTimeMillis();
        boolean result = false;
        result = issAnalysisService.buildAllIssIndicators();
        issAnalysisService.buildInduProvinceMap();
        long end = System.currentTimeMillis();
        System.out.println(" buildAllIssIndicators 总时间：" + (end - start) + "ms");
        Assert.isTrue(result, "数据构建失败");
   }
    
    
    @Test
    public void testBuildIssFinanceDate(){
         long start = System.currentTimeMillis();
         boolean result = false;
         result = issAnalysisService.buildIssFinanceDate();
         long end = System.currentTimeMillis();
         System.out.println(" buildIssFinanceDate 总时间：" + (end - start) + "ms");
         Assert.isTrue(result, "数据构建失败");
    }
    
    @Test
    public void testCityIssMapping(){
        Query query = new Query();
        query.fields()
            .include("comUniCode")
            .include("areaUniCode1");
        List<BondComInfoDoc> list = mongoTemplate.find(query, BondComInfoDoc.class);
        System.out.println(list.size());
    }
    /************************************数据转换结束*******************************************/
    
    
    /************************************接口测试开始*******************************************/
    
    /************************************接口测试结束*******************************************/
    
    
}
