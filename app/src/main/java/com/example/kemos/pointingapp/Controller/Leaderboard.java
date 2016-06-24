package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.kemos.pointingapp.Model.User;
import com.example.kemos.pointingapp.R;
import com.example.kemos.pointingapp.View.CustomLeaderboardAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Leaderboard extends AppCompatActivity {

    ArrayList<User> arrayUsers = new ArrayList<User>();
    ListView listview;
    String studyGroup ;
    SharedPreferences sharedpreferences;
    static DatabaseReference mDatabase;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_item);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
        studyGroup = sharedpreferences.getString("StudyGroup",null);

        getUsers();

        listview = (ListView) findViewById(R.id.list);
   }

    public void getUsers( ){

        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                if (td != null)
                    for (final Map.Entry<String, Object> mapEntry : td.entrySet()) {
                        mDatabase.child("users").child(mapEntry.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                user.setUserId( mapEntry.getKey());
                                if ( user.getStudyGroup().equals(studyGroup))
                                arrayUsers.add(user);
                                Collections.sort(arrayUsers);
                                listview.setAdapter(new CustomLeaderboardAdapter(getApplicationContext(), arrayUsers));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_activity) {
            startActivity(new Intent(this,AddActivity.class));
            return true;
        }
        if (id == R.id.my_activities) {
            startActivity(new Intent(this,MyActivities.class));
            return true;
        }
        if (id == R.id.leader_board) {
            startActivity(new Intent(this,Leaderboard.class));
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


}