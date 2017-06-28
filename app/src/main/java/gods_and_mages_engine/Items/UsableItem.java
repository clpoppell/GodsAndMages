package gods_and_mages_engine.Items;

import gods_and_mages_engine.LivingCreature;

/**
 * Used for items that can used from the in-game inventory
 */
public abstract class UsableItem extends BaseItem{
	public UsableItem(String name, String namePlural, String desc, int price){
		super(name, namePlural, desc, price);
	}
	
	/**
	 * Implementing classes must override this method to define what happens when the item type is used.
	 * @param target the livingCreature to be affected
	 */
	public abstract void UseItem(LivingCreature target);
}
