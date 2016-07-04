package com.example.kemos.pointingapp.Model;

/**
 * Created by kemos on 4/23/2016.
 */
public class User implements Comparable<User> {
    String userName ;
    String password ;
    String userPoints ;
    String studyGroup ;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserPoints() {
        return userPoints;
    }

    public String getStudyGroup() {
        return studyGroup;
    }


    @Override
    public int compareTo(User user) {
        if(Integer.parseInt(this.getUserPoints())  == Integer.parseInt(user.getUserPoints()))
            return 0;
        else
            return Integer.parseInt(this.getUserPoints()) > Integer.parseInt(user.getUserPoints())? -1:1;
    }
}
