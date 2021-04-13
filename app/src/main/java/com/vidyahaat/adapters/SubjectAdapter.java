package com.vidyahaat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.model.homescreen.SubjectDetails;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {

    List<SubjectDetails> subjectDetailsList;
    OnCommunicator onCommunicator;

    public SubjectAdapter(List<SubjectDetails> subjectDetailsList, OnCommunicator onCommunicator) {
        this.subjectDetailsList = subjectDetailsList;
        this.onCommunicator = onCommunicator;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.subjectName.setText(subjectDetailsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return subjectDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectName = itemView.findViewById(R.id.subject_name);
            itemView.setOnClickListener(view -> onCommunicator.onClickListener(subjectDetailsList.get(getAdapterPosition()).getId()));
        }
    }

    public interface OnCommunicator {
        public void onClickListener(String id);
    }
}
