package com.yarenchoi.tenderness.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;

import com.yarenchoi.tenderness.db.PrefManager;

import java.util.Locale;

/**
 * Created by YarenChoi on 2016/8/22.
 * 应用主题配置器
 */
public class UIThemeConfig {

    public static final String LANGUAGE_CHINA = "zh_rCN";
    public static final String LANGUAGE_ENGLISH = "en_US";

    private UIThemeConfig() {
    }

    public static void init(Context context) {

        //设置夜间模式
        setNightMode(PrefManager.getNightMode());
        //设置系统语言
        setAppLanguage(context.getResources(), PrefManager.getLanguage());
    }

    public static void setAppLanguage(Resources resources, String lanAtr) {
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        switch (lanAtr) {
            case LANGUAGE_CHINA:
                config.setLocale(new Locale("zh", "CN"));
                break;
            case LANGUAGE_ENGLISH:
                config.setLocale(new Locale("en", "US"));
                break;
        }
        resources.updateConfiguration(config, dm);
    }

    public static void setNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
