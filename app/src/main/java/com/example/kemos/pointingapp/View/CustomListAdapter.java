package com.example.kemos.pointingapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kemos.pointingapp.Model.Activity;
import com.example.kemos.pointingapp.R;

import java.util.ArrayList;


public class CustomListAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    private final ArrayList<Activity> activityItemArray;

    public CustomListAdapter(Context c, ArrayList<Activity> activityItemArray) {

        this.context = c;
        this.activityItemArray = activityItemArray;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {



        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_list_cell, null);


            TextView postContent = (TextView) convertView.findViewById(R.id.activityType);
            String activityType = activityItemArray.get(position).getActivityType() ;
            postContent.setText(activityType.substring(0,activityType.length()-6));

            TextView activityPoint = (TextView) convertView.findViewById(R.id.activity_point);
            activityPoint.setText(activityItemArray.get(position).getPoint() + " pts");

            TextView date = (TextView) convertView.findViewById(R.id.date);
            date.setText(activityItemArray.get(position).getDate());

            TextView status = (TextView) convertView.findViewById(R.id.status);
            status.setText(activityItemArray.get(position).getStatus());

            return convertView;
    }

    @Override
    public int getCount() {
        return activityItemArray.size();
    }

    @Override
    public Object getItem(int position) {
        return activityItemArray.get(position).getStatus();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}