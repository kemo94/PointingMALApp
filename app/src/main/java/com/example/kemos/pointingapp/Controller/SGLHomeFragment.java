package com.example.kemos.pointingapp.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kemos.pointingapp.Model.Activity;
import com.example.kemos.pointingapp.R;

import java.util.ArrayList;


public class SGLHomeFragment extends Fragment {

    ArrayList<Activity> arrayActivities = new ArrayList<Activity>();
    boolean check = false ;
    String studyGroup ;
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
        listview = (ListView) rootView.findViewById(R.id.list);

        return rootView;
    }
    public void setActivities( ArrayList<Activity> arrayActivities){
        this.arrayActivities = arrayActivities ;

    }
}