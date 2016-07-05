package com.example.kemos.pointingapp.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kemos.pointingapp.Model.Activity;
import com.example.kemos.pointingapp.R;
import com.example.kemos.pointingapp.View.CustomSGLListAdapter;

import java.util.ArrayList;


public class SGLHomeFragment extends Fragment {

    ListView listview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.listview_item, container, false);
        listview = (ListView) rootView.findViewById(R.id.list);

        return rootView;
    }
    public void setActivities( ArrayList<Activity> arrayActivities){
        listview.setAdapter(new CustomSGLListAdapter(getActivity(), arrayActivities));

    }
}