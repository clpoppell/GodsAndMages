package gods_and_mages_engine;

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
	
	@Override
	public int getSpeed(){ return speed; }
	
	@Override
	public int getAtkPower(){ return str; }
	
	@Override
	public int getDefValue(){ return sta; }
	
	@Override
	public int getAccuracy(){ return agi; }
	
	@Override
	public int getAvoidance(){ return agi; }
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
