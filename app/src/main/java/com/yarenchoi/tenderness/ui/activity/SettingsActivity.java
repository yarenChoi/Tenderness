package com.yarenchoi.tenderness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.PrefManager;
import com.yarenchoi.tenderness.presenter.ISettingsPresenterImpl;
import com.yarenchoi.tenderness.presenter.ipresenter.ISettingsPresenter;
import com.yarenchoi.tenderness.ui.UIThemeConfig;
import com.yarenchoi.tenderness.ui.activity.iactivity.ISettingsActivity;
import com.yarenchoi.tenderness.utils.GalleryFinalUtils;

public class SettingsActivity extends BaseActivity implements ISettingsActivity {

    private static final int SHOW_PROGRESS_DIALOG = 0;
    private static final int HIDE_PROGRESS_DIALOG = 1;
    private AlertDialog progressDialog;
    SwitchCompat nightSwitch;
    TextView lanTextView;

    private ISettingsPresenter presenter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        nightSwitch = (SwitchCompat) this.findViewById(R.id.sc_night_mode);
        lanTextView = (TextView) this.findViewById(R.id.tv_language);
    }

    @Override
    protected void setUpView() {
        presenter = new ISettingsPresenterImpl(this);
        presenter.showDefaultSettings();

        nightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setNightMode();
            }
        });

    }

    @Override
    protected void handler(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                progressDialog = new AlertDialog.Builder(this)
                        .setView(LayoutInflater.from(this).inflate(R.layout.dlg_progress, null))
                        .setCancelable(false)
                        .create();
                progressDialog.show();
                break;
            case HIDE_PROGRESS_DIALOG:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                exitAndRefresh();
                break;
        }
    }

    private void exitAndRefresh() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void showLanguageDialog(View view) {
        View lanDialog = LayoutInflater.from(this).inflate(R.layout.dlg_language, null);
        RadioGroup radioGroup = (RadioGroup) lanDialog.findViewById(R.id.rg_language);
        switch (PrefManager.getLanguage()) {
            case UIThemeConfig.LANGUAGE_CHINA:
                radioGroup.check(R.id.rb_chinese);
                break;
            case UIThemeConfig.LANGUAGE_ENGLISH:
                radioGroup.check(R.id.rb_english);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_chinese:
                        presenter.setLanguage(UIThemeConfig.LANGUAGE_CHINA);
                        break;
                    case R.id.rb_english:
                        presenter.setLanguage(UIThemeConfig.LANGUAGE_ENGLISH);
                        break;
                }
            }
        });

        progressDialog = new AlertDialog.Builder(this)
                .setView(lanDialog)
                .create();
        progressDialog.show();
    }

    public void cleanCache(View view) {
        GalleryFinalUtils.cleanCache();
        Toast.makeText(SettingsActivity.this, getString(R.string.delete_succeed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDefaultNightMode(boolean nightMode) {
        nightSwitch.setChecked(nightMode);
    }

    @Override
    public void showDefaultLanguage(String lanDesc) {
        lanTextView.setText(lanDesc);
    }

    @Override
    public void showProgressBar() {
        handler.sendEmptyMessage(SHOW_PROGRESS_DIALOG);
    }

    @Override
    public void hideProgressBar() {
        handler.sendEmptyMessageDelayed(HIDE_PROGRESS_DIALOG, 1000);
    }

    @Override
    public boolean getSwitchStatus() {
        return nightSwitch.isChecked();
    }


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
