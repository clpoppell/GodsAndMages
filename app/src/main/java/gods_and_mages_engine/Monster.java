package gods_and_mages_engine;

public class Monster extends LivingCreature{
	public int id;
	public int rewardExperiencePoints;
	public int rewardGold;
	
	public Monster(int id, String name, int maximumHitPoints, int currentHitPoints, int strength,
				   int stamina, int agility, int speed, int rewardExperiencePoints, int rewardGold,
				   double baseHitRate, double baseAvoidance){
		super(name, maximumHitPoints, currentHitPoints, strength, stamina, agility, speed);
		
		this.id= id;
		this.rewardExperiencePoints= rewardExperiencePoints;
		this.rewardGold= rewardGold;
		/*
		status= Types.statusSet.Normal;
		lootTable= new List<LootItem>();
		targetingChance= new List<int>{ 25,25,25,25 }; //Sets up List of values for monster to use in targeting
		*/
	}
	
	public int getAttackPower(){
		return this.strength;
	}
	
	public int getDefenseValue(){
		return this.stamina;
	}
}
