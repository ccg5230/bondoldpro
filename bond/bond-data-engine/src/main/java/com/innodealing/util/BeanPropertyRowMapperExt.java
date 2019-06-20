package com.innodealing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;

import com.innodealing.model.dm.bond.ccxe.BondFinFalBalaTafbb;

public class BeanPropertyRowMapperExt<T> extends BeanPropertyRowMapper<T> {

    public BeanPropertyRowMapperExt(Class<T> mappedClass) {
        super(mappedClass);
    }
    
    protected String underscoreName(String name) {
        
        Pattern pattern = Pattern.compile("([a-z]+?)([0-9]+)");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find())
        {
            //System.out.println(matcher.group(1));
            //System.out.println(matcher.group(2));
            return matcher.group(1) + "_"  + matcher.group(2);
        }
        
        return super.underscoreName(name);
    }
    
    public static void main(String[] args) {
        new BeanPropertyRowMapperExt<BondFinFalBalaTafbb>(BondFinFalBalaTafbb.class);
    }

}
