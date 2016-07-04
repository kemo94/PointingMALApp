package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Leaderboard extends Fragment {

    ArrayList<User> arrayUsers = new ArrayList<User>();
    ListView listview;
    String studyGroup ;
    SharedPreferences sharedpreferences;
    static DatabaseReference mDatabase;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
   }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.listview_item, container, false);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getActivity().getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
        studyGroup = sharedpreferences.getString("StudyGroup", null);

        getUsers();

        listview = (ListView) rootView.findViewById(R.id.list);

        return rootView;
    }
    public void getUsers( ){

        mDatabase.child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                if (td != null)
                    for (final Map.Entry<String, Object> mapEntry : td.entrySet()) {
                        mDatabase.child("student").child(mapEntry.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                user.setUserName( mapEntry.getKey());
                                if ( user.getStudyGroup().equals(studyGroup))
                                arrayUsers.add(user);
                                Collections.sort(arrayUsers);
                                listview.setAdapter(new CustomLeaderboardAdapter(getActivity(), arrayUsers));
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