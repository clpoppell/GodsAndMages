package gods_and_mages_engine;

import java.util.LinkedHashMap;
import java.util.Map;

import gods_and_mages_engine.Abilities.BaseAbility;
import gods_and_mages_engine.Abilities.BaseTrait;

public abstract class LivingCreature{
	//region Constants
	
	
	//region Variables
	protected String name;
	protected int currentHitPoints;
	protected int maximumHitPoints;
	
	protected String status;
	protected boolean canBattle= true;
	
	protected Map<String, BaseTrait> traits= new LinkedHashMap<String, BaseTrait>();
	protected Map<String, BaseAbility> abilities= new LinkedHashMap<String, BaseAbility>();
	
	//Base Stats
	protected int str; // Physical attack modifier, goes into PC attackPower stat
	protected int sta; // Physical defensive modifier, goes into PC defenseValue stat
	
	protected int agi; //
	protected int speed; //
	//endregion
	
	public LivingCreature(String name, int maximumHitPoints, int str, int sta, int agi, int speed){
		this.name= name;
		this.maximumHitPoints= maximumHitPoints;
		this.currentHitPoints= maximumHitPoints;
		this.str= str;
		this.sta= sta;
		this.agi= agi;
		this.speed= speed;
	}
	
	//region Accessors
	public String getName(){ return name; }
	
	public void setName(String name){ this.name= name; }
	
	public int getCurrentHitPoints(){ return currentHitPoints; }
	
	public void setCurrentHitPoints(int currentHitPoints){ this.currentHitPoints= currentHitPoints; }
	
	public int getMaximumHitPoints(){ return maximumHitPoints; }
	
	public String getStatus(){ return status; }
	
	public void setStatus(String status){ this.status= status; }
	
	// Applies a change to creature's current hp
	//
	public void changeCurrentHP(int amt){
		if(currentHitPoints + amt >= maximumHitPoints){
			currentHitPoints= maximumHitPoints;
		}
		else if(currentHitPoints + amt <= 0){
			currentHitPoints= 0;
			status= "Dead";
		}
		else{ currentHitPoints += amt; }
	}
	
	public abstract int getSpeed();
	
	public abstract int getAtkPower();
	
	public abstract int getDefValue();
	
	public abstract int getAccuracy();
	
	public abstract int getAvoidance();
	//endregion
	
	@Override
	public String toString(){
		return this.name;
	}
}
