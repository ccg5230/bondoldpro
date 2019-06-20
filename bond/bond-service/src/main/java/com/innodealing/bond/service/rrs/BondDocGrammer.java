package com.innodealing.bond.service.rrs;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

public class BondDocGrammer {

	static public final CurrenyProc CURRENY = new CurrenyProc();
	static public final RatioProc RATIO = new RatioProc();
	static public class NopPreprocessor implements IValuePreprocessor
	{
		@Override
		public Object format(Object value) {
			return value;
		}
	}
	
	static public class CurrenyProc implements IValuePreprocessor
	{
		@Override
		public Object format(Object value) {
			if (value instanceof Double) 
			{
				return (Double)value/10000.00;
			}
			else if (value instanceof BigDecimal) 
			{
				return ((BigDecimal)value).doubleValue()/10000.00;
			}
			return value;
		}
	}
	
	static public class RatioProc implements IValuePreprocessor
	{
		@Override
		public Object format(Object value) {
			if (value instanceof Double) 
			{
				return (Double)value*100;
			}
			return value;
		}
	}

	static public class FdFmt0 implements IFieldFormatter {
		@Override
		public String format(String name, Double pre, Double last) {
			if (last == null) return "";
			return String.format("%1$s%2$.2f", name, last);
		}
	}

	static public class FdFmt1 implements IFieldFormatter {

		@Override
		public String format(String name, Double pre, Double last) {
			if (last == null) return "";
			if (pre == null) 
				return String.format("%1$s%2$.2f", name, last);
			if (last > pre) {
				return String.format("%1$s从%2$.2f上升为%3$.2f", name, pre, last);
			}
			else if (last < pre) {
				return String.format("%1$s从%2$.2f下降为%3$.2f", name, pre, last);
			}
			else {
				return String.format("%1$s为%2$.2f，维持不变", name, last);
			}
		}
	}

	static public class FdFmt2 implements IFieldFormatter {

		@Override
		public String format(String name, Double pre, Double last) {
			if (last == null) return "";
			if (pre == null) 
				return String.format("%1$s%2$.2f", name, last);
			if (last > pre) {
				return String.format("%1$s较上期增加%2$.2f", name, last-pre);
			}
			else if (last < pre) {
				return String.format("%1$s较上期减少%2$.2f", name, pre-last);
			}
			else {
				return new String("维持不变");
			}
		}

	}

	static public class FdFmt3 implements IFieldFormatter {

		@Override
		public String format(String name, Double pre, Double last) {
			if (last == null) return "";
			if (pre == null) 
				return String.format("%1$s%2$.2f", name, last);
			if (last > pre) {
				return String.format("%1$s增加", name);
			}
			else if (last < pre) {
				return String.format("%1$s下调", name);
			}
			else {
				return String.format("%1$s为%2$.2f", name, last);
			}
		}
	}

	static public class FdFmt4 implements IFieldFormatter {

		@Override
		public String format(String name, Double pre, Double last) {
			if (last == null) return "";
			if (pre == null) 
				return String.format("%1$s%2$.2f", name, last);
			if (last > pre) {
				return String.format("%1$s%2$.2f, 较上期增加%3$.2f", name, last, last-pre);
			}
			else if (last < pre) {
				return String.format("%1$s%2$.2f, 较上期减少%3$.2f", name, last, pre-last);
			}
			else {
				return String.format("%1$s%2$.2f, 较上期基本不变", name, last);
			}
		}
	}

	static public class FdFmt5 implements IFieldFormatter {

		@Override
		public String format(String name, Double pre, Double last) {
			if (last == null) return "";
			if (pre == null) 
				return String.format("%1$s%2$.2f", name);
			if (last > pre) {
				return String.format("%1$s%2$.2f, 较上期增加", name);
			}
			else if (last < pre) {
				return String.format("%1$s%2$.2f, 较上期减少", name);
			}
			else {
				return String.format("%1$s%2$.2f, 较上期基本不变", name);
			}
		}
	}

	static public class FdFmt6 implements IFieldFormatter {

		@Override
		public String format(String name, Double pre, Double last) {
			if (last == null) return "";
			if (pre == null) 
				return String.format("%1$s%2$.2f", name, last);
			if (last > pre) {
				return String.format("%1$s上升为%3$.2f", name, pre, last);
			}
			else if (last < pre) {
				return String.format("%1$s下降为%3$.2f", name, pre, last);
			}
			else {
				return String.format("%1$s为%2$.2f，维持不变", name, last);
			}
		}
	}

	static public class FdFmt implements IFieldFormatter {
		
		static final FdFmt DEFAULT_SCORE_FMT = new FdFmt("上升%4$.3f", "下降%4$.3f","不变", "数据缺失，无法计算", "数据缺失，无法计算");;
		String up;
		String down;
		String unchange;
		String nolast;
		String nopre;

		FdFmt(String up, String down, String unchange, String nolast, String nopre)
		{
			this.up = up;
			this.down = down;
			this.unchange = unchange;
			this.nopre = nopre;
			this.nolast = nolast;
		}

		@Override
		public String format(String name, Double pre, Double last) {
			if (last == null) 
				return String.format(nolast, name, pre, last, null);
			if (pre == null) 
				return String.format(nopre, name, pre, last, null);
			if (last > pre) 
				return String.format(up, name, pre, last, last-pre);
			else if (last < pre) 
				return String.format(down, name, pre, last, pre-last);
			else 
				return String.format(unchange, name, pre, last);
		}
	}

	static public class GrammaticLink implements IDocFieldLink {

		// ${this}; ${next}     coordination
		// ${this}。 ${next}     coordination
		// ${this}, ${next}     coordination
		// ${this}, 同时${next}  coordination
		// ${this}, 其中${next}  include 
		// ${this}, 故${next}   deduce
		// ${this}, 因此${next}  deduce
		// ${this}, 所以${next}  deduce
		// ${this}, 使得${next}  deduce
		// ${this}, 导致${next}  deduce
		// 由于${this}, ${next}  deduce
		String format;   

		public GrammaticLink(String format, Integer connType, BondDocField field) {
			super();
			this.format = format;
			this.connType = connType;
			this.field = field;
		}

		//coordination:0
		//include: 1
		//deduce:2
		Integer connType; 

		BondDocField field;

		public BondDocField getField() {
			return field;
		}
		public void setField(BondDocField field) {
			this.field = field;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public Integer getConnType() {
			return connType;
		}
		public void setConnType(Integer connType) {
			this.connType = connType;
		}
	}

	static public class FormatLink implements IDocFieldLink {

		String both;
		String lhs;
		String rhs;
		String none;   
		BondDocField field;

		public FormatLink(String both, String lhs, String rhs, String none, BondDocField field) {
			super();
			this.both = both;
			this.lhs = lhs;
			this.rhs = rhs;
			this.none = none;
			this.field = field;
		}

		public String getBoth() {
			return both;
		}

		public void setBoth(String both) {
			this.both = both;
		}

		public String getLhs() {
			return lhs;
		}

		public void setLhs(String lhs) {
			this.lhs = lhs;
		}

		public String getRhs() {
			return rhs;
		}

		public void setRhs(String rhs) {
			this.rhs = rhs;
		}

		public String getNone() {
			return none;
		}

		public void setNone(String none) {
			this.none = none;
		}

		public void setField(BondDocField field) {
			this.field = field;
		}

		public BondDocField getField() {
			return field;
		}

	}
}
