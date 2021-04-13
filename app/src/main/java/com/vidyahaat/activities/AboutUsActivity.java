package com.vidyahaat.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vidyahaat.R;
import com.vidyahaat.utilities.MyPreference;

public class AboutUsActivity extends AppCompatActivity {


    TextView aboutUsTv;
    MyPreference myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutUsTv = findViewById(R.id.about_us_tv);

        myPreference = new MyPreference(this);
        aboutUsTv.setText(myPreference.readAboutUs());
    }
}