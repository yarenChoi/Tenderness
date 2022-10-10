package com.yarenchoi.tenderness.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.yarenchoi.tenderness.BaseApplication;
import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.ui.UIHandler;

/**
 * Created by YarenChoi on 2016/8/11.
 * BaseActivity类
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected final String TAG = this.getClass().getSimpleName();

    protected static UIHandler handler = new UIHandler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d("BaseActivity", getClass().getSimpleName());
        BaseApplication.addActivity(this);
        setHandler();
        initContentView(savedInstanceState);
        setUpView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.removeActivity(this);
    }

    private void setHandler() {
        handler.setHandler(new UIHandler.IHandler() {
            public void handleMessage(Message msg) {
                handler(msg);//有消息就提交给子类实现的方法
            }
        });
    }

    protected void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void back(View view) {
        finish();
    }

    /**
     * 初始化控件
     */
    protected abstract void initContentView(Bundle savedInstanceState);

    /**
     * 设置属性，监听等
     */
    protected abstract void setUpView();

    /**
     * 让子类处理消息
     */
    protected abstract void handler(Message msg);

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}
