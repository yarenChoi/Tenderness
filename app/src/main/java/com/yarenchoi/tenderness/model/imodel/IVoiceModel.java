package com.yarenchoi.tenderness.model.imodel;

import com.yarenchoi.tenderness.db.entity.Voice;

import java.util.List;

/**
 * Created by YarenChoi on 2016/8/30.
 * 录音
 */
public interface IVoiceModel {

    /**
     * 从本地数据库中取出所有Voice
     * @return List<Voice>
     */
    List<Voice> loadVoiceList();

    /**
     * 从本地数据库中取出指定Voice
     * @param voiceId Voice的id
     * @return Voice
     */
    Voice loadVoice(Long voiceId);

    /**
     * 保存录音到本地数据库
     */
    void saveRecording(String title, float len, String voiceUrl);

    /**
     * 删除语音
     * @param voiceId Voice的id
     */
    void deleteVoice(Long voiceId);
}
