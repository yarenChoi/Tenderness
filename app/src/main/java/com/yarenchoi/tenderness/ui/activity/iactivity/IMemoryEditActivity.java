package com.yarenchoi.tenderness.ui.activity.iactivity;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by YarenChoi on 2016/8/22.
 */
public interface IMemoryEditActivity {
    void showProgressBar();

    void hideProgressBar();

    void showResult(String result);

    String getMemoryTitle();

    String getMemoryDesc();

    List<PhotoInfo> getPhotoInfoList();
}
