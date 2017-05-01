package gods_and_mages_engine;

import gods_and_mages_engine.Items.*;

public class Player extends LivingCreature{
	private int exp;
	public int level;
	public Weapon currentWeapon;
	
	public Player(String name, int exp, Weapon currentWeapon, int maximumHitPoints,
				  int currentHitPoints, int strength, int stamina, int agility, int speed){
		super(name, maximumHitPoints, currentHitPoints, strength, stamina, agility, speed);
		
		this.setExp(exp);
		this.currentWeapon= currentWeapon;
		/*
		status= Types.statusSet.Normal;
		*/
	}
	
	public int getExp(){
		return exp;
	}
	
	public void setExp(int exp){
		this.exp += exp;
		if(this.exp < 5){ this.level= 0; } //
		else if(this.exp >= 2500){ this.level= 50; } //
		else{ this.level= (int)Math.floor(Math.sqrt(this.exp)); } //
	}
}
