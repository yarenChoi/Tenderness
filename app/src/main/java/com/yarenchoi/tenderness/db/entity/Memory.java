package com.yarenchoi.tenderness.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.DaoException;
import com.yarenchoi.tenderness.db.dao.DaoSession;
import com.yarenchoi.tenderness.db.dao.MemoryDao;
import com.yarenchoi.tenderness.db.dao.ImageDao;

/**
 * Created by YarenChoi on 2016/8/17.
 * 记忆实体类
 */
@Entity
public class Memory {
    @Id
    private Long id;

    /**
     * 与Image实体类一对多关系
     * 外键约束
     */
    @ToMany(referencedJoinProperty = "memoryId")
    private List<Image> images;

    /**
     * 标题
     */
    private String title;

    /**
     * 记忆描述
     */
    private String desc;

    /**
     * 记录日期
     */
    @NotNull
    private Date date;

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 604059028)
    public synchronized void resetImages() {
        images = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1958279539)
    public List<Image> getImages() {
        if (images == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ImageDao targetDao = daoSession.getImageDao();
            List<Image> imagesNew = targetDao._queryMemory_Images(id);
            synchronized (this) {
                if(images == null) {
                    images = imagesNew;
                }
            }
        }
        return images;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1885479955)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMemoryDao() : null;
    }

    /** Used for active entity operations. */
    @Generated(hash = 175584621)
    private transient MemoryDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1199686939)
    public Memory(Long id, String title, String desc, @NotNull Date date) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    @Generated(hash = 884616065)
    public Memory() {
    }
}
