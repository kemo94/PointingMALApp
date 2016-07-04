package com.example.kemos.pointingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.kemos.pointingapp.Controller.SGLHome;
import com.example.kemos.pointingapp.Controller.StudentHome;
import com.example.kemos.pointingapp.Controller.UserTypeActivity;

public class Splash extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
       //  requestWindowFeature(Window.FEATURE_NO_TITLE);

        final SharedPreferences sharedpreferences  = getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if ( sharedpreferences.getString("UserName", null) == null )
                startActivity(new Intent(Splash.this, UserTypeActivity.class));
                else {
                    if (  sharedpreferences.getString("userType", null).equals("student") )
                    startActivity(new Intent(Splash.this, StudentHome.class));
                    else
                    startActivity(new Intent(Splash.this, SGLHome.class));

                }
                finish();
            }
        }, secondsDelayed * 1000);
    }

}
