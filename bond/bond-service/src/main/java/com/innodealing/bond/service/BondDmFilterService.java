/**
 * 
 */
package com.innodealing.bond.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.bond.validation.MessageValidation;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.engine.mongo.bond.BondDmFilterRepository;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.mongo.dm.BondDmFilterDoc;
import com.innodealing.model.mongo.dm.BondIssuerDoc;

/**
 * @author Administrator
 *
 */
@Service
public class BondDmFilterService {

    private static final Logger LOG = LoggerFactory.getLogger(BondDmFilterService.class);

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    private BondDmFilterRepository filterRepository;

    @Autowired
    protected BondComInfoRepository comInfoRepo;

    @Autowired
    private MessageValidation messageValidation;

//    @Autowired
//    private RedisMsgService redisUtil;

    public BondDmFilterDoc saveFilter(Long userId, BondDmFilterDoc filter) {

        // TODO: 入参检查
        if (StringUtils.isEmpty(filter.getFilterName())) {
            int i = 1;
            for (; i < 1000; ++i) {
                String filterName = "筛选方案" + i;
                Query query = new Query();
                query.addCriteria(Criteria.where("userId").is(userId).andOperator(Criteria.where("filterName").is(filterName)));
                if (!mongoOperations.exists(query, BondDmFilterDoc.class)) {
                    filter.setFilterName(filterName);
                    break;
                }
            }
            if (i >= 1000) {
                throw new BusinessException("筛选方案数量超过上限1000");
            }
        }

        // 检查筛选方案是否重复
        this.filterNameChecked(userId, filter.getFilterName());
        // 检查筛选方案是否查过最大限制
//        this.exceedMaxFilter(userId);

        BondIssuerDoc issuDoc = filter.getIssuer();
        if (issuDoc != null) {
            Long comUniCode = issuDoc.getComUniCode();
            if (comUniCode == null) {
                throw new BusinessException("发行人数据异常:" + comUniCode);
            }
            if (!comInfoRepo.exists(comUniCode)) {
                throw new BusinessException("发行人不存在 :" + comUniCode);
            }
        }

        filter.setFilterId(null);
        /** 创建时间 */
        filter.setCreateTime(new Date());
        /** 更新时间 */
        filter.setUpdateTime(new Date());

        mongoOperations.save(filter, "filters");
        if (null != filter) {
            String filterMsg = userId + "_" + filter.getFilterName().trim();
            messageValidation.saveMessage(filterMsg);
        }
        return filter;
    }

    public BondDmFilterDoc updateFilter(BondDmFilterDoc filter) {
        if (StringUtils.isEmpty(filter.getFilterId())) {
            throw new BusinessException("筛选方案不能为空");
        }
        mongoOperations.save(filter, "filters");
        return filter;
    }

    public List<BondDmFilterDoc> findFilterNamesByUserId(Long userId) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Query query = new Query();
        query.with(sort);
        query.addCriteria(Criteria.where("userId").is(userId));
        query.fields().include("filterId").include("filterName").include("induClass").include("createTime");
        return mongoOperations.find(query, BondDmFilterDoc.class);
    }

    public BondDmFilterDoc findFilterById(String filterId) {
        return filterRepository.findOne(filterId);
    }

    public BondDmFilterDoc saveFilterName(Long userId, String filterId, String filterName) {
        BondDmFilterDoc doc = filterRepository.findOne(filterId);
        if (doc == null) {
            throw new BusinessException("该筛选方案不存在:" + filterId);
        }

        String filterMsg = userId + "_" + filterName.trim();
        if (messageValidation.isSameMessage(filterMsg)) {
            // if (doc.getIsInTrash()) {
            // throw new BusinessException("该方案名已在历史筛选方案中，请重新输入。");
            // } else {
            throw new BusinessException("方案名已重复，请重新输入。");
            // }
        } else {
            messageValidation.saveMessage(filterMsg);
            filterMsg = userId + "_" + doc.getFilterName().trim();
            messageValidation.deleteMessage(filterMsg);
        }

        doc.setFilterName(filterName);
        filterRepository.save(doc);
        return doc;
    }

    public List<BondDmFilterDoc> findFilterNamesFromTrashByUserId(Long userId) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");

        Query query = new Query();
        query.with(sort);

        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoOperations.find(query, BondDmFilterDoc.class);
    }

    public void deleteFileterFromTrash(Long userId, String filterId) {
        BondDmFilterDoc doc = filterRepository.findOne(filterId);
        if (doc == null) {
            throw new BusinessException("该筛选方案不存在:" + filterId);
        }
        String filterMsg = userId + "_" + doc.getFilterName().trim();
        messageValidation.deleteMessage(filterMsg);
        filterRepository.delete(filterId);
    }

    /**
     * 筛选方案名是否重复
     * 
     * @param userId
     * @param filterId
     * @param filterName
     * @return
     */
    public Boolean filterNameChecked(Long userId, String filterName) {

        String filterMsg = userId + "_" + filterName.trim();
        if (messageValidation.isSameMessage(filterMsg)) {
            throw new BusinessException("方案名已重复，请重新输入。");
        }

        return true;
    }

    /**
     * 查询筛选方案数量是否超过最大限制
     * 
     * @param userId
     * @return
     */
    public Boolean exceedMaxFilter(Long userId) {

        List<BondDmFilterDoc> list = findFilterNamesFromTrashByUserId(userId);

        if (list != null && list.size() >= 10) {
            throw new BusinessException("筛选方案数量超过上限10");
        }

        return true;
    }

}
