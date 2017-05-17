package gods_and_mages_engine;

public class LivingCreature{
	//region Variables
	private String name;
	private int currentHitPoints;
	private int maximumHitPoints;
	private double hitRate;
	private double avoidance;
	
	//Base Stats
	private int str; // Physical attack modifier, goes into PC attackPower stat
	private int sta; // Physical defensive modifier, goes into PC defenseValue stat
	
	private int agi; //
	private int speed; //
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
	
	public void setCurrentHitPoints(int currentHitPoints){ this.currentHitPoints = currentHitPoints; }
	
	public int getMaximumHitPoints(){ return maximumHitPoints; }
	
	public void setMaximumHitPoints(int maximumHitPoints){ this.maximumHitPoints = maximumHitPoints; }
	
	public double getHitRate(){ return hitRate; }
	
	public void setHitRate(double hitRate){ this.hitRate = hitRate; }
	
	public double getAvoidance(){ return avoidance; }
	
	public void setAvoidance(double avoidance){ this.avoidance = avoidance; }
	
	public int getStr(){ return str; }
	
	public void setStr(int mod){ this.str += mod; }
	
	public int getSta(){ return sta; }
	
	public void setSta(int mod){ this.sta += mod; }
	
	public int getAgi(){ return agi; }
	
	public void setAgi(int mod){ this.agi += mod; }
	
	public int getSpeed(){ return speed; }
	
	public void setSpeed(int mod){ this.speed += mod; }
	//endregion
	
	@Override
	public String toString(){
		return this.getName();
	}
}
