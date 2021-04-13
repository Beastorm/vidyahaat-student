package com.vidyahaat.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditAddressFragment extends Fragment {

    ProgressBar otherInfoProgressBar;
    TextInputLayout tilStudyIn, tilFlatNo, tilLocality, tilCity, tilPin, tilState;
    TextInputEditText studyInEt, flatEt, localityEt, cityEt, pinEt, stateEt;
    boolean isStudyInValidated = true, isFlatNoValidated = false, isLocalityValidated = false,
            isCityValidated = false, isPinValidated = false, isStateValidated = false;
    Button saveBtn;
    MyPreference myPreferences;
    NavController navController;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_address, container, false);
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
        studyInEt = view.findViewById(R.id.study_in_et);
        flatEt = view.findViewById(R.id.flat_no_et);
        localityEt = view.findViewById(R.id.locality_et);
        cityEt = view.findViewById(R.id.city_et);
        pinEt = view.findViewById(R.id.pin_code_et);
        stateEt = view.findViewById(R.id.state_et);
        myPreferences = new MyPreference(requireContext());
        otherInfoProgressBar = view.findViewById(R.id.others_info_progress_bar);
        otherInfoProgressBar.setVisibility(View.GONE);
        saveBtn = view.findViewById(R.id.save_and_go_btn);
        progressBar = view.findViewById(R.id.progressBar);


        navController = Navigation.findNavController(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        saveBtn.setEnabled(false);
        performSaveAndGoBtnEnabledORDisabled();

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
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilStudyIn.setError(null);

                        } else {
                            tilStudyIn.setError("InValid Data");
                            isStudyInValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {
                        isStudyInValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {
                    isStudyInValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
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
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilFlatNo.setError(null);

                        } else {
                            tilFlatNo.setError("InValid Data");
                            isFlatNoValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isFlatNoValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {

                    isFlatNoValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
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
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilLocality.setError(null);

                        } else {
                            tilLocality.setError("InValid Data");
                            isLocalityValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isLocalityValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {

                    isLocalityValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
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
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilCity.setError(null);

                        } else {
                            tilCity.setError("InValid Data");
                            isCityValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isCityValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {

                    isCityValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
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
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilPin.setError(null);

                        } else {
                            tilPin.setError("InValid PIN");
                            isPinValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isPinValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performSaveAndGoBtnEnabledORDisabled();


                    }

                } catch (Exception e) {

                    isPinValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
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
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilState.setError(null);

                        } else {
                            tilState.setError("InValid Data");
                            isStateValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.3);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isStateValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.3);
                        performSaveAndGoBtnEnabledORDisabled();

                    }

                } catch (Exception e) {

                    isStateValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.3);
                    performSaveAndGoBtnEnabledORDisabled();

                }


            }
        });

        saveBtn.setOnClickListener(view -> {
            saveNewAddress();
        });


    }

    private void saveNewAddress() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = apiInterface.editProfile(myPreferences.readStudentId(), null, null, null, getAddress(), null, null, null, null, null,null,null);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
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

    public void performSaveAndGoBtnEnabledORDisabled() {


        if (isStudyInValidated) {

            if (isFlatNoValidated) {
                if (isLocalityValidated) {

                    if (isCityValidated) {

                        if (isPinValidated) {

                            if (isStateValidated) {
                                saveBtn.setEnabled(true);
                                saveBtn.setAlpha((float) 1);

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
            } else {
                saveBtn.setEnabled(false);
                saveBtn.setAlpha((float) 0.3);


            }

        } else {
            saveBtn.setEnabled(false);
            saveBtn.setAlpha((float) 0.3);
        }


    }

    private String getAddress() {

        return flatEt.getText().toString().trim() + ", " + localityEt.getText().toString().trim() + ", " + cityEt.getText().toString().trim() + "\n" + stateEt.getText().toString().trim() + " - " + pinEt.getText().toString().trim();
    }

}