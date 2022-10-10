package com.yarenchoi.tenderness.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Memory;
import com.yarenchoi.tenderness.presenter.IMemoryPresenterImpl;
import com.yarenchoi.tenderness.presenter.ipresenter.IMemoryPresenter;
import com.yarenchoi.tenderness.ui.activity.MemoryShowActivity;
import com.yarenchoi.tenderness.ui.adapter.MemoriesViewAdapter;
import com.yarenchoi.tenderness.ui.fragment.ifragment.IMemoryFragment;
import com.yarenchoi.tenderness.utils.GalleryFinalUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YarenChoi on 2016/8/11.
 * 记忆墙碎片化界面
 */
public class MemoryFragment extends BaseFragment implements IMemoryFragment, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView memoryView;
    SwipeRefreshLayout refreshLayout;
    FloatingActionButton fab;
    AlertDialog alertDialog;
    private MemoriesViewAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private IMemoryPresenter presenter;

    private List<Memory> memories = new ArrayList<>();
    private int pageIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memory, container, false);
    }

    @Override
    protected void initView() {
        View contentView = getView();
        if (contentView != null) {
            memoryView = (RecyclerView) contentView.findViewById(R.id.rv_memory);
            refreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.srl_memory);
            fab = (FloatingActionButton) contentView.findViewById(R.id.fab);
        }
    }

    @Override
    protected void setUpView() {
        presenter = new IMemoryPresenterImpl(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectionDialog();
            }
        });

        memoryView.setLayoutManager(mLayoutManager = new LinearLayoutManager(getContext()));
        memoryView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MemoriesViewAdapter(this.getContext(), memories);
        adapter.setOnItemClickListener(new MemoriesViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MemoryShowActivity.startActivity(getContext(),
                        memories.get(position).getId());
            }
        });
        memoryView.setAdapter(adapter);
        memoryView.addOnScrollListener(mOnScrollListener);
        refreshLayout.setOnRefreshListener(this);

        //初始化
        onRefresh();
    }

    @Override
    public void hideRefreshLayout() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMemories(List<Memory> newMemories) {
        memories.addAll(newMemories);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        memories.clear();
        presenter.loadMemories(pageIndex = 0);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            /**
             * scrollState有三种状态，分别是SCROLL_STATE_IDLE、SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
             * SCROLL_STATE_IDLE是当屏幕停止滚动时
             * SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时
             * SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
             */
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem == adapter.getItemCount() - 1) {
                // 上拉加载更多
                // 不足一页则执行刷新，数据少时可锁定不可加载
                if (memories.size() >= 10) {
                    pageIndex++;
                    presenter.loadMemories(pageIndex);
                }
            }
        }
    };

    private void showSelectionDialog() {
        if (alertDialog == null) {
            View dialogView = LayoutInflater.from(this.getContext()).inflate(R.layout.dlg_create_memory, null);
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
            alertDialog = new AlertDialog.Builder(this.getContext())
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
        GalleryFinalUtils.openCameraForCreateMemory(this.getContext());
    }

    private void openGallery() {
        hideSelectionDialog();
        GalleryFinalUtils.openGalleryForCreateMemory(this.getContext());
    }

}
