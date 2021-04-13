package com.vidyahaat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.vidyahaat.R;
import com.vidyahaat.utilities.MyPreference;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    MyPreference myPreference;
    public static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        myPreference = new MyPreference(this);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (myPreference.readLoginStatus()) {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(i);
                    finish();

                }

            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onBackPressed() {

        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        super.onBackPressed();
    }
}