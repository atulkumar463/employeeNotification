package com.beadcore.employeenotification.Views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.Retrofit.ApiClient;
import com.beadcore.employeenotification.Retrofit.ApiInterface;
import com.beadcore.employeenotification.Retrofit.LoginResponse;
import com.beadcore.employeenotification.helper.PrefManager;
import com.beadcore.employeenotification.helper.ProgressView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userId, password;
    TextInputLayout tiuser, tipass;
    TextView forgotPasswordButton;
    CardView cvLogin;
    ProgressView progressView;
    ApiInterface apiInterface;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = findViewById(R.id.teUserId);
        password = findViewById(R.id.tiPassword);
        forgotPasswordButton = findViewById(R.id.tvForgetPass);
        tipass = findViewById(R.id.tePassword);
        tiuser = findViewById(R.id.tiUsername);
        cvLogin = findViewById(R.id.cvLogin);
        userId.setFocusable(true);
        checkPermission();
        apiInterface = ApiClient.getClient(LoginActivity.this).create(ApiInterface.class);
        progressView = new ProgressView(LoginActivity.this);

        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nullValidator(userId.getText().toString()))
                    tiuser.setError("Please enter Employee Id");
                else if (!isValidEmail(userId.getText().toString()))
                    tiuser.setError("Please enter proper email id");
                else if (nullValidator(password.getText().toString())) {
                    tiuser.setError(null);
                    tipass.setError("Please Enter Password");
                } else {
                    tiuser.setError(null);
                    tipass.setError(null);
                    loginApiCall(userId.getText().toString(), password.getText().toString());
                }

            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,ForgetPassword.class));
            }
        });


    }

    private void loginApiCall(String username, String pass) {

        progressView.showLoader();

        JSONObject object = new JSONObject();

        try {
            object.put("user_email", username);
            object.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        gson = new Gson();

        JsonObject jsonObject = gson.fromJson(object.toString(), JsonObject.class);

        Call<LoginResponse> call = apiInterface.login(jsonObject);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                progressView.hideLoader();
                if (response.body().isStatus()) {

                    PrefManager.writeStringPref(LoginActivity.this, PrefManager.LOGIN_TOKEN, response.body().getToken());
                    PrefManager.writeStringPref(LoginActivity.this, PrefManager.IMAGE, response.body().getData().getEmployee_image());
                    PrefManager.writeStringPref(LoginActivity.this, PrefManager.COMPANY_NAME, response.body().getData().getCompany_name());
                    PrefManager.writeStringPref(LoginActivity.this, PrefManager.NAME, response.body().getData().getEmployee_name());


                    generateToken(response.body().getToken());


                } else {
                    Log.i("fail to api cll", "onResponse: ");
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressView.hideLoader();
                Toast.makeText(LoginActivity.this, "Something went wrong please check your internet connection", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void registerFirebaseToken(String token, String fcmToken) {
        progressView.showLoader();

        Log.i("fcmtoken", "registerFirebaseToken: " + fcmToken);

        if (fcmToken == null) {
            progressView.hideLoader();
            Toast.makeText(this, "Unable to register on google services for notification p[lease try later", Toast.LENGTH_SHORT).show();
        } else {

            PrefManager.writeStringPref(LoginActivity.this, PrefManager.FCM_TOKEN, fcmToken);

            JSONObject object = new JSONObject();

            try {

                object.put("firebase_token", fcmToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObject jsonObject = gson.fromJson(object.toString(), JsonObject.class);


            ApiClient.ResetClient();
            apiInterface = ApiClient.getAuthorizationClient(LoginActivity.this, token).create(ApiInterface.class);
            Call<LoginResponse> call = apiInterface.updateToken(jsonObject);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    progressView.hideLoader();
                    if (response.body().isStatus()) {
                        Toast.makeText(LoginActivity.this, "Welcome to notification app", Toast.LENGTH_SHORT).show();

                        PrefManager.writeStringPref(LoginActivity.this, PrefManager.LOGIN_STATUS, "true");

                        startActivity(new Intent(LoginActivity.this, Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressView.hideLoader();
                    Toast.makeText(LoginActivity.this, "Unable to connect server please check your internet connection", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void generateToken(final String token) {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.i("firebasetoken", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String FCMtoken = task.getResult().getToken();

                        // Log and toast

                        Log.i("firebasetoken token 45", FCMtoken);
                        registerFirebaseToken(token, FCMtoken);
                    }
                });


    }

    private void checkPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.INTERNET, Manifest.permission.WAKE_LOCK,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean nullValidator(String value) {
        return value.matches("");
    }


    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}