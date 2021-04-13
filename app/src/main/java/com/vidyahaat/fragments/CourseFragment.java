package com.vidyahaat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.vidyahaat.adapters.CourseAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.course.CourseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseFragment extends Fragment implements CourseAdapter.OnCommunicator {

    RecyclerView courseRv;
    CourseAdapter courseAdapter;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        courseRv = view.findViewById(R.id.course_rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager examLinearLayoutManager = new LinearLayoutManager(getActivity());
        examLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        courseRv.setLayoutManager(examLinearLayoutManager);
        courseRv.setHasFixedSize(true);

        getCourse();
    }

    private void getCourse() {


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CourseModel> call = apiInterface.getCoursesOfAChapter(getArguments().getString("id"));
        call.enqueue(new Callback<CourseModel>() {
            @Override
            public void onResponse(@NonNull Call<CourseModel> call, @NonNull Response<CourseModel> response) {
                if (response.isSuccessful()) {

                    if (response.body().getResponse().equals("Successful")) {

                        courseAdapter = new CourseAdapter(response.body().getData(), CourseFragment.this);
                        courseRv.setAdapter(courseAdapter);
                        courseAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CourseModel> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onClickListener(String url, String name, String desc, String doc, String courseId) {
        Bundle bundle = new Bundle();
        bundle.putString("image_url", url);
        bundle.putString("name", name);
        bundle.putString("desc", desc);
        bundle.putString("doc", doc);
        bundle.putString("course_id", courseId);
        navController.navigate(R.id.action_courseFragment_to_courseDetailsFragment, bundle);
    }
}