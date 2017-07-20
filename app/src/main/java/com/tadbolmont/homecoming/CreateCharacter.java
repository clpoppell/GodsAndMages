package com.tadbolmont.homecoming;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import gods_and_mages_engine.Player_Char.PlayerCharacter;

public class CreateCharacter extends BaseActivity implements AdapterView.OnItemSelectedListener{
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
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){}
	
	public void onNothingSelected(AdapterView<?> parent){}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			View v= getCurrentFocus();
			if(v instanceof EditText){
				Rect outRect= new Rect();
				v.getGlobalVisibleRect(outRect);
				if(!outRect.contains((int)event.getRawX(), (int)event.getRawY())){
					v.clearFocus();
					InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		}
		return super.dispatchTouchEvent( event );
	}
	
	public void startGame(View view){
		EditText editText= (EditText)findViewById(R.id.name_editText);
		Intent pIntent= getIntent();
		Intent intent= new Intent(this, ExplorationScreenActivity.class);
		
		int id= pIntent.getIntExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, 0);
		intent.putExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, id);
		
		PlayerCharacter.makePlayer(id, new String[]{ editText.getText().toString(), raceSpinner.getSelectedItem().toString(),
				classSpinner.getSelectedItem().toString(), jobSpinner.getSelectedItem().toString() });
		
		startActivity(intent);
	}
}
