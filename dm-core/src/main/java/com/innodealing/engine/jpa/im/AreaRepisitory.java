package com.innodealing.engine.jpa.im;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.innodealing.model.im.Area;

@Component
public interface AreaRepisitory extends BaseRepositoryIm<Area, Long>{
   
        @Query(nativeQuery = true, value = "SELECT * FROM t_area where id = ?1") 
        public Area findById(int id);
}
