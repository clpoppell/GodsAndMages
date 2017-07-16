package gods_and_mages_engine.Quests;

import java.util.Map;

import gods_and_mages_engine.Player_Char.PlayerCharacter;

/**
 * Used to define quests whose completion involves collecting a specified item.
 */
public class CollectQuest extends BaseQuest{
	public final String targetItem;
	public final int itemAmount;
	
	public CollectQuest(String name, String description, String targetItem, int itemAmount,
						int rewardExperiencePoints, int rewardGold, Map<String, Integer> rewardItems){
		super(name, description, rewardExperiencePoints, rewardGold, rewardItems);
		this.targetItem= targetItem;
		this.itemAmount= itemAmount;
	}
	
	/**
	 * Removes {@code itemAmount} of {@code targetItem} from player character's inventory, then calls
	 * super method.
	 */
	@Override
	public void completeQuest(){
		PlayerCharacter pc= PlayerCharacter.getPlayerCharacter();
		pc.removeItemFromInventory(targetItem, itemAmount);
		super.completeQuest();
	}
	
	/**
	 * Checks amount of {@code targetItem} in player character's inventory against {@code itemAmount}.
	 * @return {@code true} if {@linkplain gods_and_mages_engine.Player_Char.PlayerCharacter#checkItemQuantity}
	 * greater than or equal to {@code itemAmount}
	 */
	@Override
	public boolean isComplete(){
		PlayerCharacter pc= PlayerCharacter.getPlayerCharacter();
		if(pc.checkItemQuantity(targetItem) >= itemAmount){ return true; }
		return false;
	}
}
