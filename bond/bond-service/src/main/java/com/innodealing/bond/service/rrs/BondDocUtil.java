package com.innodealing.bond.service.rrs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import static java.lang.Math.min;

public class BondDocUtil  
{
	private static final Logger LOG = LoggerFactory.getLogger(BondDocUtil.class);
	
	static public String listFieldsByOrder(List<BondDocField> causes, Integer count, Double direction)
	{
		if (direction.equals(0)) return "";
		
		String results = "";
		List<BondDocField> fields = findFieldsByOrder(causes, count, direction);
		for(BondDocField f: fields) {
			if (!StringUtils.isEmpty(results))
				results += "，";
			results += f.getColDesc();
		}
		return results;
	}
	
	static private List<BondDocField> findFieldsByOrder(List<BondDocField> causes, Integer count, Double direction) 
	{
		causes = removeNullDocField(causes);
		causes.sort(new BondDocFieldRiseCompare());
		if (direction < 0) {
			Collections.reverse(causes);
		}
		List<BondDocField> newFields = new ArrayList<BondDocField>();
		for (BondDocField f : causes) {
			Double diff = f.getLast() - f.getPre();
			LOG.info("findFieldsByOrder, field:" + f.toString() + ", diff:" + diff);
			if (direction > 0) {
				if (diff > 0) 
					newFields.add(f);
			}
			else if (direction < 0) {
				if (diff < 0) 
					newFields.add(f);
			}
		}
		return newFields.subList(0, min(newFields.size(), count));
	}
	
	static private List<BondDocField> removeNullDocField(List<BondDocField> fields)
	{
		List<BondDocField> newFields = new ArrayList<BondDocField>();
		for(BondDocField f : fields) {
			if (f.getLast() != null && f.getPre() != null)
				newFields.add(f);
		}
		return newFields;
	}
	
	static private class BondDocFieldRiseCompare implements Comparator<BondDocField>  {
		@Override
		public int compare(BondDocField o1, BondDocField o2) {

			if (o1 == null || o2 == null)
				throw new NullPointerException();
			
			if ((o1.getLast() == null || o1.getPre() == null)
					&& ( o2.getLast() != null && o2.getPre() != null))
				return -1;	
			if ((o2.getLast() == null || o2.getPre() == null)
					&& ( o1.getLast() != null && o1.getPre() != null))
				return 1;
			if ((o2.getLast() == null || o2.getPre() == null)
					&& ( o1.getLast() == null && o1.getPre() == null))
				return 0;
			
			Double ret = (o1.getLast() - o1.getPre()) - (o2.getLast() - o2.getPre());
			return (ret > 0)? 1 : ((ret < 0)? -1 : 0);              
		}
	}
}
