package com.innodealing.model.mongo.dm;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author feng.ma
 * @date 2017年8月25日 上午10:28:37
 * @describe
 */

@Service("BondFavOtherIdxDoc")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_favorite_otheridx")
public class BondFavOtherIdxDoc extends BondFavoriteRadarMappingDoc {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935761337981382102L;

}
