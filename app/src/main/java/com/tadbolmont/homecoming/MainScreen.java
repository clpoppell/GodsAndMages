package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import gods_and_mages_engine.Database.SaveGameDBHelper;
import gods_and_mages_engine.Player_Char.Player;

public class MainScreen extends BaseActivity{
	private Intent intent;
	private SaveGameDBHelper dbHelper= new SaveGameDBHelper(this);
	private int id;
	private int load;
	private Player pc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		//Get the Intent that started this activity and extract the extras
		intent= getIntent();
		id= intent.getIntExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, 0);
		load= intent.getIntExtra(SavedGamesDisplay.EXTRA_MESSAGE_LOAD, 0);
		
		setupPlayerCharacter();
	}
	
	private void setupPlayerCharacter(){
		//Capture the layout's TextView and set the string as its text
		TextView textView= (TextView)findViewById(R.id.textView);
		
		if(load == 0){ newGame(); }
		else{ loadGame(); }
		
		saveGame();
		
		textView.append(pc.toString() +"\n\n");
	}
	
	private void newGame(){
		String[] charInfo= {
				intent.getStringExtra(SavedGamesDisplay.EXTRA_MESSAGE_NAME),
				intent.getStringExtra(SavedGamesDisplay.EXTRA_MESSAGE_RACE),
				intent.getStringExtra(SavedGamesDisplay.EXTRA_MESSAGE_CLASS),
				intent.getStringExtra(SavedGamesDisplay.EXTRA_MESSAGE_JOB)
		};
		pc= new Player(charInfo, id);

		dbHelper.insertCharacter(id, charInfo, pc.getStatArray(), pc.getEquipmentArray(), pc.getStatus(), pc.getLocationName());
	}
	
	private void loadGame(){
		String[] charInfo= dbHelper.loadSave(id);
		pc= new Player(charInfo, id, true);
	}
	
	private void saveGame(){
		dbHelper.updateStatus(id, pc.getGold(), pc.getExp(), pc.getCurrentHitPoints(), pc.getStatus());
	}
}
