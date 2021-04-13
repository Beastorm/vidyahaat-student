package com.vidyahaat.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.model.viewassignment.Data;

import java.util.List;

public class AssignmentHistoryAdapter extends RecyclerView.Adapter<AssignmentHistoryAdapter.MyViewHolder> {

    List<Data> dataList;

    public AssignmentHistoryAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment_history_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(dataList.get(position).getAssignmentDetails().get(0).getName());
        if (dataList.get(position).getGrades().length() == 0) {
            holder.marks.setText("In Process");
            holder.marks.setTextColor(Color.BLUE);
        } else {
            holder.marks.setText(String.format("Grade: %s", dataList.get(position).getGrades()));
            holder.marks.setTextColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, lastDate, downloadLink, marks;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            marks = itemView.findViewById(R.id.marks);
            // downloadLink = itemView.findViewById(R.id.link);
            // lastDate = itemView.findViewById(R.id.last_date);

        }
    }
}
