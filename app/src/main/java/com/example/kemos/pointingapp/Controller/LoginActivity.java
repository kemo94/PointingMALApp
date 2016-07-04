package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kemos.pointingapp.Model.CheckDeviceStatus;
import com.example.kemos.pointingapp.Model.FirebaseOperation;
import com.example.kemos.pointingapp.R;
import com.example.kemos.pointingapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    FirebaseOperation firebaseOperation ;
    SharedPreferences sharedpreferences;
    ArrayList<User> arrayUsers = new ArrayList<User>();
    SharedPreferences.Editor editor;
    private DatabaseReference mDatabase;
    EditText userName , userPassword ;
    String userType;
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (!CheckDeviceStatus.isNetworkAvailable(getApplicationContext()))
            Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
        else {

            firebaseOperation = new FirebaseOperation(getApplicationContext());
            mDatabase = FirebaseDatabase.getInstance().getReference();

            sharedpreferences = getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            userType = sharedpreferences.getString("userType", null);

            getUsers();
            Button signUpBtn = (Button) findViewById(R.id.signupBtn);
            Button loginBtn = (Button) findViewById(R.id.loginBtn);
            userName = (EditText) findViewById(R.id.userName);
            userPassword = (EditText) findViewById(R.id.password);

            firebaseOperation = new FirebaseOperation(this);

            signUpBtn.setOnClickListener(onButtonClick);
            loginBtn.setOnClickListener(onButtonClick);
        }
   }


    public boolean checkUser(String userName ,String userPassword ) {
        for ( int i = 0 ;i < arrayUsers.size() ; i++ )
            if ( userName.equals(arrayUsers.get(i).getUserName()) && userPassword.equals(arrayUsers.get(i).getPassword()) ) {
                editor.putString("UserName", arrayUsers.get(i).getUserName());
                editor.putString("StudyGroup", arrayUsers.get(i).getStudyGroup());
                editor.commit();
                return true;
            }

        return false ;
    }

    public void getUsers( ){

        mDatabase.child(userType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                if (td != null)
                    for (final Map.Entry<String, Object> mapEntry : td.entrySet()) {
                        mDatabase.child(userType).child(mapEntry.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                user.setUserName( mapEntry.getKey());
                                arrayUsers.add(user);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                   }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
        private View.OnClickListener onButtonClick = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String name = userName.getText().toString();
                    String password = userPassword.getText().toString();

                    if (!CheckDeviceStatus.isNetworkAvailable(getApplicationContext()))
                        Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
                    else {
                        switch (v.getId()) {

                            case R.id.signupBtn: {

                                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

                                break;
                            }

                            case R.id.loginBtn: {
                                if (name.length() > 0 && password.length() > 0) {
                                    if (checkUser(name, password)) {

                                        if (userType.equals("SGL"))
                                            startActivity(new Intent(LoginActivity.this, SGLHome.class));
                                        else
                                            startActivity(new Intent(LoginActivity.this, StudentHome.class));
                                    } else
                                        Toast.makeText(getApplicationContext(), R.string.wrong_login, Toast.LENGTH_LONG).show();

                                } else
                                    Toast.makeText(getApplicationContext(), R.string.fill_field, Toast.LENGTH_LONG).show();

                                break;
                            }
                        }
                    }
                }
        };

}