package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.kemos.pointingapp.Model.Activity;
import com.example.kemos.pointingapp.R;
import com.example.kemos.pointingapp.View.CustomSGLListAdapter;
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
    ListView listview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_item);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
        studyGroup = sharedpreferences.getString("StudyGroup",null);
        getActivities();
        listview = (ListView) findViewById(R.id.list);
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
                                listview.setAdapter(new CustomSGLListAdapter(getApplicationContext(), arrayActivities));
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


}