package com.yarenchoi.tenderness.presenter;

import com.yarenchoi.tenderness.db.entity.Memory;
import com.yarenchoi.tenderness.model.IMemoryModelImpl;
import com.yarenchoi.tenderness.model.imodel.IMemoryModel;
import com.yarenchoi.tenderness.presenter.ipresenter.IMemoryPresenter;
import com.yarenchoi.tenderness.ui.fragment.ifragment.IMemoryFragment;

import java.util.List;

/**
 * Created by YarenChoi on 2016/8/18.
 * Memory适配器接口实现
 */
public class IMemoryPresenterImpl implements IMemoryPresenter {
    private IMemoryModel iMemoryModel;
    private IMemoryFragment iMemoryFragment;

    public IMemoryPresenterImpl(IMemoryFragment iMemoryFragment) {
        this.iMemoryFragment = iMemoryFragment;
        this.iMemoryModel = new IMemoryModelImpl();
    }

    @Override
    public void loadMemories(int page) {
        if (page == 0) {
            //刷新操作
            iMemoryFragment.loadMemories(iMemoryModel.loadMemories(page));
            iMemoryFragment.hideRefreshLayout();
        } else {
            //加载操作
            List<Memory> memories = iMemoryModel.loadMemories(page);
            if (memories.size() != 0) {
                iMemoryFragment.loadMemories(memories);
            }
        }
    }
}
