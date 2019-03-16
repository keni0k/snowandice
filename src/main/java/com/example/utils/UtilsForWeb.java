package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsForWeb {

    public static String getDateFormat(Date date){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss a zzz");
        return formatForDateNow.format(date);
    }

}
