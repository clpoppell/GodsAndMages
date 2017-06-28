package gods_and_mages_engine.Items;

import gods_and_mages_engine.Player_Char.PlayerCharacter;

/**
 * Used for equipable items that modify the player character's defense value
 * @see PlayerCharacter#getDefValue()
 */
public class Armor extends EquipableItem{
	public final double armorMod;
	
	public Armor(String name, String namePlural, String desc, int price, double armorMod){
		super(name, namePlural, desc, price);
		this.armorMod= armorMod;
	}
	
	@Override
	public String toString(){
		return super.toString() + armorMod;
	}
}
