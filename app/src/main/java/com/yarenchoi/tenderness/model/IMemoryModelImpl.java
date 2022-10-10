package com.yarenchoi.tenderness.model;

import com.yarenchoi.tenderness.db.DBLoader;
import com.yarenchoi.tenderness.db.dao.DaoSession;
import com.yarenchoi.tenderness.db.dao.ImageDao;
import com.yarenchoi.tenderness.db.dao.MemoryDao;
import com.yarenchoi.tenderness.db.entity.Image;
import com.yarenchoi.tenderness.db.entity.Memory;
import com.yarenchoi.tenderness.model.imodel.IMemoryModel;

import java.util.Date;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by YarenChoi on 2016/8/18.
 *
 */
public class IMemoryModelImpl implements IMemoryModel {

    public IMemoryModelImpl() {
    }

    @Override
    public List<Memory> loadMemories(int page) {
        DaoSession daoSession = DBLoader.getDaoSession();
        return daoSession
                .getMemoryDao()
                .queryBuilder()
                .orderDesc(MemoryDao.Properties.Id)
                .offset(page * 10)
                .limit(10)
                .list();
    }

    @Override
    public Memory loadMemory(Long memoryId) {
        DaoSession daoSession = DBLoader.getDaoSession();
        return daoSession
                .getMemoryDao()
                .queryBuilder()
                .where(MemoryDao.Properties.Id.eq(memoryId))
                .unique();
    }

    @Override
    public void createMemory(String title, String desc, List<PhotoInfo> photoInfoList) {
        DaoSession daoSession = DBLoader.getDaoSession();
        Memory newMemory = new Memory();
        newMemory.setTitle(title);
        newMemory.setDesc(desc);
        newMemory.setDate(new Date());
        daoSession.getMemoryDao().insert(newMemory);

        for (PhotoInfo photoInfo:photoInfoList) {
            Image image = new Image();
            image.setImgUrl(photoInfo.getPhotoPath());
            image.setMemoryId(newMemory.getId());
            daoSession.getImageDao().insert(image);
        }
    }

    @Override
    public void updateMemory(Long memoryId, String title, String desc, List<PhotoInfo> photoInfoList) {
        DaoSession daoSession = DBLoader.getDaoSession();
        Memory memory = daoSession
                .getMemoryDao()
                .queryBuilder()
                .where(MemoryDao.Properties.Id.eq(memoryId))
                .unique();
        memory.setTitle(title);
        memory.setDesc(desc);
        daoSession.getMemoryDao().update(memory);

        daoSession.getImageDao().deleteInTx(daoSession.getImageDao()._queryMemory_Images(memoryId));
        for (PhotoInfo photoInfo:photoInfoList) {
            Image image = new Image();
            image.setImgUrl(photoInfo.getPhotoPath());
            image.setMemoryId(memoryId);
            daoSession.getImageDao().insert(image);
        }
    }

    @Override
    public void deleteMemory(Long memoryId) {
        DaoSession daoSession = DBLoader.getDaoSession();
        MemoryDao memoryDao = daoSession.getMemoryDao();
        ImageDao imageDao = daoSession.getImageDao();
        memoryDao.deleteByKey(memoryId);
        imageDao.deleteInTx(imageDao._queryMemory_Images(memoryId));
    }
}
