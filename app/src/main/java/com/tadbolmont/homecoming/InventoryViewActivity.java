package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import gods_and_mages_engine.Items.InventoryItem;
import gods_and_mages_engine.Items.UsableItem;
import gods_and_mages_engine.Player_Char.PlayerCharacter;

public class InventoryViewActivity extends BaseActivity{
	private static final float ITEM_INFO_WEIGHT= 0.75f;
	private static final float USE_BUTTON_WEIGHT= 0.25f;
	
	private LinearLayout listLayout;
	private PlayerCharacter pc;
	private int id= 0;
	private int temp= 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_view);
		
		Intent intent= getIntent();
		id= intent.getIntExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, 0);
		
		listLayout= (LinearLayout)findViewById(R.id.itemList);
		pc= PlayerCharacter.getPlayerCharacter();
		
		showInventory();
	}
	
	private void showInventory(){
		Map<String, InventoryItem> items= pc.getInventory();
		final String[] itemNames= new String[items.size()];
		
		int i= 0;
		
		for(InventoryItem item : items.values()){
			LinearLayout row= new LinearLayout(this);
			row.setOrientation(LinearLayout.HORIZONTAL);
			
			TextView itemInfo= new TextView(this);
			
			itemInfo.setText(item.toString());
			itemInfo.setTextSize(getResources().getDimension(R.dimen.txtsize));
			itemInfo.setBackgroundResource(R.drawable.view_border);
			itemInfo.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, ITEM_INFO_WEIGHT));
			row.addView(itemInfo);
			
			final TextView useBtn= new TextView(this);
			
			// temporary
			useBtn.setText("Use");
			useBtn.setTextSize(getResources().getDimension(R.dimen.button_txtsize));
			useBtn.setGravity(Gravity.CENTER);
			
			useBtn.setBackgroundResource(R.drawable.view_border);
			// temporary
			useBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, USE_BUTTON_WEIGHT));
			
			if(item.getItem() instanceof UsableItem){
				useBtn.setId(i);
				
				useBtn.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v){
						Button temp= (Button)findViewById(R.id.equipBtn);
						temp.setText(itemNames[v.getId()]);
					}
				});
			}// temporary
			//else{ useBtn.setTextColor(getResources().getColor(R.color.textColorSecondary)); }
			
			row.addView(useBtn);
			
			itemNames[i]= item.getName();
			i++;
			listLayout.addView(row);
		}
	}
	
	public void toMain(View view){
		Intent intent= new Intent(this, MainScreen.class);
		intent.putExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, id);
		startActivity(intent);
	}
	
	public void equipView(View view){
		
	}
}
