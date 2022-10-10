package com.yarenchoi.tenderness.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yarenchoi.tenderness.db.dao.DaoMaster;

/**
 * Created by YarenChoi on 2016/8/17.
 * 封装DaoMaster.OpenHelper实现数据库升级
 */
public class DBDevOpenHelper extends DaoMaster.OpenHelper {

    public DBDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                //创建新表，注意createTable()是静态方法
                // SchoolDao.createTable(db, true);

                // 加入新字段
                // db.execSQL("ALTER TABLE 'moments' ADD 'audio_path' TEXT;");

                break;
        }
    }
}