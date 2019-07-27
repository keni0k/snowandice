package com.example.utils;

import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URLParsingSettings;
import org.joda.time.LocalTime;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Utils {

    public static String randomToken(int length) {
        final String mCHAR = "qwertyuioplkjhgfdsazxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(mCHAR.length());
            char ch = mCHAR.charAt(number);
            builder.append(ch);
        }

        return builder.toString();
    }

    public String getTime(){
        String time = new LocalTime().toDateTimeToday().toString().replace('T', ' ');
        time = time.substring(0, time.indexOf('.'));
        return time;
    }

    public static String getDateFormat(Date date){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yy hh:mm a zzz");
        return formatForDateNow.format(date);
    }

    public static URI getUrl(String url) throws GalimatiasParseException, URISyntaxException {
        URLParsingSettings settings = URLParsingSettings.create();
        io.mola.galimatias.URL myUrl = io.mola.galimatias.URL.parse(settings, url);
        return myUrl.toJavaURI();
    }

}
