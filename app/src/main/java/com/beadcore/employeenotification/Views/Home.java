package com.beadcore.employeenotification.Views;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beadcore.employeenotification.Firebase.MyFirebaseMessagingService;
import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.RecyclerAdapters.NotificationAdapter;
import com.beadcore.employeenotification.Retrofit.ApiClient;
import com.beadcore.employeenotification.Retrofit.ApiInterface;
import com.beadcore.employeenotification.Retrofit.SimpplifiedAttendence;
import com.beadcore.employeenotification.helper.PrefManager;
import com.beadcore.employeenotification.helper.ProgressView;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView notificationRecyclerView;
    List<SimpplifiedAttendence.DataBean> attendanceObjects;
    NotificationAdapter notificationAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //AttendanceHelper attendanceHelper;
    ProgressView progressView;
    TextView tvname;
    boolean fromNotification = false;
    ImageView image;
    CircleImageView profileImaghe;
    Runnable graphData;
    HashMap<String, String> dailyAttendenceMarkIn;
    HashMap<String, String> dailyAttendenceMarkOut;
    HashMap<String, String> overtime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbaar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        MyFirebaseMessagingService.setNotificationListener(new MyFirebaseMessagingService.NotificationListener() {
            @Override
            public void onNewNotification() {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        getAttendenceApiCall();
                    }
                });

            }
        });

        View headerView = navigationView.getHeaderView(0);
        tvname = headerView.findViewById(R.id.tvName);
        image = headerView.findViewById(R.id.ivImage);
        profileImaghe = headerView.findViewById(R.id.profile_image);
        profileImaghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, FullImage.class);

                // Get the transition name from the string
                String transitionName = getString(R.string.transition_string);

                // Define the view that the animation will start from
                View viewStart = findViewById(R.id.profile_image);

                ActivityOptionsCompat options =

                        ActivityOptionsCompat.makeSceneTransitionAnimation(Home.this,
                                viewStart,   // Starting view
                                transitionName    // The String
                        );
                //Start the Intent
                ActivityCompat.startActivity(Home.this, intent, options.toBundle());

            }
        });

        tvname.setText(PrefManager.readStringPref(Home.this, PrefManager.NAME));

        Glide.with(Home.this).load(ApiClient.BASE_URL + PrefManager.readStringPref(Home.this, PrefManager.IMAGE))
                // .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .into(profileImaghe);
       /* try {

            Log.i("atul", "onCreate: " + PrefManager.readStringPref(Home.this, PrefManager.IMAGE));
            byte[] decodedString = Base64.decode(PrefManager.readStringPref(Home.this, PrefManager.IMAGE), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImaghe.setImageBitmap(decodedByte);

        } catch (NullPointerException e) {
            e.printStackTrace();
            tvname.setVisibility(View.GONE);
        }*/


        progressView = new ProgressView(Home.this);

        fromNotification = getIntent().getBooleanExtra("from_notification", false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        notificationRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        attendanceObjects = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                //  fetchAttendanceData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        notificationAdapter = new NotificationAdapter(Home.this, attendanceObjects);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        notificationRecyclerView.setLayoutManager(mLayoutManager);
        notificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notificationRecyclerView.setAdapter(notificationAdapter);

        getAttendenceApiCall();


        graphData = new Runnable() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void run() {

                Log.i("date", "run: i am running multiple times");
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, 1);
                int myMonth = c.get(Calendar.MONTH);

                dailyAttendenceMarkIn = new HashMap<>();
                dailyAttendenceMarkOut = new HashMap<>();
                overtime = new HashMap<>();
                SimpleDateFormat dateformetter = new SimpleDateFormat("yyyy-MM-dd");
                Log.i("startDate", "run: " + dateformetter.format(c.getTime()));
                Date date = null;

                while (myMonth == c.get(Calendar.MONTH)) {
                    Log.i("day Of month", "run: " + dateformetter.format(c.getTime()));

                    dailyAttendenceMarkIn.put(dateformetter.format(c.getTime()), "0");
                    dailyAttendenceMarkOut.put(dateformetter.format(c.getTime()), "0");
                    overtime.put(dateformetter.format(c.getTime()), "0");
                    c.add(Calendar.DAY_OF_MONTH, 1);

                }
                Log.i("day", "run: " + dailyAttendenceMarkIn.size());

                for (SimpplifiedAttendence.DataBean attendanceObject : attendanceObjects) {

                    try {
                        date = dateformetter.parse(attendanceObject.getDate());
                    } catch (ParseException e) {

                    }

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    Date date1 = null;
                    Date date2 = null;
                    long difference=0;
                    try {
                        date1 = format.parse(attendanceObject.getShift_in_time());
                        date2 = format.parse(attendanceObject.getIn_time());

                     difference = date2.getTime() - date1.getTime();
                    } catch (ParseException|NullPointerException e ) {
                        e.printStackTrace();
                        difference=0;

                    }

                    Log.i("timediffence", "run: date "+attendanceObject.getDate() +"time deiifrnce"+difference/60000);


                    dailyAttendenceMarkIn.put(attendanceObject.getDate(), String.valueOf(difference/60000));
                    dailyAttendenceMarkOut.put(attendanceObject.getDate(), attendanceObject.getOut_time());
                    overtime.put(attendanceObject.getDate(), attendanceObject.getOver_time());

                }




            }
        };
    }


    public void getAttendenceApiCall() {
        progressView.showLoader();
        attendanceObjects.clear();


        ApiInterface apiInterface =
                ApiClient.getAuthorizationClient(Home.this, PrefManager.readStringPref(Home.this, PrefManager.LOGIN_TOKEN))
                        .create(ApiInterface.class);

        Call<SimpplifiedAttendence> call = apiInterface.simplifiedAttendence();
        call.enqueue(new Callback<SimpplifiedAttendence>() {
            @Override
            public void onResponse(Call<SimpplifiedAttendence> call, Response<SimpplifiedAttendence> response) {

                if (response.body().isStatus()) {
                    attendanceObjects.addAll(response.body().getData());
                    progressView.hideLoader();
                    notificationAdapter.notifyDataSetChanged();


                    graphData.run();


                } else {
                    Toast.makeText(Home.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpplifiedAttendence> call, Throwable t) {
                progressView.hideLoader();
                Toast.makeText(Home.this, "Unabel to fetch details please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void openLoginActivity() {
        Intent intent = new Intent(Home.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.reset_pass) {


            startActivity(new Intent(Home.this, ResetPasswordActivity.class).putExtra("markin",dailyAttendenceMarkIn)
                    .putExtra("markout",dailyAttendenceMarkOut)
            .putExtra("overtime",overtime));
        } else if (id == R.id.statics) {
            Log.i("hashmap1", "onNavigationItemSelected: "+dailyAttendenceMarkIn);
            startActivity(new Intent(Home.this, GraphActivity.class).putExtra("markin",dailyAttendenceMarkIn)
                    .putExtra("markout",dailyAttendenceMarkOut)
                    .putExtra("overtime",overtime));

        }else if (id == R.id.logout) {
            PrefManager.writeStringPref(Home.this, PrefManager.LOGIN_STATUS, "false");
            PrefManager.clearPref(Home.this);
            deleteInstance();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


    public void deleteInstance() {

        progressView.showLoader();
        DeleteToken deleteToken = new DeleteToken();
        deleteToken.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }


    public class DeleteToken extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                Log.i("SDSD", "ERROR : " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(Home.this, "Logout Successfull", Toast.LENGTH_SHORT).show();
            progressView.hideLoader();
            startActivity(new Intent(Home.this, LoginActivity.class).
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            // openLoginActivity();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getAttendenceApiCall();

    }
}
