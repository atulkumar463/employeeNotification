package com.beadcore.employeenotification.Views;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.beadcore.employeenotification.Firebase.MyFirebaseMessagingService;
import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.RecyclerAdapters.DetailedAttendanceAdapter;
import com.beadcore.employeenotification.Retrofit.ApiClient;
import com.beadcore.employeenotification.Retrofit.ApiInterface;
import com.beadcore.employeenotification.Retrofit.DetailedAttendenceResponse;
import com.beadcore.employeenotification.Retrofit.SimpplifiedAttendence;
import com.beadcore.employeenotification.helper.PrefManager;
import com.beadcore.employeenotification.helper.ProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedAttendance extends AppCompatActivity {


    RecyclerView recyclerView;
    DetailedAttendanceAdapter adapter;
    List<DetailedAttendenceResponse.DataBean> dataBeans;
    String date;
    TextView tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_attendance);
        tvDate= findViewById(R.id.tvdate);

        date= getIntent().getStringExtra("date");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = formatter.parse(date);
            tvDate.setText(new SimpleDateFormat("dd-MMM-YYYY ").format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        dataBeans= new ArrayList<>();
        recyclerView= findViewById(R.id.my_recycler_view);
        adapter= new DetailedAttendanceAdapter(DetailedAttendance.this, dataBeans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);



        CallGetDetailsApi();

        MyFirebaseMessagingService.setNotificationListener(new MyFirebaseMessagingService.NotificationListener() {
            @Override
            public void onNewNotification() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        CallGetDetailsApi();

                    }
                });


            }
        });
    }

    private void CallGetDetailsApi() {
        final ProgressView progressView= new ProgressView(DetailedAttendance.this);
        progressView.showLoader();


        JSONObject object= new JSONObject();

        try {
            object.put("date",date);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(object.toString(), JsonObject.class);


        ApiInterface apiInterface =
                ApiClient.getAuthorizationClient(DetailedAttendance.this, PrefManager.readStringPref(DetailedAttendance.this, PrefManager.LOGIN_TOKEN))
                        .create(ApiInterface.class);

        Call<DetailedAttendenceResponse> call = apiInterface.detailedAttendence(jsonObject);

        call.enqueue(new Callback<DetailedAttendenceResponse>() {
            @Override
            public void onResponse(Call<DetailedAttendenceResponse> call, Response<DetailedAttendenceResponse> response) {

                dataBeans.clear();
                progressView.hideLoader();
                if (response.body().isStatus()){
                    dataBeans.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(DetailedAttendance.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailedAttendenceResponse> call, Throwable t) {
                progressView.hideLoader();

            }
        });
    }
}
