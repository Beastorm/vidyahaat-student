package com.vidyahaat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import com.vidyahaat.activities.ChannelsActivity;
import com.vidyahaat.activities.PlayVideoContentActivity;
import com.vidyahaat.adapters.MockTestAdapter;
import com.vidyahaat.adapters.SubjectAdapter;
import com.vidyahaat.adapters.WatchListAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.homescreen.HomeModel;
import com.vidyahaat.model.homescreen.MockTestDetails;
import com.vidyahaat.model.order.OrderModel;
import com.vidyahaat.model.watchlist.WatchListModel;
import com.vidyahaat.utilities.AutoFitGridLayoutManager;
import com.vidyahaat.utilities.MyPreference;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SubjectAdapter.OnCommunicator, MockTestAdapter.OnCommunicator, WatchListAdapter.OnCommunicationListener {

    TextView fullNameTv, aboutTv;
    MyPreference myPreference;
    RecyclerView subjectsRv;
    SubjectAdapter subjectAdapter;
    NavController navController;
    Button checkBtn;
    RecyclerView mockTestRv, watchListRv;
    MockTestAdapter mockTestAdapter;
    ConstraintLayout assignmentContainer, subjectContainer, liveContainer, watchListContainer;
    WatchListAdapter watchListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullNameTv = view.findViewById(R.id.full_name_tv);
        subjectsRv = view.findViewById(R.id.subject_rv);
        aboutTv = view.findViewById(R.id.about_vidyahaat);
        checkBtn = view.findViewById(R.id.check_btn);
        navController = Navigation.findNavController(view);
        mockTestRv = view.findViewById(R.id.mock_test_rv);
        assignmentContainer = view.findViewById(R.id.assignment_container);
        liveContainer = view.findViewById(R.id.live_container);
        subjectContainer = view.findViewById(R.id.subject_container);
        watchListContainer = view.findViewById(R.id.watch_list_container);
        watchListRv = view.findViewById(R.id.watchlist_rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myPreference = new MyPreference(requireActivity());
        fullNameTv.setText(myPreference.readFullUserName());

        LinearLayoutManager examLinearLayoutManager = new LinearLayoutManager(getActivity());
        examLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        subjectsRv.setLayoutManager(examLinearLayoutManager);
        subjectsRv.setHasFixedSize(true);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        watchListRv.setLayoutManager(llm);
        watchListRv.setHasFixedSize(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(getContext(), width / 2 - 32);
        mockTestRv.setLayoutManager(layoutManager);
        getALLWatchList();
        getAllData();

        if (myPreference.readStudentType().equals("outsider")) {
            assignmentContainer.setVisibility(View.GONE);
            liveContainer.setVisibility(View.GONE);
            subjectContainer.setVisibility(View.GONE);
            watchListContainer.setVisibility(View.GONE);
        }
        checkBtn.setOnClickListener(view -> navController.navigate(R.id.assignmentFragment));

        liveContainer.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ChannelsActivity.class);
            requireActivity().startActivity(intent);

        });
    }

    private void getAllData() {
        Log.i("TAG", "onResponse: ");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Log.i("TAG", "onResponse " + myPreference.readStudentId());
        Call<HomeModel> call = apiInterface.getHomeScreenData(myPreference.readStudentId());
        call.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeModel> call, @NonNull Response<HomeModel> response) {
                Log.i("TAG", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        getAllOrders(response.body());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<HomeModel> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClickListener(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        navController.navigate(R.id.action_homeFragment_to_chapterFragment, bundle);
    }


    // on click of mock test item
    @Override
    public void onClick(MockTestDetails mockTestDetails, String destination) {
        Bundle bundle = new Bundle();
        bundle.putString("id", mockTestDetails.getId());
        bundle.putString("src", "mock_test");
        bundle.putString("duration", mockTestDetails.getDuration());
        bundle.putString("time", mockTestDetails.getExamTime());
        bundle.putString("date", mockTestDetails.getExamdate());
        bundle.putString("mock_title", mockTestDetails.getName());
        bundle.putString("price", mockTestDetails.getPrice());
        bundle.putString("firstPrize", mockTestDetails.getPrizeOne());
        bundle.putString("secondPrize", mockTestDetails.getPrizeTwo());
        bundle.putString("thirdPrize", mockTestDetails.getPrizeThree());
      //  navController.navigate(R.id.action_homeFragment_to_mockTestDetailsFragment, bundle);

        if (destination.equals("pay"))
            navController.navigate(R.id.action_homeFragment_to_mockTestDetailsFragment, bundle);
        else {
            navController.navigate(R.id.action_homeFragment_to_testQuestionsFragment, bundle);
        }
    }

    private void getALLWatchList() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<WatchListModel> call = apiInterface.viewWatchList(myPreference.readStudentId());
        call.enqueue(new Callback<WatchListModel>() {
            @Override
            public void onResponse(@NonNull Call<WatchListModel> call, @NonNull Response<WatchListModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        watchListContainer.setVisibility(View.VISIBLE);
                        watchListAdapter = new WatchListAdapter(response.body().getData(), HomeFragment.this);
                        watchListRv.setAdapter(watchListAdapter);
                        watchListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WatchListModel> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBtnClick(String videoUrl, String currentPlayListName, String sln, String id, String courseId) {
        Intent intent = new Intent(requireContext(), PlayVideoContentActivity.class);
        intent.putExtra("video_url", videoUrl);
        intent.putExtra("currentPlayListName", currentPlayListName);
        intent.putExtra("sln", sln);
        intent.putExtra("id", id);
        intent.putExtra("course_id", courseId);
        requireActivity().startActivity(intent);
    }

    private void getAllOrders(HomeModel body) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<OrderModel> call = apiInterface.viewOrders(myPreference.readStudentId());
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(@NotNull Call<OrderModel> call, @NotNull Response<OrderModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;

                    aboutTv.setText(body.getData().getAbout());
                    Log.i("TAG", "onResponse: " + body.getData().getAbout());
                    myPreference.writeAboutUs(body.getData().getAbout());
                    Log.i("Home Fragment", "" + body.getData().getSubjectDetails());
                    Log.i("TAG", "onResponse: " + body.getData().getClassDetails());
                    if (body.getData().getClassDetails() != null) {
                        subjectAdapter = new SubjectAdapter(body.getData().getSubjectDetails(), HomeFragment.this);
                        subjectsRv.setAdapter(subjectAdapter);
                        subjectAdapter.notifyDataSetChanged();
                    }
                    mockTestAdapter = new MockTestAdapter(body.getData().getMockTestDetails(), response.body().getData(), HomeFragment.this);
                    mockTestRv.setAdapter(mockTestAdapter);
                    mockTestAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(@NotNull Call<OrderModel> call, @NotNull Throwable t) {

            }
        });
    }
}