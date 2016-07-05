package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kemos.pointingapp.Model.Activity;
import com.example.kemos.pointingapp.Model.CheckDeviceStatus;
import com.example.kemos.pointingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SGLHome extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    ArrayList<Activity> arrayActivities = new ArrayList<Activity>();
    static DatabaseReference mDatabase;
    String studyGroup ;
    SGLHomeFragment sglfragment;
    private Toolbar toolbar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.sgl_home_activity);
        if (!CheckDeviceStatus.isNetworkAvailable(getApplicationContext()))
            Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
        else {
             sglfragment =(SGLHomeFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_movies);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            sharedpreferences = getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
            studyGroup = sharedpreferences.getString("StudyGroup", null);
            getActivities();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        }
   }

    public boolean checkActivity(String activityId  , Activity activity  ) {
        for ( int i = 0 ;i < arrayActivities.size() ; i++ )
            if ( activityId.equals(arrayActivities.get(i).getActivityId()) ) {
                arrayActivities.set(i , activity);
                return true;
            }
        return false ;
    }
    public void getActivities() {
        mDatabase.child("activities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                if (td != null)
                    for (final Map.Entry<String, Object> mapEntry : td.entrySet()) {
                        mDatabase.child("activities").child(mapEntry.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Activity activity = dataSnapshot.getValue(Activity.class);
                                activity.setActivityId(mapEntry.getKey());
                                if (activity.getStudyGroup().equals(studyGroup) && !checkActivity(mapEntry.getKey() , activity) )
                                    arrayActivities.add(activity);

                                Collections.sort(arrayActivities);
                                sglfragment.setActivities(arrayActivities);
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
        getMenuInflater().inflate(R.menu.sgl_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signout) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(this,UserTypeActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


}