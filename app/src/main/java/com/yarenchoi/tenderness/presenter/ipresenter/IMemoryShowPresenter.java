package com.yarenchoi.tenderness.presenter.ipresenter;

/**
 * Created by YarenChoi on 2016/9/2.
 * 显示memory界面适配器接口
 */
public interface IMemoryShowPresenter {

    /**
     * 加载记忆信息
     * @param memoryId ID
     */
    void loadMemory(Long memoryId);

    /**
     * 删除记忆
     * @param memoryId ID
     */
    void deleteMemory(Long memoryId);
}
