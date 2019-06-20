package com.innodealing.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.exception.BusinessException;

@Component
public class SecurityDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * find sysuser by account and password
     * @param userAccount
     * @param password
     * @return map user
     */
    public Map<String,Object> findByNameAndPwd(String userAccount, String password){
        String sql = "SELECT *  FROM innodealing.users WHERE  username = ?";
        Map<String, Object> mapUser = null;
        try {
            mapUser = jdbcTemplate.queryForMap(sql, userAccount);
        } catch (DataAccessException e) {
           throw new BusinessException("账号{0}不存在！",userAccount);
        }
        Object passwordFromDB  = mapUser.get("password");
        if(passwordFromDB != null){
            if(!passwordFromDB.equals(password)){
                throw new BusinessException("密码有误，请核实您的密码是否正确！",userAccount);
            }
        }
        return mapUser;
    }
}
