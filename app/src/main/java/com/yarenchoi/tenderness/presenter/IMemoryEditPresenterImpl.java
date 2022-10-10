package com.yarenchoi.tenderness.presenter;

import com.yarenchoi.tenderness.model.IMemoryModelImpl;
import com.yarenchoi.tenderness.model.imodel.IMemoryModel;
import com.yarenchoi.tenderness.presenter.ipresenter.IMemoryEditPresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.IMemoryEditActivity;

/**
 * Created by YarenChoi on 2016/8/23.
 * 创建记忆适配器接口实现
 */
public class IMemoryEditPresenterImpl implements IMemoryEditPresenter {
    private IMemoryModel model;
    private IMemoryEditActivity view;

    public IMemoryEditPresenterImpl(IMemoryEditActivity iMemoryEditActivity) {
        this.view = iMemoryEditActivity;
        this.model = new IMemoryModelImpl();
    }

    @Override
    public void createMemory() {
        view.showProgressBar();
        model.createMemory(view.getMemoryTitle(), view.getMemoryDesc(), view.getPhotoInfoList());
        view.hideProgressBar();
    }

    @Override
    public void updateMemory(Long memoryId) {
        view.showProgressBar();
        model.updateMemory(memoryId, view.getMemoryTitle(), view.getMemoryDesc(), view.getPhotoInfoList());
        view.hideProgressBar();
    }
}
