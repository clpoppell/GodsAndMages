package com.tadbolmont.homecoming;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

// Custom class that all activities in project are subclassed from.
// This class provides needed functionality for implementation of theme.
public class BaseActivity extends AppCompatActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_dark_theme", false)){
			setTheme(R.style.AppTheme_Dark);
		}
		else{ setTheme(R.style.AppTheme); }
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		registerChangeListener();
	}
	
	private void registerChangeListener(){
		final SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
		
		sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener(){
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
				if(!key.equals("pref_dark_theme")){ return; }
				recreate();
			}
		});
	}
}
