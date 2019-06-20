/**
 * 
 */
package com.innodealing.bond.service;

import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.param.BondRating;
import com.innodealing.engine.jdbc.bond.BondSearchExtDao;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondComparisonDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.util.StringUtils;

/**
 * @author Administrator
 *
 */
@Service
public class BondBasicInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(BondBasicInfoService.class);

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    BondBasicInfoRepository basicInfoReposity;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private BondFavoriteService bondFavService;

    @Autowired
    private BondComparisonService comparisonService;

    @Autowired
    BondInstitutionInduAdapter induAdapter;
    
    @Autowired
    BondSearchExtDao bondSearchExtDao;
    
    @Autowired
    BondRatingPoolService bondRatingPoolService;

    LoadingCache<String, BondBasicInfoDoc> shortNameCache = CacheBuilder.newBuilder().concurrencyLevel(5).weakKeys()
            .maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, BondBasicInfoDoc>() {
                public BondBasicInfoDoc load(String key) throws Exception {
                    return mongoOperations.findOne(
                            new Query(new Criteria().andOperator(Criteria.where("shortName").is(key),
                                    Criteria.where("currStatus").is(1), Criteria.where("issStaPar").is(1))),
                            BondBasicInfoDoc.class);
                }
            });

    LoadingCache<Long, BondBasicInfoDoc> basicInfoCache = CacheBuilder.newBuilder().concurrencyLevel(5).weakKeys()
            .maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<Long, BondBasicInfoDoc>() {
                public BondBasicInfoDoc load(Long key) throws Exception {
                    return basicInfoReposity.findOne(key);
                }
            });
    
    LoadingCache<Long, BondBasicInfoDoc> basicInfoIssuerCache = CacheBuilder.newBuilder().concurrencyLevel(5).weakKeys()
            .maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<Long, BondBasicInfoDoc>() {
                public BondBasicInfoDoc load(Long key) throws Exception {
                	Query query = new Query();
                	query.addCriteria(Criteria.where("issuerId").is(key)).fields().include("issuer")
                	.include("comPro").include("mainBus").include("sidBus");
                	return mongoOperations.findOne(query, BondBasicInfoDoc.class);
                	
                    /*return mongoOperations.findOne(
                    		new Query(new Criteria().andOperator(Criteria.where("issuerId").is(key))).
                    		fields().include("").include("").include(""),BondBasicInfoDoc.class);*/
                }
            });

    public BondBasicInfoDoc findByBondUniCode(Long bondUniCode,long userid) 
	{
		try {
			if(bondUniCode == null){
				return null;
			}
			BondBasicInfoDoc doc =  basicInfoCache.get(bondUniCode);
			if(doc!=null && doc.getIssuerId()!=null && userid!=0)
				doc.setBondCreditRatingGroup(bondRatingPoolService.getRatingGroupByComUniCode(userid, doc.getIssuerId()));
			return doc;
		} catch (Exception e) {
//			e.printStackTrace();
			LOG.error("主体为空 bondUniCode =" + bondUniCode );
			return null;
		}
	}
    
    public BondBasicInfoDoc findByUserAndBondUniCode(Long userId, Long bondUniCode) 
    {
        HashMap<Long, BondFavorite> bondUniCode2FavMap = new HashMap<Long, BondFavorite>();
        List<BondFavorite> favList = bondFavService.findFavoriteByUserId(toIntExact(userId));
        if (favList != null) {
            favList.forEach(f -> {
                bondUniCode2FavMap.put(f.getBondUniCode(), f);
            });
        }

        Set<Long> comparisonSet = new HashSet<Long>();
        List<BondComparisonDoc> comps = comparisonService.findComparisonByUserId(userId);
        if (comps != null) {
            comps.forEach(compDoc -> {
                comparisonSet.add(compDoc.getBondId());
            });
        }

        BondBasicInfoDoc bond = null;
        try {
            bond = basicInfoCache.get(bondUniCode);
        } catch (ExecutionException e) {
            LOG.error("获得债券信息失败", e);
            return null;
        }
        
        if (bond != null) {
            BondFavorite fav = bondUniCode2FavMap.get(bond.getBondUniCode());
            if (fav != null) {
                bond.setIsFavorited(true);
                bond.setFavoriteId(fav.getFavoriteId());
            } else {
                bond.setIsFavorited(false);
            }
            bond.setIsCompared(comparisonSet.contains(bond.getBondUniCode()));
        }
        
        return bond;
    }
    
    public BondBasicInfoDoc findByIssuerId(Long issuerId) 
   	{
    	BondBasicInfoDoc result = null;
   		try {
   		    result =  basicInfoIssuerCache.get(issuerId);
   		    if(StringUtils.isEmpty(result)){
   		    	return result;
   		    }
   			return result;
   		} catch (ExecutionException e) {
   			e.printStackTrace();
   			return result;
   		}
   	}

    public BondBasicInfoDoc findByShortName(String shortName) {
        try {
            return shortNameCache.get(shortName);
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    public List<BondBasicInfoDoc> searchByNameAndCode(String queryString, Integer limit) {
        PageRequest request = new PageRequest(0, limit, new Sort(Sort.Direction.DESC, "code"));

        // TODO performance optimization by better TECH
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("currStatus").is(1), Criteria.where("issStaPar").is(1));
        criteria.orOperator(Criteria.where("shortName").regex("^.*" + queryString + ".*$"),
                Criteria.where("code").regex("^.*" + queryString + ".*$"));

        Query query = new Query();
        query.addCriteria(criteria).with(request);
        query.fields().include("bondUniCode").include("code").include("shortName");

        return mongoOperations.find(query, BondBasicInfoDoc.class);
    }

    public List<BondRating> findRatingHist(Long bondId) {
        String sql = String
                .format("SELECT bond_uni_code, bond_cred_level, rate_writ_date, CHI_SHORT_NAME as org_short_name\r\n"
                        + "				FROM bond_ccxe.D_BOND_CRED_CHAN  \r\n"
                        + "				left join bond_ccxe.d_pub_org_info_r on D_BOND_CRED_CHAN.ORG_UNI_CODE = d_pub_org_info_r.ORG_UNI_CODE\r\n"
                        + "				WHERE bond_uni_code = " + bondId + "\r\n"
                        + "				AND D_BOND_CRED_CHAN.isvalid = 1  \r\n"
                        + "				ORDER BY rate_writ_date DESC");

        List<BondRating> bondRatings = (List<BondRating>) jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<BondRating>(BondRating.class));
        if (bondRatings == null) {
            LOG.error("internal error");
            return new ArrayList<BondRating>();
        }
        ;

        return bondRatings;
    }

    public List<BondBasicInfoDoc> searchByNameAndCode(String codePrefix, String shortNamePrefix, String operator,
            Integer page, Integer limit) {
        PageRequest request = new PageRequest(page, limit, new Sort(Sort.Direction.ASC, "code"));

        List<Criteria> subCriterias = new ArrayList<Criteria>();
        if (!StringUtils.isEmpty(codePrefix))
            subCriterias.add(Criteria.where("code").regex("^.*" + codePrefix + ".*$"));
        if (!StringUtils.isEmpty(shortNamePrefix))
            subCriterias.add(Criteria.where("shortName").regex("^.*" + shortNamePrefix + ".*$"));

        Criteria mainCriteria = new Criteria();

        Criteria filter = new Criteria();
        filter.andOperator(Criteria.where("currStatus").is(1), Criteria.where("issStaPar").is(1));

        if (operator.equalsIgnoreCase("and"))
            mainCriteria.andOperator(
                    new Criteria().andOperator(subCriterias.toArray(new Criteria[subCriterias.size()])), filter);
        else if (operator.equalsIgnoreCase("or"))
            mainCriteria.andOperator(new Criteria().orOperator(subCriterias.toArray(new Criteria[subCriterias.size()])),
                    filter);
        Query query = new Query();
        query.addCriteria(mainCriteria).with(request);
        query.fields().include("bondUniCode").include("code").include("shortName").include("tenor");

        return mongoOperations.find(query, BondBasicInfoDoc.class);
    }

    //这个函数的返回值只被使用了isFavorited/isCompared, 所以不做induId的转换
    //TODO: 违反LoD
    public BondDetailDoc isFavoritedAndCompared(Long userId, Long bondId) {
        
        if (userId == null) {
            throw new BusinessException("用户 id不能为空");
        }

        HashMap<Long, BondFavorite> bondUniCode2FavMap = new HashMap<Long, BondFavorite>();
        List<BondFavorite> favList = bondFavService.findFavoriteByUserId(toIntExact(userId));
        if (favList != null) {
            favList.forEach(f -> {
                bondUniCode2FavMap.put(f.getBondUniCode(), f);
            });
        }

        // 对比列表
        Set<Long> comparisonSet = new HashSet<Long>();
        List<BondComparisonDoc> comps = comparisonService.findComparisonByUserId(userId);
        if (comps != null) {
            comps.forEach(compDoc -> {
                comparisonSet.add(compDoc.getBondId());
            });
        }
        BondDetailDoc bondDetailDocs = new BondDetailDoc();

        BondFavorite fav = bondUniCode2FavMap.get(bondId);
        if (fav != null) {
            bondDetailDocs.setIsFavorited(true);
            bondDetailDocs.setFavoriteId(fav.getFavoriteId());
        } else {
            bondDetailDocs.setIsFavorited(false);
        }
        bondDetailDocs.setIsCompared(comparisonSet.contains(bondId));

        return bondDetailDocs;
    }
    
	/**
     * 搜索债劵
     * @param queryString
     * @param limit
     * @return
     */
    public Page<BondBasicInfoDoc> searchBond(String queryString, Integer limit, Long userId) {
    	
 		if (userId == null) {
 			throw new BusinessException("用户 id不能为空");
 		}
    	
        PageRequest request = new PageRequest(0, limit, new Sort(Sort.Direction.DESC, "code"));
        
		List<Long> list = new ArrayList<Long>();
        if(!StringUtils.isEmpty(queryString)) {
            queryString = queryString==null ? "" : queryString.trim().toLowerCase();
            queryString = StringUtils.filter(queryString);//替换特殊符号
            list = bondSearchExtDao.findBondUniCodesByQuerykey(queryString);
        }
        /**TODO 二期要加入新债搜索，和前端一起上线。新债放入单独标签显示，最好新增一个接口
         * List<BondBasicInfoDoc> basicInfoList = bondSearchExtDao.findNewBondsByQuerykey(queryString);
         * 返回字段如下,参照BondBasicDataConstructHandler.saveBondInfo2Mongo
         * "bondUniCode": 103007584, //债券id
                "code": "7110.IB", //债券代码
                "shortName": "02苏交通债", //债券缩写
                "tenor": "90D",//剩余期限  ，需计算
                "issBondRating": "AAA/AAA", //主体债项，需计算
                "bondRating": "AAA", //外部债项评级，根据issBondRating获取
                "isFavorited": false, //是否关注，下面会计算
                "isCompared": false //是否已经加入对比，下面会计算
         */
        List<BondBasicInfoDoc> basicInfoList = new ArrayList<BondBasicInfoDoc>();
        long count = 0;
        if(null != list && !list.isEmpty()){
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator( Criteria.where("currStatus").is(1), Criteria.where("issStaPar").is(1), 
                    Criteria.where("_id").in(list));

            query.addCriteria(criteria).with(request);
            query.fields().include("bondUniCode").include("code").include("shortName").include("tenor").include("issBondRating").include("issuerId");
            
            basicInfoList = mongoOperations.find(query, BondBasicInfoDoc.class);
            count = mongoOperations.count(query, BondBasicInfoDoc.class);
        }
        
        if(basicInfoList==null || basicInfoList.isEmpty()){
			return new PageImpl<>(basicInfoList);
		}
 		
 		ExecutorService CachedThreadPool = Executors.newCachedThreadPool();
 		
 		for(BondBasicInfoDoc basicInfoDoc : basicInfoList){
 			CachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					BondDetailDoc detailDoc =  isFavoritedAndCompared(userId,basicInfoDoc.getBondUniCode());
					if(detailDoc!=null){
						//处理对比与关注
						basicInfoDoc.setFavoriteId(detailDoc.getFavoriteId());
						basicInfoDoc.setIsFavorited(detailDoc.getIsFavorited());
						basicInfoDoc.setIsCompared(detailDoc.getIsCompared());
					}
					//外部评级 (临时用下这个变量)
					String issBondRating = basicInfoDoc.getIssBondRating();
					if(!StringUtils.isEmpty(issBondRating) && issBondRating.contains("/")) {
						String bondRating = issBondRating.substring(issBondRating.indexOf("/")+1, issBondRating.length());
						basicInfoDoc.setBondRating(bondRating);
					}
				}
			});
 		}
 		
 		CachedThreadPool.shutdown();
		try {
			CachedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOG.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		
 		Page<BondBasicInfoDoc> basicInfoPage = new PageImpl<BondBasicInfoDoc>(
 		        (List<BondBasicInfoDoc>) induAdapter.conv(basicInfoList, userId), 
 		        request,count);
 		
 		return basicInfoPage;
    }

	public List<BondBasicInfoDoc> findValidBondBasicInfosByIssuerId(Long issuerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("issuerId").is(issuerId).and("currStatus").is(1).and("issStaPar").is(1));
		return mongoOperations.find(query, BondBasicInfoDoc.class);
	}
	
	public List<BondBasicInfoDoc> findListByShortName(String bondShortName){
		 Query query = new Query();
	     query.addCriteria(Criteria.where("shortName").is(bondShortName));
	     return mongoOperations.find(query, BondBasicInfoDoc.class);
	}

}
