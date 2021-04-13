package com.vidyahaat.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.vidyahaat.utilities.MyPreference;


public class SignUpFragment extends Fragment {

    TextView tapHere;
    NavController navController;

   // public static final String TAG = "SignUpFragment";
    ProgressBar progressBar;


    TextInputLayout tilMobileNo, tilEmail, tilPwd, tilName;
    TextInputEditText mobileNoEt, emailEt, pwdEt, nameEt;
    ConstraintLayout next_btn;

    boolean isMobileValidated = false, isEmailValidated = false, isPwdValidated = false, isNameValidated = false;


    MyPreference myPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tapHere = view.findViewById(R.id.already_account_here);

        tilMobileNo = view.findViewById(R.id.til_mobile_no);
        tilEmail = view.findViewById(R.id.til_email_id);
        tilPwd = view.findViewById(R.id.til_pwd);
        tilName = view.findViewById(R.id.til_name);

        mobileNoEt = view.findViewById(R.id.mobile_no_et);
        emailEt = view.findViewById(R.id.email_id_et);
        pwdEt = view.findViewById(R.id.pwd_et);
        nameEt = view.findViewById(R.id.name_et);

        next_btn = view.findViewById(R.id.sign_up_btn);
        progressBar = view.findViewById(R.id.progressBar);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSignUpTxtBtnSpan();

        nameEt.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                // String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#$%^&*()\\-_+={}\\[\\]|;:\"<>,./?]).{8,}";
                try {

                    if (!TextUtils.isEmpty(s.toString())) {
                        if (s.toString().length() > 1) {

                            isNameValidated = true;
                            next_btn.setEnabled(true);
                            next_btn.setAlpha((float) 1);
                            performSignUpBtnEnabledORDisabled();
                            tilName.setError(null);


                        } else {
                            isNameValidated = false;
                            next_btn.setEnabled(false);
                            next_btn.setAlpha((float) 0.7);
                            performSignUpBtnEnabledORDisabled();
                            // tilName.setError("Use A-Z, a-z, Special Symbol, 0-9 and Must At\nleast Be 8 Characters Long");
                            tilName.setError("Name must be 1 chars long");
                        }

                    } else {
                        isNameValidated = false;
                        next_btn.setEnabled(false);
                        next_btn.setAlpha((float) 0.7);
                        performSignUpBtnEnabledORDisabled();
                    }

                } catch (Exception e) {
                    isNameValidated = false;

                    next_btn.setEnabled(false);
                    next_btn.setAlpha((float) 0.7);
                    performSignUpBtnEnabledORDisabled();
                }
            }
        });
        mobileNoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String regex = "^[6-9]\\d{9}$";

                try {

                    if (!TextUtils.isEmpty(s.toString())) {
                        if (s.toString().matches(regex)) {

                            isMobileValidated = true;

                            next_btn.setEnabled(true);
                            next_btn.setAlpha((float) 1);
                            performSignUpBtnEnabledORDisabled();
                            tilMobileNo.setError(null);

                        } else {


                            tilMobileNo.setError("InValid Mobile No");
                            isMobileValidated = false;

                            next_btn.setEnabled(false);
                            next_btn.setAlpha((float) 0.7);
                            performSignUpBtnEnabledORDisabled();

                        }


                    } else {
                        isMobileValidated = false;

                        next_btn.setEnabled(false);
                        next_btn.setAlpha((float) 0.7);
                        performSignUpBtnEnabledORDisabled();


                    }

                } catch (Exception e) {
                    isMobileValidated = false;

                    next_btn.setEnabled(false);
                    next_btn.setAlpha((float) 0.7);
                    performSignUpBtnEnabledORDisabled();

                }
            }
        });

        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String regexEmail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
                try {
                    if (!TextUtils.isEmpty(s.toString())) {
                        if (s.toString().matches(regexEmail)) {

                            isEmailValidated = true;

                            next_btn.setEnabled(true);
                            next_btn.setAlpha((float) 1);
                            performSignUpBtnEnabledORDisabled();
                            tilEmail.setError(null);


                        } else {
                            isEmailValidated = false;

                            next_btn.setEnabled(false);
                            next_btn.setAlpha((float) 0.7);
                            performSignUpBtnEnabledORDisabled();
                            tilEmail.setError("Use A-Z, a-z, Special Symbol, 0-9 and Must At least Be 8 Characters Long");

                        }
                    } else {
                        isEmailValidated = false;

                        next_btn.setEnabled(false);
                        next_btn.setAlpha((float) 0.7);
                        performSignUpBtnEnabledORDisabled();
                        //tilName.setError("Name Must Be Grater than 5 Characters");

                    }
                } catch (Exception e) {
                    isEmailValidated = false;

                    next_btn.setEnabled(false);
                    next_btn.setAlpha((float) 0.7);
                    performSignUpBtnEnabledORDisabled();


                }
            }
        });

        pwdEt.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                // String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#$%^&*()\\-_+={}\\[\\]|;:\"<>,./?]).{8,}";
                try {

                    if (!TextUtils.isEmpty(s.toString())) {
                        if (s.toString().length() >= 5) {

                            isPwdValidated = true;
                            next_btn.setEnabled(true);
                            next_btn.setAlpha((float) 1);
                            performSignUpBtnEnabledORDisabled();
                            tilPwd.setError(null);


                        } else {
                            isPwdValidated = false;
                            next_btn.setEnabled(false);
                            next_btn.setAlpha((float) 0.7);
                            performSignUpBtnEnabledORDisabled();
                            tilPwd.setError("Must be 5 Chars Long");
                        }

                    } else {
                        isPwdValidated = false;
                        next_btn.setEnabled(false);
                        next_btn.setAlpha((float) 0.7);
                        performSignUpBtnEnabledORDisabled();
                    }

                } catch (Exception e) {
                    isPwdValidated = false;

                    next_btn.setEnabled(false);
                    next_btn.setAlpha((float) 0.7);
                    performSignUpBtnEnabledORDisabled();
                }
            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", nameEt.getText().toString().trim());
                bundle.putString("mobile", mobileNoEt.getText().toString().trim());
                bundle.putString("email", emailEt.getText().toString().trim());
                bundle.putString("pwd", pwdEt.getText().toString().trim());
                navController.navigate(R.id.action_signUpFragment_to_othersInfoFragment2, bundle);
                // signUpProcess();
            }
        });
    }


    public void performSignUpBtnEnabledORDisabled() {
        if (isEmailValidated) {

            if (isMobileValidated) {

                if (isPwdValidated) {

                    if (isNameValidated) {
                        next_btn.setEnabled(true);
                        next_btn.setAlpha((float) 1);
                    } else {
                        next_btn.setEnabled(false);
                        next_btn.setAlpha((float) 0.7);

                    }

                } else {
                    next_btn.setEnabled(false);
                    next_btn.setAlpha((float) 0.7);


                }
            } else {

                next_btn.setEnabled(false);
                next_btn.setAlpha((float) 0.7);
            }


        } else {
            next_btn.setEnabled(false);
            next_btn.setAlpha((float) 0.7);
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
                        // your action

                        navController.navigate(R.id.loginFragment);
                        //onFragmentListener1.setOnFragment("already account");

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