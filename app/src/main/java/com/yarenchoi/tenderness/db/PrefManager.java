package com.yarenchoi.tenderness.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.yarenchoi.tenderness.BaseApplication;
import com.yarenchoi.tenderness.ui.UIThemeConfig;

/**
 * Created by YarenChoi on 2016/8/22.
 * SharedPreferences
 */
public class PrefManager {
    private static final SharedPreferences preferences = BaseApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = preferences.edit();

    private static final String LANGUAGE = "language";
    private static final String NIGHT_MODE = "nightMode";

    public static void setLanguage(String language) {
        editor.putString(LANGUAGE, language).commit();
    }

    public static String getLanguage() {
        return preferences.getString(LANGUAGE, UIThemeConfig.LANGUAGE_CHINA);
    }

    public static void setNightMode(boolean nightMode) {
        editor.putBoolean(NIGHT_MODE, nightMode).commit();
    }

    public static boolean getNightMode() {
        return preferences.getBoolean(NIGHT_MODE, false);
    }
}
