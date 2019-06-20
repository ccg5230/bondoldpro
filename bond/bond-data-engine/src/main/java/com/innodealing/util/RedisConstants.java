package com.innodealing.util;

/**
 * @author kunkun.zhou
 * @clasename RedisConstants.java
 * @decription Redis的key
 */
public class RedisConstants {
	public static final long EXPIRE_TIME = 7200; // time out ,TimeUnit:second
	
	public static final long EXPIRE_TIME_AUTH = 29; // amaresun auth time out is 30 days. for valid,keep 29 days. TimeUnit:day 
	
	public static final String BOND_FIN_GEN_BALA_TAFBB_CUT_TIME = "bond_fin_gen_bala_tafbb_cut_time";//bond_fin_gen_bala_tafbb_cut_time
	
	public static final String BOND_COM_UNI_CODE_MATCH_ID = "bond_com_uni_code_match_id_";//bond_com_uni_code_match_id_[com_uni_code]
	
	public static final String BOND_RULE_ROW_KEY = "bond_rule_row_key_";//bond_rule_row_key_[tbl_nm]
	
	public static final String BOND_AMARESUN_AUTH = "bond_amaresun_auth_";//bond_amaresun_auth_[auth]
}
