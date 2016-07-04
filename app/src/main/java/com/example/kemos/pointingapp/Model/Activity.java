package com.example.kemos.pointingapp.Model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kemos on 4/23/2016.
 */
public class Activity implements Comparable<Activity>{

    String activityId ;
    String userName ;
    String activityURL ;
    String activityType ;
    String point ;
    String status ;
    String studyGroup ;
    String date ;

    public String getActivityURL() {
        return activityURL;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserName() {
        return userName;
    }

    public String getStudyGroup() {
        return studyGroup;
    }

    public String getPoint() {
        return point;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getActivityType() {
        return activityType;
    }


    @Override
    public int compareTo(Activity activity) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null , date2 = null ;
        try {
            date1 = format.parse(activity.getDate());
            date2 =  format.parse(this.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.compareTo(date2);
    }
}
