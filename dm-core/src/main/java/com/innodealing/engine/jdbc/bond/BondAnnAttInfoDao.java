package com.innodealing.engine.jdbc.bond;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.innodealing.model.dm.bond.BondClassOneAnnAttInfo;
import com.innodealing.model.dm.bond.BondClassOneAnnInfo;
import com.innodealing.model.dm.bond.ccxe.BondDAnnMain;

@Component
@Repository
public class BondAnnAttInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final int MAX_UPLOAD_TIMEST = 24;//附件最大上传次数
    private static final int MAX_GET_LIMIT = 50;//最大数量

    public List<BondClassOneAnnAttInfo> queryByUrl() {

        String querySQL = "select id,att_type as attType,src_url as srcUrl,publish_date as publishDate,upload_times as uploadTimes from" +
        " t_bond_ann_att_info  where ftp_file_path = '' and file_name != '' " + 
                " order by publish_date desc,id desc limit " + MAX_GET_LIMIT;

        RowMapper<BondClassOneAnnAttInfo> rowMapper = new BeanPropertyRowMapper<BondClassOneAnnAttInfo>(BondClassOneAnnAttInfo.class);

        return (List<BondClassOneAnnAttInfo>) jdbcTemplate.query(querySQL, rowMapper);

    }

    public List<BondClassOneAnnAttInfo> queryUnUploadAtt(String ids) {
        String querySQL = "select id,att_type as attType,src_url as srcUrl,publish_date as publishDate,upload_times as uploadTimes from" + " t_bond_ann_att_info"
                + " where ftp_file_path = '' OR ftp_file_path  IS  NULL and file_name != ''" + " and id IN (" + ids + ") ";

        RowMapper<BondClassOneAnnAttInfo> rowMapper = new BeanPropertyRowMapper<BondClassOneAnnAttInfo>(BondClassOneAnnAttInfo.class);
        return (List<BondClassOneAnnAttInfo>) jdbcTemplate.query(querySQL, rowMapper);

    }

    public int updateFtpFilePath(BondClassOneAnnAttInfo bondAnnAttInfo) throws Exception {
        String querySQL = "update t_bond_ann_att_info set ftp_file_path =?,last_update_time=?,upload_times=? where id =?";
        int num = jdbcTemplate.update(querySQL, new Object[] { bondAnnAttInfo.getFtpFilePath(),bondAnnAttInfo.getLastUpdateTime(),
                bondAnnAttInfo.getUploadTimes(), bondAnnAttInfo.getId() });
        return num;

    }
    
    public int deleteUploadFailedAttDatas() throws Exception {
        String sql = "DELETE FROM t_bond_ann_att_info WHERE ftp_file_path = '' AND upload_times >="+ MAX_UPLOAD_TIMEST;
        int num = jdbcTemplate.update(sql);
        return num;

    }

    public List<BondClassOneAnnInfo> queryAnnInfoList(Long bondUniCode, String likeKey) {
        StringBuffer sql = new StringBuffer(32);
        sql.append("SELECT id,").append(" ann_title annTitle,").append(" bond_uni_code bondUniCode");
        sql.append(" FROM dmdb.t_bond_ann_info");
        sql.append(" WHERE ").append(" bond_uni_code =").append(bondUniCode);
        sql.append(" AND ann_title LIKE").append("'%").append(likeKey).append("%'");
        RowMapper<BondClassOneAnnInfo> rowMapper = new BeanPropertyRowMapper<BondClassOneAnnInfo>(BondClassOneAnnInfo.class);
        List<BondClassOneAnnInfo> list = (List<BondClassOneAnnInfo>) jdbcTemplate.query(sql.toString(), rowMapper);
        return list;
    }

    /**
     * 
     * queryOtherAnnInfoList:(查询债券非募集非申购类相关公告)
     *
     * @param @param
     *            bondUniCode
     * @param @param
     *            likeKey
     * @param @return
     *            设定文件
     * @return List<BondClassOneAnnInfo> DOM对象
     * @throws @since
     *             CodingExample Ver 1.1
     */
    public List<BondClassOneAnnInfo> queryAnnInfoList(Long bondUniCode) {
        StringBuffer sql = new StringBuffer(32);
        sql.append("SELECT id,").append(" ann_title annTitle,").append(" bond_uni_code bondUniCode");
        sql.append(" FROM dmdb.t_bond_ann_info");
        sql.append(" WHERE ").append(" bond_uni_code =").append(bondUniCode);

        RowMapper<BondClassOneAnnInfo> rowMapper = new BeanPropertyRowMapper<BondClassOneAnnInfo>(BondClassOneAnnInfo.class);
        List<BondClassOneAnnInfo> list = (List<BondClassOneAnnInfo>) jdbcTemplate.query(sql.toString(), rowMapper);

        return list;
    }

    // 查询公告附件
    public List<BondClassOneAnnAttInfo> queryAttInfoList(List<BondClassOneAnnInfo> annList, final String likeKey) {
        StringBuffer sql = new StringBuffer(32);
        sql.append("SELECT id,file_name fileName,ftp_file_path ftpFilePath");
        sql.append(" FROM dmdb.t_bond_ann_att_info");
        sql.append(" WHERE (ftp_file_path !='' AND ftp_file_path IS NOT NULL)");
        sql.append(" AND ann_id in(");
        StringBuffer sb = new StringBuffer();
        for (BondClassOneAnnInfo ann : annList) {
            Long annId = ann.getId();
            sb.append(annId).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sql.append(sb.toString());
        sql.append(") ");
        sql.append(" AND file_name LIKE").append("'%").append(likeKey).append("%'");
        RowMapper<BondClassOneAnnAttInfo> rowMapper = new BeanPropertyRowMapper<BondClassOneAnnAttInfo>(BondClassOneAnnAttInfo.class);
        List<BondClassOneAnnAttInfo> list = (List<BondClassOneAnnAttInfo>) jdbcTemplate.query(sql.toString(), rowMapper);

        return list;
    }

    public List<BondClassOneAnnAttInfo> queryOtherAttInfoList(List<BondClassOneAnnInfo> annList) {
        StringBuffer sql = new StringBuffer(32);
        sql.append("SELECT id,file_name fileName,ftp_file_path ftpFilePath");
        sql.append(" FROM dmdb.t_bond_ann_att_info");
        sql.append(" WHERE (ftp_file_path !='' AND ftp_file_path IS NOT NULL)");
        sql.append(" AND ann_id in(");
        StringBuffer sb = new StringBuffer();
        for (BondClassOneAnnInfo ann : annList) {
            Long annId = ann.getId();
            sb.append(annId).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sql.append(sb.toString());
        sql.append(") ");
        sql.append(" AND (file_name NOT LIKE'%申购%' AND file_name NOT LIKE'%募集%')");
        RowMapper<BondClassOneAnnAttInfo> rowMapper = new BeanPropertyRowMapper<BondClassOneAnnAttInfo>(BondClassOneAnnAttInfo.class);
        List<BondClassOneAnnAttInfo> list = (List<BondClassOneAnnAttInfo>) jdbcTemplate.query(sql.toString(), rowMapper);

        return list;
    }

    public BondClassOneAnnAttInfo queryAttInfoById(Long id) {
        StringBuffer sql = new StringBuffer(32);
        sql.append("SELECT id,file_name fileName,ftp_file_path ftpFilePath");
        sql.append(" FROM dmdb.t_bond_ann_att_info");
        sql.append(" WHERE id =").append(id);
        RowMapper<BondClassOneAnnAttInfo> rowMapper = new BeanPropertyRowMapper<BondClassOneAnnAttInfo>(BondClassOneAnnAttInfo.class);
        BondClassOneAnnAttInfo att = (BondClassOneAnnAttInfo) jdbcTemplate.queryForObject(sql.toString(), rowMapper);
        return att;
    }
    
    public BondDAnnMain queryComRatingAtt(Long annId) {
        StringBuffer sql = new StringBuffer(32);
        sql.append("SELECT ANN_ID,PERF_REP_DATE,DECL_DATE,ANN_TITLE,ATT_TITLE,FILE_PATH");
        sql.append(" FROM bond_ccxe.D_ANN_MAIN");
        sql.append(" WHERE ANN_ID =").append(annId);
        RowMapper<BondDAnnMain> rowMapper = new BeanPropertyRowMapper<BondDAnnMain>(BondDAnnMain.class);
        BondDAnnMain att = (BondDAnnMain) jdbcTemplate.queryForObject(sql.toString(), rowMapper);
        return att;
    }
    
}
