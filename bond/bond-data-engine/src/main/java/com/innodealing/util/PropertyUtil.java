package com.innodealing.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

public class PropertyUtil {
    public static String getPropertyAsString(Properties prop) {    
        StringWriter writer = new StringWriter();
        prop.list(new PrintWriter(writer));
        return writer.getBuffer().toString();
      }
}
