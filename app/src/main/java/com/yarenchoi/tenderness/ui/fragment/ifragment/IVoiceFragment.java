package com.yarenchoi.tenderness.ui.fragment.ifragment;

import com.yarenchoi.tenderness.db.entity.Voice;

import java.util.List;

/**
 * Created by YarenChoi on 2016/8/30.
 * 显示语言界面接口
 */
public interface IVoiceFragment {

    void showRefreshing();

    void hideRefreshing();

    void loadVoice(List<Voice> voiceList);
}
