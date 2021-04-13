package com.vidyahaat.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.vidyahaat.R;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.testper.TestPerformanceModel;
import com.vidyahaat.model.testques.Data;
import com.vidyahaat.model.testques.TestQuestionModel;
import com.vidyahaat.utilities.MyPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestQuestionsFragment extends Fragment {

    TextView questionTv, testCompletePerTv, countDownTv;
    RadioButton optionA, optionB, optionC, optionD;
    RadioGroup questionGrp;
    ImageView nextBtn, prevBtn;
    int currentQuestion = 0, totalQuestion = 0;
    String[] currentQuestionOptions;
    List<Data> questionsList;
    List<String> userChoiceAnswerList = new ArrayList<>();
    String currentChoice;
    String currentQuesAnswer;
    Button submitBtn;
    String testDuration;
    CountDownTimer countDownTimer;
    ProgressBar progressBar;
    MyPreference myPreference;
    long startTimeInMS, timeLeftInMS;
    ConstraintLayout testContainer, alreadyTestSubmittedContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionTv = view.findViewById(R.id.question_tv);
        optionA = view.findViewById(R.id.option_a_btn);
        optionB = view.findViewById(R.id.option_b_btn);
        optionC = view.findViewById(R.id.option_c_btn);
        optionD = view.findViewById(R.id.option_d_btn);
        questionGrp = view.findViewById(R.id.question_grp);
        prevBtn = view.findViewById(R.id.prev_ques_btn);
        nextBtn = view.findViewById(R.id.next_ques_btn);
        countDownTv = view.findViewById(R.id.count_down_tv);
        testContainer = view.findViewById(R.id.test_container);
        progressBar = view.findViewById(R.id.progressBar);
        testCompletePerTv = view.findViewById(R.id.test_complete_per_tv);
        submitBtn = view.findViewById(R.id.submit_btn);
        alreadyTestSubmittedContainer = view.findViewById(R.id.already_test_submit_container);
        myPreference = new MyPreference(requireContext());

        //testData = getIntent().getParcelableExtra("data");
        // assert testData != null;

        assert getArguments() != null;
        testDuration = getArguments().getString("duration");
        Log.i("TestQuestionActivity", "time : " + convertStringToLong());


        startTimer();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentQuestion++;
                if (currentQuestion < totalQuestion) {
                    loadUI(getOptionOfCurrentQuestion(questionsList.get(currentQuestion).getAnsoption()), currentQuestion);
                    questionTv.setText(questionsList.get(currentQuestion).getQuestion());
                    selectOptionOfUserChoice();
                }
            }
        });

        prevBtn.setOnClickListener(view1 -> {
            currentQuestion--;
            loadUI(getOptionOfCurrentQuestion(questionsList.get(currentQuestion).getAnsoption()), currentQuestion);
            questionTv.setText(questionsList.get(currentQuestion).getQuestion());
            selectOptionOfUserChoice();
        });
        questionGrp.setOnCheckedChangeListener((radioGroup, checkedId) -> {

            if (checkedId == R.id.option_a_btn) {

                optionA.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.SRC_OVER);
                optionB.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                optionC.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                optionD.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                currentChoice = optionA.getText().toString().trim();

            } else if (checkedId == R.id.option_b_btn) {

                optionB.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.SRC_OVER);
                optionA.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                optionC.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                optionD.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                currentChoice = optionB.getText().toString().trim();

            } else if (checkedId == R.id.option_c_btn) {

                optionC.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.SRC_OVER);
                optionA.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                optionB.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                optionD.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                currentChoice = optionC.getText().toString().trim();

            } else if (checkedId == R.id.option_d_btn) {

                optionD.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.SRC_OVER);
                optionA.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                optionB.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                optionC.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
                currentChoice = optionD.getText().toString().trim();
            }


            if (currentChoice != null) {
                if (currentChoice.equals(currentQuesAnswer)) {
                    if (currentQuestion >= 0 && currentQuestion < userChoiceAnswerList.size()) {
                        userChoiceAnswerList.set(currentQuestion, "r" + getRbFromText(currentChoice));
                    }

                } else {
                    if (currentQuestion >= 0 && currentQuestion < userChoiceAnswerList.size()) {
                        userChoiceAnswerList.set(currentQuestion, "wr" + getRbFromText(currentChoice));
                    }

                }
            }
            Log.i("TestQuestionActivity", "AnswerListOfUser: " + userChoiceAnswerList);

        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int rightAns = 0;
                double per = 0;
                if (userChoiceAnswerList.size() > 0) {
                    for (String item : userChoiceAnswerList) {
                        if (item.startsWith("r")) {
                            rightAns++;

                        }
                    }
                    per = ((double) rightAns / totalQuestion) * 100;
                }

                Log.i("TestQuestionActivity", "onClick: " + per);
                Log.i("TestQuestionActivity", "onClick: " + userChoiceAnswerList);
                //Toast.makeText(TestQuestionActivity.this, "" + per, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireContext(), TestResultActivity.class);
                intent.putExtra("per", per);
                assert getArguments() != null;
                intent.putExtra("test_id", getArguments().getString("id"));
                startActivity(intent);
            }
        });

    }

    public void selectOptionOfUserChoice() {
        String option;
        String selectedOption;
        option = userChoiceAnswerList.get(currentQuestion);
        selectedOption = option.substring(option.lastIndexOf("r") + 1);

        switch (selectedOption) {
            case "optionA":
                optionA.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.SRC_OVER);
                break;
            case "optionB":
                optionB.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.SRC_OVER);
                break;
            case "optionC":
                optionC.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.SRC_OVER);
                break;
            case "optionD":
                optionD.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.SRC_OVER);
                break;
        }
    }

    public String getRbFromText(String choice) {

        if (choice.equals(optionA.getText().toString().trim())) {
            return "optionA";
        } else if (choice.equals(optionB.getText().toString().trim())) {
            return "optionB";
        } else if (choice.equals(optionC.getText().toString().trim())) {
            return "optionC";
        } else if (choice.equals(optionD.getText().toString().trim())) {
            return "optionD";
        } else {
            return "";
        }

    }

    private String[] getOptionOfCurrentQuestion(String options) {
        return options.split("#&#");
    }


    @SuppressLint("SetTextI18n")
    private void loadUI(String[] options, int currentQuestion) {

        if (!(currentQuestion >= 0 && currentQuestion < userChoiceAnswerList.size())) {
            userChoiceAnswerList.add(currentQuestion, "s");
        }
        currentQuesAnswer = questionsList.get(currentQuestion).getAns();
        Log.i("TestQuestionActivity", "AnswerListOfUser: " + userChoiceAnswerList);
        optionA.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
        optionB.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
        optionC.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
        optionD.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.outline_gray_btn_bg_shape));
        testCompletePerTv.setText((int) (((currentQuestion + 1) / (float) totalQuestion) * 100) + "%");

        if (currentQuestion == 0) {
            prevBtn.setAlpha((float) 0.3);
            prevBtn.setEnabled(false);
        } else {
            prevBtn.setAlpha((float) 1);
            prevBtn.setEnabled(true);
        }


        if (currentQuestion == questionsList.size() - 1) {
            nextBtn.setEnabled(false);
            nextBtn.setAlpha((float) 0.3);
            submitBtn.setVisibility(View.VISIBLE);
        } else {
            nextBtn.setEnabled(true);
            nextBtn.setAlpha((float) 1);
            submitBtn.setVisibility(View.GONE);
        }

        optionA.setText(options[0]);
        optionB.setText(options[1]);
        optionC.setText(options[2]);
        optionD.setText(options[3]);

    }

    public long convertStringToLong() {
        return Long.parseLong(testDuration.substring(0, testDuration.indexOf("m") - 1));
    }

    public void startTimer() {
        startTimeInMS = convertStringToLong() * 60 * 1000;
        timeLeftInMS = startTimeInMS;
        countDownTimer = new CountDownTimer(timeLeftInMS, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMS = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;
        if (getArguments().getString("src").equals("mock_test")) {
            getAllMOckTestQuestions();
        } else {
            getAllQuestion();
            getAlreadySubmittedTest(getArguments().getString("id"));
        }

    }

    private void getAllMOckTestQuestions() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        assert getArguments() != null;
        Call<TestQuestionModel> call = apiInterface.getAllQuestionsOfAMockTest(getArguments().getString("id"));
        call.enqueue(new Callback<TestQuestionModel>() {
            @Override
            public void onResponse(@NonNull Call<TestQuestionModel> call, @NonNull Response<TestQuestionModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        progressBar.setVisibility(View.GONE);
                        testContainer.setVisibility(View.VISIBLE);
                        questionsList = response.body().getData();
                        questionTv.setText(questionsList.get(0).getQuestion());
                        totalQuestion = questionsList.size();
                        // questionsList = testData.getQuestion();
                        prevBtn.setAlpha((float) 0.3);
                        prevBtn.setEnabled(false);
                        currentQuestionOptions = getOptionOfCurrentQuestion(questionsList.get(0).getAnsoption());
                        currentQuesAnswer = questionsList.get(0).getAns();
                        loadUI(currentQuestionOptions, currentQuestion);
                        Log.i("TestQuestionActivity", "onCreate: " + Arrays.toString(currentQuestionOptions));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TestQuestionModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                testContainer.setVisibility(View.GONE);
            }
        });

    }

    private void getAllQuestion() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        assert getArguments() != null;
        Call<TestQuestionModel> call = apiInterface.getAllQuestionsOfATest(getArguments().getString("id"));

        call.enqueue(new Callback<TestQuestionModel>() {
            @Override
            public void onResponse(@NonNull Call<TestQuestionModel> call, @NonNull Response<TestQuestionModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        progressBar.setVisibility(View.GONE);
                        testContainer.setVisibility(View.VISIBLE);
                        questionsList = response.body().getData();
                        questionTv.setText(questionsList.get(0).getQuestion());
                        totalQuestion = questionsList.size();
                        // questionsList = testData.getQuestion();
                        prevBtn.setAlpha((float) 0.3);
                        prevBtn.setEnabled(false);
                        currentQuestionOptions = getOptionOfCurrentQuestion(questionsList.get(0).getAnsoption());
                        currentQuesAnswer = questionsList.get(0).getAns();
                        loadUI(currentQuestionOptions, currentQuestion);
                        Log.i("TestQuestionActivity", "onCreate: " + Arrays.toString(currentQuestionOptions));
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<TestQuestionModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                testContainer.setVisibility(View.GONE);
            }
        });

    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMS / 1000) / 60;
        int sec = (int) (timeLeftInMS / 1000) % 60;
        String formattedTimeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, sec);
        countDownTv.setText(formattedTimeLeft);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void getAlreadySubmittedTest(String testId) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TestPerformanceModel> call = apiInterface.getAllTestResult(myPreference.readStudentId());
        call.enqueue(new Callback<TestPerformanceModel>() {
            @Override
            public void onResponse(@NonNull Call<TestPerformanceModel> call, @NonNull Response<TestPerformanceModel> response) {
                // progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        if (response.body().getData().size() > 0) {
                            int flag = 0;
                            for (com.vidyahaat.model.testper.Data item : response.body().getData()) {

                                if (testId.equals(item.getId())) {
                                    Toast.makeText(requireContext(), "Test Already Attended", Toast.LENGTH_SHORT).show();
                                    flag = 1;

                                }

                            }
                            if (flag == 1) {
                                alreadyTestSubmittedContainer.setVisibility(View.VISIBLE);
                                testContainer.setVisibility(View.GONE);

                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<TestPerformanceModel> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                //  progressBar.setVisibility(View.GONE);
            }
        });

    }

}