package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gods_and_mages_engine.Database.SaveGameDBHelper;
import gods_and_mages_engine.Player_Char.Player;

public class SavedGamesDisplay extends BaseActivity{
	//region Extra Keys
	public static final String EXTRA_MESSAGE_ID= "com.tadbolmont.homecoming.ID";
	public static final String EXTRA_MESSAGE_LOAD= "com.tadbolmont.homecoming.LOAD";
	public static final String EXTRA_MESSAGE_NAME= "com.tadbolmont.homecoming.NAME";
	public static final String EXTRA_MESSAGE_RACE= "com.tadbolmont.homecoming.RACE";
	public static final String EXTRA_MESSAGE_CLASS= "com.tadbolmont.homecoming.CLASS";
	public static final String EXTRA_MESSAGE_JOB= "com.tadbolmont.homecoming.JOB";
	//endregion
	
	SaveGameDBHelper dbHelper= SaveGameDBHelper.getInstance();
	private Intent intent;
	private boolean load= false;
	private String[] saveInfoOne;
	private String[] saveInfoTwo;
	private String[] saveInfoThree;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_games_display);
		
		populateText();
	}
	
	private void populateText(){
		// Get the Intent that started this activity and extract the integer extra
		Intent pIntent= getIntent();
		String titleText= "";
		
		if(pIntent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0) == 1){
			titleText= "Select Save File";
			intent= new Intent(this, CreateCharacter.class);
		}
		else if(pIntent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0) == 2){
			titleText= "Select Saved Game";
			intent= new Intent(this, MainScreen.class);
			load= true;
			intent.putExtra(EXTRA_MESSAGE_LOAD, 1);
		}
		
		// Capture the layout's TextView and set the string as its text
		TextView textView= (TextView)findViewById(R.id.savesTitle);
		textView.setText(titleText);
		
		// Query database for each save slot and sets button text if save data exists
		saveInfoOne= dbHelper.loadCharacterInfo(1);
		if(saveInfoOne != null){
			Button button= (Button)findViewById(R.id.saveOne);
			button.setText(saveInfoOne[0] +"\n"+ saveInfoOne[1] +" "+ saveInfoOne[2]);
		}
		
		saveInfoTwo= dbHelper.loadCharacterInfo(2);
		if(saveInfoTwo != null){
			Button button= (Button)findViewById(R.id.saveTwo);
			button.setText(saveInfoTwo[0] +"\n"+ saveInfoTwo[1] +" "+ saveInfoTwo[2]);
		}
		
		saveInfoThree= dbHelper.loadCharacterInfo(3);
		if(saveInfoThree != null){
			Button button= (Button)findViewById(R.id.saveThree);
			button.setText(saveInfoThree[0] +"\n"+ saveInfoThree[1] +" "+ saveInfoThree[2]);
		}
		
		dbHelper.close();
	}
	
	public void onSaveFileButtonClick(View view){
		int id= 0;
		
		switch(view.getId()){
			case R.id.saveOne:
				id= 1;
				if(load){ Player.makePlayer(id, null); }
				break;
			case R.id.saveTwo:
				id= 2;
				if(load){ Player.makePlayer(id, null); }
				break;
			case R.id.saveThree:
				id= 3;
				if(load){ Player.makePlayer(id, null); }
				break;
		}
		
		intent.putExtra(EXTRA_MESSAGE_ID, id);
		startActivity(intent);
	}
}
