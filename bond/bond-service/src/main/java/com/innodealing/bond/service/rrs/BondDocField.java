package com.innodealing.bond.service.rrs;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.innodealing.bond.service.rrs.BondDocGrammer.FormatLink;
import com.innodealing.bond.service.rrs.BondDocGrammer.GrammaticLink;
import com.innodealing.bond.service.rrs.BondDocGrammer.NopPreprocessor;

public class BondDocField implements IPdFieldDocGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(BondDocRuleBank.class);
	
	String colDesc;
	String colName;
	Double last;
	Double pre;
	IDocFieldLink next;
	IFieldFormatter formatter;
	IValuePreprocessor valPreProcessor = new NopPreprocessor();

	public IValuePreprocessor valPreProc() {
		return valPreProcessor;
	}
	
	public BondDocField setValPreProc(IValuePreprocessor p) {
		this.valPreProcessor = p;
		return this;
	}

	public BondDocField link(IDocFieldLink next)
	{
		this.next = next;
		return next.getField();
	}
	
	public String genText()
	{
		String thisText = formatter.format(colDesc, pre, last);
		LOG.info(this.toString() + ", text:" + thisText);

		if(next == null) 
			return thisText;
		else {
			String nextText = next.getField().genText();
			
			if (this.next instanceof GrammaticLink) {
				GrammaticLink next = (GrammaticLink)this.next;
				//coordination:0
				//include: 1
				//deduce:2
				switch(next.getConnType()) {

				case 0: //并列关系
					if (StringUtils.isEmpty(thisText))
						return nextText; //并列关系表示每个子句都是独立的，缺少其他子句不影响本句的显示
					else {
						return String.format(next.getFormat(), thisText, nextText);
					}
				case 1: //包含关系 this->next
					if (StringUtils.isEmpty(nextText)) {
						return thisText; //如果没有子句，那么显示主句 
					}
					else if (StringUtils.isEmpty(thisText)) {
						return ""; //如果没有主句，那么整句不显示
					}
					else {
						return String.format(next.getFormat(), thisText, nextText);
					}
				case 2: //因果关系 this->next
					if (StringUtils.isEmpty(nextText)) {
						return ""; //无结果那么整句都不显示
					}
					else if (StringUtils.isEmpty(thisText)) {
						return nextText; //无因只有果那么显示结果，忽略原因
					}
					else {
						return String.format(next.getFormat(), thisText, nextText);
					}
				case 3: //包含关系 next->this
					if (StringUtils.isEmpty(thisText)) {
						return thisText; //如果没有子句，那么显示主句 
					}
					else if (StringUtils.isEmpty(nextText)) {
						return ""; //如果没有主句，那么整句不显示
					}
					else {
						return String.format(next.getFormat(), thisText, nextText);
					}
				case 4: //因果关系 next->this
					if (StringUtils.isEmpty(thisText)) {
						return ""; //无结果那么整句都不显示
					}
					else if (StringUtils.isEmpty(nextText)) {
						return nextText; //无因只有果那么显示结果，忽略原因
					}
					else {
						return String.format(next.getFormat(), thisText, nextText);
					}
				}
			}
			else {
				if (this.next instanceof FormatLink) {
					FormatLink next = (FormatLink)this.next;
					if (!StringUtils.isEmpty(thisText) && !StringUtils.isEmpty(nextText)) {
						return String.format(next.getBoth(), thisText, nextText); 
					}
					else if (StringUtils.isEmpty(thisText) && !StringUtils.isEmpty(nextText)) {
						return String.format(next.getRhs(), thisText, nextText); 
					}
					else if (!StringUtils.isEmpty(thisText) && StringUtils.isEmpty(nextText)) {
						return String.format(next.getLhs(), thisText, nextText); 
					}
					else {
						return String.format(next.none, thisText, nextText);
					}
				}
			}
			return "";
		}
	}
	
	public IFieldFormatter getFormatter() {
		return formatter;
	}
	public void setFormatter(IFieldFormatter formatter) {
		this.formatter = formatter;
	}
	public String getColDesc() {
		return colDesc;
	}
	public void setColDesc(String colDesc) {
		this.colDesc = colDesc;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public Double getLast() {
		return last;
	}
	public void setLast(Object last) 
	{
		if (last instanceof Number) {
			this.last = ((Number)last).doubleValue();
		}
	}
	public Double getPre() {
		return pre;
	}
	public void setPre(Object pre) {
		if (pre instanceof Number) {
			this.pre = ((Number)pre).doubleValue();
		}
	}
	
	@Override
	public String toString() {
		return "BondDocField [" + (colDesc != null ? "colDesc=" + colDesc + ", " : "")
				+ (colName != null ? "colName=" + colName + ", " : "") + (last != null ? "last=" + last + ", " : "")
				+ (pre != null ? "pre=" + pre + ", " : "") + (next != null ? "link=" + next + ", " : "")
				+ (formatter != null ? "formatter=" + formatter : "") + "]";
	}
	
}
