package com.vidyahaat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vidyahaat.R;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.viewprofilepackage.Data;
import com.vidyahaat.model.viewprofilepackage.ViewProfileModel;
import com.vidyahaat.utilities.MyPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    EditText fullNameEt, mobileNoEt, study_in_et, school_or_college_name_et;
    TextView addressTV;
    MyPreference myPreference;
    ImageView editAddressBtn, editNameBtn, editBankDetailsBtn;
    NavController navController;
    Button changePwdBtn, bankDetailBtn, changePanBtn;
    ConstraintLayout bankDetailsContainer;
    Data data;
    TextView bankNameTv, branchNameTv, accountNoTv, ifscCodeTv, panCardNoTv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mobileNoEt = view.findViewById(R.id.mobile_no_et);
        fullNameEt = view.findViewById(R.id.full_name_et);
        study_in_et = view.findViewById(R.id.study_in_et);
        school_or_college_name_et = view.findViewById(R.id.school_or_college_name_et);
        addressTV = view.findViewById(R.id.address_details_tv);
        editAddressBtn = view.findViewById(R.id.edit_address_btn);

        myPreference = new MyPreference(requireContext());

        navController = Navigation.findNavController(view);
        editNameBtn = view.findViewById(R.id.name_edit_btn);
        changePwdBtn = view.findViewById(R.id.change_pwd_btn);
        bankDetailBtn = view.findViewById(R.id.bank_details_btn);
        editBankDetailsBtn = view.findViewById(R.id.edit_bank_detail_btn);
        bankDetailsContainer = view.findViewById(R.id.bank_details_container);

        bankNameTv = view.findViewById(R.id.bank_name_tv);
        branchNameTv = view.findViewById(R.id.branch_name_tv);
        accountNoTv = view.findViewById(R.id.account_number_tv);
        ifscCodeTv = view.findViewById(R.id.ifsc_tv);
        panCardNoTv = view.findViewById(R.id.pan_card_no_tv);
        changePanBtn = view.findViewById(R.id.change_pan_btn);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewProfile();
        mobileNoEt.setText(myPreference.readMobileNo());
        fullNameEt.setText(myPreference.readFullUserName());
        addressTV.setText(myPreference.readAddress());
        study_in_et.setText(myPreference.readStudyIn());

        school_or_college_name_et.setText(myPreference.readCollegeOrSchoolName());


        editAddressBtn.setOnClickListener(view -> navController.navigate(R.id.editAddressFragment));
        editNameBtn.setOnClickListener(view -> {

        });

        changePwdBtn.setOnClickListener(view -> navController.navigate(R.id.changePasswordFragment));
        bankDetailBtn.setOnClickListener(view -> navController.navigate(R.id.bankDetailsFragment));


        changePanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.editPanCardNumberFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewProfile();
    }

    private void viewProfile() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ViewProfileModel> call = apiInterface.viewProfile(myPreference.readStudentId());

        call.enqueue(new Callback<ViewProfileModel>() {
            @Override
            public void onResponse(Call<ViewProfileModel> call, Response<ViewProfileModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Data data = response.body().getData().get(0);
                    panCardNoTv.setText(String.format("Pan Card Number: %s", data.getPancard_number()));
                    if (!(data.getBank_account().equals(""))) {
                        bankDetailsContainer.setVisibility(View.VISIBLE);
                        bankDetailBtn.setVisibility(View.GONE);
                        bankNameTv.setText(String.format("Bank Name: %s", data.getBank_name()));
                        branchNameTv.setText(String.format("Bank Branch: %s", data.getBranch()));
                        accountNoTv.setText(String.format("Account Number: %s", data.getBank_account()));
                        ifscCodeTv.setText(String.format("IFSC Code: %s", data.getIfsc_code()));

                        myPreference.writePanNumber(data.getPancard_number());
                        myPreference.writePanNumberType(data.getPancard_type());


                    }
                    editBankDetailsBtn.setOnClickListener(view -> {

                        Bundle bundle = new Bundle();
                        bundle.putString("src", "profileEdit");
                        bundle.putString("bank_name", data.getBank_name());
                        bundle.putString("bank_branch", "mock_test");
                        bundle.putString("account_no", data.getBranch());
                        bundle.putString("ifsc", data.getBank_account());
                        navController.navigate(R.id.action_profileFragment_to_bankDetailsFragment, bundle);

                    });
                }
            }

            @Override
            public void onFailure(Call<ViewProfileModel> call, Throwable t) {

            }
        });
    }
}