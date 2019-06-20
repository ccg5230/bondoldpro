package com.innodealing.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.innodealing.BondApp;
import com.innodealing.service.RatingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class RatingServiceTest {
    @Autowired
    private RatingService ratingService;
    
    
    @Test
    public void buildPdRatingTest() {
        long start = System.currentTimeMillis();
        boolean result = ratingService.buildRatingIssBondDoc();
        long end = System.currentTimeMillis();
        System.out.println(" buildRatingIssBondDoc 总时间：" + (end - start) + "ms");
        Assert.isTrue(result, "数据构建失败");
    }

  
}
