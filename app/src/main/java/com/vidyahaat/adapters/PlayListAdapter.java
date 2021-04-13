package com.vidyahaat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.model.video.Data;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.MyViewHolder> {

    List<Data> dataList;
    OnPlayListItemClickListener onPlayListItemClickListener;

    public PlayListAdapter(List<Data> dataList, OnPlayListItemClickListener onPlayListItemClickListener) {
        this.dataList = dataList;
        this.onPlayListItemClickListener = onPlayListItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.serialNo.setText(position + 1 + "");
        holder.duration.setText(dataList.get(position).getDuration());
        holder.topicName.setText(dataList.get(position).getTitle());
        holder.desc.setText(dataList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView serialNo, topicName, duration, desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            serialNo = itemView.findViewById(R.id.serial_no_tv);
            topicName = itemView.findViewById(R.id.video_title_tv);
            duration = itemView.findViewById(R.id.duration_tv);
            desc = itemView.findViewById(R.id.desc);

            itemView.setOnClickListener(view -> onPlayListItemClickListener.onItemClicked(dataList.get(getAdapterPosition()).getVideo(),
                    dataList.get(getAdapterPosition()).getTitle(), getAdapterPosition() + 1 + "",
                    dataList.get(getAdapterPosition()).getId()));
        }
    }

    public interface OnPlayListItemClickListener {
        public void onItemClicked(String videoUrl, String currentPlayListName, String sln, String id);
    }
}
