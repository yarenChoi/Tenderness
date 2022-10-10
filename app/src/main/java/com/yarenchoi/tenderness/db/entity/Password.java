package com.yarenchoi.tenderness.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by YarenChoi on 2016/8/18.
 * 密码实体类
 */
@Entity
public class Password {
    @Id
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 更多描述
     */
    private String desc;

    @Generated(hash = 1839761567)
    public Password(Long id, String title, String userName, String pwd, String desc) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.pwd = pwd;
        this.desc = desc;
    }

    @Generated(hash = 565943725)
    public Password() {
    }

    public Long getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(String title) {
        return this.title;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
