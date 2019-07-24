package com.beadcore.employeenotification.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.helper.PrefManager;

public class Splash extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        PrefManager.writeStringPref(Splash.this,PrefManager.EMP_ID,"empId");
        timer();
    }



    private void timer() {
        new CountDownTimer(500,200) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                Intent intent;
                Log.i("debug", "onFinish: "+PrefManager.readStringPref(Splash.this,PrefManager.LOGIN_STATUS));
                if ((PrefManager.readStringPref(Splash.this, PrefManager.LOGIN_STATUS)).equalsIgnoreCase("true")) {
                     intent = new Intent(Splash.this, Home.class);
                }else {
                     intent = new Intent(Splash.this, LoginActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(Splash.this,
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(intent);
                    finish();
            }
        }.start();
    }
}
