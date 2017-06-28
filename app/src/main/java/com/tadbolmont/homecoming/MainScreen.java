package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gods_and_mages_engine.Battle;
import gods_and_mages_engine.Items.InventoryItem;
import gods_and_mages_engine.Monster;
import gods_and_mages_engine.Player_Char.PlayerCharacter;
import gods_and_mages_engine.Quests.BaseQuest;
import gods_and_mages_engine.World;

public class MainScreen extends BaseActivity{
	private int id;
	private PlayerCharacter pc;
	private Battle battle;
	
	//temp
	private Monster m;
	private Monster m2;
	private Monster m3;
	List<Monster> mList= new ArrayList<Monster>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		// Sets textView's scrolling movement
		
		
		// Get the Intent that started this activity and extract the extras
		Intent intent = getIntent();
		id= intent.getIntExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, 0);
		
		setupPlayerCharacter();
	}
	
	private void setupPlayerCharacter(){
		pc= PlayerCharacter.getPlayerCharacter();
		m= new Monster("Goblin", "", 30, 15, 15, 25, 20, 4, 20);
		m2= new Monster("Spider", "", 5, 15, 15, 25, 20, 4, 20);
		m3= new Monster("Rat", "", 30, 15, 15, 25, 20, 4, 20);
		
		mList.add(m);
		mList.add(m2);
		mList.add(m3);
		
		battle= new Battle(mList);
		
		TextView textView= (TextView)findViewById(R.id.textView);
		textView.setText(pc.toString() +"\n\n");
		
		scroll();
		
		//TextView textView= (TextView)findViewById(R.id.textView);
		//textView.append(pc.toString() +"\n\n");
	}
	
	// Level Up
	public void talk(View view){
		pc.addItemToInventory("Gem", 1);
		
		showPlayer();
		scroll();
	}
	
	// Start Battle
	public void beginBattle(View view){
		String questName= "Find A Gem";
		if(pc.checkQuest(questName) == BaseQuest.QuestStatus.QUEST_IN_PROGRESS){
			BaseQuest quest= World.getQuest(questName);
			quest.completeQuest();
		}
		
		showPlayer();
		scroll();
	}
	
	// Fight
	public void encounter(View view){
		pc.changeLocation("Captain's Quarters");
		scroll();
	}
	
	// Inventory
	public void displayInventory(View view){
		String string= "";
		Iterator<InventoryItem> iterate2= pc.getInventory().values().iterator();
		while(iterate2.hasNext()){
			InventoryItem item= iterate2.next();
			string += item.toString() +"\n";
		}
		
		TextView textView= (TextView)findViewById(R.id.textView);
		textView.setText(string +"\n\n");
		
		scroll();
	}
	
	private void showPlayer(){
		TextView textView= (TextView)findViewById(R.id.textView);
		textView.setText(pc.toString() +"\n\n");
	}
	
	private void scroll(){
		ScrollView sv= (ScrollView)findViewById(R.id.scrollView);
		sv.fullScroll(ScrollView.FOCUS_DOWN);
	}
}
