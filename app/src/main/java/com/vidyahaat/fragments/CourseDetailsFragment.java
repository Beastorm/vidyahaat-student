package com.vidyahaat.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vidyahaat.R;
import com.vidyahaat.activities.PlayVideoContentActivity;
import com.vidyahaat.adapters.TestAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.test.TestCourseModel;
import com.vidyahaat.utilities.AutoFitGridLayoutManager;
import com.vidyahaat.utilities.MyPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsFragment extends Fragment implements TestAdapter.OnCommunicator {

    ImageView imageView;
    TextView nameTv, descTv, downloadBtn, viewAssignmentBtn, viewVideoContentBtn;
    NavController navController;
    RecyclerView testRv;
    TestAdapter testAdapter;
    MyPreference myPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.course_img);
        nameTv = view.findViewById(R.id.name);
        descTv = view.findViewById(R.id.desc_tv);
        downloadBtn = view.findViewById(R.id.download_btn);
        viewAssignmentBtn = view.findViewById(R.id.view_assignment_btn);
        testRv = view.findViewById(R.id.test_rv);
        myPreference = new MyPreference(requireContext());
        navController = Navigation.findNavController(view);
        viewVideoContentBtn = view.findViewById(R.id.view_video_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getArguments() != null;
        Glide.with(requireContext()).load(getArguments().getString("image_url")).into(imageView);
        nameTv.setText(getArguments().getString("name"));
        descTv.setText(getArguments().getString("desc"));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(getContext(), width / 2 - 32);
        testRv.setLayoutManager(layoutManager);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getArguments() != null;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getArguments().getString("doc")));
                startActivity(browserIntent);
                downloadBtn.setText("Check in Your Notification Panel");
            }
        });

        viewAssignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                assert getArguments() != null;
                bundle.putString("course_id", getArguments().getString("course_id"));
                navController.navigate(R.id.action_courseDetailsFragment_to_assignmentFragment, bundle);
            }
        });

        viewVideoContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), PlayVideoContentActivity.class);
                assert getArguments() != null;
                intent.putExtra("course_id", getArguments().getString("course_id"));
                requireContext().startActivity(intent);
            }
        });

        getAllTest();
    }


    public void getAllTest() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        assert getArguments() != null;
        Call<TestCourseModel> call = apiInterface.getAllCourseTests(getArguments().getString("course_id"));
        call.enqueue(new Callback<TestCourseModel>() {
            @Override
            public void onResponse(@NonNull Call<TestCourseModel> call, @NonNull Response<TestCourseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {

                        testAdapter = new TestAdapter(response.body().getData(), CourseDetailsFragment.this);
                        testRv.setAdapter(testAdapter);
                        testAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<TestCourseModel> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(String id, String duration) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("src", "test");
        bundle.putString("duration", duration);
        navController.navigate(R.id.action_courseDetailsFragment_to_testQuestionsFragment, bundle);
        // getAlreadySubmittedTest(id);
    }


}