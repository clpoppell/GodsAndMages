package com.tadbolmont.homecoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gods_and_mages_engine.Battle;
import gods_and_mages_engine.Monster;
import gods_and_mages_engine.Player_Char.Player;

public class MainScreen extends BaseActivity{
	private int id;
	private Player pc;
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
		
		//Get the Intent that started this activity and extract the extras
		Intent intent = getIntent();
		id= intent.getIntExtra(SavedGamesDisplay.EXTRA_MESSAGE_ID, 0);
		
		setupPlayerCharacter();
	}
	
	private void setupPlayerCharacter(){
		pc= Player.getPlayer();
		m= new Monster("Goblin", "", 30, 15, 15, 25, 20, 4, 20);
		m2= new Monster("Spider", "", 5, 15, 15, 25, 20, 4, 20);
		m3= new Monster("Rat", "", 30, 15, 15, 25, 20, 4, 20);
		
		mList.add(m);
		mList.add(m2);
		mList.add(m3);
		
		battle= new Battle(mList);
		
		//TextView textView= (TextView)findViewById(R.id.textView);
		//textView.append(pc.toString() +"\n\n");
	}
	
	public void encounter(View view){
		battle.pcAttack(2);
		battle.pcAttack(0);
		battle.pcAttack(1);
		
		TextView textView= (TextView)findViewById(R.id.textView);
		textView.append(battle.printResults() +"\n");
		scroll();
	}
	
	public void talk(View view){
		pc.addExp(20);
		TextView textView= (TextView)findViewById(R.id.textView);
		textView.setText(pc.toString() +"\n\n");
		
		scroll();
	}
	
	public void beginBattle(View view){
		
	}
	
	private void scroll(){
		ScrollView sv= (ScrollView)findViewById(R.id.scrollView2);
		sv.fullScroll(ScrollView.FOCUS_DOWN);
	}
}
