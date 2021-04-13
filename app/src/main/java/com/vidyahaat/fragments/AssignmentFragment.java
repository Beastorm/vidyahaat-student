package com.vidyahaat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.activities.UploadAssignmentActivity;
import com.vidyahaat.adapters.AssignmentHistoryAdapter;
import com.vidyahaat.adapters.AssignmentListAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.assignment.AssignmentModel;
import com.vidyahaat.model.assignment.Data;
import com.vidyahaat.model.viewassignment.ViewAssignmentModel;
import com.vidyahaat.utilities.MyPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentFragment extends Fragment {

    AssignmentListAdapter assignmentListAdapter;
    AssignmentHistoryAdapter assignmentHistoryAdapter;
    Button addAssignmentBtn;
    NavController navController;
    RecyclerView assignmentHistoryRv, assignmentListRv;
    ConstraintLayout assignmentContainer, submittedAssignmentContainer;
    MyPreference myPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addAssignmentBtn = view.findViewById(R.id.add_assignment_btn);
        assignmentListRv = view.findViewById(R.id.assignment_rv);
        assignmentHistoryRv = view.findViewById(R.id.assignment_history_rv);
        assignmentContainer = view.findViewById(R.id.assignment_list_container);
        submittedAssignmentContainer = view.findViewById(R.id.assignment_history_container);
        navController = Navigation.findNavController(view);
        myPreference = new MyPreference(requireContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager examLinearLayoutManager = new LinearLayoutManager(getActivity());
        examLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        assignmentListRv.setLayoutManager(examLinearLayoutManager);
        assignmentListRv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        assignmentHistoryRv.setLayoutManager(llm);
        assignmentHistoryRv.setLayoutManager(llm);
        assignmentHistoryRv.setHasFixedSize(true);

        addAssignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // navController.navigate(R.id.addAssignmentFragment);
                Intent intent = new Intent(requireContext(), UploadAssignmentActivity.class);
                startActivity(intent);
            }
        });

        if (getArguments() != null)
            getAllAssignment();
        else
            getAllSubmittedAssignment();
    }


    // submitted by a individual student
    private void getAllSubmittedAssignment() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ViewAssignmentModel> call = apiInterface.getAllSubmittedAssignments(myPreference.readStudentId());

        call.enqueue(new Callback<ViewAssignmentModel>() {
            @Override
            public void onResponse(@NonNull Call<ViewAssignmentModel> call, @NonNull Response<ViewAssignmentModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        if (response.body().getData().size() > 0) {
                            submittedAssignmentContainer.setVisibility(View.VISIBLE);
                            assignmentHistoryAdapter = new AssignmentHistoryAdapter(response.body().getData());
                            assignmentHistoryRv.setAdapter(assignmentHistoryAdapter);
                            assignmentHistoryAdapter.notifyDataSetChanged();
                        }

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<ViewAssignmentModel> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAllAssignment() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        assert getArguments() != null;
        Call<AssignmentModel> call = apiInterface.getAllAssignments(getArguments().getString("course_id"));
        call.enqueue(new Callback<AssignmentModel>() {
            @Override
            public void onResponse(@NonNull Call<AssignmentModel> call, @NonNull Response<AssignmentModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {

                        assignmentContainer.setVisibility(View.VISIBLE);
                        assignmentListAdapter = new AssignmentListAdapter(response.body().getData());
                        assignmentListRv.setAdapter(assignmentListAdapter);
                        assignmentListAdapter.notifyDataSetChanged();
                        //  getAllSubmittedAssignment(response.body().getData());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<AssignmentModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void getAllSubmittedAssignment(List<Data> data) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ViewAssignmentModel> call = apiInterface.getAllSubmittedAssignments(myPreference.readStudentId());

        call.enqueue(new Callback<ViewAssignmentModel>() {
            @Override
            public void onResponse(@NonNull Call<ViewAssignmentModel> call, @NonNull Response<ViewAssignmentModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("Successful")) {


                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<ViewAssignmentModel> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}