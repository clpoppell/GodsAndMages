package gods_and_mages_engine.Player_Char;

import java.util.ArrayList;
import gods_and_mages_engine.Ability;
import gods_and_mages_engine.Items.*;
import gods_and_mages_engine.LivingCreature;
import gods_and_mages_engine.World;

public class Player extends LivingCreature{
	//region Default values for player character
	private static final int DEFAULTHP= 10;
	private static final int DEFAULTSTR= 10;
	private static final int DEFAULTSTA= 10;
	private static final int DEFAULTAGI= 10;
	private static final int DEFAULTSPEED= 10;
	private static final Weapon DEFAULTWPN= (Weapon)World.getItem("Club");
	//endregion
	
	//region Variables
	private int exp;
	private int level;
	private CharRace charRace;
	private CharClass charClass;
	private CharJob charJob;
	private Weapon currentWpn; // Weapon currently equipped
	//Armor currentArmor; // Armor currently equipped
	//Accessory accOne; // 1st Accessory currently equipped
	//Accessory accTwo; // 2nd Accessory currently equipped
	//ArrayList<Item> inventory; // Current inventory;
	private ArrayList<Ability> abilities; // Abilities available to player
	//Derived Stats
	private int attackPower; // attackPower is equal to str
	private int defenseValue; // armorValue is equal to sta
	//endregion
	
	public Player(String name, String charRaceName, String charClassName, String charJobName){
		super(name, DEFAULTHP, DEFAULTSTR, DEFAULTSTA, DEFAULTAGI, DEFAULTSPEED);
		this.charRace= World.makeCharRace(charRaceName);
		this.charClass= World.makeCharClass(charClassName);
		this.charJob= World.makeCharJob(charJobName);
		this.currentWpn= DEFAULTWPN;
		applyMods();
		setAbilities();
	}
	
	//temporary
	public Player(String name, int exp, Weapon currentWpn, int maximumHitPoints,
				  int currentHitPoints, int strength, int stamina, int agility, int speed){
		super(name, maximumHitPoints, strength, stamina, agility, speed);
		
		this.setExp(exp);
		this.currentWpn = currentWpn;
		/*
		status= Types.statusSet.Normal;
		*/
	}
	
	private void applyMods(){
		//temporary
		this.setStr(this.charRace.getStrMod());
		this.setSta(this.charRace.getStaMod());
		this.setAgi(this.charRace.getAgiMod());
		this.setSpeed(this.charRace.getSpeedMod());
	}
	
	//region Accessors
	public int getExp(){
		return exp;
	}
	
	public void setExp(int exp){
		this.exp += exp;
		if(this.exp < 5){ this.level= 0; } //
		else if(this.exp >= 2500){ this.level= 50; } //
		else{ this.level= (int)Math.floor(Math.sqrt(this.exp)); } //
	}
	
	public int getLevel(){
		return level;
	}
	
	public CharRace getCharRace(){
		return charRace;
	}
	
	public CharClass getCharClass(){
		return charClass;
	}
	
	public CharJob getCharJob(){
		return charJob;
	}
	
	public Weapon getCurrentWpn(){
		return currentWpn;
	}
	
	public ArrayList<Ability> getAbilities(){
		return abilities;
	}
	
	private void setAbilities(){
		/*for(Ability ability : this.charRace.getAbilitiesGranted()){
			this.abilities.add(ability);
		}
		for(Ability ability : this.charClass.getAbilitiesGranted()){
			this.abilities.add(ability);
		}
		for(Ability ability : this.charJob.getAbilitiesGranted()){
			this.abilities.add(ability);
		}*/
	}
	//endregion
	
	//temporary
	@Override
	public String toString(){
		return this.getName() +"\n"+ this.charRace.getName() +"\n"+ this.charClass.getName() +"\n"+
				this.charJob.getName() +"\nHP: "+ this.getMaximumHitPoints() +"\nStr: "+ this.getStr()
				+"\nSta: "+ this.getSta() +"\nAgi: "+ this.getAgi() +"\nSpeed: "+ this.getSpeed()
				+"\n"+ currentWpn.toString();
	}
}
