package com.yarenchoi.tenderness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Image;
import com.yarenchoi.tenderness.db.entity.Memory;
import com.yarenchoi.tenderness.presenter.IMemoryShowPresenterImpl;
import com.yarenchoi.tenderness.presenter.ipresenter.IMemoryShowPresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.IMemoryShowActivity;
import com.yarenchoi.tenderness.ui.adapter.MemoryDetailAdapter;
import com.yarenchoi.tenderness.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

public class MemoryShowActivity extends BaseActivity implements IMemoryShowActivity {

    private static final String MEMORY_ID = "memoryId";

    Long memoryId;
    Memory memory;

    TitleBar titleBar;
    RecyclerView memoryView;
    AlertDialog delDialog;
    IMemoryShowPresenter presenter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_memory_show);
        titleBar = (TitleBar) this.findViewById(R.id.title_bar);
        memoryView = (RecyclerView) this.findViewById(R.id.rv_memory_detail);
    }

    @Override
    protected void setUpView() {
        memoryId = getIntent().getLongExtra(MEMORY_ID, -1);

        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(view);
            }
        });

        memoryView.setLayoutManager(new LinearLayoutManager(this));
        memoryView.setItemAnimator(new DefaultItemAnimator());

        presenter = new IMemoryShowPresenterImpl(this);
        presenter.loadMemory(memoryId);
    }

    private void showPopupWindow(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater()
                .inflate(R.menu.activity_show_memory_pop, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.et_memory) {
                    toEditMemory();
                } else if (item.getItemId() == R.id.del_memory) {
                    showDelDialog();
                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    protected void handler(Message msg) {
    }

    private void toEditMemory() {
        List<PhotoInfo> photoInfoList = new ArrayList<>();
        for (Image image:memory.getImages()) {
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPhotoPath(image.getImgUrl());
            photoInfoList.add(photoInfo);
        }
        MemoryEditActivity.startActivity(MemoryShowActivity.this,
                photoInfoList,
                memoryId,
                memory.getTitle(),
                memory.getDesc());
    }

    private void showDelDialog() {
        delDialog = new AlertDialog
                .Builder(MemoryShowActivity.this)
                .setView(getLayoutInflater().inflate(R.layout.dlg_del_memory, null))
                .create();
        delDialog.show();
    }

    private void hideDelDialog() {
        if (delDialog != null && delDialog.isShowing()) {
            delDialog.dismiss();
        }
    }

    public void cancelDeletion(View view) {
        hideDelDialog();
    }

    public void confirmDeletion(View view) {
        presenter.deleteMemory(memoryId);
    }

    @Override
    public void loadMemory(final Memory memory) {
        this.memory = memory;
        MemoryDetailAdapter adapter = new MemoryDetailAdapter(this, memory);
        adapter.setOnItemClickListener(new MemoryDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击预览相片
                PhotoPreviewActivity.startActivity(MemoryShowActivity.this,
                        (ArrayList<Image>) memory.getImages(),
                        position);
            }
        });
        memoryView.setAdapter(adapter);
    }

    @Override
    public void finishDeletion() {
        hideDelDialog();
        Toast.makeText(MemoryShowActivity.this, getString(R.string.delete_succeed), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void error() {
        titleBar.setRightLayoutVisibility(View.GONE);
        ViewStub vsError = (ViewStub) this.findViewById(R.id.vs_memory_error);
        vsError.inflate();
        ImageView ivError = (ImageView) findViewById(R.id.iv_vs);
        // TODO: 2016/9/12 setImageView
        TextView tvError = (TextView) findViewById(R.id.tv_vs);
        tvError.setText(getString(R.string.this_memory_is_not_found));
    }

    public static void startActivity(Context context, Long memoryId) {
        Intent intent = new Intent(context, MemoryShowActivity.class);
        intent.putExtra(MEMORY_ID, memoryId);
        context.startActivity(intent);
    }
}
