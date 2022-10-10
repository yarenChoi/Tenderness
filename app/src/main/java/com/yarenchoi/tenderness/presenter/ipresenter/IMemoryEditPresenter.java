package com.yarenchoi.tenderness.presenter.ipresenter;

/**
 * Created by YarenChoi on 2016/8/23.
 */
public interface IMemoryEditPresenter {

    /**
     * 新建记忆
     */
    void createMemory();

    /**
     * 更新记忆信息
     */
    void updateMemory(Long memoryId);
}
