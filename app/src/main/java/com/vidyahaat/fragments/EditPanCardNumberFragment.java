package com.vidyahaat.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class EditPanCardNumberFragment extends Fragment {


    TextInputLayout tilPan;
    TextInputEditText panEt;
    RadioGroup panOptions;
    MyPreference myPreferences;
    String panType = "Self";
    Button saveBtn;
    boolean isPanValidated = false;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pan_card_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilPan = view.findViewById(R.id.pan_no_til);
        panEt = view.findViewById(R.id.pan_no_et);
        panOptions = view.findViewById(R.id.radioGroup);
        saveBtn = view.findViewById(R.id.save_btn);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myPreferences = new MyPreference(requireActivity());

        panEt.setText(myPreferences.readPanNumber());

        panOptions.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            if (null != rb) {
                panType = rb.getText().toString();
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
                            saveBtn.setEnabled(true);
                            saveBtn.setAlpha((float) 1);
                            performSaveAndGoBtnEnabledORDisabled();
                            tilPan.setError(null);

                        } else {
                            tilPan.setError("InValid Data");
                            isPanValidated = false;

                            saveBtn.setEnabled(false);
                            saveBtn.setAlpha((float) 0.7);
                            performSaveAndGoBtnEnabledORDisabled();

                        }


                    } else {

                        isPanValidated = false;

                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha((float) 0.7);
                        performSaveAndGoBtnEnabledORDisabled();

                    }

                } catch (Exception e) {

                    isPanValidated = false;

                    saveBtn.setEnabled(false);
                    saveBtn.setAlpha((float) 0.7);
                    performSaveAndGoBtnEnabledORDisabled();

                }


            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePanDetails();
            }
        });


    }

    private void savePanDetails() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.editProfile(myPreferences.readStudentId(), null, null,
                null, null, null, null, null,
                null, null, panType, Objects.requireNonNull(panEt.getText()).toString().trim());

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

    private void performSaveAndGoBtnEnabledORDisabled() {
        if (isPanValidated) {
            saveBtn.setEnabled(true);
            saveBtn.setAlpha((float) 1);
        } else {
            saveBtn.setEnabled(false);
            saveBtn.setAlpha((float) 0.7);
        }
    }
}