package gods_and_mages_engine.Items;

/**
 * Super class for all items that can be equipped to enhance the player character.
 */
public abstract class EquipableItem extends BaseItem{
	public EquipableItem(String name, String namePlural, String description, int price){
		super(name, namePlural, description, price);
	}
}

