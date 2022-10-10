package com.yarenchoi.tenderness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Image;
import com.yarenchoi.tenderness.ui.adapter.PhotoPreviewAdapter;
import com.yarenchoi.tenderness.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class PhotoPreviewActivity extends BaseActivity {

    private static final String IMAGE_LIST = "imageList";
    private static final String POSITION = "position";

    TitleBar titleBar;

    List<Image> imageList;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_photo_preview);
        titleBar = (TitleBar) this.findViewById(R.id.title_bar);
    }

    @Override
    protected void setUpView() {
        imageList = (List<Image>) getIntent().getSerializableExtra(IMAGE_LIST);
        int position = getIntent().getIntExtra(POSITION, 0);

        titleBar.setTitle(position + 1 + "/" + imageList.size());

        ViewPager photoPager = (ViewPager) this.findViewById(R.id.vp_photo_preview);
        photoPager.setAdapter(new PhotoPreviewAdapter(PhotoPreviewActivity.this, imageList));

        photoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                titleBar.setTitle(position + 1 + "/" + imageList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        photoPager.setCurrentItem(position);
    }

    @Override
    protected void handler(Message msg) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageList.clear();
    }

    public static void startActivity(Context context, ArrayList<Image> imageList, int position) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        intent.putExtra(IMAGE_LIST, imageList);
        intent.putExtra(POSITION, position);
        context.startActivity(intent);
    }
}
