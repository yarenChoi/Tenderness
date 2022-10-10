package com.yarenchoi.tenderness.model.imodel;

import com.yarenchoi.tenderness.db.entity.Memory;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by YarenChoi on 2016/8/18.
 *
 */
public interface IMemoryModel {
    List<Memory> loadMemories(int page);

    Memory loadMemory(Long memoryId);

    void createMemory(String title, String desc, List<PhotoInfo> photoInfoList);

    void updateMemory(Long memoryId, String title, String desc, List<PhotoInfo> photoInfoList);

    void deleteMemory(Long memoryId);
}
