package com.innodealing.engine.jpa.im.user;

import org.springframework.data.jpa.repository.Query;

import com.innodealing.engine.jpa.im.BaseRepositoryIm;
import com.innodealing.model.im.user.SysuserQuote;

public interface SysuserQuoteRepository extends BaseRepositoryIm<SysuserQuote, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM t_sysuser_quote where dmacct = ?1") 
    public SysuserQuote getSysuserQuoteByDmacct(String dmacct);

}
