package com.yarenchoi.tenderness.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Memory;
import com.yarenchoi.tenderness.utils.GlideLoaderUtils;
import com.yarenchoi.tenderness.utils.ScreenUtils;
import com.yarenchoi.tenderness.utils.TimeFormatUtils;

import java.util.List;

/**
 * Created by YarenChoi on 2016/9/2.
 * Memories Item Adapter
 */
public class MemoriesViewAdapter extends RecyclerView.Adapter<MemoriesViewAdapter.MemoriesViewHolder> {

    private List<Memory> memories;
    private Context mContext;

    private int imgWidth;
    private int imgHeight;
    OnItemClickListener onItemClickListener;

    public MemoriesViewAdapter(Context context, List<Memory> memories) {
        this.memories = memories;
        this.mContext = context;
        imgWidth = ScreenUtils.getScreenMetrics(context).widthPixels;
        imgHeight = ScreenUtils.dip2px(context, 120f);
    }

    @Override
    public MemoriesViewAdapter.MemoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemoriesViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_memory, parent, false));
    }

    @Override
    public void onBindViewHolder(MemoriesViewHolder holder, int position) {
        holder.title.setText(getItem(position).getTitle());
        holder.date.setText(TimeFormatUtils.getFormatDate(getItem(position).getDate()));
        holder.time.setText(TimeFormatUtils.getFormatTime(getItem(position).getDate()));
        GlideLoaderUtils.loadImageWithMemoryCache(
                mContext,
                getItem(position).getImages().get(0).getImgUrl(),
                holder.cover,
                imgWidth,
                imgHeight);
    }

    public Memory getItem(int position) {
        return memories.get(position);
    }

    @Override
    public int getItemCount() {
        return memories.size();
    }

    public class MemoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View rootView;
        ImageView cover;
        TextView title;
        TextView date;
        TextView time;
        public MemoriesViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            itemView.setOnClickListener(this);
            cover = (ImageView) itemView.findViewById(R.id.iv_memory_cover);
            title = (TextView) itemView.findViewById(R.id.tv_memory_title);
            date = (TextView) itemView.findViewById(R.id.tv_memory_date);
            time = (TextView) itemView.findViewById(R.id.tv_memory_time);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getLayoutPosition());
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
