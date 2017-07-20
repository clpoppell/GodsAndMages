package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gods_and_mages_engine.Items.Accessory;
import gods_and_mages_engine.Items.Armor;
import gods_and_mages_engine.Items.BaseItem;
import gods_and_mages_engine.Items.Weapon;
import gods_and_mages_engine.Player_Char.PlayerCharacter;
import gods_and_mages_engine.World;

public class EquipScreenActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{
	private int id= 0;
	private PlayerCharacter pc= PlayerCharacter.getPlayerCharacter();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equip_screen);
		
		Intent intent= getIntent();
		id= intent.getIntExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, 0);
		
		showEquipment();
	}
	
	private void showEquipment(){
		TextView tvWeaponDesc= (TextView)findViewById(R.id.weaponDescription);
		tvWeaponDesc.setText(pc.getCurrentWpn().toString());
		
		TextView tvArmorDesc= (TextView)findViewById(R.id.armorDescription);
		tvArmorDesc.setText(pc.getCurrentArmor().toString());
		
		TextView tvAccOneDesc= (TextView)findViewById(R.id.accessoryOneDescription);
		tvAccOneDesc.setText(pc.getCurrentAccOne().toString());
		
		TextView tvAccTwoDesc= (TextView)findViewById(R.id.accessoryTwoDescription);
		tvAccTwoDesc.setText(pc.getCurrentAccTwo().toString());
		
		// Refactor this section
		List<String> weaponList= new ArrayList<String>();
		List<String> armorList= new ArrayList<String>();
		List<String> accessoryList= new ArrayList<String>();
		
		// Populates lists
		for(String itemName : pc.getInventory().keySet()){
			BaseItem item= World.getItem(itemName);
			
			if(item instanceof Weapon){ weaponList.add(itemName); }
			if(item instanceof Armor){ armorList.add(itemName); }
			if(item instanceof Accessory){ accessoryList.add(itemName); }
		}
		
		// Populates armorChoiceList
		Spinner weaponSpinner= (Spinner)findViewById(R.id.weaponChoiceList);
		weaponSpinner.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> weaponAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weaponList);
		weaponAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		weaponSpinner.setAdapter(weaponAdapter);
		
		// Populates armorChoiceList
		Spinner armorSpinner= (Spinner)findViewById(R.id.armorChoiceList);
		armorSpinner.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> armorAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armorList);
		armorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		armorSpinner.setAdapter(armorAdapter);
		
		// Populates accessoryOneChoiceList & accessoryTwoChoiceList
		Spinner accessoryOneSpinner= (Spinner)findViewById(R.id.accessoryOneChoiceList);
		accessoryOneSpinner.setOnItemSelectedListener(this);
		
		Spinner accessoryTwoSpinner= (Spinner)findViewById(R.id.accessoryTwoChoiceList);
		accessoryTwoSpinner.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> accessoryAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accessoryList);
		accessoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accessoryOneSpinner.setAdapter(accessoryAdapter);
		accessoryTwoSpinner.setAdapter(accessoryAdapter);
	}
	
	public void toMain(View v){
		Intent intent= new Intent(this, ExplorationScreenActivity.class);
		intent.putExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, id);
		startActivity(intent);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id){}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent){}
}
