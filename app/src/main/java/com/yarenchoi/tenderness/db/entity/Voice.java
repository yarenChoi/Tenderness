package com.yarenchoi.tenderness.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by YarenChoi on 2016/8/18.
 * 语音实体类
 */
@Entity
public class Voice {
    @Id
    private Long id;

    /**
     * 语音文件地址
     */
    @NotNull
    private String voiceUrl;

    /**
     * 语音时长
     */
    private float len;

    /**
     * 语音标题
     */
    private String title;

    /**
     * 语音日期
     */
    @NotNull
    private Date date;

    @Generated(hash = 2132524891)
    public Voice(Long id, @NotNull String voiceUrl, float len, String title,
            @NotNull Date date) {
        this.id = id;
        this.voiceUrl = voiceUrl;
        this.len = len;
        this.title = title;
        this.date = date;
    }

    @Generated(hash = 1158611544)
    public Voice() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getVoiceUrl() {
        return this.voiceUrl;
    }

    public void setLen(float len) {
        this.len = len;
    }

    public float getLen() {
        return this.len;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

}
