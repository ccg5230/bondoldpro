package com.innodealing.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import com.innodealing.model.dm.bond.BondAreaXRef;


@Component
public class AreaXRefCache {

	private static final Logger LOG = LoggerFactory.getLogger(AreaXRefCache.class);

	@Autowired
	private  JdbcTemplate jdbcTemplate;

	private HashMap<Integer, Set<Long>> areaMap = new HashMap<Integer, Set<Long> >();

	public AreaXRefCache(){}

	public Set<Long> get(List<Integer> regions) 
	{
		if (regions == null) return null;
		Set<Long> results = new HashSet<Long>(); 
		regions.forEach(region -> {
			results.addAll(get(region));
		});
		return results;
	}
	
	public Set<Long> get(Integer region)
	{
		if (region == null) 
			return null;
		
		if (!areaMap.isEmpty()) return areaMap.get(region);

		String sql = " SELECT pub_area_code.AREA_UNI_CODE, pub_area_code.AREA_CHI_SHORT_NAME, PAR.PAR_NAME, PAR.PAR_code  \r\n" + 
				"				FROM bond_ccxe.pub_area_code  \r\n" + 
				"				inner JOIN dmdb.t_pub_region ON bond_ccxe.pub_area_code.AREA_CHI_SHORT_NAME = dmdb.t_pub_region.province  \r\n" + 
				"				inner JOIN (  \r\n" + 
				"				SELECT *  \r\n" + 
				"				FROM dmdb.t_pub_par  \r\n" + 
				"				WHERE t_pub_par.par_sys_code = 2020  \r\n" + 
				"				) AS PAR ON dmdb.t_pub_region.region = PAR.PAR_NAME  \r\n" + 
				"				WHERE pub_area_code.AREA_NAME3 IS NULL AND pub_area_code.AREA_NAME1 = '中国' AND pub_area_code.AREA_NAME2 IS NOT NULL;";

		List<BondAreaXRef> pars = (List<BondAreaXRef>) jdbcTemplate.query(
				sql, new BeanPropertyRowMapper<BondAreaXRef>(BondAreaXRef.class));

		if (pars == null || pars.isEmpty()) {
			LOG.error("failed to get area xref");
			return null;
		}

		Iterator<BondAreaXRef> iterator = pars.iterator();
		while (iterator.hasNext()) {
			BondAreaXRef par = iterator.next();
			if (par.getParCode() == null) {
				LOG.error("empty area par code");
				continue;
			}
			if(!areaMap.containsKey(par.getParCode())) {
				areaMap.put(par.getParCode(), new HashSet<Long>());
			}
			Set<Long> areaUniCodes = areaMap.get(par.getParCode());
			areaUniCodes.add(par.getAreaUniCode());
		}	

		return areaMap.get(region);
	}

}
