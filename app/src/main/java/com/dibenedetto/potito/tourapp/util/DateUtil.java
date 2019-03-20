package com.dibenedetto.potito.tourapp.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    //
    public static String convertDateToString(Date date) {
        //Date now = Calendar.getInstance().getTime();

        return DateFormat.getInstance().format(date); // .getDateInstance(); // .getTimeInstance();
    }

    public static Date convertStringToDate(String stringDate) {
        DateFormat df = DateFormat.getInstance();
        Date date = null;
        try {
            date = df.parse(stringDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getCurrentDateAsString() {
        return DateFormat.getInstance().format(getCurrentDate());
    }
}
