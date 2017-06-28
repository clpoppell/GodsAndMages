package gods_and_mages_engine.Items;

import gods_and_mages_engine.World;

/**
 * Used for items that cannot be removed from player inventory by normal means, nor multiples added.
 * <p>
 *     Serves as a marker to
 *     	{@linkplain gods_and_mages_engine.Player_Char.PlayerCharacter#removeItemFromInventory(String, int)}
 *     		to not allow removal, and to
 *     	{@linkplain gods_and_mages_engine.Player_Char.PlayerCharacter#addItemToInventory(String, int)}
 *     		to not allow for multiple additions.
 * </p>
 */
public class KeyItem extends BaseItem{
	public KeyItem(String name, String desc){
		super(name, name, desc, World.UNSELLABLE_ITEM_PRICE, World.UNSELLABLE_ITEM_PRICE);
	}
}
