package com.yarenchoi.tenderness.ui.activity.iactivity;

/**
 * Created by YarenChoi on 2016/9/5.
 */
public interface ISettingsActivity {

    void showDefaultNightMode(boolean nightMode);

    void showDefaultLanguage(String lanDesc);

    void showProgressBar();

    void hideProgressBar();

    boolean getSwitchStatus();
}
