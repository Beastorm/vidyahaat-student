package com.vidyahaat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.vidyahaat.R;
import com.vidyahaat.activities.HomeActivity;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.utilities.MyPreference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestResultActivity extends AppCompatActivity {

    double resPercentage;
    TextView resultPer;
    Button doneBtn;
    String testId;
    MyPreference myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        resultPer = findViewById(R.id.test_per_tv);
        doneBtn = findViewById(R.id.done_btn);
        myPreference = new MyPreference(this);

        resultPer.setText((int) getIntent().getDoubleExtra("per", 0.0) + "%");

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadTestResult();

            }
        });
    }

    private void uploadTestResult() {


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.submitTestResult(getIntent().getStringExtra("test_id"),
                myPreference.readStudentId(), (int) getIntent().getDoubleExtra("per", 0.0) + "%");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TestResultActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TestResultActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(TestResultActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}