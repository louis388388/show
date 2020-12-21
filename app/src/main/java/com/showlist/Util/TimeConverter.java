package com.showlist.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 時間轉換器
 */
public class TimeConverter {

    private SimpleDateFormat inputDateFormat;
    private SimpleDateFormat outputDateFormat;
    private static final float DAY_IN_MILLIS = 86400000f;

    /**
     * @param inputFormat 轉換輸入時間的格式
     * @param outFormat   轉換輸出時間的格式
     * @param inputTimeZone  時區標準 ex: GMT  UTC
     */
    public TimeConverter(String inputFormat, String outFormat, String inputTimeZone) {
        inputDateFormat = new SimpleDateFormat(inputFormat, Locale.getDefault());
        inputDateFormat.setTimeZone(TimeZone.getTimeZone(inputTimeZone));
        outputDateFormat = new SimpleDateFormat(outFormat, Locale.getDefault());
    }

    /**
     * 轉換Time 為特定顯示格式
     *
     * @param timeString 輸入時間字串
     * @return 輸出轉換成字串
     **/
    public String convert(String timeString) {
        try {
            Date date = inputDateFormat.parse(timeString);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}

