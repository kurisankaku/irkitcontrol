package com.kokonote.irkitcontrol.util;

import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by kuriyama on 2014/07/27.
 */
public class DateTimeUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 現在時刻をyyyy-MM-dd HH:mm:ssの形式で取得する
     * @return
     */
    public static String dateFormatNow(){
        return DateFormat.format(DATE_FORMAT, Calendar.getInstance()).toString();
    }
}
