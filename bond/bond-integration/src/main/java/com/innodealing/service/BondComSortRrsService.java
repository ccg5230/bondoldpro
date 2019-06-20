package com.innodealing.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.innodealing.aop.NoLogging;
import com.innodealing.model.CompRRS;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.SimpleStatistics;

@Component
public class BondComSortRrsService {
    
    private static final Logger LOG = LoggerFactory.getLogger(BondComSortRrsService.class);
    
    @Autowired
    private  JdbcTemplate jdbcTemplate;
    
    private boolean isIntialized = false; 
    private HashMap<Long, Double> comRrsMap = new  HashMap<Long, Double>();
    private HashMap<Long, Long> comPdMap = new  HashMap<Long, Long>();
    
    class ZScoreParam
    {
        public ZScoreParam(Double mean, Double stddev) {
            super();
            this.mean = mean;
            this.stddev = stddev;
        }
        private Double mean;   
        private Double stddev;

        public Double getMean() {
            return mean;
        }
        public void setMean(Double mean) {
            this.mean = mean;
        }
        public Double getStddev() {
            return stddev;
        }
        public void setStddev(Double stddev) {
            this.stddev = stddev;
        }
    };
    
    private HashMap<Long, ZScoreParam> modelStdDevMap = new  HashMap<Long, ZScoreParam>();

    @NoLogging
    synchronized public Double findSortRrsByComUniCode(Long comUniCode) {
        init();
        if (comUniCode != null) {
            Double adjust = comRrsMap.get(comUniCode);
            Long pd = comPdMap.get(comUniCode);
            if (pd == null) {
                LOG.warn("comUniCode " + comUniCode + " pd is null");
                return 0.00;
            }
            return pd*10 - ((adjust==null)? 0.00:adjust);
        }
        return 0.00;
    }
    
    public void init() {
        if (!isIntialized) {
            loadData();
            isIntialized = true;
        }
    }
    
    public void loadData()
    {
    	comPdMap.clear();
        final String sql = "select P.Comp_ID, P.year, X.com_uni_code, P.Rating, R.id as pdOrder, S.model_id, S.model_name, IFNULL(RATIO1, 0) + IFNULL(RATIO2, 0) + IFNULL(RATIO3, 0) + IFNULL(RATIO4, 0) + IFNULL(RATIO5, 0) + IFNULL(RATIO6, 0) + IFNULL(RATIO7, 0) + IFNULL(RATIO8, 0) + IFNULL(RATIO9, 0) + IFNULL(RATIO10, 0) AS SCORE  from \r\n" + 
                "(\r\n" + 
                "    select * from (\r\n" + 
                "        select Comp_ID, Rating, year, concat(year, LPAD(CAST(quan_month as CHAR(10)), 2, '0')) as ym from  /*amaresun*/ dmdb.dm_bond\r\n" + 
                // "        where dm_bond.year in ('" + Calendar.getInstance().get(Calendar.YEAR)  + "', '" + (Calendar.getInstance().get(Calendar.YEAR)-1) + "') \r\n" +
                "        where Rating is not null " + 
                "        order by dm_bond.year desc , dm_bond.quan_month desc \r\n" + 
                "    ) T\r\n" + 
                "    group by Comp_ID\r\n" + 
                ") P\r\n" + 
                "left join  /*amaresun*/ dmdb.rating_ratio_score S on P.Comp_ID = S.Comp_ID and P.ym = S.YEAR\r\n" + 
                "left join dmdb.t_bond_com_ext as X on P.Comp_ID = X.ama_com_id\r\n" + 
                "left join dmdb.t_bond_pd_par as R on P.Rating = R.rating\r\n" + 
                "WHERE X.com_uni_code is not null  \r\n" + 
                "order by model_id , Comp_ID\r\n" + 
                "";

        List<CompRRS> records = (List<CompRRS>) jdbcTemplate.query(sql, 
                new BeanPropertyRowMapper<CompRRS>(CompRRS.class));
        if (records == null ) {
            LOG.error("calculateRatingRatioScore internal error, empty result set");
            return ;
        };

        //计算模型score标准差
        Long curModelId = null;
        CompRRS curCompRrs = null;
        List<Double> modelScores = new ArrayList<Double>();

        for (int i = 0; i < records.size(); ++ i) {
            curCompRrs = records.get(i);
            
            //跳过score缺最新一期的数据
            if (curCompRrs.getModelId() == null) {
                LOG.warn("rating_ratio_score 缺少最新一期数据, ComUniCode:" + curCompRrs.getComUniCode());
                continue;
            }
            
            if (curModelId == null) {
                curModelId = curCompRrs.getModelId();
            }
            else  if (!curModelId.equals(curCompRrs.getModelId())) {
                saveModelDev(curModelId, modelScores);
                modelScores = new ArrayList<Double>();
                curModelId = curCompRrs.getModelId();
            }
            
            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            if (curCompRrs.getYear().equals(thisYear) || curCompRrs.getYear().equals(thisYear -1))
                modelScores.add(curCompRrs.getScore());
        }

        saveModelDev(curModelId, modelScores);

        //计算公司score在行业中的位置
        for (CompRRS r : records) {
            Double sortScore = null;
            if (r.getModelId() != null) {
                ZScoreParam zScoreParam = modelStdDevMap.get(r.getModelId());
                if (zScoreParam != null) {
                    if (r.getScore() != null) {
                        sortScore = (r.getScore() - zScoreParam.getMean())/zScoreParam.getStddev();
                        comRrsMap.put(r.getComUniCode(), sortScore);
                    }
                }
                else {
                    LOG.error("modelId " + r.getModelId() + " does not exist");
                }
            }
            comPdMap.put(r.getComUniCode(), r.getPdOrder());
            LOG.debug("compId:" + r.getComUniCode() + " sort-score:" + sortScore);
        }
    }
    
    private void saveModelDev(Long curModelId, List<Double> modelScores) {
        Double stdDev = new SimpleStatistics(modelScores.toArray(new Double[modelScores.size()])).getStdDev();
        Double mean = new SimpleStatistics(modelScores.toArray(new Double[modelScores.size()])).getMean();
        LOG.info("model:" + curModelId + " mean:" + mean + " stddev:" + stdDev);
        modelStdDevMap.put(curModelId, new ZScoreParam(mean, stdDev));
    }
}
