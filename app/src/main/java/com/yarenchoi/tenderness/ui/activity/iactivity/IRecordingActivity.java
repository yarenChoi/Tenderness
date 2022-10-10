package com.yarenchoi.tenderness.ui.activity.iactivity;

/**
 * Created by YarenChoi on 2016/8/29.
 * 录音界面接口
 */
public interface IRecordingActivity {

    void showProgress();

    void hideProgress();

    void showResult(String result);

    String getVoiceTitle();

    float getVoiceLen();

    String getVoiceUrl();

}
