package com.yarenchoi.tenderness.ui.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Image;
import com.yarenchoi.tenderness.ui.activity.BaseActivity;
import com.yarenchoi.tenderness.utils.GlideLoaderUtils;
import com.yarenchoi.tenderness.utils.ScreenUtils;

import java.util.List;

import cn.finalteam.galleryfinal.adapter.ViewHolderRecyclingPagerAdapter;
import cn.finalteam.galleryfinal.widget.zoonview.PhotoView;

/**
 * Created by YarenChoi on 2016/9/12.
 * PreviewAdapter
 */
public class PhotoPreviewAdapter extends ViewHolderRecyclingPagerAdapter<PhotoPreviewAdapter.PreviewViewHolder, Image> {

    private Context mContext;
    private DisplayMetrics mDisplayMetrics;

    public PhotoPreviewAdapter(Context context, List<Image> imageList) {
        super(context, imageList);
        this.mContext = context;
        this.mDisplayMetrics = ScreenUtils.getScreenMetrics(context);
    }

    @Override
    public PreviewViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = getLayoutInflater().inflate(R.layout.item_photo_preview, null);
        return new PreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreviewViewHolder holder, int position) {
        Image image = getDatas().get(position);
        String path = "";
        if (image != null) {
            path = image.getImgUrl();
        }
        GlideLoaderUtils.loadImageWithoutPlaceholder((BaseActivity) mContext,
                path,
                holder.mImageView,
                mDisplayMetrics.widthPixels/2,
                mDisplayMetrics.heightPixels/2);
    }

    static class PreviewViewHolder extends ViewHolderRecyclingPagerAdapter.ViewHolder{
        PhotoView mImageView;
        public PreviewViewHolder(View view) {
            super(view);
            mImageView = (PhotoView) view.findViewById(R.id.pv_preview);
        }
    }
}
