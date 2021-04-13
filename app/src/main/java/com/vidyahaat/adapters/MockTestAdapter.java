package com.vidyahaat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vidyahaat.R;
import com.vidyahaat.model.homescreen.MockTestDetails;
import com.vidyahaat.model.order.Data;

import java.util.List;

public class MockTestAdapter extends RecyclerView.Adapter<MockTestAdapter.MyViewHolder> {

    List<MockTestDetails> mockTestDetailsList;
    List<Data> orderList;
    OnCommunicator onCommunicator;

    public MockTestAdapter(List<MockTestDetails> mockTestDetailsList, List<Data> orders, OnCommunicator onCommunicator) {
        this.mockTestDetailsList = mockTestDetailsList;
        this.orderList = orders;
        this.onCommunicator = onCommunicator;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mock_test_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(mockTestDetailsList.get(position).getName());
        holder.examDate.setText(mockTestDetailsList.get(position).getDate().split(" ")[0]);
        holder.duration.setText(String.format("Duration: %s", mockTestDetailsList.get(position).getDuration()));
        holder.examTime.setText(String.format("Exam Time: %s", mockTestDetailsList.get(position).getExamTime()));
        Glide.with(holder.imageView.getContext()).load(mockTestDetailsList.get(position).getImage()).into(holder.imageView);

        if (orderList.size() > 0)
            for (Data item : orderList) {
                if (item.getId().equals(mockTestDetailsList.get(position).getId())) {
                    holder.payNowButton.setText("Take Quiz");
                    holder.price.setText("");
                } else {
                    holder.payNowButton.setText("Pay Now");
                    holder.price.setText(String.format("Entry Fee: %s/-", mockTestDetailsList.get(position).getPrice()));
                }

            }

        else {
            holder.payNowButton.setText("Pay Now");
            holder.price.setText(String.format("Entry Fee: %s/-", mockTestDetailsList.get(position).getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return mockTestDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, examDate, duration, examTime;
        ImageView imageView;
        Button payNowButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.test_name);
            imageView = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.cost_tv);
            examDate = itemView.findViewById(R.id.exam_date);
            duration = itemView.findViewById(R.id.duration);
            examTime = itemView.findViewById(R.id.exam_time_tv);
            payNowButton = itemView.findViewById(R.id.pay_now_btn);

            if (payNowButton.getText().equals("Pay Now")) {
                payNowButton.setOnClickListener(view -> onCommunicator.onClick(mockTestDetailsList.get(getAdapterPosition()), "pay"));
            } else {
                payNowButton.setOnClickListener(view -> onCommunicator.onClick(mockTestDetailsList.get(getAdapterPosition()), "take"));
            }

        }
    }

    public interface OnCommunicator {
        void onClick(MockTestDetails mockTestDetails, String destination);
    }
}
