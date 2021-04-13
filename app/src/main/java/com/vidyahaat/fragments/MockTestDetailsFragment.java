package com.vidyahaat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vidyahaat.R;
import com.vidyahaat.activities.PaymentActivity;


public class MockTestDetailsFragment extends Fragment {

    TextView titleTv, durationTv, dateTv, timeTv, firstPrizeTv, secondPrizeTv, thirdPrizeTv, priceTv;
    Button payNowBtn;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mock_test_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        priceTv = view.findViewById(R.id.price_tv);
        titleTv = view.findViewById(R.id.title_tv);
        firstPrizeTv = view.findViewById(R.id.first_prize_tv);
        secondPrizeTv = view.findViewById(R.id.second_prize_tv);
        thirdPrizeTv = view.findViewById(R.id.third_prize_tv);
        durationTv = view.findViewById(R.id.duration_tv);
        timeTv = view.findViewById(R.id.time_tv);
        dateTv = view.findViewById(R.id.date_tv);
        payNowBtn = view.findViewById(R.id.pay_now_btn);
        navController = Navigation.findNavController(view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();

        assert bundle != null;
        titleTv.setText(bundle.getString("mock_title"));
        durationTv.setText(bundle.getString("duration"));
        timeTv.setText(bundle.getString("time"));
        dateTv.setText(bundle.getString("date"));
        firstPrizeTv.setText(String.format("%s (1st)", bundle.getString("firstPrize")));
        secondPrizeTv.setText(String.format("%s (2nd)", bundle.getString("secondPrize")));
        thirdPrizeTv.setText(String.format("%s (3rd)", bundle.getString("thirdPrize")));
        priceTv.setText(String.format("%s/-", bundle.getString("price")));


        payNowBtn.setOnClickListener(view -> {
//            Bundle bundle1 = new Bundle();
//            bundle1.putString("cost", bundle.getString("price"));
//            bundle1.putString("id", bundle.getString("id"));

            Intent intent = new Intent(requireContext(), PaymentActivity.class);
            intent.putExtra("cost", bundle.getString("price"));
            intent.putExtra("id", bundle.getString("id"));
            requireActivity().startActivity(intent);
        });
    }
}