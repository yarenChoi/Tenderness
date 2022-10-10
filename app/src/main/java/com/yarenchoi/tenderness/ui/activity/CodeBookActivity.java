package com.yarenchoi.tenderness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;

import com.yarenchoi.tenderness.R;

public class CodeBookActivity extends BaseActivity {

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_code_book);

    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void handler(Message msg) {
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CodeBookActivity.class));
    }
}
