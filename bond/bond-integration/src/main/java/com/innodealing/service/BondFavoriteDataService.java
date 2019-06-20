package com.innodealing.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.internet.MimeMessage;

import com.google.gson.internal.LinkedTreeMap;
import com.innodealing.consts.Constants;
import com.innodealing.consts.PortfolioConstants;
import com.innodealing.domain.JsonResult;
import com.innodealing.engine.jdbc.NotificationMsgDao;
import com.innodealing.engine.jdbc.bond.*;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.mongo.dm.*;
import com.innodealing.util.HttpClientUtil;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StopWatch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.innodealing.domain.vo.BondFavoriteGroupEmailVO;
import com.innodealing.engine.jpa.dm.BondFavoriteFinaIndexRepository;
import com.innodealing.engine.jpa.dm.BondFavoriteGroupRepository;
import com.innodealing.engine.jpa.dm.BondFavoritePriceIndexRepository;
import com.innodealing.engine.jpa.dm.BondFavoriteRadarMappingRepository;
import com.innodealing.engine.jpa.dm.BondFavoriteRadarSchemaRepository;
import com.innodealing.engine.jpa.dm.BondFavoriteRepository;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondFavoriteDocRepository;
import com.innodealing.engine.mongo.bond.BondFavoriteGroupDocRepository;
import com.innodealing.engine.mongo.bond.BondNotificationMsgDocRepository;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.dm.bond.BondFavoriteFinaIndex;
import com.innodealing.model.dm.bond.BondFavoriteGroup;
import com.innodealing.model.dm.bond.BondFavoritePriceIndex;
import com.innodealing.model.dm.bond.BondFavoriteRadarMapping;
import com.innodealing.model.dm.bond.BondFavoriteRadarSchema;
import com.innodealing.model.dm.bond.BondNotificationMsg;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

