package com.yarenchoi.tenderness;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.yarenchoi.tenderness.db.DBLoader;
import com.yarenchoi.tenderness.db.PrefManager;
import com.yarenchoi.tenderness.ui.UIThemeConfig;
import com.yarenchoi.tenderness.ui.activity.BaseActivity;
import com.yarenchoi.tenderness.utils.GalleryFinalUtils;
import com.yarenchoi.tenderness.utils.Installation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YarenChoi on 2016/8/18.
 * Application控制器
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    private static Context context;
    private static BaseApplication instance;
    private static String appId;
    public static List<BaseActivity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        //生成标识本应用的唯一ID
        appId = Installation.id(context);
        //初始化应用主题
        UIThemeConfig.init(context);
        //加载数据库
        DBLoader.init(context);
        //初始化GalleryFinal
        GalleryFinalUtils.init(context);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        finishAll();
    }

    /**
     * 获取全局context
     * @return this.context;
     */
    public static Context getContext(){
        return context;
    }

    /**
     * 获取应用实例
     * @return 应用
     */
    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 获取标识应用的唯一ID
     * @return ID
     */
    public static String getAppId() {
        return appId;
    }

    /**
     * 退出整个程序
     * 在这里写释放SDK资源等代码
     */
    public static void finishAll() {
        for (BaseActivity baseActivity : activities) {
            if (!baseActivity.isFinishing()) {
                baseActivity.finish();
            }
        }

        //TODO: 释放SDK的资源

        try {
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addActivity(BaseActivity baseActivity) {
        activities.add(baseActivity);
        Log.i(TAG, "Activity Stack push " + activities.size());
    }

    public static void removeActivity(BaseActivity baseActivity) {
        activities.remove(baseActivity);
        Log.i(TAG, "Activity Stack pop " + activities.size());
    }
}