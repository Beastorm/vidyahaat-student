package com.vidyahaat.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vidyahaat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.utilities.MyPreference;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordFragment extends Fragment {

    TextInputLayout tilOldPwd, tilNewPwd;
    TextInputEditText etNewPwd, etOldPwd;
    NavController navController;

    Button changeBtn;
    boolean isOldValidated = false, isNewValidated = false;
    MyPreference myPreferences;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tilNewPwd = view.findViewById(R.id.til_new_pwd);
        tilOldPwd = view.findViewById(R.id.til_old_pwd);
        etNewPwd = view.findViewById(R.id.et_new_pwd);
        etOldPwd = view.findViewById(R.id.et_old_pwd);
        navController = Navigation.findNavController(view);
        changeBtn = view.findViewById(R.id.changeBtn);
        myPreferences = new MyPreference(requireContext());

        progressBar = view.findViewById(R.id.progressBar);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        etOldPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (editable.toString().trim().length() >= 5) {
                        isOldValidated = true;
                        tilOldPwd.setError(null);
                    } else {
                        isOldValidated = false;
                        tilOldPwd.setError("Password Must be greater or equal to 5 chars");
                    }
                    performEnableDisabledBtn();

                } catch (Exception e) {
                    isOldValidated = false;
                    tilOldPwd.setError("Password Must be greater or equal to 5 chars");
                    performEnableDisabledBtn();
                }
            }
        });

        etNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    if (editable.toString().trim().length() >= 5) {
                        isNewValidated = true;
                        tilNewPwd.setError(null);
                    } else {
                        isNewValidated = false;
                        tilNewPwd.setError("Password Must be greater or equal to 5 chars");
                    }
                    performEnableDisabledBtn();

                } catch (Exception e) {
                    isNewValidated = false;
                    tilNewPwd.setError("Password Must be greater or equal to 5 chars");
                    performEnableDisabledBtn();
                }

            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Objects.requireNonNull(etOldPwd.getText()).toString().trim().equals(myPreferences.readPwd())) {
                    saveNewPassword();
                } else {
                    Toast.makeText(requireContext(), "Enter Correct Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void performEnableDisabledBtn() {

        if (isNewValidated) {
            if (isOldValidated) {
                changeBtn.setAlpha(1);
                changeBtn.setEnabled(true);

            } else {
                changeBtn.setAlpha((float) 0.3);
                changeBtn.setEnabled(false);
            }

        } else {
            changeBtn.setAlpha((float) 0.3);
            changeBtn.setEnabled(false);
        }
    }

    private void saveNewPassword() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = apiInterface.editProfile(myPreferences.readStudentId(), null, null, null, null,
                Objects.requireNonNull(etNewPwd.getText()).toString().trim(), null, null, null, null, null, null);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Changed Successfully", Toast.LENGTH_SHORT).show();
                    navController.navigateUp();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}