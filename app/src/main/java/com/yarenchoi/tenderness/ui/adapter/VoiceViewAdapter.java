package com.yarenchoi.tenderness.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Voice;
import com.yarenchoi.tenderness.utils.TimeFormatUtils;

import java.util.List;

/**
 * Created by YarenChoi on 2016/9/6.
 * 语音列表适配器
 */
public class VoiceViewAdapter extends RecyclerView.Adapter<VoiceViewAdapter.VoiceViewHolder> {

    private List<Voice> voiceList;

    OnItemClickListener onItemClickListener;

    public VoiceViewAdapter(List<Voice> voiceList) {
        this.voiceList = voiceList;
    }

    @Override
    public VoiceViewAdapter.VoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VoiceViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_voice, parent, false));
    }

    @Override
    public void onBindViewHolder(VoiceViewHolder holder, int position) {
        holder.title.setText(getItem(position).getTitle());
        holder.date.setText(TimeFormatUtils.getFormatDate(getItem(position).getDate()));
        holder.len.setText(String.valueOf(Math.round(getItem(position).getLen()) + "\""));
    }

    public Voice getItem(int position) {
        return voiceList.get(position);
    }

    @Override
    public int getItemCount() {
        return voiceList.size();
    }

    public class VoiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View rootView;
        TextView title;
        TextView date;
        TextView len;
        public VoiceViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.tv_voice_title);
            date = (TextView) itemView.findViewById(R.id.tv_voice_date);
            len = (TextView) itemView.findViewById(R.id.tv_voice_len);
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
