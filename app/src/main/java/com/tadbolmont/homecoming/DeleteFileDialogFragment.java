package com.tadbolmont.homecoming;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

// This class provides a simple confirmation dialog for all needed purposes
public class DeleteFileDialogFragment extends DialogFragment{
	public DeleteFileDialogFragment(){ super(); }
	/*
	The activity that creates an instance of this dialog fragment must
	implement this interface in order to receive event callbacks.
	Each method passes the DialogFragment in case the host needs to query it.
	*/
	public interface DeleteFileDialogListener{
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	DeleteFileDialogListener mListener;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try{
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener= (DeleteFileDialogListener)activity;
		}
		catch(ClassCastException e){
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.confirm_text)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						mListener.onDialogPositiveClick(DeleteFileDialogFragment.this);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						mListener.onDialogNegativeClick(DeleteFileDialogFragment.this);
					}
				});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
