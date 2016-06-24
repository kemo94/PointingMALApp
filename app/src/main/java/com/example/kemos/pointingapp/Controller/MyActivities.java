package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.kemos.pointingapp.R;
import com.example.kemos.pointingapp.Model.Activity;
import com.example.kemos.pointingapp.View.CustomListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyActivities extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    ArrayList<Activity> arrayActivities = new ArrayList<Activity>();
    static DatabaseReference mDatabase;
    String userId ;
    ListView listview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_item);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("UserId",null);
        getActivities();
         listview = (ListView) findViewById(R.id.list);
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
                                if (activity.getUserId().equals(userId)) {
                                    arrayActivities.add(activity);
                                    listview.setAdapter(new CustomListAdapter(getApplicationContext(), arrayActivities));
                                }

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