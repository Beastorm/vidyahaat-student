package com.vidyahaat.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vidyahaat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vidyahaat.activities.HomeActivity;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.loginuser.Data;
import com.vidyahaat.model.loginuser.LoginUser;
import com.vidyahaat.utilities.MyPreference;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {


    TextInputLayout tilMobile, tilPwd;
    TextInputEditText mobileNoEt, pwdEt;
    boolean isMobileValidated = false, isPwdValidated = false;

    private TextView tapHere, forgetPwdBtn;
    private Context context;
    ProgressBar loginProgressBar;

    ConstraintLayout loginBtn;
    MyPreference myPreferences;

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        tapHere = view.findViewById(R.id.already_account_here);
        loginBtn = view.findViewById(R.id.login_btn);
        tilMobile = view.findViewById(R.id.til_mobile_no);
        tilPwd = view.findViewById(R.id.til_pwd);
        mobileNoEt = view.findViewById(R.id.mobile_no_et);
        pwdEt = view.findViewById(R.id.pwd_et);


        loginProgressBar = view.findViewById(R.id.login_progress_bar);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSignUpTxtBtnSpan();
        loginBtn.setAlpha((float) 0.3);
        loginBtn.setEnabled(false);

        myPreferences = new MyPreference(requireContext());


        mobileNoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    if (editable.toString().length() > 0) {

                        isMobileValidated = true;
                        tilMobile.setError(null);
                        performLoginUpBtnEnabledORDisabled();

                    } else {
                        isMobileValidated = false;
                        tilMobile.setError("Mobile / Email should not be empty");
                        performLoginUpBtnEnabledORDisabled();


                    }

                } catch (Exception e) {
                    isMobileValidated = false;
                    tilMobile.setError("Mobile / Email should not be empty");
                    performLoginUpBtnEnabledORDisabled();

                }

            }
        });

        pwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    if (editable.toString().length() >= 5) {

                        isPwdValidated = true;
                        tilPwd.setError(null);
                        performLoginUpBtnEnabledORDisabled();

                    } else {

                        isPwdValidated = false;
                        tilPwd.setError("Password length should greater or equal to 5 Chars");
                        performLoginUpBtnEnabledORDisabled();
                    }

                } catch (Exception exception) {
                    isPwdValidated = false;
                    tilPwd.setError("Password length should greater or equal to 5 Chars");
                    performLoginUpBtnEnabledORDisabled();

                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
        });
    }

    private void userLogin() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LoginUser> call = apiInterface.performSignIn(Objects.requireNonNull(mobileNoEt.getText()).toString().trim(), Objects.requireNonNull(pwdEt.getText()).toString().trim());
        call.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(@NonNull Call<LoginUser> call, @NonNull Response<LoginUser> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        Data data = response.body().getData().get(0);
                        myPreferences.writeFullUserName(data.getName());
                        myPreferences.writeMobileNo(data.getMobile());
                        myPreferences.writeEmailId(data.getEmail());
                        myPreferences.writeLoginStatus(true);
                        myPreferences.writeStudentId(response.body().getData().get(0).getId());
                        myPreferences.writeAddress(data.getAddress());
                        myPreferences.writeStudyIn(data.getClassDetails().get(0).getName());
                        //   myPreferences.writeStudyIn(data.getSchoolDetails().get(0).getName());
                        myPreferences.writeClassId(data.getClassDetails().get(0).getId());
                        myPreferences.writePanNumberType(data.getPanCardType());
                        myPreferences.writePanNumber(data.getPanCardNumber());

                        Log.i("TAG", "onResponse: " + response.body().getData().get(0).getId());
                        if (data.getSchool().equals("0"))
                            myPreferences.writeStudentType("outsider");
                        else
                            myPreferences.writeStudentType("insider");
                        Intent intent = new Intent(requireContext(), HomeActivity.class);
                        requireActivity().startActivity(intent);
                        requireActivity().finish();

                    } else {
                        Toast.makeText(requireContext(), "Wrong Credential", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginUser> call, @NonNull Throwable t) {
                Log.i("LoginFragment", "onResponse: " + t.getMessage());
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void performLoginUpBtnEnabledORDisabled() {


        if (isMobileValidated) {

            if (isPwdValidated) {
                loginBtn.setEnabled(true);
                loginBtn.setAlpha((float) 1);
            } else {
                loginBtn.setEnabled(false);
                loginBtn.setAlpha((float) 0.3);
            }
        } else {

            loginBtn.setEnabled(false);
            loginBtn.setAlpha((float) 0.3);
        }


        //Toast.makeText(context, "" + sigUpBtn.isEnabled(), Toast.LENGTH_SHORT).show();
    }

    private void initSignUpTxtBtnSpan() {
        String completeString = tapHere.getText().toString().trim();
        String partToClick = "here";
        TextView createAccount = createLink(tapHere, completeString, partToClick,
                new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        navController.navigate(R.id.signUpFragment);
                        // onFragmentListener.setOnFragment("create account");

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        // this is where you set link color, underline, typeface etc.
                        int linkColor = ContextCompat.getColor(requireContext(), R.color.design_default_color_primary);
                        ds.setColor(linkColor);
                        ds.setUnderlineText(false);
                    }
                });


    }


    private static TextView createLink(TextView targetTextView, String completeString,
                                       String partToClick, ClickableSpan clickableAction) {

        SpannableString spannableString = new SpannableString(completeString);

        // make sure the String is exist, if it doesn't exist
        // it will throw IndexOutOfBoundException
        int startPosition = completeString.indexOf(partToClick);
        int endPosition = completeString.lastIndexOf(partToClick) + partToClick.length();

        spannableString.setSpan(clickableAction, startPosition, endPosition,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        targetTextView.setText(spannableString);
        targetTextView.setMovementMethod(LinkMovementMethod.getInstance());

        return targetTextView;
    }
}