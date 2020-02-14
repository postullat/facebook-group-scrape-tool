package com.fbreaper.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {

    public static final String GENERAL_DATE_PATTERN = "dd-M-yyyy hh:mm:ss";

    public static String getCurrentDate(){
        return new SimpleDateFormat(GENERAL_DATE_PATTERN).format(new Date());
    }

    public static Long getUnixTimestamp(){
        return System.currentTimeMillis();
    }

}
