package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

/** 
* @author feng.ma
* @date 2017年5月17日 下午3:44:44 
* @describe 
*/
@Service("BondFavMaturityIdxDoc")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@JsonInclude(Include.NON_NULL)
@Document(collection="bond_favorite_maturityidx")
public class BondFavMaturityIdxDoc extends BondFavoriteRadarMappingDoc{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5658246485574918315L;

}
