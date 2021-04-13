package com.vidyahaat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vidyahaat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vidyahaat.activities.RegisterActivity;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.signup.UserSignUp;
import com.vidyahaat.utilities.MyPreference;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OthersInfoFragment extends Fragment {

    ProgressBar otherInfoProgressBar;
    RadioGroup panOptions;
    TextInputLayout tilStudyIn, tilFlatNo, tilLocality, tilCity, tilPin, tilState, tilPan;
    TextInputEditText studyInEt, flatEt, localityEt, cityEt, pinEt, stateEt, panEt;
    boolean isStudyInValidated = true, isFlatNoValidated = false, isLocalityValidated = false,
            isCityValidated = false, isPinValidated = false, isStateValidated = false, isPanValidated = false;

    Button signUpBtn;
    MyPreference myPreferences;
    String panType = "Self";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_others_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        tilStudyIn = view.findViewById(R.id.study_in_til);
        tilFlatNo = view.findViewById(R.id.flat_no_til);
        tilLocality = view.findViewById(R.id.locality_til);
        tilCity = view.findViewById(R.id.city_til);
        tilPin = view.findViewById(R.id.pin_code_til);
        tilState = view.findViewById(R.id.state_til);
        tilPan = view.findViewById(R.id.pan_no_til);


        studyInEt = view.findViewById(R.id.study_in_et);
        flatEt = view.findViewById(R.id.flat_no_et);
        localityEt = view.findViewById(R.id.locality_et);
        cityEt = view.findViewById(R.id.city_et);
        pinEt = view.findViewById(R.id.pin_code_et);
        stateEt = view.findViewById(R.id.state_et);
        panEt = view.findViewById(R.id.pan_no_et);
        panOptions = view.findViewById(R.id.radioGroup);
        myPreferences = new MyPreference(requireContext());
        otherInfoProgressBar = view.findViewById(R.id.others_info_progress_bar);
        otherInfoProgressBar.setVisibility(View.GONE);
        signUpBtn = view.findViewById(R.id.save_and_go_btn);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        signUpBtn.setEnabled(false);
        performSaveAndGoBtnEnabledORDisabled();

        panOptions.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            if (null != rb) {
                panType = rb.getText().toString();
            }

        });

        studyInEt.addTextChangedListener(new TextWatcher() {
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
                            isStudyInValidated = true;
                            signUpBtn.setEnabled(true);
                            signUpBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilStudyIn.setError(null);

                        } else {
                            tilStudyIn.setError("InValid Data");
                            isStudyInValidated = false;

                            signUpBtn.setEnabled(false);
                            signUpBtn.setAlpha((float) 0.7);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {
                        isStudyInValidated = false;

                        signUpBtn.setEnabled(false);
                        signUpBtn.setAlpha((float) 0.7);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {
                    isStudyInValidated = false;

                    signUpBtn.setEnabled(false);
                    signUpBtn.setAlpha((float) 0.7);
                    performSaveAndGoBtnEnabledORDisabled();


                }

            }
        });


        flatEt.addTextChangedListener(new TextWatcher() {
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
                            isFlatNoValidated = true;
                            signUpBtn.setEnabled(true);
                            signUpBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilFlatNo.setError(null);

                        } else {
                            tilFlatNo.setError("InValid Data");
                            isFlatNoValidated = false;

                            signUpBtn.setEnabled(false);
                            signUpBtn.setAlpha((float) 0.7);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isFlatNoValidated = false;

                        signUpBtn.setEnabled(false);
                        signUpBtn.setAlpha((float) 0.7);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {

                    isFlatNoValidated = false;

                    signUpBtn.setEnabled(false);
                    signUpBtn.setAlpha((float) 0.7);
                    performSaveAndGoBtnEnabledORDisabled();


                }

            }
        });


        localityEt.addTextChangedListener(new TextWatcher() {
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
                            isLocalityValidated = true;
                            signUpBtn.setEnabled(true);
                            signUpBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilLocality.setError(null);

                        } else {
                            tilLocality.setError("InValid Data");
                            isLocalityValidated = false;

                            signUpBtn.setEnabled(false);
                            signUpBtn.setAlpha((float) 0.7);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isLocalityValidated = false;

                        signUpBtn.setEnabled(false);
                        signUpBtn.setAlpha((float) 0.7);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {

                    isLocalityValidated = false;

                    signUpBtn.setEnabled(false);
                    signUpBtn.setAlpha((float) 0.7);
                    performSaveAndGoBtnEnabledORDisabled();


                }

            }
        });


        cityEt.addTextChangedListener(new TextWatcher() {
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
                            isCityValidated = true;
                            signUpBtn.setEnabled(true);
                            signUpBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilCity.setError(null);

                        } else {
                            tilCity.setError("InValid Data");
                            isCityValidated = false;

                            signUpBtn.setEnabled(false);
                            signUpBtn.setAlpha((float) 0.7);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isCityValidated = false;

                        signUpBtn.setEnabled(false);
                        signUpBtn.setAlpha((float) 0.7);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {

                    isCityValidated = false;

                    signUpBtn.setEnabled(false);
                    signUpBtn.setAlpha((float) 0.7);
                    performSaveAndGoBtnEnabledORDisabled();

                }

            }
        });

        pinEt.addTextChangedListener(new TextWatcher() {
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

                        if (s.toString().trim().length() == 6) {
                            isPinValidated = true;
                            signUpBtn.setEnabled(true);
                            signUpBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilPin.setError(null);

                        } else {
                            tilPin.setError("InValid PIN");
                            isPinValidated = false;

                            signUpBtn.setEnabled(false);
                            signUpBtn.setAlpha((float) 0.7);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isPinValidated = false;

                        signUpBtn.setEnabled(false);
                        signUpBtn.setAlpha((float) 0.7);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {

                    isPinValidated = false;

                    signUpBtn.setEnabled(false);
                    signUpBtn.setAlpha((float) 0.7);
                    performSaveAndGoBtnEnabledORDisabled();

                }


            }
        });

        stateEt.addTextChangedListener(new TextWatcher() {
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
                            isStateValidated = true;
                            signUpBtn.setEnabled(true);
                            signUpBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilState.setError(null);

                        } else {
                            tilState.setError("InValid Data");
                            isStateValidated = false;

                            signUpBtn.setEnabled(false);
                            signUpBtn.setAlpha((float) 0.7);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isStateValidated = false;

                        signUpBtn.setEnabled(false);
                        signUpBtn.setAlpha((float) 0.7);
                        performSaveAndGoBtnEnabledORDisabled();

                    }

                } catch (Exception e) {

                    isStateValidated = false;

                    signUpBtn.setEnabled(false);
                    signUpBtn.setAlpha((float) 0.7);
                    performSaveAndGoBtnEnabledORDisabled();

                }


            }
        });

        panEt.addTextChangedListener(new TextWatcher() {
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
                            isPanValidated = true;
                            signUpBtn.setEnabled(true);
                            signUpBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilPan.setError(null);

                        } else {
                            tilPan.setError("InValid Data");
                            isPanValidated = false;

                            signUpBtn.setEnabled(false);
                            signUpBtn.setAlpha((float) 0.7);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isPanValidated = false;

                        signUpBtn.setEnabled(false);
                        signUpBtn.setAlpha((float) 0.7);
                        performSaveAndGoBtnEnabledORDisabled();

                    }

                } catch (Exception e) {

                    isPanValidated = false;

                    signUpBtn.setEnabled(false);
                    signUpBtn.setAlpha((float) 0.7);
                    performSaveAndGoBtnEnabledORDisabled();

                }


            }
        });

        signUpBtn.setOnClickListener(view -> signUpProcess());


    }

    public void performSaveAndGoBtnEnabledORDisabled() {


        if (isStudyInValidated) {

            if (isFlatNoValidated) {
                if (isLocalityValidated) {

                    if (isCityValidated) {

                        if (isPinValidated) {

                            if (isStateValidated) {

                                if (isPanValidated) {
                                    signUpBtn.setEnabled(true);
                                    signUpBtn.setAlpha((float) 1);
                                } else {
                                    signUpBtn.setEnabled(false);
                                    signUpBtn.setAlpha((float) 0.7);
                                }

                            } else {

                                signUpBtn.setEnabled(false);
                                signUpBtn.setAlpha((float) 0.7);
                            }
                        } else {
                            signUpBtn.setEnabled(false);
                            signUpBtn.setAlpha((float) 0.7);

                        }

                    } else {
                        signUpBtn.setEnabled(false);
                        signUpBtn.setAlpha((float) 0.7);
                    }
                }
            } else {
                signUpBtn.setEnabled(false);
                signUpBtn.setAlpha((float) 0.7);


            }

        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setAlpha((float) 0.7);
        }


        // Toast.makeText(context, "" + saveAndGoBtn.isEnabled(), Toast.LENGTH_SHORT).show();


    }

    private String getAddress() {

        return Objects.requireNonNull(flatEt.getText()).toString().trim() + ", " + Objects.requireNonNull(localityEt.getText()).toString().trim() + ", " + Objects.requireNonNull(cityEt.getText()).toString().trim() + "\n" + Objects.requireNonNull(stateEt.getText()).toString().trim() + " - " + Objects.requireNonNull(pinEt.getText()).toString().trim();
    }

    private void signUpProcess() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        otherInfoProgressBar.setVisibility(View.VISIBLE);
        assert getArguments() != null;
        Call<UserSignUp> call = apiInterface.signUp(getArguments().getString("name"), getArguments().getString("email"),
                getArguments().getString("mobile"), getArguments().getString("pwd"), getAddress(), panType, Objects.requireNonNull(panEt.getText()).toString().trim());

        call.enqueue(new Callback<UserSignUp>() {
            @Override
            public void onResponse(@NonNull Call<UserSignUp> call, @NonNull Response<UserSignUp> response) {
                otherInfoProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successfull")) {
                        Intent intent = new Intent(requireContext(), RegisterActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                        Toast.makeText(requireContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Already SignUp", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserSignUp> call, @NonNull Throwable t) {
                otherInfoProgressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}