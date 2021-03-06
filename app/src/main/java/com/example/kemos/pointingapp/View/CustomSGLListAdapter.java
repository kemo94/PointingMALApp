package com.example.kemos.pointingapp.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kemos.pointingapp.Model.Activity;
import com.example.kemos.pointingapp.Model.CheckDeviceStatus;
import com.example.kemos.pointingapp.Model.FirebaseOperation;
import com.example.kemos.pointingapp.R;

import java.util.ArrayList;


public class CustomSGLListAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    private final ArrayList<Activity> activityItemArray;

    FirebaseOperation firebaseOperation ;
    public CustomSGLListAdapter(Context c, ArrayList<Activity> activityItemArray) {
        firebaseOperation = new FirebaseOperation(c) ;
        this.context = c;
        this.activityItemArray = activityItemArray;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (!activityItemArray.get(position).getStatus().equals("Pending"))
                convertView = inflater.inflate(R.layout.sgl_list_status_cell, null);
             else {
                   if ( activityItemArray.get(position).getActivityURL().length() != 0 )
                    convertView = inflater.inflate(R.layout.sgl_list_url_cell, null);
                    else
                    convertView = inflater.inflate(R.layout.sgl_list_cell, null);
            }
        }
        TextView studentName = (TextView) convertView.findViewById(R.id.studentName);
        studentName.setText(activityItemArray.get(position).getUserName());

        TextView activity = (TextView) convertView.findViewById(R.id.activityType);
        String activityType = activityItemArray.get(position).getActivityType() ;
        activity.setText(activityType.substring(0,activityType.length()-6));

        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(activityItemArray.get(position).getDate());

        if (activityItemArray.get(position).getStatus().equals("Pending")) {

            if ( activityItemArray.get(position).getActivityURL().length() != 0 ) {
                Button openURLBtn = (Button) convertView.findViewById(R.id.openURLBtn);
                openURLBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!CheckDeviceStatus.isNetworkAvailable(context))
                            Toast.makeText(context, R.string.no_network, Toast.LENGTH_LONG).show();
                        else {
                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(activityItemArray.get(position).getActivityURL()));

                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            try {
                                context.startActivity(intent);
                            }
                            catch (Exception e){
                                Toast.makeText(context, R.string.invalid_url, Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
            }
            Button rejectBtn = (Button) convertView.findViewById(R.id.rejectBtn);
            rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!CheckDeviceStatus.isNetworkAvailable(context))
                        Toast.makeText(context, R.string.no_network, Toast.LENGTH_LONG).show();
                    else {
                        firebaseOperation.updateStatusActivity(activityItemArray.get(position).getActivityId(),
                                activityItemArray.get(position).getUserName(),
                                activityItemArray.get(position).getPoint(),
                                "Rejected",
                                false);
                    }
                }
            });
            Button approveBtn = (Button) convertView.findViewById(R.id.approveBtn);

            approveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!CheckDeviceStatus.isNetworkAvailable(context))
                        Toast.makeText(context, R.string.no_network, Toast.LENGTH_LONG).show();
                    else {
                        firebaseOperation.updateStatusActivity(activityItemArray.get(position).getActivityId(),
                                activityItemArray.get(position).getUserName(),
                                activityItemArray.get(position).getPoint(),
                                "Approved",
                                false);
                    }
                }
            });
        }else{
            TextView status = (TextView) convertView.findViewById(R.id.status);
            status.setText(activityItemArray.get(position).getStatus());

        }

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