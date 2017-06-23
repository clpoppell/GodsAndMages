package gods_and_mages_engine.Quests;


public class CollectQuest extends BaseQuest{
	public final String targetItem;
	public final int itemAmount;
	
	public CollectQuest(String name, String description, String targetItem, int itemAmount,
						int rewardExperiencePoints, int rewardGold, String[] rewardItems){
		super(name, description, rewardExperiencePoints, rewardGold, rewardItems);
		this.targetItem= targetItem;
		this.itemAmount= itemAmount;
	}
	
	@Override
	public boolean isComplete(){
		
		return super.isComplete();
	}
}
