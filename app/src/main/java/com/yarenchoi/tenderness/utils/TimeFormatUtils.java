package com.yarenchoi.tenderness.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YarenChoi on 2016/8/24.
 * 日期格式化工具类
 */
public class TimeFormatUtils {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat allFormat = new SimpleDateFormat("yyyyMMddHHmmSS");

    private TimeFormatUtils(){
    }

    public static String getFormatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String getFormatTime(Date date) {
        return timeFormat.format(date);
    }

    public static String getFormatFileName() {
        Log.d("TimeFormatUtils", "file name" + allFormat.format(new Date()));
        return allFormat.format(new Date());
    }
}
