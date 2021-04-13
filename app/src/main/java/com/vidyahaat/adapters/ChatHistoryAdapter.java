package com.vidyahaat.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.model.chathistory.Data;

import java.util.List;

public class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.MyViewHolder> {
    List<Data> dataList;

    public ChatHistoryAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String senderType = dataList.get(position).getSendertype();
        String sms = dataList.get(position).getSms();
        String time = dataList.get(position).getDate();

        holder.chatTx.setText(sms);
        holder.timeTx.setText(time);

        if (senderType.equals("Teacher")) {
            holder.chatLin.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            holder.timeTx.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        } else {
            holder.chatLin.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            holder.timeTx.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout chatLin;
        TextView chatTx, timeTx;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chatTx = itemView.findViewById(R.id.chat_text);
            chatLin = itemView.findViewById(R.id.chatlin);
            timeTx = itemView.findViewById(R.id.time_tx);
        }
    }
}
