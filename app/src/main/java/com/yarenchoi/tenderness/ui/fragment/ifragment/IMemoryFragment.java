package com.yarenchoi.tenderness.ui.fragment.ifragment;

import com.yarenchoi.tenderness.db.entity.Memory;

import java.util.List;

/**
 * Created by YarenChoi on 2016/8/18.
 *
 */
public interface IMemoryFragment {

    /**
     * 停止刷新动画
     */
    void hideRefreshLayout();

    /**
     * 加载记忆列表
     * @param newMemories 从数据库读取的Memory
     */
    void loadMemories(List<Memory> newMemories);
}
