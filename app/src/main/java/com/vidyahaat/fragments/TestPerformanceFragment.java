package com.vidyahaat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.adapters.TestPerformanceAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.testper.TestPerformanceModel;
import com.vidyahaat.utilities.MyPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestPerformanceFragment extends Fragment {

    RecyclerView testHistoryRv;
    TestPerformanceAdapter testPerformanceAdapter;
    MyPreference myPreference;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_performance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        testHistoryRv = view.findViewById(R.id.test_history_rv);
        progressBar = view.findViewById(R.id.progressBar);

        myPreference = new MyPreference(requireContext());

        LinearLayoutManager examLinearLayoutManager = new LinearLayoutManager(getActivity());
        examLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testHistoryRv.setLayoutManager(examLinearLayoutManager);
        testHistoryRv.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getAllTestPerList();
    }

    private void getAllTestPerList() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<TestPerformanceModel> call = apiInterface.getAllTestResult(myPreference.readStudentId());
        call.enqueue(new Callback<TestPerformanceModel>() {
            @Override
            public void onResponse(@NonNull Call<TestPerformanceModel> call, @NonNull Response<TestPerformanceModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        if (response.body().getData().size() > 0) {
                            testPerformanceAdapter = new TestPerformanceAdapter(response.body().getData());
                            testHistoryRv.setAdapter(testPerformanceAdapter);
                            testPerformanceAdapter.notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<TestPerformanceModel> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}