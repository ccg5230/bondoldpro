package com.innodealing.reader;

import java.sql.ResultSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.innodealing.engine.mongo.bond.ComRiskRepository;
import com.innodealing.model.mongo.dm.ComRiskComInfo;

@Component
public class ComRiskReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComRiskReader.class);

	@Autowired
	public DataSource dataSource;
	
	@Autowired
	protected ComRiskRepository bondRiskRepository;

	@Bean
	public ItemReader<ComRiskComInfo> reader() {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.* FROM (SELECT a.rating,");
		sb.append("\n\t(CASE ");
		sb.append("\n\tWHEN a.rating = 'AAA' OR a.rating = 'AA+' THEN 1");
		sb.append("\n\tWHEN a.rating = 'AA' OR a.rating = 'AA-' OR a.rating = 'A+' THEN 2");
		sb.append("\n\tWHEN a.rating = 'A' OR a.rating = 'A-' OR a.rating = 'BBB+' OR a.rating = 'BBB'  THEN 3");
		sb.append("\n\tWHEN a.rating = 'BBB-' OR a.rating = 'BB+' THEN 4");
		sb.append("\n\tWHEN a.rating = 'BB' OR a.rating = 'BB-' OR a.rating = 'B+'  THEN 5");
		sb.append("\n\tWHEN a.rating = 'B' OR a.rating = 'B-' OR a.rating = 'CCC+'  THEN 6");
		sb.append("\n\tELSE");
		sb.append("\n\t7");
		sb.append("\n\tEND");
		sb.append("\n\t) AS level,c.iss_cred_level,b.com_chi_name,b.com_uni_code");
		sb.append("\n\tFROM amaresun.dm_bond a");
		sb.append("\n\tLEFT JOIN dmdb.t_bond_com_ext b ON b.ama_com_id = a.comp_id ");
		sb.append(
				"\n\tLEFT JOIN bond_ccxe.d_bond_iss_cred_chan c ON c.com_uni_code = b.com_uni_code AND IS_NEW_RATE = 1");
		sb.append("\n\tWHERE a.year <= 2017  AND YEAR(c.RATE_WRIT_DATE)<= 2017");
		sb.append("\n\tORDER BY c.RATE_WRIT_DATE DESC,a.YEAR  DESC,a.quan_month DESC) t GROUP BY t.com_uni_code");

		JdbcCursorItemReader<ComRiskComInfo> reader = new JdbcCursorItemReader<>();
		reader.setSql(sb.toString());
		reader.setDataSource(dataSource);
		reader.setRowMapper((ResultSet resultSet, int rowNum) -> {
			if (!(resultSet.isAfterLast()) && !(resultSet.isBeforeFirst())) {
				ComRiskComInfo bondRiskComInfo = new ComRiskComInfo();

				bondRiskComInfo.setRating(resultSet.getString("rating"));
				bondRiskComInfo.setLevel(resultSet.getInt("level"));
				bondRiskComInfo.setIssCredLevel(resultSet.getString("iss_cred_level"));
				bondRiskComInfo.setComChiName(resultSet.getString("com_chi_name"));
				bondRiskComInfo.setComUniCode(resultSet.getLong("com_uni_code"));
				bondRiskComInfo.setPdTime(2017);

				LOGGER.info("RowMapper BondRiskComInfo : {}", bondRiskComInfo);
				return bondRiskComInfo;
			} else {
				LOGGER.info("Returning null from rowMapper");
				return null;
			}
		});

		return reader;
	}
	

}
