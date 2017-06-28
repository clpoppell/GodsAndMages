package gods_and_mages_engine.Items;

import gods_and_mages_engine.World;

/**
 * Used for equipable items that add a {@code Trait} to the player character
 * @see gods_and_mages_engine.Abilities.BaseTrait
 */
public class Accessory extends EquipableItem{
	public final String trait;
	
	public Accessory(String name, String namePlural, String desc, int price, String traitName){
		super(name, namePlural, desc, price);
		trait= traitName;
	}
	
	@Override
	public String toString(){
		return super.toString() + World.getTrait(trait).toString();
	}
}
