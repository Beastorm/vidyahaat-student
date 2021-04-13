package com.vidyahaat.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vidyahaat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.utilities.MyPreference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailsFragment extends Fragment {

    TextInputLayout bankNameTil, bankBranchTil, bankIfscTil, bankAccountNoTil;
    TextInputEditText bankNameEt, bankBranchEt, bankIfscEt, bankAccountNoEt;
    ConstraintLayout saveBtn;
    MyPreference myPreferences;
    boolean isBankNameValidated = false, isBranchNameValidated = false, isIfscValidated = false, isAccountNoValidated = false;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bankNameTil = view.findViewById(R.id.til_bank_name_id);
        bankAccountNoTil = view.findViewById(R.id.til_account_no);
        bankBranchTil = view.findViewById(R.id.til_branch_name);
        bankIfscTil = view.findViewById(R.id.til_ifsc);
        bankNameEt = view.findViewById(R.id.bank_name_id_et);
        bankAccountNoEt = view.findViewById(R.id.account_no_et);
        bankBranchEt = view.findViewById(R.id.branch_name_et);
        bankIfscEt = view.findViewById(R.id.ifsc_et);
        saveBtn = view.findViewById(R.id.save_btn);
        myPreferences = new MyPreference(requireContext());
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        performEnableOrDisabled();
        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.getString("src").equals("profileEdit")) {
                bankNameEt.setText(bundle.getString("bank_name"));
                bankBranchEt.setText(bundle.getString("bank_branch"));
                bankAccountNoEt.setText(bundle.getString("account_no"));
                bankIfscEt.setText(bundle.getString("ifsc"));
                isBankNameValidated = true;
                isBranchNameValidated = true;
                isIfscValidated = true;
                isAccountNoValidated = true;
                performEnableOrDisabled();
            }
        }


        bankNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if (!TextUtils.isEmpty(s.toString().trim())) {

                        if (s.toString().trim().length() > 1) {
                            isBankNameValidated = true;
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performEnableOrDisabled();
                            bankNameTil.setError(null);

                        } else {
                            bankNameTil.setError("InValid Data");
                            isBankNameValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performEnableOrDisabled();

                        }


                    } else {

                        isBankNameValidated = false;
                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performEnableOrDisabled();


                    }

                } catch (Exception e) {

                    isBankNameValidated = false;
                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
                    performEnableOrDisabled();
                }

            }
        });
        bankBranchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if (!TextUtils.isEmpty(s.toString().trim())) {

                        if (s.toString().trim().length() > 1) {
                            isBranchNameValidated = true;
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performEnableOrDisabled();
                            bankBranchTil.setError(null);

                        } else {
                            bankBranchTil.setError("InValid Data");
                            isBranchNameValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performEnableOrDisabled();

                        }


                    } else {

                        isBranchNameValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performEnableOrDisabled();


                    }

                } catch (Exception e) {

                    isBranchNameValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
                    performEnableOrDisabled();


                }

            }
        });

        bankAccountNoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if (!TextUtils.isEmpty(s.toString().trim())) {

                        if (s.toString().trim().length() > 1) {
                            isAccountNoValidated = true;
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performEnableOrDisabled();
                            bankAccountNoTil.setError(null);

                        } else {
                            bankAccountNoTil.setError("InValid Data");
                            isAccountNoValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performEnableOrDisabled();

                        }


                    } else {

                        isAccountNoValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performEnableOrDisabled();


                    }

                } catch (Exception e) {

                    isAccountNoValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
                    performEnableOrDisabled();


                }

            }
        });

        bankIfscEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if (!TextUtils.isEmpty(s.toString().trim())) {

                        if (s.toString().trim().length() > 1) {
                            isIfscValidated = true;
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performEnableOrDisabled();
                            bankIfscTil.setError(null);

                        } else {
                            bankIfscTil.setError("InValid Data");
                            isIfscValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performEnableOrDisabled();

                        }


                    } else {

                        isIfscValidated = false;
                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performEnableOrDisabled();


                    }

                } catch (Exception e) {

                    isIfscValidated = false;
                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
                    performEnableOrDisabled();


                }

            }
        });

        saveBtn.setOnClickListener(view -> {
            saveBankDetails();

        });
    }

    private void saveBankDetails() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.editProfile(myPreferences.readStudentId(), null, null,
                null, null, null, bankNameEt.getText().toString().trim(), bankBranchEt.getText().toString().trim(),
                bankAccountNoEt.getText().toString().trim(), bankIfscEt.getText().toString().trim(), null, null);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                    navController.navigateUp();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    void performEnableOrDisabled() {

        if (isBankNameValidated) {

            if (isBranchNameValidated) {
                if (isAccountNoValidated) {
                    if (isIfscValidated) {
                        saveBtn.setEnabled(true);
                        saveBtn.setAlpha(1);
                    } else {
                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                    }

                } else {
                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
                }

            } else {
                saveBtn.setEnabled(false);
                saveBtn.setAlpha((float) 0.3);
            }
        } else {
            saveBtn.setEnabled(false);
            saveBtn.setAlpha((float) 0.3);
        }
    }
}