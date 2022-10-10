package com.yarenchoi.tenderness.ui.activity.iactivity;

import com.yarenchoi.tenderness.db.entity.Voice;

/**
 * Created by YarenChoi on 2016/9/6.
 * 播放语音界面接口
 */
public interface IPlayVoiceActivity {

    /**
     * 初始化语音
     * @param voice 从数据库中取出的语音对象
     */
    void initVoice(Voice voice);

    /**
     * 显示语音的标题
     * @param title 从数据库中取出的语音标题
     */
    void showVoiceTitle(String title);

    /**
     * 显示语音的日期
     * @param date 从数据库中取出的语音日期
     */
    void showVoiceDate(String date);

    /**
     * 显示语音的时长
     * @param len 从数据库中取出的语音时长
     */
    void showVoiceLen(String len);
}
