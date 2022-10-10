package com.yarenchoi.tenderness.presenter;

import android.util.Log;

import com.yarenchoi.tenderness.db.entity.Memory;
import com.yarenchoi.tenderness.model.IMemoryModelImpl;
import com.yarenchoi.tenderness.model.imodel.IMemoryModel;
import com.yarenchoi.tenderness.presenter.ipresenter.IMemoryShowPresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.IMemoryShowActivity;

/**
 * Created by YarenChoi on 2016/9/2.
 *
 */
public class IMemoryShowPresenterImpl implements IMemoryShowPresenter {

    private IMemoryShowActivity view;
    private IMemoryModel model;

    public IMemoryShowPresenterImpl(IMemoryShowActivity view) {
        this.view = view;
        model = new IMemoryModelImpl();
    }

    @Override
    public void loadMemory(Long memoryId) {
        Memory target = null;
        if (memoryId != -1) {
            target = model.loadMemory(memoryId);
        }
        if (target != null) {
            view.loadMemory(target);
        } else {
            view.error();
        }
    }

    @Override
    public void deleteMemory(Long memoryId) {
        model.deleteMemory(memoryId);
        view.finishDeletion();
    }
}
