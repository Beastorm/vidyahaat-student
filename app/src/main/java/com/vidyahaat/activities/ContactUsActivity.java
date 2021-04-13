package com.vidyahaat.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.vidyahaat.R;
import com.vidyahaat.utilities.MyPreference;

public class ContactUsActivity extends AppCompatActivity {


    Toolbar toolbar;
    TextView email, mobile;
    MyPreference myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        email = findViewById(R.id.email_id);
        mobile = findViewById(R.id.mobile_no);

        toolbar = findViewById(R.id.help_toolbar);
        toolbar.setTitle("Contact Us");
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contact Us");
        myPreferences = new MyPreference(this);

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mobile.getText().toString().trim(), null));
                    startActivity(callIntent);

                } else
                    Toast.makeText(ContactUsActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString().trim()});
                Intent mailer = Intent.createChooser(intent, "Choose mail Application");
                startActivity(mailer);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}