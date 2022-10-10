package com.yarenchoi.tenderness.presenter.ipresenter;

/**
 * Created by YarenChoi on 2016/9/5.
 * 应用设置适配器接口
 */
public interface ISettingsPresenter {

    /**
     * 显示默认的设置
     */
    void showDefaultSettings();

    /**
     * 设置夜间模式
     */
    void setNightMode();

    /**
     * 设置语言环境
     *
     */
    void setLanguage(String lan);

}
