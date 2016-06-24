package com.example.kemos.pointingapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kemos.pointingapp.Model.FirebaseOperation;
import com.example.kemos.pointingapp.R;


public class AddActivity extends AppCompatActivity {

    String [] activityPoint = {"1" ,"3","2","4","5","2"};
    SharedPreferences sharedpreferences;
    FirebaseOperation firebaseOperation ;
    Spinner spinner ;
    EditText activityURL ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(String.valueOf(R.string.my_prefs), Context.MODE_PRIVATE);
        firebaseOperation = new FirebaseOperation(getApplicationContext()) ;
      	setContentView(R.layout.add_activity);
        Button addBtn = (Button) findViewById(R.id.addBtn);
        activityURL = (EditText) findViewById(R.id.activity_url);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.items, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        addBtn.setOnClickListener(onButtonClick);
   }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_activity) {
            startActivity(new Intent(this,AddActivity.class));
            return true;
        }
        if (id == R.id.my_activities) {
            startActivity(new Intent(this,MyActivities.class));
            return true;
        }
        if (id == R.id.leader_board) {
            startActivity(new Intent(this,Leaderboard.class));
            return true;
        }

         return super.onOptionsItemSelected(item);

    }


    private View.OnClickListener onButtonClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if ( spinner.getSelectedItemPosition() == 0 )
                Toast.makeText(getApplicationContext(), R.string.please_select, Toast.LENGTH_LONG).show();
            else {
                firebaseOperation.addActivity(sharedpreferences.getString("UserId", null),
                        sharedpreferences.getString("UserName", null),
                        activityURL.getText().toString(),
                        spinner.getSelectedItem().toString(),
                        activityPoint[spinner.getSelectedItemPosition()],
                        sharedpreferences.getString("StudyGroup", null));
                Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_LONG).show();
            }
            activityURL.setText("");
        }
    };

}