package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
	private final PlayerCharacter pc= PlayerCharacter.getPlayerCharacter();
	private int id= 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_view);
		
		Intent intent= getIntent();
		id= intent.getIntExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, 0);
		listLayout= (LinearLayout)findViewById(R.id.itemList);
		
		showInventory();
	}
	
	// Method needs major refactoring, works but feels extremely hackish
	private void showInventory(){
		final Map<String, InventoryItem> items= pc.getInventory();
		final String[] itemNames= new String[items.size()];
		final LinearLayout[] rows= new LinearLayout[items.size()];
		final TextView[] descriptions= new TextView[items.size()];
		
		int i= 0; // Used as index for storing in itemNames, descriptions, & rows
		
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
			useBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, USE_BUTTON_WEIGHT));
			
			if(item.getItem() instanceof UsableItem){
				useBtn.setId(i);
				
				useBtn.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v){
						String itemName= itemNames[v.getId()];
						InventoryItem item= items.get(itemName);
						UsableItem itemToUse= (UsableItem)(item.getItem());
						itemToUse.UseItem(pc);
						
						if(items.containsKey(itemName)){ descriptions[v.getId()].setText(item.toString()); }
						else{ listLayout.removeView(rows[v.getId()]); }
					}
				});
			}
			// temporary
			else{ useBtn.setTextColor(getResources().getColor(R.color.textColorSecondary)); }
			
			rows[i]= row;
			descriptions[i]= itemInfo;
			row.addView(useBtn);
			
			itemNames[i]= item.getItem().getKey();
			i++;
			listLayout.addView(row);
		}
	}
	
	public void toMain(View v){
		Intent intent= new Intent(this, ExplorationScreenActivity.class);
		intent.putExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, id);
		startActivity(intent);
	}
	
	public void equipView(View v){
		Intent intent= new Intent(this, EquipScreenActivity.class);
		intent.putExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, id);
		startActivity(intent);
	}
}
