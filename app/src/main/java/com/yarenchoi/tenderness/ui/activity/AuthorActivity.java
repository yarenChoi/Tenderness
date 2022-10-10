package com.yarenchoi.tenderness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.ui.activity.iactivity.IAuthorActivity;
import com.yarenchoi.tenderness.utils.GlideLoaderUtils;

public class AuthorActivity extends BaseActivity implements IAuthorActivity {

    ImageView avatar;
    TextView name;
    TextView desc;
    TextView contact;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_author);
        avatar = (ImageView) this.findViewById(R.id.iv_avatar);
        name = (TextView) this.findViewById(R.id.tv_name);
        desc = (TextView) this.findViewById(R.id.tv_desc);
        contact = (TextView) this.findViewById(R.id.tv_contact);
    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void handler(Message msg) {
    }

    @Override
    public void showAvatar(String imgUrl) {
        // TODO: 2016/9/4 加载头像
        GlideLoaderUtils.loadAvatar(AuthorActivity.this, imgUrl, avatar);
    }

    @Override
    public void showName(String name) {
        this.name.setText(name);
    }

    @Override
    public void showDesc(String desc) {
        this.desc.setText(desc);
    }

    @Override
    public void showContact(String contact) {
        this.contact.setText(contact);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AuthorActivity.class));
    }
}
