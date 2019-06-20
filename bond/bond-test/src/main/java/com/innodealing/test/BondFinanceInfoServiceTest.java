package com.innodealing.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.innodealing.BondApp;
import com.innodealing.bond.service.finance.BondFinanceInfoService;
import com.innodealing.bond.vo.analyse.IssFinanceChangeKVo;
import com.innodealing.bond.vo.finance.ComQuantileInfoVo;
import com.innodealing.engine.jdbc.bond.IndicatorDao;
import com.innodealing.engine.mongo.bond.BondFinanceInfoInduRepository;
import com.innodealing.engine.mongo.bond.BondFinanceInfoQuarterRepository;
import com.innodealing.util.SafeUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class BondFinanceInfoServiceTest {

    @Autowired
    private BondFinanceInfoService bondFinanceInfoService;

    
    @Autowired
    private BondFinanceInfoInduRepository bondFinanceInfoInduRepository;

    @Autowired
    private BondFinanceInfoQuarterRepository bondFinanceInfoQuarterRepository;

   
    @Test
    public void findBondFinanceInfoInduDocTest() {

    }

   

    @Test
    public void findBondInduAnalyseTest() {
//        BondInduAnalyseDoc analyse = new BondInduAnalyseDoc(1l, 100, null);
//        InduAnalyse analyse1 = new InduAnalyse("", 20, 30);
//        InduAnalyse analyse2 = new InduAnalyse("", 20, 30);
//        InduAnalyse analyse3 = new InduAnalyse("", 20, 30);
//        InduAnalyse analyse4 = new InduAnalyse("", 20, 30);
//        InduAnalyse analyse5 = new InduAnalyse("", 20, 30);
//
//        List<InduAnalyse> induAnalyses = new ArrayList<>();
//        induAnalyses.add(analyse1);
//        induAnalyses.add(analyse2);
//        induAnalyses.add(analyse3);
//        induAnalyses.add(analyse4);
//        induAnalyses.add(analyse5);
//
//        analyse.setInduAnalyses(induAnalyses);
//
//        BondInduAnalyseDoc saved = bondInduAnalyseRepository.save(analyse);
//
//        Assert.notNull(saved, "bondInduAnalyseRepository  保存失败");

//        BondInduAnalyseDoc induAnalyse = bondFinanceInfoService.findBondInduAnalyse(2104001L);
//        System.out.println("induAnalyse-->" + induAnalyse);
    }

    @Test
    public void findIssInduRatingCompare() {

//        Random rand = new Random();
//        List<InduCredRatingDoc> icrs = new ArrayList<>();
//        for (long induId = 0; induId < 100; induId++) {
//
//            for (long issId = 0; issId < 1000; issId++) {
//                int currR = rand.nextInt(6), lastR = rand.nextInt(6);
//                InduCredRatingDoc icr = new InduCredRatingDoc(induId, issId, "胡同电力" + issId,
//                        currR == 0 ? 1 : currR, lastR == 0 ? 1 : lastR);
//                icrs.add(icr);
//
//            }
//        }
        // 批量添加
        // induCredRatingRepository.save(icrs);

        // long count = mongoTemplate.count(new
        // Query(Criteria.where("lastR").is(1)), InduCredRatingDoc.class);
        // System.out.println("数量---》" + count);

//        List<IssInduRatingCompareVo> result = bondFinanceInfoService.findIssInduRatingCompare(1+"" , 2+"");
//        Assert.notEmpty(result, "没有查询结果");
//        System.out.println("findIssInduRatingCompare-->" + result.toString());

    }

    
    @Test
    public void findIssFinanceChangeKXTest() {
        long start = System.currentTimeMillis();
        boolean result = false;
        Long issId = 96521L;
        String category = "增长指标";
        String field = "bank_ratio8";
        List<IssFinanceChangeKVo> list =  bondFinanceInfoService.findIssFinanceChangeKX(issId, category, field, null, 500115L);
        long end = System.currentTimeMillis();
        System.out.println(" buildIndicatorFieldMapping 总时间：" + (end - start) + "ms");
        Assert.notEmpty(list,"数据为空");
    }

    @Test
    public void findIssBreachProbabilityCompareTest(){
//        List<Long> iss = SafeUtils.strToArrLong("");
//        List<Long> com = SafeUtils.strToArrLong("");
//        PdCompareVo PdCompareVo = bondFinanceInfoService.findIssBreachProbabilityCompare(iss, com);
//        System.out.println(PdCompareVo);
//        Assert.notNull(PdCompareVo,"数据为空");
    }
    
    @Test
    public void findIssInduRatingCompareTest() {
//        long start = System.currentTimeMillis();
//        String issInduId = "2107001,2107002,2107003,2107004,2107999";
//        String compareInduId = issInduId;
//        List<IssInduRatingCompareVo> list = bondFinanceInfoService.findIssInduRatingCompare(SafeUtils.strToArrLong(issInduId), SafeUtils.strToArrLong(compareInduId));
//        long end = System.currentTimeMillis();
//        System.out.println(" findIssInduRatingCompare 总时间：" + (end - start) + "ms");
//        Assert.notEmpty(list,"数据为空");
    }
    
    @Test
    public void quartileMapTest() throws Exception {
    	Date finDate = SafeUtils.convertStringToDate("2007-06-30", SafeUtils.DATE_FORMAT);
    	Map<String, Integer> result = bondFinanceInfoService.quartileMap(200000687L, finDate, null, IndicatorDao.SPECIAL);
    	System.out.println(result);
    }
    
    private static final Logger LOG = LoggerFactory.getLogger(BondFinanceInfoServiceTest.class);
    
    @Test 
    public void findComQuantile(){
    	List<String> fields = new ArrayList<>();
    	fields.add("AP_day");
    	fields.add("Turnover");
    	ComQuantileInfoVo result = bondFinanceInfoService.findComQuantile(200354704L, fields, 516733L);
    	LOG.info("findComQuantile->" + result.toString());
    }
}
