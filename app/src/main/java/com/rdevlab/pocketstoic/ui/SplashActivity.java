package com.rdevlab.pocketstoic.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rdevlab.pocketstoic.R;

/**
 * @author rms
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
//            }
//        }, 5000);
    }
}
