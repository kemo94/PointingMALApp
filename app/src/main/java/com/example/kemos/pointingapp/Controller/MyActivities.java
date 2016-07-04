package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kemos.pointingapp.Model.Activity;
import com.example.kemos.pointingapp.R;
import com.example.kemos.pointingapp.View.CustomListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MyActivities extends Fragment {

    SharedPreferences sharedpreferences;
    ArrayList<Activity> arrayActivities = new ArrayList<Activity>();
    static DatabaseReference mDatabase;
    boolean check = false ;
    String userName ;
    ListView listview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


        if ( !check )
            Toast.makeText(getActivity(), R.string.no_activities, Toast.LENGTH_LONG).show();
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.listview_item, container, false);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            sharedpreferences = getActivity().getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
            userName = sharedpreferences.getString("UserName", null);
            getActivities();
            listview = (ListView) rootView.findViewById(R.id.list);

        return rootView;
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
                                if (activity.getUserName().equals(userName)) {
                                    arrayActivities.add(activity);

                                    Collections.sort(arrayActivities);
                                    listview.setAdapter(new CustomListAdapter(getActivity(), arrayActivities));
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

}