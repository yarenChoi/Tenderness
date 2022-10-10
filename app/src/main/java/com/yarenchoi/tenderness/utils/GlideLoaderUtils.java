package com.yarenchoi.tenderness.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.ui.activity.BaseActivity;

import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by YarenChoi on 2016/8/22.
 * 图片加载器工具类
 */
public class GlideLoaderUtils {

    public static void loadImage(BaseActivity activity, String path, ImageView imageView) {
        Glide.with(activity)
                .load("file://" + path)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void loadAddPhotoHint(Context context, ImageView imageView) {
        Glide.with(context)
                .load(R.mipmap.ic_done)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void loadAvatar(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.vito)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transform(new CircleTransform(context))
                .into(imageView);
    }

    public static void loadImageWithoutPlaceholder(BaseActivity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load("file://" + path)
                .error(R.drawable.image_default)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void loadImageWithoutMemoryCache(BaseActivity activity, String path, ImageView imageView) {
        Glide.with(activity)
                .load("file://" + path)
                .error(R.drawable.image_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void loadImageWithMemoryCache(Context context, String path, ImageView imageView, int width, int height) {
        Glide.with(context)
                .load("file://" + path)
                .error(R.drawable.image_default)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * Created by yarenChoi on 2016/6/2.
     * 圆形头像
     */
    private static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }

    public static class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
            Glide.with(activity)
                    .load("file://" + path)
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
//                    .centerCrop()
                    .into(new ImageViewTarget<GlideDrawable>(imageView) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            imageView.setImageDrawable(resource);
                        }

                        @Override
                        public void setRequest(Request request) {
                            imageView.setTag(R.id.gallery_final_item_tag_key,request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request) imageView.getTag(R.id.gallery_final_item_tag_key);
                        }
                    });
        }

        @Override
        public void clearMemoryCache() {
        }
    }
}
