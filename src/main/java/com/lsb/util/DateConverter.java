package com.lsb.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static Date toDate(Long ts) {
        return new Date((long) ts * 1000);
    }

    public static String toString(Date d) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(d);
    }
    
}
