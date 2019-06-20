package com.innodealing.engine.jdbc.bond;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.innodealing.model.dm.bond.BondAnnAttInfo;
import com.innodealing.model.dm.bond.PubAreaCode;

@Component
@Repository
public class PubAreaCodeDao {
	
	@Autowired  
    private  JdbcTemplate jdbcTemplate;
	
	
	public List<PubAreaCode> findAreaUniCode(long areaUniCode){
		String sql = "select AREA_UNI_CODE as AreaUniCode,sub_uni_code as SubUniCode,AREA_CHI_NAME as AreaChiName,AREA_NAME2 as AreaName2,area_name3 as AreaName3,area_name4 as AreaName4,area_name5 as AreaName5 FROM bond_ccxe.pub_area_code  where AREA_UNI_CODE = " + areaUniCode;
		RowMapper<PubAreaCode> rowMapper = new BeanPropertyRowMapper<PubAreaCode>(PubAreaCode.class); 
		List<PubAreaCode> list = jdbcTemplate.query(sql,rowMapper);
		return list;
		
		
	}
	public List<PubAreaCode> findAllProvince(){
		String sql = "SELECT AREA_UNI_CODE as AreaUniCode,sub_uni_code as SubUniCode,AREA_CHI_NAME as AreaChiName,AREA_NAME2 as AreaName2,area_name3 as AreaName3,area_name4 as AreaName4,area_name5 as AreaName5 FROM bond_ccxe.pub_area_code WHERE area_name2 IS not null AND area_name3 IS null AND area_name4 is null AND AREA_NAME1='中国'  AND area_name5 is null ";
		RowMapper<PubAreaCode> rowMapper = new BeanPropertyRowMapper<PubAreaCode>(PubAreaCode.class); 
		List<PubAreaCode> list = jdbcTemplate.query(sql,rowMapper);
		return list;
	}
	
	public List<PubAreaCode> findCityByProvinceCode(long subcode){
		String sql = "SELECT AREA_UNI_CODE as AreaUniCode,sub_uni_code as SubUniCode,AREA_CHI_NAME as AreaChiName,AREA_NAME2 as AreaName2,area_name3 as AreaName3,area_name4 as AreaName4,area_name5 as AreaName5 FROM bond_ccxe.pub_area_code" +  
				" WHERE area_name2 IS not null AND area_name3 IS not null AND area_name4 is null AND AREA_NAME1='中国'  AND area_name5 is null " + 
				" AND sub_uni_code="+subcode;
		RowMapper<PubAreaCode> rowMapper = new BeanPropertyRowMapper<PubAreaCode>(PubAreaCode.class); 
		List<PubAreaCode> list = jdbcTemplate.query(sql,rowMapper);
		return list;
	}
	
	public List<PubAreaCode> findAllProvinceCountryByCode(long areaUniCode){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT AREA_UNI_CODE as AreaUniCode,sub_uni_code as SubUniCode,AREA_CHI_NAME as AreaChiName,AREA_NAME2 as AreaName2,area_name3 as AreaName3,area_name4 as AreaName4,area_name5 as AreaName5"); 
		sql.append(" from bond_ccxe.pub_area_code a3 ");
		sql.append(" WHERE a3.sub_uni_code IN( ");
		sql.append(" (SELECT AREA_UNI_CODE FROM bond_ccxe.pub_area_code WHERE sub_uni_code in (");
		sql.append(" select a2.area_Uni_Code from bond_ccxe.pub_area_code a");
		sql.append(" left JOIN bond_ccxe.pub_area_code a1 on a.sub_uni_code = a1.area_Uni_Code");
		sql.append(" left JOIN bond_ccxe.pub_area_code a2 on a1.sub_uni_code = a2.area_Uni_Code");
		sql.append(" where a.area_Uni_Code=").append(areaUniCode).append(")");
		sql.append(" ))");
		RowMapper<PubAreaCode> rowMapper = new BeanPropertyRowMapper<PubAreaCode>(PubAreaCode.class); 
		List<PubAreaCode> list = jdbcTemplate.query(sql.toString(),rowMapper);
		return list;
	}

}
