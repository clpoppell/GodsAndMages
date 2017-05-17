package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gods_and_mages_engine.Database.SaveGameDBHelper;

public class SavedGamesDisplay extends BaseActivity{
	public static final String EXTRA_MESSAGE= "com.tadbolmont.homecoming.MESSAGE";
	
	Intent intent;
	
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
		
		if(pIntent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0) == 2){
			titleText= "Select Saved Game";
			intent= new Intent(this, MainScreen.class);
		}
		
		// Capture the layout's TextView and set the string as its text
		TextView textView= (TextView)findViewById(R.id.savesTitle);
		textView.setText(titleText);
		
		// Query database for each save slot and sets button text if save data exists
		SaveGameDBHelper dbHelper= new SaveGameDBHelper(this);
		
		String[] saveInfoOne= dbHelper.loadCharacter(1);
		if(saveInfoOne != null){
			Button button= (Button)findViewById(R.id.saveOne);
			button.setText(saveInfoOne[0] +"\n"+ saveInfoOne[1] +" "+ saveInfoOne[2]);
		}
		
		String[] saveInfoTwo= dbHelper.loadCharacter(2);
		if(saveInfoTwo != null){
			Button button= (Button)findViewById(R.id.saveTwo);
			button.setText(saveInfoTwo[0] +"\n"+ saveInfoTwo[1] +" "+ saveInfoTwo[2]);
		}
		
		String[] saveInfoThree= dbHelper.loadCharacter(3);
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
				break;
			case R.id.saveTwo:
				id= 2;
				break;
			case R.id.saveThree:
				id= 3;
				break;
		}
		
		intent.putExtra(EXTRA_MESSAGE, id);
		startActivity(intent);
	}
}
