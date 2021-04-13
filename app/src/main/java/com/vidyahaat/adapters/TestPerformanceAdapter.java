package com.vidyahaat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.model.testper.Data;

import java.util.List;

public class TestPerformanceAdapter extends RecyclerView.Adapter<TestPerformanceAdapter.MyViewHolder> {

    List<Data> data;

    public TestPerformanceAdapter(List<Data> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_performance_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.testName.setText(data.get(position).getTestDetails().get(0).getName());
        holder.mark.setText(data.get(position).getMarks() + "%");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView testName, mark;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            testName = itemView.findViewById(R.id.test_name);
            mark = itemView.findViewById(R.id.marks_tv);
        }
    }
}
