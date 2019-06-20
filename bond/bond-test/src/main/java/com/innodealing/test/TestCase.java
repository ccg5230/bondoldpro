package com.innodealing.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.innodealing.BondApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class TestCase {
    @Autowired
    private JdbcTemplate JdbcTemplate;

    @Test
    public void testSql(){
        String sql  = "select id from t_sysuser";
        List<Map<String,Object>> list = JdbcTemplate.queryForList(sql);
        System.out.println(list.size());
    }
    
    public static void main(String[] args) {
    	RestTemplate restTemplate = new RestTemplate();
    	String url ="http://www.shclearing.com/wcm/shch/pages/client/download/download.jsp?FileName=P020170913632190943504.pdf&DownName=金科地产集团股份有限公司关于第十届董事会第八次会议决议的公告.pdf";
    	ResponseEntity<byte[]> res = restTemplate.postForEntity(url, new HashMap<>(), byte[].class);
    	System.out.println(res);
	}
}
