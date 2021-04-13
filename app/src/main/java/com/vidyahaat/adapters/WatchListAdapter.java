package com.vidyahaat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.model.watchlist.Data;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.MyViewHolder> {

    List<Data> dataList;
    OnCommunicationListener onCommunicationListener;
    public WatchListAdapter(List<Data> dataList, OnCommunicationListener onCommunicationListener) {
        this.dataList = dataList;
        this.onCommunicationListener = onCommunicationListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watch_list_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.videoName.setText(dataList.get(position).getVideoDetails().get(0).getTitle());

    }

    @Override
    public int getItemCount() {

        return Math.min(dataList.size(), 10);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView videoName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(view -> onCommunicationListener.onBtnClick(
                    dataList.get(getAdapterPosition()).getVideoDetails().get(0).getVideo(),
                    dataList.get(getAdapterPosition()).getVideoDetails().get(0).getTitle(),
                    getAdapterPosition() + 1 + "",
                    dataList.get(getAdapterPosition()).getVideoDetails().get(0).getId(),
                    dataList.get(getAdapterPosition()).getVideoDetails().get(0).getCourse()
            ));
        }
    }

    public interface OnCommunicationListener {

        void onBtnClick(String videoUrl, String currentPlayListName, String sln, String id, String courseId);
    }
}
