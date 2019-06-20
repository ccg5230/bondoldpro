package com.innodealing.dao.jdbc.dm.bond.asbrs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.BaseDao;
import com.innodealing.model.dm.bond.asbrs.BondInsuFinaSheet;

/**
 * @author kunkun.zhou
 * @date 2016年12月07日
 * @clasename BondInsuFinaSheetDao.java
 * @decription TODO
 */
@Component
public class BondInsuFinaSheetDao extends BaseDao<BondInsuFinaSheet> {

	@Transactional
	public void deleteAll(){
		asbrsJdbcTemplate.execute("delete from insu_fina_sheet");
	}
	
	@Transactional
	public int batchSave(final List<BondInsuFinaSheet> list) {
		
		String sql = "insert into insu_fina_sheet "
				+ " ( "
				+ " Comp_ID, FIN_ENTITY, FIN_STATE_TYPE, FIN_DATE, FIN_PERIOD, last_update_timestamp, ROW_KEY, "
				+ " IBS101, "
				+ " IBS102, "
				+ " IBS103, "
				+ " IBS104, "
				+ " IBS105, "
				+ " IBS107, "
				+ " IBS108, "
				+ " IBS109, "
				+ " IBS106, "
				+ " IBS110, "
				+ " IBS111, "
				+ " IBS112, "
				+ " IBS113, "
				+ " IBS114, "
				+ " IBS127, "
				+ " IBS115, "
				+ " IBS116, "
				+ " IBS120, "
				+ " IBS118, "
				+ " IBS119, "
				+ " IBS121, "
				+ " IBS128, "
				+ " IBS122, "
				+ " IBS124, "
				+ " IBS129, "
				+ " IBS125, "
				+ " IBS123, "
				+ " IBS001, "
				+ " IBS201, "
				+ " IBS202, "
				+ " IBS203, "
				+ " IBS204, "
				+ " IBS205, "
				+ " IBS206, "
				+ " IBS207, "
				+ " IBS208, "
				+ " IBS209, "
				+ " IBS210, "
				+ " IBS223, "
				+ " IBS211, "
				+ " IBS212, "
				+ " IBS224, "
				+ " IBS213, "
				+ " IBS214, "
				+ " IBS215, "
				+ " IBS216, "
				+ " IBS217, "
				+ " IBS218, "
				+ " IBS219, "
				+ " IBS221, "
				+ " IBS222, "
				+ " IBS220, "
				+ " IBS002, "
				+ " IBS301, "
				+ " IBS302, "
				+ " IBS302_2, "
				+ " IBS303, "
				+ " IBS304, "
				+ " IBS305, "
				+ " IBS308, "
				+ " IBS306, "
				+ " IBS309, "
				+ " IBS003, "
				+ " IBS004, "
				+ "IPL100, "
				+ "IPL101, "
				+ "IPL102, "
				+ "IPL102_1,"
				+ "IPL102_2,"
				+ "IPL103,"
				+ "IPL104,"
				+ "IPL104_1,"
				+ "IPL105,"
				+ "IPL106,"
				+ "IPL107,"
				+ "IPL200,"
				+ "IPL201,"
				+ "IPL202,"
				+ "IPL202_1,"
				+ "IPL203,"
				+ "IPL203_1,"
				+ "IPL204,"
				+ "IPL205,"
				+ "IPL206,"
				+ "IPL207,"
				+ "IPL208,"
				+ "IPL208_1,"
				+ "IPL210,"
				+ "IPL209,"
				+ "IPL300,"
				+ "IPL301,"
				+ "IPL302,"
				+ "IPL400,"
				+ "IPL401,"
				+ "IPL500,"
				+ "IPL501,"
				+ "IPL502,"
				+ "IPL503,"
				+ "IPL504,"
				+ "IPL505,"
				+ "IPL506,"
				+ "IPL507,"
				+ "IPL508,"
				+ "ICF101,"
				+ "ICF102,"
				+ "ICF103,"
				+ "ICF104,"
				+ "ICF105,"
				+ "ICF106,"
				+ "ICF108,"
				+ "ICF100,"
				+ "ICF201,"
				+ "ICF202,"
				+ "ICF203,"
				+ "ICF204,"
				+ "ICF205,"
				+ "ICF206,"
				+ "ICF207,"
				+ "ICF208,"
				+ "ICF200,"
				+ "ICF001,"
				+ "ICF301,"
				+ "ICF302,"
				+ "ICF303,"
				+ "ICF304,"
				+ "ICF305,"
				+ "ICF300,"
				+ "ICF401,"
				+ "ICF402,"
				+ "ICF403,"
				+ "ICF404,"
				+ "ICF405,"
				+ "ICF400,"
				+ "ICF002,"
				+ "ICF501,"
				+ "ICF502,"
				+ "ICF503,"
				+ "ICF504,"
				+ "ICF505,"
				+ "ICF500,"
				+ "ICF601,"
				+ "ICF602,"
				+ "ICF603,"
				+ "ICF604,"
				+ "ICF600,"
				+ "ICF003,"
				+ "ICF701,"
				+ "ICF004,"
				+ "ICF801,"
				+ "ICF005,"
				+ "ICF901,"
				+ "ICF902,"
				+ "ICF903,"
				+ "ICF904,"
				+ "ICF905,"
				+ "ICF906,"
				
				+ "ICF907,"
				+ "ICF908,"
				+ "ICF909,"
				+ "ICF910,"
				+ "ICF911,"
				+ "ICF912,"
				+ "ICF913,"
				+ "ICF914,"
				+ "ICF915,"
				+ "ICF916,"
				+ "ICF917,"
				+ "ICF918,"
				+ "ICF919,"
				+ "ICF920,"
				+ "ICF921,"
				+ "ICF922,"
				+ "ICF923,"
				+ "ICF924,"
				+ "ICF925,"
				+ "ICF926,"
				+ "ICF927"
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
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ? "
				+ " ) ";
		
		int[] batchsize = asbrsJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				BondInsuFinaSheet vo = list.get(i);

				ps.setLong(1, vo.getCOMP_ID());
				ps.setString(2, vo.getFIN_ENTITY());
				ps.setString(3, vo.getFIN_STATE_TYPE());
			//	ps.setObject(4, new Timestamp(finDate.getTime()));
				// 只要年月日
				ps.setDate(4, new java.sql.Date(vo.getFIN_DATE().getTime()));
				ps.setInt(5, vo.getFIN_PERIOD());
				ps.setObject(6, new Timestamp(vo.getLast_update_timestamp().getTime()));
				ps.setString(7, vo.getROW_KEY());
				
				ps.setBigDecimal(8, vo.getIBS101());
				ps.setBigDecimal(9, vo.getIBS102());
				ps.setBigDecimal(10, vo.getIBS103());
				ps.setBigDecimal(11, vo.getIBS104());
				ps.setBigDecimal(12, vo.getIBS105());
				
				ps.setBigDecimal(13, vo.getIBS107());
				ps.setBigDecimal(14, vo.getIBS108());
				ps.setBigDecimal(15, vo.getIBS109());
				ps.setBigDecimal(16, vo.getIBS106());
				ps.setBigDecimal(17, vo.getIBS110());
				ps.setBigDecimal(18, vo.getIBS111());
				ps.setBigDecimal(19, vo.getIBS112());
				ps.setBigDecimal(20, vo.getIBS113());
				ps.setBigDecimal(21, vo.getIBS114());
				ps.setBigDecimal(22, vo.getIBS127());
				ps.setBigDecimal(23, vo.getIBS115());
				ps.setBigDecimal(24, vo.getIBS116());
				
				ps.setBigDecimal(25, vo.getIBS120());
				ps.setBigDecimal(26, vo.getIBS118());
				
				ps.setBigDecimal(27, vo.getIBS119());
				ps.setBigDecimal(28, vo.getIBS121());
				ps.setBigDecimal(29, vo.getIBS128());
				
				ps.setBigDecimal(30, vo.getIBS122());
				ps.setBigDecimal(31, vo.getIBS124());
				ps.setBigDecimal(32, vo.getIBS129());
				ps.setBigDecimal(33, vo.getIBS125());
				
				ps.setBigDecimal(34, vo.getIBS123());
				ps.setBigDecimal(35, vo.getIBS001());
				ps.setBigDecimal(36, vo.getIBS201());
				ps.setBigDecimal(37, vo.getIBS202());
				ps.setBigDecimal(38, vo.getIBS203());
				ps.setBigDecimal(39, vo.getIBS204());
				
				ps.setBigDecimal(40, vo.getIBS205());
				
				ps.setBigDecimal(41, vo.getIBS206());
				ps.setBigDecimal(42, vo.getIBS207());
				ps.setBigDecimal(43, vo.getIBS208());
				ps.setBigDecimal(44, vo.getIBS209());
				ps.setBigDecimal(45, vo.getIBS210());
				
				ps.setBigDecimal(46, vo.getIBS223());
				ps.setBigDecimal(47, vo.getIBS211());
				ps.setBigDecimal(48, vo.getIBS212());
				ps.setBigDecimal(49, vo.getIBS224());
				ps.setBigDecimal(50, vo.getIBS213());
				ps.setBigDecimal(51, vo.getIBS214());
				ps.setBigDecimal(52, vo.getIBS215());
				ps.setBigDecimal(53, vo.getIBS216());
				ps.setBigDecimal(54, vo.getIBS217());
				ps.setBigDecimal(55, vo.getIBS218());
				ps.setBigDecimal(56, vo.getIBS219());
				ps.setBigDecimal(57, vo.getIBS221());
				ps.setBigDecimal(58, vo.getIBS222());
				ps.setBigDecimal(59, vo.getIBS220());
				
				ps.setBigDecimal(60, vo.getIBS002());
				ps.setBigDecimal(61, vo.getIBS301());
				ps.setBigDecimal(62, vo.getIBS302());
				ps.setBigDecimal(63, vo.getIBS302_2());
				ps.setBigDecimal(64, vo.getIBS303());
				ps.setBigDecimal(65, vo.getIBS304());
				ps.setBigDecimal(66, vo.getIBS305());
				ps.setBigDecimal(67, vo.getIBS308());
				
				ps.setBigDecimal(68, vo.getIBS306());
				ps.setBigDecimal(69, vo.getIBS309());
				ps.setBigDecimal(70, vo.getIBS003());
				ps.setBigDecimal(71, vo.getIBS004());
				ps.setBigDecimal(72, vo.getIPL100());
				ps.setBigDecimal(73, vo.getIPL101());
				ps.setBigDecimal(74, vo.getIPL102());
				ps.setBigDecimal(75, vo.getIPL102_1());
				
				//
				ps.setBigDecimal(76, vo.getIPL102_2());
				ps.setBigDecimal(77, vo.getIPL103());
				ps.setBigDecimal(78, vo.getIPL104());
				ps.setBigDecimal(79, vo.getIPL104_1());
				ps.setBigDecimal(80, vo.getIPL105());
				ps.setBigDecimal(81, vo.getIPL106());
				ps.setBigDecimal(82, vo.getIPL107());
				ps.setBigDecimal(83, vo.getIPL200());
				ps.setBigDecimal(84, vo.getIPL201());
				ps.setBigDecimal(85, vo.getIPL202());
				
				ps.setBigDecimal(86, vo.getIPL202_1());
				ps.setBigDecimal(87, vo.getIPL203());
				ps.setBigDecimal(88, vo.getIPL203_1());
				ps.setBigDecimal(89, vo.getIPL204());
				ps.setBigDecimal(90, vo.getIPL205());
				ps.setBigDecimal(91, vo.getIPL206());
				ps.setBigDecimal(92, vo.getIPL207());
				ps.setBigDecimal(93, vo.getIPL208());
				ps.setBigDecimal(94, vo.getIPL208_1());
				ps.setBigDecimal(95, vo.getIPL210());
				
				ps.setBigDecimal(96, vo.getIPL209());
				ps.setBigDecimal(97, vo.getIPL300());
				ps.setBigDecimal(98, vo.getIPL301());
				ps.setBigDecimal(99, vo.getIPL302());
				ps.setBigDecimal(100, vo.getIPL400());
				ps.setBigDecimal(101, vo.getIPL401());
				ps.setBigDecimal(102, vo.getIPL500());
				ps.setBigDecimal(103, vo.getIPL501());
				ps.setBigDecimal(104, vo.getIPL502());
				ps.setBigDecimal(105, vo.getIPL503());
				
				ps.setBigDecimal(106, vo.getIPL504());
				ps.setBigDecimal(107, vo.getIPL505());
				ps.setBigDecimal(108, vo.getIPL506());
				ps.setBigDecimal(109, vo.getIPL507());
				ps.setBigDecimal(110, vo.getIPL508());
				ps.setBigDecimal(111, vo.getICF101());
				ps.setBigDecimal(112, vo.getICF102());
				ps.setBigDecimal(113, vo.getICF103());
				ps.setBigDecimal(114, vo.getICF104());
				ps.setBigDecimal(115, vo.getICF105());
				
				ps.setBigDecimal(116, vo.getICF106());
				ps.setBigDecimal(117, vo.getICF108());
				ps.setBigDecimal(118, vo.getICF100());
				ps.setBigDecimal(119, vo.getICF201());
				ps.setBigDecimal(120, vo.getICF202());
				ps.setBigDecimal(121, vo.getICF203());
				ps.setBigDecimal(122, vo.getICF204());
				ps.setBigDecimal(123, vo.getICF205());
				ps.setBigDecimal(124, vo.getICF206());
				ps.setBigDecimal(125, vo.getICF207());
				
				ps.setBigDecimal(126, vo.getICF208());
				ps.setBigDecimal(127, vo.getICF200());
				ps.setBigDecimal(128, vo.getICF001());
				ps.setBigDecimal(129, vo.getICF301());
				ps.setBigDecimal(130, vo.getICF302());
				ps.setBigDecimal(131, vo.getICF303());
				ps.setBigDecimal(132, vo.getICF304());
				ps.setBigDecimal(133, vo.getICF305());
				ps.setBigDecimal(134, vo.getICF300());
				ps.setBigDecimal(135, vo.getICF401());
				
				ps.setBigDecimal(136, vo.getICF402());
				ps.setBigDecimal(137, vo.getICF403());
				ps.setBigDecimal(138, vo.getICF404());
				ps.setBigDecimal(139, vo.getICF405());
				ps.setBigDecimal(140, vo.getICF400());
				ps.setBigDecimal(141, vo.getICF002());
				ps.setBigDecimal(142, vo.getICF501());
				ps.setBigDecimal(143, vo.getICF502());
				ps.setBigDecimal(144, vo.getICF503());
				ps.setBigDecimal(145, vo.getICF504());
				
				ps.setBigDecimal(146, vo.getICF505());
				ps.setBigDecimal(147, vo.getICF500());
				ps.setBigDecimal(148, vo.getICF601());
				ps.setBigDecimal(149, vo.getICF602());
				ps.setBigDecimal(150, vo.getICF603());
				ps.setBigDecimal(151, vo.getICF604());
				ps.setBigDecimal(152, vo.getICF600());
				ps.setBigDecimal(153, vo.getICF003());
				ps.setBigDecimal(154, vo.getICF701());
				ps.setBigDecimal(155, vo.getICF004());
				
				ps.setBigDecimal(156, vo.getICF801());
				ps.setBigDecimal(157, vo.getICF005());
				ps.setBigDecimal(158, vo.getICF901());
				ps.setBigDecimal(159, vo.getICF902());
				ps.setBigDecimal(160, vo.getICF903());
				ps.setBigDecimal(161, vo.getICF904());
				ps.setBigDecimal(162, vo.getICF905());
				ps.setBigDecimal(163, vo.getICF906());
				ps.setBigDecimal(164, vo.getICF907());
				ps.setBigDecimal(165, vo.getICF908());
				
				ps.setBigDecimal(166, vo.getICF909());
				ps.setBigDecimal(167, vo.getICF910());
				ps.setBigDecimal(168, vo.getICF911());
				ps.setBigDecimal(169, vo.getICF912());
				ps.setBigDecimal(170, vo.getICF913());
				ps.setBigDecimal(171, vo.getICF914());
				ps.setBigDecimal(172, vo.getICF915());
				ps.setBigDecimal(173, vo.getICF916());
				ps.setBigDecimal(174, vo.getICF917());
				ps.setBigDecimal(175, vo.getICF918());
				
				ps.setBigDecimal(176, vo.getICF919());
				ps.setBigDecimal(177, vo.getICF920());
				ps.setBigDecimal(178, vo.getICF921());
				ps.setBigDecimal(179, vo.getICF922());
				ps.setBigDecimal(180, vo.getICF923());
				ps.setBigDecimal(181, vo.getICF924());
				ps.setBigDecimal(182, vo.getICF925());
				ps.setBigDecimal(183, vo.getICF926());
				ps.setBigDecimal(184, vo.getICF927());
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
