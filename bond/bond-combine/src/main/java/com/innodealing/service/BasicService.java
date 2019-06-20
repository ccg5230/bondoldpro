package com.innodealing.service;

import com.innodealing.domain.dto.GlobalConfig;
import com.innodealing.domain.dto.UpgradeConfig;
import com.innodealing.domain.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BasicService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${url.meta_service}")
    private String META_SERVICE_URL;

    public List<GlobalConfig> getGlobalConfig() {
        String sql = "SELECT " +
                "config.ID AS id," +
                "config.PropName AS configName," +
                "config.PropValue AS configValue" +
                " FROM innodealing.t_sysconfig config" +
                " WHERE config.TerminalType = 2 AND config.SectionName = 'appgeneric'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GlobalConfig.class));
    }

    public UpgradeConfig getUpgradeConfig(String version, Integer terminalType) {
        String sql = "SELECT " +
                "upgrade.forceupdate AS mandatory," +
                "upgrade.nextversion AS newVersion," +
                "version.downloadurl AS urlAPK," +
                "true AS available" +
                " FROM innodealing.t_clientupgrade upgrade" +
                " INNER JOIN innodealing.t_clientversion version ON version.status = 1 AND version.versioncode = upgrade.nextversion AND version.tmtype = " + terminalType +
                " WHERE upgrade.status = 1 AND upgrade.tmtype = " + terminalType + " AND upgrade.versioncode = " + version +
                " ORDER BY CAST(upgrade.nextVersion AS DECIMAL(5,2)) DESC " +
                " LIMIT 1";
        return jdbcTemplate.queryForObject(sql, UpgradeConfig.class);
    }

    /**
     * 从meta_service获取用户
     *
     * @param userId 用户手机号
     * @return 用户
     */
    public Object getUserDetailByUserId(Long userId) {
        final String url = String.format("%s/user/detail/id", META_SERVICE_URL);
        return getUserDetail(userId, url);
    }

    /**
     * 从meta_service获取用户
     *
     * @param field 用户名
     * @param url   请求链接
     * @return 用户
     */
    private <T> Object getUserDetail(T field, final String url) {
        List<T> httpBody = Arrays.asList(field);
        try {
            ResponseEntity<RestResponse<List<Object>>> responseEntity
                    = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(httpBody),
                    new ParameterizedTypeReference<RestResponse<List<Object>>>() {
                    });
            if (responseEntity == null) {
                return null;
            }
            RestResponse<List<Object>> restResponse = responseEntity.getBody();
            if (restResponse == null || restResponse.getCode() == null || restResponse.getCode() != 0) {
                return null;
            }
            List<Object> users = restResponse.getData();
            if (users != null && users.size() > 0) {
                Object user = users.get(0);
                if (user != null) {
                    return user;
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    /**
     * 修改密码
     *
     * @param
     * @return
     */
    public Boolean updatePassword(String username, String password) {
        String url = String.format("%s/user/%s/password/%s", META_SERVICE_URL, username, password);
        return updatePassword(url);
    }

    private Boolean updatePassword(String url) {
        try {
            ResponseEntity<RestResponse<Boolean>> responseEntity
                    = restTemplate.exchange(url, HttpMethod.PUT, null,
                    new ParameterizedTypeReference<RestResponse<Boolean>>() {
                    }, "idm_basic");

            if (responseEntity == null) {
                return false;
            }
            RestResponse<Boolean> restResponse = responseEntity.getBody();
            if (restResponse == null || restResponse.getCode() == null || restResponse.getCode() != 0) {
                return false;
            }
            Boolean success = restResponse.getData();
            if (success == null) {
                return false;
            }
            return success;
        } catch (Exception ex) {
            return false;
        }
    }
}
