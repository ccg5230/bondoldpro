package com.innodealing.service;

import java.util.HashMap;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.innodealing.aop.NoLogging;
import com.innodealing.model.BondCcxePar;


@Component
public class CcxeParCache {
	
	private static final Logger LOG = LoggerFactory.getLogger(CcxeParCache.class);
	
	public enum SysParEnum
	{
		MATUQUNIT(3078), RATETYPE(3038), INTERPAYCLAS(3039),
		RATEPROS(3110), BASERATE(3079), SECMAR(1011);
		
		private Integer value;    
		private SysParEnum(Integer value) {
			this.value = value;
		}
		public Integer code() {
			return value;
		}
	}
	
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	private Long parSysCode;
	private HashMap<Integer, HashMap<Integer, String>> sysParMap 
		= new HashMap<Integer, HashMap<Integer, String>>();
	
	public CcxeParCache(){}

	@NoLogging
	private HashMap<Integer, String> getParMap(Integer sysParCode)
	{
		if (sysParMap.containsKey(sysParCode)) return sysParMap.get(sysParCode);
		
		HashMap<Integer, String> parMap = new HashMap<Integer, String>();
		String sql = "select * from bond_ccxe.pub_par where par_sys_code=" + sysParCode.toString();
    	List<BondCcxePar> pars = (List<BondCcxePar>) jdbcTemplate.query(
    			sql, new BeanPropertyRowMapper<BondCcxePar>(BondCcxePar.class));
    	
    	if (pars == null || pars.isEmpty()) {
    		LOG.error("failed to find par_sys_code:" , sysParCode);
    		return null;
    	}
    	
    	Iterator<BondCcxePar> iterator = pars.iterator();
    	while (iterator.hasNext()) {
    		BondCcxePar par = iterator.next();
    		parMap.put(par.getParCode(), par.getParName());
    	}	
    	
    	sysParMap.put(sysParCode, parMap);
    	return parMap;
	}
	
	@NoLogging
	public synchronized String convert(Integer sysParCode, Integer parCode)
	{
		if (parCode == null) return null;
		
		HashMap<Integer, String> parMap = getParMap(sysParCode);
		if (parMap == null) return "";
		
		return parMap.getOrDefault(parCode, "");
	}
}
