package com.yarenchoi.tenderness.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yarenchoi.tenderness.db.dao.DaoMaster;
import com.yarenchoi.tenderness.db.dao.DaoSession;

/**
 * Created by YarenChoi on 2016/8/18.
 * 数据库加载器
 */
public class DBLoader {
    private static final String DATABASE_NAME = "Tenderness-db";
    private static DaoSession daoSession;

    private DBLoader() {
    }

    public static void init(Context context) {
        DBDevOpenHelper dbDevOpenHelper = new DBDevOpenHelper(context, DATABASE_NAME, null);
        SQLiteDatabase db = dbDevOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
