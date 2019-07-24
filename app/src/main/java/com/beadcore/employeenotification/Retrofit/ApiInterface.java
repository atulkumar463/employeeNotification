package com.beadcore.employeenotification.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("login")
    Call<LoginResponse> login(@Body JsonObject jsonObject);


    @POST("token")
    Call<LoginResponse> updateToken(@Body JsonObject jsonObject);


    @GET("simplified")
    Call<SimpplifiedAttendence> simplifiedAttendence();


    @POST("change_password")
    Call<LoginResponse> changePassword(@Body JsonObject jsonObject);

    @POST("forget_password")
    Call<LoginResponse> forgetPassword(@Body JsonObject jsonObject);


    @POST("detailed")
    Call<DetailedAttendenceResponse> detailedAttendence(@Body JsonObject jsonObject);




 /*   @POST("AttendanceSystem/login/verifyLogin")
    Call<LoginVerifyResponse> loginVerify(@Body LoginVerifyRequest req);

    @POST("AttendanceSystem/login/resetPassword")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest req);

    @POST("AttendanceSystem/GetAttendance/GetDetails")
    Call<List<AttendanceLogResponse>> getAttendanceLogs(@Body AttendanceLogRequest req);*/

}
