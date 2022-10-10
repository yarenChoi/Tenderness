package com.yarenchoi.tenderness.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Memory;
import com.yarenchoi.tenderness.ui.activity.BaseActivity;
import com.yarenchoi.tenderness.utils.GalleryFinalUtils;
import com.yarenchoi.tenderness.utils.GlideLoaderUtils;
import com.yarenchoi.tenderness.utils.TimeFormatUtils;

/**
 * Created by YarenChoi on 2016/9/2.
 * 记忆详情适配器
 */
public class MemoryDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private enum ITEM_TYPE {
        HEADER,
        NORMAL
    }

    private BaseActivity activity;
    private Memory memory;
    OnItemClickListener onItemClickListener;

    public MemoryDetailAdapter(BaseActivity activity, Memory memory) {
        this.activity = activity;
        this.memory = memory;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.HEADER.ordinal()) {
            return new HeaderViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_memory_header, parent, false));
        } else if (viewType == ITEM_TYPE.NORMAL.ordinal()) {
            return new NormalViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_memory_normal, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            GlideLoaderUtils.loadImageWithoutMemoryCache(
                    activity,
                    memory.getImages().get(0).getImgUrl(),
                    ((HeaderViewHolder) holder).cover);
            ((HeaderViewHolder) holder).title.setText(memory.getTitle());
            ((HeaderViewHolder) holder).date.setText(TimeFormatUtils.getFormatDate(memory.getDate()));
            ((HeaderViewHolder) holder).time.setText(TimeFormatUtils.getFormatTime(memory.getDate()));
            ((HeaderViewHolder) holder).desc.setText(memory.getDesc());
        } else if (holder instanceof NormalViewHolder) {
            GlideLoaderUtils.loadImageWithoutMemoryCache(
                    activity,
                    memory.getImages().get(position - 1).getImgUrl(),
                    ((NormalViewHolder) holder).imageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE.HEADER.ordinal() : ITEM_TYPE.NORMAL.ordinal();
    }

    @Override
    public int getItemCount() {
        if (memory != null) {
            return memory.getImages().size() + 1;
        } else {
            return 0;
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView title;
        TextView date;
        TextView time;
        TextView desc;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.iv_memory_cover);
            title = (TextView) itemView.findViewById(R.id.tv_memory_title);
            date = (TextView) itemView.findViewById(R.id.tv_memory_date);
            time = (TextView) itemView.findViewById(R.id.tv_memory_time);
            desc = (TextView) itemView.findViewById(R.id.tv_memory_desc);
        }
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        public NormalViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_memory_image);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getLayoutPosition() - 1);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
}
