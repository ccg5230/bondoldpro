package com.innodealing.bond.service.rrs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class BondDocTableSchema {
	
	private String table = new String();
	private Map<String, String> col2descMap = new HashMap<String, String>();
	private String fieldstr = new String();

	public BondDocTableSchema(String table, Map<String, String> params) {
		this.table = table;
		this.col2descMap = params;
	}
	
	public void prepareForQuery() {
		if (col2descMap != null) {
			this.col2descMap.forEach((k,v) -> { 
				if (!StringUtils.isEmpty(this.fieldstr)) 
					this.fieldstr = this.fieldstr.concat(","); 
				this.fieldstr = this.fieldstr.concat(k);
			});
		}
	}

	public String getColumnByDesc(String desc) {
		return col2descMap.get(desc);
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public Map<String, String> getFields() {
		return col2descMap;
	}
	public void setFields(Map<String, String> fields) {
		this.col2descMap = fields;
	}
	public String getFieldstr() {
		return fieldstr;
	}
	public void setFieldstr(String fieldstr) {
		this.fieldstr = fieldstr;
	}

}
