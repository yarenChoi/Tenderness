package com.yarenchoi.tenderness.model;

import com.yarenchoi.tenderness.BaseApplication;
import com.yarenchoi.tenderness.db.PrefManager;
import com.yarenchoi.tenderness.model.imodel.ISettingsModel;
import com.yarenchoi.tenderness.ui.UIThemeConfig;

/**
 * Created by YarenChoi on 2016/9/5.
 */
public class ISettingsModelImpl implements ISettingsModel {

    @Override
    public boolean getNightMode() {
        return PrefManager.getNightMode();
    }

    @Override
    public String getLanguage() {
        String lanDesc = "";
        switch (PrefManager.getLanguage()) {
            case UIThemeConfig.LANGUAGE_CHINA:
                lanDesc = "简体中文";
                break;
            case UIThemeConfig.LANGUAGE_ENGLISH:
                lanDesc = "English";
        }
        return lanDesc;
    }

    @Override
    public void setNightMode(boolean nightMode) {
        PrefManager.setNightMode(nightMode);
        UIThemeConfig.setNightMode(nightMode);
    }

    @Override
    public void setLanguage(String lan) {
        PrefManager.setLanguage(lan);
        UIThemeConfig.setAppLanguage(BaseApplication.getInstance().getResources(), lan);
    }
}
