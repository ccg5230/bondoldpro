package com.innodealing.dao.jdbc.dm.bond.asbrs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.BaseDao;
import com.innodealing.model.dm.bond.asbrs.BondSecuFinaSheet;

/**
 * @author kunkun.zhou
 * @date 2016年12月07日
 * @clasename BondSecuFinaSheetDao.java
 * @decription TODO
 */
@Component
public class BondSecuFinaSheetDao extends BaseDao<BondSecuFinaSheet> {

	@Transactional
	public void deleteAll(){
		asbrsJdbcTemplate.execute("delete from secu_fina_sheet");
	}
	@Transactional
	public int batchSave(final List<BondSecuFinaSheet> list) {
		
		String sql = "insert into secu_fina_sheet "
				+ " ( "
				+ " Comp_ID, FIN_ENTITY, FIN_STATE_TYPE, FIN_DATE, FIN_PERIOD, last_update_timestamp, ROW_KEY, "
				+ " SBS101, "
				+ " SBS101_1, "
				+ " SBS102, "
				+ " SBS102_1, "
				+ " SBS103, "
				+ " SBS104, "
				+ " SBS105, "
				+ " SBS106, "
				+ " SBS107, "
				+ " SBS108, "
				+ " SBS109, "
				+ " SBS110, "
				+ " SBS111, "
				+ " SBS113, "
				+ " SBS114, "
				+ " SBS114_1, "
				+ " SBS115, "
				+ " SBS112, "
				+ " SBS116, "
				+ " SBS001, "
				+ " SBS201, "
				+ " SBS201_1, "
				+ " SBS202, "
				+ " SBS203, "
				+ " SBS204, "
				+ " SBS205, "
				+ " SBS206, "
				+ " SBS207, "
				+ " SBS208, "
				+ " SBS209, "
				+ " SBS210, "
				+ " SBS212, "
				+ " SBS213, "
				+ " SBS214, "
				+ " SBS216, "
				+ " SBS215, "
				+ " SBS002, "
				+ " SBS301, "
				+ " SBS302, "
				+ " SBS302_2, "
				+ " SBS303, "
				+ " SBS305, "
				+ " SBS304, "
				+ " SBS306, "
				+ " SBS310, "
				+ " SBS307, "
				+ " SBS003, "
				+ " SBS004, "
				+ " SPL100, "
				+ " SPL101, "
				+ " SPL101_1, "
				+ " SPL101_2, "
				+ " SPL101_3, "
				+ " SPL103, "
				+ " SPL104, "
				+ " SPL104_1, "
				+ " SPL105, "
				+ " SPL106, "
				+ " SPL107, "
				+ " SPL200, "
				+ " SPL201, "
				+ " SPL202, "
				+ " SPL203, "
				+ " SPL204, "
				+ " SPL300, "
				+ " SPL301, "
				+ " SPL302, "
				+ "SPL400,"
				+ "SPL401,"
				+ "SPL500,"
				+ "SPL501,"
				+ "SPL502,"
				+ "SPL503,"
				+ "SPL504,"
				+ "SPL505,"
				+ "SPL506,"
				+ "SPL507,"
				+ "SPL508,"
				+ "SCF101,"
				+ "SCF106,"
				+ "SCF102,"
				+ "SCF103,"
				+ "SCF104,"
				+ "SCF105,"
				+ "SCF100,"
				+ "SCF202,"
				+ "SCF203,"
				+ "SCF204,"
				+ "SCF200,"
				+ "SCF001,"
				+ "SCF301,"
				+ "SCF304,"
				+ "SCF303,"
				+ "SCF300,"
				+ "SCF401,"
				+ "SCF402,"
				+ "SCF403,"
				+ "SCF400,"
				+ "SCF002,"
				+ "SCF501,"
				+ "SCF502,"
				+ "SCF503,"
				+ "SCF500,"
				+ "SCF601,"
				+ "SCF602,"
				+ "SCF603,"
				+ "SCF600,"
				+ "SCF003,"
				+ "SCF004,"
				+ "SCF005,"
				+ "SCF006,"
				+ "SCF007,"
				+ "SCF901,"
				+ "SCF902,"
				+ "SCF903,"
				+ "SCF904,"
				+ "SCF905,"
				+ "SCF906,"
				+ "SCF907,"
				+ "SCF908,"
				+ "SCF909,"
				+ "SCF910,"
				+ "SCF911,"
				+ "SCF912,"
				+ "SCF913,"
				+ "SCF914,"
				+ "SCF915,"
				+ "SCF916,"
				+ "SCF917,"
				+ "SCF918,"
				+ "SCF919,"
				+ "SCF920,"
				+ "SCF921,"
				+ "SCF922,"
				+ "SCF923,"
				+ "SCF924,"
				+ "SCF925,"
				+ "SCF926,"
				+ "SCF927"				
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
				+ " ?, ?, ?, ?, ?, ? "
				+ " ) ";
		
		int[] batchsize = asbrsJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				BondSecuFinaSheet vo = list.get(i);

				ps.setLong(1, vo.getCOMP_ID());
				ps.setString(2, vo.getFIN_ENTITY());
				ps.setString(3, vo.getFIN_STATE_TYPE());
			//	ps.setObject(4, new Timestamp(finDate.getTime()));
				// 只要年月日
				ps.setDate(4, new java.sql.Date(vo.getFIN_DATE().getTime()));
				ps.setInt(5, vo.getFIN_PERIOD());
				ps.setObject(6, new Timestamp(vo.getLast_update_timestamp().getTime()));
				ps.setString(7, vo.getROW_KEY());
				
				ps.setBigDecimal(8, vo.getSBS101());
				ps.setBigDecimal(9, vo.getSBS101_1());
				ps.setBigDecimal(10, vo.getSBS102());
				ps.setBigDecimal(11, vo.getSBS102_1());
				ps.setBigDecimal(12, vo.getSBS103());
				
				ps.setBigDecimal(13, vo.getSBS104());
				ps.setBigDecimal(14, vo.getSBS105());
				ps.setBigDecimal(15, vo.getSBS106());
				ps.setBigDecimal(16, vo.getSBS107());
				ps.setBigDecimal(17, vo.getSBS108());
				ps.setBigDecimal(18, vo.getSBS109());
				ps.setBigDecimal(19, vo.getSBS110());
				ps.setBigDecimal(20, vo.getSBS111());
				ps.setBigDecimal(21, vo.getSBS113());
				ps.setBigDecimal(22, vo.getSBS114());
				ps.setBigDecimal(23, vo.getSBS114_1());
				ps.setBigDecimal(24, vo.getSBS115());
				
				ps.setBigDecimal(25, vo.getSBS112());
				ps.setBigDecimal(26, vo.getSBS116());
				
				ps.setBigDecimal(27, vo.getSBS001());
				ps.setBigDecimal(28, vo.getSBS201());
				ps.setBigDecimal(29, vo.getSBS201_1());
				
				ps.setBigDecimal(30, vo.getSBS202());
				ps.setBigDecimal(31, vo.getSBS203());
				ps.setBigDecimal(32, vo.getSBS204());
				ps.setBigDecimal(33, vo.getSBS205());
				
				ps.setBigDecimal(34, vo.getSBS206());
				ps.setBigDecimal(35, vo.getSBS207());
				ps.setBigDecimal(36, vo.getSBS208());
				ps.setBigDecimal(37, vo.getSBS209());
				ps.setBigDecimal(38, vo.getSBS210());
				ps.setBigDecimal(39, vo.getSBS212());
				
				ps.setBigDecimal(40, vo.getSBS213());
				
				ps.setBigDecimal(41, vo.getSBS214());
				ps.setBigDecimal(42, vo.getSBS216());
				ps.setBigDecimal(43, vo.getSBS215());
				ps.setBigDecimal(44, vo.getSBS002());
				ps.setBigDecimal(45, vo.getSBS301());
				
				ps.setBigDecimal(46, vo.getSBS302());
				ps.setBigDecimal(47, vo.getSBS302_2());
				ps.setBigDecimal(48, vo.getSBS303());
				ps.setBigDecimal(49, vo.getSBS305());
				ps.setBigDecimal(50, vo.getSBS304());
				ps.setBigDecimal(51, vo.getSBS306());
				ps.setBigDecimal(52, vo.getSBS310());
				ps.setBigDecimal(53, vo.getSBS307());
				ps.setBigDecimal(54, vo.getSBS003());
				ps.setBigDecimal(55, vo.getSBS004());
				ps.setBigDecimal(56, vo.getSPL100());
				ps.setBigDecimal(57, vo.getSPL101());
				ps.setBigDecimal(58, vo.getSPL101_1());
				ps.setBigDecimal(59, vo.getSPL101_2());
				
				ps.setBigDecimal(60, vo.getSPL101_3());
				ps.setBigDecimal(61, vo.getSPL103());
				ps.setBigDecimal(62, vo.getSPL104());
				ps.setBigDecimal(63, vo.getSPL104_1());
				ps.setBigDecimal(64, vo.getSPL105());
				ps.setBigDecimal(65, vo.getSPL106());
				ps.setBigDecimal(66, vo.getSPL107());
				ps.setBigDecimal(67, vo.getSPL200());
				
				ps.setBigDecimal(68, vo.getSPL201());
				ps.setBigDecimal(69, vo.getSPL202());
				ps.setBigDecimal(70, vo.getSPL203());
				ps.setBigDecimal(71, vo.getSPL204());
				ps.setBigDecimal(72, vo.getSPL300());
				ps.setBigDecimal(73, vo.getSPL301());
				ps.setBigDecimal(74, vo.getSPL302());
				
				//
				ps.setBigDecimal(75, vo.getSPL400());
				ps.setBigDecimal(76, vo.getSPL401());
				ps.setBigDecimal(77, vo.getSPL500());
				ps.setBigDecimal(78, vo.getSPL501());
				ps.setBigDecimal(79, vo.getSPL502());
				ps.setBigDecimal(80, vo.getSPL503());
				ps.setBigDecimal(81, vo.getSPL504());
				ps.setBigDecimal(82, vo.getSPL505());
				ps.setBigDecimal(83, vo.getSPL506());
				ps.setBigDecimal(84, vo.getSPL507());
				
				ps.setBigDecimal(85, vo.getSPL508());
				ps.setBigDecimal(86, vo.getSCF101());
				ps.setBigDecimal(87, vo.getSCF106());
				ps.setBigDecimal(88, vo.getSCF102());
				ps.setBigDecimal(89, vo.getSCF103());
				ps.setBigDecimal(90, vo.getSCF104());
				ps.setBigDecimal(91, vo.getSCF105());
				ps.setBigDecimal(92, vo.getSCF100());
				ps.setBigDecimal(93, vo.getSCF202());
				ps.setBigDecimal(94, vo.getSCF203());
				
				ps.setBigDecimal(95, vo.getSCF204());
				ps.setBigDecimal(96, vo.getSCF200());
				ps.setBigDecimal(97, vo.getSCF001());
				ps.setBigDecimal(98, vo.getSCF301());
				ps.setBigDecimal(99, vo.getSCF304());
				ps.setBigDecimal(100, vo.getSCF303());
				ps.setBigDecimal(101, vo.getSCF300());
				ps.setBigDecimal(102, vo.getSCF401());
				ps.setBigDecimal(103, vo.getSCF402());
				ps.setBigDecimal(104, vo.getSCF403());
				
				ps.setBigDecimal(105, vo.getSCF400());
				ps.setBigDecimal(106, vo.getSCF002());
				ps.setBigDecimal(107, vo.getSCF501());
				ps.setBigDecimal(108, vo.getSCF502());
				ps.setBigDecimal(109, vo.getSCF503());
				ps.setBigDecimal(110, vo.getSCF500());
				ps.setBigDecimal(111, vo.getSCF601());
				ps.setBigDecimal(112, vo.getSCF602());
				ps.setBigDecimal(113, vo.getSCF603());
				ps.setBigDecimal(114, vo.getSCF600());
				
				ps.setBigDecimal(115, vo.getSCF003());
				ps.setBigDecimal(116, vo.getSCF004());
				ps.setBigDecimal(117, vo.getSCF005());
				ps.setBigDecimal(118, vo.getSCF006());
				ps.setBigDecimal(119, vo.getSCF007());
				ps.setBigDecimal(120, vo.getSCF901());
				ps.setBigDecimal(121, vo.getSCF902());
				ps.setBigDecimal(122, vo.getSCF903());
				ps.setBigDecimal(123, vo.getSCF904());
				ps.setBigDecimal(124, vo.getSCF905());
				
				ps.setBigDecimal(125, vo.getSCF906());
				ps.setBigDecimal(126, vo.getSCF907());
				ps.setBigDecimal(127, vo.getSCF908());
				ps.setBigDecimal(128, vo.getSCF909());
				ps.setBigDecimal(129, vo.getSCF910());
				ps.setBigDecimal(130, vo.getSCF911());
				ps.setBigDecimal(131, vo.getSCF912());
				ps.setBigDecimal(132, vo.getSCF913());
				ps.setBigDecimal(133, vo.getSCF914());
				ps.setBigDecimal(134, vo.getSCF915());
				
				ps.setBigDecimal(135, vo.getSCF916());
				ps.setBigDecimal(136, vo.getSCF917());
				ps.setBigDecimal(137, vo.getSCF918());
				ps.setBigDecimal(138, vo.getSCF919());
				ps.setBigDecimal(139, vo.getSCF920());
				ps.setBigDecimal(140, vo.getSCF921());
				ps.setBigDecimal(141, vo.getSCF922());
				ps.setBigDecimal(142, vo.getSCF923());
				ps.setBigDecimal(143, vo.getSCF924());
				ps.setBigDecimal(144, vo.getSCF925());
				
				ps.setBigDecimal(145, vo.getSCF926());
				ps.setBigDecimal(146, vo.getSCF927());				
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
