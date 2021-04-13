package com.vidyahaat.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.vidyahaat.R;

public class MyPreference {


    private final SharedPreferences mSharedPreferences;
    private final Context context;


    public MyPreference(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
        this.context = context;
    }

    public void LogOut() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();


    }

    public void writeCurrentTransactionTimeStamp(String timeStamp) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.time_stamp), timeStamp);
        editor.apply();
    }

    public String readCurrentTransactionTimeStamp() {
        return mSharedPreferences.getString(context.getString(R.string.time_stamp), "default");
    }


    public void writeFullUserName(String fullName) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.full_name), fullName);
        editor.apply();

    }

    public String readFullUserName() {

        return mSharedPreferences.getString(context.getString(R.string.full_name), "default");

    }

    public void writePwd(String pwd) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.pwd), pwd);
        editor.apply();

    }

    public String readPwd() {

        return mSharedPreferences.getString(context.getString(R.string.pwd), "default");

    }

    public void writePanNumberType(String panNumberType) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.pan_type), panNumberType);
        editor.apply();
    }

    public String readPanNumberType() {
        return mSharedPreferences.getString(context.getString(R.string.pan_type), "default");

    }

    public void writePanNumber(String panNumber) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.pan), panNumber);
        editor.apply();

    }

    public String readPanNumber() {

        return mSharedPreferences.getString(context.getString(R.string.pan), "default");

    }

    public void writeEmailId(String email) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.email), email);
        editor.apply();

    }

    public String readEmailId() {

        return mSharedPreferences.getString(context.getString(R.string.email), "default");

    }

    public void writeOthersFormFilledStatus(boolean isFilled) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.form_filled), isFilled);
        editor.apply();
    }

    public boolean readOthersFormFilledStatus() {

        return mSharedPreferences.getBoolean(context.getString(R.string.form_filled), false);

    }


    public void writeSignUpMethod(String signUpMethod) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.sign_up_method), signUpMethod);
        editor.apply();


    }

    public String readSignUpMethod() {

        return mSharedPreferences.getString(context.getString(R.string.sign_up_method), "default");


    }


//    public void writeOTP(String otp) {
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        editor.putString(context.getString(R.string.otp), otp);
//        editor.apply();
//
//
//    }
//
//
//    public String readOTP() {
//
//        return mSharedPreferences.getString(context.getString(R.string.otp), "default");
//
//
//    }


    public void writeStudentId(String id) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.std_id), id);
        editor.apply();


    }


    public String readStudentId() {

        return mSharedPreferences.getString(context.getString(R.string.std_id), "default");


    }


    public void writeLoginStatus(boolean loginStatus) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.login_status), loginStatus);
        editor.apply();
    }

    public boolean readLoginStatus() {
        return mSharedPreferences.getBoolean(context.getString(R.string.login_status), false);
    }

    public void writeMobileVerifiedStatus(boolean isVerified) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.verified), isVerified);
        editor.apply();


    }

    public boolean readMobileVerifiedStatus() {

        return mSharedPreferences.getBoolean(context.getString(R.string.verified), false);


    }

    public void writeMobileNo(String mobileNo) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.mobile_no), mobileNo);
        editor.apply();


    }

    public String readMobileNo() {

        return mSharedPreferences.getString(context.getString(R.string.mobile_no), "default");


    }


    public void writeUserProfilePic(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.url), url);
        editor.apply();


    }


    public String readUserProfilePic() {

        return mSharedPreferences.getString(context.getString(R.string.url), "default");


    }

    public void writeStudyIn(String studyIn) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.study_in), studyIn);
        editor.apply();


    }

    public String readStudyIn() {

        return mSharedPreferences.getString(context.getString(R.string.study_in), "default");


    }

    public void writeCollegeOrSchoolName(String collegeOrSchoolName) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.college_or_school_name), collegeOrSchoolName);
        editor.apply();

    }

    public String readCollegeOrSchoolName() {

        return mSharedPreferences.getString(context.getString(R.string.college_or_school_name), "eg. xyz school");

    }

    public void writeClassId(String classId) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.class_id), classId);
        editor.apply();
    }

    public String readClassId() {

        return mSharedPreferences.getString(context.getString(R.string.class_id), "default");


    }

    public void writeStudentType(String type) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.type), type);
        editor.apply();
    }

    public String readStudentType() {
        return mSharedPreferences.getString(context.getString(R.string.type), "default");


    }

    public void writeAboutUs(String about) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.about), about);
        editor.apply();

    }

    public String readAboutUs() {

        return mSharedPreferences.getString(context.getString(R.string.about), "default");

    }

    public void writeCity(String city) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.city), city);
        editor.apply();

    }

    public String readCity() {

        return mSharedPreferences.getString(context.getString(R.string.city), "default");

    }

    public void writeState(String state) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.state), state);
        editor.apply();

    }

    public String readState() {

        return mSharedPreferences.getString(context.getString(R.string.state), "default");
    }

    public void writePin(String pin) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.pin), pin);
        editor.apply();
    }

    public String readPin() {
        return mSharedPreferences.getString(context.getString(R.string.pin), "default");

    }


    public void writeAddress(String address) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(context.getString(R.string.address), address);
        editor.apply();

    }

    public String readAddress() {
        return mSharedPreferences.getString(context.getString(R.string.address), "eg. xyz school");

    }


}
