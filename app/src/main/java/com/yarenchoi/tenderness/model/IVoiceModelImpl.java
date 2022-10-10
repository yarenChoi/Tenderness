package com.yarenchoi.tenderness.model;

import com.yarenchoi.tenderness.db.DBLoader;
import com.yarenchoi.tenderness.db.dao.DaoSession;
import com.yarenchoi.tenderness.db.dao.VoiceDao;
import com.yarenchoi.tenderness.db.entity.Voice;
import com.yarenchoi.tenderness.model.imodel.IVoiceModel;

import java.util.Date;
import java.util.List;

/**
 * Created by YarenChoi on 2016/8/30.
 * 录音
 */
public class IVoiceModelImpl implements IVoiceModel {

    @Override
    public List<Voice> loadVoiceList() {
        DaoSession daoSession = DBLoader.getDaoSession();
        return daoSession
                .getVoiceDao()
                .queryBuilder()
                .orderDesc(VoiceDao.Properties.Id)
                .list();
    }

    @Override
    public Voice loadVoice(Long voiceId) {
        DaoSession daoSession = DBLoader.getDaoSession();
        return daoSession
                .getVoiceDao()
                .queryBuilder()
                .where(VoiceDao.Properties.Id.eq(voiceId))
                .unique();
    }

    @Override
    public void saveRecording(String title, float len, String voiceUrl) {
        DaoSession daoSession = DBLoader.getDaoSession();
        Voice newVoice = new Voice();
        newVoice.setTitle(title);
        newVoice.setLen(len);
        newVoice.setVoiceUrl(voiceUrl);
        newVoice.setDate(new Date());
        daoSession.getVoiceDao().insert(newVoice);
    }

    @Override
    public void deleteVoice(Long voiceId) {
        DBLoader.getDaoSession()
                .getVoiceDao()
                .deleteByKey(voiceId);
    }
}
