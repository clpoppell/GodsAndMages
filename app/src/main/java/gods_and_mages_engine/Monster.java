package gods_and_mages_engine;

public class Monster extends LivingCreature{
	private final double baseHitRate;
	private final double baseAvoidance;
	private final int rewardExperiencePoints;
	private final int rewardGold;
	
	public Monster(String name, String desc, int maximumHitPoints, int strength, int stamina,
				   int agility, int speed, double baseHitRate, double baseAvoidance,
				   int rewardExperiencePoints, int rewardGold){
		super(name, maximumHitPoints, strength, stamina, agility, speed);
		
		this.baseHitRate= baseHitRate;
		this.baseAvoidance= baseAvoidance;
		this.rewardExperiencePoints= rewardExperiencePoints;
		this.rewardGold= rewardGold;
		/*
		status= Types.statusSet.Normal;
		lootTable= new List<LootItem>();
		targetingChance= new List<int>{ 25,25,25,25 }; //Sets up List of values for monster to use in targeting
		*/
	}
	
	@Override
	public int getAtkPower(){
		return this.getStr();
	}
	
	@Override
	public int getDefValue(){ return this.getSta(); }
}
