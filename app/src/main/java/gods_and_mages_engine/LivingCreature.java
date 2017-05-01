package gods_and_mages_engine;

public class LivingCreature{
	public String name;
	public int currentHitPoints;
	public int maximumHitPoints;
	public double hitRate;
	public double avoidance;
	
	//Base Stats
	public int strength; //Physical attack modifier, goes into PC attackPower stat
	public int stamina; //Physical defensive modifier, goes into PC defenseValue stat
	
	public int agility; //
	public int speed; //
	
	//Derived Stats
	protected int attackPower; //Monster attackPower is equal to strength
	protected int defenseValue; //Monster armorValue is equal to stamina
	
	public LivingCreature(String name, int maximumHitPoints, int currentHitPoints, int strength,
						  int stamina, int agility, int speed){
		this.name= name;
		this.maximumHitPoints= maximumHitPoints;
		this.currentHitPoints= currentHitPoints;
		this.strength= strength;
		this.stamina= stamina;
		this.agility= agility;
		this.speed= speed;
	}
}
