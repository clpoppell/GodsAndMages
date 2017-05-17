package com.tadbolmont.homecoming;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class AboutDialog extends DialogPreference{
	
	public AboutDialog(Context context, AttributeSet attrs){
		super(context, attrs);
		
		setDialogLayoutResource(R.layout.about_layout);
	}
	
	/** Hide the cancel button */
	@Override
	protected void onPrepareDialogBuilder(Builder builder){
		super.onPrepareDialogBuilder(builder);
		builder.setNegativeButton(null, null);
	}
}
