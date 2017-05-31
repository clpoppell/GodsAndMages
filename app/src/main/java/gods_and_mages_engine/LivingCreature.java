package gods_and_mages_engine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import gods_and_mages_engine.Abilities.BaseTrait;

public abstract class LivingCreature{
	
	
	//region Variables
	protected String name;
	protected int currentHitPoints;
	protected int maximumHitPoints;
	protected double hitRate;
	protected double avoidance;
	protected String status;
	protected Map<String, BaseTrait> traits= new LinkedHashMap<String, BaseTrait>();
	
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
	
	public void setName(String name){ this.name = name; }
	
	public int getCurrentHitPoints(){ return currentHitPoints; }
	
	public void setCurrentHitPoints(int currentHitPoints){ this.currentHitPoints= currentHitPoints; }
	
	public int getMaximumHitPoints(){ return maximumHitPoints; }
	
	public void setMaximumHitPoints(int maximumHitPoints){ this.maximumHitPoints= maximumHitPoints; }
	
	public double getHitRate(){ return hitRate; }
	
	public void setHitRate(double hitRate){ this.hitRate = hitRate; }
	
	public double getAvoidance(){ return avoidance; }
	
	public void setAvoidance(double avoidance){ this.avoidance = avoidance; }
	
	public String getStatus(){ return status; }
	
	public void setStatus(String status){ this.status= status; }
	
	public int getStr(){ return str; }
	
	public void setStr(int mod){ this.str += mod; }
	
	public int getSta(){ return sta; }
	
	public void setSta(int mod){ this.sta += mod; }
	
	public int getAgi(){ return agi; }
	
	public void setAgi(int mod){ this.agi += mod; }
	
	public int getSpeed(){ return speed; }
	
	public void setSpeed(int mod){ this.speed += mod; }
	
	// Temporary return values
	public int changeCurrentHP(int amount){
		this.currentHitPoints += amount;
		return this.currentHitPoints;
	}
	
	public abstract int getAtkPower();
	
	public abstract int getDefValue();
	//endregion
	
	public int damageDealt(LivingCreature defender){
		Random r= new Random();
		int range= (int)Math.ceil(this.getAtkPower() * 1.5) - this.getAtkPower();
		
		int damage= r.nextInt(range) + (this.getAtkPower() + 1);
		double reduction= defender.getDefValue()/(defender.getDefValue()+100.00);
		
		damage -= (int)Math.ceil((damage * reduction));
		
		if(damage <= 0){ damage= 1; }
		if(damage > defender.currentHitPoints){ damage= defender.currentHitPoints; }
		
		//Wakes up defender if asleep
		//if(defender.status == Types.statusSet.Asleep){ defender.status= Types.statusSet.Normal;}
		return damage;
	}
	
	@Override
	public String toString(){
		return this.getName();
	}
}
