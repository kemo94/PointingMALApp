package com.example.kemos.pointingapp.Model;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kemos on 4/23/2016.
 */
public class FirebaseOperation {
    Context context;
    static DatabaseReference mDatabase;
    int tmpPoint ;
    boolean added = false;
    public FirebaseOperation(Context context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public void addUser(String userName , String studyGroup , String password,String userType) {

        Map<String, String> user = new HashMap<String, String>();
        user.put("studyGroup", studyGroup);
        user.put("password", password);
        if ( userType.equals("student"))
        user.put("userPoints", "0");
        mDatabase.child(userType).child(userName).setValue(user);


    }

    public void addActivity( String userName, String activityURL, String activityType, String point,String studyGroup) {

        Map<String, String> activity = new HashMap<String, String>();
        activity.put("userName", userName);
        activity.put("activityURL", activityURL);
        activity.put("activityType", activityType);
        activity.put("point", point);
        activity.put("status", "Pending");
        activity.put("date", getCurrentTimeStamp());
        activity.put("studyGroup", studyGroup);
        mDatabase.child("activities").push().setValue(activity);
    }

    public void updateStatusActivity( String activityId   , final String userName , String point, String status , boolean check) {
        added = check;
        Map<String, Object> activity = new HashMap<String, Object>();
        activity.put("status", status);
        mDatabase.child("activities").child(activityId).updateChildren(activity);

        if (status.equals("Approved")) {
            tmpPoint = Integer.parseInt(point);
            mDatabase.child("student").child(userName).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     User user = dataSnapshot.getValue(User.class);
                     tmpPoint += Integer.parseInt(user.getUserPoints() );

                     if ( !added ) {
                         added = true;
                         Map<String, Object> u = new HashMap<String, Object>();
                         u.put("userPoints", String.valueOf(tmpPoint));
                         mDatabase.child("student").child(userName).updateChildren(u);
                     }
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });
        }
    }



}