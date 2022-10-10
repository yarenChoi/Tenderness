package com.yarenchoi.tenderness.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Voice;
import com.yarenchoi.tenderness.presenter.IVoicePresenterImpl;
import com.yarenchoi.tenderness.presenter.ipresenter.IVoicePresenter;
import com.yarenchoi.tenderness.ui.activity.BaseActivity;
import com.yarenchoi.tenderness.ui.activity.PlayVoiceActivity;
import com.yarenchoi.tenderness.ui.activity.RecordingActivity;
import com.yarenchoi.tenderness.ui.adapter.VoiceViewAdapter;
import com.yarenchoi.tenderness.ui.fragment.ifragment.IVoiceFragment;
import com.yarenchoi.tenderness.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YarenChoi on 2016/8/24.
 * 录音界面
 */
public class VoiceFragment extends BaseFragment implements IVoiceFragment, SwipeRefreshLayout.OnRefreshListener{

    private static final int REQUEST_RECORDER = 0;

    SwipeRefreshLayout refreshLayout;
    RecyclerView voiceView;
    FloatingActionButton recorderBtn;
    VoiceViewAdapter adapter;

    private List<Voice> voiceList = new ArrayList<>();
    private IVoicePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voice, container, false);
    }

    @Override
    protected void initView() {
        View contentView = getView();
        if (contentView != null) {
            refreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.srl_voice);
            voiceView = (RecyclerView) contentView.findViewById(R.id.rv_voice);
            recorderBtn = (FloatingActionButton) contentView.findViewById(R.id.fab_recorder);
        }
    }

    @Override
    protected void setUpView() {
        presenter = new IVoicePresenterImpl(this);

        recorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordingActivity.startActivity(VoiceFragment.this, REQUEST_RECORDER);
            }
        });

        voiceView.setLayoutManager(new LinearLayoutManager(getContext()));
        voiceView.setItemAnimator(new DefaultItemAnimator());
        adapter = new VoiceViewAdapter(voiceList);
        adapter.setOnItemClickListener(new VoiceViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PlayVoiceActivity.startActivity(getContext(),
                        voiceList.get(position).getId());
            }
        });
        voiceView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        voiceList.clear();
        presenter.loadVoiceList();
    }

    @Override
    public void showRefreshing() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadVoice(List<Voice> voiceList) {
        this.voiceList.addAll(voiceList);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RECORDER) {
            if (resultCode == BaseActivity.RESULT_OK) {
                onRefresh();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
