package com.innodealing.dao.jdbc.dm.bond.asbrs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.innodealing.dao.jdbc.BaseDao;
import com.innodealing.model.dm.bond.asbrs.BondManuFinaSheet;

/**
 * @author kunkun.zhou
 * @date 2016年12月07日
 * @clasename BondManuFinaSheetDao.java
 * @decription TODO
 */
@Component
public class BondManuFinaSheetDao extends BaseDao<BondManuFinaSheet> {

	@Transactional
	public void deleteAll(){
		asbrsJdbcTemplate.execute("delete from manu_fina_sheet");
	}
	
	@Transactional
	public int batchSave(final List<BondManuFinaSheet> list) {
		
		String sql = "insert into manu_fina_sheet "
				+ " ( "
				+ " Comp_ID, FIN_ENTITY, FIN_STATE_TYPE, FIN_DATE, FIN_PERIOD, last_update_timestamp, ROW_KEY, "
				+ " BS001, "
				+ " BS002, "
				+ " BS003, "
				+ " BS004, "
				+ " BS005, "
				+ " BS100, "
				+ " BS101, "
				+ " BS102, "
				+ " BS103, "
				+ " BS104, "
				+ " BS105, "
				+ " BS106, "
				+ " BS107, "
				+ " BS108, "
				+ " BS109, "
				+ " BS110, "
				+ " BS111, "
				+ " BS200, "
				+ " BS201, "
				+ " BS2011, "
				+ " BS2015, "
				+ " BS2016, "
				+ " BS202, "
				+ " BS203, "
				+ " BS204, "
				+ " BS205, "
				+ " BS220, "
				+ " BS221, "
				+ " BS222, "
				+ " BS223, "
				+ " BS224, "
				+ " BS225, "
				+ " BS240, "
				+ " BS251, "
				+ " BS252, "
				+ " BS253, "
				+ " BS254, "
				+ " BS255, "
				+ " BS300, "
				+ " BS301, "
				+ " BS302, "
				+ " BS303, "
				+ " BS304, "
				+ " BS305, "
				+ " BS306, "
				+ " BS307, "
				+ " BS308, "
				+ " BS309, "
				+ " BS310, "
				+ " BS311, "
				+ " BS312, "
				+ " BS313, "
				+ " BS400, "
				+ " BS401, "
				+ " BS402, "
				+ " BS403, "
				+ " BS404, "
				+ " BS405, "
				+ " BS406, "
				+ " BS407, "
				+ " BS501, "
				+ " BS502, "
				+ " BS503, "
				+ " BS504, "
				+ " BS505, "
				+ " BS506, "
				+ " BS507, "
				+ "PL101,"
				+ "PL102,"
				+ "PL103,"
				+ "PL201,"
				+ "PL202_5,"
				+ "PL202,"
				+ "PL203,"
				+ "PL203_1,"
				+ "PL204,"
				+ "PL205,"
				+ "PL200,"
				+ "PL210,"
				+ "PL211,"
				+ "PL212,"
				+ "PL220,"
				+ "PL301,"
				+ "PL301_1,"
				+ "PL301_3,"
				+ "PL301_7,"
				+ "PL300,"
				+ "PL401,"
				+ "PL402,"
				+ "PL402_1,"
				+ "PL400,"
				+ "PL501,"
				+ "PL500,"
				+ "PL601,"
				+ "PL600,"
				+ "CF101,"
				+ "CF102,"
				+ "CF103,"
				+ "CF100,"
				+ "CF201,"
				+ "CF202,"
				+ "CF203,"
				+ "CF204,"
				+ "CF200,"
				+ "CF001,"
				+ "CF301,"
				+ "CF302,"
				+ "CF303,"
				+ "CF305,"
				+ "CF304,"
				+ "CF300,"
				+ "CF401,"
				+ "CF402,"
				+ "CF404,"
				+ "CF403,"
				+ "CF400,"
				+ "CF002,"
				+ "CF501,"
				+ "CF502,"
				+ "CF504,"
				+ "CF503,"
				+ "CF500,"
				+ "CF601,"
				+ "CF602,"
				+ "CF603,"
				+ "CF600,"
				+ "CF003,"
				+ "CF004,"
				+ "CF005,"
				+ "CF006,"
				+ "CF007,"
				+ "CF901,"
				+ "CF902,"
				+ "CF903,"
				+ "CF904,"
				+ "CF905,"
				+ "CF906,"
				+ "CF907,"
				+ "CF908,"
				+ "CF909,"
				+ "CF910,"
				+ "CF911,"
				+ "CF912,"
				+ "CF913,"
				+ "CF914,"
				+ "CF915,"
				+ "CF916,"
				+ "CF917,"
				+ "CF918,"
				+ "CF919,"
				+ "CF920,"
				+ "CF921,"
				+ "CF922,"
				+ "CF923,"
				+ "CF924,"
				+ "CF925"
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
				+ " ?, ?, ? "
				+ " ) ";
		
		int[] batchsize = asbrsJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				BondManuFinaSheet vo = list.get(i);

				ps.setLong(1, vo.getCOMP_ID());
				ps.setString(2, vo.getFIN_ENTITY());
				ps.setString(3, vo.getFIN_STATE_TYPE());
			//	ps.setObject(4, new Timestamp(finDate.getTime()));
				// 只要年月日
				ps.setDate(4, new java.sql.Date(vo.getFIN_DATE().getTime()));
				ps.setInt(5, vo.getFIN_PERIOD());
				ps.setObject(6, new Timestamp(vo.getLast_update_timestamp().getTime()));
				ps.setString(7, vo.getROW_KEY());
				
				ps.setBigDecimal(8, vo.getBS001());
				ps.setBigDecimal(9, vo.getBS002());
				ps.setBigDecimal(10, vo.getBS003());
				ps.setBigDecimal(11, vo.getBS004());
				ps.setBigDecimal(12, vo.getBS005());
				
				ps.setBigDecimal(13, vo.getBS100());
				ps.setBigDecimal(14, vo.getBS101());
				ps.setBigDecimal(15, vo.getBS102());
				ps.setBigDecimal(16, vo.getBS103());
				ps.setBigDecimal(17, vo.getBS104());
				ps.setBigDecimal(18, vo.getBS105());
				ps.setBigDecimal(19, vo.getBS106());
				ps.setBigDecimal(20, vo.getBS107());
				ps.setBigDecimal(21, vo.getBS108());
				ps.setBigDecimal(22, vo.getBS109());
				ps.setBigDecimal(23, vo.getBS110());
				ps.setBigDecimal(24, vo.getBS111());
				
				ps.setBigDecimal(25, vo.getBS200());
				ps.setBigDecimal(26, vo.getBS201());
				
				ps.setBigDecimal(27, vo.getBS2011());
				ps.setBigDecimal(28, vo.getBS2015());
				ps.setBigDecimal(29, vo.getBS2016());
				
				ps.setBigDecimal(30, vo.getBS202());
				ps.setBigDecimal(31, vo.getBS203());
				ps.setBigDecimal(32, vo.getBS204());
				ps.setBigDecimal(33, vo.getBS205());
				
				ps.setBigDecimal(34, vo.getBS220());
				ps.setBigDecimal(35, vo.getBS221());
				ps.setBigDecimal(36, vo.getBS222());
				ps.setBigDecimal(37, vo.getBS223());
				ps.setBigDecimal(38, vo.getBS224());
				ps.setBigDecimal(39, vo.getBS225());
				
				ps.setBigDecimal(40, vo.getBS240());
				
				ps.setBigDecimal(41, vo.getBS251());
				ps.setBigDecimal(42, vo.getBS252());
				ps.setBigDecimal(43, vo.getBS253());
				ps.setBigDecimal(44, vo.getBS254());
				ps.setBigDecimal(45, vo.getBS255());
				
				ps.setBigDecimal(46, vo.getBS300());
				ps.setBigDecimal(47, vo.getBS301());
				ps.setBigDecimal(48, vo.getBS302());
				ps.setBigDecimal(49, vo.getBS303());
				ps.setBigDecimal(50, vo.getBS304());
				ps.setBigDecimal(51, vo.getBS305());
				ps.setBigDecimal(52, vo.getBS306());
				ps.setBigDecimal(53, vo.getBS307());
				ps.setBigDecimal(54, vo.getBS308());
				ps.setBigDecimal(55, vo.getBS309());
				ps.setBigDecimal(56, vo.getBS310());
				ps.setBigDecimal(57, vo.getBS311());
				ps.setBigDecimal(58, vo.getBS312());
				ps.setBigDecimal(59, vo.getBS313());
				
				ps.setBigDecimal(60, vo.getBS400());
				ps.setBigDecimal(61, vo.getBS401());
				ps.setBigDecimal(62, vo.getBS402());
				ps.setBigDecimal(63, vo.getBS403());
				ps.setBigDecimal(64, vo.getBS404());
				ps.setBigDecimal(65, vo.getBS405());
				ps.setBigDecimal(66, vo.getBS406());
				ps.setBigDecimal(67, vo.getBS407());
				
				ps.setBigDecimal(68, vo.getBS501());
				ps.setBigDecimal(69, vo.getBS502());
				ps.setBigDecimal(70, vo.getBS503());
				ps.setBigDecimal(71, vo.getBS504());
				ps.setBigDecimal(72, vo.getBS505());
				ps.setBigDecimal(73, vo.getBS506());
				ps.setBigDecimal(74, vo.getBS507());
				
				//
				ps.setBigDecimal(75, vo.getPL101());
				ps.setBigDecimal(76, vo.getPL102());
				ps.setBigDecimal(77, vo.getPL103());
				ps.setBigDecimal(78, vo.getPL201());
				ps.setBigDecimal(79, vo.getPL202_5());
				ps.setBigDecimal(80, vo.getPL202());
				ps.setBigDecimal(81, vo.getPL203());
				ps.setBigDecimal(82, vo.getPL203_1());
				ps.setBigDecimal(83, vo.getPL204());
				ps.setBigDecimal(84, vo.getPL205());
				
				ps.setBigDecimal(85, vo.getPL200());
				ps.setBigDecimal(86, vo.getPL210());
				ps.setBigDecimal(87, vo.getPL211());
				ps.setBigDecimal(88, vo.getPL212());
				ps.setBigDecimal(89, vo.getPL220());
				ps.setBigDecimal(90, vo.getPL301());
				ps.setBigDecimal(91, vo.getPL301_1());
				ps.setBigDecimal(92, vo.getPL301_3());
				ps.setBigDecimal(93, vo.getPL301_7());
				ps.setBigDecimal(94, vo.getPL300());
				
				ps.setBigDecimal(95, vo.getPL401());
				ps.setBigDecimal(96, vo.getPL402());
				ps.setBigDecimal(97, vo.getPL402_1());
				ps.setBigDecimal(98, vo.getPL400());
				ps.setBigDecimal(99, vo.getPL501());
				ps.setBigDecimal(100, vo.getPL500());
				ps.setBigDecimal(101, vo.getPL601());
				ps.setBigDecimal(102, vo.getPL600());
				ps.setBigDecimal(103, vo.getCF101());
				ps.setBigDecimal(104, vo.getCF102());
				
				ps.setBigDecimal(105, vo.getCF103());
				ps.setBigDecimal(106, vo.getCF100());
				ps.setBigDecimal(107, vo.getCF201());
				ps.setBigDecimal(108, vo.getCF202());
				ps.setBigDecimal(109, vo.getCF203());
				ps.setBigDecimal(110, vo.getCF204());
				ps.setBigDecimal(111, vo.getCF200());
				ps.setBigDecimal(112, vo.getCF001());
				ps.setBigDecimal(113, vo.getCF301());
				ps.setBigDecimal(114, vo.getCF302());
				
				ps.setBigDecimal(115, vo.getCF303());
				ps.setBigDecimal(116, vo.getCF305());
				ps.setBigDecimal(117, vo.getCF304());
				ps.setBigDecimal(118, vo.getCF300());
				ps.setBigDecimal(119, vo.getCF401());
				ps.setBigDecimal(120, vo.getCF402());
				ps.setBigDecimal(121, vo.getCF404());
				ps.setBigDecimal(122, vo.getCF403());
				ps.setBigDecimal(123, vo.getCF400());
				ps.setBigDecimal(124, vo.getCF002());
				
				ps.setBigDecimal(125, vo.getCF501());
				ps.setBigDecimal(126, vo.getCF402());
				ps.setBigDecimal(127, vo.getCF504());
				ps.setBigDecimal(128, vo.getCF503());
				ps.setBigDecimal(129, vo.getCF500());
				ps.setBigDecimal(130, vo.getCF601());
				ps.setBigDecimal(131, vo.getCF602());
				ps.setBigDecimal(132, vo.getCF603());
				ps.setBigDecimal(133, vo.getCF600());
				ps.setBigDecimal(134, vo.getCF003());
				
				ps.setBigDecimal(135, vo.getCF004());
				ps.setBigDecimal(136, vo.getCF005());
				ps.setBigDecimal(137, vo.getCF006());
				ps.setBigDecimal(138, vo.getCF007());
				ps.setBigDecimal(139, vo.getCF901());
				ps.setBigDecimal(140, vo.getCF902());
				ps.setBigDecimal(141, vo.getCF903());
				ps.setBigDecimal(142, vo.getCF904());
				ps.setBigDecimal(143, vo.getCF905());
				ps.setBigDecimal(144, vo.getCF906());
				
				ps.setBigDecimal(145, vo.getCF907());
				ps.setBigDecimal(146, vo.getCF908());
				ps.setBigDecimal(147, vo.getCF909());
				ps.setBigDecimal(148, vo.getCF910());
				ps.setBigDecimal(149, vo.getCF911());
				ps.setBigDecimal(150, vo.getCF912());
				ps.setBigDecimal(151, vo.getCF913());
				ps.setBigDecimal(152, vo.getCF914());
				ps.setBigDecimal(153, vo.getCF915());
				ps.setBigDecimal(154, vo.getCF916());
				
				ps.setBigDecimal(155, vo.getCF917());
				ps.setBigDecimal(156, vo.getCF918());
				ps.setBigDecimal(157, vo.getCF919());
				ps.setBigDecimal(158, vo.getCF920());
				ps.setBigDecimal(159, vo.getCF921());
				ps.setBigDecimal(160, vo.getCF922());
				ps.setBigDecimal(161, vo.getCF923());
				ps.setBigDecimal(162, vo.getCF924());
				ps.setBigDecimal(163, vo.getCF925());
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
