/**
 * 
 */
package com.innodealing.engine.jpa.dm;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.model.dm.bond.UserOprRecord;

/**
 * @author stephen.ma
 * @date 2017年1月5日
 * @clasename UserOprRecordRepository.java
 * @decription TODO
 */
@Component
public interface UserOprRecordRepository extends BaseRepository<UserOprRecord, Integer> {
	
	@Modifying
	@Query("UPDATE UserOprRecord SET updateTime=?1 WHERE userId=?2")
	public void updateDateTimeByUserId(Date date, Integer userId);

}
