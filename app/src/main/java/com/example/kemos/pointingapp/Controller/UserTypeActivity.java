package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kemos.pointingapp.R;


public class UserTypeActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type_activity);
        sharedpreferences = getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        Button SGL = (Button) findViewById(R.id.SGL);
        Button student = (Button) findViewById(R.id.student);
        SGL.setOnClickListener(onButtonClick);
        student.setOnClickListener(onButtonClick);
   }

        private View.OnClickListener onButtonClick = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (v.getId()) {

                        case R.id.SGL: {

                                    editor.putString("userType", "SGL");
                                    editor.commit();

                            startActivity(new Intent(UserTypeActivity.this, LoginActivity.class));
                            break;
                        }
                        case R.id.student: {

                            editor.putString("userType", "users");
                            editor.commit();
                            startActivity(new Intent(UserTypeActivity.this, LoginActivity.class));
                            break;
                        }

                    }
                }
        };

}