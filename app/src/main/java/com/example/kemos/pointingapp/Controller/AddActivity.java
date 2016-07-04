package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kemos.pointingapp.Model.CheckDeviceStatus;
import com.example.kemos.pointingapp.Model.FirebaseOperation;
import com.example.kemos.pointingapp.R;


public class AddActivity extends Fragment {

    String [] activityPoint = {"0" ,"1" ,"3","2","4","5","2"};
    SharedPreferences sharedpreferences;
    FirebaseOperation firebaseOperation ;
    Spinner spinner ;
    EditText activityURL ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_activity, container, false);
        sharedpreferences = getActivity().getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
        firebaseOperation = new FirebaseOperation(getActivity());
        Button addBtn = (Button) rootView.findViewById(R.id.addBtn);
        activityURL = (EditText) rootView.findViewById(R.id.activity_url);
        activityURL.setVisibility(View.GONE);

        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinner.getSelectedItemPosition() == 1 || spinner.getSelectedItemPosition() == 2)
                    activityURL.setVisibility(View.VISIBLE);
                else
                    activityURL.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.items, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        addBtn.setOnClickListener(onButtonClick);
        return rootView;
    }
    private View.OnClickListener onButtonClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!CheckDeviceStatus.isNetworkAvailable(getActivity()))
                Toast.makeText(getActivity(), R.string.no_network, Toast.LENGTH_LONG).show();
            else{
                if (spinner.getSelectedItemPosition() == 0)
                    Toast.makeText(getActivity(), R.string.please_select, Toast.LENGTH_LONG).show();
                else {
                    if ((spinner.getSelectedItemPosition() == 1 || spinner.getSelectedItemPosition() == 2) &&
                            activityURL.getText().toString().length() == 0)
                        Toast.makeText(getActivity(), R.string.missing_url, Toast.LENGTH_LONG).show();
                    else {
                        if ( activityURL.getText().toString().length() != 0 && !URLUtil.isValidUrl( activityURL.getText().toString()) )
                            Toast.makeText(getActivity(), R.string.invalid_url, Toast.LENGTH_LONG).show();
                         else{
                            firebaseOperation.addActivity(sharedpreferences.getString("UserName", null),
                                    activityURL.getText().toString(),
                                    spinner.getSelectedItem().toString(),
                                    activityPoint[spinner.getSelectedItemPosition()],
                                    sharedpreferences.getString("StudyGroup", null));
                            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                activityURL.setText("");
            }
        }
    };

}