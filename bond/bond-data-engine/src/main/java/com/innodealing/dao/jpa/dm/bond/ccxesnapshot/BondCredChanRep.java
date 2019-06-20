package com.innodealing.dao.jpa.dm.bond.ccxesnapshot;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.dao.jpa.BaseRepository;
import com.innodealing.model.dm.bond.ccxe.BondCredChan;

@Component("snapshotBean_BondCredChanRep")
public interface BondCredChanRep extends BaseRepository<BondCredChan, String>{
}
