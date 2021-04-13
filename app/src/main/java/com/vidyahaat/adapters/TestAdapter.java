package com.vidyahaat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vidyahaat.R;
import com.vidyahaat.model.test.Data;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {

    List<Data> dataList;
    OnCommunicator onCommunicator;

    public TestAdapter(List<Data> dataList, OnCommunicator onCommunicator) {
        this.dataList = dataList;
        this.onCommunicator = onCommunicator;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        Glide.with(holder.imageView.getContext()).load(dataList.get(position).getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.test_name);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(view -> {
                onCommunicator.onClick(dataList.get(getAdapterPosition()).getId(), dataList.get(getAdapterPosition()).getDuration());
            });
        }
    }

    public interface OnCommunicator {
        public void onClick(String id, String duration);
    }
}
