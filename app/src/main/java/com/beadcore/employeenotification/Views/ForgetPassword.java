package com.beadcore.employeenotification.Views;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.Retrofit.ApiClient;
import com.beadcore.employeenotification.Retrofit.ApiInterface;
import com.beadcore.employeenotification.Retrofit.LoginResponse;
import com.beadcore.employeenotification.helper.ProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    CardView submitbutton;
    TextInputEditText email,empId;
    TextInputLayout tiemail,tiempId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        submitbutton = findViewById(R.id.submit_button);
        email = findViewById(R.id.teEmail);
        empId = findViewById(R.id.teEmpId);
        tiemail= findViewById(R.id.tiEmail);
        tiempId= findViewById(R.id.tiEmpId);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(email.getText().toString())){
                    tiemail.setError("Please enter proper email id");
                }else if (nullValidator(empId.getText().toString()))
                    tiempId.setError("Please enter Employee id");
                else {
                    callForgetPassApi(email.getText().toString(),empId.getText().toString());
                }
            }
        });

    }

    private void callForgetPassApi(String email, String empid) {
        final ProgressView progressView= new ProgressView(ForgetPassword.this);
        ApiInterface apiInterface= ApiClient.getClient(ForgetPassword.this).create(ApiInterface.class);
        progressView.showLoader();
        JSONObject object = new JSONObject();

        try {
            object.put("user_email", email);
            object.put("employee_id", empid);
        } catch (JSONException e) {
            e.printStackTrace();
        }


      Gson  gson = new Gson();

        JsonObject jsonObject = gson.fromJson(object.toString(), JsonObject.class);

        progressView.showLoader();
        Call<LoginResponse> call= apiInterface.forgetPassword(jsonObject);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressView.hideLoader();
                if (response.body().isStatus()){
                    Toast.makeText(ForgetPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgetPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressView.hideLoader();
                Toast.makeText(ForgetPassword.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean nullValidator(String value) {
        return value.matches("");
    }

}
