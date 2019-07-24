package com.beadcore.employeenotification.Views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.Retrofit.ApiClient;
import com.beadcore.employeenotification.Retrofit.ApiInterface;
import com.beadcore.employeenotification.Retrofit.LoginResponse;
import com.beadcore.employeenotification.helper.PrefManager;
import com.beadcore.employeenotification.helper.ProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetPasswordActivity extends AppCompatActivity {

    EditText employee_old_pass, employee_new_pass, employee_confirm_pass;
    TextView employee_id,toolbaarTitle;
    CardView submit_button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
        employee_id.setText(PrefManager.readStringPref(ResetPasswordActivity.this, PrefManager.NAME));
        toolbaarTitle= findViewById(R.id.tvToolbaar);
        toolbaarTitle.setText("Reset Password");

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (employee_old_pass.getText().length() < 6) {
                    Toast.makeText(ResetPasswordActivity.this, "Password Should be 8 characters Long", Toast.LENGTH_SHORT).show();
                } else if (employee_new_pass.getText().length() < 6) {
                    Toast.makeText(ResetPasswordActivity.this, "Password Should be 8 characters Long", Toast.LENGTH_SHORT).show();
                } else if (employee_confirm_pass.getText().length() < 6) {
                    Toast.makeText(ResetPasswordActivity.this, "Password Should be 8 characters Long", Toast.LENGTH_SHORT).show();
                } else if (!employee_new_pass.getText().toString().equals(employee_confirm_pass.getText().toString())) {
                    Toast.makeText(ResetPasswordActivity.this, "Confirm Password Doesn't Match", Toast.LENGTH_SHORT).show();
                } else {

                    resetPasswordAPiCall(employee_old_pass.getText().toString(),employee_confirm_pass.getText().toString());
                }
            }
        });
    }

    private void resetPasswordAPiCall(String oldPass, String newPass) {

        ProgressView progressView= new ProgressView(ResetPasswordActivity.this);
        ApiInterface apiInterface= ApiClient.getAuthorizationClient(ResetPasswordActivity.this,
                PrefManager.readStringPref(ResetPasswordActivity.this,PrefManager.LOGIN_TOKEN)).create(ApiInterface.class);

        progressView.showLoader();

        JSONObject object= new JSONObject();

        try {
            object.put("old_password",oldPass);
            object.put("new_password",newPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(object.toString(), JsonObject.class);

        Call<LoginResponse> call= apiInterface.changePassword(jsonObject);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().isStatus()){
                    Toast.makeText(ResetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(ResetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Unable to change password please check your internet connectivity", Toast.LENGTH_SHORT).show();
            }
        });






    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ResetPasswordActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {



      /*  InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);*/
    }

   /* public void resetPassword(String id, String old_pass, String new_pass) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmployee_id(id);
        resetPasswordRequest.setEmployee_old_password(old_pass);
        resetPasswordRequest.setEmployee_new_password(new_pass);
        Call<ResetPasswordResponse> call = apiService.resetPassword(resetPasswordRequest);

        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {

                if (response.body() != null && response.body().isStatus()) {
                    deleteInstance();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteInstance() {
        Log.i("SDSD", "DELETING FIREBASE TOKEN!!");
        DeleteFirebaseToken deleteFirebaseToken = new DeleteFirebaseToken();
        deleteFirebaseToken.execute();
    }
*/

    /*public class DeleteFirebaseToken extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
              *//*  editor.putString("firebase_token", null);
                editor.putString("employee_id", null);
                editor.putBoolean("login_status", false);
                editor.apply();*//*

                PrefManager.clearPref(ResetPasswordActivity.this);
            } catch (IOException e) {
                Log.i("SDSD", "ERROR : " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ResetPasswordActivity.this, "Try to Login With new Password!!", Toast.LENGTH_SHORT).show();
            openLoginActivity();
        }
    }*/

    private void openLoginActivity() {
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    public void initView() {
        employee_id = (TextView) findViewById(R.id.employee_id);
        employee_old_pass = (EditText) findViewById(R.id.old_password);
        employee_new_pass = (EditText) findViewById(R.id.new_password);
        employee_confirm_pass = (EditText) findViewById(R.id.confirm_new_password);
        submit_button =  findViewById(R.id.submit_button);
        setupUI(findViewById(R.id.parent_view));

    }
}
