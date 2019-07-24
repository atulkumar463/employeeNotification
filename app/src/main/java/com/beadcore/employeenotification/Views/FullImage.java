package com.beadcore.employeenotification.Views;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.Retrofit.ApiClient;
import com.beadcore.employeenotification.Utils.CustomImageView;
import com.beadcore.employeenotification.helper.PrefManager;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullImage extends AppCompatActivity {

    @BindView(R.id.logo_imageview)
    ImageView logoImageview;

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        ButterKnife.bind(this);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        Glide.with(FullImage.this).load(ApiClient.BASE_URL + PrefManager.readStringPref(FullImage.this, PrefManager.IMAGE))
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder).into(logoImageview);




    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleGestureDetector.onTouchEvent(ev);
        return true;
    }



private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    @Override
    public
    boolean onScale(ScaleGestureDetector scaleGestureDetector){
        mScaleFactor *= scaleGestureDetector.getScaleFactor();
        mScaleFactor = Math.max(0.1f,
                Math.min(mScaleFactor, 10.0f));
        logoImageview.setScaleX(mScaleFactor);
        logoImageview.setScaleY(mScaleFactor);
        return true;
    }
}

}