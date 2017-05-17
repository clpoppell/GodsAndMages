package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import gods_and_mages_engine.Database.SaveGameDBHelper;
import gods_and_mages_engine.Player_Char.Player;

public class MainScreen extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		setupPlayerCharacter();
	}
	
	private void setupPlayerCharacter(){
		//Get the Intent that started this activity and extract the string
		Intent intent= getIntent();
		int id= intent.getIntExtra(CreateCharacter.EXTRA_MESSAGE, 0);
		
		//Capture the layout's TextView and set the string as its text
		TextView textView= (TextView)findViewById(R.id.textView);
		
		SaveGameDBHelper dbHelper= new SaveGameDBHelper(this);
		String[] charInfo= dbHelper.loadCharacter(id);

		Player pc= new Player(charInfo[0], charInfo[1], charInfo[2], charInfo[3]);
		
		textView.setText(pc.toString());
	}
}
