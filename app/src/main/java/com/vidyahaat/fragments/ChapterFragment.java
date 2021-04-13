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
import com.vidyahaat.adapters.ChapterAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.chapter.SubjectModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterFragment extends Fragment implements ChapterAdapter.OnCommunicator {

    RecyclerView chapterRv;
    ChapterAdapter chapterAdapter;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapter, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        chapterRv = view.findViewById(R.id.chapter_rv);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager examLinearLayoutManager = new LinearLayoutManager(getActivity());
        examLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chapterRv.setLayoutManager(examLinearLayoutManager);
        chapterRv.setHasFixedSize(true);


        getChapterList();
    }

    private void getChapterList() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        assert getArguments() != null;
        Call<SubjectModel> call = apiInterface.getChaptersOfASubject(getArguments().getString("id"));
        call.enqueue(new Callback<SubjectModel>() {
            @Override
            public void onResponse(@NonNull Call<SubjectModel> call, @NonNull Response<SubjectModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("Successful")) {
                        chapterAdapter = new ChapterAdapter(response.body().getData(), ChapterFragment.this::onClickListener);
                        chapterRv.setAdapter(chapterAdapter);
                        chapterAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<SubjectModel> call, @NonNull Throwable t) {

                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClickListener(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        navController.navigate(R.id.action_chapterFragment_to_courseFragment, bundle);
    }
}