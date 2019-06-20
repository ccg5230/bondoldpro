package com.innodealing.consts;

import java.util.Arrays;
import java.util.List;

/**
 * @author stephen.ma
 * @date 2016年7月26日
 * @clasename MqConstants.java
 * @decription TODO
 */
public class PortfolioConstants {

	/**
	 * 人工维护专用
	 */
	public static final String PORTFOLIO_EMAIL_MAINTAIN_PWD = "innodealingSecret@GroupEmails";

	/**
	 * 定时任务专用
	 */
	public static final String PORTFOLIO_EMAIL_CRONTAB_PWD = "innodealingSecretClearRedisGroupEmails";

	/**
	 * 测试邮件通道专用
	 */
	public static final String PORTFOLIO_EMAIL_TEST_PWD = "innodealingEmailTest";

	/**
	 * 投组邮件密码池
	 */
	public static final List<String> PORTFOLIO_EMAIL_PWD_POOL = Arrays.asList(PORTFOLIO_EMAIL_MAINTAIN_PWD, PORTFOLIO_EMAIL_CRONTAB_PWD);

	/**
	 * 单次关注债券的条数上限（应用事务）
	 */
	public static final int BATCH_FAVORITE_LIMIT = 2000;

}
