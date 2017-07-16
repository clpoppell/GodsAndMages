package gods_and_mages_engine.Quests;

import java.util.Map;

import gods_and_mages_engine.Player_Char.PlayerCharacter;

/**
 * Super class for all quest classes, to implement shared fields and methods.
 * <p> Allows all quest objects to be stored in the same collections. </p>
 */
public abstract class BaseQuest{
	/** Defines completion status codes for classes that use quest objects */
	public enum QuestStatus{ QUEST_NOT_STARTED, QUEST_IN_PROGRESS, QUEST_COMPLETE }
	
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
	private final Map<String, Integer> rewardItems;
	public final String rewardQuest= "";
	
	public BaseQuest(String name, String description, int rewardExperiencePoints, int rewardGold,
					 Map<String, Integer> rewardItems){
		this.name= name;
		this.description= description;
		this.rewardExperiencePoints= rewardExperiencePoints;
		this.rewardGold= rewardGold;
		this.rewardItems= rewardItems;
	}
	
	/**
	 * Handles results of quest completion.
	 * <p>
	 *     Implementing classes override this method to handle any aspects unique to the appropriate
	 *     quest type, then call the super class method to handle the shared aspects.
	 * </p>
	 */
	public void completeQuest(){
		PlayerCharacter pc= PlayerCharacter.getPlayerCharacter();
		pc.addExp(rewardExperiencePoints);
		pc.addGold(rewardGold);
		for(String itemName : rewardItems.keySet()){
			pc.addItemToInventory(itemName, rewardItems.get(itemName));
		}
	}
	
	/**
	 * Implementing classes must override this method to provide a means of checking whether
	 * the quest objectives have been completed.
	 */
	public abstract boolean isComplete();
	
	@Override
	public String toString(){ return name +": "+ description; }
}
