package com.yarenchoi.tenderness.model.imodel;

/**
 * Created by YarenChoi on 2016/9/5.
 */
public interface ISettingsModel {

    boolean getNightMode();

    String getLanguage();

    void setNightMode(boolean nightMode);

    void setLanguage(String lan);
}
