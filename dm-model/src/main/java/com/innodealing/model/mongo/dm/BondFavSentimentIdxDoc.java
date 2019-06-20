package com.innodealing.model.mongo.dm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

/** 
* @author feng.ma
* @date 2017年6月14日 下午2:15:37 
* @describe 
*/
@Service("BondFavSentimentIdxDoc")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@JsonInclude(Include.NON_NULL)
@Document(collection="bond_favorite_sentimentidx")
public class BondFavSentimentIdxDoc extends BondFavoriteRadarMappingDoc{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7376968108305724962L;

}
