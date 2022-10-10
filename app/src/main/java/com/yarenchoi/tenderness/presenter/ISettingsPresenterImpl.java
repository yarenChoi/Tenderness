package com.yarenchoi.tenderness.presenter;

import com.yarenchoi.tenderness.model.ISettingsModelImpl;
import com.yarenchoi.tenderness.model.imodel.ISettingsModel;
import com.yarenchoi.tenderness.presenter.ipresenter.ISettingsPresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.ISettingsActivity;

/**
 * Created by YarenChoi on 2016/9/5.
 */
public class ISettingsPresenterImpl implements ISettingsPresenter {

    ISettingsActivity view;
    ISettingsModel model;

    public ISettingsPresenterImpl(ISettingsActivity view) {
        this.view = view;
        model = new ISettingsModelImpl();
    }

    @Override
    public void showDefaultSettings() {
        view.showDefaultNightMode(model.getNightMode());
        view.showDefaultLanguage(model.getLanguage());
    }

    @Override
    public void setNightMode() {
        view.showProgressBar();
        model.setNightMode(view.getSwitchStatus());
        view.hideProgressBar();
    }

    @Override
    public void setLanguage(String lan) {
        view.showProgressBar();
        model.setLanguage(lan);
        view.hideProgressBar();
    }
}
