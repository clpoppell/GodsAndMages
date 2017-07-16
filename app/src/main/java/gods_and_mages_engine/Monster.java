package gods_and_mages_engine;

/**
 * Used to define monster objects used for battles.
 */
public class Monster extends LivingCreature implements Cloneable{
	public final String desc;
	public final int rewardExperiencePoints;
	public final int rewardGold;
	
	public Monster(String name, String desc, int maximumHitPoints, int strength, int stamina,
				   int agility, int speed, int rewardExperiencePoints, int rewardGold){
		super(name, maximumHitPoints, strength, stamina, agility, speed);
		
		this.desc= desc;
		this.rewardExperiencePoints= rewardExperiencePoints;
		this.rewardGold= rewardGold;
		this.status= "Normal";
		/*
		lootTable= new List<LootItem>();
		*/
	}
	
	/** @return {@code speed} */
	@Override
	public int getSpeed(){ return speed; }
	
	/** @return {@code strength} */
	@Override
	public int getAtkPower(){ return strength; }
	
	/** @return {@code stamina} */
	@Override
	public int getDefValue(){ return stamina; }
	
	/** @return {@code agility} */
	@Override
	public int getAccuracy(){ return agility; }
	
	/** @return {@code agility} */
	@Override
	public int getAvoidance(){ return agility; }
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
