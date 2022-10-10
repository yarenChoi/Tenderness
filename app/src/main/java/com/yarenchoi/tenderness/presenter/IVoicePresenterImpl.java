package com.yarenchoi.tenderness.presenter;

import com.yarenchoi.tenderness.model.IVoiceModelImpl;
import com.yarenchoi.tenderness.model.imodel.IVoiceModel;
import com.yarenchoi.tenderness.presenter.ipresenter.IVoicePresenter;
import com.yarenchoi.tenderness.ui.fragment.ifragment.IVoiceFragment;

/**
 * Created by YarenChoi on 2016/8/30.
 * 显示语音界面适配器接口实现
 */
public class IVoicePresenterImpl implements IVoicePresenter {

    private IVoiceFragment view;
    private IVoiceModel model;

    public IVoicePresenterImpl(IVoiceFragment view) {
        this.view = view;
        model = new IVoiceModelImpl();
    }

    @Override
    public void loadVoiceList() {
//        view.showRefreshing();
        view.loadVoice(model.loadVoiceList());
        view.hideRefreshing();
    }
}
