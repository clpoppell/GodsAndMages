package com.tadbolmont.homecoming;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gods_and_mages_engine.Database.SaveGameDBHelper;

public class ManageDataActivity extends BaseActivity implements DeleteFileDialogFragment.DeleteFileDialogListener{
	SaveGameDBHelper dbHelper;
	int id= 0;
	boolean[] empty= { true, true, true, true };
	Button button1;
	Button button2;
	Button button3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_files_delete);
		
		dbHelper= SaveGameDBHelper.getInstance();
		populateText();
	}
	
	private void populateText(){
		button1= (Button)findViewById(R.id.deleteSaveOne);
		button2= (Button)findViewById(R.id.deleteSaveTwo);
		button3= (Button)findViewById(R.id.deleteSaveThree);
		
		// Query database for each save slot and sets button text if save data exists
		String[] saveInfoOne= dbHelper.loadCharacterInfo(1);
		if(saveInfoOne != null){
			empty[1]= false;
			button1.setText(saveInfoOne[0] +"\n"+ saveInfoOne[1] +" "+ saveInfoOne[2]);
		}
		
		String[] saveInfoTwo= dbHelper.loadCharacterInfo(2);
		if(saveInfoTwo != null){
			empty[2]= false;
			button2.setText(saveInfoTwo[0] +"\n"+ saveInfoTwo[1] +" "+ saveInfoTwo[2]);
		}
		
		String[] saveInfoThree= dbHelper.loadCharacterInfo(3);
		if(saveInfoThree != null){
			empty[3]= false;
			button3.setText(saveInfoThree[0] +"\n"+ saveInfoThree[1] +" "+ saveInfoThree[2]);
		}
	}
	
	public void onDeleteSaveFileButtonClick(View view){
		switch(view.getId()){
			case R.id.deleteSaveOne:
				id= 1;
				break;
			case R.id.deleteSaveTwo:
				id= 2;
				break;
			case R.id.deleteSaveThree:
				id= 3;
				break;
		}
		
		if(!empty[id]){
			DeleteFileDialogFragment dialog= new DeleteFileDialogFragment();
			dialog.show(getFragmentManager(), "delete");
		}
	}
	
	public void allSavesButtonClick(View view){
		id= 0;
		DeleteFileDialogFragment dialog= new DeleteFileDialogFragment();
		dialog.show(getFragmentManager(), "delete");
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog){
		empty[id]= true;
		
		if(id == 0){
			dbHelper.refreshDatabase();
			empty[1]= true;
			empty[2]= true;
			empty[3]= true;
			
			button1.setText("Empty");
			button2.setText("Empty");
			button3.setText("Empty");
		}
		else if(id == 1){
			dbHelper.deleteSave(id);
			button1.setText("Empty");
		}
		else if(id == 2){
			dbHelper.deleteSave(id);
			button2.setText("Empty");
		}
		else if(id == 3){
			dbHelper.deleteSave(id);
			button3.setText("Empty");
		}
	}
	
	@Override
	public void onDialogNegativeClick(DialogFragment dialog){}
}
