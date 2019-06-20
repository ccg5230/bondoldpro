package com.innodealing.model.mongo.dm;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_bestquote_netprice")
public class BondBestQuoteNetpriceDoc extends BondBestQuoteDoc {

	private static final long serialVersionUID = 1L;

}
