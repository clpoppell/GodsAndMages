package gods_and_mages_engine.Quests;

public abstract class BaseQuest{
	public final String name;
	public final String description;
	
	// temporary
	public final String targetMonster= "";
	public final int monsterAmount= 0;
	
	public final String targetLocation= "";
	public final String targetAbility= "";
	public final String targetNPC= "";
	// temporary
	
	public final int rewardExperiencePoints;
	public final int rewardGold;
	private final String[] rewardItems;
	public final String reqardQuest= "";
	
	public BaseQuest(String name, String description, int rewardExperiencePoints, int rewardGold, String[] rewardItems){
		this.name= name;
		this.description= description;
		this.rewardExperiencePoints= rewardExperiencePoints;
		this.rewardGold= rewardGold;
		this.rewardItems= rewardItems;
	}
	
	public abstract String getKey();
	
	public String[] getRewardItems(){ return rewardItems.clone(); }
	
	protected boolean isComplete(){ return true; }
	
	@Override
	public String toString(){ return name +": "+ description; }
}
