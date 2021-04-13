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
import com.vidyahaat.model.course.Data;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    List<Data> dataList;
    OnCommunicator onCommunicator;

    public CourseAdapter(List<Data> dataList, OnCommunicator onCommunicator) {
        this.dataList = dataList;
        this.onCommunicator = onCommunicator;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(dataList.get(position).getName());
        holder.desc.setText(dataList.get(position).getDescription());
        Glide.with(holder.itemView.getContext())
                .load(dataList.get(position).getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.chapter_name);
            desc = itemView.findViewById(R.id.desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCommunicator.onClickListener(dataList.get(getAdapterPosition()).getImage(),
                            dataList.get(getAdapterPosition()).getName(),
                            dataList.get(getAdapterPosition()).getDescription(),
                            dataList.get(getAdapterPosition()).getDoc(),
                            dataList.get(getAdapterPosition()).getId());
                }
            });

        }
    }

    public interface OnCommunicator {
        public void onClickListener(String url, String name, String desc, String doc, String courseId);
    }
}
