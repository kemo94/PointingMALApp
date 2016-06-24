package com.example.kemos.pointingapp.Model;

/**
 * Created by kemos on 4/23/2016.
 */
public class User implements Comparable<User> {
    String userId ;
    String userName ;
    String password ;
    String userPoints ;
    String studyGroup ;
    String userEmail ;

    public void setUserPoints(String userPoints) {
        this.userPoints = userPoints;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPoints() {
        return userPoints;
    }

    public String getStudyGroup() {
        return studyGroup;
    }

    public String getUserEmail() {
        return userEmail;
    }


    @Override
    public int compareTo(User user) {
        if(Integer.parseInt(this.getUserPoints())  == Integer.parseInt(user.getUserPoints()))
            return 0;
        else
            return Integer.parseInt(this.getUserPoints()) > Integer.parseInt(user.getUserPoints())? -1:1;
    }
}
