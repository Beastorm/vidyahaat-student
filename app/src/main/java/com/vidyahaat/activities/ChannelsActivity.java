package com.vidyahaat.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.adapters.ChannelAdapter;
import com.vidyahaat.model.channel.ChannelModel;

import java.util.Objects;


public class ChannelsActivity extends AppCompatActivity {

    RecyclerView channelRv;
    ChannelAdapter channelAdapter;
    Toolbar toolbar;
    EditText channelNameEt, tokenEt;
    boolean isChannelValidated = false, isTokenValidated = false;
    Button joinChannelBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
        toolbar = findViewById(R.id.toolbar2);
        joinChannelBtn = findViewById(R.id.join_channel_btn);

        channelNameEt = findViewById(R.id.channel_name_et);
        tokenEt = findViewById(R.id.token_et);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setTitle("Channels");


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
        channelRv = findViewById(R.id.channel_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        channelRv.setLayoutManager(llm);
        channelRv.setHasFixedSize(true);

        channelAdapter = new ChannelAdapter(ChannelModel.getAllChannel());
        channelRv.setAdapter(channelAdapter);
        channelAdapter.notifyDataSetChanged();


        channelNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    if (editable.toString().trim().length() > 0) {
                        isChannelValidated = true;
                        channelNameEt.setError(null);
                    } else {
                        isChannelValidated = false;
                        channelNameEt.setError("ChannelName Required");
                    }
                    isFormValidated();
                } catch (Exception e) {
                    isChannelValidated = false;
                    channelNameEt.setError("ChannelName Required");
                    isFormValidated();
                }

            }
        });

        tokenEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {

                    if (editable.toString().trim().length() > 0) {
                        isTokenValidated = true;
                        tokenEt.setError(null);
                    } else {
                        isTokenValidated = false;
                        tokenEt.setError("Token Required");
                    }
                    isFormValidated();
                } catch (Exception e) {
                    isTokenValidated = false;
                    tokenEt.setError("Token Required");
                    isFormValidated();
                }
            }
        });

        joinChannelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChannelsActivity.this, LiveChannelActivity.class);
                intent.putExtra("channel", channelNameEt.getText().toString().trim());
                intent.putExtra("token", tokenEt.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private void isFormValidated() {

        if (isChannelValidated && isTokenValidated) {
            joinChannelBtn.setEnabled(true);
            joinChannelBtn.setAlpha(1);
        } else {
            joinChannelBtn.setEnabled(false);
            joinChannelBtn.setAlpha((float) 0.3);
        }

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