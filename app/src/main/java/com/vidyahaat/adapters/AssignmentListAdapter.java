package com.vidyahaat.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.model.assignment.Data;

import java.util.List;

public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.MyViewHolder> {
    List<Data> dataList;

    public AssignmentListAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment_list_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(dataList.get(position).getName());
        holder.desc.setText(String.format("id: %s", dataList.get(position).getId()));
        //  holder.downloadLink.setText(dataList.get(position).getDoc());
        holder.lastDate.setText(dataList.get(position).getLastdate());
       // holder.id.setText(dataList.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, lastDate, downloadLink, id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            desc = itemView.findViewById(R.id.desc);
            downloadLink = itemView.findViewById(R.id.link);
            lastDate = itemView.findViewById(R.id.last_date);


            downloadLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataList.get(getAdapterPosition()).getDoc()));
                    itemView.getContext().startActivity(browserIntent);
                }
            });
        }
    }
}
