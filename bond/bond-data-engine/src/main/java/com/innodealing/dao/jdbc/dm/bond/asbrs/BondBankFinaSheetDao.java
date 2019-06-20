package com.innodealing.dao.jdbc.dm.bond.asbrs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.BaseDao;
import com.innodealing.model.dm.bond.asbrs.BondBankFinaSheet;

/**
 * @author kunkun.zhou
 * @date 2016年12月07日
 * @clasename BondBankFinaSheetDao.java
 * @decription TODO
 */
@Component
public class BondBankFinaSheetDao extends BaseDao<BondBankFinaSheet> {
	
	@Transactional
	public void deleteAll(){
		asbrsJdbcTemplate.execute("delete from bank_fina_sheet");
	}
	
	@Transactional
	public int batchSave(final List<BondBankFinaSheet> list) {
		
		String sql = "insert into bank_fina_sheet "
				+ " ( "
				+ " Comp_ID, FIN_ENTITY, FIN_STATE_TYPE, FIN_DATE, FIN_PERIOD, last_update_timestamp, ROW_KEY, "
				+ " BBS101, "
				+ " BBS102, "
				+ " BBS103, "
				+ " BBS104, "
				+ " BBS105, "
				+ " BBS106, "
				+ " BBS107, "
				+ " BBS108, "
				+ " BBS110, "
				+ " BBS111, "
				+ " BBS112, "				
				+ " BBS118, "
				+ " BBS114, "
				+ " BBS1102, "
				+ " BBS115, "
				+ " BBS1103, "
				+ " BBS116, "
				+ " BBS113, "
				+ " BBS117, "
				+ " BBS001, "
				+ " BBS202, "
				+ " BBS201, "
				+ " BBS203, "
				+ " BBS204, "
				+ " BBS205, "
				+ " BBS206, "
				+ " BBS207, "
				+ " BBS208, "
				+ " BBS209, "
				+ " BBS210, "
				+ " BBS212, "
				+ " BBS213, "
				+ " BBS211, "
				+ " BBS214, "
				+ " BBS002, "
				+ " BBS401, "
				+ " BBS402, "
				+ " BBS402_1, "
				+ " BBS403, "
				+ " BBS405, "
				+ " BBS404, "
				+ " BBS408, "
				+ " BBS409, "
				+ " BBS406, "
				+ " BBS003, "
				+ " BBS004, "
				+ " BPL100, "
				+ " BPL101, "
				+ " BPL101_1, "
				+ " BPL101_2, "
				+ " BPL102, "
				+ " BPL102_1, "
				+ " BPL102_2, "
				+ " BPL103, "
				+ " BPL103_1, "
				+ " BPL104, "
				+ " BPL105, "
				+ " BPL106, "
				+ " BPL200, "
				+ " BPL201, "
				+ " BPL202, "
				+ " BPL203, "
				+ " BPL204, "
				+ " BPL300, "
				+ " BPL301, "
				+ " BPL302, "
				+ " BPL400, "
				+ " BPL401, "
				+ " BPL500, "
				+ " BPL501, "
				+ " BPL502, "
				+ " BPL503, "
				+ " BPL504, "
				+ " BPL505, "
				+ " BPL506, "
				+ " BPL507, "
				+ " BPL508, "
				+ " BCF101, "
				+ " BCF102, "
				+ " BCF103, "
				+ " BCF107, "
				+ " BCF108, "
				+ " BCF100, "
				+ " BCF204, "
				+ " BCF205, "
				+ " BCF206, "
				+ " BCF207, "
				+ " BCF208, "
				+ " BCF209, "
				+ " BCF210, "
				+ " BCF200, "
				+ " BCF001, "
				+ " BCF301, "
				+ " BCF302, "
				+ " BCF303, "
				+ " BCF304, "
				+ " BCF305, "
				+ " BCF300, "
				+ " BCF401, "
				+ " BCF402, "
				+ " BCF403, "
				+ " BCF404, "
				+ " BCF400, "
				+ " BCF002, "
				+ " BCF501, "
				+ " BCF502, "
				+ " BCF503, "
				+ " BCF504, "
				+ " BCF500, "
				+ " BCF601, "
				+ " BCF602, "
				+ " BCF603, "
				+ " BCF604, "
				+ " BCF600, "
				+ " BCF003, "
				+ " BCF701, "
				+ " BCF004, "
				+ " BCF801, "
				+ " BCF005, "
				+ " BCF901, "
				+ " BCF902, "
				+ " BCF903, "
				+ " BCF904, "
				+ " BCF905, "
				+ " BCF906, "
				+ " BCF907, "
				+ " BCF908, "
				+ " BCF909, "
				+ " BCF910, "
				+ " BCF911, "
				+ " BCF912, "
				+ " BCF913, "
				+ " BCF914, "
				+ " BCF915, "
				+ " BCF916, "
				+ " BCF917, "
				+ " BCF918, "
				+ " BCF919, "
				+ " BCF920, "
				+ " BCF921, "
				+ " BCF922, "
				+ " BCF923, "
				+ " BCF924, "
				+ " BCF925, "
				+ " BCF926, "
				+ " BCF927 "
				+ " ) "
				+ " values "
				+ " ( "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ? "
				+ " ) ";
		
		int[] batchsize = asbrsJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				BondBankFinaSheet vo = list.get(i);

				ps.setLong(1, vo.getCOMP_ID());
				ps.setString(2, vo.getFIN_ENTITY());
				ps.setString(3, vo.getFIN_STATE_TYPE());
			//	ps.setObject(4, new Timestamp(finDate.getTime()));
				// 只要年月日
				ps.setDate(4, new java.sql.Date(vo.getFIN_DATE().getTime()));
				ps.setInt(5, vo.getFIN_PERIOD());
				ps.setObject(6, new Timestamp(vo.getLast_update_timestamp().getTime()));
				ps.setString(7, vo.getROW_KEY());
				
				ps.setBigDecimal(8, vo.getBBS101());
				ps.setBigDecimal(9, vo.getBBS102());
				ps.setBigDecimal(10, vo.getBBS103());
				ps.setBigDecimal(11, vo.getBBS104());
				ps.setBigDecimal(12, vo.getBBS105());
				
				ps.setBigDecimal(13, vo.getBBS106());
				ps.setBigDecimal(14, vo.getBBS107());
				ps.setBigDecimal(15, vo.getBBS108());
				ps.setBigDecimal(16, vo.getBBS110());
				ps.setBigDecimal(17, vo.getBBS111());
				ps.setBigDecimal(18, vo.getBBS112());
				ps.setBigDecimal(19, vo.getBBS118());
				ps.setBigDecimal(20, vo.getBBS114());
				ps.setBigDecimal(21, vo.getBBS1102());
				ps.setBigDecimal(22, vo.getBBS115());
				ps.setBigDecimal(23, vo.getBBS1103());
				ps.setBigDecimal(24, vo.getBBS116());
				
				ps.setBigDecimal(25, vo.getBBS113());
				ps.setBigDecimal(26, vo.getBBS117());
				
				ps.setBigDecimal(27, vo.getBBS001());
				ps.setBigDecimal(28, vo.getBBS202());
				ps.setBigDecimal(29, vo.getBBS201());
				
				ps.setBigDecimal(30, vo.getBBS203());
				ps.setBigDecimal(31, vo.getBBS204());
				ps.setBigDecimal(32, vo.getBBS205());
				ps.setBigDecimal(33, vo.getBBS206());
				
				ps.setBigDecimal(34, vo.getBBS207());
				ps.setBigDecimal(35, vo.getBBS208());
				ps.setBigDecimal(36, vo.getBBS209());
				ps.setBigDecimal(37, vo.getBBS210());
				ps.setBigDecimal(38, vo.getBBS212());
				ps.setBigDecimal(39, vo.getBBS213());
				
				ps.setBigDecimal(40, vo.getBBS211());
				
				ps.setBigDecimal(41, vo.getBBS214());
				ps.setBigDecimal(42, vo.getBBS002());
				ps.setBigDecimal(43, vo.getBBS401());
				ps.setBigDecimal(44, vo.getBBS402());
				ps.setBigDecimal(45, vo.getBBS402_1());
				
				ps.setBigDecimal(46, vo.getBBS403());
				ps.setBigDecimal(47, vo.getBBS405());
				ps.setBigDecimal(48, vo.getBBS404());
				ps.setBigDecimal(49, vo.getBBS408());
				ps.setBigDecimal(50, vo.getBBS409());
				ps.setBigDecimal(51, vo.getBBS406());
				ps.setBigDecimal(52, vo.getBBS003());
				ps.setBigDecimal(53, vo.getBBS004());
				ps.setBigDecimal(54, vo.getBPL100());
				ps.setBigDecimal(55, vo.getBPL101());
				ps.setBigDecimal(56, vo.getBPL101_1());
				ps.setBigDecimal(57, vo.getBPL101_2());
				ps.setBigDecimal(58, vo.getBPL102());
				ps.setBigDecimal(59, vo.getBPL102_1());
				
				ps.setBigDecimal(60, vo.getBPL102_2());
				ps.setBigDecimal(61, vo.getBPL103());
				ps.setBigDecimal(62, vo.getBPL103_1());
				ps.setBigDecimal(63, vo.getBPL104());
				ps.setBigDecimal(64, vo.getBPL105());
				ps.setBigDecimal(65, vo.getBPL106());
				ps.setBigDecimal(66, vo.getBPL200());
				ps.setBigDecimal(67, vo.getBPL201());
				
				ps.setBigDecimal(68, vo.getBPL202());
				ps.setBigDecimal(69, vo.getBPL203());
				ps.setBigDecimal(70, vo.getBPL204());
				ps.setBigDecimal(71, vo.getBPL300());
				ps.setBigDecimal(72, vo.getBPL301());
				ps.setBigDecimal(73, vo.getBPL302());
				ps.setBigDecimal(74, vo.getBPL400());
				
				//
				ps.setBigDecimal(75, vo.getBPL401());
				ps.setBigDecimal(76, vo.getBPL500());
				ps.setBigDecimal(77, vo.getBPL501());
				ps.setBigDecimal(78, vo.getBPL502());
				ps.setBigDecimal(79, vo.getBPL503());
				ps.setBigDecimal(80, vo.getBPL504());
				ps.setBigDecimal(81, vo.getBPL505());
				ps.setBigDecimal(82, vo.getBPL506());
				ps.setBigDecimal(83, vo.getBPL507());
				ps.setBigDecimal(84, vo.getBPL508());
				
				ps.setBigDecimal(85, vo.getBCF101());
				ps.setBigDecimal(86, vo.getBCF102());
				ps.setBigDecimal(87, vo.getBCF103());

				ps.setBigDecimal(88, vo.getBCF107());
				ps.setBigDecimal(89, vo.getBCF108());
				ps.setBigDecimal(90, vo.getBCF100());

				ps.setBigDecimal(91, vo.getBCF204());
				ps.setBigDecimal(92, vo.getBCF205());
				ps.setBigDecimal(93, vo.getBCF206());
				ps.setBigDecimal(94, vo.getBCF207());
				ps.setBigDecimal(95, vo.getBCF208());
				ps.setBigDecimal(96, vo.getBCF209());
				ps.setBigDecimal(97, vo.getBCF210());
				ps.setBigDecimal(98, vo.getBCF200());
				
				ps.setBigDecimal(99, vo.getBCF001());
				ps.setBigDecimal(100, vo.getBCF301());
				ps.setBigDecimal(101, vo.getBCF302());
				ps.setBigDecimal(102, vo.getBCF303());
				ps.setBigDecimal(103, vo.getBCF304());
				ps.setBigDecimal(104, vo.getBCF305());
				ps.setBigDecimal(105, vo.getBCF300());
				ps.setBigDecimal(106, vo.getBCF401());
				ps.setBigDecimal(107, vo.getBCF402());
				ps.setBigDecimal(108, vo.getBCF403());
				
				ps.setBigDecimal(109, vo.getBCF404());
				ps.setBigDecimal(110, vo.getBCF400());
				ps.setBigDecimal(111, vo.getBCF002());
				ps.setBigDecimal(112, vo.getBCF501());
				ps.setBigDecimal(113, vo.getBCF502());
				ps.setBigDecimal(114, vo.getBCF503());
				ps.setBigDecimal(115, vo.getBCF504());
				ps.setBigDecimal(116, vo.getBCF500());
				ps.setBigDecimal(117, vo.getBCF601());
				ps.setBigDecimal(118, vo.getBCF602());
				
				ps.setBigDecimal(119, vo.getBCF603());
				ps.setBigDecimal(120, vo.getBCF604());
				ps.setBigDecimal(121, vo.getBCF600());
				ps.setBigDecimal(122, vo.getBCF003());
				ps.setBigDecimal(123, vo.getBCF701());
				ps.setBigDecimal(124, vo.getBCF004());
				ps.setBigDecimal(125, vo.getBCF801());
				ps.setBigDecimal(126, vo.getBCF005());
				ps.setBigDecimal(127, vo.getBCF901());
				ps.setBigDecimal(128, vo.getBCF902());
				
				ps.setBigDecimal(129, vo.getBCF903());
				ps.setBigDecimal(130, vo.getBCF904());
				ps.setBigDecimal(131, vo.getBCF905());
				ps.setBigDecimal(132, vo.getBCF906());
				ps.setBigDecimal(133, vo.getBCF907());
				ps.setBigDecimal(134, vo.getBCF908());
				ps.setBigDecimal(135, vo.getBCF909());
				ps.setBigDecimal(136, vo.getBCF910());
				ps.setBigDecimal(137, vo.getBCF911());
				ps.setBigDecimal(138, vo.getBCF912());
				
				ps.setBigDecimal(139, vo.getBCF913());
				ps.setBigDecimal(140, vo.getBCF914());
				ps.setBigDecimal(141, vo.getBCF915());
				ps.setBigDecimal(142, vo.getBCF916());
				ps.setBigDecimal(143, vo.getBCF917());
				ps.setBigDecimal(144, vo.getBCF918());
				ps.setBigDecimal(145, vo.getBCF919());
				ps.setBigDecimal(146, vo.getBCF920());
				ps.setBigDecimal(147, vo.getBCF921());
				
				ps.setBigDecimal(148, vo.getBCF922());
				ps.setBigDecimal(149, vo.getBCF923());
				ps.setBigDecimal(150, vo.getBCF924());
				ps.setBigDecimal(151, vo.getBCF925());
				ps.setBigDecimal(152, vo.getBCF926());
				ps.setBigDecimal(153, vo.getBCF927());
			
			}

			public int getBatchSize() {
				return list.size();
			}
		});
		int count = 0;
		for (int i = 0; i < batchsize.length; i++) {
//			logger.info("batchUpdateOnlinestate batch :" + batchsize[i] + "--length:" + batchsize.length + "---index:" + i + "---onlinestate:" + onlinestate);
			if (batchsize[i] == 1) {
				count++;
			}
		}
		if (batchsize.length == 0) {
			return 0;
		}
		return count;
	}

}
