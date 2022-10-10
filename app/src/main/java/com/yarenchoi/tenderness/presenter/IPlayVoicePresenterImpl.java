package com.yarenchoi.tenderness.presenter;

import com.yarenchoi.tenderness.db.entity.Voice;
import com.yarenchoi.tenderness.model.IVoiceModelImpl;
import com.yarenchoi.tenderness.model.imodel.IVoiceModel;
import com.yarenchoi.tenderness.presenter.ipresenter.IPlayVoicePresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.IPlayVoiceActivity;
import com.yarenchoi.tenderness.utils.TimeFormatUtils;

/**
 * Created by YarenChoi on 2016/9/6.
 * 播放语音界面适配器实例对象
 */
public class IPlayVoicePresenterImpl implements IPlayVoicePresenter {

    IPlayVoiceActivity view;
    IVoiceModel model;

    public IPlayVoicePresenterImpl(IPlayVoiceActivity view) {
        this.view = view;
        this.model = new IVoiceModelImpl();
    }

    @Override
    public void loadVoice(Long voiceId) {
        Voice voice = model.loadVoice(voiceId);
        view.initVoice(voice);
        view.showVoiceTitle(voice.getTitle());
        view.showVoiceDate(TimeFormatUtils.getFormatDate(voice.getDate()));
        view.showVoiceLen(String.valueOf(Math.round(voice.getLen())) + "\"");
    }
}
