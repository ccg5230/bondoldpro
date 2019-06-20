package com.innodealing.bond.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.innodealing.bond.vo.perfin.BondPerFinanceVo;
import com.innodealing.consts.PerFinanceConstants;
import com.innodealing.engine.jdbc.bond.BondPerFinanceDao;
import com.innodealing.model.dm.bond.BondPerFinance;
import com.innodealing.util.SafeUtils;

/**
 * @author stephen.ma
 * @date 2017年3月20日
 * @clasename BondPerFinService.java
 * @decription TODO
 */
@Service
public class BondPerFinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPerFinService.class);

	@Autowired
	private BondPerFinanceDao bondPerFinanceDao;

	/**
	 * @param userId
	 * @param keyword
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<BondPerFinanceVo> findPerfinreport(Integer userId, String keyword, Integer page, Integer size) {
		List<BondPerFinanceVo> listRes = new ArrayList<>();
		List<BondPerFinance> list = bondPerFinanceDao.getPerFinByKeyword(userId, keyword, page*size, size);
		if (null != list && list.size() > 0) {
			list.stream().forEachOrdered(bondPerFinance -> {
				BondPerFinanceVo vo = new BondPerFinanceVo();
				BeanUtils.copyProperties(bondPerFinance, vo);
//				vo.setCompCls(PerFinanceConstants.COMP_CLS_MAP.get(bondPerFinance.getCompClsCode()));
				vo.setCompCls(bondPerFinance.getInduClassNameL4());
				vo.setQuarter(bondPerFinance.getFinYear() + "/Q" + bondPerFinance.getFinQuarter());
				if (null != bondPerFinance.getRateTime()) {
					vo.setRateTime(SafeUtils.convertDateToString(bondPerFinance.getRateTime(), "yyyy年MM月dd日  HH:mm:ss"));
				}else{
					vo.setRateTime("");
				}

				listRes.add(vo);
			});
		}
		Integer total = bondPerFinanceDao.getgetPerFinByKeywordCount(userId, keyword);

		return new PageImpl<>(listRes, new PageRequest(page, size), total);
	}

}
