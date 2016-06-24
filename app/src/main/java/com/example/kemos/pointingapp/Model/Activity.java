package com.example.kemos.pointingapp.Model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kemos on 4/23/2016.
 */
public class Activity implements Comparable<Activity>{

    String userId ;
    String activityId ;
    String userName ;
    String activityURL ;
    String activityType ;
    String point ;
    String status ;
    String studyGroup ;
    String date ;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(String studyGroup) {
        this.studyGroup = studyGroup;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityURL() {
        return activityURL;
    }

    public void setActivityURL(String activityURL) {
        this.activityURL = activityURL;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
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
