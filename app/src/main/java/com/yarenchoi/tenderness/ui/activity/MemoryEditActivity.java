package com.yarenchoi.tenderness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.presenter.IMemoryEditPresenterImpl;
import com.yarenchoi.tenderness.presenter.ipresenter.IMemoryEditPresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.IMemoryEditActivity;
import com.yarenchoi.tenderness.ui.adapter.PhotoListAdapter;
import com.yarenchoi.tenderness.utils.GalleryFinalUtils;
import com.yarenchoi.tenderness.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

public class MemoryEditActivity extends BaseActivity implements IMemoryEditActivity {

    private static final String PHOTO_INFO = "photoInfoList";
    private static final String MEMORY_ID = "memoryId";
    private static final String MEMORY_TITLE = "memoryTitle";
    private static final String MEMORY_DESC = "memoryDesc";
    private static final int SHOW_PROGRESS_DIALOG = 0;
    private static final int HIDE_PROGRESS_DIALOG = 1;

    private Long memoryId;
    private List<PhotoInfo> photoInfoList;
    private PhotoListAdapter adapter;

    private TitleBar titleBar;
    private AlertDialog progressDialog;
    private EditText etMemoryTitle;
    private EditText etMemoryDesc;
    private GridView photoList;
    private AlertDialog alertDialog;

    private IMemoryEditPresenter presenter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_memory_edit);
        titleBar = (TitleBar) this.findViewById(R.id.title_bar);
        etMemoryTitle = (EditText) this.findViewById(R.id.et_memory_title);
        etMemoryDesc = (EditText) this.findViewById(R.id.et_memory_desc);
        photoList = (GridView) this.findViewById(R.id.gv_photo_list);
    }

    @Override
    protected void setUpView() {
        hideSoftKeyboard();
        presenter = new IMemoryEditPresenterImpl(this);

        //根据是否有memoryId判断是编辑操作还是新建操作
        memoryId = getIntent().getLongExtra(MEMORY_ID, -1);
        if (memoryId != -1) {
            //编辑操作

            titleBar.setTitle(getResources().getString(R.string.edit_memory_title));
            titleBar.setRightLayoutClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.updateMemory(memoryId);
                }
            });
            etMemoryTitle.setText(getIntent().getStringExtra(MEMORY_TITLE));
            etMemoryTitle.setSelection(getIntent().getStringExtra(MEMORY_TITLE).length());
            etMemoryDesc.setText(getIntent().getStringExtra(MEMORY_DESC));
        } else {
            //新建操作

            titleBar.setTitle(getResources().getString(R.string.create_memory_title));
            titleBar.setRightLayoutClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.createMemory();
                }
            });
        }

        photoInfoList = (List<PhotoInfo>) getIntent().getSerializableExtra(PHOTO_INFO);
        adapter = new PhotoListAdapter(this, photoInfoList);
        photoList.setAdapter(adapter);
        photoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != photoInfoList.size()) {
                    GalleryFinalUtils.toPhotoPreview(MemoryEditActivity.this, photoInfoList);
                } else {
                    showSelectionDialog();
                }
            }
        });
        photoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != photoInfoList.size()) {
                    deletePhoto(position);
                }
                return true;
            }
        });
    }

    private void deletePhoto(int index) {
        photoInfoList.remove(index);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void handler(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                if (progressDialog == null) {
                    progressDialog = new AlertDialog.Builder(this)
                            .setView(LayoutInflater.from(this).inflate(R.layout.dlg_progress, null))
                            .setCancelable(false)
                            .create();
                }
                progressDialog.show();
                break;
            case HIDE_PROGRESS_DIALOG:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                this.finish();
                break;
        }
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
    public void showResult(String result) {
        Log.d(TAG, result);
    }

    @Override
    public String getMemoryTitle() {
        return etMemoryTitle.getText().toString();
    }

    @Override
    public String getMemoryDesc() {
        return etMemoryDesc.getText().toString();
    }

    @Override
    public List<PhotoInfo> getPhotoInfoList() {
        return this.photoInfoList;
    }

    private void showSelectionDialog() {
        if (alertDialog == null) {
            View dialogView = LayoutInflater.from(MemoryEditActivity.this).inflate(R.layout.dlg_create_memory, null);
            LinearLayout openCameraLayout = (LinearLayout) dialogView.findViewById(R.id.ll_open_camera);
            LinearLayout openGalleryLayout = (LinearLayout) dialogView.findViewById(R.id.ll_open_gallery);
            openCameraLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCamera();
                }
            });
            openGalleryLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openGallery();
                }
            });
            alertDialog = new AlertDialog.Builder(MemoryEditActivity.this)
                    .setView(dialogView)
                    .create();
        }
        alertDialog.show();
    }

    private void hideSelectionDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    private void openCamera() {
        hideSelectionDialog();
        GalleryFinalUtils.openCameraForAddPhoto(MemoryEditActivity.this,
                new GalleryFinalUtils.OnGFCallback() {
                    @Override
                    public void onHanlderSuccess(List<PhotoInfo> resultList) {
                        photoInfoList.addAll(resultList);
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void openGallery() {
        hideSelectionDialog();
        GalleryFinalUtils.openGalleryForAddPhoto(MemoryEditActivity.this,
                new GalleryFinalUtils.OnGFCallback() {
                    @Override
                    public void onHanlderSuccess(List<PhotoInfo> resultList) {
                        photoInfoList.addAll(resultList);
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoInfoList.clear();
        System.gc();
        Log.d(TAG, "photoInfoList.clear()");
    }

    /**
     * 选择图片后进入本界面新建记忆
     * @param context 启动activity的上下文
     * @param photoInfoList 选择好的图片列表（来自GalleryFinal）
     */
    public static void startActivity(Context context,
                                     List<PhotoInfo> photoInfoList,
                                     @Nullable Long memoryId,
                                     @Nullable String title,
                                     @Nullable String desc) {
        Intent intent = new Intent(context, MemoryEditActivity.class);
        intent.putExtra(PHOTO_INFO, (ArrayList<PhotoInfo>) photoInfoList);
        if (memoryId != null) {
            intent.putExtra(MEMORY_ID, memoryId);
            intent.putExtra(MEMORY_TITLE, title);
            intent.putExtra(MEMORY_DESC, desc);
        }
        context.startActivity(intent);
    }
}
