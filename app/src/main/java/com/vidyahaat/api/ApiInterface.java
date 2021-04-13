package com.vidyahaat.api;

import com.vidyahaat.model.assignment.AssignmentModel;
import com.vidyahaat.model.chapter.SubjectModel;
import com.vidyahaat.model.chathistory.ChatHistoryModel;
import com.vidyahaat.model.course.CourseModel;
import com.vidyahaat.model.homescreen.HomeModel;
import com.vidyahaat.model.loginuser.LoginUser;
import com.vidyahaat.model.order.OrderModel;
import com.vidyahaat.model.paytmmodel.PaytmChecksum;
import com.vidyahaat.model.signup.UserSignUp;
import com.vidyahaat.model.teacher.TeacherModel;
import com.vidyahaat.model.test.TestCourseModel;
import com.vidyahaat.model.testper.TestPerformanceModel;
import com.vidyahaat.model.testques.TestQuestionModel;
import com.vidyahaat.model.uploadassignment.UploadAssignmentModel;
import com.vidyahaat.model.video.VideoPlayListModel;
import com.vidyahaat.model.viewassignment.ViewAssignmentModel;
import com.vidyahaat.model.viewprofilepackage.ViewProfileModel;
import com.vidyahaat.model.watchlist.WatchListModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/studentLogin")
    Call<LoginUser> performSignIn(
            @Field("email") String email,
            @Field("password") String pwd
    );


    @FormUrlEncoded
    @POST("api/homeScreen")
    Call<HomeModel> getHomeScreenData(
            @Field("student") String email
    );

    @FormUrlEncoded
    @POST("api/chapter")
    Call<SubjectModel> getChaptersOfASubject(
            @Field("subject") String id
    );


    @FormUrlEncoded
    @POST("api/course")
    Call<CourseModel> getCoursesOfAChapter(
            @Field("chapter") String id
    );

    @FormUrlEncoded
    @POST("api/assignment")
    Call<AssignmentModel> getAllAssignments(
            @Field("course") String courseId
    );


    @Multipart
    @POST("api/assignment_submission")
    Call<UploadAssignmentModel> submitAssignment(@Part("assignment") RequestBody assignmentId,
                                                 @Part("student") RequestBody studentId,
                                                 @Part MultipartBody.Part doc);

    @FormUrlEncoded
    @POST("api/alreadysubmitassignment")
    Call<ViewAssignmentModel> getAllSubmittedAssignments(
            @Field("student") String stdId
    );

    @FormUrlEncoded
    @POST("api/test")
    Call<TestCourseModel> getAllCourseTests(
            @Field("course") String courseId
    );

    @FormUrlEncoded
    @POST("api/testquestion")
    Call<TestQuestionModel> getAllQuestionsOfATest(
            @Field("test") String testId
    );

    @FormUrlEncoded
    @POST("api/resultsubmit")
    Call<ResponseBody> submitTestResult(
            @Field("test") String testId,
            @Field("student") String stdId,
            @Field("marks") String marks
    );

    @FormUrlEncoded
    @POST("api/mocktestquestion")
    Call<TestQuestionModel> getAllQuestionsOfAMockTest(
            @Field("mocktest") String mockTestId
    );

    @FormUrlEncoded
    @POST("api/teacherListClaseWise")
    Call<TeacherModel> getAllTeachers(
            @Field("class") String classId
    );

    @FormUrlEncoded
    @POST("api/studentChat")
    Call<ChatHistoryModel> getChatHistory(
            @Field("teacher") String teacherId,
            @Field("student") String studentId
    );

    @FormUrlEncoded
    @POST("api/studentChat")
    Call<ResponseBody> sendChat(
            @Field("teacher") String teacherId,
            @Field("student") String studentId,
            @Field("sms") String msg
    );


    @FormUrlEncoded
    @POST("api/studentRegister")
    Call<UserSignUp> signUp(
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mob,
            @Field("password") String pwd,
            @Field("address") String address,
            @Field("pancard_type") String panType,
            @Field("pancard_number") String panNo
    );


    @FormUrlEncoded
    @POST("api/alreadysubmittest")
    Call<TestPerformanceModel> getAllTestResult(
            @Field("student") String name
    );

    @FormUrlEncoded
    @POST("api/video")
    Call<VideoPlayListModel> getAllVideoPlayList(
            @Field("course") String courseId
    );

    @FormUrlEncoded
    @POST("api/videoWatchList")
    Call<ResponseBody> addVideoToWatchList(
            @Field("video") String videoId,
            @Field("student") String studentId
    );

    @FormUrlEncoded
    @POST("api/videoWatchList")
    Call<WatchListModel> viewWatchList(
            @Field("student") String studentId
    );

    @FormUrlEncoded
    @POST("api/studentProfile")
    Call<ResponseBody> editProfile(
            @Field("studentId") String studentId,
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("password") String password,
            @Field("bank_name") String bankName,
            @Field("branch") String branch,
            @Field("bank_account") String bankAccount,
            @Field("ifsc_code") String ifscCode,
            @Field("pancard_type") String panType,
            @Field("pancard_number") String panNo

    );

    @FormUrlEncoded
    @POST("api/studentProfileView")
    Call<ViewProfileModel> viewProfile(
            @Field("studentId") String studentId

    );


    @GET("Paytm_Android_App/init_transaction.php")
    Call<PaytmChecksum> getToken(@Query("orderId") String orderId,
                                 @Query("custId") String studentId,
                                 @Query("tnxAmount") String amount
    );


    @FormUrlEncoded
    @POST("api/orders")
    Call<ResponseBody> createOrder(@Field("quiz_id") String quizId,
                                   @Field("order_id") String orderId,
                                   @Field("student_id") String studentId,
                                   @Field("mid") String mid,
                                   @Field("paid_amt") String cost,
                                   @Field("txn_id") String transactionId,
                                   @Field("payment_mode") String paymentMode,
                                   @Field("currency") String currency,
                                   @Field("gateway_name") String gatewayName,
                                   @Field("bank_txn_id") String bankTxnId,
                                   @Field("bank_name") String bankName,
                                   @Field("check_sum_hash") String checkSum,
                                   @Field("status ") String status
    );

    @FormUrlEncoded
    @POST("api/orders")
    Call<OrderModel> viewOrders(@Field("student_id") String studentId
    );
}
