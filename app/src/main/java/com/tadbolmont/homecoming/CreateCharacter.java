package com.tadbolmont.homecoming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateCharacter extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
	public static final String EXTRA_MESSAGE= "com.tadbolmont.homecoming.MESSAGE";
	
	Spinner raceSpinner;
	Spinner classSpinner;
	Spinner jobSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_character);
		
		//Populates Race Spinner
		raceSpinner= (Spinner)findViewById(R.id.race_spinner);
		raceSpinner.setOnItemSelectedListener(this);
		//Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> raceAdapter= ArrayAdapter.createFromResource(this, R.array.race_list, android.R.layout.simple_spinner_item);
		//Specify the layout to use when the list of choices appears
		raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Apply the raceAdapter to the spinner
		raceSpinner.setAdapter(raceAdapter);
		
		//Populates Class Spinner
		classSpinner= (Spinner)findViewById(R.id.class_spinner);
		classSpinner.setOnItemSelectedListener(this);
		//Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> classAdapter= ArrayAdapter.createFromResource(this, R.array.class_list, android.R.layout.simple_spinner_item);
		//Specify the layout to use when the list of choices appears
		classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Apply the classAdapter to the spinner
		classSpinner.setAdapter(classAdapter);
		
		//Populates Job Spinner
		jobSpinner= (Spinner)findViewById(R.id.job_spinner);
		jobSpinner.setOnItemSelectedListener(this);
		//Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> jobAdapter= ArrayAdapter.createFromResource(this, R.array.job_list, android.R.layout.simple_spinner_item);
		//Specify the layout to use when the list of choices appears
		jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Apply the jobAdapter to the spinner
		jobSpinner.setAdapter(jobAdapter);
	}
	
	public void startGame(View view){
		String[] charInfo= new String[4];
		EditText editText;
		Intent intent= new Intent(this, MainScreen.class);
		
		editText= (EditText)findViewById(R.id.name_editText);
		charInfo[0]= editText.getText().toString();
		charInfo[1]= raceSpinner.getSelectedItem().toString();
		charInfo[2]= classSpinner.getSelectedItem().toString();
		charInfo[3]= jobSpinner.getSelectedItem().toString();
		intent.putExtra(EXTRA_MESSAGE, charInfo);
		
		startActivity(intent);
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){}
	
	public void onNothingSelected(AdapterView<?> parent){}
}
