package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gods_and_mages_engine.Player_Char.PlayerCharacter;

public class EquipScreenActivity extends BaseActivity{
	private int id= 0;
	private PlayerCharacter pc= PlayerCharacter.getPlayerCharacter();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equip_screen);
		
		
	}
	
	
	
	public void toMain(View v){
		Intent intent= new Intent(this, MainScreen.class);
		intent.putExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, id);
		startActivity(intent);
	}
}
