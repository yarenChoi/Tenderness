package com.yarenchoi.tenderness.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.widget.Toast;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.ui.activity.MemoryEditActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.PhotoPreviewActivity;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by YarenChoi on 2016/8/20.
 * GalleryFinal工具类
 */
public class GalleryFinalUtils {
    private static final int REQUEST_OPEN_CAMERA = 13;
    private static final int REQUEST_OPEN_GALLERY = 14;
    private static final int SELECT_MAX_SIZE = 9;
    private static final String PHOTO_LIST = "photo_list";

    public static void init(Context context) {
        //设置主题
        ThemeConfig theme = new ThemeConfig.Builder()
                //Color.rgb(0x38, 0x42, 0x48)
                .setTitleBarBgColor(context.getResources().getColor(R.color.colorPrimary))
                .setFabNornalColor(context.getResources().getColor(R.color.colorPrimary))
                .setFabPressedColor(context.getResources().getColor(R.color.colorPrimaryDark))//Color.rgb(0x20, 0x25, 0x28)
                .setCheckSelectedColor(context.getResources().getColor(R.color.colorPrimary))
                .setCropControlColor(context.getResources().getColor(R.color.colorPrimary))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableEdit(true)//编辑
                .setEnableCrop(true)//裁剪
                .setEnableRotate(true)//旋转
                .setCropSquare(true)//裁剪正方形
                .setEnablePreview(true)//预览
                .build();
        //配置ImageLoader与文件目录
        ImageLoader imageloader = new GlideLoaderUtils.GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                .setFunctionConfig(functionConfig)
                //配置编辑（裁剪和旋转）功能产生的cache文件保存目录
                .setEditPhotoCacheFolder(new File(Environment.getExternalStorageDirectory(),
                        "/Tenderness/" + "galleryFinalCache/"))
                //设置拍照保存目录
                .setTakePhotoFolder(new File(Environment.getExternalStorageDirectory(),
                        "/Tenderness/" + "TDNSphyto/"))
                .build();
        GalleryFinal.init(coreConfig);
    }

    public static void openCameraForCreateMemory(final Context context) {
        GalleryFinal.openCamera(REQUEST_OPEN_CAMERA, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                MemoryEditActivity.startActivity(context, resultList, null, null, null);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void openGalleryForCreateMemory(final Context context) {
        GalleryFinal.openGalleryMuti(REQUEST_OPEN_GALLERY, SELECT_MAX_SIZE, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                MemoryEditActivity.startActivity(context, resultList, null, null, null);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void openCameraForAddPhoto(final Context context, final OnGFCallback listener) {
        GalleryFinal.openCamera(REQUEST_OPEN_CAMERA, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                listener.onHanlderSuccess(resultList);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void openGalleryForAddPhoto(final Context context, final OnGFCallback listener) {
        GalleryFinal.openGalleryMuti(REQUEST_OPEN_GALLERY, SELECT_MAX_SIZE, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                listener.onHanlderSuccess(resultList);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void toPhotoPreview(Context context, List<PhotoInfo> mSelectPhotoMap) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        intent.putExtra(PHOTO_LIST, (ArrayList<PhotoInfo>) mSelectPhotoMap);
        context.startActivity(intent);
    }

    public static void cleanCache() {
        GalleryFinal.cleanCacheFile();
    }

    public interface OnGFCallback {
        void onHanlderSuccess(List<PhotoInfo> resultList);
//        void onHanlderFailure(int requestCode, String errorMsg);
    }
}
