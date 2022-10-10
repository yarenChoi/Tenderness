package com.yarenchoi.tenderness.presenter;

import com.yarenchoi.tenderness.model.IVoiceModelImpl;
import com.yarenchoi.tenderness.model.imodel.IVoiceModel;
import com.yarenchoi.tenderness.presenter.ipresenter.IRecordingPresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.IRecordingActivity;

/**
 * Created by YarenChoi on 2016/8/29.
 * 录音接口实现
 */
public class IRecordingPresenterImpl implements IRecordingPresenter {
    private IVoiceModel model;
    private IRecordingActivity view;

    public IRecordingPresenterImpl(IRecordingActivity iRecordingActivity) {
        this.view = iRecordingActivity;
        model = new IVoiceModelImpl();
    }

    @Override
    public void saveRecording() {
        view.showProgress();
        model.saveRecording(view.getVoiceTitle(), view.getVoiceLen(), view.getVoiceUrl());
//        view.showResult();
        view.hideProgress();
    }
}
