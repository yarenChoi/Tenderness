package com.yarenchoi.tenderness.ui.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.ui.activity.BaseActivity;
import com.yarenchoi.tenderness.utils.GlideLoaderUtils;

import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.adapter.ViewHolderAdapter;

/**
 * Created by YarenChoi on 2016/9/3.
 * 继承于galleryFinal中的通用适配器
 * 用于显示memory编辑界面的图片列表(GirdView)
 */
public class PhotoListAdapter extends ViewHolderAdapter<PhotoListAdapter.PhotoViewHolder, PhotoInfo> {
    private BaseActivity mActivity;

    public PhotoListAdapter(BaseActivity activity, List<PhotoInfo> list) {
        super(activity, list);
        mActivity = activity;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflate(R.layout.item_photo, parent);
        return new PhotoViewHolder(view);
    }

    @Override
    public int getCount() {
        return getDatas().size() + 1;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {

        if (position == getDatas().size() || getDatas().size() == 0) {
            GlideLoaderUtils.loadAddPhotoHint(mActivity, holder.photo);
        } else {
            PhotoInfo photoInfo = getItem(position);

            String path = "";
            if (photoInfo != null) {
                path = photoInfo.getPhotoPath();
            }

            GlideLoaderUtils.loadImage(mActivity, path, holder.photo);
            holder.mView.setAnimation(AnimationUtils.loadAnimation(mActivity, GalleryFinal.getCoreConfig().getAnimation()));
        }
    }

    public class PhotoViewHolder extends ViewHolderAdapter.ViewHolder {

        public ImageView photo;
        public View mView;

        public PhotoViewHolder(View view) {
            super(view);
            mView = view;
            photo = (ImageView) view.findViewById(R.id.siv_photo);
        }
    }
}
