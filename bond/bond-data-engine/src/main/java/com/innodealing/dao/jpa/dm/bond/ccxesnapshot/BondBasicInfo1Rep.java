package com.innodealing.dao.jpa.dm.bond.ccxesnapshot;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jpa.BaseRepository;
import com.innodealing.model.dm.bond.ccxe.BondBasicInfo1;


@Component("snapshotBean_BondBasicInfo1Rep")
public interface BondBasicInfo1Rep extends BaseRepository<BondBasicInfo1, String>{
    
}