@Service
public class BondFavoriteDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BondFavoriteDataService.class);

    private @Autowired
    JdbcTemplate jdbcTemplate;

    private @Autowired
    MongoTemplate mongoTemplate;

    private @Autowired
    BondFavoriteDao favoriteDAO;

    private @Autowired
    BondFavoriteGroupDAO groupDAO;

    private @Autowired
    NotificationMsgDao msgDAO;

    private @Autowired
    BondFavoriteRadarMappingDAO radarMappingDAO;

    private @Autowired
    BondFavoritePriceIndexDAO priceIndexDAO;

    private @Autowired
    BondFavoriteFinaIndexDAO finaIndexDAO;

    private @Autowired
    BondNotificationMsgDocRepository bondNotificationMsgDocRepository;

    private @Autowired
    BondFavoriteDocRepository bondFavoriteDocRepository;

    private @Autowired
    BondFavoriteGroupDocRepository bondFavoriteGroupDocRepository;

    private @Autowired
    BondFavoriteRepository bondFavoriteRepository;

    private @Autowired
    BondFavoriteGroupRepository groupRepo;

    private @Autowired
    BondBasicInfoRepository bondBasicInfoRepository;

    protected @Autowired
    MongoOperations mongoOperations;

    private @Autowired
    BondFavoriteRadarSchemaRepository bondFavRadarSchemaRepo;

    private @Autowired
    Gson gson;

    private @Autowired
    JavaMailSender mailSender;

    private @Autowired
    VelocityEngine velocityEngine;

    private @Autowired
    BondFavoriteRadarMappingRepository favRadarMappingRepo;

    private @Autowired
    BondBasicInfoRepository bondBasicInfoRepo;

    @Autowired
    private ApplicationContext context;

    private @Autowired
    BondFavoritePriceIndexRepository favPriceIndexRepo;

    private @Autowired
    BondFavoriteFinaIndexRepository favFinaIndexRepo;

    protected @Autowired
    RedisUtil redisUtil;

    @Value("${config.define.bondsentiment.sentimentDetailUrl}")
    private String sentimentDetailUrl;

    @Value("${spring.mail.username}")
    private String emailAccount;

    public String buildBondNotificationMsgDoc(Long gte, Long lte) {
        int limit = 5000;
        int count = this.getBondNotificationMsgCount(gte, lte);
        int num = count / limit + 1;

        LOGGER.info("buildBondNotificationMsgDoc start...");
        LOGGER.info("buildBondNotificationMsgDoc msg count:" + count + ", pages:" + num + ", limit:" + limit);

        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("_id").lte(gte), Criteria.where("_id").gte(lte));
        Query rmQuery = new Query(criteria);
        mongoTemplate.remove(rmQuery, BondNotificationMsgDoc.class);

        ExecutorService exec = Executors.newFixedThreadPool(10);
        ArrayList<Integer> pages = new ArrayList<Integer>();
        // for debug
        // num = 1;
        for (int page = 0; page < num; page++) {
            pages.add(page);
        }

        for (Integer workingPage : pages) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    StringBuffer sql = new StringBuffer();
                    sql.append(
                            " SELECT t.seq_id AS id, t.bond_id AS bondId, t.event_type AS eventType, t.msg_content AS msgContent,t.create_time AS createTime, t.news_index AS newsIndex, t.important AS important ");
                    sql.append(" FROM dmdb.t_bond_notification_msg t ");
                    sql.append(" where t.seq_id >=").append(gte);
                    sql.append(" and t.seq_id <=").append(lte);
                    sql.append(" ORDER BY t.seq_id ASC LIMIT ").append(workingPage * limit).append(",").append(limit);
                    List<BondNotificationMsgDoc> entites = jdbcTemplate.query(sql.toString(),
                            new BeanPropertyRowMapper<>(BondNotificationMsgDoc.class));
                    if (null != entites && entites.size() > 0) {
                        LOGGER.info("buildBondNotificationMsgDoc from:" + workingPage * limit + ", size:" + limit);
                        bondNotificationMsgDocRepository.save(entites);
                    }
                }
            });
        }
        exec.shutdown();
        try {
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("等待任务完成中发生异常 ", e);
            e.printStackTrace();
        }

        LOGGER.info("buildBondNotificationMsgDoc end...");
        return "done";
    }

    private int getBondNotificationMsgCount(Long gte, Long lte) {
        String countsql = "SELECT COUNT(1) FROM dmdb.t_bond_notification_msg";
        countsql += " where seq_id >= " + gte;
        countsql += " and seq_id <= " + lte;
        return jdbcTemplate.queryForObject(countsql, Integer.class);
    }

    // 每1000条构建数据
    public String buildBondFavoriteDoc() {
        int limit = 1000;
        int count = this.getBondFavoriteCount();
        int num = count / limit + 1;

        LOGGER.info("buildBondFavoriteDoc count:" + count);

        bondFavoriteDocRepository.deleteAll();

        for (int i = 0; i < num; i++) {
            StringBuffer sql = new StringBuffer();
            sql.append(
                    "SELECT t.favorite_id AS favoriteId,t.user_id AS userId,t.group_id AS groupId,t.bond_uni_code AS bondUniCode,t.create_time AS createTime,t.update_time AS updateTime,t.is_delete AS isDelete,t.bookmark,t.bookmark_update_time AS bookmarkUpdateTime,t.openinterest, t.remark ");
            sql.append("FROM dmdb.t_bond_favorite t ORDER BY t.favorite_id ASC LIMIT ").append(i * limit).append(",")
                    .append(limit);

            List<BondFavoriteDoc> entites = jdbcTemplate.query(sql.toString(),
                    new BeanPropertyRowMapper<>(BondFavoriteDoc.class));

            if (null != entites && entites.size() > 0) {

                LOGGER.info("buildBondFavoriteDoc entites size:" + entites.size());

                bondFavoriteDocRepository.save(entites);
            }
        }

        return "done";
    }

    private int getBondFavoriteCount() {
        String countsql = "SELECT COUNT(1) FROM dmdb.t_bond_favorite";
        return jdbcTemplate.queryForObject(countsql, Integer.class);
    }

    public String buildBondFavoriteGroupDoc() {
        int limit = 1000;
        int count = this.getBondFavoriteGroupCount();
        int num = count / limit + 1;

        LOGGER.info("buildBondFavoriteGroupDocData count:" + count);

        bondFavoriteGroupDocRepository.deleteAll();

        for (int i = 0; i < num; i++) {
            StringBuffer sql = new StringBuffer();
            sql.append(
                    "SELECT t.group_id as groupId,t.group_name AS groupName,t.group_type AS groupType,t.user_id AS userId,t.is_delete AS isDelete,t.notified_enable AS notifiedEnable,t.notified_eventtype AS notifiedEventtype,t.create_time AS createTime,t.update_time AS updateTime ");
            sql.append("FROM dmdb.t_bond_favorite_group t ORDER BY t.group_id ASC LIMIT ").append(i * limit).append(",")
                    .append(limit);

            List<BondFavoriteGroup> entites = jdbcTemplate.query(sql.toString(),
                    new BeanPropertyRowMapper<>(BondFavoriteGroup.class));

            if (null != entites && entites.size() > 0) {
                LOGGER.info("buildBondFavoriteGroupDocData entites size:" + entites.size());
                List<BondFavoriteGroupDoc> docEntites = new ArrayList<>();
                entites.stream().forEach(entity -> {
                    BondFavoriteGroupDoc bfgDoc = new BondFavoriteGroupDoc();
                    bfgDoc.setCreateTime(entity.getCreateTime());
                    bfgDoc.setGroupId(entity.getGroupId());
                    bfgDoc.setGroupName(entity.getGroupName());
                    bfgDoc.setGroupType(entity.getGroupType());
                    bfgDoc.setIsDelete(entity.getIsDelete());
                    bfgDoc.setNotifiedEnable(entity.getNotifiedEnable());
                    bfgDoc.setUpdateTime(entity.getUpdateTime());
                    bfgDoc.setUserId(entity.getUserId());
                    if (StringUtils.isNotBlank(entity.getNotifiedEventtype())) {
                        bfgDoc.setNotifiedEventtype(
                                gson.fromJson(entity.getNotifiedEventtype(), new TypeToken<List<Integer>>() {
                                }.getType()));
                    }

                    docEntites.add(bfgDoc);
                });

                bondFavoriteGroupDocRepository.save(docEntites);
            }
        }

        return "done";
    }

    private int getBondFavoriteGroupCount() {
        String countsql = "SELECT COUNT(1) FROM dmdb.t_bond_favorite_group";
        return jdbcTemplate.queryForObject(countsql, Integer.class);
    }

    /**
     * @return
     */
    public String cleanBondFavoriteDirtydata() {
        LOGGER.info("cleanBondFavoriteDirtydata is begin...");
        String sql = "SELECT t.group_id AS groupId,t.bond_uni_code AS bondUniCode FROM dmdb.t_bond_favorite t WHERE t.is_delete=0 GROUP BY t.group_id,t.bond_uni_code HAVING COUNT(t.bond_uni_code) > 1";

        List<BondFavorite> entites = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BondFavorite.class));

        entites.stream().forEach(bondFavorite -> {
            updateBondFavoriteData(bondFavorite);
        });

		/*
         * ExecutorService exec = Executors.newCachedThreadPool();
		 * ArrayList<BondFavorite> pages = new ArrayList<BondFavorite>(); for
		 * (int page = 0; page < entites.size(); page++) {
		 * pages.add(entites.get(page)); }
		 * 
		 * for (BondFavorite workingPage : pages) { exec.execute( new Runnable()
		 * {
		 * 
		 * @Override public void run() { updateBondFavoriteData(workingPage); }
		 * } ); }
		 * 
		 * exec.shutdown(); try { exec.awaitTermination(Long.MAX_VALUE,
		 * TimeUnit.NANOSECONDS); } catch (InterruptedException e) {
		 * LOGGER.error("等待任务完成中发生异常 ", e); e.printStackTrace(); }
		 */

        LOGGER.info("cleanBondFavoriteDirtydata end...");

        return "done";
    }

    private void updateBondFavoriteData(BondFavorite bondFavorite) {
        StringBuffer datasql = new StringBuffer();
        datasql.append(
                "SELECT t.favorite_id AS favoriteId,t.user_id AS userId,t.group_id AS groupId,t.bond_uni_code AS bondUniCode,t.create_time AS createTime,t.update_time AS updateTime,t.is_delete AS isDelete,t.bookmark,t.bookmark_update_time AS bookmarkUpdateTime,t.openinterest, t.remark ");
        datasql.append("FROM dmdb.t_bond_favorite t WHERE t.is_delete=0 ");
        datasql.append("AND t.group_id=").append(bondFavorite.getGroupId()).append(" AND t.bond_uni_code=")
                .append(bondFavorite.getBondUniCode()).append(" ");
        datasql.append("ORDER BY t.create_time ASC ");

        List<BondFavorite> bondFavorites = jdbcTemplate.query(datasql.toString(),
                new BeanPropertyRowMapper<>(BondFavorite.class));

        for (int i = 0; i < bondFavorites.size() - 1; i++) {
            BondFavorite updateEntity = bondFavorites.get(i);
            updateEntity.setIsDelete(1);
            updateEntity.setUpdateTime(new Date());
            bondFavoriteRepository.save(updateEntity);
        }
    }

    /**
     * @param gte
     * @param lte
     * @return
     */
    public String updateBondSentimentMsgData(Long gte, Long lte) {
        LOGGER.info("updateBondSentimentMsgData is begin...");

        long begin = System.currentTimeMillis();

        int limit = 5000;
        int count = this.getSentimentBondIdsInNotificationCount(gte, lte);
        int num = count / limit + 1;

        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Integer> pages = new ArrayList<Integer>();

        for (int page = 0; page < num; page++) {
            pages.add(page);
        }

        for (Integer workingPage : pages) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    handleSentimentBondNoteMsg(limit, workingPage, gte, lte);
                }
            });
        }

        exec.shutdown();

        try {
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("等待任务完成中发生异常 ", e);
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        LOGGER.info("updateBondSentimentMsgData end...time cost:" + (end - begin));

        return "done";
    }

    private int getSentimentBondIdsInNotificationCount(Long gte, Long lte) {
        String sql = "SELECT COUNT(1) FROM (SELECT a.bond_id FROM dmdb.t_bond_notification_msg a WHERE a.event_type=5 AND a.news_index=0 AND a.seq_id <="
                + lte + " AND a.seq_id>=" + gte + " GROUP BY a.bond_id) t";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * @param limit
     * @param workingPage
     */
    private void handleSentimentBondNoteMsg(int limit, Integer workingPage, Long gte, Long lte) {
        String sql = "SELECT a.bond_id FROM dmdb.t_bond_notification_msg a WHERE a.event_type=5 AND a.news_index=0 AND a.seq_id <="
                + lte + " AND a.seq_id>=" + gte + " GROUP BY a.bond_id ORDER BY a.bond_id ASC LIMIT "
                + workingPage * limit + "," + limit;
        List<Long> bondIds = jdbcTemplate.queryForList(sql, Long.class);

        bondIds.stream().forEach(bondId -> {
            StringBuffer msqsql = new StringBuffer();
            msqsql.append(
                    " SELECT t.seq_id AS id, t.bond_id AS bondId, t.event_type AS eventType, t.msg_content AS msgContent,t.create_time AS createTime, t.news_index as newsIndex ");
            msqsql.append(" FROM dmdb.t_bond_notification_msg t ");
            msqsql.append(" WHERE t.event_type=5 AND t.news_index=0 AND t.bond_id=").append(bondId);
            msqsql.append(" AND t.seq_id <=").append(lte).append("  AND t.seq_id>=").append(gte);
            msqsql.append(" ORDER BY t.create_time DESC");
            List<BondNotificationMsgDoc> entites = jdbcTemplate.query(msqsql.toString(),
                    new BeanPropertyRowMapper<>(BondNotificationMsgDoc.class));

            entites.stream().forEach(bondNotificationMsg -> {
                BondBasicInfoDoc bondBasicInfo = bondBasicInfoRepository.findOne(bondId);
                Query query = new Query();
                query.addCriteria(Criteria.where("comUniCode").is(SafeUtils.getString(bondBasicInfo.getIssuerId()))
                        .and("title").is(bondNotificationMsg.getMsgContent()));
                query.with(new Sort(new Order(Direction.DESC, "pubDate")));
                query.limit(1);
                BondSentimentDistinctDoc bsdDoc = mongoOperations.findOne(query, BondSentimentDistinctDoc.class);

                if (null != bsdDoc) {
                    String updatesql = "UPDATE t_bond_notification_msg t SET t.news_index=" + bsdDoc.getIndex()
                            + ",t.important=" + SafeUtils.getInt(bsdDoc.getImportant()) + ",t.msg_content='"
                            + bsdDoc.getTitle() + "'" + " WHERE t.event_type=5 AND t.seq_id="
                            + bondNotificationMsg.getId();
                    jdbcTemplate.update(updatesql);
                }
            });
            LOGGER.info("updateBondSentimentMsgData update sum:" + (entites == null ? 0 : entites.size()));

        });
    }

    /**
     * 获取提醒消息的跳转地址
     *
     * @param msg
     * @return
     */
    private String getMsgSkipUrl(BondNotificationMsgDoc msg) {
//        BondSentimentDoc sentiDoc = bondSentimentService.oneSentiments(msg.getNewsIndex(), msg.getImportant());
//        return sentiDoc == null ? null : sentiDoc.getUrl();
        String url = "";
        Map<String, String> params = new HashMap<>();
        params.put("id", msg.getNewsIndex() + "");
        String resJson = HttpClientUtil.doPost(sentimentDetailUrl, params);
        JsonResult bondSentimentTagNewsSimpleResult = gson.fromJson(resJson, new TypeToken<JsonResult>() { }.getType());
        try {
            if (bondSentimentTagNewsSimpleResult != null && bondSentimentTagNewsSimpleResult.getData() != null) {
                LinkedTreeMap<String, String> bondSentimentTagNewsSimpleMap =
                        (LinkedTreeMap<String, String>) bondSentimentTagNewsSimpleResult.getData();
                url = bondSentimentTagNewsSimpleMap.getOrDefault("url", "");
            }
        } catch (Exception ex) {
            LOGGER.error(String.format("getMsgSkipUrl error[%1$s]", ex.getMessage()));
        }
        return url;
    }

    public String removeUselessDefaultGroup(String encryptKey) {
        StopWatch sw = new StopWatch();
        sw.start("remove");
        StringBuilder result = new StringBuilder();
        Date currDate = SafeUtils.getCurrentTime();
        if (StringUtils.isBlank(encryptKey) || !encryptKey.equals("innodealingRemove")) {
            return "密钥不正确";
        }
        List<BondFavoriteGroup> defaultGroupList = groupRepo.findAllByIsDelete(0).stream()
                .filter(grp -> grp.getGroupName().equals("我的持仓")).collect(Collectors.toList());
        List<BondFavoriteGroup> uselessDefaultGroupList = defaultGroupList.stream()
                .filter(grp -> bondFavoriteRepository.getFavoriteCountByGroupId(grp.getGroupId()) == 0)
                .collect(Collectors.toList());
        result.append(String.format("共有[%1$d]个“我的持仓”投组没有添加任何关注债券，可以移除，", uselessDefaultGroupList.size()));
        uselessDefaultGroupList.stream().forEach(grp -> {
            grp.setIsDelete(1);
            grp.setUpdateTime(currDate);
        });
        if (!uselessDefaultGroupList.isEmpty()) {
            groupRepo.save(uselessDefaultGroupList);
        }
        sw.stop();
        result.append(String.format("总共花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    public String reconsitutionTodayMsg(String encryptKey) {
        StopWatch sw = new StopWatch();
        sw.start("msgType");
        StringBuilder result = new StringBuilder();
        if (StringUtils.isBlank(encryptKey) || !encryptKey.equals("innodealingMsg")) {
            return "密钥不正确";
        }
        // 从Mongodb取出最大的msgid
        Long lastMsgId = 0L;
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").gt(0L));
        query.with(new Sort(Direction.DESC, "_id"));
        BondNotificationMsgDoc lastMsgDoc = mongoOperations.findOne(query.limit(1), BondNotificationMsgDoc.class);
        if (null != lastMsgDoc) {
            lastMsgId = lastMsgDoc.getId();
        }
        String sql = "SELECT COUNT(1) FROM t_bond_notification_msg AS msg WHERE seq_id>" + lastMsgId;
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
        int limit = 2000;
        int page = total/limit + 1;
        for (int i = 0; i <= page; i++) {
            int start = i * limit;
            String msgSql = "SELECT t.seq_id AS id,t.bond_id AS bondId, t.create_time AS createTime, t.emotion_tag AS emotionTag, t.event_type AS eventType,t.group_id AS groupId,t.important,t.msg_content AS msgContent,t.news_index AS newsIndex FROM dmdb.t_bond_notification_msg t WHERE t.seq_id>%1$d LIMIT %2$d, %3$d;";
            String formatMsgSql = String.format(msgSql, lastMsgId, start, limit);
            LOGGER.info("total > limit msgSql:"+formatMsgSql+"total:"+total+",start:"+start+",end:"+limit);
	        List<BondNotificationMsg> msgList = jdbcTemplate.query(formatMsgSql, new BeanPropertyRowMapper<>(BondNotificationMsg.class));
	        this.saveMsgDocList(msgList);
        }
        sw.stop();
        result.append(String.format("花费[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    private void saveMsgDocList(List<BondNotificationMsg> msgList) {
        if (!msgList.isEmpty()) {
            List<BondNotificationMsgDoc> msgDocList = new ArrayList<>();
            msgList.stream().forEach(msg -> {
                BondNotificationMsgDoc msgDoc = new BondNotificationMsgDoc();
                BeanUtils.copyProperties(msg, msgDoc);
                if (null == msg.getEmotionTag()) {
                	msgDoc.setEmotionTag(0);
				}
                msgDocList.add(msgDoc);
            });
            if (!msgDocList.isEmpty()) {
                mongoOperations.insertAll(msgDocList);
            }
        }
    }

    /**
     * 重构所有有效投组
     * @param encryptKey
     * @return
     */
    public String reconsitutionAllValidGroup(String encryptKey) {
        StringBuilder result = new StringBuilder();
        Date currDate = SafeUtils.getCurrentTime();
        if (StringUtils.isBlank(encryptKey) || !encryptKey.equals("innodealingRebuild")) {
            return "密钥不正确";
        }
        // 1. 初始化三张表+五个缓存
        result.append(this.reconsitutionInitialize());
        if (validGroupList.isEmpty() || groupFavoriteMapping.isEmpty()) {
            return "投组映射为空";
        }
        // 2.普通提醒条件
        result.append(this.reconsitutionGroupCommonType(currDate));
        // 3.价格指标
        result.append(this.reconsitutionGroupPriceType(currDate));
        // 4.财务指标
        result.append(this.reconsitutionGroupFinaType(currDate));
        validGroupList.clear();
        groupFavoriteMapping.clear();
        return result.toString();
    }

    /**
     * 初始化集合+三张表+五个缓存
     * @return
     */
    private String reconsitutionInitialize() {
        StopWatch sw = new StopWatch();
        sw.start("mainCommon");
        StringBuilder result = new StringBuilder();

        validGroupList.clear();
        groupFavoriteMapping.clear();
        validGroupList = groupRepo.findAllByIsDelete(0);
//        List<CompletableFuture> validGroupFuture = validGroupList.stream().map(grp -> CompletableFuture.runAsync(() ->
//            groupFavoriteMapping.put(grp.getGroupId(), bondFavoriteRepository.findAllByIsDeleteAndGroupIdIn(0, Arrays.asList(grp.getGroupId())))))
//            .collect(Collectors.toList());
//        validGroupFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
        validGroupList.stream().forEach(grp -> CompletableFuture.runAsync(() ->
            groupFavoriteMapping.put(grp.getGroupId(), bondFavoriteRepository.findAllByIsDeleteAndGroupIdIn(0, Arrays.asList(grp.getGroupId())))));
        result.append(String.format("共有[%1$d]个有效的投组。", validGroupList.size()));
        favRadarMappingRepo.deleteByGroupIdNot(0L);
        favFinaIndexRepo.deleteByGroupIdNotOrFavoriteIdNot(0L, 0L);
        favPriceIndexRepo.deleteByGroupIdNotOrFavoriteIdNot(0L, 0L);
        Query query = new Query();
        mongoOperations.remove(query, BondFavMaturityIdxDoc.class);
        mongoOperations.remove(query, BondFavRatingIdxDoc.class);
        mongoOperations.remove(query, BondFavSentimentIdxDoc.class);
        mongoOperations.remove(query, BondFavPriceIdxDoc.class);
        mongoOperations.remove(query, BondFavFinaIdxDoc.class);
        mongoOperations.remove(query, BondFavOtherIdxDoc.class);
        sw.stop();
        result.append(String.format("初始化集合，Mysql表以及Mongodb缓存花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    /**
     * 重构投组普通雷达条件+缓存
     * @param currDate
     * @return
     */
    private String reconsitutionGroupCommonType(Date currDate) {
        StopWatch sw = new StopWatch();
        sw.start("mainCommon");
        StringBuilder result = new StringBuilder();
        // 过滤出有notifiedEnableType的投组
        List<BondFavoriteGroup> commonGroupList = validGroupList.stream()
                .filter(grp -> StringUtils.isNotBlank(grp.getNotifiedEventtype())).collect(Collectors.toList());
        result.append(String.format("共有[%1$d]个有提醒条件的投组。", commonGroupList.size()));
        List<BondFavoriteRadarMapping> groupCommonRadarList = new ArrayList<>();
        List<Object> groupCommonRadarDocList = new ArrayList<>();
        commonGroupList.stream().forEach(grp -> {
            Integer groupId = grp.getGroupId();
            List<Long> newRadarTypeList = new ArrayList<>();
            List<Integer> notifiedTypeList = gson.fromJson(grp.getNotifiedEventtype(), new TypeToken<List<Integer>>() { }.getType());
            notifiedTypeList.stream().forEach(type -> {
                if (commonRadarTypeMap.containsKey(type)) {
                    newRadarTypeList.addAll(commonRadarTypeMap.get(type));
                }
            });
            List<BondFavoriteRadarMapping> defaultCommonRadarList = favRadarMappingRepo.findAllByGroupIdAndRadarIdIn(0L,
                    newRadarTypeList);
            // 单个投组的普通雷达入库
            defaultCommonRadarList.stream().forEach(rdr -> {
                BondFavoriteRadarMapping commonRadar = new BondFavoriteRadarMapping();
                BeanUtils.copyProperties(rdr, commonRadar);
                commonRadar.setId(null);
                commonRadar.setGroupId(SafeUtils.getLong(groupId));
                commonRadar.setCreateTime(currDate);
                groupCommonRadarList.add(commonRadar);
            });
            //favRadarMappingRepo.save(groupCommonRadarList);
            this.batchInsertCommonByJdbcTemplate(groupCommonRadarList);
            groupCommonRadarList.clear();

            // 单个投组的普通雷达缓存,关注债券数量*默认普通雷达数目
            if (groupFavoriteMapping.containsKey(groupId) && groupFavoriteMapping.get(groupId) != null
                    && !groupFavoriteMapping.get(groupId).isEmpty()) {
                // 遍历该投组的关注债券列表
                groupFavoriteMapping.get(groupId).stream().forEach(fav -> {
                    defaultCommonRadarList.stream().forEach(rdr -> {
                        if (radarMongoMap.containsKey(rdr.getRadarId())) {
                            try {
                                String beanName = radarMongoMap.get(rdr.getRadarId());
                                Object commonRadarDoc = context.getBean(beanName);
                                BeanUtils.copyProperties(rdr, commonRadarDoc);
                                commonRadarDoc.getClass().getMethod("setFavoriteId", Long.class).invoke(commonRadarDoc, SafeUtils.getLong(fav.getFavoriteId()));
                                commonRadarDoc.getClass().getMethod("setGroupId", Long.class).invoke(commonRadarDoc, SafeUtils.getLong(groupId));
                                commonRadarDoc.getClass().getMethod("setCreateTime", Date.class).invoke(commonRadarDoc, currDate);
                                commonRadarDoc.getClass().getMethod("setBondUniCode", Long.class).invoke(commonRadarDoc, fav.getBondUniCode());
                                commonRadarDoc.getClass().getMethod("setOpeninterest", Integer.class).invoke(commonRadarDoc, fav.getOpeninterest());
                                commonRadarDoc.getClass().getMethod("setNotifiedEnable", Integer.class).invoke(commonRadarDoc, grp.getNotifiedEnable());
                                commonRadarDoc.getClass().getMethod("setUserId", Integer.class).invoke(commonRadarDoc, grp.getUserId());
                                if (fav.getBondUniCode() != null) {
                                    BondBasicInfoDoc bondBasicInfo = bondBasicInfoRepo.findOne(fav.getBondUniCode());
                                    // 设置发行人
                                    if (bondBasicInfo != null) {
                                        commonRadarDoc.getClass().getMethod("setComUniCode", Long.class).invoke(commonRadarDoc, bondBasicInfo.getIssuerId());
                                    }
                                }
                                groupCommonRadarDocList.add(commonRadarDoc);
                            } catch (Exception ex) {
                                LOGGER.error("my error " + ex.getMessage());
                            }
                        }
                    });
                });
                if (!groupCommonRadarDocList.isEmpty()) {
                    mongoOperations.insertAll(groupCommonRadarDocList);
                    groupCommonRadarDocList.clear();
                }
            }
        });
        sw.stop();
        result.append(String.format("总共花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    /**
     * 重构投组价格雷达条件+缓存
     * @param currDate
     * @return
     */
    private String reconsitutionGroupPriceType(Date currDate) {
        StopWatch sw = new StopWatch();
        sw.start("mainPrice");
        StringBuilder result = new StringBuilder();
        List<BondFavoritePriceIndex> defaultPriceIndexList = favPriceIndexRepo.findAllByGroupIdAndFavoriteId(0L, 0L);
        List<BondFavoritePriceIndex> groupPriceIndexList = new ArrayList<>();
        List<BondFavoritePriceIndex> favPriceIndexList = new ArrayList<>();
        List<BondFavPriceIdxDoc> favPriceIndexDocList = new ArrayList<>();
        // 生成投组全局默认条件, 只用于前端展示
        validGroupList.stream().forEach(grp -> {
            Long groupIdL = SafeUtils.getLong(grp.getGroupId());
            defaultPriceIndexList.stream().forEach(priceIdx -> {
                BondFavoritePriceIndex newPriceIdx = new BondFavoritePriceIndex();
                BeanUtils.copyProperties(priceIdx, newPriceIdx);
                newPriceIdx.setId(null);
                newPriceIdx.setGroupId(groupIdL);
                newPriceIdx.setFavoriteId(0L);
                newPriceIdx.setStatus(1);
                newPriceIdx.setCreateTime(currDate);
                groupPriceIndexList.add(newPriceIdx);
            });
            favPriceIndexRepo.save(groupPriceIndexList);
            groupPriceIndexList.clear();
        });

        // 有效投组，生成入库+缓存
        List<BondFavoriteGroup> priceGroupList = validGroupList.stream()
                .filter(grp -> grp.getNotifiedEventtype() != null && grp.getNotifiedEventtype().indexOf("7") != -1)
                .collect(Collectors.toList());
        priceGroupList.stream().forEach(grp -> {
            favPriceIndexList.clear();
            favPriceIndexDocList.clear();
            Integer groupId = grp.getGroupId();
            Long groupIdL = SafeUtils.getLong(groupId);
            Integer notifiedEnable = grp.getNotifiedEnable();
            if (groupFavoriteMapping.containsKey(groupId) && groupFavoriteMapping.get(groupId) != null
                && !groupFavoriteMapping.get(groupId).isEmpty()) {
                groupFavoriteMapping.get(groupId).stream().forEach(fav -> {
                    defaultPriceIndexList.stream().forEach(rdr -> {
                        // 入库
                        BondFavoritePriceIndex newPriceIndex = new BondFavoritePriceIndex();
                        BeanUtils.copyProperties(rdr, newPriceIndex);
                        newPriceIndex.setId(null);
                        newPriceIndex.setGroupId(groupIdL);
                        newPriceIndex.setFavoriteId(SafeUtils.getLong(fav.getFavoriteId()));
                        newPriceIndex.setStatus(1);
                        newPriceIndex.setCreateTime(currDate);
                        favPriceIndexList.add(newPriceIndex);
                        // 缓存
                        BondFavPriceIdxDoc newPriceIdxDoc = new BondFavPriceIdxDoc();
                        BeanUtils.copyProperties(rdr, newPriceIdxDoc);
                        BeanUtils.copyProperties(fav, newPriceIdxDoc);
                        newPriceIdxDoc.setGroupId(groupIdL);
                        newPriceIdxDoc.setFavoriteId(SafeUtils.getLong(fav.getFavoriteId()));
                        newPriceIdxDoc.setNotifiedEnable(notifiedEnable);
                        newPriceIdxDoc.setUserId(grp.getUserId());
                        favPriceIndexDocList.add(newPriceIdxDoc);
                    });
                });
                List<BondFavoritePriceIndex> savedPriceIndexList = favPriceIndexRepo.save(favPriceIndexList);
                if (!savedPriceIndexList.isEmpty() && !favPriceIndexDocList.isEmpty()) {
                    mongoOperations.insertAll(favPriceIndexDocList);
                }
                favPriceIndexList.clear();
                favPriceIndexDocList.clear();
            }
        });
        sw.stop();
        result.append(String.format("总共花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    /**
     * 重构投组财务指标雷达条件+缓存
     * @param currDate
     * @return
     */
    private String reconsitutionGroupFinaType(Date currDate) {
        StopWatch sw = new StopWatch();
        sw.start("mainFina");
        StringBuilder result = new StringBuilder();
        List<BondFavoriteFinaIndex> defaultFinaIndexList = favFinaIndexRepo.findAllByGroupIdAndFavoriteId(0L, 0L);
        List<BondFavoriteFinaIndex> groupFinaIndexList = new ArrayList<>();
        List<BondFavoriteFinaIndex> favFinaIndexList = new ArrayList<>();
        List<BondFavFinaIdxDoc> favFinaIndexDocList = new ArrayList<>();
        // 生成投组全局默认条件, 只用于前端展示
        validGroupList.stream().forEach(grp -> {
            Long groupIdL = SafeUtils.getLong(grp.getGroupId());
            defaultFinaIndexList.stream().forEach(finaIdx -> {
                BondFavoriteFinaIndex newFinaIdx = new BondFavoriteFinaIndex();
                BeanUtils.copyProperties(finaIdx, newFinaIdx);
                newFinaIdx.setId(null);
                newFinaIdx.setGroupId(groupIdL);
                newFinaIdx.setFavoriteId(0L);
                newFinaIdx.setStatus(1);
                newFinaIdx.setCreateTime(currDate);
                groupFinaIndexList.add(newFinaIdx);
            });
            favFinaIndexRepo.save(groupFinaIndexList);
            groupFinaIndexList.clear();
        });

        // 有效投组，生成入库+缓存
        List<BondFavoriteGroup> finaGroupList = validGroupList.stream()
                .filter(grp -> grp.getNotifiedEventtype() != null && grp.getNotifiedEventtype().indexOf("3") != -1)
                .collect(Collectors.toList());
        finaGroupList.stream().forEach(grp -> {
            favFinaIndexList.clear();
            favFinaIndexDocList.clear();
            Integer groupId = grp.getGroupId();
            Long groupIdL = SafeUtils.getLong(groupId);
            Integer notifiedEnable = grp.getNotifiedEnable();
            if (groupFavoriteMapping.containsKey(groupId) && groupFavoriteMapping.get(groupId) != null
                    && !groupFavoriteMapping.get(groupId).isEmpty()) {
                groupFavoriteMapping.get(groupId).stream().forEach(fav -> {
                    defaultFinaIndexList.stream().forEach(rdr -> {
                        // 入库
                        BondFavoriteFinaIndex newFinaIndex = new BondFavoriteFinaIndex();
                        BeanUtils.copyProperties(rdr, newFinaIndex);
                        newFinaIndex.setId(null);
                        newFinaIndex.setGroupId(groupIdL);
                        newFinaIndex.setFavoriteId(SafeUtils.getLong(fav.getFavoriteId()));
                        newFinaIndex.setStatus(1);
                        newFinaIndex.setCreateTime(currDate);
                        favFinaIndexList.add(newFinaIndex);
                        // 缓存
                        BondFavFinaIdxDoc newFinaIdxDoc = new BondFavFinaIdxDoc();
                        BeanUtils.copyProperties(rdr, newFinaIdxDoc);
                        BeanUtils.copyProperties(fav, newFinaIdxDoc);
                        newFinaIdxDoc.setGroupId(groupIdL);
                        newFinaIdxDoc.setFavoriteId(SafeUtils.getLong(fav.getFavoriteId()));
                        newFinaIdxDoc.setNotifiedEnable(notifiedEnable);
                        newFinaIdxDoc.setUserId(grp.getUserId());
                        favFinaIndexDocList.add(newFinaIdxDoc);
                    });
                });
                List<BondFavoriteFinaIndex> savedFinaIndexList = favFinaIndexRepo.save(favFinaIndexList);
                if (!savedFinaIndexList.isEmpty() && !favFinaIndexDocList.isEmpty()) {
                    mongoOperations.insertAll(favFinaIndexDocList);
                }
                favFinaIndexList.clear();
                favFinaIndexDocList.clear();
            }
        });
        sw.stop();
        result.append(String.format("总共花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    private static final Map<Integer, List<Long>> commonRadarTypeMap = new HashMap<>();
    private static final Map<Long, String> radarMongoMap = new HashMap<>();

    private static Map<Integer, List<BondFavorite>> groupFavoriteMapping = new HashMap<>();
    private static List<BondFavoriteGroup> validGroupList = new ArrayList<>();

    static {
        commonRadarTypeMap.put(1, Arrays.asList(7L, 8L, 9L));
        commonRadarTypeMap.put(2, Arrays.asList(10L, 11L));
        commonRadarTypeMap.put(4, Arrays.asList(12L));
        //commonRadarTypeMap.put(5, Arrays.asList(14L, 15L, 16L, 17L));
        commonRadarTypeMap.put(5, Arrays.asList(16L));
        commonRadarTypeMap.put(6, Arrays.asList(13L));

        radarMongoMap.put(7L, "BondFavMaturityIdxDoc");
        radarMongoMap.put(8L, "BondFavMaturityIdxDoc");
        radarMongoMap.put(9L, "BondFavMaturityIdxDoc");
        radarMongoMap.put(10L, "BondFavRatingIdxDoc");
        radarMongoMap.put(11L, "BondFavRatingIdxDoc");
        radarMongoMap.put(12L, "BondFavRatingIdxDoc");
        radarMongoMap.put(13L, "BondFavRatingIdxDoc");
        radarMongoMap.put(14L, "BondFavSentimentIdxDoc");
        radarMongoMap.put(15L, "BondFavSentimentIdxDoc");
        radarMongoMap.put(16L, "BondFavSentimentIdxDoc");
        radarMongoMap.put(17L, "BondFavSentimentIdxDoc");
        radarMongoMap.put(18L, "BondFavOtherIdxDoc");
        radarMongoMap.put(19L, "BondFavOtherIdxDoc");
    }

    @Transactional
    protected String batchInsertCommonByJdbcTemplate(List<BondFavoriteRadarMapping> radarList) {
        final List<BondFavoriteRadarMapping> tempList = radarList;
        String sql = "INSERT INTO t_bond_favorite_radar_mapping(group_id, radar_id, threshold, create_time) VALUES(?,?,?,?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, tempList.get(i).getGroupId());
                ps.setLong(2, tempList.get(i).getRadarId());
                ps.setInt(3, tempList.get(i).getThreshold() == null ? 0 : tempList.get(i).getThreshold());
                ps.setObject(4, new java.sql.Timestamp(tempList.get(i).getCreateTime().getTime()));
            }
            @Override
            public int getBatchSize() {
                return tempList.size();
            }
        });
        return "Task Done";
    }

    @Transactional
    protected String batchInsertPriceByJdbcTemplate(List<BondFavoritePriceIndex> radarList) {
        final List<BondFavoritePriceIndex> tempList = radarList;
        String sql = "INSERT INTO t_bond_favorite_radar_mapping(group_id, favorite_id, price_index, price_type," +
                " price_condi, index_value, index_unit, status, create_time) VALUES(?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, tempList.get(i).getGroupId());
                ps.setLong(2, tempList.get(i).getFavoriteId());
                ps.setInt(3, tempList.get(i).getPriceIndex());
                ps.setInt(4, tempList.get(i).getPriceType());
                ps.setInt(5, tempList.get(i).getPriceCondi());
                ps.setBigDecimal(6, tempList.get(i).getIndexValue());
                ps.setInt(7, tempList.get(i).getIndexUnit());
                ps.setInt(8, 1);
                ps.setObject(9, new java.sql.Timestamp(tempList.get(i).getCreateTime().getTime()));
            }
            @Override
            public int getBatchSize() {
                return tempList.size();
            }
        });
        return "Task Done";
    }

    @Transactional
    protected String batchInsertFinaByJdbcTemplate(List<BondFavoriteFinaIndex> radarList) {
        final List<BondFavoriteFinaIndex> tempList = radarList;
        String sql = "INSERT INTO t_bond_favorite_radar_mapping(group_id, favorite_id, index_type, index_code_expr," +
                " index_name, fina_sub_type, index_value_type, index_value_nexus, index_value_low, index_value_high," +
                " status, create_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, tempList.get(i).getGroupId());
                ps.setLong(2, tempList.get(i).getFavoriteId());
                ps.setInt(3, tempList.get(i).getIndexType());
                ps.setString(4, tempList.get(i).getIndexCodeExpr());
                ps.setString(5, tempList.get(i).getIndexName());
                ps.setInt(6, tempList.get(i).getFinaSubType() == null ? 0 : tempList.get(i).getFinaSubType());
                ps.setInt(7, tempList.get(i).getIndexValueType());
                ps.setInt(8, tempList.get(i).getIndexValueNexus());
                ps.setInt(9, tempList.get(i).getIndexValueUnit());
                ps.setBigDecimal(10, tempList.get(i).getIndexValueLow());
                ps.setBigDecimal(11, tempList.get(i).getIndexValueHigh());
                ps.setInt(12, 1);
                ps.setObject(13, new java.sql.Timestamp(tempList.get(i).getCreateTime().getTime()));
            }
            @Override
            public int getBatchSize() {
                return tempList.size();
            }
        });
        return "Task Done";
    }

    public String updateRadar27FromMapping(String encryptKey) {
        if (StringUtils.isBlank(encryptKey) || !encryptKey.equals("innodealing27")) {
            return "密钥不正确";
        }
        StopWatch sw = new StopWatch();
        sw.start("innodealing27");
        StringBuilder result = new StringBuilder();
        Date currDate = SafeUtils.getCurrentTime();
        this.handleRadar9Related(currDate);
        this.handleRadar18Related(currDate);
        sw.stop();
        result.append(String.format("总共花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    private void handleRadar18Related(Date currDate) {
        // 所有有效投组生成18，入库，加入Mongo缓存
        List<BondFavoriteRadarMapping> newGroupMappingList = new ArrayList<>();
        List<Long> groupIdLList = radarMappingDAO.getAllDistinctGroupIdList();
        groupIdLList.stream().forEach(gId -> {
            BondFavoriteRadarMapping radarMapping = new BondFavoriteRadarMapping();
            radarMapping.setGroupId(gId);
            radarMapping.setRadarId(18L);
            radarMapping.setThreshold(0);
            radarMapping.setCreateTime(currDate);
            newGroupMappingList.add(radarMapping);
        });
        this.batchInsertCommonByJdbcTemplate(newGroupMappingList);

        List<BondFavoriteRadarMapping> radar19List = favRadarMappingRepo.findAllByRadarId(19L);
        // 缓存
        List<Integer> groupIdList = groupIdLList.stream().map(gId -> SafeUtils.getInteger(gId)).collect(Collectors.toList());
        List<BondFavorite> allFavList = bondFavoriteRepository.findAllByIsDeleteAndGroupIdIn(0, groupIdList);
        // groupId<->radar缓存
        Map<Integer, List<BondFavoriteRadarMapping>> groupIdRadarMap = newGroupMappingList.stream()
                .collect(Collectors.groupingBy(rdr -> SafeUtils.getInteger(rdr.getGroupId())));
        // favId<->issuerId缓存
        List<BondBasicInfoDoc> basicDocList = bondBasicInfoRepo.findAllByBondUniCodeIn(allFavList.stream()
                .map(BondFavorite::getBondUniCode).collect(Collectors.toList()));

        Map<Integer, Long> favIdIssuerIdMap = new HashMap<>();
        allFavList.stream().forEach(fav -> {
            try {
                Long issuerId = basicDocList.stream().filter(doc -> doc.getBondUniCode() != null && doc.getBondUniCode().equals(fav.getBondUniCode())).findFirst()
                        .orElse(new BondBasicInfoDoc() { {setIssuerId(0L);}}).getIssuerId();
                favIdIssuerIdMap.put(fav.getFavoriteId(), issuerId);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        });
        // groupId<->notifiedEnable缓存
        List<BondFavoriteGroup> groupList = groupRepo.findAllByGroupIdIn(groupIdList);
        Map<Long, Integer> groupIdNotiMap = groupList.stream().collect(
                Collectors.toMap(grp -> SafeUtils.getLong(grp.getGroupId()), BondFavoriteGroup::getNotifiedEnable));

        List<BondFavOtherIdxDoc> otherIdxDocList = new ArrayList<>();
        allFavList.stream().forEach(fav -> {
            Long favIdL = SafeUtils.getLong(fav.getFavoriteId());
            if (groupIdRadarMap.containsKey(fav.getGroupId())) {
                groupIdRadarMap.get(fav.getGroupId()).stream().forEach(radar -> {
                    try {
                        BondFavOtherIdxDoc otherIdxDoc = new BondFavOtherIdxDoc();
                        BeanUtils.copyProperties(radar, otherIdxDoc);
                        otherIdxDoc.setFavoriteId(favIdL);
                        otherIdxDoc.setCreateTime(currDate);
                        otherIdxDoc.setNotifiedEnable(groupIdNotiMap.get(fav.getGroupId()));
                        otherIdxDoc.setUserId(fav.getUserId());
                        otherIdxDoc.setBondUniCode(fav.getBondUniCode());
                        otherIdxDoc.setOpeninterest(fav.getOpeninterest());
                        if (favIdIssuerIdMap != null && favIdIssuerIdMap.containsKey(fav.getFavoriteId())) {
                            otherIdxDoc.setComUniCode(favIdIssuerIdMap.get(fav.getFavoriteId()));
                        }
                        otherIdxDocList.add(otherIdxDoc);
                    } catch (Exception ex) {
                        LOGGER.error("handleRadar18Related: error is " + ex.getMessage());
                    }
                });
            }
        });
        if (!otherIdxDocList.isEmpty()) {
            mongoOperations.insertAll(otherIdxDocList);
        }
    }

    private void handleRadar9Related(Date currDate) {
        // 选出9的groupId，生成21，22，23加入radarMapping入库，加入Mongo缓存
        List<BondFavoriteRadarMapping> newGroupMappingList = new ArrayList<>();
        List<Long> groupIdLList = radarMappingDAO.getDistinctGroupIdListByRadarId(9L);
        List<Long> newRadarIdList = Arrays.asList(21L, 22L, 23L);
        groupIdLList.stream().forEach(gId -> {
            newRadarIdList.stream().forEach(rId -> {
                BondFavoriteRadarMapping radarMapping = new BondFavoriteRadarMapping();
                radarMapping.setGroupId(gId);
                radarMapping.setRadarId(rId);
                radarMapping.setThreshold(1);
                radarMapping.setCreateTime(currDate);
                newGroupMappingList.add(radarMapping);
            });
        });
        this.batchInsertCommonByJdbcTemplate(newGroupMappingList);
        favRadarMappingRepo.deleteByRadarId(9L);
        // 缓存
        List<BondFavMaturityIdxDoc> maturityIdxDocList = new ArrayList<>();
        List<Integer> groupIdList = groupIdLList.stream().map(gId -> SafeUtils.getInteger(gId)).collect(Collectors.toList());
        List<BondFavorite> allFavList = bondFavoriteRepository.findAllByIsDeleteAndGroupIdIn(0, groupIdList);
        // groupId<->radar缓存
        Map<Integer, List<BondFavoriteRadarMapping>> groupIdRadarMap = newGroupMappingList.stream()
                .collect(Collectors.groupingBy(rdr -> SafeUtils.getInteger(rdr.getGroupId())));
        // favId<->issuerId缓存
        List<BondBasicInfoDoc> basicDocList = bondBasicInfoRepo.findAllByBondUniCodeIn(allFavList.stream()
                .map(BondFavorite::getBondUniCode).collect(Collectors.toList()));
        Map<Integer, Long> favIdIssuerIdMap = new HashMap<>();
        allFavList.stream().forEach(fav -> {
            try {
                Long issuerId = basicDocList.stream().filter(doc -> doc.getBondUniCode() != null && doc.getBondUniCode().equals(fav.getBondUniCode())).findFirst()
                        .orElse(new BondBasicInfoDoc() { {setIssuerId(0L);}}).getIssuerId();
                favIdIssuerIdMap.put(fav.getFavoriteId(), issuerId);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        });
        // groupId<->notifiedEnable缓存
        List<BondFavoriteGroup> groupList = groupRepo.findAllByGroupIdIn(groupIdList);
        Map<Integer, Integer> groupIdNotiMap = groupList.stream().collect(
                Collectors.toMap(grp -> grp.getGroupId(), BondFavoriteGroup::getNotifiedEnable));
        allFavList.stream().forEach(fav -> {
            Long favIdL = SafeUtils.getLong(fav.getFavoriteId());
            if (groupIdRadarMap.containsKey(fav.getGroupId())) {
                groupIdRadarMap.get(fav.getGroupId()).stream().forEach(radar -> {
                    try {
                        BondFavMaturityIdxDoc maturityIdxDoc = new BondFavMaturityIdxDoc();
                        BeanUtils.copyProperties(radar, maturityIdxDoc);
                        maturityIdxDoc.setFavoriteId(favIdL);
                        maturityIdxDoc.setCreateTime(currDate);
                        maturityIdxDoc.setNotifiedEnable(groupIdNotiMap.get(fav.getGroupId()));
                        maturityIdxDoc.setUserId(fav.getUserId());
                        maturityIdxDoc.setBondUniCode(fav.getBondUniCode());
                        maturityIdxDoc.setOpeninterest(fav.getOpeninterest());
                        if (favIdIssuerIdMap != null && favIdIssuerIdMap.containsKey(fav.getFavoriteId())) {
                            maturityIdxDoc.setComUniCode(favIdIssuerIdMap.get(fav.getFavoriteId()));
                        }
                        maturityIdxDocList.add(maturityIdxDoc);
                    } catch (Exception ex) {
                        LOGGER.error("handleRadar9Related: error is " + ex.getMessage());
                    }
                });
            }
        });
        if (!maturityIdxDocList.isEmpty()) {
            mongoOperations.insertAll(maturityIdxDocList);
        }
        mongoOperations.remove(new Query(Criteria.where("radarId").is(9L)), BondFavMaturityIdxDoc.class);
    }

    public String updateRadar19FromMapping(String key) {
        Date currDate = SafeUtils.getCurrentTime();
        // 所有有效投组生成19，入库，加入Mongo缓存
        List<BondFavoriteRadarMapping> radarMapping19List = favRadarMappingRepo.findAllByRadarId(19L);
        List<Integer> groupIdList = radarMapping19List.stream().filter(rdr -> rdr.getGroupId() > 0L)
                .map(rdr -> SafeUtils.getInteger(rdr.getGroupId())).collect(Collectors.toList());
        // 缓存
        List<BondFavorite> allFavList = bondFavoriteRepository.findAllByIsDeleteAndGroupIdIn(0, groupIdList);
        // groupId<->radar缓存
        Map<Integer, List<BondFavoriteRadarMapping>> groupIdRadarMap = radarMapping19List.stream()
                .collect(Collectors.groupingBy(rdr -> SafeUtils.getInteger(rdr.getGroupId())));
        // favId<->issuerId缓存
        List<BondBasicInfoDoc> basicDocList = bondBasicInfoRepo.findAllByBondUniCodeIn(allFavList.stream()
                .map(BondFavorite::getBondUniCode).collect(Collectors.toList()));

        Map<Integer, Long> favIdIssuerIdMap = new HashMap<>();
        allFavList.stream().forEach(fav -> {
            try {
                Long issuerId = basicDocList.stream().filter(doc -> doc.getBondUniCode() != null && doc.getBondUniCode().equals(fav.getBondUniCode())).findFirst()
                        .orElse(new BondBasicInfoDoc() { {setIssuerId(0L);}}).getIssuerId();
                favIdIssuerIdMap.put(fav.getFavoriteId(), issuerId);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        });
        // groupId<->notifiedEnable缓存
        List<BondFavoriteGroup> groupList = groupRepo.findAllByGroupIdIn(groupIdList);
        Map<Long, Integer> groupIdNotiMap = groupList.stream().collect(
                Collectors.toMap(grp -> SafeUtils.getLong(grp.getGroupId()), BondFavoriteGroup::getNotifiedEnable));

        List<BondFavOtherIdxDoc> otherIdxDocList = new ArrayList<>();
        allFavList.stream().forEach(fav -> {
            Long favIdL = SafeUtils.getLong(fav.getFavoriteId());
            if (groupIdRadarMap.containsKey(fav.getGroupId())) {
                groupIdRadarMap.get(fav.getGroupId()).stream().forEach(radar -> {
                    try {
                        BondFavOtherIdxDoc otherIdxDoc = new BondFavOtherIdxDoc();
                        BeanUtils.copyProperties(radar, otherIdxDoc);
                        otherIdxDoc.setFavoriteId(favIdL);
                        otherIdxDoc.setCreateTime(currDate);
                        otherIdxDoc.setNotifiedEnable(groupIdNotiMap.get(SafeUtils.getLong(fav.getGroupId())));
                        otherIdxDoc.setUserId(fav.getUserId());
                        otherIdxDoc.setBondUniCode(fav.getBondUniCode());
                        otherIdxDoc.setOpeninterest(fav.getOpeninterest());
                        if (favIdIssuerIdMap != null && favIdIssuerIdMap.containsKey(fav.getFavoriteId())) {
                            otherIdxDoc.setComUniCode(favIdIssuerIdMap.get(fav.getFavoriteId()));
                        }
                        otherIdxDocList.add(otherIdxDoc);
                    } catch (Exception ex) {
                        LOGGER.error("handleRadar19Related: error is " + ex.getMessage());
                    }
                });
            }
        });
        if (!otherIdxDocList.isEmpty()) {
            mongoOperations.insertAll(otherIdxDocList);
        }
        return "ok";
    }


    /**
     * 14+17
     * @param encryptKey
     */
    public String updateRadar31FromMapping(String encryptKey) {
        if (StringUtils.isBlank(encryptKey) || !encryptKey.equals("innodealing31")) {
            return "密钥不正确";
        }
        StopWatch sw = new StopWatch();
        sw.start("innodealing31");
        StringBuilder result = new StringBuilder();
        Date currDate = SafeUtils.getCurrentTime();

        // 删除已存在的14和17
        List<Long> newRadarIdList = Arrays.asList(14L, 17L);
        favRadarMappingRepo.deleteByRadarIdInAndGroupIdNot(newRadarIdList, 0L);
        mongoOperations.remove(new Query(Criteria.where("radarId").in(newRadarIdList)), BondFavSentimentIdxDoc.class);
        LOGGER.info(String.format("Removed 14 and 17 from db."));

        // 选出包含16的groupId，生成14和17入库radarMapping，再加入Mongo缓存
        List<BondFavoriteRadarMapping> newGroupMappingList = new ArrayList<>();
        List<Long> groupIdLList = radarMappingDAO.getDistinctGroupIdListByRadarId(16L);
        groupIdLList.stream().forEach(gId -> {
            newRadarIdList.stream().forEach(rId -> {
                BondFavoriteRadarMapping radarMapping = new BondFavoriteRadarMapping();
                radarMapping.setGroupId(gId);
                radarMapping.setRadarId(rId);
                radarMapping.setCreateTime(currDate);
                newGroupMappingList.add(radarMapping);
             });
        });
        this.batchInsertCommonByJdbcTemplate(newGroupMappingList);
        LOGGER.info(String.format("Saved %1$d new group radar mappings", newGroupMappingList.size()));

        // Mongodb缓存
        List<BondFavSentimentIdxDoc> sentimentIdxDocList = new ArrayList<>();
        List<Integer> groupIdList = groupIdLList.stream().map(gId -> SafeUtils.getInteger(gId)).collect(Collectors.toList());
        List<BondFavorite> allFavList = bondFavoriteRepository.findAllByIsDeleteAndGroupIdIn(0, groupIdList);
        // groupId<->radar缓存
        Map<Integer, List<BondFavoriteRadarMapping>> groupIdRadarMap = newGroupMappingList.stream()
             .collect(Collectors.groupingBy(rdr -> SafeUtils.getInteger(rdr.getGroupId())));
        // favId<->issuerId缓存
        List<BondBasicInfoDoc> basicDocList = bondBasicInfoRepo.findAllByBondUniCodeIn(allFavList.stream()
             .map(BondFavorite::getBondUniCode).collect(Collectors.toList()));
        Map<Integer, Long> favIdIssuerIdMap = new HashMap<>();
        allFavList.stream().forEach(fav -> {
            // 如果不存在发行人，置为0
            if (basicDocList.stream().anyMatch(doc -> doc.getBondUniCode() != null && doc.getBondUniCode().equals(fav.getBondUniCode()))) {
                Long issuerId = basicDocList.stream().filter(doc -> doc.getBondUniCode() != null && doc.getBondUniCode().equals(fav.getBondUniCode())).findFirst().get().getIssuerId();
                favIdIssuerIdMap.put(fav.getFavoriteId(), issuerId == null ? 0L : issuerId);
            } else {
                favIdIssuerIdMap.put(fav.getFavoriteId(), 0L);
            }
        });
        // groupId<->notifiedEnable缓存
        List<BondFavoriteGroup> groupList = groupRepo.findAllByGroupIdIn(groupIdList);
        Map<Integer, Integer> groupIdNotiMap = groupList.stream()
                .collect(Collectors.toMap(grp -> grp.getGroupId(), BondFavoriteGroup::getNotifiedEnable));
        allFavList.stream().forEach(fav -> {
            Long favIdL = SafeUtils.getLong(fav.getFavoriteId());
            if (groupIdRadarMap.containsKey(fav.getGroupId())) {
                groupIdRadarMap.get(fav.getGroupId()).stream().forEach(radar -> {
                    try {
                        BondFavSentimentIdxDoc sentimentIdxDoc = new BondFavSentimentIdxDoc();
                        BeanUtils.copyProperties(radar, sentimentIdxDoc);
                        sentimentIdxDoc.setFavoriteId(favIdL);
                        sentimentIdxDoc.setCreateTime(currDate);
                        sentimentIdxDoc.setNotifiedEnable(groupIdNotiMap.get(fav.getGroupId()));
                        sentimentIdxDoc.setUserId(fav.getUserId());
                        sentimentIdxDoc.setBondUniCode(fav.getBondUniCode());
                        sentimentIdxDoc.setOpeninterest(fav.getOpeninterest());
                        if (favIdIssuerIdMap != null && favIdIssuerIdMap.containsKey(fav.getFavoriteId())) {
                            sentimentIdxDoc.setComUniCode(favIdIssuerIdMap.get(fav.getFavoriteId()));
                        }
                        sentimentIdxDocList.add(sentimentIdxDoc);
                    } catch (Exception ex) {
                        LOGGER.error("handleRadar31Related: error is " + ex.getMessage());
                    }
                });
            }
        });
        if (!sentimentIdxDocList.isEmpty()) {
//            mongoOperations.insertAll(sentimentIdxDocList);
            // 分批存储
            int totalSize = sentimentIdxDocList.size(); //总记录数
            int batchSize = 10000; //每页N条
            int totalBatch = totalSize / batchSize; //共N页
            if (totalSize % batchSize != 0) {
                totalBatch += 1;
                if (totalSize < batchSize) {
                    batchSize = sentimentIdxDocList.size();
                }
            }
            for (int batchIdx = 1; batchIdx < totalBatch + 1; batchIdx++) {
                int startIdx = (batchIdx - 1) * batchSize;
                int endIdx = batchIdx * batchSize > totalSize ? totalSize : batchIdx * batchSize;
                mongoOperations.insertAll(sentimentIdxDocList.subList(startIdx, endIdx));
                LOGGER.info(String.format("Saved [%1$d] batch with [%2$d] sentimentIdxDoc items", batchIdx,
                        sentimentIdxDocList.subList(startIdx, endIdx).size()));
            }

            LOGGER.info(String.format("Finally saved %1$d sentimentIdxDoc items", sentimentIdxDocList.size()));
        }

        sw.stop();
        result.append(String.format("总共花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    public String deleteDuplicateFavorite(String encryptKey) {
        if (StringUtils.isBlank(encryptKey) || !encryptKey.equals("innodealingDDF")) {
            return "密钥不正确";
        }
        StopWatch sw = new StopWatch();
        sw.start("innodealingDDF");
        StringBuilder result = new StringBuilder();
        String currDateStr = SafeUtils.getCurrDateFmtStr(SafeUtils.DATE_TIME_FORMAT1);
        List<BondFavorite> targetFavList = favoriteDAO.getDuplicatedFavList(BondFavorite.class);
        if (!targetFavList.isEmpty()) {
            result.append(String.format("Found [%1$d] items,", targetFavList.size()));
            LOGGER.info(String.format("Found [%1$d] items", targetFavList.size()));
            Map<Integer, List<BondFavorite>> group2FavListMap = targetFavList.stream()
                    .collect(Collectors.groupingBy(BondFavorite::getGroupId));
            result.append(String.format(" with [%1$d] groups.", group2FavListMap.keySet().size()));
            LOGGER.info(String.format("With [%1$d] groups", group2FavListMap.keySet().size()));
            group2FavListMap.keySet().stream().forEach(groupId -> {
                List<Integer> toBeDeletedFavIdList = new ArrayList<>();
                List<BondFavorite> groupDuplicatedFavList = group2FavListMap.get(groupId);
                Map<Long, List<BondFavorite>> code2FavListMap = groupDuplicatedFavList.stream()
                        .collect(Collectors.groupingBy(BondFavorite::getBondUniCode));
                // 按照code筛选待删除关注债券
                code2FavListMap.keySet().stream().forEach(code -> {
                    if (code.equals(0L)) {
                        // 非法code，全部删除
                        List<Integer> favIdList = code2FavListMap.get(code).stream().map(BondFavorite::getFavoriteId)
                                .collect(Collectors.toList());
                        toBeDeletedFavIdList.addAll(favIdList);
                    } else {
                        // 重复code，只保留最后一个
                        List<Integer> favIdList = code2FavListMap.get(code).stream()
                                .sorted(Comparator.comparing(BondFavorite::getCreateTime)).map(BondFavorite::getFavoriteId)
                                .collect(Collectors.toList());
                        toBeDeletedFavIdList.addAll(favIdList.subList(0, favIdList.size() - 1));
                    }
                });
                // 处理该投组的待删除关注债券
                priceIndexDAO.batchDeleteByFavIdList(toBeDeletedFavIdList);
                finaIndexDAO.batchDeleteByFavIdList(toBeDeletedFavIdList);
                Query query = new Query(Criteria.where("favoriteId").in(toBeDeletedFavIdList));
                mongoOperations.remove(query, BondFavMaturityIdxDoc.class);
                mongoOperations.remove(query, BondFavRatingIdxDoc.class);
                mongoOperations.remove(query, BondFavSentimentIdxDoc.class);
                mongoOperations.remove(query, BondFavOtherIdxDoc.class);
                mongoOperations.remove(query, BondFavPriceIdxDoc.class);
                mongoOperations.remove(query, BondFavFinaIdxDoc.class);
                favoriteDAO.batchLogicDelete(toBeDeletedFavIdList, currDateStr);

                result.append(String.format(" Removed [%1$d] favorites with groupId[%2$d] from mysql and mongodb.",
                        toBeDeletedFavIdList.size(), groupId));
                LOGGER.info(String.format("removed [%1$d] favorites with groupId[%2$d] from mysql and mongodb",
                        toBeDeletedFavIdList.size(), groupId));
            });
        }
        sw.stop();
        result.append(String.format("总共花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }


    // =======================================================================================
    // ===================================投组邮件=============================================
    // =======================================================================================

    /**
     *
     * @param encryptKey
     * @param userId 用户Id[如果不为0，只筛选该用户的投组]，为0则查询所有符合条件的投组
     * @param inputStartDateStr 消息开始时间[yyyy-MM-dd HH:mm:ss]，为空则前一天的08:30:00
     * @param inputEndDateStr 消息结束时间[yyyy-MM-dd HH:mm:ss]，为空则当天的08:30:00
     * @param mailTo 邮件发送对象[如果有值，所有邮件发送到该地址]，为空则发送给投组对应的用户
     * @return
     */
    public String sendCustomGroupEmails(String encryptKey, Integer userId, String inputStartDateStr, String inputEndDateStr, String mailTo) {
        if (!PortfolioConstants.PORTFOLIO_EMAIL_PWD_POOL.contains(encryptKey)) {
            return "密钥不正确";
        }
        if (encryptKey.equals(PortfolioConstants.PORTFOLIO_EMAIL_CRONTAB_PWD)) {
            // 清理缓存, 定时任务专用
            redisUtil.removePattern("groupEmail_*");
        }
        StopWatch sw = new StopWatch();
        sw.start("emailUser");
        List<BondFavoriteGroupEmailVO> groupEmailVOList;
        List<Integer> successGroupIdList = new ArrayList<>();
        List<Integer> failedGroupIdList = new ArrayList<>();
        List<Integer> cachedGroupIdList = new ArrayList<>();
        try {
            String startDateStr = StringUtils.isBlank(inputStartDateStr)
                    ? String.format("%1$s 08:30:00", SafeUtils.getDateFromNow(-1)) : inputStartDateStr;
            String endDateStr = StringUtils.isBlank(inputEndDateStr)
                    ? String.format("%1$s 08:30:00", SafeUtils.getDateFromNow(0)) : inputEndDateStr;
            // 找出所有投组，限制订阅了邮件，并且投组关注的债券数量大于0，并且今日有债券提醒消息
            groupEmailVOList = groupDAO.getGroupEmailVOList(userId, startDateStr, endDateStr, BondFavoriteGroupEmailVO.class);
            LOGGER.info(String.format("sendCustomGroupEmails: found [%1$d] groups", groupEmailVOList.size()));
            if ( groupEmailVOList== null || groupEmailVOList.isEmpty()) {
                return "NO msg found";
            }
            // 类别名称缓存
            List<BondFavoriteRadarSchema> validRadarSchemaList = bondFavRadarSchemaRepo.findAllByStatus(1);
            Map<Integer, String> radarTypeMap = validRadarSchemaList.stream()
                    .collect(Collectors.toMap(item -> SafeUtils.getInteger(item.getId()), BondFavoriteRadarSchema::getTypeName));
            // 遍历发送邮件
            groupEmailVOList.forEach(emailVO -> {
                Integer groupId = emailVO.getGroupId();
                // 判断是否已经发送过邮件
                String isGroupEmailSent = (String)redisUtil.get("groupEmail_" + groupId);
                if (StringUtils.isNotBlank(isGroupEmailSent)) {
                    cachedGroupIdList.add(groupId);
                }
                if (StringUtils.isNotBlank(mailTo) || StringUtils.isBlank(isGroupEmailSent)) {
                    Map<String, Object> groupFavMap = this.getGroupFavoriteMap(emailVO, radarTypeMap, startDateStr, endDateStr);
                    if (groupFavMap != null && !groupFavMap.isEmpty()) {
                        emailVO.getFavoriteMap().put("root", groupFavMap);
                        if (this.sendEmailByVelocityTemplate(emailVO, mailTo)) {
                            successGroupIdList.add(groupId);
                        } else {
                            failedGroupIdList.add(groupId);
                        }
                    }
                }
            });
        } catch (Exception ex) {
            LOGGER.error(String.format("sendCustomGroupEmails error[%1$s]", ex.getMessage()));
            return "sendCustomGroupEmails 出错，请检查日志";
        }
        sw.stop();
        String result = String.format("sendCustomGroupEmails 花费[%1$d]ms, 共[%2$d]个投组符合条件，其中已缓存了[%3$d]个投组，本次成功发送并缓存了[%4$d]个投组",
                sw.getTotalTimeMillis(), groupEmailVOList.size(), cachedGroupIdList.size(), successGroupIdList.size());
        if (failedGroupIdList.size() > 0) {
            result += String.format("，失败了[%1$d]个投组，失败详情请检查日志", failedGroupIdList.size());
        }
        return result;
    }

    /**
     * 获取投组中关注债券列表的消息详情
     * @param groupEmailVO
     * @param radarTypeMap
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    private Map<String, Object> getGroupFavoriteMap(BondFavoriteGroupEmailVO groupEmailVO, Map<Integer, String> radarTypeMap, String startDateStr, String endDateStr) {
        Map<String, Object> groupFavMap = new HashMap<>();
        // 按照startDateStr找到符合条件的favId列表
        List<Integer> validFavIdList = favoriteDAO.getFavIdListByStartDate(groupEmailVO.getGroupId(), startDateStr, endDateStr);
        List<BondFavorite> favList = bondFavoriteRepository.findAllByFavoriteIdInAndIsDelete(validFavIdList, 0);
        // BondBasicInfo缓存
        Map<Long, BondBasicInfoDoc> code2BasicMap = bondBasicInfoRepository.findAllByBondUniCodeIn(favList.stream()
                .map(BondFavorite::getBondUniCode).collect(Collectors.toList())).stream()
                .collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, item -> item));
        favList.stream().forEach(fav -> {
            BondBasicInfoDoc bondBasicInfoDoc = code2BasicMap.get(fav.getBondUniCode());
            Map<String, Object> favoriteItem = new HashMap<>();
            try {
                Date startDate = SafeUtils.convertStringToDate(startDateStr, SafeUtils.DATE_TIME_FORMAT1);
                Date endDate = SafeUtils.convertStringToDate(endDateStr, SafeUtils.DATE_TIME_FORMAT1);
                Map<String, Object> msgMap = this.getValidMsgMapByBondUniCodeAndDate(fav, radarTypeMap, startDate, endDate);
                if (!msgMap.isEmpty()) {
                    favoriteItem.put("msgMap", msgMap);
                    favoriteItem.put("favoriteId", fav.getFavoriteId());
                    favoriteItem.put("bondUniCode", fav.getBondUniCode());
                    favoriteItem.put("shortName", bondBasicInfoDoc.getShortName());
                    favoriteItem.put("code", bondBasicInfoDoc.getCode());
                    favoriteItem.put("tenorDays", bondBasicInfoDoc.getTenorDays());
                    favoriteItem.put("issuer", bondBasicInfoDoc.getIssuer());
                    groupFavMap.put(fav.getFavoriteId().toString(), favoriteItem);
                }
            } catch (Exception ex) {
                LOGGER.error("getGroupFavoriteMap: " + ex.getMessage());
            }
        });
        return groupFavMap;
    }

    /**
     *
     * @param groupEmailVO
     * @param mailTo 如果为空，则发到groupEmailVO中的email
     */
    private boolean sendEmailByVelocityTemplate(BondFavoriteGroupEmailVO groupEmailVO, String mailTo) {
        String todayDate = SafeUtils.getCurrDateFmtStr(SafeUtils.DATE_FORMAT2);
        mailTo = StringUtils.isBlank(mailTo) ? groupEmailVO.getEmail() : mailTo;
        String subject = String.format("%1$s%2$s债券提醒", todayDate, groupEmailVO.getGroupName());
        try {
            if (groupEmailVO.getFavoriteMap() != null && !groupEmailVO.getFavoriteMap().isEmpty()) {
                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mail, true);
                helper.setFrom(emailAccount);
                helper.setTo(mailTo);
                helper.setSubject(subject);
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        "favoriteGroupEmailTemplate.vm", "UTF-8", groupEmailVO.getFavoriteMap());
                helper.setText(text, true);
                mailSender.send(mail);
                LOGGER.info(String.format("sendEmailByVelocity: sent email to [%1$s] with subject[%2$s], groupId is [%3$d]",
                        mailTo, subject, groupEmailVO.getGroupId()));
                redisUtil.set("groupEmail_" + groupEmailVO.getGroupId(), "1");
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error(String.format("sendEmailByVelocity error: groupId[%1$d], mailTo[%2$s], subject[%3$s], exception[%4$s]",
                    groupEmailVO.getGroupId(), mailTo, subject, ex.getMessage()));
        }
        return false;
    }

    /**
     * 获取今天的有效提醒消息Map(用于Velocity模板), 根据bondUniCode
     *
     * @param favorite
     * @param startDate
     * @return
     */
    private Map<String, Object> getValidMsgMapByBondUniCodeAndDate(BondFavorite favorite, Map<Integer, String> radarTypeMap, Date startDate, Date endDate) {
        Map<String, Object> msgMapResult = new LinkedHashMap<>();
        Query query = new Query();
        Criteria criteria = new Criteria();
        Date msgBeginDate = startDate.getTime() > favorite.getCreateTime().getTime() ? startDate : favorite.getCreateTime();
        criteria.andOperator(Criteria.where("bondId").is(favorite.getBondUniCode()),
                Criteria.where("groupId").is(SafeUtils.getLong(favorite.getGroupId())),
                Criteria.where("createTime").gte(msgBeginDate).lte(endDate));
        query.addCriteria(criteria);
        query.with(new Sort(Direction.DESC, "createTime"));
        List<BondNotificationMsgDoc> msgDocList = mongoOperations.find(query, BondNotificationMsgDoc.class);
        // 需求1800：公告14和新闻预警16只推送情感emotionTag非空且大于0的
        List<Integer> emotionConditionList = Arrays.asList(14, 16);
        msgDocList = msgDocList.stream().filter(msg -> !emotionConditionList.contains(msg.getEventType()) || msg.getEmotionTag() > 0)
                .collect(Collectors.toList());
        msgDocList.stream().forEach(msg -> {
            Map<String, Object> msgItem = new HashMap<>();
            msgItem.put("msgId", msg.getId());
            msgItem.put("className", radarTypeMap.get(msg.getEventType()));
            msgItem.put("content", msg.getMsgContent());
            if (msg.getEventType() == 16) {
                // 新闻预警16
                String skipUrl = this.getMsgSkipUrl(msg);
                if (StringUtils.isNotBlank(skipUrl)) {
                    msgItem.put("skipUrl", skipUrl);
                }
            }
            msgItem.put("creatTimeStr", SafeUtils.convertDateToString(msg.getCreateTime(), SafeUtils.DATE_TIME_FORMAT3));
            msgMapResult.put(msg.getId().toString(), msgItem);
        });
        return msgMapResult;
    }

    public String testEmailChannel(String encryptKey) {
        if (!encryptKey.equals(PortfolioConstants.PORTFOLIO_EMAIL_TEST_PWD)) {
            return "密钥不正确";
        }
        StopWatch sw = new StopWatch();
        sw.start(PortfolioConstants.PORTFOLIO_EMAIL_TEST_PWD);
        StringBuilder result = new StringBuilder();
        String mailTo = "chao.xiao@innodealing.com";
        String subject = "测试邮件通道";
        StringBuilder content = new StringBuilder();
        IntStream.range(1,10).forEach(i -> content.append(i + ", Hey dude, how are you!/r/n"));
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom(emailAccount);
            helper.setTo(mailTo);
            helper.setSubject(subject);
            helper.setText(content.toString());
            mailSender.send(mail);
            LOGGER.info(String.format("testEmailChannel: sent email to [%1$s] with subject[%2$s]。", mailTo, subject));
            result.append(String.format("testEmailChannel: sent email to [%1$s] with subject[%2$s]。", mailTo, subject));
        } catch (Exception ex) {
            LOGGER.error(String.format("testEmailChannel error: sent email to [%1$s] with subject[%2$s], exception[%3$s]。",
                    mailTo, subject, ex.getMessage()));
            result.append(String.format("testEmailChannel error: sent email to [%1$s] with subject[%2$s], exception[%3$s]。",
                    mailTo, subject, ex.getMessage()));
        }
        sw.stop();
        result.append(String.format("总共花费时间[%1$d]ms。", sw.getTotalTimeMillis()));
        return result.toString();
    }

    public String recheckMsgReadStatusCache() {
        List<BondMsgUserStatusDoc> readCacheMsgList = mongoOperations.find(new Query(), BondMsgUserStatusDoc.class);
        List<Long> msgIdList = readCacheMsgList.stream().map(BondMsgUserStatusDoc::getNotifyMsgId).collect(Collectors.toList());
        List<Long> toBeRemovedReadCacheMsgIdList = msgDAO.getToBeRemovedReadCacheMsgIdList(msgIdList);
        if (toBeRemovedReadCacheMsgIdList.size() > 0) {
            // 分批删除
            int totalSize = toBeRemovedReadCacheMsgIdList.size(); //总记录数
            int batchSize = PortfolioConstants.BATCH_FAVORITE_LIMIT; //每页N条
            int totalBatch = totalSize / batchSize; //共N页
            if (totalSize % batchSize != 0) {
                totalBatch += 1;
                if (totalSize < batchSize) {
                    batchSize = toBeRemovedReadCacheMsgIdList.size();
                }
            }
            for (int batchIdx = 1; batchIdx < totalBatch + 1; batchIdx++) {
                int startIdx = (batchIdx - 1) * batchSize;
                int endIdx = batchIdx * batchSize > totalSize ? totalSize : batchIdx * batchSize;
                List<Long> pagedToBeRemovedReadCacheMsgIdList = toBeRemovedReadCacheMsgIdList.subList(startIdx, endIdx);
                mongoOperations.remove(new Query(Criteria.where("_id").in(pagedToBeRemovedReadCacheMsgIdList)),
                        BondMsgUserStatusDoc.class);
                LOGGER.info("recheckMsgReadStatusCache: removed [{}] msg from mongodb cache",
                        pagedToBeRemovedReadCacheMsgIdList.size());
            }
            String result = String.format("recheckMsgReadStatusCache: removed [%1$d] msg from mongodb cache",
                    toBeRemovedReadCacheMsgIdList.size());
            LOGGER.info(result);
            return result;
        } else {
            LOGGER.info("recheckMsgReadStatusCache: no msg need to be removed from mongodb cache");
            return "no msg removed";
        }
    }

    public String removeMsgWithInvalidGroupId() {
        List<Long> msgIdList = msgDAO.getMsgListWithInvalidGroupId();
        int resultSize = msgIdList.size();
        if (resultSize > 0) {
            // 分批删除
            int totalSize = resultSize; //总记录数
            int batchSize = PortfolioConstants.BATCH_FAVORITE_LIMIT; //每页N条
            int totalBatch = totalSize / batchSize; //共N页
            if (totalSize % batchSize != 0) {
                totalBatch += 1;
                if (totalSize < batchSize) {
                    batchSize = resultSize;
                }
            }
            for (int batchIdx = 1; batchIdx < totalBatch + 1; batchIdx++) {
                int startIdx = (batchIdx - 1) * batchSize;
                int endIdx = batchIdx * batchSize > totalSize ? totalSize : batchIdx * batchSize;
                List<Long> pagedMsgIdList = msgIdList.subList(startIdx, endIdx);
                msgDAO.deleteByMsgIdList(pagedMsgIdList);
                mongoOperations.remove(new Query(Criteria.where("_id").in(pagedMsgIdList)), BondMsgUserStatusDoc.class);
                mongoOperations.remove(new Query(Criteria.where("_id").in(pagedMsgIdList)), BondNotificationMsgDoc.class);
                LOGGER.info("removeMsgWithInvalidGroupId: removed [{}] msg from portfolio", pagedMsgIdList.size());
            }
            String result = String.format("removeMsgWithInvalidGroupId: removed [%1$d] msg from portfolio", msgIdList.size());
            LOGGER.info(result);
            return result;
        } else {
            LOGGER.info("removeMsgWithInvalidGroupId: no msg need to be removed from portfolio");
            return "no msg removed";
        }
    }
}
