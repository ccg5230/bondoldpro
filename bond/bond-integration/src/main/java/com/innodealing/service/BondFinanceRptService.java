package com.innodealing.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.innodealing.amqp.SenderService;
import com.innodealing.domain.JsonResult;
import com.innodealing.util.HttpClientUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

@Service
public class BondFinanceRptService {

	private final static Logger logger = LoggerFactory.getLogger(BondFinanceRptService.class);

	// 1，新增财报；2，修改财报; 91 为规定时间未收集到财报
	public final static int FIN_RPTFLAG_91 = 91;

	// 节点日期的月份
	public static final List<Integer> FINARPT_MONTH_LISTS = new ArrayList<>(Arrays.asList(5, 9, 11));

	@Value("${config.define.bondweb.noFinanceRptUrl}")
	private String noFinanceRptUrl;

	private @Autowired SenderService senderService;

	public String handleUncollectedFinarpt() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(SafeUtils.getCurrentTime());
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;

		if (day == 1 && FINARPT_MONTH_LISTS.contains(month)) {
			// 请求接口数据，获取规定时间未收集到财报的主体
			Map<String, String> params = new HashMap<>();
			String quarterDate = calendar.get(Calendar.YEAR) + "-" + (month < 10 ? "0" + month : month) + "-"
					+ (day < 10 ? "0" + day : day);
			params.put("date", quarterDate);

			logger.info("handleUncollectedFinarpt URL:" + noFinanceRptUrl + ",quarterDate:" + quarterDate);
			String resJson = HttpClientUtil.doGet(noFinanceRptUrl, params);

			if (StringUtils.isNotBlank(resJson)) {
				handleAndPubData(resJson);
			}

		}

		return "done";
	}

	private void handleAndPubData(String resJson) {
		JsonResult resultObj = JSON.parseObject(resJson, JsonResult.class);
		List<JSONObject> list = (List<JSONObject>) resultObj.getData();

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			JSONObject issNoFinance = (JSONObject) iterator.next();

			Map<String, Object> map = new HashMap<>();
			map.put("comUniCode", issNoFinance.get("comUniCode"));
			map.put("finQuarter", issNoFinance.get("quarter"));
			map.put("finRptFlag", FIN_RPTFLAG_91);
			// 发送数据
			senderService.sendBondFinspclindic2Rabbitmq(JSON.toJSONString(map));
		}
	}

}
