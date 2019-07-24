package com.ouilift.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String dateToString(Date mDate, String sFormat) {
        SimpleDateFormat format = new SimpleDateFormat(sFormat);
        return format.format(mDate);
    }
}
