package com.vidyahaat.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.adapters.ChatHistoryAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.chathistory.ChatHistoryModel;
import com.vidyahaat.utilities.MyPreference;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView chatRv;
    MyPreference myPreference;
    ChatHistoryAdapter chatHistoryAdapter;
    ImageView sendBtn;
    EditText messageEt;
    Thread chatThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("teacher"));
        chatRv = findViewById(R.id.chat_rv);
        sendBtn = findViewById(R.id.sendBtn);
        messageEt = findViewById(R.id.type_massage);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRv.setLayoutManager(layoutManager);
        layoutManager.setStackFromEnd(true);

        if (toolbar != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        myPreference = new MyPreference(this);
        getChatHistory();
        //startViewChatThread();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = messageEt.getText().toString().trim();
                if (msg.length() > 0) {
                    if (!msg.equals("")) {
                        sendChatMessage(msg);
                        messageEt.setText("");

                    }
                }

            }
        });
    }

    private void startViewChatThread() {

        chatThread = new Thread() {
            public void run() {
                int i;
                try {
                    for (i = 1; i > 0; i++) {
                        sleep(5000);
                        getChatHistory();
                    }


                } catch (Exception e) {
                    Log.i("TAG", "run: " + e.getMessage());
                }
            }
        };
        chatThread.start();
    }

    private void sendChatMessage(String msg) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.sendChat(getIntent().getStringExtra("id"), myPreference.readStudentId(), msg);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    getChatHistory();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });

    }

    private void getChatHistory() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ChatHistoryModel> call = apiInterface.getChatHistory(getIntent().getStringExtra("id"), myPreference.readStudentId());
        call.enqueue(new Callback<ChatHistoryModel>() {
            @Override
            public void onResponse(@NonNull Call<ChatHistoryModel> call, @NonNull Response<ChatHistoryModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("Successful")) {

                        chatHistoryAdapter = new ChatHistoryAdapter(response.body().getData());
                        chatRv.setAdapter(chatHistoryAdapter);
                        chatHistoryAdapter.notifyDataSetChanged();
                        startViewChatThread();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ChatHistoryModel> call, @NonNull Throwable t) {

                Toast.makeText(ChatActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (chatThread != null)
//            if (chatThread.isAlive())
//                chatThread.();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}