package com.example.kemos.pointingapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kemos.pointingapp.Model.User;
import com.example.kemos.pointingapp.R;

import java.util.ArrayList;


public class CustomLeaderboardAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    private final ArrayList<User> userItemArray;

    public CustomLeaderboardAdapter(Context c, ArrayList<User> userItemArray) {

        this.context = c;
        this.userItemArray = userItemArray;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {



        if (convertView == null)
            convertView = inflater.inflate(R.layout.leaderboard_list_cell, null);


            TextView userName = (TextView) convertView.findViewById(R.id.userName);
            userName.setText(userItemArray.get(position).getUserName());

            TextView userPoint = (TextView) convertView.findViewById(R.id.userPoint);
            userPoint.setText(userItemArray.get(position).getUserPoints() + " pts");

            return convertView;
    }

    @Override
    public int getCount() {
        return userItemArray.size();
    }

    @Override
    public Object getItem(int position) {
        return userItemArray.get(position).getUserName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}