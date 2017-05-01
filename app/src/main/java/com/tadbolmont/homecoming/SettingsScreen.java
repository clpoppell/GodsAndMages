package com.tadbolmont.homecoming;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsScreen extends BaseActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// Display the fragment as the main content.
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}
	
	public static class SettingsFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			
			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.settings);
		}
	}
}