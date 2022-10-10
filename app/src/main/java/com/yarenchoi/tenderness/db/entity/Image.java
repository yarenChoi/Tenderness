package com.yarenchoi.tenderness.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;

/**
 * Created by YarenChoi on 2016/8/18.
 * 图片信息实体类
 */
@Entity
public class Image implements Serializable {
    @Id
    private Long id;

    /**
     * 图片文件地址
     */
    private String imgUrl;

    private Long memoryId;

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemoryId() {
        return this.memoryId;
    }

    public void setMemoryId(Long memoryId) {
        this.memoryId = memoryId;
    }

    @Generated(hash = 1362784467)
    public Image(Long id, String imgUrl, Long memoryId) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.memoryId = memoryId;
    }

    @Generated(hash = 1590301345)
    public Image() {
    }

}
