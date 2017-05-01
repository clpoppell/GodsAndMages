package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity{
	public static final String EXTRA_MESSAGE= "com.tadbolmont.homecoming.MESSAGE";
	
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newGame(View view){
		Intent intent= new Intent(this, SavedGamesDisplay.class);
		intent.putExtra(EXTRA_MESSAGE, "Select Save File");
		startActivity(intent);
    }
    
    public void loadGame(View view){
		Intent intent= new Intent(this, SavedGamesDisplay.class);
		intent.putExtra(EXTRA_MESSAGE, "Select Saved Game");
		startActivity(intent);
	}
	
	public void chaptersScreen(View view){
		
	}
	
	public void gameSettings(View view){
		Intent intent= new Intent(this, SettingsScreen.class);
		startActivity(intent);
	}
}
