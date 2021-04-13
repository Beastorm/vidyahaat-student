package com.vidyahaat.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.adapters.TeacherAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.teacher.TeacherModel;
import com.vidyahaat.utilities.MyPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageFragment extends Fragment {

    RecyclerView teacherRv;
    TeacherAdapter teacherAdapter;
    MyPreference myPreference;
    ProgressBar progressBar;
    ConstraintLayout lockContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        teacherRv = view.findViewById(R.id.teacher_rv);
        progressBar = view.findViewById(R.id.progressBar);
        lockContainer = view.findViewById(R.id.lock_container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myPreference = new MyPreference(requireContext());

        if (myPreference.readStudentType().equals("outsider")) {
            lockContainer.setVisibility(View.VISIBLE);
        } else {
            getAllTeachersOfAClass();
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            teacherRv.setLayoutManager(llm);
            teacherRv.setHasFixedSize(true);
            lockContainer.setVisibility(View.GONE);
        }
    }

    private void getAllTeachersOfAClass() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<TeacherModel> call = apiInterface.getAllTeachers(myPreference.readClassId());

        call.enqueue(new Callback<TeacherModel>() {
            @Override
            public void onResponse(@NonNull Call<TeacherModel> call, @NonNull Response<TeacherModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("Successfull")) {
                        Log.i("TAG", "getAllTeachersOfAClass: " + response.body().getData());
                        teacherAdapter = new TeacherAdapter(response.body().getData());
                        teacherRv.setAdapter(teacherAdapter);
                        teacherAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<TeacherModel> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
