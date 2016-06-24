package com.example.kemos.pointingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.kemos.pointingapp.Controller.UserTypeActivity;

public class Splash extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
       //  requestWindowFeature(Window.FEATURE_NO_TITLE);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, UserTypeActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }

}
