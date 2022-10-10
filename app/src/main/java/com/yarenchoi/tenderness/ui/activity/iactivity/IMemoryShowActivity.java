package com.yarenchoi.tenderness.ui.activity.iactivity;

import com.yarenchoi.tenderness.db.entity.Memory;

/**
 * Created by YarenChoi on 2016/9/2.
 * 显示memory细节界面接口
 */
public interface IMemoryShowActivity {

    /**
     * 加载记忆信息
     */
    void loadMemory(Memory memory);

    /**
     * 完成删除操作并退出界面
     */
    void finishDeletion();

    /**
     * 查询不到记忆时执行操作
     */
    void error();
}
